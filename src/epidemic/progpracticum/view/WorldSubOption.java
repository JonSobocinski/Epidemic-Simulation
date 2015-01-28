
package epidemic.progpracticum.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import epidemic.progpracticum.model.SimParams;

public class WorldSubOption extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 2006978575133108780L;
    private final JPanel contentPanel = new JPanel();
    private JSpinner numberOfDoctorsSpinner;
    private JSpinner chanceOfInfectionSpinner;
    private JSpinner daysSickUntilYouDieSpinner;
    private JSpinner chanceToBeHealedSpinner;
    private JSpinner numberOfPoliceSpinner;

    private SimulationFrame mySimulationFrame;

    private final int maxiumPer = 100;
    private final int minPer = 1;
    private final int maxSupportUnits = 10;
    private final int maxDaysSickForRandomGeneration = 100;

    public WorldSubOption(SimulationFrame aSimulationFrame) {
        setResizable(false);
        setTitle("World Options");
        setType(Type.UTILITY);
        setAlwaysOnTop(true);

        mySimulationFrame = aSimulationFrame;

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(10, 1, 0, 0));
        {
            JLabel doctorLabel = new JLabel("Number Of Doctors");
            doctorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(doctorLabel);
        }
        {
            numberOfDoctorsSpinner = new JSpinner();
            numberOfDoctorsSpinner.setModel(new SpinnerNumberModel(SimParams
                    .getNumberOfDoctorsToCreate(), 0, maxSupportUnits, 1));
            contentPanel.add(numberOfDoctorsSpinner);
            numberOfDoctorsSpinner.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent anEvent) {
                    SimParams.setNumberOfDoctorsToCreate((int) numberOfDoctorsSpinner
                            .getValue());

                }
            });

        }

        {
            JLabel policeLabel = new JLabel("Number Of Police");
            policeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(policeLabel);
        }
        {
            numberOfPoliceSpinner = new JSpinner();
            numberOfPoliceSpinner.setModel(new SpinnerNumberModel(SimParams
                    .getNumberOfPoliceToCreate(), 0, maxSupportUnits, 1));
            contentPanel.add(numberOfPoliceSpinner);
            numberOfPoliceSpinner.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent anEvent) {
                    SimParams.setNumberOfPoliceToCreate((int) numberOfDoctorsSpinner
                            .getValue());

                }
            });

        }
        {
            JLabel chanceOfInfectionLabel = new JLabel("Percent Chance Of Being Infected");
            chanceOfInfectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(chanceOfInfectionLabel);
        }
        {
            chanceOfInfectionSpinner = new JSpinner();
            chanceOfInfectionSpinner.setModel(new SpinnerNumberModel(SimParams
                    .getChanceOfInection(), minPer, maxiumPer, 1));
            contentPanel.add(chanceOfInfectionSpinner);
            chanceOfInfectionSpinner.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent anEvent) {
                    SimParams.setChanceOfInection((int) chanceOfInfectionSpinner.getValue());
                }
            });

        }

        {
            JLabel chanceToBeHealedLabel = new JLabel("Percent Chance Of Being Healed");
            chanceToBeHealedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(chanceToBeHealedLabel);
        }
        {
            chanceToBeHealedSpinner = new JSpinner();
            chanceToBeHealedSpinner.setModel(new SpinnerNumberModel(SimParams
                    .getChanceOfInection(), minPer, maxiumPer, 1));
            contentPanel.add(chanceToBeHealedSpinner);
            chanceToBeHealedSpinner.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent anEvent) {
                    SimParams.setChanceOfInection((int) chanceToBeHealedSpinner.getValue());
                }
            });

        }

        {
            JLabel daysSickUntilYouDieLabel = new JLabel("Days Sick Until You Die");
            daysSickUntilYouDieLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(daysSickUntilYouDieLabel);
        }
        {
            daysSickUntilYouDieSpinner = new JSpinner();
            daysSickUntilYouDieSpinner.setModel(new SpinnerNumberModel(SimParams
                    .getDaysSickUntilYouDie(), minPer, null, 1));
            contentPanel.add(daysSickUntilYouDieSpinner);
            daysSickUntilYouDieSpinner.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent anEvent) {
                    SimParams.setDaysSickUntilYouDie((int) daysSickUntilYouDieSpinner
                            .getValue());
                }
            });

        }

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                JButton randomizeButton = new JButton("Randomize Settings");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent anEvent) {

                        setValues();
                        mySimulationFrame.updateNumberLabels();
                        mySimulationFrame.updateGUI();
                        dispose();

                    }
                });

                randomizeButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        randomizeOptions();

                    }
                });
                okButton.setActionCommand("OK");
                randomizeButton.setActionCommand("OK");
                buttonPane.add(randomizeButton);
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }

        pack();
        setLocationRelativeTo(null);

    }

    public void randomizeOptions() {
        SimParams
                .setNumberOfDoctorsToCreate((SimParams.GENERATOR.nextInt(maxSupportUnits) + 1));
        SimParams.setChanceOfInection(SimParams.GENERATOR.nextInt(maxiumPer) + 1);
        SimParams.setChanceToBeHealed(SimParams.GENERATOR.nextInt(maxiumPer));
        SimParams.setDaysSickUntilYouDie(SimParams.GENERATOR
                .nextInt(maxDaysSickForRandomGeneration));
        SimParams.setNumberOfPoliceToCreate(SimParams.GENERATOR.nextInt(maxSupportUnits) + 1);

        numberOfDoctorsSpinner.setValue(SimParams.getNumberOfDoctorsToCreate());
        chanceOfInfectionSpinner.setValue(SimParams.getChanceOfInection());
        daysSickUntilYouDieSpinner.setValue(SimParams.getDaysSickUntilYouDie());
        chanceToBeHealedSpinner.setValue(SimParams.getChanceToBeHealed());
        numberOfPoliceSpinner.setValue(SimParams.getNumberOfPoliceToCreate());

        mySimulationFrame.updateNumberLabels();
        mySimulationFrame.updateGUI();

    }

    private void setValues() {
        SimParams.setChanceOfInection((int) chanceOfInfectionSpinner.getValue());
        SimParams.setChanceToBeHealed((int) chanceToBeHealedSpinner.getValue());
        SimParams.setDaysSickUntilYouDie((int) daysSickUntilYouDieSpinner.getValue());
        SimParams.setNumberOfDoctorsToCreate((int) numberOfDoctorsSpinner.getValue());
        SimParams.setNumberOfPoliceToCreate((int) numberOfPoliceSpinner.getValue());

        mySimulationFrame.updateNumberLabels();
        mySimulationFrame.updateGUI();

    }

}
