package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class UnplayedGameFilter extends GameFilter {
    //Fields
    ObservableList<String> possibleDeckStatuses;
    int minAddedYear;
    int maxAddedYear;
    double minHours;
    double maxHours;
    LocalDate maxAddedDate;

    public UnplayedGameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                              ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                              ObservableList<GameCollection> possibleCollections, ObservableList<String> possibleDeckStatuses,
                              String titleContains, int minReleaseYear, int maxReleaseYear, int minAddedYear,
                              int maxAddedYear, double minHours, double maxHours) {
        super(possibleStatuses, possibleFranchises, possiblePlatforms, possibleGenres, possibleCollections,
                titleContains, minReleaseYear, maxReleaseYear);
        this.possibleDeckStatuses = possibleDeckStatuses;
        this.minAddedYear = minAddedYear;
        this.maxAddedYear = maxAddedYear;
        this.minHours = minHours;
        this.maxHours = maxHours;
    }

    public ObservableList<UnplayedGame> filteredList(){
        ObservableList<UnplayedGame> newList = FXCollections.observableArrayList();

        for(UnplayedGame game : GameLists.unplayedList){
            //Iterate for every UnplayedGame
            //Local variables
            int effectiveAddedYear = game.getAddedYear();
            int effectiveAddedMonth = game.getAddedMonth();
            int effectiveAddedDay = game.getAddedDay();

            if(maxAddedDate != null) {
                //If maxAddedDate is provided, make date usable
                if (effectiveAddedYear == 0)
                    effectiveAddedYear = 1;

                if (effectiveAddedMonth == 0)
                    effectiveAddedMonth = 1;

                if (effectiveAddedDay == 0)
                    effectiveAddedDay = 1;
            }

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
                    (game.getAddedYear() >= minAddedYear) &&
                    //Added year is entered and game is greater or equal
                    (game.getAddedYear() <= maxAddedYear) &&
                    //Added year is entered and game is less than or equal
                    (maxAddedDate == null || !maxAddedDate.isBefore(LocalDate.of(effectiveAddedYear, effectiveAddedMonth, effectiveAddedDay))) &&
                    //If max added date is set, filter games by entire date rather than just year
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

    //Return the count of games that meet the filter
    public int getFilterCount(boolean countAllCollection){
        //Local variables
        int count = filteredList().size();

        if(countAllCollection){
            //If countAllCollection is selected, count PlayedGames meeting filter requirements
            for(GameCollection collection : possibleCollections)
                //For every collection
                for(Game game : collection.getGames())
                    //For every game in the collection
                    if(game instanceof PlayedGame &&
                            //If game is a playedGame
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
                            (game.getReleaseYear() <= maxReleaseYear))
                        //Release year is entered and game is less than or equal
                        count++;
        }

        return count;
    }

    public int getMinAddedYear() {
        return minAddedYear;
    }

    public int getMaxAddedYear() {
        return maxAddedYear;
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

    public void setMaxAddedDate(LocalDate maxAddedDate) {
        this.maxAddedDate = maxAddedDate;
    }
}
