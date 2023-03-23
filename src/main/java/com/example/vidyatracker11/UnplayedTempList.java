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
    ListView<String> listView = new ListView<>();
    Button addButton = new Button("Add Selected Game");
    Button removeButton = new Button("Remove Selected Game");
    Label countLabel = new Label("");
    Label hoursLabel = new Label("");
    VBox vbox = new VBox(5, addButton, removeButton, countLabel, hoursLabel);

    //Fields
    ObservableList<String> titles = FXCollections.observableArrayList();    //Title of each game in the list
    ObservableList<Double> hours = FXCollections.observableArrayList();     //Hours of each game in the list

    public UnplayedTempList() {
        //GUI
        listView.setItems(titles);
        getChildren().addAll(listView, vbox);
        setPadding(new Insets(5));
        setSpacing(5);

        addButton.setOnAction(e -> {
            //Add a game to the list
            if (ApplicationGUI.unplayedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //If an item is selected

                titles.add(ApplicationGUI.unplayedGamesTable.getSelectionModel().getSelectedItem().getTitle());
                hours.add(ApplicationGUI.unplayedGamesTable.getSelectionModel().getSelectedItem().getHours());
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
                hours.remove(index);
                updateLabels();
            }
        });
    }

    //Updates labels that show total count of items in the list and total hours.
    public void updateLabels() {
        if (titles.isEmpty()) {
            //There are no games in the list
            countLabel.setText("");
            hoursLabel.setText("");
        } else {
            //Local variables
            int count = titles.size();  //Length of the list
            double hoursTotal = 0.0;    //Total hours of each game in the list

            for (Double hour : hours)
                //Add up all hours in the list
                hoursTotal += hour;

            //Update labels
            countLabel.setText("Count: " + count);
            hoursLabel.setText("Total Hours: " + hoursTotal);
        }
    }

    //titles getter
    public ObservableList<String> getTitles() {
        return titles;
    }
}
