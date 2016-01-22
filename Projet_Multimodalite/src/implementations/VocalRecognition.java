package implementations;

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
import interfaces.VocalRecognitionAPI;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

class VocalRecognition implements IvyMessageListener {

    private static Ivy bus;
    private Controller controller;

    VocalRecognition(Controller newController) throws IvyException {
        // initialization, name and ready message
        bus = new Ivy("IvyTranslater", "IvyTranslater Ready", null);
        bus.bindMsg("^sra5 Parsed=Type:(.*) Value:(.*) Confidence=(.*) NP", this);
        controller = newController;
        // starts the bus on the default domain
        bus.start(null);
    }

    // callback associated to the "Hello" messages"
    @Override
    public void receive(IvyClient client, String[] commands) {
            String type = getType(commands[0]);
            String value = commands[1];
            String confidence = commands[2];
            
            if (type == VocalRecognitionAPI.COLOR ){
                
            }
            else if(type.equals(client))
            
            
    }

    public static void main(String args[]) throws IvyException {
        new VocalRecognition(new Controller());
    }

    public static void demiTour() {
        try {
            bus.sendMsg("Virginie Say=\"Faites demi-tour des que possible\"");
        } catch (IvyException ex) {
            Logger.getLogger(VocalRecognition.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
