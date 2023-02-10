package com.example.vidyatracker11;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Window used to edit an existing unplayed game
public class UnplayedEditWindow extends AddEditGame {
    //Hours
    Label hoursLabel = new Label("Predicted Hours:");
    TextField hoursBox = new TextField();
    VBox hoursVBox = new VBox(hoursLabel, hoursBox);

    //Deck Status
    Label deckLabel = new Label("Deck Status:");
    ChoiceBox<String> deckBox = new ChoiceBox<>();
    VBox deckVBox = new VBox(deckLabel, deckBox);

    public UnplayedEditWindow(UnplayedGame game, Stage stage) {
        super();
        mainLabel.setText("Edit Data Values for " + game.getTitle());
        mainHBox.getChildren().addAll(statusVBox, titleVBox, franchiseVBox,
                platformVBox, genreVBox, hoursVBox, deckVBox, releaseVBox);
        doneButton.setText("Save Changes and Close Window");
        getChildren().addAll(mainLabel, mainHBox, doneButton);

        hoursVBox.setAlignment(Pos.CENTER);
        deckVBox.setAlignment(Pos.CENTER);

        //Status
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
        statusBox.getSelectionModel().select(game.getStatus());

        //Genre
        genreBox.getSelectionModel().select(game.getGenre());

        //Platform
        platformBox.getSelectionModel().select(game.getPlatform());

        //Title/Franchise
        titleBox.setText(game.getTitle());
        franchiseBox.setText(game.getFranchise());

        //Hours
        hoursBox.setText("" + game.getHours());
        hoursBox.setTextFormatter(new TextFormatter<>(doubleFilter));

        //Deck Status
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
        if (game.getDeckCompatible().equals("Yes") || game.getDeckCompatible().equals("No") || game.getDeckCompatible().equals("Maybe")) {
            deckBox.getSelectionModel().select(game.getDeckCompatible());
        } else {
            deckBox.getSelectionModel().selectLast();
        }

        //Release Date
        releaseYearBox.setText("" + game.getReleaseYear());
        releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());
        releaseDayBox.getSelectionModel().select(game.getReleaseDay());

        doneButton.setOnAction(e -> {
            try{
                saveAndQuit(game, stage);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and saves the inputted data to the given unplayed game.
    public void saveAndQuit(UnplayedGame game, Stage stage) throws NumberFormatException{
        //Set Status
        game.setStatus(statusBox.getSelectionModel().getSelectedItem());

        //Set Title
        game.setTitle(titleBox.getText());

        //Set Franchise
        game.setFranchise(franchiseBox.getText());

        //Set Platform
        game.setPlatform(platformBox.getSelectionModel().getSelectedItem());

        //Set Genre
        game.setGenre(genreBox.getSelectionModel().getSelectedItem());

        //Set Hours
        if (hoursBox.getText().equals("") || hoursBox.getText().equals(".")) {
            game.setHours(0);
        } else {
            game.setHours(Double.parseDouble(hoursBox.getText()));
        }

        //Set Deck Status
        if (deckBox.getSelectionModel().getSelectedItem().equals("Blank")) {
            game.setDeckCompatible("");
        } else {
            game.setDeckCompatible(deckBox.getSelectionModel().getSelectedItem());
        }

        //Set Release Date
        if (releaseYearBox.getText().equals("")) {
            game.setReleaseYear(0);
        } else {
            game.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));
        }
        game.setReleaseMonth(releaseMonthBox.getValue());
        game.setReleaseDay(releaseDayBox.getValue());

        ApplicationGUI.statusCountBoxUnplayed.updateData();
        ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedSortChoices, ApplicationGUI.unplayedFilterChoices);
        ApplicationGUI.changeMade = true;
        stage.close();
    }
}
