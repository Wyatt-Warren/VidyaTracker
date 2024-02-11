package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class PlayedGameFilter extends GameFilter {
    //Fields
    ObservableList<String> possibleShortStatuses;
    ObservableList<Integer> possibleRatings;
    ObservableList<String> possible100PercentStatuses;
    int minCompletionYear;
    int maxCompletionYear;
    LocalDate maxCompletionDate;

    public PlayedGameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                            ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                            ObservableList<GameCollection> possibleCollections, ObservableList<String> possibleShortStatuses,
                            ObservableList<Integer> possibleRatings, ObservableList<String> possible100PercentStatuses,
                            String titleContains, int minReleaseYear, int maxReleaseYear,
                            int minCompletionYear, int maxCompletionYear) {
        super(possibleStatuses, possibleFranchises, possiblePlatforms, possibleGenres, possibleCollections,
                titleContains, minReleaseYear, maxReleaseYear);
        this.possibleShortStatuses = possibleShortStatuses;
        this.possibleRatings = possibleRatings;
        this.possible100PercentStatuses = possible100PercentStatuses;
        this.minCompletionYear = minCompletionYear;
        this.maxCompletionYear = maxCompletionYear;
    }

    public ObservableList<PlayedGame> filteredList(){
        ObservableList<PlayedGame> newList = FXCollections.observableArrayList();

        for(PlayedGame game : GameLists.playedList){
            //Iterate for every PlayedGame
            //Local variables
            int effectiveCompletionYear = game.getCompletionYear();
            int effectiveCompletionMonth = game.getCompletionMonth();
            int effectiveCompletionDay = game.getCompletionDay();

            if(maxCompletionDate != null) {
                //If maxCompletionDate is provided, make date usable
                if (game.getCompletionYear() == 0)
                    effectiveCompletionYear = 1;

                if (game.getCompletionMonth() == 0)
                    effectiveCompletionMonth = 1;

                if (game.getCompletionDay() == 0)
                    effectiveCompletionDay = 1;
            }

            if((possibleStatuses.isEmpty() || possibleStatuses.contains(game.getStatus())) &&
                    //Status is selected and game matches
                    (possibleShortStatuses.isEmpty() || possibleShortStatuses.contains(game.getShortStatus())) &&
                    //Short status is selected and game matches
                    (titleContains.equals("") || game.getTitle().toLowerCase().contains(titleContains.toLowerCase())) &&
                    //Title is selected and game matches
                    (possibleFranchises.isEmpty() || possibleFranchises.contains(game.getFranchise())) &&
                    //Franchise is selected and game matches
                    (possibleRatings.isEmpty() || possibleRatings.contains(game.getRating())) &&
                    //Rating is selected and game matches
                    (possiblePlatforms.isEmpty() || possiblePlatforms.contains(game.getPlatform())) &&
                    //Platform is selected and game matches
                    (possibleGenres.isEmpty() || possibleGenres.contains(game.getGenre())) &&
                    //Genre is selected and game matches
                    (game.getReleaseYear() >= minReleaseYear) &&
                    //Release year is entered and game is greater or equal
                    (game.getReleaseYear() <= maxReleaseYear) &&
                    //Release year is entered and game is less than or equal
                    (game.getCompletionYear() >= minCompletionYear) &&
                    //Completion year is entered and game is greater or equal
                    (game.getCompletionYear() <= maxCompletionYear) &&
                    //Completion year is entered and game is less  than or equal
                    (maxCompletionDate == null || !maxCompletionDate.isBefore(LocalDate.of(effectiveCompletionYear, effectiveCompletionMonth, effectiveCompletionDay))) &&
                    (possible100PercentStatuses.isEmpty() || possible100PercentStatuses.contains(game.getPercent100())) &&
                    //100% status is selected and game matches
                    (possibleCollections.isEmpty() || gameInAnyCollection(game)))
                    //Collection is selected and game is contained in one.

                //Add game to list
                newList.add(game);
        }

        return newList;
    }

    public ObservableList<String> getPossibleShortStatuses() {
        return possibleShortStatuses;
    }

    public ObservableList<Integer> getPossibleRatings() {
        return possibleRatings;
    }

    public ObservableList<String> getPossible100PercentStatuses() {
        return possible100PercentStatuses;
    }

    public void setMaxCompletionDate(LocalDate maxCompletionDate) {
        this.maxCompletionDate = maxCompletionDate;
    }
}
