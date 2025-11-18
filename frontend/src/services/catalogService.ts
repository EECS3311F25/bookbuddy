import { apiClient } from "@/services/api";
import type { BookCatalog, BookCatalogRequest } from "@/types/api";

export const catalogService = {
  async getAllBooks(): Promise<BookCatalog[]> {
    const { data } = await apiClient.get<BookCatalog[]>("/api/catalog");
    return data;
  },

  async getBookById(bookId: number): Promise<BookCatalog> {
    const { data } = await apiClient.get<BookCatalog>(`/api/catalog/${bookId}`);
    return data;
  },

  async addBook(request: BookCatalogRequest): Promise<BookCatalog> {
    const { data } = await apiClient.post<BookCatalog>("/api/catalog", request);
    return data;
  },

  async updateBook(
    bookId: number,
    request: BookCatalogRequest,
  ): Promise<BookCatalog> {
    const { data } = await apiClient.put<BookCatalog>(
      `/api/catalog/${bookId}`,
      request,
    );
    return data;
  },

  async deleteBook(bookId: number): Promise<string> {
    const { data } = await apiClient.delete<string>(`/api/catalog/${bookId}`);
    return data;
  },
};
