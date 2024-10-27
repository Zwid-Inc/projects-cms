"use client";

import { usePathname } from "next/navigation";

export default function CurrentPath() {
  const pathname = usePathname();
  const id = pathname.split("/").pop();

  return (
    <div className="font-bold">
      <p>Project: {id}</p>
    </div>
  );
}
