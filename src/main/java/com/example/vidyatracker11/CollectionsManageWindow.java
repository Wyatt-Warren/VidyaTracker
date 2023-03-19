package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

//GUI for managing collections
public class CollectionsManageWindow extends VBox {
    //GUI
    Label label = new Label("Manage Collections");
    TextField collectionTextField = new TextField();
    Button addCollectionButton = new Button("Add Collection");
    ListView<GameCollection> collectionListView = new ListView<>();

    //Buttons
    Button removeButton = new Button("Remove Selected Collection");
    Button renameButton = new Button("Rename Selected Collection");
    Button moveUpButton = new Button("Move Up");
    Button moveDownButton = new Button("Move Down");
    VBox buttonBox = new VBox(renameButton, removeButton, moveUpButton, moveDownButton);

    GridPane gridPane = new GridPane();

    //Fields
    boolean selectedRemoved = false;   //If the first item is removed, the ChoiceBox needs to be updated

    public CollectionsManageWindow(ChoiceBox<GameCollection> collectionChoices){
        //GUI
        label.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        collectionListView.setItems(GameLists.collectionList);
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(10);
        gridPane.add(collectionTextField, 0, 0);
        gridPane.add(addCollectionButton, 1, 0);
        gridPane.add(collectionListView, 0, 1);
        gridPane.add(buttonBox, 1, 1);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        GridPane.setValignment(removeButton, VPos.TOP);
        getChildren().addAll(label, gridPane);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);

        addCollectionButton.setOnAction(e1 -> {
            //Create a new collection with a given title

            if(!collectionTextField.getText().equals("")) {
                //Text is entered
                GameLists.collectionList.add(new GameCollection(collectionTextField.getText()));
                collectionTextField.setText("");
                ApplicationGUI.changeMade = true;
            }
        });

        removeButton.setOnAction(e1 -> {
            //Removes the selected collection from the list

            if(collectionListView.getSelectionModel().getSelectedIndex() != -1){
                //A collection is selected

                if(collectionChoices.getSelectionModel().isSelected(collectionListView.getSelectionModel().getSelectedIndex()))
                    //The collection selected in collectionChoices is removed
                    selectedRemoved = true;

                GameLists.collectionList.remove(collectionListView.getSelectionModel().getSelectedItem());
                ApplicationGUI.changeMade = true;
            }
        });

        renameButton.setOnAction(e1 -> {
            //Replaces the selected Collection's title with the current string
            //Local variables
            int selectionInt = collectionListView.getSelectionModel().getSelectedIndex();   //Selected collection
            String newName = collectionTextField.getText();                                 //Text to rename the collection to

            if(!newName.equals("") && selectionInt != -1){
                //Text is entered and a collection is selected
                GameLists.collectionList.get(selectionInt).setTitle(newName);
                ApplicationGUI.changeMade = true;
                collectionTextField.setText("");

                //Solution to a problem where the listview doesn't update after renaming
                //because the observable list doesn't change, only a value in an object within the observable list
                collectionListView.setItems(FXCollections.observableArrayList());
                collectionListView.setItems(GameLists.collectionList);
            }
        });

        moveUpButton.setOnAction(e1 -> {
            //Moves the selected item up in the list by one position
            //Local variables
            int selectionIndex = collectionListView.getSelectionModel().getSelectedIndex(); //Index for the selected collection

            if(selectionIndex > 0){
                //An item is selected and it is not the top item
                GameLists.collectionList.add(selectionIndex - 1, GameLists.collectionList.remove(selectionIndex));
                collectionListView.getSelectionModel().select(selectionIndex - 1);
                ApplicationGUI.changeMade = true;
            }
        });

        moveDownButton.setOnAction(e1 -> {
            //Moves the selected item down in the list by one position
            int selectionIndex = collectionListView.getSelectionModel().getSelectedIndex(); //Index for the selected collection

            if(selectionIndex > -1 && selectionIndex < GameLists.collectionList.size() -1){
                //An item is selected and it is not the bottom.
                GameLists.collectionList.add(selectionIndex + 1, GameLists.collectionList.remove(selectionIndex));
                collectionListView.getSelectionModel().select(selectionIndex + 1);
                ApplicationGUI.changeMade = true;
            }
        });
    }
}
