package game.movingEntitiesPackage;


import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Grubber, Subklasse der {@link Attachable}
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Cultivator extends Attachable {
    /*
     * Diese Klasse ist in dieser Implementierung nicht unbedingt n�tig. F�r S�hmaschine und Grubber w�ren keine gesonderten Klassen notwendig, da die Klassen sich nicht wesentlich voneinander unterscheiden.
     * Hier wurden jedoch eventuelle Erweiterungen des Spiels im Hinterkopf behalten (z.B. dass die S�hmaschine Saatgut ben�tigt).
     * Vor diesem Hintergrund ist es sinnvoll, extra Klassen anzulegen.
     */

    /** Import eines Bildes f�r den Grubber . */
    final static Image cultivator = new Image("file:src/Images/cultivator/cultivator_right.png");

    /**
     * Konstruktor um ein Grubberobjekt zu initialisieren. Das Grubberobjekt wird mit den �bergebenen Koordinaten initialisiert.
     * Damit dieses erscheint, muss noch eine entsprechende Group erzeugt (initialize()-Methode) und dargestellt werden.
     * @param koordX X-Koordinate des Grubbers (bei Initialisierung)
     * @param koordY Y-Koordinate des Grubbers (bei Initialisierung)
     * @param name Name des Grubbers
     * @param attached gibt an ob der Grubber angeh�ngt ist
     */
    public Cultivator(double koordX, double koordY, String name, boolean attached) {
        super(koordX, koordY, name, attached);
        entityNode = new Group(new ImageView(cultivator));
        moveEntityTo(getKoordX(), getKoordY());
    }

}
