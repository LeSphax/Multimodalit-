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
import interfaces.Constants;
import interfaces.VocalRecognitionAPI;
import java.awt.Color;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class VocalRecognition implements IvyMessageListener, VocalRecognitionAPI {

    private static Ivy bus;
    private Controller controller;

    public static Map<String, Color> mapColors;

    VocalRecognition(Controller newController) throws IvyException {
        controller = newController;
        initMap();

        bus = new Ivy("IvyTranslater", "IvyTranslater Ready", null);
        bus.bindMsg("^sra5 Parsed=Type:(.*) Value:(.*) Confidence=(.*) NP", this);

        bus.start(null);
    }

    // callback associated to the "Hello" messages"
    @Override
    public void receive(IvyClient client, String[] commands) {
        String type = commands[0];
        String value = commands[1];
        float confidence = Float.parseFloat(commands[2]);

        switch (type) {
            case VocalRecognitionAPI.COLOR:
                if (confidence > 0.5f) {
                    controller.saidColor(mapColors.get(value));
                }
                break;
            case VocalRecognitionAPI.OBJECT:
                if (confidence > 0.5f) {
                    controller.saidShape(Constants.Shape.valueOf(value));
                }
                break;
            case VocalRecognitionAPI.POSITION:
                if (confidence > 0.5f) {
                    controller.saidPosition();
                }
                break;
        }
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

    public static void initMap() {
        mapColors.put("noir", Color.black);
        mapColors.put("bleu", Color.blue);
        mapColors.put("rouge", Color.red);
        mapColors.put("vert", Color.green);
        mapColors.put("jaune", Color.yellow);
        mapColors.put("gris", Color.gray);
        mapColors.put("magenta", Color.magenta);
        mapColors.put("orange", Color.orange);
        mapColors.put("rose", Color.pink);
        mapColors.put("blanc", Color.white);
    }
}
