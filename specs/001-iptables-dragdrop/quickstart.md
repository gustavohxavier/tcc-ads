# Quickstart: iptables-dragdrop (PT-BR)

## Pré-requisitos

- Java 21+ instalado no sistema host.
- Runtime JavaFX disponível para a distribuição JVM alvo.
- Um sistema Linux para validar o script `iptables` gerado (opcional para testes locais).

## Executando a aplicação

1. Compilar e executar a aplicação desktop.
   - Com Gradle (quando disponível): `./gradlew run` ou `gradle run`
   - Se houver um JAR empacotado: `java -jar iptables-dragdrop.jar`

2. Na interface da aplicação, crie uma nova regra usando os controles (drag-and-drop ou preenchimento de campos).
   - Informe origem/destino (IP ou CIDR) ou deixe em branco para qualquer origem/destino
   - Selecione o protocolo e portas opcionais
   - Escolha a ação: `ACCEPT`, `DROP` ou `REJECT`

3. Verifique a pré-visualização mostra a regra formatada corretamente.

4. Exporte o script:
   - Copie o conteúdo para a área de transferência, ou
   - Salve em um arquivo `.sh` ou `.txt`.

5. Valide o arquivo exportado (opcional em máquina Linux de teste):
   - Abra o `.sh`/`.txt` e confirme que contém comandos `iptables` do tipo `filter`.
   - Exemplo: `iptables -A INPUT -s 192.168.1.0/24 -d 10.0.0.1 -p tcp --dport 22 -j ACCEPT`

## Passos de verificação manual (T017, T021, T025, T029)

Siga estes passos manuais para validar cada user story e os critérios de aceitação:

- Verificação US1 (T017): regra básica — SSH accept
  1. Adicione uma regra com: Origem `192.168.1.0/24`, Destino `10.0.0.1`, Protocolo `tcp`, Porta Destino `22`, Ação `ACCEPT`.
  2. Confirme que a pré-visualização mostra a linha equivalente a `iptables -A INPUT -s 192.168.1.0/24 -d 10.0.0.1 -p tcp --dport 22 -j ACCEPT`.
  3. Exporte e valide o arquivo salvo abrindo-o em um editor ou copiando para um sistema Linux de teste.

- Verificação US2 (T021): reordenação e edição
  1. Crie duas regras A e B com destinos diferentes.
  2. Reordene B acima de A usando os controles de mover.
  3. Verifique que a pré-visualização e o script exportado refletem a nova ordem (comando correspondente a B aparece antes do comando A).

- Verificação US3 (T025): filtro/visualização
  1. Adicione múltiplas regras com ações diferentes (`ACCEPT`, `DROP`).
  2. Aplique filtro por ação `DROP` na pré-visualização.
  3. Confirme que apenas regras com `DROP` aparecem na pré-visualização enquanto a ordem canônica das regras permanece intacta para exportação.

- Validação de exportação e UX (T029): mensagens de validação e falhas de export
  1. Tente adicionar uma regra com IP malformado (por exemplo `999.999.999.999`) e confirme que a UI mostra uma mensagem de erro clara e impede a adição.
  2. Tente adicionar uma regra duplicada e confirme que a UI alerta sobre duplicata.
  3. Ao exportar, force uma condição de falha de salvamento (por ex., escolher um caminho inválido) e confirme que a aplicação lida com erro sem corromper dados.

## Resultados Esperados

- A UI permanece responsiva durante a edição de regras.
- A pré-visualização reflete alterações imediatamente.
- O arquivo gerado preserva a ordem canônica das regras e contém apenas comandos da tabela `filter` do `iptables`.
- Mensagens de erro são claras e evitam exportação de regras inválidas.

## Observações

- A execução do script exportado em um sistema Linux exige privilégios (por exemplo `sudo`) e é de responsabilidade do administrador aplicar as regras.
- Para builds locais com JavaFX, é recomendável usar Gradle com o plugin JavaFX configurado (conforme `build.gradle`).
