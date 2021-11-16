package game.movingEntitiesPackage;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Implementierung f�r eine S�hmaschine, Subklasse der Klasse {@link Attachable}
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Seeder extends Attachable{
    /*
     * Diese Klasse ist in dieser Implementierung nicht unbedingt n�tig. F�r S�hmaschine und Grubber w�ren keine gesonderten Klassen notwendig, da die Klassen sich nicht wesentlich voneinander unterscheiden.
     * Hier wurden jedoch eventuelle Erweiterungen des Spiels im Hinterkopf behalten (z.B. dass die S�hmaschine Saatgut ben�tigt).
     * Vor diesem Hintergrund ist es sinnvoll, extra Klassen anzulegen.
     */

    /** Import eines Bildes f�r die S�hmaschine . */
    final static Image seeder = new Image("file:src/Images/seeder/seeder_right.png");

    /**
     * Konstruktor um ein S�hmaschinenobjekt zu initialisieren. Das S�hmaschinenobjekt wird mit den �bergebenen Koordinaten initialisiert.
     * Damit dieses erscheint, muss noch eine entsprechende Group erzeugt (initialize()-Methode) und dargestellt werden.
     * @param koordX  X-Koordinate der S�hmaschine (bei Initialisierung)
     * @param koordY  Y-Koordinate der S�hmaschine (bei Initialisierung)
     * @param name Name der S�hmaschine
     * @param attached gibt an ob die S�hmaschine angeh�ngt ist
     */
    public Seeder(double koordX, double koordY, String name, boolean attached) {
        super(koordX, koordY, name, attached);
        entityNode = new Group(new ImageView(seeder));
        moveEntityTo(getKoordX(), getKoordY());
    }

}
