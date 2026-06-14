# Contract: iptables Export Output

## Purpose

Define the contract for the text output produced by the iptables drag-and-drop application.

## Input

- Ordered list of user-created rules.
- Each rule includes:
  - origem
  - destino
  - protocolo
  - portaOrigem
  - portaDestino
  - interfaceEntrada
  - interfaceSaida
  - acao
  - comentario (optional)

## Output

- A plain text file with one `iptables` command per rule.
- Output may be saved with a `.txt` extension or copied to clipboard.
- The order of commands must match the current rule order in the UI.
- Only the `filter` table is used.

## Format

- Each command begins with `iptables`.
- Standard flags: `-A`, `-p`, `-s`, `-d`, `--sport`, `--dport`, `-i`, `-o`, `-j`.
- Example:
  - `iptables -A INPUT -s 192.168.1.0/24 -d 10.0.0.1 -p tcp --dport 22 -j ACCEPT`

## Guarantees

- No `nat`, `mangle`, or `raw` table commands are generated.
- No direct firewall application occurs from the app.
- The exported file is text only and safe to open in any editor.

## Error handling

- Invalid rules are not included in output until corrected.
- The app must prevent save/copy actions if the rule set contains invalid entries.
