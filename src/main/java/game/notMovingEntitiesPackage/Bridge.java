package game.notMovingEntitiesPackage;

import game.FarmingHoleApplication;
import game.movingEntitiesPackage.MovingEntity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Klasse f�r eine Br�cke, welche als Ziel f�r das Spiel fertigzustellen ist.
 * Um die Br�cke zu reparieren ben�tigt man einen gewissen Geldbetrag.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Bridge {

    /** Repr�sentiert den aktuellen Stand der Br�cke. */
    private boolean repaired = false;

    /** Repr�sentiert den aktuellen Preis der Br�cke, um sie zu reparieren. */
    public final static int bridgePrice = 12000;

    /** Darstellung einer reparierten Br�cke. */
    private ImageView bridgeComplete = new ImageView(new Image("file:src/Images/way/bridge_complete.png"));

    /** Darstellung einer kaputten Br�cke. */
    private ImageView bridgeBroken = new ImageView(new Image("file:src/Images/way/broken_bridge.png"));

    /** ImageView-Objekt f�r das Hammer-Icon. */
    private ImageView hammerImage = new ImageView(new Image("file:src/Images/way/hammer.png"));;


    /**
     * Konstruktor f�r die Br�cke. Der Status der Br�cke wird gepr�ft.
     * @param repaired Status der Br�cke.
     */
    public Bridge(boolean repaired) {
        this.repaired=repaired;
        setBridgeImage();

    }


    /**
     * Ist die Br�cke noch nicht repariert, wird das Hammer-Icon aus- oder eingeblendet.
     */
    public void updateBridge() {
        if (!repaired) {
            if(MovingEntity.currentlyActive != null && Buildings.evaluateIfEntityInArea(Buildings.bridgeArea, MovingEntity.currentlyActive.getKoordX(), MovingEntity.currentlyActive.getKoordY())) {
                hammerImage.setVisible(true);
            } else {
                hammerImage.setVisible(false);
            }
        } else {
            hammerImage.setVisible(false);
        }

    }


    /**
     * Getter f�r das Hammer-Icon
     * @return hammerImage Bild f�r das Hammer-Icon
     */
    public ImageView getHammerImage() {
        return hammerImage;
    }


    /**
     * Getter f�r die Darstellung der reparierten Br�cke
     * @return bridgeComplete
     */
    public ImageView getBridgeComplete() {
        return bridgeComplete;
    }


    /**
     * Getter f�r die Darstellung der kaputten Br�cke
     * @return bridgeBroken
     */
    public ImageView getBridgeBroken() {
        return bridgeBroken;
    }

    /**
     * Methode, mit der eine kaputte Br�cke in eine reparierte Br�cke umgewandelt wird.
     * Diese Methode wird aufgerufen, wenn ein {@link MovingEntity} die Br�cke repariert.
     */
    public void changeToRepairedBridge() {
        repaired = true;
        setBridgeImage();
        FarmingHoleApplication app = new FarmingHoleApplication();
        Stage outroStage = new Stage();
        outroStage.setOnCloseRequest(e->e.consume());
        outroStage.setResizable(false);
        outroStage.setTitle("FarmingHole - Outro");
        outroStage.getIcons().add(new Image("file:src/Images/startscene/icon.png"));
        outroStage.setScene(app.createOutroScene(outroStage));
        outroStage.show();
    }

    /**
     * Methode, die das aktuelle Bild der Br�cke setzt.
     * Ist die Br�cke repariert, so ist das Bild der reparierten Br�cke sichtbar, das Bild der kaputten Br�cke ist nicht sichtbar.
     * Ist die Br�cke nicht repariert, so ist das Bild der kaputten Br�cke sichtbar, das Bild der reparierten Br�cke ist dabei nicht sichtbar.
     */
    public void setBridgeImage() {
        bridgeComplete.setVisible(repaired);
        bridgeBroken.setVisible(!repaired);

    }

    /**
     * Gibt zur�ck, ob die Br�cke repariert ist.
     * @return repaired true, wenn die Br�cke repariert ist; false, wenn nicht.
     */
    public boolean isRepaired() {
        return repaired;
    }

}
