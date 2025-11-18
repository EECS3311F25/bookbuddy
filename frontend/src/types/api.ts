export enum Genre {
  FICTION = "FICTION",
  NON_FICTION = "NON_FICTION",
  FANTASY = "FANTASY",
  SCIENCE_FICTION = "SCIENCE_FICTION",
  MYSTERY = "MYSTERY",
  ROMANCE = "ROMANCE",
  CLASSICS = "CLASSICS",
  PHYLOSOPHY = "PHYLOSOPHY",
  HISTORY = "HISTORY",
  BIOGRAPHY = "BIOGRAPHY",
  PSYCHOLOGY = "PSYCHOLOGY",
  OTHER = "OTHER",
}

export enum ShelfStatus {
  WANT_TO_READ = "WANT_TO_READ",
  CURRENTLY_READING = "CURRENTLY_READING",
  READ = "READ",
}

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
}

export interface LoginRequest {
  usernameOrEmail: string;
  password: string;
}

export interface BookCatalog {
  id: number;
  title: string;
  author: string;
  description?: string;
  coverUrl?: string;
  openLibraryId?: string;
  genre: Genre;
}

export interface UserBook {
  id: number;
  book: BookCatalog;
  shelf: ShelfStatus;
  completedAt?: string;
  createdAt: string;
}

export interface UserRequest {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
}

export interface BookCatalogRequest {
  title: string;
  author: string;
  description?: string;
  coverUrl?: string;
  openLibraryId?: string;
  genre?: Genre;
}

export interface UserBookRequest {
  userId: number;
  bookId: number;
  shelf?: ShelfStatus;
}

export interface AddBookFromSearchRequest {
  userId: number;
  title: string;
  author: string;
  openLibraryId: string;
  coverUrl?: string;
  genre?: Genre;
  shelf?: ShelfStatus;
}

export interface BookSearchResult {
  openLibraryId: string;
  title: string;
  author: string;
  coverUrl: string | null;
  publishYear: number | null;
}

export interface SearchResponse {
  totalResults: number;
  currentPage: number;
  books: BookSearchResult[];
}

export interface ReviewRequest {
  userId: number;
  bookId: number;
  rating: number;
  reviewText?: string;
}

export interface ReviewResponse {
  id: number;
  username: string;
  bookTitle: string;
  bookId: number;
  rating: number;
  reviewText?: string;
}
