/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations;

import interfaces.Constants;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author kerbrase
 */
public class Controller implements interfaces.IController{

    @Override
    public void saidColor(Color color) {
        System.out.println(color);
    }

    @Override
    public void saidToCopyObjectColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saidShape(Constants.Shape shape) {
        System.out.println(shape);
    }

    @Override
    public void saidPosition() {
        System.out.println("Position");
    }

    @Override
    public void receivePointerPosition(Point point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gestureDetected(Constants.Gesture gesture) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
