package game.notMovingEntitiesPackage;

import game.FarmingHoleApplication;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/*
 * Die einzelnen Geb�ude auf der Karte werden hier nicht nach einem objektorientierten Paradigma implementiert. Dies k�nnte durch eine
 * Umstrukturierung dieser Klasse erreicht werden. Daher folgender Vorschalg bei gen�gend Zeit: Bei Aufruf des Konstruktors kann ein
 * Building-Objekt erstellt werden, dem eine Position auf der Map, ein Bereich (indem mit einem Geb�ude interagiert werden kannund ein
 * Bild) �bergeben wird. Sp�ter k�nnte auch die Br�cke von der umstrukturierten Building Klasse erben und damit ein spezielles Building-Objekt darstellen.
 */

/**
 * Dient zur Festlegung der Position von Geb�uden und der Definition von Bereichen in denen sie angesprochen
 * werden k�nnen.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Buildings {

    /** Finale Variable, die den Abstand des Farmhouse nach links festlegt. */
    private static final double farmhouseLeftAnchor = 615.0;

    /** Finale Variable, die den Abstand des Farmhouse nach oben festlegt. */
    private static final double farmhouseTopAnchor = 245.0;

    /** Finale Variable, die den Abstand des Barn nach links festlegt. */
    private static final double barnLeftAnchor = 615.0;

    /** Finale Variable, die den Abstand des Barn nach oben festlegt. */
    private static final double barnTopAnchor = 165.0;

    /** Finale Variable, die den Abstand des Silo nach links festlegt. */
    private static final double siloLeftAnchor = 360.0;

    /** Finale Variable, die den Abstand des Silo nach oben festlegt. */
    private static final double siloTopAnchor = 200.0;

    /** Finale Variable, die den Abstand der Tankstelle nach links festlegt. */
    private static final double gasstationLeftAnchor = 775.0;

    /** Finale Variable, die den Abstand der Tankstelle nach oben festlegt. */
    private static final double gasstationTopAnchor = 360.0;

    /** Finale Variable, die den Abstand der Br�cke nach links festlegt. */
    private static final double bridgeLeftAnchor = 352.0;

    /** Finale Variable, die den Abstand der Br�cke nach oben festlegt. */
    private static final double bridgeTopAnchor = 575.0;

    /** M�glichkeit die Namen der Geb�ude zu speichern. */
    private static String name;

    /** Koordinaten, welche den Bereich festlegen, in denen das Farmhouse von sich bewegenden Objekten angesprochen werden kann. */
    public static final double[] farmhouseArea = {580.0, 700.0, 300.0, 350.0};

    /** Koordinaten, welche den Bereich festlegen, in denen der Barn von sich bewegenden Objekten angesprochen werden kann. */
    public static final double[] barnArea = {580.0, 700.0, 220.0, 280.0};

    /** Koordinaten, welche den Bereich festlegen, in denen das Silo von sich bewegenden Objekten angesprochen werden kann. */
    public static final double[] siloArea = {390.0, 420.0, 250.0, 330.0};

    /** Koordinaten, welche den Bereich festlegen, in denen die Tankstelle von sich bewegenden Objekten angesprochen werden kann. */
    public static final double[] gasstationArea = {825.0, 889.0, 370.0, 435.0};

    /** Koordinaten, welche den Bereich festlegen, in denen die Br�cke von sich bewegenden Objekten angesprochen werden kann. */
    public static final double[] bridgeArea = {380.0, 425.0, 595.0, 630.0};


    /**
     * F�gt nach dem Import die Geb�ude in einem Anchor Pane Objekt an der zuvor durch Variablen definierten Stelle ein.
     * @return bef�lltes AnchorPane Objekt
     */
    public AnchorPane addBuildings() {
        AnchorPane buildingPane = new AnchorPane();

        ImageView farm_house_view = new ImageView(new Image("file:src/Images/buildings/farm_house.png"));
        AnchorPane.setLeftAnchor(farm_house_view, farmhouseLeftAnchor);
        AnchorPane.setTopAnchor(farm_house_view, farmhouseTopAnchor);

        ImageView stall_shed_view = new ImageView(new Image("file:src/Images/buildings/stall_shed.png"));
        AnchorPane.setLeftAnchor(stall_shed_view, barnLeftAnchor);
        AnchorPane.setTopAnchor(stall_shed_view, barnTopAnchor);

        ImageView silo_view = new ImageView(new Image("file:src/Images/buildings/silos.png"));
        AnchorPane.setLeftAnchor(silo_view, siloLeftAnchor);
        AnchorPane.setTopAnchor(silo_view, siloTopAnchor);

        ImageView gasstation_view = new ImageView(new Image("file:src/Images/buildings/GasStation.png"));
        AnchorPane.setLeftAnchor(gasstation_view, gasstationLeftAnchor);
        AnchorPane.setTopAnchor(gasstation_view, gasstationTopAnchor);

        AnchorPane.setLeftAnchor(FarmingHoleApplication.bridge.getBridgeBroken(), Buildings.getBridgeleftanchor());
        AnchorPane.setTopAnchor(FarmingHoleApplication.bridge.getBridgeBroken(), Buildings.getBridgetopanchor());
        AnchorPane.setLeftAnchor(FarmingHoleApplication.bridge.getBridgeComplete(), Buildings.getBridgeleftanchor());
        AnchorPane.setTopAnchor(FarmingHoleApplication.bridge.getBridgeComplete(), Buildings.getBridgetopanchor());
        AnchorPane.setLeftAnchor(FarmingHoleApplication.bridge.getHammerImage(), Buildings.getBridgeleftanchor()+32);
        AnchorPane.setTopAnchor(FarmingHoleApplication.bridge.getHammerImage(), Buildings.getBridgetopanchor()+32);

        buildingPane.getChildren().addAll(stall_shed_view, farm_house_view, silo_view, gasstation_view, FarmingHoleApplication.bridge.getBridgeBroken(), FarmingHoleApplication.bridge.getBridgeComplete(), FarmingHoleApplication.bridge.getHammerImage());

        return buildingPane;
    }

    /**
     * �berpr�ft ob das bewegte Objekt auf Basis der Position mit dem Geb�ude interagieren kann.
     * @param buildingArea Legt den Bereich fest.
     * @param KoordX X Koordinate des untersuchten bewegten Objekts.
     * @param KoordY Y Koordinate des untersuchten bewegten Objekts.
     * @return wenn sich das Objekt im Bereich befindet true andernfalls false
     */
    public static boolean evaluateIfEntityInArea(double[] buildingArea, double KoordX, double KoordY) {
        if(buildingArea[0]<KoordX && buildingArea[1]>KoordX
                && buildingArea[2]<KoordY && buildingArea[3]>KoordY) {
            return true;
        }
        return false;
    }

    /**
     * Getter f�r die finale Variable bridgeLeftAnchor. Diese gibt den Abstand der {@link Bridge} nach links an.
     * @return die finale Variable bridgeLeftAnchor, die den Abstand der Br�cke nach links angibt.
     */
    public static double getBridgeleftanchor() {
        return bridgeLeftAnchor;
    }

    /**
     * Getter f�r die finale Variable bridgeTopAnchor. Diese gibt den Abstand der Br�cke nach oben an.
     * @return die finale Variable bridgeTopAnchor, die den Abstand der Br�cke nach oben angibt.
     */
    public static double getBridgetopanchor() {
        return bridgeTopAnchor;
    }


    /**
     * Statische Methode, die den Namen des Building Objekts zur�ckgibt.
     * @return Name des Building Objekts.
     */
    public static String getName() {
        return name;
    }

    /**
     * Statische Methode, die den Namen des Building Objekts setzt.
     * @param name Name, der gesetzt werden soll.
     */
    public static void setName(String name) {
        Buildings.name = name;
    }



}
