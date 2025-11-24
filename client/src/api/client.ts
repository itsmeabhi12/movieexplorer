import { ApiError } from "../types/api";

export async function apiFetch<T>(
  endpoint: string,
  options?: RequestInit
): Promise<{ data?: T; error?: ApiError }> {
  try {
    const API_BASE_URL =
      process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

    const url = new URL(`${API_BASE_URL}${endpoint}`);
    const response = await fetch(url.toString(), {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      ...options,
    });

    if (!response.ok) {
      try {
        const data = await response.json();
        return {
          error: {
            message: data.description || data.message || "API request failed",
          },
        };
      } catch {
        return {
          error: {
            message: `HTTP ${response.status}: ${response.statusText}`,
          },
        };
      }
    }

    if (response.status === 204) {
      return { data: undefined };
    }

    const raw = await response.text();
    const parsed = raw ? (JSON.parse(raw) as T) : undefined;

    return { data: parsed };
  } catch (error) {
    return {
      error: {
        message:
          error instanceof Error
            ? error.message
            : "An unexpected error occurred",
      },
    };
  }
}
