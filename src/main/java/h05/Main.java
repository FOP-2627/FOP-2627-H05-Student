package h05;

import h05.core.BaseRegisseur;
import h05.worlds.collecting.CollectingRegisseur;
import h05.worlds.racing.RacingRegisseur;
import h05.worlds.sorting.SortingRegisseur;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        /*
         * To launch a specific world, use the corresponding world-class as the dynamic type of the actualReg variable.
         *
         * List of all worlds:
         * - RecyclingRegisseur: cleaning the stadium before the race
         * - SortingRegisseur:   sorting the collected garbage for recycling
         * - RacingRegisseur:    race with boosters
         *
         * Below you can see an example of how to start a world.
         * Complete the assignment fully for it to display correctly.
         */

        BaseRegisseur actualReg = new CollectingRegisseur();
        // BaseRegisseur actualReg = new SortingRegisseur();
        // BaseRegisseur actualReg = new RacingRegisseur();


        actualReg.start();
    }
}
