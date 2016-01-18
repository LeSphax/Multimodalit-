package tpvocal;

/**
 * Yet another Ivy java program example
 *
 * This is the example from the documentation
 *
 * @author Yannick Jestin <jestin@cena.fr>
 *
 * (c) CENA
 *
 * This program is distributed as is
 *
 */
import fr.dgac.ivy.*;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

class ivyTranslater implements IvyMessageListener {

    private static Ivy bus;
    private TPVocal frame;

    ivyTranslater(TPVocal theFrame) throws IvyException {
        // initialization, name and ready message
        bus = new Ivy("IvyTranslater", "IvyTranslater Ready", null);
        bus.bindMsg("^sra5 Parsed=Action:(.*) Position:(.*) Confidence=(.*) NP", this);
        bus.bindMsg("^sra5 Parsed=Action:(.*) Conf(.*)ence=(.*) NP", this);
        frame = theFrame;
        // starts the bus on the default domain
        bus.start(null);
    }

    // callback associated to the "Hello" messages"
    public void receive(IvyClient client, String[] commands) {
        System.out.println("Message recu " + commands[0] + commands[1]);
        float confidence = Float.parseFloat(commands[2].replace(',', '.'));
        if (confidence > 0.5){
        if (commands[0].equals("deplacement")) {
            System.out.println("Deplacement OK");
            if (commands[1].equals("haut")) {
                System.out.println("Haut");
                frame.changePositionSquare(TPVocal.Direction.HAUT);
            } else if (commands[1].equals("bas")) {
                System.out.println("Bas");
                frame.changePositionSquare(TPVocal.Direction.BAS);
            } else if (commands[1].equals("gauche")) {
                System.out.println("Gauche");
                frame.changePositionSquare(TPVocal.Direction.GAUCHE);
            } else if (commands[1].equals("droite")) {
                System.out.println("Droite");
                frame.changePositionSquare(TPVocal.Direction.DROITE);
            }
        }
        else if (commands[0].equals("initialiser")){
            System.out.println("INIT");
            try {
                bus.sendMsg("Virginie Say=\"Tu es au milieu petit batard\"");
            } catch (IvyException ex) {
                Logger.getLogger(ivyTranslater.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.initSquare();
        }
        }

    }

    public static void main(String args[]) throws IvyException {
        new ivyTranslater(new TPVocal());
    }
    
    public static void demiTour(){
        try {
            bus.sendMsg("Virginie Say=\"Faites demi-tour des que possible\"");
        } catch (IvyException ex) {
            Logger.getLogger(ivyTranslater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
