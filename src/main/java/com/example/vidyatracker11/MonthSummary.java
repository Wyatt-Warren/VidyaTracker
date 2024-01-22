package com.example.vidyatracker11;

import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class MonthSummary extends TimePeriodSummary{
    //GUI
    ChoiceBox<String> switchPeriodChoicesMonth = new ChoiceBox<>();

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
        switchPeriodBox.getChildren().clear();
        switchPeriodBox.getChildren().addAll(switchPeriodLabel, switchPeriodChoicesMonth, switchPeriodChoices, switchPeriodButton);

        //Populate switchPeriodChoices
        //Variables
        int minMonth = Integer.MAX_VALUE;                           //Lowest month value
        int maxMonth = ApplicationGUI.localDate.getMonthValue();    //Highest month value
        minYear = Integer.MAX_VALUE;
        maxYear = ApplicationGUI.localDate.getYear();
        noDates = true;

        for(PlayedGame game : GameLists.playedList){
            //Iterate through each game to find min and max years and months
            if(game.getCompletionYear() == 0 || game.getCompletionMonth() == 0)
                //Game has no completion year or month
                continue;

            //There is at least one game with a completion year and month
            noDates = false;

            if(game.getCompletionYear() < minYear && game.getCompletionMonth() != 0||
                    (game.getCompletionYear() == minYear && game.getCompletionMonth() < minMonth)) {
                //New lowest month value
                minYear = game.getCompletionYear();
                minMonth = game.getCompletionMonth();
            }

            if((game.getCompletionYear() > maxYear && game.getCompletionMonth() != 0) ||
                    (game.getCompletionYear() == maxYear && game.getCompletionMonth() > maxMonth)) {
                //New highest month value
                maxYear = game.getCompletionYear();
                maxMonth = game.getCompletionMonth();
            }
        }

        if(!noDates) {
            for(int i = maxYear; i >= minYear; i--)
                //Add each year between min and max to switchPeriodChoices
                switchPeriodChoices.getItems().add(i);

            //There are completion dates
            for(int i = 1; i <= 12; i++)
                switchPeriodChoicesMonth.getItems().add(SpecificMonth.months[i]);

            switchPeriodButton.setDisable(false);
        } else
            //There are no completion dates
            switchPeriodButton.setDisable(true);

        switchPeriodChoicesMonth.getSelectionModel().select(SpecificMonth.months[thisMonth.getMonth()]);
        switchPeriodChoices.getSelectionModel().select((Integer) thisMonth.getYear());

        switchPeriodChoicesMonth.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
            //When month is selected, if month and year are current year, disable button
            switchPeriodButton.setDisable((int) newNum >= ApplicationGUI.localDate.getMonthValue() - 1 &&
                switchPeriodChoices.getSelectionModel().getSelectedItem() == ApplicationGUI.localDate.getYear())
        );

        switchPeriodChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
            //When year is selected, if month and year are current year, disable button
            switchPeriodButton.setDisable(switchPeriodChoicesMonth.getSelectionModel().getSelectedIndex() >= ApplicationGUI.localDate.getMonthValue() - 1 &&
                switchPeriodChoices.getItems().get((int) newNum) == ApplicationGUI.localDate.getYear())
        );

        switchPeriodChoicesMonth.getSelectionModel().select(SpecificMonth.months[thisMonth.getMonth()]);
        switchPeriodChoices.getSelectionModel().select((Integer) thisMonth.getYear());

        switchPeriodButton.setOnAction(e-> {
            MonthSummary newMonthSummary =
                    new MonthSummary(
                            new SpecificMonth(switchPeriodChoicesMonth.getSelectionModel().getSelectedIndex() + 1,
                                    switchPeriodChoices.getSelectionModel().getSelectedItem()), stage);
            stage.getScene().setRoot(newMonthSummary);
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
