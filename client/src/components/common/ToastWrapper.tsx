"use client"; // important!

import { Toaster } from "react-hot-toast";

export default function ToasterWrapper() {
  return (
    <Toaster
      position="bottom-center"
      toastOptions={{
        duration: 1300,
        style: {
          background: "#1f2937",
          color: "#f9fafb",
          padding: "12px 24px",
          borderRadius: "8px",
        },
      }}
    />
  );
}
