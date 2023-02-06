package com.example.vidyatracker11;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayedGamesTable extends TableView<PlayedGame> {
  TableColumn<PlayedGame, String> statusColumn = new TableColumn<>("Status");
  
  TableColumn<PlayedGame, String> shortColumn = new TableColumn<>("Short?");
  
  TableColumn<PlayedGame, String> titleColumn = new TableColumn<>("Title");
  
  TableColumn<PlayedGame, Integer> ratingColumn = new TableColumn<>("Rating");
  
  TableColumn<PlayedGame, String> platformColumn = new TableColumn<>("Platform");
  
  TableColumn<PlayedGame, String> genreColumn = new TableColumn<>("Genre");
  
  TableColumn<PlayedGame, Integer> releaseYearColumn = new TableColumn<>("Release Year");
  
  TableColumn<PlayedGame, Integer> completionYearColumn = new TableColumn<>("Completion Year");
  
  TableColumn<PlayedGame, String> percent100Column = new TableColumn<>("100%");
  
  SortedList<PlayedGame> sortedList = new SortedList<>(GameLists.playedList);
  
  FilteredList<PlayedGame> filteredList = new FilteredList<>(sortedList);
  
  ObservableList<TableColumn<PlayedGame, ?>> columnList = FXCollections.observableArrayList(
          statusColumn, shortColumn, titleColumn, ratingColumn, platformColumn, genreColumn,
          releaseYearColumn, completionYearColumn, percent100Column);
  
  private static final Date date = new Date();
  
  private static final ZoneId timeZone = ZoneId.systemDefault();
  
  private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();
  
  public PlayedGamesTable(ChoiceBox<String> sortChoiceBox, ChoiceBox<String> filterChoiceBox, StatusCountBoxPlayed statusCountBoxPlayed) {
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    shortColumn.setCellValueFactory(new PropertyValueFactory<>("isItShort"));
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
    platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
    genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
    releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
    completionYearColumn.setCellValueFactory(new PropertyValueFactory<>("completionYear"));
    percent100Column.setCellValueFactory(new PropertyValueFactory<>("percent100"));

    for (TableColumn<PlayedGame, ?> playedGameTableColumn : this.columnList)
      playedGameTableColumn.setSortable(false); 
    setPrefSize(900.0D, 99999.0D);
    preventColumnReorderingOrResizing(this);
    setItems(filteredList);
    getColumns().addAll(columnList);

    statusColumn.setCellFactory(e -> new TableCell<PlayedGame, String>() {
          public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText(null);
              setStyle("");
            } else {
              setText(item);
              if ((getItem()).equals("Playing")) {
                setStyle("-fx-background-color: lightgreen;");
              } else if ((getItem()).equals("Completed")) {
                setStyle("-fx-background-color: cornflowerblue;");
              } else {
                setStyle("-fx-background-color: sandybrown;");
              } 
            } 
          }
        });

    shortColumn.setCellFactory(e -> new TableCell<PlayedGame, String>() {
          public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText(null);
              setStyle("");
            } else {
              setText(item);
              if ((getItem()).equals("Yes")) {
                setStyle("-fx-background-color: lightgreen;");
              } else if ((getItem()).equals("No")) {
                setStyle("-fx-background-color: lightcoral;");
              } else {
                setStyle("");
              } 
            } 
          }
        });

    ratingColumn.setCellFactory(e -> new TableCell<PlayedGame, Integer>() {
          public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText(null);
              setStyle("");
            } else {
              setText("" + item);
              if (item == 0)
                setText(""); 
            } 
          }
        });

    percent100Column.setCellFactory(e -> new TableCell<PlayedGame, String>() {
          public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText(null);
              setStyle("");
            } else {
              setText(item);
              if ((getItem()).equals("Yes")) {
                setStyle("-fx-background-color: lightgreen;");
              } else if ((getItem()).equals("No")) {
                setStyle("-fx-background-color: lightcoral;");
              } else {
                setStyle("");
              } 
            } 
          }
        });

    releaseYearColumn.setCellFactory(e -> new TableCell<PlayedGame, Integer>() {
          public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText(null);
              setStyle("");
            } else {
              setText("" + item);
              if (item == 0)
                setText(""); 
            } 
          }
        });

    completionYearColumn.setCellFactory(e -> new TableCell<PlayedGame, Integer>() {
          public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
              setText(null);
              setStyle("");
            } else {
              setText("" + item);
              if (item == 0)
                setText(""); 
              if ((getItem()) == PlayedGamesTable.localDate.getYear()) {
                setStyle("-fx-background-color: lightgreen;");
              } else if ((getItem()) == PlayedGamesTable.localDate.getYear() - 1) {
                setStyle("-fx-background-color: gold;");
              } else {
                setStyle("");
              } 
            } 
          }
        });
    setRowFactory(tv -> {
          TableRow<PlayedGame> row = new TableRow<>();
          row.setOnMouseClicked(event -> {
            if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                    && event.getClickCount() == 2) {

              PlayedGame clickedRow = row.getItem();
              Stage stage = new Stage();
              stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
              stage.setResizable(false);
              PlayedEditWindow window = new PlayedEditWindow(clickedRow, stage, this, sortChoiceBox, filterChoiceBox, statusCountBoxPlayed);
              Scene scene = new Scene(window);
              stage.setScene(scene);
              stage.setTitle("Edit Game Data");
              stage.initModality(Modality.APPLICATION_MODAL);
              stage.show();
              scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                  stage.close();
                }else if(e.getCode() == KeyCode.ENTER){
                  try {
                    window.saveAndQuit(clickedRow, stage, this, sortChoiceBox, filterChoiceBox, statusCountBoxPlayed);
                  } catch (InvalidPercentException | InvalidStatusException | InvalidShortStatusException
                           | InvalidRatingException | InvalidPlatformException | InvalidGenreException
                           | InvalidYearException | InvalidMonthException | InvalidDayException e1) {
                    e1.printStackTrace();
                  }
                }
              });
            }
          });
          return row;
        });
  }
  
  public void updateColumnWidth() {
    for (int i = 0; i < columnList.size(); i++) {
      Text text = new Text(columnList.get(i).getText());
      double width = text.getLayoutBounds().getWidth() + 20.0D;
      for (int j = 0; j < filteredList.size(); j++) {
        if (i == 3 || i == 6 || i == 7) { //Rating, Release year, Completion year
          text = new Text(Integer.toString((int) columnList.get(i).getCellData(j)));
        } else {
          text = new Text((String) columnList.get(i).getCellData(j));
        } 
        double newWidth = text.getLayoutBounds().getWidth() + 20.0D;
        if (newWidth > width)
          width = newWidth; 
      } 
      columnList.get(i).setPrefWidth(width);
    } 
  }
  
  public void sortAndFilter(ChoiceBox<String> sortChoices, ChoiceBox<String> filterChoices) {
    switch (filterChoices.getSelectionModel().getSelectedIndex()) {
      case 0:
        filterByShort(true);
        break;
      case 1:
        filterByShort(false);
        break;
      case 2:
        filterBy100(true);
        break;
      case 3:
        filterBy100(false);
        break;
      case 4:
        unFilter();
        break;
    } 
    switch (sortChoices.getSelectionModel().getSelectedIndex()) {
      case 0:
        sortByTitle();
        break;
      case 1:
        sortByRating();
        break;
      case 2:
        sortByPlatform();
        break;
      case 3:
        sortByGenre();
        break;
      case 4:
        sortByDate(true);
        break;
      case 5:
        sortByDate(false);
        break;
    } 
    updateColumnWidth();
    refresh();
  }
  
  public static <T> void preventColumnReorderingOrResizing(TableView<T> tableView) {
    Platform.runLater(() -> {
          for (Node header : tableView.lookupAll(".column-header"))
            header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume); 
          for (Node resizeLine : tableView.lookupAll(".column-header-background"))
            resizeLine.addEventFilter(MouseEvent.ANY, Event::consume); 
        });
  }
  
  public ObservableList<PlayedGame> normalSort(ObservableList<PlayedGame> givenList) {
    ObservableList<PlayedGame> newList = FXCollections.observableArrayList();

    for (PlayedGame playedGame : givenList) {
      boolean placed = false;

      for (int j = 0; j < newList.size(); j++) {
        String givenFranchise = playedGame.getFranchise().toLowerCase();
        String newFranchise = newList.get(j).getFranchise().toLowerCase();

        if (givenFranchise.startsWith("the "))
          givenFranchise = givenFranchise.replace("the ", ""); 
        if (newFranchise.startsWith("the "))
          newFranchise = newFranchise.replace("the ", "");

        String givenListsDate = String.format("%04d%02d%02d", playedGame.getReleaseYear(),
                playedGame.getReleaseMonth(), playedGame.getReleaseDay());
        String newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
        String givenListFranchiseDate =  givenFranchise + givenListsDate;
        String newListsFranchiseDate = newFranchise + newListsDate;
        int comparedFranchiseNum = givenListFranchiseDate.compareTo(newListsFranchiseDate);
        if (comparedFranchiseNum < 0) {
          newList.add(j, playedGame);
          placed = true;
          break;
        }
      }

      if (!placed)
        newList.add(playedGame);
    } 
    return newList;
  }
  
  public void sortByTitle() {
    ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
    String[] statuses = { "Playing", "Completed", "On Hold" };
    for (String status : statuses) {
      ObservableList<PlayedGame> givenStatusList = FXCollections.observableArrayList();
      for (PlayedGame playedGame : this.filteredList) {
        if (playedGame.getStatus().equals(status))
          givenStatusList.add(playedGame); 
      } 
      newList.addAll(normalSort(givenStatusList));
    } 
    filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void sortByRating() {
    ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
    ObservableList<PlayedGame> totalList = FXCollections.observableArrayList(filteredList);
    for (int i = 10; i > 0; i--) {
      ObservableList<PlayedGame> givenRatingList = FXCollections.observableArrayList();
      for (PlayedGame playedGame : totalList) {
        if (playedGame.getRating() == i)
          givenRatingList.add(playedGame); 
      } 
      totalList.removeAll(givenRatingList);
      newList.addAll(normalSort(givenRatingList));
    } 
    newList.addAll(normalSort(totalList));
    filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void sortByPlatform() {
    ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
    ObservableList<PlayedGame> totalList = FXCollections.observableArrayList(filteredList);
    ObservableList<String> platList = GameLists.platformList;
    for (String s : platList) {
      ObservableList<PlayedGame> givenPlatformList = FXCollections.observableArrayList();
      for (PlayedGame playedGame : totalList) {
        if (playedGame.getPlatform().equals(s))
          givenPlatformList.add(playedGame); 
      } 
      totalList.removeAll(givenPlatformList);
      newList.addAll(normalSort(givenPlatformList));
    } 
    newList.addAll(normalSort(totalList));
    filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void sortByGenre() {
    ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
    ObservableList<PlayedGame> totalList = FXCollections.observableArrayList(filteredList);
    ObservableList<String> genreList = GameLists.genreList;
    for (String s : genreList) {
      ObservableList<PlayedGame> givenGenreList = FXCollections.observableArrayList();
      for (PlayedGame playedGame : totalList) {
        if (playedGame.getGenre().equals(s))
          givenGenreList.add(playedGame); 
      } 
      totalList.removeAll(givenGenreList);
      newList.addAll(normalSort(givenGenreList));
    } 
    newList.addAll(normalSort(totalList));
    this.filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void sortByDate(boolean release) {
    ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
    ObservableList<PlayedGame> totalList = FXCollections.observableArrayList(filteredList);
    for (PlayedGame playedGame : totalList) {
      boolean placed = false;
      for (int j = 0; j < newList.size(); j++) {
        String givenListsDate, newListsDate;
        if (release) {
          givenListsDate = String.format("%04d%02d%02d", playedGame.getReleaseYear(),
                  playedGame.getReleaseMonth(), playedGame.getReleaseDay());
          newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                  newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
        } else {
          givenListsDate = String.format("%04d%02d%02d", playedGame.getCompletionYear(),
                  playedGame.getCompletionMonth(), playedGame.getCompletionDay());
          newListsDate = String.format("%04d%02d%02d", newList.get(j).getCompletionYear(),
                  newList.get(j).getCompletionMonth(), newList.get(j).getCompletionDay());
        } 
        String gameListFranchiseDate = "" + givenListsDate + playedGame.getFranchise();
        String newListsFranchiseDate = "" + newListsDate + newList.get(j).getFranchise();
        int comparedFranchiseNum = gameListFranchiseDate.compareTo(newListsFranchiseDate);
        if (comparedFranchiseNum < 0) {
          newList.add(j, playedGame);
          placed = true;
          break;
        } 
      } 
      if (!placed)
        newList.add(playedGame);
    } 
    filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void filterByShort(boolean yesNo) {
    FilteredList<PlayedGame> newList = new FilteredList<>(GameLists.playedList, p -> true);
    newList.setPredicate(playedGame -> yesNo ? playedGame.getIsItShort().equals("Yes") : playedGame.getIsItShort().equals("No"));
    filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void filterBy100(boolean yesNo) {
    FilteredList<PlayedGame> newList = new FilteredList<>(GameLists.playedList, p -> true);
    newList.setPredicate(playedGame -> yesNo ? playedGame.getPercent100().equals("Yes") : playedGame.getPercent100().equals("No"));
    filteredList = new FilteredList<>(newList);
    setItems(filteredList);
  }
  
  public void unFilter() {
    filteredList = new FilteredList<>(GameLists.playedList);
    setItems(filteredList);
  }
}
