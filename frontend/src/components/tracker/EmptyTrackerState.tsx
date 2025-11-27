interface EmptyTrackerStateProps {
  monthName: string;
  year: number;
  onCreateTracker: () => void;
}

export function EmptyTrackerState({
  monthName,
  year,
  onCreateTracker,
}: EmptyTrackerStateProps) {
  return (
    <div className="text-center py-12">
      <div className="max-w-md mx-auto bg-card border border-border rounded-lg p-8">
        <h2 className="text-2xl font-bold text-foreground mb-2">
          No Tracker for {monthName} {year}
        </h2>
        <p className="text-muted-foreground mb-6">
          Create a reading goal for this month to start tracking your progress.
        </p>
        <button
          onClick={onCreateTracker}
          className="px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90 transition font-medium"
        >
          🎯 Create Tracker
        </button>
      </div>
    </div>
  );
}
