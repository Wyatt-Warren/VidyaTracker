package com.example.vidyatracker11;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.image.ImageView;
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
    //Date
    private static final Date date = new Date();
    private static final ZoneId timeZone = ZoneId.systemDefault();
    public static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();                         //Used to get current year

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
    public static MenuItem addTempListMenuItem = new MenuItem("Add Selected Game to Temp List");
    public static MenuItem collectGameMenuItem = new MenuItem("Add Selected Game to Collection");
    public static MenuItem showGameCollectionsMenuItem = new MenuItem("Show Collections Containing Selected Game");
    public static SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
    public static MenuItem editGenreListMenuItem = new MenuItem("Edit Genre List");
    public static MenuItem editPlatformListMenuItem = new MenuItem("Edit Platform List");
    public static Menu listMenu = new Menu("List");

    //Random Menu
    public static MenuItem chooseRandomGameMenuItem = new MenuItem("Choose a Random Game to Replay");
    public static MenuItem generateRandomListMenuItem = new MenuItem("Generate a Random List of Games Based on Filters");
    public static MenuItem chooseRandomFromList = new MenuItem("Choose a Random Game From the Temp List");
    public static Menu randomMenu = new Menu("Random");

    //Misc Menu
    public static MenuItem monthSummaryMenuItem = new MenuItem("Show Month Summary");
    public static MenuItem yearSummaryMenuItem = new MenuItem("Show Year Summary");
    public static SeparatorMenuItem separatorMenuItem6 =  new SeparatorMenuItem();
    public static MenuItem statsMenuItem = new MenuItem("Show Stats Window");
    public static MenuItem collectionsMenuItem = new MenuItem("Show Collections Window");
    public static MenuItem achievementsMenuItem = new MenuItem("Show Achievements Window");
    public static SeparatorMenuItem separatorMenuItem7 = new SeparatorMenuItem();
    public static MenuItem playedGoalsMenuItem = new MenuItem("Show Played Game Goals");
    public static MenuItem unplayedGoalsMenuItem = new MenuItem("Show Unplayed Game Goals");
    public static Menu miscMenu = new Menu("Misc");

    //Played Window
    public static StatusCountBoxPlayed statusCountBoxPlayed = new StatusCountBoxPlayed();
    public static PlayedTempList playedTempList = new PlayedTempList();
    public static Button switchFromPlayed = new Button("Show Unplayed List");
    public static HBox topBoxPlayed = new HBox(statusCountBoxPlayed, playedTempList, switchFromPlayed);
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
    public static MenuBar menuBar = new MenuBar(fileMenu, listMenu, randomMenu, miscMenu);
    public static VBox primarySceneVBox = new VBox(menuBar, playedWindow);
    public static Scene primaryScene = new Scene(primarySceneVBox, screenWidthMain, screenHeightMain);
    public static Stage primaryStage;

    //Other
    public static ContextMenu rowContextMenu = new ContextMenu(addNewGameMenuItem,                      //Right click menu for each TableView
            editGameMenuItem, moveGameMenuItem, removeGameMenuItem,
            addTempListMenuItem, collectGameMenuItem, showGameCollectionsMenuItem);
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
    public void start(Stage stage1) {
        primaryStage = stage1;

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
        HBox.setHgrow(topBoxPlayed, javafx.scene.layout.Priority.ALWAYS);
        playedChoiceHBox.setSpacing(5);
        playedChoiceHBox.setPadding(new Insets(2));
        playedGamesVBox.setSpacing(5);
        playedWindow.setSpacing(5);
        topBoxUnplayed.setAlignment(Pos.CENTER_LEFT);
        topBoxUnplayed.setSpacing(10);
        HBox.setHgrow(topBoxUnplayed, javafx.scene.layout.Priority.ALWAYS);
        unplayedChoiceHBox.setSpacing(5);
        unplayedChoiceHBox.setPadding(new Insets(2));
        unplayedGamesVBox.setSpacing(5);
        unplayedWindow.setSpacing(5);
        primaryScene.getStylesheets().add(styleSheet);
        setStageTitle();
        primaryStage.getIcons().add(icon);

        //Corner Image
        try {
            Image image = new Image("file:image.png");

            ImageView imageViewPlayed = new ImageView(image);
            ImageView imageViewUnplayed = new ImageView(image);
            imageViewPlayed.setFitHeight(200);
            imageViewUnplayed.setFitHeight(200);
            imageViewPlayed.setPreserveRatio(true);
            imageViewUnplayed.setPreserveRatio(true);

            HBox imageViewPlayedBox = new HBox(imageViewPlayed);
            HBox imageViewUnplayedBox = new HBox(imageViewUnplayed);
            imageViewPlayedBox.setAlignment(Pos.CENTER);
            imageViewUnplayedBox.setAlignment(Pos.CENTER);
            HBox.setHgrow(imageViewPlayedBox, javafx.scene.layout.Priority.ALWAYS);
            HBox.setHgrow(imageViewUnplayedBox, javafx.scene.layout.Priority.ALWAYS);
            imageViewPlayedBox.setPadding(new Insets(5));
            imageViewUnplayedBox.setPadding(new Insets(5));

            topBoxPlayed.getChildren().add(imageViewPlayedBox);
            topBoxUnplayed.getChildren().add(imageViewUnplayedBox);
        }catch (IllegalArgumentException ignored){
            //Only if the user has placed an image called image.png in the folder with the jar
        }

        //Populate ChoiceBoxes
        playedSortChoices.getItems().addAll("Status", "Short", "Title", "Franchise", "Rating", "Platform", "Genre",
                "Release Date", "Completion Date", "100%");
        playedFilterChoices.getItems().addAll("Status", "Short", "Franchise", "Rating", "Platform",
                "Genre", "Release Year", "Completion Year", "100%", "Collection", "No Filter");
        unplayedSortChoices.getItems().addAll("Status", "Title", "Franchise", "Platform", "Genre", "Release Date",
                "Date Added", "Hours", "Deck Status");
        unplayedFilterChoices.getItems().addAll("Status", "Franchise", "Platform", "Genre", "Release Year",
                "Year Added", "Deck Status", "Collection", "No Filter");

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
                case 5:
                    //Release Year
                    //Local variables
                    ObservableList<Integer> years = FXCollections.observableArrayList();    //List of all years

                    for(UnplayedGame game : GameLists.unplayedList){
                        //Iterate for each UnplayedGame
                        //Local variables
                        int year;   //release year of game

                        if(unplayedFilterChoices.getSelectionModel().getSelectedIndex() == 4)
                            //Release year is selected
                            year = game.getReleaseYear();
                        else
                            //Added year is selected
                            year = game.getAddedYear();

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
                case 6:
                    //Deck Status
                    unplayedFilterTokenChoices.getItems().addAll("Yes", "No", "Maybe", "Blank");
                    break;
                case 7:
                    //Collection
                    for(GameCollection collection: GameLists.collectionList){
                        //Add each collection title to playedFilterTokenChoices
                        unplayedFilterTokenChoices.getItems().add(collection.getTitle());
                    }
                    break;
                case 8:
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
                removeGameMenuItem, addTempListMenuItem, collectGameMenuItem,
                showGameCollectionsMenuItem, separatorMenuItem3, editGenreListMenuItem,
                editPlatformListMenuItem);
        randomMenu.getItems().addAll(chooseRandomGameMenuItem, chooseRandomFromList, generateRandomListMenuItem);
        miscMenu.getItems().addAll(monthSummaryMenuItem, yearSummaryMenuItem, separatorMenuItem6,
                statsMenuItem, collectionsMenuItem, achievementsMenuItem, separatorMenuItem7, playedGoalsMenuItem,
                unplayedGoalsMenuItem);

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
                changeMade = true;
                setStageTitle();

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
                changeMade = false;
                setStageTitle();
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
                setStageTitle();
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
                    setStageTitle();
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
                    setStageTitle();
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
                    setStageTitle();
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
                    setStageTitle();
                });

                //Close window
                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            }
        });

        addTempListMenuItem.setOnAction(e -> {
            //Add a game to the temp list
            if(playedOpen)
                playedTempList.addButton.fire();
            else
                unplayedTempList.addButton.fire();
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
                        //Local variables
                        GameCollection collection = collectionChoiceBox.getSelectionModel().getSelectedItem();  //Selected collection

                        if(collection.getGames().contains(game)){
                            //If collection contains game, ask user whether they still want to add it.
                            //Local variables
                            Stage stage2 = new Stage();
                            Label label2 = new Label(collection.getTitle() + " already contains " + game.getTitle() + ".");
                            Label label3 = new Label("Add anyway?");
                            Button yesButton = new Button("Yes");
                            Button noButton = new Button("No");
                            HBox buttonBox = new HBox(yesButton, noButton);
                            VBox vbox1 = new VBox(label2, label3, buttonBox);
                            Scene scene1 = new Scene(vbox1);

                            //GUI
                            stage2.getIcons().add(icon);
                            stage2.setResizable(false);
                            stage2.initModality(Modality.APPLICATION_MODAL);
                            stage2.setTitle("Add Game?");
                            stage2.setScene(scene1);
                            label2.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                            buttonBox.setSpacing(5);
                            buttonBox.setAlignment(Pos.TOP_CENTER);
                            vbox1.setSpacing(20);
                            vbox1.setAlignment(Pos.TOP_CENTER);
                            vbox1.setPadding(new Insets(10));
                            scene1.getStylesheets().add(styleSheet);

                            yesButton.setOnAction(e2 -> {
                                //Add game to collection
                                collection.getGames().add(game);
                                changeMade = true;
                                setStageTitle();
                                stage2.close();
                                stage.close();
                            });

                            noButton.setOnAction(e3 -> stage2.close());

                            scene1.setOnKeyPressed(e4 -> {
                                if (e4.getCode() == KeyCode.ESCAPE) {
                                    //If escape is pressed, close window
                                    stage2.close();
                                } else if (e4.getCode() == KeyCode.ENTER) {
                                    //If enter is pressed, add the game
                                    yesButton.fire();
                                }
                            });

                            stage2.show();

                        }else{
                            //Add game to collection
                            collection.getGames().add(game);
                            changeMade = true;
                            setStageTitle();
                            stage.close();
                        }
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
            if (playedOpen){
                //playedTempList
                if (playedTempList.getGames().size() > 0) {
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
                    label.setText(playedTempList.getGames().get(rand.nextInt(playedTempList.getGames().size())).getTitle());
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
            }else {
                //unplayedTempList
                if (unplayedTempList.getGames().size() > 0) {
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
                    label.setText(unplayedTempList.getGames().get(rand.nextInt(unplayedTempList.getGames().size())).getTitle());
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

        monthSummaryMenuItem.setOnAction(e -> {
            //Open month summary view
            //Local variables
            boolean monthsExist = false;    //True if there are games with completion year and month.

            for(PlayedGame game : GameLists.playedList)
                if(game.getCompletionYear() != 0 && game.getCompletionMonth() != 0) {
                    monthsExist = true;
                    break;
                }

            Stage stage = new Stage();

            if(monthsExist) {
                //There are games with completion year and month.
                //Local variables
                MonthSummary monthSummary;
                Scene scene;
                int currentYear = localDate.getYear();
                int lastMonth = localDate.getMonthValue() - 1;

                if (lastMonth == 0) {
                    //Loop from january to previous year december
                    lastMonth = 12;
                    currentYear--;
                }

                //GUI
                monthSummary = new MonthSummary(new SpecificMonth(lastMonth, currentYear), stage);
                scene = new Scene(monthSummary);
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Month Summary");
                stage.setHeight(screenHeightMain);
                stage.setWidth(1000);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        //If escape is pressed, close window
                        stage.close();
                    }
                });

            }else{
                //There are no games with completion month and year
                //Local Variables
                Label label = new Label("");
                Button button = new Button("Close");
                VBox vbox = new VBox(label, button);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setTitle("Month Summary");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                label.setStyle("-fx-font-size: 16;");
                label.setText("There are no games with completion months.");
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
            }
            stage.show();
        });

        yearSummaryMenuItem.setOnAction(e -> {
            //Open year summary view
            //Local vairables
            boolean yearsExist = false; //There are games with completion years

            for(PlayedGame game : GameLists.playedList)
                if(game.getCompletionYear() != 0){
                    yearsExist = true;
                    break;
                }

            Stage stage = new Stage();

            if(yearsExist) {
                //There are games with completion year.
                //Local variables
                YearSummary yearSummary = new YearSummary(localDate.getYear() - 1, stage);
                Scene scene = new Scene(yearSummary);

                //GUI
                stage.getIcons().add(icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Year Summary");
                stage.setHeight(screenHeightMain);
                stage.setWidth(1000);
                scene.getStylesheets().add(styleSheet);
                stage.setScene(scene);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE) {
                        //If escape is pressed, close window
                        stage.close();
                    }
                });

            }else{
                //There are no games with completion year
                //Local Variables
                Label label = new Label("");
                Button button = new Button("Close");
                VBox vbox = new VBox(label, button);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(icon);
                stage.setTitle("Year Summary");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                label.setStyle("-fx-font-size: 16;");
                label.setText("There are no games with completion years.");
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
            }
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
            stage.setHeight(screenHeightMain);
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

        playedGoalsMenuItem.setOnAction(e -> {
            //Open the goal window for played game goals
            Stage stage = new Stage();
            PlayedGoalWindow playedGoalWindow = new PlayedGoalWindow();
            Scene scene = new Scene(playedGoalWindow);

            //GUI
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Played Game Goals");
            stage.setHeight(screenHeightMain / 1.5);
            stage.setScene(scene);
            scene.getStylesheets().add(styleSheet);
            stage.show();
        });

        unplayedGoalsMenuItem.setOnAction(e -> {
            //Open the goal window for played game goals
            Stage stage = new Stage();
            UnplayedGoalWindow unplayedGoalWindow = new UnplayedGoalWindow();
            Scene scene = new Scene(unplayedGoalWindow);

            //GUI
            stage.getIcons().add(icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Unplayed Game Goals");
            stage.setHeight(screenHeightMain / 1.5);
            stage.setScene(scene);
            scene.getStylesheets().add(styleSheet);
            stage.show();
        });

        switchFromPlayed.setOnAction(e -> {
            //Switch the current window from the played list to the unplayed list.
            //Set scene to unplayed
            primarySceneVBox.getChildren().clear();
            primarySceneVBox.getChildren().addAll(menuBar, unplayedWindow);

            //Set chooseRandomGameMenuItem text accordingly
            chooseRandomGameMenuItem.setText("Choose a Random Game to Play");
            playedOpen = false;
        });

        switchFromUnplayed.setOnAction(e -> {
            //Switches the current window from the unplayed list to the played list.
            //Set scene to played
            primarySceneVBox.getChildren().clear();
            primarySceneVBox.getChildren().addAll(menuBar, playedWindow);

            //Set chooseRandomGameMenuItem text accordingly
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
        addTempListMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.T,
                KeyCombination.CONTROL_DOWN));
        collectGameMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C,
                KeyCombination.CONTROL_DOWN));
        showGameCollectionsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C,
                KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        //open the default "List.json" file
        openFile(currentFilePathOut);

        //Find unplayed goals that just ended and set their end progress value
        for(UnplayedGameGoal goal : GameLists.unplayedGoalList)
            //For every unplayed goal
            if(LocalDate.of(goal.getEndYear(), goal.getEndMonth(), goal.getEndDay()).isBefore(localDate) &&
            goal.getEndProgress() == -1)
                //If goal is past end date and end progress is not yet set, set end progress to current progress.
                goal.setEndProgress(goal.getFilter().filteredList().size());

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public static void setStageTitle(){
        if(changeMade)
            primaryStage.setTitle("Vidya Tracker - *" + currentFilePathOut.getFileName().toString());
        else
            primaryStage.setTitle("Vidya Tracker - " + currentFilePathOut.getFileName().toString());
    }

    //Saves the current data to a given file object.
    public static void saveFile(File fileOut) throws FileNotFoundException {
        //Local variables
        JSONObject file = new JSONObject();             //JSONObject containing data
        JSONArray platformListArray = new JSONArray();  //List of all platforms
        JSONArray genreListArray = new JSONArray();     //List of all genres
        JSONArray playedGameArray = new JSONArray();    //List of all PlayedGames
        JSONArray unplayedGameArray = new JSONArray();  //List of all UnplayedGames
        JSONArray collectionArray = new JSONArray();    //List of all Collections
        JSONArray playedTempArray = new JSONArray();    //List of games in playedTempList
        JSONArray unplayedTempArray = new JSONArray();  //List of games in unplayedTempList
        JSONArray playedGoalArray = new JSONArray();    //List of add played goals
        JSONArray unplayedGoalArray = new JSONArray();  //List of add played goals
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

        file.put("PL", platformListArray);

        for (int i = 0; i < GameLists.genreList.size(); i++)
            //Add each genre to the genre list
            genreListArray.put(GameLists.genreList.get(i));

        file.put("GL", genreListArray);

        if(GameLists.playedList.size() != 0) {
            //If there are played games, put played list
            for (int i = 0; i < GameLists.playedList.size(); i++) {
                //Add each played game to the PlayedGame list
                //Local variables
                JSONObject newGame = new JSONObject();          //The game data to save
                PlayedGame game = GameLists.playedList.get(i);  //The game to save

                try {
                    //Put status
                    newGame.put("S", game.getStatus());

                    if (!game.getTitle().equals(""))
                        //If game has title, put title
                        newGame.put("T", game.getTitle());

                    //Put platform and genre
                    newGame.put("P", game.getPlatform());
                    newGame.put("G", game.getGenre());

                    if (game.getReleaseYear() != 0)
                        //If game has release year, put release year
                        newGame.put("RY", game.getReleaseYear());

                    if (game.getReleaseMonth() != 0)
                        //If game has release month, put release month
                        newGame.put("RM", game.getReleaseMonth());

                    if (game.getReleaseDay() != 0)
                        //If game has release day, put release day
                        newGame.put("RD", game.getReleaseDay());

                    if (!game.getFranchise().equals(""))
                        //If game has franchise, put franchise
                        newGame.put("F", game.getFranchise());

                    if (game.getRating() != 0)
                        //If game has rating, put rating
                        newGame.put("R", game.getRating());

                    if (!game.getShortStatus().equals(""))
                        //If game has short status, put short status
                        newGame.put("SS", game.getShortStatus());

                    if (game.getCompletionYear() != 0)
                        //If game has completion year, put completion year
                        newGame.put("CY", game.getCompletionYear());

                    if (game.getCompletionMonth() != 0)
                        //If game has completion month, put completion month
                        newGame.put("CM", game.getCompletionMonth());

                    if (game.getCompletionDay() != 0)
                        //If game has completion day, put completion day
                        newGame.put("CD", game.getCompletionDay());

                    if (!game.getPercent100().equals(""))
                        //If game has 100% status, put 100% status
                        newGame.put("1", game.getPercent100());

                    //Put newGame into the total array
                    playedGameArray.put(newGame);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            file.put("P", playedGameArray);
        }

        if(GameLists.unplayedList.size() !=  0) {
            for (int i = 0; i < GameLists.unplayedList.size(); i++) {
                //Add each played game to the UnplayedGame list
                //Local variables
                JSONObject newGame = new JSONObject();              //The game data to save
                UnplayedGame game = GameLists.unplayedList.get(i);  //The game to save

                try {
                    //Put each piece of data in newGame
                    //Put status
                    newGame.put("S", game.getStatus());

                    if (!game.getTitle().equals(""))
                        //If game has title, put title
                        newGame.put("T", game.getTitle());

                    //Put platform and genre
                    newGame.put("P", game.getPlatform());
                    newGame.put("G", game.getGenre());

                    if (game.getReleaseYear() != 0)
                        //If game has release year, put release year
                        newGame.put("RY", game.getReleaseYear());

                    if (game.getReleaseMonth() != 0)
                        //If game has release month, put release month
                        newGame.put("RM", game.getReleaseMonth());

                    if (game.getReleaseDay() != 0)
                        //If game has release day, put release day
                        newGame.put("RD", game.getReleaseDay());

                    if(game.getAddedYear() != 0)
                        //If game has added year, put added year
                        newGame.put("AY", game.getAddedYear());

                    if(game.getAddedMonth() != 0)
                        //If game has added month, put added month
                        newGame.put("AM", game.getAddedMonth());

                    if(game.getAddedDay() != 0)
                        //If game has added day, put added day
                        newGame.put("AD", game.getAddedDay());

                    if (!game.getFranchise().equals(""))
                        //If game has franchise, put franchise
                        newGame.put("F", game.getFranchise());

                    if (game.getHours() != 0)
                        //If game has hours, put hours
                        newGame.put("H", game.getHours());

                    if (!game.getDeckCompatible().equals(""))
                        //If game has deck status, put deck status
                        newGame.put("D", game.getDeckCompatible());

                    //Put newGame into the total array
                    unplayedGameArray.put(newGame);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            file.put("U", unplayedGameArray);
        }

        if(GameLists.collectionList.size() != 0) {
            //If there are collections, save collections
            for (int i = 0; i < GameLists.collectionList.size(); i++) {
                //Add each collection to the collection list
                //Local variables
                JSONObject newCollection = new JSONObject();                    //The collection data to save
                GameCollection collection = GameLists.collectionList.get(i);    //The collection to save
                JSONArray games = new JSONArray();                              //List of each game ID

                try {
                    //Put the collection title
                    newCollection.put("CT", collection.getTitle());

                    for (int j = 0; j < collection.getGames().size(); j++) {
                        //Add each game from the collection to games
                        //Local variables
                        Game game = collection.getGames().get(j);   //The game to save
                        String gameID = "";                         //The gameID to write

                        if (game instanceof PlayedGame) {
                            //If game is a PlayedGame, start its ID with p and get its index from playedList
                            gameID = "p" + GameLists.playedList.indexOf(game);
                        } else if (game instanceof UnplayedGame) {
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

            file.put("C", collectionArray);
        }

        if(playedTempList.getGames().size() != 0) {
            //If there are games in played temp list, save played temp list
            for (int i = 0; i < playedTempList.getGames().size(); i++) {
                //Add each game to playedTempArray
                //Local variables
                PlayedGame game = playedTempList.getGames().get(i); //Game to save

                //Put game in array
                playedTempArray.put(GameLists.playedList.indexOf(game));
            }

            file.put("PT", playedTempArray);
        }

        if(unplayedTempList.getGames().size() != 0) {
            //If there are games in unplayed temp list, save unplayed temp list
            for (int i = 0; i < unplayedTempList.getGames().size(); i++) {
                //Add each game to unplayedTempArray
                //Local variables
                UnplayedGame game = unplayedTempList.getGames().get(i); //Game to save

                //Put game in array
                unplayedTempArray.put(GameLists.unplayedList.indexOf(game));
            }

            file.put("UT", unplayedTempArray);
        }

        if(GameLists.playedGoalList.size() != 0) {
            //If there are played game goals, save goals

            for (int i = 0; i < GameLists.playedGoalList.size(); i++) {
                //Add each played goal to playedGoalArray
                //Local variables
                JSONObject newGoal = new JSONObject();
                JSONObject filters = new JSONObject();
                PlayedGameGoal goal = GameLists.playedGoalList.get(i);

                if (!goal.getTitle().equals(""))
                    //If goal has title, save title
                    newGoal.put("GT", goal.getTitle());

                newGoal.put("SY", goal.getStartYear());
                newGoal.put("SM", goal.getStartMonth());
                newGoal.put("SD", goal.getStartDay());
                newGoal.put("EY", goal.getEndYear());
                newGoal.put("EM", goal.getEndMonth());
                newGoal.put("ED", goal.getEndDay());

                if (goal.getStartProgress() != 0)
                    //If there is start progress, put start progress
                    newGoal.put("SP", goal.getStartProgress());

                if (goal.getGoalProgress() != 0)
                    //If there is end progress, put end progress
                    newGoal.put("GP", goal.getGoalProgress());

                if (!goal.getFilter().getPossibleStatuses().isEmpty()) {
                    //If there are statuses in filter, save statuses
                    //Local variables
                    JSONArray statuses = new JSONArray();

                    for (String status : goal.getFilter().getPossibleStatuses())
                        //Loop for each status
                        statuses.put(status);

                    filters.put("S", statuses);
                }

                if (!goal.getFilter().getTitleContains().equals(""))
                    //If there is title in filter, save title
                    filters.put("T", goal.getFilter().getTitleContains());

                if (!goal.getFilter().getPossibleFranchises().isEmpty()) {
                    //If there are franchises in filter, save franchises
                    //Local variables
                    JSONArray franchises = new JSONArray();

                    for (String franchise : goal.getFilter().getPossibleFranchises())
                        //Loop for each franchise
                        franchises.put(franchise);

                    filters.put("F", franchises);
                }

                if (!goal.getFilter().getPossiblePlatforms().isEmpty()) {
                    //If there are platforms in filter, save platforms
                    //Local variables
                    JSONArray platforms = new JSONArray();

                    for (String platform : goal.getFilter().getPossiblePlatforms())
                        //Loop for each platform
                        platforms.put(platform);

                    filters.put("P", platforms);
                }

                if (!goal.getFilter().getPossibleGenres().isEmpty()) {
                    //If there are genres in filter, save genres
                    //Local variables
                    JSONArray genres = new JSONArray();

                    for (String genre : goal.getFilter().getPossibleGenres())
                        //Loop for each genre
                        genres.put(genre);

                    filters.put("G", genres);
                }

                if(goal.getFilter().getMinReleaseYear() != 0)
                    //If there is minimum release year, put minimum release year
                    filters.put("LY", goal.getFilter().getMinReleaseYear());

                if(goal.getFilter().getMaxReleaseYear() != Integer.MAX_VALUE)
                    //If there is maximum release year, put maximum release year
                    filters.put("HY", goal.getFilter().getMaxReleaseYear());

                if (!goal.getFilter().getPossibleCollections().isEmpty()) {
                    //If there are collections in filter, save collections
                    //Local variables
                    JSONArray collections = new JSONArray();

                    for (GameCollection collection : goal.getFilter().getPossibleCollections())
                        //Loop for each collection
                        collections.put(collection.getTitle());

                    filters.put("C", collections);
                }

                if (!goal.getFilter().getPossibleShortStatuses().isEmpty()) {
                    //If there are short statuses in filter, save short statuses
                    //Local variables
                    JSONArray shortStatuses = new JSONArray();

                    for (String shortStatus : goal.getFilter().getPossibleShortStatuses())
                        //Loop for each short status
                        shortStatuses.put(shortStatus);

                    filters.put("SS", shortStatuses);
                }

                if (!goal.getFilter().getPossibleRatings().isEmpty()) {
                    //If there are ratings in filter, save ratings
                    //Local variables
                    JSONArray ratings = new JSONArray();

                    for (int rating : goal.getFilter().getPossibleRatings())
                        //Loop for each short status
                        ratings.put(rating);

                    filters.put("R", ratings);
                }

                if (!goal.getFilter().getPossible100PercentStatuses().isEmpty()) {
                    //If there are 100% statuses in filter, save 100% statuses
                    //Local variables
                    JSONArray percent100Statuses = new JSONArray();

                    for (String percent100Status : goal.getFilter().getPossible100PercentStatuses())
                        //Loop for each short status
                        percent100Statuses.put(percent100Status);

                    filters.put("1", percent100Statuses);
                }

                newGoal.put("F", filters);
                playedGoalArray.put(newGoal);
            }

            file.put("PG", playedGoalArray);
        }

        if(GameLists.unplayedGoalList.size() != 0) {
            //If there are unplayed goals, save goals

            for (int i = 0; i < GameLists.unplayedGoalList.size(); i++) {
                //Add each played goal to unplayedGoalArray
                //Local variables
                JSONObject newGoal = new JSONObject();
                JSONObject filters = new JSONObject();
                UnplayedGameGoal goal = GameLists.unplayedGoalList.get(i);

                if (!goal.getTitle().equals(""))
                    //If goal has title, save title
                    newGoal.put("GT", goal.getTitle());

                newGoal.put("SY", goal.getStartYear());
                newGoal.put("SM", goal.getStartMonth());
                newGoal.put("SD", goal.getStartDay());
                newGoal.put("EY", goal.getEndYear());
                newGoal.put("EM", goal.getEndMonth());
                newGoal.put("ED", goal.getEndDay());

                if (goal.getStartProgress() != 0)
                    //If there is start progress, put start progress
                    newGoal.put("SP", goal.getStartProgress());

                if (goal.getGoalProgress() != 0)
                    //If there is goal progress, put goal progress
                    newGoal.put("GP", goal.getGoalProgress());

                if (goal.getEndProgress() != -1)
                    //If there is end progress, put end progress
                    newGoal.put("EP", goal.getEndProgress());

                if (!goal.getFilter().getPossibleStatuses().isEmpty()) {
                    //If there are statuses in filter, save statuses
                    //Local variables
                    JSONArray statuses = new JSONArray();

                    for (String status : goal.getFilter().getPossibleStatuses())
                        //Loop for each status
                        statuses.put(status);

                    filters.put("S", statuses);
                }

                if (!goal.getFilter().getTitleContains().equals(""))
                    //If there is title in filter, save title
                    filters.put("T", goal.getFilter().getTitleContains());

                if (!goal.getFilter().getPossibleFranchises().isEmpty()) {
                    //If there are franchises in filter, save franchises
                    //Local variables
                    JSONArray franchises = new JSONArray();

                    for (String franchise : goal.getFilter().getPossibleFranchises())
                        //Loop for each franchise
                        franchises.put(franchise);

                    filters.put("F", franchises);
                }

                if (!goal.getFilter().getPossiblePlatforms().isEmpty()) {
                    //If there are platforms in filter, save platforms
                    //Local variables
                    JSONArray platforms = new JSONArray();

                    for (String platform : goal.getFilter().getPossiblePlatforms())
                        //Loop for each platform
                        platforms.put(platform);

                    filters.put("P", platforms);
                }

                if (!goal.getFilter().getPossibleGenres().isEmpty()) {
                    //If there are genres in filter, save genres
                    //Local variables
                    JSONArray genres = new JSONArray();

                    for (String genre : goal.getFilter().getPossibleGenres())
                        //Loop for each genre
                        genres.put(genre);

                    filters.put("G", genres);
                }

                if(goal.getFilter().getMinReleaseYear() != 0)
                    //If there is minimum release year, put minimum release year
                    filters.put("LY", goal.getFilter().getMinReleaseYear());

                if(goal.getFilter().getMaxReleaseYear() != Integer.MAX_VALUE)
                    //If there is maximum release year, put maximum release year
                    filters.put("HY", goal.getFilter().getMaxReleaseYear());

                if (!goal.getFilter().getPossibleCollections().isEmpty()) {
                    //If there are collections in filter, save collections
                    //Local variables
                    JSONArray collections = new JSONArray();

                    for (GameCollection collection : goal.getFilter().getPossibleCollections())
                        //Loop for each collection
                        collections.put(collection.getTitle());

                    filters.put("C", collections);
                }

                if(goal.getFilter().getMinAddedYear() != 0)
                    //If there is a minimum added year, put minimum added year
                    filters.put("LA", goal.getFilter().getMinAddedYear());

                if(goal.getFilter().getMaxAddedYear() != 0)
                    //If there is a maximum added year, put maximum added year
                    filters.put("HA", goal.getFilter().getMaxAddedYear());

                if(goal.getFilter().getMinHours() != 0)
                    //If there is minimum hours, put minimum hours
                    filters.put("LH", goal.getFilter().getMinHours());

                if(goal.getFilter().getMaxHours() != Double.MAX_VALUE)
                    //If there is maximum hours, put maximum hours
                    filters.put("HH", goal.getFilter().getMaxHours());

                if (!goal.getFilter().getPossibleDeckStatuses().isEmpty()) {
                    //If there are deck statuses in filter, save deck statuses
                    //Local variables
                    JSONArray deckStatuses = new JSONArray();

                    for (String deckStatus : goal.getFilter().getPossibleDeckStatuses())
                        //Loop for each collection
                        deckStatuses.put(deckStatus);

                    filters.put("D", deckStatuses);
                }

                newGoal.put("F", filters);
                unplayedGoalArray.put(newGoal);
            }

            file.put("UG", unplayedGoalArray);
        }

        //Write the JSONObject to the file and close the PrintWriter
        pw.write(file.toString());
        pw.flush();
        pw.close();

        //Change window to finished after saving
        label.setText("File Saved.");
        vbox.getChildren().add(button);

        changeMade = false;
        setStageTitle();
        button.setOnAction(e1 -> stage.close());
    }

    //Opens a file from the given file path
    public static void openFile(Path filePath) {
        try {
            //Local variables
            String jsonString = Files.readString(filePath);         //String to parse
            JSONObject file = new JSONObject(jsonString);           //String as a JSONObject
            JSONArray platformList = file.getJSONArray("PL");   //List of platforms in the file
            JSONArray genreList = file.getJSONArray("GL");      //List  of genres in the file
            JSONArray playedGameList = null;                        //list of PlayedGames in the file
            JSONArray unplayedGameList = null;                      //List of UnplayedGames in the file
            JSONArray collectionList = null;                        //List of collections in the file
            JSONArray playedTempArray = null;                       //List of played temp array items in the file
            JSONArray unplayedTempArray = null;                     //List of unplayed temp array items in the file
            JSONArray playedGoalList = null;                        //List of played goals in the file
            JSONArray unplayedGoalList = null;                      //List of unplayed goals in the file

            if(file.has("P"))
                //If there are played games, open played games list
                playedGameList = file.getJSONArray("P");

            if(file.has("U"))
                //If there are unplayed games, open unplayed games list
                unplayedGameList = file.getJSONArray("U");

            if(file.has("C"))
                //If there are collections, open collection list
                collectionList = file.getJSONArray("C");

            if(file.has("PT"))
                //If there are items in the played temp list, open played temp list
                playedTempArray = file.getJSONArray("PT");

            if(file.has("UT"))
                //If there are items in the unplayed temp list, open unplayed temp list
                unplayedTempArray = file.getJSONArray("UT");

            if(file.has("PG"))
                //If there are played game goals, open played game goals
                playedGoalList = file.getJSONArray("PG");

            if(file.has("UG"))
                //If there are unplayed game goals, open unplayed game goals
                unplayedGoalList = file.getJSONArray("UG");

            //Reset all lists before setting new data
            GameLists.platformList = FXCollections.observableArrayList();
            GameLists.genreList = FXCollections.observableArrayList();
            GameLists.playedList = FXCollections.observableArrayList();
            GameLists.unplayedList = FXCollections.observableArrayList();
            GameLists.collectionList = FXCollections.observableArrayList();
            ApplicationGUI.playedTempList.setGames(FXCollections.observableArrayList());
            ApplicationGUI.unplayedTempList.setGames(FXCollections.observableArrayList());
            GameLists.playedGoalList = FXCollections.observableArrayList();
            GameLists.unplayedGoalList = FXCollections.observableArrayList();

            for (int i = 0; i < platformList.length(); i++)
                //Add each platform
                GameLists.platformList.add((String) platformList.get(i));

            for (int i = 0; i < genreList.length(); i++)
                //Add each genre
                GameLists.genreList.add((String) genreList.get(i));

            if(playedGameList != null)
                for (int i = 0; i < playedGameList.length(); i++) {
                    //Add each PlayedGame
                    //Local variables
                    JSONObject newObj = (JSONObject) playedGameList.get(i); //Game data from the JSON file
                    PlayedGame newGame;                                     //New game to add to the list
                    String title = "";                                      //New game's title
                    int releaseYear = 0;                                    //New game's release year
                    int releaseMonth = 0;                                   //New game's release month
                    int releaseDay = 0;                                     //New game's release day
                    String franchise = "";                                  //New game's franchise
                    int rating = 0;                                         //New game's rating
                    String shortStatus = "";                                //New game's short status
                    int completionYear = 0;                                 //New game's completion year
                    int completionMonth = 0;                                //New game's completion month
                    int completionDay = 0;                                  //New game's completion day
                    String percent100 = "";                                 //New game's 100% status

                    if(newObj.has("T"))
                        //If game has title, set title
                        title = (String) newObj.get("T");

                    if(newObj.has("RY"))
                        //If the game has a release year, set release year
                        releaseYear = (int) newObj.get("RY");

                    if(newObj.has("RM"))
                        //If the game has a release month, set release month
                        releaseMonth = (int) newObj.get("RM");

                    if(newObj.has("RD"))
                        //If the game has a release day, set release day
                        releaseDay = (int) newObj.get("RD");

                    if(newObj.has("F"))
                        //If game has franchise, set franchise
                        franchise = (String) newObj.get("F");

                    if(newObj.has("R"))
                        //If the game has a rating, set rating
                        rating = (int) newObj.get("R");

                    if(newObj.has("SS"))
                        //If game has short status, set short status
                        shortStatus = (String) newObj.get("SS");

                    if(newObj.has("CY"))
                        //If the game has a completion year, set completion year
                        completionYear = (int) newObj.get("CY");

                    if(newObj.has("CM"))
                        //If the game has a completion month, set completion month
                        completionMonth = (int) newObj.get("CM");

                    if(newObj.has("CD"))
                        //If the game has a completion day, set completion day
                        completionDay = (int) newObj.get("CD");

                    if(newObj.has("1"))
                        //If game has 100% status, set 100% status
                        percent100 = (String) newObj.get("1");

                    newGame = new PlayedGame(title, (String) newObj.get("S"), (String) newObj.get("P"),
                            (String) newObj.get("G"), releaseYear, releaseMonth, releaseDay);

                    //Add data to game
                    newGame.setCompletionYear(completionYear);
                    newGame.setCompletionMonth(completionMonth);
                    newGame.setCompletionDay(completionDay);
                    newGame.setShortStatus(shortStatus);
                    newGame.setRating(rating);
                    newGame.setPercent100(percent100);
                    newGame.setFranchise(franchise);

                    //Add game to list
                    GameLists.playedList.add(newGame);
                }

            if(unplayedGameList != null)
                for (int i = 0; i < unplayedGameList.length(); i++) {
                    //Add each PlayedGame
                    //Local variables
                    JSONObject newObj = (JSONObject) unplayedGameList.get(i);   //Game data from the JSON file
                    UnplayedGame newGame;                                       //New game to add to the list
                    String title = "";                                          //New game's title
                    int releaseYear = 0;                                        //New game's release year
                    int releaseMonth = 0;                                       //New game's release month
                    int releaseDay = 0;                                         //New game's release day
                    String franchise = "";                                      //New game's franchise
                    int addedYear = 0;                                          //New game's added year
                    int addedMonth = 0;                                         //New game's added month
                    int addedDay = 0;                                           //New game's added day
                    double hours = 0.0;                                         //New game's hours
                    String deckStatus = "";                                     //New game's deck status

                    if(newObj.has("T"))
                        //If game has title, set title
                        title = (String) newObj.get("T");

                    if(newObj.has("RY"))
                        //If the game has a release year, set release year
                        releaseYear = (int) newObj.get("RY");

                    if(newObj.has("RM"))
                        //If the game has a release month, set release month
                        releaseMonth = (int) newObj.get("RM");

                    if(newObj.has("RD"))
                        //If the game has a release day, set release day
                        releaseDay = (int) newObj.get("RD");

                    if(newObj.has("F"))
                        //If game has franchise, set franchise
                        franchise = (String) newObj.get("F");

                    if(newObj.has("AY"))
                        //If the game has an added year, set added year
                        addedYear = (int) newObj.get("AY");

                    if(newObj.has("AM"))
                        //If the game has an added month, set added month
                        addedMonth = (int) newObj.get("AM");

                    if(newObj.has("AD"))
                        //If the game has an added day, set added day
                        addedDay = (int) newObj.get("AD");

                    if(newObj.has("H"))
                        //If game has hours, set hours
                        try{
                            //Hours looks like double
                            hours = ((BigDecimal) newObj.get("H")).doubleValue();
                        } catch (ClassCastException e2) {
                            //Hours looks like int
                            hours = (int) newObj.get("H") * 1.0;
                        }

                    if(newObj.has("D"))
                        //If game has deck status, set deck status
                        deckStatus = (String) newObj.get("D");

                    newGame = new UnplayedGame(title, (String) newObj.get("S"), (String) newObj.get("P"),
                            (String) newObj.get("G"), releaseYear, releaseMonth, releaseDay);

                    //Add data to game,
                    newGame.setFranchise(franchise);
                    newGame.setDeckCompatible(deckStatus);
                    newGame.setHours(hours);
                    newGame.setAddedYear(addedYear);
                    newGame.setAddedMonth(addedMonth);
                    newGame.setAddedDay(addedDay);

                    //Add game to list
                    GameLists.unplayedList.add(newGame);
                }

            if(collectionList != null)
                for(int i = 0; i < collectionList.length(); i++){
                    //Add each collection
                    //Local variables
                    JSONObject newObj = (JSONObject) collectionList.get(i);                        //Collection data from the JSON file
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

            if(playedTempArray != null)
                for(int i = 0; i < playedTempArray.length(); i++){
                    //Add each item in playedTempArray
                    //Local variables
                    int gameID = (int) playedTempArray.get(i);    //Index of game

                    //Add game to list
                    playedTempList.getGames().add(GameLists.playedList.get(gameID));
                }

            if(unplayedTempArray != null)
                for(int i = 0; i < unplayedTempArray.length(); i++){
                    //Add each item in playedTempArray
                    //Local variables
                    int gameID = (int) unplayedTempArray.get(i);    //ID of game

                    //Add game to list
                    unplayedTempList.getGames().add(GameLists.unplayedList.get(gameID));
                }

            if(playedGoalList != null)
                for(int i = 0; i < playedGoalList.length(); i++){
                    //Add each game in playedGoalList
                    //Local variables
                    JSONObject newObj = (JSONObject) playedGoalList.get(i);                             //Goal data from json file
                    JSONObject filterObj = (JSONObject) newObj.get("F");                                //Filters data from json file
                    PlayedGameGoal newGoal;                                                             //New goal to add to list
                    String title = "";                                                                  //Title of new goal
                    int startProgress = 0;                                                              //Start progress of new goal
                    int goalProgress = 0;                                                                //Goal progress of new goal
                    PlayedGameFilter filter;                                                            //Filter for new goal
                    ObservableList<String> statuses = FXCollections.observableArrayList();              //Statuses for filter
                    String titleContains = "";                                                          //Title contains for filter
                    ObservableList<String> franchises = FXCollections.observableArrayList();            //Franchises for filter
                    ObservableList<String> platforms = FXCollections.observableArrayList();             //Platforms for filter
                    ObservableList<String> genres = FXCollections.observableArrayList();                //Genres for filter
                    int minReleaseYear = 0;                                                             //Minimum release year for filter
                    int maxReleaseYear = Integer.MAX_VALUE;                                             //Maximum release year for filter
                    ObservableList<GameCollection> collections = FXCollections.observableArrayList();   //Collections for filter
                    ObservableList<String> shortStatuses = FXCollections.observableArrayList();         //Short statuses for filter
                    ObservableList<Integer> ratings = FXCollections.observableArrayList();              //Ratings statuses for filter
                    ObservableList<String> percent100Statuses = FXCollections.observableArrayList();    //100% statuses for filter

                    if(newObj.has("GT"))
                        //If goal has title, get title
                        title = (String) newObj.get("GT");

                    if(newObj.has("SP"))
                        //If goal has start progress, get start progress
                        startProgress = (int) newObj.get("SP");

                    if(newObj.has("GP"))
                        //If goal has goal progress, get goal progress
                        goalProgress = (int) newObj.get("GP");

                    if(filterObj.has("S")) {
                        //If filter has statuses, get statuses
                        //Local variables
                        JSONArray statusesArray = filterObj.getJSONArray("S");

                        for(int j = 0; j < statusesArray.length(); j++)
                            //For each item in statuses array, put status in statuses
                            statuses.add((String) statusesArray.get(j));
                    }

                    if(filterObj.has("T"))
                        //If filter has title, get title
                        titleContains = (String) filterObj.get("T");

                    if(filterObj.has("F")) {
                        //If filter has franchises, get franchises
                        //Local variables
                        JSONArray franchisesArray = filterObj.getJSONArray("F");

                        for (int j = 0; j < franchisesArray.length(); j++)
                            //For each item in franchises array, put franchise in franchises
                            franchises.add((String) franchisesArray.get(j));
                    }

                    if(filterObj.has("P")) {
                        //If filter has platforms, get platforms
                        //Local variables
                        JSONArray platformsArray = filterObj.getJSONArray("P");

                        for (int j = 0; j < platformsArray.length(); j++)
                            //For each item in platforms array, put platform in platforms
                            platforms.add((String) platformsArray.get(j));
                    }

                    if(filterObj.has("G")) {
                        //If filter has genres, get genres
                        //Local variables
                        JSONArray genresArray = filterObj.getJSONArray("G");

                        for (int j = 0; j < genresArray.length(); j++)
                            //For each item in genres array, put genre in genres
                            genres.add((String) genresArray.get(j));
                    }

                    if(filterObj.has("LY"))
                        //If filter has minimum release year, get year
                        minReleaseYear = (int) filterObj.get("LY");

                    if(filterObj.has("HY"))
                        //If filter has maximum release year, get year
                        maxReleaseYear = (int) filterObj.get("HY");

                    if(filterObj.has("C")) {
                        //If filter has collections, get collections
                        //Local variables
                        JSONArray collectionsArray = filterObj.getJSONArray("C");

                        for (int j = 0; j < collectionsArray.length(); j++)
                            //For each item in collections array, put short status in collections

                            for (GameCollection collection : GameLists.collectionList)
                                //For each collection, add the one with the same name.

                                if(collection.getTitle().equals(collectionsArray.get(j))) {
                                    //If collection found, add it to list and stop looping
                                    collections.add(collection);
                                    break;
                                }
                    }

                    if(filterObj.has("SS")) {
                        //If filter has short statuses, get short statuses
                        //Local variables
                        JSONArray shortStatusArray = filterObj.getJSONArray("SS");

                        for (int j = 0; j < shortStatusArray.length(); j++)
                            //For each item in short statuses array, put short status in short statuses
                            shortStatuses.add((String) shortStatusArray.get(j));
                    }

                    if(filterObj.has("R")) {
                        //If filter has ratings, get ratings
                        //Local variables
                        JSONArray ratingsArray = filterObj.getJSONArray("R");

                        for (int j = 0; j < ratingsArray.length(); j++)
                            //For each item in ratings array, put rating in short ratings
                            ratings.add((int) ratingsArray.get(j));
                    }

                    if(filterObj.has("1")) {
                        //If filter has 100% statuses, get 100% statuses
                        //Local variables
                        JSONArray percent100Array = filterObj.getJSONArray("1");

                        for (int j = 0; j < percent100Array.length(); j++)
                            //For each item in 100% statuses array, put 100% status in 100% statuses
                            percent100Statuses.add((String) percent100Array.get(j));
                    }

                    //Create new filter
                    filter = new PlayedGameFilter(statuses, franchises, platforms, genres, collections, shortStatuses,
                            ratings, percent100Statuses, titleContains, minReleaseYear, maxReleaseYear,
                            0, Integer.MAX_VALUE);
                    filter.setMaxCompletionDate(
                            LocalDate.of((int) newObj.get("EY"), (int) newObj.get("EM"), (int) newObj.get("ED")));

                    //Create new goal
                    newGoal = new PlayedGameGoal(title, (int) newObj.get("SY"), (int) newObj.get("SM"),
                            (int) newObj.get("SD"), (int) newObj.get("EY"), (int) newObj.get("EM"),
                            (int) newObj.get("ED"), startProgress, goalProgress, filter);

                    GameLists.playedGoalList.add(newGoal);
                }

            if(unplayedGoalList != null)
                for(int i = 0; i < unplayedGoalList.length(); i++){
                    //Add each game in unplayedGoalList
                    //Local variables
                    JSONObject newObj = (JSONObject) unplayedGoalList.get(i);                             //Goal data from json file
                    JSONObject filterObj = (JSONObject) newObj.get("F");                                //Filters data from json file
                    UnplayedGameGoal newGoal;                                                             //New goal to add to list
                    String title = "";                                                                  //Title of new goal
                    int startProgress = 0;                                                              //Start progress of new goal
                    int goalProgress = 0;                                                                //Goal progress of new goal
                    int endProgress = -1;                                                               //End progress of new goal
                    UnplayedGameFilter filter;                                                            //Filter for new goal
                    ObservableList<String> statuses = FXCollections.observableArrayList();              //Statuses for filter
                    String titleContains = "";                                                          //Title contains for filter
                    ObservableList<String> franchises = FXCollections.observableArrayList();            //Franchises for filter
                    ObservableList<String> platforms = FXCollections.observableArrayList();             //Platforms for filter
                    ObservableList<String> genres = FXCollections.observableArrayList();                //Genres for filter
                    int minReleaseYear = 0;                                                             //Minimum release year for filter
                    int maxReleaseYear = Integer.MAX_VALUE;                                             //Maximum release year for filter
                    ObservableList<GameCollection> collections = FXCollections.observableArrayList();   //Collections for filter
                    int minAddedYear =  0;                                                              //Minimum added year for filter
                    int maxAddedYear =  Integer.MAX_VALUE;                                              //Maximum added year for filter
                    double minHours = 0.0;                                                              //Min hours for filter
                    double maxHours = Double.MAX_VALUE;                                                 //Max hours for filter
                    ObservableList<String> deckStatuses = FXCollections.observableArrayList();          //Deck statuses for filter

                    if(newObj.has("GT"))
                        //If goal has title, get title
                        title = (String) newObj.get("GT");

                    if(newObj.has("SP"))
                        //If goal has start progress, get start progress
                        startProgress = (int) newObj.get("SP");

                    if(newObj.has("GP"))
                        //If goal has goal progress, get goal progress
                        goalProgress = (int) newObj.get("GP");

                    if(newObj.has("EP"))
                        //If goal has end progress, get end progress
                        endProgress = (int) newObj.get("EP");

                    if(filterObj.has("S")) {
                        //If filter has statuses, get statuses
                        //Local variables
                        JSONArray statusesArray = filterObj.getJSONArray("S");

                        for(int j = 0; j < statusesArray.length(); j++)
                            //For each item in statuses array, put status in statuses
                            statuses.add((String) statusesArray.get(j));
                    }

                    if(filterObj.has("T"))
                        //If filter has title, get title
                        titleContains = (String) filterObj.get("T");

                    if(filterObj.has("F")) {
                        //If filter has franchises, get franchises
                        //Local variables
                        JSONArray franchisesArray = filterObj.getJSONArray("F");

                        for (int j = 0; j < franchisesArray.length(); j++)
                            //For each item in franchises array, put franchise in franchises
                            franchises.add((String) franchisesArray.get(j));
                    }

                    if(filterObj.has("P")) {
                        //If filter has platforms, get platforms
                        //Local variables
                        JSONArray platformsArray = filterObj.getJSONArray("P");

                        for (int j = 0; j < platformsArray.length(); j++)
                            //For each item in platforms array, put platform in platforms
                            platforms.add((String) platformsArray.get(j));
                    }

                    if(filterObj.has("G")) {
                        //If filter has genres, get genres
                        //Local variables
                        JSONArray genresArray = filterObj.getJSONArray("G");

                        for (int j = 0; j < genresArray.length(); j++)
                            //For each item in genres array, put genre in genres
                            genres.add((String) genresArray.get(j));
                    }

                    if(filterObj.has("LY"))
                        //If filter has minimum release year, get year
                        minReleaseYear = (int) filterObj.get("LY");

                    if(filterObj.has("HY"))
                        //If filter has maximum release year, get year
                        maxReleaseYear = (int) filterObj.get("HY");

                    if(filterObj.has("C")) {
                        //If filter has collections, get collections
                        //Local variables
                        JSONArray collectionsArray = filterObj.getJSONArray("C");

                        for (int j = 0; j < collectionsArray.length(); j++)
                            //For each item in collections array, put short status in collections

                            for (GameCollection collection : GameLists.collectionList)
                                //For each collection, add the one with the same name.

                                if(collection.getTitle().equals(collectionsArray.get(j))) {
                                    //If collection found, add it to list and stop looping
                                    collections.add(collection);
                                    break;
                                }
                    }

                    if(filterObj.has("LA"))
                        //If filter has minimum added year, get year
                        minAddedYear = (int) filterObj.get("LA");

                    if(filterObj.has("HA"))
                        //If filter has maximum added year, get year
                        maxAddedYear = (int) filterObj.get("HA");

                    if(filterObj.has("LH")) {
                        //If filter has minimum hours, get hours
                        try {
                            //Hours looks like double
                            minHours = ((BigDecimal) filterObj.get("LH")).doubleValue();
                        } catch (ClassCastException e2) {
                            //Hours looks like int
                            minHours = (int) filterObj.get("LH") * 1.0;
                        }
                    }

                    if(filterObj.has("HH")) {
                        //If filter has maximum hours, get hours
                        try {
                            //Hours looks like double
                            maxHours = ((BigDecimal) filterObj.get("HH")).doubleValue();
                        } catch (ClassCastException e2) {
                            //Hours looks like int
                            maxHours = (int) filterObj.get("HH") * 1.0;
                        }
                    }

                    if(filterObj.has("D")) {
                        //If filter has deck statuses, get deck statuses
                        //Local variables
                        JSONArray deckStatusesArray = filterObj.getJSONArray("D");

                        for (int j = 0; j < deckStatusesArray.length(); j++)
                            //For each item in deck statuses array, put deck status in deck statuses
                            deckStatuses.add((String) deckStatusesArray.get(j));
                    }

                    //Create new filter
                    filter = new UnplayedGameFilter(statuses, franchises, platforms, genres, collections, deckStatuses,
                            titleContains, minReleaseYear, maxReleaseYear, minAddedYear, maxAddedYear, minHours, maxHours);

                    //Create new goal
                    newGoal = new UnplayedGameGoal(title, (int) newObj.get("SY"), (int) newObj.get("SM"),
                            (int) newObj.get("SD"), (int) newObj.get("EY"), (int) newObj.get("EM"),
                            (int) newObj.get("ED"), startProgress, goalProgress, endProgress, filter);

                    GameLists.unplayedGoalList.add(newGoal);
                }

            //Update GUI
            playedGamesTable.sortAndFilter(playedFilterTokenChoices.getSelectionModel().getSelectedItem());
            unplayedGamesTable.sortAndFilter(unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());
            statusCountBoxPlayed.updateData();
            statusCountBoxUnplayed.updateData();
            playedTempList.updateLabels();
            unplayedTempList.updateLabels();

            //Fixes an issue where listview doesn't show the items
            playedTempList.getListView().setItems(playedTempList.getGames());
            unplayedTempList.getListView().setItems(unplayedTempList.getGames());
        } catch (NoSuchFileException ignored) {
            changeMade = true;
            setStageTitle();
        } catch (NullPointerException | IOException e1) {
            e1.printStackTrace();
        }
    }
}