package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

//GUI and code for each individual achievement
public class AchievementBox<T> extends HBox {
    //Top Box
    Label titleLabel = new Label();
    Label countLabel = new Label("Count: ");
    Label nextRankLabel = new Label("Progress: ");
    HBox labelBox = new HBox(countLabel, nextRankLabel);
    HBox topBox = new HBox(titleLabel, labelBox);

    //Progress bar
    ProgressBar progressBar = new ProgressBar();

    //Bottom Box
    Label descriptionLabel = new Label();
    Button showListButton = new Button("Show Applicable Items");
    HBox bottomBox = new HBox(descriptionLabel, showListButton);

    //Badge Box
    ImageView badge = new ImageView();
    Label rankLabel = new Label("Rank: ");
    VBox badgeBox = new VBox(badge, rankLabel);

    VBox vbox = new VBox(topBox, progressBar, bottomBox);

    //Fields
    private ObservableList<T> items = FXCollections.observableArrayList();  //Items applicable to the achievement
    private final int[] ranks;                                              //The minimum number of achievements required for each rank
    private int rank = 0;                                                   //The current rank of the achievement
    private final boolean big;                                              //Flag used for to differentiate the top achievement

    public AchievementBox(String title, String description, int[] ranks, boolean big){
        //Set global variables
        this.big = big;
        this.ranks = ranks;

        //GUI
        titleLabel.setText(title);
        titleLabel.setStyle("-fx-font-size: 18;-fx-font-weight: bold;");
        labelBox.setAlignment(Pos.CENTER_RIGHT);
        labelBox.setSpacing(20);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        descriptionLabel.setText(description);
        descriptionLabel.setMinWidth(480-(new Text(showListButton.getText())).getLayoutBounds().getWidth());
        vbox.setMinWidth(500);
        vbox.setMaxWidth(500);
        vbox.setSpacing(5);
        rankLabel.setStyle("-fx-font-size: 14;-fx-font-weight: bold;");
        badgeBox.setAlignment(Pos.CENTER);
        setSpacing(10);
        getChildren().addAll(vbox, badgeBox);

        if(big){
            //Layout changes for big achievement
            vbox.setMinWidth(564);
            vbox.setMaxWidth(564);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(0, badgeBox);
            descriptionLabel.setMinWidth(544-(new Text(showListButton.getText())).getLayoutBounds().getWidth());
        }

        showListButton.setOnAction(e -> {
            //Shows a window containing a list of all applicable games
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
            //Shows a view of each rank's icon as well as the minimum number of required items
            //GUI
            Stage stage = new Stage();
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Ranks");
            VBox box = new VBox();
            ScrollPane scrollPane = new ScrollPane(box);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            if(big){
                //Changes for big achievement
                stage.setHeight(900);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            }

            for(int i = 0; i < ranks.length; i++) {
                //Creating a HBox for each rank
                //GUI
                Label label = new Label(i + ":\t" + ranks[i]);
                ImageView badgeImage;

                if(i<=rank)
                    //If the current HBox is the current rank of the achievement or below
                    label.setStyle("-fx-font-size: 18;-fx-font-weight: bold;-fx-text-fill: #00ffff;");
                else
                    //Current HBox rank is not achieved
                    label.setStyle("-fx-font-size: 18;-fx-font-weight: bold;");

                if(big) {
                    //Rank image for big achievement
                    badgeImage = new ImageView(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/RankBig" + i + ".png"))));
                } else
                    //Rank image for other achievements
                    badgeImage = new ImageView(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/Rank" + i + ".png"))));

                //GUI
                HBox rankBox = new HBox(badgeImage, label);
                rankBox.setSpacing(20);
                rankBox.setAlignment(Pos.CENTER_LEFT);
                box.getChildren().add(rankBox);
            }

            //GUI
            Scene scene = new Scene(scrollPane);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            box.setPadding(new Insets(5));
            box.setSpacing(10);
            box.setAlignment(Pos.CENTER);
            stage.setScene(scene);
            stage.show();
        });
    }

    //Set fields according to item list
    public void updateProgress(){
        //Local variables
        int count = items.size();   //Total number of applicable items
        int progress;               //Progress between the current rank and the next one
        int rankNeeded;             //Required amount of items for the next rank

        //Set rank according to values in ranks list
        for(int i = 10; i >= 0; i--)
            if(count >= ranks[i]){
               rank = i;
               break;
            }

        //Set values using new rank value
        progress = count-ranks[rank];
        rankNeeded = ranks[rank+1]-ranks[rank];

        //GUI
        countLabel.setText("Count: " + count);
        nextRankLabel.setText("Progress to next rank: " + progress + "/" + rankNeeded);
        progressBar.setProgress((progress*1.0) / rankNeeded);
        rankLabel.setText("Rank: " + rank);

        if(big)
            //Rank image for big achievement
            badge.setImage(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/RankBig" + rank + ".png"))));
        else
            //Rank image for other achievements
            badge.setImage(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/Rank" + rank + ".png"))));
    }

    //Getter for items
    public ObservableList<T> getItems() {
        return items;
    }

    //Getter for big
    public boolean isBig() {
        return big;
    }

    //Setter for items
    public void setItems(ObservableList<T> items) {
        this.items = items;
    }
}
