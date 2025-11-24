import { apiFetch } from "./client";

export async function signin(username: string, password: string) {
  const result = await apiFetch<AuthResponse>(`/api/auth/signin`, {
    method: "POST",
    body: JSON.stringify({ username, password }),
  });

  return result;
}

export async function signup(username: string, password: string) {
  const result = await apiFetch<AuthResponse>(`/api/auth/signup`, {
    method: "POST",
    body: JSON.stringify({ username, password }),
  });

  return result;
}

export async function signout() {
  return await apiFetch<void>(`/api/auth/signout`, {
    method: "POST",
  });
}
