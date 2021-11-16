package game.movingEntitiesPackage;

import javafx.scene.image.Image;
import game.FarmingHoleApplication;
import game.GameChat;
import game.notMovingEntitiesPackage.Buildings;
import game.notMovingEntitiesPackage.GrainTank;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Implementierung f�r einen Traktor.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz

 */
public class Tractor extends Vehicle{

    /**
     * Spiegelt die Richtung wieder, in die der Traktor zeigt. Ist 1, wenn der Traktor nach links zeigt und -1, wenn der Traktor nach rechts zeigt.
     * Wird in updateEntity() kontinuierlich aktualisiert. Wird verwendet, um die genaue Position eines angeh�ngten Trailers zu bestimmen.
     */
    private int factorX = -1;

    /** Speichert den aktuell aktiven Arbeitsger�te. */
    public Attachable currentlyAttached;

    /** benutzungsf�hige Arbeitsger�te  */
    public Attachable canBeUsed;

    /**
     * Konstruktor um ein Traktorobjekt zu initialisieren. Das Traktorobjekt wird mit den �bergebenen Koordinaten initialisiert.
     * Damit das Traktorobjekt erscheint, muss noch eine entsprechende Group erzeugt (initialize()-Methode) und dargestellt werden.
     * @param koordX X-Koordinate des Traktors (bei Initialisierung)
     * @param koordY Y-Koordinate des Traktors (bei Initialisierung)
     * @param name Name des Traktors
     * @param used true, wenn der Traktor gerade benutzt wird; false, wenn er nicht benutzt wird
     * @param fuel F�llemenge des Sprit-Tanks des Traktors bei Initialisierung
     * @param currentlyAttached Aktuell angeh�ngtes Attachables-Objekt
     */
    public Tractor(double koordX, double koordY, String name, boolean used, double fuel, Attachable currentlyAttached) {
        super(koordX, koordY);
        setName(name);
        setUsed(used);
        setAnimation(new Tractor_Animation());
        getAnimation().setAnimationImages();
        entityNode = getAnimation().animate();
        moveEntityTo(getKoordX(), getKoordY());
        vehicleFuelTank = new FuelTank();
        vehicleFuelTank.setFuel(fuel);
        this.currentlyAttached = currentlyAttached;
    }


    /**
     * Festlegung der Bewegung des Traktors durch Keys in der Scene.
     * @param scene �bergeben wird die Scene, auf der das gesamte Game beruht.
     */
    public void tractorKeyActions(Scene scene) { //kann noch in vehicles als vehicleKeyActions() geschrieben werden
        if (isUsed()) {
            defineKeyActions(scene);
        }
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
        if(moveLeft) factorX = 1; //Faktor gibt an, ob das Attachable links oder rechts vom Traktor steht.
        if(moveRight) factorX = -1;
        if(currentlyAttached != null) {
            currentlyAttached.moveEntityTo(getKoordX()+20*factorX, getKoordY()-20);
        } //je nachdem auf welcher Seite des Traktors sich das Vehicle Objekt befindet wird es beim Abh�ngvorgang auf eine Position gesetzt. Dies sorgt f�r eine realistischere Darstellung.
        vehicleFuelTank.updateFuel();
        vehicleFuelTank.checkFuel();
        return initialize();
    }


    @Override
    public void fillFromOrToGrainTank() {
        if(Buildings.evaluateIfEntityInArea(Buildings.siloArea, getKoordX(), getKoordY()) && currentlyAttached != null && currentlyAttached.getClass() == Trailer.class) {
            GrainTank trailerGrainTank = ((Trailer) currentlyAttached).getGrainTank();
            if(FarmingHoleApplication.silo.getCapacity() > 0
                    && trailerGrainTank.getCapacity() < trailerGrainTank.getMax_capacity()) {
                FarmingHoleApplication.silo.emptyGrainTank();
                trailerGrainTank.fillGrainTank();
                GameChat.setNewMessage(FarmingHoleApplication.getHarvest_per_field() + " Grain: Silo -> Trailer");
            }
        }
    }


    @Override
    public void sellGrain() {
        if(Buildings.evaluateIfEntityInArea(Buildings.farmhouseArea, getKoordX(), getKoordY()) && currentlyAttached != null && currentlyAttached.getClass() == Trailer.class) {
            GrainTank trailerGrainTank = ((Trailer) currentlyAttached).getGrainTank();
            if(trailerGrainTank.getCapacity() > 0) {
                trailerGrainTank.emptyGrainTank();
                FarmingHoleApplication.setMyMoney(FarmingHoleApplication.getMyMoney()+FarmingHoleApplication.getGrainPrice());
                GameChat.setNewMessage("Sold " + FarmingHoleApplication.getHarvest_per_field() + " Grain from Trailer");
                GameChat.setNewMessage("Money +" + FarmingHoleApplication.getGrainPrice() + " Coins");
            }
        }
    }


    @Override
    public void fuelActions() {
        refuelVehicle();
    }


    /**
     * Methode um zu entscheiden, ob ein Traktor das {@link Attachable} anh�ngen oder abh�ngen soll.
     * Nutzt er gerade ein Attachable, so h�ngt er dieses ab.
     * Nutzt er gerade kein Attachable, so h�ngt er das angesprochene Attachable an.
     */
    @Override
    public void attachOrDetachAttachable() {
        if (currentlyAttached != null) {
            unuseAttachables();
        } else {
            useAttachables(checkForAttachablesNearby());
        }
    }


    /**
     * Methode um mit dem Traktor ein Attachable zu nutzen.
     * @param attachable Parameter ist das entsprechende Attachable, das genutzt werden soll.
     */
    public void useAttachables(Attachable attachable) {
        if (attachable == null) {
            GameChat.setNewMessage("No Attachable nearby");
        }else if (attachable.isAttached()==false) {
            currentlyAttached = canBeUsed;
            currentlyAttached.setAttached(true);
            currentlyAttached.entityNode.setVisible(false);
            getAnimation().setAnimationImages();
        }
    }


    /**
     * Methode, um ein aktuell vom Traktor genutztes {@link Attachable} abzuh�ngen/nicht mehr zu nutzen.
     */
    public void unuseAttachables() {
        Group currentlyUsedAttImg = null;
        for (int i = 0; i<FarmingHoleApplication.allAttachables.size(); i++) {
            if(currentlyAttached == FarmingHoleApplication.allAttachables.get(i)) {
                currentlyUsedAttImg = (Group) FarmingHoleApplication.allAttachables.get(i).initialize();
            }
        }
        currentlyUsedAttImg.setScaleX(-factorX);
        currentlyAttached.setAttached(false);
        currentlyAttached = null;
        getAnimation().setAnimationImages();
        currentlyUsedAttImg.setVisible(true);
        int offset;
        if(factorX==-1) offset = 15;
        else offset = 8;
        moveEntityTo(getKoordX()+offset, getKoordY()-25);

    }


    /**
     * Methode, die auf ein {@link Attachable} in der N�he des Fahrzeugs pr�ft.
     * @return Gibt einen {@link Attachable} zur�ck, der sich in der N�he befindet; gibt null zur�ck, falls kein {@link Attachable} in der N�he.
     */
    public Attachable checkForAttachablesNearby() {
        double tractorX = getKoordX();
        double tractorY = getKoordY()-25; //reduced 25 because of the heigth of the menu bar

        for (Attachable a: FarmingHoleApplication.allAttachables) {
            if (Math.abs(a.getKoordX()-tractorX)<10
                    && Math.abs(a.getKoordY()-tractorY)<10
                    && currentlyAttached == null && currentlyActive != null && currentlyActive.getClass() == Tractor.class) {
                canBeUsed = a;
                return canBeUsed;
            }
        }
        return null;
    }

    /**
     * Innere Klasse f�r die Animation des Traktors und alle zugeh�rigen Methoden.
     * @author heiss, knoll, schlager, schulz
     */
    public class Tractor_Animation extends VehicleAnimation{

        /**
         * Bilder werden je nach verwendeter Maschine in das Image-Array gesetzt.
         */
        @Override
        public void setAnimationImages() {
            Image tractor_1 = new Image("file:src/Images/tractor/tractorRight.png");
            Image tractor_2_driving = new Image("file:src/Images/tractor/tractorRight2_driving.png");
            Image tractor_3_driving = new Image("file:src/Images/tractor/tractorRight3_driving.png");
            Image tractor_4_driving = new Image("file:src/Images/tractor/tractorRight4_driving.png");

            Image tractor_w_trailer = new Image("file:src/Images/tractor/tractorRight2_trailer.png");
            Image tractor_w_trailer1 = new Image("file:src/Images/tractor/tractorRight2_trailer.png");
            Image tractor_w_trailer2 = new Image("file:src/Images/tractor/tractorRight3_trailer.png");
            Image tractor_w_trailer3 = new Image("file:src/Images/tractor/tractorRight4_traiiler.png");

            Image tractor_w_seeder = new Image("file:src/Images/tractor/tractorRight_seeder_1.png");
            Image tractor_w_seeder1 = new Image("file:src/Images/tractor/tractorRight_seeder_1.png");
            Image tractor_w_seeder2 = new Image("file:src/Images/tractor/tractorRight_seeder_2.png");
            Image tractor_w_seeder3 = new Image("file:src/Images/tractor/tractorRight_seeder_3.png");

            Image tractor_w_cultivator = new Image("file:src/Images/tractor/tractorRight_cultivator_1.png");
            Image tractor_w_cultivator1 = new Image("file:src/Images/tractor/tractorRight_cultivator_1.png");
            Image tractor_w_cultivator2 = new Image("file:src/Images/tractor/tractorRight_cultivator_2.png");
            Image tractor_w_cultivator3 = new Image("file:src/Images/tractor/tractorRight_cultivator_3.png");

            if (currentlyAttached != null) {
                if(currentlyAttached.getClass() == Trailer.class) {
                    if (vehicleFuelTank.checkFuel() && isUsed()) {
                        images[0] = tractor_w_trailer;
                        images[1] = tractor_w_trailer1;
                        images[2] = tractor_w_trailer2;
                        images[3] = tractor_w_trailer3;
                    } else {
                        images[0] = tractor_w_trailer;
                        images[1] = tractor_w_trailer;
                        images[2] = tractor_w_trailer;
                        images[3] = tractor_w_trailer;
                    }

                } else if(currentlyAttached.getClass() == Seeder.class){
                    if (vehicleFuelTank.checkFuel() && isUsed()) {
                        images[0] = tractor_w_seeder;
                        images[1] = tractor_w_seeder1;
                        images[2] = tractor_w_seeder2;
                        images[3] = tractor_w_seeder3;
                    } else {
                        images[0] = tractor_w_seeder;
                        images[1] = tractor_w_seeder;
                        images[2] = tractor_w_seeder;
                        images[3] = tractor_w_seeder;
                    }

                } else if(currentlyAttached.getClass() == Cultivator.class){
                    if (vehicleFuelTank.checkFuel() && isUsed()) {
                        images[0] = tractor_w_cultivator;
                        images[1] = tractor_w_cultivator1;
                        images[2] = tractor_w_cultivator2;
                        images[3] = tractor_w_cultivator3;
                    } else {
                        images[0] = tractor_w_cultivator;
                        images[1] = tractor_w_cultivator;
                        images[2] = tractor_w_cultivator;
                        images[3] = tractor_w_cultivator;
                    }
                }

            } else {
                if (currentlyActive != null && vehicleFuelTank.checkFuel() && isUsed()) {
                    images[0] = tractor_1;
                    images[1] = tractor_2_driving;
                    images[2] = tractor_3_driving;
                    images[3] = tractor_4_driving;
                } else {
                    images[0] = tractor_1;
                    images[1] = tractor_1;
                    images[2] = tractor_1;
                    images[3] = tractor_1;
                }

            }
        }

    }

    //#EasterEgg: LoremIpsum ;-)


}