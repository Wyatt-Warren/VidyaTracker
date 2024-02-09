package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.Comparator;

public abstract class GoalWindow extends HBox {
    //Manage goals
    Button addGoalButton = new Button("Add New Goal");
    Button editGoalButton = new Button("Edit Selected Goal");
    Button removeGoalButton = new Button("Remove Selected Goal");
    VBox manageGoalsButtonsBox = new VBox(addGoalButton, editGoalButton, removeGoalButton);
    HBox manageGoalsBox = new HBox();

    //View goal
    Label mainLabel = new Label("No Goal Selected");
    Label goalStatusLabel = new Label("Not Started");
    Label startDateLabel = new Label();
    Label endDateLabel = new Label();
    HBox dateBox = new HBox(startDateLabel, endDateLabel);
    ProgressBar timeProgressBar = new ProgressBar();
    ProgressBar goalProgressBar = new ProgressBar();
    Label startProgressLabel = new Label();
    Label currentProgressLabel = new Label();
    Label goalProgressLabel = new Label();
    HBox progressBox = new HBox(startProgressLabel, currentProgressLabel, goalProgressLabel);
    Label filtersMainLabel = new Label("Filters");
    VBox filtersVBox = new VBox();
    VBox goalVBox = new VBox(mainLabel, goalStatusLabel, dateBox, timeProgressBar,
            goalProgressBar, progressBox, filtersMainLabel, filtersVBox);

    public static final Comparator<GameGoal> endDateComparator = (o1, o2) -> {                     //Sort by release date
        //Local variables
        String sortBy1;         //String to sort with for o1
        String sortBy2;         //String to sort with for o2

        sortBy1 = String.format("%010d%02d%02d", o1.getEndYear(),
                o1.getEndMonth(), o1.getEndDay());
        sortBy2 = String.format("%010d%02d%02d", o2.getEndYear(),
                o2.getEndMonth(), o2.getEndDay());

        return sortBy1.compareTo(sortBy2);
    };

    public GoalWindow(){
        manageGoalsButtonsBox.setSpacing(5);
        manageGoalsBox.setSpacing(5);
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:32;");
        goalStatusLabel.setStyle("-fx-font-size: 18;-fx-font-weight: bold;");
        startDateLabel.setMaxWidth(Double.MAX_VALUE);
        startDateLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        HBox.setHgrow(startDateLabel, Priority.ALWAYS);
        endDateLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        endDateLabel.setMaxWidth(Double.MAX_VALUE);
        endDateLabel.setTextAlignment(TextAlignment.RIGHT);
        timeProgressBar.setMaxWidth(Double.MAX_VALUE);
        goalProgressBar.setMaxWidth(Double.MAX_VALUE);
        startProgressLabel.setMaxWidth(Double.MAX_VALUE);
        startProgressLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        HBox.setHgrow(startProgressLabel, Priority.ALWAYS);
        currentProgressLabel.setMaxWidth(Double.MAX_VALUE);
        currentProgressLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        HBox.setHgrow(currentProgressLabel, Priority.ALWAYS);
        goalProgressLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        goalProgressLabel.setMaxWidth(Double.MAX_VALUE);
        goalProgressLabel.setTextAlignment(TextAlignment.RIGHT);
        HBox.setHgrow(progressBox, Priority.ALWAYS);
        filtersMainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:32;");
        goalVBox.setSpacing(10);
        goalVBox.setAlignment(Pos.TOP_CENTER);
        goalVBox.setMinWidth(500);
        getChildren().addAll(manageGoalsBox, goalVBox);
        setSpacing(40);
        setPadding(new Insets(5));
    }
}
