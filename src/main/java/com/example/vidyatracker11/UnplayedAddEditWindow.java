package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class UnplayedAddEditWindow extends AddEditGame{
    //GUI
    //Date Added
    Label addedLabel = new Label("Date Added:");
    //Year
    Label addedYearLabel = new Label("Year:");
    TextField addedYearBox = new TextField();
    HBox addedYearHBox = new HBox(addedYearLabel, addedYearBox);
    //Month
    Label addedMonthLabel = new Label("Month:");
    ChoiceBox<Integer> addedMonthBox = new ChoiceBox<>();
    HBox addedMonthHBox = new HBox(addedMonthLabel, addedMonthBox);
    //Day
    Label addedDayLabel = new Label("Day:");
    ChoiceBox<Integer> addedDayBox = new ChoiceBox<>();
    HBox addedDayHBox = new HBox(addedDayLabel, addedDayBox);
    Button addedCurrentDateButton = new Button("Enter Current Date");
    VBox addedVBox = new VBox(addedLabel, addedYearHBox, addedMonthHBox,
            addedDayHBox, addedCurrentDateButton);

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
        addedYearHBox.setAlignment(Pos.CENTER);
        addedMonthHBox.setAlignment(Pos.CENTER);
        addedDayHBox.setAlignment(Pos.CENTER);
        addedVBox.setAlignment(Pos.CENTER);
        addedVBox.setSpacing(5);
        hoursVBox.setAlignment(Pos.CENTER);
        deckVBox.setAlignment(Pos.CENTER);
        mainHBox.getChildren().addAll(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox, releaseVBox, addedVBox, hoursVBox, deckVBox);
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        //Set status values
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist", "Ignored");

        //Only allow integers for addedYearBox
        addedYearBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Update addedYearBox if value is changed
        addedYearBox.textProperty().addListener(e ->
                setDayCount(addedMonthBox.getSelectionModel().getSelectedItem(), addedDayBox, addedYearBox));

        //Set added month values
        addedMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        addedMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            //Set day count when month is changed
            setDayCount((int) newNum, addedDayBox, addedYearBox);
        });

        addedCurrentDateButton.setOnAction(e -> {
            //Set year
            addedYearBox.setText("" + ApplicationGUI.localDate.getYear());

            //Set month
            addedMonthBox.getSelectionModel().select(ApplicationGUI.localDate.getMonthValue());

            //Set day
            addedDayBox.getSelectionModel().select(ApplicationGUI.localDate.getDayOfMonth());
        });

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
