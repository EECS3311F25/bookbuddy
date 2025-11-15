import { Home, BookOpen, TrendingUp, User } from "lucide-react";
import { NavLink } from "react-router-dom";

export default function MobileNav() {
  return (
    <nav className="fixed bottom-0 left-0 right-0 bg-card border-t border-border z-50 md:hidden">
      <div className="flex justify-around items-center h-16 px-4">
        <NavLink
          to="/home"
          className={({ isActive }) =>
            `flex flex-col items-center gap-1 px-4 py-2 rounded-lg transition ${
              isActive ? "text-primary" : "text-muted-foreground"
            }`
          }
        >
          <Home className="w-6 h-6" />
          <span className="text-xs font-medium">Home</span>
        </NavLink>

        <NavLink
          to="/library"
          className={({ isActive }) =>
            `flex flex-col items-center gap-1 px-4 py-2 rounded-lg transition ${
              isActive ? "text-primary" : "text-muted-foreground"
            }`
          }
        >
          <BookOpen className="w-6 h-6" />
          <span className="text-xs font-medium">Library</span>
        </NavLink>

        <NavLink
          to="/tracker"
          className={({ isActive }) =>
            `flex flex-col items-center gap-1 px-4 py-2 rounded-lg transition ${
              isActive ? "text-primary" : "text-muted-foreground"
            }`
          }
        >
          <TrendingUp className="w-6 h-6" />
          <span className="text-xs font-medium">Tracker</span>
        </NavLink>

        <NavLink
          to="/settings"
          className={({ isActive }) =>
            `flex flex-col items-center gap-1 px-4 py-2 rounded-lg transition ${
              isActive ? "text-primary" : "text-muted-foreground"
            }`
          }
        >
          <User className="w-6 h-6" />
          <span className="text-xs font-medium">Profile</span>
        </NavLink>
      </div>
    </nav>
  );
}
