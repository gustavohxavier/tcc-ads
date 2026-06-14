# Tasks: iptables-dragdrop

**Input**: Design documents from `specs/001-iptables-dragdrop/`

**Prerequisites**: `plan.md`, `spec.md`, `data-model.md`, `contracts/`, `quickstart.md`

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Initialize the Java/JavaFX project and prepare the repository structure.

- [ ] T001 Initialize Gradle Java application in the repository root with `build.gradle`, `settings.gradle`, and Gradle wrapper files
- [ ] T002 Create source directories `src/main/java/app/`, `src/main/resources/`, `src/test/java/app/`
- [ ] T003 Add JavaFX and JUnit 5 dependencies to `build.gradle` and configure the `application` plugin
- [ ] T004 [P] Create initial application entry point in `src/main/java/app/Main.java`
- [ ] T005 [P] Create base UI package and empty JavaFX scene files in `src/main/java/app/ui/`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Implement core modeling, validation, and export logic used by all user stories.

- [ ] T006 Create `Rule.java` in `src/main/java/app/model/Rule.java` with fields for origem, destino, protocolo, portaOrigem, portaDestino, interfaceEntrada, interfaceSaida, acao, comentario, and posicao
- [ ] T007 Create `RuleSet.java` in `src/main/java/app/model/RuleSet.java` to manage an ordered list of rules and filtered preview state
- [ ] T008 Implement `RuleValidator.java` in `src/main/java/app/service/RuleValidator.java` with IP/CIDR validation, port range validation, protocol constraints, and duplicate rule detection
- [ ] T009 Implement `IptablesScriptGenerator.java` in `src/main/java/app/service/IptablesScriptGenerator.java` to translate an ordered `RuleSet` into plain text `iptables` filter commands
- [ ] T010 [P] Create unit tests for rule modeling and validation in `src/test/java/app/model/RuleTest.java` and `src/test/java/app/model/RuleSetTest.java`
- [ ] T011 [P] Create unit tests for script generation in `src/test/java/app/service/IptablesScriptGeneratorTest.java`
- [ ] T012 [P] Create unit tests for validation rules in `src/test/java/app/service/RuleValidatorTest.java`

---

## Phase 3: User Story 1 - Criar regra básica (Priority: P1) 🎯 MVP

**Goal**: Allow users to build a packet-filtering rule by dragging components into a rule editor and generate a preview and script output.

**Independent Test**: Create a rule with `ACCEPT`, protocol `tcp`, destination port `22`, verify preview updates, and generate a script containing the corresponding `iptables` command.

- [ ] T013 [US1] Implement `RuleBuilderView.java` in `src/main/java/app/ui/RuleBuilderView.java` to expose rule component fields and drag-and-drop creation interactions
- [ ] T014 [US1] Implement rule creation logic in `src/main/java/app/ui/RuleBuilderView.java` that builds `Rule` objects from the UI and inserts them into `RuleSet`
- [ ] T015 [US1] Implement `RulePreviewView.java` in `src/main/java/app/ui/RulePreviewView.java` to display formatted rule summaries and a rule counter
- [ ] T016 [US1] Implement the "Gerar script" action in `src/main/java/app/ui/RulePreviewView.java` or `ExportDialog.java` to show generated script text from `IptablesScriptGenerator`
- [ ] T017 [US1] Add a manual verification task in `specs/001-iptables-dragdrop/quickstart.md` for creating a simple SSH accept rule and validating the generated `iptables` command

---

## Phase 4: User Story 2 - Ordenação e edição de regras (Priority: P2)

**Goal**: Allow users to reorder, edit, and remove rules in the assembly area and keep the generated script order consistent.

**Independent Test**: Create two rules, reorder them, and verify the preview and generated script reflect the new order.

- [ ] T018 [US2] Implement drag-and-drop reorder support in `src/main/java/app/ui/RuleBuilderView.java` or the rule list component
- [ ] T019 [US2] Implement edit and remove actions in `src/main/java/app/ui/RuleBuilderView.java` with updates to the underlying `RuleSet`
- [ ] T020 [US2] Ensure `RulePreviewView.java` and `IptablesScriptGenerator.java` respect the updated rule order after reordering
- [ ] T021 [US2] Add a manual independent test note in `specs/001-iptables-dragdrop/quickstart.md` for reorder behavior and script order verification

---

## Phase 5: User Story 3 - Filtro/visualização (Priority: P2)

**Goal**: Give users a filter panel that shows only matching rules in the preview, without changing the underlying ordered rule set.

**Independent Test**: Apply filter `DROP` and verify only drop rules appear in the preview while the rule order remains intact for export.

- [ ] T022 [US3] Implement filter controls in `src/main/java/app/ui/RulePreviewView.java` for action, protocol, port, and IP criteria
- [ ] T023 [US3] Implement preview filtering logic in `src/main/java/app/model/RuleSet.java` or a dedicated preview helper class
- [ ] T024 [US3] Ensure the filtered preview state is shown as `regrasFiltradas` and does not modify the canonical rule order used by script export
- [ ] T025 [US3] Add a manual independent test note in `specs/001-iptables-dragdrop/quickstart.md` for filter behavior and preview validation

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Complete export behavior, input validation UX, documentation, and overall quality.

- [ ] T026 [P] Implement `.sh` export and clipboard copy support in `src/main/java/app/ui/ExportDialog.java`
- [ ] T027 [P] Ensure the generator only uses `iptables` `filter` table commands and rejects any NAT/mangle/raw rules in `src/main/java/app/service/IptablesScriptGenerator.java`
- [ ] T028 [P] Implement validation error messages in `src/main/java/app/ui/RuleBuilderView.java` and ensure invalid rules cannot be exported
- [ ] T029 [P] Update `specs/001-iptables-dragdrop/quickstart.md` with explicit validation and export verification steps
- [ ] T030 [P] Refactor code for SOLID separation between UI, model, service, and export layers in `src/main/java/app/`
- [ ] T031 [P] Apply formatting and code quality checks across source and test files

---

## Dependencies & Execution Order

- Phase 1: Setup must complete first.
- Phase 2: Foundational tasks block all user stories and must complete before Phase 3.
- User Story phases (Phase 3, 4, 5) can start after Phase 2; each story is independently testable.
- Phase 6: Polish & Cross-Cutting concerns should occur after core stories are implemented.

### User Story Completion Order

1. User Story 1 (P1) — MVP and first independently testable delivery
2. User Story 2 (P2) — reorder/edit support
3. User Story 3 (P2) — filter/preview support

### Parallel Opportunities

- `T010`, `T011`, `T012` can run in parallel because they are test coverage tasks on separate files
- `T013` and `T014` can run in parallel if one engineer focuses on UI structure while another builds model binding
- `T018`, `T019`, and `T022` are story-specific but can be developed in parallel once foundational services are ready
- `T026`, `T027`, `T028`, `T029`, `T030`, `T031` can run in parallel as finishing and polish work

## Implementation Strategy

### MVP Scope
- Complete Phase 1 and Phase 2
- Deliver User Story 1 only
- Verify the generated script for a simple SSH rule and confirm preview updates

### Incremental Delivery
1. Finish Setup and Foundational work
2. Deliver User Story 1 and validate independently
3. Add User Story 2 and verify reorder/script consistency
4. Add User Story 3 and verify filter preview behavior
5. Complete polish, export, and validation documentation

### Independent Test Criteria

- US1: Build a simple rule, confirm preview updates, and generate an `iptables` command for SSH
- US2: Reorder two rules, confirm preview/script order changes accordingly
- US3: Filter rules by attribute and confirm preview displays only matching rules while export preserves ordered rules
