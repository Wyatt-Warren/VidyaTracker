package com.example.vidyatracker11;

import javafx.scene.control.Button;

public class PlayedGoalFilterWindow extends PlayedFilterWindow{
    //GUI
    Button confirmButton = new Button("Confirm Filters");
    //Fields
    private PlayedGameFilter filter;

    public PlayedGoalFilterWindow(PlayedGameFilter oldFilter) {
        //GUI
        mainLabel.setText("Set Filters for Goal");
        layer1HBox.getChildren().addAll(statusVBox, shortVBox,
                titleVBox, franchiseVBox, ratingVBox);
        layer2HBox.getChildren().addAll(platformVBox, genreVBox, releaseYearVBox,
                percent100VBox, collectionVBox);
        getChildren().add(confirmButton);

        //Set status values
        statusView.getItems().addAll(oldFilter.getPossibleStatuses());

        //Set short values
        shortView.getItems().addAll(oldFilter.getPossibleShortStatuses());

        //Set title value
        titleField.setText(oldFilter.getTitleContains());

        //Set franchise values
        franchiseView.getItems().addAll(oldFilter.getPossibleFranchises());

        //Set rating values
        ratingView.getItems().addAll(oldFilter.getPossibleRatings());

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

        //Set 100% values
        percent100View.getItems().addAll(oldFilter.getPossible100PercentStatuses());

        //Set collection values
        collectionView.getItems().addAll(oldFilter.getPossibleCollections());
    }

    public void setFilters(){
        //Local variables
        int releaseYearMinValue = 0;
        int releaseYearMaxValue = Integer.MAX_VALUE;

        //If text fields for integer values are empty, they should be 0 or MAX_VALUE, otherwise, they should be inputed text
        if (!releaseYearMinField.getText().equals(""))
            releaseYearMinValue = Integer.parseInt(releaseYearMinField.getText());
        if (!(releaseYearMaxField.getText().equals("")))
            releaseYearMaxValue = Integer.parseInt(releaseYearMaxField.getText());

        //Create new filter and use it to create list of valid games
        filter = new PlayedGameFilter(statusView.getItems(), franchiseView.getItems(),
                platformView.getItems(), genreView.getItems(), collectionView.getItems(), shortView.getItems(),
                ratingView.getItems(), percent100View.getItems(), titleField.getText(),
                releaseYearMinValue, releaseYearMaxValue, 0, Integer.MAX_VALUE);
    }

    public PlayedGameFilter getFilter() {
        return filter;
    }
}
