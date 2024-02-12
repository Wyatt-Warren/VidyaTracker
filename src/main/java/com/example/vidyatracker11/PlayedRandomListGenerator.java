package com.example.vidyatracker11;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Random;

public class PlayedRandomListGenerator extends PlayedFilterWindow{
    //GUI
    //Length
    Label lengthLabel = new Label("List Size:");
    TextField lengthField = new TextField();
    VBox lengthVBox = new VBox(lengthLabel, lengthField);
    Button generateButton = new Button("Generate List");
    ListView<String> generatedList = new ListView<>();

    public PlayedRandomListGenerator(){
        //GUI
        lengthVBox.setAlignment(Pos.TOP_CENTER);
        lengthVBox.setSpacing(5.0);
        mainLabel.setText("Generate List Based on Filters");
        getChildren().addAll(generateButton, generatedList);
        layer1HBox.getChildren().addAll(lengthVBox, statusVBox, shortVBox,
                titleVBox, franchiseVBox, ratingVBox);
        layer2HBox.getChildren().addAll(platformVBox, genreVBox, releaseYearVBox,
                completionYearVBox, percent100VBox, collectionVBox);

        //Only allow integers for lengthField
        lengthField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        generateButton.setOnAction(e -> {
            //Generate a random list of items based on selected filters
            if (!lengthField.getText().equals("")) {
                //There must be a number in lengthField
                //Local variables
                Random rand = new Random();
                PlayedGameFilter newFilter;                 //Filter object used to generate list
                ObservableList<PlayedGame> potentialList;   //List of items that fit the requirements selected
                int releaseYearMinValue = 0;
                int releaseYearMaxValue = Integer.MAX_VALUE;
                int completionYearMinValue = 0;
                int completionYearMaxValue = Integer.MAX_VALUE;

                //If text fields for integer values are empty, they should be 0 or MAX_VALUE, otherwise, they should be inputed text
                if (!releaseYearMinField.getText().equals(""))
                    releaseYearMinValue = Integer.parseInt(releaseYearMinField.getText());
                if (!releaseYearMaxField.getText().equals(""))
                    releaseYearMaxValue = Integer.parseInt(releaseYearMaxField.getText());
                if (!completionYearMinField.getText().equals(""))
                    completionYearMinValue = Integer.parseInt(completionYearMinField.getText());
                if (!completionYearMaxField.getText().equals(""))
                    completionYearMaxValue = Integer.parseInt(completionYearMaxField.getText());

                //Create new filter and use it to create list of valid games
                newFilter = new PlayedGameFilter(statusView.getItems(), franchiseView.getItems(),
                        platformView.getItems(), genreView.getItems(), collectionView.getItems(), shortView.getItems(),
                        ratingView.getItems(), percent100View.getItems(), titleField.getText(),
                        releaseYearMinValue, releaseYearMaxValue, completionYearMinValue, completionYearMaxValue);
                potentialList = newFilter.filteredList();

                //Clear the existing list to generate a new one
                generatedList.getItems().clear();

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
