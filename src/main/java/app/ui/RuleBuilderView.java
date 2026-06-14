package app.ui;

import app.model.Rule;
import app.model.RuleSet;
import app.service.RuleValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class RuleBuilderView extends BorderPane {
    private final RuleSet ruleSet = new RuleSet();
    private final RuleValidator validator = new RuleValidator();
    private Consumer<RuleSet> onRuleSetChanged = ignored -> {};

    private final ObservableList<String> ruleSummaries = FXCollections.observableArrayList();
    private final ListView<String> rulesListView = new ListView<>(ruleSummaries);

    private final TextField origemField = new TextField();
    private final TextField destinoField = new TextField();
    private final ComboBox<String> protocoloCombo = new ComboBox<>();
    private final TextField portaOrigemField = new TextField();
    private final TextField portaDestinoField = new TextField();
    private final TextField interfaceEntradaField = new TextField();
    private final TextField interfaceSaidaField = new TextField();
    private final ComboBox<String> acaoCombo = new ComboBox<>();
    private final TextField comentarioField = new TextField();
    private final Label messageLabel = new Label();

    public RuleBuilderView() {
        protocoloCombo.getItems().setAll("tcp", "udp", "icmp", "all");
        acaoCombo.getItems().setAll("ACCEPT", "DROP", "REJECT");
        protocoloCombo.getSelectionModel().selectFirst();
        acaoCombo.getSelectionModel().selectFirst();

        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Origem"), 0, 0);
        form.add(origemField, 1, 0);
        form.add(new Label("Destino"), 0, 1);
        form.add(destinoField, 1, 1);
        form.add(new Label("Protocolo"), 0, 2);
        form.add(protocoloCombo, 1, 2);
        form.add(new Label("Porta Origem"), 0, 3);
        form.add(portaOrigemField, 1, 3);
        form.add(new Label("Porta Destino"), 0, 4);
        form.add(portaDestinoField, 1, 4);
        form.add(new Label("Iface Entrada"), 0, 5);
        form.add(interfaceEntradaField, 1, 5);
        form.add(new Label("Iface Saída"), 0, 6);
        form.add(interfaceSaidaField, 1, 6);
        form.add(new Label("Ação"), 0, 7);
        form.add(acaoCombo, 1, 7);
        form.add(new Label("Comentário"), 0, 8);
        form.add(comentarioField, 1, 8);

        Button addButton = new Button("Adicionar regra");
        addButton.setOnAction(event -> createRule());
        form.add(addButton, 0, 9, 2, 1);
        form.add(messageLabel, 0, 10, 2, 1);

        Button removeButton = new Button("Remover regra selecionada");
        removeButton.setOnAction(event -> removeSelectedRule());

        Button moveUpButton = new Button("Mover para cima");
        moveUpButton.setOnAction(event -> moveSelectedRule(-1));

        Button moveDownButton = new Button("Mover para baixo");
        moveDownButton.setOnAction(event -> moveSelectedRule(1));

        HBox listControls = new HBox(removeButton, moveUpButton, moveDownButton);
        listControls.setSpacing(10);

        VBox leftPanel = new VBox(form, rulesListView, listControls);
        leftPanel.setSpacing(10);
        leftPanel.setPadding(new Insets(10));

        setCenter(leftPanel);
        rulesListView.setPrefHeight(300);
    }

    private void createRule() {
        Rule rule = new Rule(
                UUID.randomUUID().toString(),
                origemField.getText(),
                destinoField.getText(),
                protocoloCombo.getValue(),
                portaOrigemField.getText(),
                portaDestinoField.getText(),
                interfaceEntradaField.getText(),
                interfaceSaidaField.getText(),
                acaoCombo.getValue(),
                comentarioField.getText(),
                ruleSet.getRegras().size()
        );

        List<String> errors = validator.validate(rule);
        if (!errors.isEmpty()) {
            messageLabel.setText(String.join(" ", errors));
            return;
        }

        if (validator.isDuplicate(rule, ruleSet.getRegras())) {
            messageLabel.setText("Regra duplicada detectada. Ajuste os campos antes de adicionar.");
            return;
        }

        ruleSet.addRule(rule);
        updateRuleSummaries();
        clearInputs();
        messageLabel.setText("Regra adicionada com sucesso.");
        onRuleSetChanged.accept(ruleSet);
    }

    private void removeSelectedRule() {
        int selectedIndex = rulesListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= ruleSet.getRegras().size()) {
            messageLabel.setText("Selecione uma regra antes de remover.");
            return;
        }
        String selectedId = ruleSet.getRegras().get(selectedIndex).getId();
        ruleSet.removeRule(selectedId);
        updateRuleSummaries();
        messageLabel.setText("Regra removida.");
        onRuleSetChanged.accept(ruleSet);
    }

    private void moveSelectedRule(int direction) {
        int selectedIndex = rulesListView.getSelectionModel().getSelectedIndex();
        int targetIndex = selectedIndex + direction;
        if (selectedIndex < 0 || targetIndex < 0 || targetIndex >= ruleSet.getRegras().size()) {
            messageLabel.setText("Seleção inválida para mover.");
            return;
        }
        ruleSet.reorder(selectedIndex, targetIndex);
        updateRuleSummaries();
        rulesListView.getSelectionModel().select(targetIndex);
        messageLabel.setText("Regra movida.");
        onRuleSetChanged.accept(ruleSet);
    }

    private void updateRuleSummaries() {
        ruleSummaries.clear();
        for (Rule rule : ruleSet.getRegras()) {
            ruleSummaries.add(String.format("[%d] %s %s %s -> %s", rule.getPosicao(), rule.getAcao(), rule.getProtocolo(), rule.getOrigem(), rule.getDestino()));
        }
    }

    private void clearInputs() {
        origemField.clear();
        destinoField.clear();
        portaOrigemField.clear();
        portaDestinoField.clear();
        interfaceEntradaField.clear();
        interfaceSaidaField.clear();
        comentarioField.clear();
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setOnRuleSetChanged(Consumer<RuleSet> listener) {
        this.onRuleSetChanged = listener;
    }
}
