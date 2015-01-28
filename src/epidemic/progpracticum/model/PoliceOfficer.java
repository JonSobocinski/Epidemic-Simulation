
package epidemic.progpracticum.model;

import java.awt.Point;

public class PoliceOfficer extends AbstractEntity {

    private int infectedIHaveKilled;
    private int birdsKilled;
    private int humansKilled;
    private int doctorsKilled;

    public PoliceOfficer(char aSpecies, boolean aSick, int aDayNum, Point aLocation) {
        super(aSpecies, aSick, aDayNum, aLocation, 'N');
        this.makeImmune();
        infectedIHaveKilled = 0;

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

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "POLICE";
    }

    @Override
    public String itemsInformation(AbstractEntity entity) {
        return "This officer has killed " + infectedIHaveKilled
               + " infected. They have killed " + humansKilled + " humans, " + birdsKilled
               + " birds, and " + doctorsKilled + " doctors.";

    }

    public void incrementKillCount(AbstractEntity anEntityIHaveKilled) {
        infectedIHaveKilled++;
        if (anEntityIHaveKilled.toString().equalsIgnoreCase("HUMAN")) {
            humansKilled++;
        }
        else if (anEntityIHaveKilled.toString().equalsIgnoreCase("BIRD")) {
            birdsKilled++;
        }
        else if (anEntityIHaveKilled.toString().equalsIgnoreCase("DOCTOR")) {
            doctorsKilled++;
        }
    }

}
