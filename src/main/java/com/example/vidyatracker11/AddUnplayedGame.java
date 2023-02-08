package com.example.vidyatracker11;

import java.util.function.UnaryOperator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//The window for adding a new game to the unplayed game list.
public class AddUnplayedGame extends VBox {
    //Status
    Label statusLabel = new Label("Status:");
    ChoiceBox < String > statusBox = new ChoiceBox < > ();
    VBox statusVBox = new VBox(statusLabel, statusBox);

    //Title
    Label titleLabel = new Label("Title:");
    TextField titleBox = new TextField();
    VBox titleVBox = new VBox(titleLabel, titleBox);

    //Franchise
    Label franchiseLabel = new Label("Franchise (Leave blank if no franchise):");
    TextField franchiseBox = new TextField();
    VBox franchiseVBox = new VBox(franchiseLabel, franchiseBox);

    //Platform
    Label platformLabel = new Label("Platform:");
    ChoiceBox < String > platformBox = new ChoiceBox < > ();
    VBox platformVBox = new VBox(platformLabel, platformBox);

    //Genre
    Label genreLabel = new Label("Genre:");
    ChoiceBox < String > genreBox = new ChoiceBox < > ();
    VBox genreVBox = new VBox(genreLabel, genreBox);

    //Hours
    Label hoursLabel = new Label("Predicted Hours:");
    TextField hoursBox = new TextField();
    VBox hoursVBox = new VBox(hoursLabel, hoursBox);

    //Deck Status
    Label deckLabel = new Label("Deck Status:");
    ChoiceBox < String > deckBox = new ChoiceBox < > ();
    VBox deckVBox = new VBox(deckLabel, deckBox);

    //Release Date
    Label releaseLabel = new Label("Release Date:");
    //Year
    Label releaseYearLabel = new Label("Year:");
    TextField releaseYearBox = new TextField();
    HBox releaseYearHBox = new HBox(releaseYearLabel, releaseYearBox);
    //Month
    Label releaseMonthLabel = new Label("Month:");
    ChoiceBox < Integer > releaseMonthBox = new ChoiceBox < > ();
    HBox releaseMonthHBox = new HBox(releaseMonthLabel, releaseMonthBox);
    //Day
    Label releaseDayLabel = new Label("Day:");
    ChoiceBox < Integer > releaseDayBox = new ChoiceBox < > ();
    HBox releaseDayHBox = new HBox(releaseDayLabel, releaseDayBox);
    VBox releaseVBox = new VBox(releaseLabel, releaseYearHBox, releaseMonthHBox, releaseDayHBox);

    public AddUnplayedGame(Stage parentStage, UnplayedGamesTable table, ChoiceBox < String > sortChoiceBox,
                           ChoiceBox < String > sortFilterBox, StatusCountBoxUnplayed statusCountBoxUnplayed,
                           StatsScreen stats) {
        Label mainLabel = new Label("Add New Unplayed Game");
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        HBox mainHBox = new HBox(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox, hoursVBox, deckVBox, releaseVBox);
        Button doneButton = new Button("Create New Unplayed Game");
        mainHBox.setSpacing(5);

        releaseYearHBox.setAlignment(Pos.CENTER);
        releaseMonthHBox.setAlignment(Pos.CENTER);
        releaseDayHBox.setAlignment(Pos.CENTER);
        statusVBox.setAlignment(Pos.CENTER);
        titleVBox.setAlignment(Pos.CENTER);
        franchiseVBox.setAlignment(Pos.CENTER);
        platformVBox.setAlignment(Pos.CENTER);
        genreVBox.setAlignment(Pos.CENTER);
        hoursVBox.setAlignment(Pos.CENTER);
        deckVBox.setAlignment(Pos.CENTER);
        releaseVBox.setAlignment(Pos.CENTER);

        setAlignment(Pos.CENTER);
        getChildren().addAll(mainLabel, mainHBox, doneButton);
        setPadding(new Insets(5));

        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
        statusBox.getSelectionModel().selectFirst();
        platformBox.getItems().addAll(GameLists.platformList);
        platformBox.getSelectionModel().selectFirst();
        genreBox.getItems().addAll(GameLists.genreList);
        genreBox.getSelectionModel().selectFirst();
        hoursBox.setTextFormatter(new TextFormatter < > (change -> {
            String input = change.getText();
            boolean noPeriods = true;
            for (int i = 0; i < hoursBox.getText().length(); i++) {
                if (hoursBox.getText().charAt(i) == '.')
                    noPeriods = false;
            }
            return (input.matches("[0-9]*") || (input.matches("\\.*") && noPeriods)) ? change : null;
        }));
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
        deckBox.getSelectionModel().selectLast();
        UnaryOperator < TextFormatter.Change > integerFilter = change -> {
            String input = change.getText();
            return input.matches("[0-9]*") ? change : null;
        };

        releaseYearBox.setTextFormatter(new TextFormatter < > (integerFilter));
        releaseMonthBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt, releaseDayBox);
        });
        releaseMonthBox.getSelectionModel().selectFirst();
        releaseDayBox.getSelectionModel().selectFirst();
        doneButton.setOnAction(e -> {
            try{
                saveAndQuit(parentStage, table, sortChoiceBox, sortFilterBox, statusCountBoxUnplayed, stats);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and creates a new game based on the inputted data.
    public void saveAndQuit(Stage parentStage, UnplayedGamesTable table, ChoiceBox < String > sortChoiceBox,
                            ChoiceBox < String > sortFilterBox, StatusCountBoxUnplayed statusCountBoxUnplayed,
                            StatsScreen stats) throws NumberFormatException {
        int releaseYear;
        if (releaseYearBox.getText().equals("")) {
            releaseYear = 0;
        } else {
            releaseYear = Integer.parseInt(releaseYearBox.getText());
        }
        UnplayedGame newGame = new UnplayedGame(titleBox.getText(), statusBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem(), genreBox.getSelectionModel().getSelectedItem(),
                releaseYear, releaseMonthBox.getValue(), releaseDayBox.getValue());
        if (!franchiseBox.getText().equals(""))
            newGame.setFranchise(franchiseBox.getText());
        if (hoursBox.getText().equals("") || hoursBox.getText().equals(".")) {
            newGame.setHours(0);
        } else {
            newGame.setHours(Double.parseDouble(hoursBox.getText()));
        }
        if ((deckBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
            newGame.setDeckCompatible("");
        } else {
            newGame.setDeckCompatible(deckBox.getSelectionModel().getSelectedItem());
        }
        if (releaseYearBox.getText().equals("")) {
            newGame.setReleaseYear(0);
        } else {
            newGame.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));
        }
        GameLists.unplayedList.add(newGame);
        statusCountBoxUnplayed.updateData();
        table.sortAndFilter(sortChoiceBox, sortFilterBox);
        stats.updateStats();
        ApplicationGUI.changeMade = true;
        parentStage.close();
    }

    //Sets the days for the day drop down based on the selected month
    public void setDayCount(int month, ChoiceBox < Integer > dayBox) {
        int dayCount = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: //January, March, May, July, August, October, December
                dayCount = 31;
                break;
            case 2: //February
                dayCount = 29;
                break;
            case 4:
            case 6:
            case 9:
            case 11: //April, June, September, November
                dayCount = 30;
                break;
        }
        dayBox.getItems().clear();
        for (int i = 0; i <= dayCount; i++)
            dayBox.getItems().add(i);
        dayBox.getSelectionModel().select(0);
    }
}