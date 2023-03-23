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

    public StatsScreen(Stage parentStage) {

        //GUI
        playedShortTable.setMinHeight(600);
        playedFranchiseTable.setMinHeight(600);
        playedPlatformTable.setMinHeight(600);
        playedGenreTable.setMinHeight(600);
        playedReleaseYearTable.setMinHeight(600);
        playedCompletionYearTable.setMinHeight(600);
        playedRatingTable.setMinHeight(600);
        playedPercent100Table.setMinHeight(600);
        unplayedFranchiseTable.setMinHeight(600);
        unplayedPlatformTable.setMinHeight(600);
        unplayedGenreTable.setMinHeight(600);
        unplayedReleaseYearTable.setMinHeight(600);
        unplayedDeckTable.setMinHeight(600);
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
        TableMethods.preventColumnReordering(playedShortTable);
        TableMethods.preventColumnReordering(playedFranchiseTable);
        TableMethods.preventColumnReordering(playedPlatformTable);
        TableMethods.preventColumnReordering(playedGenreTable);
        TableMethods.preventColumnReordering(playedReleaseYearTable);
        TableMethods.preventColumnReordering(playedCompletionYearTable);
        TableMethods.preventColumnReordering(playedRatingTable);
        TableMethods.preventColumnReordering(playedPercent100Table);
        TableMethods.preventColumnReordering(unplayedFranchiseTable);
        TableMethods.preventColumnReordering(unplayedPlatformTable);
        TableMethods.preventColumnReordering(unplayedGenreTable);
        TableMethods.preventColumnReordering(unplayedReleaseYearTable);
        TableMethods.preventColumnReordering(unplayedDeckTable);

        //Resizing
        TableMethods.preventColumnResizing(playedShortTable);
        TableMethods.preventColumnResizing(playedFranchiseTable);
        TableMethods.preventColumnResizing(playedPlatformTable);
        TableMethods.preventColumnResizing(playedGenreTable);
        TableMethods.preventColumnResizing(playedReleaseYearTable);
        TableMethods.preventColumnResizing(playedCompletionYearTable);
        TableMethods.preventColumnResizing(playedRatingTable);
        TableMethods.preventColumnResizing(playedPercent100Table);
        TableMethods.preventColumnResizing(unplayedFranchiseTable);
        TableMethods.preventColumnResizing(unplayedPlatformTable);
        TableMethods.preventColumnResizing(unplayedGenreTable);
        TableMethods.preventColumnResizing(unplayedReleaseYearTable);
        TableMethods.preventColumnResizing(unplayedDeckTable);
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

    //Sets the data in the played game franchise table or genre table.
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

    //Sets the data in the played game franchise table or genre table.
    public ObservableList<UnplayedDataEntry> setUnplayedFranchiseData() {
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<String, UnplayedDataEntry> map = new HashMap<>();
        for(UnplayedGame game : GameLists.unplayedList){
            if(map.containsKey(game.getFranchise())){
                UnplayedDataEntry data = map.get(game.getFranchise());
                data.setCount(data.getCount()+1);
                data.setTotalHours(data.getTotalHours()+game.getHours());
                data.setPercent(data.getCount()*1.0 / GameLists.unplayedList.size() * 100);

            }else if(!game.getFranchise().equals("")){
                UnplayedDataEntry newData = new UnplayedDataEntry();
                newData.setName(game.getFranchise());
                newData.setCount(1);
                newData.setTotalHours(game.getHours());
                newData.setPercent(1.0 / GameLists.unplayedList.size() * 100);
                map.put(game.getFranchise(), newData);
                if (dataList.isEmpty()){
                    dataList.add(newData);
                }else {
                    boolean placed = false;
                    for (int i = 0; i < dataList.size(); i++) {
                        if(dataList.get(i).getName().toLowerCase().compareTo(newData.getName().toLowerCase()) > 0){
                            dataList.add(i, newData);
                            placed = true;
                            break;
                        }
                    }
                    if(!placed){
                        dataList.add(newData);
                    }
                }
            }
        }
        return dataList;
    }

    //Sets the data in the played game platform table or genre table.
    public ObservableList<PlayedDataEntry> setPlayedPlatGenData(ObservableList<String> list, boolean platform) {
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<String, PlayedDataEntry> map = new HashMap<>();

        //Populate the map with platforms or genres
        for(String genPlat : list){
            PlayedDataEntry newData = new PlayedDataEntry();
            newData.setName(genPlat);
            newData.setCount(0);
            newData.setPercent(0.0);
            map.put(genPlat, newData);
            dataList.add(newData);
        }

        //Accumulate data for each platform or genre
        for(PlayedGame game : GameLists.playedList){
            PlayedDataEntry data;
            if(platform){
                data = map.get(game.getPlatform());
            }else{
                data = map.get(game.getGenre());
            }
            data.setCount(data.getCount()+1);
            if(game.getRating()!=0){
                data.setRatingCount(data.getRatingCount()+1);
                data.setTotalRating(data.getTotalRating()+game.getRating());
                data.setAverageRating(data.getTotalRating()*1.0 / data.getRatingCount());
            }
            data.setPercent(data.getCount()*1.0 / GameLists.playedList.size() * 100);
        }
        return dataList;
    }

    //Sets the data for the unplayed game platform table or genre table.
    public ObservableList<UnplayedDataEntry> setUnplayedPlatGenData(ObservableList<String> list, boolean platform) {
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<String, UnplayedDataEntry> map = new HashMap<>();

        //Populate the map with platforms or genres
        for(String genPlat : list){
            UnplayedDataEntry newData = new UnplayedDataEntry();
            newData.setName(genPlat);
            newData.setCount(0);
            newData.setPercent(0.0);
            map.put(genPlat, newData);
            dataList.add(newData);
        }

        //Accumulate data for each platform or genre
        for(UnplayedGame game : GameLists.unplayedList){
            UnplayedDataEntry data;
            if(platform){
                data = map.get(game.getPlatform());
            }else{
                data = map.get(game.getGenre());
            }
            data.setCount(data.getCount()+1);
            data.setTotalHours(data.getTotalHours() + game.getHours());
            data.setPercent(data.getCount()*1.0 / GameLists.unplayedList.size() * 100);
        }
        return dataList;
    }

    //Sets data in the played release year table or completion year table.
    public ObservableList<PlayedDataEntry> setPlayedYearData(boolean release) {
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<Integer, PlayedDataEntry> map = new HashMap<>();

        for(PlayedGame game : GameLists.playedList){
            int year;
            if(release){
                year  = game.getReleaseYear();
            }else{
                year = game.getCompletionYear();
            }

            if(map.containsKey(year)){
                PlayedDataEntry data = map.get(year);
                data.setCount(data.getCount()+1);
                if(game.getRating()!=0){
                    data.setRatingCount(data.getRatingCount()+1);
                    data.setTotalRating(data.getTotalRating()+game.getRating());
                    data.setAverageRating(data.getTotalRating()*1.0 / data.getRatingCount());
                }
                data.setPercent(data.getCount()*1.0 / GameLists.playedList.size() * 100);

            }else{
                PlayedDataEntry newData = new PlayedDataEntry();
                newData.setIntName(year);
                newData.setCount(1);
                newData.setPercent(1.0 / GameLists.playedList.size() * 100);
                if(game.getRating()!=0){
                    newData.setTotalRating(game.getRating());
                    newData.setRatingCount(1);
                    newData.setAverageRating(game.getRating()*1.0);
                }
                map.put(year, newData);
                if (dataList.isEmpty()){
                    dataList.add(newData);
                }else {
                    boolean placed = false;
                    for (int i = 0; i < dataList.size(); i++) {
                        if(dataList.get(i).getIntName() > year){
                            dataList.add(i, newData);
                            placed = true;
                            break;
                        }
                    }
                    if(!placed){
                        dataList.add(newData);
                    }
                }
            }
        }
        return dataList;
    }

    //Sets the data for the unplayed release year table.
    public ObservableList<UnplayedDataEntry> setUnplayedYearData() {
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<Integer, UnplayedDataEntry> map = new HashMap<>();

        for(UnplayedGame game : GameLists.unplayedList){
            int year = game.getReleaseYear();

            if(map.containsKey(year)){
                UnplayedDataEntry data = map.get(year);
                data.setCount(data.getCount()+1);
                data.setTotalHours(data.getTotalHours() + game.getHours());
                data.setPercent(data.getCount()*1.0 / GameLists.unplayedList.size() * 100);

            }else{
                UnplayedDataEntry newData = new UnplayedDataEntry();
                newData.setIntName(year);
                newData.setCount(1);
                newData.setPercent(1.0 / GameLists.unplayedList.size() * 100);
                newData.setTotalHours(game.getHours());
                map.put(year, newData);
                if (dataList.isEmpty()){
                    dataList.add(newData);
                }else {
                    boolean placed = false;
                    for (int i = 0; i < dataList.size(); i++) {
                        if(dataList.get(i).getIntName() > year){
                            dataList.add(i, newData);
                            placed = true;
                            break;
                        }
                    }
                    if(!placed){
                        dataList.add(newData);
                    }
                }
            }
        }
        return dataList;
    }

    //Sets the data for the played rating table
    public ObservableList<PlayedDataEntry> setPlayedRatingData() {
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<Integer, PlayedDataEntry> map = new HashMap<>();

        //Populate map with ratings 0-10
        for(int i = 0; i <=  10; i++){
            PlayedDataEntry newData = new PlayedDataEntry();
            newData.setIntName(i);
            newData.setCount(0);
            newData.setPercent(0.0);
            map.put(i, newData);
            dataList.add(newData);
        }

        //Accumulate data for each rating
        for(PlayedGame game : GameLists.playedList){
            PlayedDataEntry data = map.get(game.getRating());
            data.setCount(data.getCount()+1);
            data.setPercent(data.getCount() * 1.0 / GameLists.playedList.size() * 100);
        }

        return dataList;
    }

    //Sets the data for the played percent100 table
    public ObservableList<PlayedDataEntry> setPlayedPercentData() {
        String[] statuses = {"", "Yes", "No"};
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<String, PlayedDataEntry> map = new HashMap<>();

        //Populate map with each status
        for(String s : statuses){
            PlayedDataEntry newData = new PlayedDataEntry();
            if(s.equals("")){
                newData.setName("Blank");
            }else {
                newData.setName(s);
            }
            newData.setCount(0);
            newData.setPercent(0.0);
            map.put(s, newData);
            dataList.add(newData);
        }

        //Accumulate data for each status
        for(PlayedGame game : GameLists.playedList){
            PlayedDataEntry data = map.get(game.getPercent100());
            data.setCount(data.getCount()+1);
            if(game.getRating()!=0){
                data.setRatingCount(data.getRatingCount() + 1);
                data.setTotalRating(data.getTotalRating() + game.getRating());
                data.setAverageRating(data.getTotalRating()*1.0 / data.getRatingCount());
            }
            data.setPercent(data.getCount() * 1.0 / GameLists.playedList.size() * 100);
        }

        return dataList;
    }

    //Sets the data for the unplayed deck status table
    public ObservableList<UnplayedDataEntry> setUnplayedDeckData() {
        ObservableList<String> deckList = FXCollections.observableArrayList("", "Yes", "No", "Maybe");
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<String, UnplayedDataEntry> map = new HashMap<>();

        //Populate map with each status
        for(String s : deckList){
            UnplayedDataEntry newData = new UnplayedDataEntry();
            if(s.equals("")){
                newData.setName("Blank");
            }else {
                newData.setName(s);
            }
            newData.setCount(0);
            newData.setPercent(0.0);
            map.put(s, newData);
            dataList.add(newData);
        }

        //Accumulate data for each status
        for(UnplayedGame game : GameLists.unplayedList){
            UnplayedDataEntry data = map.get(game.getDeckCompatible());
            data.setCount(data.getCount()+1);
            data.setTotalHours(data.getTotalHours() + game.getHours());
            data.setPercent(data.getCount() * 1.0 / GameLists.unplayedList.size() * 100);
        }

        return dataList;
    }
}