package com.example.vidyatracker11;

import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PlayedGoalWindow extends GoalWindow{
    //GUI
    TableColumn<PlayedGameGoal, String> goalTitleColumn = new TableColumn<>("Goal");
    TableColumn<PlayedGameGoal, Integer> goalYearColumn = new TableColumn<>("Y");
    TableColumn<PlayedGameGoal, Integer> goalMonthColumn = new TableColumn<>("M");
    TableColumn<PlayedGameGoal, Integer> goalDayColumn = new TableColumn<>("D");
    TableView<PlayedGameGoal> goalTable = new TableView<>();
    SortedList<PlayedGameGoal> sortedList = new SortedList<>(GameLists.playedGoalList);

    public PlayedGoalWindow(){
        //GUI
        goalTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        goalYearColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));
        goalMonthColumn.setCellValueFactory(new PropertyValueFactory<>("endMonth"));
        goalDayColumn.setCellValueFactory(new PropertyValueFactory<>("endDay"));
        goalTable.getColumns().addAll(goalTitleColumn, goalYearColumn, goalMonthColumn, goalDayColumn);
        goalTable.setItems(sortedList);
        goalTable.setPrefWidth(350);
        goalTable.setMaxWidth(350);
        TableMethods.preventColumnSorting(goalTable);
        TableMethods.preventColumnResizing(goalTable);
        TableMethods.preventColumnReordering(goalTable);
        sortedList.setComparator(GoalWindow.endDateComparator);
        manageGoalsBox.getChildren().addAll(goalTable, manageGoalsButtonsBox);

        goalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            //When goal is selected on table, update display
            if (newSelection != null)
                //If something is selected
                updateGoal(newSelection);
        });
        goalTable.getSelectionModel().selectFirst();

        goalTable.setRowFactory(tv -> {
            //Row factory
            //Local variables
            TableRow<PlayedGameGoal> row = new TableRow<>();    //The row

            row.setOnMouseClicked(event -> {
                //Mouse is clicked

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2)
                    //Mouse double-clicks a non-empty row
                    //Open edit game window
                    editGoalButton.fire();
            });

            //Set right click menu
            row.setContextMenu(ApplicationGUI.rowContextMenu);

            return row;
        });

        addGoalButton.setOnAction(e -> {
            //Open add goal window
            //Local variables
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
            //Open edit goal window for selected goal

            if(goalTable.getSelectionModel().getSelectedIndex() != -1) {
                //If a goal is selected
                //Local variables
                Stage stage = new Stage();
                PlayedEditGoal playedEditGoal = new PlayedEditGoal(stage, goalTable);
                Scene scene = new Scene(playedEditGoal);

                //GUI
                stage.getIcons().add(ApplicationGUI.icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Edit Played Game Goal");
                stage.setScene(scene);
                scene.getStylesheets().add(ApplicationGUI.styleSheet);
                stage.show();
            }
        });

        removeGoalButton.setOnAction(e -> {
            //Remove the currently selected goal

            if(goalTable.getSelectionModel().getSelectedIndex() != -1){
                //If a goal is selected
                //Local variables
                PlayedGameGoal goal = goalTable.getSelectionModel().getSelectedItem();
                Stage stage = new Stage();
                Label label = new Label("Remove " + goal.getTitle());
                Label label1 = new Label("Are you sure?");
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hbox = new HBox(yesButton, noButton);
                VBox vbox = new VBox(label, label1, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(ApplicationGUI.icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Remove Played Game Goal");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                yesButton.setStyle("-fx-font-size: 16;");
                yesButton.setPrefWidth(80);
                noButton.setStyle("-fx-font-size: 16;");
                noButton.setPrefWidth(80);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(30);
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setPadding(new Insets(10));
                scene.getStylesheets().add(ApplicationGUI.styleSheet);

                yesButton.setOnAction(e1 -> {
                    //Remove from list
                    GameLists.playedGoalList.remove(goalTable.getSelectionModel().getSelectedItem());

                    if(GameLists.playedGoalList.size() != 0) {
                        //If there are goals remaining, select the first one
                        goalTable.getSelectionModel().clearSelection();
                        goalTable.getSelectionModel().selectFirst();
                    }else
                        //Otherwise update the display for no goals selected
                        updateGoal();

                    //Update table
                    TableMethods.updateColumnWidth(goalTable.getColumns());
                    goalTable.refresh();

                    ApplicationGUI.changeMade = true;
                    ApplicationGUI.setStageTitle();
                    stage.close();
                });

                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            }
        });

        //Update table
        TableMethods.updateColumnWidth(goalTable.getColumns());
        goalTable.refresh();
    }

    //Updates display when no goal is selected
    public void updateGoal(){
        mainLabel.setText("No Goal Selected");
        goalStatusLabel.setText(GameGoal.statuses[0]);
        startDateLabel.setText("");
        endDateLabel.setText("");
        timeProgressBar.setProgress(-1);
        goalProgressBar.setProgress(-1);
        startProgressLabel.setText("");
        currentProgressLabel.setText("");
        goalProgressLabel.setText("");
        filtersVBox.getChildren().clear();
        filtersVBox.getChildren().add(new Label("No Filters"));
    }

    //Update display with information from the selected goal
    public void updateGoal(PlayedGameGoal goal){
        //Local variables
        int currentProgress = goal.getFilter().filteredList().size() - goal.getStartProgress();
        double progressRatio;
        double timeRatio;
        long dayProgress;
        long dayTotal;
        LocalDate startDate = LocalDate.of(goal.getStartYear(), goal.getStartMonth(), goal.getStartDay());
        LocalDate endDate = LocalDate.of(goal.getEndYear(), goal.getEndMonth(), goal.getEndDay());

        if(currentProgress < 0)
            //Clamp currentProgress min value to 0
            currentProgress = 0;

        if(goal.getGoalProgress() - goal.getStartProgress() == 0)
            //Any number with a goal of 0 should equal 100% complete
            progressRatio = 1;
        else
            //No divide by 0 error
            progressRatio = (currentProgress * 1.0) / (goal.getGoalProgress() - goal.getStartProgress());

        //Set goalProgressBar progress
        goalProgressBar.setProgress(progressRatio);

        //Days since start date
        dayProgress = ChronoUnit.DAYS.between(startDate, ApplicationGUI.localDate) + 1;

        //Days between start and end date
        dayTotal = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        //Ratio of time passed towards goal
        timeRatio = (dayProgress * 1.0) / dayTotal;

        //Set timeProgressBar progress
        timeProgressBar.setProgress(timeRatio);

        //Set title text
        mainLabel.setText(goal.getTitle());

        //Set start date text
        startDateLabel.setText(String.format("Start\n%d/%2d/%2d",
                goal.getStartYear(), goal.getStartMonth(), goal.getStartDay()));

        //Set end date text
        endDateLabel.setText(String.format("End\n%d/%2d/%2d",
                goal.getEndYear(), goal.getEndMonth(), goal.getEndDay()));

        //Set start progress text
        startProgressLabel.setText("Start\n" + goal.getStartProgress());

        //Set current progress text
        currentProgressLabel.setText("Progress\n" + goal.getFilter().filteredList().size());

        //Set goal progress text
        goalProgressLabel.setText("End\n" + goal.getGoalProgress());

        //Setting status
        if(ApplicationGUI.localDate.isBefore(startDate))
            //Goal has not started
            goalStatusLabel.setText(GameGoal.statuses[0]);

        else if(timeRatio <= 1){
            //Goal has started, but is not over

            if(currentProgress >= goal.getGoalProgress() - goal.getStartProgress())
                //Goal is done early
                goalStatusLabel.setText(GameGoal.statuses[4]);

            else if (progressRatio < timeRatio)
                //Progress is behind schedule
                goalStatusLabel.setText(GameGoal.statuses[1]);

            else if (progressRatio == timeRatio)
                //Progress is ahead on schedule
                goalStatusLabel.setText(GameGoal.statuses[2]);

            else if (progressRatio > timeRatio)
                //Progress is ahead of schedule
                goalStatusLabel.setText(GameGoal.statuses[3]);

        }else if(progressRatio >= 1)
            //Goal is completed successfully
            goalStatusLabel.setText(GameGoal.statuses[4]);

        else
            //Goal was failed
            goalStatusLabel.setText(GameGoal.statuses[5]);

        //Clear items from filter vbox before adding new ones.
        filtersVBox.getChildren().clear();

        if(!goal.getFilter().possibleStatuses.isEmpty())
            //Goal has filter for statuses
            filtersVBox.getChildren().add(new Label(listItems("Statuses: ", goal.getFilter().possibleStatuses)));

        if(!goal.getFilter().possibleShortStatuses.isEmpty())
            //Goal has filter for short statuses
            filtersVBox.getChildren().add(new Label(listItems("Short Statuses: ", goal.getFilter().possibleShortStatuses)));

        if(!goal.getFilter().titleContains.equals(""))
            //Goal has filter for title
            filtersVBox.getChildren().add(new Label("Title Contains: " + goal.getFilter().titleContains));

        if(!goal.getFilter().possibleFranchises.isEmpty())
            //Goal has filter for franchises
            filtersVBox.getChildren().add(new Label(listItems("Franchises: ", goal.getFilter().possibleFranchises)));

        if(!goal.getFilter().possibleRatings.isEmpty())
            //Goal has filter for ratings
            filtersVBox.getChildren().add(new Label(listItems("Ratings: ", goal.getFilter().possibleRatings)));

        if(!goal.getFilter().possiblePlatforms.isEmpty())
            //Goal has filter for platforms
            filtersVBox.getChildren().add(new Label(listItems("Platforms: ", goal.getFilter().possiblePlatforms)));

        if(!goal.getFilter().possibleGenres.isEmpty())
            //Goal has filter for genres
            filtersVBox.getChildren().add(new Label(listItems("Genres: ", goal.getFilter().possibleGenres)));

        if(goal.getFilter().minReleaseYear != 0)
            //Goal has filter for minimum release year
            filtersVBox.getChildren().add(new Label("Minimum Release Year: " + goal.getFilter().minReleaseYear));

        if(goal.getFilter().maxReleaseYear != Integer.MAX_VALUE)
            //Goal has filter for maximum release year
            filtersVBox.getChildren().add(new Label("Maximum Release Year: " + goal.getFilter().maxReleaseYear));

        if(!goal.getFilter().possible100PercentStatuses.isEmpty())
            //Goal has filter for 100 percent statuses
            filtersVBox.getChildren().add(new Label(listItems("100% Statuses: ", goal.getFilter().possible100PercentStatuses)));

        if(!goal.getFilter().possibleCollections.isEmpty())
            //Goal has filter for collections
            filtersVBox.getChildren().add(new Label(listItems("Collections: ", goal.getFilter().possibleCollections)));

        if(filtersVBox.getChildren().isEmpty())
            //If no filters were added
            filtersVBox.getChildren().add(new Label("No Filters"));

    }
}
