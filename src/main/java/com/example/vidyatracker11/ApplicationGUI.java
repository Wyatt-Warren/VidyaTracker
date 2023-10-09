package com.example.vidyatracker11;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.UnaryOperator;

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
    public static MenuItem showGameCollectionsMenuItem = new MenuItem("Show Collections Containing Selected Game");
    public static SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
    public static MenuItem editGenreListMenuItem = new MenuItem("Edit Genre List");
    public static MenuItem editPlatformListMenuItem = new MenuItem("Edit Platform List");
    public static SeparatorMenuItem separatorMenuItem4 = new SeparatorMenuItem();
    public static MenuItem statsMenuItem = new MenuItem("Show Stats Window");
    public static MenuItem collectionsMenuItem = new MenuItem("Show Collections Window");
    public static MenuItem achievementsMenuItem = new MenuItem("Show Achievements Window");
    public static Menu listMenu = new Menu("List");

    //Random Menu
    public static MenuItem chooseRandomGameMenuItem = new MenuItem("Choose a Random Game to Replay");
    public static MenuItem generateRandomListMenuItem = new MenuItem("Generate a Random List of Games Based on Filters");
    public static MenuItem chooseRandomFromList = new MenuItem("Choose a Random Game From the Small List");
    public static Menu randomMenu = new Menu("Random");

    //Played Window
    public static StatusCountBoxPlayed statusCountBoxPlayed = new StatusCountBoxPlayed();
    public static Button switchFromPlayed = new Button("Show Unplayed List");
    public static HBox topBoxPlayed = new HBox(statusCountBoxPlayed, switchFromPlayed);
    public static Label playedSortLabel = new Label("Sort by:");
    public static ChoiceBox<String> playedSortChoices = new ChoiceBox<>();
    public static Label playedFilterLabel = new Label("Filter by:");
    public static ChoiceBox<String> playedFilterChoices = new ChoiceBox<>();
    public static Label playedFilterTokenLabel = new Label("Filter Token:");
    public static ChoiceBox<String> playedFilterTokenChoices = new ChoiceBox<>();
    public static HBox playedChoiceHBox = new HBox(playedSortLabel, playedSortChoices, playedFilterLabel,
            playedFilterChoices, playedFilterTokenLabel, playedFilterTokenChoices);
    public static PlayedGamesTable playedGamesTable = new PlayedGamesTable();
    public static VBox playedGamesVBox = new VBox(playedChoiceHBox, playedGamesTable);
    public static VBox playedWindow = new VBox(topBoxPlayed, playedGamesVBox);

    //Unplayed Window
    public static StatusCountBoxUnplayed statusCountBoxUnplayed = new StatusCountBoxUnplayed();
    public static UnplayedTempList unplayedTempList = new UnplayedTempList();
    public static Button switchFromUnplayed = new Button("Show Played List");
    public static HBox topBoxUnplayed = new HBox(statusCountBoxUnplayed, unplayedTempList, switchFromUnplayed);
    public static Label unplayedSortLabel = new Label("Sort by:");
    public static ChoiceBox<String> unplayedSortChoices = new ChoiceBox<>();
    public static Label unplayedFilterLabel = new Label("Filter by:");
    public static ChoiceBox<String> unplayedFilterChoices = new ChoiceBox<>();
    public static Label unplayedFilterTokenLabel = new Label("Filter Token:");
    public static ChoiceBox<String> unplayedFilterTokenChoices = new ChoiceBox<>();
    public static HBox unplayedChoiceHBox = new HBox(unplayedSortLabel, unplayedSortChoices, unplayedFilterLabel,
            unplayedFilterChoices, unplayedFilterTokenLabel, unplayedFilterTokenChoices);
    public static UnplayedGamesTable unplayedGamesTable = new UnplayedGamesTable();
    public static VBox unplayedGamesVBox = new VBox(unplayedChoiceHBox, unplayedGamesTable);
    public static VBox unplayedWindow = new VBox(topBoxUnplayed, unplayedGamesVBox);

    //Main GUI
    public static final int screenWidthMain = 1500;
    public static final int screenHeightMain = 800;
    public static MenuBar menuBar = new MenuBar(fileMenu, listMenu, randomMenu);
    public static VBox primarySceneVBox = new VBox(menuBar, playedWindow);
    public static Scene primaryScene = new Scene(primarySceneVBox, screenWidthMain, screenHeightMain);

    //Other
    public static ContextMenu rowContextMenu = new ContextMenu(addNewGameMenuItem,                      //Right click menu for each TableView
            editGameMenuItem, moveGameMenuItem, removeGameMenuItem,
            collectGameMenuItem, showGameCollectionsMenuItem);
    public static FileChooser fileChooser = new FileChooser();                                          //FileChooser for opening files
    public static String styleSheet = "style.css";                                                      //Stylesheet for all GUI
    public static Image icon = new Image(                                                               //Icon for each window
            Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png")));
    public static boolean changeMade = false;                                                           //Used to determine whether it is necessary to save the file upon closing the window
    public static boolean playedOpen = true;                                                            //Used to determine which list is open
    public static Path currentFilePathOut = Path.of("List.json").toAbsolutePath();                  //Default saving path
    public static HashMap<String, String> colorMap = new HashMap<>();                                   //Hashmap containing colors for tableview cells
    public static UnaryOperator<TextFormatter.Change> integerFilter = change -> {                                 //Filters if text is not a valid integer
        String input = change.getControlNewText();
        return input.matches("\\d{0,9}") ? change : null;
    };
    public static UnaryOperator<TextFormatter.Change> doubleFilter = change -> {                                  //Filters if text is not a valid double
        String input = change.getControlNewText();
        return input.matches("\\d*\\.\\d*")||input.matches("\\d*") ? change : null;
    };

    //Main method
    public static void main(String[] args) {
        launch(args);
    }

    //Start method
    public void start(Stage primaryStage) {
        //Populate colorMap
        colorMap.put("", "");
        colorMap.put("Playing", "-fx-background-color: #4a8c32;");
        colorMap.put("Completed", "-fx-background-color: #225089;");
        colorMap.put("On Hold", "-fx-background-color: #328c63;");
        colorMap.put("Backlog", "-fx-background-color: #545454;");
        colorMap.put("SubBacklog", "-fx-background-color: #666666;");
        colorMap.put("Wishlist", "-fx-background-color: #993745;");
        colorMap.put("Ignored", "-fx-background-color: #7c211b;");
        colorMap.put("Yes", "-fx-background-color: #4a8c32;");
        colorMap.put("No", "-fx-background-color: #993737;");
        colorMap.put("Maybe", "-fx-background-color: #b59a24;");
        colorMap.put("CURRENTYEAR", "-fx-background-color: #4a8c32;");
        colorMap.put("LASTYEAR", "-fx-background-color: #b59a24;");

        //FileChooser
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName("List");

        //GUI
        topBoxPlayed.setAlignment(Pos.CENTER_LEFT);
        topBoxPlayed.setSpacing(10);
        playedChoiceHBox.setSpacing(5);
        playedChoiceHBox.setPadding(new Insets(2));
        playedGamesVBox.setSpacing(5);
        playedWindow.setSpacing(5);
        topBoxUnplayed.setAlignment(Pos.CENTER_LEFT);
        topBoxUnplayed.setSpacing(10);
        unplayedChoiceHBox.setSpacing(5);
        unplayedChoiceHBox.setPadding(new Insets(2));
        unplayedGamesVBox.setSpacing(5);
        unplayedWindow.setSpacing(5);
        primaryScene.getStylesheets().add(styleSheet);
        primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
        primaryStage.getIcons().add(icon);

        //Populate ChoiceBoxes
        playedSortChoices.getItems().addAll("Status", "Short", "Title", "Franchise", "Rating", "Platform", "Genre",
                "Release Date", "Completion Date", "100%");
        playedFilterChoices.getItems().addAll("Status", "Short", "Franchise", "Rating", "Platform",
                "Genre", "Release Year", "Completion Year", "100%", "Collection", "No Filter");
        unplayedSortChoices.getItems().addAll("Status", "Title", "Franchise", "Platform", "Genre", "Release Date",
                "Hours", "Deck Status");
        unplayedFilterChoices.getItems().addAll("Status", "Franchise", "Platform", "Genre", "Release Year",
                "Deck Status", "Collection", "No Filter");

        //Played ChoiceBox listeners
        playedSortChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem()));
        playedFilterChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            //Set playedFilterTokenChoices according to what is selected in playedFilterChoices

            //Delete and re-make playedFilterTokenChoices
            //This fixes an issue where playedFilterTokenChoices would remember how far down you scrolled after changing filter type
            //For example, Franchise to Status. It would be scrolled past the available options
            int index = playedChoiceHBox.getChildren().indexOf(playedFilterTokenChoices);
            playedChoiceHBox.getChildren().remove(playedFilterTokenChoices);
            playedFilterTokenChoices = new ChoiceBox<>();
            playedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) ->
                    playedGamesTable.sortAndFilter(newValue1));
            playedChoiceHBox.getChildren().add(index, playedFilterTokenChoices);

            switch (playedFilterChoices.getSelectionModel().getSelectedIndex()){
                //Switch for each possible selection of playedFilterChoices
                case 0:
                    //Status
                    playedFilterTokenChoices.getItems().addAll("Playing", "Completed", "On Hold");
                    break;
                case 1:
                case 8:
                    //Short, 100%
                    playedFilterTokenChoices.getItems().addAll("Yes", "No", "Blank");
                    break;
                case 2:
                    //Franchise
                    //Local variables
                    ObservableList<String> franchises = FXCollections.observableArrayList();    //List of all franchises

                    for(PlayedGame game : GameLists.playedList)
                        //Iterate for each PlayedGame
                        if( !game.getFranchise().equals("") && !franchises.contains(game.getFranchise()))
                            //Game has a franchise and it isn't already in the list
                            franchises.add(game.getFranchise());

                    //Sort franchise list
                    Collections.sort(franchises);

                    playedFilterTokenChoices.getItems().addAll(franchises);
                    break;
                case 3:
                    //Rating
                    playedFilterTokenChoices.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
                    break;
                case 4:
                    //Platform
                    playedFilterTokenChoices.getItems().addAll(GameLists.platformList);
                    break;
                case 5:
                    //Genre
                    playedFilterTokenChoices.getItems().addAll(GameLists.genreList);
                    break;
                case 6:
                case 7:
                    //Release Year, Completion Year
                    //Local variables
                    ObservableList<Integer> years = FXCollections.observableArrayList();    //List of all years

                    for(PlayedGame game : GameLists.playedList){
                        //Iterate for each PlayedGame
                        //Local variable
                        int year;   //Release or completion year of each game

                        if (playedFilterChoices.getSelectionModel().getSelectedIndex()==6)
                            //If Release Year is selected
                            year = game.getReleaseYear();
                        else
                            //If Completion Year is selected
                            year = game.getCompletionYear();

                        if(year!=0 && !years.contains((year)))
                            //If the year is not 0 and not already in the list
                            years.add(year);
                    }

                    //Sort year list
                    Collections.sort(years);

                    for(int i : years){
                        //Add each year to playedFilterTokenChoices as a string
                        playedFilterTokenChoices.getItems().add(""+i);
                    }

                    break;
                case 9:
                    //Collection
                    for(GameCollection collection: GameLists.collectionList){
                        //Add each collection title to playedFilterTokenChoices
                        playedFilterTokenChoices.getItems().add(collection.getTitle());
                    }
                    break;
                case 10:
                    //Don't filter
                    //Sort because since there is nothing to select, it won't fire the listener
                    playedGamesTable.sortAndFilter("");
                    break;
            }
            //After items are added, select the first one
            playedFilterTokenChoices.getSelectionModel().selectFirst();
        });
        playedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                playedGamesTable.sortAndFilter(newValue));

        //Unplayed ChoiceBox listeners
        unplayedSortChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem()));
        unplayedFilterChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            //Set unplayedFilterTokenChoices according to what is selected in unplayedFilterChoices

            //Delete and re-make unplayedFilterTokenChoices
            //This fixes an issue where unplayedFilterTokenChoices would remember how far down you scrolled after changing filter type
            //For example, Franchise to Status. It would be scrolled past the available options
            int index = unplayedChoiceHBox.getChildren().indexOf(unplayedFilterTokenChoices);
            unplayedChoiceHBox.getChildren().remove(unplayedFilterTokenChoices);
            unplayedFilterTokenChoices = new ChoiceBox<>();
            unplayedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) ->
                    unplayedGamesTable.sortAndFilter(newValue1));
            unplayedChoiceHBox.getChildren().add(index, unplayedFilterTokenChoices);

            switch (ApplicationGUI.unplayedFilterChoices.getSelectionModel().getSelectedIndex()) {
                //Switch for each possible selection of unplayedFilterChoices
                case 0:
                    //Status
                    unplayedFilterTokenChoices.getItems().addAll("Backlog", "SubBacklog", "Wishlist", "Ignored");
                    break;
                case 1:
                    //Franchise
                    //Local variables
                    ObservableList<String> franchises = FXCollections.observableArrayList();    //List of all franchises

                    for(UnplayedGame game : GameLists.unplayedList)
                        //Iterate for each UnplayedGame
                        if(!game.getFranchise().equals("") && !franchises.contains(game.getFranchise()))
                            //Game has a franchise and it isn't already in the list
                            franchises.add(game.getFranchise());

                    Collections.sort(franchises);
                    unplayedFilterTokenChoices.getItems().addAll(franchises);
                    break;
                case 2:
                    //Platform
                    unplayedFilterTokenChoices.getItems().addAll(GameLists.platformList);
                    break;
                case 3:
                    //Genre
                    unplayedFilterTokenChoices.getItems().addAll(GameLists.genreList);
                    break;
                case 4:
                    //Release Year
                    //Local variables
                    ObservableList<Integer> years = FXCollections.observableArrayList();    //List of all years

                    for(UnplayedGame game : GameLists.unplayedList){
                        //Iterate for each UnplayedGame
                        //Local variables
                        int year = game.getReleaseYear();   //release year of game

                        if(year!=0 && !years.contains((year)))
                            //If year is not 0 and not in years list
                            years.add(year);
                    }

                    //Sort years list
                    Collections.sort(years);

                    for(int i : years)
                        //Add each year to unplayedFilterTokenChoices
                        unplayedFilterTokenChoices.getItems().add(""+i);

                    break;
                case 5:
                    //Deck Status
                    unplayedFilterTokenChoices.getItems().addAll("Yes", "No", "Maybe", "Blank");
                    break;
                case 6:
                    //Collection
                    for(GameCollection collection: GameLists.collectionList){
                        //Add each collection title to playedFilterTokenChoices
                        unplayedFilterTokenChoices.getItems().add(collection.getTitle());
                    }
                    break;
                case 7:
                    //No Filter
                    //Sort because since there is nothing to select, it won't fire the listener
                    unplayedGamesTable.sortAndFilter("");
                    break;
            }
            //After items are added, select the first one
            unplayedFilterTokenChoices.getSelectionModel().selectFirst();
        });
        unplayedFilterTokenChoices.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                unplayedGamesTable.sortAndFilter(newValue));

        //Select the first item in ChoiceBoxes
        playedSortChoices.getSelectionModel().selectFirst();
        playedFilterChoices.getSelectionModel().selectLast();
        unplayedSortChoices.getSelectionModel().selectFirst();
        unplayedFilterChoices.getSelectionModel().selectLast();

        //Add FileMenuItems to each Menu
        fileMenu.getItems().addAll(newFileMenuItem, openFileMenuItem, separatorMenuItem,
                saveFileMenuItem, saveAsFileMenuItem, separatorMenuItem5, exitMenuItem);
        listMenu.getItems().addAll(addNewGameMenuItem, editGameMenuItem, moveGameMenuItem,
                removeGameMenuItem, collectGameMenuItem, showGameCollectionsMenuItem,
                separatorMenuItem3, editGenreListMenuItem, editPlatformListMenuItem,
                separatorMenuItem4, statsMenuItem, collectionsMenuItem,
                achievementsMenuItem);
        randomMenu.getItems().addAll(chooseRandomGameMenuItem, generateRandomListMenuItem);

        newFileMenuItem.setOnAction(e -> {
            //Reset all lists.
            //Local variables
            Stage stage = new Stage();
            Label label = new Label("Are you sure?");
            Label label1 = new Label("Unsaved data will be lost.");
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");
            HBox hbox = new HBox(yesButton, noButton);
            VBox vbox = new VBox(label, label1, hbox);
            Scene scene = new Scene(vbox);

            //GUI
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create New File");
            stage.setScene(scene);
            label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
            yesButton.setStyle("-fx-font-size: 16;");
            noButton.setStyle("-fx-font-size: 16;");
            yesButton.setPrefWidth(80);
            noButton.setPrefWidth(80);
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(30);
            vbox.setPadding(new Insets(10));
            vbox.setSpacing(20);
            vbox.setAlignment(Pos.TOP_CENTER);
            scene.getStylesheets().add(styleSheet);

            yesButton.setOnAction(e1 -> {
                //Clear all lists
                GameLists.playedList.clear();
                GameLists.unplayedList.clear();
                GameLists.genreList.clear();
                GameLists.genreList.add("Action");
                GameLists.platformList.clear();
                GameLists.platformList.add("PC");
                GameLists.collectionList.clear();

                //Reset GUI
                playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
                statusCountBoxPlayed.updateData();
                statusCountBoxUnplayed.updateData();

                //Reset current file
                currentFilePathOut = Path.of("List.json").toAbsolutePath();
                primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());

                stage.close();
            });

            //Close the window without resetting the lists
            noButton.setOnAction(e1 -> stage.close());

            stage.show();
        });

        openFileMenuItem.setOnAction(e -> {
            try {
                //Open a new file with fileChooser
                currentFilePathOut = fileChooser.showOpenDialog(primaryStage).toPath();
                openFile(currentFilePathOut);
                primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
            }catch (NullPointerException ignored){
                //The user closed the fileChooser window
            }
         });

        saveFileMenuItem.setOnAction(e -> {
            try {
                //Saves to the default "List.json" file
                File fileOut = new File(currentFilePathOut.toString());
                saveFile(fileOut);
            } catch (NullPointerException | FileNotFoundException e1) {
                e1.printStackTrace();
            }
        });

        saveAsFileMenuItem.setOnAction(e -> {
            try {
                //Saves to a file chosen by the user.
                currentFilePathOut = fileChooser.showSaveDialog(primaryStage).toPath();
                saveFile(currentFilePathOut.toFile());
                primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
            } catch (NullPointerException | FileNotFoundException ignored) {
                //The user closed the fileChooser window
            }
        });

        exitMenuItem.setOnAction(e -> {
            //Closes the application, prompts the user if they want to save.

            if (changeMade) {
                //If the user has made any changes that should be saved
                //Don't close the window
                e.consume();

                //Local Variables
                Stage stage = new Stage();
                Label label = new Label("Save file?");
                Button saveButton = new Button("Save");
                Button saveAsButton = new Button("Save As");
                Button dontButton = new Button("Don't Save");
                Button cancelButton = new Button("Cancel");
                HBox hbox = new HBox(saveButton, saveAsButton, dontButton, cancelButton);
                VBox vbox = new VBox(label, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setTitle("Save File");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                label.setStyle("-fx-font-size: 24;");
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(5);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(5));
                scene.getStylesheets().add(styleSheet);

                saveButton.setOnAction(e1 -> {
                    //Save to the current file and close
                    saveFileMenuItem.fire();
                    primaryStage.close();
                    stage.close();
                });

                saveAsButton.setOnAction(e1 -> {
                    //Let the user choose a file to save to and close
                    saveAsFileMenuItem.fire();
                    primaryStage.close();
                    stage.close();
                });

                dontButton.setOnAction(e1 -> {
                    //Close the window without saving
                    primaryStage.close();
                    stage.close();
                });

                //Return to the main window
                cancelButton.setOnAction(e1 -> stage.close());

                stage.setScene(scene);
                stage.show();
            } else {
                //If the user has not made any changes, the program doesn't need to save
                primaryStage.close();
            }
        });

        addNewGameMenuItem.setOnAction(e -> {
            //Creates a new game for either the played or unplayed list
            //Local Variables
            Stage stage = new Stage();

            if (playedOpen) {
                //Played Game
                //Local Variables
                PlayedAddWindow playedAddWindow = new PlayedAddWindow(stage);
                Scene scene = new Scene(playedAddWindow);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("Add New Played Game");
                stage.initModality(Modality.APPLICATION_MODAL);
                scene.getStylesheets().add(styleSheet);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        //If escape is pressed, close the window
                        stage.close();
                    } else if (e1.getCode() == KeyCode.ENTER) {
                        //If enter is pressed, save the new game
                            playedAddWindow.saveAndQuit(stage);
                    }
                });

                stage.show();
            } else {
                //Unplayed Game
                //Local variables
                UnplayedAddWindow unplayedAddWindow = new UnplayedAddWindow(stage);
                Scene scene = new Scene(unplayedAddWindow);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("Add New Played Game");
                stage.initModality(Modality.APPLICATION_MODAL);
                scene.getStylesheets().add(styleSheet);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        //If escape is pressed, close the window
                        stage.close();
                    } else if (e1.getCode() == KeyCode.ENTER) {
                        //If enter is pressed, save the new game
                            unplayedAddWindow.saveAndQuit(stage);
                    }
                });

                stage.show();
            }
        });

        editGameMenuItem.setOnAction(e -> {

            //Opens the edit window for the selected game.
            if(playedOpen && playedGamesTable.getSelectionModel().getSelectedIndex()!=-1){
                //PlayedGames table is open, and an item is selected
                    playedGamesTable.editGame(playedGamesTable.getSelectionModel().getSelectedItem());
            }else if(unplayedGamesTable.getSelectionModel().getSelectedIndex()!=-1)
                //UnplayedGames table is open, and an item is selected
                    unplayedGamesTable.editGame(unplayedGamesTable.getSelectionModel().getSelectedItem());
        });

        moveGameMenuItem.setOnAction(e -> {
            //Moves the selected game from the currently open list to the other.

            if (playedOpen && playedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //PlayedGame -> UnplayedGame
                //PlayedGames table is open and a game is selected
                //Local variables
                PlayedGame game = playedGamesTable.getSelectionModel().getSelectedItem();   //Game to be moved
                Stage stage = new Stage();
                Label label = new Label("Move " + game.getTitle());
                Label label1 = new Label("Are you sure?");
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hbox = new HBox(yesButton, noButton);
                VBox vbox = new VBox(label, label1, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Move Played Game");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                yesButton.setStyle("-fx-font-size: 16;");
                yesButton.setPrefWidth(80);
                noButton.setStyle("-fx-font-size: 16;");
                noButton.setPrefWidth(80);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(30);
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setPadding(new Insets(10));
                scene.getStylesheets().add(styleSheet);

                yesButton.setOnAction(e1 -> {
                    //Create an equivalent UnplayedGame and remove the current PlayedGame
                    //Local variables
                    UnplayedGame newGame = new UnplayedGame(game.getTitle(), "Backlog", game.getPlatform(),     //New UnplayedGame
                            game.getGenre(), game.getReleaseYear(), game.getReleaseMonth(), game.getReleaseDay());

                    newGame.setFranchise(game.getFranchise());
                    GameLists.unplayedList.add(newGame);

                    for(GameCollection collection : GameLists.collectionList)
                        //Replace game in each collection with newGame
                        if(collection.getGames().contains(game)) {
                            //If collection contains the game, add newGame and remove game
                            collection.getGames().add(collection.getGames().indexOf(game), newGame);
                            collection.getGames().remove(game);
                        }

                    //Remove game and update both tables
                    GameLists.playedList.remove(game);
                    playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                    unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());

                    stage.close();
                    changeMade = true;
                });

                //Close window
                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            } else if (unplayedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //Unplayed Game -> Played Game
                //UnplayedGames table is open and an Item is selected
                //Local variables
                UnplayedGame game = unplayedGamesTable.getSelectionModel().getSelectedItem();   //Game to be moved
                Stage stage = new Stage();
                Label label = new Label("Move " + game.getTitle());
                Label label1 = new Label("Are you sure?");
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hbox = new HBox(yesButton, noButton);
                VBox vbox = new VBox(label, label1, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Move Unplayed Game");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                yesButton.setStyle("-fx-font-size: 16;");
                yesButton.setPrefWidth(80);
                noButton.setStyle("-fx-font-size: 16;");
                noButton.setPrefWidth(80);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(30);
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setPadding(new Insets(10));
                scene.getStylesheets().add(styleSheet);

                yesButton.setOnAction(e1 -> {
                    //Create an equivalent PlayedGame and remove the current UnplayedGame
                    //Local variables
                    PlayedGame newGame = new PlayedGame(game.getTitle(), "Playing", game.getPlatform(),         //New PlayedGame
                            game.getGenre(), game.getReleaseYear(), game.getReleaseMonth(), game.getReleaseDay());

                    newGame.setFranchise(game.getFranchise());
                    GameLists.playedList.add(newGame);

                    for(GameCollection collection : GameLists.collectionList)
                        //Replace game in each collection with newGame
                        if(collection.getGames().contains(game)) {
                            //If collection contains the game, add newGame and remove game
                            collection.getGames().add(collection.getGames().indexOf(game), newGame);
                            collection.getGames().remove(game);
                        }


                    //Remove game and update both tables
                    GameLists.unplayedList.remove(game);
                    playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                    unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());

                    stage.close();
                    changeMade = true;
                });

                //Close window
                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            }
        });

        removeGameMenuItem.setOnAction(e -> {
            //Removed selected game from the list.

            if (playedOpen && playedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //PlayedGames table is open and a game is selected
                //Local variables
                PlayedGame game = playedGamesTable.getSelectionModel().getSelectedItem();   //Selected game
                Stage stage = new Stage();
                Label label = new Label("Remove " + game.getTitle());
                Label label1 = new Label("Are you sure?");
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hbox = new HBox(yesButton, noButton);
                VBox vbox = new VBox(label, label1, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Remove Played Game");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                yesButton.setStyle("-fx-font-size: 16;");
                yesButton.setPrefWidth(80);
                noButton.setStyle("-fx-font-size: 16;");
                noButton.setPrefWidth(80);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(30);
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setPadding(new Insets(10));
                scene.getStylesheets().add(styleSheet);

                yesButton.setOnAction(e1 -> {
                    //Remove game
                    GameLists.playedList.remove(game);

                    for(GameCollection collection : GameLists.collectionList)
                        //Remove game from each collection
                        collection.getGames().remove(game);

                    statusCountBoxPlayed.updateData();
                    playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                    stage.close();
                    changeMade = true;
                });

                //Close window
                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            } else if (unplayedGamesTable.getSelectionModel().getSelectedIndex() != -1) {
                //UnplayedGames table is open and a game is selected
                //Local variables
                UnplayedGame game = unplayedGamesTable.getSelectionModel().getSelectedItem();   //Selected game
                Stage stage = new Stage();
                Label label = new Label("Remove " + game.getTitle());
                Label label1 = new Label("Are you sure?");
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hbox = new HBox(yesButton, noButton);
                VBox vbox = new VBox(label, label1, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Remove Unplayed Game");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                yesButton.setStyle("-fx-font-size: 16;");
                yesButton.setPrefWidth(80);
                noButton.setStyle("-fx-font-size: 16;");
                noButton.setPrefWidth(80);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(30);
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setPadding(new Insets(10));
                scene.getStylesheets().add(styleSheet);

                yesButton.setOnAction(e1 -> {
                    //Remove game
                    GameLists.unplayedList.remove(game);

                    for(GameCollection collection : GameLists.collectionList)
                        //Remove game from each collection
                        collection.getGames().remove(game);

                    statusCountBoxUnplayed.updateData();
                    unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
                    stage.close();
                    changeMade = true;
                });

                //Close window
                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            }
        });

        collectGameMenuItem.setOnAction(e -> {
            //Add a game to a collection

            if(GameLists.collectionList.isEmpty()) {
                //There are no collections
                //Local variables
                Stage stage = new Stage();
                Label label = new Label("No Collections");
                Scene scene = new Scene(label);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("No Collections");
                stage.setScene(scene);
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-font-size: 24;");
                label.setPadding(new Insets(5));
                scene.getStylesheets().add(styleSheet);

                stage.show();
            }else {
                //There are collections
                //Local variables
                Game game;      //Selected game
                int gameInt;    //Selected game's index

                if (playedOpen) {
                    //PlayedGame
                    //Set game index
                    gameInt = playedGamesTable.getSelectionModel().getSelectedIndex();

                    if (gameInt != -1) {
                        //Game is selected
                        game = playedGamesTable.getSelectionModel().getSelectedItem();
                    } else {
                        //There is no game selected
                        game = null;
                    }

                } else {
                    //UnplayedGame
                    //Set game index
                    gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                    if (gameInt != -1) {
                        //Game is selected
                        game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                    } else {
                        //There is no game selected
                        game = null;
                    }
                }

                if (gameInt != -1) {
                    //Game is selected
                    //Local variables
                    Stage stage = new Stage();
                    Label label = new Label("Add " + game.getTitle() + " to Collection");
                    Label label1 = new Label("Choose Collection");
                    ChoiceBox<GameCollection> collectionChoiceBox = new ChoiceBox<>(GameLists.collectionList);
                    Button addButton = new Button("Add Game");
                    VBox vbox = new VBox(label, label1, collectionChoiceBox, addButton);
                    Scene scene = new Scene(vbox);

                    //GUI
                    stage.getIcons().add(icon);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Select Collection");
                    stage.setScene(scene);
                    label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                    collectionChoiceBox.getSelectionModel().selectFirst();
                    vbox.setSpacing(20);
                    vbox.setAlignment(Pos.TOP_CENTER);
                    vbox.setPadding(new Insets(10));
                    scene.getStylesheets().add(styleSheet);

                    addButton.setOnAction(e1 -> {
                        //Add game to collection
                        collectionChoiceBox.getSelectionModel().getSelectedItem().getGames().add(game);
                        stage.close();
                        changeMade = true;
                    });

                    scene.setOnKeyPressed(e1 -> {
                        if (e1.getCode() == KeyCode.ESCAPE) {
                            //If escape is pressed, close window
                            stage.close();
                        } else if (e1.getCode() == KeyCode.ENTER) {
                            //If enter is pressed, add the game
                            addButton.fire();
                        }
                    });

                    stage.show();
                }
            }
        });

        showGameCollectionsMenuItem.setOnAction(e -> {
            //Open a window showing each collection the selected game is in.
            //Local variables
            Game game;      //Selected game
            int gameInt;    //Selected game's index

            if (playedOpen) {
                //PlayedGame
                //Set game index
                gameInt = playedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    //Game is selected
                    game = playedGamesTable.getSelectionModel().getSelectedItem();
                } else {
                    //There is no game selected
                    game = null;
                }

            } else {
                //UnplayedGame
                //Set game index
                gameInt = unplayedGamesTable.getSelectionModel().getSelectedIndex();

                if (gameInt != -1) {
                    //Game is selected
                    game = unplayedGamesTable.getSelectionModel().getSelectedItem();
                } else {
                    //There is no game selected
                    game = null;
                }
            }

            if (gameInt != -1) {
                //Game is selected
                //Local variables
                Stage stage = new Stage();
                Label label = new Label("Collections Containing " + game.getTitle());
                ListView<GameCollection> collectionListView = new ListView<>();
                VBox vbox = new VBox(label, collectionListView);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Select Collection");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                vbox.setPadding(new Insets(10));
                scene.getStylesheets().add(styleSheet);

                //Populate list of collections
                for(GameCollection collection : GameLists.collectionList)
                    if(collection.getGames().contains(game))
                        collectionListView.getItems().add(collection);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        //If escape is pressed, close window
                        stage.close();
                    }
                });

                stage.show();
            }
        });

        editGenreListMenuItem.setOnAction(e -> {
            //Open a window for the user to edit the genre list.
            //Local variables
            Stage stage = new Stage();
            EditGenreList window = new EditGenreList();
            Scene scene = new Scene(window);

            //GUI
            stage.getIcons().add(icon);
            stage.setTitle("Edit Genre List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            scene.getStylesheets().add(styleSheet);

            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE)
                    //If escape is pressed, close the window
                    stage.close();
                else if (e1.getCode() == KeyCode.ENTER && window.addItemField.isFocused())
                    window.addItemButton.fire();

            });

            stage.show();
        });

        editPlatformListMenuItem.setOnAction(e -> {
            //Open a window for the user to edit the platform list.
            //Local variables
            Stage stage = new Stage();
            EditPlatformList window = new EditPlatformList();
            Scene scene = new Scene(window);

            //GUI
            stage.getIcons().add(icon);
            stage.setTitle("Edit Platform List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            scene.getStylesheets().add(styleSheet);

            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE)
                    stage.close();
                else if (e1.getCode() == KeyCode.ENTER && window.addItemField.isFocused())
                    //If enter is pressed while addItemField is focused, add the inputted platform
                    window.addItemButton.fire();
            });

            stage.show();
        });

        statsMenuItem.setOnAction(e -> {
            //Open the stats view
            //Local variables
            Stage stage = new Stage();
            StatsScreen statsScreen = new StatsScreen(stage);
            Scene scene = new Scene(statsScreen);

            //GUI
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Stats");
            scene.getStylesheets().add(styleSheet);
            stage.setScene(scene);

            stage.show();
        });

        collectionsMenuItem.setOnAction(e -> {
            //Open the collection view
            //Local variables
            Stage stage = new Stage();
            CollectionsWindow collectionsWindow = new CollectionsWindow(stage);
            Scene scene = new Scene(collectionsWindow);

            //GUI
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Collections");
            stage.setScene(scene);
            scene.getStylesheets().add(styleSheet);

            stage.show();
        });

        achievementsMenuItem.setOnAction(e -> {
            //Open the achievements window
            //Local variables
            Stage stage = new Stage();
            AchievementWindow achievementWindow = new AchievementWindow();
            ScrollPane scrollPane = new ScrollPane(achievementWindow);
            Scene scene = new Scene(scrollPane);

            //GUI
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Achievements");
            stage.setHeight(900);
            stage.setScene(scene);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            scene.getStylesheets().add(styleSheet);

            stage.show();
            for(AchievementBox<?> achievementBox : achievementWindow.achievementBoxes)
                //Iterate through each achievement to adjust the size
                if(achievementBox.isBig())
                    //Main achievement
                    achievementBox.labelBox.setMinWidth(564-achievementBox.titleLabel.getBoundsInParent().getWidth());
                else
                    //Other achievements
                    achievementBox.labelBox.setMinWidth(500-achievementBox.titleLabel.getBoundsInParent().getWidth());
        });

        chooseRandomGameMenuItem.setOnAction(e -> {
            //Chooses a random game from the unplayed list with the status "Backlog", or "Subbacklog"
            //Local variables
            ArrayList<String> gameList = new ArrayList<>(); //List of games to choose from

            if(playedOpen){
                //Played games
                for (int i = 0; i < GameLists.playedList.size(); i++)
                    //Iterate through each game
                    if ((GameLists.playedList.get(i)).getStatus().equals("Completed"))
                        //If game is not from wishlist, add it to gameList
                        gameList.add((GameLists.playedList.get(i)).getTitle());
            }else{
                //Unplayed games
                for (int i = 0; i < GameLists.unplayedList.size(); i++)
                    //Iterate through each game
                    if ((GameLists.unplayedList.get(i)).getStatus().equals("Backlog") ||
                            (GameLists.unplayedList.get(i)).getStatus().equals("SubBacklog"))
                        //If game is not from wishlist, add it to gameList
                        gameList.add((GameLists.unplayedList.get(i)).getTitle());
            }

            if (gameList.size() > 0) {
                //If there are any games to choose from
                //Local variables
                Random rand = new Random();
                Stage stage = new Stage();
                Label label = new Label("");
                Button button = new Button("Close");
                VBox vbox = new VBox(label, button);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setTitle("Random Game");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                label.setStyle("-fx-font-size: 16;");
                label.setText(gameList.get(rand.nextInt(gameList.size())));
                button.setOnAction(e1 -> stage.close());
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(5));
                scene.getStylesheets().add(styleSheet);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE || e1.getCode() == KeyCode.ENTER) {
                        //If enter or escape are pressed, exit the window
                        stage.close();
                    }
                });

                stage.show();
            }
        });

        chooseRandomFromList.setOnAction(e -> {
            //Chooses a random game from the temporary list.

            if (unplayedTempList.getTitles().size() > 0) {
                //There are games
                //Local variables
                Random rand = new Random();
                Stage stage = new Stage();
                Label label = new Label("");
                Button button = new Button("Close");
                VBox vbox = new VBox(label, button);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setTitle("Random Game");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                label.setStyle("-fx-font-size: 16;");
                label.setText(unplayedTempList.getTitles().get(rand.nextInt(unplayedTempList.getTitles().size())));
                button.setOnAction(e1 -> stage.close());
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(5));
                scene.getStylesheets().add(styleSheet);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE || e1.getCode() == KeyCode.ENTER) {
                        //If escape or ender are pressed, close the window
                        stage.close();
                    }
                });

                stage.show();
            }
        });

        generateRandomListMenuItem.setOnAction(e -> {
            //Generates a random list of unplayed games based on filters provided by the user.
            //Local variables
            Stage stage = new Stage();
            RandomListGenerator window;
            Scene scene;

            if(playedOpen){
                //Played game list generator
                window = new PlayedRandomListGenerator();
                scene = new Scene(window, 1300, 750);
            }else{
                //Unplayed game list generator
                window = new UnplayedRandomListGenerator();
                scene = new Scene(window, 1000, 750);
            }


            //GUI
            stage.getIcons().add(icon);
            stage.setTitle("Random Game List");
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            scene.getStylesheets().add(styleSheet);

            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE) {
                    //If escape is pressed, close the window
                    stage.close();
                } else if (e1.getCode() == KeyCode.ENTER) {
                    //If enter is pressed, generate a list
                    window.generateButton.fire();
                }
            });

            stage.show();
        });

        switchFromPlayed.setOnAction(e -> {
            //Switch the current window from the played list to the unplayed list.
            //Set scene to unplayed
            primarySceneVBox.getChildren().clear();
            primarySceneVBox.getChildren().addAll(menuBar, unplayedWindow);

            //Set randomMenu items accordingly
            randomMenu.getItems().clear();
            randomMenu.getItems().addAll(chooseRandomGameMenuItem, chooseRandomFromList, generateRandomListMenuItem);
            chooseRandomGameMenuItem.setText("Choose a Random Game to Play");
            playedOpen = false;
        });

        switchFromUnplayed.setOnAction(e -> {
            //Switches the current window from the unplayed list to the played list.
            //Set scene to played
            primarySceneVBox.getChildren().clear();
            primarySceneVBox.getChildren().addAll(menuBar, playedWindow);

            //Set randomMenu items accordingly
            randomMenu.getItems().clear();
            randomMenu.getItems().addAll(chooseRandomGameMenuItem, generateRandomListMenuItem);
            chooseRandomGameMenuItem.setText("Choose a Random Game to Replay");
            playedOpen = true;
        });

        primaryStage.setOnCloseRequest(e -> {
            //Consume the close request and fire the exit button so that the program will ask the user if they want to save their list.
            e.consume();
            exitMenuItem.fire();
        });

        //Set ctrl combos
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
        showGameCollectionsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C,
                KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        //open the default "List.json" file
        openFile(currentFilePathOut);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    //Saves the current data to a given file object.
    public void saveFile(File fileOut) throws FileNotFoundException {
        //Local variables
        JSONObject file = new JSONObject();             //JSONObject containing data
        JSONArray platformListArray = new JSONArray();  //List of all platforms
        JSONArray genreListArray = new JSONArray();     //List of all genres
        JSONArray playedGameArray = new JSONArray();    //List of all PlayedGames
        JSONArray unplayedGameArray = new JSONArray();  //List of all UnplayedGames
        JSONArray collectionArray = new JSONArray();    //List of all Collections
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileOut), StandardCharsets.UTF_8));
        Stage stage = new Stage();
        Label label = new Label("Saving...");
        VBox vbox = new VBox(label);
        Scene scene = new Scene(vbox, 200, 100);
        Button button = new Button("Close");

        //GUI
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Saving...");
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 24;");
        vbox.setAlignment(Pos.CENTER);
        scene.getStylesheets().add(styleSheet);
        stage.setScene(scene);

        //Show this loading stage before saving
        stage.show();

        for (int i = 0; i < GameLists.platformList.size(); i++)
            //Add each platform to the platform list
            platformListArray.put(GameLists.platformList.get(i));

        for (int i = 0; i < GameLists.genreList.size(); i++)
            //Add each genre to the genre list
            genreListArray.put(GameLists.genreList.get(i));

        for (int i = 0; i < GameLists.playedList.size(); i++) {
            //Add each played game to the PlayedGame list
            //Local variables
            JSONObject newGame = new JSONObject();          //The game data to save
            PlayedGame game = GameLists.playedList.get(i);  //The game to save

            try {
                //Put each piece of data in newGame
                newGame.put("S", game.getStatus());
                newGame.put("T", game.getTitle());
                newGame.put("P", game.getPlatform());
                newGame.put("G", game.getGenre());
                newGame.put("RY", game.getReleaseYear());
                newGame.put("RM", game.getReleaseMonth());
                newGame.put("RD", game.getReleaseDay());
                newGame.put("F", game.getFranchise());
                newGame.put("R", game.getRating());
                newGame.put("SS", game.getShortStatus());
                newGame.put("CY", game.getCompletionYear());
                newGame.put("CM", game.getCompletionMonth());
                newGame.put("CD", game.getCompletionDay());
                newGame.put("1", game.getPercent100());

                //Put newGame into the total array
                playedGameArray.put(newGame);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        for (int i = 0; i < GameLists.unplayedList.size(); i++) {
            //Add each played game to the UnplayedGame list
            //Local variables
            JSONObject newGame = new JSONObject();              //The game data to save
            UnplayedGame game = GameLists.unplayedList.get(i);  //The game to save

            try {
                //Put each piece of data in newGame
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

                //Put newGame into the total array
                unplayedGameArray.put(newGame);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        for (int i = 0; i < GameLists.collectionList.size(); i++){
            //Add each collection to the collection list
            //Local varaibles
            JSONObject newCollection = new JSONObject();                    //The collection data to save
            GameCollection collection = GameLists.collectionList.get(i);    //The collection to save
            JSONArray games = new JSONArray();                              //List of each game ID

            try {
                //Put the collection title
                newCollection.put("CT", collection.getTitle());

                for(int j = 0; j < collection.getGames().size(); j++){
                    //Add each game from the collection to games
                    //Local variables
                    Game game = collection.getGames().get(j);   //The game to save
                    String gameID = "";                         //The gameID to write

                    if(game instanceof PlayedGame){
                        //If game is a PlayedGame, start its ID with p and get its index from playedList
                        gameID = "p" + GameLists.playedList.indexOf(game);
                    }else if(game instanceof UnplayedGame){
                        //If game is an UnplayedGame, start its ID with u and get its index from unplayedList
                        gameID = "u" + GameLists.unplayedList.indexOf(game);
                    }

                    //Put the game in the array
                    games.put(gameID);
                }

                //Put the collection in the collection list
                newCollection.put("CG", games);
                collectionArray.put(newCollection);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        //Put each list in the JSONObject
        file.put("PL", platformListArray);
        file.put("GL", genreListArray);
        file.put("P", playedGameArray);
        file.put("U", unplayedGameArray);
        file.put("C", collectionArray);

        //Write the JSONObject to the file and close the PrintWriter
        pw.write(file.toString());
        pw.flush();
        pw.close();

        //Change window to finished after saving
        label.setText("File Saved.");
        vbox.getChildren().add(button);

        changeMade = false;
        button.setOnAction(e1 -> stage.close());
    }

    //Opens a file from the given file path
    public void openFile(Path filePath) {
        try {
            //Local variables
            String jsonString = Files.readString(filePath);             //String to parse
            JSONObject file = new JSONObject(jsonString);               //String as a JSONObject
            JSONArray platformList = file.getJSONArray("PL");       //List of platforms in the file
            JSONArray genreList = file.getJSONArray("GL");          //List  of genres in the file
            JSONArray playedGameList = file.getJSONArray("P");      //list of PlayedGames in the file
            JSONArray unplayedGameList = file.getJSONArray("U");    //List of UnplayedGames in the file
            JSONArray collectionArray = file.getJSONArray("C");     //List of collections in the file

            //Reset all lists before setting new data
            GameLists.platformList = FXCollections.observableArrayList();
            GameLists.genreList = FXCollections.observableArrayList();
            GameLists.playedList = FXCollections.observableArrayList();
            GameLists.unplayedList = FXCollections.observableArrayList();
            GameLists.collectionList = FXCollections.observableArrayList();

            for (int i = 0; i < platformList.length(); i++)
                //Add each platform
                GameLists.platformList.add((String) platformList.get(i));

            for (int i = 0; i < genreList.length(); i++)
                //Add each genre
                GameLists.genreList.add((String) genreList.get(i));

            for (int i = 0; i < playedGameList.length(); i++) {
                //Add each PlayedGame
                //Local variables
                JSONObject newObj = (JSONObject) playedGameList.get(i);                                 //Game data from the JSON file
                PlayedGame newGame = new PlayedGame((String) newObj.get("T"), (String) newObj.get("S"), //New game to add to the list
                        (String) newObj.get("P"), (String) newObj.get("G"), (int) newObj.get("RY"),
                        (int) newObj.get("RM"), (int) newObj.get("RD"));

                //Add data to game
                newGame.setCompletionYear((int) newObj.get("CY"));
                newGame.setCompletionMonth((int) newObj.get("CM"));
                newGame.setCompletionDay((int) newObj.get("CD"));
                newGame.setShortStatus((String) newObj.get("SS"));
                newGame.setRating((int) newObj.get("R"));
                newGame.setPercent100((String) newObj.get("1"));
                newGame.setFranchise((String) newObj.get("F"));

                //Add game to list
                GameLists.playedList.add(newGame);
            }

            for (int i = 0; i < unplayedGameList.length(); i++) {
                //Add each PlayedGame
                //Local variables
                JSONObject newObj = (JSONObject) unplayedGameList.get(i);                                   //Game data from the JSON file
                UnplayedGame newGame = new UnplayedGame((String) newObj.get("T"), (String) newObj.get("S"), //New game to add to the list
                        (String) newObj.get("P"), (String) newObj.get("G"), (int) newObj.get("RY"),
                        (int) newObj.get("RM"), (int) newObj.get("RD"));

                //Add data to game,
                newGame.setFranchise((String) newObj.get("F"));
                newGame.setDeckCompatible((String) newObj.get("D"));

                try {
                    //If hours value looks like a decimal
                    newGame.setHours(((BigDecimal) newObj.get("H")).doubleValue());
                } catch (ClassCastException e2) {
                    //If hours value looks like an int
                    newGame.setHours((int) newObj.get("H"));
                }

                //Add game to list
                GameLists.unplayedList.add(newGame);
            }

            for(int i = 0; i < collectionArray.length(); i++){
                //Add each collection
                //Local variables
                JSONObject newObj = (JSONObject) collectionArray.get(i);                        //Collection data from the JSON file
                GameCollection newCollection = new GameCollection((String) newObj.get("CT"));   //New collection to add to the list
                JSONArray collectionGameArray = newObj.getJSONArray("CG");                  //Array of games for the new collection

                for(int j = 0; j < collectionGameArray.length(); j++){
                    //Add each game from collectionGameArray to newCollection
                    //Local variables
                    String gameID = (String) collectionGameArray.get(j);            //ID of game
                    int gameIndex = Integer.parseInt(gameID.substring(1));  //index of game

                    if(gameID.charAt(0)=='p')
                        //If game is a playedGame, add it to played list
                        newCollection.getGames().add(GameLists.playedList.get(gameIndex));
                    else if(gameID.charAt(0)=='u')
                        //If game is an unplayedGame, add it to unplayed list
                        newCollection.getGames().add(GameLists.unplayedList.get(gameIndex));
                }

                //Add collection to list
                GameLists.collectionList.add(newCollection);
            }

            //Update GUI
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