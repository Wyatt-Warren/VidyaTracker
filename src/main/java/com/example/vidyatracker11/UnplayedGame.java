package com.example.vidyatracker11;

//An item in the unplayed game list.
public class UnplayedGame extends Game {

    private double hours;

    private String deckCompatible = "";

    //Constructor
    public UnplayedGame(String title, String status, String platform,
                        String genre, int releaseYear, int releaseMonth,
                        int releaseDay) {
        super(title, status, platform, genre, releaseYear, releaseMonth, releaseDay);
        if (!status.equals("Backlog") && !status.equals("SubBacklog") && !status.equals("Wishlist"))
            throw new IllegalArgumentException();
    }

    //Hours setter (double)
    public void setHours(double hours) {
        if (hours < 0)
            throw new IllegalArgumentException();
        this.hours = hours;
    }

    //Hours setter (int)
    public void setHours(int hours) {
        if (hours < 0)
            throw new IllegalArgumentException();
        this.hours = hours;
    }

    //Status setter
    public void setStatus(String status) {
        if (!status.equals("Backlog") && !status.equals("SubBacklog") && !status.equals("Wishlist"))
            throw new IllegalArgumentException();
        this.status = status;
    }

    //Deck status setter
    public void setDeckCompatible(String deckCompatible) {
        if (!deckCompatible.equals("Blank") && !deckCompatible.equals("") && !deckCompatible.equals("Yes") && !deckCompatible.equals("No") && !deckCompatible.equals("Maybe"))
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
