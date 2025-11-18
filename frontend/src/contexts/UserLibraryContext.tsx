import { useState, useEffect, useCallback, type ReactNode } from "react";
import { userbooksService } from "@/services";
import { useAuth } from "@/contexts/useAuth";
import { UserLibraryContext } from "@/contexts/userLibraryContext";
import type {
  UserBook,
  AddBookFromSearchRequest,
  ShelfStatus,
} from "@/types/api";
import { toast } from "sonner";

export function UserLibraryProvider({ children }: { children: ReactNode }) {
  const { user } = useAuth();
  const [userBooks, setUserBooks] = useState<UserBook[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isInitialized, setIsInitialized] = useState(false);

  const refreshLibrary = useCallback(async () => {
    if (!user) {
      setUserBooks([]);
      setIsInitialized(true);
      return;
    }

    setIsLoading(true);
    try {
      const books = await userbooksService.getUserBooks(user.id);
      setUserBooks(books);
    } catch (error) {
      console.error("Failed to fetch user library:", error);
      toast.error("Failed to load your library");
    } finally {
      setIsLoading(false);
      setIsInitialized(true);
    }
  }, [user]);

  useEffect(() => {
    refreshLibrary();
  }, [refreshLibrary]);

  const addBookFromSearch = useCallback(
    async (request: Omit<AddBookFromSearchRequest, "userId">) => {
      if (!user) {
        toast.error("You must be logged in to add books");
        return;
      }

      try {
        const newUserBook = await userbooksService.addBookFromSearch({
          ...request,
          userId: user.id,
        });
        setUserBooks((prev) => [...prev, newUserBook]);
        toast.success("Added to Want to Read");
      } catch (error) {
        console.error("Failed to add book:", error);
        toast.error("Failed to add book to library");
        throw error;
      }
    },
    [user],
  );

  const updateShelfStatus = useCallback(
    async (userBookId: number, shelf: ShelfStatus) => {
      try {
        const updatedBook = await userbooksService.updateShelfStatus(
          userBookId,
          shelf,
        );
        setUserBooks((prev) =>
          prev.map((book) => (book.id === userBookId ? updatedBook : book)),
        );

        const shelfName = shelf
          .replace(/_/g, " ")
          .toLowerCase()
          .replace(/\b\w/g, (c) => c.toUpperCase());
        toast.success(`Moved to ${shelfName}`);
      } catch (error) {
        console.error("Failed to update shelf status:", error);
        toast.error("Failed to update book status");
        throw error;
      }
    },
    [],
  );

  const removeBook = useCallback(async (userBookId: number) => {
    try {
      await userbooksService.removeBookFromLibrary(userBookId);
      setUserBooks((prev) => prev.filter((book) => book.id !== userBookId));
      toast.success("Removed from library");
    } catch (error) {
      console.error("Failed to remove book:", error);
      toast.error("Failed to remove book");
      throw error;
    }
  }, []);

  const isBookInLibrary = useCallback(
    (openLibraryId: string) => {
      return userBooks.some(
        (userBook) => userBook.book.openLibraryId === openLibraryId,
      );
    },
    [userBooks],
  );

  return (
    <UserLibraryContext.Provider
      value={{
        userBooks,
        isLoading,
        isInitialized,
        addBookFromSearch,
        updateShelfStatus,
        removeBook,
        refreshLibrary,
        isBookInLibrary,
      }}
    >
      {children}
    </UserLibraryContext.Provider>
  );
}
