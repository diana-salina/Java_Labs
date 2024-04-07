package ru.nsu.salina.controller2;


import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.nsu.salina.model.Direction;
import ru.nsu.salina.model.Model;

public class ControllerFX implements EventHandler<KeyEvent> {
    private final Model model;
    public ControllerFX(Model m) {
        this.model = m;
    }

    @Override
    public void handle(KeyEvent ev) {
        if (ev.getEventType() == KeyEvent.KEY_PRESSED) {
            KeyCode key = ev.getCode();
            if (key == KeyCode.LEFT || key == KeyCode.A) {
                model.setPlayerDirection(Direction.LEFT);
            } else if (key == KeyCode.RIGHT || key == KeyCode.D) {
                model.setPlayerDirection(Direction.RIGHT);
            } else if (key == KeyCode.UP || key == KeyCode.W) {
                model.setPlayerDirection(Direction.UP);
            } else if (key == KeyCode.DOWN || key == KeyCode.S) {
                model.setPlayerDirection(Direction.DOWN);
            } else if (this.model.isDead() && key == KeyCode.ENTER) {
                this.model.setReset(true);
            }
        } else if (ev.getEventType() == KeyEvent.KEY_RELEASED) {
            KeyCode key = ev.getCode();
            if (key == KeyCode.LEFT || key == KeyCode.RIGHT
                    || key == KeyCode.A || key == KeyCode.D) {
                this.model.setPlayerDirection(Direction.STAY_X);
            } else if (key == KeyCode.UP || key == KeyCode.DOWN
                    || key == KeyCode.W || key == KeyCode.S) {
                this.model.setPlayerDirection(Direction.STAY_Y);
            }
        }
    }
}
