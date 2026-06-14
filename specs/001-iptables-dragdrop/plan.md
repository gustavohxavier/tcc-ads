# Implementation Plan: iptables-dragdrop

**Branch**: `feature/develop` | **Date**: 2026-06-14 | **Spec**: specs/001-iptables-dragdrop/spec.md

**Input**: Feature specification from `specs/001-iptables-dragdrop/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Build a cross-platform Java desktop application using the JVM and JavaFX that helps users assemble `iptables` packet-filtering rules through drag-and-drop. The app will provide a filterable preview of the current rule set and export the final rule list as a `.txt` script or copy it to the clipboard.

## Technical Context

**Language/Version**: Java 21+ (JVM)

**Primary Dependencies**: JavaFX for UI; JUnit 5 for testing; Gradle for build and packaging.

**Storage**: In-memory rule model during runtime; export to plain text file for persistence.

**Testing**: JUnit 5 unit tests for rule validation and script generation; manual UI validation for drag-and-drop and preview responsiveness.

**Target Platform**: Desktop cross-platform on any OS with a JVM (Windows, macOS, Linux).

**Project Type**: Desktop GUI application / productivity tool.

**Performance Goals**: Support responsive filtering and preview updates within 250ms for 100 rules; export generation should be near-instant.

**Constraints**: Output only `filter` table commands for packet filtering; do not apply firewall rules directly; generated output must be text-only.

**Scale/Scope**: A session-based rule builder that generates scripts for administrators. No remote execution or persistent database storage required for MVP.

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- The app follows the constitution by keeping the implementation simple, testable, and user-friendly.
- SOLID principles are applied by separating UI, rule modeling, validation, and export logic.
- Tests are required for rule parsing and script generation.
- Simplicity is preserved by limiting functionality to rule assembly and export.

## Project Structure

### Documentation (this feature)

```text
specs/001-iptables-dragdrop/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── iptables-export-contract.md
└── tasks.md
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/
│   │   ├── app/
│   │   │   ├── Main.java
│   │   │   ├── ui/
│   │   │   │   ├── RuleBuilderView.java
│   │   │   │   ├── RulePreviewView.java
│   │   │   │   └── ExportDialog.java
│   │   │   ├── model/
│   │   │   │   ├── Rule.java
│   │   │   │   ├── RuleSet.java
│   │   │   │   └── ExportFormatter.java
│   │   │   └── service/
│   │   │       ├── RuleValidator.java
│   │   │       └── IptablesScriptGenerator.java
│   └── resources/
│       └── application.css
└── test/
    └── java/
        └── app/
            ├── model/
            │   ├── RuleTest.java
            │   └── RuleSetTest.java
            └── service/
                ├── RuleValidatorTest.java
                └── IptablesScriptGeneratorTest.java
```

**Structure Decision**: Use a single Java desktop project rather than separate frontend/backend or native apps, since the requirement is cross-platform JVM execution.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| Drag-and-drop desktop UI | Required to meet the explicit UX requirement | CLI or static form UI would not satisfy drag-and-drop behavior.
| JavaFX dependency | Needed for a portable, interactive GUI across OSes | Swing was older and less suitable for modern UX; Electron would be heavier and less aligned with JVM portability.

## Phase 0: Research

- Confirmed Java/JavaFX is the best fit for cross-platform GUI and JVM portability.
- Confirmed the application will generate only `iptables` `filter` rules and will not execute firewall commands.

## Phase 1: Design & Contracts

- Data model and entities defined in `data-model.md`.
- Export contract defined in `contracts/iptables-export-contract.md`.
- Quickstart usage documented in `quickstart.md`.

## Phase 2: Implementation Strategy

1. Setup Java project with Gradle and JavaFX.
2. Implement rule model, validation, and export generator.
3. Build the drag-and-drop UI and preview pane.
4. Add export options: copy to clipboard and save `.txt`.
5. Add unit tests for validation and export.
6. Validate end-to-end by generating `iptables` text from sample rules.

## Notes

- The plan assumes the user environment has Java 21+ installed.
- The app does not include persistence beyond export file generation.

