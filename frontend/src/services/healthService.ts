import { apiClient } from "../lib/api/client";

export interface HealthResponse {
  service: string;
  status: string;
  timestamp: string;
}

export const healthService = {
  /**
   * Check backend health status
   * GET /api/health
   */
  check: async (): Promise<HealthResponse> => {
    const response = await apiClient.get<HealthResponse>("/api/health");
    return response.data;
  },
};
