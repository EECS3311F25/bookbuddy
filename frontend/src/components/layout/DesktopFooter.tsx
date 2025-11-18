export default function DesktopFooter() {
  return (
    <footer className="hidden md:block fixed bottom-0 left-0 right-0 bg-card border-t border-border z-40">
      <div className="container mx-auto px-6 h-12 flex items-center justify-center">
        <p className="text-xs text-muted-foreground">
          Made by{" "}
          <span className="font-semibold">
            <a href="https://ojadeyemi.github.io/">OJ Adeyemi</a>
          </span>{" "}
          and
          <span className="font-semibold">
            {" "}
            <a href="https://www.linkedin.com/in/sanae-faghfouri-735848283/">
              Sanae Faghfouri
            </a>
          </span>
        </p>
      </div>
    </footer>
  );
}
