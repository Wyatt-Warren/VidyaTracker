package com.example.vidyatracker11;

import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class PlayedGoalWindow extends GoalWindow{
    TableColumn<PlayedGameGoal, String> goalTitleColumn = new TableColumn<>("Goal");
    TableColumn<PlayedGameGoal, Integer> goalYearColumn = new TableColumn<>("Y");
    TableColumn<PlayedGameGoal, Integer> goalMonthColumn = new TableColumn<>("M");
    TableColumn<PlayedGameGoal, Integer> goalDayColumn = new TableColumn<>("D");
    TableView<PlayedGameGoal> goalTable = new TableView<>();
    SortedList<PlayedGameGoal> sortedList = new SortedList<>(GameLists.playedGoalList);

    public PlayedGoalWindow(){
        goalTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        goalYearColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));
        goalMonthColumn.setCellValueFactory(new PropertyValueFactory<>("endMonth"));
        goalDayColumn.setCellValueFactory(new PropertyValueFactory<>("endDay"));
        goalTable.getColumns().addAll(goalTitleColumn, goalYearColumn, goalMonthColumn, goalDayColumn);
        goalTable.setItems(sortedList);
        sortedList.setComparator(GoalWindow.endDateComparator);
        manageGoalsBox.getChildren().addAll(goalTable, manageGoalsButtonsBox);

        goalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                updateGoal(newSelection);
        });

        addGoalButton.setOnAction(e -> {
            Stage stage = new Stage();
            PlayedAddGoal playedAddGoal = new PlayedAddGoal(stage, goalTable);
            Scene scene = new Scene(playedAddGoal);

            //GUI
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Played Game Goal");
            stage.setScene(scene);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            stage.show();
        });

        editGoalButton.setOnAction(e -> {
            if(goalTable.getSelectionModel().getSelectedIndex() != -1) {
                Stage stage = new Stage();
                PlayedEditGoal playedEditGoal = new PlayedEditGoal(stage, goalTable);
                Scene scene = new Scene(playedEditGoal);

                //GUI
                stage.getIcons().add(ApplicationGUI.icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Add New Played Game Goal");
                stage.setScene(scene);
                scene.getStylesheets().add(ApplicationGUI.styleSheet);
                stage.show();
            }
        });

        removeGoalButton.setOnAction(e -> {
            if(goalTable.getSelectionModel().getSelectedIndex() != -1){
                GameLists.playedGoalList.remove(goalTable.getSelectionModel().getSelectedItem());
                if(goalTable.getSelectionModel().getSelectedIndex() != -1)
                    goalTable.getSelectionModel().selectFirst();
                updateGoal();
            }
        });
    }

    public void updateGoal(){
        mainLabel.setText("No Goal Selected");
        goalStatusLabel.setText("Not Started");
        startDateLabel.setText("");
        endDateLabel.setText("");
        timeProgressBar.setProgress(-1);
        goalProgressBar.setProgress(-1);
        startProgressLabel.setText("");
        currentProgressLabel.setText("");
        goalProgressLabel.setText("");
        filtersVBox.getChildren().clear();
    }

    public void updateGoal(PlayedGameGoal goal){
        //Local variables
        int currentProgress = goal.getFilter().filteredList().size() - goal.getStartProgress();
        double progressRatio;
        double timeRatio;
        long dayProgress;
        long dayTotal;

        if(currentProgress < 0)
            //Clamp currentProgress min value to 0
            currentProgress = 0;

        //Calculate ratio for progressBar
        progressRatio = (currentProgress * 1.0) / (goal.getGoalProgress() - goal.getStartProgress());

        if (progressRatio > 1)
            //Clamp progressRatio max value to 1
            progressRatio = 1;

        //Set goalProgressBar progress
        goalProgressBar.setProgress(progressRatio);

        //Days since start date
        dayProgress = ChronoUnit.DAYS.between(
                LocalDate.of(goal.getStartYear(), goal.getStartMonth(), goal.getStartDay()),
                LocalDate.of(ApplicationGUI.localDate.getYear(), ApplicationGUI.localDate.getMonthValue(), ApplicationGUI.localDate.getDayOfMonth()));

        //Days between start and end date
        dayTotal = ChronoUnit.DAYS.between(
                LocalDate.of(goal.getStartYear(), goal.getStartMonth(), goal.getStartDay()),
                LocalDate.of(goal.getEndYear(), goal.getEndMonth(), goal.getEndDay()));

        timeRatio = (dayProgress * 1.0) / dayTotal;

        if(timeRatio > 1)
            //Clamp timeRatio max value to 1
            timeRatio = 1;

        //Set timeProgressBar progress
        timeProgressBar.setProgress(timeRatio);

        //Set title text
        mainLabel.setText(goal.getTitle());

        //Set start date text
        startDateLabel.setText(String.format("%d/%2d/%2d",
                goal.getStartYear(), goal.getStartMonth(), goal.getStartDay()));

        //Set end date text
        endDateLabel.setText(String.format("%d/%2d/%2d",
                goal.getEndYear(), goal.getEndMonth(), goal.getEndDay()));

        //Set start progress text
        startProgressLabel.setText("" + goal.getStartProgress());

        //Set current progress text
        currentProgressLabel.setText("" + goal.getFilter().filteredList().size());

        //Set goal progress text
        goalProgressLabel.setText("" + goal.getGoalProgress());
    }
}
