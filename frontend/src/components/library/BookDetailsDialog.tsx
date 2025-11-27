import { useState, useEffect, useMemo } from "react";
import { X, Star } from "lucide-react";
import { ReviewModal } from "./ReviewModal";
import { reviewsService } from "@/services";
import { useUserLibrary } from "@/contexts/useUserLibrary";
import { useAuth } from "@/contexts/auth";
import { toast } from "sonner";
import type { UserBook, ReviewResponse } from "@/types/api";
import { ShelfStatus } from "@/types/api";
import { FALLBACK_COVER } from "@/constants";

interface BookDetailsDialogProps {
  isOpen: boolean;
  onClose: () => void;
  userBook: UserBook;
}

export function BookDetailsDialog({
  isOpen,
  onClose,
  userBook,
}: BookDetailsDialogProps) {
  const { user } = useAuth();
  const { updateShelfStatus, removeBook } = useUserLibrary();
  const [reviews, setReviews] = useState<ReviewResponse[]>([]);
  const [averageRating, setAverageRating] = useState(0);
  const [isLoadingReviews, setIsLoadingReviews] = useState(true);
  const [isReviewModalOpen, setIsReviewModalOpen] = useState(false);

  const userReview = useMemo(
    () => reviews.find((r) => r.username === user?.username),
    [reviews, user],
  );

  const ratingDistribution = useMemo(() => {
    const dist = { 5: 0, 4: 0, 3: 0, 2: 0, 1: 0 };
    reviews.forEach((r) => {
      dist[r.rating as keyof typeof dist]++;
    });
    return dist;
  }, [reviews]);

  const ratingPercentages = useMemo(() => {
    const total = reviews.length || 1;
    return {
      5: Math.round((ratingDistribution[5] / total) * 100),
      4: Math.round((ratingDistribution[4] / total) * 100),
      3: Math.round((ratingDistribution[3] / total) * 100),
      2: Math.round((ratingDistribution[2] / total) * 100),
      1: Math.round((ratingDistribution[1] / total) * 100),
    };
  }, [ratingDistribution, reviews.length]);

  const loadReviews = async () => {
    setIsLoadingReviews(true);
    try {
      const reviewsData = await reviewsService.getReviewsByBook(
        userBook.book.id,
      );
      setReviews(reviewsData);

      // Only fetch average if reviews exist
      if (reviewsData.length > 0) {
        const avgRating = await reviewsService.getAverageRating(
          userBook.book.id,
        );
        setAverageRating(avgRating);
      } else {
        setAverageRating(0);
      }
    } catch (error) {
      console.error("Failed to load reviews:", error);
      // Don't show error toast if it's just missing reviews (404)
      if (error && typeof error === "object" && "response" in error) {
        const axiosError = error as { response?: { status?: number } };
        if (axiosError.response?.status !== 404) {
          toast.error("Failed to load reviews");
        }
      }
    } finally {
      setIsLoadingReviews(false);
    }
  };

  useEffect(() => {
    if (isOpen) {
      loadReviews();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isOpen, userBook.book.id]);

  const handleShelfChange = async (shelf: ShelfStatus) => {
    try {
      await updateShelfStatus(userBook.id, shelf);
      toast.success("Shelf status updated");
    } catch (error) {
      console.error("Failed to update shelf:", error);
    }
  };

  const handleMarkAsRead = () => {
    handleShelfChange(ShelfStatus.READ);
  };

  const handleRemove = async () => {
    if (confirm("Remove this book from your library?")) {
      try {
        await removeBook(userBook.id);
        toast.success("Book removed from library");
        onClose();
      } catch (error) {
        console.error("Failed to remove book:", error);
      }
    }
  };

  const handleReviewSubmit = async (rating: number, reviewText: string) => {
    if (!user) return;

    try {
      if (userReview) {
        await reviewsService.updateReview(userReview.id, {
          userId: user.id,
          bookId: userBook.book.id,
          rating,
          reviewText,
        });
        toast.success("Review updated");
      } else {
        await reviewsService.createReview({
          userId: user.id,
          bookId: userBook.book.id,
          rating,
          reviewText,
        });
        toast.success("Review submitted");
      }
      await loadReviews();
    } catch (error) {
      console.error("Failed to submit review:", error);
      toast.error("Failed to submit review");
      throw error;
    }
  };

  const formatGenre = (genre: string) => {
    return genre
      .replace(/_/g, " ")
      .toLowerCase()
      .replace(/\b\w/g, (c) => c.toUpperCase());
  };

  const formatDate = (dateString?: string) => {
    if (!dateString) return null;
    return new Date(dateString).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  if (!isOpen) return null;

  return (
    <>
      <div
        className="fixed inset-0 bg-black/50 z-60"
        onClick={onClose}
        aria-hidden="true"
      />

      <div className="fixed inset-0 z-60 flex items-start md:items-center justify-center p-0 md:p-4 pointer-events-none">
        <div
          className="bg-card w-full h-full md:h-auto md:max-w-2xl md:max-h-[90vh] md:rounded-2xl flex flex-col pointer-events-auto overflow-hidden"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="flex items-center justify-between p-4 border-b border-border shrink-0">
            <h2 className="text-lg font-bold text-foreground">Book Details</h2>
            <button
              onClick={onClose}
              className="p-2 hover:bg-muted rounded-lg transition"
              aria-label="Close"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          <div className="flex-1 overflow-y-auto">
            <div className="p-6 space-y-6">
              <div className="flex flex-col items-center gap-4">
                <img
                  src={userBook.book.coverUrl || FALLBACK_COVER}
                  alt={`${userBook.book.title} cover`}
                  className="w-48 h-72 object-cover rounded-lg shadow-lg"
                  crossOrigin="anonymous"
                  loading="lazy"
                  referrerPolicy="no-referrer"
                  onError={(e) => {
                    e.currentTarget.src = FALLBACK_COVER;
                  }}
                />
                <div className="text-center space-y-1">
                  <h3 className="text-2xl font-bold text-foreground">
                    {userBook.book.title}
                  </h3>
                  <p className="text-lg text-muted-foreground">
                    by {userBook.book.author}
                  </p>
                  <span className="inline-block px-3 py-1 bg-green-100 dark:bg-green-900/30 text-green-700 dark:text-green-400 text-sm font-medium rounded-full">
                    {formatGenre(userBook.book.genre)}
                  </span>
                </div>
              </div>

              {userBook.book.description && (
                <div>
                  <h4 className="text-sm font-semibold text-foreground mb-2">
                    Description
                  </h4>
                  <p className="text-sm text-muted-foreground leading-relaxed">
                    {userBook.book.description}
                  </p>
                </div>
              )}

              <div className="grid grid-cols-2 gap-4 text-sm">
                <div>
                  <p className="text-muted-foreground">Added to Library</p>
                  <p className="font-medium text-foreground">
                    {formatDate(userBook.createdAt)}
                  </p>
                </div>
                {userBook.completedAt && (
                  <div>
                    <p className="text-muted-foreground">Completed</p>
                    <p className="font-medium text-foreground">
                      {formatDate(userBook.completedAt)}
                    </p>
                  </div>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-foreground mb-2">
                  Shelf Status
                </label>
                <select
                  value={userBook.shelf}
                  onChange={(e) =>
                    handleShelfChange(e.target.value as ShelfStatus)
                  }
                  className="w-full px-3 py-2 bg-input text-foreground rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-green-500"
                >
                  <option value="WANT_TO_READ">Want to Read</option>
                  <option value="CURRENTLY_READING">Currently Reading</option>
                  <option value="READ">Read</option>
                </select>
              </div>

              <div className="border-t border-border pt-6">
                <div className="flex items-center justify-between mb-4">
                  <h4 className="text-lg font-semibold text-foreground">
                    My Rating
                  </h4>
                  <button
                    onClick={() => setIsReviewModalOpen(true)}
                    className="px-3 py-1.5 text-sm bg-green-600 hover:bg-green-700 text-white rounded-lg transition"
                  >
                    {userReview ? "Edit Review" : "Write Review"}
                  </button>
                </div>

                {isLoadingReviews ? (
                  <p className="text-sm text-muted-foreground">
                    Loading reviews...
                  </p>
                ) : reviews.length > 0 ? (
                  <div className="flex gap-6">
                    <div className="text-center">
                      <div className="text-5xl font-bold text-green-600">
                        {averageRating.toFixed(1)}
                      </div>
                      <div className="flex gap-0.5 justify-center my-2">
                        {[1, 2, 3, 4, 5].map((star) => (
                          <Star
                            key={star}
                            className={`w-4 h-4 ${
                              star <= Math.round(averageRating)
                                ? "fill-yellow-400 text-yellow-400"
                                : "text-muted-foreground"
                            }`}
                          />
                        ))}
                      </div>
                      <p className="text-xs text-muted-foreground">
                        Based on {reviews.length} review
                        {reviews.length !== 1 ? "s" : ""}
                      </p>
                    </div>

                    <div className="flex-1 space-y-1">
                      {[5, 4, 3, 2, 1].map((rating) => (
                        <div key={rating} className="flex items-center gap-2">
                          <span className="text-sm text-foreground w-3">
                            {rating}
                          </span>
                          <div className="flex-1 h-2 bg-muted rounded-full overflow-hidden">
                            <div
                              className="h-full bg-green-500 transition-all"
                              style={{
                                width: `${ratingPercentages[rating as keyof typeof ratingPercentages]}%`,
                              }}
                            />
                          </div>
                          <span className="text-xs text-muted-foreground w-10 text-right">
                            {
                              ratingPercentages[
                                rating as keyof typeof ratingPercentages
                              ]
                            }
                            %
                          </span>
                        </div>
                      ))}
                    </div>
                  </div>
                ) : (
                  <p className="text-sm text-muted-foreground">
                    No reviews yet. Be the first to review!
                  </p>
                )}
              </div>
            </div>
          </div>

          <div className="flex gap-3 p-4 border-t border-border shrink-0">
            <button
              onClick={handleMarkAsRead}
              disabled={userBook.shelf === "READ"}
              className="flex-1 px-4 py-3 text-white bg-green-600 hover:bg-green-700 rounded-lg font-medium transition disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Mark as Read
            </button>
            <button
              onClick={handleRemove}
              className="flex-1 px-4 py-3 text-destructive bg-destructive/10 hover:bg-destructive/20 rounded-lg font-medium transition"
            >
              Remove from Library
            </button>
          </div>
        </div>
      </div>

      <ReviewModal
        isOpen={isReviewModalOpen}
        onClose={() => setIsReviewModalOpen(false)}
        bookId={userBook.book.id}
        bookTitle={userBook.book.title}
        existingReview={userReview}
        onSubmit={handleReviewSubmit}
      />
    </>
  );
}
