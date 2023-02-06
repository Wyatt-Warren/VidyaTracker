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

public class PlayedEditWindow extends VBox {
  //Status
  Label statusLabel = new Label("Status:");
  ChoiceBox<String> statusBox = new ChoiceBox<>();
  VBox statusVBox = new VBox(statusLabel, statusBox);

  //Short Status
  Label shortLabel = new Label("Short Status:");
  ChoiceBox<String> shortBox = new ChoiceBox<>();
  VBox shortVBox = new VBox(shortLabel, shortBox);

  //Title
  Label titleLabel = new Label("Title:");
  TextField titleBox = new TextField();
  VBox titleVBox = new VBox(titleLabel, titleBox);

  //Franchise
  Label franchiseLabel = new Label("Franchise (Leave blank if no franchise):");
  TextField franchiseBox = new TextField();
  VBox franchiseVBox = new VBox(franchiseLabel, franchiseBox);

  //Rating
  Label ratingLabel = new Label("Rating");
  ChoiceBox<Integer> ratingBox = new ChoiceBox<>();
  VBox ratingVBox = new VBox(ratingLabel, ratingBox);

  //Platform
  Label platformLabel = new Label("Platform:");
  ChoiceBox<String> platformBox = new ChoiceBox<>();
  VBox platformVBox = new VBox(platformLabel, platformBox);

  //Genre
  Label genreLabel = new Label("Genre:");
  ChoiceBox<String> genreBox = new ChoiceBox<>();
  VBox genreVBox = new VBox(genreLabel, genreBox);

  //Release
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
  
  public PlayedEditWindow(PlayedGame game, Stage parentStage, PlayedGamesTable table,
                          ChoiceBox<String> sortChoiceBox, ChoiceBox<String> sortFilterBox,
                          StatusCountBoxPlayed statusCountBoxPlayed) {
    Label mainLabel = new Label("Edit Data Values for " + game.getTitle());
    mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
    HBox mainHBox = new HBox(statusVBox, shortVBox, titleVBox,
            franchiseVBox, ratingVBox, platformVBox,
            genreVBox, releaseVBox, completionVBox, percentVBox);
    Button doneButton = new Button("Save Changes and Close Window");
    mainHBox.setSpacing(5.0D);

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
    setPadding(new Insets(5.0D));
    statusBox.getItems().addAll("Playing", "Completed", "On Hold");
    statusBox.getSelectionModel().select(game.getStatus());
    shortBox.getItems().addAll("Yes", "No", "Blank");
    if (game.getIsItShort().equals("Yes") || game.getIsItShort().equals("No")) {
      shortBox.getSelectionModel().select(game.getIsItShort());
    } else {
      shortBox.getSelectionModel().selectLast();
    } 
    titleBox.setText(game.getTitle());
    if (!game.getFranchise().equals(game.getTitle()))
      franchiseBox.setText(game.getFranchise());
    ratingBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    ratingBox.getSelectionModel().select(game.getRating());
    platformBox.getItems().addAll(GameLists.platformList);
    platformBox.getSelectionModel().select(game.getPlatform());
    genreBox.getItems().addAll(GameLists.genreList);
    genreBox.getSelectionModel().select(game.getGenre());
    UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String input = change.getText();
        return input.matches("[0-9]*") ? change : null;
      };
    releaseYearBox.setText("" + game.getReleaseYear());
    releaseYearBox.setTextFormatter(new TextFormatter<>(integerFilter));
    releaseMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
          int newInt = (int) newNum;
          setDayCount(newInt, releaseDayBox);
        });
    releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());
    releaseDayBox.getSelectionModel().select(game.getReleaseDay());
    completionYearBox.setText("" + game.getCompletionYear());
    completionYearBox.setTextFormatter(new TextFormatter<>(integerFilter));
    completionMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    completionMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
          int newInt = (int) newNum;
          setDayCount(newInt, completionDayBox);
        });
    completionMonthBox.getSelectionModel().select(game.getCompletionMonth());
    completionDayBox.getSelectionModel().select(game.getCompletionDay());
    percentBox.getItems().addAll("Yes", "No", "Blank");
    if (game.getPercent100().equals("Yes") || game.getPercent100().equals("No")) {
      percentBox.getSelectionModel().select(game.getPercent100());
    } else {
      percentBox.getSelectionModel().selectLast();
    } 
    doneButton.setOnAction(e -> {
          try {
            saveAndQuit(game, parentStage, table, sortChoiceBox, sortFilterBox, statusCountBoxPlayed);
          } catch (InvalidPercentException|InvalidStatusException|InvalidShortStatusException|InvalidRatingException|InvalidPlatformException|InvalidGenreException|InvalidYearException|InvalidMonthException|InvalidDayException e1) {
            e1.printStackTrace();
          } 
        });
  }
  
  public void saveAndQuit(PlayedGame game, Stage parentStage, PlayedGamesTable table,
                          ChoiceBox<String> sortChoiceBox, ChoiceBox<String> sortFilterBox,
                          StatusCountBoxPlayed statusCountBoxPlayed) throws
          InvalidPercentException, InvalidStatusException, InvalidShortStatusException,
          InvalidRatingException, InvalidPlatformException, InvalidGenreException,
          InvalidYearException, InvalidMonthException, InvalidDayException {
    //Status
    game.setStatus(statusBox.getSelectionModel().getSelectedItem());

    //Short Status
    if (shortBox.getSelectionModel().getSelectedItem().equals("Blank")) {
      game.setIsItShort("");
    } else {
      game.setIsItShort(shortBox.getSelectionModel().getSelectedItem());
    }

    //Title/Franchise
    game.setTitle(titleBox.getText());
    if (franchiseBox.getText().equals("")) {
      game.setFranchise(titleBox.getText());
    } else {
      game.setFranchise(franchiseBox.getText());
    }

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

    statusCountBoxPlayed.updateData();
    table.sortAndFilter(sortChoiceBox, sortFilterBox);
    ApplicationGUI.changeMade = true;
    parentStage.close();
  }
  
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
