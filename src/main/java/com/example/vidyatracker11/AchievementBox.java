package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AchievementBox<T> extends HBox {
    private ObservableList<T> items = FXCollections.observableArrayList();
    int[] ranks;
    Label titleLabel = new Label();
    Label progressLabel = new Label("");
    Label rankLabel = new Label("");
    HBox topBox = new HBox(titleLabel, progressLabel,rankLabel);
    ProgressBar progressBar = new ProgressBar();
    ImageView badge = new ImageView(ApplicationGUI.icon);
    Label descriptionLabel = new Label();
    Button showListButton = new Button("Show Applicable Items");
    HBox bottomBox = new HBox(descriptionLabel, showListButton);
    VBox vbox = new VBox(topBox, progressBar, bottomBox);

    public AchievementBox(String title, String description, int[] ranks, boolean big){
        this.ranks = ranks;
        titleLabel.setText(title);
        descriptionLabel.setText(description);

        getChildren().addAll(vbox, badge);

        if(big){
            vbox.getChildren().add(0, badge);
        }

        showListButton.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Items");
            ListView<T> listView = new ListView<>(items);

            Scene scene = new Scene(listView);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            stage.setScene(scene);
            stage.show();
        });
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public void setItems(ObservableList<T> items) {
        this.items = items;
    }
}
