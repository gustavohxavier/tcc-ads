package app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RuleSet {
    private final List<Rule> regras = new ArrayList<>();
    private List<Rule> regrasFiltradas = new ArrayList<>();

    public void addRule(Rule rule) {
        regras.add(rule);
        refreshFiltered(ruleItem -> true);
    }

    public void removeRule(String ruleId) {
        regras.removeIf(rule -> Objects.equals(rule.getId(), ruleId));
        refreshFiltered(ruleItem -> true);
    }

    public void updateRule(String ruleId, Rule updatedRule) {
        for (int i = 0; i < regras.size(); i++) {
            if (Objects.equals(regras.get(i).getId(), ruleId)) {
                regras.set(i, updatedRule);
                break;
            }
        }
        refreshFiltered(ruleItem -> true);
    }

    public void reorder(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= regras.size() || toIndex < 0 || toIndex >= regras.size()) {
            return;
        }
        Rule rule = regras.remove(fromIndex);
        regras.add(toIndex, rule);
        refreshFiltered(ruleItem -> true);
    }

    public List<Rule> getRegras() {
        return List.copyOf(regras);
    }

    public List<Rule> getRegrasFiltradas() {
        return List.copyOf(regrasFiltradas);
    }

    public void refreshFiltered(Predicate<Rule> filter) {
        regrasFiltradas = regras.stream().filter(filter).collect(Collectors.toList());
    }

    public Optional<Rule> findById(String ruleId) {
        return regras.stream().filter(rule -> Objects.equals(rule.getId(), ruleId)).findFirst();
    }
}
