import { ChevronLeft, ChevronRight } from "lucide-react";

interface MonthNavigationHeaderProps {
  monthName: string;
  year: number;
  isCurrentMonth: boolean;
  onNavigatePrev: () => void;
  onNavigateNext: () => void;
  onGoToCurrentMonth: () => void;
}

export function MonthNavigationHeader({
  monthName,
  year,
  isCurrentMonth,
  onNavigatePrev,
  onNavigateNext,
  onGoToCurrentMonth,
}: MonthNavigationHeaderProps) {
  return (
    <div className="mb-8">
      <h1 className="text-3xl font-bold text-foreground mb-4">
        📚 Monthly Tracker
      </h1>

      <div className="flex items-center justify-between gap-4">
        <button
          onClick={onNavigatePrev}
          className="p-2 hover:bg-accent rounded-lg transition"
          title="Previous month"
        >
          <ChevronLeft className="w-6 h-6" />
        </button>

        <div className="flex-1 text-center">
          <h2 className="text-2xl font-semibold text-foreground">
            {monthName} {year}
          </h2>
          {!isCurrentMonth && (
            <button
              onClick={onGoToCurrentMonth}
              className="mt-1 text-sm text-primary hover:underline"
            >
              Go to current month
            </button>
          )}
        </div>

        <button
          onClick={onNavigateNext}
          className="p-2 hover:bg-accent rounded-lg transition"
          title="Next month"
        >
          <ChevronRight className="w-6 h-6" />
        </button>
      </div>
    </div>
  );
}
