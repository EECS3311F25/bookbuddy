import { apiClient } from "@/services/api";
import type {
  UserBook,
  UserBookRequest,
  AddBookFromSearchRequest,
  ShelfStatus,
} from "@/types/api";

export const userbooksService = {
  async getUserBooks(userId: number): Promise<UserBook[]> {
    const { data } = await apiClient.get<UserBook[]>(
      `/api/userbooks/user/${userId}`,
    );
    return data;
  },

  async getAllUserBooks(): Promise<UserBook[]> {
    const { data } = await apiClient.get<UserBook[]>("/api/userbooks");
    return data;
  },

  async getUserBookById(id: number): Promise<UserBook> {
    const { data } = await apiClient.get<UserBook>(`/api/userbooks/${id}`);
    return data;
  },

  async addBookToLibrary(request: UserBookRequest): Promise<UserBook> {
    const { data } = await apiClient.post<UserBook>("/api/userbooks", request);
    return data;
  },

  async addBookFromSearch(
    request: AddBookFromSearchRequest,
  ): Promise<UserBook> {
    const { data } = await apiClient.post<UserBook>(
      "/api/userbooks/add-from-search",
      request,
    );
    return data;
  },

  async updateShelfStatus(id: number, shelf: ShelfStatus): Promise<UserBook> {
    const { data } = await apiClient.put<UserBook>(
      `/api/userbooks/${id}/shelf`,
      null,
      {
        params: { shelf },
      },
    );
    return data;
  },

  async removeBookFromLibrary(id: number): Promise<string> {
    const { data } = await apiClient.delete<string>(`/api/userbooks/${id}`);
    return data;
  },
};
