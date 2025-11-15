import { useState } from "react";
import { X } from "lucide-react";
import type { User } from "../../types/api";

interface ProfileModalProps {
  isOpen: boolean;
  onClose: () => void;
  userData: User;
  onSaveProfile: (data: {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
  }) => Promise<void>;
  onChangePassword: (
    currentPassword: string,
    newPassword: string,
  ) => Promise<void>;
}

export function ProfileModal({
  isOpen,
  onClose,
  userData,
  onSaveProfile,
  onChangePassword,
}: ProfileModalProps) {
  const [activeTab, setActiveTab] = useState<"profile" | "password">("profile");
  const [formData, setFormData] = useState({
    firstName: userData.firstName,
    lastName: userData.lastName,
    username: userData.username,
    email: userData.email,
  });
  const [passwordData, setPasswordData] = useState({
    currentPassword: "",
    newPassword: "",
    confirmPassword: "",
  });
  const [profileErrors, setProfileErrors] = useState<{
    firstName?: string;
    lastName?: string;
    username?: string;
  }>({});
  const [passwordError, setPasswordError] = useState("");
  const [passwordSuccess, setPasswordSuccess] = useState("");
  const [passwordStrength, setPasswordStrength] = useState<
    "weak" | "medium" | "strong" | null
  >(null);
  const [isLoading, setIsLoading] = useState(false);

  if (!isOpen) return null;

  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/;
  const usernameRegex = /^[a-zA-Z0-9_]{3,20}$/;
  const nameRegex = /^[a-zA-Z\s'-]{1,50}$/;

  const validatePassword = (password: string) => {
    if (passwordRegex.test(password)) return "strong";
    if (password.length >= 6) return "medium";
    return "weak";
  };

  const validateProfile = () => {
    const errors: typeof profileErrors = {};
    if (!formData.firstName.trim() || !nameRegex.test(formData.firstName)) {
      errors.firstName = "Invalid first name";
    }
    if (!formData.lastName.trim() || !nameRegex.test(formData.lastName)) {
      errors.lastName = "Invalid last name";
    }
    if (!usernameRegex.test(formData.username)) {
      errors.username =
        "Username must be 3-20 characters (letters, numbers, underscore only)";
    }
    return errors;
  };

  const handleSaveProfile = async () => {
    const errors = validateProfile();
    if (Object.keys(errors).length > 0) {
      setProfileErrors(errors);
      return;
    }
    setProfileErrors({});
    setIsLoading(true);
    try {
      await onSaveProfile(formData);
      onClose();
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      const errorMessage = error.message || "Failed to update profile";

      // Detect which field caused the error and show error on correct field
      if (errorMessage.toLowerCase().includes("username")) {
        setProfileErrors({ username: errorMessage });
      } else if (errorMessage.toLowerCase().includes("email")) {
        setProfileErrors({ firstName: "Email cannot be changed" });
      } else {
        setProfileErrors({ firstName: errorMessage });
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleChangePassword = async () => {
    setPasswordError("");
    setPasswordSuccess("");

    if (!passwordData.currentPassword.trim()) {
      setPasswordError("Current password is required");
      return;
    }

    const strength = validatePassword(passwordData.newPassword);
    if (strength === "weak") {
      setPasswordError(
        "Password must be at least 8 characters with uppercase, lowercase, and numbers",
      );
      return;
    }

    if (passwordData.newPassword !== passwordData.confirmPassword) {
      setPasswordError("New passwords do not match");
      return;
    }

    if (passwordData.currentPassword === passwordData.newPassword) {
      setPasswordError("New password must be different from current password");
      return;
    }

    setIsLoading(true);
    try {
      await onChangePassword(
        passwordData.currentPassword,
        passwordData.newPassword,
      );
      setPasswordSuccess("Password changed successfully!");
      setPasswordData({
        currentPassword: "",
        newPassword: "",
        confirmPassword: "",
      });
      setPasswordStrength(null);
      setTimeout(() => {
        setPasswordSuccess("");
      }, 3000);
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      setPasswordError(error.message || "Failed to change password");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black/50 z-60 flex items-end md:items-center justify-center p-4">
      <div className="bg-card w-full md:max-w-md rounded-t-2xl md:rounded-2xl p-4 md:p-6 space-y-4 max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-bold text-foreground">
            Account Settings
          </h2>
          <button
            onClick={onClose}
            className="p-2 hover:bg-secondary rounded-lg transition"
            aria-label="Close modal"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Tabs */}
        <div className="flex gap-2 border-b border-border">
          <button
            onClick={() => setActiveTab("profile")}
            className={`px-4 py-2 font-medium text-sm transition border-b-2 -mb-px ${
              activeTab === "profile"
                ? "text-primary border-primary"
                : "text-muted-foreground border-transparent hover:text-foreground"
            }`}
          >
            Profile
          </button>
          <button
            onClick={() => setActiveTab("password")}
            className={`px-4 py-2 font-medium text-sm transition border-b-2 -mb-px ${
              activeTab === "password"
                ? "text-primary border-primary"
                : "text-muted-foreground border-transparent hover:text-foreground"
            }`}
          >
            Password
          </button>
        </div>

        {/* Profile Tab */}
        {activeTab === "profile" && (
          <div className="space-y-4">
            <div>
              <label className="text-sm font-medium text-foreground">
                First Name
              </label>
              <input
                type="text"
                value={formData.firstName}
                onChange={(e) => {
                  setFormData({ ...formData, firstName: e.target.value });
                  setProfileErrors({ ...profileErrors, firstName: undefined });
                }}
                className={`w-full mt-1 px-4 py-2 bg-input rounded-lg border transition focus:outline-none focus:ring-2 ${
                  profileErrors.firstName
                    ? "border-destructive focus:ring-destructive"
                    : "border-border focus:ring-primary"
                }`}
              />
              {profileErrors.firstName && (
                <p className="text-xs text-destructive mt-1">
                  {profileErrors.firstName}
                </p>
              )}
            </div>

            <div>
              <label className="text-sm font-medium text-foreground">
                Last Name
              </label>
              <input
                type="text"
                value={formData.lastName}
                onChange={(e) => {
                  setFormData({ ...formData, lastName: e.target.value });
                  setProfileErrors({ ...profileErrors, lastName: undefined });
                }}
                className={`w-full mt-1 px-4 py-2 bg-input rounded-lg border transition focus:outline-none focus:ring-2 ${
                  profileErrors.lastName
                    ? "border-destructive focus:ring-destructive"
                    : "border-border focus:ring-primary"
                }`}
              />
              {profileErrors.lastName && (
                <p className="text-xs text-destructive mt-1">
                  {profileErrors.lastName}
                </p>
              )}
            </div>

            <div>
              <label className="text-sm font-medium text-foreground">
                Username
              </label>
              <input
                type="text"
                value={formData.username}
                onChange={(e) => {
                  setFormData({ ...formData, username: e.target.value });
                  setProfileErrors({ ...profileErrors, username: undefined });
                }}
                className={`w-full mt-1 px-4 py-2 bg-input rounded-lg border transition focus:outline-none focus:ring-2 ${
                  profileErrors.username
                    ? "border-destructive focus:ring-destructive"
                    : "border-border focus:ring-primary"
                }`}
              />
              {profileErrors.username && (
                <p className="text-xs text-destructive mt-1">
                  {profileErrors.username}
                </p>
              )}
            </div>

            <div>
              <label className="text-sm font-medium text-foreground">
                Email
              </label>
              <input
                type="email"
                value={formData.email}
                disabled
                className="w-full mt-1 px-4 py-2 bg-secondary rounded-lg border border-border text-muted-foreground cursor-not-allowed"
              />
              <p className="text-xs text-muted-foreground mt-1">
                Email cannot be changed
              </p>
            </div>

            <div className="flex gap-3 pt-4">
              <button
                onClick={onClose}
                disabled={isLoading}
                className="flex-1 px-4 py-2 bg-secondary text-foreground rounded-lg font-medium hover:opacity-80 transition disabled:opacity-50"
              >
                Cancel
              </button>
              <button
                onClick={handleSaveProfile}
                disabled={isLoading}
                className="flex-1 px-4 py-2 bg-primary text-primary-foreground rounded-lg font-medium hover:opacity-90 transition disabled:opacity-50"
              >
                {isLoading ? "Saving..." : "Save Changes"}
              </button>
            </div>
          </div>
        )}

        {/* Password Tab */}
        {activeTab === "password" && (
          <div className="space-y-4">
            <div>
              <label className="text-sm font-medium text-foreground">
                Current Password
              </label>
              <input
                type="password"
                value={passwordData.currentPassword}
                onChange={(e) =>
                  setPasswordData({
                    ...passwordData,
                    currentPassword: e.target.value,
                  })
                }
                placeholder="Enter your current password"
                className="w-full mt-1 px-4 py-2 bg-input rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-primary transition"
              />
            </div>

            <div>
              <label className="text-sm font-medium text-foreground">
                New Password
              </label>
              <input
                type="password"
                value={passwordData.newPassword}
                onChange={(e) => {
                  const value = e.target.value;
                  setPasswordData({ ...passwordData, newPassword: value });
                  if (value) {
                    setPasswordStrength(validatePassword(value));
                  } else {
                    setPasswordStrength(null);
                  }
                }}
                placeholder="Enter new password"
                className="w-full mt-1 px-4 py-2 bg-input rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-primary transition"
              />
              {passwordStrength && (
                <div className="mt-2 space-y-1">
                  <div className="flex gap-1 h-1.5">
                    <div
                      className={`flex-1 rounded-full ${
                        passwordStrength ? "bg-destructive" : "bg-border"
                      }`}
                    />
                    <div
                      className={`flex-1 rounded-full ${
                        passwordStrength === "strong" ||
                        passwordStrength === "medium"
                          ? "bg-yellow-500"
                          : "bg-border"
                      }`}
                    />
                    <div
                      className={`flex-1 rounded-full ${
                        passwordStrength === "strong"
                          ? "bg-primary"
                          : "bg-border"
                      }`}
                    />
                  </div>
                  <p
                    className={`text-xs font-medium ${
                      passwordStrength === "strong"
                        ? "text-primary"
                        : passwordStrength === "medium"
                          ? "text-yellow-600"
                          : "text-destructive"
                    }`}
                  >
                    Password strength: {passwordStrength}
                  </p>
                  <p className="text-xs text-muted-foreground">
                    Use 8+ characters with uppercase, lowercase, and numbers
                  </p>
                </div>
              )}
            </div>

            <div>
              <label className="text-sm font-medium text-foreground">
                Confirm Password
              </label>
              <input
                type="password"
                value={passwordData.confirmPassword}
                onChange={(e) =>
                  setPasswordData({
                    ...passwordData,
                    confirmPassword: e.target.value,
                  })
                }
                placeholder="Confirm new password"
                className="w-full mt-1 px-4 py-2 bg-input rounded-lg border border-border focus:outline-none focus:ring-2 focus:ring-primary transition"
              />
            </div>

            {passwordError && (
              <div className="p-3 bg-destructive/10 border border-destructive/20 rounded-lg">
                <p className="text-sm text-destructive font-medium">
                  {passwordError}
                </p>
              </div>
            )}

            {passwordSuccess && (
              <div className="p-3 bg-primary/10 border border-primary/20 rounded-lg">
                <p className="text-sm text-primary font-medium">
                  {passwordSuccess}
                </p>
              </div>
            )}

            <div className="flex gap-3 pt-4">
              <button
                onClick={onClose}
                disabled={isLoading}
                className="flex-1 px-4 py-2 bg-secondary text-foreground rounded-lg font-medium hover:opacity-80 transition disabled:opacity-50"
              >
                Cancel
              </button>
              <button
                onClick={handleChangePassword}
                disabled={isLoading}
                className="flex-1 px-4 py-2 bg-primary text-primary-foreground rounded-lg font-medium hover:opacity-90 transition disabled:opacity-50"
              >
                {isLoading ? "Updating..." : "Update Password"}
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
