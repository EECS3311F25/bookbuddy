import { Plus } from "lucide-react";
import type { UserBook } from "@/types/api";

interface CurrentlyReadingCarouselProps {
  books: UserBook[];
  onAddBookClick: () => void;
  onBookClick: (book: UserBook) => void;
}

export function CurrentlyReadingCarousel({
  books,
  onAddBookClick,
  onBookClick,
}: CurrentlyReadingCarouselProps) {
  // Filter for currently reading books
  const currentlyReading = books.filter(
    (book) => book.shelf === "CURRENTLY_READING",
  );

  // Empty state - show big plus button
  if (currentlyReading.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center py-12 px-4">
        <button
          onClick={onAddBookClick}
          className="group flex flex-col items-center gap-4 p-8 rounded-3xl bg-card hover:bg-secondary transition-all duration-300 border-2 border-dashed border-border hover:border-primary"
          aria-label="Add a book to start reading"
        >
          <div className="w-20 h-20 rounded-full bg-primary/10 group-hover:bg-primary/20 flex items-center justify-center transition-colors">
            <Plus className="w-10 h-10 text-primary" strokeWidth={2.5} />
          </div>
          <div className="text-center">
            <p className="text-lg font-semibold text-foreground mb-1">
              Start Reading a Book
            </p>
            <p className="text-sm text-muted-foreground">
              Click to search and add your first book
            </p>
          </div>
        </button>
      </div>
    );
  }

  // Carousel with books
  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between px-4 md:px-6">
        <h2 className="text-xl font-bold text-foreground">Currently Reading</h2>
        <button
          onClick={onAddBookClick}
          className="flex items-center gap-2 px-4 py-2 bg-primary text-primary-foreground rounded-xl hover:opacity-90 transition font-medium text-sm"
        >
          <Plus className="w-4 h-4" />
          Add Book
        </button>
      </div>

      {/* Horizontal scrollable carousel */}
      <div className="overflow-x-auto scrollbar-hide px-4 md:px-6">
        <div className="flex gap-4 pb-4">
          {currentlyReading.map((userBook) => (
            <button
              key={userBook.id}
              onClick={() => onBookClick(userBook)}
              className="flex-shrink-0 w-40 group"
            >
              <div className="relative aspect-[2/3] rounded-xl overflow-hidden bg-secondary mb-3 shadow-md group-hover:shadow-xl transition-shadow">
                {userBook.book.coverUrl ? (
                  <img
                    src={userBook.book.coverUrl}
                    alt={userBook.book.title}
                    className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
                  />
                ) : (
                  <div className="w-full h-full flex items-center justify-center bg-gradient-to-br from-primary/20 to-primary/5">
                    <p className="text-4xl font-bold text-primary/30">
                      {userBook.book.title.charAt(0)}
                    </p>
                  </div>
                )}
              </div>
              <div className="text-left">
                <h3 className="font-semibold text-sm text-foreground line-clamp-2 mb-1">
                  {userBook.book.title}
                </h3>
                <p className="text-xs text-muted-foreground line-clamp-1">
                  {userBook.book.author}
                </p>
              </div>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
