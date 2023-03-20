package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

//Box on the main unplayed game window that shows stats relating to statuses.
public class StatusCountBoxUnplayed extends GridPane {
    Label statusLabel = new Label("Status");

    Label countLabel = new Label("Count");

    Label HLTBLabel = new Label("HLTB Hours");

    Label backlogLabel = new Label("Backlog");

    Label backlogCountLabel = new Label();

    Label backlogHoursLabel = new Label();

    Label subBacklogLabel = new Label("SubBacklog");

    Label subBacklogCountLabel = new Label();

    Label subBacklogHoursLabel = new Label();

    Label wishlistLabel = new Label("Wishlist");

    Label wishlistCountLabel = new Label();

    Label wishlistHoursLabel = new Label();

    Label totalLabel = new Label("Total");

    Label totalCountLabel = new Label();

    Label totalHoursLabel = new Label();

    public StatusCountBoxUnplayed() {
        add(statusLabel, 0, 0);
        add(countLabel, 1, 0);
        add(HLTBLabel, 2, 0);
        add(backlogLabel, 0, 1);
        add(subBacklogLabel, 0, 2);
        add(wishlistLabel, 0, 3);
        add(totalLabel, 0, 4);
        add(backlogCountLabel, 1, 1);
        add(subBacklogCountLabel, 1, 2);
        add(wishlistCountLabel, 1, 3);
        add(totalCountLabel, 1, 4);
        add(backlogHoursLabel, 2, 1);
        add(subBacklogHoursLabel, 2, 2);
        add(wishlistHoursLabel, 2, 3);
        add(totalHoursLabel, 2, 4);
        updateData();
        setPadding(new Insets(5));
        for (Node n : getChildren()) {
            if (n instanceof Control) {
                Control control = (Control)n;
                control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                control.setStyle("-fx-border-color: white;" +
                                "-fx-border-width: .2; " +
                                "-fx-alignment: center;");
            }
            if (n instanceof Pane) {
                Pane pane = (Pane)n;
                pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                pane.setStyle("-fx-border-color: white;" +
                        "-fx-border-width: .2; " +
                        "-fx-alignment: center;");
            }
        }
    }

    //Sets the labels' texts accordingly.
    public void updateData() {
        backlogCountLabel.setText("" + GameLists.getUnplayedStatusCount("Backlog"));
        subBacklogCountLabel.setText("" + GameLists.getUnplayedStatusCount("SubBacklog"));
        wishlistCountLabel.setText("" + GameLists.getUnplayedStatusCount("Wishlist"));
        totalCountLabel.setText("" + GameLists.unplayedList.size());
        backlogHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("Backlog")));
        subBacklogHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("SubBacklog")));
        wishlistHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("Wishlist")));
        totalHoursLabel.setText(String.format("%.2f", GameLists.getTotalHours()));
    }
}
