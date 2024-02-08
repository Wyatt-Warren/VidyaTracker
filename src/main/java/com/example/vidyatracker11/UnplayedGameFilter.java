package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UnplayedGameFilter extends GameFilter {
    //Fields
    ObservableList<String> possibleDeckStatuses;
    double minHours;
    double maxHours;

    public UnplayedGameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                              ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                              ObservableList<GameCollection> possibleCollections, ObservableList<String> possibleDeckStatuses,
                              String titleContains, int minReleaseYear, int maxReleaseYear, double minHours,
                              double maxHours) {
        super(possibleStatuses, possibleFranchises, possiblePlatforms, possibleGenres, possibleCollections,
                titleContains, minReleaseYear, maxReleaseYear);
        this.possibleDeckStatuses = possibleDeckStatuses;
        this.minHours = minHours;
        this.maxHours = maxHours;
    }

    public ObservableList<UnplayedGame> filteredList(){
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();

        for(UnplayedGame game : GameLists.unplayedList){
            //Iterate for every UnplayedGame

            if((possibleStatuses.isEmpty() || possibleStatuses.contains(game.getStatus())) &&
                    //Status is selected and game matches
                    (titleContains.equals("") || game.getTitle().toLowerCase().contains(titleContains.toLowerCase())) &&
                    //Title is selected and game matches
                    (possibleFranchises.isEmpty() || possibleFranchises.contains(game.getFranchise())) &&
                    //Franchise is selected and game matches
                    (possiblePlatforms.isEmpty() || possiblePlatforms.contains(game.getPlatform())) &&
                    //Platform is selected and game matches
                    (possibleGenres.isEmpty() || possibleGenres.contains(game.getGenre())) &&
                    //Genre is selected and game matches
                    (game.getReleaseYear() >= minReleaseYear) &&
                    //Release year is entered and game is greater or equal
                    (game.getReleaseYear() <= maxReleaseYear) &&
                    //Release year is entered and game is less than or equal
                    (game.getHours() >= minHours) &&
                    //Hours is entered and game is greater or equal
                    (game.getHours() <= maxHours) &&
                    //Hours is entered and game is less than or equal
                    (possibleDeckStatuses.isEmpty() || possibleDeckStatuses.contains(game.getDeckCompatible())) &&
                    //Deck status is entered and game is less than or equal
                    (possibleCollections.isEmpty() || gameInAnyCollection(game)))
                //Collection is selected and game is contained in one.

                //Add game to list
                newList.add(game);
        }

        return newList;
    }

    public ObservableList<String> getPossibleDeckStatuses() {
        return possibleDeckStatuses;
    }

    public double getMinHours() {
        return minHours;
    }

    public double getMaxHours() {
        return maxHours;
    }

    public void setPossibleDeckStatuses(ObservableList<String> possibleDeckStatuses) {
        this.possibleDeckStatuses = possibleDeckStatuses;
    }

    public void setMinHours(int minHours) {
        this.minHours = minHours;
    }

    public void setMaxHours(int maxHours) {
        this.maxHours = maxHours;
    }
}
