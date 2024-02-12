package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;

public class UnplayedGoalFilterWindow extends GoalFilterWindow{
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

    //Fields
    private UnplayedGameFilter filter;

    public UnplayedGoalFilterWindow(UnplayedGameFilter oldFilter){
        //GUI
        layer1HBox.getChildren().addAll(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox);
        layer2HBox.getChildren().addAll(releaseYearVBox, addedYearVBox, hoursVBox,
                deckVBox, collectionVBox);
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
        statusView.getItems().addAll(oldFilter.getPossibleStatuses());

        //Set platform values
        platformView.getItems().addAll(oldFilter.getPossiblePlatforms());

        //Set genre values
        genreView.getItems().addAll(oldFilter.getPossibleGenres());

        //Set release year values
        if(oldFilter.getMinReleaseYear() == 0)
            releaseYearMinField.setText("");
        else
            releaseYearMinField.setText("" + oldFilter.getMinReleaseYear());
        if(oldFilter.getMaxReleaseYear() == Integer.MAX_VALUE)
            releaseYearMaxField.setText("");
        else
            releaseYearMaxField.setText("" + oldFilter.getMaxReleaseYear());

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
        franchiseView.getItems().addAll(oldFilter.getPossibleFranchises());

        //Only allow integers for addedMinField and addedMaxField
        addedYearMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));
        addedYearMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Set added year values
        if(oldFilter.getMinAddedYear() == 0)
            addedYearMinField.setText("");
        else
            addedYearMinField.setText("" + oldFilter.getMinAddedYear());
        if(oldFilter.getMaxAddedYear() == Integer.MAX_VALUE)
            addedYearMaxField.setText("");
        else
            addedYearMaxField.setText("" + oldFilter.getMaxAddedYear());

        //Only allow doubles for hoursMinField and hoursMaxField
        hoursMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));
        hoursMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));

        //Set hours values
        if(oldFilter.getMinHours() == 0)
            hoursMinField.setText("");
        else
            hoursMinField.setText("" + oldFilter.getMinHours());
        if(oldFilter.getMinHours() == Double.MAX_VALUE)
            hoursMaxField.setText("");
        else
            hoursMaxField.setText("" + oldFilter.getMaxHours());

        //Set deck values
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
        deckView.getItems().addAll(oldFilter.getPossibleDeckStatuses());

        //Set collection values
        collectionBox.getItems().addAll(GameLists.collectionList);
        collectionView.getItems().addAll(oldFilter.possibleCollections);

        deckAddButton.setOnAction(e -> {
            if (!deckView.getItems().contains(deckBox.getSelectionModel().getSelectedItem()) &&
                    deckBox.getSelectionModel().getSelectedItem() != null)
                //When deckAddButton is pressed, If an item is selected, and it is not already in deckView, add it to deckView
                deckView.getItems().add(deckBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from deckView
        deckRemoveButton.setOnAction(e -> deckView.getItems().remove(deckView.getSelectionModel().getSelectedItem()));

    }

    public void setFilters(){
        int releaseYearMinValue = 0;
        int releaseYearMaxValue = Integer.MAX_VALUE;
        int addedYearMinValue = 0;
        int addedYearMaxValue = Integer.MAX_VALUE;
        double hoursMinValue = 0;
        double hoursMaxValue = Double.MAX_VALUE;

        //If text fields for numerical values are empty, they should be 0 or MAX_VALUE, otherwise, they should be inputed text
        if (!releaseYearMinField.getText().equals(""))
            releaseYearMinValue = Integer.parseInt(releaseYearMinField.getText());
        if (!(releaseYearMaxField.getText().equals("")))
            releaseYearMaxValue = Integer.parseInt(releaseYearMaxField.getText());
        if (!addedYearMinField.getText().equals(""))
            addedYearMinValue = Integer.parseInt(addedYearMinField.getText());
        if (!(addedYearMaxField.getText().equals("")))
            addedYearMaxValue = Integer.parseInt(addedYearMaxField.getText());
        if (!hoursMinField.getText().equals(""))
            hoursMinValue = Double.parseDouble(hoursMinField.getText());
        if (!hoursMaxField.getText().equals(""))
            hoursMaxValue = Double.parseDouble(hoursMaxField.getText());

        //Create new filter and use it to create list of valid games
        filter = new UnplayedGameFilter(statusView.getItems(), franchiseView.getItems(),
                platformView.getItems(), genreView.getItems(), collectionView.getItems(), deckView.getItems(),
                titleField.getText(), releaseYearMinValue, releaseYearMaxValue, addedYearMinValue, addedYearMaxValue,
                hoursMinValue, hoursMaxValue);
    }

    public UnplayedGameFilter getFilter() {
        return filter;
    }
}