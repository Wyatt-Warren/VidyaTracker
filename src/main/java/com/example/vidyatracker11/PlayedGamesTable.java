package com.example.vidyatracker11;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

//Tableview on the main window that displays played games
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

    public PlayedGamesTable() {
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        shortColumn.setCellValueFactory(new PropertyValueFactory<>("isItShort"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
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
                        setStyle("-fx-background-color: lightgreen;");
                    } else if ((getItem()).equals("Completed")) {
                        setStyle("-fx-background-color: cornflowerblue;");
                    } else {
                        setStyle("-fx-background-color: sandybrown;");
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
                        setStyle("-fx-background-color: lightgreen;");
                    } else if ((getItem()).equals("No")) {
                        setStyle("-fx-background-color: lightcoral;");
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
                        setStyle("-fx-background-color: lightgreen;");
                    } else if ((getItem()).equals("No")) {
                        setStyle("-fx-background-color: lightcoral;");
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
                    PlayedEditWindow window = new PlayedEditWindow(clickedRow, stage);
                    Scene scene = new Scene(window);
                    stage.setScene(scene);
                    stage.setTitle("Edit Game Data");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                    scene.setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.ESCAPE) {
                            stage.close();
                        }else if(e.getCode() == KeyCode.ENTER){
                            try{
                                window.saveAndQuit(clickedRow, stage);
                            }catch (NumberFormatException e1){
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            });
            return row;
        });
    }

    //Calls sort methods based on the selected sort and filter options
    public void sortAndFilter(ChoiceBox<String> sortChoices, ChoiceBox<String> filterChoices) {
        switch (filterChoices.getSelectionModel().getSelectedIndex()) { //Filter first
            case 0: //Short yes
                filterByShort(true);
                break;
            case 1: //Short no
                filterByShort(false);
                break;
            case 2: //100 yes
                filterBy100(true);
                break;
            case 3: //100 no
                filterBy100(false);
                break;
            case 4: //Don't filter
                unFilter();
                break;
        }
        switch (sortChoices.getSelectionModel().getSelectedIndex()) { //Sort next
            case 0: //Title
                sortByTitle();
                break;
            case 1: //Rating
                sortByRating();
                break;
            case 2: //Platform
                sortByPlatform();
                break;
            case 3: //Genre
                sortByGenre();
                break;
            case 4: //Release Date
                sortByDate(true);
                break;
            case 5: //Completion Date
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

    //Sorts by status first, in the order P, C, O, and then does normal sort.
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

    //Filters the list based on whether short yes or short no is selected
    public void filterByShort(boolean yesNo) {
        FilteredList<PlayedGame> newList = new FilteredList<>(GameLists.playedList, p -> true);
        newList.setPredicate(playedGame -> yesNo ? playedGame.getIsItShort().equals("Yes") : playedGame.getIsItShort().equals("No"));
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Filters the list based on whether 100 yes or 100 no is selected
    public void filterBy100(boolean yesNo) {
        FilteredList<PlayedGame> newList = new FilteredList<>(GameLists.playedList, p -> true);
        newList.setPredicate(playedGame -> yesNo ? playedGame.getPercent100().equals("Yes") : playedGame.getPercent100().equals("No"));
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Used if no filter is selected.
    public void unFilter() {
        filteredList = new FilteredList<>(GameLists.playedList);
        setItems(filteredList);
    }
}
