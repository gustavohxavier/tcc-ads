# Data Model: iptables-dragdrop

## Core Entities

### Regra
Represents a single packet filtering rule.

- `id`: unique identifier for the rule instance
- `origem`: source IP or CIDR range
- `destino`: destination IP or CIDR range
- `protocolo`: `tcp`, `udp`, `icmp`, or `all`
- `portaOrigem`: optional source port or port range
- `portaDestino`: optional destination port or port range
- `interfaceEntrada`: optional network interface name for incoming packets
- `interfaceSaida`: optional network interface name for outgoing packets
- `acao`: `ACCEPT`, `DROP`, or `REJECT`
- `comentario`: optional user note
- `posicao`: order index in the rule list

### Conjunto de Regras
Represents the ordered set of rules managed by the user.

- `regras`: ordered list of `Regra`
- `filtrosAtivos`: active filter criteria used for preview
- `dataHoraCriacao`: timestamp for session state (optional)

## Validation Rules

- IP/CIDR fields must be syntactically valid or empty for "any".
- Protocol must be one of the supported values: `tcp`, `udp`, `icmp`, `all`.
- Port values must be in the range 1-65535 or valid port ranges.
- If `all` protocol is selected, port fields may be ignored or disabled.
- Interfaces are optional and may be empty.
- Exact duplicate rules should be detected and require confirmation before adding.

## Preview and Filtering

### Filtro de visualização
- `acao`: filter by `ACCEPT`, `DROP`, `REJECT`, or show all.
- `protocolo`: filter by protocol or show all.
- `porta`: filter by source or destination port.
- `ip`: filter by source or destination address.

### Preview State
- `regrasFiltradas`: subset of `regras` matching `filtrosAtivos`
- `textoPrevia`: generated textual representation of `regrasFiltradas`

## Behavior

- Adding a rule transitions it from draft state into the ordered `Conjunto de Regras`.
- Reordering updates `posicao` and the generated script order.
- Editing a rule updates the corresponding entity and refreshes preview.
- Exporting produces a linear translation of the current `regras` order to iptables command lines.
