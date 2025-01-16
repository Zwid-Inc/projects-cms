"use client";
import { useSidebar } from "@/components/ui/sidebar";
import { cn } from "@/lib/utils";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Separator } from "@/components/ui/separator";
import { ThemeToggle } from "./ThemeToggle";
import { usePathname } from "next/navigation";

export const Navbar = () => {
  const { open } = useSidebar();
  const pathname = usePathname();

  const getPageTitle = (path: string) => {
    const segment = path.split("/")[1];
    if (!segment) return null;
    return segment.charAt(0).toUpperCase() + segment.slice(1);
  };

  const pageTitle = getPageTitle(pathname);

  return (
    <nav
      className={cn(
        "fixed top-0 left-0 w-full p-4 backdrop-blur-[6px] z-10 transition-all duration-300",
        open ? "pl-[260px]" : "pl-[48px]"
      )}
    >
      <div className="flex w-full justify-between items-center">
        <div className="flex items-center">
          <header className="flex h-12 shrink-0 items-center gap-2 transition-[width,height] ease-linear">
            <div className="flex items-center gap-2 px-4">
              <Separator orientation="vertical" className="mr-2 h-4" />
              <Breadcrumb>
                <BreadcrumbList>
                  <BreadcrumbItem className="hidden md:block">
                    <BreadcrumbLink href="/">Projects CMS</BreadcrumbLink>
                  </BreadcrumbItem>
                  {pageTitle && (
                    <>
                      <BreadcrumbSeparator className="hidden md:block" />
                      <BreadcrumbItem>
                        <BreadcrumbPage>{pageTitle}</BreadcrumbPage>
                      </BreadcrumbItem>
                    </>
                  )}
                </BreadcrumbList>
              </Breadcrumb>
            </div>
          </header>
        </div>
        <div>
          <ThemeToggle />
        </div>
      </div>
    </nav>
  );
};
