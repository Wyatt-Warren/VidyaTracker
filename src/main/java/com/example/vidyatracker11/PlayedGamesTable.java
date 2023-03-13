package com.example.vidyatracker11;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Tableview on the main window that displays played games
public class PlayedGamesTable extends TableView<PlayedGame> {
    TableColumn<PlayedGame, String> statusColumn = new TableColumn<>("Status");

    TableColumn<PlayedGame, String> shortColumn = new TableColumn<>("Short?");

    TableColumn<PlayedGame, String> titleColumn = new TableColumn<>("Title");

    TableColumn<PlayedGame, String> franchiseColumn = new TableColumn<>("Franchise");

    TableColumn<PlayedGame, Integer> ratingColumn = new TableColumn<>("Rating");

    TableColumn<PlayedGame, String> platformColumn = new TableColumn<>("Platform");

    TableColumn<PlayedGame, String> genreColumn = new TableColumn<>("Genre");

    TableColumn<PlayedGame, Integer> releaseYearColumn = new TableColumn<>("Release Year");

    TableColumn<PlayedGame, Integer> completionYearColumn = new TableColumn<>("Completion Year");

    TableColumn<PlayedGame, String> percent100Column = new TableColumn<>("100%");

    SortedList<PlayedGame> sortedList = new SortedList<>(GameLists.playedList);

    FilteredList<PlayedGame> filteredList = new FilteredList<>(sortedList);

    ObservableList<TableColumn<PlayedGame, ?>> columnList = FXCollections.observableArrayList(
            statusColumn, shortColumn, titleColumn, franchiseColumn, ratingColumn, platformColumn,
            genreColumn, releaseYearColumn, completionYearColumn, percent100Column);

    private static final Date date = new Date();

    private static final ZoneId timeZone = ZoneId.systemDefault();

    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();

    public PlayedGamesTable() {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        shortColumn.setCellValueFactory(new PropertyValueFactory<>("isItShort"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        franchiseColumn.setCellValueFactory(new PropertyValueFactory<>("franchise"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        completionYearColumn.setCellValueFactory(new PropertyValueFactory<>("completionYear"));
        percent100Column.setCellValueFactory(new PropertyValueFactory<>("percent100"));

        for (TableColumn<PlayedGame, ?> playedGameTableColumn : columnList)
            playedGameTableColumn.setSortable(false);
        setPrefSize(900, 99999);
        TableMethods.preventColumnReordering(this);
        setItems(filteredList);
        getColumns().addAll(columnList);

        statusColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ((getItem()).equals("Playing")) {
                        setStyle("-fx-background-color: #4a8c32;");
                    } else if ((getItem()).equals("Completed")) {
                        setStyle("-fx-background-color: #225089;");
                    } else {
                        setStyle("-fx-background-color: #aa5825;");
                    }
                }
            }
        });

        shortColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ((getItem()).equals("Yes")) {
                        setStyle("-fx-background-color: #4a8c32;");
                    } else if ((getItem()).equals("No")) {
                        setStyle("-fx-background-color: #993737;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        ratingColumn.setCellFactory(e -> new TableCell<>() {
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

        percent100Column.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ((getItem()).equals("Yes")) {
                        setStyle("-fx-background-color: #4a8c32;");
                    } else if ((getItem()).equals("No")) {
                        setStyle("-fx-background-color: #993737;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        releaseYearColumn.setCellFactory(e -> new TableCell<>() {
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
                        setStyle("-fx-background-color: #4a8c32;");
                    } else if ((getItem()) == PlayedGamesTable.localDate.getYear() - 1) {
                        setStyle("-fx-background-color: #aa9120;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        completionYearColumn.setCellFactory(e -> new TableCell<>() {
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
                        setStyle("-fx-background-color: #4a8c32;");
                    } else if ((getItem()) == PlayedGamesTable.localDate.getYear() - 1) {
                        setStyle("-fx-background-color: #aa9120;");
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

                    editGame(row.getItem());
                }
            });
            row.setContextMenu(ApplicationGUI.rowContextMenu);
            return row;
        });
    }

    public void editGame(PlayedGame game){
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
        stage.setResizable(false);
        PlayedEditWindow window = new PlayedEditWindow(game, stage);
        Scene scene = new Scene(window);
        scene.getStylesheets().add(ApplicationGUI.styleSheet);
        stage.setScene(scene);
        stage.setTitle("Edit Game Data");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }else if(e.getCode() == KeyCode.ENTER){
                try{
                    window.saveAndQuit(game, stage);
                }catch (NumberFormatException e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    //Calls sort methods based on the selected sort and filter options
    //When an item is selected it tries to do sortAndFilter before setting the new value, so we just pass the variable
    //NewValue from the listener so it will use the right one. That's why filterToken is a parameter
    public void sortAndFilter(String filterToken) {
        try {
            if (filterToken.equals("Blank")) filterToken = "";
        }catch (NullPointerException ignored){
          //lol
        }
        final String finalFilterToken = filterToken;
        switch (ApplicationGUI.playedFilterChoices.getSelectionModel().getSelectedIndex()) { //Filter first
            case 0: //Status
                filterByAny(playedGame ->
                        playedGame.getStatus().equals(finalFilterToken));
                break;
            case 1: //Short
                filterByAny(playedGame ->
                        playedGame.getIsItShort().equals(finalFilterToken));
                break;
            case 2: //Franchise
                filterByAny(playedGame ->
                        playedGame.getFranchise().equals(finalFilterToken));
                break;
            case 3: //Rating
                try{
                    filterByAny(playedGame -> {
                        int intToken = Integer.parseInt(finalFilterToken);
                        return playedGame.getRating() == intToken;
                    });
                }catch (NumberFormatException ignored){
                    //lol
                }
                break;
            case 4: //Platform
                filterByAny(playedGame ->
                        playedGame.getPlatform().equals(finalFilterToken));
                break;
            case 5: //Genre
                filterByAny(playedGame ->
                        playedGame.getGenre().equals(finalFilterToken));
                break;
            case 6: //Release Year
                try{
                    filterByAny(playedGame -> {
                        int intToken = Integer.parseInt(finalFilterToken);
                        return playedGame.getReleaseYear()==intToken;
                    });
                }catch (NumberFormatException ignored){
                    //lol
                }
                break;
            case 7: //Completion Year
                try{
                    filterByAny(playedGame -> {
                        int intToken = Integer.parseInt(finalFilterToken);
                        return playedGame.getCompletionYear()==intToken;
                    });
                }catch (NumberFormatException ignored){
                    //lol
                }
                break;
            case 8: //100%
                filterByAny(playedGame ->
                        playedGame.getPercent100().equals(finalFilterToken));
                break;
            case 9: //Don't filter
                unFilter();
                break;
        }
        switch (ApplicationGUI.playedSortChoices.getSelectionModel().getSelectedIndex()) { //Sort next
            case 0://Status
                sortByStatus();
                break;
            case 1: //Title
                setItems(new FilteredList<>(normalSort(filteredList)));
                break;
            case 2: //Rating
                sortByRating();
                break;
            case 3: //Platform
                sortByPlatform();
                break;
            case 4: //Genre
                sortByGenre();
                break;
            case 5: //Release Date
                sortByDate(true);
                break;
            case 6: //Completion Date
                sortByDate(false);
                break;
        }
        TableMethods.updateColumnWidth(columnList);
        refresh();
    }

    //Sorts by franchise, within the same franchise, sorts by release date
    public ObservableList<PlayedGame> normalSort(ObservableList<PlayedGame> givenList) {
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList();

        for (PlayedGame playedGame : givenList) {
            boolean placed = false;

            for (int j = 0; j < newList.size(); j++) {
                String oldSortingName;
                if(playedGame.getFranchise().equals(""))
                    oldSortingName = playedGame.getTitle().toLowerCase();
                else
                    oldSortingName = playedGame.getFranchise().toLowerCase();

                String newSortingName;
                if(newList.get(j).getFranchise().equals(""))
                    newSortingName = newList.get(j).getTitle().toLowerCase();
                else
                    newSortingName = newList.get(j).getFranchise().toLowerCase();

                if (oldSortingName.startsWith("the "))
                    oldSortingName = oldSortingName.replace("the ", "");
                if (newSortingName.startsWith("the "))
                    newSortingName = newSortingName.replace("the ", "");

                String oldListsDate = String.format("%04d%02d%02d", playedGame.getReleaseYear(),
                        playedGame.getReleaseMonth(), playedGame.getReleaseDay());
                String newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                        newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
                String givenListFranchiseDate =  oldSortingName + oldListsDate;
                String newListsFranchiseDate = newSortingName + newListsDate;
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

    //Sorts by status first, in the order P, C, O, and then does normal sort.
    public void sortByStatus() {
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
        String[] statuses = { "Playing", "Completed", "On Hold" };
        for (String status : statuses) {
            ObservableList<PlayedGame> givenStatusList = FXCollections.observableArrayList();
            for (PlayedGame playedGame : filteredList) {
                if (playedGame.getStatus().equals(status))
                    givenStatusList.add(playedGame);
            }
            newList.addAll(normalSort(givenStatusList));
        }
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Sorts by rating and then normal sort
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

    //Sorts by platform and then normal sort
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

    //Sorts by genre and then normal sort
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
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Sorts by date and then franchise. Normal sort would be overkill since sorting by date within a franchise
    //doesn't make sense if it's already sorted by date
    public void sortByDate(boolean release) {
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList();
        ObservableList<PlayedGame> totalList = FXCollections.observableArrayList(filteredList);
        for (PlayedGame playedGame : totalList) {
            boolean placed = false;
            for (int j = 0; j < newList.size(); j++) {
                String oldFranchise;
                if(playedGame.getFranchise().equals(""))
                    oldFranchise = playedGame.getTitle().toLowerCase();
                else
                    oldFranchise = playedGame.getFranchise().toLowerCase();

                String newFranchise;
                if(newList.get(j).getFranchise().equals(""))
                    newFranchise = newList.get(j).getTitle().toLowerCase();
                else
                    newFranchise = newList.get(j).getFranchise().toLowerCase();

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
                String gameListFranchiseDate = "" + givenListsDate + oldFranchise;
                String newListsFranchiseDate = "" + newListsDate + newFranchise;
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

    //Filters the list based on a predicate
    public void filterByAny(Predicate<PlayedGame> predicate){
        FilteredList<PlayedGame> newList = new FilteredList<>(GameLists.playedList, p -> true);
        newList.setPredicate(predicate);
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Used if no filter is selected.
    public void unFilter() {
        filteredList = new FilteredList<>(GameLists.playedList);
        setItems(filteredList);
    }
}
