package com.example.vidyatracker11;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class GoalFilterWindow extends VBox {
    //GUI
    Label mainLabel = new Label("Set Filters for Goal");

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

    //Franchise
    Label franchiseLabel = new Label("Possible Franchises:");
    ChoiceBox<String> franchiseBox = new ChoiceBox<>();
    Button franchiseAddButton = new Button("Add");
    Button franchiseRemoveButton = new Button("Remove");
    HBox franchiseButtonBox = new HBox(franchiseAddButton, franchiseRemoveButton);
    ListView<String> franchiseView = new ListView<>();
    VBox franchiseVBox = new VBox(franchiseLabel, franchiseBox, franchiseButtonBox, franchiseView);

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

    //Release Year
    Label releaseYearMinLabel = new Label("Min Release Year:");
    TextField releaseYearMinField = new TextField();
    Label releaseYearMaxLabel = new Label("Max Release Year:");
    TextField releaseYearMaxField = new TextField();
    VBox releaseYearVBox = new VBox(releaseYearMinLabel, releaseYearMinField, releaseYearMaxLabel,
            releaseYearMaxField);

    //Collection
    Label collectionLabel = new Label("Possible Collections:");
    ChoiceBox<GameCollection> collectionBox = new ChoiceBox<>();
    Button collectionAddButton = new Button("Add");
    Button collectionRemoveButton = new Button("Remove");
    HBox collectionButtonBox = new HBox(collectionAddButton, collectionRemoveButton);
    ListView<GameCollection> collectionView = new ListView<>();
    VBox collectionVBox = new VBox(collectionLabel, collectionBox, collectionButtonBox,
            collectionView);

    HBox layer1HBox = new HBox();
    HBox layer2HBox = new HBox();
    VBox filtersVBox = new VBox(layer1HBox, layer2HBox);
    Button confirmButton = new Button("Confirm Filters");

    public GoalFilterWindow(){
        //GUI
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        statusButtonBox.setAlignment(Pos.CENTER);
        franchiseButtonBox.setAlignment(Pos.CENTER);
        platformButtonBox.setAlignment(Pos.CENTER);
        genreButtonBox.setAlignment(Pos.CENTER);
        collectionButtonBox.setAlignment(Pos.CENTER);
        statusVBox.setAlignment(Pos.TOP_CENTER);
        titleVBox.setAlignment(Pos.TOP_CENTER);
        franchiseVBox.setAlignment(Pos.TOP_CENTER);
        platformVBox.setAlignment(Pos.TOP_CENTER);
        genreVBox.setAlignment(Pos.TOP_CENTER);
        releaseYearVBox.setAlignment(Pos.TOP_CENTER);
        collectionVBox.setAlignment(Pos.TOP_CENTER);
        layer1HBox.setAlignment(Pos.TOP_CENTER);
        layer2HBox.setAlignment(Pos.TOP_CENTER);
        statusButtonBox.setSpacing(5.0);
        franchiseButtonBox.setSpacing(5.0);
        platformButtonBox.setSpacing(5.0);
        genreButtonBox.setSpacing(5.0);
        statusVBox.setSpacing(5.0);
        titleVBox.setSpacing(5.0);
        franchiseVBox.setSpacing(5.0);
        platformVBox.setSpacing(5.0);
        genreVBox.setSpacing(5.0);
        releaseYearVBox.setSpacing(5.0);
        collectionVBox.setSpacing(5.0);
        layer1HBox.setSpacing(5.0);
        layer2HBox.setSpacing(5.0);
        filtersVBox.setSpacing(40.0);
        layer1HBox.setMaxHeight(350);
        layer2HBox.setMaxHeight(350);
        setAlignment(Pos.CENTER);
        getChildren().addAll(mainLabel, filtersVBox, confirmButton);
        setFillWidth(false);
        setPadding(new Insets(5.0));
        setSpacing(5.0);

        //Set platform values
        platformBox.getItems().addAll(GameLists.platformList);

        //Set genre values
        genreBox.getItems().addAll(GameLists.genreList);

        //Only allow integers for releaseYearMinField and releaseYearMaxField
        releaseYearMinField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));
        releaseYearMaxField.setTextFormatter(new TextFormatter<>(ApplicationGUI.integerFilter));

        //Set collection values
        collectionBox.getItems().addAll(GameLists.collectionList);

        statusAddButton.setOnAction(e -> {
            if (!statusView.getItems().contains(statusBox.getSelectionModel().getSelectedItem()) &&
                    statusBox.getSelectionModel().getSelectedItem() != null)
                //When statusAddButton is pressed, If an item is selected, and it is not already in statusView, add it to statusView
                statusView.getItems().add(statusBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from statusView
        statusRemoveButton.setOnAction(e -> statusView.getItems().remove(statusView.getSelectionModel().getSelectedItem()));

        franchiseAddButton.setOnAction(e -> {
            if (!franchiseView.getItems().contains(franchiseBox.getSelectionModel().getSelectedItem()) &&
                    franchiseBox.getSelectionModel().getSelectedItem() != null)
                //When statusAddButton is pressed, If an item is selected, and it is not already in statusView, add it to statusView
                franchiseView.getItems().add(franchiseBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from statusView
        franchiseRemoveButton.setOnAction(e -> franchiseView.getItems().remove(franchiseView.getSelectionModel().getSelectedItem()));

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

        collectionAddButton.setOnAction(e -> {
            if(!collectionView.getItems().contains(collectionBox.getSelectionModel().getSelectedItem()) &&
                    collectionBox.getSelectionModel().getSelectedItem() != null)
                //When collectionAddButton is pressed, If an item is selected, and it is not already in collectionView, add it to collectionView
                collectionView.getItems().add(collectionBox.getSelectionModel().getSelectedItem());
        });

        //Remove selected item from collectionView
        collectionRemoveButton.setOnAction(e -> collectionView.getItems().remove(collectionView.getSelectionModel().getSelectedItem()));
    }
}
