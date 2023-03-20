package com.example.vidyatracker11;

//The window for editing the genre list
public class EditGenreList extends EditGenPlatList {

    public EditGenreList() {
        super();

        //GUI
        mainLabel.setText("Edit Genre List");
        addItemButton.setText("Add Genre");
        removeItemButton.setText("Remove Selected Genre");
        renameItemButton.setText("Rename Selected Genre");

        list = GameLists.genreList;
        listView.setItems(list);
        inListWarning = "Genre already in list";
    }

    //Sets all games with given genre to the first in the list
    @Override
    public void removeGameItems(String toRemove) {
        for(PlayedGame game : GameLists.playedList)
            //Iterate for each PlayedGame
            if(game.getGenre().equals(toRemove))
                //If the game's genre is the one to be removed, set it to the top one
                game.setGenre(list.get(0));

        for(UnplayedGame game : GameLists.unplayedList)
            //Iterate for each UnplayedGame
            if(game.getGenre().equals(toRemove))
                //If the game's genre is the one to be removed, set it to the top one
                game.setGenre(list.get(0));
    }

    //Change games' genre from oldName to newName
    @Override
    public void renameGameItems(String oldName, String newName) {
        for(PlayedGame game : GameLists.playedList)
            //Iterate for each PlayedGame
            if(game.getGenre().equals(oldName))
                //If the game's genre is the one to be renamed, set it to the new name
                game.setGenre(newName);

        for(UnplayedGame game : GameLists.unplayedList)
            //Iterate for each UnplayedGame
            if(game.getGenre().equals(oldName))
                //If the game's genre is the one to be renamed, set it to the new name
                game.setGenre(newName);
    }
}