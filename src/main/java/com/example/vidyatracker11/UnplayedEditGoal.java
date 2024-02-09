package com.example.vidyatracker11;

import javafx.stage.Stage;

public class UnplayedEditGoal extends UnplayedAddEditGoal{
    public UnplayedEditGoal(Stage stage, UnplayedGameGoal goal){
        mainLabel.setText("Edit " + goal.getTitle());

        //Set title
        titleBox.setText(goal.getTitle());

        //Set start date
        startYearBox.setText("" + goal.getStartYear());
        startMonthBox.getSelectionModel().select(goal.getStartMonth());
        startDayBox.getSelectionModel().select(goal.getStartDay());

        //Set end date
        endYearBox.setText("" + goal.getEndYear());
        endMonthBox.getSelectionModel().select(goal.getEndMonth());
        endDayBox.getSelectionModel().select(goal.getEndDay());

        //Set start progress
        progressStartBox.setText("" + goal.getStartProgress());

        //Set goal progress
        progressGoalBox.setText("" + goal.getGoalProgress());

        //Set filter
        filter = goal.getFilter();

        doneButton.setOnAction(e -> {
            //Save changes to goal
            //Local variables
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
                //Set start date
                goal.setStartYear(startYear);
                goal.setStartMonth(startMonthBox.getSelectionModel().getSelectedItem());
                goal.setStartDay(startDayBox.getSelectionModel().getSelectedItem());

                //Set end date
                goal.setEndYear(endYear);
                goal.setEndMonth(endMonthBox.getSelectionModel().getSelectedItem());
                goal.setEndDay(endDayBox.getSelectionModel().getSelectedItem());

                //Set goal progress
                goal.setGoalProgress(goalProgress);

                //Set start progress
                goal.setStartProgress(startProgress);

                //Set filter
                goal.setFilter(filter);

                ApplicationGUI.changeMade = true;
                ApplicationGUI.setStageTitle();
                stage.close();
            }else
                warningLabel.setText("End Date must be greater than Start Date. Neither can contain zeros.");
        });
    }
}