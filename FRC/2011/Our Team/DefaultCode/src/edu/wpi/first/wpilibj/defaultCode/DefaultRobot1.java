/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.defaultCode;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;

public class DefaultRobot1 extends IterativeRobot {
    // Declare variable for the robot drive system
    RobotDrive m_robotDrive;		// robot will use PWM 1-4 for drive motors
    //MotorControl m_robotDrive;
    DriverStation ds;

    CANJaguar frontLeftJag;
    CANJaguar frontRightJag;
    CANJaguar rearLeftJag;
    CANJaguar rearRightJag;

    CANJaguar boomRightJag;
    CANJaguar boomLeftJag;
    CANJaguar stickRightJag;
    CANJaguar stickLeftJag;
    Servo wristServo;
	Solenoid gripperSolenoid;

	double boomAngle = 0.5;
	double stickAngle = 0.5;
	double lastTargetHeight = 18;
	double currentHeight = 18;
	double targetHeight = 18;
	double heightIncrement = 0;
    int currentPreset = -1;
    int presetStage = 0;
	boolean doAutoArmStuff = false;

    Servo deployServo;
	boolean deployed = false;

    DigitalInput leftLineSensor;
    DigitalInput middleLineSensor;
    DigitalInput rightLineSensor;
    int[] lastThreeLines = new int[3];
    int lineLoops = 0;
    boolean hitY = false;
	boolean hasStopped = false;
	int ignoreLines = 0;

    int m_dsPacketsReceivedInCurrentSecond;	// keep track of the ds packets received in the current second

    // Declare variables for the two joysticks being used
    Joystick m_joystick;			// joystick 1 (arcade stick or right tank stick)

    static final int NUM_BUTTONS = 10;
	int[] m_buttons = new int[NUM_BUTTONS];

    boolean slow_drive = false;

    // Local variables to count the number of periodic loops performed
    int m_autoPeriodicLoops;
    int m_disabledPeriodicLoops;
    int m_telePeriodicLoops;

    /**
     * Constructor for this "BuiltinDefaultCode" Class.
     *
     * The constructor creates all of the objects used for the different inputs and outputs of
     * the robot.  Essentially, the constructor defines the input/output mapping for the robot,
     * providing named objects for each of the robot interfaces.
     */
    public DefaultRobot1() {
        System.out.println("BuiltinDefaultCode Constructor Started\n");

        ds = DriverStation.getInstance();

        // Create a robot using standard right/left robot drive on PWMS 1, 2, 3, and #4

        try {
            frontLeftJag = new CANJaguar(4);
            frontRightJag = new CANJaguar(5);
            rearLeftJag = new CANJaguar(3);
            rearRightJag = new CANJaguar(2);
        } catch (Exception e) {}

        m_robotDrive = new RobotDrive(frontLeftJag, rearLeftJag, frontRightJag, rearRightJag);
        m_robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        m_robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        m_robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        m_robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        /*
        try {
            frontLeftJag= new CANJaguar(4, CANJaguar.ControlMode.kSpeed);
            frontRightJag = new CANJaguar(5, CANJaguar.ControlMode.kSpeed);
            rearLeftJag = new CANJaguar(3, CANJaguar.ControlMode.kSpeed);
            rearRightJag = new CANJaguar(2, CANJaguar.ControlMode.kSpeed);

            m_robotDrive = new MotorControl(frontRightJag, rearRightJag, frontLeftJag, rearLeftJag);
        } catch (Exception e) {}*/

        try {
            boomRightJag = new CANJaguar(6, CANJaguar.ControlMode.kPosition);
            boomLeftJag = new CANJaguar(9, CANJaguar.ControlMode.kPosition);
            stickRightJag = new CANJaguar(7, CANJaguar.ControlMode.kPosition);
            stickLeftJag = new CANJaguar(8, CANJaguar.ControlMode.kPosition);

			for(int setThatMode = 0; setThatMode < 5; setThatMode++){
				boomRightJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				boomLeftJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				stickRightJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				stickLeftJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				Thread.sleep(100);
				if(boomRightJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& boomLeftJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& stickLeftJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& stickRightJag.getControlMode()==CANJaguar.ControlMode.kPosition)
					break;
			}

            boomRightJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            boomLeftJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            stickRightJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            stickLeftJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
			
			for(int setThatMode = 0; setThatMode < 5; setThatMode++){
				boomRightJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				boomLeftJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				stickRightJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				stickLeftJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				Thread.sleep(100);
				if(boomRightJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& boomLeftJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& stickLeftJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& stickRightJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus)
					break;
			}

        } catch (Exception e) {
			System.out.println(e.getMessage());
		}
		wristServo = new Servo(2);
		gripperSolenoid = new Solenoid(1);
		
        deployServo = new Servo(1);

        leftLineSensor = new DigitalInput(14);
        middleLineSensor = new DigitalInput(13);
        rightLineSensor = new DigitalInput(12);

        m_dsPacketsReceivedInCurrentSecond = 0;

        // Define joysticks being used at USB port #1 and USB port #2 on the Drivers Station
        m_joystick = new Joystick(1);
		for (int buttonDex = 0; buttonDex < NUM_BUTTONS; buttonDex++) {
			m_buttons[buttonDex] = (int)(m_joystick.getRawButton(buttonDex+1)?1:0);		// The indices in m_buttons will be one less than the numbers on the controller, since arrays begin at 0
		}

        // Initialize counters to record the number of loops completed in autonomous and teleop modes
        m_autoPeriodicLoops = 0;
        m_disabledPeriodicLoops = 0;
        m_telePeriodicLoops = 0;

        System.out.println("BuiltinDefaultCode Constructor Completed\n");
    }

    /********************************** Init Routines *************************************/

    public void robotInit() {
        // Actions which would be performed once (and only once) upon initialization of the
        // robot would be put here.

        System.out.println("RobotInit() completed.\n");
    }

    public void disabledInit() {
        m_disabledPeriodicLoops = 0;			// Reset the loop counter for disabled mode
        startSec = (int)(Timer.getUsClock() / 1000000.0);
        printSec = startSec + 1;
    }

    public void autonomousInit() {
        m_autoPeriodicLoops = 0;
		try{
			boomAngle = (boomLeftJag.getPosition()+boomRightJag.getPosition())/2.0 * 180;
			stickAngle = (stickLeftJag.getPosition()+stickRightJag.getPosition())/2.0 * 180;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(boomAngle != 0 && stickAngle != 0) doAutoArmStuff = true;
    }

    public void teleopInit() {
        m_telePeriodicLoops = 0;                    // Reset the loop counter for teleop mode
        m_dsPacketsReceivedInCurrentSecond = 0;     // Reset the number of dsPackets in current second
		try{
			boomAngle = (boomLeftJag.getPosition()+boomRightJag.getPosition())/2.0 * 180;
			stickAngle = (stickLeftJag.getPosition()+stickRightJag.getPosition())/2.0 * 180;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
    }

    /********************************** Periodic Routines *************************************/
    static int printSec;
    static int startSec;

    public void disabledPeriodic() {
        updateDashboard();

        lastThreeLines[lineLoops%3] = 0;
        lastThreeLines[lineLoops%3] += (leftLineSensor.get()) ? 0 : 100;
        lastThreeLines[lineLoops%3] += (middleLineSensor.get()) ? 0 : 10;
        lastThreeLines[lineLoops%3] += (rightLineSensor.get()) ? 0 : 1;
        lineLoops++;

        // feed the user watchdog at every period when disabled
        Watchdog.getInstance().feed();

        // increment the number of disabled periodic loops completed
        m_disabledPeriodicLoops++;
		lineLoops = 0;
		hitY = false;
		hasStopped = false;
		ignoreLines = 0;

		try {
			//boomAngle = (boomLeftJag.getPosition()+boomRightJag.getPosition())/2.0 * 180;
			//stickAngle = (stickLeftJag.getPosition()+stickRightJag.getPosition())/2.0 * 180;
			System.out.println("Boom: "+boomLeftJag.getPosition()+" | "+boomRightJag.getPosition());
			System.out.println("Stick: "+stickLeftJag.getPosition()+" | "+stickRightJag.getPosition());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    public void autonomousPeriodic() {
        updateDashboard();
        // feed the user watchdog at every period when in autonomous
        Watchdog.getInstance().feed();

        m_autoPeriodicLoops++;

		deployServo.setAngle(140);

        if (!hasStopped) {//m_autoPeriodicLoops % (GetLoopsPerSec()/1.0) == 0) {
			boolean straightLine = ds.getDigitalIn(1);
			boolean goLeft = ds.getDigitalIn(2) && !straightLine;

			int lineReading = 0;
			lineReading += (leftLineSensor.get()) ? 0 : 100;
			lineReading += (middleLineSensor.get()) ? 0 : 10;
			lineReading += (rightLineSensor.get()) ? 0 : 1;
			System.out.print("" + lineReading + " | ");

			double rightMotors = 0;
			double leftMotors = 0;

			switch (lineReading) {
				case   0:
					/*if(hitY){
						if(goLeft) {
							rightMotors = -0.7*(lastThreeLines[0]%10 + lastThreeLines[1]%10 + lastThreeLines[2]%10)/3.0;
							leftMotors = 0.8*((lastThreeLines[0]/100)%10 + (lastThreeLines[1]/100)%10 + (lastThreeLines[2]/100)%10)/3.0;
						}
						else {
							rightMotors = 0.8*(lastThreeLines[0]%10 + lastThreeLines[1]%10 + lastThreeLines[2]%10)/3.0;
							leftMotors = -0.7*((lastThreeLines[0]/100)%10 + (lastThreeLines[1]/100)%10 + (lastThreeLines[2]/100)%10)/3.0;
						}
					}
					else*/{
						rightMotors = 1.2 * (lastThreeLines[0] % 10 + lastThreeLines[1] % 10 + lastThreeLines[2] % 10) / 3.0;
						leftMotors = 1.2*(Math.floor(lastThreeLines[0]/100)%10 + Math.floor(lastThreeLines[1]/100)%10 + Math.floor(lastThreeLines[2]/100)%10)/3.0;
					}
					break;
				case   1: leftMotors = 0.55; rightMotors = 0.7; break;
				case  11: leftMotors = 0.6; rightMotors = 0.7; break;
				case  10: leftMotors = 0.8; rightMotors = 0.8; break;
				case 110: leftMotors = 0.7; rightMotors = 0.6; break;
				case 100: leftMotors = 0.7; rightMotors = 0.55; break;
				case 111:
					if (straightLine || hitY) {
						hasStopped = true;
						System.out.print("STOPPED! | ");
					} else {
						if (goLeft) {
							leftMotors = 0.7;
							rightMotors = -0.7;
							lineReading = 100;
						} else {
							leftMotors = -0.7;
							rightMotors = 0.7;
							lineReading = 1;
						}

						hitY = true;
						System.out.print("HIT! | ");
					}
					break;
				case 101:
					if (!straightLine) {
						if (goLeft) {
							leftMotors = 0.7;
							rightMotors = -0.7;
							lineReading = 100;
						} else {
							leftMotors = -0.7;
							rightMotors = 0.7;
							lineReading = 1;
						}
						hitY = true;
						System.out.print("HIT! | ");
					}
					break;
				default: break;
			}

			m_robotDrive.tankDrive(-leftMotors, -rightMotors);
			System.out.println("Left: " + leftMotors + ", Right: " + rightMotors);

			if(lineReading > 0) lastThreeLines[lineLoops%3] = lineReading;
			lineLoops++;
        }
		else if(ignoreLines > 35){
			int lineReading = 0;
			lineReading += (leftLineSensor.get()) ? 0 : 100;
			lineReading += (middleLineSensor.get()) ? 0 : 10;
			lineReading += (rightLineSensor.get()) ? 0 : 1;
			System.out.print("" + lineReading + " | ");

			double rightMotors = 0;
			double leftMotors = 0;

			switch (lineReading) {
				case   0:
					leftMotors = 1*(lastThreeLines[0]%10 + lastThreeLines[1]%10 + lastThreeLines[2]%10)/3.0;
					rightMotors = 1*((lastThreeLines[0]/100)%10 + (lastThreeLines[1]/100)%10 + (lastThreeLines[2]/100)%10)/3.0;
					break;
				case   1: leftMotors = 0.4; rightMotors = 0.8; break;
				case  10: leftMotors = 0.9; rightMotors = 0.9; break;
				case  11: leftMotors = 0.7; rightMotors = 0.9; break;
				case 100: leftMotors = 0.8; rightMotors = 0.4; break;
				case 110: leftMotors = 0.9; rightMotors = 0.7; break;
				case 111: leftMotors = 0.8; rightMotors = 0.8; break;
				case 101: leftMotors = 0.8; rightMotors = 0.8; break;
				default: break;
			}

			m_robotDrive.tankDrive(-leftMotors, -rightMotors);
			System.out.println("Left: " + leftMotors + ", Right: " + rightMotors);

			lastThreeLines[lineLoops%3] = lineReading;
			lineLoops++;
		}
		else {
			if(!doAutoArmStuff){
				ignoreLines++;
				m_robotDrive.tankDrive(0.8, -0.8);
				System.out.println("Ignoring line");
			}
		}
    }

    public void teleopPeriodic() {
		lineLoops = 0;
		hitY = false;
		hasStopped = false;
		ignoreLines = 0;
		
        // feed the user watchdog at every period when in autonomous
        Watchdog.getInstance().feed();
        // increment the number of teleop periodic loops completed
        m_telePeriodicLoops++;
        // increment DS packets received
        m_dsPacketsReceivedInCurrentSecond++;
		
        updateDashboard();
		
		// Update which buttons are being pressed
		for (int buttonDex = 0; buttonDex < NUM_BUTTONS; buttonDex++) {
			m_buttons[buttonDex] = (int)(m_joystick.getRawButton(buttonDex+1)?1:0);
		}
		
		
		/*if(m_buttons[7])
			m_robotDrive.tankDrive(-m_joystick.getRawAxis(3), -m_joystick.getRawAxis(2));
		else
			m_robotDrive.tankDrive(m_joystick.getRawAxis(2), m_joystick.getRawAxis(3));*/
		//m_robotDrive.pidWrite(-m_joystick.getRawAxis(1));
		
		
        // drive with arcade style
		m_robotDrive.arcadeDrive((1+m_buttons[6]*-2)*m_joystick.getRawAxis(2), m_joystick.getRawAxis(4)/3*2, false);

		
		// Code for turning the servo to deploy the minibot
		if (m_buttons[4] == 1)
			deployed = true;
		
		if (deployed)
			deployServo.setAngle(0);
		else
			deployServo.setAngle(140);
		
		// DAT AAAAARM
		
		wristServo.setAngle(90+110*m_buttons[8]);
		if(m_buttons[5]==1){
			if(!gripperSolenoid.get()) gripperSolenoid.set(true);
			else gripperSolenoid.set(true);
		}
		/*
		try{
			boomAngle = (boomLeftJag.getPosition()-0.25+boomRightJag.getPosition()-0.25)/2.0*360;
			stickAngle = (stickLeftJag.getPosition()-0.25+stickRightJag.getPosition()-0.25)/2.0*360;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		double targetExtension = 24.0;
		currentHeight = 23 - (44*Math.sin((180-boomAngle-stickAngle)/180*Math.PI)-36*Math.sin((180-boomAngle)/180*Math.PI));
		double targetBoom = 90;
		double targetStick = 90;

		int presetButton = -1;
		for (int buttonIndex = 0; buttonIndex < NUM_BUTTONS; buttonIndex++) {
			if (m_buttons[buttonIndex] == 1 && buttonIndex != 4
					&& buttonIndex != 6 && buttonIndex != 7) {
				switch (buttonIndex+1) {
					case  2: targetHeight = 20; break;
					case  3: targetHeight = 20; break;
					case  1: targetHeight = 20; break;
					case  4: targetHeight = 55; break;
					case 10: targetHeight = 94; break;
					default: targetHeight = 20; break;
				}
				if(buttonIndex + 1 != 2) targetHeight += 8 * m_buttons[7];
				targetHeight -= 23; //base of arm
				presetButton = buttonIndex;
				break;
			}
		}
		if (presetButton > -1) {
			currentPreset = presetButton + 1;
			lastTargetHeight = currentHeight;
			heightIncrement = (targetHeight - lastTargetHeight)/20.0;
			presetStage = 0;
		}
		if (currentPreset == 2) {
			double currentExtension = 44*Math.cos((180-boomAngle-stickAngle)/180*Math.PI)-36*Math.cos((180-boomAngle)/180*Math.PI);
			if(presetStage <= 80){
				if (presetStage <= 20) {
					targetExtension = 18+4+6-targetHeight/Math.sqrt(3);

					if (currentHeight >= (lastTargetHeight+heightIncrement*presetStage-0.5)
							&& currentHeight <= (lastTargetHeight+heightIncrement*presetStage+0.5)
							&& currentExtension >= (targetExtension - 0.5)
							&& currentExtension <= (targetExtension + 0.5)) {
						presetStage++;
						if (presetStage == 21) {
							targetHeight = 5-23;
							lastTargetHeight = currentHeight;
							heightIncrement = (targetHeight - lastTargetHeight)/20.0;
						}
					}
				}
				else if (presetStage <= 40) {		//Move wrist to the ground at a 60 degree yeaaaaaah...
					targetExtension = 18+4+6-(lastTargetHeight+heightIncrement*(presetStage-20))/Math.sqrt(3);
					if (currentHeight >= (lastTargetHeight+heightIncrement*(presetStage-20)-0.5)
							&& currentHeight <= (lastTargetHeight+heightIncrement*(presetStage-20)+0.5)
							&& currentExtension >= (targetExtension-0.5)
							&& currentExtension <= (targetExtension+0.5)) {
						presetStage++;
						if (presetStage == 41) {
							targetHeight = 5-23;
							lastTargetHeight = currentHeight;
							heightIncrement = 0;
						}
					}
				}
				else if (presetStage <= 60) {		//Move wrist outward level the ground
					targetExtension = 18+4+6+(28-40)/20.0;
					if (currentHeight >= (targetHeight-0.5)
							&& currentHeight <= (targetHeight+0.5)
							&& currentExtension >= (targetExtension-0.5)
							&& currentExtension <= (targetExtension+0.5)) {
						presetStage++;
						if (presetStage == 61) {
							targetHeight = 20-23;
							lastTargetHeight = currentHeight;
							heightIncrement = (targetHeight - lastTargetHeight)/20.0;
						}
					}
				}
				else {		//Move the wrist back to storage position
					targetExtension = 12;
					if (currentHeight >= ((lastTargetHeight+heightIncrement*(presetStage-60))-0.5)
							&& currentHeight <= ((lastTargetHeight+heightIncrement*(presetStage-60))+0.5)
							&& currentExtension >= (targetExtension-0.5)
							&& currentExtension <= (targetExtension+0.5)) {
						presetStage++;
					}
				}
				double hypotenuse = Math.sqrt(MathUtils.pow(targetExtension, 2) + MathUtils.pow((lastTargetHeight+heightIncrement*(presetStage-20*Math.floor(presetStage/20))), 2));
				targetStick = MathUtils.acos((36*36+44*44-hypotenuse*hypotenuse)/(2*36*44))/Math.PI*180;
				double heightTriangleAngle = MathUtils.atan2(targetExtension, (lastTargetHeight+heightIncrement*(presetStage-60)))/Math.PI*180;
				double stickTriangleAngle = MathUtils.acos((36*36+hypotenuse*hypotenuse-44*44)/(2*36*hypotenuse))/Math.PI*180;
				targetBoom = stickTriangleAngle+heightTriangleAngle;
			}
			else {							//Routine finished
				currentPreset = -1;
				presetStage = 0;
			}
		}
		else if(presetStage <= 20){
			if(currentHeight >= (lastTargetHeight+heightIncrement*presetStage-0.5)
					&& currentHeight <= (lastTargetHeight+heightIncrement*presetStage+0.5)){
				presetStage++;
			}
			double hypotenuse = Math.sqrt(MathUtils.pow(targetExtension, 2)+MathUtils.pow(lastTargetHeight+heightIncrement*presetStage, 2));
			targetStick = MathUtils.acos((36*36+44*44-hypotenuse*hypotenuse)/(2*36*44))/Math.PI*180;
			double heightTriangleAngle = MathUtils.atan2(targetExtension, (lastTargetHeight+heightIncrement*presetStage))/Math.PI*180;
			double stickTriangleAngle = MathUtils.acos((36*36+hypotenuse*hypotenuse-44*44)/(2*36*hypotenuse))/Math.PI*180;
			targetBoom = stickTriangleAngle+heightTriangleAngle;
		}
		else{
			currentPreset = -1;
			presetStage = 0;
		}
		
		targetBoom = targetBoom/180.0;
		if(targetBoom < 0.3) targetBoom = 0.3;
		if(targetBoom > 0.7) targetBoom = 0.7;
		
		double boomDiff = targetBoom - boomAngle/180.0;
		if(Math.abs(boomDiff) < 0.005) boomDiff = 0;
		double boomV = boomDiff * 25;
		if(boomV != 0)
			if(Math.abs(boomV) < 2.5) boomV = (boomV < 0)?-2.5:2.5;
		try{
			boomLeftJag.setX(boomV/12.0);
			boomRightJag.setX(boomV/12.0);
		}
		catch(Exception e){
		}

		targetStick = targetStick/180.0;
		if(targetStick < 0.3) targetStick = 0.3;
		if(targetStick > 0.7) targetStick = 0.7;
		double stickDiff = targetStick - stickAngle/180.0;
		if(Math.abs(stickDiff) < 0.005) stickDiff = 0;
		double stickV = stickDiff * -25;
		if(stickV != 0)
			if(Math.abs(stickV) < 2.5) stickV = (stickV < 0)?-2.5:2.5;
		try{
			stickLeftJag.setX(stickV/12.0);
			stickRightJag.setX(stickV/12.0);
		}
		catch(Exception e){
		}*/

		double targetboom = (m_joystick.getRawAxis(1)+1)*0.3+0.2;
		if(targetboom < 0.2) targetboom = 0.2;
		if(targetboom > 0.8) targetboom = 0.8;
		double boomDiff = 0;
		try{
			if(boomLeftJag.getPosition()!=0)
				boomDiff = targetboom - boomLeftJag.getPosition();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(Math.abs(boomDiff) < 0.005) boomDiff = 0;
		double boomV = boomDiff * 25;
		if(boomV != 0)
			if(Math.abs(boomV) < 2.5) boomV = (boomV < 0)?-2.5:2.5;
		try{
			boomLeftJag.setX(boomV/12.0);
			boomRightJag.setX(boomV/12.0);
		}
		catch(Exception e){
		}

		double targetstick = (-m_joystick.getRawAxis(3)+1)*0.3+0.2;
		if(targetstick < 0.2) targetstick = 0.2;
		if(targetstick > 0.8) targetstick = 0.8;
		double stickDiff = 0;
		try{
			if(stickLeftJag.getPosition() != 0)
				stickDiff = targetstick - stickLeftJag.getPosition();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(Math.abs(stickDiff) < 0.005) stickDiff = 0;
		double stickV = stickDiff * -35;
		if(stickV != 0)
			if(Math.abs(stickV) < 2.5) stickV = (stickV < 0)?-2.5:2.5;
		try{
			stickLeftJag.setX(stickV/12.0);
			stickRightJag.setX(stickV/12.0);
		}
		catch(Exception e){
		}
    }

    int GetLoopsPerSec() {
        return 20;
    }

    void updateDashboard() {
        Dashboard lowDashData = ds.getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 4;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                    lowDashData.addCluster();
                    {
                        int module = 6;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();
        }
        lowDashData.finalizeCluster();

        lowDashData.addByte(gripperSolenoid.getAll());

        lowDashData.commit();
    }
}