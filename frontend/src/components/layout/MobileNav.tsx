import { Home, BookOpen, TrendingUp, User, Search } from "lucide-react";
import { NavLink } from "react-router-dom";
import { config } from "@/config";
import { UserAvatar } from "@/components/common/UserAvatar";
import { useAuth } from "@/contexts/useAuth";

interface MobileNavProps {
  onSearchClick: () => void;
}

export default function MobileNav({ onSearchClick }: MobileNavProps) {
  const { user } = useAuth();

  return (
    <>
      {/* Top Header for Mobile */}
      <header className="fixed top-0 left-0 right-0 bg-card border-b border-border z-50 md:hidden">
        <div className="flex items-center justify-between h-14 px-4">
          {/* Logo */}
          <div className="flex items-center gap-2">
            <div className="w-7 h-7 bg-primary rounded-lg flex items-center justify-center">
              <BookOpen
                className="w-4 h-4 text-primary-foreground"
                strokeWidth={3}
              />
            </div>
            <h1 className="text-lg font-bold text-foreground">
              {config.appName}
            </h1>
          </div>

          {/* Search + Avatar */}
          <div className="flex items-center gap-2">
            <button
              onClick={onSearchClick}
              className="p-2 hover:bg-muted rounded-lg transition text-muted-foreground"
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
      </header>

      {/* Bottom Tab Bar */}
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
    </>
  );
}
