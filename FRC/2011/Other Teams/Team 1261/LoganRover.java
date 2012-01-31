/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot;

//Only import what we need, don't bog down the memory.
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 * Class runs all the robot code including autonomous and teleoperated periods.
 *
 * @author: Tanner Smith (creator)
 * @robotName: Logan Rover
 * @robotNamee: Logan Su
 * @package robot
 */
public class LoganRover extends IterativeRobot {
    public static final double AUTO_CORNER_FORWARD_TIME = 2;    //Time for autonomous, in seconds
    public static final double AUTO_MIDDLE_FORWARD_TIME = 1;
    
    public static final double AUTO_FORWARD_SPEED       = -0.5; //Speeds for autonomous mode, forward speed
    public static final double AUTO_RIGHT_SPIN_SPEED    = -1;   //Speeds for autonomous mode, right corner spin speed
    public static final double AUTO_LEFT_SPIN_SPEED     = 1;    //Speeds for autonomous mode, left corner spin speed

    private Joystick joystickOne;                               //Drive/Manipulator Joystick
    
    private DigitalInput leftAutoSwitch;                        //Left Auto Switch (on robot)
    private DigitalInput rightAutoSwitch;                       //Right Auto Switch (on robot)

    private Timer autoTimer;                                    //Normally would use match time, but can't find that.
    
    private RobotDrive drivetrain;                              //Motors on Drivetrain
    private Intake intake;                                      //Intake System

    private Jaguar leftDrive, rightDrive;                       //Drivetrain Motors
    private Victor intakeMotorOne, intakeMotorTwo;              //Intake Motors
    private Victor unobtaniumMotor;                             //Also part of intake system

    /**
     * This function is run when the robot is first started up and initializes any
     * buttons, joysticks, or motors that the robot will use.
     */
    public void robotInit() {
        joystickOne = new Joystick(1);
        
        leftAutoSwitch = new DigitalInput(1);
        rightAutoSwitch = new DigitalInput(2);

        autoTimer = new Timer();

        leftDrive = new Jaguar(3);
        rightDrive = new Jaguar(1);
        intakeMotorOne = new Victor(2);     //Could set these in Intake, but this is nicer and easier to find.
        intakeMotorTwo = new Victor(4);
        unobtaniumMotor = new Victor(5);

        drivetrain = new RobotDrive(leftDrive, rightDrive);

        //Not sure why I have to do both front/rear of each side as I only have two motors?
        //Invert left side
        drivetrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        drivetrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //Invert right side
        drivetrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drivetrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        intake = new Intake(joystickOne, intakeMotorOne, intakeMotorTwo, unobtaniumMotor);
    }

    /**
     * This function is called before the beginning of autonomous.
     */
    public void autonomousInit() {
        //Start the timer that is used for autonomous.
        autoTimer.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        getWatchdog().feed();

        double elapsedTime = autoTimer.get(); //Time is in seconds

        //Perform whichever autonomous mode we chose from the switches
        if (leftAutoSwitch.get() == true && rightAutoSwitch.get() == false)
        {
            //Put us into left autonomous
            if (elapsedTime <= AUTO_CORNER_FORWARD_TIME) {
                //Move forward
                drivetrain.arcadeDrive(AUTO_FORWARD_SPEED, 0);
            } else {
                //Spin!
                drivetrain.arcadeDrive(0, AUTO_LEFT_SPIN_SPEED);
            }
        } else if (leftAutoSwitch.get() == false && rightAutoSwitch.get() == true) {
            //Put us into right autonomous
            if (elapsedTime <= AUTO_CORNER_FORWARD_TIME) {
                //Move forward
                drivetrain.arcadeDrive(AUTO_FORWARD_SPEED, 0);
            } else {
                //Spin!
                drivetrain.arcadeDrive(0, AUTO_RIGHT_SPIN_SPEED);
            }
        } else {
            //Middle position (both switches on or off)
            if (elapsedTime < AUTO_MIDDLE_FORWARD_TIME) {
                //Move forward
                drivetrain.arcadeDrive(AUTO_FORWARD_SPEED, 0);
            } else {
                //Spin!
                //Direction doesn't really matter at all...
                drivetrain.arcadeDrive(0, AUTO_LEFT_SPIN_SPEED);
            }
        }
    }

    /*
     * This function is called before the beginning of teleop
     */
    public void teleopInit() {
        //Get rid of that timer, we don't need it anymore.
        autoTimer.stop();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        getWatchdog().feed();

        //Drive the robot
        drivetrain.arcadeDrive(joystickOne);

        //Have the intake system (belts and unobtanium) perform to whatever mode it is in.
        intake.doAction();
    }
    
}
