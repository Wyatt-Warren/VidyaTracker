package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

//The window for editting the genre list
public class EditGenreList extends VBox {
  Label mainLabel = new Label("Edit Genre List");
  
  Label warningLabel = new Label();
  
  TextField addGenreField = new TextField();
  
  Button addGenreButton = new Button("Add Genre");
  
  ListView<String> listView = new ListView<>(GameLists.genreList);
  
  Button removeGenreButton = new Button("Remove Selected Genre");
  
  Button renameGenreButton = new Button("Rename Selected Genre");
  
  Button moveUpButton = new Button("Move Up");
  
  Button moveDownButton = new Button("Move Down");
  
  VBox buttonBox = new VBox(renameGenreButton, removeGenreButton, moveUpButton, moveDownButton);
  
  GridPane gridPane = new GridPane();
  
  public EditGenreList(PlayedGamesTable pTable, UnplayedGamesTable uTable, ChoiceBox<String> pSortChoices,
                       ChoiceBox<String> pFilterChoices, ChoiceBox<String> uSortChoices,
                       ChoiceBox<String> uFilterChoices) {
    mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
    gridPane.add(addGenreField, 0, 0);
    gridPane.add(addGenreButton, 1, 0);
    gridPane.add(warningLabel, 1, 1);
    gridPane.add(listView, 0, 2);
    gridPane.add(buttonBox, 1, 2);
    gridPane.setHgap(5.0);
    gridPane.setVgap(5.0);
    buttonBox.setAlignment(Pos.TOP_CENTER);
    buttonBox.setSpacing(10.0);
    GridPane.setValignment(removeGenreButton, VPos.TOP);
    setAlignment(Pos.CENTER);
    setPadding(new Insets(5.0));
    setSpacing(5.0);
    getChildren().addAll(mainLabel, gridPane);

    //Add the current string to the genre list.
    addGenreButton.setOnAction(e -> {
          if (GameLists.genreList.contains(addGenreField.getText())) {
            warningLabel.setText("Genre already in list");
          } else if (!addGenreField.getText().equals("")) {
            GameLists.genreList.add(addGenreField.getText());
            warningLabel.setText("");
            addGenreField.setText("");
            ApplicationGUI.changeMade = true;
          } 
        });

    //Remove the selected item from the genre list.
    removeGenreButton.setOnAction(e -> {
          int selectionInt = listView.getSelectionModel().getSelectedIndex();
          if (selectionInt != -1 && GameLists.genreList.size() > 1) {
            int i;
            for (i = 0; i < GameLists.playedList.size(); i++) {
              if (GameLists.playedList.get(i).getGenre().equals(listView.getSelectionModel().getSelectedItem()))
                try {
                  if (selectionInt == 0) {
                    GameLists.playedList.get(i).setGenre(GameLists.genreList.get(1));
                  } else {
                    GameLists.playedList.get(i).setGenre(GameLists.genreList.get(0));
                  } 
                } catch (InvalidGenreException e1) {
                  e1.printStackTrace();
                }  
            } 
            for (i = 0; i < GameLists.unplayedList.size(); i++) {
              if (GameLists.unplayedList.get(i).getGenre().equals(listView.getSelectionModel().getSelectedItem()))
                try {
                  if (selectionInt == 0) {
                    GameLists.unplayedList.get(i).setGenre(GameLists.genreList.get(1));
                  } else {
                    GameLists.unplayedList.get(i).setGenre(GameLists.genreList.get(0));
                  } 
                } catch (InvalidGenreException e1) {
                  e1.printStackTrace();
                }  
            } 
            pTable.sortAndFilter(pSortChoices, pFilterChoices);
            uTable.sortAndFilter(uSortChoices, uFilterChoices);
            GameLists.genreList.remove(selectionInt);
            ApplicationGUI.changeMade = true;
          } 
        });

    //Replaces the selected genre with the current string
    renameGenreButton.setOnAction(e -> {
          if (GameLists.genreList.contains(addGenreField.getText())) {
            this.warningLabel.setText("Genre already in list");
          } else if (!addGenreField.getText().equals("")) {
            this.warningLabel.setText("");
            int selectionInt = this.listView.getSelectionModel().getSelectedIndex();
            GameLists.genreList.add(selectionInt, addGenreField.getText());
            if (selectionInt != -1 && GameLists.genreList.size() > 1) {
              int i;
              for (i = 0; i < GameLists.playedList.size(); i++) {
                if (GameLists.playedList.get(i).getGenre().equals(listView.getSelectionModel().getSelectedItem()))
                  try {
                    GameLists.playedList.get(i).setGenre(addGenreField.getText());
                  } catch (InvalidGenreException e1) {
                    e1.printStackTrace();
                  }  
              } 
              for (i = 0; i < GameLists.unplayedList.size(); i++) {
                if (GameLists.unplayedList.get(i).getGenre().equals(listView.getSelectionModel().getSelectedItem()))
                  try {
                    GameLists.unplayedList.get(i).setGenre(addGenreField.getText());
                  } catch (InvalidGenreException e1) {
                    e1.printStackTrace();
                  }  
              } 
              pTable.sortAndFilter(pSortChoices, pFilterChoices);
              uTable.sortAndFilter(uSortChoices, uFilterChoices);
              GameLists.genreList.remove(selectionInt + 1);
              addGenreField.setText("");
              ApplicationGUI.changeMade = true;
            } 
          } 
        });

    //Moves the selected genre up in the list by one position.
    moveUpButton.setOnAction(e -> {
          int selectionIndex = listView.getSelectionModel().getSelectedIndex();
          if (selectionIndex > 0) {
            GameLists.genreList.add(selectionIndex - 1, GameLists.genreList.remove(selectionIndex));
            listView.getSelectionModel().select(selectionIndex - 1);
            ApplicationGUI.changeMade = true;
          } 
        });

    //Moves the selected genre down in the list by one position.
    moveDownButton.setOnAction(e -> {
          int selectionIndex = listView.getSelectionModel().getSelectedIndex();
          if (selectionIndex > -1 && selectionIndex < GameLists.genreList.size() - 1) {
            GameLists.genreList.add(selectionIndex + 1, GameLists.genreList.remove(selectionIndex));
            listView.getSelectionModel().select(selectionIndex + 1);
            ApplicationGUI.changeMade = true;
          } 
        });
  }
}
