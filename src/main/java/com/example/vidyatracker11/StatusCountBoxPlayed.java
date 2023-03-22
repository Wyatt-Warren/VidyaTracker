package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

//Box on the main played game window that shows stats relating to statuses.
public class StatusCountBoxPlayed extends GridPane {
    //GUI
    Label statusLabel = new Label("Status");
    Label countLabel = new Label("Count");
    Label thisYearLabel = new Label("This Year");
    Label lastYearLabel = new Label("Last Year");
    Label playingLabel = new Label("Playing");
    Label playingCountLabel = new Label();
    Label completedLabel = new Label("Completed");
    Label completedCountLabel = new Label();
    Label completedThisYearCountLabel = new Label();
    Label completedLastYearCountLabel = new Label();
    Label shortCompletedLabel = new Label("Short Completed");
    Label shortCompletedCountLabel = new Label();
    Label shortCompletedThisYearCountLabel = new Label();
    Label shortCompletedLastYearCountLabel = new Label();
    Label holdLabel = new Label("On Hold");
    Label holdCountLabel = new Label();
    Label totalLabel = new Label("Total");
    Label totalCountLabel = new Label();
    Label totalThisYearCountLabel = new Label();
    Label totalLastYearCountLabel = new Label();

    //Fields
    private static final Date date = new Date();
    private static final ZoneId timeZone = ZoneId.systemDefault();
    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate(); //Used to get current year

    public StatusCountBoxPlayed() {
        //GUI
        add(statusLabel, 0, 0);
        add(countLabel, 1, 0);
        add(thisYearLabel, 2, 0);
        add(lastYearLabel, 3, 0);
        add(playingLabel, 0, 1);
        add(completedLabel, 0, 2);
        add(shortCompletedLabel, 0, 3);
        add(holdLabel, 0, 4);
        add(totalLabel, 0, 5);
        add(playingCountLabel, 1, 1);
        add(completedCountLabel, 1, 2);
        add(shortCompletedCountLabel, 1, 3);
        add(holdCountLabel, 1, 4);
        add(totalCountLabel, 1, 5);
        add(completedThisYearCountLabel, 2, 2);
        add(completedLastYearCountLabel, 3, 2);
        add(shortCompletedThisYearCountLabel, 2, 3);
        add(shortCompletedLastYearCountLabel, 3, 3);
        add(totalThisYearCountLabel, 2, 5);
        add(totalLastYearCountLabel, 3, 5);
        add(new Label(), 2, 1);
        add(new Label(), 3, 1);
        add(new Label(), 2, 4);
        add(new Label(), 3, 4);
        setPadding(new Insets(5));

        for (Node n : getChildren()) {
            //Updating style for each item in GridPane
            //Local variables
            Control control = (Control) n;  //Cast the item to control

            //GUI
            control.setPadding(new Insets(2));
            control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            control.setStyle("-fx-border-color: white;" +
                    "-fx-border-width: .2; " +
                    "-fx-alignment: center;");
        }

        updateData();
    }

    //Sets labels' texts accordingly
    public void updateData() {
        //Count of games with status Playing
        playingCountLabel.setText("" + GameLists.getPlayedStatusCount("Playing"));

        //Count of games with status Completed and short status No
        completedCountLabel.setText("" + GameLists.getPlayedStatusCount("Completed"));

        //Count of games with status Completed and short status Yes
        shortCompletedCountLabel.setText("" + GameLists.getPlayedStatusShortCount("Completed"));

        //Count of games with status On Hold
        holdCountLabel.setText("" + GameLists.getPlayedStatusCount("On Hold"));

        //Total count of games
        totalCountLabel.setText("" + GameLists.playedList.size());

        //Total count of games with status Completed, short status No, and release year as the current year
        completedThisYearCountLabel.setText("" + GameLists.getCompletedYearCount(localDate.getYear()));

        //Total count of games with status Completed, short status No, and release year as last year
        completedLastYearCountLabel.setText("" + GameLists.getCompletedYearCount(localDate.getYear()-1));

        //Total count of games with status Completed, short status Yes, and release year as the current year
        shortCompletedThisYearCountLabel.setText("" + GameLists.getShortCompletedYearCount(localDate.getYear()));

        //Total count of games with status Completed, short status Yes, and release year as last year
        shortCompletedLastYearCountLabel.setText("" + GameLists.getShortCompletedYearCount(localDate.getYear()-1));

        //Total count of games with status Completed and release year as the current year
        totalThisYearCountLabel.setText("" + GameLists.getTotalYearCount(localDate.getYear()));

        //Total count of games with the status Completed and release year as the last year
        totalLastYearCountLabel.setText("" + GameLists.getTotalYearCount(localDate.getYear()-1));
    }
}
