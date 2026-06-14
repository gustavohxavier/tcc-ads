package app.service;

import app.model.Rule;
import app.model.RuleSet;

import java.util.List;
import java.util.stream.Collectors;

public class IptablesScriptGenerator {

    public String generateScript(RuleSet ruleSet) {
        return generateScript(ruleSet.getRegras());
    }

    public String generateScript(List<Rule> rules) {
        List<String> lines = rules.stream()
                .map(this::ruleToCommand)
                .collect(Collectors.toList());
        return String.join(System.lineSeparator(), lines);
    }

    private String ruleToCommand(Rule rule) {
        StringBuilder command = new StringBuilder("iptables -A INPUT");

        appendSource(rule, command);
        appendDestination(rule, command);
        appendProtocol(rule, command);
        appendPorts(rule, command);
        appendInterfaces(rule, command);
        command.append(" -j ").append(rule.getAcao());

        return command.toString().trim();
    }

    private void appendSource(Rule rule, StringBuilder command) {
        if (rule.getOrigem() != null && !rule.getOrigem().isBlank()) {
            command.append(" -s ").append(rule.getOrigem().trim());
        }
    }

    private void appendDestination(Rule rule, StringBuilder command) {
        if (rule.getDestino() != null && !rule.getDestino().isBlank()) {
            command.append(" -d ").append(rule.getDestino().trim());
        }
    }

    private void appendProtocol(Rule rule, StringBuilder command) {
        if (rule.getProtocolo() != null && !rule.getProtocolo().isBlank()) {
            command.append(" -p ").append(rule.getProtocolo().trim());
        }
    }

    private void appendPorts(Rule rule, StringBuilder command) {
        if (rule.getPortaOrigem() != null && !rule.getPortaOrigem().isBlank()) {
            command.append(" --sport ").append(rule.getPortaOrigem().trim());
        }
        if (rule.getPortaDestino() != null && !rule.getPortaDestino().isBlank()) {
            command.append(" --dport ").append(rule.getPortaDestino().trim());
        }
    }

    private void appendInterfaces(Rule rule, StringBuilder command) {
        if (rule.getInterfaceEntrada() != null && !rule.getInterfaceEntrada().isBlank()) {
            command.append(" -i ").append(rule.getInterfaceEntrada().trim());
        }
        if (rule.getInterfaceSaida() != null && !rule.getInterfaceSaida().isBlank()) {
            command.append(" -o ").append(rule.getInterfaceSaida().trim());
        }
    }
}
