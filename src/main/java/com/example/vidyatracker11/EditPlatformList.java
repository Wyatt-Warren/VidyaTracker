package com.example.vidyatracker11;

//The window for editing the platform list.
public class EditPlatformList extends EditGenPlatList {

    public EditPlatformList() {
        super();
        mainLabel.setText("Edit Platform List");
        addItemButton.setText("Add Platform");
        removeItemButton.setText("Remove Selected Platform");
        renameItemButton.setText("Rename Selected Platform");
        list = GameLists.platformList;
        listView.setItems(list);
        inListWarning = "Platform already in list";
    }

    //Sets all games with given platform to the first in the list
    @Override
    public void removeGameItems(String toRemove) {
        for(PlayedGame game : GameLists.playedList)
            if(game.getPlatform().equals(toRemove))
                game.setPlatform(list.get(0));

        for(UnplayedGame game : GameLists.unplayedList)
            if(game.getPlatform().equals(toRemove))
                game.setPlatform(list.get(0));
    }

    //Changes games' platform from oldName to newName
    @Override
    public void renameGameItems(String oldName, String newName) {
        for(PlayedGame game : GameLists.playedList)
            if(game.getPlatform().equals(oldName))
                game.setPlatform(newName);

        for(UnplayedGame game : GameLists.unplayedList)
            if(game.getPlatform().equals(oldName))
                game.setPlatform(newName);
    }
}