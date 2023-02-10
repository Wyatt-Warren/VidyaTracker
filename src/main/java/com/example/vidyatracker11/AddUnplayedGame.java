package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//The window for adding a new game to the unplayed game list.
public class AddUnplayedGame extends AddEditGame {

    //Hours
    Label hoursLabel = new Label("Predicted Hours:");
    TextField hoursBox = new TextField();
    VBox hoursVBox = new VBox(hoursLabel, hoursBox);

    //Deck Status
    Label deckLabel = new Label("Deck Status:");
    ChoiceBox<String> deckBox = new ChoiceBox<>();
    VBox deckVBox = new VBox(deckLabel, deckBox);

    public AddUnplayedGame(Stage stage) {
        super();
        mainLabel.setText("Add New Unplayed Game");
        mainHBox.getChildren().addAll(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox, hoursVBox, deckVBox, releaseVBox);
        doneButton.setText("Create New Unplayed Game");
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        hoursVBox.setAlignment(Pos.CENTER);
        deckVBox.setAlignment(Pos.CENTER);

        //Status
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
        statusBox.getSelectionModel().selectFirst();

        //Genre
        genreBox.getSelectionModel().selectFirst();

        //Platform
        platformBox.getSelectionModel().selectFirst();

        //Hours
        hoursBox.setTextFormatter(new TextFormatter<>(doubleFilter));

        //Deck Status
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
        deckBox.getSelectionModel().selectLast();

        doneButton.setOnAction(e -> {
            try{
                saveAndQuit(stage);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and creates a new game based on the inputted data.
    public void saveAndQuit(Stage stage) throws NumberFormatException {
        //Set Release Year
        int releaseYear;
        if (releaseYearBox.getText().equals("")) {
            releaseYear = 0;
        } else {
            releaseYear = Integer.parseInt(releaseYearBox.getText());
        }

        //Create new game object
        UnplayedGame newGame = new UnplayedGame(titleBox.getText(), statusBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem(), genreBox.getSelectionModel().getSelectedItem(),
                releaseYear, releaseMonthBox.getValue(), releaseDayBox.getValue());

        //Set Franchise
        if (!franchiseBox.getText().equals(""))
            newGame.setFranchise(franchiseBox.getText());

        //Set Hours
        if (hoursBox.getText().equals("") || hoursBox.getText().equals(".")) {
            newGame.setHours(0);
        } else {
            newGame.setHours(Double.parseDouble(hoursBox.getText()));
        }

        //Set Deck Status
        if ((deckBox.getSelectionModel().getSelectedItem()).equals("Blank")) {
            newGame.setDeckCompatible("");
        } else {
            newGame.setDeckCompatible(deckBox.getSelectionModel().getSelectedItem());
        }

        GameLists.unplayedList.add(newGame);
        ApplicationGUI.statusCountBoxUnplayed.updateData();
        ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedSortChoices, ApplicationGUI.unplayedFilterChoices);
        ApplicationGUI.stats.updateStats();
        ApplicationGUI.changeMade = true;
        stage.close();
    }
}