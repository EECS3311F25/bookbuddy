import { ChevronRight } from "lucide-react";

interface SettingsItemProps {
  icon: React.ReactNode;
  label: string;
  value?: string;
  onClick?: () => void;
  children?: React.ReactNode;
  isClickable?: boolean;
}

export function SettingsItem({
  icon,
  label,
  value,
  onClick,
  children,
  isClickable = true,
}: SettingsItemProps) {
  return (
    <div
      onClick={onClick}
      className={`p-4 flex items-center justify-between ${
        isClickable && onClick
          ? "cursor-pointer hover:bg-secondary/50 transition"
          : ""
      }`}
    >
      <div className="flex items-center gap-4 flex-1 min-w-0">
        <div className="w-10 h-10 bg-accent/20 rounded-lg flex items-center justify-center flex-shrink-0">
          {icon}
        </div>
        <div className="flex-1 min-w-0">
          <p className="font-medium text-foreground">{label}</p>
          {value && (
            <p className="text-xs text-muted-foreground truncate">{value}</p>
          )}
        </div>
        {children}
      </div>
      {isClickable && onClick && (
        <ChevronRight className="w-5 h-5 text-muted-foreground ml-2 flex-shrink-0" />
      )}
    </div>
  );
}
