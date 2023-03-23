package com.example.vidyatracker11;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Superclass for EditGenreList and EditPlatformList
public abstract class EditGenPlatList extends VBox {
    //GUI
    Label mainLabel = new Label();
    TextField addItemField = new TextField();
    Button addItemButton = new Button();
    Label warningLabel = new Label();
    ListView<String> listView = new ListView<>();
    Button renameItemButton = new Button();
    Button removeItemButton = new Button();
    Button moveUpButton = new Button("Move Up");
    Button moveDownButton = new Button("Move Down");
    VBox buttonBox = new VBox(renameItemButton, removeItemButton, moveUpButton, moveDownButton);
    GridPane gridPane = new GridPane();

    //Fields
    ObservableList<String> list;    //List of either genres or platforms
    String inListWarning = "";      //Text that should display on warning

    public EditGenPlatList(){
        //GUI
        mainLabel.setStyle("-fx-font-weight:bold;-fx-font-size:24;");
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(10);
        gridPane.add(addItemField, 0, 0);
        gridPane.add(addItemButton, 1, 0);
        gridPane.add(warningLabel, 1, 1);
        gridPane.add(listView, 0, 2);
        gridPane.add(buttonBox, 1, 2);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(5));
        setSpacing(5);
        getChildren().addAll(mainLabel, gridPane);

        addItemButton.setOnAction(e -> {
            //Add the current string to the list.
            if (list.contains(addItemField.getText()))
                //If the entered text is already in the list
                warningLabel.setText(inListWarning);
            else if (!addItemField.getText().equals("")) {
                //If the text is not in the list and is not blank
                list.add(addItemField.getText());
                warningLabel.setText("");
                addItemField.setText("");
                ApplicationGUI.changeMade = true;
            }
        });

        removeItemButton.setOnAction(e -> {
            //Remove selected item from the list
            //Local variables
            int selectionInt = listView.getSelectionModel().getSelectedIndex(); //Index of the selected item

            if(selectionInt != -1 && list.size() > 1){
                //If an item is selected, and it is not the last item remaining
                //Local variables
                String toRemove = listView.getSelectionModel().getSelectedItem();   //Store item to be removed from games before it is removed from the list
                Stage stage = new Stage();
                Label label = new Label("Remove " + toRemove + "?");
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                HBox hbox = new HBox(yesButton, noButton);
                VBox vbox = new VBox(label, hbox);
                Scene scene = new Scene(vbox);

                //GUI
                stage.getIcons().add(ApplicationGUI.icon);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Create New File");
                stage.setScene(scene);
                label.setStyle("-fx-font-weight: bold;-fx-font-size: 16;");
                yesButton.setStyle("-fx-font-size: 16;");
                noButton.setStyle("-fx-font-size: 16;");
                yesButton.setPrefWidth(80);
                noButton.setPrefWidth(80);
                hbox.setAlignment(Pos.CENTER);
                hbox.setSpacing(30);
                vbox.setPadding(new Insets(10));
                vbox.setSpacing(20);
                vbox.setAlignment(Pos.TOP_CENTER);
                scene.getStylesheets().add(ApplicationGUI.styleSheet);

                yesButton.setOnAction(e1 -> {
                    //Remove item from list
                    list.remove(selectionInt);

                    //Remove genre/platform from each game in lists
                    removeGameItems(toRemove);

                    //Update tables
                    ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                    ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());

                    ApplicationGUI.changeMade = true;
                    stage.close();
                });

                //Close the window without removing the item
                noButton.setOnAction(e1 -> stage.close());

                stage.show();
            }
        });

        renameItemButton.setOnAction(e -> {
            //Replace the selected item with the current string
            int selectionInt = listView.getSelectionModel().getSelectedIndex(); //Selected index of the list
            String newName = addItemField.getText();                            //Text entered by the user to rename the item to
            String oldName = listView.getSelectionModel().getSelectedItem();    //Store item to be renamed before it is removed from the list

            if(list.contains(newName)){
                //If the item is already in the list
                warningLabel.setText(inListWarning);
            }else if(!newName.equals("") && selectionInt != -1){
                //If the item is selected and text is entered
                //Replace item in the list
                list.remove(selectionInt);
                list.add(selectionInt, newName);

                //Rename item in each game
                renameGameItems(oldName, newName);

                //Clear warning
                warningLabel.setText("");

                //Clear text
                addItemField.setText("");

                //Update tables
                ApplicationGUI.playedGamesTable.sortAndFilter(ApplicationGUI.playedFilterTokenChoices.getSelectionModel().getSelectedItem());
                ApplicationGUI.unplayedGamesTable.sortAndFilter(ApplicationGUI.unplayedFilterTokenChoices.getSelectionModel().getSelectedItem());

                ApplicationGUI.changeMade = true;
            }
        });

        moveUpButton.setOnAction(e -> {
            //Moves the selected item up in the list by one position.
            //Local variables
            int selectionIndex = listView.getSelectionModel().getSelectedIndex();   //Index of the selected item

            if (selectionIndex > 0) {
                //If an item is selected, adn it is not on the top of the list

                //Remove the selected item and place it up by one
                list.add(selectionIndex - 1, list.remove(selectionIndex));

                //Select the new item
                listView.getSelectionModel().select(selectionIndex - 1);

                ApplicationGUI.changeMade = true;
            }
        });

        moveDownButton.setOnAction(e -> {
            //Moves the selected item down in the list by one position.
            //Local variables
            int selectionIndex = listView.getSelectionModel().getSelectedIndex();   //Index of the selected item

            if (selectionIndex > -1 && selectionIndex < list.size() - 1) {
                //If an item is selected, and it is not on the bottom of the list
                //Remove the selected item and place it down by one
                list.add(selectionIndex + 1, list.remove(selectionIndex));

                //Select the new item
                listView.getSelectionModel().select(selectionIndex + 1);

                ApplicationGUI.changeMade = true;
            }
        });
    }

    //Remove the item from each game
    public abstract void removeGameItems(String toRemove);

    //Rename the item in each game
    public abstract void renameGameItems(String oldName, String newName);

}
