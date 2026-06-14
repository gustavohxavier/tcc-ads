# Sync Impact Report
<!--
Version change: template -> 1.0.0

Modified principles:
- [PRINCIPLE_1_NAME] Template -> I. Legibilidade e Clareza (Clean Code)
- [PRINCIPLE_2_NAME] Template -> II. Princípios SOLID
- [PRINCIPLE_3_NAME] Template -> III. Testes e Qualidade (TDD recomendado)
- [PRINCIPLE_4_NAME] Template -> IV. Simplicidade e Experiência do Usuário
- [PRINCIPLE_5_NAME] Template -> V. Observabilidade e Manutenibilidade

Added sections:
- Restrições Técnicas (substitui SECTION_2_NAME)
- Fluxo de Desenvolvimento (substitui SECTION_3_NAME)

Removed sections: none

Templates checked:
- .specify/templates/plan-template.md: ✅ aligned (uses "Constitution Check")
- .specify/templates/spec-template.md: ✅ aligned
- .specify/templates/tasks-template.md: ✅ aligned
- .specify/templates/constitution-template.md: ✅ used to generate this file

Follow-up TODOs:
- None
-->

# tcc-ads Constitution

## Core Principles

### I. Legibilidade e Clareza (Clean Code)
Código deve ser legível por humanos. Nomes de variáveis, funções e classes DEVEM ser descritivos; funções DEVEM ser pequenas e com uma única responsabilidade; comentários SÓ são permitidos quando explicam "porquê", não "o quê"; duplicação DEVEM ser evitada (DRY). Racional: a clareza reduz custo de manutenção e facilita revisão.

### II. Princípios SOLID
- Single Responsibility (SRP): cada módulo/classe DEVEM ter uma responsabilidade única e bem definida.
- Open/Closed (OCP): componentes DEVEM ser abertos para extensão e fechados para modificação.
- Liskov Substitution (LSP): substituição por subtipos NÃO deve quebrar expectativas do cliente.
- Interface Segregation (ISP): interfaces DEVEM ser específicas ao cliente, evitando interfaces "gordas".
- Dependency Inversion (DIP): módulos de alto nível NÃO DEVEM depender de módulos de baixo nível; ambos DEVEM depender de abstrações.
Racional: SOLID mantém o design flexível e testável.

### III. Testes e Qualidade (TDD recomendado)
Testes automatizados DEVEM existir para funcionalidades críticas. Para cada nova função pública ou comportamento: escrever testes que falhem primeiro (recomendado TDD), depois implementar e refatorar. Cobertura mínima orientadora: unidades para regras de negócio, contratos para integrações e testes de aceitação para jornadas principais. Todas as PRs DEVEM passar CI com lint + testes.

### IV. Simplicidade e Experiência do Usuário
O produto/dev API DEVEM ser simples de usar: minimizar superfície pública, fornecer padrões sensatos (convenções e valores padrão), mensagens de erro úteis e documentação mínima adequada (quickstart). Evitar otimizações prematuras e complexidade desnecessária (YAGNI). Racional: usuário final e integradores valorizam previsibilidade e facilidade.

### V. Observabilidade e Manutenibilidade
Instrumentação básica DEVEM existir: logs estruturados, métricas chave e rastreamento quando aplicável. Erros DEVEM ser detectáveis e acionáveis. Versionamento semântico DEVEM ser usado para releases (MAJOR.MINOR.PATCH) e quebrantes SÓ ocorrem em MAJOR com migração documentada.

## Restrições Técnicas
- Padronizar formatação e qualidade: adotar linter e formatter (ex.: ESLint/Prettier, black/flake8, rustfmt/clippy conforme tecnologia).
- CI DEVEM rodar lint, testes e checks de segurança antes de merge.
- Dependências externas DEVEM ser justificadas em PR e atualizadas periodicamente.

## Fluxo de Desenvolvimento
- Branches: `main` para releases, `develop` ou ramas por feature com prefixo `feat/` ou `fix/`.
- Pull Requests DEVEM ter descrição clara, critérios de aceitação e referência a `spec.md`/`plan.md` quando aplicável.
- Revisão: mínimo 1 approver para mudanças triviais, 2 approvers para mudanças de design ou dependências.
- Commits DEVEM ser atômicos e com mensagens claras (ex.: `feat: adicionar validação de CPF`).
- Gate: A etapa "Constitution Check" no `plan-template.md` é obrigatória antes de prosseguir para implementação.

## Governance
Emendas à Constituição DEVEM seguir o processo: 1) abrir PR explicando a mudança e impacto; 2) incluírem um plano de migração se houver breaking changes; 3) aprovação por pelo menos dois mantenedores; 4) documentação atualizada. Versão segue SemVer:
- MAJOR: remoção ou redefinição incompatível de princípios/governança.
- MINOR: adição de princípios ou expansão material.
- PATCH: clarificações, correções de texto e ajustes não estruturais.

**Version**: 1.0.0 | **Ratified**: 2026-06-14 | **Last Amended**: 2026-06-14
