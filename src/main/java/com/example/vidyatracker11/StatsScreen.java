package com.example.vidyatracker11;

import java.util.Collections;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StatsScreen extends VBox {
  TableColumn<PlayedDataEntry, String> playedPlatformTitleColumn = new TableColumn<>("Platform");
  
  TableColumn<PlayedDataEntry, Integer> playedPlatformCountColumn = new TableColumn<>("Count");
  
  TableColumn<PlayedDataEntry, String> playedPlatformPercentColumn = new TableColumn<>("Percent");
  
  TableColumn<PlayedDataEntry, String> playedPlatformRatingColumn = new TableColumn<>("Average Rating");
  
  static TableView<PlayedDataEntry> playedPlatformTable = new TableView<>();
  
  TableColumn<PlayedDataEntry, String> playedGenreTitleColumn = new TableColumn<>("Genre");
  
  TableColumn<PlayedDataEntry, Integer> playedGenreCountColumn = new TableColumn<>("Count");
  
  TableColumn<PlayedDataEntry, String> playedGenrePercentColumn = new TableColumn<>("Percent");
  
  TableColumn<PlayedDataEntry, String> playedGenreRatingColumn = new TableColumn<>("Average Rating");
  
  static TableView<PlayedDataEntry> playedGenreTable = new TableView<>();
  
  TableColumn<PlayedDataEntry, String> playedReleaseYearTitleColumn = new TableColumn<>("Release Year");
  
  TableColumn<PlayedDataEntry, Integer> playedReleaseYearCountColumn = new TableColumn<>("Count");
  
  TableColumn<PlayedDataEntry, String> playedReleaseYearPercentColumn = new TableColumn<>("Percent");
  
  TableColumn<PlayedDataEntry, String> playedReleaseYearRatingColumn = new TableColumn<>("Average Rating");
  
  static TableView<PlayedDataEntry> playedReleaseYearTable = new TableView<>();
  
  TableColumn<PlayedDataEntry, String> playedCompletionYearTitleColumn = new TableColumn<>("Completion Year");
  
  TableColumn<PlayedDataEntry, Integer> playedCompletionYearCountColumn = new TableColumn<>("Count");
  
  TableColumn<PlayedDataEntry, String> playedCompletionYearPercentColumn = new TableColumn<>("Percent");
  
  TableColumn<PlayedDataEntry, String> playedCompletionYearRatingColumn = new TableColumn<>("Average Rating");
  
  static TableView<PlayedDataEntry> playedCompletionYearTable = new TableView<>();
  
  TableColumn<PlayedDataEntry, String> playedRatingTitleColumn = new TableColumn<>("Rating");
  
  TableColumn<PlayedDataEntry, Integer> playedRatingCountColumn = new TableColumn<>("Count");
  
  TableColumn<PlayedDataEntry, String> playedRatingPercentColumn = new TableColumn<>("Percent");
  
  static TableView<PlayedDataEntry> playedRatingTable = new TableView<>();
  
  TableColumn<PlayedDataEntry, String> playedPercent100TitleColumn = new TableColumn<>("100% Status");
  
  TableColumn<PlayedDataEntry, Integer> playedPercent100CountColumn = new TableColumn<>("Count");
  
  TableColumn<PlayedDataEntry, String> playedPercent100PercentColumn = new TableColumn<>("Percent");
  
  TableColumn<PlayedDataEntry, String> playedPercent100RatingColumn = new TableColumn<>("Average Rating");
  
  static TableView<PlayedDataEntry> playedPercent100Table = new TableView<>();
  
  TableColumn<UnplayedDataEntry, String> unplayedPlatformTitleColumn = new TableColumn<>("Platform");
  
  TableColumn<UnplayedDataEntry, Integer> unplayedPlatformCountColumn = new TableColumn<>("Count");
  
  TableColumn<UnplayedDataEntry, String> unplayedPlatformPercentColumn = new TableColumn<>("Percent");
  
  TableColumn<UnplayedDataEntry, String> unplayedPlatformHoursColumn = new TableColumn<>("Total Hours");
  
  static TableView<UnplayedDataEntry> unplayedPlatformTable = new TableView<>();
  
  TableColumn<UnplayedDataEntry, String> unplayedGenreTitleColumn = new TableColumn<>("Genre");
  
  TableColumn<UnplayedDataEntry, Integer> unplayedGenreCountColumn = new TableColumn<>("Count");
  
  TableColumn<UnplayedDataEntry, String> unplayedGenrePercentColumn = new TableColumn<>("Percent");
  
  TableColumn<UnplayedDataEntry, String> unplayedGenreHoursColumn = new TableColumn<>("Total Hours");
  
  static TableView<UnplayedDataEntry> unplayedGenreTable = new TableView<>();
  
  TableColumn<UnplayedDataEntry, String> unplayedReleaseYearTitleColumn = new TableColumn<>("Release Year");
  
  TableColumn<UnplayedDataEntry, Integer> unplayedReleaseYearCountColumn = new TableColumn<>("Count");
  
  TableColumn<UnplayedDataEntry, String> unplayedReleaseYearPercentColumn = new TableColumn<>("Percent");
  
  TableColumn<UnplayedDataEntry, String> unplayedReleaseYearHoursColumn = new TableColumn<>("Total Hours");
  
  static TableView<UnplayedDataEntry> unplayedReleaseYearTable = new TableView<>();
  
  TableColumn<UnplayedDataEntry, String> unplayedDeckTitleColumn = new TableColumn<>("Deck Status");
  
  TableColumn<UnplayedDataEntry, Integer> unplayedDeckCountColumn = new TableColumn<>("Count");
  
  TableColumn<UnplayedDataEntry, String> unplayedDeckPercentColumn = new TableColumn<>("Percent");
  
  TableColumn<UnplayedDataEntry, String> unplayedDeckHoursColumn = new TableColumn<>("Total Hours");
  
  static TableView<UnplayedDataEntry> unplayedDeckTable = new TableView<>();
  
  Label playedLabel = new Label("Played Games");
  
  Label unplayedLabel = new Label("Unplayed Games");
  
  HBox playedBox = new HBox(playedPlatformTable, playedGenreTable, playedReleaseYearTable,
          playedCompletionYearTable, playedRatingTable, playedPercent100Table);
  
  HBox unplayedBox = new HBox(unplayedPlatformTable, unplayedGenreTable, unplayedReleaseYearTable, unplayedDeckTable);
  
  ObservableList<TableColumn<?, ?>> columnList = FXCollections.observableArrayList(
          playedPlatformTitleColumn, playedPlatformCountColumn, playedPlatformPercentColumn,
          playedPlatformRatingColumn, playedGenreTitleColumn, playedGenreCountColumn,
          playedGenrePercentColumn, playedGenreRatingColumn, playedReleaseYearTitleColumn,
          playedReleaseYearCountColumn, playedReleaseYearPercentColumn, playedPlatformRatingColumn,
          playedCompletionYearTitleColumn, playedCompletionYearCountColumn, playedCompletionYearPercentColumn,
          playedCompletionYearRatingColumn, playedRatingTitleColumn, playedRatingCountColumn,
          playedRatingPercentColumn, playedPercent100TitleColumn, playedPercent100CountColumn,
          playedPercent100PercentColumn, playedPercent100RatingColumn, unplayedPlatformTitleColumn,
          unplayedPlatformCountColumn, unplayedPlatformPercentColumn, unplayedPlatformHoursColumn,
          unplayedGenreTitleColumn, unplayedGenreCountColumn, unplayedGenrePercentColumn,
          unplayedGenreHoursColumn, unplayedReleaseYearTitleColumn, unplayedReleaseYearCountColumn,
          unplayedReleaseYearPercentColumn, unplayedReleaseYearHoursColumn, unplayedDeckTitleColumn,
          unplayedDeckCountColumn, unplayedDeckPercentColumn, unplayedDeckHoursColumn);
  
  public StatsScreen() {
    playedPlatformTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    playedGenreTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    playedReleaseYearTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    playedCompletionYearTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    playedRatingTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    playedPercent100TitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    unplayedPlatformTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    unplayedGenreTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    unplayedReleaseYearTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    unplayedDeckTitleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    playedPlatformCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    playedGenreCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    playedReleaseYearCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    playedCompletionYearCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    playedRatingCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    playedPercent100CountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    unplayedPlatformCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    unplayedGenreCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    unplayedReleaseYearCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    unplayedDeckCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
    playedPlatformPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    playedGenrePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    playedReleaseYearPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    playedCompletionYearPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    playedRatingPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    playedPercent100PercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    unplayedPlatformPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    unplayedGenrePercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    unplayedReleaseYearPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    unplayedDeckPercentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
    playedPlatformRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
    playedGenreRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
    playedReleaseYearRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
    playedCompletionYearRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
    playedPercent100RatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
    unplayedPlatformHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
    unplayedGenreHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
    unplayedReleaseYearHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
    unplayedDeckHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));

    playedPlatformTable.getColumns().addAll(playedPlatformTitleColumn, playedPlatformCountColumn, playedPlatformPercentColumn, playedPlatformRatingColumn);
    playedGenreTable.getColumns().addAll(playedGenreTitleColumn, playedGenreCountColumn, playedGenrePercentColumn, playedGenreRatingColumn);
    playedReleaseYearTable.getColumns().addAll(playedReleaseYearTitleColumn, playedReleaseYearCountColumn, playedReleaseYearPercentColumn, playedReleaseYearRatingColumn);
    playedCompletionYearTable.getColumns().addAll(playedCompletionYearTitleColumn, playedCompletionYearCountColumn, playedCompletionYearPercentColumn, playedCompletionYearRatingColumn);
    playedRatingTable.getColumns().addAll(playedRatingTitleColumn, playedRatingCountColumn, playedRatingPercentColumn);
    playedPercent100Table.getColumns().addAll(playedPercent100TitleColumn, playedPercent100CountColumn, playedPercent100PercentColumn, playedPercent100RatingColumn);
    unplayedPlatformTable.getColumns().addAll(unplayedPlatformTitleColumn, unplayedPlatformCountColumn, unplayedPlatformPercentColumn, unplayedPlatformHoursColumn);
    unplayedGenreTable.getColumns().addAll(unplayedGenreTitleColumn, unplayedGenreCountColumn, unplayedGenrePercentColumn, unplayedGenreHoursColumn);
    unplayedReleaseYearTable.getColumns().addAll(unplayedReleaseYearTitleColumn, unplayedReleaseYearCountColumn, unplayedReleaseYearPercentColumn, unplayedReleaseYearHoursColumn);
    unplayedDeckTable.getColumns().addAll(unplayedDeckTitleColumn, unplayedDeckCountColumn, unplayedDeckPercentColumn, unplayedDeckHoursColumn);

    playedBox.setAlignment(Pos.CENTER);
    this.unplayedBox.setAlignment(Pos.CENTER);
    setAlignment(Pos.CENTER);
    this.playedLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
    this.unplayedLabel.setStyle("-fx-font-size: 16;-fx-font-weight: bold;");
    getChildren().addAll(playedLabel, this.playedBox, this.unplayedLabel, this.unplayedBox);
    setPadding(new Insets(5.0));
    updateStats();
  }
  
  public static void preventColumnReorderingOrResizingForAll() {
    preventColumnReorderingOrResizing(playedPlatformTable);
    preventColumnReorderingOrResizing(playedGenreTable);
    preventColumnReorderingOrResizing(playedReleaseYearTable);
    preventColumnReorderingOrResizing(playedCompletionYearTable);
    preventColumnReorderingOrResizing(playedRatingTable);
    preventColumnReorderingOrResizing(playedPercent100Table);
    preventColumnReorderingOrResizing(unplayedPlatformTable);
    preventColumnReorderingOrResizing(unplayedGenreTable);
    preventColumnReorderingOrResizing(unplayedReleaseYearTable);
    preventColumnReorderingOrResizing(unplayedDeckTable);
  }
  
  public void updateColumnWidth() {
    for (int i = 0; i < columnList.size(); i++) {
      Text text = new Text(columnList.get(i).getText());
      double width = text.getLayoutBounds().getWidth() + 20.0;
      for (int j = 0; j < (columnList.get(i)).getTableView().getItems().size(); j++) {
        ObservableList<Integer> intColIndexes = FXCollections.observableArrayList(1, 5, 9, 13, 17, 20, 24, 28, 32, 36);
        if (intColIndexes.contains(i)) {
          text = new Text(Integer.toString((int) columnList.get(i).getCellData(j)));
        } else {
          text = new Text((String) columnList.get(i).getCellData(j));
        } 
        double newWidth = text.getLayoutBounds().getWidth() + 20.0;
        if (newWidth > width)
          width = newWidth; 
      } 
      columnList.get(i).setPrefWidth(width);
    } 
  }
  
  public static <T> void preventColumnReorderingOrResizing(TableView<T> tableView) {
    Platform.runLater(() -> {
          for (Node header : tableView.lookupAll(".column-header"))
            header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume); 
          for (Node resizeLine : tableView.lookupAll(".column-header-background"))
            resizeLine.addEventFilter(MouseEvent.ANY, Event::consume); 
        });
  }
  
  public void updateStats() {
    if (GameLists.playedList.size() != 0) {
      playedPlatformTable.setItems(setPlayedPlatformData(GameLists.platformList, true));
      playedGenreTable.setItems(setPlayedPlatformData(GameLists.genreList, false));
      playedReleaseYearTable.setItems(setPlayedYearData(true));
      playedCompletionYearTable.setItems(setPlayedYearData(false));
      playedRatingTable.setItems(setPlayedRatingData());
      playedPercent100Table.setItems(setPlayedPercentData());
    } 
    if (GameLists.unplayedList.size() != 0) {
      unplayedPlatformTable.setItems(setUnplayedPlatformData(GameLists.platformList, true));
      unplayedGenreTable.setItems(setUnplayedPlatformData(GameLists.genreList, false));
      unplayedReleaseYearTable.setItems(setUnplayedYearData());
      unplayedDeckTable.setItems(setUnplayedToasterData());
    } 
    updateColumnWidth();
  }
  
  public ObservableList<PlayedDataEntry> setPlayedPlatformData(ObservableList<String> list, boolean platform) {
    ObservableList<PlayedDataEntry> dataList = FXCollections.observableArrayList();
    for (String s : list) {
      PlayedDataEntry newPlatGenre = new PlayedDataEntry();
      newPlatGenre.setName(s);
      double count = 0.0;
      int ratingCount = 0;
      double totalRating = 0.0;
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
      newPlatGenre.setPercent(String.format("%.2f", count / GameLists.playedList.size() * 100.0D));
      if (ratingCount != 0)
        newPlatGenre.setAverageRating(String.format("%.2f", totalRating / ratingCount));
      dataList.add(newPlatGenre);
    } 
    return dataList;
  }
  
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
      newPlatGenre.setPercent(String.format("%.2f", count / GameLists.unplayedList.size() * 100.0));
      newPlatGenre.setTotalHours(String.format("%.2f", totalHours));
      dataList.add(newPlatGenre);
    } 
    return dataList;
  }
  
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
      newYear.setPercent(String.format("%.2f", count / GameLists.playedList.size() * 100.0));
      if (ratingCount != 0)
        newYear.setAverageRating(String.format("%.2f", totalRating / ratingCount));
      dataList.add(newYear);
    } 
    return dataList;
  }
  
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
      newYear.setPercent(String.format("%.2f", count / GameLists.unplayedList.size() * 100.0));
      newYear.setTotalHours(String.format("%.2f", totalHours));
      dataList.add(newYear);
    } 
    return dataList;
  }
  
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
      newRating.setPercent(String.format("%.2f", count / GameLists.playedList.size() * 100.0));
      dataList.add(newRating);
    } 
    return dataList;
  }
  
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
      newPercent.setPercent(String.format("%.2f", count / GameLists.playedList.size() * 100.0));
      if (ratingCount != 0)
        newPercent.setAverageRating(String.format("%.2f", totalRating / ratingCount));
      dataList.add(newPercent);
    } 
    return dataList;
  }
  
  public ObservableList<UnplayedDataEntry> setUnplayedToasterData() {
    ObservableList<String> toasterList = FXCollections.observableArrayList("", "Yes", "No", "Maybe");
    ObservableList<UnplayedDataEntry> dataList = FXCollections.observableArrayList();
    for (String s : toasterList) {
      UnplayedDataEntry newToaster = new UnplayedDataEntry();
      if (s.equals("")) {
        newToaster.setName("Blank");
      } else {
        newToaster.setName(s);
      } 
      double count = 0.0;
      double totalHours = 0.0;
      for (int j = 0; j < GameLists.unplayedList.size(); j++) {
        if (GameLists.unplayedList.get(j).getDeckCompatible().equals(s)) {
          count++;
          totalHours += GameLists.unplayedList.get(j).getHours();
        } 
      } 
      newToaster.setCount((int)count);
      newToaster.setPercent(String.format("%.2f", count / GameLists.unplayedList.size() * 100.0));
      newToaster.setTotalHours(String.format("%.2f", totalHours));
      dataList.add(newToaster);
    } 
    return dataList;
  }
}