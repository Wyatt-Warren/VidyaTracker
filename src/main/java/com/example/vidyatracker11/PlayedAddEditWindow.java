package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//Window for adding or editing played games
public abstract class PlayedAddEditWindow extends AddEditGame{
    //GUI
    //Short Status
    Label shortLabel = new Label("Short Status:");
    ChoiceBox<String> shortBox = new ChoiceBox<>();
    VBox shortVBox = new VBox(shortLabel, shortBox);

    //Rating
    Label ratingLabel = new Label("Rating");
    ChoiceBox<Integer> ratingBox = new ChoiceBox<>();
    VBox ratingVBox = new VBox(ratingLabel, ratingBox);

    //Completion Date
    Label completionLabel = new Label("Completion Date:");
    //Year
    Label completionYearLabel = new Label("Year:");
    TextField completionYearBox = new TextField();
    HBox completionYearHBox = new HBox(completionYearLabel, completionYearBox);
    //Month
    Label completionMonthLabel = new Label("Month:");
    ChoiceBox<Integer> completionMonthBox = new ChoiceBox<>();
    HBox completionMonthHBox = new HBox(completionMonthLabel, completionMonthBox);
    //Day
    Label completionDayLabel = new Label("Day:");
    ChoiceBox<Integer> completionDayBox = new ChoiceBox<>();
    HBox completionDayHBox = new HBox(completionDayLabel, completionDayBox);
    Button completionCurrentDateButton = new Button("Enter Current Date");
    VBox completionVBox = new VBox(completionLabel, completionYearHBox, completionMonthHBox,
            completionDayHBox, completionCurrentDateButton);

    //100Percent Status
    Label percentLabel = new Label("100% Status:");
    ChoiceBox<String> percentBox = new ChoiceBox<>();
    VBox percentVBox = new VBox(percentLabel, percentBox);

    //Fields
    private static final Date date = new Date();
    private static final ZoneId timeZone = ZoneId.systemDefault();
    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();                         //Used to get current date

    public PlayedAddEditWindow(Stage stage){
        super();
        //GUI
        shortVBox.setAlignment(Pos.CENTER);
        ratingVBox.setAlignment(Pos.CENTER);
        completionYearHBox.setAlignment(Pos.CENTER);
        completionMonthHBox.setAlignment(Pos.CENTER);
        completionDayHBox.setAlignment(Pos.CENTER);
        completionVBox.setAlignment(Pos.CENTER);
        completionVBox.setSpacing(5);
        percentVBox.setAlignment(Pos.CENTER);
        mainHBox.getChildren().addAll(statusVBox, shortVBox, titleVBox,
                franchiseVBox, ratingVBox, platformVBox,
                genreVBox, releaseVBox, completionVBox,
                percentVBox);
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        //Set status values
        statusBox.getItems().addAll("Playing", "Completed", "On Hold");

        //Set short values
        shortBox.getItems().addAll("Yes", "No", "Blank");

        //Set ratings
        ratingBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //Only allow integers for completionYearBox
        completionYearBox.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Update completionYearBox if value is changed
        completionYearBox.textProperty().addListener(e ->
                setDayCount(completionMonthBox.getSelectionModel().getSelectedItem(), completionDayBox, completionYearBox));

        //Set completion month values
        completionMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

        completionMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            //Set day count when month is changed
            setDayCount((int) newNum, completionDayBox, completionYearBox);
        });

        completionCurrentDateButton.setOnAction(e -> {
            //Set year
            completionYearBox.setText("" + localDate.getYear());

            //Set month
            completionMonthBox.getSelectionModel().select(localDate.getMonthValue());

            //Set day
            completionDayBox.getSelectionModel().select(localDate.getDayOfMonth());
        });

        //Set 100% values
        percentBox.getItems().addAll("Yes", "No", "Blank");

        doneButton.setOnAction(e -> {
            //Save game
            try{
                saveAndQuit(stage);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });

    }

    //Save game and close window
    public abstract void saveAndQuit(Stage stage);
}
