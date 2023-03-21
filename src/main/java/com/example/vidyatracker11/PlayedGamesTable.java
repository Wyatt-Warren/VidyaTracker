package com.example.vidyatracker11;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
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

//Tableview on the main window that displays played games
public class PlayedGamesTable extends TableView<PlayedGame> {
    //Columns
    TableColumn<PlayedGame, String> statusColumn = new TableColumn<>("Status");
    TableColumn<PlayedGame, String> shortColumn = new TableColumn<>("Short");
    TableColumn<PlayedGame, String> titleColumn = new TableColumn<>("Title");
    TableColumn<PlayedGame, String> franchiseColumn = new TableColumn<>("Franchise");
    TableColumn<PlayedGame, Integer> ratingColumn = new TableColumn<>("Rating");
    TableColumn<PlayedGame, String> platformColumn = new TableColumn<>("Platform");
    TableColumn<PlayedGame, String> genreColumn = new TableColumn<>("Genre");
    TableColumn<PlayedGame, Integer> releaseYearColumn = new TableColumn<>("Release Year");
    TableColumn<PlayedGame, Integer> completionYearColumn = new TableColumn<>("Completion Year");
    TableColumn<PlayedGame, String> percent100Column = new TableColumn<>("100%");
    ObservableList<TableColumn<PlayedGame, ?>> columnList = FXCollections.observableArrayList(
            statusColumn, shortColumn, titleColumn, franchiseColumn, ratingColumn, platformColumn,
            genreColumn, releaseYearColumn, completionYearColumn, percent100Column);

    //Comparators
    private static final Comparator<Game> statusComparator = new Comparator<>() {                   //Sort by status
        //Local variables
        final ObservableList<String> statuses = FXCollections.observableArrayList(  //List of possible statuses
                "Playing", "Completed", "On Hold",
                "Backlog", "SubBacklog", "Wishlist");
        @Override
        public int compare(Game o1, Game o2) {
            //Return a value according to position in statuses list
            return statuses.indexOf(o1.getStatus()) - statuses.indexOf(o2.getStatus());
        }
    };
    private static final Comparator<Game> shortStatusComparator = new Comparator<>() {              //Sort by short status
        //Local variables
        final ObservableList<String> statuses = FXCollections.observableArrayList(  //List of possible statuses
                "Yes", "No", "");
        @Override
        public int compare(Game o1, Game o2) {
            //Return a value according to position in statuses list
            //Local variables
            PlayedGame p1 = (PlayedGame) o1;    //Cast o1 to PlayedGame
            PlayedGame p2 = (PlayedGame) o2;    //Cast o2 to PlayedGame

            return statuses.indexOf(p1.getShortStatus()) - statuses.indexOf(p2.getShortStatus());
        }
    };
    private static final Comparator<Game> ratingComparator = (o1, o2) -> {                          //Sort by rating
        //Return rating of o1 - rating of o2
        //Local variables
        PlayedGame p1 = (PlayedGame) o1;    //Cast o1 to PlayedGame
        PlayedGame p2 = (PlayedGame) o2;    //Cast o2 to PlayedGame

        return p2.getRating() - p1.getRating();
    };
    private static final Comparator<Game> platformComparator =                                      //Sort by platform
            Comparator.comparingInt(o -> GameLists.platformList.indexOf(o.getPlatform()));
    private static final Comparator<Game> genreComparator =                                         //Sort by genre
            Comparator.comparingInt(o -> GameLists.genreList.indexOf(o.getGenre()));
    private static final Comparator<Game> releaseDateComparator = (o1, o2) -> {                     //Sort by release date
        //Local variables
        String sortBy1 = String.format("%04d%02d%02d", o1.getReleaseYear(), //String to sort with for o1
                o1.getReleaseMonth(), o1.getReleaseDay());
        String sortBy2= String.format("%04d%02d%02d", o2.getReleaseYear(),  //String to sort with for o2
                o2.getReleaseMonth(), o2.getReleaseDay());

        return sortBy1.compareTo(sortBy2);

    };
    private static final Comparator<Game> completionDateComparator = (o1, o2) -> {            //Sort by completion date
        //Local variables
        PlayedGame p1 = (PlayedGame) o1;                                        //Cast o1 to PlayedGame
        PlayedGame p2 = (PlayedGame) o2;                                        //Cast o2 to PlayedGame
        String sortBy1 = String.format("%04d%02d%02d", p1.getCompletionYear(),  //String to sort with for o1
                p1.getCompletionMonth(), p1.getCompletionDay());
        String sortBy2= String.format("%04d%02d%02d", p2.getCompletionYear(),   //String to sort with for o2
                p2.getCompletionMonth(), p2.getCompletionDay());

        return sortBy1.compareTo(sortBy2);
    };
    private static final Comparator<Game> percentComparator = new Comparator<>() {            //Sort by 100% status
        //Local variables
        final ObservableList<String> statuses = FXCollections.observableArrayList(  //List of possible statuses
                "Yes", "No", "");
        @Override
        public int compare(Game o1, Game o2) {
            //Return a value according to position in statuses list
            //Local variables
            PlayedGame p1 = (PlayedGame) o1;    //Cast o1 to PlayedGame
            PlayedGame p2 = (PlayedGame) o2;    //Cast o2 to PlayedGame

            return statuses.indexOf(p1.getPercent100()) - statuses.indexOf(p2.getPercent100());
        }
    };

    //Fields
    private static final Date date = new Date();
    private static final ZoneId timeZone = ZoneId.systemDefault();
    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate(); //Used to get current year
    FilteredList<PlayedGame> filteredList = new FilteredList<>(GameLists.playedList);           //List of items in the table

    public PlayedGamesTable() {
        //ValueFactory for each column
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        shortColumn.setCellValueFactory(new PropertyValueFactory<>("shortStatus"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        franchiseColumn.setCellValueFactory(new PropertyValueFactory<>("franchise"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        platformColumn.setCellValueFactory(new PropertyValueFactory<>("platform"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        completionYearColumn.setCellValueFactory(new PropertyValueFactory<>("completionYear"));
        percent100Column.setCellValueFactory(new PropertyValueFactory<>("percent100"));

        setPrefSize(900, 99999);
        getColumns().addAll(columnList);
        setItems(filteredList);
        TableMethods.preventColumnSorting(this);
        TableMethods.preventColumnResizing(this);
        TableMethods.preventColumnReordering(this);

        statusColumn.setCellFactory(e -> new TableCell<>() {
            //Status column cell factory
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

        shortColumn.setCellFactory(e -> new TableCell<>() {
            //Short status column cell factory
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

        ratingColumn.setCellFactory(e -> new TableCell<>() {
            //Rating column cell factory
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
                }
            }
        });

        percent100Column.setCellFactory(e -> new TableCell<>() {
            //100% status column cell factory
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
            //Release year column cell factory
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {

                    if (item == 0)
                        //0 means no year
                        setText("");
                    else
                        //Set text
                        setText("" + item);

                    if ((getItem()) == PlayedGamesTable.localDate.getYear()) {
                        //Current year color
                        setStyle(ApplicationGUI.colorMap.get("CURRENTYEAR"));
                    } else if ((getItem()) == PlayedGamesTable.localDate.getYear() - 1) {
                        //Last year color
                        setStyle(ApplicationGUI.colorMap.get("LASTYEAR"));
                    } else {
                        //Any other year color
                        setStyle("");
                    }
                }
            }
        });

        completionYearColumn.setCellFactory(e -> new TableCell<>() {
            //Release year column cell factory
            public void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //Cells where there is no game object
                    setText(null);
                    setStyle("");
                } else {

                    if (item == 0)
                        //0 means no year
                        setText("");
                    else
                        //Set text
                        setText("" + item);

                    if ((getItem()) == PlayedGamesTable.localDate.getYear()) {
                        //Current year color
                        setStyle(ApplicationGUI.colorMap.get("CURRENTYEAR"));
                    } else if ((getItem()) == PlayedGamesTable.localDate.getYear() - 1) {
                        //Last year color
                        setStyle(ApplicationGUI.colorMap.get("LASTYEAR"));
                    } else {
                        //Any other year color
                        setStyle("");
                    }
                }
            }
        });

        setRowFactory(tv -> {
            //Row factory
            //Local variables
            TableRow<PlayedGame> row = new TableRow<>();    //The row

            row.setOnMouseClicked(event -> {
                //Mouse is clicked

                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2)
                    //Mouse double-clicks a non-empty row
                    //Open edit game window
                    editGame(row.getItem());
            });

            //Set right click menu
            row.setContextMenu(ApplicationGUI.rowContextMenu);

            return row;
        });
    }

    //Open a window for editing a game
    public void editGame(PlayedGame game){
        //Local variables
        Stage stage = new Stage();
        PlayedEditWindow window = new PlayedEditWindow(game, stage);
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
                try{
                    window.saveAndQuit(stage);
                }catch (NumberFormatException e1){
                    e1.printStackTrace();
                }
            }
        });

        stage.show();
    }

    //Calls sort methods based on the selected sort and filter options
    //When an item is selected, it tries to do sortAndFilter before setting the new value, so we just pass the variable
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
                        playedGame.getShortStatus().equals(finalFilterToken));
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
                sortByAny(statusComparator);
                break;
            case 1: //Title
                setItems(new FilteredList<>(basicSort(filteredList)));
                break;
            case 2: //Rating
                sortByAny(ratingComparator);
                break;
            case 3: //Platform
                sortByAny(platformComparator);
                break;
            case 4: //Genre
                sortByAny(genreComparator);
                break;
            case 5: //Release Date
                sortByAny(releaseDateComparator);
                break;
            case 6: //Completion Date
                sortByAny(completionDateComparator);
                break;
        }
        TableMethods.updateColumnWidth(columnList);
        refresh();
    }

    //Sort's by title, but franchises are grouped and sorted by release date
    public static ObservableList<PlayedGame> basicSort(ObservableList<PlayedGame> oldList){
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList(oldList);
        //Sort by release date first
        newList.sort(releaseDateComparator);

        //Sort by title/franchise
        newList.sort((o1, o2) -> {
            //Local variables
            String sortBy1; //First item sort name
            String sortBy2; //Second item sort name

            if(o1.getFranchise().equals(""))
                //Get title if no franchise
                sortBy1 = o1.getTitle().toLowerCase();
            else
                //Get franchise if present
                sortBy1 = o1.getFranchise().toLowerCase();

            if(o2.getFranchise().equals(""))
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
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList(basicSort(filteredList));   //List to be sorted by the end, first sorted by basicSort

        newList.sort(comparator);
        setItems(newList);
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
