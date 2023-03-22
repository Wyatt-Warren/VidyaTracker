package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.util.Comparator;

public class TableMethods {
    //Comparators
    public static final Comparator<Game> statusComparator = new Comparator<>() {                   //Sort by status
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
    public static final Comparator<Game> shortStatusComparator = new Comparator<>() {              //Sort by short status
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
    public static final Comparator<Game> ratingComparator = (o1, o2) -> {                          //Sort by rating
        //Return rating of o1 - rating of o2
        //Local variables
        PlayedGame p1 = (PlayedGame) o1;    //Cast o1 to PlayedGame
        PlayedGame p2 = (PlayedGame) o2;    //Cast o2 to PlayedGame

        return p2.getRating() - p1.getRating();
    };
    public static final Comparator<Game> platformComparator =                                      //Sort by platform
            Comparator.comparingInt(o -> GameLists.platformList.indexOf(o.getPlatform()));
    public static final Comparator<Game> genreComparator =                                         //Sort by genre
            Comparator.comparingInt(o -> GameLists.genreList.indexOf(o.getGenre()));
    public static final Comparator<Game> releaseDateComparator = (o1, o2) -> {                     //Sort by release date
        //Local variables
        String sortBy1 = String.format("%04d%02d%02d", o1.getReleaseYear(), //String to sort with for o1
                o1.getReleaseMonth(), o1.getReleaseDay());
        String sortBy2= String.format("%04d%02d%02d", o2.getReleaseYear(),  //String to sort with for o2
                o2.getReleaseMonth(), o2.getReleaseDay());

        return sortBy1.compareTo(sortBy2);

    };
    public static final Comparator<Game> completionDateComparator = (o1, o2) -> {            //Sort by completion date
        //Local variables
        PlayedGame p1 = (PlayedGame) o1;                                        //Cast o1 to PlayedGame
        PlayedGame p2 = (PlayedGame) o2;                                        //Cast o2 to PlayedGame
        String sortBy1 = String.format("%04d%02d%02d", p1.getCompletionYear(),  //String to sort with for o1
                p1.getCompletionMonth(), p1.getCompletionDay());
        String sortBy2= String.format("%04d%02d%02d", p2.getCompletionYear(),   //String to sort with for o2
                p2.getCompletionMonth(), p2.getCompletionDay());

        return sortBy1.compareTo(sortBy2);
    };
    public static final Comparator<Game> percentComparator = new Comparator<>() {           //Sort by 100% status
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
    public static final Comparator<Game> hoursComparator = (o1, o2) -> {                    //Sort by hours
        //Return a value according to position in statuses list
        //Local variables
        UnplayedGame p1 = (UnplayedGame) o1;    //Cast o1 to UnplayedGame
        UnplayedGame p2 = (UnplayedGame) o2;    //Cast o2 to UnplayedGame

        return Double.compare(p1.getHours(), p2.getHours());
    };
    public static final Comparator<Game> deckStatusComparator = new Comparator<>() {        //Sort by Deck status
        //Local variables
        final ObservableList<String> statuses = FXCollections.observableArrayList(  //List of possible statuses
                "Yes", "No", "Maybe", "");
        @Override
        public int compare(Game o1, Game o2) {
            //Return a value according to position in statuses list
            //Local variables
            UnplayedGame p1 = (UnplayedGame) o1;    //Cast o1 to UnplayedGame
            UnplayedGame p2 = (UnplayedGame) o2;    //Cast o2 to UnplayedGame

            return statuses.indexOf(p1.getDeckCompatible()) - statuses.indexOf(p2.getDeckCompatible());
        }
    };

    //Prevents the user reordering columns
    public static <T> void preventColumnReordering(TableView<T> tableView) {
        for(TableColumn<?, ?> column : tableView.getColumns())
            column.setReorderable(false);
    }

    public static <T> void preventColumnSorting(TableView<T> tableView){
        for(TableColumn<?, ?> column : tableView.getColumns())
            column.setSortable(false);
    }

    public static <T> void preventColumnResizing(TableView<T> tableView){
        for(TableColumn<?, ?> column : tableView.getColumns())
            column.setResizable(false);
    }

    //Sends data from each cell to a text object and gets the width of that object. Whatever the greatest value is,
    // the width should be sightly more
    public static <T> void updateColumnWidth(ObservableList<TableColumn<T, ?>> columnList) {
        for (TableColumn<T, ?> tableColumn : columnList) {
            Text text = new Text(tableColumn.getText());
            double width = text.getLayoutBounds().getWidth() + 20;
            for (int j = 0; j < tableColumn.getTableView().getItems().size(); j++) {
                if (tableColumn.getCellData(j) instanceof Double) {
                    text = new Text(Double.toString((Double) tableColumn.getCellData(j)));
                } else if (tableColumn.getCellData(j) instanceof Integer) {
                    text = new Text(Integer.toString((Integer) tableColumn.getCellData(j)));
                } else {
                    text = new Text((String) tableColumn.getCellData(j));
                }
                double newWidth = text.getLayoutBounds().getWidth() + 20;
                if (newWidth > width)
                    width = newWidth;
            }
            tableColumn.setPrefWidth(width);
        }
    }
}
