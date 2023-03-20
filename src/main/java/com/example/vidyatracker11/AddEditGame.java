package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

//Superclass containing for PlayedEditWindow, UnplayedEditWindow, PlayedAddWindow, and UnplayedAddWindow
public class AddEditGame extends VBox{
    //GUI
    Label mainLabel = new Label();
    Button doneButton = new Button();
    HBox mainHBox = new HBox();

    //Status
    Label statusLabel = new Label("Status:");
    ChoiceBox<String> statusBox = new ChoiceBox<>();
    VBox statusVBox = new VBox(statusLabel, statusBox);

    //Title
    Label titleLabel = new Label("Title:");
    TextField titleBox = new TextField();
    VBox titleVBox = new VBox(titleLabel, titleBox);

    //Franchise
    Label franchiseLabel = new Label("Franchise:");
    TextField franchiseBox = new TextField();
    VBox franchiseVBox = new VBox(franchiseLabel, franchiseBox);

    //Platform
    Label platformLabel = new Label("Platform:");
    ChoiceBox<String> platformBox = new ChoiceBox<>();
    VBox platformVBox = new VBox(platformLabel, platformBox);

    //Genre
    Label genreLabel = new Label("Genre:");
    ChoiceBox<String> genreBox = new ChoiceBox<>();
    VBox genreVBox = new VBox(genreLabel, genreBox);

    //Release Date
    Label releaseLabel = new Label("Release Date:");
    //Year
    Label releaseYearLabel = new Label("Year:");
    TextField releaseYearBox = new TextField();
    HBox releaseYearHBox = new HBox(releaseYearLabel, releaseYearBox);
    //Month
    Label releaseMonthLabel = new Label("Month:");
    ChoiceBox<Integer> releaseMonthBox = new ChoiceBox<>();
    HBox releaseMonthHBox = new HBox(releaseMonthLabel, releaseMonthBox);
    //Day
    Label releaseDayLabel = new Label("Day:");
    ChoiceBox<Integer> releaseDayBox = new ChoiceBox<>();
    HBox releaseDayHBox = new HBox(releaseDayLabel, releaseDayBox);
    VBox releaseVBox = new VBox(releaseLabel, releaseYearHBox, releaseMonthHBox, releaseDayHBox);

    //Fields
    UnaryOperator<TextFormatter.Change> integerFilter = change -> {                                 //Filters if text is not a valid integer
        String input = change.getControlNewText();
        return input.matches("\\d{0,9}") ? change : null;
    };
    UnaryOperator<TextFormatter.Change> doubleFilter = change -> {                                  //Filters if text is not a valid double
        String input = change.getControlNewText();
        return input.matches("\\d*\\.\\d*")||input.matches("\\d{0,9}") ? change : null;
    };

    public AddEditGame(){
        //GUI
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        statusVBox.setAlignment(Pos.CENTER);
        titleVBox.setAlignment(Pos.CENTER);
        franchiseVBox.setAlignment(Pos.CENTER);
        platformVBox.setAlignment(Pos.CENTER);
        genreVBox.setAlignment(Pos.CENTER);
        releaseYearHBox.setAlignment(Pos.CENTER);
        releaseMonthHBox.setAlignment(Pos.CENTER);
        releaseDayHBox.setAlignment(Pos.CENTER);
        releaseVBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(5);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));

        //Set platform values
        platformBox.getItems().addAll(GameLists.platformList);
        platformBox.getSelectionModel().selectFirst();

        //Set genre values
        genreBox.getItems().addAll(GameLists.genreList);
        genreBox.getSelectionModel().selectFirst();

        //Only allow integers for releaseYearBox
        releaseYearBox.setTextFormatter(new TextFormatter<>(integerFilter));

        //Update releaseDayBox if value is changed
        releaseYearBox.textProperty().addListener(e ->
                setDayCount(releaseMonthBox.getSelectionModel().getSelectedItem(), releaseDayBox, releaseYearBox));

        //Set release month
        releaseMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            //setDayCount when month is changed
            setDayCount((int) newNum, releaseDayBox, releaseYearBox);
        });

        //Select the first options of ChoiceBoxes
        releaseMonthBox.getSelectionModel().selectFirst();
        releaseDayBox.getSelectionModel().selectFirst();
    }

    //Sets the days in releaseDayBox based on the month selected
    public void setDayCount(int month, ChoiceBox<Integer> dayBox, TextField yearBox) {
        //Local variables
        int dayCount = 0;

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
                if(isLeapYear(Integer.parseInt(yearBox.getText())))
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
        for (int i = 0; i <= dayCount; i++)
            //Add each day individually
            dayBox.getItems().add(i);
        dayBox.getSelectionModel().select(0);
    }

    //Returns true if the given year is a leap year
    public boolean isLeapYear(int year){
        if(year % 4 == 0)
            //Year is divisible by four

            if(year % 100 == 0) {
                //If year is divisible by 100, only true if it's also divisible by 400
                return year % 400 == 0;

            }else
                //Year is not divisible by 100
                return true;

        //If year is not divisible by 4
        return false;
    }
}
