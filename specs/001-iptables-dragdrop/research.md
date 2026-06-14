# Research: iptables-dragdrop

## Decision: Java desktop application with JavaFX

- Chosen approach: build a cross-platform desktop app using Java (JVM) and JavaFX for the UI.
- Rationale: Java meets the requirement that the executable can run on any OS with a JVM, while JavaFX provides the drag-and-drop and GUI capabilities needed for rule composition.
- Alternatives considered:
  - Electron/web app: cross-platform but would require bundling a browser runtime and a heavier distribution.
  - Native desktop app per OS: unnecessary complexity when JVM portability is available.

## Decision: Export-only script generation

- Chosen approach: application generates an iptables script file and optionally copies the generated content to clipboard.
- Rationale: the feature must only create filtering rules; it should not apply firewall changes directly.
- Alternatives considered:
  - running iptables commands directly from the app: rejected because it would require root privileges and break the "script generation only" requirement.

## Decision: Use `filter` table only

- Chosen approach: restrict output to the default `filter` table and standard chains, with no NAT/mangle or raw modifications.
- Rationale: this matches the user requirement that rules should only generate packet filtering, avoiding unrelated firewall features.

## Technology and UX

- Primary UI model: drag-and-drop rule builder panels plus a filterable preview pane.
- Export options: copy text to clipboard, generate `.txt` file, optionally generate a `.sh` file if the implementation chooses to include it.
- The app will keep rules in-memory during the session and provide local export; persistent storage is not required for MVP.
