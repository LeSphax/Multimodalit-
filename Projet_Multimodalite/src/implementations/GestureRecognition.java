/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;
import interfaces.GestureRecognitionAPI;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kerbrase
 */
public class GestureRecognition implements GestureRecognitionAPI {

    private List<Template> dictionary;
    private Controller controller;
    private Stroke currentStroke;

    private String currentTemplateName;

    /**
     * @return the currentStroke
     */
    public Stroke getCurrentStroke() {
        return currentStroke;
    }

    void setCurrentTemplateName(String currentTemplateName) {
        this.currentTemplateName = currentTemplateName;
    }

    public enum Mode {

        LEARNING,
        RECOGNIZING
    }

    private Mode mode;

    public GestureRecognition(Controller controller) {
        this.controller = controller;
        dictionary = load();
        mode = Mode.RECOGNIZING;
        startPaletteListening();
    }

    public GestureRecognition() {
        controller = new Controller(new PaletteController());
        dictionary = load();
        if (dictionary == null) {
            resetDictionary();
        }
        mode = Mode.LEARNING;
        startPaletteListening();
    }

    @Override
    public void mousePressed(Point2D.Double event) {
        currentStroke = new Stroke();
        currentStroke.addPoint(event);
    }

    @Override
    public Gesture mouseReleased(Point2D.Double event) {
        currentStroke.addPoint(event);
        switch (mode) {
            case LEARNING:
                dictionary.add(new Template(currentStroke, currentTemplateName));
                break;
            case RECOGNIZING:
                Gesture gesture = recognizeGesture();
                if (gesture != null) {
                    System.out.println(gesture);
                    controller.gestureDetected(gesture);
                }
                return gesture;
            default:
                throw new AssertionError(mode.name());

        }
        return null;
    }

    public Gesture recognizeGesture() {
        double smallestDistance = Template.MAX_DISTANCE;
        String currentGestureName = null;

        for (Template template : dictionary) {
            double distance = template.calculateDistance(currentStroke);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                currentGestureName = template.getName();
            }
        }
        if (currentGestureName != null) {
            return GestureRecognitionAPI.Gesture.valueOf(currentGestureName);
        }
        return null;
    }

    @Override
    public void mouseDragged(Point2D.Double event) {
        currentStroke.addPoint(event);
    }

    public void setMode(Mode newMode) {
        if (mode != newMode) {
            switch (mode) {
                case LEARNING:
                    break;
                case RECOGNIZING:
                    break;
                default:
                    throw new AssertionError(mode.name());

            }
            mode = newMode;
        }
    }

    public void resetDictionary() {
        dictionary = new ArrayList<>();
    }

    void save() {
        try {
            FileOutputStream fileStream = new FileOutputStream("dictionary");
            ObjectOutputStream stream = new ObjectOutputStream(fileStream);
            stream.writeObject(dictionary);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestureRecognition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestureRecognition.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Template> load() {
        List<Template> list = null;
        try {
            FileInputStream fileStream = new FileInputStream("dictionary");
            ObjectInputStream stream = new ObjectInputStream(fileStream);
            list = (List) stream.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestureRecognition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(GestureRecognition.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void testLoad() {
        System.out.println(dictionary.size());
    }

    public static void main(String args[]) {
        GestureRecognition recognizer = new GestureRecognition(new Controller(new PaletteController()));
        recognizer.testLoad();
    }

    private void startPaletteListening() {
        try {
            bus = new Ivy("Palette", "Palette ready", null);

            callback = (IvyClient ic, String[] strings) -> {
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                mousePressed(new Point2D.Double(x, y));
            };
            callback2 = (IvyClient ic, String[] strings) -> {
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                mouseReleased(new Point2D.Double(x, y));
            };

            callback3 = (IvyClient ic, String[] strings) -> {
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                mouseDragged(new Point2D.Double(x, y));
            };
            callback4 = (IvyClient ic, String[] strings) -> {
                int x = Integer.parseInt(strings[0]);
                int y = Integer.parseInt(strings[1]);
                controller.receivePointerPosition(new Point(x, y));
            };
            bus.bindMsg("Palette:MousePressed x=(.*) y=(.*)", callback);
            bus.bindMsg("Palette:MouseReleased x=(.*) y=(.*)", callback2);
            bus.bindMsg("Palette:MouseDragged x=(.*) y=(.*)", callback3);
            bus.bindMsg("Palette:MouseClicked x=(.*) y=(.*)", callback4);
            bus.start(null);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Ivy bus;
    IvyMessageListener callback, callback2, callback3, callback4;

}
