import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AppShell from "@/components/layout/AppShell";
import { CurrentlyReadingCarousel } from "@/components/home/CurrentlyReadingCarousel";
import { BookDetailsDialog } from "@/components/library/BookDetailsDialog";
import { useAuth } from "@/contexts/useAuth";
import { useUserLibrary } from "@/contexts/useUserLibrary";
import type { UserBook } from "@/types/api";

export default function HomePage() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { userBooks } = useUserLibrary();
  const [selectedBook, setSelectedBook] = useState<UserBook | null>(null);
  const [isSearchOpen, setIsSearchOpen] = useState(false);

  // Redirect to login if not authenticated
  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  if (!user) {
    return null;
  }

  const handleAddBookClick = () => {
    // Open search dialog via AppShell
    setIsSearchOpen(true);
  };

  const handleBookClick = (book: UserBook) => {
    setSelectedBook(book);
  };

  return (
    <AppShell>
      <div className="min-h-screen bg-background">
        {/* Welcome Section */}
        <div className="px-4 md:px-6 py-6 md:py-8">
          <h1 className="text-3xl md:text-4xl font-bold text-foreground mb-2">
            Welcome back, {user.firstName}!
          </h1>
          <p className="text-muted-foreground">Continue your reading journey</p>
        </div>

        {/* Currently Reading Carousel */}
        <div className="pb-8">
          <CurrentlyReadingCarousel
            books={userBooks}
            onAddBookClick={handleAddBookClick}
            onBookClick={handleBookClick}
          />
        </div>
      </div>

      {/* Book Details Dialog */}
      {selectedBook && (
        <BookDetailsDialog
          isOpen={true}
          userBook={selectedBook}
          onClose={() => setSelectedBook(null)}
        />
      )}

      {/* Trigger search dialog through custom event */}
      {isSearchOpen && (
        <div
          ref={(el) => {
            if (el) {
              // Trigger search via clicking the search button in AppShell
              const searchButton = document.querySelector(
                '[aria-label="Search books"]',
              ) as HTMLButtonElement;
              if (searchButton) {
                searchButton.click();
              }
              setIsSearchOpen(false);
            }
          }}
        />
      )}
    </AppShell>
  );
}
