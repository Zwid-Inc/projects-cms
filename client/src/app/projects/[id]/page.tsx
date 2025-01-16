"use client";

import { useEffect, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { Pencil, Trash2 } from "lucide-react";
interface Task {
  id: number;
  taskName: string;
  sequenceNr: number;
  description: string;
  creationDateTime: string;
}

export interface Project {
  id: number;
  projectName: string;
  projectDescription: string;
  projectOwnerId: number;
  MaintainersIds: { userId: number }[];
  creationTime: string;
  releaseDate: string;
  taskList: Task[];
}

export default function ProjectPage() {
  const [project, setProject] = useState<Project | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const pathname = usePathname();
  const id = pathname.split("/").pop();

  const router = useRouter();
  const [isDeleting, setIsDeleting] = useState(false);

  const handleEdit = () => {
    router.push(`/projects/${id}/edit`);
  };

  const handleDelete = async () => {
    setIsDeleting(true);
    try {
      const token = localStorage.getItem("jwt");
      const response = await fetch(`http://localhost:8080/api/projects/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.status === 401) {
        localStorage.removeItem("jwt");
        router.push("/login");
        return;
      }

      if (!response.ok) {
        throw new Error("Failed to delete project");
      }

      router.push("/projects");
      router.refresh();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to delete project");
    } finally {
      setIsDeleting(false);
    }
  };

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const token = localStorage.getItem("jwt");

        if (!token) {
          router.push("/login");
          return;
        }

        const response = await fetch(
          `http://localhost:8080/api/projects/${id}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        if (response.status === 401) {
          localStorage.removeItem("jwt");
          router.push("/login");
          return;
        }

        if (!response.ok) {
          throw new Error("Failed to fetch project");
        }

        const data = await response.json();
        setProject(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An error occurred");
      } finally {
        setIsLoading(false);
      }
    };

    if (id) fetchProject();
  }, [id]);

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!project) return <div>Project not found</div>;

  return (
    <div className="container mx-auto p-4">
      <Card className="mb-6">
        <CardHeader>
          <div className="flex justify-between items-center">
            <CardTitle>{project?.projectName}</CardTitle>
            <div className="space-x-2">
              <Button variant="outline" size="sm" onClick={handleEdit}>
                <Pencil className="h-4 w-4 mr-2" />
                Edit
              </Button>
              <AlertDialog>
                <AlertDialogTrigger asChild>
                  <Button variant="destructive" size="sm">
                    <Trash2 className="h-4 w-4 mr-2" />
                    Delete
                  </Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                    <AlertDialogDescription>
                      This action cannot be undone. This will permanently delete
                      the project.
                    </AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                    <AlertDialogAction
                      onClick={handleDelete}
                      disabled={isDeleting}
                    >
                      {isDeleting ? "Deleting..." : "Delete"}
                    </AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <p className="mb-2">{project.projectDescription}</p>
          <div className="text-sm text-gray-600">
            <p>
              Created: {new Date(project.creationTime).toLocaleDateString()}
            </p>
            <p>Release: {new Date(project.releaseDate).toLocaleDateString()}</p>
            <p>Owner ID: {project.projectOwnerId}</p>
            <p>
              Maintainers:{" "}
              {project.MaintainersIds.map((m) => m.userId).join(", ")}
            </p>
          </div>
        </CardContent>
      </Card>

      <h2 className="text-xl font-bold mb-4">Tasks</h2>
      <div className="grid gap-4">
        {project.taskList.map((task) => (
          <Card key={task.id}>
            <CardHeader>
              <CardTitle className="text-lg">{task.taskName}</CardTitle>
            </CardHeader>
            <CardContent>
              <p>{task.description}</p>
              <div className="text-sm text-gray-600 mt-2">
                <p>Sequence: {task.sequenceNr}</p>
                <p>
                  Created:{" "}
                  {new Date(task.creationDateTime).toLocaleDateString()}
                </p>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
