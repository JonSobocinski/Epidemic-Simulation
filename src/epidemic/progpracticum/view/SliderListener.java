
package epidemic.progpracticum.view;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import epidemic.progpracticum.model.SimulationWorld;

public class SliderListener implements ChangeListener {

    SimulationWorld myWorldSimulator;

    public SliderListener(SimulationWorld myWorldSimulatorData) {
        myWorldSimulator = myWorldSimulatorData;
    }

    @Override
    public void stateChanged(ChangeEvent anEvent) {
        JSlider source = (JSlider) anEvent.getSource();
        if (source.getName().equals("PEOPLE")) {
            myWorldSimulator.setMyNumberOfPeopleToCreate(source.getValue());
        }
        else if (source.getName().equals("BIRD")) {
            myWorldSimulator.setMyNumberOfBirdsToCreate(source.getValue());
        }
        else {
            myWorldSimulator.setMyPerOfBirdsInfected(source.getValue());
        }
        myWorldSimulator.updateNumberLabels();

    }

}
