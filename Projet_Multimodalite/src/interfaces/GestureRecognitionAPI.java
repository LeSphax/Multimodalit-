/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.geom.Point2D;

/**
 *
 * @author kerbrase
 */
public interface GestureRecognitionAPI {


    public void mouseDragged(Point2D.Double event);

    public Gesture mouseReleased(Point2D.Double event);

    public void mousePressed(Point2D.Double event);
    
    public enum Gesture {
        Rectangle,
        Ellipse,
        Supprimer,
        Deplacer
    }
}
