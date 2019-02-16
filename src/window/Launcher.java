package window;

import io.LeitnerSetReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Launcher extends Application {
    Button loadButton = new Button("Load from file");
    Stage primaryStage = null;
    MainApp mainApp = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Cards");

        initButtonBehavior();

        GridPane gridPane = new GridPane();
        gridPane.add(loadButton, 0, 0);
        gridPane.setHgap(6);
        gridPane.setVgap(6);

        Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(gridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));

        primaryStage.setScene(new Scene(rootGroup));
        primaryStage.show();
    }

    private void initButtonBehavior() {
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage loadingStage = new Stage();
                loadingStage.setTitle("File Chooser");

                boolean fileNeeded = true;
                boolean hasCanceled = false;

                File file = null;

                while (fileNeeded && !hasCanceled) {
                    FileChooser fileChooser = new FileChooser();

                    fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Deck Files (*.deck)", "*.deck"),
                        new FileChooser.ExtensionFilter("All Files", "*.*")
                    );

                    file = fileChooser.showOpenDialog(loadingStage);

                    hasCanceled = (file == null);
                    if (hasCanceled) {
                        break;
                    }

                    fileNeeded = (!file.getName().endsWith(".deck"));

                    if (fileNeeded) {
                        Alert alert = new Alert(
                            Alert.AlertType.ERROR,
                            "Improper file extension.",
                            ButtonType.OK
                        );
                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        alert.showAndWait();

                        loadingStage.hide();

                        fileNeeded = true;
                        hasCanceled = false;
                    }
                }

                if (hasCanceled) {
                    return;
                }
                LeitnerSetReader leitnerSetReader = new LeitnerSetReader(file);
                mainApp = new MainApp(leitnerSetReader);
                primaryStage.hide();
            }
        });
    }
}
