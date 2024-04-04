package ru.nsu.salina.model;

import ru.nsu.salina.model.objects.Player;
import ru.nsu.salina.model.objects.Meteor;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Model {
    private final Player player;
    private final ArrayList<Meteor> meteors;
    private boolean death;
    private double score;
    private int bestScore;
    private final int height;
    private final int width;
    private boolean reset;
    public Model(){
        this.height = 400;
        this.width = 400;
        this.player = new Player(this.width / 2, this.height / 2);
        this.meteors = new ArrayList<>();
        this.score = 0;
        this.death = false;
        this.reset = false;
        try {
            this.bestScore = getBestScoreFromList();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void setReset(boolean r) {
        this.reset = r;
    }
    public boolean getReset() { return this.reset; }
    public void increaseScore() {
        this.score += 0.015;
    }
    private int getBestScoreFromList() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("best_score.txt"))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line.trim());
            }
        }
        return 0;
    }

    public void setBestScore(int score) {
        this.bestScore = score;
        try {
            updateScoreFile();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateScoreFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("best_score.txt"))) {
            writer.write(Integer.toString(bestScore));
        }
    }
    public int getHeight() { return this.height; }
    public int getWidth() { return this.width; }
    public int getPlayerX() {
        return this.player.getX();
    }
    public int getPlayerY() {
        return this.player.getY();
    }
    public ArrayList<Meteor> getMeteors() {
        return this.meteors;
    }
    public boolean isDead() {
        return this.death;
    }
    public double getScore() {
        return this.score;
    }
    public int getBestScore() {
        return this.bestScore;
    }
    public void updateMeteors() {
        Random random = new Random();

        int x = random.nextInt(this.width);

        int timeOfChange = 5;

        if(random.nextInt(100) < 7) {
            if ((int)score % (2 * timeOfChange) < timeOfChange) {
                this.meteors.add(new Meteor(x, this.height));
            } else {
                this.meteors.add(new Meteor(x, 0));
            }
        }

        for(Meteor meteor : this.meteors) {
            if ((int) score % (2 * timeOfChange) < timeOfChange) {
                meteor.move(0, -5);
            } else {
                meteor.move(0, 5);
            }

            if (meteor.do_hit(this.player)) {
                this.death = true;
            }
        }
        final int meteorsLimit = 50;
        if (meteors.size() > meteorsLimit) {
            int delNumb = random.nextInt(10);
            for (int i = 0; i < delNumb; ++i) {
                meteors.remove(i);
            }
        }
    }
    public void movePlayer(int dx, int dy) {
        this.player.move(dx, dy);
    }
}
