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
public interface PaletteManagementAPI {
    
    public void createObject(Constants.Shape shape, Point position, Color color);
    
    public void deleteObject(Constants.Shape shape, Point position, Color color);
    
    public void moveObject(Point origin, Point destination);

}
