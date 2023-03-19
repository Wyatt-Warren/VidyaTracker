package com.example.vidyatracker11;

import javafx.stage.Stage;

//The window for adding a new game to the played game list.
public class PlayedAddWindow extends PlayedAddEditWindow {

    public PlayedAddWindow(Stage stage) {
        super(stage);

        //GUI
        mainLabel.setText("Add New Played Game");
        doneButton.setText("Create New Played Game");

        //Select items in each ChoiceBox
        statusBox.getSelectionModel().selectFirst();
        shortBox.getSelectionModel().selectLast();
        ratingBox.getSelectionModel().selectFirst();
        completionMonthBox.getSelectionModel().selectFirst();
        completionDayBox.getSelectionModel().selectFirst();
        percentBox.getSelectionModel().selectLast();
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