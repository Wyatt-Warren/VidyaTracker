package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;

public abstract class PlayedFilterWindow extends FilterWindow {
    //GUI
    //Short
    Label shortLabel = new Label("Possible Short Statuses:");
    ChoiceBox<String> shortBox = new ChoiceBox<>();
    Button shortAddButton = new Button("Add");
    Button shortRemoveButton = new Button("Remove");
    HBox shortButtonBox = new HBox(shortAddButton, shortRemoveButton);
    ListView<String> shortView = new ListView<>();
    VBox shortVBox = new VBox(shortLabel, shortBox, shortButtonBox, shortView);

    //Rating
    Label ratingLabel = new Label("Possible Ratings:");
    ChoiceBox<Integer> ratingBox = new ChoiceBox<>();
    Button ratingAddButton = new Button("Add");
    Button ratingRemoveButton = new Button("Remove");
    HBox ratingButtonBox = new HBox(ratingAddButton, ratingRemoveButton);
    ListView<Integer> ratingView = new ListView<>();
    VBox ratingVBox = new VBox(ratingLabel, ratingBox, ratingButtonBox, ratingView);

    //Completion Year
    Label completionYearMinLabel = new Label("Min Completion Year:");
    TextField completionYearMinField = new TextField();
    Label completionYearMaxLabel = new Label("Max Completion Year:");
    TextField completionYearMaxField = new TextField();
    VBox completionYearVBox = new VBox(completionYearMinLabel, completionYearMinField, completionYearMaxLabel,
            completionYearMaxField);
    //100 Percent
    Label percent100Label = new Label("Possible 100% Statuses:");
    ChoiceBox<String> percent100Box = new ChoiceBox<>();
    Button percent100AddButton = new Button("Add");
    Button percent100RemoveButton = new Button("Remove");
    HBox percent100ButtonBox = new HBox(percent100AddButton, percent100RemoveButton);
    ListView<String> percent100View = new ListView<>();
    VBox percent100VBox = new VBox(percent100Label, percent100Box, percent100ButtonBox, percent100View);

    public PlayedFilterWindow(){
        //GUI
        setAlignment(Pos.CENTER);
        shortButtonBox.setAlignment(Pos.CENTER);
        ratingButtonBox.setAlignment(Pos.CENTER);
        percent100ButtonBox.setAlignment(Pos.CENTER);
        shortVBox.setAlignment(Pos.TOP_CENTER);
        ratingVBox.setAlignment(Pos.TOP_CENTER);
        completionYearVBox.setAlignment(Pos.TOP_CENTER);
        percent100VBox.setAlignment(Pos.TOP_CENTER);
        shortVBox.setSpacing(5.0);
        shortButtonBox.setSpacing(5.0);
        ratingVBox.setSpacing(5.0);
        ratingButtonBox.setSpacing(5.0);
        completionYearVBox.setSpacing(5.0);
        percent100VBox.setSpacing(5.0);
        percent100ButtonBox.setSpacing(5.0);

        //Set status values
        statusBox.getItems().addAll("Playing", "Completed", "On Hold");

        //Set short values
        shortBox.getItems().addAll("Yes", "No", "Blank");

        //Set franchise values
        ObservableList<String> franchises = FXCollections.observableArrayList();    //List of all franchises
        for(PlayedGame game : GameLists.playedList)
            //Iterate for each PlayedGame
            if( !game.getFranchise().equals("") && !franchises.contains(game.getFranchise()))
                //Game has a franchise and it isn't already in the list
                franchises.add(game.getFranchise());
        //Sort franchise list
        Collections.sort(franchises);
        franchiseBox.getItems().addAll(franchises);

        //Set rating values
        ratingBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //Only allow integers for completionYearMinField and completionYearMaxField
        completionYearMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));
        completionYearMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Set 100% values
        percent100Box.getItems().addAll("Yes", "No", "Blank");

        shortAddButton.setOnAction(e -> {
            if (!shortView.getItems().contains(shortBox.getSelectionModel().getSelectedItem()) &&
                    shortBox.getSelectionModel().getSelectedItem() != null)
                //When shortAddButton is pressed, If an item is selected, and it is not already in shortView, add it to shortView
                shortView.getItems().add(shortBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from shortView
        shortRemoveButton.setOnAction(e -> shortView.getItems().remove(shortView.getSelectionModel().getSelectedItem()));

        ratingAddButton.setOnAction(e -> {
            if (!ratingView.getItems().contains(ratingBox.getSelectionModel().getSelectedItem()) &&
                    ratingBox.getSelectionModel().getSelectedItem() != null)
                //When ratingAddButton is pressed, If an item is selected, and it is not already in ratingView, add it to ratingView
                ratingView.getItems().add(ratingBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from ratingView
        ratingRemoveButton.setOnAction(e -> ratingView.getItems().remove(ratingView.getSelectionModel().getSelectedItem()));

        percent100AddButton.setOnAction(e -> {
            if (!percent100View.getItems().contains(percent100Box.getSelectionModel().getSelectedItem()) &&
                    percent100Box.getSelectionModel().getSelectedItem() != null)
                //When percent100AddButton is pressed, If an item is selected, and it is not already in percent100View, add it to percent100View
                percent100View.getItems().add(percent100Box.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from percent100View
        percent100RemoveButton.setOnAction(e -> percent100View.getItems().remove(percent100View.getSelectionModel().getSelectedItem()));
    }
}
