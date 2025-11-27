import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AppShell from "@/components/layout/AppShell";
import { CurrentlyReadingCarousel } from "@/components/home/CurrentlyReadingCarousel";
import { BookDetailsDialog } from "@/components/library/BookDetailsDialog";
import { useAuth } from "@/contexts/useAuth";
import { useUserLibrary } from "@/contexts/useUserLibrary";
import { monthlyTrackerService } from "@/services/monthlyTrackerService";
import type { UserBook, TrackerProgress } from "@/types/api";

export default function HomePage() {
  const navigate = useNavigate();
  const { user } = useAuth();
  const { userBooks } = useUserLibrary();
  const [selectedBook, setSelectedBook] = useState<UserBook | null>(null);
  const [isSearchOpen, setIsSearchOpen] = useState(false);
  const [trackerProgress, setTrackerProgress] =
    useState<TrackerProgress | null>(null);

  // Redirect to login if not authenticated
  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  // Load monthly tracker progress
  useEffect(() => {
    const loadTrackerProgress = async () => {
      if (!user) return;

      try {
        const tracker = await monthlyTrackerService.getCurrentMonthTracker(
          user.id,
        );

        // Guard: Ensure tracker and tracker.id exist before calling getProgress
        if (tracker?.id) {
          const progress = await monthlyTrackerService.getProgress(tracker.id);
          setTrackerProgress(progress);
        }
      } catch {
        // No tracker for current month - that's okay
        setTrackerProgress(null);
      }
    };

    if (user) {
      loadTrackerProgress();
    }
  }, [user]);

  if (!user) {
    return null;
  }

  const handleAddBookClick = () => {
    setIsSearchOpen(true);
  };

  const handleBookClick = (book: UserBook) => {
    setSelectedBook(book);
  };

  const currentMonth = new Date().toLocaleString("default", { month: "long" });

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

        {/* Monthly Tracker Widget */}
        {trackerProgress && (
          <div className="px-4 md:px-6 pb-6">
            <div className="bg-card border border-border rounded-lg p-6 hover:shadow-md transition-shadow">
              <div className="flex items-center justify-between mb-4">
                <div>
                  <h2 className="text-lg font-bold text-foreground">
                    📅 {currentMonth} Reading Goal
                  </h2>
                  <p className="text-sm text-muted-foreground">
                    {trackerProgress.completedBooks} of{" "}
                    {trackerProgress.targetBooks} books completed
                  </p>
                </div>
                <button
                  onClick={() => navigate("/tracker")}
                  className="px-4 py-2 bg-primary text-primary-foreground text-sm rounded-lg hover:opacity-90 transition font-medium"
                >
                  View Tracker
                </button>
              </div>

              {/* Progress Bar */}
              <div className="mb-3">
                <div className="flex justify-between text-xs mb-1">
                  <span className="text-muted-foreground">Progress</span>
                  <span className="font-medium text-foreground">
                    {trackerProgress.completionPercentage.toFixed(0)}%
                  </span>
                </div>
                <div className="w-full bg-secondary rounded-full h-2 overflow-hidden">
                  <div
                    className="bg-primary h-full transition-all duration-300"
                    style={{
                      width: `${Math.min(trackerProgress.completionPercentage, 100)}%`,
                    }}
                  />
                </div>
              </div>

              {/* Quick Stats */}
              <div className="grid grid-cols-3 gap-2 text-center">
                <div className="bg-background/50 rounded p-2">
                  <p className="text-lg font-bold text-primary">
                    {trackerProgress.targetBooks}
                  </p>
                  <p className="text-xs text-muted-foreground">Goal</p>
                </div>
                <div className="bg-background/50 rounded p-2">
                  <p className="text-lg font-bold text-green-600">
                    {trackerProgress.completedBooks}
                  </p>
                  <p className="text-xs text-muted-foreground">Done</p>
                </div>
                <div className="bg-background/50 rounded p-2">
                  <p className="text-lg font-bold text-blue-600">
                    {trackerProgress.totalBooks}
                  </p>
                  <p className="text-xs text-muted-foreground">Tracking</p>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* No Tracker CTA */}
        {!trackerProgress && (
          <div className="px-4 md:px-6 pb-6">
            <div className="bg-card border border-border rounded-lg p-6 text-center">
              <p className="text-lg font-medium text-foreground mb-2">
                📅 Set a reading goal for {currentMonth}
              </p>
              <p className="text-sm text-muted-foreground mb-4">
                Track your monthly progress and stay motivated!
              </p>
              <button
                onClick={() => navigate("/tracker")}
                className="px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90 transition font-medium"
              >
                Create Monthly Tracker
              </button>
            </div>
          </div>
        )}

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
