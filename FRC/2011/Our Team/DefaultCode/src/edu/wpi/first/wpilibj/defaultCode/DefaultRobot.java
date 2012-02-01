/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.defaultCode;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Compressor;
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

public class DefaultRobot extends IterativeRobot {
    DriverStation ds;
	boolean[] dsButtons = new boolean[14];
	
    // Declare variable for the robot drive system
    //MotorControl m_robotDrive;
    RobotDrive m_robotDrive;
    CANJaguar frontLeftJag;
    CANJaguar frontRightJag;
    CANJaguar rearLeftJag;
    CANJaguar rearRightJag;
	boolean arcadeDrive = true;
	boolean override = true;
    int lastArcadeButton = 0;
    int lastOverButton = 0;
    int lastGripperButton = 0;

    CANJaguar boomRightJag;
    CANJaguar boomLeftJag;
    CANJaguar stickRightJag;
    CANJaguar stickLeftJag;
    CANJaguar wristJag;
	
	Compressor compressor;
	int lastStartingButton = 0;
	Servo topLeftRoller;
	Servo topRightRoller;
	Servo bottomLeftRoller;
	Servo bottomRightRoller;
	DigitalInput gripperButton;
    Solenoid gripperSolenoid;

	double rollerValue = 0;

    double boomAngle = 0.5;
    double stickAngle = 0.5;
    double wristAngle = 0.5;
    double lastTargetHeight = 20;
	double lastTargetLength = 20;
	double lastTargetWrist = 0;
    double currentHeight = 20;
    double currentLength = 20;
	double targetLength = 20;
    double targetHeight = 20;
    double targetWrist = 0;
    double heightIncrement = 0;
    double lengthIncrement = 0;
	double wristIncrement = 0;
	int middlePeg = 0;
	int numIncrements = 1;
    int currentPreset = 10;
    int presetStage = 0;
	boolean boomHitTarget = false;
	boolean stickHitTarget = false;
	boolean wristHitTarget = false;
	int boomStillBuffer = 0;
	int stickStillBuffer = 0;
	int wristStillBuffer = 0;
	boolean presetButton = false;
	double lastWristPot = 1.61;
	
	int dT = 1;
	double integral = 0;
	double lastError = 0;

    Servo deployServo;
    boolean deploy = false;

    DigitalInput leftLineSensor;
    DigitalInput middleLineSensor;
    DigitalInput rightLineSensor;
    int[] lastThreeLines = new int[3];
    int lineLoops = 0;
    boolean hitY = false;
    boolean hasStopped = false;
    int ignoreLines = 0;
    boolean doAutoArmStuff = false;
	int yLoops = 0;
	int dropLoops = 0;
	boolean droppedTube = false;
	int backwardsLoops = 0;

    int m_dsPacketsReceivedInCurrentSecond;	// keep track of the ds packets received in the current second

    // Declare variables for the joystick being used
    Joystick m_joystick;

    static final int NUM_BUTTONS = 10;
    int[] m_buttons = new int[NUM_BUTTONS];

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
    public DefaultRobot() {
        System.out.println("BuiltinDefaultCode Constructor Started\n");

        ds = DriverStation.getInstance();

        // Create a robot using standard right/left robot drive on PWMS 1, 2, 3, and #4

        try {
            frontLeftJag = new CANJaguar(4);
            frontRightJag = new CANJaguar(5);
            rearLeftJag = new CANJaguar(3);
            rearRightJag = new CANJaguar(2);
        } catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
//        try {
//            frontLeftJag = new CANJaguar(4);
//            frontRightJag = new CANJaguar(5);
//            rearLeftJag = new CANJaguar(3, CANJaguar.ControlMode.kPosition);
//            rearRightJag = new CANJaguar(2);
//
//			for(int setThatMode = 0; setThatMode < 5; setThatMode++){
//				rearLeftJag.changeControlMode(CANJaguar.ControlMode.kPosition);
//				Thread.sleep(100);
//				if(rearLeftJag.getControlMode()==CANJaguar.ControlMode.kPosition);
//					break;
//			}
//            rearLeftJag.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
//			rearLeftJag.configEncoderCodesPerRev(720);
//			rearLeftJag.enableControl(0);
//			rearLeftJag.disableControl();
//
//			for(int setThatMode = 0; setThatMode < 5; setThatMode++){
//				rearLeftJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
//				Thread.sleep(100);
//				if(rearLeftJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus)
//					break;
//			}
//
//        } catch (Exception e) {
//			System.out.println(e.getMessage());
//		}

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
        } catch (Exception e) {
			System.out.println(e.getMessage());
		 }*/

        try {
            boomRightJag = new CANJaguar(6, CANJaguar.ControlMode.kPosition);
            boomLeftJag = new CANJaguar(9, CANJaguar.ControlMode.kPosition);
            stickRightJag = new CANJaguar(7, CANJaguar.ControlMode.kPosition);
            stickLeftJag = new CANJaguar(8, CANJaguar.ControlMode.kPosition);
            wristJag = new CANJaguar(10, CANJaguar.ControlMode.kPosition);

			for(int setThatMode = 0; setThatMode < 5; setThatMode++){
				boomRightJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				boomLeftJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				stickRightJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				stickLeftJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				wristJag.changeControlMode(CANJaguar.ControlMode.kPosition);
				Thread.sleep(100);
				if(boomRightJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& boomLeftJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& stickLeftJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& stickRightJag.getControlMode()==CANJaguar.ControlMode.kPosition
						&& wristJag.getControlMode()==CANJaguar.ControlMode.kPosition);
					break;
			}

            boomRightJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            boomLeftJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            stickRightJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            stickLeftJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            wristJag.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
			
			for(int setThatMode = 0; setThatMode < 5; setThatMode++){
				boomRightJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				boomLeftJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				stickRightJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				stickLeftJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				wristJag.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
				Thread.sleep(100);
				if(boomRightJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& boomLeftJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& stickLeftJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& stickRightJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus
						&& wristJag.getControlMode()==CANJaguar.ControlMode.kPercentVbus)
					break;
			}

        } catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		gripperSolenoid = new Solenoid(1);
		compressor = new Compressor(1, 1);
		compressor.start();
		topLeftRoller = new Servo(2);
		topRightRoller = new Servo(3);
		bottomLeftRoller = new Servo(4);
		bottomRightRoller = new Servo(5);
		gripperButton = new DigitalInput(2);
		
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
		deploy = false;
    }

    public void autonomousInit() {
        m_autoPeriodicLoops = 0;
		/*
		 try{
			boomAngle = (boomLeftJag.getPosition()+boomRightJag.getPosition())/2.0 * 180;
			stickAngle = (stickLeftJag.getPosition()+stickRightJag.getPosition())/2.0 * 180;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(boomAngle != 0 && stickAngle != 0) doAutoArmStuff = true;
		*/
		try{
            double bLJP = boomLeftJag.getPosition();
            double sLJP = stickRightJag.getPosition();
            double wJP = wristJag.getPosition();
            boomAngle = (180-(bLJP*270-45));
            stickAngle = (180-(sLJP*270-45));
            wristAngle = (180-(wJP*270-45));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        double[] armCoords = getWristCoordinates(boomAngle, stickAngle);
        currentHeight = armCoords[1];
        currentLength = armCoords[0];

		lineLoops = 0;
		dropLoops = 0;
		yLoops = 0;
		hitY = false;
		hasStopped = false;
		droppedTube = false;
		backwardsLoops = 0;
		ignoreLines = 0;
		
		currentPreset = 10;
		targetHeight = 20;
		targetLength = 18;
		lastTargetHeight = Math.max(currentHeight, 20.0);
		lastTargetLength = currentLength;
		lastTargetWrist = wristAngle;
		targetWrist = 0;
		middlePeg = 1;
		int yIncrements = (int)Math.abs(Math.floor((targetHeight - lastTargetHeight)/3.0))+1;
		int xIncrements = (int)Math.abs(Math.floor((targetLength - lastTargetLength)/1.0))+1;
		numIncrements = Math.max(xIncrements, yIncrements);
		//			numIncrements *= 2;
		heightIncrement = (targetHeight - lastTargetHeight)/(double)numIncrements;
		lengthIncrement = (targetLength - lastTargetLength)/(double)numIncrements;
		wristIncrement = (targetWrist - lastTargetWrist)/(double)numIncrements;
    }

    public void teleopInit() {
        m_telePeriodicLoops = 0;                    // Reset the loop counter for teleop mode
        m_dsPacketsReceivedInCurrentSecond = 0;     // Reset the number of dsPackets in current second
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
		dropLoops = 0;
		yLoops = 0;
		hitY = false;
		hasStopped = false;
		droppedTube = false;
		backwardsLoops = 0;
		ignoreLines = 0;
		deploy = false;

//		try{
//			float bLJP = (float)rearLeftJag.getPosition();
//			System.out.println("Nya? "+bLJP);
//		}
//		catch(Exception e){
//			System.out.println(e.getMessage());
//		}

		try{
			float bLJP = (float)boomLeftJag.getPosition();
			float sLJP = (float)stickRightJag.getPosition();
			float wJP = (float)wristJag.getPosition();
            boomAngle = (180-(bLJP*270-45));
            stickAngle = (180-(sLJP*270-45));
            wristAngle = (180-(wJP*270-45));
			double[] armCoords = getWristCoordinates(boomAngle, stickAngle);
			//System.out.println(""+(int)armCoords[0]+", "+(int)armCoords[1]+")");
			//System.out.print("Boom: "+bLJP+" | Stick: "+sLJP+" | Wrist: "+wJP+"\t|\t");
			//System.out.println(""+(float)boomAngle+", "+(float)stickAngle+", "+(float)wristAngle);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		double bPot = 0;
		double sPot = 0;
		double wPot = 0;
        try{
			bPot = ds.getEnhancedIO().getAnalogIn(2);
			sPot = ds.getEnhancedIO().getAnalogIn(4);
			wPot = ds.getEnhancedIO().getAnalogIn(6);
			//System.out.println(""+(float)bPot+", "+(float)sPot+", "+(float)wPot);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
    }

    public void autonomousPeriodic() {
		updateDashboard();
		// feed the user watchdog at every period when in autonomous
		Watchdog.getInstance().feed();

		m_autoPeriodicLoops++;

		deployServo.setAngle(140);

		boolean straightLine = true;
		boolean goLeft = false;
		boolean doAuto = false;

        try{
			double sPot = ds.getEnhancedIO().getAnalogIn(4); //stick
			double wPot = ds.getEnhancedIO().getAnalogIn(6); //wrist
			/*if(sPot < 0.66*3.3){
				straightLine = false;
				if(sPot < 0.33*3.3)
					goLeft = true;
			}*/
			if(wPot > 0.5*3.3) doAuto = true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		if(doAuto){
		if (!(hasStopped || hitY)) {

			int lineReading = 0;
			lineReading += (leftLineSensor.get()) ? 0 : 100;
			lineReading += (middleLineSensor.get()) ? 0 : 10;
			lineReading += (rightLineSensor.get()) ? 0 : 1;
			//System.out.print("" + lineReading + " | ");

			double rightMotors = 0;
			double leftMotors = 0;

			switch (lineReading) {
				case   0:
					rightMotors = 0.7 * (lastThreeLines[0] % 10 + lastThreeLines[1] % 10 + lastThreeLines[2] % 10) / 3.0;
					leftMotors = 0.7 * (Math.floor(lastThreeLines[0]/100)%10 + Math.floor(lastThreeLines[1]/100)%10 + Math.floor(lastThreeLines[2]/100)%10)/3.0;
					//if(leftMotors == 0 && rightMotors == 0) hasStopped = true;
					break;
				case   1: leftMotors = 0.55; rightMotors = 0.7; break;
				case  11: leftMotors = 0.6; rightMotors = 0.7; break;
				case  10: leftMotors = 0.8; rightMotors = 0.8; break;
				case 110: leftMotors = 0.7; rightMotors = 0.6; break;
				case 100: leftMotors = 0.7; rightMotors = 0.55; break;
				case 111:
					if (straightLine) {
						hasStopped = true;
						System.out.print("STOPPED! | ");
						dropLoops = m_autoPeriodicLoops;
					}
					else {
						if (!goLeft) {
							leftMotors = 0.7;
							rightMotors = -0.7;
							lineReading = 100;
						}
						else {
							leftMotors = -0.7;
							rightMotors = 0.7;
							lineReading = 1;
						}
						hitY = true;
						System.out.print("HIT! | ");
						yLoops = m_autoPeriodicLoops;
					}
					break;
				case 101:
					if (!straightLine) {
						if (!goLeft) {
							leftMotors = 0.7;
							rightMotors = -0.7;
							lineReading = 100;
						}
						else {
							leftMotors = -0.7;
							rightMotors = 0.7;
							lineReading = 1;
						}
						hitY = true;
						System.out.print("HIT! | ");
						yLoops = m_autoPeriodicLoops;
					}
					break;
				default: break;
			}

			m_robotDrive.tankDrive(-leftMotors*0.87, -rightMotors*0.87);
			//System.out.println("Left: " + (float)(leftMotors) + ", Right: " + (float)(rightMotors));

			if(lineReading > 0) lastThreeLines[lineLoops%3] = lineReading;
			lineLoops++;
		}
        else if(currentPreset == -1 && m_autoPeriodicLoops < dropLoops + 10){
        }
		else if(!droppedTube){
			presetStage = 0;
			lastTargetHeight = Math.max(currentHeight, 20.0);
			lastTargetLength = currentLength;
			lastTargetWrist = wristAngle;
			targetWrist = 65;
			middlePeg = 0;
			targetHeight = currentHeight-6;
			targetLength = currentLength-6;
			gripperSolenoid.set(true);
			droppedTube = true;
			currentPreset = 0;
			int yIncrements = (int)Math.abs(Math.floor((targetHeight - lastTargetHeight)/3.0))+1;
			int xIncrements = (int)Math.abs(Math.floor((targetLength - lastTargetLength)/1.0))+1;
			numIncrements = Math.max(xIncrements, yIncrements);
			//		numIncrements *= 2;
			heightIncrement = (targetHeight - lastTargetHeight)/(double)numIncrements;
			lengthIncrement = (targetLength - lastTargetLength)/(double)numIncrements;
			wristIncrement = (targetWrist - lastTargetWrist)/(double)numIncrements;
		}
		else if(currentPreset == 0){
			System.out.println("DERP");
			m_robotDrive.tankDrive(0.5, 0.5);
			backwardsLoops = m_autoPeriodicLoops;
		}
		else if(currentPreset == -1){
			System.out.println("DERP");
			m_robotDrive.tankDrive(0.5, 0.5);
		}




		
        try{
            double bLJP = boomLeftJag.getPosition();
            double sLJP = stickRightJag.getPosition();
            double wJP = wristJag.getPosition();
            boomAngle = (180-(bLJP*270-45));
            stickAngle = (180-(sLJP*270-45));
            wristAngle = (180-(wJP*270-45));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        double[] armCoords = getWristCoordinates(boomAngle, stickAngle);
        currentHeight = armCoords[1];
        currentLength = armCoords[0];
        double[] targetAngles = {90, 90};
		double targetWristA = 90;



		if(currentPreset > -1){
			if((presetStage+1) % numIncrements == 0){
				boolean defaultCase = false;
				switch(currentPreset){
					//case 1: targetHeight = 25+8*middlePeg; targetLength = 2; currentPreset = 11; targetWrist = 45; break;
					//case 4: targetHeight = 55+8*middlePeg; targetLength = 2; currentPreset = 41; targetWrist = 20; break;
					case 10: targetHeight = (92+8*middlePeg)/2.0; targetLength = 18; currentPreset = 101; targetWrist = 45; break;
					case 101: targetHeight = 92+8*middlePeg; targetLength = 17; currentPreset = 102; targetWrist = 110; break;
					case 0: targetHeight = 20; targetLength = 18; targetWrist = 45; currentPreset = 3;
						
						break;
					case 3: targetHeight = 20; targetLength = 18; targetWrist = 0; gripperSolenoid.set(false); currentPreset = 31; break;
					default:
						currentPreset = -1;
						defaultCase = true;
						targetAngles = getArmAngles(targetLength, targetHeight);
						targetWristA = targetWrist;
						break;
				}
				presetStage = 0;
				if(!defaultCase){
					lastTargetHeight = currentHeight;
					lastTargetLength = currentLength;
					lastTargetWrist = wristAngle;
					int yIncrements = (int)Math.abs(Math.floor((targetHeight - lastTargetHeight)/3.0))+1;
					int xIncrements = (int)Math.abs(Math.floor((targetLength - lastTargetLength)/1.0))+1;
					numIncrements = Math.max(xIncrements, yIncrements);
					//	numIncrements *= 2;
					heightIncrement = (targetHeight - lastTargetHeight)/(double)numIncrements;
					lengthIncrement = (targetLength - lastTargetLength)/(double)numIncrements;
					wristIncrement = (targetWrist - lastTargetWrist)/(double)numIncrements;
					targetAngles[0] = boomAngle;
					targetAngles[1] = stickAngle;
					targetWristA = wristAngle;
				}
			}
			else{
				presetStage++;
				double tX = lastTargetLength+lengthIncrement*presetStage;
				double tY = lastTargetHeight+heightIncrement*presetStage;
				targetWristA = lastTargetWrist+wristIncrement*presetStage;
				targetAngles = getArmAngles(tX, tY);
				if((presetStage + 1) % numIncrements == 0){
					System.out.println(""+targetAngles[0]+", "+targetAngles[1]+", "+targetWristA);
					double targetboom = 1-(targetAngles[0] + 45) / 270.0;
					double targetstick = 1-(targetAngles[1]+45)/270.0;
					double targetwrist = 1-(targetWristA+45)/270.0;
					System.out.println("Pots: "+(float)targetboom+", "+(float)targetstick+", "+(float)targetwrist);
					float TboomAngle = (float)(180-(targetboom*270-45));
					float TstickAngle = (float)(180-(targetstick*270-45));
					float TwristAngle = (float)(180-(targetwrist*270-45));
					System.out.println("Recalculated Angles:"+TboomAngle+", "+TstickAngle+", "+TwristAngle+"");
				}
			}
        }
        else{
			targetAngles = getArmAngles(targetLength, targetHeight);
			targetWristA = targetWrist;
        }

        double boomV = 0;
		double targetboom = 1-(targetAngles[0] + 45) / 270.0;
		double boomDiff = 0;
		try{
			if(boomLeftJag.getPosition()!=0)
				boomDiff = targetboom - boomLeftJag.getPosition();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		double boomGain = 60;
		if(boomDiff > 0) boomGain = 230;
		boomV = (-1*boomGain * boomDiff);
		double boomMaxV = 9*1;
		if(Math.abs(boomV) > boomMaxV) boomV = (boomV < boomMaxV)?-boomMaxV:boomMaxV;
		if(Math.abs(boomV) > 1.5){
			if(Math.abs(boomV) < 3.25)  boomV = (boomV < 0)?-3.25:3.25;
		}
		else boomV  = 0;
        try{
            boomLeftJag.setX(boomV/12.0);
            boomRightJag.setX(boomV/12.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        double stickV = 0;
		double targetstick = 1-(targetAngles[1]+45)/270.0;
		double stickDiff = 0;
		try{
			if(stickRightJag.getPosition() != 0)
				stickDiff = targetstick - stickRightJag.getPosition();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		stickV = 1 * (150 * stickDiff);
		if(Math.abs(stickV) > 9) stickV = (stickV < 0)?-9:9;
		if(Math.abs(stickV) > 1.0){
			if(Math.abs(stickV) < 3.35)  stickV = (stickV < 0)?-3.35:3.35;
		}
		else stickV = 0;
        try{
            stickLeftJag.setX(stickV/12.0);
            stickRightJag.setX(stickV/12.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        double wristV = 0;
		double targetwrist = 1-(targetWristA+45)/270.0;
		double wristDiff = 0;
		try{
			if(wristJag.getPosition() != 0)
				wristDiff = targetwrist - wristJag.getPosition();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		wristV = -1 * (35 * wristDiff);
		if(Math.abs(wristV) > 12) wristV = (wristV < 0)?-12:12;
		if(Math.abs(wristV) > 0.2){
			if(Math.abs(wristV) < 1.0)  wristV = (wristV < 0)?-1.0:1.0;
		}
		else wristV = 0;
        try{
            wristJag.setX(wristV/12.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
		}
    }

    public void teleopPeriodic() {

        // feed the user watchdog at every period when in autonomous
        Watchdog.getInstance().feed();
        // increment the number of teleop periodic loops completed
        m_telePeriodicLoops++;
        // increment DS packets received
        m_dsPacketsReceivedInCurrentSecond++;

        updateDashboard();
        
        try{
            double bLJP = boomLeftJag.getPosition();
            double sLJP = stickRightJag.getPosition();
            double wJP = wristJag.getPosition();
            boomAngle = (180-(bLJP*270-45));
            stickAngle = (180-(sLJP*270-45));
            wristAngle = (180-(wJP*270-45));
			//System.out.println(""+(float)boomAngle+", "+(float)stickAngle+", "+(float)wristAngle);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        double[] armCoords = getWristCoordinates(boomAngle, stickAngle);
        currentHeight = armCoords[1];
        currentLength = armCoords[0];
        //double[] targetAngles = {90, 90, 90};

        // Update which buttons are being pressed
        lastOverButton = m_buttons[9];
        lastArcadeButton = m_buttons[2];
        lastStartingButton = m_buttons[3];
        lastGripperButton = m_buttons[5];
        //lastGripperButton = dsButtons[9];
        for (int buttonDex = 0; buttonDex < NUM_BUTTONS; buttonDex++) {
			m_buttons[buttonDex] = (int)(m_joystick.getRawButton(buttonDex+1)?1:0);
        }
        if(m_buttons[2] == 1 && lastArcadeButton == 0){
			arcadeDrive = !arcadeDrive;
        }
        if(m_buttons[9] == 1 && lastOverButton == 0){
			override = !override;
        }
        if(m_buttons[5] == 1 && lastGripperButton == 0){
			gripperSolenoid.set(!gripperSolenoid.get());
        }
		double bPot = 0.5*3.3;
		double sPot = 0.5*3.3;
		double wPot = 0.5*3.3;
		boolean rotateUp = false;
		boolean rotateDown = false;
		boolean deployment = false;
        try{
			ds.getEnhancedIO().setLED(1, arcadeDrive);
			ds.getEnhancedIO().setLED(2, (m_buttons[6]==1));
			ds.getEnhancedIO().setLED(3, override);
			for (int buttonDex = 0; buttonDex < 14; buttonDex++) {
				dsButtons[buttonDex] = !ds.getEnhancedIO().getDigital(buttonDex+1);
			}
			rotateUp = dsButtons[9];
			rotateDown = dsButtons[7];
			deployment = dsButtons[11];
			//deploy: 12
			//down: 8
			//up: 10
			bPot = ds.getEnhancedIO().getAnalogIn(2);
			sPot = ds.getEnhancedIO().getAnalogIn(4);
			wPot = ds.getEnhancedIO().getAnalogIn(6);

			/*
			if(Math.abs(wPot-1.61)>0.05 && Math.abs(wPot-lastWristPot)>0.05) targetWrist = 90 + 70*(1.61-wPot);
			lastWristPot = wPot;
			
			if(Math.abs(bPot-1.65)>0.1 || Math.abs(sPot-1.65)>0.1){
				currentPreset = -1;
				presetStage = 0;
				targetLength = currentLength + 3*(1.65-xPot);
				targetHeight = currentHeight + 3*(1.65-yPot);
			}*/
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

        //m_robotDrive.pidWrite(-m_joystick.getRawAxis(1));
        // drive with arcade style

		double halfArm = 1.0;
		//if(currentHeight > 50) halfArm = 0.66;

		if(!override){
			if(arcadeDrive){
				m_robotDrive.arcadeDrive((1-2*m_buttons[6])*m_joystick.getRawAxis(2)*halfArm, m_joystick.getRawAxis(4)/(3+(m_buttons[7]*3))*2*halfArm, false);
			}
			else{
				if(m_buttons[6]==1)
					m_robotDrive.tankDrive(-m_joystick.getRawAxis(3), -m_joystick.getRawAxis(2));
				else
					m_robotDrive.tankDrive(m_joystick.getRawAxis(2), m_joystick.getRawAxis(3));
			}
		}

        // Code for turning the servo to deploy the minibot
        if (m_buttons[4] == 1 || deployment)
            deploy = true;
		//else deploy = false;

        if (deploy)// && m_telePeriodicLoops > 50*110)
            deployServo.setAngle(80);
        else
            deployServo.setAngle(150);


        // DAT AAAAARM
		if(!override){
			if(gripperSolenoid.get() != dsButtons[0]){
				gripperSolenoid.set(dsButtons[0]);
			}
			if(rotateUp || rotateDown){
				double rotateValue = 0;
				rotateValue -= (rotateUp)?0.5:0;
				rotateValue += (rotateDown)?0.5:0;
				
				topLeftRoller.set((0.41452991452991456)+rotateValue);
				topRightRoller.set(0.44017094017094016-rotateValue);
				bottomRightRoller.set(0.44017094017094016-rotateValue);
				bottomLeftRoller.set((0.4230769230769231)+rotateValue);
				//deploy: 12
				//down: 8
				//up: 10
			}
			else if(gripperButton.get() && !dsButtons[0]){
				topLeftRoller.set((0.41452991452991456)+0.5);
				topRightRoller.set(0.44017094017094016-0.5);
				bottomLeftRoller.set(0.44017094017094016-0.5);
				bottomRightRoller.set((0.4230769230769231)+0.5);
			}
			else{
				topLeftRoller.set((0.41452991452991456));
				topRightRoller.set(0.44017094017094016);
				bottomLeftRoller.set(0.44017094017094016);
				bottomRightRoller.set((0.4230769230769231));
			}
		}

		//Servo center values
		//topleft 0.41452991452991456-0.41025641025641024 (0.4123901005)
		//topright 0.44017094017094016
		//bottomleft 0.44017094017094016
		//bottomright 0.4230769230769231

        double boomV = 0;
		if(!override)
		{
			//double targetboom = (targetAngles[0] + 45) / 270.0;
			double targetboom = (3.3-bPot)/3.71018053 + 0.06610589;
			//if(targetboom < 0.18) targetboom = 0.18;
			//if(targetboom > 0.82) targetboom = 0.82;
			double boomDiff = 0;
			try{
				if(boomLeftJag.getPosition()!=0)
					boomDiff = targetboom - boomLeftJag.getPosition();
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			/*if(Math.abs(boomDiff) > 0.01){
				boomHitTarget = false;
			}
			if(Math.abs(boomDiff) < 0.005){
				boomDiff = 0;
				if(!boomHitTarget){
					boomHitTarget = true;
					boomStillBuffer = 0;
				}
			}
			if(boomHitTarget){
				if(Math.abs(boomDiff) < 0.01){
					if(boomStillBuffer % 10 != 0){
					  boomDiff = 0;
					}
					boomStillBuffer ++;
				}
			}
			boomV = boomDiff * 120;
			double boomMinV = 4.0;
			if(boomV != 0)
				if(Math.abs(boomV) < boomMinV) boomV = (boomV < 0)?-boomMinV:boomMinV;*/

			double boomGain = 170;
			if(boomDiff > 0) boomGain = 80;
			boomV = ((boomGain*-1) * boomDiff);
			double boomMaxV = 9*1;
			if(Math.abs(boomV) > boomMaxV) boomV = (boomV < boomMaxV)?-boomMaxV:boomMaxV;
			if(Math.abs(boomV) > 1.5){
				if(Math.abs(boomV) < 3.25)  boomV = (boomV < 0)?-3.25:3.25;
			}
			else boomV = 0;
		}
		else{
			if(lastStartingButton == 1){
				double targetboom = 0.41152954;
				double boomDiff = 0;
				try{
					if(boomLeftJag.getPosition() != 0)
						boomDiff = targetboom - boomLeftJag.getPosition();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				boomV = (-160 * boomDiff);
				if(Math.abs(boomV) > 9) boomV = (boomV < 0)?-9:9;
				if(Math.abs(boomV) > 0.65){
					if(Math.abs(boomV) < 2.5)  boomV = (boomV < 0)?-3.25:3.25;
				}
				else boomV = 0;
			}
			else boomV = 12 * -m_joystick.getRawAxis(4);
		}
        try{
            boomLeftJag.setX(boomV/12.0);
            boomRightJag.setX(boomV/12.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        double stickV = 0;
		if(!override){
			//double targetstick = (targetAngles[1]+45)/270.0;
			double targetstick = sPot/3.490056 + 0.01808; //.1155
			//if(targetstick < 0.18) targetstick = 0.18;
			//if(targetstick > 0.82) targetstick = 0.82;
			double stickDiff = 0;
			try{
				if(stickRightJag.getPosition() != 0)
					stickDiff = targetstick - stickRightJag.getPosition();
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}/*
			if(Math.abs(stickDiff) > 0.01){
				stickHitTarget = false;
			}
			if(Math.abs(stickDiff) < 0.005){
				stickDiff = 0;
				if(!stickHitTarget){
					stickHitTarget = true;
					stickStillBuffer = 0;
				}
			}
			if(stickHitTarget){
				if(Math.abs(stickDiff) < 0.01){
					if(stickStillBuffer % 10 != 0){
					  stickDiff = 0;
					}
					stickStillBuffer ++;
				}
			}
			stickV = stickDiff * -250;
			double stickMinV = 4.0;
			if(stickV != 0)
				if(Math.abs(stickV) < stickMinV) stickV = (stickV < 0)?-stickMinV:stickMinV;*/

			stickV = ((150.0*1) * stickDiff);
			double stickMaxV = 9*1;
			if(Math.abs(stickV) > stickMaxV) stickV = (stickV < stickMaxV)?-stickMaxV:stickMaxV;
			if(Math.abs(stickV) > 1.0){
				if(Math.abs(stickV) < 3.35)  stickV = (stickV < 0)?-3.35:3.35;
			}
			else stickV = 0;
		}
		else{
			if(lastStartingButton == 1){
				double targetstick = 0.7986296;
				double stickDiff = 0;
				try{
					if(stickRightJag.getPosition() != 0)
						stickDiff = targetstick - stickRightJag.getPosition();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				stickV = 1 * (150 * stickDiff);
				if(Math.abs(stickV) > 12) stickV = (stickV < 0)?-12:12;
				if(Math.abs(stickV) > 0.85){
					if(Math.abs(stickV) < 3.35)  stickV = (stickV < 0)?-3.35:3.35;
				}
				else stickV = 0;
			}
			else stickV = 7 * -m_joystick.getRawAxis(3);
		}

        try{
            stickLeftJag.setX(stickV/12.0);
            stickRightJag.setX(stickV/12.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        double wristV = 0;
		if(!override){
			//double targetwrist = (targetWristA+45)/270.0;
			double targetwrist = (1-wPot/3.51774071) - 0.03755193;
			//if(targetwrist > (45.0+45.0)/270.0 && !gripperSolenoid.get())
				//targetwrist = (45.0+45.0)/270.0;
			double wristDiff = 0;
			try{
				if(wristJag.getPosition() != 0)
					wristDiff = targetwrist - wristJag.getPosition();
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			/*
			if(Math.abs(wristDiff) > 0.01){
				wristHitTarget = false;
			}
			if(Math.abs(wristDiff) < 0.005){
				wristDiff = 0;
				if(!wristHitTarget){
					wristHitTarget = true;
					wristStillBuffer = 0;
				}
			}
			if(wristHitTarget){
				if(Math.abs(wristDiff) < 0.01){
					if(wristStillBuffer % 10 != 0){
					  wristDiff = 0;
					}
					wristStillBuffer ++;
				}
			}
			wristV = wristDiff * -20;
			if(wristV != 0)
				if(Math.abs(wristV) < 1.5) wristV = (wristV < 0)?-1.5:1.5;*/

			wristV = ((-35.0*1) * wristDiff);
			double wristMaxV = 9*1;
			if(Math.abs(wristV) > wristMaxV) wristV = (wristV < wristMaxV)?-wristMaxV:wristMaxV;
			if(Math.abs(wristV) > 0.2){
				if(Math.abs(wristV) < 1.0)  wristV = (wristV < 0)?-1.0:1.0;
			}
			else wristV = 0;
		}
        else{
			if(lastStartingButton == 1){
				double targetwrist = 0.8758545;
				double wristDiff = 0;
				try{
					if(wristJag.getPosition() != 0)
						wristDiff = targetwrist - wristJag.getPosition();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				wristV = -1 * (35 * wristDiff);
				if(Math.abs(wristV) > 12) wristV = (wristV < 0)?-12:12;
				if(Math.abs(wristV) > 0.2){
					if(Math.abs(wristV) < 1.0)  wristV = (wristV < 0)?-1.0:1.0;
				}
				else wristV = 0;
			}
			else wristV = 4 * -m_joystick.getRawAxis(2);
		}
        try{
            wristJag.setX(wristV/12.0);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    double[] getArmAngles(double wristX, double wristY){
        double hypotenuse = Math.sqrt(MathUtils.pow(wristX, 2) + MathUtils.pow(wristY-23, 2));
        double stickBoomAngle = MathUtils.acos( (36*36 + 44*44 - hypotenuse*hypotenuse) / (2*36*44) );
        double hypotenuseHorizontalAngle = MathUtils.atan2((wristY-23), wristX);
        double hypotenuseBoomAngle = MathUtils.acos( (36*36 + hypotenuse*hypotenuse - 44*44) / (2*36*hypotenuse) );
        double boomHorizontalAngle = (hypotenuseHorizontalAngle + hypotenuseBoomAngle);
        double[] armAngles = {boomHorizontalAngle/Math.PI*180, stickBoomAngle/Math.PI*180};
        return armAngles;
    }

    double[] getWristCoordinates(double boomAngle, double stickAngle){
        double hypotenuse = Math.sqrt(36*36 + 44*44 - 2*36*44*Math.cos(stickAngle/180.0*Math.PI));
        double hypotenuseBoomAngle = MathUtils.acos( (36*36 + hypotenuse*hypotenuse - 44*44) / (2*36*hypotenuse) );
        double hypotenuseHorizontalAngle = boomAngle/180.0*Math.PI - hypotenuseBoomAngle;
        double xCoord = hypotenuse*Math.cos(hypotenuseHorizontalAngle);
        double yCoord = hypotenuse*Math.sin(hypotenuseHorizontalAngle) + 23;
        double[] wristCoords = {xCoord, yCoord};
        return wristCoords;
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

        lowDashData.addByte(Solenoid.getAllFromDefaultModule());

        lowDashData.commit();
    }
}