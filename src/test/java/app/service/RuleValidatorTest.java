package app.service;

import app.model.Rule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleValidatorTest {

    @Test
    void validRuleHasNoErrors() {
        Rule rule = new Rule("1", "192.168.1.0/24", "10.0.0.1", "tcp", "1024", "22", "eth0", "eth1", "ACCEPT", "test", 0);
        RuleValidator validator = new RuleValidator();

        assertTrue(validator.validate(rule).isEmpty());
    }

    @Test
    void invalidProtocolProducesError() {
        Rule rule = new Rule("1", "0.0.0.0/0", "10.0.0.1", "ftp", "", "22", "", "", "ACCEPT", "", 0);
        RuleValidator validator = new RuleValidator();

        assertFalse(validator.validate(rule).isEmpty());
    }

    @Test
    void duplicateDetectionWorks() {
        Rule rule = new Rule("1", "192.168.1.0/24", "10.0.0.1", "tcp", "", "22", "", "", "ACCEPT", "test", 0);
        Rule duplicate = new Rule("2", "192.168.1.0/24", "10.0.0.1", "tcp", "", "22", "", "", "ACCEPT", "test 2", 1);
        RuleValidator validator = new RuleValidator();

        assertTrue(validator.isDuplicate(duplicate, java.util.List.of(rule)));
    }
}
