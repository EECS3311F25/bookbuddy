import { useState, useEffect, useCallback } from "react";
import { X } from "lucide-react";
import { SearchInput } from "./SearchInput";
import { SearchResults } from "./SearchResults";
import { searchApi } from "@/lib/api/services/search";
import type { BookSearchResult } from "@/types/api";

interface SearchDialogProps {
  isOpen: boolean;
  onClose: () => void;
}

const RESULTS_PER_PAGE = 20;

export function SearchDialog({ isOpen, onClose }: SearchDialogProps) {
  const [query, setQuery] = useState("");
  const [books, setBooks] = useState<BookSearchResult[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalResults, setTotalResults] = useState(0);

  const hasMore = books.length < totalResults;

  // Search function
  const performSearch = useCallback(
    async (searchQuery: string, page: number) => {
      if (!searchQuery.trim()) {
        setBooks([]);
        setTotalResults(0);
        return;
      }

      setIsLoading(true);
      try {
        const response = await searchApi.searchBooks({
          q: searchQuery,
          limit: RESULTS_PER_PAGE,
          page,
        });

        if (page === 1) {
          // New search - replace results
          setBooks(response.books);
        } else {
          // Load more - append results
          setBooks((prev) => [...prev, ...response.books]);
        }

        setTotalResults(response.totalResults);
        setCurrentPage(page);
      } catch (error) {
        console.error("Search failed:", error);
      } finally {
        setIsLoading(false);
      }
    },
    [],
  );

  // Handle search input change
  const handleSearch = useCallback(
    (newQuery: string) => {
      setQuery(newQuery);
      setCurrentPage(1);
      performSearch(newQuery, 1);
    },
    [performSearch],
  );

  // Handle load more
  const handleLoadMore = useCallback(() => {
    if (!isLoading && hasMore && query) {
      performSearch(query, currentPage + 1);
    }
  }, [isLoading, hasMore, query, currentPage, performSearch]);

  // Handle add book to library
  const handleAddBook = useCallback((book: BookSearchResult) => {
    // TODO: Implement add to library functionality
    console.log("Add book:", book);
  }, []);

  // Close dialog on ESC key
  useEffect(() => {
    const handleEsc = (e: KeyboardEvent) => {
      if (e.key === "Escape") onClose();
    };

    if (isOpen) {
      document.addEventListener("keydown", handleEsc);
    }

    return () => document.removeEventListener("keydown", handleEsc);
  }, [isOpen, onClose]);

  // Reset state when dialog closes
  useEffect(() => {
    if (!isOpen) {
      setQuery("");
      setBooks([]);
      setCurrentPage(1);
      setTotalResults(0);
    }
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <>
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-black/50 z-60"
        onClick={onClose}
        aria-hidden="true"
      />

      {/* Dialog Container */}
      <div className="fixed inset-0 z-60 flex items-start md:items-center justify-center p-0 md:p-4 pointer-events-none">
        <div
          className="bg-card w-full h-full md:h-auto md:max-w-2xl md:max-h-[85vh] md:rounded-2xl flex flex-col pointer-events-auto"
          onClick={(e) => e.stopPropagation()}
        >
          {/* Header */}
          <div className="flex items-center justify-between p-4 border-b border-border shrink-0">
            <h2 className="text-lg font-bold text-foreground">Search Books</h2>
            <button
              onClick={onClose}
              className="p-2 hover:bg-muted rounded-lg transition"
              aria-label="Close search"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          {/* Search Input */}
          <div className="p-4 border-b border-border shrink-0">
            <SearchInput onSearch={handleSearch} />
          </div>

          {/* Results */}
          <div className="flex-1 overflow-y-auto p-4">
            {query ? (
              <SearchResults
                books={books}
                isLoading={isLoading}
                hasMore={hasMore}
                onLoadMore={handleLoadMore}
                onAddBook={handleAddBook}
                emptyMessage="No books found. Try a different search."
              />
            ) : (
              <div className="flex items-center justify-center h-full text-muted-foreground">
                Start typing to search for books...
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
