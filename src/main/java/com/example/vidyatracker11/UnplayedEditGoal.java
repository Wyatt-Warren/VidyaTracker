package com.example.vidyatracker11;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UnplayedEditGoal extends UnplayedAddEditGoal{
    public UnplayedEditGoal(Stage stage, TableView<UnplayedGameGoal> table, UnplayedGoalWindow window){
        //Local variables
        UnplayedGameGoal goal = table.getSelectionModel().getSelectedItem();

        //Set title of window
        mainLabel.setText("Edit " + goal.getTitle());

        //Set title
        titleBox.setText(goal.getTitle());

        //Set start date
        startYearBox.setText("" + goal.getStartYear());
        startMonthBox.getSelectionModel().select((Integer) goal.getStartMonth());
        startDayBox.getSelectionModel().select((Integer) goal.getStartDay());

        //Set end date
        endYearBox.setText("" + goal.getEndYear());
        endMonthBox.getSelectionModel().select((Integer) goal.getEndMonth());
        endDayBox.getSelectionModel().select((Integer) goal.getEndDay());

        //Set start progress
        progressStartBox.setText("" + goal.getStartProgress());

        //Set goal progress
        progressGoalBox.setText("" + goal.getGoalProgress());

        //Set end progress
        if(goal.getEndProgress() != -1)
            //There is a valid end progress
            progressEndBox.setText("" + goal.getEndProgress());

        //Set filter
        filter = goal.getFilter();

        //Set countAllCollection
        countAllCollection = goal.isCountAllCollection();

        doneButton.setOnAction(e -> {
            //Save changes to goal
            //Local variables
            int startYear = 0;
            int endYear = 0;
            int startProgress = 0;
            int goalProgress = 0;
            int endProgress = -1;
            LocalDate startDate;
            LocalDate endDate;

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

            if(!progressEndBox.getText().equals(""))
                endProgress = Integer.parseInt(progressEndBox.getText());

            if(startYear == 0 ||
                    startMonthBox.getSelectionModel().getSelectedItem() == 0 ||
                    startDayBox.getSelectionModel().getSelectedItem() == 0) {
                //If start date contains zeros
                warningLabel.setText("Start date may not contain zeros.");
                return;
            }

            if(endYear == 0 ||
                    endMonthBox.getSelectionModel().getSelectedItem() == 0 ||
                    endDayBox.getSelectionModel().getSelectedItem() == 0) {
                //If end date contains zeros
                warningLabel.setText("End date may not contain zeros.");
                return;
            }

            //Start date
            startDate = LocalDate.of(startYear, startMonthBox.getSelectionModel().getSelectedItem(), startDayBox.getSelectionModel().getSelectedItem());

            //End date
            endDate = LocalDate.of(endYear, endMonthBox.getSelectionModel().getSelectedItem(), endDayBox.getSelectionModel().getSelectedItem());

            if(endDate.isBefore(startDate)){
                //End date is before start date
                warningLabel.setText("End date may not be before start date.");
                return;
            }

            if(goalProgress > startProgress) {
                //Goal is less than start progress
                warningLabel.setText("Goal may not be greater than start progress.");
                return;
            }

            if(endProgress > startProgress){
                //End progress is greater than start progress
                warningLabel.setText("End progress may not be greater than start progress.");
                return;
            }

            //Set title
            goal.setTitle(titleBox.getText());

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

            //Set end progress
            goal.setEndProgress(endProgress);

            //Set countAllCollection
            goal.setCountAllCollection(countAllCollection);

            //Set filter
            filter.setMaxAddedDate(LocalDate.of(
                    endYear, endMonthBox.getSelectionModel().getSelectedItem(), endDayBox.getSelectionModel().getSelectedItem()));
            goal.setFilter(filter);

            //Remake the whole list as a lazy way to deal with items updating and needing to move tables
            window.ongoingList = new FilteredList<>(window.sortedList,
                    p -> !LocalDate.of(p.getEndYear(), p.getEndMonth(), p.getEndDay()).isBefore(ApplicationGUI.localDate));
            window.endedList = new FilteredList<>(window.sortedList,
                    p -> LocalDate.of(p.getEndYear(), p.getEndMonth(), p.getEndDay()).isBefore(ApplicationGUI.localDate));
            window.ongoingTable.setItems(window.ongoingList);
            window.endedTable.setItems(window.endedList);
            TableMethods.updateColumnWidth(window.ongoingTable.getColumns());
            TableMethods.updateColumnWidth(window.endedTable.getColumns());

            //Update table
            table.getSelectionModel().clearSelection();
            table.getSelectionModel().select(goal);
            table.refresh();

            ApplicationGUI.changeMade = true;
            ApplicationGUI.setStageTitle();
            stage.close();
        });
    }
}