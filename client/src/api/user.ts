import { User } from "../types/user";
import { apiFetch } from "./client";

export async function getMe() {
  return await apiFetch<User>("/api/me");
}
