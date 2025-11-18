import { useContext } from "react";
import { UserLibraryContext } from "./userLibraryContext";

export function useUserLibrary() {
  const context = useContext(UserLibraryContext);
  if (!context) {
    throw new Error("useUserLibrary must be used within UserLibraryProvider");
  }
  return context;
}
