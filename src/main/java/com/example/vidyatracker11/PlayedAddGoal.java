package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class PlayedAddGoal extends PlayedAddEditGoal{
    public PlayedAddGoal(Stage stage){
        mainLabel.setText("Add New Played Game Goal");

        //Create new empty filter
        filter = new PlayedGameFilter(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                "", 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

        doneButton.setOnAction(e -> {
            //Add new goal to goal list.
            //Local variables
            PlayedGameGoal newGoal;
            int startYear = 0;
            int endYear = 0;
            int startProgress = 0;
            int goalProgress = 0;

            if(!startYearBox.getText().equals(""))
                //If start year is entered, set value
                startYear = Integer.parseInt(startYearBox.getText());

            if(!endYearBox.getText().equals(""))
                //If end year is entered, set value
                endYear = Integer.parseInt(endYearBox.getText());

            if(!progressStartBox.getText().equals(""))
                //If start progress is entered, set value
                startProgress = Integer.parseInt(progressStartBox.getText());

            if(!progressGoalBox.getText().equals(""))
                //If goal progress is entered, set value
                goalProgress = Integer.parseInt(progressGoalBox.getText());

            newGoal = new PlayedGameGoal(titleBox.getText(), startYear, startMonthBox.getSelectionModel().getSelectedItem(),
                    startDayBox.getSelectionModel().getSelectedItem(), endYear, endMonthBox.getSelectionModel().getSelectedItem(),
                    endDayBox.getSelectionModel().getSelectedItem(), startProgress, goalProgress, filter);

            GameLists.playedGoalList.add(newGoal);

            ApplicationGUI.changeMade = true;
            ApplicationGUI.setStageTitle();
            stage.close();
        });
    }
}
