package game.movingEntitiesPackage;

import game.FarmingHoleApplication;
import game.GameChat;
import game.notMovingEntitiesPackage.Buildings;
import game.notMovingEntitiesPackage.Tiles;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Superklasse aller Fahrzeuge im Spiel.
 * Die Klasse gibt Methoden, innere Klassen und Instanzvariablen vor, die jedes Fahrzeug hat.
 * Dazu z�hlt u.a. die Animation und der Sprittank mit entprechenden Methoden.
 * {@link Tractor} und {@link Harvester} sind Fahrzeuge und erben somit von dieser Klasse.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public abstract class Vehicle extends MovingEntity{

    /** Sprit-Tank des Fahrzeugs als Instanz der inneren Klasse FuelTank */
    protected FuelTank vehicleFuelTank;

    /** Festlegung der maximalen Tank-Kapazit�t von Fahrzeuge */
    public static final double MAX_FUEL = 150.0;

    /** Animation des Fahrzeugs als Instanz der inneren Klasse VehicleAnimation. */
    private VehicleAnimation animation;

    /** Name des Fahrzeugs */
    private String name;

    /** Gibt an, ob das Fahrzeug gerade genutzt wird. */
    private boolean used;

    /** Player-Objekt, das das Fahrzeug aktuell nutzt. */
    private Player currentUser;


    /**
     * Konstruktor f�r ein Fahrzeug.
     * Wird lediglich in den Konstruktoren der Subklassen aufgerufen, da es sich bei Vehicle um eine abstrakte Klasse handelt.
     * @param koordX X-Koordinate des Fahrzeugs bei Initialisierung.
     * @param koordY Y-Koordinate des Fahrzeugs bei Initialisierung.
     */
    public Vehicle(double koordX, double koordY) {
        super(koordX, koordY);
        FarmingHoleApplication.allVehicles.add(this);
    }


    @Override
    public Group initialize() {
        getAnimation().setAnimationImages();
        return (Group) entityNode;
    }


    /**
     * Getter f�r die Instanzvariable used.
     * @return used Gibt an, ob das Fahrzeug gerade genutzt wird.
     */
    public boolean isUsed() {
        return used;
    }


    /**
     * Setter f�r die Instanzvariable used.
     * @param used Angabe, ob das Fahrzeug gerade genutzt wird.
     */
    public void setUsed(boolean used) {
        this.used = used;
    }


    /**
     * Getter f�r den Sprit-Tank des Fahrzeugs.
     * @return vehicleFuelTank Sprit-Tank des Fahrzeugs
     */
    public FuelTank getVehicleFuelTank() {
        return vehicleFuelTank;
    }


    @Override
    public abstract void updateKoords();


    /**
     * Getter f�r den Namen des Fahrzeugs
     * @return name Name des Fahrzeugs
     */
    public String getName() {
        return name;
    }


    /**
     * Setter f�r den Namen des Fahrzeugs
     * @param name Name des Fahrzeugs
     */
    public void setName(String name) {
        this.name = name;
    }



    @Override
    public void defineKeyActions(Scene scene) {
        int dx = 0, dy = 0;
        if (keyActions(scene)[0] && vehicleFuelTank.checkFuel()) { dy -= 1;}
        if (keyActions(scene)[1] && vehicleFuelTank.checkFuel()) { dy += 1;}
        if (keyActions(scene)[2] && vehicleFuelTank.checkFuel()) { dx += 1;}
        if (keyActions(scene)[3] && vehicleFuelTank.checkFuel()) { dx -= 1;}
        moveEntityBy(dx, dy);
    }

    /**
     * Legt die Funktionen fest, was beim Befahren der Felder mit dem {@link Tractor} mit jeweiligem {@link Attachable} dem {@link Harvester} passieren sollen.
     * @param usedVehicle das zum Zeitpunkt des Befahren benutzte Fahrzeug (Davon h�ngen Funktionen ab)
     * @param TileNr Verwendet zur �berpr�fung der Position des Fahrzeugs auf aktuelle Koordinate des Feldes
     */
    public void workOnFields(Vehicle usedVehicle, int TileNr) {
        Tiles t = new Tiles();
        if(usedVehicle.getClass() == Tractor.class && t.checkIfOnFieldTile(TileNr)) {
            Tractor activeTractor = (Tractor) usedVehicle;
            if(activeTractor.currentlyAttached != null && activeTractor.currentlyAttached.getClass() == Cultivator.class && Tiles.getTileName(TileNr) == Tiles.getField_raw_import()) {
                t.changeTile(TileNr, Tiles.getField_grubbed_import());
            } else if (activeTractor.currentlyAttached != null && activeTractor.currentlyAttached.getClass() == Seeder.class && Tiles.getTileName(TileNr) == Tiles.getField_grubbed_import()) {
                t.fieldGrowing(TileNr);
            }
        } else if(usedVehicle.getClass() == Harvester.class && t.checkIfOnFieldTile(TileNr) && Tiles.getMapContent()[TileNr] == Tiles.getField_ripe_import()) {
            Harvester activeHarvester = (Harvester) usedVehicle;
            if (activeHarvester.getGrainTank().fillGrainTank()) {
                t.changeTile(TileNr, Tiles.getField_raw_import());
            }

        }

    }


    /**
     * Methode, um den Tank eines Fahrzeugs aufzuf�llen.
     * Die F�llmenge des Tanks wird erh�ht und das Geld reduziert, sofern genug Geld vorhanden ist.
     */
    public void refuelVehicle() {
        if(Buildings.evaluateIfEntityInArea(Buildings.gasstationArea, getKoordX(), getKoordY())) {
            if (FarmingHoleApplication.getMyMoney() >= FarmingHoleApplication.FUEL_PRICE) {
                if (vehicleFuelTank.getFuel()<MAX_FUEL) {
                    FarmingHoleApplication.setMyMoney(FarmingHoleApplication.getMyMoney()-FarmingHoleApplication.FUEL_PRICE);
                    vehicleFuelTank.refillFuelTank(MAX_FUEL);
                    GameChat.setNewMessage("Fuel +1 Liter");
                    GameChat.setNewMessage("Money -" + FarmingHoleApplication.FUEL_PRICE + " Coins");
                } else {
                    GameChat.setNewMessage("Tank full");
                }
            } else {
                GameChat.setNewMessage("Not enough money to refuel");
            }
        }
    }


    /**
     * Getter, welcher die Animation des entsprechenden Fahrzeugs zur�ckgibt.
     * @return Animation des Fahrzeugs.
     */
    public VehicleAnimation getAnimation() {
        return animation;
    }

    /**
     * Setzt die Animation f�r das entsprechende Fahrzeug.
     * @param animation Die Animation des Fahrzeugs.
     */
    public void setAnimation(VehicleAnimation animation) {
        this.animation = animation;
    }

    /**
     * Getter f�r die Instanzvariable currentUser.
     * @return currentUser Player-Objekt, das Fahrzeug aktuell nutzt.
     *
     */
    public Player getCurrentUser() {
        return currentUser;
    }

    /**
     * Setter f�r die Instanzvariable currentUser.
     * @param currentUser Player-Objekt, das Fahrzeug aktuell nutzt.
     *
     */
    public void setCurrentUser(Player currentUser) {
        this.currentUser = currentUser;
    }


    /**
     * Innere Klasse, die den Sprit-Tank eines Fahrzeugs abbildet.
     * Sie stellt Methoden zur Verf�gung, um den Tankinhalt kontinuierlich zu reduzieren, wenn das Fahrzeug verwendet wird,
     * den Tank wieder aufzuf�llen, sowie Getter und Setter f�r den Tankinhalt.
     * @author heiss, knoll, schlager, schulz
     *
     */
    public class FuelTank{
        /** Aktuelle F�llmenge des Sprit-Tanks */
        private double fuel;

        /** Gibt an, ob die Animation noch zum "stehenden" Fahrzeug ge�ndert werden muss. */
        private boolean changedAnimationToStatic = false;

        /**
         * Methode, mit der der Inhalt des Tanks reduziert wird, wenn das Fahrzeug verwendet wird.
         * Wird sp�ter in einem Timer mitaufgerufen, um den Tankinhalt bei Benutzung des Fahrzeugs kontinuierlich zu reduzieren.
         */
        public void updateFuel() {
            if (isUsed() && fuel>0) {
                fuel-=0.01;
            }
        }

        /**
         * Pr�ft den F�llstand des Sprit-Tanks und stoppt das Fahrzeug, wenn der Tank leer ist.
         * @return true, wenn noch Sprit im Tank ist; false, wenn tank leer
         *
         */
        public boolean checkFuel() {
            if (getFuel()<0.01) {
                if (!changedAnimationToStatic) {
                    changedAnimationToStatic = true;
                    getAnimation().setAnimationImages();
                }
                return false;
            } else {
                changedAnimationToStatic = false;
                return true;
            }

        }



        /**
         * Setter f�r die Instanzvariable changedAnimationToStatic.
         * @param changedAnimationToStatic Gibt an, ob die Animation noch zum "stehenden" Fahrzeug ge�ndert werden muss.
         */
        public void setChangedAnimationToStatic(boolean changedAnimationToStatic) {
            this.changedAnimationToStatic = changedAnimationToStatic;
        }

        /**
         * Getter f�r den F�llstand des Sprit-Tanks.
         * @return fuel F�llstand des Sprit-Tanks des Fahrzeugs.
         */
        public double getFuel() {
            return fuel;
        }

        /**
         * Setter f�r den F�llstand des Sprit-Tanks.
         * @param fuel F�llstand des Sprit-Tanks des Fahrzeugs.
         */
        public void setFuel(double fuel) {
            this.fuel = fuel;
        }

        /**
         * Methode f�r das Bef�llen eines Sprit-Tanks.
         * Bei Aufruf wird die F�llmenge des Tanks um einen Liter erh�ht, sofern der Tank noch nicht voll ist.
         * @param maxFuel Maximale F�llmenge f�r den Fahrzeugtank.
         */
        public void refillFuelTank(double maxFuel) {
            if(fuel<maxFuel) {
                fuel+=1;
            }
        }
    }


    /**
     * Innere Klasse f�r die Animation eines Fahrzeugs.
     * @author heiss, knoll, schlager, schulz
     *
     */
    public abstract class VehicleAnimation {

        /**  Initialisierung der Gruppe "vehicleAnimationGroup". */
        private Group vehicleAnimationGroup;

        /** Initialisierung der Timeline, welche als Basis f�r den zeitlichen Ablauf der Animation dient. */
        private Timeline t =  new Timeline();

        /** Initialisierung des Image-Array "images" f�r die animierten Bilder. */
        Image[] images = new Image[4];

        /**
         * Definition der Gruppe und der Timeline f�r die Animation.
         * @return Gruppe mit animierten Bildern
         */
        public Group animate() {

            vehicleAnimationGroup = new Group(new ImageView(images[0]));

            t.setCycleCount(Timeline.INDEFINITE);


            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(200),
                    (ActionEvent event) -> {
                        vehicleAnimationGroup.getChildren().setAll(new ImageView(images[1]));
                    }));

            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(400),
                    (ActionEvent event) -> {
                        vehicleAnimationGroup.getChildren().setAll(new ImageView(images[2]));
                    }));

            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(600),
                    (ActionEvent event) -> {
                        vehicleAnimationGroup.getChildren().setAll(new ImageView(images[3]));
                    }));

            t.play();

            return vehicleAnimationGroup;
        }

        /**
         * Bilder f�r die Animation werden in das Image-Array gesetzt.
         */
        public abstract void setAnimationImages();

        /**
         * Stoppen der Animation.
         */
        public void stopAnimation() {
            t.stop();
        }

        /**
         * Start der Animation.
         */
        public void startAnimation() {
            t.play();
        }
    }

}
