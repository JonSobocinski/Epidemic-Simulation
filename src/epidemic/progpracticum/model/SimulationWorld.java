
package epidemic.progpracticum.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import epidemic.progpracticum.view.SimulationFrame;

public class SimulationWorld {

    private int myNumberOfPeopleToCreate;
    private int myNumberOfBirdsToCreate;
    private int myPerOfBirdsInfected;
    private SimulationFrame mySimulationFrame;
    private List<Point> myPointList;
    private List<AbstractEntity> myEntityList;
    private int myDayCounter;
    private int myHumansInfected;
    private int myBirdsInfected;
    private int myHumansHealthy;
    private int myBirdsHealthy;
    private int myBirdsDead;
    private int myHumansDead;
    private int myDoctorsHealthy;
    private int myDoctorsInfected;
    private int myPoliceHealthy;
    private int babyCounter;

    public SimulationWorld(SimulationFrame aSimulationFrame) {

        myEntityList = new CopyOnWriteArrayList<AbstractEntity>();
        myPointList = new ArrayList<Point>();

        mySimulationFrame = aSimulationFrame;
        myNumberOfPeopleToCreate = 0;
        myNumberOfBirdsToCreate = 0;
        myPerOfBirdsInfected = 0;
        myDayCounter = 1;
        myHumansInfected = 0;
        myBirdsInfected = 0;
        myHumansHealthy = 0;
        myBirdsHealthy = 0;
        myBirdsDead = 0;
        myHumansDead = 0;
        myDoctorsHealthy = 0;
        myDoctorsInfected = 0;
        myPoliceHealthy = 0;
        babyCounter = 0;

        generatePoints();
    }

    public void createHumans() {
        for (int i = 0; i < myNumberOfPeopleToCreate; i++) {
            char gender;
            if (i % 2 == 0) {
                gender = 'M';
            }
            else {
                gender = 'F';
            }
            int index = SimParams.GENERATOR.nextInt(myPointList.size());
            Point point = myPointList.get(index);
            myPointList.remove(index);
            Human human = new Human('H', false, 0, point, gender);
            myHumansHealthy++;
            myEntityList.add(human);

        }

    }

    public void createBirds() {

        for (int i = 0; i < myNumberOfBirdsToCreate; i++) {
            int index = SimParams.GENERATOR.nextInt(myPointList.size());
            Point point = myPointList.get(index);
            myPointList.remove(index);
            Bird bird = new Bird('H', false, 0, point);
            myBirdsHealthy++;
            myEntityList.add(bird);
        }

    }

    public void createDoctors() {
        for (int i = 0; i < SimParams.getNumberOfDoctorsToCreate(); i++) {
            int index = SimParams.GENERATOR.nextInt(myPointList.size());
            Point point = myPointList.get(index);
            myPointList.remove(index);
            Doctor doctor = new Doctor('D', false, 0, point);
            myEntityList.add(doctor);
            myDoctorsHealthy++;
        }
    }

    public void createPolicemen() {

        for (int i = 0; i < SimParams.getNumberOfPoliceToCreate(); i++) {
            int index = SimParams.GENERATOR.nextInt(myPointList.size());
            Point point = myPointList.get(index);
            myPointList.remove(index);
            PoliceOfficer copper = new PoliceOfficer('P', false, 0, point);

            myEntityList.add(copper);
            myPoliceHealthy++;
        }
    }

    public void infectPreDetBirds() {

        if (myPerOfBirdsInfected > 0) {
            int birdsToInfect = (myPerOfBirdsInfected * myNumberOfBirdsToCreate) / 100;
            int birdCounter = 0;
            for (AbstractEntity item : myEntityList) {
                if (birdCounter < birdsToInfect) {
                    if (item.validateEntityIsABird()) {
                        item.setMyIsSick(true);
                        myBirdsInfected++;
                        myBirdsHealthy--;

                        birdCounter++;

                    }
                }

            }
        }

    }

    public void performGameMechs() {
        for (AbstractEntity mainEntity : myEntityList) {

            mainEntity.move();

            for (AbstractEntity secondaryEntity : myEntityList) {

                infect(mainEntity, secondaryEntity);
                healWithDoctors(mainEntity, secondaryEntity);
                policeOfficersAttack(mainEntity, secondaryEntity);
                breed(mainEntity, secondaryEntity);

            }

            killInfected(mainEntity);
        }

    }

    private void breed(AbstractEntity mainEntity, AbstractEntity secondaryEntity) {
        // if (babyCounter < 5000) {
        if (mainEntity.validateTwoEntitiesAreTogether(secondaryEntity)
            && mainEntity.validateHealthyHuman() && secondaryEntity.validateHealthyHuman()
            && mainEntity.validateTwoEntitesOfOppositeSex(secondaryEntity)) {
            Human mainHuman = (Human) mainEntity;
            Human secondHuman = (Human) secondaryEntity;
            if (!mainHuman.getNewBorn() && !secondHuman.getNewBorn()) {

                if (SimParams.getChanceToBreed() >= SimParams.GENERATOR.nextInt(99) + 1) {

                    char gender;
                    if (myPointList.size() % 2 == 0) {
                        gender = 'M';
                    }
                    else {
                        gender = 'F';
                    }

                    Human human =
                            new Human('H', false, 0, mainEntity.getMyLocation(), gender, true);
                    myHumansHealthy++;
                    babyCounter++;

                    myEntityList.add(human);
                    // }
                }
            }
        }
        // else {
        // System.out.println("Too Many Entites");
        // return;
        // }
    }

    private void killInfected(AbstractEntity entityToKill) {

        if (entityToKill.getMyDaysSick() > SimParams.getDaysSickUntilYouDie()
            && entityToKill.validateInfectedEntity()) {
            entityToKill.killEntity();
            checkAndIncWhoWasKilled(entityToKill);
            Point point = entityToKill.getMyLocation();
            myPointList.add(point);
            myEntityList.remove(entityToKill);

        }
    }

    private void policeOfficersAttack(AbstractEntity police, AbstractEntity infected) {

        if (police.validateTwoEntitiesAreTogether(infected)
            && police.validateEntityIsAPoliceOfficer() && infected.validateInfectedEntity()) {

            infected.killEntity();
            checkAndIncWhoWasKilled(infected);
            ((PoliceOfficer) police).incrementKillCount(infected);

        }
    }

    public void reset() {
        myPointList.clear();
        myEntityList.clear();
        generatePoints();
    }

    private void generatePoints() {
        int width = SimParams.PANEL_PIX_WIDTH + 10;
        int height = SimParams.PANEL_PIX_HEIGHT + 10;

        for (int i = 0; i < width; i += SimParams.HUMAN_DIM) {
            for (int j = 0; j < height; j += SimParams.HUMAN_DIM) {
                Point point = new Point(i, j);
                myPointList.add(point);
            }
        }
    }

    public void updateNumberLabels() {
        mySimulationFrame.updateNumberLabels();
    }

    /**
     * @return the myNumberOfPeopleToCreate
     */
    public int getMyNumberOfPeopleToCreate() {
        return myNumberOfPeopleToCreate;
    }

    /**
     * @param myNumberOfPeopleToCreate the myNumberOfPeopleToCreate to set
     */
    public void setMyNumberOfPeopleToCreate(int myNumberOfPeopleToCreate) {
        this.myNumberOfPeopleToCreate = myNumberOfPeopleToCreate;
    }

    /**
     * @return the myNumberOfBirdsToCreate
     */
    public int getMyNumberOfBirdsToCreate() {
        return myNumberOfBirdsToCreate;
    }

    /**
     * @param myNumberOfBirdsToCreate the myNumberOfBirdsToCreate to set
     */
    public void setMyNumberOfBirdsToCreate(int myNumberOfBirdsToCreate) {
        this.myNumberOfBirdsToCreate = myNumberOfBirdsToCreate;
    }

    /**
     * @return the myPerOfBirdsInfected
     */
    public int getMyPerOfBirdsInfected() {
        return myPerOfBirdsInfected;
    }

    /**
     * @param myPerOfBirdsInfected the myPerOfBirdsInfected to set
     */
    public void setMyPerOfBirdsInfected(int myPerOfBirdsInfected) {
        this.myPerOfBirdsInfected = myPerOfBirdsInfected;
    }

    private void infect(AbstractEntity entityToInfect, AbstractEntity entityWithInfection) {

        if (entityToInfect.validateTwoEntitiesAreTogether(entityToInfect, entityWithInfection)
            && entityWithInfection.validateInfectedEntity()
            && entityToInfect.validateHealthyEntity())
            if (SimParams.getChanceOfInection() >= SimParams.GENERATOR.nextInt(99) + 1) {
                entityToInfect.setMyIsSick(true);
                checkWhoWasInfected(entityToInfect);

            }

    }

    /**
     * @return the myEntityList
     */
    public List<AbstractEntity> getMyEntityList() {
        return myEntityList;
    }

    /**
     * @param myEntityList the myEntityList to set
     */
    public void setMyEntityList(List<AbstractEntity> myEntityList) {
        this.myEntityList = myEntityList;
    }

    /**
     * @return the myDayCounter
     */
    public int getMyDayCounter() {
        return myDayCounter;
    }

    /**
     * @param myDayCounter the myDayCounter to set
     */
    public void incrementMyDayCounter() {
        myDayCounter++;
    }

    private void resetDayCount() {
        myDayCounter = 0;
    }

    /**
     * @return the myHumansInfected
     */
    public int getMyHumansInfected() {
        return myHumansInfected;
    }

    private void resetMyHumanInfectedCount() {
        myHumansInfected = 0;
    }

    /**
     * @return the myBirdsInfected
     */
    public int getMyBirdsInfected() {
        return myBirdsInfected;
    }

    private void resetMyBirdsInfected() {
        myBirdsInfected = 0;
    }

    /**
     * @return the myHumansHealthy
     */
    public int getMyHumansHealthy() {
        return myHumansHealthy;
    }

    private void resetHealthHumanCounter() {
        myHumansHealthy = 0;
    }

    /**
     * @return the myBirdsHealty
     */
    public int getMyBirdsHealty() {
        return myBirdsHealthy;
    }

    private void resetHealthyBirdCount() {
        myBirdsHealthy = 0;
    }

    private void resetHumanDeathCount() {
        myHumansDead = 0;
    }

    private void resetBirdDeathCount() {
        myBirdsDead = 0;
    }

    public void resetStats() {

        resetDayCount();

        resetMyHumanInfectedCount();
        resetHealthHumanCounter();

        resetHealthyBirdCount();
        resetMyBirdsInfected();
        resetHumanDeathCount();
        resetBirdDeathCount();
    }

    public int getHumanDeathTotal() {
        return myHumansDead;
    }

    public int getBirdDeathTotal() {
        return myBirdsDead;
    }

    private void checkWhoWasInfected(AbstractEntity anEntity) {
        if (anEntity.toString().equalsIgnoreCase("HUMAN")) {
            myHumansInfected++;
            myHumansHealthy--;

        }
        else if (anEntity.toString().equalsIgnoreCase("BIRD")) {
            myBirdsInfected++;
            myBirdsHealthy--;

        }
        else {
            myDoctorsInfected++;
            myDoctorsHealthy--;
        }

    }

    private void checkWhoWasHealed(AbstractEntity anEntity) {
        if (anEntity.toString().equalsIgnoreCase("HUMAN")) {
            myHumansInfected--;
            myHumansHealthy++;

        }
        else if (anEntity.toString().equalsIgnoreCase("BIRD")) {
            myBirdsInfected--;
            myBirdsHealthy++;

        }
        else {
            myDoctorsInfected--;
            myDoctorsHealthy++;
        }

    }

    public void checkAndIncWhoWasKilled(AbstractEntity anEntity) {
        if (anEntity.toString().equalsIgnoreCase("HUMAN")) {

            if (anEntity.isSick()) {
                myHumansInfected--;
                myHumansDead++;
            }
            else {
                myHumansDead++;
                myHumansHealthy--;
            }

        }
        else if (anEntity.toString().equalsIgnoreCase("BIRD")) {
            if (anEntity.isSick()) {
                myBirdsInfected--;
                myBirdsDead++;
            }
            else {
                myBirdsDead++;
                myBirdsHealthy--;
            }

        }
        else {

            myDoctorsInfected++;
            myDoctorsHealthy--;
        }

    }

    public void healWithDoctors(AbstractEntity doctor, AbstractEntity patient) {

        if (doctor.validateTwoEntitiesAreTogether(patient) && doctor.validateEntityIsADoctor()
            && patient.validateInfectedEntity() && !patient.equals(doctor)) {

            if (SimParams.getChanceToBeHealed() >= SimParams.GENERATOR.nextInt(100) + 1) {
                patient.healEntity();
                checkWhoWasHealed(patient);

            }
        }

    }

    /**
     * @return the myDoctorsHealth
     */
    public int getMyDoctorsHealth() {
        return myDoctorsHealthy;
    }

    /**
     * @return the myDoctorsInfected
     */
    public int getMyDoctorsInfected() {
        return myDoctorsInfected;
    }

}
