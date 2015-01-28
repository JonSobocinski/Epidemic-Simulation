
package epidemic.progpracticum.view;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * An extension of JCheckBoxMenuItem that doesn't close the menu when selected.
 * 
 * @author Darryl http://tips4java.wordpress.com/2010/09/12/keeping-menus-open/
 * @version V1.0
 */
public class StayOpenCheckBoxMenuItem extends JCheckBoxMenuItem {

    /**
     * Default serial number added by Eclipse.
     */
    private static final long serialVersionUID = 1L;
    /**
     * File path used in this object. Keeps track of currently selected button.
     */
    private static MenuElement[] path;

    {
        getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent anEvent) {
                if (getModel().isArmed() && isShowing()) {
                    path = MenuSelectionManager.defaultManager().getSelectedPath();
                }
            }
        });
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem()
     */
    public StayOpenCheckBoxMenuItem() {
        super();
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem(Action)
     * @param anAction An Action
     */
    public StayOpenCheckBoxMenuItem(final Action anAction) {
        super(anAction);
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem(Icon)
     * @param anIcon An Icon
     */
    public StayOpenCheckBoxMenuItem(final Icon anIcon) {
        super(anIcon);
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String)
     * @param aText A text
     */
    public StayOpenCheckBoxMenuItem(final String aText) {
        super(aText);
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String, boolean)
     * @param aText A Text
     * @param aSelection A Selection
     */
    public StayOpenCheckBoxMenuItem(final String aText, final boolean aSelection) {
        super(aText, aSelection);
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String, Icon)
     * @param aText A Text
     * @param anIcon An Icon
     */
    public StayOpenCheckBoxMenuItem(final String aText, final Icon anIcon) {
        super(aText, anIcon);
    }

    /**
     * @see JCheckBoxMenuItem#JCheckBoxMenuItem(String, Icon, boolean)
     * @param aText A Text
     * @param anIcon An Icon
     * @param aSelection A Selection
     */
    public StayOpenCheckBoxMenuItem(final String aText, final Icon anIcon,
                                    final boolean aSelection) {
        super(aText, anIcon, aSelection);
    }

    /**
     * Overridden to reopen the menu.
     * 
     * @param aPressTime the time to "hold down" the button, in milliseconds
     */
    @Override
    public void doClick(int aPressTime) {
        super.doClick(aPressTime);
        MenuSelectionManager.defaultManager().setSelectedPath(path);
    }
}
