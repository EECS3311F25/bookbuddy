import type { BookSearchResult } from "@/types/api";

interface BookCardProps {
  book: BookSearchResult;
  onAddClick?: (book: BookSearchResult) => void;
}

const FALLBACK_COVER =
  "https://via.placeholder.com/128x192/f5f5dc/8b4513?text=No+Cover";

export function BookCard({ book, onAddClick }: BookCardProps) {
  return (
    <div className="bg-card border border-border rounded-lg p-4 hover:shadow-md transition flex gap-4">
      {/* Book Cover */}
      <img
        src={book.coverUrl || FALLBACK_COVER}
        alt={`${book.title} cover`}
        className="w-20 h-28 object-cover rounded flex-shrink-0"
        onError={(e) => {
          e.currentTarget.src = FALLBACK_COVER;
        }}
      />

      {/* Book Info */}
      <div className="flex-1 min-w-0">
        <h3 className="font-semibold text-foreground line-clamp-2 mb-1">
          {book.title}
        </h3>
        <p className="text-sm text-muted-foreground mb-1">{book.author}</p>
        {book.publishYear && (
          <p className="text-xs text-muted-foreground">
            Published {book.publishYear}
          </p>
        )}

        {/* Add Button */}
        {onAddClick && (
          <button
            onClick={() => onAddClick(book)}
            className="mt-3 px-3 py-1.5 bg-primary text-primary-foreground text-sm rounded-lg hover:opacity-90 transition font-medium"
          >
            Add to Library
          </button>
        )}
      </div>
    </div>
  );
}
