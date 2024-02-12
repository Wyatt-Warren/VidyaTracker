package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;

public class UnplayedAdvancedFilters extends UnplayedFilterWindow{
    //GUI
    Button confirmButton = new Button("Confirm Filters");
    Button resetButton = new Button("Reset All Filters");
    //Fields
    private UnplayedGameFilter filter;

    public UnplayedAdvancedFilters(UnplayedGameFilter oldFilter){
        //GUI
        mainLabel.setText("Choose Advanced Filters");
        layer1HBox.getChildren().addAll(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox);
        layer2HBox.getChildren().addAll(releaseYearVBox, addedYearVBox, hoursVBox,
                deckVBox, collectionVBox);
        getChildren().add(1, resetButton);
        getChildren().addAll(confirmButton);

        //Set status values
        statusView.getItems().addAll(oldFilter.getPossibleStatuses());

        //Set title value
        titleField.setText(oldFilter.getTitleContains());

        //Set franchise values
        franchiseView.getItems().addAll(oldFilter.getPossibleFranchises());

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

        //Set added year values
        if(oldFilter.getMinAddedYear() == 0)
            addedYearMinField.setText("");
        else
            addedYearMinField.setText("" + oldFilter.getMinAddedYear());
        if(oldFilter.getMaxAddedYear() == Integer.MAX_VALUE)
            addedYearMaxField.setText("");
        else
            addedYearMaxField.setText("" + oldFilter.getMaxAddedYear());

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
        deckView.getItems().addAll(oldFilter.getPossibleDeckStatuses());

        //Set collection values
        collectionView.getItems().addAll(oldFilter.possibleCollections);

        resetButton.setOnAction(e -> {
            //Clear all selected filters when pressed

            statusView.setItems(FXCollections.observableArrayList());
            titleField.setText("");
            franchiseView.setItems(FXCollections.observableArrayList());
            platformView.setItems(FXCollections.observableArrayList());
            genreView.setItems(FXCollections.observableArrayList());
            releaseYearMinField.setText("");
            releaseYearMaxField.setText("");
            addedYearMinField.setText("");
            addedYearMaxField.setText("");
            hoursMinField.setText("");
            hoursMaxField.setText("");
            deckView.setItems(FXCollections.observableArrayList());
            collectionView.setItems(FXCollections.observableArrayList());
        });
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
