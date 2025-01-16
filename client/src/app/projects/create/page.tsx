"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";

export default function CreateProjectPage() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);

    const formData = new FormData(e.currentTarget);
    const projectData = {
      projectName: formData.get("projectName"),
      projectDescription: formData.get("projectDescription"),
      projectMaintainers: [
        { id: parseInt(formData.get("maintainer1") as string) },
        { id: parseInt(formData.get("maintainer2") as string) },
      ],
    };

    try {
      const token = localStorage.getItem("jwt");
      const response = await fetch(
        "http://localhost:8080/api/projects?ownerId=1",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify(projectData),
        }
      );

      if (!response.ok) throw new Error("Failed to create project");

      router.push("/projects");
      router.refresh();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to create project");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle>Create New Project</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="projectName">Project Name</Label>
              <Input id="projectName" name="projectName" required />
            </div>
            <div className="space-y-2">
              <Label htmlFor="projectDescription">Description</Label>
              <Textarea
                id="projectDescription"
                name="projectDescription"
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="maintainer1">First Maintainer ID</Label>
              <Input
                id="maintainer1"
                name="maintainer1"
                type="number"
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="maintainer2">Second Maintainer ID</Label>
              <Input
                id="maintainer2"
                name="maintainer2"
                type="number"
                required
              />
            </div>
            {error && <div className="text-red-500">{error}</div>}
            <Button type="submit" disabled={isLoading}>
              {isLoading ? "Creating..." : "Create Project"}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
