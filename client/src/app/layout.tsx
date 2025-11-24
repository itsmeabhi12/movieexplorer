import type { Metadata } from "next";
import ToasterWrapper from "../components/common/ToastWrapper";
import { AuthProvider } from "../lib/auth-context";
import "./globals.css";

export const metadata: Metadata = {
  title: "Movie Explorer",
  description: "Search and manage your movie watchlist",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="bg-gray-900 text-white">
        <AuthProvider>{children}</AuthProvider>
        <ToasterWrapper />
      </body>
    </html>
  );
}
