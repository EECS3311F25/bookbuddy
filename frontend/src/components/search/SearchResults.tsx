import { Loader2 } from "lucide-react";
import { useInfiniteScroll } from "@/hooks/useInfiniteScroll";
import { BookCard } from "./BookCard";
import type { BookSearchResult, Genre } from "@/types/api";

interface SearchResultsProps {
  books: BookSearchResult[];
  isLoading: boolean;
  hasMore: boolean;
  onLoadMore: () => void;
  onAddBook?: (book: BookSearchResult, genre?: Genre) => void;
  isBookInLibrary?: (openLibraryId: string) => boolean;
  bookExistsInCatalog?: (openLibraryId: string) => boolean;
  emptyMessage?: string;
}

export function SearchResults({
  books,
  isLoading,
  hasMore,
  onLoadMore,
  onAddBook,
  isBookInLibrary,
  bookExistsInCatalog,
  emptyMessage = "No books found. Try a different search.",
}: SearchResultsProps) {
  const observerTarget = useInfiniteScroll({
    onLoadMore,
    hasMore,
    isLoading,
    threshold: 200,
  });

  // Empty state
  if (!isLoading && books.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center py-12 text-center">
        <p className="text-muted-foreground">{emptyMessage}</p>
      </div>
    );
  }

  return (
    <div className="space-y-3">
      {/* Book Grid */}
      {books.map((book, index) => (
        <BookCard
          key={`${book.openLibraryId}-${index}`}
          book={book}
          onAddClick={onAddBook}
          isInLibrary={isBookInLibrary?.(book.openLibraryId)}
          bookExistsInCatalog={bookExistsInCatalog?.(book.openLibraryId)}
        />
      ))}

      {/* Infinite Scroll Trigger */}
      <div ref={observerTarget} className="flex justify-center py-4">
        {isLoading && <Loader2 className="w-6 h-6 animate-spin text-primary" />}
      </div>

      {/* End of Results */}
      {!hasMore && books.length > 0 && (
        <p className="text-center text-sm text-muted-foreground py-4">
          No more results
        </p>
      )}
    </div>
  );
}
