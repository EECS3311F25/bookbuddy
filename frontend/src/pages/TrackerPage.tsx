import AppShell from "../components/layout/AppShell";

export default function TrackerPage() {
  return (
    <AppShell>
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-foreground mb-6">
          Monthly Tracker
        </h1>
        <p className="text-muted-foreground">
          Track your monthly reading goals and see your completion progress
          here.
        </p>
      </div>
    </AppShell>
  );
}
