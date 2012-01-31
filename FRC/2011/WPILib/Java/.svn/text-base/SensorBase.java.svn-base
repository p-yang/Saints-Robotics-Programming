/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

/**
 * Base class for all sensors.
 * Stores most recent status information as well as containing utility functions for checking
 * channels and error processing.
 */
public class SensorBase {

    private static final int[] modulePopulation = new int[]{0, 9201, 9201, 0, 9403, 0, 9403, 9472, 9472};
    /**
     * Ticks per microsecond
     */
    public static final int kSystemClockTicksPerMicrosecond = 40;
    /**
     * Number of digital channels per digital sidecar
     */
    public static final int kDigitalChannels = 14;
    /**
     * Number of analog channels per module
     */
    public static final int kAnalogChannels = 8;
    /**
     * Number of analog modules
     */
    public static final int kAnalogModules = 2;
    /**
     * Number of solenoid channels per moduel
     */
    public static final int kSolenoidChannels = 8;
    /**
     * Number of analog modules
     */
    public static final int kSolenoidModules = 2;
    /**
     * Number of PWM channels per sidecar
     */
    public static final int kPwmChannels = 10;
    /**
     * Number of relay channels per sidecar
     */
    public static final int kRelayChannels = 8;
    /**
     * Number of slots in the chassis
     */
    public static final int kChassisSlots = 8;
    private static int m_defaultAnalogModule = 1;
    private static int m_defaultDigitalModule = 4;
    private static int m_defaultSolenoidModule = 8;

    /**
     * Creates an instance of the sensor base and gets an FPGA handle
     */
    public SensorBase() {
    }

    /**
     * Sets the default Digital Module.
     * This sets the default digital module to use for objects that are created without
     * specifying the digital module in the constructor. The default module is initialized
     * to the first module in the chassis.
     *
     * @param slot The cRIO slot containing the digital module.
     */
    public static void setDefaultDigitalModule(final int slot) {
        checkDigitalModule(slot);
        SensorBase.m_defaultDigitalModule = slot;
    }

    /**
     * Sets the default Analog module.
     * This sets the default analog module to use for objects that are created without
     * specifying the analog module in the constructor. The default module is initialized
     * to the first module in the chassis.
     *
     * @param slot The cRIO slot containing the analog module.
     */
    public static void setDefaultAnalogModule(final int slot) {
        checkAnalogModule(slot);
        SensorBase.m_defaultAnalogModule = slot;
    }

    /**
     * Set the default location for the Solenoid (9472) module.
     * Currently the module must be in slot 8, but it might change in the future.
     *
     * @param slot The cRIO slot containing the analog module.
     */
    public static void setDefaultSolenoidModule(final int slot) {
        checkSolenoidModule(slot);
        SensorBase.m_defaultSolenoidModule = slot;
    }

    /**
     * Check that the digital module number is valid.
     * Module numbers are the slot number that they are inserted in.
     *
     * @param slot The cRIO slot to check.
     */
    protected static void checkDigitalModule(final int slot) {
        if (slot > kChassisSlots || modulePopulation[slot] != 9403) {
            System.err.print("Module attached to slot ");
            System.err.print(slot);
            System.err.println(" is not a digital module.");
            throw new IndexOutOfBoundsException("Module attached to slot " + slot + " is not a digital module");
        }
    }

    /**
     * Check that the digital module number is valid.
     * Module numbers are the slot number that they are inserted in.
     *
     * @param slot The cRIO slot to check.
     */
    protected static void checkRelayModule(final int slot) {
        checkDigitalModule(slot);
    }

    /**
     * Check that the digital module number is valid.
     * Module numbers are the slot number that they are inserted in.
     *
     * @param slot The cRIO slot to check.
     */
    protected static void checkPWMModule(final int slot) {
        SensorBase.checkDigitalModule(slot);
    }

    /**
     * Check that the analog module number is valid.
     * Module numbers are the slot numbers that they are inserted in.
     *
     * @param slot The cRIO slot to check.
     */
    protected static void checkAnalogModule(final int slot) {
        if (slot > kChassisSlots || modulePopulation[slot] != 9201) {
            System.err.print("Module attached to slot ");
            System.err.print(slot);
            System.err.println(" is not an analog module.");
        }
    }

    /**
     * Verify that the solenoid module is correct.
     * Verify that the solenoid module is slot 8 or 7.
     *
     * @param slot The cRIO slot to check.
     */
    protected static void checkSolenoidModule(final int slot) {
        if (slot > kChassisSlots || modulePopulation[slot] != 9472) {
            System.err.print("Module attached to slot ");
            System.err.print(slot);
            System.err.println(" is not a solenoid module.");
        }
    }

    /**
     * Check that the digital channel number is valid.
     * Verify that the channel number is one of the legal channel numbers. Channel numbers are
     * 1-based.
     *
     * @param channel The channel number to check.
     */
    protected static void checkDigitalChannel(final int channel) {
        if (channel <= 0 || channel > kDigitalChannels) {
            System.err.println("Requested digital channel number is out of range.");
        }
    }

    /**
     * Check that the digital channel number is valid.
     * Verify that the channel number is one of the legal channel numbers. Channel numbers are
     * 1-based.
     *
     * @param channel The channel number to check.
     */
    protected static void checkRelayChannel(final int channel) {
        if (channel <= 0 || channel > kRelayChannels) {
            System.err.println("Requested relay channel number is out of range.");
            throw new IndexOutOfBoundsException("Requested relay channel number is out of range.");
        }
    }

    /**
     * Check that the digital channel number is valid.
     * Verify that the channel number is one of the legal channel numbers. Channel numbers are
     * 1-based.
     *
     * @param channel The channel number to check.
     */
    protected static void checkPWMChannel(final int channel) {
        if (channel <= 0 || channel > kPwmChannels) {
            System.err.println("Requested PWM channel number is out of range.");
            throw new IndexOutOfBoundsException("Requested PWM channel number is out of range.");
        }
    }

    /**
     * Check that the analog channel number is value.
     * Verify that the analog channel number is one of the legal channel numbers. Channel numbers
     * are 1-based.
     *
     * @param channel The channel number to check.
     */
    protected static void checkAnalogChannel(final int channel) {
        if (channel <= 0 || channel > kAnalogChannels) {
            System.err.println("Requested analog channel number is out of range.");
        }
    }

    /**
     * Verify that the solenoid channel number is within limits.  Channel numbers
     * are 1-based.
     *
     * @param channel The channel number to check.
     */
    protected static void checkSolenoidChannel(final int channel) {
        if (channel <= 0 || channel > kSolenoidChannels) {
            System.err.println("Requested solenoid channel number is out of range.");
        }
    }

    /**
     * Get the slot that contains the default analog module.
     *
     * @return The slot containing the default analog module.
     */
    public static int getDefaultAnalogModule() {
        return SensorBase.m_defaultAnalogModule;
    }

    /**
     * Get the slot that contains the default analog module.
     *
     * @return The slot containing the default analog module.
     */
    public static int getDefaultDigitalModule() {
        return SensorBase.m_defaultDigitalModule;
    }

    /**
     * Get the slot that contains the default analog module.
     *
     * @return The slot containing the default analog module.
     */
    public static int getDefaultSolenoidModule() {
        return SensorBase.m_defaultSolenoidModule;
    }

    /**
     * Free the resources used by this object
     */
    protected void free() {
    }
}
