#ifndef _RAWC_CONTROL_BOARD_H
#define _RAWC_CONTROL_BOARD_H
//=============================================================================
// File: RAWCControlBoard.h
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

#include "WPILib.h"
#include "RAWCRobot.h"

// Constants for control board channels
#define FAR_LED_CHAN			3
#define MID_LED_CHAN			5
#define NEAR_LED_CHAN			7
#define SW_KICK_FAR_CHAN		5
#define SW_KICK_NEAR_CHAN		1
#define SW_KICK_MID_CHAN		3
#define THROTTLE_CHAN			4
#define WHEEL_CHAN				2
#define SW_GEAR_CHAN			6
#define SW_QUICK_TURN_CHAN		8
#define SW_MANUAL_OVERRIDE_CHAN	10
#define SW_SELF_RIGHT_1_CHAN	14 
#define SW_SELF_RIGHT_2_CHAN	12 
#define SW_ARM_DEPLOY_CHAN		15
#define SW_ROLLER_CHAN			9
#define SW_DO_KICK_CHAN			11
#define SW_HANG_CHAN			13
#define SW_AUTO_SELECT_CHAN		16
#define LED_BALL_LOCK_CHAN		1
#define LED_TARGET_LOCK_CHAN	4

/// This class offers access to the 2010 specific RAWC Control Board
///
class RAWCControlBoard
{
private:
	DriverStationEnhancedIO * ds;
	static RAWCControlBoard *singletonInstance;
	bool autoLatch;
	
	// Construction
	RAWCControlBoard();
	
public:
	enum DistanceLEDs{
		LED_DISTANCE_NONE = 0,
		LED_DISTANCE_FAR,
		LED_DISTANCE_MIDDLE,
		LED_DISTANCE_NEAR,
		LED_DISTANCE_ALL
	};
	
	static RAWCControlBoard* getInstance();
	
	// Analog Getters
	double getRawThrottle();
	double getRawWheel();
	float getThrottle();
	float getWheel();
	
	// LED Setters/Getters
	void setDistanceLED(DistanceLEDs dist);
	void clearDistanceLED();
	void setBallAcquiredLED();
	void clearBallAcquiredLED();
	void setTargetLockLED();
	void clearTargetLockLED();
	
	// Button Getters
	bool getButtonKickNear();
	bool getButtonKickMiddle();
	bool getButtonKickFar();
	bool getButtonShifter();
	bool getButtonKick();
	bool getButtonAutoSelect();
	bool getButtonManualOverride();
	bool getButtonSelfRightManual();
	bool getButtonSelfRightAutomatic();
	bool getButtonHang();
	bool getButtonRoller();
	bool getQuickTrun();
	bool getButtonArmDeploy();
};

#endif
