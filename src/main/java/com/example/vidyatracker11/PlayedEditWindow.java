package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Window used to edit an existing played game.
public class PlayedEditWindow extends AddEditGame {
    //Short Status
    Label shortLabel = new Label("Short Status:");
    ChoiceBox<String> shortBox = new ChoiceBox<>();
    VBox shortVBox = new VBox(shortLabel, shortBox);

    //Rating
    Label ratingLabel = new Label("Rating");
    ChoiceBox<Integer> ratingBox = new ChoiceBox<>();
    VBox ratingVBox = new VBox(ratingLabel, ratingBox);

    //Completion
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
    VBox completionVBox = new VBox(completionLabel, completionYearHBox, completionMonthHBox, completionDayHBox);

    //100percent Status
    Label percentLabel = new Label("100% Status:");
    ChoiceBox<String> percentBox = new ChoiceBox<>();
    VBox percentVBox = new VBox(percentLabel, percentBox);

    public PlayedEditWindow(PlayedGame game, Stage stage) {
        super();
        mainLabel.setText("Edit Data Values for " + game.getTitle());
        mainHBox.getChildren().addAll(statusVBox, shortVBox, titleVBox,
                franchiseVBox, ratingVBox, platformVBox,
                genreVBox, releaseVBox, completionVBox, percentVBox);
        doneButton.setText("Save Changes and Close Window");
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        completionYearHBox.setAlignment(Pos.CENTER);
        completionMonthHBox.setAlignment(Pos.CENTER);
        completionDayHBox.setAlignment(Pos.CENTER);
        completionVBox.setAlignment(Pos.CENTER);
        shortVBox.setAlignment(Pos.CENTER);
        ratingVBox.setAlignment(Pos.CENTER);
        percentVBox.setAlignment(Pos.CENTER);

        //Status
        statusBox.getItems().addAll("Playing", "Completed", "On Hold");
        statusBox.getSelectionModel().select(game.getStatus());

        //Short Status
        shortBox.getItems().addAll("Yes", "No", "Blank");
        if (game.getIsItShort().equals("Yes") || game.getIsItShort().equals("No")) {
            shortBox.getSelectionModel().select(game.getIsItShort());
        } else {
            shortBox.getSelectionModel().selectLast();
        }

        //Title/Franchise
        titleBox.setText(game.getTitle());
        franchiseBox.setText(game.getFranchise());

        //Rating
        ratingBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ratingBox.getSelectionModel().select(game.getRating());

        //Genre
        genreBox.getSelectionModel().select(game.getGenre());

        //Platform
        platformBox.getSelectionModel().select(game.getPlatform());

        //Release Date
        releaseYearBox.setText("" + game.getReleaseYear());
        releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());
        releaseDayBox.getSelectionModel().select(game.getReleaseDay());

        //Completion Date
        completionYearBox.setText("" + game.getCompletionYear());
        completionYearBox.setTextFormatter(new TextFormatter<>(integerFilter));
        completionYearBox.textProperty().addListener(e ->
                setDayCount(completionMonthBox.getSelectionModel().getSelectedItem(), completionDayBox, completionYearBox));
        completionMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        completionMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt, completionDayBox, completionYearBox);
        });
        completionMonthBox.getSelectionModel().select(game.getCompletionMonth());
        completionDayBox.getSelectionModel().select(game.getCompletionDay());

        //100 Percent Status
        percentBox.getItems().addAll("Yes", "No", "Blank");
        if (game.getPercent100().equals("Yes") || game.getPercent100().equals("No")) {
            percentBox.getSelectionModel().select(game.getPercent100());
        } else {
            percentBox.getSelectionModel().selectLast();
        }

        doneButton.setOnAction(e -> {
            try{
                saveAndQuit(game, stage);
            }catch(NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and saves the inputted data to the given game
    public void saveAndQuit(PlayedGame game, Stage stage) throws NumberFormatException{
        //Status
        game.setStatus(statusBox.getSelectionModel().getSelectedItem());

        //Short Status
        if (shortBox.getSelectionModel().getSelectedItem().equals("Blank")) {
            game.setIsItShort("");
        } else {
            game.setIsItShort(shortBox.getSelectionModel().getSelectedItem());
        }

        //Title
        game.setTitle(titleBox.getText());

        //Franchise
        game.setFranchise(franchiseBox.getText());

        //Rating
        game.setRating(ratingBox.getSelectionModel().getSelectedItem());

        //Platform
        game.setPlatform(platformBox.getSelectionModel().getSelectedItem());

        //Genre
        game.setGenre(genreBox.getSelectionModel().getSelectedItem());

        //Release Date
        if (releaseYearBox.getText().equals("")) {
            game.setReleaseYear(0);
        } else {
            game.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));
        }
        game.setReleaseMonth(releaseMonthBox.getValue());
        game.setReleaseDay(releaseDayBox.getValue());

        //Completion Date
        if (completionYearBox.getText().equals("")) {
            game.setCompletionYear(0);
        } else {
            game.setCompletionYear(Integer.parseInt(completionYearBox.getText()));
        }
        game.setCompletionMonth(completionMonthBox.getValue());
        game.setCompletionDay(completionDayBox.getValue());

        //Percent Status
        if (percentBox.getSelectionModel().getSelectedItem().equals("Blank")) {
            game.setPercent100("");
        } else {
            game.setPercent100(percentBox.getSelectionModel().getSelectedItem());
        }

        ApplicationGUI.statusCountBoxPlayed.updateData();
        ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());
        ApplicationGUI.changeMade = true;
        stage.close();
    }
}