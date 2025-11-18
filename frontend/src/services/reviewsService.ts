import { apiClient } from "@/services/api";
import type { ReviewRequest, ReviewResponse } from "@/types/api";

export const reviewsService = {
  async createReview(request: ReviewRequest): Promise<ReviewResponse> {
    const { data } = await apiClient.post<ReviewResponse>(
      "/api/reviews",
      request,
    );
    return data;
  },

  async getReviewsByBook(bookId: number): Promise<ReviewResponse[]> {
    const { data } = await apiClient.get<ReviewResponse[]>(
      `/api/reviews/book/${bookId}`,
    );
    return data;
  },

  async getAverageRating(bookId: number): Promise<number> {
    const { data } = await apiClient.get<number>(
      `/api/reviews/book/${bookId}/average`,
    );
    return data;
  },

  async getReviewsByUser(userId: number): Promise<ReviewResponse[]> {
    const { data } = await apiClient.get<ReviewResponse[]>(
      `/api/reviews/user/${userId}`,
    );
    return data;
  },

  async updateReview(
    reviewId: number,
    request: ReviewRequest,
  ): Promise<ReviewResponse> {
    const { data } = await apiClient.put<ReviewResponse>(
      `/api/reviews/${reviewId}`,
      request,
    );
    return data;
  },

  async deleteReview(reviewId: number, userId: number): Promise<void> {
    await apiClient.delete(`/api/reviews/${reviewId}`, {
      params: { userId },
    });
  },
};
