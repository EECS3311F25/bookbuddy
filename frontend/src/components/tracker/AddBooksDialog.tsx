import { useState } from "react";
import { X, Search } from "lucide-react";
import type { UserBook } from "@/types/api";

interface AddBooksDialogProps {
  isOpen: boolean;
  onClose: () => void;
  availableBooks: UserBook[];
  monthName: string;
  onAddBook: (userBookId: number) => void;
}

export function AddBooksDialog({
  isOpen,
  onClose,
  availableBooks,
  monthName,
  onAddBook,
}: AddBooksDialogProps) {
  const [searchQuery, setSearchQuery] = useState("");

  if (!isOpen) return null;

  const filteredBooks = availableBooks.filter(
    (book) =>
      book.book?.title?.toLowerCase().includes(searchQuery.toLowerCase()) ||
      book.book?.author?.toLowerCase().includes(searchQuery.toLowerCase()),
  );

  return (
    <>
      <div className="fixed inset-0 bg-black/50 z-40" onClick={onClose} />
      <div className="fixed inset-0 z-50 flex items-center justify-center p-4 pointer-events-none">
        <div
          className="bg-card w-full max-w-2xl max-h-[80vh] rounded-lg flex flex-col pointer-events-auto"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="flex items-center justify-between p-6 border-b border-border">
            <div>
              <h2 className="text-xl font-bold text-foreground">
                Add Books to Tracker
              </h2>
              <p className="text-sm text-muted-foreground">
                Select books from your library to add to {monthName}'s tracker
              </p>
            </div>
            <button
              onClick={onClose}
              className="p-2 hover:bg-muted rounded-lg transition"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          <div className="flex-1 overflow-y-auto p-6">
            {availableBooks.length === 0 ? (
              <p className="text-center text-muted-foreground py-8">
                All your books are already in the tracker!
              </p>
            ) : (
              <div className="space-y-4">
                <div className="relative">
                  <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                  <input
                    type="text"
                    placeholder="Search books..."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    className="w-full pl-10 pr-4 py-2 bg-input text-foreground border border-border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary"
                  />
                </div>
                <div className="space-y-2">
                  {filteredBooks.length === 0 ? (
                    <p className="text-center text-muted-foreground py-8">
                      No books found matching "{searchQuery}"
                    </p>
                  ) : (
                    filteredBooks.map((book) => (
                      <div
                        key={book.id}
                        className="flex items-center justify-between p-4 border border-border rounded-lg hover:bg-accent/50 transition-colors"
                      >
                        <div>
                          <p className="font-medium text-foreground">
                            {book.book?.title || "Unknown Title"}
                          </p>
                          <p className="text-sm text-muted-foreground">
                            {book.book?.author || "Unknown Author"}
                          </p>
                        </div>
                        <button
                          onClick={() => onAddBook(book.id)}
                          className="px-4 py-2 bg-primary text-primary-foreground text-sm rounded-lg hover:opacity-90 transition font-medium"
                        >
                          ➕ Add
                        </button>
                      </div>
                    ))
                  )}
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}
