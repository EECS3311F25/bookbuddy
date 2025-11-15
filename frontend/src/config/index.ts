/**
 * Application configuration
 * Centralized place for all environment variables and config
 */

export const config = {
  /**
   * API Base URL
   * Default: http://localhost:8080
   * Override with VITE_API_BASE_URL environment variable
   */
  apiBaseUrl: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",

  /**
   * API Timeout (milliseconds)
   */
  apiTimeout: 10000,

  /**
   * Application Name
   */
  appName: "BookBuddy",

  /**
   * Application Version
   */
  appVersion: "1.0.0",

  /**
   * Enable debug mode
   */
  isDevelopment: import.meta.env.DEV,

  /**
   * Enable production mode
   */
  isProduction: import.meta.env.PROD,
} as const;
