/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import interfaces.GestureRecognitionAPI;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kerbrase
 */
public class GestureRecognition implements GestureRecognitionAPI {

    private List<Template> dictionary;
    private Controller controller;
    private Stroke currentStroke;

    /**
     * @return the currentStroke
     */
    public Stroke getCurrentStroke() {
        return currentStroke;
    }

    public enum Mode {

        LEARNING,
        RECOGNIZING
    }

    private Mode currentMode;

    public GestureRecognition(Controller controller) {
        this.controller = controller;
        dictionary = load();
        currentMode = Mode.RECOGNIZING;
    }
    
    public GestureRecognition() {
        dictionary = new ArrayList<>();
        currentMode = Mode.LEARNING;
    }

    @Override
    public void mousePressed(Point2D.Double event) {
        currentStroke = new Stroke();
        currentStroke.addPoint(event);
    }

    @Override
    public void mouseReleased(Point2D.Double event) {
        currentStroke.addPoint(event);
        switch (currentMode) {
            case LEARNING:
                
                break;
            case RECOGNIZING:
                break;
            default:
                throw new AssertionError(currentMode.name());

        }

    }

    @Override
    public void mouseDragged(Point2D.Double event) {
        currentStroke.addPoint(event);
    }

    @Override
    public void askPointerPosition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setMode(Mode mode) {
        currentMode = mode;
    }
    
    public List<Template> load(){
        return null;
    }

}
