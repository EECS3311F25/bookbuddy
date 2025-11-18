import AppShell from "@/components/layout/AppShell";

export default function HomePage() {
  return (
    <AppShell>
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-foreground mb-4">Home</h1>
          <p className="text-muted-foreground">Coming soon...</p>
        </div>
      </div>
    </AppShell>
  );
}
