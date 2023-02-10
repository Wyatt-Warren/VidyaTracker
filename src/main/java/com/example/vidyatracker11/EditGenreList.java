package com.example.vidyatracker11;

//The window for editing the genre list
public class EditGenreList extends EditGenPlatList {

    public EditGenreList() {
        super();
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
            if(game.getGenre().equals(toRemove))
                game.setGenre(list.get(0));

        for(UnplayedGame game : GameLists.unplayedList)
            if(game.getGenre().equals(toRemove))
                game.setGenre(list.get(0));
    }

    //Changes games' genre from oldName to newName
    @Override
    public void renameGameItems(String oldName, String newName) {
        for(PlayedGame game : GameLists.playedList)
            if(game.getGenre().equals(oldName))
                game.setGenre(newName);

        for(UnplayedGame game : GameLists.unplayedList)
            if(game.getGenre().equals(oldName))
                game.setGenre(newName);
    }
}