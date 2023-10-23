package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlayedRandomListGenerator extends RandomListGenerator{
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

    public PlayedRandomListGenerator(){
        //GUI
        layer1HBox.getChildren().addAll(lengthVBox, statusVBox, shortVBox,
                titleVBox, franchiseVBox, ratingVBox);
        layer2HBox.getChildren().addAll(platformVBox, genreVBox, releaseYearVBox,
                completionYearVBox, percent100VBox, collectionVBox);
        shortButtonBox.setAlignment(Pos.CENTER);
        ratingButtonBox.setAlignment(Pos.CENTER);
        percent100ButtonBox.setAlignment(Pos.CENTER);
        shortVBox.setAlignment(Pos.TOP_CENTER);
        ratingVBox.setAlignment(Pos.TOP_CENTER);
        completionYearVBox.setAlignment(Pos.TOP_CENTER);
        percent100VBox.setAlignment(Pos.TOP_CENTER);
        shortVBox.setSpacing(5.0);
        ratingVBox.setSpacing(5.0);
        completionYearVBox.setSpacing(5.0);
        percent100VBox.setSpacing(5.0);

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
                    shortBox.getSelectionModel().getSelectedItem() != null)
                //When ratingAddButton is pressed, If an item is selected, and it is not already in ratingView, add it to ratingView
                ratingView.getItems().add(ratingBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from ratingView
        ratingRemoveButton.setOnAction(e -> ratingView.getItems().remove(ratingView.getSelectionModel().getSelectedItem()));

        percent100AddButton.setOnAction(e -> {
            if (!percent100View.getItems().contains(percent100Box.getSelectionModel().getSelectedItem()) &&
                    shortBox.getSelectionModel().getSelectedItem() != null)
                //When percent100AddButton is pressed, If an item is selected, and it is not already in percent100View, add it to percent100View
                percent100View.getItems().add(percent100Box.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from percent100View
        percent100RemoveButton.setOnAction(e -> percent100View.getItems().remove(percent100View.getSelectionModel().getSelectedItem()));

        generateButton.setOnAction(e -> {
            //Generate a random list of items based on selected filters
            if (!lengthField.getText().equals("")) {
                //There must be a number in lengthField
                //Local variables
                Random rand = new Random();
                ArrayList<PlayedGame> potentialList = new ArrayList<>();  //List of items that fit the requirements selected

                //Clear the existing list to generate a new one
                generatedList.getItems().clear();

                for (PlayedGame game : GameLists.playedList) {
                    //Iterate for every PlayedGame

                    if ((statusView.getItems().isEmpty() || statusView.getItems().contains(game.getStatus())) &&
                            //Status is selected and game matches
                            (shortView.getItems().isEmpty() || shortView.getItems().contains(game.getShortStatus())) &&
                            //Short status is selected and game matches
                            (titleField.getText().equals("") || game.getTitle().toLowerCase().contains(titleField.getText().toLowerCase())) &&
                            //Title is selected and game matches
                            (franchiseView.getItems().isEmpty() || franchiseView.getItems().contains(game.getFranchise())) &&
                            //franchise is selected and game matches
                            (ratingView.getItems().isEmpty() || ratingView.getItems().contains(game.getRating())) &&
                            //Rating is selected and game matches
                            (platformView.getItems().isEmpty() || platformView.getItems().contains(game.getPlatform())) &&
                            //platform is selected and game matches
                            (genreView.getItems().isEmpty() || genreView.getItems().contains(game.getGenre())) &&
                            //genre is selected and game matches
                            (releaseYearMinField.getText().equals("") || game.getReleaseYear() >= Integer.parseInt(releaseYearMinField.getText())) &&
                            //release year is entered and game is greater or equal
                            (releaseYearMaxField.getText().equals("") || game.getReleaseYear() <= Integer.parseInt(releaseYearMaxField.getText())) &&
                            //release year is entered and game is less than or equal
                            (completionYearMinField.getText().equals("") || game.getReleaseYear() >= Integer.parseInt(completionYearMinField.getText())) &&
                            //completion year is entered and game is greater or equal
                            (completionYearMaxField.getText().equals("") || game.getReleaseYear() <= Integer.parseInt(completionYearMaxField.getText())) &&
                            //completion year is entered and game is less than or equal
                            (percent100View.getItems().isEmpty() || percent100View.getItems().contains(game.getPercent100())) &&
                            //100% status is selected and game matches
                            (collectionView.getItems().isEmpty() || gameInCollectionView(game)))

                        //Add valid game to potentialList
                        potentialList.add(game);
                }

                if (!potentialList.isEmpty()) {
                    //If there are items that are compatible with filters
                    //Local variables
                    int gameNum = Integer.parseInt(lengthField.getText());  //Amount of games in the list

                    if (gameNum > potentialList.size())
                        //If there are less possible games than what was entered, only generate as many as possible
                        gameNum = potentialList.size();

                    for (int i = 0; i < gameNum; i++)
                        //Add gameNum amount of games to the final list
                        generatedList.getItems().add(potentialList.remove(rand.nextInt(potentialList.size())).getTitle());
                }
            }
        });
    }
}
