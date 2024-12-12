import { Metadata } from "next";
import { z } from "zod";

import { columns } from "@/tasks/components/columns";
import { DataTable } from "@/tasks/components/data-table";
import { UserNav } from "@/tasks/components/user-nav";
import { taskSchema } from "./schema";

export const metadata: Metadata = {
  title: "Tasks",
  description: "A task and issue tracker build using Tanstack Table.",
};

async function getTasks() {
  try {
    const apiUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080";
    const response = await fetch(`${apiUrl}/api/tasks`, {
      cache: "no-store",
    });

    if (!response.ok) {
      throw new Error("Failed to fetch tasks");
    }

    const data = await response.json();
    const tasks = data.content || [];

    // Map backend response to match schema
    const transformedTasks = tasks.map(
      (task: {
        id: string;
        taskName: string;
        description: string;
        sequenceNr: number;
        creationDateTime: string;
      }) => ({
        id: task.id,
        taskName: task.taskName,
        description: task.description,
        sequenceNr: task.sequenceNr,
        creationDateTime: task.creationDateTime,
      })
    );

    return z.array(taskSchema).parse(transformedTasks);
  } catch (error) {
    console.error("Error fetching tasks:", error);
    return [];
  }
}

export default async function TaskPage() {
  const tasks = await getTasks();
  console.log(tasks);
  return (
    <>
      <div className="hidden h-full flex-1 flex-col space-y-8 p-8 md:flex">
        <div className="flex items-center justify-between space-y-2">
          <div>
            <h2 className="text-2xl font-bold tracking-tight">Welcome back!</h2>
            <p className="text-muted-foreground">
              Here&apos;s a list of your tasks for this month!
            </p>
          </div>
          <div className="flex items-center space-x-2">
            <UserNav />
          </div>
        </div>
        {/* <DataTable data={tasks} columns={columns} /> */}
      </div>
    </>
  );
}
