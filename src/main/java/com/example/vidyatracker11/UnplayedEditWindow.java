package com.example.vidyatracker11;

import javafx.stage.Stage;

//Window used to edit an existing unplayed game
public class UnplayedEditWindow extends UnplayedAddEditWindow {
    //Fields
    UnplayedGame game;  //Game being edited

    public UnplayedEditWindow(UnplayedGame game, Stage stage) {
        super(stage);
        this.game = game;

        //GUI
        mainLabel.setText("Edit Values for " + game.getTitle());
        doneButton.setText("Save Changes and Close Window");

        //Select status
        statusBox.getSelectionModel().select(game.getStatus());

        //Select genre
        genreBox.getSelectionModel().select(game.getGenre());

        //Select platform
        platformBox.getSelectionModel().select(game.getPlatform());

        //Set Title/Franchise text
        titleBox.setText(game.getTitle());
        franchiseBox.setText(game.getFranchise());

        //Set hours text
        hoursBox.setText("" + game.getHours());

        //Set release year text
        releaseYearBox.setText("" + game.getReleaseYear());

        //Select release month
        releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());

        //Select release day
        releaseDayBox.getSelectionModel().select(game.getReleaseDay());

        if (!game.getDeckCompatible().equals(""))
            //If deck status is not blank, select deck status
            deckBox.getSelectionModel().select(game.getDeckCompatible());
        else
            //If it is blank, select Blank
            deckBox.getSelectionModel().selectLast();

    }

    //Closes the window and saves the inputted data to the given unplayed game.
    public void saveAndQuit(Stage stage){
        //Set Status
        game.setStatus(statusBox.getSelectionModel().getSelectedItem());

        //Set Title
        game.setTitle(titleBox.getText());

        //Set Franchise
        game.setFranchise(franchiseBox.getText());

        //Set Platform
        game.setPlatform(platformBox.getSelectionModel().getSelectedItem());

        //Set Genre
        game.setGenre(genreBox.getSelectionModel().getSelectedItem());

        if (releaseYearBox.getText().equals(""))
            //If no value is entered, set year to 0
            game.setReleaseYear(0);
        else
            //A value is entered
            game.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));

        if (hoursBox.getText().equals("") || hoursBox.getText().equals("."))
            //If no value is entered, set hours to 0
            game.setHours(0);
        else
            //A value is entered
            game.setHours(Double.parseDouble(hoursBox.getText()));

        if (deckBox.getSelectionModel().getSelectedItem().equals("Blank"))
            //If Blank is selected, set deck compatible to a blank string
            game.setDeckCompatible("");
        else
            //Something other than blank is selected
            game.setDeckCompatible(deckBox.getSelectionModel().getSelectedItem());

        //Set release month and day
        game.setReleaseMonth(releaseMonthBox.getValue());
        game.setReleaseDay(releaseDayBox.getValue());

        //Update data
        ApplicationGUI.statusCountBoxUnplayed.updateData();
        ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());

        ApplicationGUI.changeMade = true;
        ApplicationGUI.setStageTitle();
        stage.close();
    }
}
