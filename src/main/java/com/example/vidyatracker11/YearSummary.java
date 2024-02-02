package com.example.vidyatracker11;

import javafx.stage.Stage;

public class YearSummary extends TimePeriodSummary{
    //Fields
    int thisYear;   //Year being viewed

    public YearSummary(int year, Stage stage) {
        //Set global variables
        thisYear = year;
        finishConstructing();

        //GUI
        mainLabel.setText(thisYear + " Summary");
        countSamePeriodLabel.setText("Games Released in " + year + ":  " + samePeriodGames.size());
        switchPeriodLabel.setText("Other Years: ");
        switchPeriodButton.setText("View Year Summary");

        switchPeriodChoices.getSelectionModel().select((Integer)thisYear);

        //Populate switchPeriodChoices
        //Variables
        minYear = Integer.MAX_VALUE;
        maxYear = ApplicationGUI.localDate.getYear() - 1;
        noDates = true;

        for(PlayedGame game : GameLists.playedList){
            //Iterate through each game to find min and max years
            if(game.getCompletionYear() == 0)
                //Game has no completion year
                continue;

            //There is at least one game with a completion year
            noDates = false;

            if(game.getCompletionYear() < minYear)
                //New lowest year value
                minYear = game.getCompletionYear();
        }

        if(!noDates) {
            //There are years
            for (int i = maxYear; i >= minYear; i--)
                //Add each year between min and max to switchPeriodChoices
                switchPeriodChoices.getItems().add(i);
            switchPeriodButton.setDisable(false);
        } else
            //There are no years
            switchPeriodButton.setDisable(true);

        switchPeriodButton.setOnAction(e -> {
            //Switch to new year
            YearSummary newYearSummary =
                    new YearSummary(switchPeriodChoices.getSelectionModel().getSelectedItem(), stage);
            stage.getScene().setRoot(newYearSummary);
        });

        //Disable previousPeriod if at earliest year
        previousPeriod.setDisable(thisYear == minYear);

        //Disable nextPeriod if at latest year
        nextPeriod.setDisable(thisYear == maxYear);

        previousPeriod.setOnAction(e -> {
            //Switch to previous year
            YearSummary newYearSummary =
                    new YearSummary(thisYear - 1, stage);
            stage.getScene().setRoot(newYearSummary);
        });

        nextPeriod.setOnAction(e -> {
            //Switch to next year
            YearSummary newYearSummary =
                    new YearSummary(thisYear + 1, stage);
            stage.getScene().setRoot(newYearSummary);
        });
    }

    //Set games completed in thisYear
    @Override
    public void setThisPeriodGames() {
        for(PlayedGame game : GameLists.playedList)
            //Iterate through each playedGame
            if(game.getStatus().equals("Completed") && game.getCompletionYear() == thisYear) {
                //If game was completed in this year
                thisPeriodGames.add(game);

                if(game.getReleaseYear() == thisYear)
                    //If game was released and completed in this year
                    samePeriodGames.add(game);
            }
    }
}
