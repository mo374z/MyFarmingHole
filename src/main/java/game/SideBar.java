package game;

import game.movingEntitiesPackage.Harvester;
import game.movingEntitiesPackage.MovingEntity;
import game.movingEntitiesPackage.Player;
import game.movingEntitiesPackage.Tractor;
import game.movingEntitiesPackage.Trailer;
import game.movingEntitiesPackage.Vehicle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Erstellung der SideBar am rechten Rand des Spiels.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class SideBar {

    /** Deklaration der Sidebar als BorderPane */
    public static BorderPane sidebar = new BorderPane();

    /** Import des Hintergrundbildes f�r die Sidebar */
    private static BackgroundImage sidebar_background = new BackgroundImage(new Image("file:src/Images/sidebar/sidebar_bg.png"), null, null, null, null);

    /** Import des Bildes f�r den Resetbutton der SideBar */
    private static ImageView resetButton = new ImageView(new Image("file:src/Images/sidebar/reset_button.png"));

    /** Import des Bildes, welches bei dr�cken des Buttons agezeigt wird   */
    private static Image resetButton2 = new Image("file:src/Images/sidebar/reset_button2.png");

    /** Festlegung Beschriftung  der SideBar mit dem aktuellen Geldstand */
    private static Label money;

    /** Festlegung der Beschriftung  der SideBar mit dem aktuellen Tank */
    private static Label fuel;

    /** Festlegung der Beschriftung  der SideBar mit dem aktuellen Getreidestand */
    private static Label grainTank;

    /**
     * Erstellung der BorderPane f�r die SideBar.
     * @return gibt die SideBar als BorderPane am rechten Rand des Fensters zur�ck
     */
    public static BorderPane buildSideBar() {
        VBox upperPart = new VBox();
        new Button();

        sidebar.setPadding(new Insets(36, 90, 36, 36));
        sidebar.setBackground(new Background(sidebar_background));

        upperPart.setSpacing(10);

        Font font = new Font(20.0);
        Font.font("Tahoma", FontWeight.BOLD, 20.0);
        Font font2 = new Font(12.0);
        Font.font("Tahoma", FontWeight.BOLD, 12.0);

        money = new Label();
        money.setFont(font);
        money.setTextFill(Color.web("#000000"));

        fuel = new Label();
        fuel.setFont(font);
        fuel.setTextFill(Color.web("#000000"));

        grainTank = new Label();
        grainTank.setFont(font);
        grainTank.setTextFill(Color.web("#000000"));

        Label tooltips = Tooltips.tooltip;
        tooltips.setFont(font2);
        tooltips.setTextFill(Color.web("#000000"));


        resetButton.setOnMousePressed(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                Vehicle.currentlyActive.moveEntityTo(500, 250);
                GameChat.setNewMessage("Teleported to spawn");
                resetButton.setImage(resetButton2);
            }

        });

        resetButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                resetButton.setImage(new Image("file:src/Images/sidebar/reset_button.png"));
            }

        });

        upperPart.getChildren().addAll(money, fuel, grainTank, tooltips, resetButton);


        VBox lowerPart = new VBox();

        Label gameChat = GameChat.getGameChat();
        gameChat.setFont(font2);
        gameChat.setTextFill(Color.web("#000000"));
        gameChat.setPrefWidth(180);
        gameChat.setWrapText(true);

        lowerPart.getChildren().addAll(gameChat);


        sidebar.setCenter(upperPart);
        sidebar.setBottom(lowerPart);

        return sidebar;
    }

    /**
     * Methode, die die Getreidef�llmenge- Beschriftung festlegt.
     * Wenn sich der Spieler in einem {@link Vehicle} mit Getreidetank befindet, so wird die F�llmenge als Beschriftung auf der SideBar angezeigt.
     * Wenn nicht, so wird der Betrag im Silo angezeigt.
     */
    public static void setGrainTankLabel() {
        if(MovingEntity.currentlyActive != null && MovingEntity.currentlyActive.getClass() == Harvester.class) {
            grainTank.setText("Harvester: " + (int) ((Harvester) MovingEntity.currentlyActive).getGrainTank().getCapacity() + " Grain");
        } else if(MovingEntity.currentlyActive != null && MovingEntity.currentlyActive.getClass() == Tractor.class) {
            Tractor activeTractor = (Tractor) MovingEntity.currentlyActive;
            if(activeTractor.currentlyAttached !=null && activeTractor.currentlyAttached.getClass()==Trailer.class){
                grainTank.setText("Trailer: " + (int) ((Trailer) activeTractor.currentlyAttached).getGrainTank().getCapacity() + " Grain");
            }
        }	else {
            grainTank.setText("Silo: " + (int) FarmingHoleApplication.silo.getCapacity() + " Grain");
        }
    }

    /**
     * Methode, welche den Tankstand f�r das Vehicle anzeigt, wenn der Spieler sich in einem Fahrzeug befindet.
     * Wenn sich der Spieler in einem {@link Vehicle} befindet, so wird der aktuelle Tank des Fahrzeugs als Beschriftung auf der SideBar angezeigt
     * Wenn nicht, so wird dem Spieler sein tragbarer Tank auf der SideBar als Beschriftung angezeigt
     */
    public static void setFuelLabel() {
        if(MovingEntity.currentlyActive != null && MovingEntity.currentlyActive.getClass().getSuperclass() == Vehicle.class) {
            fuel.setText("Fuel: " + (int) ((Vehicle)MovingEntity.currentlyActive).getVehicleFuelTank().getFuel() + " liter");
        } else if(MovingEntity.currentlyActive != null && MovingEntity.currentlyActive.getClass() == Player.class) {
            fuel.setText("Fuel Inventory: " + (int) ((Player)MovingEntity.currentlyActive).getFuelInventory() + " liter");
        }
    }

    /**
     * Setter Methode, die das vorher definierte Geld zu Beginn des Spiels als Beschriftung auf der SideBar anzeigt
     */
    public static void setMoneyLabel() {
        money.setText("Money: " + FarmingHoleApplication.getMyMoney() + " coins");
    }

    /**

     /**
     * Statische Methode, die die Beschriftungen (Geld, Tank, Getreidemenge), sowie die Spielhinweise der Klasse {@link Tooltips} enth�lt
     */
    public static void updateSideBar() {
        setMoneyLabel();
        setFuelLabel();
        setGrainTankLabel();

        GameChat.getChat();
        Tooltips.updateTooltip();
    }

}
