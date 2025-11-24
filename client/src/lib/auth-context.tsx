"use client";

import { signout } from "@/src/api/auth";
import { getMe } from "@/src/api/user";
import React, { useEffect, useState } from "react";

interface AuthContextType {
  isAuthenticated: boolean;
  isAuthCheckInProgress: boolean;
  logout: () => Promise<void>;
  checkAuthenticatedStatus: () => void;
}

export const AuthContext = React.createContext<AuthContextType>(
  {} as AuthContextType
);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isAuthCheckInProgress, setIsAuthCheckInProgress] = useState(true);

  async function checkAuthenticatedStatus() {
    const { data, error } = await getMe();
    if (data) {
      setIsAuthenticated(true);
    } else if (error) {
      setIsAuthenticated(false);
    }
  }

  async function logout() {
    const { data, error } = await signout();
    console.log({ data, error });
    if (data) {
      setIsAuthenticated(false);
    } else if (error) {
      setIsAuthenticated(true);
    }
  }

  useEffect(() => {
    setIsAuthCheckInProgress(true);
    checkAuthenticatedStatus().then(() => setIsAuthCheckInProgress(false));
  }, []);

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        isAuthCheckInProgress,
        logout,
        checkAuthenticatedStatus,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
