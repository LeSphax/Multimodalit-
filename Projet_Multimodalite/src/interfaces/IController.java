/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Point;

/**
 *
 * @author kerbrase
 */
public interface IController {
    
    public void saidColor(String color);
    
    public void receiveColor(String color);
    
    public void saidShape(Constants.Shape shape);
    
    public void saidPosition();
    
    public void receivePointerPosition(Point point);
    
    public void gestureDetected(GestureRecognitionAPI.Gesture gesture);
}
