/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.fpga.tSolenoid;
import edu.wpi.first.wpilibj.parsing.IDeviceController;

/**
 * SolenoidBase class is the common base class for the Solenoid and
 * DoubleSolenoid classes.
 */
public abstract class SolenoidBase extends SensorBase implements IDeviceController {

    protected int m_chassisSlot; ///< Slot number where the module is plugged into the chassis.
    protected static Resource m_allocated = new Resource(tSolenoid.kDO7_0_NumElements * kSolenoidChannels);

    private static tSolenoid m_fpgaSolenoidModule; ///< FPGA Solenoid Module object.
    private static int m_refCount; ///< Reference count for the chip object.

    /**
     * Constructor.
     *
     * @param slot The slot that the 9472 module is plugged into.
     */
    public SolenoidBase(final int slot) {
        m_chassisSlot = slot;
        checkSolenoidModule(m_chassisSlot);

        m_refCount++;
        if (m_refCount == 1) {
            m_fpgaSolenoidModule = new tSolenoid();
        }
    }

    /**
     * Destructor.
     */
    protected synchronized void free() {
        if (m_refCount == 1) {
            m_fpgaSolenoidModule.Release();
            m_fpgaSolenoidModule = null;
        }
        m_refCount--;
    }

    /**
     * Convert slot number to index.
     *
     * @param slot The slot in the chassis where the module is plugged in.
     * @return An index to represent the module internally.
     */
    protected int slotToIndex(final int slot) {
        return 8 - slot;
    }

    /**
     * Set the value of a solenoid.
     *
     * @param value The value you want to set on the module.
     * @param mask The channels you want to be affected.
     */
    protected synchronized void set(int value, int mask) {
        byte currentValue = (byte)tSolenoid.readDO7_0(slotToIndex(m_chassisSlot));
        // Zero out the values to change
        currentValue = (byte)(currentValue & ~mask);
        currentValue = (byte)(currentValue | (value & mask));
        tSolenoid.writeDO7_0(slotToIndex(m_chassisSlot), currentValue);
    }

    /**
     * Read all 8 solenoids as a single byte
     *
     * @return The current value of all 8 solenoids on the module.
     */
    public byte getAll() {
        return (byte)tSolenoid.readDO7_0(slotToIndex(m_chassisSlot));
    }
}
