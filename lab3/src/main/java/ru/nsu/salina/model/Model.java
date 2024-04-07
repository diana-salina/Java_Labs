package ru.nsu.salina.model;

import ru.nsu.salina.model.objects.Player;
import ru.nsu.salina.model.objects.Meteor;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Model implements AutoCloseable{
    private Player player;
    private ArrayList<Meteor> meteors;
    private boolean death;
    private double score;
    private int bestScore;
    private int height;
    private int width;
    private boolean reset;
    private final Thread thread;
    private final long timeout = 10;
    private ModelListener listener;
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
        thread = new Ticker(this);
        thread.start();
        generate();
    }
    public synchronized void generate() {
        if (isDead()) {
            if (reset) {
                player = new Player(this.width / 2, this.height / 2);
                meteors = new ArrayList<>();
                score = 0;
                setReset(false);
                death = false;
            }
        } else {
            this.player.move();
            controlPlayer();
            updateMeteors();
            increaseScore();
        }
        notifyUnsafe();
    }

    private synchronized void controlPlayer() {
        if (this.player.getX() < 0) {
            player.setX(0);
        } else if (this.player.getX() > (this.width - (int)(player.getWidth() * 2.2))) {
            player.setX(this.width - (int)(player.getWidth() * 2.2));
        }
        if (this.player.getY() < 0) {
            player.setY(0);
        } else if (this.player.getY() > (this.height - 3 * player.getHeight())) {
            player.setY(this.height - 3 * player.getHeight());
        }

        notifyUnsafe();
    }

    public void setListener(ModelListener listener) {
        this.listener = listener;
    }

    private void notifyUnsafe() {
        if (listener != null) {
            listener.onModelChanged();
        }
    }

    public void setReset(boolean r) {
        this.reset = r;
    }
    public synchronized void setPlayerDirection(Direction d) {
        this.player.setDiraction(d);
        notifyUnsafe();
    }
    private synchronized void increaseScore() {
        this.score += 0.015;
        notifyUnsafe();
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

    public synchronized void setBestScore(int score) {
        this.bestScore = score;
        try {
            updateScoreFile();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        notifyUnsafe();
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
    private synchronized void updateMeteors() {
        Random random = new Random();
        int newX = random.nextInt(this.width);
        int timeOfChange = 5;
        controlMeteorsAmount();

        if (random.nextInt(100) < 7) {
            if ((int) score % (2 * timeOfChange) < timeOfChange) {
                this.meteors.add(new Meteor(newX, this.height));
            } else {
                this.meteors.add(new Meteor(newX, 0));
            }
        }

        for (Meteor meteor : this.meteors) {
            if ((int) score % (2 * timeOfChange) < timeOfChange) {
                meteor.setDiraction(Direction.UP);
            } else {
                meteor.setDiraction(Direction.DOWN);
            }
            meteor.move();

            if (meteor.doHit(this.player)) {
                this.death = true;
            }

            int x = meteor.getX();
            int y = meteor.getY();
            int meteorHeight = meteor.getHeight();
            if (x > width || x < 0 || y > (height + meteorHeight) || y < -meteorHeight) {
                meteor.setMute(true);
            }
        }
        notifyUnsafe();
    }

    private synchronized void controlMeteorsAmount() {
        int i = 0;
        while (i < meteors.size()) {
            Meteor meteor = meteors.get(i);
            if (meteor.getMute()) {
                meteors.remove(i);
            } else {
                ++i;
            }
        }
        notifyUnsafe();
    }

    public long getTimeout() {
        return timeout;
    }
    @Override
    public void close() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }

    public void setHeight(int h) {
        this.height = h;
    }
    public void setWidth(int w) {
        this.width = w;
    }
}
