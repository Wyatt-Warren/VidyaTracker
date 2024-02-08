package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class playedGameFilter extends gameFilter{
    //Fields
    ObservableList<String> possibleShortStatuses = FXCollections.observableArrayList();
    ObservableList<Integer> possibleRatings = FXCollections.observableArrayList();
    ObservableList<String> possible100PercentStatuses = FXCollections.observableArrayList();

    public playedGameFilter(ObservableList<String> possibleStatuses, ObservableList<String> possibleFranchises,
                            ObservableList<String> possiblePlatforms, ObservableList<String> possibleGenres,
                            ObservableList<String> possibleCollections, ObservableList<String> possibleShortStatuses,
                            ObservableList<Integer> possibleRatings, ObservableList<String> possible100PercentStatuses,
                            String titleContains, int minimumReleaseYear, int maximumReleaseYear) {
        super(possibleStatuses, possibleFranchises, possiblePlatforms, possibleGenres, possibleCollections, titleContains, minimumReleaseYear, maximumReleaseYear);
        this.possibleShortStatuses = possibleShortStatuses;
        this.possibleRatings = possibleRatings;
        this.possible100PercentStatuses = possible100PercentStatuses;
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

    public void setPossibleShortStatuses(ObservableList<String> possibleShortStatuses) {
        this.possibleShortStatuses = possibleShortStatuses;
    }

    public void setPossibleRatings(ObservableList<Integer> possibleRatings) {
        this.possibleRatings = possibleRatings;
    }

    public void setPossible100PercentStatuses(ObservableList<String> possible100PercentStatuses) {
        this.possible100PercentStatuses = possible100PercentStatuses;
    }
}
