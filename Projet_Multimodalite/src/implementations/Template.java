/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 *
 * @author kerbrase
 */
public class Template implements Serializable {

    private Stroke stroke;
    private String name;

    public static final double MAX_DISTANCE = 1500;

    public Template(Stroke stroke, String name) {
        this.stroke = stroke;
        stroke.normalize();
        this.name = name;
    }

    /**
     * @return the stroke
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    double calculateDistance(Stroke otherStroke) {
        otherStroke.normalize();
        int minSize = Math.min(stroke.size(), otherStroke.size());
        double distance = 0;
        for (int i = 0; i < minSize; i++) {
            distance += Point2D.distance(stroke.getPoint(i).x, stroke.getPoint(i).y, otherStroke.getPoint(i).x, otherStroke.getPoint(i).y);
        }
        return distance;
    }
}
