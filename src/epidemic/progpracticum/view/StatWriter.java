
package epidemic.progpracticum.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import epidemic.progpracticum.model.SimParams;
import epidemic.progpracticum.model.SimulationWorld;

public class StatWriter {

    private SimulationWorld mySimulationWorld;
    private FileWriter myFileWriter;
    private File myFile;
    private JFileChooser mySavePath;
    private boolean autoSaveStats;

    public StatWriter(SimulationWorld aSimulationWorld, boolean aDecisionToAutoSaveStats)
            throws IOException {

        autoSaveStats = aDecisionToAutoSaveStats;
        mySimulationWorld = aSimulationWorld;

        if (autoSaveStats) {
            autoWriteStats();
            myFileWriter.close();
        }
        else {
            selectSaveLocationForStats();
            myFileWriter.close();
        }

    }

    private void selectSaveLocationForStats() throws IOException {

        mySavePath = new JFileChooser();
        mySavePath.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        mySavePath.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));

        int returnVal = mySavePath.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String pathName = mySavePath.getSelectedFile().getPath() + ".csv";
            myFile = new File(pathName);

            if (myFile.isFile()) {
                myFile = new File(pathName);
                myFileWriter = new FileWriter(myFile, true);
                writeStats();
            }
            else {
                myFileWriter = new FileWriter(myFile, true);
                writeHeader();
                writeStats();

            }
        }
        else {
            return;
        }

    }

    private void autoWriteStats() throws IOException {

        myFile = new File(System.getProperty("user.home") + "/Desktop/SimulationStats.csv");

        if (checkIfFileExists()) {
            myFileWriter = new FileWriter(myFile, true);
            writeStats();

        }
        else {
            myFileWriter = new FileWriter(myFile, true);
            writeHeader();
            writeStats();

        }

    }

    private boolean checkIfFileExists() {
        if (myFile.isFile()) {
            return true;
        }
        else {
            return false;
        }

    }

    private void writeHeader() throws IOException {

        myFileWriter.append("Days,");
        myFileWriter.append("Starting Humans,");
        myFileWriter.append("Starting Birds,");
        myFileWriter.append("Starting Infected Birds,");
        myFileWriter.append("Starting Doctors,");
        myFileWriter.append("Starting Police,");
        myFileWriter.append("Chance Of Infection,");
        myFileWriter.append("Chance Of Healing,");
        myFileWriter.append("Days Sick Until You Die\n");

    }

    private void writeStats() throws IOException {

        myFileWriter.append("" + mySimulationWorld.getMyDayCounter());
        myFileWriter.append("," + mySimulationWorld.getMyNumberOfPeopleToCreate());
        myFileWriter.append("," + mySimulationWorld.getMyNumberOfBirdsToCreate());
        myFileWriter.append(","
                            + (mySimulationWorld.getMyPerOfBirdsInfected() * mySimulationWorld
                                    .getMyNumberOfBirdsToCreate()) / 100);
        myFileWriter.append("," + SimParams.getNumberOfDoctorsToCreate());
        myFileWriter.append("," + SimParams.getNumberOfPoliceToCreate());

        myFileWriter.append("," + SimParams.getChanceOfInection());
        myFileWriter.append("," + SimParams.getChanceToBeHealed());
        myFileWriter.append("," + SimParams.getDaysSickUntilYouDie());
        myFileWriter.append("\n");

    }
}
