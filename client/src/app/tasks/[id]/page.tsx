"use client";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";

import { useEffect, useState } from "react";
import { usePathname } from "next/navigation";
interface Task {
  id: number;
  taskName: string;
  sequenceNr: number;
  description: string;
  creationDateTime: string;
}

export default function TaskPage() {
  const [task, setTask] = useState<Task | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const pathname = usePathname();
  const id = pathname.split("/").pop();

  useEffect(() => {
    const fetchTask = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/tasks/${id}`);
        if (!response.ok) {
          throw new Error("Failed to fetch task");
        }
        const data = await response.json();
        setTask(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An error occurred");
      } finally {
        setIsLoading(false);
      }
    };

    if (id) fetchTask();
  }, [id]);

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!task) return <div>Task not found</div>;

  return (
    <div className="container mx-auto p-4">
      <Card className="mb-6">
        <CardHeader>
          <CardTitle>{task.taskName}</CardTitle>
        </CardHeader>
        <CardContent>
          <p className="mb-2">{task.description}</p>
          <div className="text-sm text-gray-600">
            <p>Sequence Number: {task.sequenceNr}</p>
            <p>Created: {new Date(task.creationDateTime).toLocaleString()}</p>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
