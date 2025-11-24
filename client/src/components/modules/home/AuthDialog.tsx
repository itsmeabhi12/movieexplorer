"use client";

import { signin, signup } from "@/src/api/auth";
import { useState } from "react";
import toast from "react-hot-toast";
import { PrimaryButton } from "../../common/PrimaryButton";

export function AuthDialog({
  isOpen,
  onClose,
}: {
  isOpen: boolean;
  onClose: () => void;
}) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLogin, setIsLogin] = useState(true);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    if (isLogin) {
      const response = await signin(email, password);
      if (response.error) {
        toast.error("Login failed: " + response.error.message);
      } else {
        toast.success("Login successful!");
        onClose();
      }
    } else {
      const response = await signup(email, password);
      if (response.error) {
        toast.error("Signup failed: " + response.error.message);
      } else {
        toast.success("Signup successful!");
        onClose();
      }
    }
    setLoading(false);
  };

  if (!isOpen) return null;

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center"
      onClick={onClose}
    >
      {/* BLur effect */}

      <div className="absolute inset-0 bg-black/30 backdrop-blur-sm" />

      <div
        className="relative w-full max-w-sm rounded-lg bg-black p-6 shadow-lg border border-neutral-700"
        onClick={(e) => e.stopPropagation()}
      >
        <button
          type="button"
          onClick={onClose}
          aria-label="Close dialog"
          className="absolute right-2 top-2 rounded-md p-2 text-neutral-300 hover:text-white hover:bg-neutral-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition"
        >
          ✕
        </button>

        <h2 className="text-xl font-semibold mb-4">
          {isLogin ? "Login" : "Sign Up"}
        </h2>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input
            placeholder="Username"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="border border-neutral-600 rounded p-2 bg-neutral-800 text-white placeholder:text-neutral-400 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="border border-neutral-600 rounded p-2 bg-neutral-800 text-white placeholder:text-neutral-400 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
          <PrimaryButton
            onTap={() => handleSubmit}
            type="submit"
            isLoading={loading}
            title={isLogin ? "Login" : "Sign Up"}
          />
        </form>

        <button
          className="mt-4 text-sm text-blue-500 hover:underline"
          onClick={() => setIsLogin((v) => !v)}
        >
          {isLogin ? "Switch to Sign Up" : "Switch to Login"}
        </button>
      </div>
    </div>
  );
}
