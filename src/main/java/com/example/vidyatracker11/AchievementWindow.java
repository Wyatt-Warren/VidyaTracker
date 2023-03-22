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

//GUI and code for all achievements
public class AchievementWindow extends VBox {
    //Labels
    public Label mainLabel = new Label("Achievements");
    public Label spacingLabel = new Label();

    //Achievements
    public AchievementBox<PlayedGame> totalAchievement = new AchievementBox<>(
            "Total", "Total games completed",
            new int[]{0,50,100,200,300,400,500,750,1000,1500,2000}, true);
    public AchievementBox<PlayedGame> newReleasesAchievement = new AchievementBox<>(
            "New Releases", "Completed games from " + localDate.getYear(),
            new int[]{0,1,2,3,4,5,6,8,10,15,20}, false);
    public AchievementBox<PlayedGame> lastYearReleasesAchievement = new AchievementBox<>(
            "Last Year Releases", "Completed games from " + (localDate.getYear()-1),
            new int[]{0,2,4,6,8,10,12,16,20,30,40}, false);
    public AchievementBox<PlayedGame> currentYearAchievement = new AchievementBox<>(
            "Current Year", "Games completed in " + localDate.getYear(),
            new int[]{0,15,30,45,60,75,90,120,150,225,300}, false);
    public AchievementBox<PlayedGame> lastYearAchievement = new AchievementBox<>(
            "Last Year", "Games completed in " + (localDate.getYear()-1),
            new int[]{0,15,30,45,60,75,90,120,150,225,300}, false);
    public AchievementBox<PlayedGame> newGamesAchievement = new AchievementBox<>(
            "New Games", "Games completed within the release year",
            new int[]{0,5,10,20,30,40,50,75,100,150,200}, false);
    public AchievementBox<PlayedGame> oldGamesAchievement = new AchievementBox<>(
            "Old Games", "Games completed 20 or more years after the release date",
            new int[]{0,25,50,100,150,200,250,375,500,750,1000}, false);
    public AchievementBox<PlayedGame> shortGamesAchievement = new AchievementBox<>(
            "Short Games", "Short games completed",
            new int[]{0,25,50,100,150,200,250,375,500,750,1000}, false);
    public AchievementBox<String> bigFranchisesAchievement = new AchievementBox<>(
            "Big Franchises", "Franchises with 6 or more games completed",
            new int[]{0,2,4,6,8,10,15,20,30,40,50}, false);
    public AchievementBox<String> masteredGenresAchievement = new AchievementBox<>(
            "Mastered Genres", "Genres with 15 or more games completed",
            new int[]{0,2,4,6,8,10,15,20,30,40,50}, false);
    public AchievementBox<String> masteredPlatformsAchievement = new AchievementBox<>(
            "Mastered Platforms", "Platforms with 20 or more games completed",
            new int[]{0,1,2,3,4,5,7,10,15,20,25}, false);
    public AchievementBox<PlayedGame> completionistAchievement = new AchievementBox<>(
            "Completionist", "Games with 100% completion",
            new int[]{0,10,20,40,60,80,100,150,200,300,400}, false);

    //Fields
    private static final Date date = new Date();
    private static final ZoneId timeZone = ZoneId.systemDefault();
    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();                         //Used to get current year
    public ObservableList<AchievementBox<?>> achievementBoxes = FXCollections.observableArrayList(                      //List of all achievement boxes
            totalAchievement, newReleasesAchievement, lastYearReleasesAchievement,
            currentYearAchievement, lastYearAchievement, newGamesAchievement,
            oldGamesAchievement, shortGamesAchievement, bigFranchisesAchievement,
            masteredGenresAchievement, masteredPlatformsAchievement, completionistAchievement);
    public ObservableList<AchievementBox<PlayedGame>> gameCountAchievementBoxes = FXCollections.observableArrayList(    //List of all achievement boxes containing games
            totalAchievement, newReleasesAchievement, lastYearReleasesAchievement,
            currentYearAchievement, lastYearAchievement, newGamesAchievement,
            oldGamesAchievement, shortGamesAchievement, completionistAchievement);

    public AchievementWindow(){
        //GUI
        mainLabel.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        spacingLabel.setMinHeight(20);
        setPadding(new Insets(10));
        setSpacing(20);
        setAlignment(Pos.CENTER);
        getChildren().add(mainLabel);

        for(AchievementBox<?> achievementBox : achievementBoxes)
            //Add each achievement to the VBox
            getChildren().add(achievementBox);

        //Spacing Label should be between the top achievement and the next ones
        getChildren().add(2, spacingLabel);

        //Populate all achievements' lists
        populateAchievements();

        //Sort achievement lists
        for(AchievementBox<PlayedGame> achievementBox : gameCountAchievementBoxes)
            //Sort all achievements' list
            achievementBox.setItems(PlayedGamesTable.basicSort(achievementBox.getItems(), true));
        sortFranchises();

        for(AchievementBox<?> achievementBox : achievementBoxes)
            //Update values in each achievementBox
            achievementBox.updateProgress();
    }

    //Populate all achievements' lists
    public void populateAchievements(){
        //Local variables
        HashMap<String, Integer> genreMap = new HashMap<>();        //Used for counting occurrences of each genre
        HashMap<String, Integer> platformMap = new HashMap<>();     //Used for counting occurrences of each platform
        HashMap<String, Integer> franchiseMap = new HashMap<>();    //Used for counting occurrences of each franchise
        ArrayList<String> franchiseKeys = new ArrayList<>();        //List of keys in franchiseMap, used to iterate through franchises.

        for(String genre : GameLists.genreList)
            //Populate genreMap with genres
            genreMap.put(genre, 0);

        for(String platform : GameLists.platformList)
            //Populate platformMap with platforms
            platformMap.put(platform, 0);

        for(PlayedGame game : GameLists.playedList){
            //Check each game for each achievement.
            if(!game.getShortStatus().equals("Yes") && game.getStatus().equals("Completed")){
                //Non-short games
                //All completed, non-short games should be added to totalAchievement
                totalAchievement.getItems().add(game);

                if(game.getReleaseYear()==localDate.getYear())
                    //Games released in current year
                    newReleasesAchievement.getItems().add(game);

                if(game.getReleaseYear()==localDate.getYear()-1)
                    //Games released last year
                    lastYearReleasesAchievement.getItems().add(game);

                if(game.getCompletionYear()==localDate.getYear())
                    //Games beat this year
                    currentYearAchievement.getItems().add(game);

                if(game.getCompletionYear()==localDate.getYear()-1)
                    //Games beat last year
                    lastYearAchievement.getItems().add(game);

                if(game.getReleaseYear()==game.getCompletionYear())
                    //Games beat the same year they were released
                    newGamesAchievement.getItems().add(game);

                if(isTwentyYears(game))
                    //Games beat 20 years or more after they were released
                    oldGamesAchievement.getItems().add(game);

                if(!game.getFranchise().equals(""))
                    //Games that have a franchise

                    if(franchiseMap.containsKey(game.getFranchise()))
                        //Increment value if franchise is in map
                        franchiseMap.put(game.getFranchise(), franchiseMap.get(game.getFranchise())+1);

                    else {
                        //If franchise is not in map, add it
                        franchiseMap.put(game.getFranchise(), 1);
                        franchiseKeys.add(game.getFranchise());
                    }

                //All genres and platforms are in the maps since we populated, so the key definitely exists
                genreMap.put(game.getGenre(), genreMap.get(game.getGenre())+1);
                platformMap.put(game.getPlatform(), platformMap.get(game.getPlatform())+1);

                if(game.getPercent100().equals("Yes"))
                    //Games with 100% completion
                    completionistAchievement.getItems().add(game);

            }else if(game.getShortStatus().equals("Yes") && game.getStatus().equals("Completed")){
                //Short games
                shortGamesAchievement.getItems().add(game);
            }
        }

        for(String genre : GameLists.genreList)
            if(genreMap.get(genre) >= 15)
                //Genres with 15 or more games
                masteredGenresAchievement.getItems().add(genre);

        for(String platform : GameLists.platformList)
            if(platformMap.get(platform) >= 20)
                //Platforms with 20 or more games
                masteredPlatformsAchievement.getItems().add(platform);

        for(String franchise : franchiseKeys)
            if(franchiseMap.get(franchise) >= 6)
                //Franchises with 6 or more games
                bigFranchisesAchievement.getItems().add(franchise);
    }

    //Determines if a game's completion date is at least twenty years after the release date
    public boolean isTwentyYears(PlayedGame game){
        if(game.getReleaseYear()!=0 && game.getCompletionYear()!=0) {
            //Game has a date
            //Local variables
            int years = game.getCompletionYear() - game.getReleaseYear();   //Difference between completion and release years

            //First we just look at years before anything else
            if (years > 20)
                //Over twenty years
                return true;
            else if (years < 20)
                //Less than twenty years
                return false;
            else
                //Twenty years
                if (game.getCompletionMonth() > game.getReleaseMonth())
                    //If year difference is exactly twenty, we should look at months
                    //Completion month is further into the year than release month
                    return true;
                else if (game.getCompletionMonth() < game.getReleaseMonth())
                    //Release month is further into the year than completion month
                    return false;
                else
                    //Released and completed in the same month
                    //Finally, if the months are the same, we can just look at the days.
                    return game.getCompletionDay() >= game.getReleaseDay();
        }
        //Game does not have a valid date
        return false;
    }

    //bigFranchisesAchievement's list can't be sorted with PlayedGamesTable.normalSort because it doesn't store games
    public void sortFranchises(){
        ObservableList<String> newFranchiseList = FXCollections.observableArrayList();
        for(String franchise : bigFranchisesAchievement.getItems()) {
            //Loop through each item of bigFranchisesAchievement's list
            //Local variables
            boolean placed = false; //Flag for if franchises is placed in the sorted list

            for (int j = 0; j < newFranchiseList.size(); j++) {
                //Loop through each item of the new sorted list
                //Local variables
                String newFranchise = newFranchiseList.get(j);              //Current franchise in the new list
                String franchiseSortName = franchise.toLowerCase();         //Name used for sorting the franchise going into the list
                String newFranchiseSortName = newFranchise.toLowerCase();   //Name used for sorting the current franchise in the new list

                if (franchiseSortName.startsWith("the "))
                    //Remove "the" from franchiseSortName
                    franchiseSortName = franchiseSortName.replace("the ", "");

                if (newFranchiseSortName.startsWith("the "))
                    //Remove "the" from newFranchiseSortName
                    newFranchiseSortName = newFranchiseSortName.replace("the ", "");

                if (franchiseSortName.compareTo(newFranchiseSortName) < 0){
                    //If a spot is found for the franchise, insert it and stop looping
                    newFranchiseList.add(j, franchise);
                    placed = true;
                    break;
                }
            }

            if(!placed){
                //If the franchise was not placed, place it at the end of the list
                newFranchiseList.add(franchise);
            }
        }
        //When loops are done, list is sorted. Set bigFranchisesAchievement's list
        bigFranchisesAchievement.setItems(newFranchiseList);
    }
}