
package epidemic.progpracticum.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class GridListener implements ActionListener {

    private WorldPanel mySimulationPanel;

    public GridListener(WorldPanel aSimulationPanel) {
        super();
        mySimulationPanel = aSimulationPanel;
    }

    @Override
    public void actionPerformed(ActionEvent anEvent) {

        final Object source = anEvent.getSource();
        final JCheckBoxMenuItem orgin = (JCheckBoxMenuItem) source;
        if (orgin.isSelected()) {
            mySimulationPanel.setGridMode(true);
        }
        else {
            mySimulationPanel.setGridMode(false);
        }

        mySimulationPanel.updateUI();

    }

}
