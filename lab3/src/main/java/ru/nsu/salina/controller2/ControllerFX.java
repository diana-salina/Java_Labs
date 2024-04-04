package ru.nsu.salina.controller2;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.salina.model.Model;

public class ControllerFX implements EventHandler<KeyEvent> {
    private Model model;
    private int dx, dy;
    private final AnimationTimer timer;
    public ControllerFX(Model m) {
        this.model = m;
        dx = dy = 0;
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                model.movePlayer(dx, dy);
                model.updateMeteors();
                if (model.isDead()) {
                    timer.stop();
                } else {
                    model.increaseScore();
                }
            }
        };
        this.timer.start();
    }

    @Override
    public void handle(KeyEvent ev) {
        if (ev.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyCode key = ev.getCode();
            if (key == KeyCode.LEFT || key == KeyCode.A) {
                dx = -4;
            } else if (key == KeyCode.RIGHT || key == KeyCode.D) {
                dx = 4;
            } else if (key == KeyCode.UP || key == KeyCode.W) {
                dy = -4;
            } else if (key == KeyCode.DOWN || key == KeyCode.S) {
                dy = 4;
            } else if (this.model.isDead() && key == KeyCode.ENTER) {
                this.model.setReset(true);
                timer.start();
            }
        } else if (ev.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyCode key = ev.getCode();
            if (key == KeyCode.LEFT || key == KeyCode.RIGHT
                    || key == KeyCode.A || key == KeyCode.D) {
                dx = 0;
            } else if (key == KeyCode.UP || key == KeyCode.DOWN
                    || key == KeyCode.W || key == KeyCode.S) {
                dy = 0;
            }
        }
    }
    public void resetModel(Model model) {this.model = model;}

}
