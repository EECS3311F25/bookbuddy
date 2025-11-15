import type { ReactNode } from "react";
import MobileNav from "./MobileNav";
import DesktopNav from "./DesktopNav";

interface AppShellProps {
  children: ReactNode;
}

export default function AppShell({ children }: AppShellProps) {
  return (
    <div className="min-h-screen bg-background">
      <DesktopNav />
      <main className="pt-16 pb-20 md:pb-6">{children}</main>
      <MobileNav />
    </div>
  );
}
