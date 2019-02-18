package gui;

import leitner.Card;
import leitner.LeitnerSetReader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import leitner.LeitnerSet;

import java.io.*;
import java.util.Queue;

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
        private VBox vBox = new VBox(6);
        private TextArea frontText = new TextArea();
        private TextArea backText = new TextArea();

        public CenterBase() {
            frontText.setEditable(false);
            frontText.setPrefColumnCount(5);
            frontText.setPrefRowCount(2);
            backText.setEditable(false);
            backText.setPrefColumnCount(5);
            backText.setPrefRowCount(2);
            vBox.getChildren().add(frontText);
            vBox.getChildren().add(backText);
            externalRoot.setPadding(new Insets(6, 6, 6, 6));
            externalRoot.getChildren().add(vBox);
        }
    }

    private CenterBase centerBase = new CenterBase();
    private BottomBase bottomBase = new BottomBase();

    private LeitnerSet leitnerSet;
    private Queue<Card> cardsToPractice;
    private String absolutePath;

    private Stage bigStage = new Stage();

    public MainApp(File file) {
        this.absolutePath = file.getAbsolutePath();
        if (absolutePath.endsWith(".deck")) {
            LeitnerSetReader lsr = new LeitnerSetReader(file);
            lsr.read();
            this.leitnerSet = lsr.getLeitnerSet();
        }
        else if (absolutePath.endsWith(".dobj")) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(absolutePath));
                this.leitnerSet = (LeitnerSet)ois.readObject();
                ois.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        initStage();
        initButtons();
        startPractice();
    }

    private void initStage() {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerBase.externalRoot);
        borderPane.setBottom(bottomBase.externalRoot);

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(12, 12, 12, 12));
        stackPane.getChildren().add(borderPane);
        Scene bigScene = new Scene(stackPane);
        bigStage.setScene(bigScene);
        bigStage.show();

        bigStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void initButtons() {
        disableGradingButtons(true);

        bottomBase.buttons[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Card topCard = cardsToPractice.element();
                centerBase.backText.setText(topCard.getBack());

                disableGradingButtons(false);
            }
        });

        bottomBase.buttons[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Card topCard = cardsToPractice.remove();
                leitnerSet.promoteCard(topCard);
                topCard.updateDate();

                disableGradingButtons(true);
                pollNextCard();
            }
        });

        bottomBase.buttons[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Card topCard = cardsToPractice.remove();
                topCard.updateDate();

                disableGradingButtons(true);
                pollNextCard();
            }
        });

        bottomBase.buttons[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Card topCard = cardsToPractice.remove();
                leitnerSet.demoteCard(topCard);
                topCard.updateDate();

                disableGradingButtons(true);
                pollNextCard();
            }
        });

        bottomBase.buttons[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Card topCard = cardsToPractice.remove();
                cardsToPractice.add(topCard);
                leitnerSet.relegateCard(topCard);
                topCard.updateDate();

                disableGradingButtons(true);
                pollNextCard();
            }
        });

        bottomBase.buttons[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                new StatisticsWindow(leitnerSet);
            }
        });
    }

    private void disableGradingButtons(boolean isDisabled) {
        bottomBase.buttons[0].setDisable(!isDisabled);
        bottomBase.buttons[1].setDisable(isDisabled);
        bottomBase.buttons[2].setDisable(isDisabled);
        bottomBase.buttons[3].setDisable(isDisabled);
        bottomBase.buttons[4].setDisable(isDisabled);
    }

    private void startPractice() {
        cardsToPractice = leitnerSet.cardsToShow();
        pollNextCard();
    }

    private void pollNextCard() {
        if (cardsToPractice.isEmpty()) {
            centerBase.frontText.setText("No cards left to practice.");
            centerBase.backText.clear();
            bottomBase.buttons[0].setDisable(true);
            try {
                String newPath = absolutePath.replaceAll(".deck", ".dobj");
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newPath));
                oos.writeObject(leitnerSet);
                oos.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Card nextCard = cardsToPractice.element();
            centerBase.frontText.setText(nextCard.getFront());
            centerBase.backText.clear();
        }
    }

    private <T extends Event> void closeWindowEvent(T t) {
        String newPath = absolutePath.replaceAll(".deck", ".dobj");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(newPath));
            oos.writeObject(leitnerSet);
            oos.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
