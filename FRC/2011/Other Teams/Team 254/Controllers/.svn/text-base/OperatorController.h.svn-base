#ifndef _OPERATOR_CONTROLLER_H
#define _OPERATOR_CONTROLLER_H
//=============================================================================
// File: OperatorController.h
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

#include "../RAWCRobot.h"
#include "WPILib.h"
#include "../RAWCControlBoard.h"

class OperatorController{
	
private:
	RAWCRobot * bot;
	Joystick * throttle, * wheel;

public:
	RAWCControlBoard * cb;
	OperatorController();
	
	void handle();
	void handleLEDs();
	
};

#endif
