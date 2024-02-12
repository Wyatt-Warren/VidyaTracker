package com.example.vidyatracker11;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Random;

public class UnplayedRandomListGenerator extends UnplayedFilterWindow{
    //GUI
    //Length
    Label lengthLabel = new Label("List Size:");
    TextField lengthField = new TextField();
    VBox lengthVBox = new VBox(lengthLabel, lengthField);
    Button generateButton = new Button("Generate List");
    ListView<String> generatedList = new ListView<>();

    public UnplayedRandomListGenerator(){
        //GUI
        lengthVBox.setAlignment(Pos.TOP_CENTER);
        lengthVBox.setSpacing(5.0);
        mainLabel.setText("Generate List Based on Filters");
        getChildren().addAll(generateButton, generatedList);
        layer1HBox.getChildren().addAll(lengthVBox, statusVBox, titleVBox,
                franchiseVBox, platformVBox, genreVBox);
        layer2HBox.getChildren().addAll(releaseYearVBox, addedYearVBox,
                hoursVBox, deckVBox, collectionVBox);

        //Only allow integers for lengthField
        lengthField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        generateButton.setOnAction(e -> {
            //Generate a random list of items based on selected filters
            if (!lengthField.getText().equals("")) {
                //There must be a number in lengthField
                //Local variables
                Random rand = new Random();
                UnplayedGameFilter newFilter;               //Filter object used to generate list
                ObservableList<UnplayedGame> potentialList; //List of items that fit the requirements selected
                int releaseYearMinValue = 0;
                int releaseYearMaxValue = Integer.MAX_VALUE;
                int addedYearMinValue = 0;
                int addedYearMaxValue = Integer.MAX_VALUE;
                double hoursMinValue = 0;
                double hoursMaxValue = Double.MAX_VALUE;

                //If text fields for numerical values are empty, they should be 0 or MAX_VALUE, otherwise, they should be inputed text
                if (!releaseYearMinField.getText().equals(""))
                    releaseYearMinValue = Integer.parseInt(releaseYearMinField.getText());
                if (!releaseYearMaxField.getText().equals(""))
                    releaseYearMaxValue = Integer.parseInt(releaseYearMaxField.getText());
                if (!addedYearMinField.getText().equals(""))
                    addedYearMinValue = Integer.parseInt(addedYearMinField.getText());
                if (!(addedYearMaxField.getText().equals("")))
                    addedYearMaxValue = Integer.parseInt(addedYearMaxField.getText());
                if (!hoursMinField.getText().equals(""))
                    hoursMinValue = Integer.parseInt(hoursMinField.getText());
                if (!hoursMaxField.getText().equals(""))
                    hoursMaxValue = Integer.parseInt(hoursMaxField.getText());

                //Create new filter and use it to create list of valid games
                newFilter = new UnplayedGameFilter(statusView.getItems(), franchiseView.getItems(),
                        platformView.getItems(), genreView.getItems(), collectionView.getItems(), deckView.getItems(),
                        titleField.getText(), releaseYearMinValue, releaseYearMaxValue, addedYearMinValue,
                        addedYearMaxValue, hoursMinValue, hoursMaxValue);
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
