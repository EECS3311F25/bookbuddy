import { apiClient } from "../client";
import type { User, UserRequest } from "../../../types/api";

export const usersApi = {
  async register(request: UserRequest): Promise<User> {
    const { data } = await apiClient.post<User>("/api/users/register", request);
    return data;
  },

  async getAllUsers(): Promise<User[]> {
    const { data } = await apiClient.get<User[]>("/api/users");
    return data;
  },

  async getUserById(id: number): Promise<User> {
    const { data } = await apiClient.get<User>(`/api/users/${id}`);
    return data;
  },

  async getUserByUsername(username: string): Promise<User> {
    const { data } = await apiClient.get<User>(`/api/users/username/${username}`);
    return data;
  },

  async updateUser(id: number, request: UserRequest): Promise<User> {
    const { data } = await apiClient.put<User>(`/api/users/${id}`, request);
    return data;
  },

  async deleteUser(id: number): Promise<string> {
    const { data } = await apiClient.delete<string>(`/api/users/${id}`);
    return data;
  },
};
