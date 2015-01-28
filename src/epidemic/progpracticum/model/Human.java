
package epidemic.progpracticum.model;

import java.awt.Point;

public class Human extends AbstractEntity {

    private char myGender;
    private boolean newBorn;
    private int daysAsABaby;

    public Human(char aSpecies, boolean aSick, int aDayNum, Point aLocation, char aGender) {
        super(aSpecies, aSick, aDayNum, aLocation, aGender);
        daysAsABaby = 0;

    }

    public Human(char aSpecies, boolean aSick, int aDayNum, Point aLocation, char aGender,
                 boolean aNewborn) {
        super(aSpecies, aSick, aDayNum, aLocation, aGender);
        newBorn = aNewborn;
        daysAsABaby = 0;

    }

    @Override
    public void move() {
        int x = SimParams.GENERATOR.nextInt(3);
        int y = SimParams.GENERATOR.nextInt(3);

        if (x == 2) {
            x = -1;
        }
        if (y == 2) {
            y = -1;
        }

        int oldX = this.getMyLocation().x;
        int oldY = this.getMyLocation().y;
        oldX += (x * 10);
        oldY += (y * 10);
        if (oldX > SimParams.PANEL_PIX_WIDTH) {
            oldX = 0;
        }
        if (oldY > SimParams.PANEL_PIX_HEIGHT) {
            oldY = 0;
        }
        if (oldX < 0) {
            oldX = SimParams.PANEL_PIX_WIDTH - SimParams.HUMAN_DIM;
        }
        if (oldY < 0) {
            oldY = SimParams.PANEL_PIX_HEIGHT - SimParams.HUMAN_DIM;
        }
        Point newLocation = new Point(oldX, oldY);
        this.setMyLocation(newLocation);
        if (this.isSick() && !this.isDead()) {
            incementDaysSick();
        }

        if (daysAsABaby > 50) {
            newBorn = false;
        }

        if (newBorn) {
            daysAsABaby++;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HUMAN";
    }

    /**
     * @return the newBorn
     */
    public boolean getNewBorn() {
        return newBorn;
    }

    /**
     * @param newBorn the newBorn to set
     */
    public void setNewBorn(boolean newBorn) {
        this.newBorn = newBorn;
    }

}
