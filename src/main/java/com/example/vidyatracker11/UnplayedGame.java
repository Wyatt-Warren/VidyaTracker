package com.example.vidyatracker11;

//An item in the unplayed game list.
public class UnplayedGame extends Game {
    //Fields
    private double hours;               //Predicted hours to beat game
    private String deckCompatible = ""; //Game compatibility with steam deck

    public UnplayedGame(String title, String status, String platform,
                        String genre, int releaseYear, int releaseMonth,
                        int releaseDay) {
        super(title, status, platform, genre, releaseYear, releaseMonth, releaseDay);

        if (!status.equals("Backlog") && !status.equals("SubBacklog") && !status.equals("Wishlist"))
            //If status is not Backlog, SubBacklog, or Wishlist, throw an error
            throw new IllegalArgumentException();
    }

    //Hours setter (double)
    public void setHours(double hours) {
        if (hours < 0)
            //If hours is less than 0, throw an error
            throw new IllegalArgumentException();

        this.hours = hours;
    }

    //Hours setter (int)
    public void setHours(int hours) {
        if (hours < 0)
            //If hours is less than 0, throw an error
            throw new IllegalArgumentException();

        this.hours = hours;
    }

    //Status setter
    public void setStatus(String status) {
        if (!status.equals("Backlog") && !status.equals("SubBacklog") && !status.equals("Wishlist"))
            //If status is not Backlog, SubBacklog, or Wishlist, throw an error
            throw new IllegalArgumentException();

        this.status = status;
    }

    //Deck status setter
    public void setDeckCompatible(String deckCompatible) {
        if (!deckCompatible.equals("") && !deckCompatible.equals("Yes") && !deckCompatible.equals("No") && !deckCompatible.equals("Maybe"))
            //If status is not a blank string, or Yes, or No, or Maybe, throw an error
            throw new IllegalArgumentException();

        this.deckCompatible = deckCompatible;
    }

    //Hours getter
    public double getHours() {
        return hours;
    }

    //Deck status getter
    public String getDeckCompatible() {
        return deckCompatible;
    }
}
