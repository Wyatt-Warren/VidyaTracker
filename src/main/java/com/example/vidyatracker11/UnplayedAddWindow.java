package com.example.vidyatracker11;

import javafx.stage.Stage;

//The window for adding a new game to the unplayed game list.
public class UnplayedAddWindow extends UnplayedAddEditWindow {

    public UnplayedAddWindow(Stage stage) {
        super(stage);
        //GUI
        mainLabel.setText("Add New Unplayed Game");
        doneButton.setText("Create New Unplayed Game");

        //Select first item in each ChoiceBox
        statusBox.getSelectionModel().selectFirst();
        genreBox.getSelectionModel().selectFirst();
        platformBox.getSelectionModel().selectFirst();
        deckBox.getSelectionModel().selectLast();
    }

    //Closes the window and creates a new game based on the inputted data.
    public void saveAndQuit(Stage stage)  {
        //Local variables
        int releaseYear;    //Text entered in the releaseYearBox

        if (releaseYearBox.getText().equals(""))
            //If no text is entered, release year = 0
            releaseYear = 0;
        else
            //Text is entered
            releaseYear = Integer.parseInt(releaseYearBox.getText());

        //Create new game object
        UnplayedGame newGame = new UnplayedGame(titleBox.getText(), statusBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem(), genreBox.getSelectionModel().getSelectedItem(),
                releaseYear, releaseMonthBox.getValue(), releaseDayBox.getValue());

        //Set Franchise
        newGame.setFranchise(franchiseBox.getText());

        if (addedYearBox.getText().equals(""))
            //If no text is entered, set addedYear to 0
            newGame.setAddedYear(0);
        else
            //A value is entered
            newGame.setAddedYear(Integer.parseInt(addedYearBox.getText()));

        //Set added month and day
        newGame.setAddedMonth(addedMonthBox.getValue());
        newGame.setAddedDay(addedDayBox.getValue());

        if (hoursBox.getText().equals("") || hoursBox.getText().equals("."))
            //If no value is entered, hours = 0
            newGame.setHours(0);
        else
            //A value is entered
            newGame.setHours(Double.parseDouble(hoursBox.getText()));

        if ((deckBox.getSelectionModel().getSelectedItem()).equals("Blank"))
            //Blank should be replaced with a blank string
            newGame.setDeckCompatible("");
        else
            //Something other than Blank is selected
            newGame.setDeckCompatible(deckBox.getSelectionModel().getSelectedItem());

        //Add game to list
        GameLists.unplayedList.add(newGame);

        //Update data
        ApplicationGUI.unplayedGamesTable.baseList = ApplicationGUI.unplayedAdvancedFilter.filteredList();
        ApplicationGUI.statusCountBoxUnplayed.updateData();
        ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());

        ApplicationGUI.changeMade = true;
        ApplicationGUI.setStageTitle();
        stage.close();
    }
}