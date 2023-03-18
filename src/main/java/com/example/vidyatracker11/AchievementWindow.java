package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class AchievementWindow extends VBox {

    private static final Date date = new Date();

    private static final ZoneId timeZone = ZoneId.systemDefault();

    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();
    public Label mainLabel = new Label("Achievements");
    public Label spacingLabel = new Label();
    public AchievementBox<PlayedGame> totalAchievement = new AchievementBox<>(
            "Total", "Total games completed", new int[]{0,50,100,200,300,400,500,750,1000,1500,2000}, true);
    public AchievementBox<PlayedGame> newReleasesAchievement = new AchievementBox<>(
            "New Releases", "Completed games from " + localDate.getYear(), new int[]{0,1,2,3,4,5,6,8,10,15,20}, false);
    public AchievementBox<PlayedGame> lastYearReleasesAchievement = new AchievementBox<>(
            "Last Year Releases", "Completed games from " + (localDate.getYear()-1), new int[]{0,2,4,6,8,10,12,16,20,30,40}, false);
    public AchievementBox<PlayedGame> currentYearAchievement = new AchievementBox<>(
            "Current Year", "Games completed in " + localDate.getYear(), new int[]{0,15,30,45,60,75,90,120,150,225,300}, false);
    public AchievementBox<PlayedGame> lastYearAchievement = new AchievementBox<>(
            "Last Year", "Games completed in " + (localDate.getYear()-1), new int[]{0,15,30,45,60,75,90,120,150,225,300}, false);
    public AchievementBox<PlayedGame> newGamesAchievement = new AchievementBox<>(
            "New Games", "Games completed within the release year", new int[]{0,5,10,20,30,40,50,75,100,150,200}, false);
    public AchievementBox<PlayedGame> oldGamesAchievement = new AchievementBox<>(
            "Old Games", "Games completed 20 or more years after the release date", new int[]{0,25,50,100,150,200,250,375,500,750,1000}, false);
    public AchievementBox<PlayedGame> shortGamesAchievement = new AchievementBox<>(
            "Short Games", "Short games completed", new int[]{0,25,50,100,150,200,250,375,500,750,1000}, false);
    public AchievementBox<String> bigFranchisesAchievement = new AchievementBox<>(
            "Big Franchises", "Franchises with 6 or more games completed", new int[]{0,2,4,6,8,10,15,20,30,40,50}, false);
    public AchievementBox<String> masteredGenresAchievement = new AchievementBox<>(
            "Mastered Genres", "Genres with 15 or more games completed", new int[]{0,2,4,6,8,10,15,20,30,40,50}, false);
    public AchievementBox<String> masteredPlatformsAchievement = new AchievementBox<>(
            "Mastered Platforms", "Platforms with 20 or more games completed", new int[]{0,1,2,3,4,5,7,10,15,20,25}, false);
    public AchievementBox<PlayedGame> completionistAchievement = new AchievementBox<>(
            "Completionist", "Games with 100% completion", new int[]{0,10,20,40,60,80,100,150,200,300,400}, false);
    public ObservableList<AchievementBox<?>> achievementBoxes = FXCollections.observableArrayList(
            totalAchievement, newReleasesAchievement, lastYearReleasesAchievement,
            currentYearAchievement, lastYearAchievement, newGamesAchievement,
            oldGamesAchievement, shortGamesAchievement, bigFranchisesAchievement,
            masteredGenresAchievement, masteredPlatformsAchievement, completionistAchievement);

    public ObservableList<AchievementBox<PlayedGame>> gameCountAchievementBoxes = FXCollections.observableArrayList(
            totalAchievement, newReleasesAchievement, lastYearReleasesAchievement,
            currentYearAchievement, lastYearAchievement, newGamesAchievement,
            oldGamesAchievement, shortGamesAchievement, completionistAchievement);

    public AchievementWindow(){
        setPadding(new Insets(10));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");

        getChildren().add(mainLabel);
        for(AchievementBox<?> achievementBox : achievementBoxes)
            getChildren().add(achievementBox);

        spacingLabel.setMinHeight(20);
        getChildren().add(2, spacingLabel);

        populateAchievements();
        for(AchievementBox<PlayedGame> achievementBox : gameCountAchievementBoxes)
            achievementBox.setItems(PlayedGamesTable.normalSort(achievementBox.getItems()));

        //Sorting bigFranchisesAchievement's list
        ObservableList<String> newFranchiseList = FXCollections.observableArrayList();
        for(String franchise : bigFranchisesAchievement.getItems()) {
            boolean placed = false;
            for (int j = 0; j < newFranchiseList.size(); j++) {
                String newFranchise = newFranchiseList.get(j);

                String franchiseSortName = franchise.toLowerCase();
                String newFranchiseSortName = newFranchise.toLowerCase();

                if (franchiseSortName.startsWith("the "))
                    franchiseSortName = franchiseSortName.replace("the ", "");
                if (newFranchiseSortName.startsWith("the "))
                    newFranchiseSortName = newFranchiseSortName.replace("the ", "");
                if (franchiseSortName.compareTo(newFranchiseSortName) < 0){
                    newFranchiseList.add(j, franchise);
                    placed = true;
                    break;
                }
            }
            if(!placed){
                newFranchiseList.add(franchise);
            }
        }
        bigFranchisesAchievement.setItems(newFranchiseList);

        for(AchievementBox<?> achievementBox : achievementBoxes)
            achievementBox.updateProgress();
    }

    public void populateAchievements(){
        HashMap<String, Integer> genreMap = new HashMap<>();
        HashMap<String, Integer> platformMap = new HashMap<>();
        HashMap<String, Integer> franchiseMap = new HashMap<>();
        ArrayList<String> franchiseKeys = new ArrayList<>();

        //Populate genreMap with genres
        for(String genre : GameLists.genreList)
            genreMap.put(genre, 0);

        //Populate platformMap with platforms
        for(String platform : GameLists.platformList)
            platformMap.put(platform, 0);

        //Check each game for each achievement.
        for(PlayedGame game : GameLists.playedList){
            //Non-short games
            if(!game.getIsItShort().equals("Yes") && game.getStatus().equals("Completed")){
                totalAchievement.getItems().add(game);

                //Games released in current year
                if(game.getReleaseYear()==localDate.getYear())
                    newReleasesAchievement.getItems().add(game);

                //Games released last year
                if(game.getReleaseYear()==localDate.getYear()-1)
                    lastYearReleasesAchievement.getItems().add(game);

                //Games beat this year
                if(game.getCompletionYear()==localDate.getYear())
                    currentYearAchievement.getItems().add(game);

                //Games beat last year
                if(game.getCompletionYear()==localDate.getYear()-1)
                    lastYearAchievement.getItems().add(game);

                //Games beat the same year they were released
                if(game.getReleaseYear()==game.getCompletionYear())
                    newGamesAchievement.getItems().add(game);

                //Games beat twenty years or more after they were released
                if(game.getReleaseYear()!=0 && game.getCompletionYear()!=0 && isTwentyYears(game))
                    oldGamesAchievement.getItems().add(game);

                //Games that have a franchise
                if(!game.getFranchise().equals(""))
                    //Increment value if franchise is in map
                    if(franchiseMap.containsKey(game.getFranchise()))
                        franchiseMap.put(game.getFranchise(), franchiseMap.get(game.getFranchise())+1);
                    //If franchise is not in map, add it
                    else {
                        franchiseMap.put(game.getFranchise(), 1);
                        franchiseKeys.add(game.getFranchise());
                    }

                genreMap.put(game.getGenre(), genreMap.get(game.getGenre())+1);
                platformMap.put(game.getPlatform(), platformMap.get(game.getPlatform())+1);

                if(game.getPercent100().equals("Yes"))
                    completionistAchievement.getItems().add(game);

            //Short games
            }else if(game.getIsItShort().equals("Yes") && game.getStatus().equals("Completed")){
                shortGamesAchievement.getItems().add(game);
            }
        }

        //Genres with 15 or more games
        for(String genre : GameLists.genreList)
            if(genreMap.get(genre) >= 15)
                masteredGenresAchievement.getItems().add(genre);

        //Platforms with 20 or more games
        for(String platform : GameLists.platformList)
            if(platformMap.get(platform) >= 20)
                masteredPlatformsAchievement.getItems().add(platform);

        //Franchises with 6 or more games
        for(String franchise : franchiseKeys)
            if(franchiseMap.get(franchise) >= 6)
                bigFranchisesAchievement.getItems().add(franchise);
    }

    public boolean isTwentyYears(PlayedGame game){
        int years = game.getCompletionYear()-game.getReleaseYear();

        //First we just look at years before anything else
        //Over twenty years
        if(years > 20)
            return true;
        //Less than twenty years
        else if(years < 20)
            return false;
        //Twenty years
        else

            //If year difference is exactly twenty, we should look at months
            //Completion month is further into the year than release month
            if(game.getCompletionMonth() > game.getReleaseMonth())
                return true;
            //Release month is further into the year than completion month
            else if(game.getCompletionMonth() < game.getReleaseMonth())
                return false;
            //Released and completed in the same month
            else

                //Finally, if the months are the same, we can just look at the days.
                return game.getCompletionDay() >= game.getReleaseDay();
    }
}