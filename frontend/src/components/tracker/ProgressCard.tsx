import type { TrackerProgress } from "@/types/api";

interface ProgressCardProps {
  progress: TrackerProgress;
}

export function ProgressCard({ progress }: ProgressCardProps) {
  return (
    <div className="bg-card border border-border rounded-lg p-6 mb-6">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold text-foreground">🎯 Progress</h2>
        <span className="text-2xl font-bold text-primary">
          {progress.completedBooks} / {progress.targetBooks}
        </span>
      </div>
      <p className="text-sm text-muted-foreground mb-4">
        Books completed this month
      </p>

      {/* Progress Bar */}
      <div className="mb-6">
        <div className="flex justify-between text-sm mb-2">
          <span className="text-foreground">Completion</span>
          <span className="font-medium text-foreground">
            {progress.completionPercentage.toFixed(0)}%
          </span>
        </div>
        <div className="w-full bg-secondary rounded-full h-3 overflow-hidden">
          <div
            className="bg-primary h-full transition-all duration-300"
            style={{
              width: `${Math.min(progress.completionPercentage, 100)}%`,
            }}
          />
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-3 gap-4 text-center">
        <div className="bg-background/50 rounded-lg p-3">
          <p className="text-2xl font-bold text-primary">
            {progress.targetBooks}
          </p>
          <p className="text-xs text-muted-foreground">Goal</p>
        </div>
        <div className="bg-background/50 rounded-lg p-3">
          <p className="text-2xl font-bold text-green-600">
            {progress.completedBooks}
          </p>
          <p className="text-xs text-muted-foreground">Completed</p>
        </div>
        <div className="bg-background/50 rounded-lg p-3">
          <p className="text-2xl font-bold text-blue-600">
            {progress.totalBooks}
          </p>
          <p className="text-xs text-muted-foreground">In Tracker</p>
        </div>
      </div>
    </div>
  );
}
