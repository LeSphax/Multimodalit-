/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import interfaces.Constants;
import interfaces.GestureRecognitionAPI;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 *
 * @author kerbrase
 */
public class Controller implements interfaces.IController {

    private State state;
    private Constants.Shape currentShape;
    private String currentColor;
    private Point currentPosition;
    private Point finalPosition;
    private Point temporaryPosition;
    private boolean validatedPosition;
    private VocalSynthesis synthesis;

    private Timer t;
    private Palette palette;

    private enum State {

        Init,
        Create,
        Delete,
        MoveWaitingForStart,
        MoveWaitingForEnd
    }

    public Controller(Palette palette) {
        this.palette = palette;
        synthesis = new VocalSynthesis();
        t = new Timer(3000, (ActionEvent e) -> {
            timerActionPerformed();
        });
        reset();
    }

    private void timerActionPerformed() throws AssertionError {
        System.out.println(validatedPosition);
        switch (state) {
            case Init:
                break;
            case Create:
                palette.createObject(currentShape, currentPosition, currentColor);
                break;
            case Delete:
                if (currentShape == null) {
                    System.out.println("Veuillez dire la forme de l'objet à supprimer");
                } else if (currentPosition == null) {
                    System.out.println("Veuillez pointer la position de l'objet à supprimer");
                } else {
                    palette.deleteObject(currentShape, currentColor, currentPosition);
                }
                break;
            case MoveWaitingForStart:
                if (currentShape == null) {
                    System.out.println("Veuillez dire la forme de l'objet à déplacer");
                } else if (currentPosition == null) {
                    System.out.println("Veuillez pointer la position de l'objet à déplacer");
                }
                break;
            case MoveWaitingForEnd:
                if (finalPosition == null) {
                    synthesis.say("Please point to the position where you want to move the object");
                    System.out.println("Veuillez pointer la position où vous voulez déplacer l'objet");
                } else {
                    palette.moveObject(currentPosition, currentShape, currentColor, finalPosition);
                }
                break;
            default:
                throw new AssertionError(state.name());
        }
        reset();
    }

    private void reset() {
        state = State.Init;
        currentPosition = null;
        finalPosition = null;
        currentColor = null;
        currentShape = null;
        validatedPosition = false;
        t.stop();
    }

    @Override
    public void saidColor(String color) {
        System.out.println("SAID :" + color);
        if (color.equals("same") && temporaryPosition != null) {
            palette.askColor(temporaryPosition,this);
            updateColor(null);
        } else {
            updateColor(color);
        }
    }

    private void updateColor(String color) throws AssertionError {
        switch (state) {
            case Init:
                state = State.Init;
                break;
            case Create:
                state = State.Create;
                t.restart();
                currentColor = color;
                break;
            case Delete:
                state = State.Delete;
                t.restart();
                currentColor = color;
                break;
            case MoveWaitingForStart:
                state = State.MoveWaitingForStart;
                t.restart();
                currentColor = color;
                break;
            case MoveWaitingForEnd:
                state = State.MoveWaitingForEnd;
                t.restart();
                currentColor = color;
                break;
            default:
                throw new AssertionError(state.name());
        }
    }

    @Override
    public void receiveColor(String color) {
        System.out.println("RECEIVED : " + color);
        updateColor(color);
    }

    @Override
    public void saidShape(Constants.Shape shape) {
        System.out.println("SAID : " + shape);
        switch (state) {
            case Init:
                state = State.Init;
                break;
            case Create:
                break;
            case Delete:
                state = State.Delete;
                currentShape = shape;
                if (!areDeleteParametersSet()) {
                    t.restart();
                } else {
                    t.stop();
                    timerActionPerformed();
                }
                break;
            case MoveWaitingForStart:
                currentShape = shape;
                if (temporaryPosition == null) {
                    state = State.MoveWaitingForStart;
                } else {
                    currentPosition = temporaryPosition;
                    temporaryPosition = null;
                    state = State.MoveWaitingForEnd;
                }
                t.restart();
                break;
            case MoveWaitingForEnd:
                state = State.MoveWaitingForEnd;
                currentShape = shape;
                if (temporaryPosition != null) {
                    currentPosition = temporaryPosition;
                }
                temporaryPosition = null;
                break;
            default:
                throw new AssertionError(state.name());
        }
    }

    private boolean areDeleteParametersSet() {
        return !(currentColor == null || currentShape == null || currentPosition == null);
    }

    @Override
    public void saidPosition() {
        System.out.println("SAID : Position");
        switch (state) {
            case Init:
                state = State.Init;
                break;
            case Create:
                state = State.Create;
                if (currentPosition == null) {
                    currentPosition = temporaryPosition;
                    temporaryPosition = null;
                    t.restart();
                } else {
                    validatedPosition = true;
                    t.restart();
                }
                break;
            case Delete:
                state = State.Delete;
                break;
            case MoveWaitingForStart:
                state = State.MoveWaitingForStart;
                break;
            case MoveWaitingForEnd:
                if (temporaryPosition != null) {
                    finalPosition = temporaryPosition;
                } else {
                    validatedPosition = true;
                }
                timerActionPerformed();
                break;
            default:
                throw new AssertionError(state.name());

        }
    }

    @Override
    public void receivePointerPosition(Point point
    ) {
        System.out.println("Controller : ReceivePosition");
        switch (state) {
            case Init:
                state = State.Init;
                break;
            case Create:
                state = State.Create;
                if (validatedPosition) {
                    currentPosition = point;
                    validatedPosition = false;
                } else {
                    temporaryPosition = point;
                }
                System.out.println(point);
                break;
            case Delete:
                state = State.Delete;
                currentPosition = point;
                t.restart();
                System.out.println(point);
                break;
            case MoveWaitingForStart:
                if (currentShape != null) {
                    state = State.MoveWaitingForEnd;
                    currentPosition = point;

                } else {
                    state = State.MoveWaitingForStart;
                    temporaryPosition = point;
                }
                t.restart();
                System.out.println(point);
                break;
            case MoveWaitingForEnd:
                state = State.MoveWaitingForEnd;
                if (validatedPosition) {
                    finalPosition = point;
                    validatedPosition = false;
                } else {
                    temporaryPosition = point;
                }
                t.restart();
                System.out.println(point);
                break;
            default:
                throw new AssertionError(state.name());

        }
    }

    @Override
    public void gestureDetected(GestureRecognitionAPI.Gesture gesture
    ) {
        System.out.println(gesture);
        switch (gesture) {
            case Rectangle:
                reset();
                state = State.Create;
                currentShape = Constants.Shape.RECTANGLE;
                System.out.println("Controller : RECTANGLE STATE");
                t.start();
                break;
            case Ellipse:
                reset();
                state = State.Create;
                currentShape = Constants.Shape.ELLIPSE;
                System.out.println("Controller : ELLIPSE STATE");
                t.start();
                break;
            case Supprimer:
                reset();
                state = State.Delete;
                System.out.println("Controller : DELETE STATE");
                t.start();
                break;
            case Deplacer:
                reset();
                state = State.MoveWaitingForStart;
                System.out.println("Controller : MOVE STATE");
                t.start();
                break;
            default:
                throw new AssertionError(gesture.name());

        }
    }

}
