package game;

import game.movingEntitiesPackage.MovingEntity;
import game.movingEntitiesPackage.Player;
import game.movingEntitiesPackage.Vehicle;
import game.notMovingEntitiesPackage.Bridge;
import game.notMovingEntitiesPackage.Buildings;
import game.movingEntitiesPackage.Tractor;
import game.movingEntitiesPackage.Trailer;
import game.movingEntitiesPackage.Harvester;
import javafx.scene.control.Label;

/**
 * Hinweise zum Spielen, die auf der {@link SideBar} angezeigt werden
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class Tooltips {

    /** Defintion eiens statischen Strings, wenn toolTip leer ist. */
    private static String textIfNoTip = "If you can press any key \nTooltips will show up.";

    /** Definition der statischen Beschriftung "toolTip". */
    @SuppressWarnings("exports")
    public static Label tooltip = new Label();

    /**
     * Hinweise zum Spielen , die auf der {@link SideBar} auftauchen
     * @return gibt den Hinweis als String auf der SideBar zur�ck
     */
    public static String buildToolTip() {
        StringBuilder toolTip = new StringBuilder();
        toolTip.append("Press W to move north.\n");
        toolTip.append("Press A to move west.\n");
        toolTip.append("Press S to move south.\n");
        toolTip.append("Press D to move east.\n");

        if(FarmingHoleApplication.player.checkForVehiclesNearby(FarmingHoleApplication.player.getKoordX(), FarmingHoleApplication.player.getKoordY()) != null && MovingEntity.currentlyActive == FarmingHoleApplication.player) {
            toolTip.append("Press E to use "+ FarmingHoleApplication.player.getCanBeUsed().getName() + " \n");
            if(FarmingHoleApplication.player.getFuelInventory()>0 && FarmingHoleApplication.player.getCanBeUsed().getVehicleFuelTank().getFuel()<Vehicle.MAX_FUEL) {
                toolTip.append("Press R to refill " + FarmingHoleApplication.player.getCanBeUsed().getName() + " \n");
            }
        } else if (FarmingHoleApplication.player.getCurrentlyUsedVehicle() != null && MovingEntity.currentlyActive.getClass() != Player.class) {
            toolTip.append("Press E to unuse " + FarmingHoleApplication.player.getCurrentlyUsedVehicle().getName() + " \n");
        }

        if(MovingEntity.currentlyActive.checkForBuildingsNearby(MovingEntity.currentlyActive.getKoordX(), MovingEntity.currentlyActive.getKoordY()) != null) {
            if (Buildings.getName().equals("Gasstation")) {
                toolTip.append("Press R to get some fuel \n");
            }
        }

        if(Buildings.evaluateIfEntityInArea(Buildings.siloArea, MovingEntity.currentlyActive.getKoordX(), MovingEntity.currentlyActive.getKoordY())) {
            if(MovingEntity.currentlyActive.getClass()==Harvester.class) {
                toolTip.append("Press F to fill grain into "+ Buildings.getName() + " \n");
            } else if(MovingEntity.currentlyActive.getClass()==Tractor.class) {
                Tractor activeTractor = (Tractor) MovingEntity.currentlyActive;
                if (activeTractor.currentlyAttached!=null && activeTractor.currentlyAttached.getClass()==Trailer.class) {
                    toolTip.append("Press F to get grain from "+ Buildings.getName() + " \n");
                }
            }
        }

        if(Buildings.evaluateIfEntityInArea(Buildings.farmhouseArea, MovingEntity.currentlyActive.getKoordX(), MovingEntity.currentlyActive.getKoordY())){
            if(MovingEntity.currentlyActive.getClass()==Tractor.class) {
                Tractor activeTractor = (Tractor) MovingEntity.currentlyActive;
                if (activeTractor.currentlyAttached!=null && activeTractor.currentlyAttached.getClass()==Trailer.class) {
                    toolTip.append("Press T to sell grain from your trailer \n");
                }
            }
        }

        if(Vehicle.currentlyActive.getClass() == Tractor.class) {
            Tractor activeTractor = (Tractor) MovingEntity.currentlyActive;
            if (activeTractor.currentlyAttached==null) {
                for (Vehicle v: FarmingHoleApplication.allVehicles) {
                    if (v.getClass() == Tractor.class && ((Tractor) v).checkForAttachablesNearby() != null) {
                        toolTip.append("Press Q to atttach " + activeTractor.canBeUsed.getName() + " \n");
                    }
                }
            } else if (activeTractor.currentlyAttached != null) {
                toolTip.append("Press Q to detach " + activeTractor.currentlyAttached.getName() + " \n");
            }
        }

        if(Vehicle.currentlyActive.getClass() == Harvester.class && ((Harvester)Vehicle.currentlyActive).searchForTrailerNearby()!=null) {
            toolTip.append("Press F to fill Trailer " + ((Harvester)Vehicle.currentlyActive).searchForTrailerNearby().getName() + " \n");
        }

        if(MovingEntity.currentlyActive.checkForLocksNearby() != null) {
            if(MovingEntity.currentlyActive.checkForLocksNearby().isVisible) {
                toolTip.append("Press B to buy Field (costs: " + MovingEntity.currentlyActive.checkForLocksNearby().price + ") \n");
            }
        }

        if(Buildings.evaluateIfEntityInArea(Buildings.bridgeArea, MovingEntity.currentlyActive.getKoordX(), MovingEntity.currentlyActive.getKoordY()) && !FarmingHoleApplication.bridge.isRepaired()) {
            toolTip.append("Press X to repair the bridge for \n" + Bridge.bridgePrice + " Coins and win the game!");
        }

        if(toolTip.isEmpty()) {
            toolTip.append(textIfNoTip);
        }

        return toolTip.toString();
    }

    /**
     * Statische Methode, die den jeweiligen Hinweis der {@link SideBar} enth�lt
     */
    public static void updateTooltip() {
        tooltip.setText(buildToolTip());
    }

}
