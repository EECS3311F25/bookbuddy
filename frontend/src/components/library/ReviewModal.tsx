import { useState, useEffect } from "react";
import { X, Star } from "lucide-react";
import type { ReviewResponse } from "@/types/api";

interface ReviewModalProps {
  isOpen: boolean;
  onClose: () => void;
  bookId: number;
  bookTitle: string;
  existingReview?: ReviewResponse;
  onSubmit: (rating: number, reviewText: string) => Promise<void>;
}

export function ReviewModal({
  isOpen,
  onClose,
  bookTitle,
  existingReview,
  onSubmit,
}: ReviewModalProps) {
  const [rating, setRating] = useState(0);
  const [hoverRating, setHoverRating] = useState(0);
  const [reviewText, setReviewText] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    if (isOpen && existingReview) {
      setRating(existingReview.rating);
      setReviewText(existingReview.reviewText || "");
    } else if (isOpen) {
      setRating(0);
      setReviewText("");
    }
  }, [isOpen, existingReview]);

  const handleSubmit = async () => {
    if (rating === 0) return;

    setIsSubmitting(true);
    try {
      await onSubmit(rating, reviewText);
      onClose();
    } catch (error) {
      console.error("Failed to submit review:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleClose = () => {
    if (!isSubmitting) {
      setRating(0);
      setReviewText("");
      onClose();
    }
  };

  if (!isOpen) return null;

  return (
    <>
      <div
        className="fixed inset-0 bg-black/50 z-60"
        onClick={handleClose}
        aria-hidden="true"
      />

      <div className="fixed inset-0 z-60 flex items-center justify-center p-4 pointer-events-none">
        <div
          className="bg-card w-full max-w-md rounded-2xl flex flex-col pointer-events-auto"
          onClick={(e) => e.stopPropagation()}
        >
          <div className="flex items-center justify-between p-4 border-b border-border">
            <h2 className="text-lg font-bold text-foreground">
              {existingReview ? "Edit Review" : "Write a Review"}
            </h2>
            <button
              onClick={handleClose}
              disabled={isSubmitting}
              className="p-2 hover:bg-muted rounded-lg transition disabled:opacity-50"
              aria-label="Close"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          <div className="p-6 space-y-6">
            <div>
              <p className="text-sm text-muted-foreground mb-1">
                Reviewing book:
              </p>
              <p className="font-semibold text-foreground">{bookTitle}</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-foreground mb-3">
                Rating <span className="text-destructive">*</span>
              </label>
              <div className="flex gap-2">
                {[1, 2, 3, 4, 5].map((star) => (
                  <button
                    key={star}
                    type="button"
                    onClick={() => setRating(star)}
                    onMouseEnter={() => setHoverRating(star)}
                    onMouseLeave={() => setHoverRating(0)}
                    disabled={isSubmitting}
                    className="transition-transform hover:scale-110 disabled:opacity-50"
                  >
                    <Star
                      className={`w-8 h-8 ${
                        star <= (hoverRating || rating)
                          ? "fill-yellow-400 text-yellow-400"
                          : "text-muted-foreground"
                      }`}
                    />
                  </button>
                ))}
              </div>
              {rating > 0 && (
                <p className="text-sm text-muted-foreground mt-2">
                  {rating} out of 5 stars
                </p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-foreground mb-2">
                Review (optional)
              </label>
              <textarea
                value={reviewText}
                onChange={(e) => setReviewText(e.target.value)}
                disabled={isSubmitting}
                placeholder="Share your thoughts about this book..."
                className="w-full px-3 py-2 bg-input text-foreground rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-green-500 resize-none disabled:opacity-50"
                rows={5}
              />
            </div>
          </div>

          <div className="flex gap-3 p-4 border-t border-border">
            <button
              onClick={handleClose}
              disabled={isSubmitting}
              className="flex-1 px-4 py-2 text-foreground bg-muted hover:bg-muted/80 rounded-lg transition disabled:opacity-50"
            >
              Cancel
            </button>
            <button
              onClick={handleSubmit}
              disabled={rating === 0 || isSubmitting}
              className="flex-1 px-4 py-2 text-white bg-green-600 hover:bg-green-700 rounded-lg transition disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {isSubmitting
                ? "Submitting..."
                : existingReview
                  ? "Update Review"
                  : "Submit Review"}
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
