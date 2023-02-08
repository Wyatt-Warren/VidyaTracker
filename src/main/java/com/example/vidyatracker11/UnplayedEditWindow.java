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

//Window used to edit an existing unplayed game
public class UnplayedEditWindow extends VBox {
    //Status
    Label statusLabel = new Label("Status:");
    ChoiceBox<String> statusBox = new ChoiceBox<>();
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
    ChoiceBox<String> platformBox = new ChoiceBox<>();
    VBox platformVBox = new VBox(platformLabel, platformBox);

    //Genre
    Label genreLabel = new Label("Genre:");
    ChoiceBox<String> genreBox = new ChoiceBox<>();
    VBox genreVBox = new VBox(genreLabel, genreBox);

    //Hours
    Label hoursLabel = new Label("Predicted Hours:");
    TextField hoursBox = new TextField();
    VBox hoursVBox = new VBox(hoursLabel, hoursBox);

    //Deck Status
    Label deckLabel = new Label("Deck Status:");
    ChoiceBox<String> deckBox = new ChoiceBox<>();
    VBox deckVBox = new VBox(deckLabel, deckBox);

    //Release
    Label releaseLabel = new Label("Release Date:");
    //Year
    Label releaseYearLabel = new Label("Year:");
    TextField releaseYearBox = new TextField();
    HBox releaseYearHBox = new HBox(releaseYearLabel, releaseYearBox);
    //Month
    Label releaseMonthLabel = new Label("Month:");
    ChoiceBox<Integer> releaseMonthBox = new ChoiceBox<>();
    HBox releaseMonthHBox = new HBox(releaseMonthLabel, releaseMonthBox);
    //Day
    Label releaseDayLabel = new Label("Day:");
    ChoiceBox<Integer> releaseDayBox = new ChoiceBox<>();
    HBox releaseDayHBox = new HBox(releaseDayLabel, releaseDayBox);
    VBox releaseVBox = new VBox(releaseLabel, releaseYearHBox, releaseMonthHBox, releaseDayHBox);

    public UnplayedEditWindow(UnplayedGame game, Stage parentStage, UnplayedGamesTable table,
                              ChoiceBox<String> sortChoiceBox, ChoiceBox<String> sortFilterBox,
                              StatusCountBoxUnplayed statusCountBoxUnplayed) {
        Label mainLabel = new Label("Edit Data Values for " + game.getTitle());
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        HBox mainHBox = new HBox(statusVBox, titleVBox, franchiseVBox, platformVBox, genreVBox, hoursVBox, deckVBox, releaseVBox);
        Button doneButton = new Button("Save Changes and Close Window");
        mainHBox.setSpacing(5.0);
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
        setPadding(new Insets(5.0));
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
        statusBox.getSelectionModel().select(game.getStatus());
        titleBox.setText(game.getTitle());
        if (!game.getFranchise().equals(game.getTitle()))
            franchiseBox.setText(game.getFranchise());
        platformBox.getItems().addAll(GameLists.platformList);
        platformBox.getSelectionModel().select(game.getPlatform());
        genreBox.getItems().addAll(GameLists.genreList);
        genreBox.getSelectionModel().select(game.getGenre());
        hoursBox.setText("" + game.getHours());
        hoursBox.setTextFormatter(new TextFormatter<>(change -> {
            String input = change.getText();
            boolean noPeriods = true;
            for (int i = 0; i < hoursBox.getText().length(); i++) {
                if (hoursBox.getText().charAt(i) == '.')
                    noPeriods = false;
            }
            return (input.matches("[0-9]*") || (input.matches("\\.*") && noPeriods)) ? change : null;
        }));
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");
        if (game.getDeckCompatible().equals("Yes") || game.getDeckCompatible().equals("No") || game.getDeckCompatible().equals("Maybe")) {
            deckBox.getSelectionModel().select(game.getDeckCompatible());
        } else {
            deckBox.getSelectionModel().selectLast();
        }
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            return input.matches("[0-9]*") ? change : null;
        };
        releaseYearBox.setText("" + game.getReleaseYear());
        releaseYearBox.setTextFormatter(new TextFormatter<>(integerFilter));
        releaseMonthBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        releaseMonthBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            int newInt = (int) newNum;
            setDayCount(newInt, releaseDayBox);
        });
        releaseMonthBox.getSelectionModel().select(game.getReleaseMonth());
        releaseDayBox.getSelectionModel().select(game.getReleaseDay());
        doneButton.setOnAction(e -> {
            try {
                saveAndQuit(game, parentStage, table, sortChoiceBox, sortFilterBox, statusCountBoxUnplayed);
            }catch (NumberFormatException e1){
                e1.printStackTrace();
            }
        });
    }

    //Closes the window and saves the inputted data to the given unplayed game.
    public void saveAndQuit(UnplayedGame game, Stage parentStage, UnplayedGamesTable table,
                            ChoiceBox<String> sortChoiceBox, ChoiceBox<String> sortFilterBox,
                            StatusCountBoxUnplayed statusCountBoxUnplayed) throws NumberFormatException{
        game.setStatus(statusBox.getSelectionModel().getSelectedItem());
        game.setTitle(titleBox.getText());
        if (franchiseBox.getText().equals("")) {
            game.setFranchise(titleBox.getText());
        } else {
            game.setFranchise(franchiseBox.getText());
        }
        game.setPlatform(platformBox.getSelectionModel().getSelectedItem());
        game.setGenre(genreBox.getSelectionModel().getSelectedItem());
        if (hoursBox.getText().equals("") || hoursBox.getText().equals(".")) {
            game.setHours(0);
        } else {
            game.setHours(Double.parseDouble(hoursBox.getText()));
        }
        if (deckBox.getSelectionModel().getSelectedItem().equals("Blank")) {
            game.setDeckCompatible("");
        } else {
            game.setDeckCompatible(deckBox.getSelectionModel().getSelectedItem());
        }
        if (releaseYearBox.getText().equals("")) {
            game.setReleaseYear(0);
        } else {
            game.setReleaseYear(Integer.parseInt(releaseYearBox.getText()));
        }
        game.setReleaseMonth(releaseMonthBox.getValue());
        game.setReleaseDay(releaseDayBox.getValue());
        statusCountBoxUnplayed.updateData();
        table.sortAndFilter(sortChoiceBox, sortFilterBox);
        ApplicationGUI.changeMade = true;
        parentStage.close();
    }

    //Sets the days in the dropdown based on the selected month.
    public void setDayCount(int month, ChoiceBox<Integer> dayBox) {
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
