/**
 * Your name. Final project Autumn 2013
 */

package epidemic.progpracticum.model;

import java.util.Random;

/**
 * Simulation Parameters as Global Constants.
 * 
 * @author Monika
 * @version Autumn 2013
 */
public final class SimParams {

    /**
     * the width of the panel in pixels.
     */
    public static final int PANEL_PIX_WIDTH = 1000;

    /**
     * the height of the panel in pixels.
     */
    public static final int PANEL_PIX_HEIGHT = 500;

    /**
     * the max number of birds.
     */
    public static final int MAX_BIRD_NUM = 1000;

    /**
     * the max number of humans.
     */
    public static final int MAX_HUMAN_NUM = 1000;

    /**
     * the size of a human in pixels.
     */
    public static final int HUMAN_DIM = 10;

    /**
     * the size of a bird in pixels.
     */
    public static final int BIRD_DIM = 5;

    /**
     * random number generator object seeded to current time.
     */
    public static final Random GENERATOR = new Random(System.currentTimeMillis());

    /**
     * Number of milliseconds per day.
     */
    private static int animationStepTime = 100;

    /**
     * Number of dr to create.
     */
    private static int numberOfDoctorsToCreate = 5;

    /**
     * probability of getting infected.
     */
    private static int chanceOfInection = 50;

    private static int daysSickUntilYouDie = 20;

    private static int chanceOfHealing = 25;

    private static int numberOfPolicemen = 2;

    private static int chanceToBreed = 5;

    /**
     * private constructor to guard against instantiation.
     */
    private SimParams() {
    }

    public static int getNumberOfDoctorsToCreate() {
        return numberOfDoctorsToCreate;
    }

    public static void setNumberOfDoctorsToCreate(int doctorsToCreate) {
        numberOfDoctorsToCreate = doctorsToCreate;
    }

    public static int getChanceOfInection() {
        return chanceOfInection;
    }

    public static void setChanceOfInection(int chanceOfInfection) {
        chanceOfInection = chanceOfInfection;
    }

    public static int getDaysSickUntilYouDie() {
        return daysSickUntilYouDie;
    }

    /**
     * @param dAYS_SICK_UNTIL_YOU_DIE the dAYS_SICK_UNTIL_YOU_DIE to set
     */
    public static void setDaysSickUntilYouDie(int aDaysSickUntilYouDie) {
        daysSickUntilYouDie = aDaysSickUntilYouDie;
    }

    public static int getChanceToBeHealed() {
        return chanceOfHealing;
    }

    public static void setChanceToBeHealed(int chanceToBeHealed) {
        chanceOfHealing = chanceToBeHealed;
    }

    public static int getNumberOfPoliceToCreate() {
        return numberOfPolicemen;
    }

    public static void setNumberOfPoliceToCreate(int aNumber) {
        numberOfPolicemen = aNumber;
    }

    /**
     * @return the animationStepTime
     */
    public static int getAnimationStepTime() {
        return animationStepTime;
    }

    /**
     * @param animationStepTime the animationStepTime to set
     */
    public static void setAnimationStepTime(int aStepTime) {
        animationStepTime = aStepTime;
    }

    public static int getChanceToBreed() {
        // TODO Auto-generated method stub
        return chanceToBreed;
    }

    public static void setChanceToBreed(int aChanceToBreed) {
        chanceToBreed = aChanceToBreed;
    }

}
