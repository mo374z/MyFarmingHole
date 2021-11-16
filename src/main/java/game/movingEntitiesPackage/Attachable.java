package game.movingEntitiesPackage;

import game.FarmingHoleApplication;
import javafx.geometry.Bounds;
import javafx.scene.Group;

/**
 * Defintion der Anh�nger, Subklasse von Moving Entities
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public abstract class Attachable extends MovingEntity{

    /** Name des Anh�ngers. */
    private String name;

    /** Gibt an, ob der Anh�nger gerade an einem Traktor angeh�ngt ist. */
    private boolean attached;


    /**
     * Konstruktor der bei Erstellung eines Anh�ngers aufgerufen wird.
     * @param koordX X-Koordinate des Anh�ngers (bei Initialisierung).
     * @param koordY Y-Koordinate des Anh�ngers (bei Initialisierung).
     * @param name Name des Anh�ngers.
     * @param attached Gibt an, ob der Anh�nger gerade genutzt wird.
     */
    public Attachable(double koordX, double koordY, String name, boolean attached) {
        super(koordX, koordY);
        setName(name);
        this.attached = attached;
        FarmingHoleApplication.allAttachables.add(this);
    }


    @Override
    public Group initialize() {
        entityNode.setVisible(!attached);
        return (Group) entityNode;
    }


    /**
     * Getter f�r die Instanzvariable attached.
     * @return attached Gibt an, ob der Anh�nger genutzt wird.
     */
    public boolean isAttached() {
        return attached;
    }


    /**
     * Setter f�r die Instanzvariable attached.
     * @param attached Gibt an, ob der Anh�nger benutzt wird.
     */
    public void setAttached(boolean attached) {
        this.attached = attached;
    }


    /**
     * Getter f�r den Namen des Anh�ngers.
     * @return name Name des Anh�ngers.
     */
    public String getName() {
        return name;
    }


    @Override
    public void updateKoords() {
        Bounds boundsInScene = entityNode.localToScene(entityNode.getBoundsInLocal());
        setKoordX(boundsInScene.getCenterX());
        setKoordY(boundsInScene.getCenterY()-25);	//um 25 nach unten verschoben wegen des Game-Men�s
    }


    /**
     * Setter f�r den Namen des Anh�ngers.
     * @param name Name des Anh�ngers.
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Group updateEntity() {
        //no need for this method here
        return null;
    }


    @Override
    public void fillFromOrToGrainTank() {
        // not needed here
    }


    @Override
    public void attachOrDetachAttachable() {
        // not needed here
    }


    @Override
    public void fuelActions() {
        // not needed here
    }


    @Override
    public void sellGrain() {
        // not needed here
    }
}
