/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import fr.dgac.ivy.IvyException;
import implementations.PaletteController;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author annia
 */
public class TestPalette {
    public static void main(String args[]) throws IvyException{
       Point origin = new Point (50,0);
       Point destination = new Point (20,30);
       Point intersect = new Point (55,40);
       Color color = Color.YELLOW;
       PaletteController palette = new PaletteController();
       //palette.createObject(Constants.Shape.RECTANGLE, origin, null);
       //palette.moveObject(origin, destination);
       //palette.deleteObject(origin);
      // palette.createObject(Constants.Shape.ELLIPSE, destination, color);
       //palette.deleteObject(intersect, "yellow");
       //palette.deleteObject(intersect);
    }
}
