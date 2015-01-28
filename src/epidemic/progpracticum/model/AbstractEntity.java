/**
 * Your name. Final project Autumn 2013
 */

package epidemic.progpracticum.model;

import java.awt.Point;

/**
 * Abstract class representing simulation object.
 * 
 * @author Monika
 * @version Autumn 2013
 */
public abstract class AbstractEntity implements Comparable<AbstractEntity> {

    /**
     * object's location in the world.
     */
    private Point myLocation;

    /**
     * object's state: whether sick or healthy.
     */
    private boolean myIsSick;

    /**
     * object's species.
     */
    private char mySpecies;

    /**
     * number of days the object has been sick.
     */
    private int myDaysSick;

    private boolean isDead;

    private boolean isImmune;

    private char myGender;

    /**
     * Parameterized constructor that sets object's state to parameters passed.
     * 
     * @param aSpecies is a character denoting the species
     * @param aSick is a boolean value denoting whether the object is sick or
     *            not
     * @param aDayNum >= 0, the number of days the object has been sick
     * @param aLocation is the location of the object in the world, 0 <= x <
     *            world width, 0 <= y < world height
     * @custom.post object's state set to parameters passed
     */
    public AbstractEntity(final char aSpecies, final boolean aSick, final int aDayNum,
                          final Point aLocation, final char aGender) {
        mySpecies = aSpecies;
        myIsSick = aSick;
        myDaysSick = aDayNum;
        myLocation = aLocation;
        isDead = false;
        isImmune = false;
        myDaysSick = 0;
        myGender = aGender;
    }

    /**
     * Shows whether the object is sick or healthy.
     * 
     * @return is true if object is sick, false otherwise
     */
    public boolean isSick() {
        return myIsSick;
    }

    /**
     * Changes the state of object's health to sick or healthy.
     * 
     * @param aSick is a boolean value indicating whether an object should be
     *            sick or not
     * @custom.post the object is set to true if aSick is true, false otherwise
     */
    public void setMyIsSick(boolean aSick) {
        myIsSick = aSick;
    }

    /**
     * Gets objects's species.
     * 
     * @return is a character indicating object's species
     */
    public char getMySpecies() {
        return mySpecies;
    }

    /**
     * Gets objects's location in the world.
     * 
     * @return is a Point indicating object's location in the world
     */
    public Point getMyLocation() {
        return myLocation;
    }

    /**
     * Changes the object's location in the world.
     * 
     * @param aLocation is the location in the world, 0 <= x < world width, 0 <=
     *            y < world height
     * @custom.post the object's location is set to aLocation
     */
    public void setMyLocation(Point aLocation) {
        myLocation = aLocation;
    }

    /**
     * Gets the number of days the object has been sick.
     * 
     * @return is an integer indicating the number of days the object has been
     *         sick
     */
    public int getMyDaysSick() {
        return myDaysSick;
    }

    public void incementDaysSick() {
        myDaysSick++;
    }

    /**
     * Changes the number of days the object has been sick.
     * 
     * @param aDaysSick >=0 is the new number of days
     * @custom.post the length of object's illness is set to aDaysSick
     */
    public void setMyDaysSick(int aDaysSick) {
        myDaysSick = aDaysSick;
    }

    /**
     * Abstract method - moves object's location in the world according to
     * object's species movement rules.
     */
    public abstract void move();

    /**
     * Compares two world objects based on their health status.
     * 
     * @param anOther != null
     * @return is 1 if self healthy while other sick, 0 if both equal, -1 if
     *         self sick while other healthy
     */
    @Override
    public int compareTo(AbstractEntity anOther) {
        if (anOther == null) {
            System.out.println("OBJECT CAN'T BE NULL");
            throw new IllegalArgumentException();
        }
        else if (this.isSick() == false && anOther.isSick() == true) {
            return 1;
        }
        else if (this.isSick() == true && anOther.isSick() == true) {
            return 0;
        }
        else if (this.isSick() == false && anOther.isSick() == false) {
            return 0;
        }
        else {
            return -1;
        }

    }

    public boolean isDead() {
        return isDead;
    }

    public void killEntity() {
        isDead = true;
    }

    public void healEntity() {
        myIsSick = false;
        // myDaysSick = 0;
        isDead = false;
        isImmune = true;

    }

    public boolean isImmune() {
        return isImmune;

    }

    public String itemsInformation(AbstractEntity entity) {
        String text = "";
        String genderInfo = "";
        String location =
                " Their current location is: X = " + entity.getMyLocation().x + " Y= "
                        + entity.getMyLocation().y + ".";

        if (entity.getMyGender() == 'F') {
            genderInfo = " female";
        }
        else if (entity.getMyGender() == 'M') {
            genderInfo = " male";
        }

        if (entity.myIsSick && !entity.isDead) {
            text =
                    ("This " + entity.toString().toLowerCase() + genderInfo
                     + " has been sick for " + entity.getMyDaysSick()
                     + " days. They will die in "
                     + (SimParams.getDaysSickUntilYouDie() - entity.getMyDaysSick())
                     + " days." + location);
        }
        else if (!entity.myIsSick && !entity.isDead) {
            String isInmuneText;
            if (entity.isImmune) {
                isInmuneText =
                        " They are immune from the infection. They will not die. They were sick "
                                + entity.getMyDaysSick() + " days.";
            }
            else {
                isInmuneText = " They are not yet immune from the infection.";
            }
            text =
                    ("This " + entity.toString().toLowerCase() + genderInfo + " is not sick."
                     + isInmuneText + location);
        }

        return text;
    }

    /**
     * @return the myGender
     */
    public char getMyGender() {
        return myGender;
    }

    public void makeImmune() {
        isImmune = true;
    }

    public boolean validateHealthyHuman(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && !anEntity.myIsSick
            && "Human".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyHuman() {

        if (!this.isDead && !this.isImmune && !this.myIsSick
            && "Human".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyBird(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && !anEntity.myIsSick
            && "Bird".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyBird() {

        if (!this.isDead && !this.isImmune && !this.myIsSick
            && "Bird".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyDoctor(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && !anEntity.myIsSick
            && "Doctor".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyDoctor() {

        if (!this.isDead && !this.isImmune && !this.myIsSick
            && "Doctor".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedHuman(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && anEntity.myIsSick
            && "Human".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedHuman() {

        if (!this.isDead && !this.isImmune && this.myIsSick
            && "Human".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedDoctor(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && anEntity.myIsSick
            && "Doctor".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedDoctor() {

        if (!this.isDead && !this.isImmune && this.myIsSick
            && "Doctor".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedBird(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && anEntity.myIsSick
            && "Bird".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedBird() {

        if (!this.isDead && !this.isImmune && this.myIsSick
            && "Bird".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneHuman(AbstractEntity anEntity) {

        if (!anEntity.isDead && anEntity.isImmune && !anEntity.myIsSick
            && "Human".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneHuman() {

        if (!this.isDead && this.isImmune && !this.myIsSick
            && "Human".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneDoctor(AbstractEntity anEntity) {

        if (!anEntity.isDead && anEntity.isImmune && !anEntity.myIsSick
            && "Doctor".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneDoctor() {

        if (!this.isDead && this.isImmune && !this.myIsSick
            && "Doctor".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneBird(AbstractEntity anEntity) {

        if (!anEntity.isDead && anEntity.isImmune && !anEntity.myIsSick
            && "Bird".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneBird() {

        if (!this.isDead && this.isImmune && !this.myIsSick
            && "Bird".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateTwoEntitiesAreTogether(AbstractEntity anEntity,
                                                  AbstractEntity anEntityToCompare) {
        if (anEntity.getMyLocation().equals(anEntityToCompare.getMyLocation())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateTwoEntitiesAreTogether(AbstractEntity anEntityToCompare) {
        if (this.getMyLocation().equals(anEntityToCompare.getMyLocation())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedEntity(AbstractEntity anEntity) {
        if (anEntity.isSick() && !anEntity.isDead && !anEntity.isImmune()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateInfectedEntity() {
        if (this.isSick() && !this.isDead() && !this.isImmune()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyEntity() {

        if (!this.isDead() && !this.isImmune() && !this.isSick()) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateHealthyEntity(AbstractEntity anEntity) {

        if (!anEntity.isDead && !anEntity.isImmune && !anEntity.myIsSick) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneEntity() {

        if (!this.isDead && this.isImmune && !this.myIsSick) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateImmuneEntity(AbstractEntity anEntity) {

        if (!anEntity.isDead && anEntity.isImmune && !anEntity.myIsSick) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateEntityIsADoctor(AbstractEntity anEntity) {
        if ("Doctor".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateEntityIsADoctor() {
        if ("Doctor".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateEntityIsAPoliceOfficer(AbstractEntity anEntity) {
        if ("PoliceOfficer".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateEntityIsAPoliceOfficer() {
        if ("PoliceOfficer".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateEntityIsABird(AbstractEntity anEntity) {
        if ("Bird".equalsIgnoreCase(anEntity.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateEntityIsABird() {
        if ("Bird".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateTwoEntitesOfOppositeSex(AbstractEntity secondaryEntity) {
        if (this.getMyGender() != secondaryEntity.getMyGender()) {
            return true;
        }
        else {
            return false;
        }

    }

    public boolean validateTwoEntitesOfOppositeSex(AbstractEntity primaryEntity,
                                                   AbstractEntity secondaryEntity) {
        if (primaryEntity.getMyGender() != secondaryEntity.getMyGender()) {
            return true;
        }
        else {
            return false;
        }

    }
    
    public boolean validateEntityIsAHuman() {
        if ("Human".equalsIgnoreCase(this.getClass().getSimpleName())) {
            return true;
        }
        else {
            return false;
        }
    }

}
