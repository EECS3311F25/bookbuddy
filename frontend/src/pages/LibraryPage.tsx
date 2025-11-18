import { useMemo, useState } from "react";
import { Loader2, Search } from "lucide-react";
import AppShell from "@/components/layout/AppShell";
import { LibraryBookCard } from "@/components/library/LibraryBookCard";
import { BookDetailsDialog } from "@/components/library/BookDetailsDialog";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { useUserLibrary } from "@/contexts/useUserLibrary";
import type { UserBook } from "@/types/api";

export default function LibraryPage() {
  const { userBooks, isLoading, updateShelfStatus, removeBook } =
    useUserLibrary();
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedBook, setSelectedBook] = useState<UserBook | null>(null);

  const booksByShelfAndGenre = useMemo(() => {
    const filterAndGroup = (books: UserBook[]) => {
      const filtered = books.filter(
        (ub) =>
          ub.book.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
          ub.book.author.toLowerCase().includes(searchQuery.toLowerCase()),
      );

      const genreMap = new Map<string, UserBook[]>();
      const otherBooks: UserBook[] = [];

      filtered.forEach((book) => {
        const genre = book.book.genre;
        if (genre === "OTHER") {
          otherBooks.push(book);
        } else {
          if (!genreMap.has(genre)) {
            genreMap.set(genre, []);
          }
          genreMap.get(genre)!.push(book);
        }
      });

      const sortedGenres = Array.from(genreMap.entries()).sort((a, b) =>
        a[0].localeCompare(b[0]),
      );

      return { sortedGenres, otherBooks, total: filtered.length };
    };

    return {
      CURRENTLY_READING: filterAndGroup(
        userBooks.filter((ub) => ub.shelf === "CURRENTLY_READING"),
      ),
      READ: filterAndGroup(userBooks.filter((ub) => ub.shelf === "READ")),
      WANT_TO_READ: filterAndGroup(
        userBooks.filter((ub) => ub.shelf === "WANT_TO_READ"),
      ),
    };
  }, [userBooks, searchQuery]);

  const renderGenreSection = (
    sortedGenres: [string, UserBook[]][],
    otherBooks: UserBook[],
  ) => {
    const hasBooks = sortedGenres.length > 0 || otherBooks.length > 0;

    if (!hasBooks) {
      return (
        <p className="text-muted-foreground text-sm py-6 text-center">
          {searchQuery
            ? "No books match your search"
            : "No books in this category"}
        </p>
      );
    }

    return (
      <div className="space-y-6">
        {sortedGenres.map(([genre, books]) => (
          <div key={genre}>
            <h3 className="text-lg font-semibold text-foreground mb-3">
              {genre
                .replace(/_/g, " ")
                .toLowerCase()
                .replace(/\b\w/g, (c) => c.toUpperCase())}
            </h3>
            <div className="flex gap-4 overflow-x-auto pb-2 snap-x snap-mandatory scrollbar-hide">
              {books.map((userBook) => (
                <div key={userBook.id} className="shrink-0 w-48 snap-start">
                  <LibraryBookCard
                    userBook={userBook}
                    onShelfChange={updateShelfStatus}
                    onRemove={removeBook}
                    onClick={setSelectedBook}
                  />
                </div>
              ))}
            </div>
          </div>
        ))}

        {otherBooks.length > 0 && (
          <>
            {sortedGenres.length > 0 && <hr className="my-4 border-border" />}
            <div>
              <h3 className="text-lg font-semibold text-foreground mb-3">
                Other
              </h3>
              <div className="flex gap-4 overflow-x-auto pb-2 snap-x snap-mandatory scrollbar-hide">
                {otherBooks.map((userBook) => (
                  <div key={userBook.id} className="shrink-0 w-48 snap-start">
                    <LibraryBookCard
                      userBook={userBook}
                      onShelfChange={updateShelfStatus}
                      onRemove={removeBook}
                      onClick={setSelectedBook}
                    />
                  </div>
                ))}
              </div>
            </div>
          </>
        )}
      </div>
    );
  };

  if (isLoading) {
    return (
      <AppShell>
        <div className="flex items-center justify-center min-h-[60vh]">
          <Loader2 className="w-8 h-8 animate-spin text-primary" />
        </div>
      </AppShell>
    );
  }

  return (
    <AppShell>
      <div className="container mx-auto px-3 sm:px-4 py-3 sm:py-6 max-w-7xl">
        <h1 className="text-2xl sm:text-3xl font-bold text-foreground mb-3 sm:mb-4">
          My Library
        </h1>

        {userBooks.length === 0 ? (
          <div className="text-center py-12">
            <p className="text-muted-foreground text-lg mb-2">
              Your library is empty
            </p>
            <p className="text-sm text-muted-foreground">
              Search for books and add them to get started!
            </p>
          </div>
        ) : (
          <>
            <div className="relative mb-4">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
              <input
                type="text"
                placeholder="Search your library..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full pl-10 pr-4 py-2 bg-input text-foreground rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-primary"
              />
            </div>

            <Tabs defaultValue="currently-reading" className="w-full">
              <TabsList className="w-full sm:w-auto mb-4 grid grid-cols-3 sm:inline-flex">
                <TabsTrigger
                  value="currently-reading"
                  className="text-xs sm:text-sm px-2 sm:px-4"
                >
                  Currently Reading
                  <span className="ml-1 sm:ml-2 text-xs opacity-70">
                    ({booksByShelfAndGenre.CURRENTLY_READING.total})
                  </span>
                </TabsTrigger>
                <TabsTrigger
                  value="read"
                  className="text-xs sm:text-sm px-2 sm:px-4"
                >
                  Read
                  <span className="ml-1 sm:ml-2 text-xs opacity-70">
                    ({booksByShelfAndGenre.READ.total})
                  </span>
                </TabsTrigger>
                <TabsTrigger
                  value="want-to-read"
                  className="text-xs sm:text-sm px-2 sm:px-4"
                >
                  Want to Read
                  <span className="ml-1 sm:ml-2 text-xs opacity-70">
                    ({booksByShelfAndGenre.WANT_TO_READ.total})
                  </span>
                </TabsTrigger>
              </TabsList>

              <TabsContent value="currently-reading" className="mt-0">
                {renderGenreSection(
                  booksByShelfAndGenre.CURRENTLY_READING.sortedGenres,
                  booksByShelfAndGenre.CURRENTLY_READING.otherBooks,
                )}
              </TabsContent>

              <TabsContent value="read" className="mt-0">
                {renderGenreSection(
                  booksByShelfAndGenre.READ.sortedGenres,
                  booksByShelfAndGenre.READ.otherBooks,
                )}
              </TabsContent>

              <TabsContent value="want-to-read" className="mt-0">
                {renderGenreSection(
                  booksByShelfAndGenre.WANT_TO_READ.sortedGenres,
                  booksByShelfAndGenre.WANT_TO_READ.otherBooks,
                )}
              </TabsContent>
            </Tabs>
          </>
        )}
      </div>

      {selectedBook && (
        <BookDetailsDialog
          isOpen={!!selectedBook}
          onClose={() => setSelectedBook(null)}
          userBook={selectedBook}
        />
      )}
    </AppShell>
  );
}
