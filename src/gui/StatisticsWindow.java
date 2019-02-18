package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import leitner.CategoryEnum;
import leitner.LeitnerSet;

public class StatisticsWindow {

    private Stage statsStage = new Stage();
    private LeitnerSet leitnerSet;
    public StatisticsWindow(LeitnerSet leitnerSet) {
        this.leitnerSet = leitnerSet;
        initStage();
        statsStage.showAndWait();
    }

    private void initStage() {
        StackPane stackPane = new StackPane();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
            new PieChart.Data("Unseen", leitnerSet.getCountOfCardsInCategory(CategoryEnum.UNSEEN)),
            new PieChart.Data("Young", leitnerSet.getCountOfCardsInCategory(CategoryEnum.YOUNG)),
            new PieChart.Data("Mature", leitnerSet.getCountOfCardsInCategory(CategoryEnum.MATURE))
        );
        PieChart pieChart = new PieChart(data);
        pieChart.setTitle("Learning Statistics");
        stackPane.getChildren().add(pieChart);
        statsStage.setScene(new Scene(stackPane));
    }
}
