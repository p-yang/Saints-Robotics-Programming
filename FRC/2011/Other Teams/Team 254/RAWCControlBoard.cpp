//=============================================================================
// File: RAWCControlBoard.cpp
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
#include "RAWCControlBoard.h"
#include "WPILib.h"
#include "RAWCRobot.h"

// Initialize the Singleton instance
RAWCControlBoard* RAWCControlBoard::singletonInstance = NULL;

/// Creates (if needed) and returns a singleton Control Board instance
RAWCControlBoard* RAWCControlBoard::getInstance()
{
	if( singletonInstance == NULL)
		singletonInstance = new RAWCControlBoard(); 
	return singletonInstance;
}

/// Constructor for RAWC Control Board
RAWCControlBoard::RAWCControlBoard()
{
	ds = & DriverStation::GetInstance()->GetEnhancedIO();
	autoLatch = false;
	
	// Set LEDs as outputs
	ds->SetDigitalConfig(FAR_LED_CHAN,DriverStationEnhancedIO::kOutput);
	ds->SetDigitalConfig(MID_LED_CHAN,DriverStationEnhancedIO::kOutput);
	ds->SetDigitalConfig(NEAR_LED_CHAN,DriverStationEnhancedIO::kOutput);
	
	// Set Buttons to be pulled up
	ds->SetDigitalConfig(SW_GEAR_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_QUICK_TURN_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_MANUAL_OVERRIDE_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_SELF_RIGHT_1_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_SELF_RIGHT_2_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_ARM_DEPLOY_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_ROLLER_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_MANUAL_OVERRIDE_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_DO_KICK_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_HANG_CHAN, DriverStationEnhancedIO::kInputPullUp);
	ds->SetDigitalConfig(SW_AUTO_SELECT_CHAN, DriverStationEnhancedIO::kInputPullUp);
	
	// lock LEDS
	ds->SetDigitalConfig(LED_BALL_LOCK_CHAN,DriverStationEnhancedIO::kOutput);
	ds->SetDigitalConfig(LED_TARGET_LOCK_CHAN,DriverStationEnhancedIO::kOutput);
	clearDistanceLED();
	clearTargetLockLED();
	clearBallAcquiredLED();
}

/// Returns the unscaled throttle joystick value
/// @return Unscaled Throttle Value
///
double RAWCControlBoard::getRawThrottle(){
	return ds->GetAnalogIn(THROTTLE_CHAN);
}

/// Returns the unscaled wheel joystick value
/// @return Unscaled Wheel Value
///
double RAWCControlBoard::getRawWheel(){
	return ds->GetAnalogIn(WHEEL_CHAN);
}

/// Returns the scaled (-1.0 to 1.0) throttle joystick value
/// @return Scaled Throttle Value
///
float RAWCControlBoard::getThrottle(){
	return RAWCLib::AnalogInScale(getRawThrottle(), THROTTLE_CENTER_VALUE);
}

/// Returns the scaled (-1.0 to 1.0) wheel joystick value
/// @return Scaled Wheel Value
///
float RAWCControlBoard::getWheel(){
	return RAWCLib::AnalogInScale(getRawWheel(), WHEEL_CENTER_VALUE);
}

/// Sets the "Kicker Ready" LEDs on the control board
/// @param distance Which distance LED to light
void RAWCControlBoard::setDistanceLED(DistanceLEDs distance){
	bool led_near, led_far, led_middle;
	led_near = led_middle = led_far = false;
	
	switch(distance){
	case LED_DISTANCE_FAR:
		led_far = true;
		break;
	case LED_DISTANCE_NEAR:
		led_near = true;
		break;
	case LED_DISTANCE_MIDDLE:
		led_middle = true;
		break;
	case LED_DISTANCE_ALL:
		led_near = led_middle = led_far = true;
		break;
	default:
		break;
	}
	
	ds->SetDigitalOutput(FAR_LED_CHAN, led_far);
	ds->SetDigitalOutput(MID_LED_CHAN, led_middle);
	ds->SetDigitalOutput(NEAR_LED_CHAN, led_near);
	
}

/// Turns off all the "Kicker Ready" LEDs
void RAWCControlBoard::clearDistanceLED(){
	setDistanceLED(LED_DISTANCE_NONE);
}

/// Turns on the "Ball Acquired" LED
void RAWCControlBoard::setBallAcquiredLED(){
	ds->SetDigitalOutput(LED_BALL_LOCK_CHAN, true);
}

/// Turns off the "Ball Acquired" LED
void RAWCControlBoard::clearBallAcquiredLED(){
	ds->SetDigitalOutput(LED_BALL_LOCK_CHAN, false);
}

/// Turns on the "Target Locked On" LED
void RAWCControlBoard::setTargetLockLED(){
	ds->SetDigitalOutput(LED_TARGET_LOCK_CHAN, true);
}

/// Turns off the "Target Locked On" LED
void RAWCControlBoard::clearTargetLockLED(){
	ds->SetDigitalOutput(LED_TARGET_LOCK_CHAN, false);
}

/// Returns state of middle kicker button
bool RAWCControlBoard::getButtonKickMiddle(){
	float val = ds->GetAnalogIn(SW_KICK_MID_CHAN);
	return ( val <= .05 && val >= -.05);
}

/// Returns state of far kicker button
bool RAWCControlBoard::getButtonKickFar(){
	float val = ds->GetAnalogIn(SW_KICK_FAR_CHAN);
	return ( val <= .05 && val >= -.05);
}

/// Returns state of near kicker button
bool RAWCControlBoard::getButtonKickNear(){
	float val = ds->GetAnalogIn(SW_KICK_NEAR_CHAN);
	return ( val <= .05 && val >= -.05);
}

/// Returns state of shifter switch
bool RAWCControlBoard::getButtonShifter()
{
	return ! ds->GetDigital(SW_GEAR_CHAN);
}

/// Returns state of kick button
bool RAWCControlBoard::getButtonKick()
{
	return ! ds->GetDigital(SW_DO_KICK_CHAN);
}

/// Returns state of autonomous select button
bool RAWCControlBoard::getButtonAutoSelect()
{
	// This will latch on a press and only return true once per press
	// Active Low signal
	if( !ds->GetDigital(SW_AUTO_SELECT_CHAN)){
		if(!autoLatch){
			autoLatch = true;
			return true;
		}
	}
	else
		autoLatch = false;
	return false;
}

/// Returns state of the Manual override switch
bool RAWCControlBoard::getButtonManualOverride(){
	return ! ds->GetDigital(SW_MANUAL_OVERRIDE_CHAN);
}

/// Returns the state of the Self Right (Manual Control) switch
bool RAWCControlBoard::getButtonSelfRightManual(){
	return ! ds->GetDigital(SW_SELF_RIGHT_1_CHAN);
}

/// Returns the state of the automatic self right switch
bool RAWCControlBoard::getButtonSelfRightAutomatic(){
	return ! ds->GetDigital(SW_SELF_RIGHT_2_CHAN);
}

/// Returns the state of the hang button
bool RAWCControlBoard::getButtonHang(){
	return ! ds->GetDigital(SW_HANG_CHAN);
}

/// Returns the state of the roller switch
bool RAWCControlBoard::getButtonRoller(){
	return ! ds->GetDigital(SW_ROLLER_CHAN);
}

/// Returns the state of the quick turn
bool RAWCControlBoard::getQuickTrun(){
	return ! ds->GetDigital(SW_QUICK_TURN_CHAN);
}

bool RAWCControlBoard::getButtonArmDeploy(){
	return ! ds->GetDigital(SW_ARM_DEPLOY_CHAN);
}
