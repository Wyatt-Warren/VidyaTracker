package com.example.vidyatracker11;

import javafx.stage.Stage;

public class PlayedEditGoal extends PlayedAddEditGoal{
    public PlayedEditGoal(Stage stage, PlayedGameGoal goal){
        mainLabel.setText("Edit " + goal.getTitle());

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

            //Set start date
            goal.setStartYear(startYear);
            goal.setStartMonth(startMonthBox.getSelectionModel().getSelectedItem());
            goal.setStartDay(startDayBox.getSelectionModel().getSelectedItem());

            //Set end date
            goal.setEndYear(endYear);
            goal.setEndMonth(endMonthBox.getSelectionModel().getSelectedItem());
            goal.setEndDay(endDayBox.getSelectionModel().getSelectedItem());

            //Set start progress
            goal.setStartProgress(startProgress);

            //Set goal progress
            goal.setGoalProgress(goalProgress);

            //Set filter
            goal.setFilter(filter);

            ApplicationGUI.changeMade = true;
            ApplicationGUI.setStageTitle();
            stage.close();
        });
    }
}
