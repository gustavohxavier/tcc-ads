package app.model;

import java.util.Objects;

public class Rule {
    private final String id;
    private final String origem;
    private final String destino;
    private final String protocolo;
    private final String portaOrigem;
    private final String portaDestino;
    private final String interfaceEntrada;
    private final String interfaceSaida;
    private final String acao;
    private final String comentario;
    private final int posicao;

    public Rule(String id,
                String origem,
                String destino,
                String protocolo,
                String portaOrigem,
                String portaDestino,
                String interfaceEntrada,
                String interfaceSaida,
                String acao,
                String comentario,
                int posicao) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.protocolo = protocolo;
        this.portaOrigem = portaOrigem;
        this.portaDestino = portaDestino;
        this.interfaceEntrada = interfaceEntrada;
        this.interfaceSaida = interfaceSaida;
        this.acao = acao;
        this.comentario = comentario;
        this.posicao = posicao;
    }

    public String getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public String getPortaOrigem() {
        return portaOrigem;
    }

    public String getPortaDestino() {
        return portaDestino;
    }

    public String getInterfaceEntrada() {
        return interfaceEntrada;
    }

    public String getInterfaceSaida() {
        return interfaceSaida;
    }

    public String getAcao() {
        return acao;
    }

    public String getComentario() {
        return comentario;
    }

    public int getPosicao() {
        return posicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule)) return false;
        Rule rule = (Rule) o;
        return Objects.equals(origem, rule.origem)
                && Objects.equals(destino, rule.destino)
                && Objects.equals(protocolo, rule.protocolo)
                && Objects.equals(portaOrigem, rule.portaOrigem)
                && Objects.equals(portaDestino, rule.portaDestino)
                && Objects.equals(interfaceEntrada, rule.interfaceEntrada)
                && Objects.equals(interfaceSaida, rule.interfaceSaida)
                && Objects.equals(acao, rule.acao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origem, destino, protocolo, portaOrigem, portaDestino, interfaceEntrada, interfaceSaida, acao);
    }

    @Override
    public String toString() {
        return String.format("Rule[id=%s, %s -> %s, %s, sp=%s, dp=%s, i=%s, o=%s, action=%s]",
                id, origem, destino, protocolo, portaOrigem, portaDestino, interfaceEntrada, interfaceSaida, acao);
    }
}
