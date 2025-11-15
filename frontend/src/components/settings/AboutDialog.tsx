import { X } from "lucide-react";
import { config } from "../../config";

interface AboutDialogProps {
  isOpen: boolean;
  onClose: () => void;
}

export function AboutDialog({ isOpen, onClose }: AboutDialogProps) {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 z-60 flex items-end md:items-center justify-center p-4">
      <div className="bg-card w-full md:max-w-md rounded-t-2xl md:rounded-2xl p-4 md:p-6 space-y-4 max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between">
          <h2 className="text-xl font-bold text-foreground">
            About {config.appName}
          </h2>
          <button
            onClick={onClose}
            className="p-2 hover:bg-secondary rounded-lg transition"
            aria-label="Close modal"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <div className="space-y-4 text-foreground text-sm leading-relaxed">
          <div>
            <h3 className="font-semibold mb-2">About {config.appName}</h3>
            <p className="text-muted-foreground">
              {config.appName} is a simple personal reading tracker designed to
              help you organize your book collection, track what you've read,
              and maintain consistent reading habits.
            </p>
          </div>

          <div>
            <h3 className="font-semibold mb-2">Our Mission</h3>
            <p className="text-muted-foreground">
              We believe readers deserve a clean, personal tracking solution
              without the clutter of social features. {config.appName}
              prioritizes speed and simplicity, allowing you to add a book in
              under 20 seconds and see your reading progress at a glance.
            </p>
          </div>

          <div>
            <h3 className="font-semibold mb-2">Key Features</h3>
            <ul className="text-muted-foreground space-y-1">
              <li>• Organize books into personal shelves</li>
              <li>• Rate and review your reads</li>
              <li>• Track annual reading goals</li>
              <li>• Search and add books easily</li>
              <li>• Seamless mobile and desktop experience</li>
            </ul>
          </div>

          <div>
            <h3 className="font-semibold mb-2">Made By</h3>
            <p className="text-muted-foreground">
              {config.appName} was created by
              <span className="font-medium text-foreground"> OJ Adeyemi </span>
              and{" "}
              <span className="font-medium text-foreground">
                Sanae Faghfouri
              </span>
              .
            </p>
          </div>

          <div className="pt-2 border-t border-border">
            <p className="text-xs text-muted-foreground text-center">
              {config.appName} {config.appVersion}
            </p>
          </div>
        </div>

        <button
          onClick={onClose}
          className="w-full px-4 py-2 bg-primary text-primary-foreground rounded-lg font-medium hover:opacity-90 transition"
        >
          Close
        </button>
      </div>
    </div>
  );
}
