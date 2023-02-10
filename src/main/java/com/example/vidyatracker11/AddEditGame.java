package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

//Superclass containing common code for
//PlayedEditWindow, UnplayedEditWindow, AddPlayedGame, and AddUnplayedGame
public class AddEditGame extends VBox{
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

    //Misc
    Label mainLabel = new Label();
    Button doneButton = new Button();
    HBox mainHBox = new HBox();
    UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String input = change.getControlNewText();
        return input.matches("\\d{0,9}") ? change : null;
    };

    UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
        String input = change.getControlNewText();
        return input.matches("\\d*\\.\\d*") ? change : null;
    };

    public AddEditGame(){
        releaseYearHBox.setAlignment(Pos.CENTER);
        releaseMonthHBox.setAlignment(Pos.CENTER);
        releaseDayHBox.setAlignment(Pos.CENTER);
        releaseVBox.setAlignment(Pos.CENTER);
        statusVBox.setAlignment(Pos.CENTER);
        titleVBox.setAlignment(Pos.CENTER);
        franchiseVBox.setAlignment(Pos.CENTER);
        platformVBox.setAlignment(Pos.CENTER);
        genreVBox.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        mainHBox.setSpacing(5);
        setPadding(new Insets(5));

        //Platform
        platformBox.getItems().addAll(GameLists.platformList);

        //Genre
        genreBox.getItems().addAll(GameLists.genreList);

        //Release Date
        releaseYearBox.setTextFormatter(new TextFormatter < > (integerFilter));
        releaseMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt, releaseDayBox);
        });
        releaseMonthBox.getSelectionModel().selectFirst();
        releaseDayBox.getSelectionModel().selectFirst();
    }

    //Sets the days in the day drop down based on the month selected
    public void setDayCount(int month, ChoiceBox<Integer> dayBox) {
        int dayCount = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: //January, March, May, July, August, October, December
                dayCount = 31;
                break;
            case 2: //February
                dayCount = 29;
                break;
            case 4:
            case 6:
            case 9:
            case 11: //April, June, September, November
                dayCount = 30;
                break;
        }
        dayBox.getItems().clear();
        for (int i = 0; i <= dayCount; i++)
            dayBox.getItems().add(i);
        dayBox.getSelectionModel().select(0);
    }
}
