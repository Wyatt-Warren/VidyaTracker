package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PlayedAddGoal extends PlayedAddEditGoal{
    public PlayedAddGoal(Stage stage, TableView<PlayedGameGoal> table){
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

            if(goalProgress < startProgress){
                //Goal is less than start progress
                warningLabel.setText("Goal may not be less than start progress.");
                return;
            }

            //Games completed after goal should not be counted.
            filter.setMaxCompletionDate(LocalDate.of(
                    endYear, endMonthBox.getSelectionModel().getSelectedItem(), endDayBox.getSelectionModel().getSelectedItem()));

            //Create new goal
            newGoal = new PlayedGameGoal(titleBox.getText(), startYear, startMonthBox.getSelectionModel().getSelectedItem(),
                    startDayBox.getSelectionModel().getSelectedItem(), endYear, endMonthBox.getSelectionModel().getSelectedItem(),
                    endDayBox.getSelectionModel().getSelectedItem(), startProgress, goalProgress, filter);

            //Set countAllCollection
            newGoal.setCountAllCollection(countAllCollection);

            //Add new goal
            GameLists.playedGoalList.add(newGoal);

            //Update Table
            table.getSelectionModel().clearSelection();
            table.getSelectionModel().select(newGoal);
            TableMethods.updateColumnWidth(table.getColumns());
            table.refresh();

            ApplicationGUI.changeMade = true;
            ApplicationGUI.setStageTitle();
            stage.close();
        });
    }
}
