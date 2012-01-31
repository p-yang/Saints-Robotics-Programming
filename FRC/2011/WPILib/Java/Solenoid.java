/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.util.AllocationException;
import edu.wpi.first.wpilibj.util.CheckedAllocationException;

/**
 * Solenoid class for running high voltage Digital Output (9472 module).
 *
 * The Solenoid class is typically used for pneumatics solenoids, but could be used
 * for any device within the current spec of the 9472 module.
 */
public class Solenoid extends SolenoidBase {

    private int m_channel; ///< The channel on the module to control.

    /**
     * Common function to implement constructor behavior.
     */
    private synchronized void initSolenoid() {
        checkSolenoidModule(m_chassisSlot);
        checkSolenoidChannel(m_channel);

        try {
            m_allocated.allocate(slotToIndex(m_chassisSlot) * kSolenoidChannels + m_channel - 1);
        } catch (CheckedAllocationException e) {
            throw new AllocationException(
                    "Solenoid channel " + m_channel + " on module " + m_chassisSlot + " is already allocated");
        }
    }

    /**
     * Constructor.
     *
     * @param channel The channel on the module to control.
     */
    public Solenoid(final int channel) {
        super(getDefaultSolenoidModule());
        m_channel = channel;
        initSolenoid();
    }

    /**
     * Constructor.
     *
     * @param slot The slot that the 9472 module is plugged into.
     * @param channel The channel on the module to control.
     */
    public Solenoid(final int slot, final int channel) {
        super(slot);
        m_channel = channel;
        initSolenoid();
    }

    /**
     * Destructor.
     */
    protected synchronized void free() {
        m_allocated.free(slotToIndex(m_chassisSlot) * kSolenoidChannels + m_channel - 1);
    }

    /**
     * Set the value of a solenoid.
     *
     * @param on Turn the solenoid output off or on.
     */
    public void set(boolean on) {
        byte value = (byte)(on ? 0xFF : 0x00);
        byte mask = (byte)(1 << (m_channel - 1));

        set(value, mask);
    }

    /**
     * Read the current value of the solenoid.
     *
     * @return The current value of the solenoid.
     */
    public boolean get() {
	int value = getAll() & ( 1 << (m_channel - 1));
	return (value != 0);
    }
}
