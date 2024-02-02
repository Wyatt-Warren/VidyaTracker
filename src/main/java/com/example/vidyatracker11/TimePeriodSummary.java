package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.HashMap;

public abstract class TimePeriodSummary extends VBox {
    final String viewItemsString = "Show Applicable Games";

    //GUI
    Label mainLabel = new Label("");

    //Labels in row 1
    Label countTotal = new Label();
    Label countNotShortLabel = new Label();
    Button countNotShortButton = new Button(viewItemsString);
    Label countShortLabel = new Label();
    Button countShortButton = new Button(viewItemsString);

    //Labels in row 2
    Label averageRating = new Label();
    Label count100PercentLabel = new Label();
    Button count100PercentButton = new Button(viewItemsString);
    Label countSamePeriodLabel = new Label();
    Button countSamePeriodButton = new Button(viewItemsString);
    GridPane countPane = new GridPane();

    //Tables
    //Games
    TableColumn<PlayedGame, String> gameShortColumn = new TableColumn<>("Short");
    TableColumn<PlayedGame, String> gameTitleColumn = new TableColumn<>("Game");
    TableColumn<PlayedGame, Integer> gameRatingColumn = new TableColumn<>("Rating");
    TableColumn<PlayedGame, String> gamePlatformColumn = new TableColumn<>("Platform");
    TableColumn<PlayedGame, String> gameGenreColumn = new TableColumn<>("Genre");
    TableColumn<PlayedGame, Integer> gameReleaseYearColumn = new TableColumn<>("Release Year");
    TableColumn<PlayedGame, String> game100PercentColumn = new TableColumn<>("100%");
    TableView<PlayedGame> gameTable = new TableView<>();

    //Franchises
    TableColumn<PlayedDataEntry, String> franchiseTitleColumn = new TableColumn<>("Franchise");
    TableColumn<PlayedDataEntry, Integer> franchiseCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> franchisePercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> franchiseRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> franchiseTable = new TableView<>();

    //Platforms
    TableColumn<PlayedDataEntry, String> platformTitleColumn = new TableColumn<>("Platform");
    TableColumn<PlayedDataEntry, Integer> platformCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> platformPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> platformRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> platformTable = new TableView<>();

    //Genres
    TableColumn<PlayedDataEntry, String> genreTitleColumn = new TableColumn<>("Genre");
    TableColumn<PlayedDataEntry, Integer> genreCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> genrePercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> genreRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> genreTable = new TableView<>();

    HBox tableBox = new HBox(franchiseTable, platformTable, genreTable);

    //Switch period
    Button previousPeriod = new Button("<-");
    Button nextPeriod = new Button("->");
    Label switchPeriodLabel = new Label();
    //GUI
    ChoiceBox<Integer> switchPeriodChoices = new ChoiceBox<>();
    Button switchPeriodButton = new Button();
    HBox switchPeriodBox = new HBox();

    //Lists
    ObservableList<TableView<?>> tableViews = FXCollections.observableArrayList(gameTable, franchiseTable,
            platformTable, genreTable);
    ObservableList<TableColumn<PlayedGame, ?>> gameColumns = FXCollections.observableArrayList(gameShortColumn,
            gameTitleColumn, gameRatingColumn, gamePlatformColumn, gameGenreColumn, gameReleaseYearColumn,
            game100PercentColumn);
    ObservableList<TableColumn<PlayedDataEntry, ?>> dataColumns = FXCollections.observableArrayList(
            franchiseTitleColumn, franchiseCountColumn, franchisePercentColumn, franchiseRatingColumn,
            platformTitleColumn, platformCountColumn, platformPercentColumn, platformRatingColumn, genreTitleColumn,
            genreCountColumn, genrePercentColumn, genreRatingColumn);
    ObservableList<TableColumn<PlayedDataEntry, Double>> doubleColumns = FXCollections.observableArrayList(
            franchisePercentColumn, franchiseRatingColumn, platformPercentColumn, platformRatingColumn,
            genrePercentColumn, genreRatingColumn);
    ObservableList<PlayedGame> thisPeriodGames = FXCollections.observableArrayList();
    ObservableList<PlayedGame> notShortGames = FXCollections.observableArrayList();
    ObservableList<PlayedGame> shortGames = FXCollections.observableArrayList();
    ObservableList<PlayedGame> percent100Games = FXCollections.observableArrayList();
    ObservableList<PlayedGame> samePeriodGames = FXCollections.observableArrayList();

    //Fields
    int minYear;        //Lowest year where there are completed games
    int maxYear;        //Highest year where there are completed gamers
    boolean noDates;    //True if there are no completed games with completion year values

    public TimePeriodSummary() {
        //GUI
        getChildren().addAll(mainLabel, countPane, gameTable, tableBox, switchPeriodBox);
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(5, 5, 20, 5));
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        countPane.add(countTotal, 0, 0);
        countPane.add(countNotShortLabel, 1, 0);
        countPane.add(countShortLabel, 2, 0);
        countPane.add(countNotShortButton, 1, 1);
        countPane.add(countShortButton, 2, 1);
        countPane.add(new Label(), 0, 2);
        countPane.add(averageRating, 0, 3);
        countPane.add(count100PercentLabel, 1, 3);
        countPane.add(countSamePeriodLabel, 2, 3);
        countPane.add(count100PercentButton, 1, 4);
        countPane.add(countSamePeriodButton, 2, 4);
        GridPane.setHalignment(countTotal, HPos.CENTER);
        GridPane.setHalignment(countNotShortLabel, HPos.CENTER);
        GridPane.setHalignment(countShortLabel, HPos.CENTER);
        GridPane.setHalignment(countNotShortButton, HPos.CENTER);
        GridPane.setHalignment(countShortButton, HPos.CENTER);
        GridPane.setHalignment(averageRating, HPos.CENTER);
        GridPane.setHalignment(count100PercentLabel, HPos.CENTER);
        GridPane.setHalignment(countSamePeriodLabel, HPos.CENTER);
        GridPane.setHalignment(count100PercentButton, HPos.CENTER);
        GridPane.setHalignment(countSamePeriodButton, HPos.CENTER);
        countPane.setHgap(15);
        countPane.setVgap(5);
        countPane.setAlignment(Pos.CENTER);
        countTotal.setStyle("-fx-font-weight:bold;");
        countNotShortLabel.setStyle("-fx-font-weight:bold;");
        countShortLabel.setStyle("-fx-font-weight:bold;");
        averageRating.setStyle("-fx-font-weight:bold;");
        count100PercentLabel.setStyle("-fx-font-weight:bold;");
        countSamePeriodLabel.setStyle("-fx-font-weight:bold;");
        gameTable.setMaxWidth(Region.USE_PREF_SIZE);
        tableBox.setSpacing(10);
        tableBox.setAlignment(Pos.CENTER);
        switchPeriodBox.setAlignment(Pos.CENTER);
        switchPeriodBox.setSpacing(10);
        switchPeriodBox.getChildren().addAll(previousPeriod, new Label("        "), switchPeriodLabel, switchPeriodChoices,
                switchPeriodButton, new Label("        "), nextPeriod);

        //Column value factories
        //name
        franchiseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        platformTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        genreTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //count
        franchiseCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        platformCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        genreCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        //percent
        franchisePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        platformPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        genrePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));

        //rating
        franchiseRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        platformRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        genreRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));

        //game
        gameShortColumn.setCellValueFactory(new PropertyValueFactory<>("shortStatus"));
        gameTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        gameRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        gamePlatformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
        gameGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        gameReleaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        game100PercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent100"));

        //Cell factories
        gameShortColumn.setCellFactory(e -> new TableCell<>() {
            //Short status column cell factory
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {
                    //Set text
                    setText(item);

                    //Set color
                    setStyle(ApplicationGUI.colorMap.get(item));
                }
            }
        });

        gameRatingColumn.setCellFactory(e -> new TableCell<>() {
            //Rating column cell factory
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {

                    if (item == 0)
                        //0 means no rating
                        setText("");
                    else
                        //Set text
                        setText("" + item);
                }
            }
        });

        game100PercentColumn.setCellFactory(e -> new TableCell<>() {
            //100% status column cell factory
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {
                    //Set text
                    setText(item);

                    //Set color
                    setStyle(ApplicationGUI.colorMap.get(item));
                }
            }
        });

        gameReleaseYearColumn.setCellFactory(e -> new TableCell<>() {
            //Release year column cell factory
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {

                    if (item == 0)
                        //0 means no year
                        setText("");
                    else
                        //Set text
                        setText("" + item);

                    if ((getItem()) == ApplicationGUI.localDate.getYear())
                        //Current year color
                        setStyle(ApplicationGUI.colorMap.get("CURRENTYEAR"));
                    else if ((getItem()) == ApplicationGUI.localDate.getYear() - 1)
                        //Last year color
                        setStyle(ApplicationGUI.colorMap.get("LASTYEAR"));
                    else
                        //Any other year color
                        setStyle("");
                }
            }
        });

        platformTitleColumn.setCellFactory(e -> new TableCell<>() {
            //100% status column cell factory
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no data
                    setText(null);
                    setStyle("");
                } else {
                    //Set text. We remove the digits of the index so it sorts by them but does not display them
                    setText(item.substring(Integer.toString(GameLists.platformList.size()-1).length()));
                }
            }
        });

        genreTitleColumn.setCellFactory(e -> new TableCell<>() {
            //100% status column cell factory
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no data
                    setText(null);
                    setStyle("");
                } else {
                    //Set text. We remove the digits of the indexso it sorts by them but does not display them
                    setText(item.substring(Integer.toString(GameLists.genreList.size()-1).length()));
                }
            }
        });

        //Add columns to tables
        gameTable.getColumns().addAll(gameColumns);
        franchiseTable.getColumns().addAll(franchiseTitleColumn, franchiseCountColumn, franchisePercentColumn,
                franchiseRatingColumn);
        platformTable.getColumns().addAll(platformTitleColumn, platformCountColumn, platformPercentColumn,
                platformRatingColumn);
        genreTable.getColumns().addAll(genreTitleColumn, genreCountColumn, genrePercentColumn, genreRatingColumn);

    }

    //Things that should be done after the value of year or month is set by subclasses
    public void finishConstructing(){
        setThisPeriodGames();

        //Set label text
        countTotal.setText("Games Completed:  " + thisPeriodGames.size());

        //Count Variables
        double ratingTotal = 0;     //total rating used to calculate average

        for(PlayedGame game : thisPeriodGames){
            //Iterate through each game
            if(game.getRating() != 0)
                //Games with rating
                ratingTotal += game.getRating();

            if(game.getShortStatus().equals("Yes"))
                //Game is short
                shortGames.add(game);
            else
                //Game is not short
                notShortGames.add(game);

            if(game.getPercent100().equals("Yes"))
                //Games is 100% completed
                percent100Games.add(game);
        }

        //Sort lists
        notShortGames.sort(TableMethods.completionDateComparator);
        shortGames.sort(TableMethods.completionDateComparator);
        percent100Games.sort(TableMethods.completionDateComparator);
        samePeriodGames.sort(TableMethods.completionDateComparator);

        //Set count text
        countNotShortLabel.setText("Non-Short Games:  " +  notShortGames.size());
        countShortLabel.setText("Short Games:  " + shortGames.size());
        count100PercentLabel.setText("100% Completed Games:  " + percent100Games.size());
        if(ratingTotal != 0)
            averageRating.setText(String.format("Average Rating:  %.2f", (ratingTotal / thisPeriodGames.size())));
        else
            averageRating.setText("Average Rating:  0");

        //Set button actions
        countNotShortButton.setOnAction(e-> displayListWindow(notShortGames));
        countShortButton.setOnAction(e-> displayListWindow(shortGames));
        count100PercentButton.setOnAction(e-> displayListWindow(percent100Games));
        countSamePeriodButton.setOnAction(e -> displayListWindow(samePeriodGames));

        updateStats();
        preventColumnReorderingAndResizingForAll();
        TableMethods.preventColumnSorting(gameTable);
        formatDoubleColumns();
    }

    //Set thisPeriodGames
    public abstract void setThisPeriodGames();

    public void displayListWindow(ObservableList<PlayedGame> list){
        Stage stage = new Stage();
        ListView<PlayedGame> listView = new ListView<>(list);
        Scene scene = new Scene(listView);

        //GUI
        stage.getIcons().add(ApplicationGUI.icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Items");
        stage.setScene(scene);
        scene.getStylesheets().add(ApplicationGUI.styleSheet);

        stage.show();
    }

    //Sets text in double column to %.2f format
    public void formatDoubleColumns(){
        for(TableColumn<PlayedDataEntry, Double> column : doubleColumns){
            //Iterate for all columns with double data
            column.setCellFactory(col -> new TableCell<>() {
                @Override
                public void updateItem(Double someDouble, boolean empty){
                    super.updateItem(someDouble, empty);

                    if (empty || someDouble == null){
                        //Text is empty
                        setText(null);
                    }else{
                        //There is text
                        DecimalFormat decimalFormat = new DecimalFormat("0.##");
                        setText(decimalFormat.format(someDouble));
                    }
                }
            });
        }
    }

    //Call methods to prevent reordering or resizing for all tableviews
    public void preventColumnReorderingAndResizingForAll(){
        for(TableView<?> tableView : tableViews) {
            //Iterate through each tableview
            TableMethods.preventColumnReordering(tableView);
            TableMethods.preventColumnResizing(tableView);
        }
    }

    //Set table data
    public void updateStats(){
        sortGames();
        franchiseTable.setItems(setFranchiseData());
        platformTable.setItems(setPlatGenData(GameLists.platformList, true));
        genreTable.setItems(setPlatGenData(GameLists.genreList, false));

        TableMethods.updateColumnWidth(gameColumns);
        TableMethods.updateColumnWidth(dataColumns);

        gameTable.refresh();
        franchiseTable.refresh();
        platformTable.refresh();
        genreTable.refresh();
    }

    //Sets the data in the franchise table.
    public ObservableList<PlayedDataEntry> setFranchiseData() {
        //Local variables
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<String, PlayedDataEntry> map = new HashMap<>();                         //Map of each franchise

        for(PlayedGame game : thisPeriodGames){
            //Iterate for each game

            if(map.containsKey(game.getFranchise())){
                //If the current game's franchise is already in the map, not the first occurrence
                //Local variables
                PlayedDataEntry data = map.get(game.getFranchise());    //Data entry for the franchise of the current game

                //Increment the count of the franchise by one
                data.setCount(data.getCount()+1);

                if(game.getRating()!=0){
                    //If there is a rating in the current game
                    //Increment count of the games with ratings in the current franchise
                    data.setRatingCount(data.getRatingCount() + 1);

                    //Add to the total rating
                    data.setTotalRating(data.getTotalRating() + game.getRating());

                    //Update the average rating
                    data.setAverageRating(data.getTotalRating() * 1.0 / data.getRatingCount());
                }

                //Set percent of games with the current franchise out of the total
                data.setPercent(data.getCount() * 1.0 / thisPeriodGames.size() * 100);

            }else if(!game.getFranchise().equals("")){
                //If the current game's franchise is not in the map
                //Local variables
                PlayedDataEntry newData = new PlayedDataEntry();    //New data entry for the current franchise

                //Set name to the current franchise
                newData.setName(game.getFranchise());

                //Since it is new, the count is only one
                newData.setCount(1);

                if(game.getRating()!=0){
                    //If there is a rating in the current game
                    //The rating count should start at 1
                    newData.setRatingCount(1);

                    //The total rating count will just be the rating
                    newData.setTotalRating(game.getRating());

                    //The average will just be the rating
                    newData.setAverageRating(newData.getTotalRating() * 1.0);
                }

                //Set the initial percent
                newData.setPercent(1.0 / thisPeriodGames.size() * 100);

                map.put(game.getFranchise(), newData);

                //Add franchise to dataList, keep dataList sorted
                if (dataList.isEmpty())
                    //If the list of franchises is empty, just add it
                    dataList.add(newData);
                else {
                    //There are items in the list of franchises
                    //Local variables
                    boolean placed = false; //Flag if the current item is placed

                    for (int i = 0; i < dataList.size(); i++)
                        //Iterate for each item in the list of franchises

                        if(dataList.get(i).getName().toLowerCase().compareTo(newData.getName().toLowerCase()) > 0){
                            //Compare the value of the current franchise with one from the franchise list
                            dataList.add(i, newData);
                            placed = true;
                            break;
                        }

                    if(!placed)
                        //If it was never placed in the loop, put it at the end
                        dataList.add(newData);

                }
            }
        }

        return dataList;
    }

    //Sets the data in the platform table or genre table.
    public ObservableList<PlayedDataEntry> setPlatGenData(ObservableList<String> list, boolean platform) {
        //Local variables
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<String, PlayedDataEntry> map = new HashMap<>();                         //Map of every platform/genre
        ObservableList<String> platGenList;                                             //List of platforms/genres
        int digitsToDisplay;                                                            //Number of digits of the index in the title

        if (platform)
            platGenList = GameLists.platformList;
        else
            platGenList = GameLists.genreList;

        digitsToDisplay = Integer.toString(platGenList.size()-1).length();

        for(String genPlat : list){
            //Populate the map with platforms or genres
            //Local variables
            PlayedDataEntry newData = new PlayedDataEntry();    //New data for the current genre or platform

            //Set item's name
            newData.setName(String.format("%0" + digitsToDisplay + "d", platGenList.indexOf(genPlat))+ genPlat);

            //Count starts at 0
            newData.setCount(0);

            //Percent should start at 0
            newData.setPercent(0.0);

            //Add item to the map
            map.put(genPlat, newData);

            //Add item to the list
            dataList.add(newData);
        }

        for(PlayedGame game : thisPeriodGames){
            //Accumulate data for each platform or genre
            //Local variable
            PlayedDataEntry data;   //Data for the platform/genre corresponding to the current game

            if(platform)
                //Setting platform data
                data = map.get(game.getPlatform());
            else
                //Setting genre data
                data = map.get(game.getGenre());

            //Increment count
            data.setCount(data.getCount()+1);

            if(game.getRating()!=0){
                //If there is a rating for the current game
                //Increment the rating count
                data.setRatingCount(data.getRatingCount()+1);

                //Add game's rating to the total
                data.setTotalRating(data.getTotalRating()+game.getRating());

                //Update the average rating
                data.setAverageRating(data.getTotalRating() * 1.0 / data.getRatingCount());
            }

            //Update the percent
            data.setPercent(data.getCount()*1.0 / thisPeriodGames.size() * 100);
        }

        dataList.removeIf(data -> data.getCount() == 0);

        return dataList;
    }

    public void sortGames(){
        //Local Variables
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList(
                PlayedGamesTable.basicSort(thisPeriodGames, true));             //List that will be sorted

        newList.sort(TableMethods.completionDateComparator);
        gameTable.setItems(newList);
    }
}
