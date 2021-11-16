package game.notMovingEntitiesPackage;

import game.FarmingHoleApplication;
import game.movingEntitiesPackage.MovingEntity;
import game.movingEntitiesPackage.Vehicle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;


/**
 * Importiert und rendert Bilder f�r die Darstellung des Spielfelds.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Tiles {

    /**
     * Darstellung Gras.
     */
    private static Image field_meadow_import;
    /**
     * Darstellung Wald.
     */
    private static Image grass_dark_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_left_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_left_top_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_right_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_right_top_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_straight_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_straight_horizontal_import;
    /**
     * Darstellung Weg.
     */
    private static Image way_import;
    /**
     * Darstellung Fundament.
     */
    private static Image foundation_field_housing_import;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_water_corner_left;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_water_corner_right;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_water_cliff_mid_1;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_water_cliff_mid_2;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_leftside1;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_leftside2;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_rightside1;
    /**
     * Darstellung Grenze zum Wasser.
     */
    private static Image border_rightside2;
    /**
     * Darstellung Grenze zum Strand.
     */
    private static Image sand_grass_crossing;
    /**
     * Darstellung Grenze zum Strand.
     */
    private static Image sand_grass_crossing_leftside;
    /**
     * Darstellung Grenze zum Strand.
     */
    private static Image sand_grass_crossing_rightside;
    /**
     * Darstellung eines Felds.
     */
    private static Image field_raw_import;
    /**
     * Darstellung gegrubberten Felds.
     */
    private static Image field_grubbed_import;
    /**
     * Darstellung ges��ten Felds.
     */
    private static Image field_seaded_import;
    /**
     * Darstellung eines Felds im Wachstum.
     */
    private static Image field_phase1_import;
    /**
     * Darstellung eines Felds im Wachstum.
     */
    private static Image field_phase2_import;
    /**
     * Darstellung eines Felds mit reifer Bepflanzung.
     */
    private static Image field_ripe_import;
    /**
     * Darstellung eines dunklen Felds
     */
    private static Image field_raw_dark_import;

    /**
     * Array, welches alle Elemente des 20x30 Spielfelds beinhaltet.
     */
    private static Image[] mapContent = new Image[600];

    /**
     * Dient zur Festlegung nicht begehbarer Image Objekte.
     * Da es sich hierbei um eine finale nicht nachtr�glich zu �ndernde Liste handelt, ist diese mit dem Zugriffsmodifikator public versehen.
     */
    public final static Image[] notWalkableTiles = new Image[10];

    /**
     * Dient zur Festlegung nicht beackerbarer Image Objekte.
     * Da es sich hierbei um eine finale nicht nachtr�glich zu �ndernde Liste handelt, ist diese mit dem Zugriffsmodifikator public versehen.
     */
    public final static Image[] fields = new Image[6];

    /**
     * Enth�lt alle Lock Objekte, die gekauft werden k�nnen.
     */
    private static Lock[] allLocks = new Lock[4];

    /**
     * Enth�lt die ImageView Objekte aller Locks.
     */
    private static ImageView[] allLocks_img = new ImageView[4];

    /**
     * Lock Objekt, welches das kaufen eines bestimmten Felds erm�glicht.
     */
    private static Lock lock1;
    /**
     * Lock Objekt, welches das kaufen eines bestimmten Felds erm�glicht.
     */
    private static Lock lock2;
    /**
     * Lock Objekt, welches das kaufen eines bestimmten Felds erm�glicht.
     */
    private static Lock lock3;
    /**
     * Lock Objekt, welches das kaufen eines bestimmten Felds erm�glicht.
     */
    private static Lock lock4;

    /**
     * Stellt die aus quadratischen Kacheln bestehende Map dar.
     */
    private GridPane map = new GridPane();

    /**
     * Dient zum Import der Bilddateien in die Image Objekte durch einen relativen Pfad.
     */
    public void importTiles() {
        field_meadow_import = new Image("file:src/Images/fields/field_meadow.png");
        grass_dark_import = new Image("file:src/Images/way/grass_dark.png");
        foundation_field_housing_import = new Image("file:src/Images/way/foundation_field_housing.png");
        way_left_import = new Image("file:src/Images/way/way_left.png");
        way_left_top_import = new Image("file:src/Images/way/way_left_top.png");
        way_right_import = new Image("file:src/Images/way/way_right.png");
        way_right_top_import = new Image("file:src/Images/way/way_right_top.png");
        way_straight_import = new Image("file:src/Images/way/way_straight.png");
        way_straight_horizontal_import = new Image("file:src/Images/way/way_straight_horizontal.png");
        way_import = new Image("file:src/Images/way/way.png");
        border_water_corner_left = new Image("file:src/Images/border_water_beach/border_water_corner_left2.png");
        border_water_corner_right = new Image("file:src/Images/border_water_beach/border_water_corner_right.png");
        border_water_cliff_mid_1 = new Image("file:src/Images/border_water_beach/border_water_cliff_mid_1.png");
        border_water_cliff_mid_2 = new Image("file:src/Images/border_water_beach/border_water_cliff_mid_2.png");
        border_leftside1 = new Image("file:src/Images/border_water_beach/border_leftside1.png");
        border_leftside2 = new Image("file:src/Images/border_water_beach/border_leftside2.png");
        border_rightside1 = new Image("file:src/Images/border_water_beach/border_rightside1.png");
        border_rightside2 = new Image("file:src/Images/border_water_beach/border_rightside2.png");
        sand_grass_crossing = new Image("file:src/Images/way/sand_grass_crossing.png");
        sand_grass_crossing_leftside = new Image("file:src/Images/way/sand_grass_crossing_leftside.png");
        sand_grass_crossing_rightside = new Image("file:src/Images/way/sand_grass_crossing_rightside.png");

        setField_raw_import(new Image("file:src/Images/fields/field_raw.png"));
        setField_grubbed_import(new Image("file:src/Images/fields/field_grubbed.png"));
        field_seaded_import = new Image("file:src/Images/fields/field_sown.png");
        field_phase1_import = new Image("file:src/Images/fields/field_phase1.png");
        field_phase2_import = new Image("file:src/Images/fields/field_phase2.png");
        setField_ripe_import(new Image("file:src/Images/fields/field_ripe.png"));
        field_raw_dark_import = new Image("file:src/Images/fields/field_raw_dark.png");
    }

    /**
     * Berechnet aus der Feldnummer die Y-Koordinate.
     * @param TileNr Feldnummer, auf dem  sich das zu untersuchende Objekt befindet.
     * @return Y-Koordinate im GridPane.
     */
    public int getGridPaneY (int TileNr) {
        return TileNr/30;
    }

    /**
     * Berechnet aus der Feldnummer die X-Koordinate.
     * @param TileNr Feldnummer, auf dem sich das zu untersuchende Objekt befindet.
     * @return X-Koordinate im GridPane.
     */
    public int getGridPaneX (int TileNr) {
        return (TileNr-(TileNr/30)*30);
    }

    /**
     * Gibt das Image-Objekt an einer �bergebenen Stelle im mapContent-Array zur�ck.
     * Dabei ist die Stelle im mapContent-Array gleich der TileNr.
     * @param index Stelle im mapContent-Array.
     * @return Image-Objekt an der angegebenen Stelle.
     */
    public Image getMapContent(int index) {
        return mapContent[index];
    }

    /**
     * Wandelt das mapContent-Array, welches die Inhalte der gesamten Karte enth�lt, in ein Array aus String-Objekten zur Speicherung als Json um.
     * @return Der in ein String Array �bersetzte Inhalt der Karte
     */
    public String[] mapContentToString() {
        String[] mapContentString = new String[getMapContent().length];
        Image image;

        for (int i = 0; i<getMapContent().length; i++) {
            image = getMapContent()[i];
            if(image == field_meadow_import) {
                mapContentString[i] = "field_meadow_import";
            } else 	if(image == grass_dark_import) {
                mapContentString[i] = "grass_dark_import";
            } else 	if(image == foundation_field_housing_import) {
                mapContentString[i] = "foundation_field_housing_import";
            } else 	if(image == way_left_import) {
                mapContentString[i] = "way_left_import";
            } else 	if(image == way_left_top_import) {
                mapContentString[i] = "way_left_top_import";
            } else 	if(image == way_right_import) {
                mapContentString[i] = "way_right_import";
            } else 	if(image == way_right_top_import) {
                mapContentString[i] = "way_right_top_import";
            } else 	if(image == way_straight_import) {
                mapContentString[i] = "way_straight_import";
            } else 	if(image == way_straight_horizontal_import) {
                mapContentString[i] = "way_straight_horizontal_import";
            } else 	if(image == way_import) {
                mapContentString[i] = "way_import";
            } else 	if(image == border_water_corner_left) {
                mapContentString[i] = "border_water_corner_left";
            } else 	if(image == border_water_corner_right) {
                mapContentString[i] = "border_water_corner_right";
            } else 	if(image == border_water_cliff_mid_1) {
                mapContentString[i] = "border_water_cliff_mid_1";
            } else 	if(image == border_water_cliff_mid_2) {
                mapContentString[i] = "border_water_cliff_mid_2";
            } else 	if(image == border_leftside1) {
                mapContentString[i] = "border_leftside1";
            } else 	if(image == border_leftside2) {
                mapContentString[i] = "border_leftside2";
            } else 	if(image == border_rightside1) {
                mapContentString[i] = "border_rightside1";
            } else 	if(image == border_rightside2) {
                mapContentString[i] = "border_rightside2";
            } else 	if(image == sand_grass_crossing) {
                mapContentString[i] = "sand_grass_crossing";
            } else 	if(image == sand_grass_crossing_leftside) {
                mapContentString[i] = "sand_grass_crossing_leftside";
            } else 	if(image == sand_grass_crossing_rightside) {
                mapContentString[i] = "sand_grass_crossing_rightside";
            } else 	if(image == getField_raw_import()) {
                mapContentString[i] = "field_raw_import";
            } else 	if(image == getField_grubbed_import()) {
                mapContentString[i] = "field_grubbed_import";
            } else 	if(image == field_seaded_import) {
                mapContentString[i] = "field_seaded_import";
            } else 	if(image == field_phase1_import) {
                mapContentString[i] = "field_phase1_import";
            } else 	if(image == field_phase2_import) {
                mapContentString[i] = "field_phase2_import";
            } else 	if(image == getField_ripe_import()) {
                mapContentString[i] = "field_ripe_import";
            } else 	if(image == field_raw_dark_import) {
                mapContentString[i] = "field_raw_dark_import";
            } else {
                mapContentString[i] = "no such field";
            }
        }
        return mapContentString;

    }

    /**
     * Wandelt das aus der Json importierte String Array, welches die Namen der Image-Objekte in mapContent enth�lt, um.
     * @param mapContentString Das aus der Json importierte String-Array.
     * @return Das neu bef�llte mapContent-Array, welches dann zum Rendern der Karte genutzt werden kann.
     */
    public Image[] stringToMapContent(String[] mapContentString) {
        Image[] mapContentInput = new Image[mapContentString.length];
        for(int i = 0; i<mapContentString.length; i++) {
            String s = mapContentString[i];
            switch(s) {
                case "field_meadow_import": mapContentInput[i] = field_meadow_import;break;
                case "grass_dark_import": mapContentInput[i] = grass_dark_import; break;
                case "foundation_field_housing_import": mapContentInput[i] = foundation_field_housing_import; break;
                case "way_left_import": mapContentInput[i] = way_left_import; break;
                case "way_left_top_import": mapContentInput[i] = way_left_top_import; break;
                case "way_right_import": mapContentInput[i] = way_right_import; break;
                case "way_right_top_import": mapContentInput[i] = way_right_top_import; break;
                case "way_straight_import": mapContentInput[i] = way_straight_import; break;
                case "way_straight_horizontal_import": mapContentInput[i] = way_straight_horizontal_import; break;
                case "way_import": mapContentInput[i] = way_import; break;
                case "border_water_corner_left": mapContentInput[i] = border_water_corner_left; break;
                case "border_water_corner_right": mapContentInput[i] = border_water_corner_right; break;
                case "border_water_cliff_mid_1": mapContentInput[i] = border_water_cliff_mid_1; break;
                case "border_water_cliff_mid_2": mapContentInput[i] = border_water_cliff_mid_2; break;
                case "border_leftside1": mapContentInput[i] = border_leftside1; break;
                case "border_leftside2": mapContentInput[i] = border_leftside2; break;
                case "border_rightside1": mapContentInput[i] = border_rightside1; break;
                case "border_rightside2": mapContentInput[i] = border_rightside2; break;
                case "sand_grass_crossing": mapContentInput[i] = sand_grass_crossing; break;
                case "sand_grass_crossing_leftside": mapContentInput[i] = sand_grass_crossing_leftside; break;
                case "sand_grass_crossing_rightside": mapContentInput[i] = sand_grass_crossing_rightside; break;
                case "field_raw_import": mapContentInput[i] = getField_raw_import(); break;
                case "field_grubbed_import": mapContentInput[i] = getField_grubbed_import(); break;
                case "field_seaded_import": mapContentInput[i] = field_seaded_import; break;
                case "field_phase1_import": mapContentInput[i] = field_phase1_import; break;
                case "field_phase2_import": mapContentInput[i] = field_phase2_import; break;
                case "field_ripe_import": mapContentInput[i] = getField_ripe_import(); break;
                case "field_raw_dark_import": mapContentInput[i] = field_raw_dark_import; break;
                default: mapContentInput[i] = field_meadow_import; break;
            }
        }
        return mapContentInput;
    }


    /**
     * Dient zur Festlegung des standardm��igen Spielfelds mit unbearbeiteten Feldern.
     * @return Array, welches jeder Feldnummer ein Image Objekt zuordnet
     */
    public Image[] fillArray() {
        setMapContent(stringToMapContent(FarmingHoleApplication.mapContentInput));
        return getMapContent();
    }

    /**
     * Erm�glicht es basierend auf der TileNr den Name des Image-Objekts an dieser Stelle herauszufinden.
     * @param TileNr Feldnummer, auf dem  sich das betrachtete Objekt befindet.
     * @return ID des Objekts als String.
     */
    public static Image getTileName(int TileNr) {
        return getMapContent()[TileNr];
    }

    /**
     * F�llt das Array, welches nicht begehbare Felder festlegt, mit Werten.
     * @return Das bef�llte Array
     */
    public Image[] setNotWalkableTiles() {
        notWalkableTiles[0] = foundation_field_housing_import;
        notWalkableTiles[1] = border_water_cliff_mid_1;
        notWalkableTiles[2] = border_water_cliff_mid_2;
        notWalkableTiles[3] = border_water_corner_left;
        notWalkableTiles[4] = border_water_corner_right;
        notWalkableTiles[5] = border_rightside1;
        notWalkableTiles[6] = border_rightside2;
        notWalkableTiles[7] = border_leftside1;
        notWalkableTiles[8] = border_leftside2;
        notWalkableTiles[9] = grass_dark_import;
        return notWalkableTiles;
    }

    /**
     * F�llt das Array, welches nicht begehbare Felder festlegt mit Werten.
     * @return das bef�llte Array
     */
    public Image[] setFields() {
        fields[0] = getField_raw_import();
        fields[1] = getField_grubbed_import();
        fields[2] = field_seaded_import;
        fields[3] = field_phase1_import;
        fields[4] = field_phase2_import;
        fields[5] = getField_ripe_import();
        return fields;
    }


    /**
     * Rendert die Map durch Erzeugung eines GridPane Objekts. Dazu wird das zuvor bef�llte Array mithilfe einer for-schleife
     * durchlaufen und die Image-Objekte anonym als ImageView-Objekte �bergeben.
     * @return bef�lltes GridPane-Objekt
     */
    public GridPane buildMap() {

        map.setPadding(new Insets(0,0,0,0));
        importTiles();
        setNotWalkableTiles();
        setFields();
        fillArray();

        int TileNr = 0;
        for (int i=0; i<20; i++) {
            for (int j=0; j<30; j++) {
                map.add(new ImageView(getMapContent()[TileNr]), j, i);
                TileNr++;
            }
        }
        return map;
    }

    /**
     * Erm�glicht das Austauschen eines speziellen Felds.
     * @param TileNr Nummer des auszutauschenden Felds
     * @param newTile Name des neuen Felds
     */
    public void changeTile(int TileNr, Image newTile) {
        FarmingHoleApplication.map.add(new ImageView(newTile), getGridPaneX(TileNr), getGridPaneY(TileNr));
        getMapContent()[TileNr] = newTile;
    }

    /**
     * �berpr�ft auf �bereinstimmung eines jeweiligen Feldes mit der dazugeh�rigen Feldnummer
     * @param TileNr die Nummer des jeweiligen Feldes
     * @return true, wenn �bereinstimmung, andernfalls false
     */
    public boolean checkIfOnFieldTile(int TileNr) {
        for (Image i: fields) {
            if (i.equals(getTileName(TileNr))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Aktualisierung der  jeweiligen Feldnummer (Koordinaten)
     */
    public void updateTiles() {
        if (MovingEntity.currentlyActive != null && MovingEntity.currentlyActive.getClass().getSuperclass() == Vehicle.class) {
            ((Vehicle) MovingEntity.currentlyActive).workOnFields(((Vehicle) MovingEntity.currentlyActive), MovingEntity.getTileNr(FarmingHoleApplication.player.getCurrentlyUsedVehicle().getKoordX()-30,FarmingHoleApplication.player.getCurrentlyUsedVehicle().getKoordY()-25)+1);
        }
    }


    /**
     * Methode f�r das Wachstum der Felder. Hierbei wird jede 30 Sekunden das Image des Feldes gewechselt.
     * @param TileNr Angabe, auf welches Feld sich die Methode beziehen soll.
     */
    public void fieldGrowing(int TileNr) {

        changeTile(TileNr, field_seaded_import);

        Timeline t =  new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().addAll(

                new KeyFrame(Duration.seconds(30.0),
                        (ActionEvent event) -> {changeTile(TileNr, field_phase1_import);
                        }),
                new KeyFrame(Duration.seconds(60.0),
                        (ActionEvent event) -> {changeTile(TileNr, field_phase2_import);
                        }),
                new KeyFrame(Duration.seconds(90.0),
                        (ActionEvent event) -> {changeTile(TileNr, getField_ripe_import());
                        })
        );

        t.play();
    }


    /**
     * Legt das Wachsen der unterschiedlichen Wachstumsstufen der Felder nach dem Laden eines Spielstandes fest.
     */
    public void fieldGrowingAfterLoading() {

        for(int i = 0; i<getMapContent().length; i++) {
            if(getMapContent()[i] == field_seaded_import) {
                fieldGrowingAfterLoadingTimeline(i, 0.0);
            } else if(getMapContent()[i] == field_phase1_import) {
                fieldGrowingAfterLoadingTimeline(i, 30.0);
            } else if(getMapContent()[i] == field_phase2_import) {
                fieldGrowingAfterLoadingTimeline(i, 60.0);
            }
        }
    }

    /**
     *  Legt fest ab welchem Punkt das Feldwachstum weitergehen soll, nachdem der Speicherstand geladen wurde.
     * @param TileNr Koordinate des  wachsenden Feldes, dessen Bild sich ver�ndert
     * @param offset Zeitpunkt, an dem das Feldwachstum nach dem Laden gestartet werden soll
     */
    public void fieldGrowingAfterLoadingTimeline(int TileNr, double offset) {
        Timeline t =  new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().addAll(

                new KeyFrame(Duration.seconds(30.0),
                        (ActionEvent event) -> {changeTile(TileNr, field_phase1_import);
                        }),
                new KeyFrame(Duration.seconds(60.0),
                        (ActionEvent event) -> {changeTile(TileNr, field_phase2_import);
                        }),
                new KeyFrame(Duration.seconds(90.0),
                        (ActionEvent event) -> {changeTile(TileNr, getField_ripe_import());
                        })
        );

        t.play();
        t.jumpTo(Duration.seconds(offset));
    }

    /**
     * Gibt eine Liste aller Lock Objekte, die auf der Map angezeigt werden zur�ck.
     * @return Liste, die alle Lock Objekte enth�lt.
     */
    public static Lock[] getAllLocks() {
        return allLocks;
    }

    /**
     * Definiert eine Liste, welche die noch sichtbaren Schl�sser in einem Array festlegt
     * @return Array der Schl�sser
     */
    public String[] locksToString() {
        String[] allLocksString = new String[allLocks.length];
        Lock lock;

        for (int i = 0; i<allLocks.length; i++) {
            lock = allLocks[i];
            if(lock == lock1) {
                allLocksString[i] = "lock1";
            } else if(lock == lock2) {
                allLocksString[i] = "lock2";
            } else if(lock == lock3) {
                allLocksString[i] = "lock3";
            } else if(lock == lock4) {
                allLocksString[i] = "lock4";
            }
        }

        return allLocksString;
    }

    /**
     * Wandelt das aus der Json importierte String-Array, welches die Namen der Lock Objekte enth�lt um.
     * @param importedLocksList Das aus der Json importierte String Array.
     * @return Das neu bef�llte allLocks Array, welches dann zum setzen der Locks verwendet werden kann.
     */
    public Lock[] stringToLocks(String[] importedLocksList) {
        Lock[] allLocksInput = new Lock[importedLocksList.length];
        for(int i = 0; i<importedLocksList.length; i++) {
            if(importedLocksList[i] != null) {
                String s = importedLocksList[i];
                switch(s) {
                    case "lock1": allLocksInput[i] = lock1;break;
                    case "lock2": allLocksInput[i] = lock2;break;
                    case "lock3": allLocksInput[i] = lock3;break;
                    case "lock4": allLocksInput[i] = lock4;break;
                    default: allLocksInput[i] = null;
                }
            }
        }
        return allLocksInput;
    }



    /**
     * Legt die Zuordnung der Felder eines jeweiligen Schlosses fest.
     * @return alle Schl�sser mit den beinhalteten Feldern
     */
    public AnchorPane addLocks() {
        AnchorPane locks = new AnchorPane();

        int[] refferedFieldNrsLock1 = {32,33,34,35,36,62,63,64,65,66,92,93,94,95,96,122,123,124,125,126,152,153,154,155,156,182,183,184,185,186};
        int[] refferedFieldNrsLock2 = {243,244,245,246,247,273,274,275,276,277,303,304,305,306,307,308,309,310,311,333,334,335,336,337,338,339,340,341,363,364,365,366,367,368,369,370,371};
        int[] refferedFieldNrsLock3 = {422,423,424,425,426,427,428,429,430,431,452,453,454,455,456,457,458,459,460,461,482,483,484,485,486,487,488,489,490,491,512,513,514,515,516,517,518,519,520,521};
        int[] refferedFieldNrsLock4 = {343,344,345,346,347,348,349,350,351,373,374,375,376,377,378,379,380,381,403,404,405,406,407,408,409,410,411,433,434,435,436,437,438,439,440,441,463,464,465,466,467,468,469,470,471,493,494,495,496,497,498,499,500,501};

        lock1 = new Lock(125.0,115.0,25, refferedFieldNrsLock1); //zun�chst wird nur ein Basispreis festgelegt, der dann je nach Schwierigkeitsstufe mit einem Faktor multipliziert wird (sobald die Schwierigkeitsstufe gesetzt wurde)
        lock2 = new Lock(160.0,330.0,30, refferedFieldNrsLock2);
        lock3 = new Lock(200.0,500.0,35, refferedFieldNrsLock3);
        lock4 = new Lock(540.0,430.0,45, refferedFieldNrsLock4);

        allLocks = stringToLocks(FarmingHoleApplication.allLocksInput);

        for (int i = 0; i<allLocks.length; i++) {
            if(allLocks[i] != null) {
                allLocks_img[i] = allLocks[i].addLock();
                locks.getChildren().add(allLocks_img[i]);
            }
        }
        return locks;
    }

    /**
     * Methode, mit der der Preis f�r die Felder in Abh�ngigkeit des Preises pro Feld gesetzt wird.
     * Die Methode wird in FarmingHoleApplication aufgerufen, nachdem der Benutzer eine Schwierigkeitsstufe ausgew�hlt hat.
     * @param price Preis pro Feld (Variable fieldPrice aus FarmingHoleApplication)
     */
    public static void setLockPrices(int price) {
        for (Lock lock: allLocks) {
            if(lock != null) {
                lock.setPrice(lock.getPrice()*price);
            }

        }
    }

    /**
     * Legt die Sichtbarkeit des Schlosses fest.
     * @param lock das Schloss, dessen Sichbarkeit ge�ndert werden soll
     */
    public void hideLock(Lock lock) {
        lock.isVisible = false;
        int index = 0;
        for (Lock l: allLocks) {
            if(l != null && l.equals(lock)) {
                allLocks_img[index].setVisible(false);
                allLocks[index] = null;
            }
            index++;
        }
    }

    /**
     * Aktualisierung der Schl�sser.
     */
    public void updateLocks() {
        for (int i= 0; i<allLocks_img.length; i++) {
            if(allLocks_img[i] != null) {
                FarmingHoleApplication.locks.getChildren().set(i, allLocks_img[i]);
            }

        }
    }

    /**
     * �nderung des freigeschalteten Feldes (mit allen dazugeh�rigen Feldnummern) von unbearbeitbaren zu beackerbaren Feldern.
     * @param lock das Schloss, welches angesprochen wird und welches die verschiedenen zu �ndernden Feldnummern beinhaltet
     */
    public void makeFieldmyOwn(Lock lock) {
        int[] referredFieldNrs = lock.getReferredFieldNrs();

        for(int i: referredFieldNrs) {
            changeTile(i, getField_raw_import());
        }
    }

    /**
     * Getter f�r das unbearbeitete Feld. Wird ben�tigt, da dieser Feldtyp von einem Objekt der Klasse {@link game.movingEntitiesPackage.Vehicle} ge�ndert werden kann.
     * @return Image eines unbearbeiteten Felds
     */
    public static Image getField_raw_import() {
        return field_raw_import;
    }

    /**
     * Setter f�r das unbearbeitete Feld. Wird ben�tigt, da dieser Feldtyp von einem Objekt der Klasse {@link game.movingEntitiesPackage.Vehicle} ge�ndert werden kann.
     * @param field_raw_import Image eines unbearbeiteten Felds
     */
    public static void setField_raw_import(Image field_raw_import) {
        Tiles.field_raw_import = field_raw_import;
    }

    /**
     * Getter f�r das gegrubberte Feld. Wird ben�tigt, da dieser Feldtyp von einem Objekt der Klasse {@link game.movingEntitiesPackage.Vehicle} ge�ndert werden kann.
     * @return Image eines gegrubberten Felds
     */
    public static Image getField_grubbed_import() {
        return field_grubbed_import;
    }

    /**
     * Setter f�r das gegrubberte Feld. Wird ben�tigt, da dieser Feldtyp von einem Objekt der Klasse {@link game.movingEntitiesPackage.Vehicle} ge�ndert werden kann.
     * @param field_grubbed_import Image eines gegrubberten Felds
     */
    public static void setField_grubbed_import(Image field_grubbed_import) {
        Tiles.field_grubbed_import = field_grubbed_import;
    }


    /**
     * Getter f�r das reife Feld. Wird ben�tigt, da dieser Feldtyp von einem Objekt der Klasse {@link game.movingEntitiesPackage.Vehicle} ge�ndert werden kann.
     * @return Image eines reifen Felds
     */
    public static Image getField_ripe_import() {
        return field_ripe_import;
    }

    /**
     * Setter f�r das reife Feld. Wird ben�tigt, da dieser Feldtyp von einem Objekt der Klasse {@link game.movingEntitiesPackage.Vehicle} ge�ndert werden kann.
     * @param field_ripe_import Image eines reifen Felds
     */
    public static void setField_ripe_import(Image field_ripe_import) {
        Tiles.field_ripe_import = field_ripe_import;
    }


    /**
     * Getter, der als Array den aktuellen Stand der Map zur�ckgibt.
     * @return Array welches die Map repr�sentiert.
     */
    public static Image[] getMapContent() {
        return mapContent;
    }

    /**
     * Setter, der den aktuellen Stand der Map als Array setzt.
     * @param mapContent Array, welches die Map repr�sentiert.
     */
    public static void setMapContent(Image[] mapContent) {
        Tiles.mapContent = mapContent;
    }
}
