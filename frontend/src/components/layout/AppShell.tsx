import { useState, type ReactNode } from "react";
import MobileNav from "./MobileNav";
import DesktopNav from "./DesktopNav";
import { SearchDialog } from "@/components/search/SearchDialog";

interface AppShellProps {
  children: ReactNode;
}

export default function AppShell({ children }: AppShellProps) {
  const [isSearchOpen, setIsSearchOpen] = useState(false);

  const handleSearchOpen = () => setIsSearchOpen(true);
  const handleSearchClose = () => setIsSearchOpen(false);

  return (
    <div className="min-h-screen bg-background">
      <DesktopNav onSearchClick={handleSearchOpen} />
      <main className="pt-14 pb-20 md:pt-16 md:pb-6">{children}</main>
      <MobileNav onSearchClick={handleSearchOpen} />
      <SearchDialog isOpen={isSearchOpen} onClose={handleSearchClose} />
    </div>
  );
}
