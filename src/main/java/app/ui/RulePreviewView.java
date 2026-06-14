package app.ui;

import app.model.Rule;
import app.model.RuleSet;
import app.service.IptablesScriptGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RulePreviewView extends BorderPane {
    private final RuleSet ruleSet;
    private final IptablesScriptGenerator generator = new IptablesScriptGenerator();
    private final TextArea previewArea = new TextArea();
    private final Label countLabel = new Label("Regras totais: 0 | Filtradas: 0");
    private final ComboBox<String> actionFilter = new ComboBox<>();
    private final ComboBox<String> protocolFilter = new ComboBox<>();
    private final TextField portFilter = new TextField();
    private final TextField ipFilter = new TextField();

    public RulePreviewView(RuleSet ruleSet) {
        this.ruleSet = ruleSet;

        previewArea.setEditable(false);
        previewArea.setPrefHeight(400);

        actionFilter.getItems().addAll("ALL", "ACCEPT", "DROP", "REJECT");
        actionFilter.getSelectionModel().select("ALL");

        protocolFilter.getItems().addAll("ALL", "tcp", "udp", "icmp", "all");
        protocolFilter.getSelectionModel().select("ALL");

        Button filterButton = new Button("Aplicar filtro");
        Button clearFilterButton = new Button("Limpar filtro");
        Button exportButton = new Button("Exportar script");

        filterButton.setOnAction(event -> refreshPreview());
        clearFilterButton.setOnAction(event -> {
            actionFilter.getSelectionModel().select("ALL");
            protocolFilter.getSelectionModel().select("ALL");
            portFilter.clear();
            ipFilter.clear();
            refreshPreview();
        });
        exportButton.setOnAction(event -> new ExportDialog(generator.generateScript(ruleSet)).show());

        GridPane filterPane = new GridPane();
        filterPane.setPadding(new Insets(10));
        filterPane.setHgap(10);
        filterPane.setVgap(10);
        filterPane.add(new Label("Ação"), 0, 0);
        filterPane.add(actionFilter, 1, 0);
        filterPane.add(new Label("Protocolo"), 0, 1);
        filterPane.add(protocolFilter, 1, 1);
        filterPane.add(new Label("Porta"), 0, 2);
        filterPane.add(portFilter, 1, 2);
        filterPane.add(new Label("IP"), 0, 3);
        filterPane.add(ipFilter, 1, 3);
        filterPane.add(filterButton, 0, 4);
        filterPane.add(clearFilterButton, 1, 4);

        HBox topBar = new HBox(countLabel, exportButton);
        topBar.setSpacing(10);
        topBar.setPadding(new Insets(10));

        VBox container = new VBox(filterPane, topBar, previewArea);
        container.setSpacing(10);
        container.setPadding(new Insets(10));

        setCenter(container);
        refreshPreview();
    }

    public void refreshPreview() {
        Predicate<Rule> filter = rule -> {
            boolean matchesAction = actionFilter.getValue().equals("ALL") || actionFilter.getValue().equals(rule.getAcao());
            boolean matchesProtocol = protocolFilter.getValue().equals("ALL") || protocolFilter.getValue().equals(rule.getProtocolo());
            boolean matchesPort = portFilter.getText().isBlank() || rule.getPortaOrigem().contains(portFilter.getText()) || rule.getPortaDestino().contains(portFilter.getText());
            boolean matchesIp = ipFilter.getText().isBlank() || rule.getOrigem().contains(ipFilter.getText()) || rule.getDestino().contains(ipFilter.getText());
            return matchesAction && matchesProtocol && matchesPort && matchesIp;
        };

        ruleSet.refreshFiltered(filter);
        List<Rule> filtered = ruleSet.getRegrasFiltradas();
        countLabel.setText(String.format("Regras totais: %d | Filtradas: %d", ruleSet.getRegras().size(), filtered.size()));
        previewArea.setText(generator.generateScript(filtered));
    }
}
