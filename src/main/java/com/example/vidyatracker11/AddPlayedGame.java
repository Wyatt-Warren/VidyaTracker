package com.example.vidyatracker11;

import java.util.function.UnaryOperator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//The window for adding a new game to the played game list.
public class AddPlayedGame extends AddEditGame {

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
    VBox completionVBox = new VBox(completionLabel, completionYearHBox, completionMonthHBox,
            completionDayHBox);

    //100Percent Status
    Label percentLabel = new Label("100% Status:");
    ChoiceBox<String> percentBox = new ChoiceBox<>();
    VBox percentVBox = new VBox(percentLabel, percentBox);

    public AddPlayedGame(Stage parentStage) {
        Label mainLabel = new Label("Add New Played Game");
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        HBox mainHBox = new HBox(statusVBox, shortVBox, titleVBox,
                franchiseVBox, ratingVBox, platformVBox,
                genreVBox, releaseVBox, completionVBox,
                percentVBox);
        Button doneButton = new Button("Create New Played Game");
        mainHBox.setSpacing(5);

        releaseYearHBox.setAlignment(Pos.CENTER);
        releaseMonthHBox.setAlignment(Pos.CENTER);
        releaseDayHBox.setAlignment(Pos.CENTER);
        completionYearHBox.setAlignment(Pos.CENTER);
        completionMonthHBox.setAlignment(Pos.CENTER);
        completionDayHBox.setAlignment(Pos.CENTER);
        statusVBox.setAlignment(Pos.CENTER);
        shortVBox.setAlignment(Pos.CENTER);
        titleVBox.setAlignment(Pos.CENTER);
        franchiseVBox.setAlignment(Pos.CENTER);
        ratingVBox.setAlignment(Pos.CENTER);
        platformVBox.setAlignment(Pos.CENTER);
        genreVBox.setAlignment(Pos.CENTER);
        releaseVBox.setAlignment(Pos.CENTER);
        completionVBox.setAlignment(Pos.CENTER);
        percentVBox.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        getChildren().addAll(mainLabel, mainHBox, doneButton);
        setPadding(new Insets(5));

        statusBox.getItems().addAll("Playing", "Completed", "On Hold");
        statusBox.getSelectionModel().selectFirst();
        shortBox.getItems().addAll("Yes", "No", "Blank");
        shortBox.getSelectionModel().selectLast();
        ratingBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ratingBox.getSelectionModel().selectFirst();
        platformBox.getItems().addAll(GameLists.platformList);
        platformBox.getSelectionModel().selectFirst();
        genreBox.getItems().addAll(GameLists.genreList);
        genreBox.getSelectionModel().selectFirst();

        UnaryOperator < TextFormatter.Change > integerFilter = change -> {
            String input = change.getText();
            return input.matches("[0-9]*") ? change : null;
        };
        releaseYearBox.setTextFormatter(new TextFormatter < > (integerFilter));
        releaseMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt);
        });
        releaseMonthBox.getSelectionModel().selectFirst();
        releaseDayBox.getSelectionModel().selectFirst();

        completionYearBox.setTextFormatter(new TextFormatter < > (integerFilter));
        completionMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        completionMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt);
        });
        completionMonthBox.getSelectionModel().selectFirst();
        completionDayBox.getSelectionModel().selectFirst();

        percentBox.getItems().addAll("Yes", "No", "Blank");
        percentBox.getSelectionModel().selectLast();
        doneButton.setOnAction(e -> {
            try{
                saveAndQuit(parentStage);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and creates a new game with the inputted data
    public void saveAndQuit(Stage parentStage) throws NumberFormatException{
        //Set Release Year
        int releaseYear;
        if (releaseYearBox.getText().equals("")) {
            releaseYear = 0;
        } else {
            releaseYear = Integer.parseInt(releaseYearBox.getText());
        }

        //Create new game object
        PlayedGame newGame = new PlayedGame(titleBox.getText(), statusBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem(), genreBox.getSelectionModel().getSelectedItem(),
                releaseYear, releaseMonthBox.getValue(), releaseDayBox.getValue());

        //Set short status
        if ((shortBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
            newGame.setIsItShort("");
        } else {
            newGame.setIsItShort(shortBox.getSelectionModel().getSelectedItem());
        }

        //Set Franchise
        if (!franchiseBox.getText().equals(""))
            newGame.setFranchise(franchiseBox.getText());

        //Set Rating
        newGame.setRating(ratingBox.getSelectionModel().getSelectedItem());

        //Set Completion Date
        if (completionYearBox.getText().equals("")) {
            newGame.setCompletionYear(0);
        } else {
            newGame.setCompletionYear(Integer.parseInt(completionYearBox.getText()));
        }
        newGame.setCompletionMonth(completionMonthBox.getValue());
        newGame.setCompletionDay(completionDayBox.getValue());

        //Set 100Percent status
        if ((percentBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
            newGame.setPercent100("");
        } else {
            newGame.setPercent100(percentBox.getSelectionModel().getSelectedItem());
        }

        GameLists.playedList.add(newGame);
        ApplicationGUI.statusCountBoxPlayed.updateData();
        ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedSortChoices, ApplicationGUI.playedFilterChoices);
        ApplicationGUI.stats.updateStats();
        ApplicationGUI.changeMade = true;
        parentStage.close();
    }

    //Sets the days in the day drop down based on the month selected
    public void setDayCount(int month) {
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
        releaseDayBox.getItems().clear();
        for (int i = 0; i <= dayCount; i++)
            releaseDayBox.getItems().add(i);
        releaseDayBox.getSelectionModel().select(0);
    }
}