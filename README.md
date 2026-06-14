# tcc-ads

Repositório do projeto de conclusão de curso ADS (Análise e Desenvolvimento de Sistemas).

## Recursos

- **iptables-dragdrop**: Uma aplicação desktop Java/JavaFX multiplataforma para construir regras de firewall `iptables` usando drag-and-drop
  - Construtor de regras com drag-and-drop e pré-visualização em tempo real
  - Filtragem avançada por ação, protocolo, porta ou IP
  - Exportação para arquivo `.sh` ou área de transferência
  - Validação de entrada e detecção de duplicatas

## Estrutura do Projeto

```
specs/
├── 001-iptables-dragdrop/
│   ├── spec.md                 # Especificação do recurso
│   ├── plan.md                 # Plano de implementação
│   ├── data-model.md           # Modelo de dados e entidades
│   ├── contracts/              # Contratos de API/exportação
│   ├── research.md             # Pesquisa técnica e decisões
│   ├── quickstart.md           # Guia de início rápido
│   ├── tasks.md                # Detalhamento das tarefas de implementação
│   └── checklists/             # Checklists de qualidade
src/
├── main/java/app/
│   ├── Main.java               # Ponto de entrada da aplicação
│   ├── model/                  # Entidades de domínio (Rule, RuleSet)
│   ├── service/                # Lógica de negócio (Validator, Generator)
│   └── ui/                     # Visualizações JavaFX
└── test/java/app/              # Testes unitários
```

## Requisitos

- **Java 21+** (Java 17+ mínimo)
- **JavaFX 21.0.0+**
- **Gradle 8.0+**

## Compilando e Executando

### Pré-requisitos

```bash
# Verifique se Java está instalado
java -version

# Instale Gradle (macOS com Homebrew)
brew install gradle

# Ou via gerenciador de pacotes do seu sistema
```

### Comandos de Compilação

```bash
# Compilar o projeto
gradle build

# Executar a aplicação
gradle run

# Executar testes
gradle test

# Limpar artefatos de compilação
gradle clean
```

## Status do Código

### ✅ Concluído

- [x] Configuração do projeto com Gradle e dependências JavaFX
- [x] Modelo de domínio principal: classes `Rule` e `RuleSet`
- [x] Lógica de negócio: `RuleValidator` e `IptablesScriptGenerator`
- [x] UI do construtor de regras com criação drag-and-drop
- [x] Painel de pré-visualização com controles de filtragem
- [x] Diálogo de exportação com cópia para área de transferência e salvamento em arquivo
- [x] Operações de reordenação e remoção de regras
- [x] Testes unitários para camadas de modelo e serviço

### ⏳ Em Progresso

- Documentação e exemplos de início rápido
- Refatoração SOLID e revisão de código
- Execução completa da suíte de testes via Gradle
- Compilação JavaFX com configuração adequada de módulos

## Notas de Implementação

- Segue a [constituição](`.specify/memory/constitution.md`) do projeto para código limpo e princípios SOLID
- Toda lógica de negócio é independente do framework de UI
- Regras são armazenadas em memória; exporte para arquivos para persistência
- Scripts gerados usam apenas a tabela `filter` do `iptables`; sem tabelas NAT/mangle/raw

## Documentação Relacionada

- **Especificação do Recurso**: [specs/001-iptables-dragdrop/spec.md](specs/001-iptables-dragdrop/spec.md)
- **Plano de Implementação**: [specs/001-iptables-dragdrop/plan.md](specs/001-iptables-dragdrop/plan.md)
- **Modelo de Dados**: [specs/001-iptables-dragdrop/data-model.md](specs/001-iptables-dragdrop/data-model.md)
- **Contrato de Exportação**: [specs/001-iptables-dragdrop/contracts/iptables-export-contract.md](specs/001-iptables-dragdrop/contracts/iptables-export-contract.md)
