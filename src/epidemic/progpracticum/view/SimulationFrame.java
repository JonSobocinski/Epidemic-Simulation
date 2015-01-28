
package epidemic.progpracticum.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import epidemic.progpracticum.model.AbstractEntity;
import epidemic.progpracticum.model.SimParams;
import epidemic.progpracticum.model.SimulationWorld;

public class SimulationFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -6095552250725517182L;
    private JPanel myMainGUI;
    private JPanel mySetupPanel;
    private JPanel myLabelPanel;
    private JPanel mySliderPanel;
    private JPanel myStatPanel;
    private JPanel myStatHolderSubPanel;
    private JPanel myStartButtonsPanel;
    private JPanel myInformationConsole;
    private JPanel myButtonAndInformationPanel;

    private WorldPanel mySimulationPanel;

    private SimulationWorld myWorldSimulatorData;

    private SliderListener mySliderListener;

    private StartButtonListener myStartButtonsListener;

    private Timer myStartTimer;

    private MouseDoodleAdapter adapter;

    private InformationPanel myInformationSubConsole;

    private WorldSubOption options;
    private WorldDetailOptions myWorldDetailOptions;

    private JCheckBoxMenuItem myGridCheckBox;
    private JCheckBoxMenuItem myLoopCheckBox;
    private JCheckBoxMenuItem myAutoSaveStats;

    private JSlider myPeopleNumberSlider;
    private JSlider myBirdNumberSlider;
    private JSlider myPerInfectedBirds;

    private JLabel myNumberOfPeopleLabel;
    private JLabel myNumberOfBirdsLabel;
    private JLabel myPercentOfInfectedBirdsLabel;
    private JLabel humanInfectedLabel;
    private JLabel birdInfectedLabel;
    private JLabel humanHealthyLabel;
    private JLabel birdHealthLabel;
    private JLabel humanDeathCount;
    private JLabel birdDeathCount;
    private JLabel numberOfDaysLabel;

    private JLabel chanceOfInfectionLabel;
    private JLabel chanceOfHealingLabel;
    private JLabel numberOfDoctorsCreatedLabel;
    private JLabel daysUntilYouDieLabel;
    private JLabel numberPoliceCreated;
    private JLabel animationSpeed;

    private JMenuBar myMenuBar;

    private JButton myStartButton;
    private JButton myStopButton;
    private JButton myStepButton;
    private JButton myResetButton;

    private boolean loopSimulation;
    private boolean autoSaveStats;

    public SimulationFrame() {

        myWorldSimulatorData = new SimulationWorld(this);
        mySliderListener = new SliderListener(myWorldSimulatorData);
        mySimulationPanel = new WorldPanel(myWorldSimulatorData);
        myStartButtonsListener = new StartButtonListener();

        mySimulationPanel.setMaximumSize(mySimulationPanel.getSize());
        mySimulationPanel.setMinimumSize(mySimulationPanel.getSize());
        mySimulationPanel.setPreferredSize(mySimulationPanel.getSize());

        myMenuBar = new JMenuBar();
        loopSimulation = false;

        myInformationSubConsole = new InformationPanel();

        initMainPanel();
        setUpPanel();
        initLabelsAndSliders();
        initStartPanel();
        initStatPanel();

        Dimension dim =
                new Dimension(myStatPanel.getPreferredSize().width
                              + daysUntilYouDieLabel.getWidth() + 5,
                              SimParams.PANEL_PIX_HEIGHT);
        myStatPanel.setMaximumSize(dim);
        myStatPanel.setMinimumSize(dim);
        myStatPanel.setPreferredSize(dim);

        myMainGUI.add(mySimulationPanel, BorderLayout.CENTER);
        mySimulationPanel.addMouseListener(adapter);

        setMenuBar();

        this.setJMenuBar(myMenuBar);
        options = new WorldSubOption(this);
        myWorldDetailOptions = new WorldDetailOptions(this);

        pack();
    }

    private void setMenuBar() {
        JMenu file = new JMenu("File");
        JMenu options = new JMenu("Options");

        myGridCheckBox = new JCheckBoxMenuItem("Enable Grid");
        myGridCheckBox.setToolTipText("Shows An On Screen Grid");

        myLoopCheckBox = new JCheckBoxMenuItem("Enable Loop");
        myLoopCheckBox
                .setToolTipText("Once A Simulation Finishes, The Program Will Randomzize The World And Run The Simulation Again");

        myAutoSaveStats = new JCheckBoxMenuItem("Enable Stat Auto-Save");

        myGridCheckBox.setSelected(false);
        myLoopCheckBox.setSelected(false);
        myAutoSaveStats.setSelected(false);

        myMenuBar.add(file);
        myMenuBar.add(options);

        JMenuItem worldOptions = new JMenuItem("World Options");
        JMenuItem detailOptions = new JMenuItem("Detail Options");
        JMenuItem saveStats = new JMenuItem("Save Stats");

        file.add(saveStats);
        saveStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent anEvent) {
                try {
                    StatWriter statWriter =
                            new StatWriter(myWorldSimulatorData, autoSaveStats);
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        options.add(worldOptions);
        options.add(detailOptions);
        options.addSeparator();
        options.add(myGridCheckBox);
        options.add(myLoopCheckBox);
        options.add(myAutoSaveStats);

        myGridCheckBox.addActionListener(new GridListener(mySimulationPanel));
        myLoopCheckBox.addActionListener(new LoopActionListener(this));

        worldOptions.addActionListener(new OptionsListener());
        detailOptions.addActionListener(new WorldDetailListener());
        myAutoSaveStats.addActionListener(new StatAutoSaveListener(this));

    }

    private void initStatPanel() {
        Font boldFont = (new Font("Tahoma", Font.BOLD, 11));

        myStatPanel = new JPanel();
        myMainGUI.add(myStatPanel, BorderLayout.EAST);
        myStatPanel.setLayout(new BorderLayout(0, 0));

        myStatHolderSubPanel = new JPanel();
        myStatPanel.add(myStatHolderSubPanel);
        myStatHolderSubPanel.setLayout(new GridLayout(24, 0));

        JButton generateWorld = new JButton("Generate World");
        myStatHolderSubPanel.add(generateWorld);
        generateWorld.addActionListener(new GenerateWorldListener());

        JButton randomizeWorld = new JButton("Randomize World");
        myStatHolderSubPanel.add(randomizeWorld);
        randomizeWorld.addActionListener(new RandomizeWorldListener());

        JSeparator separator = new JSeparator();
        myStatHolderSubPanel.add(separator);

        JLabel envorimentCountsLabel = new JLabel("Enviroment Counts:");
        myStatHolderSubPanel.add(envorimentCountsLabel);

        JSeparator separator_1 = new JSeparator();
        myStatHolderSubPanel.add(separator_1);

        JLabel humanCountLabel = new JLabel("Human Count:");
        myStatHolderSubPanel.add(humanCountLabel);

        humanInfectedLabel = new JLabel("Infected: 0");
        humanInfectedLabel.setFont(boldFont);
        myStatHolderSubPanel.add(humanInfectedLabel);

        humanHealthyLabel = new JLabel("Healthy: 0");
        humanHealthyLabel.setFont(boldFont);
        myStatHolderSubPanel.add(humanHealthyLabel);

        humanDeathCount = new JLabel("Dead: 0");
        humanDeathCount.setFont(boldFont);
        myStatHolderSubPanel.add(humanDeathCount);

        JSeparator separator_2 = new JSeparator();
        myStatHolderSubPanel.add(separator_2);

        JLabel birdCountLabel = new JLabel("Bird Counts: ");
        myStatHolderSubPanel.add(birdCountLabel);

        birdInfectedLabel = new JLabel("Infected: 0");
        birdInfectedLabel.setFont(boldFont);
        myStatHolderSubPanel.add(birdInfectedLabel);

        birdHealthLabel = new JLabel("Healthy: 0");
        birdHealthLabel.setFont(boldFont);
        myStatHolderSubPanel.add(birdHealthLabel);

        birdDeathCount = new JLabel("Dead: 0");
        birdDeathCount.setFont(boldFont);
        myStatHolderSubPanel.add(birdDeathCount);

        JSeparator separator_3 = new JSeparator();
        myStatHolderSubPanel.add(separator_3);

        JLabel worldOptions = new JLabel("World Options:");
        myStatHolderSubPanel.add(worldOptions);
        numberOfDoctorsCreatedLabel =
                new JLabel("Doctors Created: " + SimParams.getNumberOfDoctorsToCreate());
        chanceOfInfectionLabel =
                new JLabel("Chance Of Infection: " + SimParams.getChanceOfInection() + "%");
        chanceOfHealingLabel =
                new JLabel("Chance To Be Healed: " + SimParams.getChanceToBeHealed() + "%");
        daysUntilYouDieLabel =
                new JLabel("Days Sick Until You Die: " + SimParams.getDaysSickUntilYouDie());
        numberPoliceCreated =
                new JLabel("Police Created: " + SimParams.getNumberOfPoliceToCreate());
        animationSpeed =
                new JLabel("Animation Pause Time: " + SimParams.getAnimationStepTime());

        numberOfDoctorsCreatedLabel.setFont(boldFont);
        numberPoliceCreated.setFont(boldFont);
        chanceOfInfectionLabel.setFont(boldFont);
        chanceOfHealingLabel.setFont(boldFont);
        daysUntilYouDieLabel.setFont(boldFont);
        animationSpeed.setFont(boldFont);

        myStatHolderSubPanel.add(numberOfDoctorsCreatedLabel);
        myStatHolderSubPanel.add(numberPoliceCreated);
        myStatHolderSubPanel.add(chanceOfInfectionLabel);
        myStatHolderSubPanel.add(chanceOfHealingLabel);
        myStatHolderSubPanel.add(daysUntilYouDieLabel);
        myStatHolderSubPanel.add(animationSpeed);

        JSeparator separator_4 = new JSeparator();
        myStatHolderSubPanel.add(separator_4);

        numberOfDaysLabel = new JLabel("Days: 0");
        myStatHolderSubPanel.add(numberOfDaysLabel);

    }

    private void initStartPanel() {

        myStartTimer = new Timer(SimParams.getAnimationStepTime(), new startTimerListener());

        myStartButtonsPanel = new JPanel();
        myInformationConsole = new JPanel();
        myButtonAndInformationPanel = new JPanel();

        myButtonAndInformationPanel.setLayout(new GridLayout(2, 1));

        myMainGUI.add(myButtonAndInformationPanel, BorderLayout.SOUTH);

        myStartButtonsPanel.setLayout(new GridLayout(1, 4));
        myInformationConsole.setLayout(new GridLayout(1, 1));

        myButtonAndInformationPanel.setSize(1000, 200);

        myButtonAndInformationPanel.add(myStartButtonsPanel);
        myButtonAndInformationPanel.add(myInformationConsole);
        myInformationConsole.add(myInformationSubConsole);

        myStartButton = new JButton("Start");
        myStartButton.setName("START");
        myStartButtonsPanel.add(myStartButton);
        myStartButton.addActionListener(myStartButtonsListener);

        myStopButton = new JButton("Stop");
        myStopButton.setName("STOP");
        myStopButton.addActionListener(myStartButtonsListener);
        myStartButtonsPanel.add(myStopButton);

        myStepButton = new JButton("Step");
        myStepButton.setName("STEP");
        myStepButton.addActionListener(myStartButtonsListener);
        myStartButtonsPanel.add(myStepButton);

        myResetButton = new JButton("Reset");
        myResetButton.setName("RESET");
        myResetButton.addActionListener(myStartButtonsListener);
        myStartButtonsPanel.add(myResetButton);

    }

    private void initLabelsAndSliders() {

        myNumberOfPeopleLabel =
                new JLabel("Number Of People: "
                           + myWorldSimulatorData.getMyNumberOfPeopleToCreate());
        myNumberOfPeopleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLabelPanel.add(myNumberOfPeopleLabel);

        myNumberOfBirdsLabel =
                new JLabel("Number Of Birds: "
                           + myWorldSimulatorData.getMyNumberOfBirdsToCreate());
        myNumberOfBirdsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLabelPanel.add(myNumberOfBirdsLabel);

        myPercentOfInfectedBirdsLabel =
                new JLabel("Perctange Of Infected Birds: "
                           + myWorldSimulatorData.getMyPerOfBirdsInfected() + "%");
        myPercentOfInfectedBirdsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        myLabelPanel.add(myPercentOfInfectedBirdsLabel);

        myPeopleNumberSlider = new JSlider();
        myPeopleNumberSlider.setValue(0);
        myPeopleNumberSlider.setMajorTickSpacing(100);
        myPeopleNumberSlider.setPaintTicks(true);
        myPeopleNumberSlider.setPaintLabels(true);
        myPeopleNumberSlider.setMinorTickSpacing(50);
        myPeopleNumberSlider.setMaximum(SimParams.MAX_HUMAN_NUM);
        myPeopleNumberSlider.setName("PEOPLE");
        mySliderPanel.add(myPeopleNumberSlider);

        myBirdNumberSlider = new JSlider();
        myBirdNumberSlider.setValue(0);
        myBirdNumberSlider.setPaintTicks(true);
        myBirdNumberSlider.setPaintLabels(true);
        myBirdNumberSlider.setMinorTickSpacing(50);
        myBirdNumberSlider.setMaximum(SimParams.MAX_BIRD_NUM);
        myBirdNumberSlider.setMajorTickSpacing(100);
        myBirdNumberSlider.setName("BIRD");
        mySliderPanel.add(myBirdNumberSlider);

        myPerInfectedBirds = new JSlider();
        myPerInfectedBirds.setValue(0);
        myPerInfectedBirds.setPaintLabels(true);
        myPerInfectedBirds.setPaintTicks(true);
        myPerInfectedBirds.setMinorTickSpacing(5);
        myPerInfectedBirds.setMajorTickSpacing(20);
        myPerInfectedBirds.setMaximum(100);
        myPerInfectedBirds.setName("PER");
        mySliderPanel.add(myPerInfectedBirds);

        myPeopleNumberSlider.addChangeListener(mySliderListener);
        myBirdNumberSlider.addChangeListener(mySliderListener);
        myPerInfectedBirds.addChangeListener(mySliderListener);

    }

    private void initMainPanel() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myMainGUI = new JPanel();
        setContentPane(myMainGUI);
        myMainGUI.setLayout(new BorderLayout());
        adapter = new MouseDoodleAdapter();

    }

    private void setUpPanel() {
        mySetupPanel = new JPanel();
        myMainGUI.add(mySetupPanel, BorderLayout.NORTH);
        mySetupPanel.setLayout(new BorderLayout());

        myLabelPanel = new JPanel();
        mySetupPanel.add(myLabelPanel, BorderLayout.NORTH);
        myLabelPanel.setLayout(new GridLayout(0, 3));

        mySliderPanel = new JPanel();
        mySetupPanel.add(mySliderPanel, BorderLayout.SOUTH);
        mySliderPanel.setLayout(new GridLayout(0, 3));

    }

    public void updateGUI() {
        mySimulationPanel.updateUI();
    }

    public void updateNumberLabels() {
        myPercentOfInfectedBirdsLabel.setText("Perctange Of Infected Birds: "
                                              + myWorldSimulatorData.getMyPerOfBirdsInfected()
                                              + "%");
        myNumberOfBirdsLabel.setText("Number Of Birds: "
                                     + myWorldSimulatorData.getMyNumberOfBirdsToCreate());
        myNumberOfPeopleLabel.setText("Number Of People: "
                                      + myWorldSimulatorData.getMyNumberOfPeopleToCreate());

        updateStats();

    }

    private void step() throws IOException {
        if (myWorldSimulatorData.getMyBirdsHealty() == 0
            && myWorldSimulatorData.getMyHumansHealthy() == 0
            && myWorldSimulatorData.getMyDoctorsHealth() == 0) {
            checkIfTimerExistsAndStop();

            if (autoSaveStats) {
                StatWriter statWriter = new StatWriter(myWorldSimulatorData, autoSaveStats);

            }
            if (loopSimulation) {
                reRandomizeGenerateAndStartSimulation();
            }

        }

        if (myWorldSimulatorData.getMyBirdsInfected() == 0
            && myWorldSimulatorData.getMyHumansInfected() == 0) {

            checkIfTimerExistsAndStop();

            if (autoSaveStats) {
                StatWriter statWriter = new StatWriter(myWorldSimulatorData, autoSaveStats);

            }
            if (loopSimulation) {
                reRandomizeGenerateAndStartSimulation();
            }
        }

        // if (myWorldSimulatorData.getMyDoctorsInfected() <= 0) {
        // checkIfTimerExistsAndStop();
        // }

        else {
           
            myWorldSimulatorData.performGameMechs();
            myWorldSimulatorData.incrementMyDayCounter();
            updateStats();
            mySimulationPanel.updateUI();
        }
    }

    private void reRandomizeGenerateAndStartSimulation() {
        randomizeWorld();
        options.randomizeOptions();
        createWorld();
        myStartTimer.start();

    }

    private void checkIfTimerExistsAndStop() {
        if (myStartTimer == null) {
            return;
        }
        else {
            myStartTimer.stop();
            return;
        }
    }

    private void updateStats() {
        numberOfDaysLabel.setText("Days: " + myWorldSimulatorData.getMyDayCounter());
        humanInfectedLabel.setText("Infected: " + myWorldSimulatorData.getMyHumansInfected());
        birdInfectedLabel.setText("Infected: " + myWorldSimulatorData.getMyBirdsInfected());
        birdHealthLabel.setText("Healthy: " + myWorldSimulatorData.getMyBirdsHealty());
        humanHealthyLabel.setText("Healthy: " + myWorldSimulatorData.getMyHumansHealthy());
        humanDeathCount.setText("Dead: " + myWorldSimulatorData.getHumanDeathTotal());
        birdDeathCount.setText("Dead: " + myWorldSimulatorData.getBirdDeathTotal());

        numberOfDoctorsCreatedLabel.setText("Doctors Created: "
                                            + SimParams.getNumberOfDoctorsToCreate());
        numberPoliceCreated
                .setText("Police Created: " + SimParams.getNumberOfPoliceToCreate());
        chanceOfInfectionLabel.setText("Chance Of Infection: "
                                       + SimParams.getChanceOfInection() + "%");
        chanceOfHealingLabel.setText("Chance To Be Healed: " + SimParams.getChanceToBeHealed()
                                     + "%");
        daysUntilYouDieLabel.setText("Days Sick Until You Die: "
                                     + SimParams.getDaysSickUntilYouDie());
        animationSpeed.setText("Animation Pause Time: " + SimParams.getAnimationStepTime());
        ;

    }

    private void randomizeWorld() {
        myPeopleNumberSlider
                .setValue(SimParams.GENERATOR.nextInt(SimParams.MAX_HUMAN_NUM) + 1);
        myBirdNumberSlider.setValue(SimParams.GENERATOR.nextInt(SimParams.MAX_BIRD_NUM) + 1);
        myPerInfectedBirds.setValue(SimParams.GENERATOR.nextInt(100) + 1);

    }

    private void createWorld() {
        if (myWorldSimulatorData.getMyNumberOfPeopleToCreate() == 0
            && myWorldSimulatorData.getMyNumberOfBirdsToCreate() == 0) {
            return;
        }

        if (myStartTimer != null) {
            myStartTimer.stop();
        }

        mySimulationPanel.reset();

        myWorldSimulatorData.createHumans();
        myWorldSimulatorData.createBirds();
        myWorldSimulatorData.infectPreDetBirds();
        myWorldSimulatorData.createDoctors();
        myWorldSimulatorData.createPolicemen();

        updateStats();
        mySimulationPanel.updateUI();
    }

    private class MouseDoodleAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent anEvent) {

            String information = "";
            String genderInfo = "";
            boolean foundAnEntity = false;

            Point gp = anEvent.getPoint();
            int moddedX = gp.x - (gp.x % 10);
            int moddedY = gp.y - (gp.y % 10);
            gp = new Point(moddedX, moddedY);

            for (AbstractEntity item : myWorldSimulatorData.getMyEntityList()) {
                if (item.getMyLocation().equals(gp) && !item.isDead()) {

                    if (SwingUtilities.isLeftMouseButton(anEvent)) {
                        item.killEntity();
                        myWorldSimulatorData.checkAndIncWhoWasKilled(item);
                        updateStats();
                        mySimulationPanel.updateUI();

                        if (item.getMyGender() == 'F') {
                            genderInfo = " female";
                        }
                        else if (item.getMyGender() == 'M') {
                            genderInfo = " male";
                        }
                        information =
                                " You killed the " + item.toString().toLowerCase()
                                        + genderInfo + "!";
                        foundAnEntity = true;
                    }
                    else {
                        information = information + " " + item.itemsInformation(item);
                        foundAnEntity = true;
                    }
                }
            }
            if (foundAnEntity) {
                myInformationSubConsole.setText(information);
            }
            else {
                myInformationSubConsole.setText("X: " + moddedX + " , Y: " + moddedY);
            }

        }
    }

    private class GenerateWorldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent anEvent) {
            createWorld();

        }

    }

    private class RandomizeWorldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent anEvent) {

            randomizeWorld();

            options.randomizeOptions();

            createWorld();

            updateStats();

            mySimulationPanel.updateUI();

        }

    }

    private class StartButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent anEvent) {
            JButton source = (JButton) anEvent.getSource();

            if (source.getName().equalsIgnoreCase("STEP")) {

                if (myWorldSimulatorData.getMyEntityList().isEmpty()) {
                    System.out.println("Step");
                    return;
                }

                try {
                    step();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (source.getName().equalsIgnoreCase("RESET")) {
                if (myWorldSimulatorData.getMyEntityList().isEmpty()) {
                    return;
                }

                reset();
            }
            if (source.getName().equalsIgnoreCase("START")) {
                if (myWorldSimulatorData.getMyEntityList().isEmpty()) {
                    return;
                }

                myStartTimer.start();

            }
            if (source.getName().equalsIgnoreCase("STOP")) {

                if (myStartTimer == null) {
                    return;
                }
                else {
                    myStartTimer.stop();
                    updateStats();
                    mySimulationPanel.updateUI();

                }

            }

        }

        private void reset() {
            myPeopleNumberSlider.setValue(0);
            myBirdNumberSlider.setValue(0);
            myPerInfectedBirds.setValue(0);
            myWorldSimulatorData.resetStats();
            updateStats();
            mySimulationPanel.reset();
            myInformationSubConsole.resetText();

        }

    }

    private class startTimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent anEvent) {
            try {
                step();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private class OptionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent anEvent) {

            options.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            options.setVisible(true);

        }

    }

    private class WorldDetailListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            myWorldDetailOptions.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            myWorldDetailOptions.setVisible(true);
        }

    }

    public void updateSpeed() {
        myStartTimer.setDelay(SimParams.getAnimationStepTime());

    }

    /**
     * @param loopSimulation the loopSimulation to set
     */
    public void setLoopSimulation(boolean loopSimulation) {
        this.loopSimulation = loopSimulation;
    }

    /**
     * @param autoSaveStats the autoSaveStats to set
     */
    public void setAutoSaveStats(boolean aAutoSaveStats) {
        autoSaveStats = aAutoSaveStats;

    }
}
