package game.movingEntitiesPackage;

import game.FarmingHoleApplication;
import game.GameChat;
import game.Music;
import game.notMovingEntitiesPackage.Bridge;
import game.notMovingEntitiesPackage.Buildings;
import game.notMovingEntitiesPackage.Lock;
import game.notMovingEntitiesPackage.Tiles;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 * Ist die Superklasse aller bewegten Objekte und stellt allgemeine Methoden diesbez�glich.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public abstract class MovingEntity {

    /** X-Koordinate des bewegbaren Objekts. */
    private double koordX;

    /** Y-Koordinate des bewegbaren Objekts. */
    private double koordY;

    /** Speichert das aktuell aktive Objekt. */
    public static MovingEntity currentlyActive;

    /** Enth�lt die Darstellung des Objekts. */
    protected Node entityNode;

    /** Zeigt die aktuelle Bewegungsrichtung des Objekts an. Ist wahr wenn es sich nach oben bewegt. */
    protected boolean moveUp;

    /** Zeigt die aktuelle Bewegungsrichtung des Objekts an. Ist wahr wenn es sich nach unten bewegt. */
    protected boolean moveDown;

    /** Zeigt die aktuelle Bewegungsrichtung des Objekts an. Ist wahr wenn es sich nach links bewegt. */
    protected boolean moveLeft;

    /** Zeigt die aktuelle Bewegungsrichtung des Objekts an. Ist wahr wenn es sich nach rechts bewegt. */
    protected boolean moveRight;

    /**
     * Konstruktor der Klasse MovingEntity.
     * Schreibt den erbenden Klassen vor, dass bei der Erstellung eines Objekts X-und Y-Koordinaten mitgegeben werden m�ssen.
     * @param koordX Position des Objekts auf der X-Achse.
     * @param koordY Position des Objekts auf der Y-Achse.
     */
    public MovingEntity(double koordX, double koordY) {
        this.koordX = koordX;
        this.koordY = koordY;
    }


    /**
     *  Methode, die den erbenden Klassen eine Initialiserung des Bilds/der Animation des bewegbaren Objekts vorschreibt. Die Methode wird von jeder einzelnen Subklasse �berschrieben und nach Bedarf angepasst.
     * @return die Darstellung des Objekts in einem Node-Objekt.
     */
    public abstract Node initialize();

    /**
     * �berpr�ft ob die Nummer des Felds auf ein Objekt verweist, das als nicht betretbar markiert wurde.
     * @param TileNr Nummer des Felds auf dem sich das untersuchte Objekt befindet.
     * @return gibt true zur�ck, sofern das Feld sich in der Liste der nicht betretbaren Felder befindet.
     */
    public boolean checkIfOnNotWalkableTile(int TileNr) {
        for (Image i: Tiles.notWalkableTiles) {
            if (i.equals(Tiles.getTileName(TileNr))) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Methode, die den erbenden Klassen eine Methode zur st�ndigen Aktualisierung ihrer Koordinaten vorschreibt. Die Methode wird von jeder einzelnen Subklasse �berschrieben und nach Bedarf angepasst.
     */
    public abstract void updateKoords();

    /**
     * Methode, die den erbenden Klassen eine Methode zur st�ndigen Aktualisierung des Entities vorschreibt. Die Methode wird von jeder einzelnen Subklasse �berschrieben und nach Bedarf angepasst.
     * In ihr werden dann Methoden, die st�ndig zusammen aufgerufen werden m�ssen, aufgerufen,
     * @return Gibt das entityNode des bewegbaren Objekts zur�ck.
     */
    public abstract Group updateEntity();

    /**
     * Berechnet auf Basis der X-und Y-Koordinate die Nummer des Felds.
     * @param xCoord X-Koordinate des Objekts.
     * @param yCoord Y-Koordinate des Objekts.
     * @return Ganzzahl, die die Nummer des Felds repr�sentiert.
     */
    public static int getTileNr(double xCoord, double yCoord) {
        int xTile = (int) (xCoord)/32;
        int yTile = (int) (yCoord)/32;
        int tileNr = (yTile)*30+xTile;
        return tileNr;
    }

    /**
     * Legt Aktionen auf Basis der gedr�ckten Taste fest.
     * Im Falle von WASD wird sowohl das Dr�cken, als auch das Loslassen der Taste detektiert, da sich das Objekt.
     * w�hrend diesem Zeitraum st�ndig bewegen soll. Dann wird der Wert der entsprechenden boolean Variable auf wahr gesetzt.
     * Eine Diagonalbewegung wird durch das Setzen der orthogonal stehenden Richtungen auf false nicht erm�glicht.
     * Im Falle von E wird gepr�ft, ob aus einem {@link Vehicle} ausgestiegen werden kann.
     * Im Falle von T wird gepr�ft ob sich ein Geb�ude der Klasse {@link Buildings} zur Interaktion in der N�he befindet und gegebenenfalls mit diesem interagiert.
     * @param scene Szene auf die die Methode angewendet werden soll. Hier befinden sich die anzusprechenden Objekte.
     * @return Ein Array in dem die Werte zur Bewegung dargestellt werden.
     */
    public boolean[] keyActions(Scene scene) {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W: moveUp=true; moveRight=false; moveLeft=false; break;
                    case S: moveDown=true; moveRight=false; moveLeft=false; break;
                    case A: moveLeft=true; moveUp=false; moveDown=false; entityNode.setScaleX(-1.0); break; //spiegelt das Bild verkehrt herum
                    case D: moveRight=true; moveUp=false; moveDown=false; entityNode.setScaleX(1.0); break; //spiegelt das Bild erneut
                    case E: useOrUnuseVehicle(); moveUp=false; moveDown=false; moveRight=false; moveLeft=false; break;
                    case B: buyField(); break;
                    case F: fillFromOrToGrainTank(); break;
                    case Q: attachOrDetachAttachable(); break;
                    case R: fuelActions(); break;
                    case T: sellGrain(); break;
                    case X: repairBridge(); break;
                    default: break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W: moveUp=false; break;
                    case S: moveDown=false; break;
                    case A: moveLeft=false; break;
                    case D: moveRight=false; break;
                    default: break;
                }
            }
        });

        boolean[] movements = new boolean[] {moveUp, moveDown, moveRight, moveLeft};
        return movements;
    }

    /**
     * Legt fest, bei welchen gedr�ckten Tasten die Koordinaten in welcher Gr��e ge�ndert werden, um die Position des Objekts zu �ndern.
     * @param scene Szene auf die die Methode angewendet werden soll. Hier befinden sich die anzusprechenden Objekte.
     */
    public void defineKeyActions(Scene scene) {
        int dx = 0, dy = 0;
        if (keyActions(scene)[0]) { dy -= 1;}
        if (keyActions(scene)[1]) { dy += 1;}
        if (keyActions(scene)[2]) { dx += 1;}
        if (keyActions(scene)[3]) { dx -= 1;}
        moveEntityBy(dx, dy);
    }

    /**
     * Berechnet aus der relativen �nderung der Koordinaten die absoluten Koordinaten auf die das entsprechende Objekt gesetzt werden soll.
     * @param dx �nderung der X Koordinate.
     * @param dy �nderung der Y Koordinate.
     */
    public void moveEntityBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = entityNode.getBoundsInLocal().getWidth()  / 2;
        final double cy = entityNode.getBoundsInLocal().getHeight() / 2;

        double x = cx + entityNode.getLayoutX() + dx;
        double y = cy + entityNode.getLayoutY() + dy;

        moveEntityTo(x, y);
    }

    /**
     * Setzt das entsprechende Objekt auf die �bergebenen Koordinaten. Dabei wird gepr�ft ob sich das Objekt aus dem Rand des Fensters oder auf ein nicht betretbare Feld bewegen w�rde und wenn dies zutrifft nicht bewegt.
     * @param x X-Koordinate auf die gesetzt werden soll.
     * @param y Y-Koordinate auf die gesetzt werden soll.
     */
    public void moveEntityTo(double x, double y) {
        final double cx = entityNode.getBoundsInLocal().getWidth()  / 2;
        final double cy = entityNode.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
                x + cx <= FarmingHoleApplication.windowWidth &&
                y - cy >= 0 &&
                y + cy <= FarmingHoleApplication.windowHeight && !checkIfOnNotWalkableTile(getTileNr(x+10,y+5)) && !checkIfOnNotWalkableTile(getTileNr(x-10,y+5)) ) {
            entityNode.relocate(x - cx, y - cy);
        }
    }

    /**
     * Methode, mit der ein MovingEntity einen Getreidetank bef�llen kann oder seinen eigenen Getreidetank bef�llen kann.
     * Diese Methode wird von jedem MovingEntity selbst implementiert und so an die entsprechenden Bed�rfnisse angepasst.
     */
    public abstract void fillFromOrToGrainTank();

    /**
     * Methode, mit der ein MovingEntity ein {@link Attachable} anh�ngen oder abh�ngen kann.
     * Diese Methode wird von jedem MovingEntity selbst implementiert und so an die entsprechenden Bed�rfnisse angepasst.
     */
    public abstract void attachOrDetachAttachable();

    /**
     * Methode, in der alle Funktionen in Zusammenhang mit Sprit f�r ein MovingEntity implementiert werden.
     * Diese Methode wird von jedem MovingEntity selbst implementiert und so an die entsprechenden Bed�rfnisse angepasst.
     */
    public abstract void fuelActions();

    /**
     * Methode, mit der ein MovingEntity Getreide verkaufen kann.
     * Diese Methode wird lediglich vom {@link Tractor} implementiert und ist auch nur dann aufrufbar, wenn ein {@link Trailer} angeh�ngt ist.
     */
    public abstract void sellGrain();


    /**
     * Methode, die das ausssteigen aus {@link Vehicle} erm�glicht.
     * Dabei wird auch gepr�ft, ob sich der Spieler beim Aussteigen auf einem nicht betretbaren Feld befinden w�rde
     * und demensprechend das Aussteigen verwehrt, da sonst das {@link Vehicle} "feststecken" w�rde.
     */
    public void useOrUnuseVehicle() {
        if(checkIfOnNotWalkableTile(getTileNr(getKoordX(), getKoordY()))) {
            GameChat.setNewMessage("Please teleport to spawn before getting out of your Vehicle");
        } else if (currentlyActive != null && currentlyActive.getClass() == Player.class && !((Player)currentlyActive).isInVehicle()) {
            ((Player)currentlyActive).useVehicle(((Player)currentlyActive).checkForVehiclesNearby(getKoordX(), getKoordY()));
        } else if (currentlyActive != null && currentlyActive.getClass().getSuperclass() == Vehicle.class) {
            Player playerInTheVehicle = ((Vehicle)currentlyActive).getCurrentUser();
            ((Vehicle)currentlyActive).setUsed(false);
            ((Vehicle)currentlyActive).setCurrentUser(null);
            playerInTheVehicle.getCurrentlyUsedVehicle().getAnimation().setAnimationImages();
            currentlyActive = playerInTheVehicle;
            playerInTheVehicle.setInVehicle(false);
            playerInTheVehicle.moveEntityTo(getKoordX(), getKoordY()-25); //reduced 25 because of the heigth of the menu bar
            playerInTheVehicle.entityNode.setVisible(true);

        }
    }


    /**
     * �berpr�ft ob sich Geb�ude, mit denen interagiert werden kann in der N�he befinden.
     * @param KoordX X-Koordinaten des aktuell aktiven bewegbaren Objekts.
     * @param KoordY Y-Koordinaten des aktuell aktiven bewegbaren Objekts.
     * @return Name des aktuell in der N�he befindlichen Geb�udes. Dieser wird zur Anzeige des Tooltips genutzt.
     */
    public String checkForBuildingsNearby(double KoordX, double KoordY) {
        if (Buildings.evaluateIfEntityInArea(Buildings.farmhouseArea, KoordX, KoordY)) {
            Buildings.setName("Farmhouse");
            return Buildings.getName();
        } else if (Buildings.evaluateIfEntityInArea(Buildings.barnArea, KoordX, KoordY)) {
            Buildings.setName("Barn");
            return Buildings.getName();
        } else if (Buildings.evaluateIfEntityInArea(Buildings.siloArea, KoordX, KoordY)) {
            Buildings.setName("Silos");
            return Buildings.getName();
        } else if (Buildings.evaluateIfEntityInArea(Buildings.gasstationArea, KoordX, KoordY)) {
            Buildings.setName("Gasstation");
            return Buildings.getName();
        } else if (Buildings.evaluateIfEntityInArea(Buildings.bridgeArea, KoordX, KoordY)) {
            Buildings.setName("Bridge");
            return Buildings.getName();
        } else {
            Buildings.setName(null);
            return Buildings.getName();
        }
    }

    /**
     * Pr�ft, ob sich Schl�sser der Klasse {@link Lock} ( und damit ungekaufte Felder) in der N�he befinden.
     * @return Gibt ein Lock-Objekt eines sich in der N�he befindlichen Schlossses zur�ck; Wird kein Schloss gefunden, so wird null zur�ckgegeben.
     */
    public Lock checkForLocksNearby() {

        //calculate Offset of Lock
        double entityX = getKoordX()-15.0;
        double entityY = getKoordY()-35.0;

        for (Lock l: Tiles.getAllLocks()) {
            if (l != null && Math.abs(l.getKoordX()-entityX)<20
                    && Math.abs(l.getKoordY()-entityY)<20 ) {
                l.isNearby = l;
                return l.isNearby;
            }
        }
        return null;
    }

    /**
     * Methode, mit der ein komplettes Feld gekauft werden kann.
     */
    public void buyField() {
        Lock lock = checkForLocksNearby();
        if(lock == null) {
            GameChat.setNewMessage("No Field to buy nearby");
        } else if(lock.price <= FarmingHoleApplication.getMyMoney()) {
            Tiles t = new Tiles();
            FarmingHoleApplication.setMyMoney(FarmingHoleApplication.getMyMoney()-lock.price);
            t.hideLock(lock);
            t.makeFieldmyOwn(lock);
            t.updateLocks();
        } else {
            GameChat.setNewMessage("You don't have enough money to buy this field");
        }
    }

    /**
     * Methode, mit der ein MovingEntity die {@link Bridge} reparieren und so das Spiel gewinnen kann.
     */
    public void repairBridge() {
        if(Buildings.evaluateIfEntityInArea(Buildings.bridgeArea, koordX, koordY) && !FarmingHoleApplication.bridge.isRepaired()) {
            if(FarmingHoleApplication.getMyMoney()>=Bridge.bridgePrice) {
                FarmingHoleApplication.bridge.changeToRepairedBridge();
                FarmingHoleApplication.setMyMoney(FarmingHoleApplication.getMyMoney()-Bridge.bridgePrice);
                Music.muteAll();
                GameChat.setNewMessage("Congratulations! You repaired the bridge an won the game!");
            } else {
                GameChat.setNewMessage("Not enough money to repair the bridge");
            }
        }
    }


    /**
     * Getter Methode f�r Y-Koordinate
     * @return koordY Y-Koordinate des bewegbaren Objekts.
     */
    public double getKoordY() {
        return koordY;
    }


    /**
     * Setter Methode f�r Y-Koordinate.
     * @param koordY Y-Koordinate des bewegbaren Objekts.
     */
    public void setKoordY(double koordY) {
        this.koordY = koordY;
    }


    /**
     * Getter Methode f�r X-Koordinate.
     * @return X-Koordinate des bewegbaren Objekts.
     */
    public double getKoordX() {
        return koordX;
    }


    /**
     * Setter Methode f�r X-Koordinate.
     * @param koordX X-Koordinate des bewegbaren Objekts.
     */
    public void setKoordX(double koordX) {
        this.koordX = koordX;
    }

}
