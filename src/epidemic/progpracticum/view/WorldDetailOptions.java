
package epidemic.progpracticum.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import epidemic.progpracticum.model.SimParams;

public class WorldDetailOptions extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private SimulationFrame mySimulationFrame;
    private JLabel myCurrentSpeedLabel;
    private final JSlider mySimulationSpeedSlider;

    /**
     * Create the dialog.
     */
    public WorldDetailOptions(SimulationFrame aSimulationFrame) {

        mySimulationFrame = aSimulationFrame;

        setType(Type.UTILITY);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));

        JPanel detailSliderPanel = new JPanel();
        contentPanel.add(detailSliderPanel, BorderLayout.NORTH);
        detailSliderPanel.setLayout(new BorderLayout(0, 0));

        JLabel mySimulationSpeedLabel = new JLabel("Simulation Speed");
        mySimulationSpeedLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        mySimulationSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailSliderPanel.add(mySimulationSpeedLabel, BorderLayout.NORTH);

        mySimulationSpeedSlider = new JSlider();
        mySimulationSpeedSlider.setValue(SimParams.getAnimationStepTime());
        mySimulationSpeedSlider.setPaintTicks(true);
        mySimulationSpeedSlider.setPaintLabels(true);
        mySimulationSpeedSlider.setMajorTickSpacing(100);
        mySimulationSpeedSlider.setMinorTickSpacing(50);
        mySimulationSpeedSlider.setMaximum(1000);
        mySimulationSpeedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent anEvent) {
                myCurrentSpeedLabel.setText("Current Speed: "
                                            + mySimulationSpeedSlider.getValue());
                setValues();

            }
        });
        detailSliderPanel.add(mySimulationSpeedSlider, BorderLayout.SOUTH);

        JPanel detailInfoPanel = new JPanel();
        contentPanel.add(detailInfoPanel, BorderLayout.SOUTH);

        myCurrentSpeedLabel =
                new JLabel("Current Speed: " + mySimulationSpeedSlider.getValue());
        detailInfoPanel.add(myCurrentSpeedLabel);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent anEvent) {

                setValues();
                dispose();

            }
        });

        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        Dimension dim =
                new Dimension((mySimulationSpeedSlider.getPreferredSize().width * 2),
                              this.getPreferredSize().height);
        setMinimumSize(dim);
        pack();

        setResizable(false);
        setTitle("Detail Options");

        setAlwaysOnTop(true);
        setLocationRelativeTo(null);

    }

    private void setValues() {

        SimParams.setAnimationStepTime((int) mySimulationSpeedSlider.getValue());

        mySimulationFrame.updateNumberLabels();
        mySimulationFrame.updateSpeed();
        mySimulationFrame.updateGUI();
    }

}
