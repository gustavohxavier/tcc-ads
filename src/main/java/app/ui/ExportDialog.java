package app.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExportDialog extends Stage {
    private final String scriptContent;

    public ExportDialog(String scriptContent) {
        this.scriptContent = scriptContent;

        TextArea textArea = new TextArea(scriptContent);
        textArea.setEditable(false);
        textArea.setPrefHeight(400);
        textArea.setWrapText(true);

        Button copyButton = new Button("Copiar para clipboard");
        copyButton.setOnAction(event -> copyToClipboard());

        Button saveButton = new Button("Salvar como arquivo .txt");
        saveButton.setOnAction(event -> saveToFile());

        Button closeButton = new Button("Fechar");
        closeButton.setOnAction(event -> close());

        Label messageLabel = new Label();

        VBox layout = new VBox(
                new Label("Script gerado:"),
                textArea,
                copyButton,
                saveButton,
                closeButton,
                messageLabel
        );
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 600, 600);
        this.setScene(scene);
        this.setTitle("Exportar script iptables");
    }

    private void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(scriptContent);
        clipboard.setContent(content);
    }

    private void saveToFile() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Salvar script");
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Script files (*.sh)", "*.sh"),
                new javafx.stage.FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"),
                new javafx.stage.FileChooser.ExtensionFilter("All files (*.*)", "*.*")
        );

        java.io.File file = fileChooser.showSaveDialog(this);
        if (file != null) {
            try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                writer.write(scriptContent);
            } catch (java.io.IOException ex) {
                System.err.println("Erro ao salvar arquivo: " + ex.getMessage());
            }
        }
    }
}
