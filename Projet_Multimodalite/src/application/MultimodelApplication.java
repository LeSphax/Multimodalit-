/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import fr.dgac.ivy.IvyException;
import implementations.Controller;
import implementations.GestureRecognition;
import implementations.PaletteController;
import implementations.VocalRecognition;

/**
 *
 * @author kerbrase
 */
public class MultimodelApplication {

    public static void main(String args[]) throws IvyException {
        PaletteController palette = new PaletteController();
        Controller controller = new Controller(palette);
        VocalRecognition vocalRec = new VocalRecognition(controller);
        GestureRecognition recognition = new GestureRecognition(controller);
    }

}
