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

public class AddUnplayedGame extends VBox {
  //Status
  Label statusLabel = new Label("Status:");
  ChoiceBox<String> statusBox = new ChoiceBox<>();
  VBox statusVBox = new VBox(this.statusLabel, this.statusBox);

  //Title
  Label titleLabel = new Label("Title:");
  TextField titleBox = new TextField();
  VBox titleVBox = new VBox(this.titleLabel, this.titleBox);

  //Franchise
  Label franchiseLabel = new Label("Franchise (Leave blank if no franchise):");
  TextField franchiseBox = new TextField();
  VBox franchiseVBox = new VBox(this.franchiseLabel, this.franchiseBox);

  //Platform
  Label platformLabel = new Label("Platform:");
  ChoiceBox<String> platformBox = new ChoiceBox<>();
  VBox platformVBox = new VBox(this.platformLabel, this.platformBox);

  //Genre
  Label genreLabel = new Label("Genre:");
  ChoiceBox<String> genreBox = new ChoiceBox<>();
  VBox genreVBox = new VBox(this.genreLabel, this.genreBox);

  //Hours
  Label hoursLabel = new Label("Predicted Hours:");
  TextField hoursBox = new TextField();
  VBox hoursVBox = new VBox(this.hoursLabel, this.hoursBox);

  //Deck Status
  Label deckLabel = new Label("Deck Status:");
  ChoiceBox<String> deckBox = new ChoiceBox<>();
  VBox deckVBox = new VBox(this.deckLabel, this.deckBox);

  //Release Date
  Label releaseLabel = new Label("Release Date:");
  //Year
  Label releaseYearLabel = new Label("Year:");
  TextField releaseYearBox = new TextField();
  HBox releaseYearHBox = new HBox(this.releaseYearLabel, this.releaseYearBox);
  //Month
  Label releaseMonthLabel = new Label("Month:");
  ChoiceBox<Integer> releaseMonthBox = new ChoiceBox<>();
  HBox releaseMonthHBox = new HBox(this.releaseMonthLabel, this.releaseMonthBox);
  //Day
  Label releaseDayLabel = new Label("Day:");
  ChoiceBox<Integer> releaseDayBox = new ChoiceBox<>();
  HBox releaseDayHBox = new HBox(this.releaseDayLabel, this.releaseDayBox);
  VBox releaseVBox = new VBox(this.releaseLabel, this.releaseYearHBox, this.releaseMonthHBox, this.releaseDayHBox);
  
  public AddUnplayedGame(Stage parentStage, UnplayedGamesTable table, ChoiceBox<String> sortChoiceBox, ChoiceBox<String> sortFilterBox, StatusCountBoxUnplayed statusCountBoxUnplayed, StatsScreen stats) {
    Label mainLabel = new Label("Add New Unplayed Game");
    mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
    HBox mainHBox = new HBox(this.statusVBox, this.titleVBox, this.franchiseVBox,
            this.platformVBox, this.genreVBox, this.hoursVBox,
            this.deckVBox, this.releaseVBox);
    Button doneButton = new Button("Create New Unplayed Game");
    mainHBox.setSpacing(5.0D);

    this.releaseYearHBox.setAlignment(Pos.CENTER);
    this.releaseMonthHBox.setAlignment(Pos.CENTER);
    this.releaseDayHBox.setAlignment(Pos.CENTER);
    this.statusVBox.setAlignment(Pos.CENTER);
    this.titleVBox.setAlignment(Pos.CENTER);
    this.franchiseVBox.setAlignment(Pos.CENTER);
    this.platformVBox.setAlignment(Pos.CENTER);
    this.genreVBox.setAlignment(Pos.CENTER);
    this.hoursVBox.setAlignment(Pos.CENTER);
    this.deckVBox.setAlignment(Pos.CENTER);
    this.releaseVBox.setAlignment(Pos.CENTER);

    setAlignment(Pos.CENTER);
    getChildren().addAll(mainLabel, mainHBox, doneButton );
    setPadding(new Insets(5.0D));

    this.statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
    this.statusBox.getSelectionModel().selectFirst();
    this.platformBox.getItems().addAll(GameLists.platformList);
    this.platformBox.getSelectionModel().selectFirst();
    this.genreBox.getItems().addAll(GameLists.genreList);
    this.genreBox.getSelectionModel().selectFirst();
    this.hoursBox.setTextFormatter(new TextFormatter<>(change -> {
            String input = change.getText();
            boolean noPeriods = true;
            for (int i = 0; i < this.hoursBox.getText().length(); i++) {
              if (this.hoursBox.getText().charAt(i) == '.')
                noPeriods = false; 
            } 
            return (input.matches("[0-9]*") || (input.matches("\\.*") && noPeriods)) ? change : null;
          }));
    this.deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
    this.deckBox.getSelectionModel().selectLast();
    UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String input = change.getText();
        return input.matches("[0-9]*") ? change : null;
      };

    this.releaseYearBox.setTextFormatter(new TextFormatter<>(integerFilter));
    this.releaseMonthBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    this.releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
          int newInt = (int)newNum;
          setDayCount(newInt, this.releaseDayBox);
        });
    this.releaseMonthBox.getSelectionModel().selectFirst();
    this.releaseDayBox.getSelectionModel().selectFirst();
    doneButton.setOnAction(e -> {
          try {
            saveAndQuit(parentStage, table, sortChoiceBox, sortFilterBox, statusCountBoxUnplayed, stats);
          } catch (InvalidPercentException|InvalidStatusException|InvalidShortStatusException|InvalidRatingException|InvalidPlatformException|InvalidGenreException|InvalidYearException|InvalidMonthException|InvalidDayException|NumberFormatException|InvalidHoursException|InvalidDeckStatusException e1) {
            e1.printStackTrace();
          } 
        });
  }
  
  public void saveAndQuit(Stage parentStage, UnplayedGamesTable table, ChoiceBox<String> sortChoiceBox,
                          ChoiceBox<String> sortFilterBox, StatusCountBoxUnplayed statusCountBoxUnplayed,
                          StatsScreen stats) throws InvalidPercentException, InvalidStatusException, InvalidShortStatusException,
          InvalidRatingException, InvalidPlatformException, InvalidGenreException,
          InvalidYearException, InvalidMonthException, InvalidDayException, NumberFormatException,
          InvalidHoursException, InvalidDeckStatusException {
    int releaseYear;
    if (this.releaseYearBox.getText().equals("")) {
      releaseYear = 0;
    } else {
      releaseYear = Integer.parseInt(this.releaseYearBox.getText());
    } 
    UnplayedGame newGame = new UnplayedGame(this.titleBox.getText(), this.statusBox.getSelectionModel().getSelectedItem(),
            this.platformBox.getSelectionModel().getSelectedItem(), this.genreBox.getSelectionModel().getSelectedItem(),
            releaseYear, this.releaseMonthBox.getValue(), this.releaseDayBox.getValue());
    if (!this.franchiseBox.getText().equals(""))
      newGame.setFranchise(this.franchiseBox.getText()); 
    if (this.hoursBox.getText().equals("") || this.hoursBox.getText().equals(".")) {
      newGame.setHours(0);
    } else {
      newGame.setHours(Double.parseDouble(this.hoursBox.getText()));
    } 
    if ((this.deckBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
      newGame.setDeckCompatible("");
    } else {
      newGame.setDeckCompatible(this.deckBox.getSelectionModel().getSelectedItem());
    } 
    if (this.releaseYearBox.getText().equals("")) {
      newGame.setReleaseYear(0);
    } else {
      newGame.setReleaseYear(Integer.parseInt(this.releaseYearBox.getText()));
    } 
    GameLists.unplayedList.add(newGame);
    statusCountBoxUnplayed.updateData();
    table.sortAndFilter(sortChoiceBox, sortFilterBox);
    stats.updateStats();
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
