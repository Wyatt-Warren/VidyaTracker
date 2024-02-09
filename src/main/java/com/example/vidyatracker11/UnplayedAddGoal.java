package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class UnplayedAddGoal extends UnplayedAddEditGoal{
    public UnplayedAddGoal(Stage stage){
        mainLabel.setText("Add New Unplayed Game Goal");

        //Create new empty filter
        filter = new UnplayedGameFilter(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                "", 0, Integer.MAX_VALUE, 0, Double.MAX_VALUE);

        doneButton.setOnAction(e -> {
            //Add new goal to goal list.
            //Local variables
            UnplayedGameGoal newGoal;
            int startYear = 0;
            int endYear = 0;
            int startProgress = 0;
            int goalProgress = 0;
            String startDateString;
            String endDateString;

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

            //Start date for comparing
            startDateString = String.format("%10d%2d%2d", startYear,
                    startMonthBox.getSelectionModel().getSelectedItem(),
                    startDayBox.getSelectionModel().getSelectedItem());

            //End date for comparing
            endDateString = String.format("%10d%2d%2d", endYear,
                    endMonthBox.getSelectionModel().getSelectedItem(),
                    endDayBox.getSelectionModel().getSelectedItem());

            if(startDateString.compareTo(endDateString) <= 0 &&
                    startYear != 0 &&
                    startMonthBox.getSelectionModel().getSelectedItem() != 0 &&
                    startDayBox.getSelectionModel().getSelectedItem() != 0 &&
                    endYear != 0 &&
                    endMonthBox.getSelectionModel().getSelectedItem() != 0 &&
                    endDayBox.getSelectionModel().getSelectedItem() != 0) {
                //If the start date less than end date
                newGoal = new UnplayedGameGoal(titleBox.getText(), startYear, startMonthBox.getSelectionModel().getSelectedItem(),
                        startDayBox.getSelectionModel().getSelectedItem(), endYear, endMonthBox.getSelectionModel().getSelectedItem(),
                        endDayBox.getSelectionModel().getSelectedItem(), startProgress, goalProgress, filter);

                GameLists.unplayedGoalList.add(newGoal);

                ApplicationGUI.changeMade = true;
                ApplicationGUI.setStageTitle();
                stage.close();
            }else
                warningLabel.setText("End Date must be greater than Start Date. Neither can contain zeros.");
        });
    }
}
