package game;

import game.movingEntitiesPackage.Attachable;
import game.movingEntitiesPackage.Cultivator;
import game.movingEntitiesPackage.Harvester;
import game.movingEntitiesPackage.MovingEntity;
import game.movingEntitiesPackage.Player;
import game.movingEntitiesPackage.Seeder;
import game.movingEntitiesPackage.Tractor;
import game.movingEntitiesPackage.Trailer;
import game.movingEntitiesPackage.Vehicle;
import game.notMovingEntitiesPackage.Bridge;
import game.notMovingEntitiesPackage.Buildings;
import game.notMovingEntitiesPackage.GrainTank;
import game.notMovingEntitiesPackage.Tiles;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Erstellung der Hauptklasse unserer Anwendung. Diese erbt von der Anwendungsklasse, von der JavaFX Anwendungen ausgehen: Application.
 * Diese ist der Einstiegspunkt der JavaFX-Anwendung. Die JavaFX-Laufzeit ruft beim Start unserer Anwendung die abstrakte Startmethode auf.
 * Au�erdem beinhaltet diese Klasse die Erstellung des JavaFX Scene Objekts und das JavaFX Stage Objekt.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class FarmingHoleApplication extends Application {

    /** H�he des Fensters der Applikation. */
    public final static int windowHeight = 665; // um 25 erh�ht wegen Menu Bar
    /** Breite des Fensters der Applikation. */
    public final static int windowWidth = 1216;

    /** Festlegung des Geldes des Spielers */
    public static int myMoney;

    /** Festlegung des Spritpreises */
    public static final int FUEL_PRICE = 10;

    /** Festlegung des Getreidepreises */
    private static int grainPrice;

    /** Festlegung des Ertrags pro Fl�cheninhalt */
    private static int harvest_per_field;

    /** Festlegung des Preises pro Feld */
    private static int fieldPrice;

    /**
     *  Erstellung des JavaFX Scene Objekts
     */
    public Scene scene;

    /** Spielerfigur im Spiel */
    @SuppressWarnings("exports")
    public static Player player;
    /** Traktor im Spiel */
    private static Tractor tractor;
    /** M�hdrescher im Spiel */
    private static Harvester harvester;
    /** Kipper im Spiel */
    private static Trailer trailer;
    /** S�hmaschine im Spiel */
    private static Seeder seeder;
    /** Grubber im Spiel */
    private static Cultivator cultivator;
    /** Silo im Spiel (Getreide-Tank Objekt) */
    @SuppressWarnings("exports")
    public static GrainTank silo;
    /** Br�cke im Spiel */
    @SuppressWarnings("exports")
    public static Bridge bridge;

    /**
     * Gruppe f�r die Bilder der Spieleranimation
     */
    public static Group playerImg;
    /**
     * Gruppe f�r die Bilder der Traktoranimation
     */
    private static Group tractorAnimation;
    /**
     * Gruppe f�r die Bilder der M�hdrescheranimation
     */
    private static Group harvesterImg;
    /**
     * Gruppe f�r die Bilder der Anh�ngeranimation
     */
    private static Group trailerImg;
    /**
     * Gruppe f�r die Bilder der S�hmaschinenanimation
     */
    private static Group seederImg;
    /**
     * Gruppe f�r die Bilder der Grubberanimation
     */
    private static Group cultivatorImg;
    /**
     * Neues ImageView-Objekt f�r die Br�cke
     */
    @SuppressWarnings("unused")
    private static ImageView bridgeImg;

    /**
     * Ein Vehicle-Array welches alle benutzbaren Fahrzeuge beinhaltet. Die L�nge des Arrays wird bereits bei Initialisierung festgelegt.
     */
    @SuppressWarnings("exports")
    public static ArrayList<Vehicle> allVehicles = new ArrayList<>();

    /**
     * Ein Attachable-Array welches alle benutzbaren Ger�te beinhaltet. Die L�nge des Arrays wird bereits bei Initialisierung festgelegt.
     */
    @SuppressWarnings("exports")
    public static ArrayList<Attachable> allAttachables = new ArrayList<>();

    /**
     * neues GridPane f�r die Karte des Spiel
     */
    public static GridPane map;
    /**
     * Image Objekt, das optische Versch�nerungen der GUI enth�lt.
     */
    public static Image frames;
    /**
     * AnchorPane auf dem die Locks platziert werden.
     */
    public static AnchorPane locks;
    /**
     * Enth�lt den aus dem Json File eingelesenen mapContent als String Array. Bildet die Basis f�r den Aufbau der Map.
     */
    public static String[] mapContentInput = new String[600];
    /**
     * Enth�lt alle aus dem Json File eingelesenen Lock Objekte als String Array.
     */
    public static String[] allLocksInput = new String[4];

    /**
     * {@link Tiles} Objekt, welches zum Aufruf einiger nicht statischer Methoden aus Tiles erstellt wird.
     */
    @SuppressWarnings("exports")
    public Tiles tiles;
    /**
     * Variable, die zeigt ob das Spiel in den letzten 15 Sekunden gespeichert wurde.
     */
    private boolean recentlySaved;

    /**
     * Timer, der alle w�hrend des Spiels st�ndig zu aktualisierenden Methoden enth�lt.
     */
    public static AnimationTimer timer;

    /**
     * Erstellung der main-Methode, die die statische Methode launch() der Klasse {@link FarmingHoleApplication} enth�lt. main() hat dabei einen Parameter, der ein Array von Stringreferenzen ist.
     * Diese Methode startet die JavaFX-Laufzeit und die JavaFX-Anwendung. Sie erkennt hierbei, von welcher Klasse sie aufgerufen wird, sodass man ihr nicht explizit sagen muss, welche Klasse gestartet werden soll
     * @param args die command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

    /** Ist die Basis f�r das gesamte GUI des Spiels. */
    BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) {

        //care about game Music
        Music.playSong1();

        // Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createGameMenu(stage), Music.createMusicMenu());
        root.setTop(menuBar);

        stage.setTitle("FarmingHole");
        stage.getIcons().add(new Image("file:src/Images/startscene/icon.png"));
        stage.setScene(scene);
        stage.setScene(createStartScene(stage));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Startet das Spiel in dem Daten eingelesen und Objekte erstellt werden.
     * @param file Der einzulesende Json File.
     * @param stage Die Stage, die f�r das gesamte Spiel verwendet wird.
     */
    public void startGame(File file, Stage stage) {

        loadMapFromJson(file);

        //create objects for GUI
        tiles = new Tiles();
        map = tiles.buildMap();

        frames = new Image("file:src/Images/fields/Border_field_new.png");
        Buildings buildings = new Buildings();
        AnchorPane anchor = buildings.addBuildings();
        locks = tiles.addLocks();

        loadObjectsFromJson(file);

        Tiles.setLockPrices(fieldPrice);

        playerImg = player.initialize();
        tractorAnimation = tractor.initialize();
        harvesterImg = harvester.initialize();
        seederImg = seeder.initialize();
        trailerImg = trailer.initialize();
        cultivatorImg = cultivator.initialize();



        //build up GUI
        StackPane stacked = new StackPane();
        BorderPane movingEntities = new BorderPane();
        movingEntities.getChildren().add(playerImg);
        movingEntities.getChildren().add(tractorAnimation);
        movingEntities.getChildren().add(harvesterImg);
        movingEntities.getChildren().add(trailerImg);
        movingEntities.getChildren().add(seederImg);
        movingEntities.getChildren().add(cultivatorImg);
        stacked.getChildren().addAll(map, new ImageView(frames), anchor, locks, movingEntities);
        root.setCenter(stacked);
        root.setRight(createSideBar());

        tiles.fieldGrowingAfterLoading();


        scene = new Scene(root, windowWidth, windowHeight);
        scene.setFill(Color.web("#81c483"));

        stage.setOnCloseRequest(evt -> {
            evt.consume();
            showAlertIfSaved();
        });



        timer = createAnimationTimer();
        timer.start();

    }

    /**
     * Erstellung des interaktiven Menus unserer Anwendung.
     * @param stage der Methode wird das oben definierte stage Objekt �bergeben.
     * @return gameMenu gibt das Menu mit seinen Unterpunkten und deren Funktion zur�ck
     */
    @SuppressWarnings("exports")
    public Menu createGameMenu(Stage stage) {
        // Create Menu "Game"
        Menu gameMenu = new Menu("Game");

        // Add items to "Game"
        MenuItem save = new MenuItem("Save");
        gameMenu.getItems().add(save);
        gameMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Exit");
        gameMenu.getItems().add(exit);


        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();

                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json"));
                fileChooser.setInitialDirectory(new File("src/SavedGames"));

                File file = fileChooser.showSaveDialog(stage);
                try {
                    writeJson(file.getPath());
                } catch (Exception e) {
                    GameChat.setNewMessage("Game was not saved successfully");
                }

            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                showAlertIfSaved();
            }
        });
        return gameMenu;
    }

    /**
     * Erstellung einer Warnung beim Schlie�en des Spiels. Der Spieler soll hier die M�glichkeit haben, sein Spiel vor dem Schlie�en nochmal zu speichern oder ohne zu speichern zu verlassen.
     */
    public void showAlertIfSaved() {
        if(!recentlySaved) {
            ButtonType yesAndClose = new ButtonType("Yes, close the Application", ButtonData.YES);
            ButtonType noGoBack = new ButtonType("No, go back to Application", ButtonData.NO);
            Alert alert = new Alert(AlertType.WARNING, "Did you save your game?", yesAndClose, noGoBack);
            alert.setTitle("Warning");
            alert.setHeaderText("Your game may not have been stored recently");
            Stage alertDialog = (Stage) alert.getDialogPane().getScene().getWindow();
            alertDialog.getIcons().add(new Image("file:../../../Images/startscene/icon.png"));
            alert.showAndWait();

            if (alert.getResult() == yesAndClose) {
                System.exit(0);
            } else if (alert.getResult() == noGoBack) {
                alert.close();
            }
        } else {
            System.exit(0);
        }

    }



    /**
     * Animation Timer
     * @return timer
     */
    public AnimationTimer createAnimationTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                tractor.tractorKeyActions(scene);
                harvester.harvesterKeyActions(scene);
                player.playerKeyActions(scene);
                player.updateEntity();
                tractor.updateEntity();
                harvester.updateEntity();
                seeder.updateKoords();
                trailer.updateKoords();
                cultivator.updateKoords();
                bridge.updateBridge();

                SideBar.updateSideBar();

                tiles.updateTiles();
            }
        };

        return timer;
    }


    /**
     * Erstellung der {@link SideBar} rechts am Rand des Fensters.
     * @return sidebar
     */
    public BorderPane createSideBar() {
        BorderPane sidebar = SideBar.buildSideBar();
        return sidebar;
    }


    /**
     * Schreiben eines Json Files um den Spielstand zu speichern.
     * @param filename Name der Datei.
     * @throws IOException wird geworfen, wenn die Datei nicht erfolgreich gespeichert werden kann.
     */
    @SuppressWarnings("unchecked")
    public void writeJson(String filename) throws IOException {
        Tiles t = new Tiles();

        JSONObject obj = new JSONObject();

        obj.put("playerX", player.getKoordX());
        obj.put("playerY", player.getKoordY());
        obj.put("playerInVehicle", player.isInVehicle());

        obj.put("trailerX", trailer.getKoordX());
        obj.put("trailerY", trailer.getKoordY());
        obj.put("trailerName", trailer.getName());
        obj.put("trailerGrain", trailer.getGrainTank().getCapacity());
        obj.put("trailerAttached", trailer.isAttached());

        obj.put("seederX", seeder.getKoordX());
        obj.put("seederY", seeder.getKoordY());
        obj.put("seederName", seeder.getName());
        obj.put("seederAttached", seeder.isAttached());

        obj.put("cultivatorX", cultivator.getKoordX());
        obj.put("cultivatorY", cultivator.getKoordY());
        obj.put("cultivatorName", cultivator.getName());
        obj.put("cultivatorAttached", cultivator.isAttached());

        obj.put("tractorX", tractor.getKoordX());
        obj.put("tractorY", tractor.getKoordY());
        obj.put("tractorName", tractor.getName());
        obj.put("tractorUsed", tractor.isUsed());
        obj.put("tractorFuel", tractor.getVehicleFuelTank().getFuel());
        //tractor currentlyAttached muss beim Laden gesetzt werden

        obj.put("harvesterX", harvester.getKoordX());
        obj.put("harvesterY", harvester.getKoordY());
        obj.put("harvesterName", harvester.getName());
        obj.put("harvesterUsed", harvester.isUsed());
        obj.put("harvesterFuel", harvester.getVehicleFuelTank().getFuel());

        obj.put("siloCapacity", silo.getCapacity());
        obj.put("siloMaxCapacity", silo.getMax_capacity());



        obj.put("myMoney", myMoney);
        obj.put("harvest_per_field", harvest_per_field);
        obj.put("grainPrice", grainPrice);
        obj.put("fieldPrice", fieldPrice);

        obj.put("bridgeRepaired", bridge.isRepaired());



        JSONArray mapContent = new JSONArray();
        String[] mapContentToString = t.mapContentToString();
        for (int i=0; i<600; i++) {
            mapContent.add(mapContentToString[i]);
        }
        obj.put("mapContent", mapContent);


        JSONArray locks = new JSONArray();
        String[] locksToString = t.locksToString();
        for (int i=0; i<4; i++) {
            locks.add(locksToString[i]);
        }
        obj.put("locks", locks);

        recentlySaved = true;



        Timeline timeline =  new Timeline();
        timeline.setCycleCount(1);

        timeline.getKeyFrames().addAll(

                new KeyFrame(Duration.seconds(15.0),
                        (ActionEvent event) -> {recentlySaved = false;
                        })
        );

        timeline.play();

        Files.write(Paths.get(filename), obj.toJSONString().getBytes());
        GameChat.setNewMessage("Game was saved successfully");
    }

    /**
     * Erstellung einer Methode, die das Laden der Map selbst von einer JSON- Datei erm�glicht.
     * @param file Json Datei, von der die Daten geladen werden sollen.
     */
    public void loadMapFromJson(File file) {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            bridge = new Bridge((boolean) obj.get("bridgeRepaired"));

            JSONArray mapContent = (JSONArray) obj.get("mapContent");
            if(mapContent != null) {
                int len = mapContent.size();
                for (int i=0;i<len;i++){
                    mapContentInput[i] = mapContent.get(i).toString();
                }
            }

            JSONArray locks = (JSONArray) obj.get("locks");
            if(locks != null) {
                int len = locks.size();
                for (int i=0;i<len;i++){
                    allLocksInput[i] = locks.get(i).toString();
                }
            }
        } catch (NullPointerException e) {
            //caught because locks can also have null values if you already bought fields before storing
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellung einer Methode die das Laden des Spielinhalts von einer JSON- Datei erm�glicht.
     * @param file Json Datei, von der die Daten geladen werden sollen.
     */
    public void loadObjectsFromJson(File file) {
        double internalKoordsX = 0;
        double internalKoordsY = 0;
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {

            JSONObject obj = (JSONObject) jsonParser.parse(reader);


            player = new Player((double) obj.get("playerX"), (double) obj.get("playerY"), (boolean) obj.get("playerInVehicle"));
            trailer = new Trailer((double) obj.get("trailerX"),(double) obj.get("trailerY"), (String) obj.get("trailerName"), (int) (double) obj.get("trailerGrain"), (boolean) obj.get("trailerAttached"));
            seeder = new Seeder((double) obj.get("seederX"), (double) obj.get("seederY"), (String) obj.get("seederName"), (boolean) obj.get("seederAttached"));
            cultivator = new Cultivator((double) obj.get("cultivatorX"), (double) obj.get("cultivatorY"), (String) obj.get("cultivatorName"), (boolean) obj.get("cultivatorAttached"));
            tractor = new Tractor((double) obj.get("tractorX"), (double) obj.get("tractorY"), (String) obj.get("tractorName"), (boolean) obj.get("tractorUsed"), (double) obj.get("tractorFuel"), null);
            harvester = new Harvester((double) obj.get("harvesterX"),(double) obj.get("harvesterY"), (String) obj.get("harvesterName"), (boolean) obj.get("harvesterUsed"), (double) obj.get("harvesterFuel"));
            silo = new GrainTank(0,5000);


            if(seeder.isAttached()) {
                tractor.currentlyAttached = seeder;
            } else if (trailer.isAttached()) {
                tractor.currentlyAttached = trailer;
            } else if (cultivator.isAttached()) {
                tractor.currentlyAttached = cultivator;
            }


            if((boolean) obj.get("playerInVehicle")) {

                for (Vehicle v: allVehicles) {
                    if(v.isUsed()) {
                        player.setCurrentlyUsedVehicle(v);
                        MovingEntity.currentlyActive = v;
                        if(v.getClass() == Tractor.class) {
                            internalKoordsX = (double) obj.get("tractorX");
                            internalKoordsY = (double) obj.get("tractorY");
                        } if(v.getClass() == Harvester.class) {
                            internalKoordsX = (double) obj.get("harvesterX");
                            internalKoordsY = (double) obj.get("harvesterY");
                        }
                        v.getAnimation().setAnimationImages();
                        v.setCurrentUser(player);
                    }
                }
            } else {
                internalKoordsX = (double) obj.get("playerX");
                internalKoordsY = (double) obj.get("playerY");
                MovingEntity.currentlyActive = player;
                player.setCurrentlyUsedVehicle(null);
            }


            if(MovingEntity.currentlyActive.checkIfOnNotWalkableTile(MovingEntity.getTileNr(MovingEntity.currentlyActive.getKoordX(), MovingEntity.currentlyActive.getKoordY()))) {
                MovingEntity.currentlyActive.moveEntityTo(internalKoordsX, internalKoordsY-30);
            }

            myMoney = (int) (long) obj.get("myMoney");
            harvest_per_field = (int) (long) obj.get("harvest_per_field");
            grainPrice = (int) (long) obj.get("grainPrice");
            fieldPrice = (int) (long) obj.get("fieldPrice");


        } catch (NullPointerException e) {
            //caught because myMoney and other values depending to difficulty aren't stored in "DefaultJson"
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Idee wenn noch genug Zeit: Alle hier beschriebenen Scenes k�nnten in eigene Klassen ausgelagert werden, um so das MVC-Schema zu erf�llen.

    /**
     * Erstellt eine Scene f�r das Startmen�, dass immer beim Starten der Applikation erscheint.
     * Hier kann nun ausgew�hlt werden, ob ein neues Spiel gestartet werden soll oder ob ein (altes) Spiel geladen werden soll, um weiter zu spielen.
     * @param stage Die Stage, die f�r das gesamte Spiel verwendet wird.
     * @return Die Scene, die das Startmen� beinhaltet.
     */
    public Scene createStartScene(Stage stage) {
        StackPane startPane = new StackPane();
        startPane.getChildren().addAll(new ImageView(new Image("file:src/Images/startscene/background.png")));
        startPane.getChildren().add(new ImageView(new Image("file:src/Images/startscene/logo.png")));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(375,0,0,0));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(30);

        Image newgame = new Image("file:src/Images/startscene/button_newgamepng.png");
        ImageView newgame_button = new ImageView(newgame);
        Image newgame_presssed = new Image("file:src/Images/startscene/button_newgame_pressed.png");
        newgame_button.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                newgame_button.setImage(newgame_presssed);
            }

        });
        newgame_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                newgame_button.setImage(newgame);
                stage.setScene(createDifficultyScene(stage));
            }

        });
        vbox.getChildren().add(newgame_button);

        Image loadgame = new Image("file:src/Images/startscene/button_loadgame.png");
        ImageView loadgame_button = new ImageView(loadgame);
        Image loadgame_pressed = new Image("file:src/Images/startscene/button_loadgame_pressed.png");
        loadgame_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                //....
                loadgame_button.setImage(loadgame_pressed);
            }

        });
        loadgame_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                loadgame_button.setImage(loadgame);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Game");
                fileChooser.setInitialDirectory(new File("src/SavedGames"));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json"));
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    startGame(file, stage);
                    stage.setScene(scene);
                }

            }
        });
        vbox.getChildren().add(loadgame_button);

        startPane.getChildren().add(vbox);
        return new Scene(startPane);
    }


    /**
     * Erstellt eine Scene, auf der ein Men� zur Auswahl des Schwierigkeitsgrades zu sehen ist.
     * Diese Scene wird sichtbar, nachdem im Start-Men� der Button "New Game" gedr�ckt wurde.
     * @param stage Die Stage, die f�r das gesamte Spiel verwendet wird.
     * @return Die Scene, auf der das Men� zur Auswahl des Schwierigkeitsgrades zu sehen ist.
     */
    public Scene createDifficultyScene(Stage stage) {
        StackPane difficultyPane = new StackPane();
        difficultyPane.getChildren().addAll(new ImageView(new Image("file:src/Images/startscene/background.png")));
        difficultyPane.getChildren().add(new ImageView(new Image("file:src/Images/startscene/logo.png")));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(325,0,0,0));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");

        final ToggleGroup group = new ToggleGroup();
        easy.setToggleGroup(group);
        easy.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        easy.setSelected(true);
        medium.setToggleGroup(group);
        medium.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        hard.setToggleGroup(group);
        hard.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
        StackPane difficultyMenu = new StackPane();
        VBox radioButtons = new VBox();
        radioButtons.setPadding(new Insets(100,0,0,565));
        radioButtons.setSpacing(10);
        radioButtons.getChildren().addAll(easy, medium, hard);
        difficultyMenu.getChildren().addAll(new ImageView(new Image("file:src/Images/startscene/difficulty_bg.png")), radioButtons);
        vbox.getChildren().addAll(difficultyMenu);
        Image back = new Image("file:src/Images/startscene/button_back.png");
        ImageView back_button = new ImageView(back);
        Image back_pressed = new Image("file:src/Images/startscene/button_back_pressed.png");
        back_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                back_button.setImage(back_pressed);
            }
        });

        back_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                back_button.setImage(back);
                stage.setScene(createStartScene(stage));
            }
        });

        Image play = new Image("file:src/Images/startscene/button_play.png");
        ImageView play_button = new ImageView(play);
        Image play_pressed = new Image("file:src/Images/startscene/button_play_pressed.png");
        play_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                play_button.setImage(play_pressed);

            }
        });

        play_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                play_button.setImage(play);

                if (easy.isSelected()) {
                    myMoney = 10000;
                    harvest_per_field = 5;
                    grainPrice = 100;
                    fieldPrice = 100;
                } else if (medium.isSelected()) {
                    myMoney = 5000;
                    harvest_per_field = 3;
                    grainPrice = 80;
                    fieldPrice = 250;
                } else if (hard.isSelected()) {
                    myMoney = 0;
                    harvest_per_field = 1;
                    grainPrice = 50;
                    fieldPrice = 400;
                }
                startGame(new File("src/main/resources/game/DefaultJSON.json"), stage);
                stage.setScene(scene);

            }
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(back_button, play_button);
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setSpacing(5);
        vbox.getChildren().add(buttons);
        difficultyPane.getChildren().add(vbox);
        return new Scene(difficultyPane);
    }

    /**
     * Erstellt die Outro Szene, in der der Abspann abgespielt werden kann.
     * @param stage Die Stage, die f�r das gesamte Spiel verwendet wird.
     * @return Gibt das StackPane zur�ck auf dem die Szene erstellt wurde.
     */
    public Scene createOutroScene(Stage stage) {
        StackPane stackpane = new StackPane();
        stackpane.getChildren().addAll(new ImageView(new Image("file:src/Images/startscene/background.png")));
        File f = new File("src/media/FarmingHoleOutro.mp4");
        Media m = new Media(f.toURI().toString());
        MediaPlayer player = new MediaPlayer(m);
        player.play();
        MediaView mediaView = new MediaView(player);
        stackpane.getChildren().add(mediaView);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(550,0,0,0));
        vbox.setAlignment(Pos.TOP_CENTER);

        Image continue_ = new Image("file:src/Images/startscene/button_continue.png");
        ImageView continue_button = new ImageView(continue_);
        Image continue_pressed = new Image("file:src/Images/startscene/button_continue_pressed.png");
        Image continue_invisible = new Image("file:src/Images/startscene/button_continue_invisible.png");
        continue_button.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                continue_button.setImage(continue_pressed);
            }

        });
        continue_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                continue_button.setImage(continue_);
                player.stop();
                Music.muteAll();
                Music.playSong3();
                stage.setScene(createGameEndScene(stage));
                stage.setResizable(false);
            }

        });
        vbox.getChildren().add(continue_button);
        continue_button.setImage(continue_invisible);
        Timeline t =  new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().addAll(

                new KeyFrame(Duration.seconds(130.0),
                        (ActionEvent event) -> {continue_button.setImage(continue_);
                        }));

        t.play();
        stackpane.getChildren().add(vbox);
        return new Scene(stackpane);
    }

    /**
     * Erstellt die Endszene des Spiels in der der Spieler ausw�hlen kann ob er noch weiter spielen will oder das Spiel beenden m�chte.
     * @param stage Die Stage, die f�r das gesamte Spiel verwendet wird.
     * @return Gibt das StackPane zur�ck auf dem die Szene erstellt wurde.
     */
    public Scene createGameEndScene(Stage stage) {
        StackPane stackpane = new StackPane();
        stackpane.getChildren().addAll(new ImageView(new Image("file:src/Images/startscene/background.png")));
        stackpane.getChildren().add(new ImageView(new Image("file:src/Images/startscene/logo.png")));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(205,0,0,0));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(10);
        ImageView book = new ImageView(new Image("file:src/Images/startscene/book.png"));
        Image leave = new Image("file:src/Images/startscene/button_leave.png");
        ImageView leave_button = new ImageView(leave);
        Image leave_pressed = new Image("file:src/Images/startscene/button_leave_pressed.png");
        leave_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                leave_button.setImage(leave_pressed);
            }
        });

        leave_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                leave_button.setImage(leave);
                FileChooser fileChooser = new FileChooser();

                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json"));
                fileChooser.setInitialDirectory(new File("file:src/SavedGames"));

                File file = fileChooser.showSaveDialog(stage);
                try {
                    writeJson(file.getPath());
                } catch (Exception e) {
                    GameChat.setNewMessage("Game was not saved successfully");
                }
                System.exit(0);

            }
        });

        Image continue_ = new Image("file:src/Images/startscene/button_continue_mini.png");
        ImageView continue_button = new ImageView(continue_);
        Image continue_pressed = new Image("file:src/Images/startscene/button_continue_mini_pressed.png");
        continue_button.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                continue_button.setImage(continue_pressed);

            }
        });
        continue_button.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                continue_button.setImage(continue_);
                Music.playSong2();
                stage.close();
            }
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(leave_button, continue_button);
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setSpacing(8);
        vbox.getChildren().addAll(book, buttons);
        stackpane.getChildren().add(vbox);
        return new Scene(stackpane);
    }

    /**
     * Getter f�r die Instanzvariable myMoney.
     * @return myMoney Aktueller Geldstand des Spielers.
     */
    public static int getMyMoney() {
        return myMoney;
    }

    /**
     * Setter f�r die Instanzvariable myMoney.
     * @param myMoney Aktueller Geldstand des Spielers.
     */
    public static void setMyMoney(int myMoney) {
        FarmingHoleApplication.myMoney = myMoney;
    }

    /**
     * Getter f�r die Instanzvariable fieldPrice.
     * @return fieldPrice Preis pro Feldeinheit.
     */
    public static int getFieldPrice() {
        return fieldPrice;
    }

    /**
     * Getter f�r die Instanzvariable harvest_per_field.
     * @return harvest_per_field Getreidemenge, die bei der Aberntung einer Feldeinheit erhalten wird.
     */
    public static int getHarvest_per_field() {
        return harvest_per_field;
    }

    /**
     * Getter f�r die Instanzvariable grainPrice.
     * @return grainPrice Geldmenge, die man f�r das Verkaufen einer Mengeneinheit Getreide erh�lt.
     */
    public static int getGrainPrice() {
        return grainPrice;
    }

}
