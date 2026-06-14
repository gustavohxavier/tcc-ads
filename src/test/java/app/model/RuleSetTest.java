package app.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleSetTest {

    @Test
    void addAndFindRule() {
        RuleSet ruleSet = new RuleSet();
        Rule rule = new Rule("1", "192.168.0.1", "10.0.0.1", "tcp", "", "22", "", "", "ACCEPT", "", 0);

        ruleSet.addRule(rule);

        assertEquals(1, ruleSet.getRegras().size());
        assertTrue(ruleSet.findById("1").isPresent());
    }

    @Test
    void reorderUpdatesRuleOrder() {
        RuleSet ruleSet = new RuleSet();
        Rule first = new Rule("1", "0.0.0.0/0", "10.0.0.1", "tcp", "", "22", "", "", "ACCEPT", "", 0);
        Rule second = new Rule("2", "0.0.0.0/0", "10.0.0.2", "tcp", "", "80", "", "", "ACCEPT", "", 1);

        ruleSet.addRule(first);
        ruleSet.addRule(second);
        ruleSet.reorder(0, 1);

        assertEquals("2", ruleSet.getRegras().get(0).getId());
        assertEquals("1", ruleSet.getRegras().get(1).getId());
    }

    @Test
    void filterRulesByProtocol() {
        RuleSet ruleSet = new RuleSet();
        Rule accept = new Rule("1", "0.0.0.0/0", "10.0.0.1", "tcp", "", "22", "", "", "ACCEPT", "", 0);
        Rule drop = new Rule("2", "0.0.0.0/0", "10.0.0.2", "udp", "", "53", "", "", "DROP", "", 1);

        ruleSet.addRule(accept);
        ruleSet.addRule(drop);
        ruleSet.refreshFiltered(rule -> "udp".equals(rule.getProtocolo()));

        assertEquals(1, ruleSet.getRegrasFiltradas().size());
        assertEquals("2", ruleSet.getRegrasFiltradas().get(0).getId());
    }
}
