import { apiClient } from "@/services/api";
import type { SearchResponse } from "@/types/api";

interface SearchParams {
  q?: string;
  limit?: number;
  page?: number;
}

export const searchService = {
  async searchBooks(params: SearchParams): Promise<SearchResponse> {
    const { data } = await apiClient.get<SearchResponse>("/api/search", {
      params: {
        q: params.q,
        limit: params.limit,
        page: params.page,
      },
    });
    return data;
  },
};
