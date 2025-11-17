import { apiClient } from "../lib/api/client";
import type { User, UserRequest } from "../types/api";

export const userService = {
  /**
   * Register a new user
   * POST /api/users/register
   */
  register: async (userData: UserRequest): Promise<User> => {
    const response = await apiClient.post<User>(
      "/api/users/register",
      userData,
    );
    return response.data;
  },

  /**
   * Get all users
   * GET /api/users
   */
  getAllUsers: async (): Promise<User[]> => {
    const response = await apiClient.get<User[]>("/api/users");
    return response.data;
  },

  /**
   * Get user by ID
   * GET /api/users/{id}
   */
  getUserById: async (id: number): Promise<User> => {
    const response = await apiClient.get<User>(`/api/users/${id}`);
    return response.data;
  },

  /**
   * Get user by username
   * GET /api/users/username/{username}
   */
  getUserByUsername: async (username: string): Promise<User> => {
    const response = await apiClient.get<User>(
      `/api/users/username/${username}`,
    );
    return response.data;
  },

  /**
   * Update user profile
   * PUT /api/users/{id}
   */
  updateUser: async (id: number, userData: UserRequest): Promise<User> => {
    const response = await apiClient.put<User>(`/api/users/${id}`, userData);
    return response.data;
  },

  /**
   * Delete user
   * DELETE /api/users/{id}
   */
  deleteUser: async (id: number): Promise<void> => {
    await apiClient.delete(`/api/users/${id}`);
  },
};
