package com.example.vidyatracker11;

import javafx.collections.transformation.FilteredList;
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
    TableColumn<PlayedGameGoal, String> ongoingTitleColumn = new TableColumn<>("Goal");
    TableColumn<PlayedGameGoal, Integer> ongoingYearColumn = new TableColumn<>("Y");
    TableColumn<PlayedGameGoal, Integer> ongoingMonthColumn = new TableColumn<>("M");
    TableColumn<PlayedGameGoal, Integer> ongoingDayColumn = new TableColumn<>("D");
    TableView<PlayedGameGoal> ongoingTable = new TableView<>();
    TableColumn<PlayedGameGoal, String> endedCompletedColumn = new TableColumn<>("Completed?");
    TableColumn<PlayedGameGoal, String> endedTitleColumn = new TableColumn<>("Goal");
    TableColumn<PlayedGameGoal, Integer> endedYearColumn = new TableColumn<>("Y");
    TableColumn<PlayedGameGoal, Integer> endedMonthColumn = new TableColumn<>("M");
    TableColumn<PlayedGameGoal, Integer> endedDayColumn = new TableColumn<>("D");
    TableView<PlayedGameGoal> endedTable = new TableView<>();
    SortedList<PlayedGameGoal> sortedList = new SortedList<>(GameLists.playedGoalList);
    FilteredList<PlayedGameGoal> ongoingList = new FilteredList<>(sortedList);
    FilteredList<PlayedGameGoal> endedList = new FilteredList<>(sortedList);

    public PlayedGoalWindow(){
        //GUI
        ongoingTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        ongoingYearColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));
        ongoingMonthColumn.setCellValueFactory(new PropertyValueFactory<>("endMonth"));
        ongoingDayColumn.setCellValueFactory(new PropertyValueFactory<>("endDay"));
        ongoingTable.getColumns().addAll(ongoingTitleColumn, ongoingYearColumn, ongoingMonthColumn, ongoingDayColumn);
        ongoingTable.setItems(ongoingList);
        ongoingTable.setPrefWidth(400);
        ongoingTable.setMaxWidth(400);
        endedTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        endedYearColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));
        endedMonthColumn.setCellValueFactory(new PropertyValueFactory<>("endMonth"));
        endedDayColumn.setCellValueFactory(new PropertyValueFactory<>("endDay"));
        endedTable.getColumns().addAll(endedTitleColumn, endedYearColumn, endedMonthColumn, endedDayColumn, endedCompletedColumn);
        endedTable.setItems(endedList);
        endedTable.setPrefWidth(400);
        endedTable.setMaxWidth(400);
        TableMethods.preventColumnSorting(ongoingTable);
        TableMethods.preventColumnResizing(ongoingTable);
        TableMethods.preventColumnReordering(ongoingTable);
        TableMethods.preventColumnSorting(endedTable);
        TableMethods.preventColumnResizing(endedTable);
        TableMethods.preventColumnReordering(endedTable);
        sortedList.setComparator(GoalWindow.endDateComparator);
        ongoingList.setPredicate(p -> !LocalDate.of(p.getEndYear(), p.getEndMonth(), p.getEndDay()).isBefore(ApplicationGUI.localDate));
        endedList.setPredicate(p -> LocalDate.of(p.getEndYear(), p.getEndMonth(), p.getEndDay()).isBefore(ApplicationGUI.localDate));
        tableBox.getChildren().add(1, ongoingTable);
        tableBox.getChildren().add(3, endedTable);

        ongoingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            //When goal is selected on table, update display
            if (newSelection != null) {
                //If something is selected
                updateGoal(newSelection);
                endedTable.getSelectionModel().clearSelection();
            }
        });

        ongoingTable.setRowFactory(tv -> {
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

        endedCompletedColumn.setCellValueFactory(cellData -> {
            PlayedGameGoal goal = cellData.getValue();
            String cellMessage;

            if(goal.getFilter().getFilterCount(goal.isCountAllCollection()) >= goal.getGoalProgress())
                cellMessage = "Yes";
            else
                cellMessage = "No";

            String finalCellMessage = cellMessage;
            return javafx.beans.binding.Bindings.createObjectBinding(() -> finalCellMessage);
        });

        endedCompletedColumn.setCellFactory(e -> new TableCell<>() {
            //Status column cell factory
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {
                    //Set text
                    setText(item);

                    //Set color
                    setStyle(ApplicationGUI.colorMap.get(item));
                }
            }
        });

        endedTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            //When goal is selected on table, update display
            if (newSelection != null) {
                //If something is selected
                updateGoal(newSelection);
                ongoingTable.getSelectionModel().clearSelection();
            }
        });

        endedTable.setRowFactory(tv -> {
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

        //Select a goal, ongoing has priority
        endedTable.getSelectionModel().selectFirst();
        ongoingTable.getSelectionModel().selectFirst();

        addGoalButton.setOnAction(e -> {
            //Open add goal window
            //Local variables
            Stage stage = new Stage();
            PlayedAddGoal playedAddGoal = new PlayedAddGoal(stage, ongoingTable, endedTable);
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
            //Local variables
            TableView<PlayedGameGoal> selectedTable;
            Stage stage = new Stage();
            PlayedEditGoal playedEditGoal;
            Scene scene;

            if(ongoingTable.getSelectionModel().getSelectedIndex() != -1)
                //Current table is ongoing table
                selectedTable = ongoingTable;
            else if(endedTable.getSelectionModel().getSelectedIndex() != -1)
                //Current table is ended table
                selectedTable = endedTable;
            else
                //No table is selected, do nothing
                return;

            //Set values after table is discovered
            playedEditGoal = new PlayedEditGoal(stage, selectedTable, this);
            scene = new Scene(playedEditGoal);

            //GUI
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Played Game Goal");
            stage.setScene(scene);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            stage.show();
        });

        removeGoalButton.setOnAction(e -> {
            //Remove the currently selected goal
            //Local variables
            TableView<PlayedGameGoal> selectedTable;
            PlayedGameGoal goal;
            Stage stage = new Stage();
            Label label = new Label();
            Label label1 = new Label("Are you sure?");
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");
            HBox hbox = new HBox(yesButton, noButton);
            VBox vbox = new VBox(label, label1, hbox);
            Scene scene = new Scene(vbox);

            if(ongoingTable.getSelectionModel().getSelectedIndex() != -1)
                //Current table is ongoing table
                selectedTable = ongoingTable;
            else if(endedTable.getSelectionModel().getSelectedIndex() != -1)
                //Current table is ended table
                selectedTable = endedTable;
            else
                //No table is selected, do nothing
                return;

            //Set goal after selected table is discovered
            goal = selectedTable.getSelectionModel().getSelectedItem();

            //GUI
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Played Game Goal");
            stage.setScene(scene);
            label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
            label.setText("Remove " + goal.getTitle());
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
                GameLists.playedGoalList.remove(selectedTable.getSelectionModel().getSelectedItem());

                if(GameLists.playedGoalList.size() != 0) {
                    //If there are goals remaining, select the first one
                    selectedTable.getSelectionModel().clearSelection();
                    selectedTable.getSelectionModel().selectFirst();
                }else
                    //Otherwise update the display for no goals selected
                    updateGoal();

                //Update table
                TableMethods.updateColumnWidth(selectedTable.getColumns());
                selectedTable.refresh();

                ApplicationGUI.changeMade = true;
                ApplicationGUI.setStageTitle();
                stage.close();
            });

            noButton.setOnAction(e1 -> stage.close());

            stage.show();
        });

        //Update tables
        TableMethods.updateColumnWidth(endedTable.getColumns());
        endedTable.refresh();
        TableMethods.updateColumnWidth(ongoingTable.getColumns());
        ongoingTable.refresh();
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
        int currentProgress = goal.getFilter().getFilterCount(goal.isCountAllCollection()) - goal.getStartProgress();
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
        currentProgressLabel.setText("Progress\n" + goal.getFilter().getFilterCount(goal.isCountAllCollection()));

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
