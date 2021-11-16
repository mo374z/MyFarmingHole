package game.movingEntitiesPackage;

import game.FarmingHoleApplication;
import game.GameChat;
import game.notMovingEntitiesPackage.Buildings;
import game.notMovingEntitiesPackage.GrainTank;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Einf�hrung des M�hdreschers
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Harvester extends Vehicle{

    /** Getreide-Tank des M�hdreschers, in dem er Getreide speichern kann oder woraus er Getreide abgeben kann */
    private GrainTank grainTank;

    /**
     * Verwendung des M�hdreschers unter Koordinaten
     * @param koordX X-Koordinate des M�hdreschers (bei Initialisierung)
     * @param koordY Y-Koordinate des M�hdreschers (bei Initialisierung)
     * @param name Name des M�hdreschers
     * @param used true wenn das {@link Vehicle} gerade genutzt wird, false wenn es nicht genutzt wird
     * @param fuel Tankf�llung des Fahrzeugtanks bei Initialisierung
     */
    public Harvester (double koordX, double koordY, String name, boolean used, double fuel) {
        super(koordX, koordY);
        setName(name);
        setUsed(used);
        setAnimation(new Harvester_Animation());
        getAnimation().setAnimationImages();
        entityNode = getAnimation().animate();
        grainTank = new GrainTank(0, 100);
        vehicleFuelTank = new FuelTank();
        vehicleFuelTank.setFuel(fuel);
        moveEntityTo(getKoordX(), getKoordY());

    }


    @Override
    public void updateKoords() {
        Bounds boundsInScene = entityNode.localToScene(entityNode.getBoundsInLocal());
        setKoordX(boundsInScene.getCenterX());
        setKoordY(boundsInScene.getCenterY());
    }

    @Override
    public Group updateEntity() {
        updateKoords();
        vehicleFuelTank.updateFuel();
        vehicleFuelTank.checkFuel();
        return initialize();
    }

    /**
     * Festlegung der Bewegung des M�hdreschers der m�glichen Tastenbelegung zur interaktion mit einem M�hdrescher.
     * @param scene �bergeben wird die Scene, auf der das gesamte Game beruht.
     */
    public void harvesterKeyActions(Scene scene) {
        if (isUsed()) {
            defineKeyActions(scene);
        }

    }

    @Override
    public void attachOrDetachAttachable() {
        // currently/so far, harvesters can't attach an attachable.
    }

    @Override
    public void fuelActions() {
        refuelVehicle();
    }

    @Override
    public void sellGrain() {
        // currently, you can only sell grain from a trailer.

    }

    /**
     * Innereklasse in Harvester, in der die Animation des M�hdreschers (und alle zur Animation geh�renden Methoden) gesondert betrachtet wird.
     * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
     */
    public class Harvester_Animation extends VehicleAnimation {

        @Override
        public void setAnimationImages() {
            Image harvester1 = new Image("file:src/Images/harvester/harvesterRightcomplete.png");
            Image harvester_2_driving = new Image("file:src/Images/harvester/harvesterRight2complete.png");
            Image harvester_3_driving = new Image("file:src/Images/harvester/harvesterRight3complete.png");
            Image harvester_4_driving = new Image("file:src/Images/harvester/harvesterRight4complete.png");

            if (currentlyActive != null && isUsed() && vehicleFuelTank.checkFuel()) {
                images[0] = harvester1;
                images[1] = harvester_2_driving;
                images[2] = harvester_3_driving;
                images[3] = harvester_4_driving;
            } else {
                images[0] = harvester1;
                images[1] = harvester1;
                images[2] = harvester1;
                images[3] = harvester1;
            }

        }

    }


    /**
     * Sucht nach Trailer-Objekten in der N�he des Harvesters.
     * @return Gibt das gefundene Trailer-Objekt zur�ck; Gibt null zur�ck, wenn kein entsprechendes Objekt gefunden wird
     */
    public Trailer searchForTrailerNearby() {
        for (Attachable a: FarmingHoleApplication.allAttachables) {
            if(a.getClass()==Trailer.class) {
                if (Math.abs(a.getKoordX()-getKoordX())<20
                        && Math.abs(a.getKoordY()-getKoordY()+20)<10
                        && currentlyActive.getClass() == Harvester.class
                        && a.getClass()==Trailer.class) {
                    return (Trailer) a;
                }
            }
        }
        return null;
    }

    /**
     * F�llt Getreide-Tanks in der N�he (Trailer oder Silos).
     * Dies geschieht nur, sofern diese ihre maximale Kapazit�t noch nicht erreicht haben und im Harvester �berhaupt Getreide im Getreidetank vorhanden ist.
     */
    @Override
    public void fillFromOrToGrainTank() {
        if (searchForTrailerNearby() != null
                && searchForTrailerNearby().getGrainTank().getCapacity() < searchForTrailerNearby().getGrainTank().getMax_capacity()
                && grainTank.getCapacity() > 0) {
            searchForTrailerNearby().getGrainTank().fillGrainTank();
            grainTank.emptyGrainTank();
            GameChat.setNewMessage(FarmingHoleApplication.getHarvest_per_field() + " Grain: Harvester -> Trailer");
        } else if (Buildings.evaluateIfEntityInArea(Buildings.siloArea, getKoordX(), getKoordY())) {
            if(FarmingHoleApplication.silo.getCapacity() < FarmingHoleApplication.silo.getMax_capacity()
                    && grainTank.getCapacity() > 0) {
                FarmingHoleApplication.silo.fillGrainTank();
                grainTank.emptyGrainTank();
                GameChat.setNewMessage(FarmingHoleApplication.getHarvest_per_field() + " Grain: Harvester -> Silo");
            }
        }
    }

    /**
     * Getter f�r den Getreidetank des Harvesters (Instanzvariable grainTank).
     * @return grainTank Gibt die Instanzvariable grainTank zur�ck, also ein GrainTank-Objekt.
     */
    public GrainTank getGrainTank() {
        return grainTank;
    }

}
