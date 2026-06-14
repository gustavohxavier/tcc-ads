# tcc-ads

Repositório do projeto de conclusão de curso ADS (Análise e Desenvolvimento de Sistemas).

## Features

- **iptables-dragdrop**: A cross-platform Java/JavaFX desktop application for building `iptables` firewall rules using drag-and-drop
  - Drag-and-drop rule builder with real-time preview
  - Advanced filtering by action, protocol, port, or IP
  - Export to `.sh` file or clipboard
  - Input validation and duplicate detection

## Project Structure

```
specs/
├── 001-iptables-dragdrop/
│   ├── spec.md                 # Feature specification
│   ├── plan.md                 # Implementation plan
│   ├── data-model.md           # Data model and entities
│   ├── contracts/              # API/export contracts
│   ├── research.md             # Technical research and decisions
│   ├── quickstart.md           # User quickstart guide
│   ├── tasks.md                # Implementation task breakdown
│   └── checklists/             # Quality checklists
src/
├── main/java/app/
│   ├── Main.java               # Application entry point
│   ├── model/                  # Domain entities (Rule, RuleSet)
│   ├── service/                # Business logic (Validator, Generator)
│   └── ui/                     # JavaFX views
└── test/java/app/              # Unit tests
```

## Requirements

- **Java 21+** (Java 17+ minimum)
- **JavaFX 21.0.0+**
- **Gradle 8.0+**

## Building and Running

### Prerequisites

```bash
# Verify Java is installed
java -version

# Install Gradle (macOS with Homebrew)
brew install gradle

# Or via your system package manager
```

### Build Commands

```bash
# Build the project
gradle build

# Run the application
gradle run

# Run tests
gradle test

# Clean build artifacts
gradle clean
```

## Code Status

### ✅ Completed

- [x] Project setup with Gradle and JavaFX dependencies
- [x] Core domain model: `Rule` and `RuleSet` classes
- [x] Business logic: `RuleValidator` and `IptablesScriptGenerator`
- [x] Rule builder UI with drag-and-drop creation
- [x] Preview pane with filtering controls
- [x] Export dialog with clipboard and file save
- [x] Reorder and remove rule operations
- [x] Unit tests for model and service layers

### ⏳ In Progress

- Documentation and quickstart examples
- SOLID refactoring and code review
- Full test suite execution via Gradle
- JavaFX compilation with proper module setup

## Implementation Notes

- Follows the project [constitution](`.specify/memory/constitution.md`) for clean code and SOLID principles
- All business logic is independent of UI framework
- Rules are stored in memory; export to files for persistence
- Generated scripts use `iptables` filter table only; no NAT/mangle/raw tables

## Related Documentation

- **Feature Spec**: [specs/001-iptables-dragdrop/spec.md](specs/001-iptables-dragdrop/spec.md)
- **Implementation Plan**: [specs/001-iptables-dragdrop/plan.md](specs/001-iptables-dragdrop/plan.md)
- **Data Model**: [specs/001-iptables-dragdrop/data-model.md](specs/001-iptables-dragdrop/data-model.md)
- **Export Contract**: [specs/001-iptables-dragdrop/contracts/iptables-export-contract.md](specs/001-iptables-dragdrop/contracts/iptables-export-contract.md)
