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
    public void saveAndQuit(Stage stage){
        //Local variables
        int releaseYear;    //Text entered in the releaseYearBox

        if (releaseYearBox.getText().equals(""))
            //If no text is entered, set releaseYear to 0
            releaseYear = 0;
        else
            //There is text
            releaseYear = Integer.parseInt(releaseYearBox.getText());

        //Create new game object
        PlayedGame newGame = new PlayedGame(titleBox.getText(), statusBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem(), genreBox.getSelectionModel().getSelectedItem(),
                releaseYear, releaseMonthBox.getValue(), releaseDayBox.getValue());

        //Set short status
        if ((shortBox.getSelectionModel().getSelectedItem()).equals("Blank"))
            //If Blank is selected, set short to a blank string
            newGame.setShortStatus("");
        else
            //Something other than Blank is selected
            newGame.setShortStatus(shortBox.getSelectionModel().getSelectedItem());

        //Set Franchise
        newGame.setFranchise(franchiseBox.getText());

        //Set Rating
        newGame.setRating(ratingBox.getSelectionModel().getSelectedItem());

        //Set Completion Year
        if (completionYearBox.getText().equals(""))
            //If no text is entered, set completionYear to 0
            newGame.setCompletionYear(0);
        else
            //Text is entered
            newGame.setCompletionYear(Integer.parseInt(completionYearBox.getText()));

        //Set Completion month and day
        newGame.setCompletionMonth(completionMonthBox.getValue());
        newGame.setCompletionDay(completionDayBox.getValue());

        //Set 100Percent status
        if ((percentBox.getSelectionModel().getSelectedItem()).equals("Blank"))
            //If Blank is selected, set 100% status to a blank string
            newGame.setPercent100("");
        else
            //Something other than Blank is selected
            newGame.setPercent100(percentBox.getSelectionModel().getSelectedItem());

        //Add new game to list
        GameLists.playedList.add(newGame);

        //Update data
        ApplicationGUI.statusCountBoxPlayed.updateData();
        ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());

        ApplicationGUI.changeMade = true;
        ApplicationGUI.setStageTitle();
        stage.close();
    }
}