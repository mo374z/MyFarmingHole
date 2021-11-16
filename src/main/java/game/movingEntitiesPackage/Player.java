package game.movingEntitiesPackage;

import game.FarmingHoleApplication;
import game.GameChat;
import game.notMovingEntitiesPackage.Buildings;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Beschreibung der m�glichen Aktionen des Spielers.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Player extends MovingEntity{
    /** Instanzvariable des Players, die die Menge an Fuel beschreibt, die der Player gerade mit sich tr�gt. */
    private double fuelInventory;

    /**Konstante, die die maximale Menge an Fuel beschreibt, die der Player mit sich tragen kann.*/
    public static final double MAX_FUEL_INVENTORY = 10.0;

    /** Import eines Bild des Spielers als Image Objekt. */
    private Image playerImg = new Image("file:src/Images/player/player_front_left.png");

    /** Variable, die angibt, ob sich der Spieler gerade in einem {@link Vehicle} befindet oder nicht. */
    private boolean inVehicle;

    /** Fahrzeug, welches vom Spieler gerade genutzt wird. */
    private Vehicle currentlyUsedVehicle;

    /** Fahrzeug, welches vom Spieler genutzt werden kann / in welches der Spieler einsteigen kann. */
    private Vehicle canBeUsed;


    /**
     * Konstruktor, der neben dem Setzen einer initialen X- und Y-Koordinate das importierte Bild des Spielers als ImageView Objekt der entityNode Variable �bergibt.
     * @param koordX Position des Spielers auf der X-Achse
     * @param koordY Position des Spielers auf der Y-Achse
     * @param inVehicle true, wenn sich der Spieler in einem Fahrzeug befindet; false, wenn nicht
     */
    public Player(double koordX, double koordY, boolean inVehicle) {
        super(koordX, koordY);
        entityNode = new Group(new ImageView(playerImg));
        this.inVehicle=inVehicle;
        moveEntityTo(getKoordX(), getKoordY());
    }


    @Override
    public Group initialize() {
        entityNode.setVisible(!inVehicle);
        return (Group) entityNode;
    }


    /**
     * Getter f�r die Instanzvariable inVehicle.
     * @return inVehicle Gibt an, ob sich der Spieler gerade in einem Fahrzeug befindet.
     */
    public boolean isInVehicle() {
        return inVehicle;
    }

    /**
     * Setter f�r die Instanzvariable inVehicle.
     * @param inVehicle Gibt an, ob sich der Spieler gerade in einem Fahrzeug befindet.
     */
    public void setInVehicle(boolean inVehicle) {
        this.inVehicle = inVehicle;
    }

    /**
     * Getter f�r die Instanzvariable FuelInventory
     * @return fuelInventory Die aktuelle Menge an Tankf�llung, die der Spieler mit sich tr�gt.
     */
    public double getFuelInventory() {
        return fuelInventory;
    }

    /**
     * Mit dieser Methode wird ein sich in der N�he des Spielers befindliches {@link Vehicle} aufgetankt.
     */
    @Override
    public void fuelActions() {

        Vehicle vehicleNearby = checkForVehiclesNearby(getKoordX(), getKoordY());

        if (vehicleNearby!=null && fuelInventory > 0 && vehicleNearby.vehicleFuelTank.getFuel() < Vehicle.MAX_FUEL) {
            fuelInventory-=1;
            vehicleNearby.vehicleFuelTank.setFuel(vehicleNearby.vehicleFuelTank.getFuel() + 1);
            GameChat.setNewMessage("1 Liter Fuel: Player -> " + vehicleNearby.getName());
        }

        if(Buildings.evaluateIfEntityInArea(Buildings.gasstationArea, getKoordX(), getKoordY())) {
            if(fuelInventory < MAX_FUEL_INVENTORY) {
                if (FarmingHoleApplication.myMoney >= FarmingHoleApplication.FUEL_PRICE) {
                    fuelInventory+=1.0;
                    FarmingHoleApplication.setMyMoney(FarmingHoleApplication.getMyMoney()-FarmingHoleApplication.FUEL_PRICE);
                    GameChat.setNewMessage("Fuel Inventory +1 Liter");
                    GameChat.setNewMessage("Money -" + FarmingHoleApplication.FUEL_PRICE + " Coins");
                } else {
                    GameChat.setNewMessage("Not enough money to buy fuel!");
                }

            }
        }
    }

    @Override
    public void fillFromOrToGrainTank() {
        // player has no grain tank - thus, filling from or to a grain tank is not possible

    }

    @Override
    public void attachOrDetachAttachable() {
        // you can't attach an attachable to the player.

    }

    @Override
    public void sellGrain() {
        // currently, you can only sell grain from the trailer.

    }


    /**
     * Ruft eine Methode zur Definition der spezifischen Ereignisse auf, die beim Dr�cken der Tasten aus Sicht des Spielers auftreten.
     * @param scene Szene auf die die Methode angewendet werden soll. Hier befinden sich der anzusprechende Spieler.
     */
    public void playerKeyActions(Scene scene) {
        if (!inVehicle) {
            defineKeyActions(scene);
            keyActions(scene);
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
        showGameOverMessage(checkForGameOver());
        updateKoords();
        return null;
    }

    /**
     * Setzt das Einsteigen in ein {@link Vehicle} um.
     * @param vehicle Ein aktuell in der N�he befindliches Vehicles Objekt.
     */
    public void useVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            GameChat.setNewMessage("No Vehicles nearby");
        }else if (vehicle.isUsed()==false) {
            currentlyUsedVehicle = canBeUsed;
            currentlyUsedVehicle.getAnimation().setAnimationImages();
            MovingEntity.currentlyActive = currentlyUsedVehicle;
            vehicle.setUsed(true);
            inVehicle=true;
            vehicle.setCurrentUser(this);
            entityNode.setVisible(false);
            vehicle.vehicleFuelTank.setChangedAnimationToStatic(false);
            vehicle.getAnimation().setAnimationImages();
        }

    }

    /**
     * Getter f�r die Instanzvariable currentlyUsedVehicle.
     * @return currentlyUsedVehicle Aktuell verwendetes Fahrzeug.
     */
    public Vehicle getCurrentlyUsedVehicle() {
        return currentlyUsedVehicle;
    }

    /**
     * Setter f�r die Instanzvariable currentlyUsedVehicle.
     * @param currentlyUsedVehicle Aktuell verwendetes Fahrzeug.
     */
    public void setCurrentlyUsedVehicle(Vehicle currentlyUsedVehicle) {
        this.currentlyUsedVehicle = currentlyUsedVehicle;
    }

    /**
     * �berpr�ft, ob sich Fahrzeuge in der N�he des Spielers befinden.
     * @param playerX X-Koordinaten des Spielers
     * @param playerY Y-Koordinaten des Spielers
     * @return Wenn sich ein nutzbares Fahzeug in der N�he befindet wird das Objekt in eine statische Variable der Klasse Vehicles gespeichert und von der Methode zur�ckgegeben.
     */
    public Vehicle checkForVehiclesNearby(double playerX, double playerY) {

        for (Vehicle v: FarmingHoleApplication.allVehicles) {
            if (Math.abs(v.getKoordX()-playerX)<10
                    && Math.abs(v.getKoordY()-playerY)<10
                    && !inVehicle) {
                canBeUsed = v;
                return canBeUsed;
            }
        }
        return null;
    }

    /**
     * Getter f�r die Instanzvariable canBeUsed.
     * @return canBeUsed Fahrzeug, das vom Spieler benutzt werden kann.
     */
    public Vehicle getCanBeUsed() {
        return canBeUsed;
    }

    /**
     * Setter f�r die Instanzvariable canBeUsed.
     * @param canBeUsed Fahrzeug, das vom Spieler benutzt werden kann.
     */
    public void setCanBeUsed(Vehicle canBeUsed) {
        this.canBeUsed = canBeUsed;
    }

    /** Methode, die pr�ft, ob das Spiel beendet ist ("Game Over").
     * @return Sind alle Fahrzeuge ohne Sprit und der Geldstand gleich Null, wird true zur�ckgegeben; in jedem anderen Fall wird false zur�ckgegeben.
     */
    public boolean checkForGameOver() {
        int counter = 0;
        for (Vehicle v: FarmingHoleApplication.allVehicles) {
            if(v.getVehicleFuelTank().getFuel() <= 0) {
                counter++;
            }
        }
        if (counter == FarmingHoleApplication.allVehicles.size() && FarmingHoleApplication.getMyMoney() == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Methode, mit der die Nachricht angezeigt wird, dass das Spiel beendet ist ("GameOver").
     * @param gameOver Boolean, der angibt, ob das Spiel beendet ist ("Game Over") - in der Regel wird hier die Methode checkForGameOver() aufgerufen.
     */
    public void showGameOverMessage(boolean gameOver) {
        if (gameOver) {
            FarmingHoleApplication.timer.stop();
            ButtonType yesAndClose = new ButtonType("Close the Application", ButtonData.YES);
            Alert alert = new Alert(AlertType.ERROR, "Game over!", yesAndClose);
            alert.setTitle("GAME OVER");
            alert.setHeaderText("You are out of money and fuel.");
            Stage alertDialog = (Stage) alert.getDialogPane().getScene().getWindow();
            alertDialog.getIcons().add(new Image("file:src/Images/startscene/icon.png"));
            alert.setOnHidden(evt -> System.exit(0));
            alert.show();
        }

    }


}
