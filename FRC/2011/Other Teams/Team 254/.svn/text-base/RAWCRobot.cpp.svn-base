//=============================================================================
// File: RAWCRobot.cpp
//
// COPYRIGHT 2010 Robotics Alliance of the West Coast(RAWC)
// All rights reserved.  RAWC proprietary and confidential.
//             
// The party receiving this software directly from RAWC (the "Recipient")
// may use this software and make copies thereof as reasonably necessary solely
// for the purposes set forth in the agreement between the Recipient and
// RAWC(the "Agreement").  The software may be used in source code form
// solely by the Recipient's employees/volunteers.  The Recipient shall have 
// no right to sublicense, assign, transfer or otherwise provide the source
// code to any third party. Subject to the terms and conditions set forth in
// the Agreement, this software, in binary form only, may be distributed by
// the Recipient to its users. RAWC retains all ownership rights in and to
// the software.
//
// This notice shall supercede any other notices contained within the software.
//=============================================================================
#include "RAWCRobot.h"
#include "subsystems/Kicker.h"
#include <math.h>
#include "RAWCConstants.h"
RAWCRobot* RAWCRobot::singletonInstance = NULL;

#define DRIVECODELCDDEBUGER 1

/// Creates (if needed) and returns a singleton instance of the RAWCRobot
RAWCRobot * RAWCRobot::getInstance()
{
	// If we have not created a robot yet, do so.
	// If we have created a robot, skip this
	if( singletonInstance == NULL){
		singletonInstance = new RAWCRobot();
	}
	return singletonInstance;
}

/// Constructor for RAWCRobot
RAWCRobot::RAWCRobot()
{
	// Set up drive motors
	rightDriveA = new Victor(RIGHT_DRIVE_PWM_A);
	rightDriveB = new Victor(RIGHT_DRIVE_PWM_B);
	leftDriveA = new Victor(LEFT_DRIVE_PWM_A);
	leftDriveB = new Victor(LEFT_DRIVE_PWM_B);
	
	//Solenoids
	shifterA = new Solenoid(SHIFTER_A_CHAN);
	shifterB = new Solenoid(SHIFTER_B_CHAN);
	ptoA = new Solenoid(PTO_A_CHAN);
	ptoB = new Solenoid(PTO_B_CHAN);
	
	upperStageRatchet = new Solenoid(UPPER_STAGE_RATCHET_SOLENOID_CHAN);
	
	compressorSignal = new DigitalInput(COMPRESSOR_SWITCH);
	autoHangSwitch = new DigitalInput(AUTO_HANG_SWITCH);
	armDeployLimit = new DigitalInput (ARM_DEPLOY_LIMIT);
	stopHangingLimit = new DigitalInput(STOP_HANGING_LIMIT);
	
	compressorRelay = new Relay(COMPRESSOR_RELAY,Relay::kForwardOnly);
	
	//Make Sub Systems
	//TODO : Fix these magic numbers
	
	kicker = new Kicker(KICKER_MOTOR_CHAN, KICKER_SOLENOID_CHAN, 
			ROLLER_MOTOR_CHAN, KICKER_ENC_A_CHAN, KICKER_ENC_B_CHAN,
			ROLLER_ENC_A_CHAN, ROLLER_ENC_B_CHAN);
	
	
	// Set up encoders
	leftDriveEncoder = new Encoder(LEFT_ENCODER_A_CHAN,LEFT_ENCODER_B_CHAN,true);
	rightDriveEncoder = new Encoder(RIGHT_ENCODER_A_CHAN,RIGHT_ENCODER_B_CHAN);
	leftDriveEncoder->SetDistancePerPulse(0.1007081038552321);
	leftDriveEncoder->Start();
	rightDriveEncoder->Start();
	leftDriveEncoder->Reset();
	
	gyro = new Gyro(GYRO_IN_CHAN);
	//gyro->SetSensitivity(0.0052083333333333);
	gyro->SetSensitivity(0.0033); //Kiets
	
	pitchGyro = new Gyro(PITCH_GYRO_IN_CHAN);
	pitchGyro->SetSensitivity(0.004);
	//pitchGyro->SetSensitivity(0.0052083333333333);
	
	js = new Joystick(1);
	
	shifterCounts = 0;
	currentShiftState = SHIFTER_STATE_HIGH;
	ptoCounts= 0;
	currentPTOState = PTO_STATE_LOCKED;
	
	wantToDeployArm = false;
	wantToHang = false;
	wantToSelfRight = false;
	wantToResetHang = false;
	ptoJimmyCounts = 0;
	
	wantedUpperStage = 0;
	wantedLowerStage = 0;
	
	wantedLeftDrive = 0;
	wantedRightDrive = 0;
	
	previousAngle = 0.0;
	
	reset = false;
	
	wantToFixArm = false;
	
	// Initially, we are in drive mode
	setMode(DRIVE_MODE);
}

/// Used to handle the recurring logic funtions inside the robot.
/// Please call this once per update cycle.
void RAWCRobot::handle()
{	
	// Drive/PTO handoff
	RobotModes nextMode = mode;
	// Default drive
	float tmpLeftMotor = wantedLeftDrive;
	float tmpRightMotor = wantedRightDrive;
	bool lockUpperStage = false;
	
	ShifterPositions tmpShiftPos = wantedShifterPosition;
	PTOPositions tmpPTOPos = PTO_POS_LOCKED;
	

	// TODO: remove this
	static int waitForArmCounts = 0;
	static int waitForSelfSightCounts = 0;
	static int lowerLockCounts = 0;
	
	switch(mode){
	// Drive mode logic
	case DRIVE_MODE:
		//nothing much should happen here
		tmpPTOPos = PTO_POS_LOCKED;
		ptoJimmyCounts = 0;
		lowerLockCounts = 0;
		kicker->enable();
		if(wantToDeployArm)
		{
			nextMode = ARM_DEPLOYING_MODE;
		}
		
		if(wantToSelfRight){
			nextMode = SELF_RIGHT_MODE;
		}
		waitForArmCounts = 0;
		waitForSelfSightCounts = 0;
		wantToResetHang = false;
		break;
		
	case AUTO_HANG_MODE:
		ptoJimmyCounts = 0;
		tmpPTOPos = PTO_POS_ENGAGED;
		tmpShiftPos = SHIFTER_POS_NEUTRAL;
		lockUpperStage = true;
		tmpLeftMotor = 0;
		kicker->disable();
		kicker->lock();
		tmpRightMotor = 1;
		wantToHang = false;
		if( ! wantToDeployArm ){
			nextMode = HANG_MODE;
		}
		if( ! stopHangingLimit->Get()){
			nextMode = HANG_MODE;
		}
			
		break;
		
	case HANG_MODE:
		ptoJimmyCounts = 0;
		tmpPTOPos = PTO_POS_ENGAGED;
		tmpShiftPos = SHIFTER_POS_NEUTRAL;
		lockUpperStage = true;
		tmpLeftMotor = 0;
		kicker->disable();
		kicker->lock();
		tmpRightMotor = (wantToHang) ? 1 : 0;
		wantToHang = false;
		if( wantToResetHang )
		{
			nextMode = DRIVE_MODE;
		}
		
		break;
		
	case ARM_DEPLOYING_MODE:
		tmpLeftMotor = -1.00 ;
		tmpRightMotor = -.375;
		// This has to be like this so the ratchet doesnt catch
		tmpShiftPos = SHIFTER_POS_NEUTRAL;
		tmpPTOPos = PTO_POS_ENGAGED;
		
		// Wait for the shifter to go to N and unlock the ratchet
		// before you fire PTO cylinder
		if(ptoJimmyCounts < SHIFT_TRANS_PAUSE * 2){
			tmpLeftMotor = 0;
			tmpRightMotor = 0;
			tmpPTOPos = PTO_POS_LOCKED;// this is important
			; // Add stuff for jimmying here
		}
		
		ptoJimmyCounts++;
		
		if( ! armDeployLimit->Get())
		{
			nextMode = ARM_DEPLOYED_MODE;
		}
		
	
		kicker->kick(); //disengage the ratchet pawls
		kicker->disable();
		if(wantToHang){
			nextMode = HANG_MODE;
		}
		if( ! wantToDeployArm ){
			nextMode = DRIVE_MODE;
		}
			
		break;
		
	case ARM_DEPLOYED_MODE:
		
		tmpPTOPos = PTO_POS_NEUTRAL;
		lowerLockCounts++;
	
		if(lowerLockCounts < 7){
			tmpShiftPos = SHIFTER_POS_NEUTRAL;
			tmpPTOPos = PTO_POS_ENGAGED;
			tmpRightMotor = 0;
			tmpLeftMotor = .0800;
			kicker->kick();
		}
		else if(lowerLockCounts < 30){
			tmpLeftMotor = .0;
			tmpRightMotor = 0;
			tmpShiftPos = SHIFTER_POS_NEUTRAL;
			tmpPTOPos = PTO_POS_NEUTRAL;
		}
		else {
			// fix the arm
			if(wantToFixArm){
				tmpPTOPos = PTO_POS_ENGAGED;
				tmpShiftPos = SHIFTER_POS_NEUTRAL;
				tmpRightMotor = -.475;
			}
			kicker->lock(); 
		}
	
		if(!autoHangSwitch->Get()){
			nextMode = AUTO_HANG_MODE;
			lowerLockCounts = 0;
		}
		if(wantToHang){
			nextMode = HANG_MODE;
			lowerLockCounts = 0;
		}
		if( ! wantToDeployArm){
			nextMode = DRIVE_MODE;
			lowerLockCounts = 0;
		}

		break;
	
	case SELF_RIGHT_MODE:
		tmpPTOPos = PTO_POS_ENGAGED;
		tmpShiftPos = SHIFTER_POS_NEUTRAL;
		kicker->kick();
		tmpLeftMotor = 0;
		tmpRightMotor = 0;
		if(waitForSelfSightCounts > 14){
			tmpLeftMotor = -1 ;
		}
		waitForSelfSightCounts++;
		
		if( ! armDeployLimit->Get() )
		{
			tmpLeftMotor = 0;
			nextMode = SELF_RIGHT_RESET;
		}
		
	
		break;
		
	case SELF_RIGHT_RESET:
		static int goBackDownCounts = 0;
		tmpPTOPos = PTO_POS_ENGAGED;
		tmpShiftPos = SHIFTER_POS_NEUTRAL;
		kicker->kick();
		tmpLeftMotor = 0;
		tmpRightMotor = 0;
		if( !wantToSelfRight ){
			if(goBackDownCounts < 30)
				tmpLeftMotor = 0;
			if(goBackDownCounts < 20)
				tmpLeftMotor = 1;
			else{
				nextMode = DRIVE_MODE;
				goBackDownCounts = 0;
			}
			goBackDownCounts++;
		}
		break;

	default:
		break;
	}
	
	
	static int modeCounts = 0;
	modeCounts++;
	if(modeCounts % 10 == 0){
		//printf("Gyro: %f\r\n", gyro->GetAngle()); 
		//printf("AutoHang: %d | Deploy: %d, Stop: %d\n", autoHangSwitch->Get(), armDeployLimit->Get(), stopHangingLimit->Get());
		//printf("ARM LOCK: %d\n", armLockLimit->Get());
		//printf("Mode: %d | wantedLeft: %f | wantedRight: %f\n", mode,tmpLeftMotor,tmpRightMotor);
	}
	
	if( reset )
	{
		nextMode = DRIVE_MODE;
		tmpLeftMotor = 0;
		tmpRightMotor = 0;
		tmpPTOPos = PTO_POS_NEUTRAL;
		tmpShiftPos = SHIFTER_POS_NEUTRAL;
		lockUpperStage = false;
		kicker->kick();
		kicker->enable();
	}
	
	mode = nextMode;
	
	shift(tmpShiftPos);
	shiftPTO(tmpPTOPos);
	
	upperStageRatchet->Set(!lockUpperStage); // Default-locked
	
	setLeftMotors(tmpLeftMotor);
	setRightMotors(tmpRightMotor);
	
	// first off, take care of this stuff
	kicker->handle();
	compressorHandle();
}


/// Allows skid steer robot to be driven using tank drive style inputs
/// @param leftDriveValue
/// @param rightDriveValue
///
void RAWCRobot::driveLeftRight(float leftDriveValue, float rightDriveValue)
{
	wantedLeftDrive = leftDriveValue;		
	wantedRightDrive = rightDriveValue;
}


void RAWCRobot::driveSpeedTurn(float speed, float turnRate)
{ 
	// This should work so positive speed moves forward
	// positive turn, turns right
	//NOTE AT THE MOMENT LEFT SPEED CONTROLLERS REVERSED!
	//SO NEGAVITE SPEED DRIVES FORWARD TO VICTORS
/*

	if(speed < 0)
	{
		turnRate = turnRate * -1;
	}
	speed = -1 * speed;
	
	float leftSpeed = speed * (1 + turnRate);
	float rightSpeed = speed * (1 - turnRate);
	
	
	float turnOverflowAmount = 0;
	float turnOverflowScale = .6;
	if(leftSpeed > 1)
	{
		turnOverflowAmount = leftSpeed - 1;
		rightSpeed = rightSpeed - (turnOverflowAmount*turnOverflowScale);
			
	}
	else if(leftSpeed < -1)
	{
		turnOverflowAmount = leftSpeed + 1;
		rightSpeed = rightSpeed - (turnOverflowAmount*turnOverflowScale);
		
	}
	else if(rightSpeed > 1)
	{
		turnOverflowAmount = rightSpeed - 1;
		leftSpeed = leftSpeed - (turnOverflowAmount*turnOverflowScale);
		
	}
	else if(rightSpeed < -1)
	{
		turnOverflowAmount = rightSpeed + 1;
		leftSpeed = leftSpeed - (turnOverflowAmount*turnOverflowScale);
		
	}
	else
	{
		
	}
		
	if(leftSpeed*rightSpeed < 0)
	{
		if(leftSpeed*speed < 0)
		{
			leftSpeed = 0;
				
		}
		else
		{
			rightSpeed = 0;
			
		}
	}
	else
	{
		;
	}
	
	if(speed != 0 && (leftSpeed == 0 || rightSpeed == 0))
	{
		if(leftSpeed == 0)
		{
			if (speed > 0)
				leftSpeed = -.075;
			else
				leftSpeed = .075;
			
		}
		else
		{
			if (speed > 0)
				rightSpeed = -.075;
			else
				rightSpeed = .075;
			
		}
	}
	else
	{
		
	}
	*/
	
	float angular_power = 0.0;
	float wheel = -turnRate;
	float throttle = -speed;
	float OverPower = 0.0;
	
	float sensitivity = 1.7;
	
	float sensHigh = RAWCConstants::getInstance()->getValueForKey("SensitivityHigh");
	float sensLow = RAWCConstants::getInstance()->getValueForKey("SensitivityLow");
	
	if(inHighGear() ){
		sensitivity = ( sensHigh != 0) ? sensHigh : sensitivity;
		if( mode == ARM_DEPLOYED_MODE){
			sensitivity -= .25;
		}
	}
	else {
		sensitivity = ( sensLow != 0) ? sensLow : sensitivity;
	}
	
	
	
	
	float right_power = 0.0;
	float left_power = 0.0;
	
	/*if(highgear) 
		sensitivity = 1.7;
	*/
	bool quickturn = false;
	if(quickturn) {
		OverPower = 1.0;
		sensitivity = 1.0;
		//if(highgear) sensitivity = 1.0;
		angular_power = wheel;
	} else {
		OverPower = 0.0;
		angular_power = fabs(throttle) * wheel * sensitivity;
	}


	right_power = left_power = throttle;
	left_power += angular_power;
	right_power -= angular_power;

	if (left_power > 1.0) {
		right_power -= OverPower*(left_power - 1.0);
		left_power = 1.0;
	} else if (right_power > 1.0) {
		left_power -= OverPower*(right_power - 1.0);
		right_power = 1.0;
	} else if (left_power < -1.0) {
		right_power += OverPower*(-1.0 - left_power);
		left_power = -1.0;
	} else if (right_power < -1.0) {
		left_power += OverPower*(-1.0 - right_power);
		right_power = -1.0;
	}

	float right_power_real = victor_linearize(right_power);
	float left_power_real = victor_linearize(left_power);
	
	
	driveLeftRight(left_power_real, right_power_real);
	
}

/// Allows robot to spin in place
/// @param turnRate
///
void RAWCRobot::quickTurn(float turnRate){
	//when provided with + turn, quick turn right
		
	float left = -1 * turnRate;
	float right = turnRate;
	

	driveLeftRight(left, right);	
}

/// Handles the autmatic turn on/off of the compressor
void RAWCRobot::compressorHandle(){
	if (! compressorSignal->Get()){
		compressorRelay->Set(Relay::kOn);
	}
	else
		compressorRelay->Set(Relay::kOff);
}

/// Returns the value of the drive's left side encoder
Encoder * RAWCRobot::getLeftEncoder()
{
	return leftDriveEncoder;
}

/// Returns the value of the drive's right side encoder
Encoder * RAWCRobot::getRightEncoder()
{
	return rightDriveEncoder;
}

Gyro * RAWCRobot::getGyro()
{
	return gyro; 
}

/// Shifts drive gears. 
/// @param shifterPosition
void RAWCRobot::shift(ShifterPositions shifterPosition)
{
	bool solA = false, solB = false;
	ShifterStates nextShiftState = currentShiftState;
	// It takes lots of logic to shift this year
	switch(currentShiftState)
	{
	case SHIFTER_STATE_HIGH:
		solA = false;
		solB = false;
		shifterCounts = 0;
		if(shifterPosition == SHIFTER_POS_NEUTRAL)
			nextShiftState = SHIFTER_STATE_HIGH_TRANS;
		if(shifterPosition == SHIFTER_POS_LOW)
			nextShiftState = SHIFTER_STATE_LOW;
		break;
	
	case SHIFTER_STATE_LOW:
		solA = true;
		solB = false;	
		shifterCounts = 0;
		if(shifterPosition == SHIFTER_POS_HIGH)
			nextShiftState = SHIFTER_STATE_HIGH;
		if(shifterPosition == SHIFTER_POS_NEUTRAL)
			nextShiftState = SHIFTER_STATE_LOW_TRANS;
		break;	
		
	case SHIFTER_STATE_NEUTRAL:
		solA = false;
		solB = true;	
		shifterCounts = 0;
		if(shifterPosition == SHIFTER_POS_HIGH)
			nextShiftState = SHIFTER_STATE_HIGH;
		if(shifterPosition == SHIFTER_POS_LOW)
			nextShiftState = SHIFTER_STATE_LOW;
		break;	
		
	case SHIFTER_STATE_LOW_TRANS:
		solA = true;
		solB = true;
		shifterCounts++;
		if(shifterCounts > SHIFT_TRANS_PAUSE)
			nextShiftState = SHIFTER_STATE_NEUTRAL;
		break;	
		
	case SHIFTER_STATE_HIGH_TRANS:
		solA = true;
		solB = false;
		shifterCounts++;
		if(shifterCounts > SHIFT_TRANS_PAUSE)
			nextShiftState = SHIFTER_STATE_LOW_TRANS;
		break;
		
	default:
		nextShiftState = SHIFTER_STATE_HIGH;
		break;
	}
	

	shifterA->Set(solA);
	shifterB->Set(solB);
	currentShiftState = nextShiftState;
}

/// Shifts PTO gears. 
/// @param ptoPosition
void RAWCRobot::shiftPTO(PTOPositions ptoPosition)
{
	bool solA = false, solB = false;
	PTOStates nextPTOState = currentPTOState;
	// It takes lots of logic to shift this year
	switch(currentPTOState)
	{
	case PTO_STATE_LOCKED:
		solA = false;
		solB = false;
		ptoCounts = 0;
		if(ptoPosition == PTO_POS_NEUTRAL)
			nextPTOState = PTO_STATE_LOCKED_TRANS;
		if(ptoPosition == PTO_POS_ENGAGED)
			nextPTOState = PTO_STATE_ENGAGED;
		break;
	
	case PTO_STATE_ENGAGED:
		solA = true;
		solB = false;	
		ptoCounts = 0;
		if(ptoPosition == PTO_POS_LOCKED)
			nextPTOState = PTO_STATE_LOCKED;
		if(ptoPosition == PTO_POS_NEUTRAL)
			nextPTOState = PTO_STATE_ENGAGED_TRANS;
		break;	
		
	case PTO_STATE_NEUTRAL:
		solA = false;
		solB = true;	
		ptoCounts = 0;
		if(ptoPosition == PTO_POS_LOCKED)
			nextPTOState = PTO_STATE_LOCKED;
		if(ptoPosition == PTO_POS_ENGAGED)
			nextPTOState = PTO_STATE_ENGAGED;
		break;	
		
	case PTO_STATE_ENGAGED_TRANS:
		solA = true;
		solB = true;
		ptoCounts++;
		if(ptoCounts > PTO_TRANS_PAUSE_ENGAGED)
			nextPTOState = PTO_STATE_NEUTRAL;
		break;	
		
	case PTO_STATE_LOCKED_TRANS:
		solA = true;
		solB = false;
		ptoCounts++;
		if(ptoCounts > PTO_TRANS_PAUSE_LOCKED)
			nextPTOState = PTO_STATE_ENGAGED_TRANS;
		break;
		
	default:
		nextPTOState = PTO_STATE_LOCKED;
		break;
	}
	

	ptoA->Set(solA);
	ptoB->Set(solB);
	currentPTOState = nextPTOState;
}

void RAWCRobot::driveArm(float lowerStage, float upperStage){
	wantedUpperStage = upperStage;
	wantedLowerStage = lowerStage;
}


void RAWCRobot::askForShift(ShifterPositions shifterPosition)
{
	wantedShifterPosition = shifterPosition;
}
void RAWCRobot::askForShiftPTO(PTOPositions ptoPosition){
	wantedPTOPosition = ptoPosition;
}
/// Sets the robot mode
void RAWCRobot::setMode(RobotModes newMode){
	mode = newMode;
}

/// sets the left side motors
void RAWCRobot::setLeftMotors(float val){
	val = -1.0 * val;
	
	if( val > 1.0)
		val = 1.00;
	if(val < -1.0)
		val = -1.0;
	
	leftDriveA->Set(val);
	leftDriveB->Set(val);
}

/// sets the left side motors
void RAWCRobot::setRightMotors(float val){
	if( val > 1.0)
		val = 1.00;
	if(val < -1.0)
		val = -1.0;
		
	rightDriveA->Set(val);
	rightDriveB->Set(val);
}

// ask for the arm to be deployed
void RAWCRobot::deployArm(bool deployArm)
{
	wantToDeployArm = deployArm;
}

// ask for the arm to be deployed
void RAWCRobot::hang()
{
	if(mode == ARM_DEPLOYING_MODE || mode == ARM_DEPLOYED_MODE || mode == HANG_MODE) 
		wantToHang = true;
	else wantToHang = false;
}

bool RAWCRobot::isArmDeployed(){
	return (mode == ARM_DEPLOYED_MODE);
}

void RAWCRobot::setFixArm(bool fixArm){
	wantToFixArm = fixArm;
}

void RAWCRobot::setSelfRight(bool doSelfRight){
	wantToSelfRight = doSelfRight;
}

void RAWCRobot::resetHang()
{
	wantToResetHang = true;
}

void RAWCRobot::setReset(bool resetIn){
	reset = resetIn;
}

DigitalInput * RAWCRobot::getArmDeployLimit(void)
{
	return armDeployLimit;
}

DigitalInput * RAWCRobot::getHangStopSwitch(void)
{
	return stopHangingLimit;
}

DigitalInput * RAWCRobot::getAutoHangSwitch(void)
{
	return autoHangSwitch;
}

bool RAWCRobot::inHighGear()
{
	return ( currentShiftState == SHIFTER_STATE_HIGH);
}

Gyro * RAWCRobot::getPitchGyro(){
	return pitchGyro;
}
