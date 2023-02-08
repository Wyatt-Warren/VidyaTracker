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

//The window for editing the platform list.
public class EditPlatformList extends VBox {
    Label mainLabel = new Label("Edit Platform List");

    Label warningLabel = new Label();

    TextField addPlatformField = new TextField();

    Button addPlatformButton = new Button("Add Platform");

    ListView<String> listView = new ListView<> (GameLists.platformList);

    Button removePlatformButton = new Button("Remove Selected Platform");

    Button renamePlatformButton = new Button("Rename Selected Platform");

    Button moveUpButton = new Button("Move Up");

    Button moveDownButton = new Button("Move Down");

    VBox buttonBox = new VBox(renamePlatformButton, removePlatformButton, moveUpButton, moveDownButton);

    GridPane gridPane = new GridPane();

    public EditPlatformList(PlayedGamesTable pTable, UnplayedGamesTable uTable, ChoiceBox<String> pSortChoices,
                            ChoiceBox<String> pFilterChoices, ChoiceBox<String> uSortChoices,
                            ChoiceBox<String> uFilterChoices) {
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        gridPane.add(addPlatformField, 0, 0);
        gridPane.add(addPlatformButton, 1, 0);
        gridPane.add(warningLabel, 1, 1);
        gridPane.add(listView, 0, 2);
        gridPane.add(buttonBox, 1, 2);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(10);
        GridPane.setValignment(removePlatformButton, VPos.TOP);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);
        getChildren().addAll(mainLabel, gridPane);

        //Adds the current string to the platform list
        addPlatformButton.setOnAction(e -> {
            if (GameLists.platformList.contains(addPlatformField.getText())) {
                warningLabel.setText("Platform already in list");
            } else if (!addPlatformField.getText().equals("")) {
                GameLists.platformList.add(addPlatformField.getText());
                warningLabel.setText("");
                addPlatformField.setText("");
                ApplicationGUI.changeMade = true;
            }
        });

        //Removes the selected platform from the platform list
        removePlatformButton.setOnAction(e -> {
            int selectionInt = listView.getSelectionModel().getSelectedIndex();
            if (selectionInt != -1 && GameLists.platformList.size() > 1) {
                int i;
                for (i = 0; i < GameLists.playedList.size(); i++) {
                    if (GameLists.playedList.get(i).getPlatform().equals(listView.getSelectionModel().getSelectedItem()))
                        if (selectionInt == 0) {
                            GameLists.playedList.get(i).setPlatform(GameLists.platformList.get(1));
                        } else {
                            GameLists.playedList.get(i).setPlatform(GameLists.platformList.get(0));
                        }
                }
                for (i = 0; i < GameLists.unplayedList.size(); i++) {
                    if (GameLists.unplayedList.get(i).getPlatform().equals(listView.getSelectionModel().getSelectedItem()))
                        if (selectionInt == 0) {
                            GameLists.unplayedList.get(i).setPlatform(GameLists.platformList.get(1));
                        } else {
                            GameLists.unplayedList.get(i).setPlatform(GameLists.platformList.get(0));
                        }
                }
                pTable.sortAndFilter(pSortChoices, pFilterChoices);
                uTable.sortAndFilter(uSortChoices, uFilterChoices);
                GameLists.platformList.remove(selectionInt);
                ApplicationGUI.changeMade = true;
            }
        });

        //Replaces the selected platform with the current string
        renamePlatformButton.setOnAction(e -> {
            if (GameLists.platformList.contains(addPlatformField.getText())) {
                warningLabel.setText("Platform already in list");
            } else if (!addPlatformField.getText().equals("")) {
                warningLabel.setText("");
                int selectionInt = listView.getSelectionModel().getSelectedIndex();
                GameLists.platformList.add(selectionInt, addPlatformField.getText());
                if (selectionInt != -1 && GameLists.platformList.size() > 1) {
                    int i;
                    for (i = 0; i < GameLists.playedList.size(); i++) {
                        if (GameLists.playedList.get(i).getPlatform().equals(listView.getSelectionModel().getSelectedItem()))
                            GameLists.playedList.get(i).setPlatform(addPlatformField.getText());
                    }
                    for (i = 0; i < GameLists.unplayedList.size(); i++) {
                        if (GameLists.unplayedList.get(i).getPlatform().equals(listView.getSelectionModel().getSelectedItem()))
                            GameLists.unplayedList.get(i).setPlatform(addPlatformField.getText());
                    }
                    pTable.sortAndFilter(pSortChoices, pFilterChoices);
                    uTable.sortAndFilter(uSortChoices, uFilterChoices);
                    GameLists.platformList.remove(selectionInt + 1);
                    addPlatformField.setText("");
                    ApplicationGUI.changeMade = true;
                }
            }
        });

        //Moves the selected platform up in the list by one position.
        moveUpButton.setOnAction(e -> {
            int selectionIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectionIndex > 0) {
                GameLists.platformList.add(selectionIndex - 1, GameLists.platformList.remove(selectionIndex));
                listView.getSelectionModel().select(selectionIndex - 1);
                ApplicationGUI.changeMade = true;
            }
        });

        //Moves the selected platform down in the list by one position.
        moveDownButton.setOnAction(e -> {
            int selectionIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectionIndex > -1 && selectionIndex < GameLists.platformList.size() - 1) {
                GameLists.platformList.add(selectionIndex + 1, GameLists.platformList.remove(selectionIndex));
                listView.getSelectionModel().select(selectionIndex + 1);
                ApplicationGUI.changeMade = true;
            }
        });
    }
}