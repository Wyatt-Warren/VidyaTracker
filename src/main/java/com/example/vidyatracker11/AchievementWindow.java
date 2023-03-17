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
    public AchievementBox<PlayedGame> newGamesAchievement = new AchievementBox<>(
            "New Games", "Games beaten within the release year", new int[]{0,5,10,20,30,40,50,75,100,150,200}, false);
    public AchievementBox<PlayedGame> oldGamesAchievement = new AchievementBox<>(
            "Old Games", "Games beaten 20 or more years after the release date", new int[]{0,25,50,100,150,200,250,375,500,750,1000}, false);
    public AchievementBox<PlayedGame> shortGamesAchievement = new AchievementBox<>(
            "Short Games", "Short games beaten", new int[]{0,25,50,100,150,200,250,375,500,750,1000}, false);
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
            newGamesAchievement, oldGamesAchievement, shortGamesAchievement,
            bigFranchisesAchievement, masteredGenresAchievement, masteredPlatformsAchievement,
            completionistAchievement);

    public ObservableList<AchievementBox<PlayedGame>> gameCountAchievementBoxes = FXCollections.observableArrayList(
            totalAchievement, newReleasesAchievement, lastYearReleasesAchievement,
            newGamesAchievement, oldGamesAchievement, shortGamesAchievement,
            completionistAchievement);

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

                //Games beat in current year
                if(game.getReleaseYear()==localDate.getYear())
                    newReleasesAchievement.getItems().add(game);

                //Games beat last year
                if(game.getReleaseYear()==localDate.getYear()-1)
                    lastYearReleasesAchievement.getItems().add(game);

                //Games beat the same year they were released
                if(game.getReleaseYear()==game.getCompletionYear())
                    newGamesAchievement.getItems().add(game);

                //Games beat twenty years or more after they were released
                if(game.getReleaseYear()!=0 && game.getCompletionYear()!=0 && dayDifference(game) >= 7300)
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

    public int dayDifference(PlayedGame game){
        int dayCountRelease = 0;
        dayCountRelease += (game.getReleaseYear() - 1) * 365;
        dayCountRelease += dayCountMonthsInYear(game.getReleaseMonth()-1);
        dayCountRelease += game.getReleaseDay();

        int dayCountCompleted = 0;
        dayCountCompleted += (game.getCompletionYear() - 1) * 365;
        dayCountCompleted += dayCountMonthsInYear(game.getCompletionMonth()-1);
        dayCountCompleted += game.getCompletionDay();

        return (dayCountCompleted - dayCountRelease) + getLeapDaysBetween(game);
    }

    public int dayCountMonthsInYear(int n){
        int count = 0;

        if(n > 12 || n <= 0)
            return -1;

        for(int i = n; i > 0; i--){
            count += getDayCount(i);
        }

        return count;
    }

    public int getLeapDaysBetween(PlayedGame game){
        int count = 0;
        int first = game.getReleaseYear();
        int last = game.getCompletionYear();

        //We need to only include the start and end year if leap day would be within the range
        //If the start date is after february
        if(game.getReleaseMonth() > 2)
            first++;

        //If the end date is before february
        if(game.getCompletionMonth() < 2)
            last--;
        //If the end date is in february, but before leap day
        else if(game.getCompletionMonth()==2 && game.getCompletionDay() < 29)
            last--;

        //Check each year in the range, inclusively
        for(int i = first; i <= last; i++)
            //Year is divisible by four,
            if(i % 4 == 0)
                //but not 100,
                if(i % 100 == 0) {
                    //but yes 400
                    if (i % 400 == 0)
                        count++;
                }else
                    count++;

        return count;
    }

    public int getDayCount(int month){
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: //January, March, May, July, August, October, December
                return 31;
            case 2: //February
                return 29;
            case 4:
            case 6:
            case 9:
            case 11: //April, June, September, November
                return 30;
            default:
                return -1;
        }
    }
}
