package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Objects;

public class CollectionsWindow extends VBox {
    Label mainLabel = new Label("Collections");
    Button manageButton = new Button("Manage Collections");
    ChoiceBox<GameCollection> collectionChoices = new ChoiceBox<>();
    TableView<Game> tableView = new TableView<>();
    TableColumn<Game, String> statusColumn = new TableColumn<>("Status");
    TableColumn<Game, String> titleColumn = new TableColumn<>("Title");
    TableColumn<Game, String> percent100Column = new TableColumn<>("100%");
    Button removeButton = new Button("Remove Selected Game");
    Button moveUpButton = new Button("Move Selected Game Up");
    Button moveDownButton = new Button("Move Selected Game Down");
    Label countTextLabel = new Label("Count:");
    Label hoursTextLabel = new Label("Total Hours:");
    Label percentTextLabel = new Label("Percent Complete:");
    Label percentPercentTextLabel = new Label("Percent 100% Complete:");
    Label countLabel = new Label("0");
    Label hoursLabel = new Label("0");
    Label percentLabel = new Label("0%");
    Label percentPercentLabel = new Label("0%");
    GridPane labelPane = new GridPane();
    VBox buttonBox = new VBox(removeButton, moveUpButton, moveDownButton,
            labelPane);
    HBox collectionBox = new HBox(tableView, buttonBox);

    ObservableList<TableColumn<Game, ?>> columnList = FXCollections.observableArrayList(
            statusColumn, titleColumn, percent100Column);

    Stage parentStage;

    public CollectionsWindow(Stage parentStage){
        this.parentStage = parentStage;
        getChildren().addAll(mainLabel, manageButton, collectionChoices, collectionBox);
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);
        buttonBox.setSpacing(5);
        collectionBox.setAlignment(Pos.CENTER);
        collectionBox.setSpacing(5);
        countLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        percentLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        percentPercentLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        hoursLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        countTextLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        hoursTextLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        percentTextLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");
        percentPercentTextLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;");

        tableView.getColumns().addAll(columnList);

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        labelPane.add(countTextLabel, 0, 0);
        labelPane.add(hoursTextLabel, 0, 1);
        labelPane.add(percentTextLabel, 0, 2);
        labelPane.add(percentPercentTextLabel, 0, 3);
        labelPane.add(countLabel, 1, 0);
        labelPane.add(hoursLabel, 1, 1);
        labelPane.add(percentLabel, 1, 2);
        labelPane.add(percentPercentLabel, 1, 3);
        labelPane.setHgap(10);


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
                    } else if (getItem().equals("On Hold")){
                        setStyle("-fx-background-color: #aa5825;");
                    } else if (getItem().equals("Backlog")) {
                        setStyle("-fx-background-color: #545454;");
                    } else if (getItem().equals("SubBacklog")) {
                        setStyle("-fx-background-color: #666666;");
                    } else {
                        setStyle("-fx-background-color: #993745;");
                    }
                }
            }
        });

        percent100Column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Game, String> call(TableColumn<Game, String> gameStringTableColumn) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if (getTableRow().getItem() != null && getTableRow().getItem() instanceof PlayedGame) {
                            String text = ((PlayedGame) getTableRow().getItem()).getPercent100();
                            setText(text);
                            if (text.equals("Yes")) {
                                setStyle("-fx-background-color: #4a8c32;");
                            } else if (text.equals("No")) {
                                setStyle("-fx-background-color: #993737;");
                            } else {
                                setStyle("");
                            }
                        } else {
                            setText("");
                            setStyle("");
                        }
                    }
                };
            }
        });

        manageButton.setOnAction(e -> {
            Stage stage = new Stage();
            stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationGUI.class.getResourceAsStream("/icon.png"))));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Manage Collections");

            Label label = new Label("Manage Collections");
            TextField collectionTextField = new TextField();
            Button addCollectionButton = new Button("Add Collection");
            ListView<GameCollection> collectionListView = new ListView<>();
            Button removeButton = new Button("Remove Selected Collection");
            Button renameButton = new Button("Rename Selected Collection");
            Button moveUpButton = new Button("Move Up");
            Button moveDownButton = new Button("Move Down");
            VBox buttonBox = new VBox(renameButton, removeButton, moveUpButton, moveDownButton);
            GridPane gridPane = new GridPane();
            VBox mainVBox = new VBox(label, gridPane);

            gridPane.add(collectionTextField, 0, 0);
            gridPane.add(addCollectionButton, 1, 0);
            gridPane.add(collectionListView, 0, 1);
            gridPane.add(buttonBox, 1, 1);
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            buttonBox.setAlignment(Pos.TOP_CENTER);
            buttonBox.setSpacing(10);
            GridPane.setValignment(removeButton, VPos.TOP);
            mainVBox.setAlignment(Pos.CENTER);
            mainVBox.setPadding(new Insets(5));
            mainVBox.setSpacing(5);
            label.setStyle("-fx-font-weight:bold;-fx-font-size:24;");

            //Create a new collection with a given title
            addCollectionButton.setOnAction(e1 -> {
                if(!collectionTextField.getText().equals("")) {
                    GameLists.collectionList.add(new GameCollection(collectionTextField.getText()));
                    collectionTextField.setText("");
                    ApplicationGUI.changeMade = true;
                }
            });

            //Removes the selected collection from the list
            removeButton.setOnAction(e1 -> {
                int selectionInt = collectionListView.getSelectionModel().getSelectedIndex();

                if(selectionInt != -1){
                    GameLists.collectionList.remove(collectionListView.getSelectionModel().getSelectedItem());
                    ApplicationGUI.changeMade = true;
                }
            });

            //Replaces the selected Collection's title with the current string
            renameButton.setOnAction(e1 -> {
                int selectionInt = collectionListView.getSelectionModel().getSelectedIndex();
                String newName = collectionTextField.getText();

                if(!newName.equals("") && selectionInt != -1){
                    GameLists.collectionList.get(selectionInt).setTitle(newName);
                    ApplicationGUI.changeMade = true;
                    collectionTextField.setText("");

                    //Solution to a problem where the listview doesn't update after renaming
                    //because the observable list doesn't change, only a value in an object within the observable list
                    collectionListView.setItems(FXCollections.observableArrayList());
                    collectionListView.setItems(GameLists.collectionList);
                }
            });

            //Moves the selected item up in the list by one position
            moveUpButton.setOnAction(e1 -> {
                int selectionIndex = collectionListView.getSelectionModel().getSelectedIndex();
                if(selectionIndex > 0){
                    GameLists.collectionList.add(selectionIndex - 1, GameLists.collectionList.remove(selectionIndex));
                    collectionListView.getSelectionModel().select(selectionIndex - 1);
                    ApplicationGUI.changeMade = true;
                }
            });

            //Moves the selected item down in the list by one position
            moveDownButton.setOnAction(e1 -> {
                int selectionIndex = collectionListView.getSelectionModel().getSelectedIndex();
                if(selectionIndex > -1 && selectionIndex < GameLists.collectionList.size() -1){
                    GameLists.collectionList.add(selectionIndex + 1, GameLists.collectionList.remove(selectionIndex));
                    collectionListView.getSelectionModel().select(selectionIndex + 1);
                    ApplicationGUI.changeMade = true;
                }
            });

            collectionListView.setItems(GameLists.collectionList);

            Scene scene = new Scene(mainVBox);
            scene.getStylesheets().add(ApplicationGUI.styleSheet);
            scene.setOnKeyPressed(e1 -> {
                if (e1.getCode() == KeyCode.ESCAPE) {
                    stage.close();
                } else if (e1.getCode() == KeyCode.ENTER) {
                    addCollectionButton.fire();
                }
            });

            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(e1 -> setData());
        });

        collectionChoices.getSelectionModel().selectedIndexProperty().addListener((observable, oldNum, newNum) ->
                setCollectionData((int) newNum));

        removeButton.setOnAction(e -> {
            Game selected = tableView.getSelectionModel().getSelectedItem();
            if(selected != null){
                collectionChoices.getSelectionModel().getSelectedItem().getGames().remove(selected);
                ApplicationGUI.changeMade = true;
                setLabels(collectionChoices.getSelectionModel().getSelectedItem());
            }
        });

        moveUpButton.setOnAction(e -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if(selectedIndex > 0){
                tableView.getItems().add(selectedIndex - 1, tableView.getItems().remove(selectedIndex));
                tableView.getSelectionModel().select(selectedIndex-1);
                ApplicationGUI.changeMade = true;
            }
        });

        moveDownButton.setOnAction(e -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if(selectedIndex != -1 && selectedIndex < tableView.getItems().size() -1){
                tableView.getItems().add(selectedIndex + 1, tableView.getItems().remove(selectedIndex));
                tableView.getSelectionModel().select(selectedIndex + 1);
                ApplicationGUI.changeMade = true;
            }
        });

        TableMethods.preventColumnReordering(tableView);
        setData();
    }

    public void setData(){
        if(!GameLists.collectionList.isEmpty()) {
            GameCollection selected = collectionChoices.getSelectionModel().getSelectedItem();
            collectionChoices.getItems().clear();
            for (GameCollection collection : GameLists.collectionList)
                collectionChoices.getItems().add(collection);
            if(selected == null){
                collectionChoices.getSelectionModel().selectFirst();
            }else{
                collectionChoices.getSelectionModel().select(selected);
            }
            setCollectionData(collectionChoices.getSelectionModel().getSelectedIndex());
        }
    }

    public void setCollectionData(int index){
        if(index != -1) {
            GameCollection collection = collectionChoices.getItems().get(index);
            tableView.setItems(collection.getGames());

            //Yeah... idk. This fixes something
            tableView.getColumns().clear();
            tableView.getColumns().addAll(columnList);

            TableMethods.updateColumnWidth(columnList);
            setLabels(collection);
            if(getScene()!=null) {
                parentStage.setScene(null);
                parentStage.setScene(getScene());
                parentStage.setWidth(getScene().getWidth() + 30);
            }
        }else{
            setLabels(null);
        }
    }

    public void setLabels(GameCollection collection){
        if(collection != null && !collection.getGames().isEmpty()){
            countLabel.setText("" + collection.getGames().size());

            double hours = 0.0;
            for(Game game : collection.getGames())
                if(game instanceof UnplayedGame)
                    hours += ((UnplayedGame) game).getHours();
            hoursLabel.setText(String.format("" + "%.2f", hours));

            int totalComplete = 0;
            for(Game game : collection.getGames())
                if(game.getStatus().equals("Completed"))
                    totalComplete++;
            double percent = totalComplete * 100.0 / collection.getGames().size();
            percentLabel.setText(String.format("%.2f%%", percent));
            colorLabels(percentLabel, percent);

            int total100 = 0;
            int totalWithPercent = 0;
            for(Game game : collection.getGames())
                if(game instanceof PlayedGame && (((PlayedGame) game).getPercent100().equals("Yes")||((PlayedGame) game).getPercent100().equals("No"))) {
                    totalWithPercent++;
                    if(((PlayedGame) game).getPercent100().equals("Yes"))
                        total100++;
                }
            double percentPercent;
            //It says NaN if you don't do this
            if(totalWithPercent==0)
                percentPercent = 0;
            else
                percentPercent = total100 * 100.0 / totalWithPercent;
            percentPercentLabel.setText(String.format("%.2f%%", percentPercent));
            colorLabels(percentPercentLabel, percentPercent);

        }else{
            countLabel.setText("0");
            hoursLabel.setText("0");
            percentLabel.setText("0%");
            percentPercentLabel.setText("0%");
            percentLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;-fx-text-fill: #ff0000;");
            percentPercentLabel.setStyle("-fx-font-weight:bold;-fx-font-size:14;-fx-text-fill: #ff0000;");
        }
    }

    public void colorLabels(Label label, double value){
        String style = "-fx-font-weight:bold;-fx-font-size:14;";
        if(value == 100.0){
            style += "-fx-text-fill: #00ffff;";
        }else if(value >= 75.0){
            style += "-fx-text-fill: #00ff00;";
        }else if(value >= 50.0){
            style += "-fx-text-fill: #ffff00;";
        }else if(value >= 25.0){
            style += "-fx-text-fill: #ff7f00;";
        }else{
            style += "-fx-text-fill: #ff0000;";
        }
        label.setStyle(style);
    }
}
