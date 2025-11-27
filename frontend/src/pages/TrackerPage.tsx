import { useState, useEffect, useCallback } from "react";
import { DEFAULT_MONTHLY_GOAL } from "@/constants";
import AppShell from "@/components/layout/AppShell";
import { useAuth } from "@/contexts/useAuth";
import {
  monthlyTrackerService,
  monthlyTrackerBookService,
} from "@/services/monthlyTrackerService";
import { userbooksService } from "@/services";
import {
  type UserBook,
  type MonthlyTracker,
  type TrackerProgress,
  type MonthlyTrackerBook,
  ShelfStatus,
} from "@/types/api";
import { toast } from "sonner";
import { AxiosError } from "axios";
import {
  CreateTrackerModal,
  AddBooksDialog,
  ProgressCard,
  TrackerBooksList,
  MonthNavigationHeader,
  EmptyTrackerState,
} from "@/components/tracker";

const MONTHS = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

export default function TrackerPage() {
  const { user } = useAuth();
  const [tracker, setTracker] = useState<MonthlyTracker | null>(null);
  const [progress, setProgress] = useState<TrackerProgress | null>(null);
  const [trackerBooks, setTrackerBooks] = useState<MonthlyTrackerBook[]>([]);
  const [userBooks, setUserBooks] = useState<UserBook[]>([]);
  const [loading, setLoading] = useState(true);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showAddBooksDialog, setShowAddBooksDialog] = useState(false);
  const [monthlyGoal, setMonthlyGoal] = useState(DEFAULT_MONTHLY_GOAL);

  const currentDate = new Date();
  const [selectedMonth, setSelectedMonth] = useState(
    currentDate.getMonth() + 1,
  );
  const [selectedYear, setSelectedYear] = useState(currentDate.getFullYear());

  const selectedMonthName = MONTHS[selectedMonth - 1];
  const isCurrentMonth =
    selectedMonth === currentDate.getMonth() + 1 &&
    selectedYear === currentDate.getFullYear();

  const loadTracker = useCallback(
    async (month: number, year: number) => {
      if (!user) return;

      try {
        setLoading(true);
        const fetchedTracker = await monthlyTrackerService.getTrackerByMonth(
          user.id,
          month,
          year,
        );
        setTracker(fetchedTracker);

        if (fetchedTracker?.id) {
          const trackerProgress = await monthlyTrackerService.getProgress(
            fetchedTracker.id,
          );
          setProgress(trackerProgress);

          const books = await monthlyTrackerBookService.getBooksInTracker(
            fetchedTracker.id,
          );
          setTrackerBooks(books);
        }
      } catch (error: unknown | AxiosError) {
        if (error instanceof AxiosError && error.response?.status === 404) {
          setTracker(null);
          setProgress(null);
          setTrackerBooks([]);
        } else {
          if (error instanceof AxiosError && error.response?.status !== 404) {
            toast.error("Failed to load tracker");
          }
          console.error(error);
        }
      } finally {
        setLoading(false);
      }
    },
    [user],
  );

  const loadUserBooks = useCallback(async () => {
    if (!user) return;

    try {
      const books = await userbooksService.getUserBooks(user.id);
      setUserBooks(books);
    } catch (error: unknown | AxiosError) {
      console.error(
        "Failed to load user books:",
        error instanceof AxiosError
          ? error.response?.data
          : error instanceof Error
            ? error.message
            : error,
      );
    }
  }, [user]);

  useEffect(() => {
    if (user) {
      loadTracker(selectedMonth, selectedYear);
      loadUserBooks();
    }
  }, [user, selectedMonth, selectedYear, loadTracker, loadUserBooks]);

  const createTracker = async () => {
    if (!user) return;

    try {
      const newTracker = await monthlyTrackerService.createTracker({
        userId: user.id,
        year: selectedYear,
        month: selectedMonth,
        monthlyGoal,
      });

      setTracker(newTracker);
      setShowCreateModal(false);
      toast.success(
        `Tracker created for ${selectedMonthName} ${selectedYear}!`,
      );
      loadTracker(selectedMonth, selectedYear);
    } catch (error: unknown | AxiosError) {
      if (error instanceof AxiosError && error.response?.status === 409) {
        toast.error("Tracker already exists for this month");
      } else {
        toast.error("Failed to create tracker");
      }
      console.error(error);
    }
  };

  const addBookToTracker = async (userBookId: number) => {
    if (!tracker || !tracker.id) {
      toast.error("Tracker not properly loaded. Please refresh the page.");
      return;
    }

    try {
      await monthlyTrackerBookService.addBookToTracker({
        monthlyTrackerId: tracker.id,
        userBookId,
      });

      toast.success("Book added to tracker!");
      loadTracker(selectedMonth, selectedYear);
      loadUserBooks();
    } catch (error: unknown | AxiosError) {
      if (error instanceof AxiosError && error.response?.status === 409) {
        toast.error("Book is already in tracker");
      } else {
        toast.error("Failed to add book");
      }
      console.error(error);
    }
  };

  const toggleBookCompletion = async (trackerBook: MonthlyTrackerBook) => {
    if (trackerBook.isCompleted) return;

    try {
      await monthlyTrackerBookService.markAsCompleted(trackerBook.id);

      // Optimistically update UI
      setTrackerBooks((prevBooks) =>
        prevBooks.map((book) =>
          book.id === trackerBook.id ? { ...book, isCompleted: true } : book,
        ),
      );

      toast.success("Book marked as completed!");

      // Reload to get fresh data
      setTimeout(() => {
        loadTracker(selectedMonth, selectedYear);
        loadUserBooks();
      }, 500);
    } catch (error) {
      toast.error("Failed to update book status");
      console.error(error);
      // Revert optimistic update on error
      loadTracker(selectedMonth, selectedYear);
    }
  };

  const removeBookFromTracker = async (trackerBookId: number) => {
    try {
      await monthlyTrackerBookService.removeBookFromTracker(trackerBookId);
      toast.success("Book removed from tracker");
      loadTracker(selectedMonth, selectedYear);
    } catch (error) {
      toast.error("Failed to remove book");
      console.error(error);
    }
  };

  const navigateMonth = (direction: "prev" | "next") => {
    if (direction === "prev") {
      if (selectedMonth === 1) {
        setSelectedMonth(12);
        setSelectedYear(selectedYear - 1);
      } else {
        setSelectedMonth(selectedMonth - 1);
      }
    } else {
      if (selectedMonth === 12) {
        setSelectedMonth(1);
        setSelectedYear(selectedYear + 1);
      } else {
        setSelectedMonth(selectedMonth + 1);
      }
    }
  };

  const goToCurrentMonth = () => {
    setSelectedMonth(currentDate.getMonth() + 1);
    setSelectedYear(currentDate.getFullYear());
  };

  if (loading) {
    return (
      <AppShell>
        <div className="container mx-auto px-4 py-8">
          <div className="flex items-center justify-center h-64">
            <p className="text-muted-foreground">Loading tracker...</p>
          </div>
        </div>
      </AppShell>
    );
  }

  const availableBooks = userBooks.filter(
    (book) =>
      !trackerBooks.some((tb) => tb.userBook?.id === book.id) &&
      book.shelf !== ShelfStatus.READ,
  );

  return (
    <AppShell>
      <div className="container mx-auto px-4 py-8 max-w-4xl">
        <MonthNavigationHeader
          monthName={selectedMonthName}
          year={selectedYear}
          isCurrentMonth={isCurrentMonth}
          onNavigatePrev={() => navigateMonth("prev")}
          onNavigateNext={() => navigateMonth("next")}
          onGoToCurrentMonth={goToCurrentMonth}
        />

        {!tracker ? (
          <EmptyTrackerState
            monthName={selectedMonthName}
            year={selectedYear}
            onCreateTracker={() => setShowCreateModal(true)}
          />
        ) : (
          <>
            {progress && <ProgressCard progress={progress} />}

            <TrackerBooksList
              trackerBooks={trackerBooks}
              onToggleCompletion={toggleBookCompletion}
              onRemoveBook={removeBookFromTracker}
              onOpenAddBooksDialog={() => setShowAddBooksDialog(true)}
              hasAvailableBooks={availableBooks.length > 0}
              hasTracker={!!tracker?.id}
            />
          </>
        )}
      </div>

      <CreateTrackerModal
        isOpen={showCreateModal}
        onClose={() => setShowCreateModal(false)}
        monthName={selectedMonthName}
        year={selectedYear}
        monthlyGoal={monthlyGoal}
        onGoalChange={setMonthlyGoal}
        onCreateTracker={createTracker}
      />

      <AddBooksDialog
        isOpen={showAddBooksDialog}
        onClose={() => setShowAddBooksDialog(false)}
        availableBooks={availableBooks}
        monthName={selectedMonthName}
        onAddBook={addBookToTracker}
      />
    </AppShell>
  );
}
