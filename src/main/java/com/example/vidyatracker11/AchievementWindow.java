package com.example.vidyatracker11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AchievementWindow extends VBox {

    private static final Date date = new Date();

    private static final ZoneId timeZone = ZoneId.systemDefault();

    private static final LocalDate localDate = date.toInstant().atZone(timeZone).toLocalDate();
    public Label mainLabel = new Label("Achievements");
    public AchievementBox<PlayedGame> totalAchievement = new AchievementBox<>(
            "Total", "Total games completed", new int[]{0,50,100,200,300,400,500,750,1000,1500}, true);
    public AchievementBox<PlayedGame> newReleasesAchievement = new AchievementBox<>(
            "New Releases", "Games beaten in " + localDate.getYear(), new int[]{0,1,2,3,4,5,6,8,10,15}, false);
    public AchievementBox<PlayedGame> lastYearReleasesAchievement = new AchievementBox<>(
            "Last Year Releases", "Games beaten in " + (localDate.getYear()-1), new int[]{0,2,4,6,8,10,12,16,20,30}, false);
    public AchievementBox<PlayedGame> newGamesAchievement = new AchievementBox<>(
            "New Games", "Games beaten within the release year", new int[]{0,5,10,20,30,40,50,75,100,150}, false);
    public AchievementBox<PlayedGame> oldGamesAchievement = new AchievementBox<>(
            "Old Games", "Games beaten 20 or more years after the release date", new int[]{0,25,50,100,150,200,250,375,500,750}, false);
    public AchievementBox<PlayedGame> shortGamesAchievement = new AchievementBox<>(
            "Short Games", "Short games beaten", new int[]{0,25,50,100,150,200,250,375,500,750}, false);
    public AchievementBox<String> bigFranchisesAchievement = new AchievementBox<>(
            "Big Franchises", "Franchises with 6 or more games completed", new int[]{0,2,4,6,8,10,15,20,30,40}, false);
    public AchievementBox<String> masteredGenresAchievement = new AchievementBox<>(
            "Mastered Genres", "Genres with 15 or more games completed", new int[]{0,2,4,6,8,10,15,20,30,40}, false);
    public AchievementBox<String> masteredPlatformsAchievement = new AchievementBox<>(
            "Mastered Platforms", "Platforms with 20 or more games completed", new int[]{0,1,2,3,4,5,7,10,15,20}, false);
    public AchievementBox<PlayedGame> completionistAchievement = new AchievementBox<>(
            "Completionist", "Games with 100% completion", new int[]{0,10,20,40,60,80,100,150,200,300}, false);
    public ObservableList<AchievementBox<?>> achievementBoxes = FXCollections.observableArrayList(totalAchievement, newReleasesAchievement,
            lastYearReleasesAchievement, newGamesAchievement, oldGamesAchievement,
            shortGamesAchievement, bigFranchisesAchievement, masteredGenresAchievement,
            masteredPlatformsAchievement, completionistAchievement);


    public AchievementWindow(){
        setPadding(new Insets(5));
        setSpacing(10);

        getChildren().add(mainLabel);
        for(AchievementBox<?> achievementBox : achievementBoxes)
            getChildren().add(achievementBox);

        for(PlayedGame game : GameLists.playedList) {
            if(game.getReleaseYear()!=0 && game.getCompletionYear()!=0 && game.getIsItShort().equals("No")) {
                int dayCountRelease = 0;
                dayCountRelease += game.getReleaseYear()*365;
                dayCountRelease += game.getReleaseMonth()*getDayCount(game.getReleaseMonth());
                dayCountRelease += game.getReleaseDay();

                int dayCountCompleted = 0;
                dayCountCompleted += game.getCompletionYear()*365;
                dayCountCompleted += game.getCompletionMonth()*getDayCount(game.getCompletionMonth());
                dayCountCompleted += game.getCompletionDay();
                if(dayCountCompleted-dayCountRelease>=7300){
                    System.out.println(game);
                }
            }
        }

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
