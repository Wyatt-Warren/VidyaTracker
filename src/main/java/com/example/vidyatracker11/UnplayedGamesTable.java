package com.example.vidyatracker11;

import java.util.Collections;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UnplayedGamesTable extends TableView<UnplayedGame> {
    TableColumn<UnplayedGame, String> statusColumn = new TableColumn<>("Status");

    TableColumn<UnplayedGame, String> titleColumn = new TableColumn<>("Title");

    TableColumn<UnplayedGame, String> platformColumn = new TableColumn<>("Platform");

    TableColumn<UnplayedGame, String> genreColumn = new TableColumn<>("Genre");

    TableColumn<UnplayedGame, Double> hoursColumn = new TableColumn<>("Predicted Hours");

    TableColumn<UnplayedGame, String> deckColumn = new TableColumn<>("Deck Status");

    TableColumn<UnplayedGame, Integer> releaseYearColumn = new TableColumn<>("Release Year");

    ObservableList<TableColumn<UnplayedGame, ?>> columnList = FXCollections.observableArrayList(
            statusColumn, titleColumn, platformColumn, genreColumn, hoursColumn, deckColumn, releaseYearColumn);

    SortedList<UnplayedGame> sortedList = new SortedList<>(GameLists.unplayedList);

    FilteredList<UnplayedGame> filteredList = new FilteredList<>(sortedList);

    public UnplayedGamesTable() {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
        deckColumn.setCellValueFactory(new PropertyValueFactory<>("deckCompatible"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        for (TableColumn<UnplayedGame, ?> unplayedGameTableColumn : columnList)
            unplayedGameTableColumn.setSortable(false);
        setPrefSize(900, 99999);
        TableMethods.preventColumnReordering(this);
        setItems(sortedList);
        getColumns().addAll(columnList);
        statusColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (getItem().equals("Backlog")) {
                        setStyle("-fx-background-color: darkgray;");
                    } else if (getItem().equals("SubBacklog")) {
                        setStyle("-fx-background-color: lightgrey;");
                    } else {
                        setStyle("-fx-background-color: lightcoral;");
                    }
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
        deckColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (getItem().equals("Yes")) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else if (getItem().equals("No")) {
                        setStyle("-fx-background-color: lightcoral;");
                    } else if (getItem().equals("Maybe")) {
                        setStyle("-fx-background-color: gold;");
                    } else {
                        setStyle("");
                        setText("");
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
                }
            }
        });
        setRowFactory(tv -> {
            TableRow<UnplayedGame> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    UnplayedGame clickedRow = row.getItem();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
                    stage.setResizable(false);
                    UnplayedEditWindow window = new UnplayedEditWindow(clickedRow, stage);
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
                                window.saveAndQuit(clickedRow, stage);
                            } catch (NumberFormatException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            });
            return row;
        });
    }

    //Calls sort and filter methods based on the selected sort and filter options
    public void sortAndFilter(ChoiceBox<String> sortChoice, ChoiceBox<String> filterChoice) {
        switch (filterChoice.getSelectionModel().getSelectedIndex()) { //Filter first
            case 0: //Deck status yes
                filterByDeck("Yes");
                break;
            case 1: //Deck status no
                filterByDeck("No");
                break;
            case 2: //Deck status maybe
                filterByDeck("Maybe");
                break;
            case 3: //No Filter
                unFilter();
                break;
        }
        switch (sortChoice.getSelectionModel().getSelectedIndex()) { //Sort next
            case 0: //Title
                sortByTitle();
                break;
            case 1: //Platform
                sortByPlatform();
                break;
            case 2: //Genre
                sortByGenre();
                break;
            case 3: //Hours
                sortByHours();
                break;
            case 4: //Date
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
                String givenFranchise = unplayedGame.getFranchise().toLowerCase();
                String newFranchise = newList.get(j).getFranchise().toLowerCase();

                if (givenFranchise.startsWith("the "))
                    givenFranchise = givenFranchise.replace("the ", "");
                if (newFranchise.startsWith("the "))
                    newFranchise = newFranchise.replace("the ", "");

                String givenListsDate = String.format("%04d%02d%02d", unplayedGame.getReleaseYear(),
                        unplayedGame.getReleaseMonth(), unplayedGame.getReleaseDay());
                String newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                        newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
                String gameListFranchiseDate = givenFranchise + givenListsDate;
                String newListsFranchiseDate = newFranchise + newListsDate;
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
    public void sortByTitle() {
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
                String givenListsDate = String.format("%04d%02d%02d", unplayedGame.getReleaseYear(),
                        unplayedGame.getReleaseMonth(), unplayedGame.getReleaseDay());
                String newListsDate = String.format("%04d%02d%02d", newList.get(j).getReleaseYear(),
                        newList.get(j).getReleaseMonth(), newList.get(j).getReleaseDay());
                String gameListFranchiseDate = "" + givenListsDate + unplayedGame.getFranchise();
                String newListsFranchiseDate = "" + newListsDate + newList.get(j).getFranchise();
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

    //Filters by deck status
    public void filterByDeck(String filter) {
        FilteredList<UnplayedGame> newList = new FilteredList<>(GameLists.unplayedList, p -> true);
        newList.setPredicate(unplayedGame -> unplayedGame.getDeckCompatible().equals(filter));
        this.filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Removes filters
    public void unFilter() {
        this.filteredList = new FilteredList<>(GameLists.unplayedList);
        setItems(filteredList);
    }
}
