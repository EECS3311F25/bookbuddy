import { X } from "lucide-react";

interface DeleteConfirmDialogProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
  isLoading?: boolean;
}

export function DeleteConfirmDialog({
  isOpen,
  onClose,
  onConfirm,
  isLoading = false,
}: DeleteConfirmDialogProps) {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 z-60 flex items-end md:items-center justify-center p-4">
      <div className="bg-card w-full md:max-w-md rounded-t-2xl md:rounded-2xl p-4 md:p-6 space-y-4">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-bold text-foreground">Delete Account</h2>
          <button
            onClick={onClose}
            className="p-2 hover:bg-secondary rounded-lg transition"
            aria-label="Close modal"
            disabled={isLoading}
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <div className="space-y-3 bg-destructive/5 border border-destructive/20 rounded-lg p-4">
          <p className="font-semibold text-foreground">
            This action cannot be undone.
          </p>
          <p className="text-sm text-muted-foreground">
            All your data, including books, ratings, and reading progress will
            be permanently deleted.
          </p>
        </div>

        <div className="flex gap-3 pt-4">
          <button
            onClick={onClose}
            disabled={isLoading}
            className="flex-1 px-4 py-2 bg-secondary text-foreground rounded-lg font-medium hover:opacity-80 transition disabled:opacity-50"
          >
            Cancel
          </button>
          <button
            onClick={onConfirm}
            disabled={isLoading}
            className="flex-1 px-4 py-2 bg-destructive text-destructive-foreground rounded-lg font-medium hover:opacity-90 transition disabled:opacity-50"
          >
            {isLoading ? "Deleting..." : "Delete Account"}
          </button>
        </div>
      </div>
    </div>
  );
}
