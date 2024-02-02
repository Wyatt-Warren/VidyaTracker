package com.example.vidyatracker11;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MonthSummary extends TimePeriodSummary{
    //GUI
    ChoiceBox<String> switchPeriodChoicesMonth = new ChoiceBox<>();

    //Fields
    SpecificMonth thisMonth;    //Month being viewed

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
        switchPeriodBox.getChildren().addAll(previousPeriod, new Label("        "), switchPeriodLabel, switchPeriodChoicesMonth,
                switchPeriodChoices, switchPeriodButton, new Label("        "), nextPeriod);

        //Populate switchPeriodChoices
        //Variables
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

            if(game.getCompletionYear() < minYear && game.getCompletionMonth() != 0) {
                //New lowest month value
                minYear = game.getCompletionYear();
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

        //Select the current month
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

        //Select current month again for some reason
        switchPeriodChoicesMonth.getSelectionModel().select(SpecificMonth.months[thisMonth.getMonth()]);
        switchPeriodChoices.getSelectionModel().select((Integer) thisMonth.getYear());

        switchPeriodButton.setOnAction(e-> {
            //Switch to new month
            MonthSummary newMonthSummary =
                    new MonthSummary(
                            new SpecificMonth(switchPeriodChoicesMonth.getSelectionModel().getSelectedIndex() + 1,
                                    switchPeriodChoices.getSelectionModel().getSelectedItem()), stage);
            stage.getScene().setRoot(newMonthSummary);
        });

        //Disable previousPeriod if at the earliest month
        previousPeriod.setDisable(thisMonth.getYear() == minYear && thisMonth.getMonth() == 1);

        //Disable nextPeriod if at the latest month
        nextPeriod.setDisable(thisMonth.getYear() == ApplicationGUI.localDate.getYear() && thisMonth.getMonth() >= ApplicationGUI.localDate.getMonthValue() - 1);

        previousPeriod.setOnAction(e -> {
            //Switch to previous month
            //Local variables
            int newMonth = thisMonth.getMonth() - 1;    //Month to change to
            int newYear = thisMonth.getYear();          //Year to change to

            if(newMonth == 0) {
                //If current month was January, switch to december and year - 1.
                newMonth = 12;
                newYear--;
            }

            MonthSummary newMonthSummary = new MonthSummary(new SpecificMonth(newMonth, newYear), stage);
            stage.getScene().setRoot(newMonthSummary);
        });

        nextPeriod.setOnAction(e -> {
            //Switch to next month
            //Local variables
            int newMonth = thisMonth.getMonth() + 1;    //Month to change to
            int newYear = thisMonth.getYear();          //Year to change to

            if(newMonth == 13) {
                //If current month was December, switch to January and year + 1.
                newMonth = 1;
                newYear++;
            }

            MonthSummary newMonthSummary = new MonthSummary(new SpecificMonth(newMonth, newYear), stage);
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
