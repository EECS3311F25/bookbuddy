interface UserAvatarProps {
  firstName: string;
  lastName: string;
  email: string;
  size?: "sm" | "md" | "lg";
}

export function UserAvatar({
  firstName,
  lastName,
  size = "md",
}: UserAvatarProps) {
  const initials = `${firstName.charAt(0)}${lastName.charAt(0)}`.toUpperCase();

  const sizeClasses = {
    sm: "w-8 h-8 text-xs",
    md: "w-10 h-10 text-sm",
    lg: "w-16 h-16 text-xl",
  };

  return (
    <div
      className={`${sizeClasses[size]} bg-primary rounded-full flex items-center justify-center text-primary-foreground font-bold flex-shrink-0`}
    >
      {initials}
    </div>
  );
}
