package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class AddEditGoal extends VBox {
    //GUI
    Label mainLabel = new Label();
    HBox mainHBox = new HBox();
    Label warningLabel = new Label();
    Button doneButton = new Button("Save Changes and Close Window");

    //Title
    Label titleLabel = new Label("Title:");
    TextField titleBox = new TextField();
    VBox titleVBox = new VBox(titleLabel, titleBox);

    //Start Date
    Label startDateLabel = new Label("Start Date:");
    //Year
    Label startYearLabel = new Label("Year:");
    TextField startYearBox = new TextField();
    HBox startYearHBox = new HBox(startYearLabel, startYearBox);
    //Month
    Label startMonthLabel = new Label("Month:");
    ChoiceBox<Integer> startMonthBox = new ChoiceBox<>();
    HBox startMonthHBox = new HBox(startMonthLabel, startMonthBox);
    //Day
    Label startDayLabel = new Label("Day:");
    ChoiceBox<Integer> startDayBox = new ChoiceBox<>();
    HBox startDayHBox = new HBox(startDayLabel, startDayBox);
    Button startCurrentDateButton = new Button("Enter Current Date");
    VBox startDateVBox = new VBox(startDateLabel, startYearHBox, startMonthHBox,
            startDayHBox, startCurrentDateButton);

    //End Date
    Label endDateLabel = new Label("End Date:");
    //Year
    Label endYearLabel = new Label("Year:");
    TextField endYearBox = new TextField();
    HBox endYearHBox = new HBox(endYearLabel, endYearBox);
    //Month
    Label endMonthLabel = new Label("Month:");
    ChoiceBox<Integer> endMonthBox = new ChoiceBox<>();
    HBox endMonthHBox = new HBox(endMonthLabel, endMonthBox);
    //Day
    Label endDayLabel = new Label("Day:");
    ChoiceBox<Integer> endDayBox = new ChoiceBox<>();
    HBox endDayHBox = new HBox(endDayLabel, endDayBox);
    Button endCurrentDateButton = new Button("Enter Current Date");
    VBox endDateVBox = new VBox(endDateLabel, endYearHBox, endMonthHBox,
            endDayHBox, endCurrentDateButton);

    //Progress Start
    Label progressStartLabel = new Label("Start Progress:");
    TextField progressStartBox = new TextField();
    HBox progressStartHBox = new HBox(progressStartLabel, progressStartBox);
    Button enterCurrentStartProgress = new Button("Enter Current Progress");
    VBox progressStartVBox = new VBox(progressStartHBox, enterCurrentStartProgress);

    //Progress Goal
    Label progressGoalLabel = new Label("Goal:");
    TextField progressGoalBox = new TextField();
    HBox progressGoalHBox = new HBox(progressGoalLabel, progressGoalBox);
    Button enterCurrentGoalProgress = new Button("Enter Current Progress");
    VBox progressGoalVBox = new VBox(progressGoalHBox, enterCurrentGoalProgress);

    //Filters
    Button filterButton = new Button("Manage Filters");

    public AddEditGoal() {
        //GUI
        getChildren().addAll(mainLabel, mainHBox, warningLabel, doneButton);
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(10);
        titleVBox.setAlignment(Pos.CENTER);
        startYearHBox.setAlignment(Pos.CENTER);
        startMonthHBox.setAlignment(Pos.CENTER);
        startDayHBox.setAlignment(Pos.CENTER);
        startDateVBox.setAlignment(Pos.CENTER);
        endYearHBox.setAlignment(Pos.CENTER);
        endMonthHBox.setAlignment(Pos.CENTER);
        endDayHBox.setAlignment(Pos.CENTER);
        endDateVBox.setAlignment(Pos.CENTER);
        progressStartHBox.setAlignment(Pos.CENTER);
        progressStartVBox.setAlignment(Pos.CENTER);
        progressStartVBox.setSpacing(5);
        progressGoalHBox.setAlignment(Pos.CENTER);
        progressGoalVBox.setAlignment(Pos.CENTER);
        progressGoalVBox.setSpacing(5);
        mainHBox.setSpacing(5);
        mainHBox.getChildren().addAll(titleVBox, startDateVBox, endDateVBox, progressStartVBox, progressGoalVBox, filterButton);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(10);

        //Only allow integers for startYearBox
        startYearBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Update releaseDayBox if value is changed
        startYearBox.textProperty().addListener(e -> setDayCount(startMonthBox.getSelectionModel().getSelectedItem(), startDayBox, startYearBox));

        //Set start month
        startMonthBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        startMonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldNum, newNum) -> {
            //setDayCount when month is changed
            setDayCount(newNum, startDayBox, startYearBox);
        });

        //Select the first options of start date ChoiceBoxes
        startMonthBox.getSelectionModel().selectFirst();
        startDayBox.getSelectionModel().selectFirst();

        startCurrentDateButton.setOnAction(e -> {
            //Set year
            startYearBox.setText("" + ApplicationGUI.localDate.getYear());

            //Set month
            startMonthBox.getSelectionModel().select(ApplicationGUI.localDate.getMonthValue() - 1);

            //Set day
            startDayBox.getSelectionModel().select(ApplicationGUI.localDate.getDayOfMonth() - 1);
        });

        //Only allow integers for endYearBox
        endYearBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Update releaseDayBox if value is changed
        endYearBox.textProperty().addListener(e ->
                setDayCount(endMonthBox.getSelectionModel().getSelectedItem(), endDayBox, endYearBox));

        //Set end month
        endMonthBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        endMonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldNum, newNum) -> {
            //setDayCount when month is changed
            setDayCount(newNum, endDayBox, endYearBox);
        });

        //Select the first options of end date ChoiceBoxes
        endMonthBox.getSelectionModel().selectFirst();
        endDayBox.getSelectionModel().selectFirst();

        endCurrentDateButton.setOnAction(e -> {
            //Set year
            endYearBox.setText("" + ApplicationGUI.localDate.getYear());

            //Set month
            endMonthBox.getSelectionModel().select(ApplicationGUI.localDate.getMonthValue() - 1);

            //Set day
            endDayBox.getSelectionModel().select(ApplicationGUI.localDate.getDayOfMonth() - 1);
        });

        //Only allow integers for progressStartBox
        progressStartBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Only allow integers for progressGoalBox
        progressGoalBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));
    }

    //Sets the days in day choice boxes based on the month selected
    public void setDayCount(int month, ChoiceBox<Integer> dayBox, TextField yearBox) {
        //Local variables
        int dayCount = 0;
        Integer selectedDay = dayBox.getSelectionModel().getSelectedItem();

        switch (month) {
            //Switch for each month
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                //January, March, May, July, August, October, December
                dayCount = 31;
                break;
            case 2:
                //February
                int year;

                if(yearBox.getText().equals(""))
                    //If year is blank, consider it to be 0
                    year = 0;
                else
                    //Year is not blank
                    year = Integer.parseInt(yearBox.getText());

                if(isLeapYear(year))
                    //Leap year
                    dayCount = 29;
                else
                    //Not leap year
                    dayCount = 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                //April, June, September, November
                dayCount = 30;
                break;
        }

        //Set dayBox values
        dayBox.getItems().clear();
        for (int i = 1; i <= dayCount; i++)
            //Add each day individually
            dayBox.getItems().add(i);

        if(dayBox.getItems().contains(selectedDay))
            dayBox.getSelectionModel().select(selectedDay);
        else
            dayBox.getSelectionModel().select(0);
    }

    //Returns true if the given year is a leap year
    public boolean isLeapYear(int year){
        if(year % 4 == 0)
            //Year is divisible by four

            if(year % 100 == 0)
                //If year is divisible by 100, only true if it's also divisible by 400
                return year % 400 == 0;

            else
                //Year is not divisible by 100
                return true;

        //If year is not divisible by 4
        return false;
    }
}
