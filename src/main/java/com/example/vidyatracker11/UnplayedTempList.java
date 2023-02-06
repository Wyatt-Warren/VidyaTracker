package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UnplayedTempList extends HBox {
  ObservableList<String> titles = FXCollections.observableArrayList();
  
  ObservableList<Double> hours = FXCollections.observableArrayList();
  
  ListView<String> listView = new ListView<>(titles);
  
  Button addButton = new Button("Add Selected Game");
  
  Button removeButton = new Button("Remove Selected Game");
  
  Label countLabel = new Label("");
  
  Label hoursLabel = new Label("");
  
  VBox vbox = new VBox(5.0, addButton, removeButton, countLabel, hoursLabel);
  
  public UnplayedTempList(UnplayedGamesTable table) {
    getChildren().addAll(listView, vbox);
    setPadding(new Insets(5.0));
    setSpacing(5.0D);
    addButton.setOnAction(e -> {
          if (table.getSelectionModel().getSelectedIndex() != -1) {
            titles.add(table.getSelectionModel().getSelectedItem().getTitle());
            hours.add(table.getSelectionModel().getSelectedItem().getHours());
            updateLabels();
          } 
        });
    removeButton.setOnAction(e -> {
          int index = listView.getSelectionModel().getSelectedIndex();
          if (index != -1) {
            titles.remove(index);
            hours.remove(index);
            updateLabels();
          } 
        });
  }
  
  public void updateLabels() {
    if (titles.isEmpty()) {
      countLabel.setText("");
      hoursLabel.setText("");
    } else {
      int count = titles.size();
      double hoursTotal = 0.0;
      for (Double hour : hours)
        hoursTotal += hour;
      countLabel.setText("Count: " + count);
      hoursLabel.setText("Total Hours: " + hoursTotal);
    } 
  }
  
  public ObservableList<String> getTitles() {
    return titles;
  }
}
