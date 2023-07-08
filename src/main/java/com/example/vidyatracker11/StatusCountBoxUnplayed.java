package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

//Box on the main unplayed game window that shows stats relating to statuses.
public class StatusCountBoxUnplayed extends GridPane {
    //GUI
    Label statusLabel = new Label("Status");
    Label countLabel = new Label("Count");
    Label hoursLabel = new Label("Predicted Hours");
    Label backlogLabel = new Label("Backlog");
    Label backlogCountLabel = new Label();
    Label backlogHoursLabel = new Label();
    Label subBacklogLabel = new Label("SubBacklog");
    Label subBacklogCountLabel = new Label();
    Label subBacklogHoursLabel = new Label();
    Label wishlistLabel = new Label("Wishlist");
    Label wishlistCountLabel = new Label();
    Label wishlistHoursLabel = new Label();
    Label ignoredLabel = new Label("Ignored");
    Label ignoredCountLabel = new Label();
    Label ignoredHoursLabel = new Label();
    Label totalLabel = new Label("Total");
    Label totalCountLabel = new Label();
    Label totalHoursLabel = new Label();
    Label totalIgnoredLabel = new Label("Total with Ignored");
    Label totalIgnoredCountLabel = new Label();
    Label totalIgnoredHoursLabel = new Label();

    public StatusCountBoxUnplayed() {
        //GUI
        add(statusLabel, 0, 0);
        add(countLabel, 1, 0);
        add(hoursLabel, 2, 0);
        add(backlogLabel, 0, 1);
        add(subBacklogLabel, 0, 2);
        add(wishlistLabel, 0, 3);
        add(ignoredLabel, 0, 4);
        add(totalLabel, 0, 5);
        add(totalIgnoredLabel, 0, 6);
        add(backlogCountLabel, 1, 1);
        add(subBacklogCountLabel, 1, 2);
        add(wishlistCountLabel, 1, 3);
        add(ignoredCountLabel, 1, 4);
        add(totalCountLabel, 1, 5);
        add(totalIgnoredCountLabel, 1, 6);
        add(backlogHoursLabel, 2, 1);
        add(subBacklogHoursLabel, 2, 2);
        add(wishlistHoursLabel, 2, 3);
        add(ignoredHoursLabel, 2, 4);
        add(totalHoursLabel, 2, 5);
        add(totalIgnoredHoursLabel, 2, 6);
        setPadding(new Insets(5));
        setMaxWidth(300);

        for (Node n : getChildren()) {
            //Updating style for each item in GridPane
            //Local variables
            Control control = (Control) n;  //Cast the item to control

            //GUI
            control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            control.setPadding(new Insets(3));
            control.setStyle("-fx-border-color: white;" +
                            "-fx-border-width: .2; " +
                            "-fx-alignment: center;");

        }

        updateData();
    }

    //Sets the labels' texts accordingly.
    public void updateData() {
        //Count of games with status Backlog
        backlogCountLabel.setText("" + GameLists.getUnplayedStatusCount("Backlog"));

        //Count of games with status SubBacklog
        subBacklogCountLabel.setText("" + GameLists.getUnplayedStatusCount("SubBacklog"));

        //Count of games with status Wishlist
        wishlistCountLabel.setText("" + GameLists.getUnplayedStatusCount("Wishlist"));

        //Count of games with status Ignored
        ignoredCountLabel.setText("" + GameLists.getUnplayedStatusCount("Ignored"));

        //Count of games
        totalCountLabel.setText("" + (GameLists.unplayedList.size() - GameLists.getUnplayedStatusCount("Ignored")));

        //Count of games including Ignored
        totalIgnoredCountLabel.setText("" + GameLists.unplayedList.size());

        //Total hours of games with status Backlog
        backlogHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("Backlog")));

        //Total hours of games with status SubBacklog
        subBacklogHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("SubBacklog")));

        //Total hours of games with status Wishlist
        wishlistHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("Wishlist")));

        //Total hours of games with status Ignored
        ignoredHoursLabel.setText(String.format("%.2f", GameLists.getStatusHours("Ignored")));

        //Total hours of all games
        totalHoursLabel.setText(String.format("%.2f", GameLists.getTotalHours() - GameLists.getStatusHours("Ignored")));

        //Total hours of all games including Ignored
        totalIgnoredHoursLabel.setText(String.format("%.2f", GameLists.getTotalHours()));
    }
}
