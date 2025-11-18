import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Bell, Info, LogOut, Trash2 } from "lucide-react";
import AppShell from "@/components/layout/AppShell";
import { UserAvatar } from "@/components/common/UserAvatar";
import { ToggleSwitch } from "@/components/common/ToggleSwitch";
import { SettingsSection } from "@/components/settings/SettingsSection";
import { SettingsItem } from "@/components/settings/SettingsItem";
import { ProfileModal } from "@/components/settings/ProfileModal";
import { AboutDialog } from "@/components/settings/AboutDialog";
import { DeleteConfirmDialog } from "@/components/settings/DeleteConfirmDialog";
import { usersService } from "@/services";
import { useAuth } from "@/contexts/useAuth";

export default function SettingsPage() {
  const navigate = useNavigate();
  const { user, setUser, logout } = useAuth();
  const [notifications, setNotifications] = useState(true);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isAboutOpen, setIsAboutOpen] = useState(false);
  const [isDeleteConfirmOpen, setIsDeleteConfirmOpen] = useState(false);
  const [isDeleting, setIsDeleting] = useState(false);

  // Redirect to login if not authenticated
  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  // Load preferences on mount
  useEffect(() => {
    const notificationsStored = localStorage.getItem("bookbuddy_notifications");
    if (notificationsStored !== null) {
      setNotifications(JSON.parse(notificationsStored));
    }
  }, []);

  // Save profile updates to backend
  const handleSaveProfile = async (data: {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
  }) => {
    if (!user) return;

    try {
      // Call backend API to update user
      const updatedUser = await usersService.updateUser(user.id, {
        firstName: data.firstName,
        lastName: data.lastName,
        username: data.username,
        email: data.email,
        password: "", // Password not changed here
      });

      // Update auth context (will auto-sync to localStorage)
      setUser(updatedUser);
    } catch (error) {
      const err = error as { response?: { data?: string } };
      throw new Error(err.response?.data || "Failed to update profile");
    }
  };

  // Change password
  const handleChangePassword = async (
    currentPassword: string,
    newPassword: string,
  ) => {
    if (!user) return;

    try {
      // First verify current password by attempting login
      await usersService.login({
        usernameOrEmail: user.email,
        password: currentPassword,
      });

      // Update user with new password
      await usersService.updateUser(user.id, {
        firstName: user.firstName,
        lastName: user.lastName,
        username: user.username,
        email: user.email,
        password: newPassword,
      });
    } catch (error) {
      const err = error as { response?: { status?: number; data?: string } };
      if (err.response?.status === 401) {
        throw new Error("Current password is incorrect");
      }
      throw new Error(err.response?.data || "Failed to change password");
    }
  };

  // Toggle notifications preference
  const handleNotificationsToggle = (checked: boolean) => {
    setNotifications(checked);
    localStorage.setItem("bookbuddy_notifications", JSON.stringify(checked));
  };

  // Logout
  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  // Delete account
  const handleDeleteAccount = async () => {
    if (!user) return;

    setIsDeleting(true);
    try {
      await usersService.deleteUser(user.id);
      logout();
      navigate("/login");
    } catch (error) {
      console.error("Failed to delete account:", error);
      setIsDeleting(false);
      setIsDeleteConfirmOpen(false);
    }
  };

  if (!user) {
    return null;
  }

  return (
    <AppShell>
      <div className="bg-background pb-8">
        {/* Content */}
        <div className="max-w-2xl mx-auto px-4 md:px-6 py-6 space-y-5">
          {/* Profile Section */}
          <SettingsSection title="PROFILE">
            <SettingsItem
              icon={
                <UserAvatar
                  firstName={user.firstName}
                  lastName={user.lastName}
                  email={user.email}
                  size="sm"
                />
              }
              label={`${user.firstName} ${user.lastName}`}
              value={user.email}
              onClick={() => setIsEditModalOpen(true)}
            />
          </SettingsSection>

          {/* Preferences Section */}
          <SettingsSection title="PREFERENCES">
            <SettingsItem
              icon={<Bell className="w-5 h-5 text-accent" />}
              label="Notifications"
              isClickable={false}
            >
              <ToggleSwitch
                checked={notifications}
                onChange={handleNotificationsToggle}
              />
            </SettingsItem>
          </SettingsSection>

          {/* About Section */}
          <SettingsSection title="ABOUT">
            <SettingsItem
              icon={<Info className="w-5 h-5 text-accent" />}
              label="About BookBuddy"
              onClick={() => setIsAboutOpen(true)}
            />
          </SettingsSection>

          {/* Account Section */}
          <SettingsSection title="ACCOUNT">
            <div className="p-4">
              <div className="flex gap-3">
                <button
                  onClick={handleLogout}
                  className="flex-1 px-3 py-2 bg-secondary text-foreground rounded-lg font-medium text-sm hover:opacity-80 transition flex items-center justify-center gap-2"
                >
                  <LogOut className="w-4 h-4" />
                  Logout
                </button>
                <button
                  onClick={() => setIsDeleteConfirmOpen(true)}
                  className="flex-1 px-3 py-2 bg-destructive/10 text-destructive rounded-lg font-medium text-sm hover:bg-destructive/20 transition flex items-center justify-center gap-2"
                >
                  <Trash2 className="w-4 h-4" />
                  Delete
                </button>
              </div>
            </div>
          </SettingsSection>
        </div>
      </div>

      {/* Modals */}
      <ProfileModal
        isOpen={isEditModalOpen}
        onClose={() => setIsEditModalOpen(false)}
        userData={user}
        onSaveProfile={handleSaveProfile}
        onChangePassword={handleChangePassword}
      />
      <AboutDialog isOpen={isAboutOpen} onClose={() => setIsAboutOpen(false)} />
      <DeleteConfirmDialog
        isOpen={isDeleteConfirmOpen}
        onClose={() => setIsDeleteConfirmOpen(false)}
        onConfirm={handleDeleteAccount}
        isLoading={isDeleting}
      />
    </AppShell>
  );
}
