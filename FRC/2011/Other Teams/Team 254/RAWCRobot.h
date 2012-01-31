#ifndef _RAWC_ROBOT_H
#define _RAWC_ROBOT_H
//=============================================================================
// File: RAWCRobot.h
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

#include "RAWCLib.h"
#include "subsystems/Kicker.h"


#define SHIFT_TRANS_PAUSE 	5
#define PTO_TRANS_PAUSE_ENGAGED 	5
#define PTO_TRANS_PAUSE_LOCKED		12		

/// This class provides core robot functionality and encapsulates 
/// motors, sensors, relays, etc.
///
class RAWCRobot{
	public: 
	static RAWCRobot * singletonInstance;
	
	enum ShifterPositions{
		SHIFTER_POS_NEUTRAL = 0,
		SHIFTER_POS_LOW,
		SHIFTER_POS_HIGH
	};
	
	enum PTOPositions
	{
		PTO_POS_NEUTRAL,
		PTO_POS_ENGAGED,
		PTO_POS_LOCKED
	};
	
	
	enum RobotModes{
		DRIVE_MODE,
		HANG_MODE,
		AUTO_HANG_MODE,
		ARM_DEPLOYING_MODE,
		ARM_DEPLOYED_MODE,
		SELF_RIGHT_MODE,
		SELF_RIGHT_RESET
	};
	
	enum ShifterStates{
		SHIFTER_STATE_NEUTRAL,
		SHIFTER_STATE_HIGH,
		SHIFTER_STATE_LOW,
		SHIFTER_STATE_HIGH_TRANS,
		SHIFTER_STATE_LOW_TRANS
	};

	enum PTOStates{
		PTO_STATE_NEUTRAL,
		PTO_STATE_LOCKED,
		PTO_STATE_ENGAGED,
		PTO_STATE_LOCKED_TRANS,
		PTO_STATE_ENGAGED_TRANS
	};
	
	//Sub Systems
	Kicker * kicker;
	
	static RAWCRobot* getInstance();
	
	void driveSpeedTurn(float speed, float turn);
	void driveLeftRight(float leftDriveValue, float rightDriveValue);
	void quickTurn(float turn);
	void compressorHandle();
	Encoder * getLeftEncoder();
	Encoder * getRightEncoder();
	Gyro * getGyro();
	Gyro * getPitchGyro();
	
	Joystick * js;
	
	void handle();
	
	bool isArmDeployed();
	
	void setSelfRight(bool doSelfRight);
	
	void setFixArm(bool fixArm);
	
	void askForShift(ShifterPositions shifterPosition);
	void askForShiftPTO(PTOPositions ptoPosition);
	

	void driveArm(float lowerStage, float upperStage);
	
	void deployArm(bool deployArm);
	void hang();
	void resetHang();
	
	void setReset(bool resetIn);
	
	DigitalInput * getArmDeployLimit(void);

	DigitalInput * getHangStopSwitch(void);

	DigitalInput *getAutoHangSwitch(void);
	
	bool inHighGear();

	
private:
	
	void shift(ShifterPositions shifterPosition);
	void shiftPTO(PTOPositions ptoPosition);
	void setMode(RobotModes newMode);
	
	// Drive Motors
	Victor *rightDriveA;
	Victor *rightDriveB;
	Victor *leftDriveA;
	Victor *leftDriveB;
	
	// Drive shifting pistons
	Solenoid *shifterA;
	Solenoid *shifterB;
	
	// Power take off pistons
	Solenoid *ptoA;
	Solenoid *ptoB;
	
	Solenoid * upperStageRatchet;
	
	// Compressor
	DigitalInput *compressorSignal;
	DigitalInput *autoHangSwitch; 
	DigitalInput * armDeployLimit;
	DigitalInput * stopHangingLimit;
	Relay *compressorRelay;
		
	//Sensors
	Gyro * gyro;
	Gyro * pitchGyro;
	Encoder *leftDriveEncoder;
	Encoder *rightDriveEncoder;
	
	RobotModes mode;
	
	ShifterPositions wantedShifterPosition;
	PTOPositions wantedPTOPosition;
	
	float wantedUpperStage;
	float wantedLowerStage;
	
	float wantedLeftDrive;
	float wantedRightDrive;
	
	float previousAngle;
	
	bool wantToDeployArm;
	bool wantToHang;
	bool wantToSelfRight ;
	bool wantToResetHang;
	bool wantToFixArm;
	
	int ptoJimmyCounts;
	
	int shifterCounts;
	ShifterStates currentShiftState;

	int ptoCounts;
	PTOStates currentPTOState;
	
	
	bool reset;
	
	
	RAWCRobot();
	
	void setLeftMotors(float val);
	void setRightMotors(float val);
		

	
};

#endif
