"use client";

import { useEffect, useState } from "react";
import { useRouter, usePathname } from "next/navigation";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";

interface ProjectMaintainer {
  id: number;
}

interface Project {
  id: number;
  projectName: string;
  projectDescription: string;
  projectMaintainers: ProjectMaintainer[] | null;
  releaseDate: string;
}

export default function EditProjectPage() {
  const router = useRouter();
  const pathname = usePathname();
  const id = pathname.split("/")[2];

  const [project, setProject] = useState<Project | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

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

        if (!response.ok) throw new Error("Failed to fetch project");

        const data = await response.json();
        setProject(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An error occurred");
      } finally {
        setIsLoading(false);
      }
    };

    if (id) fetchProject();
  }, [id, router]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const formData = new FormData(e.currentTarget);
    const updatedProject = {
      projectName: formData.get("projectName"),
      projectDescription: formData.get("projectDescription"),
      projectMaintainers: [
        { id: parseInt(formData.get("maintainer1") as string) },
        { id: parseInt(formData.get("maintainer2") as string) },
      ],
      releaseDate: new Date(
        formData.get("releaseDate") as string
      ).toISOString(),
    };

    try {
      const token = localStorage.getItem("jwt");
      const response = await fetch(`http://localhost:8080/api/projects/${id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(updatedProject),
      });

      if (!response.ok) {
        // Check if response has content before parsing
        const text = await response.text();
        const errorMessage = text
          ? JSON.parse(text).message
          : "Failed to update project";
        throw new Error(errorMessage);
      }

      router.push("/projects");
      router.refresh();
    } catch (err) {
      console.error("Update error:", err);
      setError(err instanceof Error ? err.message : "Failed to update project");
    } finally {
      setIsLoading(false);
    }
  };

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!project) return <div>Project not found</div>;

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle>Edit Project</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="projectName">Project Name</Label>
              <Input
                id="projectName"
                name="projectName"
                defaultValue={project?.projectName || ""}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="projectDescription">Description</Label>
              <Textarea
                id="projectDescription"
                name="projectDescription"
                defaultValue={project?.projectDescription || ""}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="maintainer1">First Maintainer ID</Label>
              <Input
                id="maintainer1"
                name="maintainer1"
                type="number"
                defaultValue={project?.projectMaintainers?.[0]?.id || ""}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="maintainer2">Second Maintainer ID</Label>
              <Input
                id="maintainer2"
                name="maintainer2"
                type="number"
                defaultValue={project?.projectMaintainers?.[1]?.id || ""}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="releaseDate">Release Date</Label>
              <Input
                id="releaseDate"
                name="releaseDate"
                type="datetime-local"
                defaultValue={
                  project?.releaseDate
                    ? new Date(project.releaseDate).toISOString().slice(0, 16)
                    : ""
                }
                required
              />
            </div>
            {error && <div className="text-red-500">{error}</div>}
            <Button type="submit" disabled={isLoading}>
              {isLoading ? "Updating..." : "Update Project"}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
