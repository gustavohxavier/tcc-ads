package app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    void ruleEqualsShouldIgnoreIdAndComment() {
        Rule first = new Rule("1", "192.168.0.1", "10.0.0.1", "tcp", "1000", "22", "eth0", "eth1", "ACCEPT", "comment", 0);
        Rule second = new Rule("2", "192.168.0.1", "10.0.0.1", "tcp", "1000", "22", "eth0", "eth1", "ACCEPT", "other", 1);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void toStringContainsKeyFields() {
        Rule rule = new Rule("1", "0.0.0.0/0", "10.0.0.1", "udp", "53", "53", "eth0", "", "DROP", "", 0);

        assertTrue(rule.toString().contains("DROP"));
        assertTrue(rule.toString().contains("udp"));
        assertTrue(rule.toString().contains("0.0.0.0/0"));
    }
}
