package com.example.vidyatracker11;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class PlayedAddEditGoal extends AddEditGoal{
    //Fields
    PlayedGameFilter filter;

    public PlayedAddEditGoal(){
        //Count games meeting requirements and set start progress to count
        enterCurrentStartProgress.setOnAction(e -> progressStartBox.setText("" + filter.getFilterCount(countAllCollection)));

        //Count games meeting requirements and set goal progress to count
        enterCurrentGoalProgress.setOnAction(e -> progressGoalBox.setText("" + filter.getFilterCount(countAllCollection)));

        filterButton.setOnAction(e -> {
            //Open window to manage filters
            //Local variables
            Stage stage = new Stage();
            PlayedAdvancedFilters playedAdvancedFilters = new PlayedAdvancedFilters(filter);
            Scene scene = new Scene(playedAdvancedFilters);
            CheckBox countAllCollectionCheck = new CheckBox("Count Unplayed Games in Collections");

            //GUI
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Set Filters for Goal");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setHeight(750);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            playedAdvancedFilters.mainLabel.setText("Set Filters for Goal");
            playedAdvancedFilters.layer1HBox.getChildren().remove(playedAdvancedFilters.platformVBox);
            playedAdvancedFilters.layer2HBox.getChildren().add(0, playedAdvancedFilters.platformVBox);
            playedAdvancedFilters.layer2HBox.getChildren().remove(playedAdvancedFilters.completionYearVBox);

            //Add checkbox for checkAllCollection
            playedAdvancedFilters.collectionVBox.getChildren().add(countAllCollectionCheck);
            countAllCollectionCheck.setSelected(countAllCollection);

            playedAdvancedFilters.confirmButton.setOnAction(e1 -> {
                //Set current filter to filter generated by playedGoalFilterWindow
                playedAdvancedFilters.setFilters();
                filter = playedAdvancedFilters.getFilter();

                //Set countAllCollection.
                countAllCollection = countAllCollectionCheck.isSelected();

                stage.close();
            });

            stage.show();
        });
    }
}
