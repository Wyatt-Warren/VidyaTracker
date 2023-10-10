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
    ListView<PlayedGame> listView = new ListView<>();
    Button addButton = new Button("Add Selected Game");
    Button removeButton = new Button("Remove Selected Game");
    Label countLabel = new Label("");
    VBox vbox = new VBox(5, addButton, removeButton, countLabel);

    //Fields
    ObservableList<PlayedGame> games = FXCollections.observableArrayList();    //Title of each game in the list

    public PlayedTempList() {
        //GUI
        listView.setItems(games);
        getChildren().addAll(listView, vbox);
        setPadding(new Insets(5));
        setSpacing(5);

        addButton.setOnAction(e -> {
            //Add a game to the list
            if (ApplicationGUI.playedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //If an item is selected

                games.add(ApplicationGUI.playedGamesTable.getSelectionModel().getSelectedItem());
                updateLabels();
                ApplicationGUI.changeMade = true;
                ApplicationGUI.setStageTitle();
            }
        });

        removeButton.setOnAction(e -> {
            //Remove an item from the list
            //Local variables
            int index = listView.getSelectionModel().getSelectedIndex();    //Selected index

            if (index != -1) {
                //An item is selected

                games.remove(index);
                updateLabels();
                ApplicationGUI.changeMade = true;
                ApplicationGUI.setStageTitle();
            }
        });
    }

    //Updates labels that show total count of items in the list and total hours.
    public void updateLabels() {
        if (games.isEmpty()) {
            //There are no games in the list
            countLabel.setText("");
        } else {
            //Local variables
            int count = games.size();  //Length of the list

            //Update labels
            countLabel.setText("Count: " + count);
        }
    }

    //listView getter
    public ListView<PlayedGame> getListView() {
        return listView;
    }

    //games getter
    public ObservableList<PlayedGame> getGames() {
        return games;
    }

    //games setter
    public void setGames(ObservableList<PlayedGame> games) {
        this.games = games;
    }
}
