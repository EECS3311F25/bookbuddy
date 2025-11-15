import AppShell from "../components/layout/AppShell";

export default function LibraryPage() {
  return (
    <AppShell>
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold text-foreground mb-6">Library</h1>
        <p className="text-muted-foreground">
          Your library will display books organized into Currently Reading,
          Read, and Want to Read tabs, with genre grouping.
        </p>
      </div>
    </AppShell>
  );
}
