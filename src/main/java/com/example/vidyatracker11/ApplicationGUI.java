package com.example.vidyatracker11;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    //Main GUI
    public static StatusCountBoxPlayed statusCountBoxPlayed = new StatusCountBoxPlayed();
    public static StatusCountBoxUnplayed statusCountBoxUnplayed = new StatusCountBoxUnplayed();
    public static ChoiceBox<String> playedSortChoices = new ChoiceBox<>();
    public static Label playedSortLabel = new Label("Sort by: ");
    public static Label playedFilterLabel = new Label("Filter by: ");
    public static ChoiceBox<String> playedFilterChoices = new ChoiceBox<>();
    public static PlayedGamesTable playedGamesTable = new PlayedGamesTable();
    public static HBox playedChoiceHBox = new HBox(playedSortLabel, playedSortChoices, playedFilterLabel, playedFilterChoices);
    public static VBox playedGamesVBox = new VBox(playedChoiceHBox, playedGamesTable);
    public static ChoiceBox<String> unplayedSortChoices = new ChoiceBox<>();
    public static ChoiceBox<String> unplayedFilterChoices = new ChoiceBox<>();
    public static UnplayedGamesTable unplayedGamesTable = new UnplayedGamesTable();
    public static Label unplayedSortLabel = new Label("Sort by: ");
    public static Label unplayedFilterLabel = new Label("Filter by: ");
    public static HBox unplayedChoiceHBox = new HBox(unplayedSortLabel, unplayedSortChoices, unplayedFilterLabel, unplayedFilterChoices);
    public static VBox unplayedGamesVBox = new VBox(unplayedChoiceHBox, unplayedGamesTable);
    public static UnplayedTempList unplayedTempList = new UnplayedTempList();
    public static Button switchFromPlayed = new Button("Show Unplayed List");
    public static Button switchFromUnplayed = new Button("Show Played List");
    public static HBox topBoxPlayed = new HBox(statusCountBoxPlayed, switchFromPlayed);
    public static HBox topBoxUnplayed = new HBox(statusCountBoxUnplayed, unplayedTempList, switchFromUnplayed);
    public static VBox playedWindow = new VBox(topBoxPlayed, playedGamesVBox);
    public static VBox unplayedWindow = new VBox(topBoxUnplayed, unplayedGamesVBox);

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
    public static SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
    public static MenuItem movePlayedGameMenuItem = new MenuItem("Move Selected Game");
    public static SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
    public static MenuItem removeGameMenuItem = new MenuItem("Remove Selected Game");
    public static SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
    public static MenuItem editGenreListMenuItem = new MenuItem("Edit Genre List");
    public static MenuItem editPlatformListMenuItem = new MenuItem("Edit Platform List");
    public static SeparatorMenuItem separatorMenuItem4 = new SeparatorMenuItem();
    public static MenuItem statsMenuItem = new MenuItem("Show Stats Window");
    public static Menu listMenu = new Menu("List");

    //Random Menu
    public static MenuItem chooseRandomGameMenuItem = new MenuItem("Choose a Random Game to Play");
    public static MenuItem chooseRandomWishlistGameMenuItem = new MenuItem("Choose a Random Game to Buy");
    public static MenuItem generateRandomListMenuItem = new MenuItem("Generate a Random List of Games Based on Filters");
    public static MenuItem chooseRandomFromList = new MenuItem("Choose a Random Game From the Small List");
    public static Menu randomMenu = new Menu("Random");

    public static MenuBar menuBar = new MenuBar(fileMenu, listMenu, randomMenu);
    public static StatsScreen stats = new StatsScreen();
    public static FileChooser fileChooser = new FileChooser();
    public static VBox primarySceneVBox = new VBox(menuBar, playedWindow);
    public static Scene primaryScene = new Scene(primarySceneVBox, 1300, 900);
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vidya Tracker");

        primaryStage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));

        playedChoiceHBox.setSpacing(5);
        unplayedChoiceHBox.setSpacing(5);
        playedGamesVBox.setSpacing(5);
        unplayedGamesVBox.setSpacing(5);

        unplayedSortChoices.getItems().addAll("Title", "Platform", "Genre", "Hours", "Release Date");
        unplayedFilterChoices.getItems().addAll("Deck Status: Yes", "Deck Status: No", "Deck Status: Maybe", "No Filter");
        playedSortChoices.getItems().addAll("Title", "Rating", "Platform", "Genre", "Release Date", "Completion Date");
        playedFilterChoices.getItems().addAll("Short: Yes", "Short: No", "100%: Yes", "100%: No", "No Filter");

        playedSortChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices));
        playedFilterChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices));
        unplayedSortChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices));
        unplayedFilterChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices));

        playedSortChoices.getSelectionModel().selectFirst();
        playedFilterChoices.getSelectionModel().selectLast();
        unplayedSortChoices.getSelectionModel().selectFirst();
        unplayedFilterChoices.getSelectionModel().selectLast();

        fileMenu.getItems().addAll(newFileMenuItem, openFileMenuItem, separatorMenuItem,
                saveFileMenuItem, saveAsFileMenuItem, separatorMenuItem5, exitMenuItem);

        listMenu.getItems().addAll(addNewGameMenuItem, separatorMenuItem1, movePlayedGameMenuItem,
                separatorMenuItem2, removeGameMenuItem, separatorMenuItem3, editGenreListMenuItem,
                editPlatformListMenuItem, separatorMenuItem4, statsMenuItem);

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
            stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
            Scene scene = new Scene(vbox, 300, 130);
            stage.setScene(scene);
            stage.show();
            yesButton.setOnAction(e1 -> {
                GameLists.playedList.clear();
                GameLists.unplayedList.clear();
                GameLists.genreList.clear();
                GameLists.genreList.add("Action");
                GameLists.platformList.clear();
                GameLists.platformList.add("PC");
                playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices);
                unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices);
                statusCountBoxPlayed.updateData();
                statusCountBoxUnplayed.updateData();
                stage.close();
                stats.updateStats();
            });
            noButton.setOnAction(e1 -> stage.close());
        });

        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName("List");
        //Load a file using filechooser
        openFileMenuItem.setOnAction(e ->
                openFile(fileChooser.showOpenDialog(primaryStage).toPath()));

        //Saves to the default "List.json" file
        saveFileMenuItem.setOnAction(e -> {
            try {
                File fileOut = new File("List.json");
                saveFile(fileOut);
            } catch (NullPointerException | FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        //Saves to a file chosen by the user.
        saveAsFileMenuItem.setOnAction(e -> {
            try {
                File fileOut = fileChooser.showSaveDialog(primaryStage);
                saveFile(fileOut);
            } catch (NullPointerException | FileNotFoundException ignored) {}
        });

        //Closes the application, asks the user if they want to save.
        exitMenuItem.setOnAction(e -> {
            if (changeMade) {
                e.consume();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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

                Scene scene = new Scene(vbox, 300, 100);
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
                stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
                stage.setResizable(false);
                AddPlayedGame addPlayedGame = new AddPlayedGame(stage);
                Scene scene = new Scene(addPlayedGame);
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
                stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
                stage.setResizable(false);
                AddUnplayedGame addUnplayedGame = new AddUnplayedGame(stage);
                Scene scene = new Scene(addUnplayedGame);
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

        //Moves the selected game from the currently open list to the other.
        movePlayedGameMenuItem.setOnAction(e -> {
            if (playedOpen) { //Played game -> unplayed game
                int gameInt = playedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    PlayedGame game = playedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                    Scene scene = new Scene(vbox, 300, 150);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                            GameLists.unplayedList.add(new UnplayedGame(game.getTitle(), "Backlog", game.getPlatform(),
                                    game.getGenre(), game.getReleaseYear(), game.getReleaseMonth(), game.getReleaseDay()));

                        GameLists.playedList.remove(game);
                        playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices);
                        unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices);
                        stage.close();
                        stats.updateStats();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }

            } else { //Unplayed Game -> Played Game
                int gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    UnplayedGame game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                    Scene scene = new Scene(vbox, 300, 150);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                            GameLists.playedList.add(new PlayedGame(game.getTitle(), "Playing", game.getPlatform(),
                                    game.getGenre(), game.getReleaseYear(), game.getReleaseMonth(), game.getReleaseDay()));

                        GameLists.unplayedList.remove(game);
                        playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices);
                        unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices);
                        stage.close();
                        stats.updateStats();
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
                    stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                    Scene scene = new Scene(vbox, 300, 150);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                        GameLists.playedList.remove(game);
                        statusCountBoxPlayed.updateData();
                        playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices);
                        stage.close();
                        stats.updateStats();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }
            } else { //Remove unplayed game
                int gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    UnplayedGame game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                    Scene scene = new Scene(vbox, 300, 150);
                    stage.setScene(scene);
                    stage.show();

                    yesButton.setOnAction(e1 -> {
                        GameLists.unplayedList.remove(game);
                        statusCountBoxUnplayed.updateData();
                        unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices);
                        stage.close();
                        stats.updateStats();
                        changeMade = true;
                    });

                    noButton.setOnAction(e1 -> stage.close());
                }
            }
        });

        //Opens a window for the user to edit the genre list.
        editGenreListMenuItem.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
            stage.setTitle("Edit Genre List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditGenreList window = new EditGenreList();
            Scene scene = new Scene(window);
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
            stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
            stage.setTitle("Edit Platform List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            EditPlatformList window = new EditPlatformList();
            Scene scene = new Scene(window);
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
                stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                label.setText(gameList.get(rand.nextInt(gameList.size())));
                Scene scene = new Scene(vbox, 200, 100);
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
                stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                label.setText(gameList.get(rand.nextInt(gameList.size())));
                Scene scene = new Scene(vbox, 200, 100);
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
                stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
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
                label.setText(unplayedTempList.getTitles().get(rand.nextInt(unplayedTempList.getTitles().size())));
                Scene scene = new Scene(vbox, 200, 100);
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
            stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
            stage.setTitle("Random Game List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            RandomListGenerator window = new RandomListGenerator();
            Scene scene = new Scene(window, 1600, 400);
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

        //Open the stats view
        statsMenuItem.setOnAction(e -> {
            primarySceneVBox.getChildren().clear();

            if (statsMenuItem.getText().equals("Show List Window")) {
                primarySceneVBox.getChildren().addAll(menuBar, playedWindow);
                playedOpen = true;
                statsMenuItem.setText("Show Stats Window");
                primaryStage.setWidth(1300);
            } else {
                primarySceneVBox.getChildren().addAll(menuBar, stats);
                statsMenuItem.setText("Show List Window");
                primaryStage.setWidth(1800);
            }

            stats.updateStats();
            StatsScreen.preventColumnReorderingOrResizingForAll();
        });

        //open the default "List.json" file
        openFile(Path.of("List.json").toAbsolutePath());
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    //Saves the current data to a given file object.
    public void saveFile(File fileOut) throws FileNotFoundException {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saving...");
        Label label = new Label("Saving...");
        label.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(label);
        label.setStyle("-fx-font-size: 24;");
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 300, 200);
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
                newGame.put("Status", game.getStatus());
                newGame.put("Title", game.getTitle());
                newGame.put("Platform", game.getPlatform());
                newGame.put("Genre", game.getGenre());
                newGame.put("Release Year", game.getReleaseYear());
                newGame.put("Release Month", game.getReleaseMonth());
                newGame.put("Release Day", game.getReleaseDay());
                newGame.put("Franchise", game.getFranchise());
                newGame.put("Rating", game.getRating());
                newGame.put("Short Status", game.getIsItShort());
                newGame.put("Completion Year", game.getCompletionYear());
                newGame.put("Completion Month", game.getCompletionMonth());
                newGame.put("Completion Day", game.getCompletionDay());
                newGame.put("100% Status", game.getPercent100());
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
                newGame.put("Status", game.getStatus());
                newGame.put("Title", game.getTitle());
                newGame.put("Platform", game.getPlatform());
                newGame.put("Genre", game.getGenre());
                newGame.put("Release Year", game.getReleaseYear());
                newGame.put("Release Month", game.getReleaseMonth());
                newGame.put("Release Day", game.getReleaseDay());
                newGame.put("Franchise", game.getFranchise());
                newGame.put("Hours", game.getHours());
                newGame.put("Deck Status", game.getDeckCompatible());
                unplayedGameArray.put(newGame);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        file.put("Platform List", platformListArray);
        file.put("Genre List", genreListArray);
        file.put("Played Games", playedGameArray);
        file.put("Unplayed Games", unplayedGameArray);
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
            JSONArray platformList = file.getJSONArray("Platform List");
            GameLists.platformList = FXCollections.observableArrayList();

            for (int i = 0; i < platformList.length(); i++)
                GameLists.platformList.add((String) platformList.get(i));
            JSONArray genreList = file.getJSONArray("Genre List");
            GameLists.genreList = FXCollections.observableArrayList();

            for (int j = 0; j < genreList.length(); j++)
                GameLists.genreList.add((String) genreList.get(j));
            JSONArray playedGameList = file.getJSONArray("Played Games");
            GameLists.playedList = FXCollections.observableArrayList();

            for (int k = 0; k < playedGameList.length(); k++) {
                Object obj = playedGameList.get(k);
                JSONObject newObj = (JSONObject) obj;
                PlayedGame newGame = new PlayedGame((String) newObj.get("Title"), (String) newObj.get("Status"),
                        (String) newObj.get("Platform"), (String) newObj.get("Genre"), (int) newObj.get("Release Year"),
                        (int) newObj.get("Release Month"), (int) newObj.get("Release Day"));
                newGame.setCompletionYear((int) newObj.get("Completion Year"));
                newGame.setCompletionMonth((int) newObj.get("Completion Month"));
                newGame.setCompletionDay((int) newObj.get("Completion Day"));
                newGame.setIsItShort((String) newObj.get("Short Status"));
                newGame.setRating((int) newObj.get("Rating"));
                newGame.setPercent100((String) newObj.get("100% Status"));
                newGame.setFranchise((String) newObj.get("Franchise"));
                GameLists.playedList.add(newGame);
            }

            JSONArray unplayedGameList = file.getJSONArray("Unplayed Games");
            GameLists.unplayedList = FXCollections.observableArrayList();

            for (int m = 0; m < unplayedGameList.length(); m++) {
                Object obj = unplayedGameList.get(m);
                JSONObject newObj = (JSONObject) obj;
                UnplayedGame newGame = new UnplayedGame((String) newObj.get("Title"), (String) newObj.get("Status"),
                        (String) newObj.get("Platform"), (String) newObj.get("Genre"), (int) newObj.get("Release Year"),
                        (int) newObj.get("Release Month"), (int) newObj.get("Release Day"));
                newGame.setFranchise((String) newObj.get("Franchise"));
                try {
                    newGame.setHours((double) newObj.get("Hours"));
                } catch (ClassCastException e2) {
                    newGame.setHours((int) newObj.get("Hours"));
                }

                newGame.setDeckCompatible((String) newObj.get("Deck Status"));
                GameLists.unplayedList.add(newGame);
            }

            playedGamesTable.sortAndFilter(playedSortChoices, playedFilterChoices);
            unplayedGamesTable.sortAndFilter(unplayedSortChoices, unplayedFilterChoices);
            statusCountBoxPlayed.updateData();
            statusCountBoxUnplayed.updateData();
            stats.updateStats();
        } catch (NoSuchFileException ignored) {
            //This is ok. It just means that the user doesn't currently have a list file.
        } catch (NullPointerException | IOException e1) {
            e1.printStackTrace();
        }
    }
}