package com.example.vidyatracker11;

import java.util.Collections;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Window showing many tableviews of stats relating the game lists
public class StatsScreen extends HBox {
    //Played Games
    //Franchise
    TableColumn<PlayedDataEntry, String> playedFranchiseTitleColumn = new TableColumn<>("Franchise");
    TableColumn<PlayedDataEntry, Integer> playedFranchiseCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedFranchisePercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedFranchiseRatingColumn = new TableColumn<>("Average Rating");
    static TableView<PlayedDataEntry> playedFranchiseTable = new TableView<>();

    //Platform
    TableColumn<PlayedDataEntry, String> playedPlatformTitleColumn = new TableColumn<>("Platform");
    TableColumn<PlayedDataEntry, Integer> playedPlatformCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedPlatformPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedPlatformRatingColumn = new TableColumn<>("Average Rating");
    static TableView<PlayedDataEntry> playedPlatformTable = new TableView<>();

    //Genre
    TableColumn<PlayedDataEntry, String> playedGenreTitleColumn = new TableColumn<>("Genre");
    TableColumn<PlayedDataEntry, Integer> playedGenreCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedGenrePercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedGenreRatingColumn = new TableColumn<>("Average Rating");
    static TableView<PlayedDataEntry> playedGenreTable = new TableView<>();

    //Release Year
    TableColumn<PlayedDataEntry, Integer> playedReleaseYearTitleColumn = new TableColumn<>("Release Year");
    TableColumn<PlayedDataEntry, Integer> playedReleaseYearCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedReleaseYearPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedReleaseYearRatingColumn = new TableColumn<>("Average Rating");
    static TableView<PlayedDataEntry> playedReleaseYearTable = new TableView<>();

    //Completion Year
    TableColumn<PlayedDataEntry, Integer> playedCompletionYearTitleColumn = new TableColumn<>("Completion Year");
    TableColumn<PlayedDataEntry, Integer> playedCompletionYearCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedCompletionYearPercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedCompletionYearRatingColumn = new TableColumn<>("Average Rating");
    static TableView<PlayedDataEntry> playedCompletionYearTable = new TableView<>();

    //Rating
    TableColumn<PlayedDataEntry, Integer> playedRatingTitleColumn = new TableColumn<>("Rating");
    TableColumn<PlayedDataEntry, Integer> playedRatingCountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedRatingPercentColumn = new TableColumn<>("Percent");
    static TableView<PlayedDataEntry> playedRatingTable = new TableView<>();

    //100 Percent Status
    TableColumn<PlayedDataEntry, String> playedPercent100TitleColumn = new TableColumn<>("100% Status");
    TableColumn<PlayedDataEntry, Integer> playedPercent100CountColumn = new TableColumn<>("Count");
    TableColumn<PlayedDataEntry, Double> playedPercent100PercentColumn = new TableColumn<>("Percent");
    TableColumn<PlayedDataEntry, Double> playedPercent100RatingColumn = new TableColumn<>("Average Rating");
    static TableView<PlayedDataEntry> playedPercent100Table = new TableView<>();

    //Unplayed Games
    //Franchise
    TableColumn<UnplayedDataEntry, String> unplayedFranchiseTitleColumn = new TableColumn<>("Franchise");
    TableColumn<UnplayedDataEntry, Integer> unplayedFranchiseCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedFranchisePercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedFranchiseHoursColumn = new TableColumn<>("Total Hours");
    static TableView<UnplayedDataEntry> unplayedFranchiseTable = new TableView<>();

    //Platform
    TableColumn<UnplayedDataEntry, String> unplayedPlatformTitleColumn = new TableColumn<>("Platform");
    TableColumn<UnplayedDataEntry, Integer> unplayedPlatformCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedPlatformPercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedPlatformHoursColumn = new TableColumn<>("Total Hours");
    static TableView<UnplayedDataEntry> unplayedPlatformTable = new TableView<>();

    //Genre
    TableColumn<UnplayedDataEntry, String> unplayedGenreTitleColumn = new TableColumn<>("Genre");
    TableColumn<UnplayedDataEntry, Integer> unplayedGenreCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedGenrePercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedGenreHoursColumn = new TableColumn<>("Total Hours");
    static TableView<UnplayedDataEntry> unplayedGenreTable = new TableView<>();

    //Release Year
    TableColumn<UnplayedDataEntry, Integer> unplayedReleaseYearTitleColumn = new TableColumn<>("Release Year");
    TableColumn<UnplayedDataEntry, Integer> unplayedReleaseYearCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedReleaseYearPercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedReleaseYearHoursColumn = new TableColumn<>("Total Hours");
    static TableView<UnplayedDataEntry> unplayedReleaseYearTable = new TableView<>();

    //Deck Status
    TableColumn<UnplayedDataEntry, String> unplayedDeckTitleColumn = new TableColumn<>("Deck Status");
    TableColumn<UnplayedDataEntry, Integer> unplayedDeckCountColumn = new TableColumn<>("Count");
    TableColumn<UnplayedDataEntry, Double> unplayedDeckPercentColumn = new TableColumn<>("Percent");
    TableColumn<UnplayedDataEntry, Double> unplayedDeckHoursColumn = new TableColumn<>("Total Hours");
    static TableView<UnplayedDataEntry> unplayedDeckTable = new TableView<>();

    Label playedLabel = new Label("Played Games");

    Label unplayedLabel = new Label("Unplayed Games");

    ChoiceBox<String> playedChoices = new ChoiceBox<>();

    ChoiceBox<String> unplayedChoices = new ChoiceBox<>();

    VBox playedBox = new VBox(playedLabel, playedChoices, playedFranchiseTable);

    VBox unplayedBox = new VBox(unplayedLabel, unplayedChoices, unplayedFranchiseTable);

    ObservableList<TableColumn<PlayedDataEntry, ?>> playedColumnList = FXCollections.observableArrayList(
            playedFranchiseTitleColumn, playedFranchiseCountColumn, playedFranchisePercentColumn,
            playedFranchiseRatingColumn, playedPlatformTitleColumn, playedPlatformCountColumn,
            playedPlatformPercentColumn, playedPlatformRatingColumn, playedGenreTitleColumn,
            playedGenreCountColumn, playedGenrePercentColumn, playedGenreRatingColumn,
            playedReleaseYearTitleColumn, playedReleaseYearCountColumn, playedReleaseYearPercentColumn,
            playedReleaseYearRatingColumn, playedCompletionYearTitleColumn, playedCompletionYearCountColumn,
            playedCompletionYearPercentColumn, playedCompletionYearRatingColumn, playedRatingTitleColumn,
            playedRatingCountColumn, playedRatingPercentColumn, playedPercent100TitleColumn,
            playedPercent100CountColumn, playedPercent100PercentColumn, playedPercent100RatingColumn);

    ObservableList<TableColumn<UnplayedDataEntry, ?>> unplayedColumnList = FXCollections.observableArrayList(
            unplayedFranchiseTitleColumn, unplayedFranchiseCountColumn, unplayedFranchisePercentColumn,
            unplayedFranchiseHoursColumn, unplayedPlatformTitleColumn, unplayedPlatformCountColumn,
            unplayedPlatformPercentColumn, unplayedPlatformHoursColumn, unplayedGenreTitleColumn,
            unplayedGenreCountColumn, unplayedGenrePercentColumn, unplayedGenreHoursColumn,
            unplayedReleaseYearTitleColumn, unplayedReleaseYearCountColumn, unplayedReleaseYearPercentColumn,
            unplayedReleaseYearHoursColumn, unplayedDeckTitleColumn, unplayedDeckCountColumn,
            unplayedDeckPercentColumn, unplayedDeckHoursColumn);

    ObservableList<TableColumn<PlayedDataEntry, Double>> playedDoubleColumnList = FXCollections.observableArrayList(
            playedFranchisePercentColumn, playedFranchiseRatingColumn, playedPlatformPercentColumn,
            playedPlatformRatingColumn, playedGenrePercentColumn, playedGenreRatingColumn,
            playedReleaseYearPercentColumn, playedReleaseYearRatingColumn, playedCompletionYearPercentColumn,
            playedCompletionYearRatingColumn, playedRatingPercentColumn, playedPercent100PercentColumn,
            playedPercent100RatingColumn);

    ObservableList<TableColumn<UnplayedDataEntry, Double>> unplayedDoubleColumnList = FXCollections.observableArrayList(
            unplayedFranchisePercentColumn, unplayedFranchiseHoursColumn, unplayedPlatformPercentColumn,
            unplayedPlatformHoursColumn, unplayedGenrePercentColumn, unplayedGenreHoursColumn,
            unplayedReleaseYearPercentColumn, unplayedReleaseYearHoursColumn, unplayedDeckPercentColumn,
            unplayedDeckHoursColumn);

    public StatsScreen() {
        playedFranchiseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playedPlatformTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playedGenreTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playedReleaseYearTitleColumn.setCellValueFactory(new PropertyValueFactory<>("intName"));
        playedCompletionYearTitleColumn.setCellValueFactory(new PropertyValueFactory<>("intName"));
        playedRatingTitleColumn.setCellValueFactory(new PropertyValueFactory<>("intName"));
        playedPercent100TitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unplayedFranchiseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unplayedPlatformTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unplayedGenreTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unplayedReleaseYearTitleColumn.setCellValueFactory(new PropertyValueFactory<>("intName"));
        unplayedDeckTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playedFranchiseCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedPlatformCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedGenreCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedReleaseYearCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedCompletionYearCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedRatingCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedPercent100CountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        unplayedFranchiseCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        unplayedPlatformCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        unplayedGenreCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        unplayedReleaseYearCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        unplayedDeckCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        playedFranchisePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedPlatformPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedGenrePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedReleaseYearPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedCompletionYearPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedRatingPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedPercent100PercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        unplayedFranchisePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        unplayedPlatformPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        unplayedGenrePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        unplayedReleaseYearPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        unplayedDeckPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        playedFranchiseRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        playedPlatformRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        playedGenreRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        playedReleaseYearRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        playedCompletionYearRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        playedPercent100RatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        unplayedFranchiseHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        unplayedPlatformHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        unplayedGenreHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        unplayedReleaseYearHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        unplayedDeckHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));

        playedFranchiseTable.setPrefHeight(99999999);
        playedPlatformTable.setPrefHeight(99999999);
        playedGenreTable.setPrefHeight(99999999);
        playedReleaseYearTable.setPrefHeight(99999999);
        playedCompletionYearTable.setPrefHeight(99999999);
        playedRatingTable.setPrefHeight(99999999);
        playedPercent100Table.setPrefHeight(99999999);
        unplayedFranchiseTable.setPrefHeight(99999999);
        unplayedPlatformTable.setPrefHeight(99999999);
        unplayedGenreTable.setPrefHeight(99999999);
        unplayedReleaseYearTable.setPrefHeight(99999999);
        unplayedDeckTable.setPrefHeight(99999999);

        //For some reason it keeps adding columns every time the window is opened so this is a quick fix to just delete the columns first
        playedFranchiseTable.getColumns().clear();
        playedPlatformTable.getColumns().clear();
        playedGenreTable.getColumns().clear();
        playedReleaseYearTable.getColumns().clear();
        playedCompletionYearTable.getColumns().clear();
        playedRatingTable.getColumns().clear();
        playedPercent100Table.getColumns().clear();
        unplayedFranchiseTable.getColumns().clear();
        unplayedPlatformTable.getColumns().clear();
        unplayedGenreTable.getColumns().clear();
        unplayedReleaseYearTable.getColumns().clear();
        unplayedDeckTable.getColumns().clear();


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

        playedChoices.getItems().addAll("Franchise", "Platform", "Genre", "Release Year", "Completion Year", "Rating", "100% Status");
        unplayedChoices.getItems().addAll("Franchise", "Platform", "Genre", "Release Year", "Deck Status");

        playedChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            switch ((int) newNum){
                case 0: //Franchise
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedFranchiseTable);
                    break;
                case 1: //Platform
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedPlatformTable);
                    break;
                case 2: //Genre
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedGenreTable);
                    break;
                case 3: //Release Year
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedReleaseYearTable);
                    break;
                case 4: //Completion Year
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedCompletionYearTable);
                    break;
                case 5: //Rating
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedRatingTable);
                    break;
                case 6: //100% Status
                    playedBox.getChildren().remove(playedBox.getChildren().size()-1);
                    playedBox.getChildren().add(playedPercent100Table);
                    break;
            }
        });

        unplayedChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) -> {
            switch ((int) newNum){
                case 0: //Franchise
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedFranchiseTable);
                    break;
                case 1: //Platform
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedPlatformTable);
                    break;
                case 2: //Genre
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedGenreTable);
                    break;
                case 3: //Release Year
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedReleaseYearTable);
                    break;
                case 4: //Completion Year
                    unplayedBox.getChildren().remove(unplayedBox.getChildren().size()-1);
                    unplayedBox.getChildren().add(unplayedDeckTable);
                    break;
            }
        });

        playedChoices.getSelectionModel().selectFirst();
        unplayedChoices.getSelectionModel().selectFirst();

        playedBox.setAlignment(Pos.CENTER);
        unplayedBox.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        playedBox.setSpacing(5);
        unplayedBox.setSpacing(5);

        playedLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        unplayedLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
        getChildren().addAll(playedBox, unplayedBox);
        setSpacing(20);
        setPadding(new Insets(5));

        updateStats();
        StatsScreen.preventColumnReorderingOrResizingForAll();
        formatDoubleColumns();
    }

    public void formatDoubleColumns(){
        for(TableColumn<PlayedDataEntry, Double> column : playedDoubleColumnList){
            column.setCellFactory(col -> new TableCell<>() {
                @Override
                public void updateItem(Double someDouble, boolean empty){
                    super.updateItem(someDouble, empty);
                    if (empty || someDouble == null){
                        setText(null);
                    }else{
                        setText(String.format("%.2f", someDouble));
                    }
                }
            });
        }
        for(TableColumn<UnplayedDataEntry, Double> column : unplayedDoubleColumnList){
            column.setCellFactory(col -> new TableCell<>() {
                @Override
                public void updateItem(Double someDouble, boolean empty){
                    super.updateItem(someDouble, empty);
                    if (empty || someDouble == null){
                        setText(null);
                    }else{
                        setText(String.format("%.2f", someDouble));
                    }
                }
            });
        }
    }

    public static void preventColumnReorderingOrResizingForAll() {
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
    }

    //Refresh the data
    public void updateStats() {
        if (GameLists.playedList.size() != 0) {
            playedFranchiseTable.setItems(setPlayedFranchiseData());
            playedPlatformTable.setItems(setPlayedPlatformData(GameLists.platformList, true));
            playedGenreTable.setItems(setPlayedPlatformData(GameLists.genreList, false));
            playedReleaseYearTable.setItems(setPlayedYearData(true));
            playedCompletionYearTable.setItems(setPlayedYearData(false));
            playedRatingTable.setItems(setPlayedRatingData());
            playedPercent100Table.setItems(setPlayedPercentData());
        }
        if (GameLists.unplayedList.size() != 0) {
            unplayedFranchiseTable.setItems(setUnplayedFranchiseData());
            unplayedPlatformTable.setItems(setUnplayedPlatformData(GameLists.platformList, true));
            unplayedGenreTable.setItems(setUnplayedPlatformData(GameLists.genreList, false));
            unplayedReleaseYearTable.setItems(setUnplayedYearData());
            unplayedDeckTable.setItems(setUnplayedDeckData());
        }
        TableMethods.updateColumnWidth(playedColumnList);
        TableMethods.updateColumnWidth(unplayedColumnList);
    }

    //Sets the data in the played game franchise table or genre table.
    public ObservableList<PlayedDataEntry> setPlayedFranchiseData() {
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        HashMap<String, PlayedDataEntry> map = new HashMap<>();
        for(PlayedGame game : GameLists.playedList){
            if(map.containsKey(game.getFranchise())){
                PlayedDataEntry data = map.get(game.getFranchise());
                data.setCount(data.getCount()+1);
                if(game.getRating()!=0){
                    data.setRatingCount(data.getRatingCount()+1);
                    data.setTotalRating(data.getTotalRating()+game.getRating());
                    data.setAverageRating(data.getTotalRating()*1.0 / data.getRatingCount());
                }
                data.setPercent(data.getCount()*1.0 / GameLists.playedList.size() * 100);

            }else if(!game.getFranchise().equals("")){
                PlayedDataEntry newData = new PlayedDataEntry();
                newData.setName(game.getFranchise());
                newData.setCount(1);
                if(game.getRating()!=0){
                    newData.setRatingCount(1);
                    newData.setTotalRating(game.getRating());
                    newData.setAverageRating(newData.getTotalRating() * 1.0);
                }
                newData.setPercent(newData.getCount()*1.0 / GameLists.playedList.size() * 100);
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
                newData.setPercent(newData.getCount()*1.0 / GameLists.playedList.size() * 100);
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
    public ObservableList<PlayedDataEntry> setPlayedPlatformData(ObservableList<String> list, boolean platform) {
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        for (String s : list) {
            PlayedDataEntry newPlatGenre = new PlayedDataEntry();
            newPlatGenre.setName(s);
            double count = 0;
            int ratingCount = 0;
            double totalRating = 0;
            for (int j = 0; j < GameLists.playedList.size(); j++) {
                String compareString;
                if (platform) {
                    compareString = GameLists.playedList.get(j).getPlatform();
                } else {
                    compareString = GameLists.playedList.get(j).getGenre();
                }
                if (compareString.equals(s)) {
                    count++;
                    if (GameLists.playedList.get(j).getRating() != 0) {
                        ratingCount++;
                        totalRating += GameLists.playedList.get(j).getRating();
                    }
                }
            }
            newPlatGenre.setCount((int)count);
            newPlatGenre.setPercent(count / GameLists.playedList.size() * 100.0);
            if (ratingCount != 0)
                newPlatGenre.setAverageRating(totalRating / ratingCount);
            dataList.add(newPlatGenre);
        }
        return dataList;
    }

    //Sets the data for the unplayed game platform table or genre table.
    public ObservableList<UnplayedDataEntry> setUnplayedPlatformData(ObservableList<String> list, boolean platform) {
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        for (String s : list) {
            UnplayedDataEntry newPlatGenre = new UnplayedDataEntry();
            newPlatGenre.setName(s);
            double count = 0.0;
            double totalHours = 0.0;
            for (int j = 0; j < GameLists.unplayedList.size(); j++) {
                String compareString;
                if (platform) {
                    compareString = GameLists.unplayedList.get(j).getPlatform();
                } else {
                    compareString = GameLists.unplayedList.get(j).getGenre();
                }
                if (compareString.equals(s)) {
                    count++;
                    totalHours += GameLists.unplayedList.get(j).getHours();
                }
            }
            newPlatGenre.setCount((int)count);
            newPlatGenre.setPercent(count / GameLists.unplayedList.size() * 100.0);
            newPlatGenre.setTotalHours(totalHours);
            dataList.add(newPlatGenre);
        }
        return dataList;
    }

    //Sets data in the played release year table or completion year table.
    public ObservableList<PlayedDataEntry> setPlayedYearData(boolean release) {
        ObservableList<Integer> yearList = FXCollections.observableArrayList();
        if (release) {
            for (int i = 0; i < GameLists.playedList.size(); i++) {
                if (!yearList.contains(GameLists.playedList.get(i).getReleaseYear()))
                    yearList.add(GameLists.playedList.get(i).getReleaseYear());
            }
        } else {
            for (int i = 0; i < GameLists.playedList.size(); i++) {
                if (!yearList.contains(GameLists.playedList.get(i).getCompletionYear()))
                    yearList.add(GameLists.playedList.get(i).getCompletionYear());
            }
        }
        Collections.sort(yearList);
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        for (Integer integer : yearList) {
            PlayedDataEntry newYear = new PlayedDataEntry();
            newYear.setName("" + integer);
            double count = 0.0;
            int ratingCount = 0;
            double totalRating = 0.0;
            for (int j = 0; j < GameLists.playedList.size(); j++) {
                int compare;
                if (release) {
                    compare = GameLists.playedList.get(j).getReleaseYear();
                } else {
                    compare = GameLists.playedList.get(j).getCompletionYear();
                }
                if (compare == integer) {
                    count++;
                    if (GameLists.playedList.get(j).getRating() != 0) {
                        ratingCount++;
                        totalRating += GameLists.playedList.get(j).getRating();
                    }
                }
            }
            newYear.setCount((int)count);
            newYear.setPercent(count / GameLists.playedList.size() * 100.0);
            if (ratingCount != 0)
                newYear.setAverageRating(totalRating / ratingCount);
            dataList.add(newYear);
        }
        return dataList;
    }

    //Sets the data for the unplayed release year table.
    public ObservableList<UnplayedDataEntry> setUnplayedYearData() {
        ObservableList<Integer> yearList = FXCollections.observableArrayList();
        for (int i = 0; i < GameLists.unplayedList.size(); i++) {
            if (!yearList.contains(GameLists.unplayedList.get(i).getReleaseYear()))
                yearList.add(GameLists.unplayedList.get(i).getReleaseYear());
        }
        Collections.sort(yearList);
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        for (Integer integer : yearList) {
            UnplayedDataEntry newYear = new UnplayedDataEntry();
            newYear.setName("" + integer);
            double count = 0.0;
            double totalHours = 0.0;
            for (int j = 0; j < GameLists.unplayedList.size(); j++) {
                if (GameLists.unplayedList.get(j).getReleaseYear() == integer) {
                    count++;
                    totalHours += GameLists.unplayedList.get(j).getHours();
                }
            }
            newYear.setCount((int)count);
            newYear.setPercent(count / GameLists.unplayedList.size() * 100.0);
            newYear.setTotalHours(totalHours);
            dataList.add(newYear);
        }
        return dataList;
    }

    //Sets the data for the played rating table
    public ObservableList<PlayedDataEntry> setPlayedRatingData() {
        ObservableList<Integer> ratingList = FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        for (Integer integer : ratingList) {
            PlayedDataEntry newRating = new PlayedDataEntry();
            newRating.setName("" + integer);
            double count = 0.0;
            for (int j = 0; j < GameLists.playedList.size(); j++) {
                if (GameLists.playedList.get(j).getRating() == integer)
                    count++;
            }
            newRating.setCount((int)count);
            newRating.setPercent(count / GameLists.playedList.size() * 100.0);
            dataList.add(newRating);
        }
        return dataList;
    }

    //Sets the data for the played percent100 table
    public ObservableList<PlayedDataEntry> setPlayedPercentData() {
        ObservableList<String> percentList = FXCollections.observableArrayList("", "Yes", "No");
        ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
        for (String s : percentList) {
            PlayedDataEntry newPercent = new PlayedDataEntry();
            if (s.equals("")) {
                newPercent.setName("Blank");
            } else {
                newPercent.setName(s);
            }
            double count = 0.0;
            int ratingCount = 0;
            double totalRating = 0.0;
            for (int j = 0; j < GameLists.playedList.size(); j++) {
                if (GameLists.playedList.get(j).getPercent100().equals(s)) {
                    count++;
                    if (GameLists.playedList.get(j).getRating() != 0) {
                        ratingCount++;
                        totalRating += GameLists.playedList.get(j).getRating();
                    }
                }
            }
            newPercent.setCount((int)count);
            newPercent.setPercent(count / GameLists.playedList.size() * 100.0);
            if (ratingCount != 0)
                newPercent.setAverageRating(totalRating / ratingCount);
            dataList.add(newPercent);
        }
        return dataList;
    }

    //Sets the data for the unplayed deck status table
    public ObservableList<UnplayedDataEntry> setUnplayedDeckData() {
        ObservableList<String> deckList = FXCollections.observableArrayList("", "Yes", "No", "Maybe");
        ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
        for (String s : deckList) {
            UnplayedDataEntry newDeck = new UnplayedDataEntry();
            if (s.equals("")) {
                newDeck.setName("Blank");
            } else {
                newDeck.setName(s);
            }
            double count = 0.0;
            double totalHours = 0.0;
            for (int j = 0; j < GameLists.unplayedList.size(); j++) {
                if (GameLists.unplayedList.get(j).getDeckCompatible().equals(s)) {
                    count++;
                    totalHours += GameLists.unplayedList.get(j).getHours();
                }
            }
            newDeck.setCount((int)count);
            newDeck.setPercent(count / GameLists.unplayedList.size() * 100.0);
            newDeck.setTotalHours(totalHours);
            dataList.add(newDeck);
        }
        return dataList;
    }
}