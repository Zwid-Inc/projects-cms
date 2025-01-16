"use client";
import React, { useState, useEffect } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";

interface Maintainer {
  userId: number;
}

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
  MaintainersIds: Maintainer[];
  creationTime: string;
  releaseDate: string;
  taskList: Task[];
}

export interface ProjectsResponse {
  content: Project[];
}

export default function ProjectsPage() {
  const [projects, setProjects] = useState<Project[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const token = localStorage.getItem("jwt");

        if (!token) {
          router.push("/login");
          return;
        }

        const response = await fetch("http://localhost:8080/api/projects", {
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
          throw new Error("Failed to fetch projects");
        }

        const data = await response.json();
        setProjects(data);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "An unknown error occurred"
        );
      } finally {
        setIsLoading(false);
      }
    };

    fetchProjects();
  }, [router]);

  if (isLoading) {
    return <div className="p-4 text-center">Loading projects...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-500">Error: {error}</div>;
  }

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Projects List</h1>
      <div className="flex justify-between items-center mb-4">
        <Button onClick={() => router.push("/projects/create")}>
          <Plus className="mr-2 h-4 w-4" />
          Add Project
        </Button>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {projects.map((project) => (
          <Link href={`/projects/${project.id}`} key={project.id}>
            <Card className="w-full hover:shadow-lg transition-shadow">
              <CardHeader>
                <CardTitle>{project.projectName}</CardTitle>
              </CardHeader>
              <CardContent>
                <p className="mt-2">{project.projectDescription}</p>
                <p className="text-sm text-gray-600 mt-2">
                  Tasks: {project.taskList.length}
                </p>
                <p className="text-xs text-gray-500 mt-2">
                  Created: {new Date(project.creationTime).toLocaleDateString()}
                </p>
                <p className="text-xs text-gray-500">
                  Release: {new Date(project.releaseDate).toLocaleDateString()}
                </p>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  );
}
