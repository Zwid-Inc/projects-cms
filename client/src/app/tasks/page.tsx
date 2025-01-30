"use client";
import React, { useState, useEffect } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";

interface Task {
  id: number;
  taskName: string;
  sequenceNr: number;
  description: string;
  creationDateTime: string;
}

interface TasksResponse {
  content: Task[];
  totalPages: number;
  totalElements: number;
}

export default function TasksPage() {
  const router = useRouter();

  const [tasks, setTasks] = useState<Task[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const token = localStorage.getItem("jwt");

        if (!token) {
          router.push("/login");
          return;
        }

        const response = await fetch("http://localhost:8080/api/tasks", {
          cache: "no-store",
          next: { revalidate: 0 },
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });

        if (response.status === 401) {
          localStorage.removeItem("jwt");
          router.push("/login");
          return;
        }

        if (!response.ok) {
          throw new Error("Failed to fetch tasks");
        }

        const data: TasksResponse = await response.json();
        setTasks(data.content);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "An unknown error occurred"
        );
      } finally {
        setIsLoading(false);
      }
    };

    fetchTasks();
    // Add router.refresh() to force revalidation
    router.refresh();
  }, [router]);

  if (isLoading) {
    return <div className="p-4 text-center">Loading tasks...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Tasks List</h1>
      <div className="flex justify-between items-center mb-4">
        <Button onClick={() => router.push("/tasks/create")}>
          <Plus className="mr-2 h-4 w-4" />
          Add Task
        </Button>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {tasks.map((task) => (
          <Link href={`/tasks/${task.id}`} key={task.id}>
            <Card className="w-full hover:shadow-lg transition-shadow">
              <CardHeader>
                <CardTitle>{task.taskName}</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="text-sm text-gray-600">
                  Sequence: {task.sequenceNr}
                </p>
                <p className="mt-2">{task.description}</p>
                <p className="text-xs text-gray-500 mt-2">
                  Created: {new Date(task.creationDateTime).toLocaleString()}
                </p>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  );
}
