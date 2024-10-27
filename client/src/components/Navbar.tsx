"use client";
import { SidebarTrigger, useSidebar } from "@/components/ui/sidebar";
import { cn } from "@/lib/utils";

export const Navbar = () => {
  const { open } = useSidebar();

  return (
    <nav
      className={cn(
        "fixed top-0 left-0 w-full p-4 backdrop-blur-[6px] z-10 transition-all duration-300",
        open ? "pl-[260px]" : "pl-0" // Adjust padding-left based on sidebar state
      )}
    >
      <div className="flex w-full justify-between items-center">
        <div className="flex items-center">
          <SidebarTrigger />
          <a href="/">
            <h1 className="text-xl font-bold ml-2">Stock Tracker</h1>
          </a>
        </div>
        <div className="flex justify-center flex-1">
          <p className="font-bold">Profits: 267.83 PLN</p>
        </div>
        <div>X</div>
      </div>
    </nav>
  );
};
