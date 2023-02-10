package com.example.vidyatracker11;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

//Superclass for EditGenreList and EditPlatformList
public abstract class EditGenPlatList extends VBox {
    Label mainLabel = new Label();
    Label warningLabel = new Label();
    TextField addItemField = new TextField();
    Button addItemButton = new Button();
    ListView<String> listView = new ListView<>();
    Button removeItemButton = new Button();
    Button renameItemButton = new Button();
    Button moveUpButton = new Button("Move Up");
    Button moveDownButton = new Button("Move Down");
    VBox buttonBox = new VBox(renameItemButton, removeItemButton, moveUpButton, moveDownButton);
    GridPane gridPane = new GridPane();

    ObservableList<String> list;
    String inListWarning = "";

    public EditGenPlatList(){
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        gridPane.add(addItemField, 0, 0);
        gridPane.add(addItemButton, 1, 0);
        gridPane.add(warningLabel, 1, 1);
        gridPane.add(listView, 0, 2);
        gridPane.add(buttonBox, 1, 2);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(10);
        GridPane.setValignment(removeItemButton, VPos.TOP);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);
        getChildren().addAll(mainLabel, gridPane);

        //Add the current string to the list.
        addItemButton.setOnAction(e -> {
            if (list.contains(addItemField.getText())) {
                warningLabel.setText(inListWarning);
            } else if (!addItemField.getText().equals("")) {
                list.add(addItemField.getText());
                warningLabel.setText("");
                addItemField.setText("");
                ApplicationGUI.changeMade = true;
            }
        });

        //Remove selected item from the list
        removeItemButton.setOnAction(e -> {
            int selectionInt = listView.getSelectionModel().getSelectedIndex();

            if(selectionInt != -1 && list.size() > 1){
                String toRemove = listView.getSelectionModel().getSelectedItem();
                list.remove(selectionInt);
                removeGameItems(toRemove);
                ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedSortChoices, ApplicationGUI.playedFilterChoices);
                ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedSortChoices, ApplicationGUI.unplayedFilterChoices);
                ApplicationGUI.changeMade = true;
            }
        });

        //Replaces the selected item with the current string
        renameItemButton.setOnAction(e -> {
            int selectionInt = listView.getSelectionModel().getSelectedIndex();
            String newName = addItemField.getText();
            String oldName = listView.getSelectionModel().getSelectedItem();

            if(list.contains(newName)){
                warningLabel.setText(inListWarning);
            }else if(!newName.equals("") && selectionInt != 1){
                warningLabel.setText("");
                list.remove(selectionInt);
                list.add(selectionInt, newName);
                renameGameItems(oldName, newName);
                ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedSortChoices, ApplicationGUI.playedFilterChoices);
                ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedSortChoices, ApplicationGUI.unplayedFilterChoices);
                ApplicationGUI.changeMade = true;
                addItemField.setText("");
            }
        });

        //Moves the selected item up in the list by one position.
        moveUpButton.setOnAction(e -> {
            int selectionIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectionIndex > 0) {
                list.add(selectionIndex - 1, list.remove(selectionIndex));
                listView.getSelectionModel().select(selectionIndex - 1);
                ApplicationGUI.changeMade = true;
            }
        });

        //Moves the selected item down in the list by one position.
        moveDownButton.setOnAction(e -> {
            int selectionIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectionIndex > -1 && selectionIndex < list.size() - 1) {
                list.add(selectionIndex + 1, list.remove(selectionIndex));
                listView.getSelectionModel().select(selectionIndex + 1);
                ApplicationGUI.changeMade = true;
            }
        });
    }

    public abstract void removeGameItems(String toRemove);

    public abstract void renameGameItems(String oldName, String newName);

}
