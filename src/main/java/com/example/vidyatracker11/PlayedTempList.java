package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayedTempList extends HBox {
    //GUI
    ListView<String> listView = new ListView<>();
    Button addButton = new Button("Add Selected Game");
    Button removeButton = new Button("Remove Selected Game");
    Label countLabel = new Label("");
    VBox vbox = new VBox(5, addButton, removeButton, countLabel);

    //Fields
    ObservableList<String> titles = FXCollections.observableArrayList();    //Title of each game in the list

    public PlayedTempList() {
        //GUI
        listView.setItems(titles);
        getChildren().addAll(listView, vbox);
        setPadding(new Insets(5));
        setSpacing(5);

        addButton.setOnAction(e -> {
            //Add a game to the list
            if (ApplicationGUI.playedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //If an item is selected

                titles.add(ApplicationGUI.playedGamesTable.getSelectionModel().getSelectedItem().getTitle());
                updateLabels();
            }
        });

        removeButton.setOnAction(e -> {
            //Remove an item from the list
            //Local variables
            int index = listView.getSelectionModel().getSelectedIndex();    //Selected index

            if (index != -1) {
                //An item is selected

                titles.remove(index);
                updateLabels();
            }
        });
    }

    //Updates labels that show total count of items in the list and total hours.
    public void updateLabels() {
        if (titles.isEmpty()) {
            //There are no games in the list
            countLabel.setText("");
        } else {
            //Local variables
            int count = titles.size();  //Length of the list

            //Update labels
            countLabel.setText("Count: " + count);
        }
    }

    //titles getter
    public ObservableList<String> getTitles() {
        return titles;
    }
}
