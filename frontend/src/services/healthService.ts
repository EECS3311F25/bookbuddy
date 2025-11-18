import { apiClient } from "@/services/api";

export interface HealthResponse {
  service: string;
  status: string;
  timestamp: string;
}

export const healthService = {
  async check(): Promise<HealthResponse> {
    const { data } = await apiClient.get<HealthResponse>("/api/health");
    return data;
  },
};
