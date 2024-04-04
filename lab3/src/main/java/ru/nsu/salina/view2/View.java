package ru.nsu.salina.view2;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ru.nsu.salina.model.Model;
import ru.nsu.salina.controller2.ControllerFX;
import ru.nsu.salina.model.objects.Meteor;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class View extends Stage {
    private Model model;
    private final ControllerFX controller;
    public View(Model m) {
        super();
        this.model = m;
        int height = m.getHeight();
        int width = m.getWidth();

        setTitle("Space Way");
        setIcon();
        setResizable(false);

        Group group = new Group();
        Scene scene = new Scene(group, width, height);
        Canvas canvas = new Canvas(width, height);
        group.getChildren().add(canvas);

        this.controller = new ControllerFX(this.model);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (model.getReset()) {
                    reset();
                }
                draw(gc);
            }
        };
        timer.start();

        scene.setOnKeyPressed(controller);
        scene.setOnKeyReleased(controller);

        centerOnScreen();
        setScene(scene);
    }
    private void setIcon() {
        Image icon = new Image("file:resources\\images\\icon.png");
        getIcons().add(icon);
    }
    private void reset() {
        this.model = new Model();
        this.controller.resetModel(this.model);
        this.model.setReset(false);
    }
    private void draw(GraphicsContext gc) {
        try {
            setBack(gc);
            setPlayer(gc);
            setMeteors(gc);
            if (model.isDead()) {
                setDepressedBack(gc);
                setDeath(gc);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        setScore(gc);
    }

    private void setBack(GraphicsContext gc) throws IllegalArgumentException {
        setObject(gc, "file:resources\\images\\back.jpg", 0, 0);
    }
    private void setObject(GraphicsContext gc, String path, int x, int y) throws IllegalArgumentException {
        Image m = new Image(path);
        gc.drawImage(m, x, y);

    }
    private void setPlayer(GraphicsContext gc) throws IllegalArgumentException {
        setObject(gc, "file:resources\\images\\player.png", model.getPlayerX(), model.getPlayerY());
    }

    private void setMeteors(GraphicsContext gc) throws IllegalArgumentException {
        for (Meteor meteor : model.getMeteors()) {
            setObject(gc, "file:resources\\images\\meteor.png", meteor.getX(), meteor.getY());
        }

    }
    private void setScore(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        gc.fillText("" + (int) model.getScore(), 10, 25);
    }
    private void setDeath(GraphicsContext gc) {
        int best = model.getBestScore();
        gc.setFill(Color.RED);
        int score = (int) model.getScore();
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        String text;
        if (best > score) {
            text = "score: " + score + " BS: " + best;
        } else {
            model.setBestScore(score);
            text = "New best score: " + score;
        }

        double textWidth = gc.getFont().getSize() / 2 * text.length();
        double x = (this.model.getWidth() - textWidth) / 2;
        double y = this.getHeight() / 2;
        gc.fillText(text, x, y);
    }

    private void setDepressedBack(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, this.model.getWidth(), this.model.getHeight());

    }
}
