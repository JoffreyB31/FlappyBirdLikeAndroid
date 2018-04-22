package network.iut.org.flappydragon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by MSI on 22/04/2018.
 */

public class Score {
    private int score;
    private String pseudo;
    private Date date;
    private int difficulty;

    public Score(int score, String pseudo, Date date, int difficulty) {
        this.score = score;
        this.pseudo = pseudo;
        this.date = date;
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getScoreDisplay() {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

        String difficutyStr = "Facile";
        if (difficulty == 1) {
            difficutyStr = "Moyen";
        } else if (difficulty == 2) {
            difficutyStr = "Difficile";
        }

        return this.pseudo + " | Score : " + this.score + " | " + difficutyStr + " | " + sf.format(this.date);
    }
}
