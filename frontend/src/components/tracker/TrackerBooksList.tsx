import { Check, X } from "lucide-react";
import type { MonthlyTrackerBook } from "@/types/api";

interface TrackerBooksListProps {
  trackerBooks: MonthlyTrackerBook[];
  onToggleCompletion: (trackerBook: MonthlyTrackerBook) => void;
  onRemoveBook: (trackerBookId: number) => void;
  onOpenAddBooksDialog: () => void;
  hasAvailableBooks: boolean;
  hasTracker: boolean;
}

export function TrackerBooksList({
  trackerBooks,
  onToggleCompletion,
  onRemoveBook,
  onOpenAddBooksDialog,
  hasAvailableBooks,
  hasTracker,
}: TrackerBooksListProps) {
  return (
    <div className="bg-card border border-border rounded-lg p-6">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold text-foreground">
          📖 Books in Tracker ({trackerBooks.length})
        </h2>
        <button
          onClick={onOpenAddBooksDialog}
          disabled={!hasAvailableBooks || !hasTracker}
          className="px-4 py-2 bg-primary text-primary-foreground text-sm rounded-lg hover:opacity-90 transition font-medium disabled:opacity-50 disabled:cursor-not-allowed"
        >
          ➕ Add Books
        </button>
      </div>

      {trackerBooks.length === 0 ? (
        <p className="text-center text-muted-foreground py-8">
          No books in tracker yet. Add some books to get started!
        </p>
      ) : (
        <div className="space-y-2">
          {trackerBooks.map((trackerBook) => (
            <div
              key={trackerBook.id}
              className={`flex items-center gap-3 p-4 border rounded-lg transition-all ${
                trackerBook.isCompleted
                  ? "bg-green-50 dark:bg-green-950/20 border-green-200 dark:border-green-900/50"
                  : "border-border hover:bg-accent/50"
              }`}
            >
              <button
                onClick={() => onToggleCompletion(trackerBook)}
                disabled={trackerBook.isCompleted}
                className={`w-8 h-8 rounded-full flex items-center justify-center transition-all ${
                  trackerBook.isCompleted
                    ? "bg-green-600 cursor-default"
                    : "border-2 border-gray-300 hover:border-green-600 hover:bg-green-50 dark:hover:bg-green-950/20 cursor-pointer"
                }`}
              >
                {trackerBook.isCompleted && (
                  <Check className="w-5 h-5 text-white" />
                )}
              </button>
              <div className="flex-1">
                <p
                  className={`font-medium ${
                    trackerBook.isCompleted
                      ? "line-through text-muted-foreground"
                      : "text-foreground"
                  }`}
                >
                  {trackerBook.userBook?.book?.title || "Unknown Title"}
                </p>
                <p className="text-sm text-muted-foreground">
                  {trackerBook.userBook?.book?.author || "Unknown Author"}
                </p>
              </div>
              {trackerBook.isCompleted && (
                <span className="px-3 py-1 bg-green-600 text-white text-xs font-medium rounded-full">
                  Completed
                </span>
              )}
              <button
                onClick={() => onRemoveBook(trackerBook.id)}
                className="p-2 text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20 rounded transition"
                title="Remove from tracker"
              >
                <X className="w-5 h-5" />
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
