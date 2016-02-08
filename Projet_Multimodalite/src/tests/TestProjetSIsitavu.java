/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import fr.dgac.ivy.IvyException;
import implementations.Controller;
import implementations.GestureRecognition;
import implementations.PaletteController;
import implementations.VocalRecognition;

/**
 *
 * @author kerbrase
 */
public class TestProjetSIsitavu {

    public static void main(String args[]) throws IvyException {
        System.out.println("Dans ColorOfPoint, on ne voit pas les system.out (Callback2)");
        System.out.println("Il faut refactorer le code, faire communiquer le 1$ recognizer et le controller par Ivy");
        PaletteController palette = new PaletteController();
        Controller controller = new Controller(palette);
        VocalRecognition vocalRec = new VocalRecognition(controller);
        GestureRecognition recognition = new GestureRecognition(controller);
        //new LearningFrame(recognition).setVisible(true);
    }

}
