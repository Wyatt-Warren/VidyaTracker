package com.example.vidyatracker11;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//The main class
public class ApplicationGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //Used to determine whether it is necessary to save the file upon closing the window.
    public static boolean changeMade = false;

    //Used to determine what certain menu items will do regarding which list to regard.
    public static boolean playedOpen = true;

    //Default saving path. Should be the currently open file or List.json by default.
    public static Path currentFilePathOut = Path.of("List.json").toAbsolutePath();

    public static final int screenWidthMain = 1500;
    public static final int screenHeightMain = 800;


    //File Menu
    public static MenuItem newFileMenuItem = new MenuItem("New File");
    public static MenuItem openFileMenuItem = new MenuItem("Open File");
    public static SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
    public static MenuItem saveFileMenuItem = new MenuItem("Save File");
    public static MenuItem saveAsFileMenuItem = new MenuItem("Save File As");
    public static SeparatorMenuItem separatorMenuItem5 = new SeparatorMenuItem();
    public static MenuItem exitMenuItem = new MenuItem("Exit");
    public static Menu fileMenu = new Menu("File");

    //List Menu
    public static MenuItem addNewGameMenuItem = new MenuItem("Add New Game");
    public static MenuItem editGameMenuItem = new MenuItem("Edit Selected Game");
    public static MenuItem moveGameMenuItem = new MenuItem("Move Selected Game");
    public static MenuItem removeGameMenuItem = new MenuItem("Remove Selected Game");
    public static MenuItem collectGameMenuItem = new MenuItem("Add Selected Game to Collection");
    public static SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
    public static MenuItem editGenreListMenuItem = new MenuItem("Edit Genre List");
    public static MenuItem editPlatformListMenuItem = new MenuItem("Edit Platform List");
    public static SeparatorMenuItem separatorMenuItem4 = new SeparatorMenuItem();
    public static MenuItem statsMenuItem = new MenuItem("Show Stats Window");
    public static MenuItem collectionsMenuItem = new MenuItem("Show Collections Window");
    public static MenuItem achievementsMenuItem = new MenuItem("Show Achievements Window");
    public static Menu listMenu = new Menu("List");

    //Random Menu
    public static MenuItem chooseRandomGameMenuItem = new MenuItem("Choose a Random Game to Play");
    public static MenuItem chooseRandomWishlistGameMenuItem = new MenuItem("Choose a Random Game to Buy");
    public static MenuItem generateRandomListMenuItem = new MenuItem("Generate a Random List of Games Based on Filters");
    public static MenuItem chooseRandomFromList = new MenuItem("Choose a Random Game From the Small List");
    public static Menu randomMenu = new Menu("Random");


    //Main GUI
    public static StatusCountBoxPlayed statusCountBoxPlayed = new StatusCountBoxPlayed();
    public static StatusCountBoxUnplayed statusCountBoxUnplayed = new StatusCountBoxUnplayed();
    public static ChoiceBox<String> playedSortChoices = new ChoiceBox<>();
    public static Label playedSortLabel = new Label("Sort by:");
    public static Label playedFilterLabel = new Label("Filter by:");
    public static Label playedFilterTokenLabel = new Label("Filter Token:");
    public static ChoiceBox<String> playedFilterChoices = new ChoiceBox<>();
    public static ChoiceBox<String> playedFilterTokenChoices = new ChoiceBox<>();
    public static PlayedGamesTable playedGamesTable = new PlayedGamesTable();
    public static HBox playedChoiceHBox = new HBox(playedSortLabel, playedSortChoices, playedFilterLabel,
            playedFilterChoices, playedFilterTokenLabel, playedFilterTokenChoices);
    public static VBox playedGamesVBox = new VBox(playedChoiceHBox, playedGamesTable);
    public static ChoiceBox<String> unplayedSortChoices = new ChoiceBox<>();
    public static ChoiceBox<String> unplayedFilterChoices = new ChoiceBox<>();
    public static ChoiceBox<String> unplayedFilterTokenChoices = new ChoiceBox<>();
    public static UnplayedGamesTable unplayedGamesTable = new UnplayedGamesTable();
    public static Label unplayedSortLabel = new Label("Sort by:");
    public static Label unplayedFilterLabel = new Label("Filter by:");
    public static Label unplayedFilterTokenLabel = new Label("Filter Token:");
    public static HBox unplayedChoiceHBox = new HBox(unplayedSortLabel, unplayedSortChoices, unplayedFilterLabel,
            unplayedFilterChoices, unplayedFilterTokenLabel, unplayedFilterTokenChoices);
    public static VBox unplayedGamesVBox = new VBox(unplayedChoiceHBox, unplayedGamesTable);
    public static UnplayedTempList unplayedTempList = new UnplayedTempList();
    public static Button switchFromPlayed = new Button("Show Unplayed List");
    public static Button switchFromUnplayed = new Button("Show Played List");
    public static HBox topBoxPlayed = new HBox(statusCountBoxPlayed, switchFromPlayed);
    public static HBox topBoxUnplayed = new HBox(statusCountBoxUnplayed, unplayedTempList, switchFromUnplayed);
    public static VBox playedWindow = new VBox(topBoxPlayed, playedGamesVBox);
    public static VBox unplayedWindow = new VBox(topBoxUnplayed, unplayedGamesVBox);

    //Other
    public static ContextMenu rowContextMenu = new ContextMenu(addNewGameMenuItem,
            editGameMenuItem, moveGameMenuItem, removeGameMenuItem, collectGameMenuItem);
    public static String styleSheet = "style.css";
    public static Image icon = new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png")));
    public static MenuBar menuBar = new MenuBar(fileMenu, listMenu, randomMenu);
    public static FileChooser fileChooser = new FileChooser();
    public static VBox primarySceneVBox = new VBox(menuBar, playedWindow);
    public static Scene primaryScene = new Scene(primarySceneVBox, screenWidthMain, screenHeightMain);
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
        primaryStage.getIcons().add(icon);

        primaryScene.getStylesheets().add(styleSheet);

        playedChoiceHBox.setSpacing(5);
        unplayedChoiceHBox.setSpacing(5);
        playedGamesVBox.setSpacing(5);
        unplayedGamesVBox.setSpacing(5);

        unplayedSortChoices.getItems().addAll("Status", "Title", "Platform", "Genre", "Hours", "Release Date");
        unplayedFilterChoices.getItems().addAll("Status", "Franchise", "Platform", "Genre", "Deck Status",
                "Release Year", "No Filter");
        playedSortChoices.getItems().addAll("Status", "Title", "Rating", "Platform", "Genre", "Release Date", "Completion Date");
        playedFilterChoices.getItems().addAll("Status", "Short", "Franchise", "Rating", "Platform",
                "Genre", "Release Year", "Completion Year", "100%", "No Filter");

        playedSortChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem()));
        unplayedSortChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem()));

        playedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                playedGamesTable.sortAndFilter(newValue));
        unplayedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                unplayedGamesTable.sortAndFilter(newValue));

        playedFilterChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            //This shit fixes an issue where playedFilterTokenChoices would remember how far down you scrolled after changing filter type
            //For example, Franchise to Status. It would be scrolled past the available options
            int index = playedChoiceHBox.getChildren().indexOf(playedFilterTokenChoices);
            playedChoiceHBox.getChildren().remove(playedFilterTokenChoices);
            playedFilterTokenChoices = new ChoiceBox<>();
            playedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) ->
                    playedGamesTable.sortAndFilter(newValue1));
            playedChoiceHBox.getChildren().add(index, playedFilterTokenChoices);

            switch (playedFilterChoices.getSelectionModel().getSelectedIndex()){
                case 0: //Status
                    playedFilterTokenChoices.getItems().addAll("Playing", "Completed", "On Hold");
                    break;
                case 1: //Short
                case 8: //100%
                    playedFilterTokenChoices.getItems().addAll("Yes", "No", "Blank");
                    break;
                case 2: //Franchise
                    ObservableList<String> franchises = FXCollections.observableArrayList();
                    for(PlayedGame game : GameLists.playedList){
                        if(!franchises.contains(game.getFranchise()) && !game.getFranchise().equals("")){
                            franchises.add(game.getFranchise());
                        }
                    }
                    Collections.sort(franchises);
                    playedFilterTokenChoices.getItems().addAll(franchises);
                    break;
                case 3: //Rating
                    playedFilterTokenChoices.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
                    break;
                case 4: //Platform
                    playedFilterTokenChoices.getItems().addAll(GameLists.platformList);
                    break;
                case 5: //Genre
                    playedFilterTokenChoices.getItems().addAll(GameLists.genreList);
                    break;
                case 6: //Release Year
                case 7: //Completion Year
                    ObservableList<Integer> years = FXCollections.observableArrayList();
                    for(PlayedGame game : GameLists.playedList){
                        int year;
                        if (playedFilterChoices.getSelectionModel().getSelectedIndex()==6){
                            year = game.getReleaseYear();
                        }else{
                            year = game.getCompletionYear();
                        }

                        if(!years.contains((year)) && year!=0){
                            years.add(year);
                        }
                    }
                    Collections.sort(years);
                    for(int i : years){
                        playedFilterTokenChoices.getItems().add(""+i);
                    }
                    break;
                case 9: //Don't filter
                    playedGamesTable.sortAndFilter("");
                    break;
            }
            playedFilterTokenChoices.getSelectionModel().selectFirst();
        });

        unplayedFilterChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            //This shit fixes an issue where unplayedFilterTokenChoices would remember how far down you scrolled after changing filter type
            //For example, Franchise to Status. It would be scrolled past the available options
            int index = unplayedChoiceHBox.getChildren().indexOf(unplayedFilterTokenChoices);
            unplayedChoiceHBox.getChildren().remove(unplayedFilterTokenChoices);
            unplayedFilterTokenChoices = new ChoiceBox<>();
            unplayedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) ->
                    unplayedGamesTable.sortAndFilter(newValue1));
            unplayedChoiceHBox.getChildren().add(index, unplayedFilterTokenChoices);

            switch (ApplicationGUI.unplayedFilterChoices.getSelectionModel().getSelectedIndex()) {
                case 0: //Status
                    unplayedFilterTokenChoices.getItems().addAll("Backlog", "SubBacklog", "Wishlist");
                    break;
                case 1: //Franchise
                    ObservableList<String> franchises = FXCollections.observableArrayList();
                    for(UnplayedGame game : GameLists.unplayedList){
                        if(!franchises.contains(game.getFranchise()) && !game.getFranchise().equals("")){
                            franchises.add(game.getFranchise());
                        }
                    }
                    Collections.sort(franchises);
                    unplayedFilterTokenChoices.getItems().addAll(franchises);
                    break;
                case 2: //Platform
                    unplayedFilterTokenChoices.getItems().addAll(GameLists.platformList);
                    break;
                case 3: //Genre
                    unplayedFilterTokenChoices.getItems().addAll(GameLists.genreList);
                    break;
                case 4: //Deck Status
                    unplayedFilterTokenChoices.getItems().addAll("Yes", "No", "Maybe", "Blank");
                    break;
                case 5: //Release Year
                    ObservableList<Integer> years = FXCollections.observableArrayList();
                    for(UnplayedGame game : GameLists.unplayedList){
                        int year;
                        year = game.getReleaseYear();

                        if(!years.contains((year)) && year!=0){
                            years.add(year);
                        }
                    }
                    Collections.sort(years);
                    for(int i : years){
                        unplayedFilterTokenChoices.getItems().add(""+i);
                    }
                    break;
                case 6: //No Filter
                    unplayedGamesTable.sortAndFilter("");
                    break;
            }
            unplayedFilterTokenChoices.getSelectionModel().selectFirst();
        });

        playedSortChoices.getSelectionModel().selectFirst();
        playedFilterChoices.getSelectionModel().selectLast();
        unplayedSortChoices.getSelectionModel().selectFirst();
        unplayedFilterChoices.getSelectionModel().selectLast();

        fileMenu.getItems().addAll(newFileMenuItem, openFileMenuItem, separatorMenuItem,
                saveFileMenuItem, saveAsFileMenuItem, separatorMenuItem5, exitMenuItem);

        listMenu.getItems().addAll(addNewGameMenuItem, editGameMenuItem, moveGameMenuItem,
                removeGameMenuItem, collectGameMenuItem, separatorMenuItem3,
                editGenreListMenuItem, editPlatformListMenuItem, separatorMenuItem4,
                statsMenuItem, collectionsMenuItem, achievementsMenuItem);

        randomMenu.getItems().addAll(chooseRandomGameMenuItem, chooseRandomWishlistGameMenuItem, generateRandomListMenuItem);

        topBoxPlayed.setAlignment(Pos.CENTER_LEFT);
        topBoxUnplayed.setAlignment(Pos.CENTER_LEFT);
        topBoxPlayed.setSpacing(10);
        topBoxUnplayed.setSpacing(10);
        playedWindow.setSpacing(5);
        unplayedWindow.setSpacing(5);

        //Reset all lists.
        newFileMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create New File");
            Label label = new Label("Are you sure?");
            Label label1 = new Label("Unsaved data will be lost.");
            label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");
            yesButton.setStyle("-fx-font-size: 16;");
            noButton.setStyle("-fx-font-size: 16;");
            yesButton.setPrefWidth(80);
            noButton.setPrefWidth(80);
            HBox hbox = new HBox(yesButton, noButton);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(30);
            VBox vbox = new VBox(label, label1, hbox);
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.TOP_CENTER);
            vbox.setPadding(new Insets(10));
            Scene scene = new Scene(vbox);
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            stage.show();
            yesButton.setOnAction(e1 -> {
                GameLists.playedList.clear();
                GameLists.unplayedList.clear();
                GameLists.genreList.clear();
                GameLists.genreList.add("Action");
                GameLists.platformList.clear();
                GameLists.platformList.add("PC");
                playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
                statusCountBoxPlayed.updateData();
                statusCountBoxUnplayed.updateData();
                currentFilePathOut = Path.of("List.json").toAbsolutePath();
                primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
                stage.close();
            });
            noButton.setOnAction(e1 -> stage.close());
        });

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName("List");
        //Load a file using filechooser
        openFileMenuItem.setOnAction(e -> {
            try {
                currentFilePathOut = fileChooser.showOpenDialog(primaryStage).toPath();
                openFile(currentFilePathOut);
                primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
            }catch (NullPointerException ignored){
                //Who cares.
            }
         });

        //Saves to the default "List.json" file
        saveFileMenuItem.setOnAction(e -> {
            try {
                File fileOut = new File(currentFilePathOut.toString());
                saveFile(fileOut);
            } catch (NullPointerException | FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        //Saves to a file chosen by the user.
        saveAsFileMenuItem.setOnAction(e -> {
            try {
                currentFilePathOut = fileChooser.showSaveDialog(primaryStage).toPath();
                saveFile(currentFilePathOut.toFile());
                primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
            } catch (NullPointerException | FileNotFoundException ignored) {}
        });

        //Closes the application, asks the user if they want to save.
        exitMenuItem.setOnAction(e -> {
            if (changeMade) {
                e.consume();
                Stage stage = new Stage();
                stage.getIcons().add(icon);
                stage.setTitle("Save File");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                Button saveButton = new Button("Save");
                Button saveAsButton = new Button("Save As");
                Button dontButton = new Button("Don't Save");
                Button cancelButton = new Button("Cancel");
                Label label = new Label("Save file?");
                HBox hbox = new HBox(saveButton, saveAsButton, dontButton, cancelButton);
                VBox vbox = new VBox(label, hbox);
                label.setStyle("-fx-font-size: 24;");
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(5);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);

                saveButton.setOnAction(e1 -> {
                    saveFileMenuItem.fire();
                    primaryStage.close();
                    stage.close();
                });

                saveAsButton.setOnAction(e1 -> {
                    saveAsFileMenuItem.fire();
                    primaryStage.close();
                    stage.close();
                });

                dontButton.setOnAction(e1 -> {
                    primaryStage.close();
                    stage.close();
                });

                cancelButton.setOnAction(e1 -> stage.close());

                Scene scene = new Scene(vbox);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.show();
            } else {
                primaryStage.close();
            }
        });

        //Creates a new game for either the played or unplayed list
        addNewGameMenuItem.setOnAction(e -> {
            if (playedOpen) { //Played Game
                Stage stage = new Stage();
                stage.getIcons().add(icon);
                stage.setResizable(false);
                AddPlayedGame addPlayedGame = new AddPlayedGame(stage);
                Scene scene = new Scene(addPlayedGame);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.setTitle("Add New Played Game");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                    } else if (e1.getCode() == KeyCode.ENTER) {
                            addPlayedGame.saveAndQuit(stage);
                    }
                });

            } else { //Unplayed Game
                Stage stage = new Stage();
                stage.getIcons().add(icon);
                stage.setResizable(false);
                AddUnplayedGame addUnplayedGame = new AddUnplayedGame(stage);
                Scene scene = new Scene(addUnplayedGame);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.setTitle("Add New Played Game");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                    } else if (e1.getCode() == KeyCode.ENTER) {
                            addUnplayedGame.saveAndQuit(stage);
                    }
                });
                statusCountBoxUnplayed.updateData();
            }
        });

        //Opens the edit window for the selected game.
        editGameMenuItem.setOnAction(e -> {
            if(playedOpen){//played game
                if(playedGamesTable.getSelectionModel().getSelectedIndex()!=-1)
                    playedGamesTable.editGame(playedGamesTable.getSelectionModel().getSelectedItem());
            }else{//unplayed game
                if(unplayedGamesTable.getSelectionModel().getSelectedIndex()!=-1)
                    unplayedGamesTable.editGame(unplayedGamesTable.getSelectionModel().getSelectedItem());
            }
        });

        //Moves the selected game from the currently open list to the other.
        moveGameMenuItem.setOnAction(e -> {
            if (playedOpen) { //Played game -> unplayed game
                int gameInt = playedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    PlayedGame game = playedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Move Played Game");
                    Label label = new Label("Move " + game.getTitle());
                    Label label1 = new Label("Are you sure?");
                    label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                    Button yesButton = new Button("Yes");
                    Button noButton = new Button("No");
                    yesButton.setStyle("-fx-font-size: 16;");
                    noButton.setStyle("-fx-font-size: 16;");
                    yesButton.setPrefWidth(80);
                    noButton.setPrefWidth(80);
                    HBox hbox = new HBox(yesButton, noButton);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(30);
                    VBox vbox = new VBox(label, label1, hbox);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.TOP_CENTER);
                    vbox.setPadding(new Insets(10));
                    Scene scene = new Scene(vbox);
                    scene.getStylesheets().add(styleSheet);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                        UnplayedGame newGame = new UnplayedGame(game.getTitle(), "Backlog", game.getPlatform(),
                                game.getGenre(), game.getReleaseYear(), game.getReleaseMonth(), game.getReleaseDay());
                        newGame.setFranchise(game.getFranchise());
                        GameLists.unplayedList.add(newGame);

                        for(GameCollection collection : GameLists.collectionList){
                            if(collection.getGames().contains(game)) {
                                collection.getGames().add(collection.getGames().indexOf(game), newGame);
                                collection.getGames().remove(game);
                            }
                        }

                        GameLists.playedList.remove(game);
                        playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                        unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
                        stage.close();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }

            } else { //Unplayed Game -> Played Game
                int gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    UnplayedGame game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Move Unplayed Game");
                    Label label = new Label("Move " + game.getTitle());
                    Label label1 = new Label("Are you sure?");
                    label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                    Button yesButton = new Button("Yes");
                    Button noButton = new Button("No");
                    yesButton.setStyle("-fx-font-size: 16;");
                    noButton.setStyle("-fx-font-size: 16;");
                    yesButton.setPrefWidth(80);
                    noButton.setPrefWidth(80);
                    HBox hbox = new HBox(yesButton, noButton);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(30);
                    VBox vbox = new VBox(label, label1, hbox);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.TOP_CENTER);
                    vbox.setPadding(new Insets(10));
                    Scene scene = new Scene(vbox);
                    scene.getStylesheets().add(styleSheet);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                        PlayedGame newGame = new PlayedGame(game.getTitle(), "Playing", game.getPlatform(),
                                game.getGenre(), game.getReleaseYear(), game.getReleaseMonth(), game.getReleaseDay());
                        newGame.setFranchise(game.getFranchise());
                        GameLists.playedList.add(newGame);

                        for(GameCollection collection : GameLists.collectionList){
                            if(collection.getGames().contains(game)) {
                                collection.getGames().add(collection.getGames().indexOf(game), newGame);
                                collection.getGames().remove(game);
                            }
                        }

                        GameLists.unplayedList.remove(game);
                        playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                        unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
                        stage.close();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }
            }
        });

        //Removed selected game from the list.
        removeGameMenuItem.setOnAction(e -> {
            if (playedOpen) { //Removed played game
                int gameInt = playedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    PlayedGame game = playedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Remove Played Game");
                    Label label = new Label("Remove " + game.getTitle());
                    Label label1 = new Label("Are you sure?");
                    label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                    Button yesButton = new Button("Yes");
                    Button noButton = new Button("No");
                    yesButton.setStyle("-fx-font-size: 16;");
                    noButton.setStyle("-fx-font-size: 16;");
                    yesButton.setPrefWidth(80);
                    noButton.setPrefWidth(80);
                    HBox hbox = new HBox(yesButton, noButton);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(30);
                    VBox vbox = new VBox(label, label1, hbox);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.TOP_CENTER);
                    vbox.setPadding(new Insets(10));
                    Scene scene = new Scene(vbox);
                    scene.getStylesheets().add(styleSheet);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                        GameLists.playedList.remove(game);

                        //Apparently this is fine even if the collection doesn't have the game.
                        for(GameCollection collection : GameLists.collectionList)
                            collection.getGames().remove(game);

                        statusCountBoxPlayed.updateData();
                        playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                        stage.close();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }
            } else { //Remove unplayed game
                int gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    UnplayedGame game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Remove Unplayed Game");
                    Label label = new Label("Remove " + game.getTitle());
                    Label label1 = new Label("Are you sure?");
                    label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                    Button yesButton = new Button("Yes");
                    Button noButton = new Button("No");
                    yesButton.setStyle("-fx-font-size: 16;");
                    noButton.setStyle("-fx-font-size: 16;");
                    yesButton.setPrefWidth(80);
                    noButton.setPrefWidth(80);
                    HBox hbox = new HBox(yesButton, noButton);
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setSpacing(30);
                    VBox vbox = new VBox(label, label1, hbox);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.TOP_CENTER);
                    vbox.setPadding(new Insets(10));
                    Scene scene = new Scene(vbox);
                    scene.getStylesheets().add(styleSheet);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                        GameLists.unplayedList.remove(game);

                        //Apparently this is fine even if the collection doesn't have the game.
                        for(GameCollection collection : GameLists.collectionList)
                            collection.getGames().remove(game);

                        statusCountBoxUnplayed.updateData();
                        unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
                        stage.close();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }
            }
        });

        collectGameMenuItem.setOnAction(e -> {
            if(GameLists.collectionList.isEmpty()) {
                Stage stage = new Stage();
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("No Collections");
                Label label = new Label("No Collections");
                label.setAlignment(Pos.CENTER);
                VBox vbox = new VBox(label);
                label.setStyle("-fx-font-size: 24;");
                vbox.setAlignment(Pos.CENTER);
                Scene scene = new Scene(vbox);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.show();
            }else {
                Game game;
                int gameInt;
                if (playedOpen) { //Removed played game
                    gameInt = playedGamesTable.getSelectionModel().getSelectedIndex();

                    if (gameInt != -1) {
                        game = playedGamesTable.getSelectionModel().getSelectedItem();
                    } else {
                        game = null;
                    }

                } else { //Remove unplayed game
                    gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                    if (gameInt != -1) {
                        game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                    } else {
                        game = null;
                    }
                }
                if (gameInt != -1) {
                    Stage stage = new Stage();
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Select Collection");
                    Label label = new Label("Add " + game.getTitle() + " to Collection");
                    Label label1 = new Label("Choose Collection");
                    label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                    ChoiceBox<GameCollection> collectionChoiceBox = new ChoiceBox<>(GameLists.collectionList);
                    collectionChoiceBox.getSelectionModel().selectFirst();
                    Button addButton = new Button("Add Game");
                    VBox vbox = new VBox(label, label1, collectionChoiceBox, addButton);
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.TOP_CENTER);
                    vbox.setPadding(new Insets(10));
                    Scene scene = new Scene(vbox);
                    scene.getStylesheets().add(styleSheet);
                    stage.setScene(scene);
                    stage.show();

                    addButton.setOnAction(e1 -> {
                        collectionChoiceBox.getSelectionModel().getSelectedItem().getGames().add(game);
                        stage.close();
                        changeMade = true;
                    });

                    scene.setOnKeyPressed(e1 -> {
                        if (e1.getCode() == KeyCode.ESCAPE) {
                            stage.close();
                        } else if (e1.getCode() == KeyCode.ENTER) {
                            addButton.fire();
                        }
                    });
                }
            }
        });

        //Opens a window for the user to edit the genre list.
        editGenreListMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setTitle("Edit Genre List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditGenreList window = new EditGenreList();
            Scene scene = new Scene(window);
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            stage.show();

            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                } else if (e1.getCode() == KeyCode.ENTER) {
                    if (window.addItemField.isFocused()) {
                        window.addItemButton.fire();
                    }
                }
            });
        });

        //Opens a window for the user to edit the platform list.
        editPlatformListMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setTitle("Edit Platform List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditPlatformList window = new EditPlatformList();
            Scene scene = new Scene(window);
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            stage.show();
            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                } else if (e1.getCode() == KeyCode.ENTER) {
                    if (window.addItemField.isFocused()) {
                        window.addItemButton.fire();
                    }
                }
            });
        });

        //Open the stats view
        statsMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Stats");
            StatsScreen statsScreen = new StatsScreen();
            Scene scene = new Scene(statsScreen, 1000, 800);
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            stage.show();
            stage.setWidth(stage.getWidth() + 10);
        });

        //Open the collection view
        collectionsMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Collections");
            CollectionsWindow collectionsWindow = new CollectionsWindow(stage);
            Scene scene = new Scene(collectionsWindow);
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            stage.show();
            stage.setWidth(stage.getWidth()+25);
        });

        achievementsMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Achievements");
            AchievementWindow achievementWindow = new AchievementWindow();
            Scene scene = new Scene(achievementWindow);
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);
            stage.show();
        });

        //Chooses a random game from the unplayed list with the status "Backlog", or "Subbacklog"
        chooseRandomGameMenuItem.setOnAction(e -> {
            ArrayList<String> gameList = new ArrayList<>();

            for (int i = 0; i < GameLists.unplayedList.size(); i++) {
                if (!(GameLists.unplayedList.get(i)).getStatus().equals("Wishlist"))
                    gameList.add((GameLists.unplayedList.get(i)).getTitle());
            }

            if (gameList.size() > 0) {
                Stage stage = new Stage();
                Random rand = new Random();
                stage.getIcons().add(icon);
                stage.setTitle("Random Game");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                Label label = new Label("");
                label.setStyle("-fx-font-size: 16;");
                Button button = new Button("Close");
                button.setOnAction(e1 -> stage.close());
                VBox vbox = new VBox(label, button);
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(5));
                label.setText(gameList.get(rand.nextInt(gameList.size())));
                Scene scene = new Scene(vbox);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.show();
                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE || e1.getCode() == KeyCode.ENTER) {
                        stage.close();
                    }
                });
            }
        });

        //Chooses a random game from the unplayed list with the status "Wishlist."
        chooseRandomWishlistGameMenuItem.setOnAction(e -> {
            ArrayList<String> gameList = new ArrayList<>();

            for (int i = 0; i < GameLists.unplayedList.size(); i++) {
                if ((GameLists.unplayedList.get(i)).getStatus().equals("Wishlist"))
                    gameList.add((GameLists.unplayedList.get(i)).getTitle());
            }

            if (gameList.size() > 0) {
                Stage stage = new Stage();
                Random rand = new Random();
                stage.getIcons().add(icon);
                stage.setTitle("Random Game");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                Label label = new Label("");
                label.setStyle("-fx-font-size: 16;");
                Button button = new Button("Close");
                button.setOnAction(e1 -> stage.close());
                VBox vbox = new VBox(label, button);
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(5));
                label.setText(gameList.get(rand.nextInt(gameList.size())));
                Scene scene = new Scene(vbox);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.show();
                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE || e1.getCode() == KeyCode.ENTER) {
                        stage.close();
                    }
                });
            }
        });

        //Chooses a random game from the temporary list.
        chooseRandomFromList.setOnAction(e -> {
            if (unplayedTempList.getTitles().size() > 0) {
                Stage stage = new Stage();
                Random rand = new Random();
                stage.getIcons().add(icon);
                stage.setTitle("Random Game");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                Label label = new Label("");
                label.setStyle("-fx-font-size: 16;");
                Button button = new Button("Close");
                button.setOnAction(e1 -> stage.close());
                VBox vbox = new VBox(label, button);
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(5));
                label.setText(unplayedTempList.getTitles().get(rand.nextInt(unplayedTempList.getTitles().size())));
                Scene scene = new Scene(vbox);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);
                stage.show();
                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE || e1.getCode() == KeyCode.ENTER) {
                        stage.close();
                    }
                });
            }
        });

        //Generates a random list of unplayed games based on filters provided by the user.
        generateRandomListMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(icon);
            stage.setTitle("Random Game List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            RandomListGenerator window = new RandomListGenerator();
            Scene scene = new Scene(window, 1600, 500);
            scene.getStylesheets().add(styleSheet);
            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                } else if (e1.getCode() == KeyCode.ENTER) {
                    window.generateButton.fire();
                }
            });
            stage.setScene(scene);
            stage.show();
        });

        //Switches the current window from the played list to the unplayed list.
        switchFromPlayed.setOnAction(e -> {
            primarySceneVBox.getChildren().clear();
            primarySceneVBox.getChildren().addAll(menuBar, unplayedWindow);
            randomMenu.getItems().clear();
            randomMenu.getItems().addAll(chooseRandomGameMenuItem, chooseRandomWishlistGameMenuItem,
                    chooseRandomFromList, generateRandomListMenuItem);
            playedOpen = false;
        });

        //Switches the current window from the unplayed list to the played list.
        switchFromUnplayed.setOnAction(e -> {
            primarySceneVBox.getChildren().clear();
            primarySceneVBox.getChildren().addAll(menuBar, playedWindow);
            randomMenu.getItems().clear();
            randomMenu.getItems().addAll(chooseRandomGameMenuItem, chooseRandomWishlistGameMenuItem,
                    generateRandomListMenuItem);
            playedOpen = true;
        });

        //Consume the close request and fire the exit button so that the program will ask the user if they want to save their list.
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exitMenuItem.fire();
        });

        openFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        saveFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN));
        saveAsFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q,
                KeyCombination.CONTROL_DOWN));
        addNewGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
                KeyCombination.CONTROL_DOWN));
        editGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E,
                KeyCombination.CONTROL_DOWN));
        moveGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.M,
                KeyCombination.CONTROL_DOWN));
        removeGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.R,
                KeyCombination.CONTROL_DOWN));
        collectGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C,
                KeyCombination.CONTROL_DOWN));

        //open the default "List.json" file
        openFile(currentFilePathOut);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    //Saves the current data to a given file object.
    public void saveFile(File fileOut) throws FileNotFoundException {
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saving...");
        Label label = new Label("Saving...");
        label.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(label);
        label.setStyle("-fx-font-size: 24;");
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 200, 100);
        scene.getStylesheets().add(styleSheet);
        stage.setScene(scene);
        stage.show();
        JSONObject file = new JSONObject();
        JSONArray platformListArray = new JSONArray();

        for (int i = 0; i < GameLists.platformList.size(); i++)
            platformListArray.put(GameLists.platformList.get(i));
        JSONArray genreListArray = new JSONArray();

        for (int j = 0; j < GameLists.genreList.size(); j++)
            genreListArray.put(GameLists.genreList.get(j));
        JSONArray playedGameArray = new JSONArray();

        for (int k = 0; k < GameLists.playedList.size(); k++) {
            JSONObject newGame = new JSONObject();
            PlayedGame game = GameLists.playedList.get(k);
            try {
                newGame.put("S", game.getStatus());
                newGame.put("T", game.getTitle());
                newGame.put("P", game.getPlatform());
                newGame.put("G", game.getGenre());
                newGame.put("RY", game.getReleaseYear());
                newGame.put("RM", game.getReleaseMonth());
                newGame.put("RD", game.getReleaseDay());
                newGame.put("F", game.getFranchise());
                newGame.put("R", game.getRating());
                newGame.put("SS", game.getIsItShort());
                newGame.put("CY", game.getCompletionYear());
                newGame.put("CM", game.getCompletionMonth());
                newGame.put("CD", game.getCompletionDay());
                newGame.put("1", game.getPercent100());
                playedGameArray.put(newGame);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        JSONArray unplayedGameArray = new JSONArray();

        for (int m = 0; m < GameLists.unplayedList.size(); m++) {
            JSONObject newGame = new JSONObject();
            UnplayedGame game = GameLists.unplayedList.get(m);
            try {
                newGame.put("S", game.getStatus());
                newGame.put("T", game.getTitle());
                newGame.put("P", game.getPlatform());
                newGame.put("G", game.getGenre());
                newGame.put("RY", game.getReleaseYear());
                newGame.put("RM", game.getReleaseMonth());
                newGame.put("RD", game.getReleaseDay());
                newGame.put("F", game.getFranchise());
                newGame.put("H", game.getHours());
                newGame.put("D", game.getDeckCompatible());
                unplayedGameArray.put(newGame);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        JSONArray collectionArray = new JSONArray();

        for (int n = 0; n < GameLists.collectionList.size(); n++){
            JSONObject newCollection = new JSONObject();
            GameCollection collection = GameLists.collectionList.get(n);
            JSONArray games = new JSONArray();
            try {
                newCollection.put("CT", collection.getTitle());
                for(int o = 0; o < collection.getGames().size(); o++){
                    Game game = collection.getGames().get(o);
                    String gameID = "";
                    if(game instanceof PlayedGame){
                        gameID = "p" + GameLists.playedList.indexOf(game);
                    }else if(game instanceof UnplayedGame){
                        gameID = "u" + GameLists.unplayedList.indexOf(game);
                    }else{
                        System.out.println("?????????????");
                    }
                    games.put(gameID);
                }
                newCollection.put("CG", games);
                collectionArray.put(newCollection);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        file.put("PL", platformListArray);
        file.put("GL", genreListArray);
        file.put("P", playedGameArray);
        file.put("U", unplayedGameArray);
        file.put("C", collectionArray);
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileOut), StandardCharsets.UTF_8));
        pw.write(file.toString());
        pw.flush();
        pw.close();
        changeMade = false;
        label.setText("File Saved.");
        Button button = new Button("Close");
        vbox.getChildren().add(button);
        button.setOnAction(e1 -> stage.close());
    }

    //Opens a file from the given file path
    public void openFile(Path filePath) {
        try {
            String jsonString = Files.readString(filePath);
            JSONObject file = new JSONObject(jsonString);

            JSONArray platformList = file.getJSONArray("PL");
            GameLists.platformList = FXCollections.observableArrayList();
            for (int i = 0; i < platformList.length(); i++)
                GameLists.platformList.add((String) platformList.get(i));

            JSONArray genreList = file.getJSONArray("GL");
            GameLists.genreList = FXCollections.observableArrayList();
            for (int j = 0; j < genreList.length(); j++)
                GameLists.genreList.add((String) genreList.get(j));

            JSONArray playedGameList = file.getJSONArray("P");
            GameLists.playedList = FXCollections.observableArrayList();
            for (int k = 0; k < playedGameList.length(); k++) {
                Object obj = playedGameList.get(k);
                JSONObject newObj = (JSONObject) obj;
                PlayedGame newGame = new PlayedGame((String) newObj.get("T"), (String) newObj.get("S"),
                        (String) newObj.get("P"), (String) newObj.get("G"), (int) newObj.get("RY"),
                        (int) newObj.get("RM"), (int) newObj.get("RD"));
                newGame.setCompletionYear((int) newObj.get("CY"));
                newGame.setCompletionMonth((int) newObj.get("CM"));
                newGame.setCompletionDay((int) newObj.get("CD"));
                newGame.setIsItShort((String) newObj.get("SS"));
                newGame.setRating((int) newObj.get("R"));
                newGame.setPercent100((String) newObj.get("1"));
                newGame.setFranchise((String) newObj.get("F"));
                GameLists.playedList.add(newGame);
            }

            JSONArray unplayedGameList = file.getJSONArray("U");
            GameLists.unplayedList = FXCollections.observableArrayList();
            for (int m = 0; m < unplayedGameList.length(); m++) {
                Object obj = unplayedGameList.get(m);
                JSONObject newObj = (JSONObject) obj;
                UnplayedGame newGame = new UnplayedGame((String) newObj.get("T"), (String) newObj.get("S"),
                        (String) newObj.get("P"), (String) newObj.get("G"), (int) newObj.get("RY"),
                        (int) newObj.get("RM"), (int) newObj.get("RD"));
                newGame.setFranchise((String) newObj.get("F"));
                try {
                    newGame.setHours(((BigDecimal) newObj.get("H")).doubleValue());
                } catch (ClassCastException e2) {
                    newGame.setHours((int) newObj.get("H"));
                }

                newGame.setDeckCompatible((String) newObj.get("D"));
                GameLists.unplayedList.add(newGame);
            }

            JSONArray collectionArray = file.getJSONArray("C");
            GameLists.collectionList = FXCollections.observableArrayList();
            for(int n = 0; n < collectionArray.length(); n++){
                Object obj = collectionArray.get(n);
                JSONObject newObj = (JSONObject) obj;
                GameCollection newCollection = new GameCollection((String) newObj.get("CT"));
                JSONArray collectionGameArray = newObj.getJSONArray("CG");
                for(int o = 0; o < collectionGameArray.length(); o++){
                    String gameID = (String) collectionGameArray.get(o);
                    int gameIndex = Integer.parseInt(gameID.substring(1));
                    if(gameID.charAt(0)=='p'){
                        newCollection.getGames().add(GameLists.playedList.get(gameIndex));
                    }else if(gameID.charAt(0)=='u'){
                        newCollection.getGames().add(GameLists.unplayedList.get(gameIndex));
                    }
                }
                GameLists.collectionList.add(newCollection);
            }

            playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
            unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
            statusCountBoxPlayed.updateData();
            statusCountBoxUnplayed.updateData();
        } catch (NoSuchFileException ignored) {
            //This is ok. It just means that the user doesn't currently have a list file.
        } catch (NullPointerException | IOException e1) {
            e1.printStackTrace();
        }
    }
}