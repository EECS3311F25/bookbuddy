import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { BookOpen, Mail, User } from "lucide-react";
import { usersService } from "@/services";
import { config } from "@/config";
import { useAuth } from "@/contexts/useAuth";
import { PasswordInput } from "@/components/common/PasswordInput";

export default function SignupPage() {
  const navigate = useNavigate();
  const { setUser } = useAuth();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSignup = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    // Validate password match
    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match");
      return;
    }

    // Validate password format (same as backend: 8+ chars, uppercase, lowercase, numbers)
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
    if (!passwordRegex.test(formData.password)) {
      setError(
        "Password must include at least 8 characters, uppercase, lowercase, and numbers",
      );
      return;
    }

    // Validate username (alphanumeric and underscore only, 3-20 chars)
    const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
    if (!usernameRegex.test(formData.username)) {
      setError(
        "Username must be 3-20 characters (letters, numbers, underscore only)",
      );
      return;
    }

    // Validate names (at least 1 character, letters and basic punctuation)
    const nameRegex = /^[a-zA-Z\s'-]{1,50}$/;
    if (!nameRegex.test(formData.firstName)) {
      setError("First name contains invalid characters");
      return;
    }
    if (!nameRegex.test(formData.lastName)) {
      setError("Last name contains invalid characters");
      return;
    }

    setIsLoading(true);

    try {
      const user = await usersService.register({
        firstName: formData.firstName,
        lastName: formData.lastName,
        username: formData.username,
        email: formData.email,
        password: formData.password,
      });

      // Update auth context (will auto-sync to localStorage)
      setUser(user);

      // Navigate to home
      navigate("/home");
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (err: any) {
      setError(err.response?.data || "Registration failed. Please try again.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-background px-4 py-8">
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
          <h2 className="text-4xl font-bold text-foreground">Create Account</h2>
          <p className="text-muted-foreground">
            Start tracking your reading journey today.
          </p>
        </div>

        {/* Form */}
        <form onSubmit={handleSignup} className="space-y-4">
          {/* First Name Input */}
          <div className="relative">
            <User className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
            <input
              type="text"
              name="firstName"
              placeholder="First Name"
              value={formData.firstName}
              onChange={handleChange}
              className="w-full pl-12 pr-4 py-3 bg-input text-foreground placeholder-muted-foreground rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary transition"
              required
            />
          </div>

          {/* Last Name Input */}
          <div className="relative">
            <User className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
            <input
              type="text"
              name="lastName"
              placeholder="Last Name"
              value={formData.lastName}
              onChange={handleChange}
              className="w-full pl-12 pr-4 py-3 bg-input text-foreground placeholder-muted-foreground rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary transition"
              required
            />
          </div>

          {/* Username Input */}
          <div className="relative">
            <User className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
            <input
              type="text"
              name="username"
              placeholder="Username"
              value={formData.username}
              onChange={handleChange}
              className="w-full pl-12 pr-4 py-3 bg-input text-foreground placeholder-muted-foreground rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary transition"
              required
            />
          </div>

          {/* Email Input */}
          <div className="relative">
            <Mail className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
              className="w-full pl-12 pr-4 py-3 bg-input text-foreground placeholder-muted-foreground rounded-2xl focus:outline-none focus:ring-2 focus:ring-primary transition"
              required
            />
          </div>

          {/* Password Input */}
          <PasswordInput
            name="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Password"
            required
          />

          {/* Confirm Password Input */}
          <PasswordInput
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            placeholder="Confirm Password"
            required
          />

          {/* Error Message */}
          {error && <p className="text-destructive text-sm">{error}</p>}

          {/* Sign Up Button */}
          <button
            type="submit"
            disabled={isLoading}
            className="w-full py-3 bg-primary text-primary-foreground font-bold rounded-2xl hover:opacity-90 transition disabled:opacity-50"
          >
            {isLoading ? "Creating Account..." : "Create Account"}
          </button>
        </form>

        {/* Login Link */}
        <div className="text-center">
          <p className="text-muted-foreground">
            Already have an account?
            <Link
              to="/login"
              className="text-primary font-semibold hover:underline"
            >
              Login
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}
