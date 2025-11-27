import { apiClient } from "./api";
import type {
  MonthlyTracker,
  MonthlyTrackerBook,
  TrackerProgress,
  CreateTrackerRequest,
  AddBookToTrackerRequest,
  BulkAddBooksRequest,
  BulkAddBooksResponse,
} from "@/types/api";

// Monthly Tracker API
export const monthlyTrackerService = {
  /**
   * Create a new monthly tracker
   */
  createTracker: async (
    request: CreateTrackerRequest,
  ): Promise<MonthlyTracker> => {
    const response = await apiClient.post("/api/monthly-tracker", request);
    return response.data;
  },

  /**
   * Get all trackers
   */
  getAllTrackers: async (): Promise<MonthlyTracker[]> => {
    const response = await apiClient.get("/api/monthly-tracker");
    return response.data;
  },

  /**
   * Get tracker by ID
   */
  getTrackerById: async (id: number): Promise<MonthlyTracker> => {
    const response = await apiClient.get(`/api/monthly-tracker/${id}`);
    return response.data;
  },

  /**
   * Get all trackers for a specific user
   */
  getTrackersByUser: async (userId: number): Promise<MonthlyTracker[]> => {
    const response = await apiClient.get(`/api/monthly-tracker/user/${userId}`);
    return response.data;
  },

  /**
   * Get tracker for current month
   */
  getCurrentMonthTracker: async (userId: number): Promise<MonthlyTracker> => {
    const response = await apiClient.get(
      `/api/monthly-tracker/user/${userId}/current`,
    );
    return response.data;
  },

  /**
   * Get tracker for specific month and year
   */
  getTrackerByMonth: async (
    userId: number,
    month: number,
    year: number,
  ): Promise<MonthlyTracker> => {
    const response = await apiClient.get(
      `/api/monthly-tracker/user/${userId}/month`,
      {
        params: { month, year },
      },
    );
    return response.data;
  },

  /**
   * Get tracker progress statistics
   */
  getProgress: async (trackerId: number): Promise<TrackerProgress> => {
    const response = await apiClient.get(
      `/api/monthly-tracker/${trackerId}/progress`,
    );
    return response.data;
  },

  /**
   * Update monthly goal
   */
  updateGoal: async (
    trackerId: number,
    newTarget: number,
  ): Promise<MonthlyTracker> => {
    const response = await apiClient.put(
      `/api/monthly-tracker/${trackerId}/goal`,
      null,
      {
        params: { newTarget },
      },
    );
    return response.data;
  },

  /**
   * Delete a tracker
   */
  deleteTracker: async (trackerId: number): Promise<void> => {
    await apiClient.delete(`/api/monthly-tracker/${trackerId}`);
  },
};

// Monthly Tracker Books API
export const monthlyTrackerBookService = {
  /**
   * Add a book to a tracker
   */
  addBookToTracker: async (
    request: AddBookToTrackerRequest,
  ): Promise<MonthlyTrackerBook> => {
    const response = await apiClient.post(
      "/api/monthly-tracker-books",
      request,
    );
    return response.data;
  },

  /**
   * Add multiple books to a tracker
   */
  addBooksToTracker: async (
    request: BulkAddBooksRequest,
  ): Promise<BulkAddBooksResponse> => {
    const response = await apiClient.post(
      "/api/monthly-tracker-books/bulk",
      request,
    );
    return response.data;
  },

  /**
   * Get all books in a tracker
   */
  getBooksInTracker: async (
    trackerId: number,
  ): Promise<MonthlyTrackerBook[]> => {
    const response = await apiClient.get(
      `/api/monthly-tracker-books/tracker/${trackerId}`,
    );
    return response.data;
  },

  /**
   * Check if a book is already in a tracker
   */
  containsBook: async (
    trackerId: number,
    userBookId: number,
  ): Promise<boolean> => {
    const response = await apiClient.get(
      `/api/monthly-tracker-books/tracker/${trackerId}/contains/${userBookId}`,
    );
    return response.data;
  },

  /**
   * Mark a book as completed
   */
  markAsCompleted: async (
    trackerBookId: number,
  ): Promise<MonthlyTrackerBook> => {
    const response = await apiClient.put(
      `/api/monthly-tracker-books/${trackerBookId}/complete`,
    );
    return response.data;
  },

  /**
   * Remove a book from tracker
   */
  removeBookFromTracker: async (trackerBookId: number): Promise<void> => {
    await apiClient.delete(`/api/monthly-tracker-books/${trackerBookId}`);
  },
};
