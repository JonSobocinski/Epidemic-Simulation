package epidemic.progpracticum.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

public class LoopActionListener implements ActionListener {

    private SimulationFrame mySimulationFrame;
    public LoopActionListener(SimulationFrame aSimulationFrame) {
        mySimulationFrame = aSimulationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent anEvent) {
        final Object source = anEvent.getSource();
        final JCheckBoxMenuItem orgin = (JCheckBoxMenuItem) source;
        if (orgin.isSelected()){
            mySimulationFrame.setLoopSimulation(true);
        }else{
            mySimulationFrame.setLoopSimulation(false);
        }

    }

}
