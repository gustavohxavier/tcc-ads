# Feature Specification: iptables-dragdrop

**Feature Branch**: `001-iptables-dragdrop`

**Created**: 2026-06-14

**Status**: Draft

**Input**: User description: "construa uma aplicação que ajude a criar um script de firewall iptables. Deverá ser do tipo drag and drop. As regras criadas devem apenas gerar filtragem de pacotes. A aplicação deverá ter um filtro para visualizar as regras que estão sendo montadas e ao final deverá gerar um script de firewall iptables com as regras de filtragem de pacotes gerenciadas pelo usuário."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Criar regra básica (Priority: P1)

Administrador cria regras por arrastar componentes (ex.: origem, destino, porta, protocolo, ação) para uma área de montagem.

**Why this priority**: entrega valor imediato — permite montar política de filtragem básica.

**Independent Test**: montar regra "aceitar TCP da porta 22 de qualquer origem" e gerar script; executar o script em ambiente Linux (com iptables) deve aplicar a regra sem erros.

**Acceptance Scenarios**:
1. **Given** editor vazio, **When** o usuário arrasta um bloco "regra" com ação `ACCEPT` e define protocolo `tcp` e porta `22`, **Then** a pré-visualização mostra a regra formatada e o contador de regras incrementa.
2. **Given** regras no painel de montagem, **When** o usuário clica "Gerar script", **Then** é produzido um script textual contendo comandos `iptables` correspondentes.

---

### User Story 2 - Ordenação e edição de regras (Priority: P2)

Usuário reordena regras por drag-and-drop e edita campos existentes.

**Independent Test**: criar duas regras conflitantes, inverter a ordem e observar mudança na pré-visualização e no script gerado.

**Acceptance Scenarios**:
1. **Given** duas regras A e B, **When** o usuário move B acima de A, **Then** o script gerado reflete a nova ordem.

---

### User Story 3 - Filtro/visualização (Priority: P2)

Usuário aplica filtros para ver apenas regras que correspondem a critérios (ex.: só `DROP`, só `porta 80`).

**Independent Test**: aplicar filtro "DROP" e verificar que apenas regras com ação `DROP` aparecem na lista de montagem.

**Acceptance Scenarios**:
1. **Given** várias regras, **When** o usuário aplica filtro por protocolo `udp`, **Then** apenas regras UDP são exibidas.

---

### Edge Cases

- Validação de entradas inválidas (IP mal formado, porta fora de intervalo) — devem exibir erro de validação e impedir inclusão.
- Regras duplicadas — sistema deve alertar e permitir confirmação antes de duplicar comandos no script.
- Limite de regras — se houver um limite prático (ex.: performance), notificar usuário.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: A aplicação MUST permitir criar regras de filtragem de pacotes via interface drag-and-drop.
- **FR-002**: A aplicação MUST permitir definir campos de regra: origem (IP/CIDR), destino (IP/CIDR), protocolo (tcp/udp/icmp/all), porta origem, porta destino, interface de entrada/saída (opcional) e ação (ACCEPT/DROP/REJECT).
- **FR-003**: A aplicação MUST permitir ordenar, editar e remover regras da lista de montagem.
- **FR-004**: A aplicação MUST fornecer um painel de pré-visualização com filtro por atributo (ação, protocolo, porta, IP).
- **FR-005**: A aplicação MUST gerar um script shell contendo comandos `iptables` (apenas filtragem de pacotes) que representa as regras na ordem definida pelo usuário.
- **FR-006**: A aplicação MUST validar entradas (endereços IP, portas) e mostrar erros claros.
- **FR-007**: A aplicação MUST garantir que não são geradas regras que executem NAT, mangle ou alterações de tabelas além da filtragem (apenas `filter` table).
- **FR-008**: A aplicação MUST permitir exportar o script gerado como arquivo `.sh` e copiar para a área de transferência.

### Key Entities

- **Regra**: atributos — id, origem, destino, protocolo, porta_origem, porta_destino, interface_in, interface_out, ação, comentários.
- **Conjunto de Regras**: lista ordenada de `Regra` que será traduzida em script.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Usuário consegue criar e gerar um script para uma regra simples (ex.: aceitar ssh) em menos de 3 minutos.
- **SC-002**: O script gerado passa sem erros quando aplicado em uma máquina Linux com `iptables` instalado (verificável em ambiente de teste).
- **SC-003**: 95% das operações de criação/edição/reordenação devem refletir a pré-visualização em menos de 250ms (sensação de responsividade aceitável).
- **SC-004**: Usuários conseguem filtrar a lista por atributo e ver apenas regras correspondentes (funcionalidade presente e testável).

## Assumptions

- Alvo primário: administradores de sistema com acesso para aplicar scripts `iptables` em hosts Linux.
- A ferramenta NÃO gerencia execução remota; apenas gera scripts (o usuário é responsável por aplicar os scripts no sistema alvo).
- Uso de `iptables` clássico (não incluindo nftables) é aceitável para o escopo inicial.
- Persistência de regras pode ser local (arquivo JSON) ou via export — implementação fica para plano técnico.
