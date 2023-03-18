package com.example.vidyatracker11;

import javafx.stage.Stage;

//Window used to edit an existing played game.
public class PlayedEditWindow extends PlayedAddEditWindow {
    PlayedGame game;    //Game being edited

    public PlayedEditWindow(PlayedGame game, Stage stage) {
        super(stage);
        this.game = game;

        statusBox.getSelectionModel().select(game.getStatus());
        if (game.getIsItShort().equals("Yes") || game.getIsItShort().equals("No")) {
            shortBox.getSelectionModel().select(game.getIsItShort());
        } else {
            shortBox.getSelectionModel().selectLast();
        }
        titleBox.setText(game.getTitle());
        franchiseBox.setText(game.getFranchise());
        ratingBox.getSelectionModel().select(game.getRating());
        genreBox.getSelectionModel().select(game.getGenre());
        platformBox.getSelectionModel().select(game.getPlatform());
        releaseYearBox.setText("" + game.getReleaseYear());
        releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());
        releaseDayBox.getSelectionModel().select(game.getReleaseDay());
        completionMonthBox.getSelectionModel().select(game.getCompletionMonth());
        completionYearBox.setText("" + game.getCompletionYear());
        completionDayBox.getSelectionModel().select(game.getCompletionDay());
        if (game.getPercent100().equals("Yes") || game.getPercent100().equals("No")) {
            percentBox.getSelectionModel().select(game.getPercent100());
        } else {
            percentBox.getSelectionModel().selectLast();
        }
    }

    //Closes the window and saves the inputted data to the given game
    public void saveAndQuit(Stage stage) throws NumberFormatException{
        //Status
        game.setStatus(statusBox.getSelectionModel().getSelectedItem());

        //Short Status
        if (shortBox.getSelectionModel().getSelectedItem().equals("Blank")) {
            game.setIsItShort("");
        } else {
            game.setIsItShort(shortBox.getSelectionModel().getSelectedItem());
        }

        //Title
        game.setTitle(titleBox.getText());

        //Franchise
        game.setFranchise(franchiseBox.getText());

        //Rating
        game.setRating(ratingBox.getSelectionModel().getSelectedItem());

        //Platform
        game.setPlatform(platformBox.getSelectionModel().getSelectedItem());

        //Genre
        game.setGenre(genreBox.getSelectionModel().getSelectedItem());

        //Release Date
        if (releaseYearBox.getText().equals("")) {
            game.setReleaseYear(0);
        } else {
            game.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));
        }
        game.setReleaseMonth(releaseMonthBox.getValue());
        game.setReleaseDay(releaseDayBox.getValue());

        //Completion Date
        if (completionYearBox.getText().equals("")) {
            game.setCompletionYear(0);
        } else {
            game.setCompletionYear(Integer.parseInt(completionYearBox.getText()));
        }
        game.setCompletionMonth(completionMonthBox.getValue());
        game.setCompletionDay(completionDayBox.getValue());

        //Percent Status
        if (percentBox.getSelectionModel().getSelectedItem().equals("Blank")) {
            game.setPercent100("");
        } else {
            game.setPercent100(percentBox.getSelectionModel().getSelectedItem());
        }

        ApplicationGUI.statusCountBoxPlayed.updateData();
        ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());
        ApplicationGUI.changeMade = true;
        stage.close();
    }
}