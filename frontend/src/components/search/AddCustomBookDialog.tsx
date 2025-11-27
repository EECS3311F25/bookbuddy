import { useState } from "react";
import { X } from "lucide-react";
import { Genre, ShelfStatus } from "@/types/api";
import { catalogService, userbooksService } from "@/services";
import { useAuth } from "@/contexts/useAuth";
import { useUserLibrary } from "@/contexts/useUserLibrary";
import { toast } from "sonner";

interface AddCustomBookDialogProps {
  isOpen: boolean;
  onClose: () => void;
}

export function AddCustomBookDialog({
  isOpen,
  onClose,
}: AddCustomBookDialogProps) {
  const { user } = useAuth();
  const { refreshLibrary } = useUserLibrary();
  const [isLoading, setIsLoading] = useState(false);
  const [formData, setFormData] = useState({
    title: "",
    author: "",
    description: "",
    coverUrl: "",
    genre: Genre.OTHER,
  });
  const [errors, setErrors] = useState<{
    title?: string;
    author?: string;
    genre?: string;
  }>({});

  if (!isOpen) return null;

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
    // Clear error for this field
    setErrors((prev) => ({ ...prev, [name]: undefined }));
  };

  const validateForm = () => {
    const newErrors: typeof errors = {};

    if (!formData.title.trim()) {
      newErrors.title = "Title is required";
    }

    if (!formData.author.trim()) {
      newErrors.author = "Author is required";
    }

    if (!formData.genre) {
      newErrors.genre = "Genre is required";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm() || !user) return;

    setIsLoading(true);

    try {
      // Step 1: Create book in catalog
      const catalogBook = await catalogService.addBook({
        title: formData.title.trim(),
        author: formData.author.trim(),
        description: formData.description.trim() || undefined,
        coverUrl: formData.coverUrl.trim() || undefined,
        openLibraryId: `custom-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`, // Generate unique ID
        genre: formData.genre,
      });

      // Step 2: Add to user's library
      await userbooksService.addBookToLibrary({
        userId: user.id,
        bookId: catalogBook.id,
        shelf: ShelfStatus.WANT_TO_READ,
      });

      // Refresh library
      await refreshLibrary();

      toast.success("Custom book added to your library!");

      // Reset form and close
      setFormData({
        title: "",
        author: "",
        description: "",
        coverUrl: "",
        genre: Genre.OTHER,
      });
      onClose();
    } catch (error) {
      console.error("Failed to add custom book:", error);
      toast.error("Failed to add custom book. Please try again.");
    } finally {
      setIsLoading(false);
    }
  };

  const genreOptions = Object.values(Genre).map((genre) => ({
    value: genre,
    label: genre
      .replace(/_/g, " ")
      .toLowerCase()
      .replace(/\b\w/g, (c) => c.toUpperCase()),
  }));

  return (
    <>
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-black/50 z-60"
        onClick={onClose}
        aria-hidden="true"
      />

      {/* Dialog Container */}
      <div className="fixed inset-0 z-60 flex items-end md:items-center justify-center p-0 md:p-4 pointer-events-none">
        <div
          className="bg-card w-full md:max-w-lg md:rounded-2xl rounded-t-2xl flex flex-col pointer-events-auto max-h-[90vh] overflow-y-auto"
          onClick={(e) => e.stopPropagation()}
        >
          {/* Header */}
          <div className="flex items-center justify-between p-4 border-b border-border shrink-0 sticky top-0 bg-card z-10">
            <h2 className="text-lg font-bold text-foreground">
              Add Custom Book
            </h2>
            <button
              onClick={onClose}
              className="p-2 hover:bg-muted rounded-lg transition"
              aria-label="Close dialog"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          {/* Form */}
          <form onSubmit={handleSubmit} className="p-4 space-y-4">
            {/* Title */}
            <div>
              <label className="block text-sm font-medium text-foreground mb-1">
                Title <span className="text-destructive">*</span>
              </label>
              <input
                type="text"
                name="title"
                value={formData.title}
                onChange={handleChange}
                className={`w-full px-4 py-2 bg-input rounded-lg border transition focus:outline-none focus:ring-2 ${
                  errors.title
                    ? "border-destructive focus:ring-destructive"
                    : "border-border focus:ring-primary"
                }`}
                placeholder="Enter book title"
              />
              {errors.title && (
                <p className="text-xs text-destructive mt-1">{errors.title}</p>
              )}
            </div>

            {/* Author */}
            <div>
              <label className="block text-sm font-medium text-foreground mb-1">
                Author <span className="text-destructive">*</span>
              </label>
              <input
                type="text"
                name="author"
                value={formData.author}
                onChange={handleChange}
                className={`w-full px-4 py-2 bg-input rounded-lg border transition focus:outline-none focus:ring-2 ${
                  errors.author
                    ? "border-destructive focus:ring-destructive"
                    : "border-border focus:ring-primary"
                }`}
                placeholder="Enter author name"
              />
              {errors.author && (
                <p className="text-xs text-destructive mt-1">{errors.author}</p>
              )}
            </div>

            {/* Genre */}
            <div>
              <label className="block text-sm font-medium text-foreground mb-1">
                Genre <span className="text-destructive">*</span>
              </label>
              <select
                name="genre"
                value={formData.genre}
                onChange={handleChange}
                className={`w-full px-4 py-2 bg-input rounded-lg border transition focus:outline-none focus:ring-2 ${
                  errors.genre
                    ? "border-destructive focus:ring-destructive"
                    : "border-border focus:ring-primary"
                }`}
              >
                {genreOptions.map((option) => (
                  <option key={option.value} value={option.value}>
                    {option.label}
                  </option>
                ))}
              </select>
              {errors.genre && (
                <p className="text-xs text-destructive mt-1">{errors.genre}</p>
              )}
            </div>

            {/* Description */}
            <div>
              <label className="block text-sm font-medium text-foreground mb-1">
                Description (Optional)
              </label>
              <textarea
                name="description"
                value={formData.description}
                onChange={handleChange}
                rows={4}
                className="w-full px-4 py-2 bg-input rounded-lg border border-border transition focus:outline-none focus:ring-2 focus:ring-primary resize-none"
                placeholder="Enter book description"
              />
            </div>

            {/* Cover URL */}
            <div>
              <label className="block text-sm font-medium text-foreground mb-1">
                Cover Image URL (Optional)
              </label>
              <input
                type="url"
                name="coverUrl"
                value={formData.coverUrl}
                onChange={handleChange}
                className="w-full px-4 py-2 bg-input rounded-lg border border-border transition focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="https://example.com/cover.jpg"
              />
              <p className="text-xs text-muted-foreground mt-1">
                Paste a direct link to the book cover image
              </p>
            </div>

            {/* Buttons */}
            <div className="flex gap-3 pt-4">
              <button
                type="button"
                onClick={onClose}
                disabled={isLoading}
                className="flex-1 px-4 py-2 bg-secondary text-foreground rounded-lg font-medium hover:opacity-80 transition disabled:opacity-50"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={isLoading}
                className="flex-1 px-4 py-2 bg-primary text-primary-foreground rounded-lg font-medium hover:opacity-90 transition disabled:opacity-50"
              >
                {isLoading ? "Adding..." : "Add Book"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
