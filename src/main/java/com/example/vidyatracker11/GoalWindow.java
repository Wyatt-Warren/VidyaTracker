package com.example.vidyatracker11;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.Comparator;

public abstract class GoalWindow extends HBox {
    //GUI
    //Manage goals
    Label ongoingGoalsLabel = new Label("Ongoing Goals");
    Label endedGoalsLabel = new Label("Ended Goals");
    VBox tableBox = new VBox(ongoingGoalsLabel, endedGoalsLabel);
    Button addGoalButton = new Button("Add New Goal");
    Button editGoalButton = new Button("Edit Selected Goal");
    Button removeGoalButton = new Button("Remove Selected Goal");
    VBox manageGoalsButtonsBox = new VBox(addGoalButton, editGoalButton, removeGoalButton);
    HBox manageGoalsBox = new HBox(tableBox, manageGoalsButtonsBox);

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
    VBox filtersVBox = new VBox(new Label("No Filters"));
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
        //GUI
        ongoingGoalsLabel.setStyle("-fx-font-weight:bold;-fx-font-size:16;");
        endedGoalsLabel.setStyle("-fx-font-weight:bold;-fx-font-size:16;");
        tableBox.setSpacing(10);
        tableBox.setAlignment(Pos.TOP_CENTER);
        manageGoalsButtonsBox.setSpacing(5);
        manageGoalsBox.setSpacing(5);
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:32;");
        goalStatusLabel.setStyle("-fx-font-size: 18;-fx-font-weight: bold;");
        startDateLabel.setTextAlignment(TextAlignment.CENTER);
        startDateLabel.setMaxWidth(Double.MAX_VALUE);
        startDateLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        HBox.setHgrow(startDateLabel, Priority.ALWAYS);
        endDateLabel.setTextAlignment(TextAlignment.CENTER);
        endDateLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        endDateLabel.setMaxWidth(Double.MAX_VALUE);
        timeProgressBar.setMaxWidth(Double.MAX_VALUE);
        goalProgressBar.setMaxWidth(Double.MAX_VALUE);
        startProgressLabel.setTextAlignment(TextAlignment.CENTER);
        startProgressLabel.setMaxWidth(Double.MAX_VALUE);
        startProgressLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        HBox.setHgrow(startProgressLabel, Priority.ALWAYS);
        currentProgressLabel.setTextAlignment(TextAlignment.CENTER);
        currentProgressLabel.setMaxWidth(Double.MAX_VALUE);
        currentProgressLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        HBox.setHgrow(currentProgressLabel, Priority.ALWAYS);
        goalProgressLabel.setTextAlignment(TextAlignment.CENTER);
        goalProgressLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        goalProgressLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(progressBox, Priority.ALWAYS);
        filtersMainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:32;");
        goalVBox.setSpacing(10);
        goalVBox.setAlignment(Pos.TOP_CENTER);
        goalVBox.setMinWidth(500);
        getChildren().addAll(manageGoalsBox, goalVBox);
        setSpacing(40);
        setPadding(new Insets(5));
    }

    //Returns a string of the items in a list in a readable format, up to 3 items.
    public String listItems(String label, ObservableList<?> list){
        //Local variables
        StringBuilder output = new StringBuilder(label);

        for(int i = 0; i < 3; i++){
            //Loop 3 times
            //Add item to string
            output.append(list.get(i));

            if(list.size() > i+1)
                //If there are more items, add a comma
                output.append(", ");
            else
                //Otherwise, stop looping
                break;
        }

        if(list.size() > 3)
            //If there is still more add ...
            output.append("...");

        return output.toString();
    }
}
