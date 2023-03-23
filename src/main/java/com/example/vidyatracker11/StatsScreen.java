package com.example.vidyatracker11;

import java.text.DecimalFormat;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Window showing many tableviews of stats relating the game lists
public class StatsScreen extends HBox {
    //GUI
    //Played Games
    //Short Status
    TableColumn<PlayedDataEntry, String> playedShortTitleColumn = new TableColumn<>("Short Status");
    TableColumn<PlayedDataEntry, Integer> playedShortCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedShortPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedShortRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedShortTable = new TableView<>();

    //Franchise
    TableColumn<PlayedDataEntry, String> playedFranchiseTitleColumn = new TableColumn<>("Franchise");
    TableColumn<PlayedDataEntry, Integer> playedFranchiseCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedFranchisePercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedFranchiseRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedFranchiseTable = new TableView<>();

    //Platform
    TableColumn<PlayedDataEntry, String> playedPlatformTitleColumn = new TableColumn<>("Platform");
    TableColumn<PlayedDataEntry, Integer> playedPlatformCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedPlatformPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedPlatformRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedPlatformTable = new TableView<>();

    //Genre
    TableColumn<PlayedDataEntry, String> playedGenreTitleColumn = new TableColumn<>("Genre");
    TableColumn<PlayedDataEntry, Integer> playedGenreCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedGenrePercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedGenreRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedGenreTable = new TableView<>();

    //Release Year
    TableColumn<PlayedDataEntry, Integer> playedReleaseYearTitleColumn = new TableColumn<>("Release Year");
    TableColumn<PlayedDataEntry, Integer> playedReleaseYearCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedReleaseYearPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedReleaseYearRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedReleaseYearTable = new TableView<>();

    //Completion Year
    TableColumn<PlayedDataEntry, Integer> playedCompletionYearTitleColumn = new TableColumn<>("Completion Year");
    TableColumn<PlayedDataEntry, Integer> playedCompletionYearCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedCompletionYearPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedCompletionYearRatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedCompletionYearTable = new TableView<>();

    //Rating
    TableColumn<PlayedDataEntry, Integer> playedRatingTitleColumn = new TableColumn<>("Rating");
    TableColumn<PlayedDataEntry, Integer> playedRatingCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedRatingPercentColumn = new TableColumn<>("Percent");
    TableView<PlayedDataEntry> playedRatingTable = new TableView<>();

    //100 Percent Status
    TableColumn<PlayedDataEntry, String> playedPercent100TitleColumn = new TableColumn<>("100% Status");
    TableColumn<PlayedDataEntry, Integer> playedPercent100CountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedPercent100PercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedPercent100RatingColumn = new TableColumn<>("Average Rating");
    TableView<PlayedDataEntry> playedPercent100Table = new TableView<>();

    //Unplayed Games
    //Franchise
    TableColumn<UnplayedDataEntry, String> unplayedFranchiseTitleColumn = new TableColumn<>("Franchise");
    TableColumn<UnplayedDataEntry, Integer> unplayedFranchiseCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedFranchisePercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedFranchiseHoursColumn = new TableColumn<>("Total Hours");
    TableView<UnplayedDataEntry> unplayedFranchiseTable = new TableView<>();

    //Platform
    TableColumn<UnplayedDataEntry, String> unplayedPlatformTitleColumn = new TableColumn<>("Platform");
    TableColumn<UnplayedDataEntry, Integer> unplayedPlatformCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedPlatformPercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedPlatformHoursColumn = new TableColumn<>("Total Hours");
    TableView<UnplayedDataEntry> unplayedPlatformTable = new TableView<>();

    //Genre
    TableColumn<UnplayedDataEntry, String> unplayedGenreTitleColumn = new TableColumn<>("Genre");
    TableColumn<UnplayedDataEntry, Integer> unplayedGenreCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedGenrePercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedGenreHoursColumn = new TableColumn<>("Total Hours");
    TableView<UnplayedDataEntry> unplayedGenreTable = new TableView<>();

    //Release Year
    TableColumn<UnplayedDataEntry, Integer> unplayedReleaseYearTitleColumn = new TableColumn<>("Release Year");
    TableColumn<UnplayedDataEntry, Integer> unplayedReleaseYearCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedReleaseYearPercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedReleaseYearHoursColumn = new TableColumn<>("Total Hours");
    TableView<UnplayedDataEntry> unplayedReleaseYearTable = new TableView<>();

    //Deck Status
    TableColumn<UnplayedDataEntry, String> unplayedDeckTitleColumn = new TableColumn<>("Deck Status");
    TableColumn<UnplayedDataEntry, Integer> unplayedDeckCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedDeckPercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedDeckHoursColumn = new TableColumn<>("Total Hours");
    TableView<UnplayedDataEntry> unplayedDeckTable = new TableView<>();

    //Other
    Label playedLabel = new Label("Played Games");
    Label unplayedLabel = new Label("Unplayed Games");
    ChoiceBox<String> playedChoices = new ChoiceBox<>();
    ChoiceBox<String> unplayedChoices = new ChoiceBox<>();
    VBox playedBox = new VBox(playedLabel, playedChoices, playedFranchiseTable);
    VBox unplayedBox = new VBox(unplayedLabel, unplayedChoices, unplayedFranchiseTable);

    //Lists
    ObservableList<TableColumn<PlayedDataEntry, ?>> playedColumnList = FXCollections.observableArrayList(
            playedShortTitleColumn, playedShortCountColumn, playedShortPercentColumn,
            playedShortRatingColumn, playedFranchiseTitleColumn, playedFranchiseCountColumn,
            playedFranchisePercentColumn, playedFranchiseRatingColumn, playedPlatformTitleColumn,
            playedPlatformCountColumn, playedPlatformPercentColumn, playedPlatformRatingColumn,
            playedGenreTitleColumn, playedGenreCountColumn, playedGenrePercentColumn,
            playedGenreRatingColumn, playedReleaseYearTitleColumn, playedReleaseYearCountColumn,
            playedReleaseYearPercentColumn, playedReleaseYearRatingColumn, playedCompletionYearTitleColumn,
            playedCompletionYearCountColumn, playedCompletionYearPercentColumn, playedCompletionYearRatingColumn,
            playedRatingTitleColumn, playedRatingCountColumn, playedRatingPercentColumn,
            playedPercent100TitleColumn, playedPercent100CountColumn, playedPercent100PercentColumn,
            playedPercent100RatingColumn);
    ObservableList<TableColumn<UnplayedDataEntry, ?>> unplayedColumnList = FXCollections.observableArrayList(
            unplayedFranchiseTitleColumn, unplayedFranchiseCountColumn, unplayedFranchisePercentColumn,
            unplayedFranchiseHoursColumn, unplayedPlatformTitleColumn, unplayedPlatformCountColumn,
            unplayedPlatformPercentColumn, unplayedPlatformHoursColumn, unplayedGenreTitleColumn,
            unplayedGenreCountColumn, unplayedGenrePercentColumn, unplayedGenreHoursColumn,
            unplayedReleaseYearTitleColumn, unplayedReleaseYearCountColumn, unplayedReleaseYearPercentColumn,
            unplayedReleaseYearHoursColumn, unplayedDeckTitleColumn, unplayedDeckCountColumn,
            unplayedDeckPercentColumn, unplayedDeckHoursColumn);

    //Columns with double data type
    ObservableList<TableColumn<PlayedDataEntry, Double>> playedDoubleColumnList = FXCollections.observableArrayList(
            playedShortPercentColumn, playedShortRatingColumn, playedFranchisePercentColumn,
            playedFranchiseRatingColumn, playedPlatformPercentColumn, playedPlatformRatingColumn,
            playedGenrePercentColumn, playedGenreRatingColumn, playedReleaseYearPercentColumn,
            playedReleaseYearRatingColumn, playedCompletionYearPercentColumn, playedCompletionYearRatingColumn,
            playedRatingPercentColumn, playedPercent100PercentColumn, playedPercent100RatingColumn);
    ObservableList<TableColumn<UnplayedDataEntry, Double>> unplayedDoubleColumnList = FXCollections.observableArrayList(
            unplayedFranchisePercentColumn, unplayedFranchiseHoursColumn, unplayedPlatformPercentColumn,
            unplayedPlatformHoursColumn, unplayedGenrePercentColumn, unplayedGenreHoursColumn,
            unplayedReleaseYearPercentColumn, unplayedReleaseYearHoursColumn, unplayedDeckPercentColumn,
            unplayedDeckHoursColumn);

    //Columns used to set value factory
    ObservableList<TableColumn<?, String>> nameColumns = FXCollections.observableArrayList(
            playedShortTitleColumn, playedFranchiseTitleColumn, playedPlatformTitleColumn,
            playedGenreTitleColumn, playedPercent100TitleColumn, unplayedFranchiseTitleColumn,
            unplayedPlatformTitleColumn, unplayedGenreTitleColumn, unplayedDeckTitleColumn);
    ObservableList<TableColumn<?, Integer>> intNameColumns = FXCollections.observableArrayList(
            playedReleaseYearTitleColumn, playedCompletionYearTitleColumn, playedRatingTitleColumn,
            unplayedReleaseYearTitleColumn
    );
    ObservableList<TableColumn<?, Integer>> countColumns = FXCollections.observableArrayList(
            playedShortCountColumn, playedFranchiseCountColumn, playedPlatformCountColumn,
            playedGenreCountColumn, playedReleaseYearCountColumn, playedCompletionYearCountColumn,
            playedRatingCountColumn, playedPercent100CountColumn, unplayedFranchiseCountColumn,
            unplayedPlatformCountColumn, unplayedGenreCountColumn, unplayedReleaseYearCountColumn,
            unplayedDeckCountColumn
    );
    ObservableList<TableColumn<?, Double>> percentColumns = FXCollections.observableArrayList(
            playedShortPercentColumn, playedFranchisePercentColumn, playedPlatformPercentColumn,
            playedGenrePercentColumn, playedReleaseYearPercentColumn, playedCompletionYearPercentColumn,
            playedRatingPercentColumn, playedPercent100PercentColumn, unplayedFranchisePercentColumn,
            unplayedPlatformPercentColumn, unplayedGenrePercentColumn, unplayedReleaseYearPercentColumn,
            unplayedDeckPercentColumn
    );
    ObservableList<TableColumn<?, Double>> ratingColumns = FXCollections.observableArrayList(
            playedShortRatingColumn, playedFranchiseRatingColumn, playedPlatformRatingColumn,
            playedGenreRatingColumn, playedReleaseYearRatingColumn, playedCompletionYearRatingColumn,
            playedPercent100RatingColumn
    );
    ObservableList<TableColumn<?, Double>> hoursColumns = FXCollections.observableArrayList(
            unplayedFranchiseHoursColumn, unplayedPlatformHoursColumn, unplayedGenreHoursColumn,
            unplayedReleaseYearHoursColumn, unplayedDeckHoursColumn
    );

    ObservableList<TableView<?>> tableViews = FXCollections.observableArrayList(
            playedShortTable, playedFranchiseTable, playedPlatformTable,
            playedGenreTable, playedReleaseYearTable, playedCompletionYearTable,
            playedRatingTable, playedPercent100Table, unplayedFranchiseTable,
            unplayedPlatformTable, unplayedGenreTable, unplayedReleaseYearTable,
            unplayedDeckTable
    );

    public StatsScreen(Stage parentStage) {
        //GUI
        playedLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        playedChoices.getSelectionModel().selectFirst();
        playedChoices.getItems().addAll("Short Status", "Franchise", "Rating", "Platform", "Genre", "Release Year", "Completion Year", "100% Status");
        playedBox.setAlignment(Pos.CENTER);
        playedBox.setSpacing(5);
        playedBox.setMaxHeight(Double.MAX_VALUE);
        unplayedLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        unplayedChoices.getSelectionModel().selectFirst();
        unplayedChoices.getItems().addAll("Franchise", "Platform", "Genre", "Release Year", "Deck Status");
        unplayedBox.setAlignment(Pos.CENTER);
        unplayedBox.setSpacing(5);
        unplayedBox.setMaxHeight(Double.MAX_VALUE);
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(5));
        getChildren().addAll(playedBox, unplayedBox);

        for(TableView<?> tableView : tableViews)
            //Set every TableView's height to 600
            tableView.setMinHeight(600);

        //Set value factories
        for(TableColumn<?, String> column : nameColumns)
            column.setCellValueFactory(new PropertyValueFactory<>("name"));
        for(TableColumn<?, Integer> column : intNameColumns)
            column.setCellValueFactory(new PropertyValueFactory<>("intName"));
        for(TableColumn<?, Integer> column : countColumns)
            column.setCellValueFactory(new PropertyValueFactory<>("count"));
        for(TableColumn<?, Double> column : percentColumns)
            column.setCellValueFactory(new PropertyValueFactory<>("percent"));
        for(TableColumn<?, Double> column : ratingColumns)
            column.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        for(TableColumn<?, Double> column : hoursColumns)
            column.setCellValueFactory(new PropertyValueFactory<>("totalHours"));

        //Add columns to tables
        playedShortTable.getColumns().addAll(playedShortTitleColumn, playedShortCountColumn,
                playedShortPercentColumn, playedShortRatingColumn);
        playedFranchiseTable.getColumns().addAll(playedFranchiseTitleColumn, playedFranchiseCountColumn,
                playedFranchisePercentColumn, playedFranchiseRatingColumn);
        playedPlatformTable.getColumns().addAll(playedPlatformTitleColumn, playedPlatformCountColumn,
                playedPlatformPercentColumn, playedPlatformRatingColumn);
        playedGenreTable.getColumns().addAll(playedGenreTitleColumn, playedGenreCountColumn,
                playedGenrePercentColumn, playedGenreRatingColumn);
        playedReleaseYearTable.getColumns().addAll(playedReleaseYearTitleColumn, playedReleaseYearCountColumn,
                playedReleaseYearPercentColumn, playedReleaseYearRatingColumn);
        playedCompletionYearTable.getColumns().addAll(playedCompletionYearTitleColumn, playedCompletionYearCountColumn,
                playedCompletionYearPercentColumn, playedCompletionYearRatingColumn);
        playedRatingTable.getColumns().addAll(playedRatingTitleColumn, playedRatingCountColumn,
                playedRatingPercentColumn);
        playedPercent100Table.getColumns().addAll(playedPercent100TitleColumn, playedPercent100CountColumn,
                playedPercent100PercentColumn, playedPercent100RatingColumn);
        unplayedFranchiseTable.getColumns().addAll(unplayedFranchiseTitleColumn, unplayedFranchiseCountColumn,
                unplayedFranchisePercentColumn, unplayedFranchiseHoursColumn);
        unplayedPlatformTable.getColumns().addAll(unplayedPlatformTitleColumn, unplayedPlatformCountColumn,
                unplayedPlatformPercentColumn, unplayedPlatformHoursColumn);
        unplayedGenreTable.getColumns().addAll(unplayedGenreTitleColumn, unplayedGenreCountColumn,
                unplayedGenrePercentColumn, unplayedGenreHoursColumn);
        unplayedReleaseYearTable.getColumns().addAll(unplayedReleaseYearTitleColumn, unplayedReleaseYearCountColumn,
                unplayedReleaseYearPercentColumn, unplayedReleaseYearHoursColumn);
        unplayedDeckTable.getColumns().addAll(unplayedDeckTitleColumn, unplayedDeckCountColumn,
                unplayedDeckPercentColumn, unplayedDeckHoursColumn);

        playedChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            switch ((int) newNum){
                //Switch for each selection of playedChoices
                case 0:
                    //Short Status
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedShortTable);
                    break;
                case 1:
                    //Franchise
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedFranchiseTable);
                    break;
                case 2:
                    //Rating
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedRatingTable);
                    break;
                case 3:
                    //Platform
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedPlatformTable);
                    break;
                case 4:
                    //Genre
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedGenreTable);
                    break;
                case 5:
                    //Release Year
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedReleaseYearTable);
                    break;
                case 6:
                    //Completion Year
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedCompletionYearTable);
                    break;
                case 7:
                    //100% Status
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedPercent100Table);
                    break;
            }

            //Automatically reset size of window
            parentStage.setScene(null);
            parentStage.setScene(getScene());
        });

        unplayedChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            switch ((int) newNum){
                //Switch for each selection of unplayedChoices
                case 0:
                    //Franchise
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedFranchiseTable);
                    break;
                case 1:
                    //Platform
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedPlatformTable);
                    break;
                case 2:
                    //Genre
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedGenreTable);
                    break;
                case 3:
                    //Release Year
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedReleaseYearTable);
                    break;
                case 4:
                    //Completion Year
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedDeckTable);
                    break;
            }

            //Automatically reset size of window
            parentStage.setScene(null);
            parentStage.setScene(getScene());
        });

        playedChoices.getSelectionModel().selectFirst();
        unplayedChoices.getSelectionModel().selectFirst();

        updateStats();
        preventColumnReorderingAndResizingForAll();
        formatDoubleColumns();
    }

    //Sets text in double column to %.2f format
    public void formatDoubleColumns(){
        for(TableColumn<PlayedDataEntry, Double> column : playedDoubleColumnList){
            //Iterate for all PlayedGame columns with double data
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
        for(TableColumn<UnplayedDataEntry, Double> column : unplayedDoubleColumnList){
            //Iterate for all UnplayedGame columns with double data
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
    public void preventColumnReorderingAndResizingForAll() {
        //Reordering
        for(TableView<?> tableView : tableViews)
            TableMethods.preventColumnReordering(tableView);

        //Resizing
        for(TableView<?> tableView : tableViews)
            TableMethods.preventColumnResizing(tableView);
    }

    //Set table data
    public void updateStats() {
        //Set played game data
        playedShortTable.setItems(setPlayedShortData());
        playedFranchiseTable.setItems(setPlayedFranchiseData());
        playedPlatformTable.setItems(setPlayedPlatGenData(GameLists.platformList, true));
        playedGenreTable.setItems(setPlayedPlatGenData(GameLists.genreList, false));
        playedReleaseYearTable.setItems(setPlayedYearData(true));
        playedCompletionYearTable.setItems(setPlayedYearData(false));
        playedRatingTable.setItems(setPlayedRatingData());
        playedPercent100Table.setItems(setPlayedPercentData());

        //Set unplayed game data
        unplayedFranchiseTable.setItems(setUnplayedFranchiseData());
        unplayedPlatformTable.setItems(setUnplayedPlatGenData(GameLists.platformList, true));
        unplayedGenreTable.setItems(setUnplayedPlatGenData(GameLists.genreList, false));
        unplayedReleaseYearTable.setItems(setUnplayedYearData());
        unplayedDeckTable.setItems(setUnplayedDeckData());

        TableMethods.updateColumnWidth(playedColumnList);
        TableMethods.updateColumnWidth(unplayedColumnList);
    }

    //Sets the data for the played short table
    public ObservableList<PlayedDataEntry> setPlayedShortData() {
        //Local variables
        String[] statuses = {"", "Yes", "No"};                                          //Potential short statuses
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<String, PlayedDataEntry> map = new HashMap<>();                         //Map of each short status

        for(String s : statuses){
            //Populate map with each status
            //Local variables
            PlayedDataEntry newData = new PlayedDataEntry();    //Data entry for the current status

            if(s.equals(""))
                //If the status is a blank string, name it Blank
                newData.setName("Blank");
            else
                //Otherwise, set it to the status
                newData.setName(s);

            //Count starts at 0
            newData.setCount(0);

            //Percent starts at 0
            newData.setPercent(0.0);

            map.put(s, newData);
            dataList.add(newData);
        }

        for(PlayedGame game : GameLists.playedList){
            //Accumulate data for each status
            //Local variables
            PlayedDataEntry data = map.get(game.getShortStatus());  //Data entry for the short status of the current game

            //Increment the count of the short status by one
            data.setCount(data.getCount()+1);

            if(game.getRating()!=0){
                //If there is a rating in the current game
                //Increment count of the games with ratings in the current short status
                data.setRatingCount(data.getRatingCount() + 1);

                //Add to the total rating
                data.setTotalRating(data.getTotalRating() + game.getRating());

                //Update the average rating
                data.setAverageRating(data.getTotalRating()*1.0 / data.getRatingCount());
            }

            //Set percent of games with the current short status out of the total
            data.setPercent(data.getCount() * 1.0 / GameLists.playedList.size() * 100);
        }

        return dataList;
    }

    //Sets the data in the played game franchise table.
    public ObservableList<PlayedDataEntry> setPlayedFranchiseData() {
        //Local variables
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<String, PlayedDataEntry> map = new HashMap<>();                         //Map of each franchise

        for(PlayedGame game : GameLists.playedList){
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
                data.setPercent(data.getCount() * 1.0 / GameLists.playedList.size() * 100);

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
                newData.setPercent(1.0 / GameLists.playedList.size() * 100);

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

    //Sets the data in the unplayed game franchise table.
    public ObservableList<UnplayedDataEntry> setUnplayedFranchiseData() {
        //Local variables
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();   //List to be returned
        HashMap<String, UnplayedDataEntry> map = new HashMap<>();                           //Map of every franchise

        for(UnplayedGame game : GameLists.unplayedList){
            //Iterate for each game

            if(map.containsKey(game.getFranchise())){
                //If the current game's franchise is already in the map, not the first occurrence
                //Local variables
                UnplayedDataEntry data = map.get(game.getFranchise());//Data entry for the franchise of the current game

                //Increment the count of the franchise by one
                data.setCount(data.getCount()+1);

                //Add to the total hours
                data.setTotalHours(data.getTotalHours()+game.getHours());

                //Set percent of games with the current franchise out of the total
                data.setPercent(data.getCount()*1.0 / GameLists.unplayedList.size() * 100);

            }else if(!game.getFranchise().equals("")){
                //If the current game's franchise is not in the map
                //Local variables
                UnplayedDataEntry newData = new UnplayedDataEntry();    //New data entry for the current franchise

                //Set the name of the franchise
                newData.setName(game.getFranchise());

                //Count should start at 1
                newData.setCount(1);

                //Total hours should be hours of the first game
                newData.setTotalHours(game.getHours());

                //Set initial percent
                newData.setPercent(1.0 / GameLists.unplayedList.size() * 100);

                //Add franchise to map
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

    //Sets the data in the played game platform table or genre table.
    public ObservableList<PlayedDataEntry> setPlayedPlatGenData(ObservableList<String> list, boolean platform) {
        //Local variables
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<String, PlayedDataEntry> map = new HashMap<>();                         //Map of every platform/genre

        for(String genPlat : list){
            //Populate the map with platforms or genres
            //Local variables
            PlayedDataEntry newData = new PlayedDataEntry();    //New data for the current genre or platform

            //Set item's name
            newData.setName(genPlat);

            //Count starts at 0
            newData.setCount(0);

            //Percent should start at 0
            newData.setPercent(0.0);

            //Add item to the map
            map.put(genPlat, newData);

            //Add item to the list
            dataList.add(newData);
        }

        for(PlayedGame game : GameLists.playedList){
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
            data.setPercent(data.getCount()*1.0 / GameLists.playedList.size() * 100);
        }

        return dataList;
    }

    //Sets the data for the unplayed game platform table or genre table.
    public ObservableList<UnplayedDataEntry> setUnplayedPlatGenData(ObservableList<String> list, boolean platform) {
        //Local variables
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();   //List to be returned
        HashMap<String, UnplayedDataEntry> map = new HashMap<>();                           //Map of every platform/genre

        for(String genPlat : list){
            //Populate the map with platforms or genres
            //Local variables
            UnplayedDataEntry newData = new UnplayedDataEntry();    //New data for the current genre or platform

            //Set item's name
            newData.setName(genPlat);

            //Count starts at 0
            newData.setCount(0);

            //Percent should start at 0
            newData.setPercent(0.0);

            //Add item to the map
            map.put(genPlat, newData);

            //Add item to the list
            dataList.add(newData);
        }

        for(UnplayedGame game : GameLists.unplayedList){
            //Accumulate data for each platform or genre
            //Local variables
            UnplayedDataEntry data; //Data for the platform/genre corresponding to the current game

            if(platform)
                //Setting platform data
                data = map.get(game.getPlatform());
            else
                //Setting genre data
                data = map.get(game.getGenre());

            //Increment count
            data.setCount(data.getCount()+1);

            //Add to total hours
            data.setTotalHours(data.getTotalHours() + game.getHours());

            //Update the percent
            data.setPercent(data.getCount()*1.0 / GameLists.unplayedList.size() * 100);
        }

        return dataList;
    }

    //Sets data in the played release year table or completion year table.
    public ObservableList<PlayedDataEntry> setPlayedYearData(boolean release) {
        //Local variables
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<Integer, PlayedDataEntry> map = new HashMap<>();                        //Map of every year

        for(PlayedGame game : GameLists.playedList){
            //Iterate for each game
            //Local variables
            int year;   //Year of the current game

            if(release)
                //Release year is selected
                year  = game.getReleaseYear();
            else
                //Completion year is selected
                year = game.getCompletionYear();


            if(map.containsKey(year)){
                //If the current game's year is already in the map, not the first occurrence
                //Local variables
                PlayedDataEntry data = map.get(year);   //data entry for the current year

                //increment count
                data.setCount(data.getCount()+1);

                if(game.getRating()!=0){
                    //Game has a rating
                    //Increment rating count
                    data.setRatingCount(data.getRatingCount() + 1);

                    //Add to the total rating
                    data.setTotalRating(data.getTotalRating() + game.getRating());

                    //Update the average rating
                    data.setAverageRating(data.getTotalRating() * 1.0 / data.getRatingCount());
                }

                //Update the percent
                data.setPercent(data.getCount()*1.0 / GameLists.playedList.size() * 100);

            }else{
                //Current game's year is not in the map
                //Local variables
                PlayedDataEntry newData = new PlayedDataEntry();    //New data entry for the game's year

                //Set name to year
                newData.setIntName(year);

                //Count starts at 1
                newData.setCount(1);

                //Set percent
                newData.setPercent(1.0 / GameLists.playedList.size() * 100);

                if(game.getRating()!=0){
                    //Game has a rating
                    //Total rating starts at the rating of the current game
                    newData.setTotalRating(game.getRating());

                    //Rating count starts at 1
                    newData.setRatingCount(1);

                    //average rating starts at the rating of the current game
                    newData.setAverageRating(game.getRating()*1.0);
                }

                //Add game to map
                map.put(year, newData);

                //Add year to dataList, keep dataList sorted
                if (dataList.isEmpty())
                    //If the dataList is empty, just add it
                    dataList.add(newData);
                else {
                    //There are items in the list of years
                    //Local variables
                    boolean placed = false; //Flag if the current item is placed

                    for (int i = 0; i < dataList.size(); i++)
                        //Iterate for each item in the list of years

                        if(dataList.get(i).getIntName() > year){
                            //Compare the value of the current year with one from the year list
                            dataList.add(i, newData);
                            placed = true;
                            break;
                        }

                    if(!placed)
                        //If the year was not placed, put it at the end of the list
                        dataList.add(newData);

                }
            }
        }

        return dataList;
    }

    //Sets the data for the unplayed release year table.
    public ObservableList<UnplayedDataEntry> setUnplayedYearData() {
        //Local variables
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();   //List to be returned
        HashMap<Integer, UnplayedDataEntry> map = new HashMap<>();                          //Map of every year

        for(UnplayedGame game : GameLists.unplayedList){
            //Iterate for each game
            //Local variables
            int year = game.getReleaseYear();   //Release year of the current game

            if(map.containsKey(year)){
                //If the current game's year is already in the map, not the first occurrence
                //Local variables
                UnplayedDataEntry data = map.get(year); //data entry for the current year

                //Increment count
                data.setCount(data.getCount()+1);

                //Add to total hours
                data.setTotalHours(data.getTotalHours() + game.getHours());

                //Update the percent
                data.setPercent(data.getCount()*1.0 / GameLists.unplayedList.size() * 100);
            }else{
                //Current game's year is not in the map
                //Local variables
                UnplayedDataEntry newData = new UnplayedDataEntry();    //New data entry for the game's year

                //Set name to year
                newData.setIntName(year);

                //Count starts at 1
                newData.setCount(1);

                //Set percent
                newData.setPercent(1.0 / GameLists.unplayedList.size() * 100);

                //Total hours starts at the hours of the current game
                newData.setTotalHours(game.getHours());

                //Add game to map
                map.put(year, newData);

                if (dataList.isEmpty())
                    //If the dataList is empty, just add it
                    dataList.add(newData);
                else {
                    //There are items in the list of years
                    //Local variables
                    boolean placed = false; //Flag if the current item is placed

                    for (int i = 0; i < dataList.size(); i++)
                        //Iterate for each item in the list of years

                        if(dataList.get(i).getIntName() > year){
                            //Compare the value of the current year with one from the year list
                            dataList.add(i, newData);
                            placed = true;
                            break;
                        }

                    if(!placed)
                        //If the year was not placed, put it at the end of the list
                        dataList.add(newData);

                }
            }
        }

        return dataList;
    }

    //Sets the data for the played rating table
    public ObservableList<PlayedDataEntry> setPlayedRatingData() {
        //Local variables
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList(); //List to be returned
        HashMap<Integer, PlayedDataEntry> map = new HashMap<>();                        //Map of each rating value

        for(int i = 0; i <=  10; i++){
            //Populate map with ratings 0-10
            //Local variables
            PlayedDataEntry newData = new PlayedDataEntry();    //Data entry for the current rating

            //Set rating name to the rating
            newData.setIntName(i);

            //Count starts at 0
            newData.setCount(0);

            //Percent starts at 0
            newData.setPercent(0.0);

            //Add to map
            map.put(i, newData);

            //Add to list
            dataList.add(newData);
        }

        for(PlayedGame game : GameLists.playedList){
            //Accumulate data for each rating
            //Local variables
            PlayedDataEntry data = map.get(game.getRating());   //data entry of the current game's rating

            //increment count
            data.setCount(data.getCount()+1);

            //update percent
            data.setPercent(data.getCount() * 1.0 / GameLists.playedList.size() * 100);
        }

        return dataList;
    }

    //Sets the data for the played percent100 table
    public ObservableList<PlayedDataEntry> setPlayedPercentData() {
        //Local variables
        String[] statuses = {"", "Yes", "No"};                                              //List of statuses
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();     //List to be returned
        HashMap<String, PlayedDataEntry> map = new HashMap<>();                             //Map of each status

        for(String s : statuses){
            //Populate map with each status
            //Local variables
            PlayedDataEntry newData = new PlayedDataEntry();    //Data entry for the current status

            if(s.equals(""))
                //If the status is a blank string, set the name to Blank
                newData.setName("Blank");
            else
                //Set the name to the status
                newData.setName(s);

            //Count starts at 0
            newData.setCount(0);

            //Percent starts at 0
            newData.setPercent(0.0);

            //Add data to map
            map.put(s, newData);

            //Add data to list
            dataList.add(newData);
        }

        for(PlayedGame game : GameLists.playedList){
            //Accumulate data for each status
            //Local variables
            PlayedDataEntry data = map.get(game.getPercent100());   //Data entry for the current game's percent status

            //increment the count
            data.setCount(data.getCount()+1);

            if(game.getRating()!=0){
                //Game has a rating
                //Increment the rating count
                data.setRatingCount(data.getRatingCount() + 1);

                //Add to the total rating
                data.setTotalRating(data.getTotalRating() + game.getRating());

                //Update the average rating
                data.setAverageRating(data.getTotalRating() * 1.0 / data.getRatingCount());
            }

            //Update the percent
            data.setPercent(data.getCount() * 1.0 / GameLists.playedList.size() * 100);
        }

        return dataList;
    }

    //Sets the data for the unplayed deck status table
    public ObservableList<UnplayedDataEntry> setUnplayedDeckData() {
        //Local variables
        String[] deckList = {"", "Yes", "No", "Maybe"};                                     //List of statuses
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();   //List to be returned
        HashMap<String, UnplayedDataEntry> map = new HashMap<>();                           //Map of each status

        for(String s : deckList){
            //Populate map with each status
            //Local variables
            UnplayedDataEntry newData = new UnplayedDataEntry();    //Data entry for the current status

            if(s.equals(""))
                //If the status is a blank string, set the name to Blank
                newData.setName("Blank");
            else
                //Set the name to the status
                newData.setName(s);

            //Count starts at 0
            newData.setCount(0);

            //Percent starts at 0
            newData.setPercent(0.0);

            //Add data to map
            map.put(s, newData);

            //Add data to list
            dataList.add(newData);
        }

        for(UnplayedGame game : GameLists.unplayedList){
            //Accumulate data for each status
            //Local variables
            UnplayedDataEntry data = map.get(game.getDeckCompatible()); //Data entry for the current game's deck status

            //Increment count
            data.setCount(data.getCount()+1);

            //Add to the total hours
            data.setTotalHours(data.getTotalHours() + game.getHours());

            //Update percent
            data.setPercent(data.getCount() * 1.0 / GameLists.unplayedList.size() * 100);
        }

        return dataList;
    }
}