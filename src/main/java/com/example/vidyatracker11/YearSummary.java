package com.example.vidyatracker11;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class YearSummary extends TimePeriodSummary{
    //GUI
    ChoiceBox<Integer> switchPeriodChoices = new ChoiceBox<>();

    //Fields
    int thisYear;                                                                               //Year being viewed

    public YearSummary(int year, Stage stage) {
        //Set global variables
        thisYear = year;
        finishConstructing();

        //GUI
        mainLabel.setText(thisYear + " Summary");
        countSamePeriodLabel.setText("Games Released in " + year + ":  " + samePeriodGames.size());
        switchPeriodLabel.setText("Other Years: ");
        switchPeriodButton.setText("View Year Summary");
        switchPeriodBox.getChildren().addAll(switchPeriodLabel, switchPeriodChoices, switchPeriodButton);

        //Populate switchPeriodChoices
        //Variables
        int minYear = Integer.MAX_VALUE;                    //Lowest year value
        int maxYear = ApplicationGUI.localDate.getYear();   //Highest year value
        boolean noYears = true;                             //True if there are no games with year data

        for(PlayedGame game : GameLists.playedList){
            //Iterate through each game to find min and max years
            if(game.getCompletionYear() == 0)
                //Game has no completion year
                continue;

            //There is at least one game with a completion year
            noYears = false;

            if(game.getCompletionYear() < minYear)
                //New lowest year value
                minYear = game.getCompletionYear();

            if(game.getCompletionYear() > maxYear)
                //New highest year value
                maxYear = game.getCompletionYear();
        }

        if(!noYears) {
            //There are years
            for (int i = maxYear; i >= minYear; i--)
                //Add each year between min and max to switchPeriodChoices
                if (i != ApplicationGUI.localDate.getYear())
                    //Don't let user view current year since it isn't done yet
                    switchPeriodChoices.getItems().add(i);
            switchPeriodButton.setDisable(false);
        } else
            //There are no years
            switchPeriodButton.setDisable(true);

        switchPeriodChoices.getSelectionModel().select((Integer)thisYear);

        switchPeriodButton.setOnAction(e -> {
            YearSummary newYearSummary =
                    new YearSummary(switchPeriodChoices.getSelectionModel().getSelectedItem(), stage);
            Scene newScene = new Scene(newYearSummary);
            newScene.getStylesheets().add(ApplicationGUI.styleSheet);
            stage.setScene(newScene);
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
