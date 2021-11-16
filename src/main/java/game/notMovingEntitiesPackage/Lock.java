package game.notMovingEntitiesPackage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Implementiert die Schl�sser �ber den noch nicht freigeschalteten Feldern der Klasse {@link Tiles} und deren Funktionen.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Lock {

    /**
     *
     */
    public Lock isNearby;

    /**
     * Importiert das Bild f�r die Darstellung eines Schlosses
     */
    private Image lockImage = new Image("file:src/Images/fields/locked.png");
    /**
     * ImageView Objekt auf Basis des importierten Bilds f�r ein Lock.
     */
    private ImageView lock_view = new ImageView(lockImage);

    /** Variable, die den Abstand des Locks nach links festlegt. */
    private double leftAnchor;

    /** Variable, die den Abstand des Locks nach oben festlegt. */
    private double topAnchor;

    /** Variable, die zeigt, ob das Lock gerade sichtbar ist. */
    public boolean isVisible;

    /** Variable, die den Preis des Locks beinhaltet. */
    public int price;

    /** Liste, die alle Feldnummern beinhaltet, die das Lock behandelt. */
    private int[] referredFieldNrs;

    /**
     * Konstruktor, der bei der Erstellung eines Locks aufgerufen wird.
     * @param x x-Position des Locks
     * @param y y-Position des Locks
     * @param price Preis des Locks und damit des referenzierten Feldes
     * @param referredFieldNrs Liste, die alle vom Lock referenzierten Feldnummern enth�lt.
     */
    public Lock(double x, double y, int price, int[] referredFieldNrs) {
        this.leftAnchor = x;
        this.topAnchor = y;
        this.price = price;
        this.referredFieldNrs = referredFieldNrs;
        isVisible = true;
    }


    /**
     * Getter f�r den Preis.
     * @return Preis eines Locks.
     */

    public int getPrice() {
        return price;
    }


    /**
     * Setter f�r den Preis eines Locks.
     * @param price Preis, der f�r das Lock festgelegt werden soll.
     */
    public void setPrice(int price) {
        this.price = price;
    }


    /**
     * F�gt ein neues Lock an der im Konstruktor �bergebenen Position hinzu.
     * @return ImageView OBjekt des Lock Objekts an einer festgelegten Stelle.
     */
    public ImageView addLock() {
        AnchorPane.setLeftAnchor(lock_view, leftAnchor);
        AnchorPane.setTopAnchor(lock_view, topAnchor);
        return lock_view;
    }


    /**
     * Getter f�r die x-Koordinate des Locks.
     * @return Abstand zur linken Seite, welcher der x-Koordinate entspricht.
     */
    public double getKoordX() {
        return leftAnchor;
    }

    /**
     * Getter f�r die y-Koordinate des Locks.
     * @return Abstand zum oberen Rand, welcher der y-Koordinate entspricht.
     */
    public double getKoordY() {
        return topAnchor;
    }


    /**
     * Getter f�r die Liste der referenzierten Felder.
     * @return Liste aller vom Lock referenzierten Felder.
     */
    public int[] getReferredFieldNrs() {
        return referredFieldNrs;
    }


}
