package com.example.vidyatracker11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RandomListGenerator extends VBox {
  Label mainLabel = new Label("Generate List Based on Filters");

  //Length
  Label lengthLabel = new Label("List Size:");
  TextField lengthField = new TextField();
  VBox lengthVBox = new VBox(lengthLabel, lengthField);

  //Status
  Label statusLabel = new Label("Possible Statuses:");
  ChoiceBox<String> statusBox = new ChoiceBox<>();
  Button statusAddButton = new Button("Add");
  Button statusRemoveButton = new Button("Remove");
  HBox statusButtonBox = new HBox(statusAddButton, statusRemoveButton);
  ListView<String> statusView = new ListView<>();
  VBox statusVBox = new VBox(statusLabel, statusBox, statusButtonBox, statusView);

  //Title
  Label titleLabel = new Label("Title Contains:");
  TextField titleField = new TextField();
  VBox titleVBox = new VBox(titleLabel, titleField);

  //Platform
  Label platformLabel = new Label("Possible Platforms:");
  ChoiceBox<String> platformBox = new ChoiceBox<>();
  Button platformAddButton = new Button("Add");
  Button platformRemoveButton = new Button("Remove");
  HBox platformButtonBox = new HBox(platformAddButton, platformRemoveButton);
  ListView<String> platformView = new ListView<>();
  VBox platformVBox = new VBox(platformLabel, platformBox, platformButtonBox, platformView);

  //Genre
  Label genreLabel = new Label("Possible Genres:");
  ChoiceBox<String> genreBox = new ChoiceBox<>();
  Button genreAddButton = new Button("Add");
  Button genreRemoveButton = new Button("Remove");
  HBox genreButtonBox = new HBox(genreAddButton, genreRemoveButton);
  ListView<String> genreView = new ListView<>();
  VBox genreVBox = new VBox(genreLabel, genreBox, genreButtonBox, genreView);

  //Hours
  Label hoursMinLabel = new Label("Hours Minimum:");
  TextField hoursMinField = new TextField();
  VBox hoursMinVBox = new VBox(hoursMinLabel, hoursMinField);
  Label hoursMaxLabel = new Label("Hours Maximum:");
  TextField hoursMaxField = new TextField();
  VBox hoursMaxVBox = new VBox(hoursMaxLabel, hoursMaxField);

  //Deck
  Label deckLabel = new Label("Possible Deck Statuses:");
  ChoiceBox<String> deckBox = new ChoiceBox<>();
  Button deckAddButton = new Button("Add");
  Button deckRemoveButton = new Button("Remove");
  HBox deckButtonBox = new HBox(deckAddButton, deckRemoveButton);
  ListView<String> deckView = new ListView<>();
  VBox deckVBox = new VBox(deckLabel, deckBox, deckButtonBox, deckView);

  //Year
  Label yearLabel = new Label("Possible Release Years:");
  ChoiceBox<Integer> yearBox = new ChoiceBox<>();
  Button yearAddButton = new Button("Add");
  Button yearRemoveButton = new Button("Remove");
  HBox yearButtonBox = new HBox(yearAddButton, yearRemoveButton);
  ListView<Integer> yearView = new ListView<>();
  VBox yearVBox = new VBox(yearLabel, yearBox, yearButtonBox, yearView);

  HBox mainHBox = new HBox(lengthVBox, statusVBox, titleVBox,
          platformVBox, genreVBox, hoursMinVBox,
          hoursMaxVBox, deckVBox, yearVBox);
  Button generateButton = new Button("Generate List");
  ListView<String> generatedList = new ListView<>();
  
  public RandomListGenerator() {
    getChildren().addAll(mainLabel, mainHBox, generateButton, generatedList);
    mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
    setFillWidth(false);
    mainHBox.setSpacing(5.0D);
    statusButtonBox.setAlignment(Pos.CENTER);
    platformButtonBox.setAlignment(Pos.CENTER);
    genreButtonBox.setAlignment(Pos.CENTER);
    deckButtonBox.setAlignment(Pos.CENTER);
    yearButtonBox.setAlignment(Pos.CENTER);
    lengthVBox.setAlignment(Pos.TOP_CENTER);
    statusVBox.setAlignment(Pos.TOP_CENTER);
    titleVBox.setAlignment(Pos.TOP_CENTER);
    platformVBox.setAlignment(Pos.TOP_CENTER);
    genreVBox.setAlignment(Pos.TOP_CENTER);
    hoursMinVBox.setAlignment(Pos.TOP_CENTER);
    hoursMaxVBox.setAlignment(Pos.TOP_CENTER);
    deckVBox.setAlignment(Pos.TOP_CENTER);
    yearVBox.setAlignment(Pos.TOP_CENTER);
    setAlignment(Pos.CENTER);

    setPadding(new Insets(5.0D));
    setSpacing(5.0D);
    lengthField.setTextFormatter(new TextFormatter<>(change -> {
            String input = change.getText();
            return input.matches("[0-9]*") ? change : null;
          }));
    statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
    platformBox.getItems().addAll(GameLists.platformList);
    genreBox.getItems().addAll(GameLists.genreList);

    hoursMinField.setTextFormatter(new TextFormatter<>(change -> {
            String input = change.getText();
            boolean noPeriods = true;
            for (int i = 0; i < hoursMinField.getText().length(); i++) {
              if (hoursMinField.getText().charAt(i) == '.')
                noPeriods = false; 
            } 
            return (input.matches("[0-9]*") || (input.matches("\\.*") && noPeriods)) ? change : null;
          }));

    hoursMaxField.setTextFormatter(new TextFormatter<>(change -> {
            String input = change.getText();
            boolean noPeriods = true;
            for (int i = 0; i < hoursMaxField.getText().length(); i++) {
              if (hoursMaxField.getText().charAt(i) == '.')
                noPeriods = false; 
            } 
            return (input.matches("[0-9]*") || (input.matches("\\.*") && noPeriods)) ? change : null;
          }));

    deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
    ObservableList<Integer> yearList = FXCollections.observableArrayList();
    for (int i = 0; i < GameLists.unplayedList.size(); i++) {
      if (!yearList.contains(GameLists.unplayedList.get(i).getReleaseYear()))
        yearList.add(GameLists.unplayedList.get(i).getReleaseYear());
    } 
    Collections.sort(yearList);
    yearBox.getItems().addAll(yearList);

    statusAddButton.setOnAction(e -> {
          if (!statusView.getItems().contains(statusBox.getSelectionModel().getSelectedItem()) &&
                  statusBox.getSelectionModel().getSelectedItem() != null)
            statusView.getItems().add(statusBox.getSelectionModel().getSelectedItem());
        });
    statusRemoveButton.setOnAction(e -> statusView.getItems().remove(statusView.getSelectionModel().getSelectedItem()));

    platformAddButton.setOnAction(e -> {
          if (!platformView.getItems().contains(platformBox.getSelectionModel().getSelectedItem()) &&
                  platformBox.getSelectionModel().getSelectedItem() != null)
            platformView.getItems().add(platformBox.getSelectionModel().getSelectedItem());
        });
    platformRemoveButton.setOnAction(e -> platformView.getItems().remove(platformView.getSelectionModel().getSelectedItem()));

    genreAddButton.setOnAction(e -> {
          if (!genreView.getItems().contains(genreBox.getSelectionModel().getSelectedItem()) &&
                  genreBox.getSelectionModel().getSelectedItem() != null)
            genreView.getItems().add(genreBox.getSelectionModel().getSelectedItem());
        });
    genreRemoveButton.setOnAction(e -> genreView.getItems().remove(genreView.getSelectionModel().getSelectedItem()));

    deckAddButton.setOnAction(e -> {
          if (!deckView.getItems().contains(deckBox.getSelectionModel().getSelectedItem()) &&
                  deckBox.getSelectionModel().getSelectedItem() != null)
            deckView.getItems().add(deckBox.getSelectionModel().getSelectedItem());
        });
    deckRemoveButton.setOnAction(e -> deckView.getItems().remove(deckView.getSelectionModel().getSelectedItem()));

    yearAddButton.setOnAction(e -> {
          if (!yearView.getItems().contains(yearBox.getSelectionModel().getSelectedItem()) &&
                  yearBox.getSelectionModel().getSelectedItem() != null)
            yearView.getItems().add(yearBox.getSelectionModel().getSelectedItem());
        });
    yearRemoveButton.setOnAction(e -> yearView.getItems().remove(yearView.getSelectionModel().getSelectedItem()));

    generateButton.setOnAction(e -> {
          if (!lengthField.getText().equals("")) {
            generatedList.getItems().clear();
            Random rand = new Random();
            ArrayList<UnplayedGame> potentialList = new ArrayList<>();
            for (int i = 0; i < GameLists.unplayedList.size(); i++) {
              UnplayedGame newGame = GameLists.unplayedList.get(i);
              if (newGame.getDeckCompatible().equals(""))
                try {
                  newGame.setDeckCompatible("Blank");
                } catch (InvalidDeckStatusException e1) {
                  e1.printStackTrace();
                }  
              if (statusView.getItems().isEmpty() || statusView.getItems().contains(newGame.getStatus()) &&
                      titleField.getText().equals("") || newGame.getTitle().toLowerCase().contains(titleField.getText().toLowerCase()) &&
                      platformView.getItems().isEmpty() || platformView.getItems().contains(newGame.getPlatform()) &&
                      genreView.getItems().isEmpty() || genreView.getItems().contains(newGame.getGenre()) &&
                      hoursMinField.getText().equals("") || newGame.getHours() >= Double.parseDouble(hoursMinField.getText()) &&
                      hoursMaxField.getText().equals("") || newGame.getHours() <= Double.parseDouble(hoursMaxField.getText()) &&
                      deckView.getItems().isEmpty() || deckView.getItems().contains(newGame.getDeckCompatible()) &&
                      yearView.getItems().isEmpty() || yearView.getItems().contains(newGame.getReleaseYear()))
                potentialList.add(newGame);
            } 
            if (!potentialList.isEmpty()) {
              int gameNum = Integer.parseInt(lengthField.getText());
              if (gameNum > potentialList.size())
                gameNum = potentialList.size(); 
              for (int j = 0; j < gameNum; j++)
                generatedList.getItems().add(potentialList.remove(rand.nextInt(potentialList.size())).getTitle());
            } 
          } 
        });
  }
}
