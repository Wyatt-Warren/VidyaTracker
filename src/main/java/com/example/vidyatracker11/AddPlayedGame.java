package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//The window for adding a new game to the played game list.
public class AddPlayedGame extends AddEditGame {

    //Short Status
    Label shortLabel = new Label("Short Status:");
    ChoiceBox<String> shortBox = new ChoiceBox<>();
    VBox shortVBox = new VBox(shortLabel, shortBox);

    //Rating
    Label ratingLabel = new Label("Rating");
    ChoiceBox<Integer> ratingBox = new ChoiceBox<>();
    VBox ratingVBox = new VBox(ratingLabel, ratingBox);

    //Completion Date
    Label completionLabel = new Label("Completion Date:");
    //Year
    Label completionYearLabel = new Label("Year:");
    TextField completionYearBox = new TextField();
    HBox completionYearHBox = new HBox(completionYearLabel, completionYearBox);
    //Month
    Label completionMonthLabel = new Label("Month:");
    ChoiceBox<Integer> completionMonthBox = new ChoiceBox<>();
    HBox completionMonthHBox = new HBox(completionMonthLabel, completionMonthBox);
    //Day
    Label completionDayLabel = new Label("Day:");
    ChoiceBox<Integer> completionDayBox = new ChoiceBox<>();
    HBox completionDayHBox = new HBox(completionDayLabel, completionDayBox);
    VBox completionVBox = new VBox(completionLabel, completionYearHBox, completionMonthHBox,
            completionDayHBox);

    //100Percent Status
    Label percentLabel = new Label("100% Status:");
    ChoiceBox<String> percentBox = new ChoiceBox<>();
    VBox percentVBox = new VBox(percentLabel, percentBox);

    public AddPlayedGame(Stage stage) {
        super();
        mainLabel.setText("Add New Played Game");
        mainHBox.getChildren().addAll(statusVBox, shortVBox, titleVBox,
                franchiseVBox, ratingVBox, platformVBox,
                genreVBox, releaseVBox, completionVBox,
                percentVBox);
        doneButton.setText("Create New Played Game");
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        completionYearHBox.setAlignment(Pos.CENTER);
        completionMonthHBox.setAlignment(Pos.CENTER);
        completionDayHBox.setAlignment(Pos.CENTER);
        shortVBox.setAlignment(Pos.CENTER);
        ratingVBox.setAlignment(Pos.CENTER);
        completionVBox.setAlignment(Pos.CENTER);
        percentVBox.setAlignment(Pos.CENTER);

        //Status
        statusBox.getItems().addAll("Playing", "Completed", "On Hold");
        statusBox.getSelectionModel().selectFirst();

        //Short Status
        shortBox.getItems().addAll("Yes", "No", "Blank");
        shortBox.getSelectionModel().selectLast();

        //Rating
        ratingBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ratingBox.getSelectionModel().selectFirst();

        //Genre
        genreBox.getSelectionModel().selectFirst();

        //Platform
        platformBox.getSelectionModel().selectFirst();

        //Completion Date
        completionYearBox.setTextFormatter(new TextFormatter<>(integerFilter));
        completionYearBox.textProperty().addListener(e ->
                setDayCount(completionMonthBox.getSelectionModel().getSelectedItem(), completionDayBox, completionYearBox));
        completionMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        completionMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt, completionDayBox, completionYearBox);
        });
        completionMonthBox.getSelectionModel().selectFirst();
        completionDayBox.getSelectionModel().selectFirst();

        //100 Percent Status
        percentBox.getItems().addAll("Yes", "No", "Blank");
        percentBox.getSelectionModel().selectLast();

        doneButton.setOnAction(e -> {
            try{
                saveAndQuit(stage);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and creates a new game with the inputted data
    public void saveAndQuit(Stage stage) throws NumberFormatException{
        //Set Release Year
        int releaseYear;
        if (releaseYearBox.getText().equals("")) {
            releaseYear = 0;
        } else {
            releaseYear = Integer.parseInt(releaseYearBox.getText());
        }

        //Create new game object
        PlayedGame newGame = new PlayedGame(titleBox.getText(), statusBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem(), genreBox.getSelectionModel().getSelectedItem(),
                releaseYear, releaseMonthBox.getValue(), releaseDayBox.getValue());

        //Set short status
        if ((shortBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
            newGame.setIsItShort("");
        } else {
            newGame.setIsItShort(shortBox.getSelectionModel().getSelectedItem());
        }

        //Set Franchise
        newGame.setFranchise(franchiseBox.getText());

        //Set Rating
        newGame.setRating(ratingBox.getSelectionModel().getSelectedItem());

        //Set Completion Date
        if (completionYearBox.getText().equals("")) {
            newGame.setCompletionYear(0);
        } else {
            newGame.setCompletionYear(Integer.parseInt(completionYearBox.getText()));
        }
        newGame.setCompletionMonth(completionMonthBox.getValue());
        newGame.setCompletionDay(completionDayBox.getValue());

        //Set 100Percent status
        if ((percentBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
            newGame.setPercent100("");
        } else {
            newGame.setPercent100(percentBox.getSelectionModel().getSelectedItem());
        }

        GameLists.playedList.add(newGame);
        ApplicationGUI.statusCountBoxPlayed.updateData();
        ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());
        ApplicationGUI.changeMade = true;
        stage.close();
    }
}