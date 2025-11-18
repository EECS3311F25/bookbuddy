import { createContext } from "react";
import type {
  UserBook,
  AddBookFromSearchRequest,
  ShelfStatus,
} from "@/types/api";

export interface UserLibraryContextType {
  userBooks: UserBook[];
  isLoading: boolean;
  isInitialized: boolean;
  addBookFromSearch: (
    request: Omit<AddBookFromSearchRequest, "userId">,
  ) => Promise<void>;
  updateShelfStatus: (userBookId: number, shelf: ShelfStatus) => Promise<void>;
  removeBook: (userBookId: number) => Promise<void>;
  refreshLibrary: () => Promise<void>;
  isBookInLibrary: (openLibraryId: string) => boolean;
}

export const UserLibraryContext = createContext<UserLibraryContextType | null>(
  null,
);
