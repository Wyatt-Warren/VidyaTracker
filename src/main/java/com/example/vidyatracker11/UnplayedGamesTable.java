package com.example.vidyatracker11;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
    TableColumn<UnplayedGame, Integer> releaseYearColumn = new TableColumn<>("Release Year");
    TableColumn<UnplayedGame, Integer> addedYearColumn = new TableColumn<>("Year Added");
    TableColumn<UnplayedGame, Double> hoursColumn = new TableColumn<>("Predicted Hours");
    TableColumn<UnplayedGame, String> deckColumn = new TableColumn<>("Deck Status");
    ObservableList<TableColumn<UnplayedGame, ?>> columnList = FXCollections.observableArrayList(
            statusColumn, titleColumn, franchiseColumn, platformColumn,
            genreColumn, releaseYearColumn, addedYearColumn, hoursColumn, deckColumn);

    //Fields
    ObservableList<UnplayedGame> baseList = FXCollections.observableArrayList(GameLists.unplayedList);  //List that other filters use as the base (set by advanced filters)
    FilteredList<UnplayedGame> filteredList = new FilteredList<>(baseList);                             //List of items in the table

    public UnplayedGamesTable() {
        //ValueFactory for each column
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        franchiseColumn.setCellValueFactory(new PropertyValueFactory<>("franchise"));
        platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        addedYearColumn.setCellValueFactory(new PropertyValueFactory<>("addedYear"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
        deckColumn.setCellValueFactory(new PropertyValueFactory<>("deckCompatible"));

        setPrefSize(900, 99999);
        getColumns().addAll(columnList);
        setItems(filteredList);
        TableMethods.preventColumnSorting(this);
        TableMethods.preventColumnResizing(this);
        TableMethods.preventColumnReordering(this);

        statusColumn.setCellFactory(e -> new TableCell<>() {
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

        releaseYearColumn.setCellFactory(e -> new TableCell<>() {
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

        addedYearColumn.setCellFactory(e -> new TableCell<>() {
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

        hoursColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {

                    if (item == 0)
                        //0 means no rating
                        setText("");
                    else {
                        //Set text
                        DecimalFormat decimalFormat = new DecimalFormat("0.##");
                        setText(decimalFormat.format(item));
                    }
                }
            }
        });

        deckColumn.setCellFactory(e -> new TableCell<>() {
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

        setRowFactory(tv -> {
            //Row factory
            //Local variables
            TableRow<UnplayedGame> row = new TableRow<>();  //The row

            row.setOnMouseClicked(event -> {
                //Mouse is clicked

                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {
                    //Mouse double-clicks a non-empty row
                    //Open edit game window
                    editGame(row.getItem());
                }
            });

            //Set right click menu
            row.setContextMenu(ApplicationGUI.rowContextMenu);

            return row;
        });
    }

    //Open a window for editing a game
    public void editGame(UnplayedGame game){
        //Local variables
        Stage stage = new Stage();
        UnplayedEditWindow window = new UnplayedEditWindow(game, stage);
        Scene scene = new Scene(window);

        //GUI
        stage.getIcons().add(ApplicationGUI.icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Edit Game Data");
        stage.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add(ApplicationGUI.styleSheet);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                //If escape is pressed, close window
                stage.close();
            }else if(e.getCode() == KeyCode.ENTER){
                //If enter is pressed, save and close window
                try {
                    window.saveAndQuit(stage);
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
            }
        });

        stage.show();
    }

    //Sorts and filters based on what items are selected in the ChoiceBoxes
    public void sortAndFilter(String filterToken) {
        try {
            if (filterToken.equals("Blank"))
            //If "Blank" is selected, the actual value is a blank string
                filterToken = "";
        }catch (NullPointerException ignored){
            //Sometimes the method is called when the value is null
            //It doesn't matter what filterToken is if it's null though
        }
        //Local variables
        final String finalFilterToken = filterToken;    //Predicates need a value that is final or effectively final

        switch (ApplicationGUI.unplayedFilterChoices.getSelectionModel().getSelectedIndex()) {
            //Switch for filter selection
            case 0:
                //Status
                filterByAny(unplayedGame ->
                        unplayedGame.getStatus().equals(finalFilterToken));
                break;
            case 1:
                //Franchise
                filterByAny(unplayedGame ->
                        unplayedGame.getFranchise().equals(finalFilterToken));
                break;
            case 2:
                //Platform
                filterByAny(unplayedGame ->
                        unplayedGame.getPlatform().equals(finalFilterToken));
                break;
            case 3:
                //Genre
                filterByAny(unplayedGame ->
                        unplayedGame.getGenre().equals(finalFilterToken));
                break;
            case 4:
                //Release Year
                try{
                    filterByAny(unplayedGame -> unplayedGame.getReleaseYear() ==
                            Integer.parseInt(finalFilterToken));
                }catch (NumberFormatException ignored){
                    //This shouldn't happen
                }
                break;
            case 5:
                //Release Year
                try{
                    filterByAny(unplayedGame -> unplayedGame.getAddedYear() ==
                            Integer.parseInt(finalFilterToken));
                }catch (NumberFormatException ignored){
                    //This shouldn't happen
                }
                break;
            case 6:
                //Deck Status
                filterByAny(unplayedGame ->
                        unplayedGame.getDeckCompatible().equals(finalFilterToken));
                break;
            case 7:
                //Collection
                filterByAny(unplayedGame -> unplayedGame.isInCollection(finalFilterToken));
                break;
            case 8:
                //Don't Filter
                unFilter();
                break;
        }
        switch (ApplicationGUI.unplayedSortChoices.getSelectionModel().getSelectedIndex()) {
            //Switch for sort selection
            case 0:
                //Status
                sortByAny(TableMethods.statusComparator);
                break;
            case 1:
                //Title
                setItems(new FilteredList<>(basicSort(filteredList, true)));
                break;
            case 2:
                //Title
                setItems(new FilteredList<>(basicSort(filteredList, false)));
                break;
            case 3:
                //Platform
                sortByAny(TableMethods.platformComparator);
                break;
            case 4:
                //Genre
                sortByAny(TableMethods.genreComparator);
                break;
            case 5:
                //Release Date
                sortByAny(TableMethods.releaseDateComparator);
                break;
            case 6:
                //Release Date
                sortByAny(TableMethods.addedDateComparator);
                break;
            case 7:
                //Hours
                sortByAny(TableMethods.hoursComparator);
                break;
            case 8:
                //Deck Status
                sortByAny(TableMethods.deckStatusComparator);
                break;
        }

        TableMethods.updateColumnWidth(columnList);
        refresh();
    }

    //Sort's by title, but franchises are grouped and sorted by release date
    public static ObservableList<UnplayedGame> basicSort(ObservableList<UnplayedGame> oldList, boolean title){
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList(oldList);
        //Sort by release date first
        newList.sort(TableMethods.releaseDateComparator);

        //Sort by title/franchise
        newList.sort((o1, o2) -> {
            //Local variables
            String sortBy1; //First item sort name
            String sortBy2; //Second item sort name

            if(o1.getFranchise().equals("") && title)
                //Get title if no franchise
                sortBy1 = o1.getTitle().toLowerCase();
            else
                //Get franchise if present
                sortBy1 = o1.getFranchise().toLowerCase();

            if(o2.getFranchise().equals("") && title)
                //Get title if no franchise
                sortBy2 = o2.getTitle().toLowerCase();
            else
                //Get franchise if present
                sortBy2 = o2.getFranchise().toLowerCase();

            if (sortBy1.startsWith("the "))
                //remove the
                sortBy1 = sortBy1.replace("the ", "");

            if (sortBy2.startsWith("the "))
                //remove the
                sortBy2 = sortBy2.replace("the ", "");

            return sortBy1.compareTo(sortBy2);
        });

        return newList;
    }

    //Sorts by basicSort and then by a given comparator
    public void sortByAny(Comparator<Game> comparator){
        //Local variables
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList(basicSort(filteredList, true));   //List to be sorted by the end, first sorted by basicSort

        newList.sort(comparator);
        setItems(newList);
    }

    //Filters the list based on a predicate
    public void filterByAny(Predicate<UnplayedGame> predicate){
        FilteredList<UnplayedGame> newList = new FilteredList<>(baseList, p -> true);
        newList.setPredicate(predicate);
        filteredList = new FilteredList<>(newList);
        setItems(filteredList);
    }

    //Removes filters
    public void unFilter() {
        filteredList = new FilteredList<>(baseList);
        setItems(filteredList);
    }
}
