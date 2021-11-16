package game.movingEntitiesPackage;

import game.notMovingEntitiesPackage.GrainTank;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Implementierung f�r einen Kipper, Subklasse der Klasse {@link Attachable}.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Trailer extends Attachable{

    /** Import eines Bildes f�r den Kipper . */
    final static Image trailer = new Image("file:src/Images/Trailer/trailer_small_right.png");

    /** Getreidetank des Trailers, damit dieser Getreide transportieren kann (insbesondere zum Shop). */
    private GrainTank grainTank;

    /**
     * Konstruktor um ein Kipper zu initialisieren. Das Anh�ngerwagenobjekt wird mit den �bergebenen Koordinaten initialisiert.
     * Damit dieses erscheint, muss noch eine entsprechende Group erzeugt (initialize()-Methode) und dargestellt werden.
     * @param koordX X-Koordinate des Kippers (bei Initialisierung).
     * @param koordY Y-Koordinate des Kippers (bei Initialisierung).
     * @param name Name des Kippers.
     * @param grain Menge an Getreide, die sich bei Initialisierung im Kipper befinden soll.
     * @param attached gibt an ob der Kipper angeh�ngt ist.
     */
    public Trailer(double koordX, double koordY, String name, int grain, boolean attached) {
        super(koordX, koordY, name, attached);
        entityNode = new Group(new ImageView(trailer));
        grainTank = new GrainTank(grain,100);
        moveEntityTo(getKoordX(), getKoordY());
    }


    /**
     * Getter f�r den Getreidetank des Kippers.
     * @return grainTank Getreidetank des Kippers.
     */
    public GrainTank getGrainTank() {
        return grainTank;
    }

}
