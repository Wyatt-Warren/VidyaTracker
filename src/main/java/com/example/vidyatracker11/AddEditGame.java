package com.example.vidyatracker11;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    Label franchiseLabel = new Label("Franchise (Leave blank if no franchise):");
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

    public AddEditGame(){

    }
}
