interface SettingsSectionProps {
  title: string;
  children: React.ReactNode;
}

export function SettingsSection({ title, children }: SettingsSectionProps) {
  return (
    <div className="space-y-2">
      <h3 className="text-sm font-semibold text-muted-foreground uppercase tracking-wide px-1">
        {title}
      </h3>
      <div className="bg-card rounded-2xl overflow-hidden divide-y divide-border shadow-sm">
        {children}
      </div>
    </div>
  );
}
