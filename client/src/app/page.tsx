import { Chart } from "@/components/Chart";
import { ChartEarnings } from "@/components/ChartEarnnings";
import { TableComponent } from "@/components/Table";
import { Button } from "@/components/ui/button";
import { Skeleton } from "@/components/ui/skeleton";

export default function Home() {
  return (
    <div className="items-center flex flex-col">
      Main
      <Chart />
      <TableComponent />
      <ChartEarnings />
      <Button>XXX</Button>
      <Skeleton className="w-40 h-40" />
    </div>
  );
}
