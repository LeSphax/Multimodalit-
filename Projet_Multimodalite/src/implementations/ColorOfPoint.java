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
import interfaces.IController;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author melnycan
 */
public class ColorOfPoint {
    private Ivy bus;
    IvyMessageListener callback, callback2;
    String color;
    IController control;
    
    public ColorOfPoint() {
        try {
            bus = new Ivy("Palette", "Palette ready", null);

            callback = new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    color = strings[5];
                    System.out.println("Picking couleur" + color);
                    control.receiveColor(color);
                }
            };
            
            callback2 = new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    String nom = strings[2];
                    System.out.println("ON NE RECOIT PAS CA : Callback 2");
                    try {
                        bus.sendMsg("Palette:DemanderInfo nom=" + nom);
                    } catch (IvyException ex) {
                        Logger.getLogger(ColorOfPoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            
            bus.bindMsg("Palette:Info nom=(.*) x=(.*) y=(.*) longueur(.*) hauteur(.*) couleurFond=(.*) couleurContour(.*)", callback);
            bus.bindMsg("Palette:ResultatTesterPoint x=(.*) y=(.*) nom=(.*)", callback2);
            bus.start(null);
        } catch (IvyException ex) {
            Logger.getLogger(PaletteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setColor(Point position, IController controller){
        System.out.println("SET COLOR");
        try {
            control = controller;
            bus.sendMsg("Palette:TesterPoint x=" + position.x + " y=" + position.y);
        } catch (IvyException ex) {
            Logger.getLogger(ColorOfPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
