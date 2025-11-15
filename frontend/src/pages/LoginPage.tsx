import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { BookOpen, Mail, Lock } from "lucide-react";
import { usersApi } from "../lib/api/services/users";
import { config } from "../config";
import { useAuth } from "@/contexts/AuthContext";

export default function LoginPage() {
  const navigate = useNavigate();
  const { setUser } = useAuth();
  const [usernameOrEmail, setUsernameOrEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    // Validate username/email is not empty
    if (!usernameOrEmail.trim()) {
      setError("Please enter your email or username");
      return;
    }

    // Validate password is not empty
    if (!password.trim()) {
      setError("Please enter your password");
      return;
    }

    setIsLoading(true);

    try {
      const user = await usersApi.login({
        usernameOrEmail,
        password,
      });

      // Update auth context (will auto-sync to localStorage)
      setUser(user);

      // Navigate to home
      navigate("/home");
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (err: any) {
      setError(
        err.response?.data || "Login failed. Please check your credentials.",
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-background px-4">
      <div className="w-full max-w-md space-y-8">
        {/* Logo */}
        <div className="flex flex-col items-center space-y-4">
          <div className="flex items-center gap-2">
            <div className="w-10 h-10 bg-primary rounded-lg flex items-center justify-center">
              <BookOpen className="w-6 h-6 text-foreground" strokeWidth={3} />
            </div>
            <h1 className="text-3xl font-bold text-foreground">
              {config.appName}
            </h1>
          </div>
        </div>

        {/* Welcome Section */}
        <div className="space-y-2 text-center">
          <h2 className="text-4xl font-bold text-foreground">Welcome Back</h2>
          <p className="text-muted-foreground">
            Log in to continue your reading journey.
          </p>
        </div>

        {/* Form */}
        <form onSubmit={handleLogin} className="space-y-4">
          {/* Email or Username Input */}
          <div className="relative">
            <Mail className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
            <input
              type="text"
              placeholder="Email or Username"
              value={usernameOrEmail}
              onChange={(e) => setUsernameOrEmail(e.target.value)}
              className="w-full pl-12 pr-4 py-3 bg-input text-foreground placeholder-muted-foreground rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary transition"
              required
            />
          </div>

          {/* Password Input */}
          <div className="relative">
            <Lock className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full pl-12 pr-4 py-3 bg-input text-foreground placeholder-muted-foreground rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary transition"
              required
            />
          </div>

          {/* Error Message */}
          {error && <p className="text-destructive text-sm">{error}</p>}

          {/* Login Button */}
          <button
            type="submit"
            disabled={isLoading}
            className="w-full py-3 bg-primary text-primary-foreground font-bold rounded-2xl hover:opacity-90 transition disabled:opacity-50"
          >
            {isLoading ? "Logging in..." : "Login"}
          </button>
        </form>

        {/* Sign Up Link */}
        <div className="text-center">
          <p className="text-muted-foreground">
            Don't have an account?
            <Link
              to="/signup"
              className="text-primary font-semibold hover:underline"
            >
              Sign Up
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
