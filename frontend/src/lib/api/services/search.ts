import { apiClient } from "../client";
import type { OpenLibrarySearchResponse } from "../../../types/api";

interface SearchParams {
  q?: string;
  title?: string;
  author?: string;
  limit?: number;
  page?: number;
}

export const searchApi = {
  async searchBooks(params: SearchParams): Promise<OpenLibrarySearchResponse> {
    const { data } = await apiClient.get<OpenLibrarySearchResponse>("/api/search", {
      params: {
        q: params.q,
        limit: params.limit,
        page: params.page,
      },
    });
    return data;
  },

  async searchByTitle(title: string, limit?: number): Promise<OpenLibrarySearchResponse> {
    const { data } = await apiClient.get<OpenLibrarySearchResponse>("/api/search/by-title", {
      params: { title, limit },
    });
    return data;
  },

  async searchByAuthor(author: string, limit?: number): Promise<OpenLibrarySearchResponse> {
    const { data } = await apiClient.get<OpenLibrarySearchResponse>("/api/search/by-author", {
      params: { author, limit },
    });
    return data;
  },
};
