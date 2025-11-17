import type { BookSearchResult } from "@/types/api";

interface BookCardProps {
  book: BookSearchResult;
  onAddClick?: (book: BookSearchResult) => void;
}

const FALLBACK_COVER = `data:image/svg+xml,${encodeURIComponent(`
  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 128 192">
    <defs>
      <linearGradient id="bookGradient" x1="0%" y1="0%" x2="100%" y2="0%">
        <stop offset="0%" style="stop-color:#d4c5a0;stop-opacity:1" />
        <stop offset="85%" style="stop-color:#f5f5dc;stop-opacity:1" />
        <stop offset="100%" style="stop-color:#e8dcc0;stop-opacity:1" />
      </linearGradient>
      <linearGradient id="spineGradient" x1="0%" y1="0%" x2="100%" y2="0%">
        <stop offset="0%" style="stop-color:#8b6f47;stop-opacity:1" />
        <stop offset="50%" style="stop-color:#a0826d;stop-opacity:1" />
        <stop offset="100%" style="stop-color:#8b6f47;stop-opacity:1" />
      </linearGradient>
    </defs>

    <!-- Book cover background -->
    <rect width="128" height="192" fill="url(#bookGradient)"/>

    <!-- Spine shadow -->
    <rect x="0" y="0" width="12" height="192" fill="url(#spineGradient)"/>

    <!-- Decorative border -->
    <rect x="16" y="16" width="96" height="160" fill="none" stroke="#8b4513" stroke-width="1.5" opacity="0.4"/>
    <rect x="20" y="20" width="88" height="152" fill="none" stroke="#8b4513" stroke-width="1" opacity="0.3"/>

    <!-- Book icon -->
    <path d="M 52 70 L 52 90 L 76 90 L 76 70 Z" fill="none" stroke="#8b4513" stroke-width="2" opacity="0.5"/>
    <path d="M 48 70 L 64 62 L 80 70" fill="none" stroke="#8b4513" stroke-width="2" opacity="0.5"/>
    <line x1="64" y1="70" x2="64" y2="90" stroke="#8b4513" stroke-width="1.5" opacity="0.5"/>

    <!-- "No Cover" text -->
    <text x="64" y="115" font-family="serif" font-size="12" fill="#8b4513" text-anchor="middle" opacity="0.7">No Cover</text>
    <text x="64" y="130" font-family="serif" font-size="9" fill="#8b4513" text-anchor="middle" opacity="0.5">Available</text>
  </svg>
`)}`;

export function BookCard({ book, onAddClick }: BookCardProps) {
  return (
    <div className="bg-card border border-border rounded-lg p-4 hover:shadow-md transition flex gap-4">
      {/* Book Cover */}
      <img
        src={book.coverUrl || FALLBACK_COVER}
        alt={`${book.title} cover`}
        className="w-20 h-28 object-cover rounded shrink-0"
        onError={(e) => {
          e.currentTarget.src = FALLBACK_COVER;
        }}
      />

      {/* Book Info */}
      <div className="flex-1 min-w-0">
        <h3 className="font-semibold text-foreground line-clamp-2 mb-1">
          {book.title}
        </h3>
        <p className="text-sm text-muted-foreground mb-1">{book.author}</p>
        {book.publishYear && (
          <p className="text-xs text-muted-foreground">
            Published {book.publishYear}
          </p>
        )}

        {/* Add Button */}
        {onAddClick && (
          <button
            onClick={() => onAddClick(book)}
            className="mt-3 px-3 py-1.5 bg-primary text-primary-foreground text-sm rounded-lg hover:opacity-90 transition font-medium"
          >
            Add to Library
          </button>
        )}
      </div>
    </div>
  );
}
