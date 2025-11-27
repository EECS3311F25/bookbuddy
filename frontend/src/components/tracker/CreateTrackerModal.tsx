interface CreateTrackerModalProps {
  isOpen: boolean;
  onClose: () => void;
  monthName: string;
  year: number;
  monthlyGoal: number;
  onGoalChange: (goal: number) => void;
  onCreateTracker: () => void;
}

export function CreateTrackerModal({
  isOpen,
  onClose,
  monthName,
  year,
  monthlyGoal,
  onGoalChange,
  onCreateTracker,
}: CreateTrackerModalProps) {
  if (!isOpen) return null;

  return (
    <>
      <div className="fixed inset-0 bg-black/50 z-40" onClick={onClose} />
      <div className="fixed inset-0 z-50 flex items-center justify-center p-4 pointer-events-none">
        <div
          className="bg-card w-full max-w-md rounded-lg p-6 pointer-events-auto"
          onClick={(e) => e.stopPropagation()}
        >
          <h2 className="text-2xl font-bold text-foreground mb-2">
            📅 Create Monthly Tracker
          </h2>
          <p className="text-muted-foreground mb-6">
            Set your reading goal for {monthName} {year}
          </p>

          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-foreground mb-2">
                Monthly Goal (number of books)
              </label>
              <input
                type="number"
                min="1"
                value={monthlyGoal}
                onChange={(e) => onGoalChange(parseInt(e.target.value) || 1)}
                className="w-full px-4 py-2 bg-input text-foreground border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>

            <div className="flex gap-3">
              <button
                onClick={onClose}
                className="flex-1 px-4 py-2 bg-muted text-foreground rounded-lg hover:opacity-90 transition font-medium"
              >
                Cancel
              </button>
              <button
                onClick={onCreateTracker}
                className="flex-1 px-4 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90 transition font-medium"
              >
                🎯 Create Tracker
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
