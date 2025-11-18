import { Trash2 } from "lucide-react";
import type { UserBook, ShelfStatus } from "@/types/api";
import { FALLBACK_COVER } from "@/lib/constants";

interface LibraryBookCardProps {
  userBook: UserBook;
  onShelfChange: (userBookId: number, shelf: ShelfStatus) => void;
  onRemove: (userBookId: number) => void;
  onClick?: (userBook: UserBook) => void;
}

export function LibraryBookCard({
  userBook,
  onShelfChange,
  onRemove,
  onClick,
}: LibraryBookCardProps) {
  const handleCardClick = (e: React.MouseEvent) => {
    // Don't trigger onClick if clicking on interactive elements
    const target = e.target as HTMLElement;
    if (
      target.tagName === "SELECT" ||
      target.tagName === "BUTTON" ||
      target.closest("select") ||
      target.closest("button")
    ) {
      return;
    }
    onClick?.(userBook);
  };

  return (
    <div
      className="bg-card border border-border rounded-lg overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
      onClick={handleCardClick}
    >
      <img
        src={userBook.book.coverUrl || FALLBACK_COVER}
        alt={`${userBook.book.title} cover`}
        className="w-full h-64 object-cover"
        crossOrigin="anonymous"
        loading="lazy"
        referrerPolicy="no-referrer"
        onError={(e) => {
          e.currentTarget.src = FALLBACK_COVER;
        }}
      />

      <div className="p-3 space-y-1.5">
        <h3 className="font-semibold text-foreground truncate leading-tight">
          {userBook.book.title}
        </h3>
        <p className="text-sm text-muted-foreground truncate">
          {userBook.book.author}
        </p>

        <div className="flex items-center gap-1.5 pt-1">
          <select
            value={userBook.shelf}
            onChange={(e) =>
              onShelfChange(userBook.id, e.target.value as ShelfStatus)
            }
            className="flex-1 px-1.5 py-0.5 bg-input text-foreground text-[10px] rounded border border-border focus:outline-none focus:ring-1 focus:ring-green-500 transition"
          >
            <option value="WANT_TO_READ">Want to Read</option>
            <option value="CURRENTLY_READING">Reading</option>
            <option value="READ">Read</option>
          </select>

          <button
            onClick={() => onRemove(userBook.id)}
            className="p-1 text-destructive hover:bg-destructive/10 rounded transition"
            aria-label="Remove from library"
          >
            <Trash2 className="w-3.5 h-3.5" />
          </button>
        </div>
      </div>
    </div>
  );
}
