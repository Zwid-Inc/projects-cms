"use client";
import { useState, useEffect } from "react";
import { useRouter, usePathname } from "next/navigation";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";

interface Task {
  id: number;
  taskName: string;
  sequenceNr: number;
  description: string;
  creationDateTime: string;
}

export default function EditTaskPage() {
  const router = useRouter();
  const pathname = usePathname();
  const taskId = pathname.split("/")[2];

  const [task, setTask] = useState<Task | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTask = async () => {
      try {
        const token = localStorage.getItem("jwt");
        if (!token) {
          router.push("/login");
          return;
        }

        const response = await fetch(
          `http://localhost:8080/api/tasks/${taskId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) throw new Error("Failed to fetch task");

        const data = await response.json();
        setTask(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An error occurred");
      } finally {
        setIsLoading(false);
      }
    };

    if (taskId) fetchTask();
  }, [taskId, router]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const formData = new FormData(e.currentTarget);
    const updatedTask = {
      taskName: formData.get("taskName"),
      description: formData.get("description"),
      sequenceNr: Number(formData.get("sequenceNr")),
      creationDateTime: formData.get("creationDateTime"),
    };

    try {
      const token = localStorage.getItem("jwt");
      const response = await fetch(
        `http://localhost:8080/api/tasks/${taskId}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(updatedTask),
        }
      );

      if (!response.ok) throw new Error("Failed to update task");

      router.push(`/tasks/${taskId}`);
      router.refresh();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to update task");
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!task) return <div>Task not found</div>;

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle>Edit Task</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="taskName">Task Name</Label>
              <Input
                id="taskName"
                name="taskName"
                defaultValue={task.taskName}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="sequenceNr">Sequence Number</Label>
              <Input
                id="sequenceNr"
                name="sequenceNr"
                type="number"
                defaultValue={task.sequenceNr}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="description">Description</Label>
              <Textarea
                id="description"
                name="description"
                defaultValue={task.description}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="creationDateTime">Creation Date</Label>
              <Input
                id="creationDateTime"
                name="creationDateTime"
                type="datetime-local"
                defaultValue={new Date(task.creationDateTime)
                  .toISOString()
                  .slice(0, 16)}
                required
              />
            </div>
            {error && <div className="text-red-500">{error}</div>}
            <Button type="submit" disabled={isLoading}>
              {isLoading ? "Updating..." : "Update Task"}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
