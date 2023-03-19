package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Random;

//GUI for individual collection view
public class CollectionsWindow extends VBox {
    //GUI
    Label mainLabel = new Label("Collections");
    Button manageButton = new Button("Manage Collections");
    ChoiceBox<GameCollection> collectionChoices = new ChoiceBox<>();

    //TableView
    TableColumn<Game, String> statusColumn = new TableColumn<>("Status");
    TableColumn<Game, String> titleColumn = new TableColumn<>("Title");
    TableColumn<Game, String> percent100Column = new TableColumn<>("100%");
    TableView<Game> tableView = new TableView<>();

    //Buttons
    Button removeButton = new Button("Remove Selected Game");
    Button moveUpButton = new Button("Move Selected Game Up");
    Button moveDownButton = new Button("Move Selected Game Down");
    Button chooseRandomGameButton = new Button("Choose a Random Unplayed Game");

    //Text
    Label countTextLabel = new Label("Count:");
    Label hoursTextLabel = new Label("Total Hours:");
    Label percentTextLabel = new Label("Percent Complete:");
    Label percentPercentTextLabel = new Label("Percent 100% Complete:");
    Label countLabel = new Label("0");
    Label hoursLabel = new Label("0");
    Label percentLabel = new Label("0%");
    Label percentPercentLabel = new Label("0%");
    GridPane labelPane = new GridPane();

    //Boxes
    VBox buttonBox = new VBox(removeButton, moveUpButton, moveDownButton,
            chooseRandomGameButton, labelPane);
    HBox collectionBox = new HBox(tableView, buttonBox);

    //Fields
    ObservableList<TableColumn<Game, ?>> columnList = FXCollections.observableArrayList(    //List of each column in the tableview
            statusColumn, titleColumn, percent100Column);
    Stage parentStage;                                                                      //Stage that contains CollectionsWindow

    public CollectionsWindow(Stage parentStage){
        this.parentStage = parentStage;

        //GUI
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.getColumns().addAll(columnList);
        buttonBox.setSpacing(5);
        countTextLabel.setStyle("-fx-font-weight:bold;");
        hoursTextLabel.setStyle("-fx-font-weight:bold;");
        percentTextLabel.setStyle("-fx-font-weight:bold;");
        percentPercentTextLabel.setStyle("-fx-font-weight:bold;");
        countLabel.setStyle("-fx-font-weight:bold;;");
        hoursLabel.setStyle("-fx-font-weight:bold;");
        percentLabel.setStyle("-fx-font-weight:bold;");
        percentPercentLabel.setStyle("-fx-font-weight:bold;");
        labelPane.add(countTextLabel, 0, 0);
        labelPane.add(hoursTextLabel, 0, 1);
        labelPane.add(percentTextLabel, 0, 2);
        labelPane.add(percentPercentTextLabel, 0, 3);
        labelPane.add(countLabel, 1, 0);
        labelPane.add(hoursLabel, 1, 1);
        labelPane.add(percentLabel, 1, 2);
        labelPane.add(percentPercentLabel, 1, 3);
        labelPane.setHgap(10);
        collectionBox.setAlignment(Pos.CENTER);
        collectionBox.setSpacing(5);
        getChildren().addAll(mainLabel, manageButton, collectionChoices, collectionBox);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);

        statusColumn.setCellFactory(e -> new TableCell<>() {
            public void updateItem(String item, boolean empty) {
                //Change color according to each status
                super.updateItem(item, empty);

                if (item == null || empty) {
                    //No text
                    setText(null);
                    setStyle("");
                } else {
                    //There is text
                    setText(item);
                    setStyle(ApplicationGUI.colorMap.get(getItem()));
                }
            }
        });

        percent100Column.setCellFactory(new Callback<>() {
            //percent100Column has to be set this way because unplayed games don't have a percent100 value
            @Override
            public TableCell<Game, String> call(TableColumn<Game, String> gameStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);

                        if (getTableRow().getItem() != null && getTableRow().getItem() instanceof PlayedGame) {
                            //Game is a played game with non-null text
                            String text = ((PlayedGame) getTableRow().getItem()).getPercent100();
                            setText(text);
                            setStyle(ApplicationGUI.colorMap.get(text));
                        } else {
                            //Cell should be blank
                            setText("");
                            setStyle("");
                        }
                    }
                };
            }
        });

        manageButton.setOnAction(e -> {
            //Window for viewing and editing the list of collections
            //Local variables
            Stage stage = new Stage();
            CollectionsManageWindow collectionsManageWindow = new CollectionsManageWindow(collectionChoices);
            Scene scene = new Scene(collectionsManageWindow);

            //GUI
            stage.getIcons().add(ApplicationGUI.icon);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Manage Collections");
            stage.setScene(scene);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);

            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE) {
                    //Escape is pressed
                    stage.close();
                } else if (e1.getCode() == KeyCode.ENTER) {
                    //Enter is pressed
                    collectionsManageWindow.addCollectionButton.fire();
                }
            });

            stage.setOnCloseRequest(e1 -> {
                //When the window is closed, update data for CollectionsWindow
                setData();

                if(collectionsManageWindow.selectedRemoved)
                    //If the first collection is removed, select the next collection
                    collectionChoices.getSelectionModel().selectFirst();
            });

            stage.show();
        });

        collectionChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                //Set data when a collection is selected
                setCollectionData((int) newNum));

        removeButton.setOnAction(e -> {
            //Remove a game from a collection
            //Local variables
            Game selected = tableView.getSelectionModel().getSelectedItem();    //Currently selected game

            if(selected != null){
                //An item is selected
                collectionChoices.getSelectionModel().getSelectedItem().getGames().remove(selected);
                ApplicationGUI.changeMade = true;
                setLabels(collectionChoices.getSelectionModel().getSelectedItem());
            }
        });

        moveUpButton.setOnAction(e -> {
            //Move a game up in the collection list
            //Local variables
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();   //Currently selected game

            if(selectedIndex > 0){
                //An item is selected and it is not the top
                tableView.getItems().add(selectedIndex - 1, tableView.getItems().remove(selectedIndex));
                tableView.getSelectionModel().select(selectedIndex-1);
                ApplicationGUI.changeMade = true;
            }
        });

        moveDownButton.setOnAction(e -> {
            //Move a game down in the collection list
            //Local variables
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();   //Currently selected game

            if(selectedIndex != -1 && selectedIndex < tableView.getItems().size() -1){
                //An item is selected and it is not the bottom
                tableView.getItems().add(selectedIndex + 1, tableView.getItems().remove(selectedIndex));
                tableView.getSelectionModel().select(selectedIndex + 1);
                ApplicationGUI.changeMade = true;
            }
        });

        chooseRandomGameButton.setOnAction(e -> {
            //Randomly select an unplayed game from the collection list
            //Local variables
            ObservableList<Game> gameList = FXCollections.observableArrayList(          //List of potential options
                    collectionChoices.getSelectionModel().getSelectedItem().getGames());

            //Remove PlayedGames from the list
            gameList.removeIf(game -> game instanceof PlayedGame);

            if (gameList.size() > 0) {
                //There are more than zero games available
                //Local variables
                Stage stage = new Stage();
                Label label = new Label("");
                Button button = new Button("Close");
                VBox vbox = new VBox(label, button);
                Scene scene = new Scene(vbox);
                Random rand = new Random();

                //GUI
                stage.getIcons().add(ApplicationGUI.icon);
                stage.setTitle("Random Game");
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                label.setStyle("-fx-font-size: 16;");
                label.setText(gameList.get(rand.nextInt(gameList.size())).getTitle());
                button.setOnAction(e1 -> stage.close());
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(5));
                scene.getStylesheets().add(ApplicationGUI.styleSheet);

                scene.setOnKeyPressed(e1 -> {
                    if (e1.getCode() == KeyCode.ESCAPE || e1.getCode() == KeyCode.ENTER) {
                        //Close window if Escape or Enter are pressed
                        stage.close();
                    }
                });

                stage.show();
            }
        });

        TableMethods.preventColumnResizing(tableView);
        TableMethods.preventColumnSorting(tableView);
        TableMethods.preventColumnReordering(tableView);
        setData();
    }

    //Sets data for CollectionsWindow
    public void setData(){
        //Local variables
        GameCollection selected = null; //The currently selected collection

        if(!GameLists.collectionList.isEmpty()) {
            //If there are collections, set selected
            selected = collectionChoices.getSelectionModel().getSelectedItem();
        }

        //Clear items and refill the ChoiceBox
        collectionChoices.getItems().clear();
        for (GameCollection collection : GameLists.collectionList)
            //Iterate for each collection
            collectionChoices.getItems().add(collection);

        if(selected == null){
            //If there was nothing selected, select the first option
            collectionChoices.getSelectionModel().selectFirst();
        }else{
            //If there was something selected, select that
            collectionChoices.getSelectionModel().select(selected);
        }

        //Set data for the tableview and labels
        setCollectionData(collectionChoices.getSelectionModel().getSelectedIndex());
    }

    //Sets data for the tableview and the labels
    public void setCollectionData(int index){
        if(index != -1) {
            //There is an item selected
            //Local variables
            GameCollection collection = collectionChoices.getItems().get(index);    //Currently selected collection

            //Populate the table
            tableView.setItems(collection.getGames());

            //There is a weird problem with percent100Column, resetting the columns fixes it.
            tableView.getColumns().clear();
            tableView.getColumns().addAll(columnList);

            //Set the column width according to maximum length data in the column
            TableMethods.updateColumnWidth(columnList);

            //Reset the scene to reset the window size
            parentStage.setScene(null);
            parentStage.setScene(getScene());

            //Set the labels according to the collection data
            setLabels(collection);
        }else{
            //There is not an item selected
            tableView.setItems(null);
            setLabels(null);
        }
    }

    //Sets data for collection labels
    public void setLabels(GameCollection collection){
        if(collection != null && !collection.getGames().isEmpty()){
            //There is a collection and it has games
            //countLabel displays the size of the collection
            countLabel.setText("" + collection.getGames().size());

            //Count the total hours of unplayed games in the collection
            double hours = 0.0;
            for(Game game : collection.getGames())
                //Iterate for each game in the collection
                if(game instanceof UnplayedGame)
                    //If the game is an unplayed game add it's hours to the total hours
                    hours += ((UnplayedGame) game).getHours();
            hoursLabel.setText(String.format("" + "%.2f", hours));

            //Get the percentage of completed games
            int totalComplete = 0;
            for(Game game : collection.getGames())
                //Iterate for each game in the collection
                if(game.getStatus().equals("Completed"))
                    //If the game is completed, increment totalComplete
                    totalComplete++;
            double percent = totalComplete * 100.0 / collection.getGames().size();
            percentLabel.setText(String.format("%.2f%%", percent));

            //Get the percentage of 100% completed games
            int total100 = 0;           //Games with 100% completion
            int totalWithPercent = 0;   //Games with any completion value
            for(Game game : collection.getGames())
                //Iterate for each game in the collection
                if(game instanceof PlayedGame && (((PlayedGame) game).getPercent100().equals("Yes")||((PlayedGame) game).getPercent100().equals("No"))) {
                    //Game is a PlayedGame, and it has a value for 100% status, increment totalWithPercent
                    totalWithPercent++;
                    if(((PlayedGame) game).getPercent100().equals("Yes"))
                        //If the 100% status is yes, increment total100
                        total100++;
                }
            double percentPercent;

            if(totalWithPercent==0)
                //If there are no games with percent, percentPercent = 0 to avoid divide by zero
                percentPercent = 0;
            else
                //There are games with percent
                percentPercent = total100 * 100.0 / totalWithPercent;
            percentPercentLabel.setText(String.format("%.2f%%", percentPercent));

            //Color percent labels
            colorLabels(percentLabel, percent);
            colorLabels(percentPercentLabel, percentPercent);
        }else{
            //If there are no collection, or collection has no games (ps3)
            //set text to 0
            countLabel.setText("0");
            hoursLabel.setText("0");
            percentLabel.setText("0%");
            percentPercentLabel.setText("0%");

            //Color percent labels
            colorLabels(percentLabel, 0);
            colorLabels(percentPercentLabel, 0);
        }
    }

    //Color label according to a value out of 100
    public void colorLabels(Label label, double value){
        //Local variables
        String style = "-fx-font-weight:bold;"; //css style data for the label, start with just bold

        if(value == 100.0){
            //Value = 100
            style += "-fx-text-fill: #00ffff;";
        }else if(value >= 75.0){
            //75 <= Value < 100
            style += "-fx-text-fill: #00ff00;";
        }else if(value >= 50.0){
            //50 <= Value < 75
            style += "-fx-text-fill: #ffff00;";
        }else if(value >= 25.0){
            //25 <= Value < 50
            style += "-fx-text-fill: #ff7f00;";
        }else{
            //0 <= Value < 25
            style += "-fx-text-fill: #ff0000;";
        }

        label.setStyle(style);
    }
}
