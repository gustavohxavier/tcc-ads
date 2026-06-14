package app.service;

import app.model.Rule;
import app.model.RuleSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IptablesScriptGeneratorTest {

    @Test
    void generatesCorrectIptablesCommand() {
        RuleSet ruleSet = new RuleSet();
        ruleSet.addRule(new Rule("1", "192.168.1.0/24", "10.0.0.1", "tcp", "", "22", "eth0", "", "ACCEPT", "", 0));

        IptablesScriptGenerator generator = new IptablesScriptGenerator();
        String script = generator.generateScript(ruleSet);

        assertTrue(script.contains("iptables -A INPUT -s 192.168.1.0/24 -d 10.0.0.1 -p tcp --dport 22 -i eth0 -j ACCEPT"));
    }

    @Test
    void emptyRuleSetReturnsEmptyScript() {
        RuleSet ruleSet = new RuleSet();
        IptablesScriptGenerator generator = new IptablesScriptGenerator();

        assertEquals("", generator.generateScript(ruleSet));
    }
}
