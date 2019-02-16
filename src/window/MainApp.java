package window;

import io.LeitnerSetReader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import leitner.LeitnerSet;

public class MainApp {

    private class BottomBase {
        private StackPane externalRoot = new StackPane();
        private HBox internalRoot = new HBox(6);
        private Button[] buttons = {
            new Button("Show Answer"),
            new Button("Easy"),
            new Button("Medium"),
            new Button("Hard"),
            new Button("Again"),
            new Button("Learning Statistics")
        };

        public BottomBase() {
            internalRoot.setPadding(new Insets(6, 6, 6, 6));
            externalRoot.getChildren().add(internalRoot);
            for (Button button : buttons) {
                internalRoot.getChildren().add(button);
            }
        }
    }

    private class CenterBase {
        private StackPane externalRoot = new StackPane();
        public TextArea textArea = new TextArea("test");

        public CenterBase() {
            textArea.setEditable(false);
            externalRoot.getChildren().add(textArea);
            externalRoot.setPadding(new Insets(6, 6, 6, 6));
        }
    }

    private CenterBase centerBase = new CenterBase();
    private BottomBase bottomBase = new BottomBase();

    private LeitnerSet leitnerSet;

    private Stage bigStage = new Stage();

    public MainApp(LeitnerSetReader leitnerSetReader) {
        leitnerSetReader.read();
        leitnerSet = leitnerSetReader.getLeitnerSet();

        initStage();
    }

    private void initStage() {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerBase.externalRoot);
        borderPane.setBottom(bottomBase.externalRoot);

        centerBase.textArea.setText(leitnerSet.toString());

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(12, 12, 12, 12));
        stackPane.getChildren().add(borderPane);
        Scene bigScene = new Scene(stackPane);
        bigStage.setScene(bigScene);
        bigStage.show();
    }

    private void
}
