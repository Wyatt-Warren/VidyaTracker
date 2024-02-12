package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;

public abstract class UnplayedFilterWindow extends FilterWindow{
    //GUI
    //Added Year
    Label addedYearMinLabel = new Label("Min Added Year:");
    TextField addedYearMinField = new TextField();
    Label addedYearMaxLabel = new Label("Max Added Year:");
    TextField addedYearMaxField = new TextField();
    VBox addedYearVBox = new VBox(addedYearMinLabel, addedYearMinField, addedYearMaxLabel,
            addedYearMaxField);

    //Hours
    Label hoursMinLabel = new Label("Min Hours:");
    TextField hoursMinField = new TextField();
    Label hoursMaxLabel = new Label("Max Hours:");
    TextField hoursMaxField = new TextField();
    VBox hoursVBox = new VBox(hoursMinLabel, hoursMinField, hoursMaxLabel, hoursMaxField);

    //Deck
    Label deckLabel = new Label("Possible Deck Statuses:");
    ChoiceBox<String> deckBox = new ChoiceBox<>();
    Button deckAddButton = new Button("Add");
    Button deckRemoveButton = new Button("Remove");
    HBox deckButtonBox = new HBox(deckAddButton, deckRemoveButton);
    ListView<String> deckView = new ListView<>();
    VBox deckVBox = new VBox(deckLabel, deckBox, deckButtonBox, deckView);
    public UnplayedFilterWindow(){
        //GUI
        setAlignment(Pos.CENTER);
        addedYearVBox.setAlignment(Pos.TOP_CENTER);
        deckButtonBox.setAlignment(Pos.CENTER);
        hoursVBox.setAlignment(Pos.TOP_CENTER);
        deckVBox.setAlignment(Pos.TOP_CENTER);
        addedYearVBox.setSpacing(5.0);
        deckButtonBox.setSpacing(5.0);
        hoursVBox.setSpacing(5.0);
        deckVBox.setSpacing(5.0);

        //Set status values
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist", "Ignored");

        //Set franchise values
        ObservableList<String> franchises = FXCollections.observableArrayList();    //List of all franchises
        for(UnplayedGame game : GameLists.unplayedList)
            //Iterate for each UnplayedGame
            if( !game.getFranchise().equals("") && !franchises.contains(game.getFranchise()))
                //Game has a franchise and it isn't already in the list
                franchises.add(game.getFranchise());
        //Sort franchise list
        Collections.sort(franchises);
        franchiseBox.getItems().addAll(franchises);

        //Only allow integers for addedMinField and addedMaxField
        addedYearMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));
        addedYearMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Only allow doubles for hoursMinField and hoursMaxField
        hoursMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));
        hoursMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));

        //Set deck values
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");

        deckAddButton.setOnAction(e -> {
            if (!deckView.getItems().contains(deckBox.getSelectionModel().getSelectedItem()) &&
                    deckBox.getSelectionModel().getSelectedItem() != null)
                //When deckAddButton is pressed, If an item is selected, and it is not already in deckView, add it to deckView
                deckView.getItems().add(deckBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from deckView
        deckRemoveButton.setOnAction(e -> deckView.getItems().remove(deckView.getSelectionModel().getSelectedItem()));
    }
}
