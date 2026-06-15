package app;

import app.ui.RuleBuilderView;
import app.ui.RulePreviewView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        RuleBuilderView builderView = new RuleBuilderView();
        RulePreviewView previewView = new RulePreviewView(builderView.getRuleSet());

        builderView.setOnRuleSetChanged(rs -> previewView.refreshPreview());

        BorderPane root = new BorderPane();
        root.setLeft(builderView);
        root.setCenter(previewView);

        Scene scene = new Scene(root, 1100, 700);
        stage.setTitle("iptables Drag-and-Drop Rule Builder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
