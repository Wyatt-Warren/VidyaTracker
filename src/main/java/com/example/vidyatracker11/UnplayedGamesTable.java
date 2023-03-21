package com.example.vidyatracker11;

import java.util.Collections;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Tableview on the main window that displays unplayed games
public class UnplayedGamesTable extends TableView<UnplayedGame> {
    //Columns
    TableColumn<UnplayedGame, String> statusColumn = new TableColumn<>("Status");
    TableColumn<UnplayedGame, String> titleColumn = new TableColumn<>("Title");
    TableColumn<UnplayedGame, String> franchiseColumn = new TableColumn<>("Franchise");
    TableColumn<UnplayedGame, String> platformColumn = new TableColumn<>("Platform");
    TableColumn<UnplayedGame, String> genreColumn = new TableColumn<>("Genre");
    TableColumn<UnplayedGame, Double> hoursColumn = new TableColumn<>("Predicted Hours");
    TableColumn<UnplayedGame, String> deckColumn = new TableColumn<>("Deck Status");
    TableColumn<UnplayedGame, Integer> releaseYearColumn = new TableColumn<>("Release Year");
    ObservableList<TableColumn<UnplayedGame, ?>> columnList = FXCollections.observableArrayList(
            statusColumn, titleColumn, franchiseColumn, platformColumn, genreColumn, hoursColumn,
            releaseYearColumn, deckColumn);
    SortedList<UnplayedGame> sortedList = new SortedList<>(GameLists.unplayedList);
    FilteredList<UnplayedGame> filteredList = new FilteredList<>(sortedList);

    public UnplayedGamesTable() {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        franchiseColumn.setCellValueFactory(new PropertyValueFactory<>("franchise"));
        platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        deckColumn.setCellValueFactory(new PropertyValueFactory<>("deckCompatible"));
        getColumns().addAll(columnList);
        TableMethods.preventColumnSorting(this);
        setPrefSize(900, 99999);
        TableMethods.preventColumnResizing(this);
        TableMethods.preventColumnReordering(this);
        setItems(sortedList);
        statusColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(ApplicationGUI.colorMap.get(item));
                }
            }
        });
        hoursColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("" + item);
                    if (item == 0.0)
                        setText("");
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
                }
            }
        });
        deckColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(ApplicationGUI.colorMap.get(item));
                }
            }
        });
        setRowFactory(tv -> {
            TableRow<UnplayedGame> row = new TableRow<>();
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

    public void editGame(UnplayedGame game){
        Stage stage = new Stage();
        stage.getIcons().add(ApplicationGUI.icon);
        stage.setResizable(false);
        UnplayedEditWindow window = new UnplayedEditWindow(game, stage);
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
                try {
                    window.saveAndQuit(game, stage);
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    //Calls sort and filter methods based on the selected sort and filter options
    //When an item is selected it tries to do sortAndFilter before setting the new value, so we just pass the variable
    //NewValue from the listener so it will use the right one. That's why filterToken is a parameter
    public void sortAndFilter(String filterToken) {
        try {
            if (filterToken.equals("Blank")) filterToken = "";
        }catch (NullPointerException ignored){
            //lol
        }
        final String finalFilterToken = filterToken;
        switch (ApplicationGUI.unplayedFilterChoices.getSelectionModel().getSelectedIndex()) { //Filter first
            case 0: //Status
                filterByAny(unplayedGame ->
                        unplayedGame.getStatus().equals(finalFilterToken));
                break;
            case 1: //Franchise
                filterByAny(unplayedGame ->
                        unplayedGame.getFranchise().equals(finalFilterToken));
                break;
            case 2: //Platform
                filterByAny(unplayedGame ->
                        unplayedGame.getPlatform().equals(finalFilterToken));
                break;
            case 3: //Genre
                filterByAny(unplayedGame ->
                        unplayedGame.getGenre().equals(finalFilterToken));
                break;
            case 4: //Deck Status
                filterByAny(unplayedGame ->
                        unplayedGame.getDeckCompatible().equals(finalFilterToken));
                break;
            case 5: //Release Year
                try{
                    filterByAny(playedGame -> {
                        int intToken = Integer.parseInt(finalFilterToken);
                        return playedGame.getReleaseYear() == intToken;
                    });
                }catch (NumberFormatException ignored){
                    //lol
                }
                break;
            case 6: //No Filter
                unFilter();
                break;
        }
        switch (ApplicationGUI.unplayedSortChoices.getSelectionModel().getSelectedIndex()) { //Sort next
            case 0:
                sortByStatus();
                break;
            case 1: //Title
                setItems(new FilteredList<>(normalSort(filteredList)));
                break;
            case 2: //Platform
                sortByPlatform();
                break;
            case 3: //Genre
                sortByGenre();
                break;
            case 4: //Hours
                sortByHours();
                break;
            case 5: //Date
                sortByDate();
                break;
        }
        TableMethods.updateColumnWidth(columnList);
        refresh();
    }

    //Sorts based on franchise, within the same franchise, sort by date
    public ObservableList<UnplayedGame> normalSort(ObservableList<UnplayedGame> givenList) {
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();

        for (UnplayedGame unplayedGame : givenList) {
            boolean placed = false;

            for (int j = 0; j < newList.size(); j++) {
                String oldSortingName;
                if(unplayedGame.getFranchise().equals(""))
                    oldSortingName = unplayedGame.getTitle().toLowerCase();
                else
                    oldSortingName = unplayedGame.getFranchise().toLowerCase();

                String newSortingName;
                if(newList.get(j).getFranchise().equals(""))
                    newSortingName = newList.get(j).getTitle().toLowerCase();
                else
                    newSortingName = newList.get(j).getFranchise().toLowerCase();

                if (oldSortingName.startsWith("the "))
                    oldSortingName = oldSortingName.replace("the ", "");
                if (newSortingName.startsWith("the "))
                    newSortingName = newSortingName.replace("the ", "");

                String oldListsDate = String.format("%04d%02d%02d", unplayedGame.getReleaseYear(),
                        unplayedGame.getReleaseMonth(), unplayedGame.getReleaseDay());
                String newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                        newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
                String gameListFranchiseDate = oldSortingName + oldListsDate;
                String newListsFranchiseDate = newSortingName + newListsDate;
                int comparedFranchiseNum = gameListFranchiseDate.compareTo(newListsFranchiseDate);
                if (comparedFranchiseNum < 0) {
                    newList.add(j, unplayedGame);
                    placed = true;
                    break;
                }
            }
            if (!placed)
                newList.add(unplayedGame);
        }
        return newList;
    }

    //Sort by title, then normal sort
    public void sortByStatus() {
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();
        String[] statuses = { "Backlog", "SubBacklog", "Wishlist" };
        for (String status : statuses) {
            ObservableList<UnplayedGame> givenStatusList = FXCollections.observableArrayList();
            for (UnplayedGame unplayedGame : filteredList) {
                if (unplayedGame.getStatus().equals(status))
                    givenStatusList.add(unplayedGame);
            }
            newList.addAll(normalSort(givenStatusList));
        }
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Sort by platform, then normal sort
    public void sortByPlatform() {
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();
        ObservableList<UnplayedGame> totalList = FXCollections.observableArrayList(filteredList);
        ObservableList<String> platList = GameLists.platformList;
        for (String s : platList) {
            ObservableList<UnplayedGame> givenPlatformList = FXCollections.observableArrayList();
            for (UnplayedGame unplayedGame : totalList) {
                if (unplayedGame.getPlatform().equals(s))
                    givenPlatformList.add(unplayedGame);
            }
            totalList.removeAll(givenPlatformList);
            newList.addAll(normalSort(givenPlatformList));
        }
        newList.addAll(normalSort(totalList));
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Sort by genre, then normal sort
    public void sortByGenre() {
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();
        ObservableList<UnplayedGame> totalList = FXCollections.observableArrayList(filteredList);
        ObservableList<String> genreList = GameLists.genreList;
        for (String s : genreList) {
            ObservableList<UnplayedGame> givenGenreList = FXCollections.observableArrayList();
            for (UnplayedGame unplayedGame : totalList) {
                if (unplayedGame.getGenre().equals(s))
                    givenGenreList.add(unplayedGame);
            }
            totalList.removeAll(givenGenreList);
            newList.addAll(normalSort(givenGenreList));
        }
        newList.addAll(normalSort(totalList));
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Sort by date then franchise. Normal sort is not needed if its already sorted by date.
    public void sortByDate() {
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();
        ObservableList<UnplayedGame> totalList = FXCollections.observableArrayList(filteredList);
        for (UnplayedGame unplayedGame : totalList) {
            boolean placed = false;
            for (int j = 0; j < newList.size(); j++) {
                String oldFranchise;
                if(unplayedGame.getFranchise().equals(""))
                    oldFranchise = unplayedGame.getTitle().toLowerCase();
                else
                    oldFranchise = unplayedGame.getFranchise().toLowerCase();

                String newFranchise;
                if(newList.get(j).getFranchise().equals(""))
                    newFranchise = newList.get(j).getTitle().toLowerCase();
                else
                    newFranchise = newList.get(j).getFranchise().toLowerCase();

                String givenListsDate = String.format("%04d%02d%02d", unplayedGame.getReleaseYear(),
                        unplayedGame.getReleaseMonth(), unplayedGame.getReleaseDay());
                String newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                        newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
                String gameListFranchiseDate = "" + givenListsDate + oldFranchise;
                String newListsFranchiseDate = "" + newListsDate + newFranchise;
                int comparedFranchiseNum = gameListFranchiseDate.compareTo(newListsFranchiseDate);
                if (comparedFranchiseNum < 0) {
                    newList.add(j, unplayedGame);
                    placed = true;
                    break;
                }
            }
            if (!placed)
                newList.add(unplayedGame);
        }
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Sorts by hours and then normal sort
    public void sortByHours() {
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();
        ObservableList<UnplayedGame> totalList = FXCollections.observableArrayList(filteredList);
        ObservableList<Double> hourList = FXCollections.observableArrayList();
        for (UnplayedGame unplayedGame : totalList) {
            if (!hourList.contains(unplayedGame.getHours()))
                hourList.add(unplayedGame.getHours());
        }
        Collections.sort(hourList);
        for (Double aDouble : hourList) {
            ObservableList<UnplayedGame> givenHourList = FXCollections.observableArrayList();
            for (UnplayedGame unplayedGame : totalList) {
                if (unplayedGame.getHours() == aDouble)
                    givenHourList.add(unplayedGame);
            }
            totalList.removeAll(givenHourList);
            newList.addAll(normalSort(givenHourList));
        }
        newList.addAll(normalSort(totalList));
        this.filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    public void filterByAny(Predicate<UnplayedGame> predicate){
        FilteredList<UnplayedGame> newList = new FilteredList<>(GameLists.unplayedList, p -> true);
        newList.setPredicate(predicate);
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Removes filters
    public void unFilter() {
        this.filteredList = new FilteredList<>(GameLists.unplayedList);
        setItems(filteredList);
    }
}
