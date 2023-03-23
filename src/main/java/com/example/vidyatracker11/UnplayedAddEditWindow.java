package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class UnplayedAddEditWindow extends AddEditGame{
    //GUI
    //Hours
    Label hoursLabel = new Label("Predicted Hours:");
    TextField hoursBox = new TextField();
    VBox hoursVBox = new VBox(hoursLabel, hoursBox);

    //Deck Status
    Label deckLabel = new Label("Deck Status:");
    ChoiceBox<String> deckBox = new ChoiceBox<>();
    VBox deckVBox = new VBox(deckLabel, deckBox);

    public UnplayedAddEditWindow(Stage stage){
        super();
        //GUI
        hoursVBox.setAlignment(Pos.CENTER);
        deckVBox.setAlignment(Pos.CENTER);
        mainHBox.getChildren().addAll(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox, hoursVBox, releaseVBox, deckVBox);
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        //Set status values
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");

        //Only allow doubles for hoursBox
        hoursBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));

        //Set deck status values
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");

        doneButton.setOnAction(e -> {
            //Save game
            try{
                saveAndQuit(stage);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Save game and close window
    public abstract void saveAndQuit(Stage stage);
}
