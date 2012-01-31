/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.samples;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.IterativeRobot;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This program demonstartes how to use the enhanced IO board.
 * In teleop, it will print the acceleromater readings and count in binary on
 * the eight LEDs.
 * If an input on the touch slider is detected it will light up the LED next to
 * the touch.
 */
public class EnhancedIOSample extends IterativeRobot {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    }
    DriverStationEnhancedIO enhancedIO = DriverStation.getInstance().getEnhancedIO();

    public void disabledPeriodic() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }
    byte ledCounter = 0;
    Timer timer;

    /**
     * Setup a timer task to print to the console every half a second
     */
    public void teleopInit() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                printData();
            }
        }, 0, 500);
    }

    /**
     * Stop printing
     */
    public void autonomousInit() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Stop printing
     */
    public void disabledInit() {
        if (timer != null) {
            timer.cancel();
        }
    }
    boolean ledAccelerometer = false;

    /**
     * This function is called periodically during operator control
     */
    public synchronized void teleopPeriodic() {
        try {
            double touchSlider = enhancedIO.getTouchSlider();

            /*
             * Reset the LED counter when you shake the board horizontally.
             */
            if (Math.abs(enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelX)) > 2.0 ||
                    Math.abs(enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelY)) > 2.0) {
                ledCounter = 0;
            }
            /*
             * Switch modes when you shake the board along the z axis.
             */
            if (Math.abs(enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelZ)) > 2.0) {
                System.out.println("Swithcing LED mode");
                ledAccelerometer = !ledAccelerometer;
                edu.wpi.first.wpilibj.Timer.delay(1.0);
            }

            if (touchSlider == -1.0) { //Use the LEDs to count in binary
                if (ledAccelerometer) {
                    double accel = -enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelX);
                    if (accel > 1.0) {
                        accel = 1.0;
                    }
                    if (accel < -1.0) {
                        accel = -1.0;
                    }
                    enhancedIO.setLEDs((byte) (0x01 << (int) ((accel + 1.0) * 3.99)));
                } else {
                    enhancedIO.setLEDs(ledCounter);
                }
            } else { //Light the LED next to the touch on the touch slider
                enhancedIO.setLEDs((byte) (0x01 << (int) (touchSlider * 7.99)));
            }
        } catch (DriverStationEnhancedIO.EnhancedIOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Print information to the console
     */
    public synchronized void printData() {
        try {
            System.out.println("Accel X Y Z: " +
                    enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelX) + " " +
                    enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelY) + " " +
                    enhancedIO.getAcceleration(DriverStationEnhancedIO.tAccelChannel.kAccelZ));
            System.out.println("Touch Slider: " + enhancedIO.getTouchSlider());
            ledCounter++; //Increment the LED byte to count in binary
        } catch (DriverStationEnhancedIO.EnhancedIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
