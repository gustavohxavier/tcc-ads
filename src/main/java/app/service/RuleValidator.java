package app.service;

import app.model.Rule;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleValidator {
    private static final Set<String> SUPPORTED_PROTOCOLS = Set.of("tcp", "udp", "icmp", "all");
    private static final Pattern CIDR_PATTERN = Pattern.compile("^([0-9]{1,3}(?:\\.[0-9]{1,3}){3})(?:/(\\d|[12]\\d|3[0-2]))?$");
    private static final Pattern PORT_RANGE_PATTERN = Pattern.compile("^(\\d{1,5})(?::(\\d{1,5}))?$");

    public List<String> validate(Rule rule) {
        List<String> errors = new ArrayList<>();

        if (rule.getProtocolo() == null || rule.getProtocolo().isBlank()) {
            errors.add("Protocolo é obrigatório.");
        } else if (!SUPPORTED_PROTOCOLS.contains(rule.getProtocolo().toLowerCase())) {
            errors.add("Protocolo deve ser tcp, udp, icmp ou all.");
        }

        if (!isValidIpOrCidr(rule.getOrigem())) {
            errors.add("Origem deve ser um IP válido, CIDR ou ficar vazia para qualquer origem.");
        }

        if (!isValidIpOrCidr(rule.getDestino())) {
            errors.add("Destino deve ser um IP válido, CIDR ou ficar vazia para qualquer destino.");
        }

        if (!isValidPortOrRange(rule.getPortaOrigem(), rule.getProtocolo())) {
            errors.add("Porta de origem deve ser um número entre 1 e 65535 ou um intervalo válido.");
        }

        if (!isValidPortOrRange(rule.getPortaDestino(), rule.getProtocolo())) {
            errors.add("Porta de destino deve ser um número entre 1 e 65535 ou um intervalo válido.");
        }

        if (rule.getAcao() == null || rule.getAcao().isBlank()) {
            errors.add("Ação é obrigatória.");
        } else if (!Set.of("ACCEPT", "DROP", "REJECT").contains(rule.getAcao())) {
            errors.add("Ação deve ser ACCEPT, DROP ou REJECT.");
        }

        if ("all".equalsIgnoreCase(rule.getProtocolo()) && (rule.getPortaOrigem() != null && !rule.getPortaOrigem().isBlank() || rule.getPortaDestino() != null && !rule.getPortaDestino().isBlank())) {
            errors.add("Quando o protocolo for 'all', as portas devem ser ignoradas ou deixadas em branco.");
        }

        return errors;
    }

    public boolean isValidIpOrCidr(String value) {
        if (value == null || value.isBlank()) {
            return true;
        }
        Matcher matcher = CIDR_PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            return false;
        }
        String ip = matcher.group(1);
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.getHostAddress().equals(ip);
        } catch (UnknownHostException ex) {
            return false;
        }
    }

    public boolean isValidPortOrRange(String value, String protocolo) {
        if (value == null || value.isBlank()) {
            return true;
        }
        if ("icmp" .equalsIgnoreCase(protocolo) || "all".equalsIgnoreCase(protocolo)) {
            return false;
        }
        Matcher matcher = PORT_RANGE_PATTERN.matcher(value.trim());
        if (!matcher.matches()) {
            return false;
        }
        try {
            int start = Integer.parseInt(matcher.group(1));
            if (start < 1 || start > 65535) {
                return false;
            }
            String endGroup = matcher.group(2);
            if (endGroup != null) {
                int end = Integer.parseInt(endGroup);
                return end >= start && end <= 65535;
            }
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean isDuplicate(Rule current, List<Rule> existingRules) {
        return existingRules.stream().anyMatch(rule -> !rule.getId().equals(current.getId()) && rule.equals(current));
    }
}
