/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

/**
 * Base class for AnalogModule and DigitalModule.
 * 
 * @author dtjones
 */
public class Module extends SensorBase {

    /**
     * An array holding the object representing each module
     */
    protected static Module[] m_modules = new Module[SensorBase.kChassisSlots];
    /**
     * The slot that the module is in
     */
    protected int m_slot;

    /**
     * Constructor.
     *
     * @param slot The slot in the chassis where the module is plugged in.
     */
    protected Module(final int slot) {
        m_slot = slot;
    }

    /**
     * Gets the slot associated with a module.
     *
     * @return The module's slot.
     */
    public int getSlot() {
        return m_slot;
    }
}
