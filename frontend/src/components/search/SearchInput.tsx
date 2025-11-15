import { useState, useEffect, useRef } from "react";
import { Search, X } from "lucide-react";

const DEFAULT_DEBOUNCE_MS = 600;

interface SearchInputProps {
  onSearch: (query: string) => void;
  placeholder?: string;
  debounceMs?: number;
}

export function SearchInput({
  onSearch,
  placeholder = "Search for books by title or author...",
  debounceMs = DEFAULT_DEBOUNCE_MS,
}: SearchInputProps) {
  const [value, setValue] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);

  // Auto-focus on mount
  useEffect(() => {
    inputRef.current?.focus();
  }, []);

  // Debounced search with minimum 2 non-whitespace characters
  useEffect(() => {
    const timer = setTimeout(() => {
      const trimmedValue = value.trim();
      // Only search if 2+ characters or empty (to clear results)
      if (trimmedValue.length >= 2 || trimmedValue.length === 0) {
        onSearch(trimmedValue);
      }
    }, debounceMs);

    return () => clearTimeout(timer);
  }, [value, debounceMs, onSearch]);

  const handleClear = () => {
    setValue("");
    inputRef.current?.focus();
  };

  return (
    <div className="relative">
      <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
      <input
        ref={inputRef}
        type="text"
        value={value}
        onChange={(e) => setValue(e.target.value)}
        placeholder={placeholder}
        className="w-full pl-10 pr-10 py-3 bg-input border border-border rounded-lg text-foreground placeholder:text-muted-foreground focus:outline-none focus:ring-2 focus:ring-ring"
      />
      {value && (
        <button
          onClick={handleClear}
          className="absolute right-3 top-1/2 -translate-y-1/2 p-1 hover:bg-muted rounded transition"
          aria-label="Clear search"
        >
          <X className="w-4 h-4 text-muted-foreground" />
        </button>
      )}
    </div>
  );
}
