/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class which only returns true if the button changes state from one to another
 * @author Tanner Smith (creator)
 */
public class ButtonChange {

    Joystick joystick;      //The joystick I want to use
    int buttonNumber;       //Which button I like better... and want to use

    boolean previousState;  //The previous state of the button

    /**
     * Constructor.
     * @param joystick The desired joystick holding the button.
     * @param buttonNumber The desired button number to monitor.
     */
    public ButtonChange(Joystick joystick, int buttonNumber) {
        this.joystick = joystick;
        this.buttonNumber = buttonNumber;
    }

    /**
     * Sets the previous state of the button after you check if it changed.
     */
    public void setPreviousState() {
        //Could get the state from the method, but this is more reliable and error-free
        previousState = joystick.getRawButton(buttonNumber);
    }

    /**
     * Check whether or not the button changed.
     * See LabVIEW code for exactly how this works.... or figure it out on your own.
     * @param actOnRelease Did we release it or did we press it?
     * @return Whether or not the button changed.
     */
    public boolean didButtonChange(boolean actOnRelease) {
        boolean currentState = joystick.getRawButton(buttonNumber);

        if (actOnRelease) {
            //I want to know when the button releases
            if (currentState == false) {
                if (previousState == false) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            //I want to know when the button is pushed
            if (currentState) {
                if (previousState == false) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
