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
import interfaces.Constants;
import static interfaces.Constants.Shape.ELLIPSE;
import static interfaces.Constants.Shape.RECTANGLE;
import interfaces.IController;
import interfaces.PaletteManagementAPI;
import java.awt.Color;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author annia
 */
public class Palette implements PaletteManagementAPI {

    private Ivy bus;
    IvyMessageListener callback, callback2;
    private Point destination;
    private int action;
    private String shape;
    private String color;

    public Palette() {
        try {
            bus = new Ivy("Palette", "Palette ready", null);

            callback = new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    String nom;
                    System.out.println(strings[2]);
                    nom = strings[2];
                    try {
                        if (action == 0) {
                            //for(String n:nom){
                            if (shape == null) {
                                if (color != null) {
                                    System.out.println("Palette:DemanderInfo null nom=" + nom);
                                    bus.sendMsg("Palette:DemanderInfo nom=" + nom);
                                } else {
                                    System.out.println("Delete object null" + nom);
                                    bus.sendMsg("Palette:SupprimerObjet nom=" + nom);
                                    //shape = null;
                                }
                            } else if (shape.equals(nom.substring(0, 1))){
                               if (color != null) {
                                   System.out.println("Palette:DemanderInfo nom=" + nom + shape);
                                    bus.sendMsg("Palette:DemanderInfo nom=" + nom);
                                } else {
                                   
                                    System.out.println("Delete object shape" + nom);
                                    bus.sendMsg("Palette:SupprimerObjet nom=" + nom);
                                    //shape = null;
                                }
                            }
                     

                            //}
                        } else if (action == 1) {
                            if (shape == null) {
                                if (color != null) {
                                    System.out.println("Palette:DemanderInfo null nom=" + nom);
                                    bus.sendMsg("Palette:DemanderInfo nom=" + nom);
                                } else {
                                     System.out.println("Move object null" + nom);
                                     bus.sendMsg("Palette:DeplacerObjet nom=" + nom + " x=" + destination.x + " y=" + destination.y);
                                    //shape = null;
                                }
                            } else if (shape.equals(nom.substring(0, 1))){
                               if (color != null) {
                                   System.out.println("Palette:DemanderInfo nom=" + nom);
                                    bus.sendMsg("Palette:DemanderInfo nom=" + nom);
                                } else {
                                     System.out.println("Move object shape" + nom);
                                     bus.sendMsg("Palette:DeplacerObjet nom=" + nom + " x=" + destination.x + " y=" + destination.y);
                                    //shape = null;
                                }
                            }
                            //for(String n:nom){
                           
                            //}

                        }
                    } catch (IvyException ex) {
                        Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            callback2 = new IvyMessageListener() {
                @Override
                public void receive(IvyClient ic, String[] strings) {
                    System.out.println(strings[5]);
                    String couleur = strings[5];
                    String nom = strings[0];
                    
                    try {
                       if (color.equals(couleur)) {
                           if (action == 0) {
                                System.out.println("Delete object color" + nom);
                                bus.sendMsg("Palette:SupprimerObjet nom=" + nom);
                            //color = null;
                            } else if (action == 1){
                                System.out.println("Move object color" + nom);
                                bus.sendMsg("Palette:DeplacerObjet nom=" + nom + " x=" + destination.x + " y=" + destination.y);
                            }
                       }
                    } catch (IvyException ex) {
                        Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            bus.bindMsg("Palette:ResultatTesterPoint x=(.*) y=(.*) nom=(.*)", callback);
            bus.bindMsg("Palette:Info nom=(.*) x=(.*) y=(.*) longueur(.*) hauteur(.*) couleurFond=(.*) couleurContour(.*)", callback2);
            bus.start(null);
        } catch (IvyException ex) {
            Logger.getLogger(Palette.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createObject(Constants.Shape shape, Point position, String stringColor) {
        try {
            System.out.println("sending message");
            Color color;
            if (position == null) {
                position = new Point(0, 0);
            }
            if (stringColor == null) {
                color = Color.white;
            } else {
                try {
                    Field field = Class.forName("java.awt.Color").getField(stringColor);
                    color = (Color) field.get(null);
                } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    color = null; // Not defined
                }
            }
            switch (shape) {
                case RECTANGLE: {

                    bus.sendMsg("Palette:CreerRectangle x=" + position.x + " y="
                            + position.y + " couleurFond=" + color.getRed() + ":"
                            + color.getGreen() + ":" + color.getBlue());

                    break;
                }
                case ELLIPSE: {
                    bus.sendMsg("Palette:CreerEllipse x=" + position.x + " y="
                            + position.y + " couleurFond=" + color.getRed() + ":"
                            + color.getGreen() + ":" + color.getBlue());
                    break;
                }
            }
        } catch (IvyException ie) {
            System.out.println("can't send message");
        }
    }

    public void moveObject(Point origin, Point destination) {
        try {
            System.out.println("sending message");
            this.destination = new Point(destination);
            action = 1;
            bus.sendMsg("Palette:TesterPoint x=" + origin.x + " y=" + origin.y);
        } catch (IvyException ie) {
            System.out.println("can't send message");
        }
    }


    @Override
    public void deleteObject(Constants.Shape shape, String color, Point position) {
        try {
            if (shape == RECTANGLE) {
                this.shape = "R";
            } else if (shape == ELLIPSE){
                this.shape = "E";
            } else {
                this.shape = null;
            }
            this.color = color;
            System.out.println("sending message");
            action = 0;
            bus.sendMsg("Palette:TesterPoint x=" + position.x + " y=" + position.y);
        } catch (IvyException ie) {
            System.out.println("can't send message");
        }
    }

    @Override
    public void moveObject(Point origin, Constants.Shape shape, String color, Point destination) {
       try {
           if (shape == RECTANGLE) {
                this.shape = "R";
            } else if (shape == ELLIPSE){
                this.shape = "E";
            } else {
                this.shape = null;
            }
            this.color = color;
            System.out.println("sending message");
            this.destination = new Point(destination);
            action = 1;
            bus.sendMsg("Palette:TesterPoint x=" + origin.x + " y=" + origin.y);
        } catch (IvyException ie) {
            System.out.println("can't send message");
        }
    }

    @Override
    public void askColor(Point position, IController controller) {
       ColorOfPoint cp = new ColorOfPoint();
       cp.setColor(position, controller);
    }

}
