package com.example.vidyatracker11;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Window used to generate a random list based on filters.
public class RandomListGenerator extends VBox {
    //GUI
    Label mainLabel = new Label("Generate List Based on Filters");

    //Length
    Label lengthLabel = new Label("List Size:");
    TextField lengthField = new TextField();
    VBox lengthVBox = new VBox(lengthLabel, lengthField);

    //Status
    Label statusLabel = new Label("Possible Statuses:");
    ChoiceBox<String> statusBox = new ChoiceBox<>();
    Button statusAddButton = new Button("Add");
    Button statusRemoveButton = new Button("Remove");
    HBox statusButtonBox = new HBox(statusAddButton, statusRemoveButton);
    ListView<String> statusView = new ListView<>();
    VBox statusVBox = new VBox(statusLabel, statusBox, statusButtonBox, statusView);

    //Title
    Label titleLabel = new Label("Title Contains:");
    TextField titleField = new TextField();
    VBox titleVBox = new VBox(titleLabel, titleField);

    //Platform
    Label platformLabel = new Label("Possible Platforms:");
    ChoiceBox<String> platformBox = new ChoiceBox<>();
    Button platformAddButton = new Button("Add");
    Button platformRemoveButton = new Button("Remove");
    HBox platformButtonBox = new HBox(platformAddButton, platformRemoveButton);
    ListView<String> platformView = new ListView<>();
    VBox platformVBox = new VBox(platformLabel, platformBox, platformButtonBox, platformView);

    //Genre
    Label genreLabel = new Label("Possible Genres:");
    ChoiceBox<String> genreBox = new ChoiceBox<>();
    Button genreAddButton = new Button("Add");
    Button genreRemoveButton = new Button("Remove");
    HBox genreButtonBox = new HBox(genreAddButton, genreRemoveButton);
    ListView<String> genreView = new ListView<>();
    VBox genreVBox = new VBox(genreLabel, genreBox, genreButtonBox, genreView);

    //Hours
    Label hoursMinLabel = new Label("Hours Minimum:");
    TextField hoursMinField = new TextField();
    VBox hoursMinVBox = new VBox(hoursMinLabel, hoursMinField);
    Label hoursMaxLabel = new Label("Hours Maximum:");
    TextField hoursMaxField = new TextField();
    VBox hoursMaxVBox = new VBox(hoursMaxLabel, hoursMaxField);

    //Deck
    Label deckLabel = new Label("Possible Deck Statuses:");
    ChoiceBox<String> deckBox = new ChoiceBox<>();
    Button deckAddButton = new Button("Add");
    Button deckRemoveButton = new Button("Remove");
    HBox deckButtonBox = new HBox(deckAddButton, deckRemoveButton);
    ListView<String> deckView = new ListView<>();
    VBox deckVBox = new VBox(deckLabel, deckBox, deckButtonBox, deckView);

    //Years
    Label yearsMinLabel = new Label("Years Minimum:");
    TextField yearsMinField = new TextField();
    VBox yearsMinVBox = new VBox(yearsMinLabel, yearsMinField);
    Label yearsMaxLabel = new Label("Years Maximum:");
    TextField yearsMaxField = new TextField();
    VBox yearsMaxVBox = new VBox(yearsMaxLabel, yearsMaxField);

    HBox mainHBox = new HBox(lengthVBox, statusVBox, titleVBox,
            platformVBox, genreVBox, hoursMinVBox,
            hoursMaxVBox, deckVBox, yearsMinVBox, yearsMaxVBox);
    Button generateButton = new Button("Generate List");
    ListView<String> generatedList = new ListView<>();

    public RandomListGenerator() {
        //GUI
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        statusButtonBox.setAlignment(Pos.CENTER);
        platformButtonBox.setAlignment(Pos.CENTER);
        genreButtonBox.setAlignment(Pos.CENTER);
        deckButtonBox.setAlignment(Pos.CENTER);
        lengthVBox.setAlignment(Pos.TOP_CENTER);
        statusVBox.setAlignment(Pos.TOP_CENTER);
        titleVBox.setAlignment(Pos.TOP_CENTER);
        platformVBox.setAlignment(Pos.TOP_CENTER);
        genreVBox.setAlignment(Pos.TOP_CENTER);
        hoursMinVBox.setAlignment(Pos.TOP_CENTER);
        hoursMaxVBox.setAlignment(Pos.TOP_CENTER);
        deckVBox.setAlignment(Pos.TOP_CENTER);
        yearsMinVBox.setAlignment(Pos.TOP_CENTER);
        yearsMaxVBox.setAlignment(Pos.TOP_CENTER);
        mainHBox.setSpacing(5.0);
        setAlignment(Pos.CENTER);
        getChildren().addAll(mainLabel, mainHBox, generateButton, generatedList);
        setFillWidth(false);
        setPadding(new Insets(5.0));
        setSpacing(5.0);

        //Only allow integers for lengthField
        lengthField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Set status values
        statusBox.getItems().addAll("Backlog", "SubBacklog", "Wishlist");

        //Set platform values
        platformBox.getItems().addAll(GameLists.platformList);

        //Set genre values
        genreBox.getItems().addAll(GameLists.genreList);

        //Only allow doubles for hoursMinField and hoursMaxField
        hoursMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));
        hoursMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.doubleFilter));

        //Only allow integers for yearsMinField and yearsMaxField
        yearsMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));
        yearsMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Set deck values
        deckBox.getItems().addAll("Yes", "No", "Maybe", "Blank");

        statusAddButton.setOnAction(e -> {
            if (!statusView.getItems().contains(statusBox.getSelectionModel().getSelectedItem()) &&
                    statusBox.getSelectionModel().getSelectedItem() != null)
                //When statusAddButton is pressed, If an item is selected, and it is not already in statusView, add it to statusView
                statusView.getItems().add(statusBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from statusView
        statusRemoveButton.setOnAction(e -> statusView.getItems().remove(statusView.getSelectionModel().getSelectedItem()));

        platformAddButton.setOnAction(e -> {
            if (!platformView.getItems().contains(platformBox.getSelectionModel().getSelectedItem()) &&
                    platformBox.getSelectionModel().getSelectedItem() != null)
                //When platformAddButton is pressed, If an item is selected, and it is not already in platformView, add it to platformView
                platformView.getItems().add(platformBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from platformView
        platformRemoveButton.setOnAction(e -> platformView.getItems().remove(platformView.getSelectionModel().getSelectedItem()));

        genreAddButton.setOnAction(e -> {
            if (!genreView.getItems().contains(genreBox.getSelectionModel().getSelectedItem()) &&
                    genreBox.getSelectionModel().getSelectedItem() != null)
                //When genreAddButton is pressed, If an item is selected, and it is not already in genreView, add it to genreView
                genreView.getItems().add(genreBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from genreView
        genreRemoveButton.setOnAction(e -> genreView.getItems().remove(genreView.getSelectionModel().getSelectedItem()));

        deckAddButton.setOnAction(e -> {
            if (!deckView.getItems().contains(deckBox.getSelectionModel().getSelectedItem()) &&
                    deckBox.getSelectionModel().getSelectedItem() != null)
                //When deckAddButton is pressed, If an item is selected, and it is not already in deckView, add it to deckView
                deckView.getItems().add(deckBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from deckView
        deckRemoveButton.setOnAction(e -> deckView.getItems().remove(deckView.getSelectionModel().getSelectedItem()));

        generateButton.setOnAction(e -> {
            //Generate a random list of items based on selected filters
            if (!lengthField.getText().equals("")) {
                //There must be a number in lengthField
                //Local variables
                Random rand = new Random();
                ArrayList<UnplayedGame> potentialList = new ArrayList<>();  //List of items that fit the requirements selected

                //Clear the existing list to generate a new one
                generatedList.getItems().clear();

                for (UnplayedGame game : GameLists.unplayedList) {
                    //Iterate for every UnplayedGame

                    if ((statusView.getItems().isEmpty() || statusView.getItems().contains(game.getStatus())) &&
                            //Status is selected and game matches
                            (titleField.getText().equals("") || game.getTitle().toLowerCase().contains(titleField.getText().toLowerCase())) &&
                            //Title is selected and game matches
                            (platformView.getItems().isEmpty() || platformView.getItems().contains(game.getPlatform())) &&
                            //platform is selected and game matches
                            (genreView.getItems().isEmpty() || genreView.getItems().contains(game.getGenre())) &&
                            //genre is selected and game matches
                            (hoursMinField.getText().equals("") || game.getHours() >= Double.parseDouble(hoursMinField.getText())) &&
                            //hours is entered and game is greater or equal
                            (hoursMaxField.getText().equals("") || game.getHours() <= Double.parseDouble(hoursMaxField.getText())) &&
                            //hours is entered and game is less than or equal
                            (deckView.getItems().isEmpty() || deckView.getItems().contains(game.getDeckCompatible())) &&
                            //deck status is selected and game matches
                            (yearsMinField.getText().equals("") || game.getReleaseYear() >= Integer.parseInt(yearsMinField.getText())) &&
                            //release year is entered and game is greater or equal
                            (yearsMaxField.getText().equals("") || game.getReleaseYear() <= Integer.parseInt(yearsMaxField.getText())))
                            //release year is entered and game is less than or equal

                        //Add valid game to potentialList
                        potentialList.add(game);
                }

                if (!potentialList.isEmpty()) {
                    //If there are items that are compatible with filters
                    //Local variables
                    int gameNum = Integer.parseInt(lengthField.getText());  //Amount of games in the list

                    if (gameNum > potentialList.size())
                        //If there are less possible games than what was entered, only generate as many as possible
                        gameNum = potentialList.size();

                    for (int i = 0; i < gameNum; i++)
                        //Add gameNum amount of games to the final list
                        generatedList.getItems().add(potentialList.remove(rand.nextInt(potentialList.size())).getTitle());
                }
            }
        });
    }
}
