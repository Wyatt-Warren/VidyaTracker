package com.example.vidyatracker11;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.text.Text;

public class TableMethods {

    //Prevents the user reordering columns
    public static <T> void preventColumnReordering(TableView<T> tableView) {
        tableView.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow headerRow = (TableHeaderRow) tableView.lookup("TableHeaderRow");
            headerRow.reorderingProperty().addListener((observableValue, aBoolean, t1) -> headerRow.setReordering(false));
        });
    }

    //Sends data from each cell to a text object and gets the width of that object. Whatever the greatest value is,
    // the width should be sightly more
    public static <T> void updateColumnWidth(ObservableList<TableColumn<T, ?>> columnList) {
        for (TableColumn<T, ?> tableColumn : columnList) {
            Text text = new Text(tableColumn.getText());
            double width = text.getLayoutBounds().getWidth() + 10;
            for (int j = 0; j < tableColumn.getTableView().getItems().size(); j++) {
                if (tableColumn.getCellData(j) instanceof Double) {
                    text = new Text(Double.toString((Double) tableColumn.getCellData(j)));
                } else if (tableColumn.getCellData(j) instanceof Integer) {
                    text = new Text(Integer.toString((Integer) tableColumn.getCellData(j)));
                } else {
                    text = new Text((String) tableColumn.getCellData(j));
                }
                double newWidth = text.getLayoutBounds().getWidth() + 10;
                if (newWidth > width)
                    width = newWidth;
            }
            tableColumn.setPrefWidth(width);
        }
    }
}
