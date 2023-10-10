package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Section on the main unplayed games window where the user can select games to add to a temporary list.
public class UnplayedTempList extends HBox {
    //GUI
    ListView<UnplayedGame> listView = new ListView<>();
    Button addButton = new Button("Add Selected Game");
    Button removeButton = new Button("Remove Selected Game");
    Label countLabel = new Label("");
    Label hoursLabel = new Label("");
    VBox vbox = new VBox(5, addButton, removeButton, countLabel, hoursLabel);

    //Fields
    ObservableList<UnplayedGame> games = FXCollections.observableArrayList();    //Games in list

    public UnplayedTempList() {
        //GUI
        listView.setItems(games);
        getChildren().addAll(listView, vbox);
        setPadding(new Insets(5));
        setSpacing(5);

        addButton.setOnAction(e -> {
            //Add a game to the list
            if (ApplicationGUI.unplayedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //If an item is selected

                games.add(ApplicationGUI.unplayedGamesTable.getSelectionModel().getSelectedItem());
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
            hoursLabel.setText("");
        } else {
            //Local variables
            int count = games.size();  //Length of the list
            double hoursTotal = 0.0;    //Total hours of each game in the list

            for (UnplayedGame game : games)
                //Add up all hours in the list
                hoursTotal += game.getHours();

            //Update labels
            countLabel.setText("Count: " + count);
            hoursLabel.setText("Total Hours: " + hoursTotal);
        }
    }

    //listView getter
    public ListView<UnplayedGame> getListView() {
        return listView;
    }

    //games getter
    public ObservableList<UnplayedGame> getGames() {
        return games;
    }

    //games setter
    public void setGames(ObservableList<UnplayedGame> games) {
        this.games = games;
    }
}
