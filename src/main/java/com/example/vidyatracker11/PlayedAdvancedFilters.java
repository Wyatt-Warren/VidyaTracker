package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;

public class PlayedAdvancedFilters extends PlayedFilterWindow{
    //GUI
    Button confirmButton = new Button("Confirm Filters");
    Button resetButton = new Button("Reset All Filters");
    //Fields
    private PlayedGameFilter filter;

    public PlayedAdvancedFilters(PlayedGameFilter oldFilter){
        //GUI
        mainLabel.setText("Choose Advanced Filters");
        layer1HBox.getChildren().addAll(statusVBox, shortVBox, titleVBox,
                franchiseVBox, ratingVBox, platformVBox);
        layer2HBox.getChildren().addAll(genreVBox, releaseYearVBox,
                completionYearVBox, percent100VBox, collectionVBox);
        getChildren().add(1, resetButton);
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

        //Set completion year values
        if(oldFilter.getMinCompletionYear() == 0)
            completionYearMinField.setText("");
        else
            completionYearMinField.setText("" + oldFilter.getMinCompletionYear());
        if(oldFilter.getMaxCompletionYear() == Integer.MAX_VALUE)
            completionYearMaxField.setText("");
        else
            completionYearMaxField.setText("" + oldFilter.getMaxCompletionYear());

        //Set 100% values
        percent100View.getItems().addAll(oldFilter.getPossible100PercentStatuses());

        //Set collection values
        collectionView.getItems().addAll(oldFilter.getPossibleCollections());

        resetButton.setOnAction(e -> {
            //Clear all selected filters when pressed

            statusView.setItems(FXCollections.observableArrayList());
            shortView.setItems(FXCollections.observableArrayList());
            titleField.setText("");
            franchiseView.setItems(FXCollections.observableArrayList());
            ratingView.setItems(FXCollections.observableArrayList());
            platformView.setItems(FXCollections.observableArrayList());
            genreView.setItems(FXCollections.observableArrayList());
            releaseYearMinField.setText("");
            releaseYearMaxField.setText("");
            completionYearMinField.setText("");
            completionYearMaxField.setText("");
            percent100View.setItems(FXCollections.observableArrayList());
            collectionView.setItems(FXCollections.observableArrayList());
        });
    }

    public void setFilters(){
        //Local variables
        int releaseYearMinValue = 0;
        int releaseYearMaxValue = Integer.MAX_VALUE;
        int completionYearMinValue = 0;
        int completionYearMaxValue = Integer.MAX_VALUE;

        //If text fields for integer values are empty, they should be 0 or MAX_VALUE, otherwise, they should be inputed text
        if (!releaseYearMinField.getText().equals(""))
            releaseYearMinValue = Integer.parseInt(releaseYearMinField.getText());
        if (!(releaseYearMaxField.getText().equals("")))
            releaseYearMaxValue = Integer.parseInt(releaseYearMaxField.getText());
        if (!completionYearMinField.getText().equals(""))
            completionYearMinValue = Integer.parseInt(completionYearMinField.getText());
        if (!(completionYearMaxField.getText().equals("")))
            completionYearMaxValue = Integer.parseInt(completionYearMaxField.getText());

        //Create new filter and use it to create list of valid games
        filter = new PlayedGameFilter(statusView.getItems(), franchiseView.getItems(),
                platformView.getItems(), genreView.getItems(), collectionView.getItems(), shortView.getItems(),
                ratingView.getItems(), percent100View.getItems(), titleField.getText(),
                releaseYearMinValue, releaseYearMaxValue, completionYearMinValue, completionYearMaxValue);
    }

    public PlayedGameFilter getFilter() {
        return filter;
    }
}
