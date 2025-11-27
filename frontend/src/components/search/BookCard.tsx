import { useState } from "react";
import { Genre, type BookSearchResult } from "@/types/api";
import { FALLBACK_COVER } from "@/constants";

interface BookCardProps {
  book: BookSearchResult;
  onAddClick?: (book: BookSearchResult, genre?: Genre) => void;
  isInLibrary?: boolean;
  bookExistsInCatalog?: boolean;
}

export function BookCard({
  book,
  onAddClick,
  isInLibrary = false,
  bookExistsInCatalog = false,
}: BookCardProps) {
  const [selectedGenre, setSelectedGenre] = useState<Genre>(Genre.OTHER);
  const [showGenreSelect, setShowGenreSelect] = useState(false);

  const handleAddClick = () => {
    if (isInLibrary) return;

    if (!bookExistsInCatalog && !showGenreSelect) {
      setShowGenreSelect(true);
    } else {
      onAddClick?.(book, bookExistsInCatalog ? undefined : selectedGenre);
      setShowGenreSelect(false);
    }
  };

  const formatGenre = (genre: Genre) => {
    return genre
      .replace(/_/g, " ")
      .toLowerCase()
      .replace(/\b\w/g, (c) => c.toUpperCase());
  };

  return (
    <div className="bg-card border border-border rounded-lg p-4 hover:shadow-md transition flex gap-4">
      <img
        src={book.coverUrl || FALLBACK_COVER}
        alt={`${book.title} cover`}
        className="w-20 h-28 object-cover rounded shrink-0"
        crossOrigin="anonymous"
        loading="lazy"
        referrerPolicy="no-referrer"
        onError={(e) => {
          e.currentTarget.src = FALLBACK_COVER;
        }}
      />

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

        {onAddClick && (
          <div className="mt-3 space-y-2">
            {showGenreSelect && !bookExistsInCatalog && (
              <select
                value={selectedGenre}
                onChange={(e) => setSelectedGenre(e.target.value as Genre)}
                className="w-full px-3 py-1.5 bg-input text-foreground text-sm rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-green-500"
              >
                {Object.values(Genre).map((genre) => (
                  <option key={genre} value={genre}>
                    {formatGenre(genre)}
                  </option>
                ))}
              </select>
            )}

            <button
              onClick={handleAddClick}
              disabled={isInLibrary}
              className="w-full px-3 py-1.5 bg-primary text-primary-foreground text-sm rounded-lg hover:opacity-90 transition font-medium disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {isInLibrary
                ? "Already in Library"
                : showGenreSelect
                  ? "Confirm Add"
                  : "Add to Library"}
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
