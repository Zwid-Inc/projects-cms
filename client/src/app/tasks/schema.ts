import { z } from "zod";

export const taskSchema = z.object({
  id: z.number(),
  taskName: z.string(),
  description: z.string(),
  sequenceNr: z.number(),
  creationDateTime: z.string(),
});

export type Task = z.infer<typeof taskSchema>;
