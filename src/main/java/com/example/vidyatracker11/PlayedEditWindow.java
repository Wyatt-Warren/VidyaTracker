package com.example.vidyatracker11;

import javafx.stage.Stage;

//Window used to edit an existing played game.
public class PlayedEditWindow extends PlayedAddEditWindow {
    //Fields
    PlayedGame game;    //Game being edited

    public PlayedEditWindow(PlayedGame game, Stage stage) {
        super(stage);
        this.game = game;

        //GUI
        mainLabel.setText("Edit Values for " + game.getTitle());
        doneButton.setText("Save Changes and Close Window");

        //Set status value
        statusBox.getSelectionModel().select(game.getStatus());

        if (!game.getShortStatus().equals(""))
            //If short value is yes or no, select yes or no
            shortBox.getSelectionModel().select(game.getShortStatus());
        else
            //If short value is a blank string, select blank
            shortBox.getSelectionModel().selectLast();

        //Set title value
        titleBox.setText(game.getTitle());

        //Set franchise value
        franchiseBox.setText(game.getFranchise());

        //Set rating
        ratingBox.getSelectionModel().select(game.getRating());

        //Set genre value
        genreBox.getSelectionModel().select(game.getGenre());

        //Set platform value
        platformBox.getSelectionModel().select(game.getPlatform());

        //Set release date value
        releaseYearBox.setText("" + game.getReleaseYear());
        releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());
        releaseDayBox.getSelectionModel().select(game.getReleaseDay());

        //Set completion date value
        completionMonthBox.getSelectionModel().select(game.getCompletionMonth());
        completionYearBox.setText("" + game.getCompletionYear());
        completionDayBox.getSelectionModel().select(game.getCompletionDay());

        if (!game.getPercent100().equals(""))
            //If 100% value is yes or no, select yes or no
            percentBox.getSelectionModel().select(game.getPercent100());
        else
            //If 100% value is a blank string, select blank
            percentBox.getSelectionModel().selectLast();
    }

    //Closes the window and saves the inputted data to the given game
    public void saveAndQuit(Stage stage) throws NumberFormatException{
        //Set status
        game.setStatus(statusBox.getSelectionModel().getSelectedItem());

        if (shortBox.getSelectionModel().getSelectedItem().equals("Blank"))
            //If short status is blank, set short status to a blank string
            game.setShortStatus("");
        else
            //Short status is not blank
            game.setShortStatus(shortBox.getSelectionModel().getSelectedItem());

        //Set title
        game.setTitle(titleBox.getText());

        //Set franchise
        game.setFranchise(franchiseBox.getText());

        //Set rating
        game.setRating(ratingBox.getSelectionModel().getSelectedItem());

        //Set platform
        game.setPlatform(platformBox.getSelectionModel().getSelectedItem());

        //Set genre
        game.setGenre(genreBox.getSelectionModel().getSelectedItem());

        if (releaseYearBox.getText().equals(""))
            //If no text is entered, set release year to 0
            game.setReleaseYear(0);
        else
            //There is text
            game.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));

        //Set release month and day
        game.setReleaseMonth(releaseMonthBox.getValue());
        game.setReleaseDay(releaseDayBox.getValue());

        if (completionYearBox.getText().equals(""))
            //If no text is entered, set completion year to 0
            game.setCompletionYear(0);
        else
            //There is text
            game.setCompletionYear(Integer.parseInt(completionYearBox.getText()));

        //Set completion month and day
        game.setCompletionMonth(completionMonthBox.getValue());
        game.setCompletionDay(completionDayBox.getValue());

        if (percentBox.getSelectionModel().getSelectedItem().equals("Blank"))
            //If 100% is blank, set percent status to a blank string
            game.setPercent100("");
        else
            //100% is not blank
            game.setPercent100(percentBox.getSelectionModel().getSelectedItem());

        //Update data
        ApplicationGUI.statusCountBoxPlayed.updateData();
        ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());

        ApplicationGUI.changeMade = true;
        ApplicationGUI.setStageTitle();
        stage.close();
    }
}