/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author kerbrase
 */
public interface IPaletteController {
    
    public void createObject(Constants.Shape shape, Point position, String color);
    
    public void deleteObject(Constants.Shape shape, String color, Point position);
    
    public void moveObject(Point origin, Constants.Shape shape, String color, Point destination);

    public void askColor(Point position, IController controller);

    
}
