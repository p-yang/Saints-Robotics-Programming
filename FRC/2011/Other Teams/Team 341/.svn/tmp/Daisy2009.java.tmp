/*----------------------------------------------------------------------------*/
/* Copyright (c) Team 341 2009. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.missdaisy;

import edu.wpi.first.wpilibj.*;
import java.util.TimerTask;

public class Daisy2009 extends IterativeRobot
{
    // Helper class to run 200Hz PID calculations
    private class FastPeriodicTask extends TimerTask
    {
        private Daisy2009 instance;
        private boolean run100HzTasks = true;
        java.util.Timer timer;

        public FastPeriodicTask(Daisy2009 instance)
        {
            this.instance = instance;
            timer = new java.util.Timer();
            timer.schedule(this, 0L, 5L); // 200Hz
        }

        // Put anything that you want to do at 200Hz in here
        private void run200Hz()
        {
            if( instance.rightStick.getRawButton(11) )
            {
                // Reset
                if( turretPID.isEnabled() )
                {
                    turretPID.reset();
                    turretPID.enable();
                }
                turretMotor.set(-1);
                turretEncoder.reset();
            }
            else if( instance.rightStick.getRawButton(10) )
            {
                // Reset
                if( turretPID.isEnabled() )
                {
                    turretPID.reset();
                    turretPID.enable();
                }
                turretMotor.set(1);
                turretEncoder.reset();
            }
            else if( instance.turretPID.isEnabled() )
            {
                // Implement a deadband on the turret to prevent oscillation
                if( instance.turretPID.onTarget(0.5) )
                {
                    instance.turretMotor.set(0);
                    instance.turretPID.calculate(instance.turretEncoder.getDistance(), 0);
                }
                else
                {
                    instance.turretMotor.set(instance.turretPID.calculate(instance.turretEncoder.getDistance(), 0));
                }
            }

            if( instance.leftDrivePID.isEnabled() )
            {
                instance.leftDrive.set(instance.leftDrivePID.calculate(instance.leftDriveEncoder.getRate(), 0));
            }

            if( instance.rightDrivePID.isEnabled() )
            {
                instance.rightDrive.set(instance.rightDrivePID.calculate(instance.rightDriveEncoder.getRate(), 0));
            }
        }

        // Put anything that you want to do at 100Hz in here
        private void run100Hz()
        {
            
        }

        public void run()
        {
            run200Hz();

            // Run any 100Hz tasks we may have
            if( run100HzTasks )
            {
                run100Hz();
            }

            run100HzTasks = !run100HzTasks;
        }
    }

    // Constants
    final double kMaxSpeedInPerSec = 120.0;

    // Robot state
    boolean isTwoStick;
    boolean turretShooting;
    int autoState;
    double secondsInAutonomous;

    // Outputs
    Jaguar leftDrive;
    Jaguar rightDrive;
    Jaguar turretMotor;
    Victor conveyorMotor;
    Victor brushSpinner1;
    Victor brushSpinner2;
    Compressor compressor;
    Solenoid hoodSolenoid;
    Solenoid gateSolenoid;

    // Inputs
    Joystick leftStick;
    Joystick rightStick;
    Joystick operatorStick;

    // Sensors
    Encoder turretEncoder;
    Encoder rightDriveEncoder;
    Encoder leftDriveEncoder;
    Gyro gyro;

    // Filters
    SynchronousPID turretPID;
    DigitalFilter turretFilter;
    SynchronousPID leftDrivePID;
    DigitalFilter leftDriveFilter;
    SynchronousPID rightDrivePID;
    DigitalFilter rightDriveFilter;

    // Tasks
    FastPeriodicTask fastTask;

    public Daisy2009()
    {
        // Create motors
        leftDrive = new Jaguar(2);
        rightDrive = new Jaguar(1);
        turretMotor = new Jaguar(3);
        conveyorMotor = new Victor(4);
        brushSpinner1 = new Victor(5);
        brushSpinner2 = new Victor(6);

        // Create the pneumatics system
        compressor = new Compressor(14,1);
        hoodSolenoid = new Solenoid(1);
        gateSolenoid = new Solenoid(4);

        // Create joysticks
        operatorStick = new Joystick(3);
        leftStick = new Joystick(4);
        rightStick = new Joystick(1);

        // Create sensors
        turretEncoder = new Encoder(10,11,false,Encoder.EncodingType.k4X);
        turretEncoder.setDistancePerPulse(-0.633431085043988);

        rightDriveEncoder = new Encoder(3,4,false,Encoder.EncodingType.k1X);
        rightDriveEncoder.setDistancePerPulse(0.0764035333);
        rightDriveEncoder.setMinRate(1.0);

        leftDriveEncoder = new Encoder(1,2,false,Encoder.EncodingType.k1X);
        leftDriveEncoder.setDistancePerPulse(0.0764035333);
        leftDriveEncoder.setMinRate(1.0);

        gyro = new Gyro(1);
        gyro.setSensitivity(.005);

        // Create filters
        turretPID = new SynchronousPID(.15, .003, .15);
        turretFilter = DigitalFilter.SinglePoleIIRFilter(.2);

        rightDrivePID = new SynchronousPID(.015, .0006, 0); // .025
        rightDriveFilter = DigitalFilter.SinglePoleIIRFilter(.1);

        leftDrivePID = new SynchronousPID(.015, .0006, 0); // .025
        leftDriveFilter = DigitalFilter.SinglePoleIIRFilter(.1);

        // Kick off tasks
        fastTask = new FastPeriodicTask(this);

        // Set up the watchdog
        getWatchdog().setEnabled(true);
        getWatchdog().setExpiration(kDefaultPeriod);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        // Initialize all of our state variables
        isTwoStick = false;
        turretShooting = true;

        leftDriveEncoder.start();
        rightDriveEncoder.start();
        turretEncoder.start();
        compressor.start();

        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Drive: 1 Stick");
        DriverStationLCD.getInstance().updateLCD();
    }

    public void disabledPeriodic()
    {
        getWatchdog().feed();

        if (leftStick.getRawButton(4))
        {
            isTwoStick = true;
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Drive: 2 Stick");
            DriverStationLCD.getInstance().updateLCD();
        } else if (leftStick.getRawButton(5))
        {
            isTwoStick = false;
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Drive: 1 Stick");
            DriverStationLCD.getInstance().updateLCD();
        }
    }

    public void disabledInit()
    {
        turretPID.reset();
        leftDrivePID.reset();
        rightDrivePID.reset();
    }

    public void autonomousInit()
    {
        autoState = 0;
        secondsInAutonomous = 0.0;

        gyro.reset();
    }

    public void teleopInit()
    {
        turretPID.enable();
        turretPID.setSetpoint(0);

        leftDrivePID.enable();
        leftDrivePID.setSetpoint(0);

        rightDrivePID.enable();
        rightDrivePID.setSetpoint(0);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {
        getWatchdog().feed();

        secondsInAutonomous += .02; // 50 Hz

        // Kill anything and everything in front of us!
        drive(1.0 * kMaxSpeedInPerSec, 1.0 * kMaxSpeedInPerSec);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        getWatchdog().feed();
        
        ///// Drive
        double leftSpeed;
        double rightSpeed;

        if (leftStick.getRawButton(4))
        {
            isTwoStick = true;
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Drive: 2 Stick");
            DriverStationLCD.getInstance().updateLCD();
        } else if (leftStick.getRawButton(5))
        {
            isTwoStick = false;
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Drive: 1 Stick");
            DriverStationLCD.getInstance().updateLCD();
        }

        if (isTwoStick)
        {
            leftSpeed = -leftStick.getY();
            rightSpeed = -rightStick.getY();
        } else
        {
            leftSpeed = -leftStick.getY() + leftStick.getX();
            if (leftSpeed > 1.0)
            {
                leftSpeed = 1.0;
            } else if (leftSpeed < -1.0)
            {
                leftSpeed = -1.0;
            }

            rightSpeed = -leftStick.getY() - leftStick.getX();
            if (rightSpeed > 1.0)
            {
                rightSpeed = 1.0;
            } else if (rightSpeed < -1.0)
            {
                rightSpeed = -1.0;
            }
        }
        drive(leftSpeed * kMaxSpeedInPerSec, rightSpeed * kMaxSpeedInPerSec);

        ///// Agitator
        if( operatorStick.getTrigger() )
        {
            // When firing, run the agitator
            brushSpinner1.set(-1);
            brushSpinner2.set(-1);
        }
        else
        {
            // When not firing, the operator controls the agitator
            brushSpinner1.set((operatorStick.getThrottle()-1.0)/2.0);
            brushSpinner2.set((operatorStick.getThrottle()-1.0)/2.0);
        }

        ///// The conveyor/shooter
        if( operatorStick.getRawButton(2) )
        {
            // Unjam/reverse
            conveyorMotor.set(-1);
            brushSpinner1.set(1);
            brushSpinner2.set(1);
        }
        else if( !turretShooting && turretPID.onTarget(7.0) )
        {
            // Pick up mode
            conveyorMotor.set(.9);
        }
        else if( operatorStick.getTrigger() )
        {
            // Firing
            conveyorMotor.set(1);
        }
        else
        {
            // Otherwise, stop
            conveyorMotor.set(0);
        }

        ///// Gate solenoid
        if( operatorStick.getTrigger() )
        {
            // Open when firing
            gateSolenoid.set(true);
        }
        else
        {
            // Close when not
            gateSolenoid.set(false);
        }

        ///// Turret control
        if( operatorStick.getRawButton(7) )
        {
            // Shoot mode
            turretShooting = true;
        }
        else if( operatorStick.getRawButton(11) )
        {
            // Pick up mode
            turretShooting = false;
        }

        if( turretShooting )
        {
            // When shooting, go +/- 32 degrees
            turretPID.setSetpoint( turretFilter.calculate(operatorStick.getX() * 32.0) );
        }
        else
        {
            // When picking up, turn 180
            turretPID.setSetpoint(180);
        }
    }

    private void drive( double leftDesiredSpeed, double rightDesiredSpeed )
    {
        leftDrivePID.setSetpoint( leftDriveFilter.calculate(leftDesiredSpeed) );
        rightDrivePID.setSetpoint( rightDriveFilter.calculate(rightDesiredSpeed) );
    }
}
