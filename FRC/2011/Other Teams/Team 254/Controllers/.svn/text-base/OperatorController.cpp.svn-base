//=============================================================================
// File: OperatorController.cpp
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
#include "OperatorController.h"
#include "../RAWCLib.h"
#include "../RAWCControlBoard.h"
#include "../RAWCRobot.h"

// Constructor
// TODO: We might not need to pass in Joysticks, if they come from the ControlBoard
OperatorController::OperatorController()
{
	bot = RAWCRobot::getInstance();
	cb = RAWCControlBoard::getInstance();
}

void OperatorController::handleLEDs()
{
	// Lock LEDs
	switch( bot->kicker->getLockStatus() ){
	case Kicker::KICKER_DISTANCE_NONE:
		cb->setDistanceLED(RAWCControlBoard::LED_DISTANCE_NONE);
		break;
	case Kicker::KICKER_DISTANCE_NEAR:
		cb->setDistanceLED(RAWCControlBoard::LED_DISTANCE_NEAR);
		break;
	case Kicker::KICKER_DISTANCE_MIDDLE:
		cb->setDistanceLED(RAWCControlBoard::LED_DISTANCE_MIDDLE);
		break;
	case Kicker::KICKER_DISTANCE_FAR:
		cb->setDistanceLED(RAWCControlBoard::LED_DISTANCE_FAR);
		break;
	default:
		cb->setDistanceLED(RAWCControlBoard::LED_DISTANCE_NONE);
		break;
	}
	
	if( bot->kicker->getBallLock() )
		cb->setBallAcquiredLED();
	else
		cb->clearBallAcquiredLED();
	
	if( bot->isArmDeployed() )
		cb->setTargetLockLED();
	else
		cb->clearTargetLockLED();
}
// handle()
// call this when you want to update the bot from a driver
void OperatorController::handle()
{
	// handle the LEDs
	handleLEDs();
		
	// Set kicker distance
	if(cb->getButtonKickNear())
		bot->kicker->setDistance(Kicker::KICKER_DISTANCE_NEAR);
	else if(cb->getButtonKickMiddle())
		bot->kicker->setDistance(Kicker::KICKER_DISTANCE_MIDDLE);
	else if(cb->getButtonKickFar())
		bot->kicker->setDistance(Kicker::KICKER_DISTANCE_FAR);
	
	// fix arm
	bot->setFixArm(cb->getButtonKickFar());
	
	// Kick
	if(cb->getButtonKick())
		bot->kicker->kick();
	
	// Drive shifting
	if( cb->getButtonShifter() )
		bot->askForShift(RAWCRobot::SHIFTER_POS_HIGH);
	else
		bot->askForShift(RAWCRobot::SHIFTER_POS_LOW);
	
	bot->setSelfRight(cb->getButtonSelfRightAutomatic() );
	
	//if(cb->getButtonAutoSelect())
		//bot->resetHang();

	bot->setReset(cb->getButtonManualOverride());
	
	bot->deployArm(cb->getButtonArmDeploy());
	
	if(cb->getButtonHang() )
		bot->hang();
	
	// Manual arm control
	bot->driveArm(cb->getThrottle(),cb->getWheel() );
	
	// Drive
	if(cb->getQuickTrun())
		bot->quickTurn(cb->getWheel());
	else
		bot->driveSpeedTurn(cb->getThrottle(), cb->getWheel());
	
	// Set the roller on or off
	bot->kicker->setRoller(cb->getButtonRoller());

}

