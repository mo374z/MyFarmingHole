package game.notMovingEntitiesPackage;

import game.FarmingHoleApplication;

/**
 * Klasse repr�sentiert einen Getreidetank.
 * Es werden somit alle ben�tigten Funktion und Variablen f�r den Umgang mit einem Getreidetank bereitsgestellt.
 * In den Klassen {@link game.movingEntitiesPackage.Harvester} und {@link game.movingEntitiesPackage.Trailer} wird jeweils eine Instanz des grainTanks erzeugt, da sowohl M�hdrescher als auch Anh�nger Getreide speichern sollen.
 * @author T. Heiss, P. Knoll, M. Schlager, N. Schulz
 *
 */
public class GrainTank {
    /** Repr�sentiert den aktuellen F�llstand des Getreidetanks. */
    private double capacity;
    /** Repr�sentiert die maximale F�llmenge des Getreidetanks. */
    private double max_capacity;

    /**
     * Konstruktor f�r einen Getreidetank, der mit entsprechender "Startkapazit�t" dann initialisiert wird.
     * @param capacity F�llmenge, die der Getreidetank zu Beginn haben soll.
     * @param max_capacity Maximale Kapazit�t, die der initialisierte Getreidetank besitzen soll.
     */
    public GrainTank(double capacity, double max_capacity) {
        this.capacity = capacity;
        this.max_capacity = max_capacity;
    }

    /**
     * Bei Aufruf dieser Methode wird die F�llmenge des Getreidetanks um eine Einheit erh�ht (sofern m�glich).
     * @return Gibt true zur�ck, wenn die F�llmenge verringert werden konnte (=wenn die vorherige Kapazit�t kleiner als die maximale Kapazit�t war), gibt false zur�ck, wenn die F�llmenge nicht erh�ht werden kann.
     */
    public boolean fillGrainTank() {
        if (capacity <= max_capacity - FarmingHoleApplication.getHarvest_per_field()) {
            capacity+=FarmingHoleApplication.getHarvest_per_field();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Getter f�r die Instanzvariable max_capacity. Gibt also die maximale Kapazit�t eines Getreidetanks zur�ck.
     * @return max_capacity Maximale Kapazit�t des Getreidetanks.
     */
    public double getMax_capacity() {
        return max_capacity;
    }

    /**
     * Bei Aufruf dieser Methode wird die F�llmenge des Getreidetanks um eine Einheit reduziert.
     * @return Gibt true zur�ck, wenn die F�llmenge verringert werden konnte (=wenn die vorherige Kapazit�t >=1 war), gibt false zur�ck, wenn die F�llmenge nicht verringert werden kann.
     */
    public boolean emptyGrainTank() {
        if (capacity >= FarmingHoleApplication.getHarvest_per_field()) {
            capacity-=FarmingHoleApplication.getHarvest_per_field();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Getter f�r die Instanzvariable capacity. Gibt also die aktuelle F�llemenge eines Getreidetanks zur�ck.
     * @return capacity Aktuelle F�llmenge des Getreidetanks.
     */
    public double getCapacity() {
        return capacity;
    }



}
