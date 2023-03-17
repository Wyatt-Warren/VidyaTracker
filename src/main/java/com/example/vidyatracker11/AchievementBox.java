package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class AchievementBox<T> extends HBox {
    private ObservableList<T> items = FXCollections.observableArrayList();
    int[] ranks;
    Label titleLabel = new Label();
    Label countLabel = new Label("Count: ");
    Label nextRankLabel = new Label("Progress: ");
    Label rankLabel = new Label("Rank: ");
    HBox labelBox = new HBox(countLabel, nextRankLabel);
    HBox topBox = new HBox(titleLabel, labelBox);
    ProgressBar progressBar = new ProgressBar();
    ImageView badge = new ImageView();
    VBox badgeBox = new VBox(badge, rankLabel);
    Label descriptionLabel = new Label();
    Button showListButton = new Button("Show Applicable Items");
    HBox bottomBox = new HBox(descriptionLabel, showListButton);
    VBox vbox = new VBox(topBox, progressBar, bottomBox);
    private int rank = 0;
    private boolean big;

    public AchievementBox(String title, String description, int[] ranks, boolean big){
        this.big = big;
        this.ranks = ranks;
        titleLabel.setText(title);
        titleLabel.setStyle("-fx-font-size: 18;-fx-font-weight: bold;");
        descriptionLabel.setText(description);
        vbox.setMinWidth(500);
        vbox.setMaxWidth(500);
        vbox.setSpacing(5);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        descriptionLabel.setMinWidth(480-(new Text(showListButton.getText())).getLayoutBounds().getWidth());
        labelBox.setAlignment(Pos.CENTER_RIGHT);
        labelBox.setSpacing(20);
        badgeBox.setAlignment(Pos.CENTER);
        rankLabel.setStyle("-fx-font-size: 14;-fx-font-weight: bold;");
        setSpacing(10);

        getChildren().addAll(vbox, badgeBox);

        if(big){
            vbox.setMinWidth(564);
            vbox.setMaxWidth(564);
            vbox.setAlignment(Pos.CENTER);
            descriptionLabel.setMinWidth(544-(new Text(showListButton.getText())).getLayoutBounds().getWidth());
            vbox.getChildren().add(0, badgeBox);
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

        badge.setOnMouseClicked(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ranks");
            VBox box = new VBox();
            for(int i = 0; i < ranks.length; i++) {
                Label label = new Label(i + ":\t" + ranks[i]);
                if(i==rank)
                    label.setStyle("-fx-font-size: 18;-fx-font-weight: bold;-fx-text-fill: #00ffff;");
                else
                    label.setStyle("-fx-font-size: 18;-fx-font-weight: bold;");
                ImageView badgeImage = new ImageView(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/Rank" + i + ".png"))));
                HBox rankBox = new HBox(badgeImage, label);
                rankBox.setSpacing(20);
                rankBox.setAlignment(Pos.CENTER_LEFT);
                box.getChildren().add(rankBox);
            }
            Scene scene = new Scene(box);
            box.setPadding(new Insets(5));
            box.setSpacing(10);
            box.setAlignment(Pos.CENTER);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            stage.setScene(scene);
            stage.show();

        });
    }

    public void updateProgress(){
        int count = items.size();
        rank = 0;
        for(int i = 10; i >= 0; i--)
            if(count >= ranks[i]){
               rank = i;
               break;
            }
        int progress = count-ranks[rank];

        progressBar.setProgress((progress*1.0) / (ranks[rank+1]-ranks[rank]));
        countLabel.setText("Count: " + count);
        nextRankLabel.setText("Progress to next rank: " + progress + "/" + (ranks[rank+1]-ranks[rank]));
        rankLabel.setText("Rank: " + rank);
        badge.setImage(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/Rank" + rank + ".png"))));
    }

    public ObservableList<T> getItems() {
        return items;
    }

    public boolean isBig() {
        return big;
    }

    public void setItems(ObservableList<T> items) {
        this.items = items;
    }
}
