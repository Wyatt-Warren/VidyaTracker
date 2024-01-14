package com.example.vidyatracker11;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MonthSummary extends TimePeriodSummary{
    //GUI
    ChoiceBox<SpecificMonth> switchPeriodChoices = new ChoiceBox<>();

    //Fields
    SpecificMonth thisMonth;                                                            //Month being viewed

    public MonthSummary(SpecificMonth month, Stage stage) {
        //Set global variables
        thisMonth = month;
        finishConstructing();

        //GUI
        mainLabel.setText(thisMonth + " Summary");
        countSamePeriodLabel.setText("Games released in " + thisMonth + ":  " + samePeriodGames.size());
        switchPeriodLabel.setText("Other Months: ");
        switchPeriodButton.setText("View Month Summary");
        switchPeriodBox.getChildren().addAll(switchPeriodLabel, switchPeriodChoices, switchPeriodButton);

        //Populate switchPeriodChoices
        //Variables
        int minYear = Integer.MAX_VALUE;                            //Lowest year value
        int minMonth = Integer.MAX_VALUE;                           //Lowest month value
        int maxYear = ApplicationGUI.localDate.getYear();           //Highest year value
        int maxMonth = ApplicationGUI.localDate.getMonthValue();    //Highest month value
        boolean noMonths = true;                                    //True if there are no games with year data

        for(PlayedGame game : GameLists.playedList){
            //Iterate through each game to find min and max years and months
            if(game.getCompletionYear() == 0 || game.getCompletionMonth() == 0)
                //Game has no completion year or month
                continue;

            //There is at least one game with a completion year and month
            noMonths = false;

            if(game.getCompletionYear() < minYear ||
                    game.getCompletionYear() == minYear && game.getCompletionMonth() < minMonth) {
                //New lowest month value
                minYear = game.getCompletionYear();
                minMonth = game.getCompletionMonth();
            }

            if(game.getCompletionYear() > maxYear ||
                    game.getCompletionYear() == minYear && game.getCompletionMonth() > maxMonth) {
                //New highest year value
                maxYear = game.getCompletionYear();
                maxMonth = game.getCompletionMonth();
            }
        }

        if(!noMonths) {
            //There are Months
            for (int i = maxYear; i >= minYear; i--)
                //Iterate through each year
                for (int j = 12; j > 0; j--) {
                    //Iterate through each month
                    if(i == maxYear && j > maxMonth)
                        //Skip months greater than max on first year iteration
                        continue;

                    if (!(ApplicationGUI.localDate.getYear() == i && ApplicationGUI.localDate.getMonthValue() == j))
                        //Add month if not current month
                        switchPeriodChoices.getItems().add(new SpecificMonth(j, i));

                    if(i == minYear && j == minMonth)
                        //Stop on last month
                        break;
                }
            switchPeriodButton.setDisable(false);
        } else
            //There are no months
            switchPeriodButton.setDisable(true);

        switchPeriodChoices.getSelectionModel().select(thisMonth);

        switchPeriodButton.setOnAction(e-> {
            MonthSummary newMonthSummary =
                    new MonthSummary(switchPeriodChoices.getSelectionModel().getSelectedItem(), stage);
            Scene newScene = new Scene(newMonthSummary);
            newScene.getStylesheets().add(ApplicationGUI.styleSheet);
            stage.setScene(newScene);
        });
    }

    //Set games completed in thisYear and thisMonth
    @Override
    public void setThisPeriodGames() {
        for(PlayedGame game : GameLists.playedList)
            //Iterate through each playedGame
            if(game.getStatus().equals("Completed") && game.getCompletionYear() == thisMonth.getYear() && game.getCompletionMonth() == thisMonth.getMonth()) {
                //If game was completed in thisYear and thisMonth
                thisPeriodGames.add(game);

                if(game.getReleaseYear() == thisMonth.getYear() && game.getReleaseMonth() == thisMonth.getMonth())
                    samePeriodGames.add(game);
            }
    }
}
