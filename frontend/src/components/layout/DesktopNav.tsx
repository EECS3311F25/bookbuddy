import { Home, BookOpen, TrendingUp, User, Search } from "lucide-react";
import { NavLink } from "react-router-dom";
import { config } from "@/config";
import { UserAvatar } from "@/components/common/UserAvatar";
import { useAuth } from "@/contexts/useAuth";

interface DesktopNavProps {
  onSearchClick: () => void;
}

export default function DesktopNav({ onSearchClick }: DesktopNavProps) {
  const { user } = useAuth();

  return (
    <nav className="hidden md:flex fixed top-0 left-0 right-0 bg-card border-b border-border z-50">
      <div className="container mx-auto px-6 h-16 flex items-center justify-between">
        {/* Logo */}
        <div className="flex items-center gap-2">
          <div className="w-8 h-8 bg-primary rounded-lg flex items-center justify-center">
            <BookOpen
              className="w-5 h-5 text-primary-foreground"
              strokeWidth={3}
            />
          </div>
          <h1 className="text-xl font-bold text-foreground">
            {config.appName}
          </h1>
        </div>

        {/* Center Nav Links */}
        <div className="flex items-center gap-1">
          <NavLink
            to="/home"
            className={({ isActive }) =>
              `flex items-center gap-2 px-4 py-2 rounded-lg transition ${
                isActive
                  ? "bg-primary/10 text-primary"
                  : "text-muted-foreground hover:bg-muted hover:text-foreground"
              }`
            }
          >
            <Home className="w-5 h-5" />
            <span className="font-medium">Home</span>
          </NavLink>

          <NavLink
            to="/library"
            className={({ isActive }) =>
              `flex items-center gap-2 px-4 py-2 rounded-lg transition ${
                isActive
                  ? "bg-primary/10 text-primary"
                  : "text-muted-foreground hover:bg-muted hover:text-foreground"
              }`
            }
          >
            <BookOpen className="w-5 h-5" />
            <span className="font-medium">Library</span>
          </NavLink>

          <NavLink
            to="/tracker"
            className={({ isActive }) =>
              `flex items-center gap-2 px-4 py-2 rounded-lg transition ${
                isActive
                  ? "bg-primary/10 text-primary"
                  : "text-muted-foreground hover:bg-muted hover:text-foreground"
              }`
            }
          >
            <TrendingUp className="w-5 h-5" />
            <span className="font-medium">Tracker</span>
          </NavLink>

          <NavLink
            to="/settings"
            className={({ isActive }) =>
              `flex items-center gap-2 px-4 py-2 rounded-lg transition ${
                isActive
                  ? "bg-primary/10 text-primary"
                  : "text-muted-foreground hover:bg-muted hover:text-foreground"
              }`
            }
          >
            <User className="w-5 h-5" />
            <span className="font-medium">Settings</span>
          </NavLink>
        </div>

        {/* Right Side: Search + Avatar */}
        <div className="flex items-center gap-3">
          <button
            onClick={onSearchClick}
            className="p-2 hover:bg-muted rounded-lg transition text-muted-foreground hover:text-foreground"
            aria-label="Search books"
          >
            <Search className="w-5 h-5" />
          </button>

          {user && (
            <NavLink to="/settings">
              <UserAvatar
                firstName={user.firstName}
                lastName={user.lastName}
                email={user.email}
                size="sm"
              />
            </NavLink>
          )}
        </div>
      </div>
    </nav>
  );
}
