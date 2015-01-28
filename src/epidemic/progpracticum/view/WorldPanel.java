
package epidemic.progpracticum.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import epidemic.progpracticum.model.AbstractEntity;
import epidemic.progpracticum.model.Human;
import epidemic.progpracticum.model.SimParams;
import epidemic.progpracticum.model.SimulationWorld;

public class WorldPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 6485148527402909270L;
    private SimulationWorld mySimData;
    private List<AbstractEntity> myAbstractEntityList;
    private final int myColorMuliplier = 5;
    private boolean grid;

    public WorldPanel(SimulationWorld aWorldSimulatorData) {

        super();
        mySimData = aWorldSimulatorData;
        myAbstractEntityList = mySimData.getMyEntityList();
        setBackground(Color.WHITE);
        Dimension myDim = new Dimension(SimParams.PANEL_PIX_WIDTH, SimParams.PANEL_PIX_HEIGHT);
        setSize(SimParams.PANEL_PIX_WIDTH, SimParams.PANEL_PIX_HEIGHT);
        setMaximumSize(myDim);
        setMinimumSize(myDim);
        setPreferredSize(myDim);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        grid = false;
    }

    public void reset() {
        mySimData.reset();
        mySimData.resetStats();
        updateUI();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (grid) {
            drawGrid(g2);
        }

        for (AbstractEntity item : myAbstractEntityList) {

            drawHumans(g2, item);
            drawBabies(g2, item);
            drawDoctors(g2, item);
            drawPolice(g2, item);
            drawBirds(g2, item);

        }
    }

    private void drawGrid(Graphics2D g2) {

        for (int i = 0; i < this.getWidth(); i += 10) {

            g2.setColor(Color.gray);
            g2.drawLine(i, 0, i, this.getHeight());
        }
        for (int i = 0; i < this.getHeight(); i += 10) {
            g2.setColor(Color.gray);
            g2.drawLine(0, i, this.getWidth(), i);
        }

    }

    private void drawBabies(Graphics2D g2, AbstractEntity item) {
        if (item.validateEntityIsAHuman()) {
            Human human = (Human) item;
            if (human.getNewBorn() && item.validateHealthyHuman()) {

                Ellipse2D ellipse =
                        new Ellipse2D.Double(item.getMyLocation().x, item.getMyLocation().y,
                                             SimParams.HUMAN_DIM - 3, SimParams.HUMAN_DIM - 3);
                g2.setColor(Color.cyan);
                g2.fill(ellipse);
                g2.draw(ellipse);
            }
            else if (human.getNewBorn() && item.validateImmuneHuman()) {
                Ellipse2D ellipse =
                        new Ellipse2D.Double(item.getMyLocation().x, item.getMyLocation().y,
                                             SimParams.HUMAN_DIM - 3, SimParams.HUMAN_DIM - 3);

                g2.setColor(Color.magenta);
                g2.fill(ellipse);
                g2.draw(ellipse);
            }
            else if (human.getNewBorn() && item.validateInfectedHuman()) {
                Ellipse2D ellipse =
                        new Ellipse2D.Double(item.getMyLocation().x, item.getMyLocation().y,
                                             SimParams.HUMAN_DIM - 3, SimParams.HUMAN_DIM - 3);
                g2.setColor(Color.black);
                g2.fill(ellipse);
                g2.draw(ellipse);

            }
        }

    }

    private void drawPolice(Graphics2D g2, AbstractEntity item) {
        if (item.validateEntityIsAPoliceOfficer()) {

            Rectangle cop =
                    new Rectangle(item.getMyLocation().x, item.getMyLocation().y, 10, 10);

            g2.setColor(new Color(51, 100, 0));
            g2.fill(cop);

            g2.draw(cop);

        }

    }

    private void drawHumans(final Graphics2D g2, AbstractEntity item) {
        Human human = null;

        if (item.getClass().getSimpleName().equalsIgnoreCase("Human")) {
            human = (Human) item;
        }

        if (item.toString().equalsIgnoreCase("HUMAN") && !item.isDead() && !human.getNewBorn()) {

            Ellipse2D ellipse;
            if (item.getMyGender() == 'F') {
                ellipse =
                        new Ellipse2D.Double(item.getMyLocation().x, item.getMyLocation().y,
                                             SimParams.HUMAN_DIM - 2, SimParams.HUMAN_DIM - 2);
            }

            else {
                ellipse =
                        new Ellipse2D.Double(item.getMyLocation().x, item.getMyLocation().y,
                                             SimParams.HUMAN_DIM, SimParams.HUMAN_DIM);
            }
            g2.setColor(Color.GREEN);
            g2.fill(ellipse);
            if (item.isSick()) {
                int r = 255 - (item.getMyDaysSick() * myColorMuliplier);
                if (r <= 0) {
                    r = 0;
                }
                g2.setColor(new Color(r, 0, 0));
                g2.fill(ellipse);
            }

            if (item.getMyGender() == 'F' && item.validateHealthyHuman()) {
                g2.setColor(Color.PINK);
                g2.fill(ellipse);
            }

            if (item.isImmune()) {

                g2.setColor(Color.magenta);
                g2.fill(ellipse);
            }

            g2.draw(ellipse);
        }
    }

    private void drawBirds(final Graphics2D g2, AbstractEntity item) {

        if (item.toString().equalsIgnoreCase("BIRD") && !item.isDead()) {
            Ellipse2D ellipse =
                    new Ellipse2D.Double(item.getMyLocation().x + 2,
                                         item.getMyLocation().y + 3, SimParams.BIRD_DIM,
                                         SimParams.BIRD_DIM);
            g2.setColor(Color.GRAY);
            g2.fill(ellipse);
            if (item.isSick()) {
                int gColor = 255 - (item.getMyDaysSick() * myColorMuliplier);
                if (gColor <= 0) {
                    gColor = 0;
                }
                g2.setColor(new Color(255, gColor, 0));
                g2.fill(ellipse);
            }
            if (item.isImmune()) {

                g2.setColor(Color.magenta);
                g2.fill(ellipse);
            }

            g2.draw(ellipse);
        }
    }

    private void drawDoctors(final Graphics2D g2, AbstractEntity item) {

        if (item.toString().equalsIgnoreCase("DOCTOR") && !item.isDead()) {
            Ellipse2D ellipse =
                    new Ellipse2D.Double(item.getMyLocation().x, item.getMyLocation().y,
                                         SimParams.HUMAN_DIM, SimParams.HUMAN_DIM);
            g2.setColor(Color.BLUE);
            g2.fill(ellipse);

            if (item.isSick()) {
                int bColor = 255 - (item.getMyDaysSick() * myColorMuliplier * (2));
                if (bColor <= 0) {
                    bColor = 0;
                }
                g2.setColor(new Color(0, 0, bColor));
                g2.fill(ellipse);
            }

            if (item.isImmune()) {

                g2.setColor(Color.ORANGE);
                g2.fill(ellipse);
            }

            g2.draw(ellipse);
        }
    }

    public void setGridMode(boolean aGridMode) {
        grid = aGridMode;
    }
}
