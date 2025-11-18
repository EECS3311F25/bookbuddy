import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ThemeProvider } from "next-themes";
import { AuthProvider } from "@/contexts/AuthContext";
import { UserLibraryProvider } from "@/contexts/UserLibraryContext.tsx";
import { Toaster } from "@/components/ui/sonner";
import "./globals.css";
import App from "./App.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <ThemeProvider attribute="class" defaultTheme="light" enableSystem>
      <AuthProvider>
        <UserLibraryProvider>
          <App />
          <Toaster />
        </UserLibraryProvider>
      </AuthProvider>
    </ThemeProvider>
  </StrictMode>,
);
