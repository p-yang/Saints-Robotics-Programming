//=============================================================================
// File: RAWCBase.cpp
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
#include "DashboardDataFormat.h"
#include "Vision/AxisCamera.h"
#include "Controllers/OperatorController.h"
#include "Controllers/AutoModeController.h"
#include "Autonomous/AutoModeSelector.h"
#include "RAWCConstants.h"


// Uncomment this to make the camera work
//#define USE_CAMERA

DriverStationLCD *m_dsLCD;

class RAWCBase : public IterativeRobot
{
	DriverStation *m_ds;
	RAWCRobot *bot;
	OperatorController * opController;
	AutoModeController * autoController;
	AutoModeSelector * autoSelector;
	int autoIndex;
	RAWCConstants * constants;
	
public:
// Constructor for this "Team254Code" Class.
	RAWCBase(void)	{
		
		
		GetWatchdog().SetEnabled(false);
	
		m_dsLCD = DriverStationLCD::GetInstance();
		
		bot = RAWCRobot::getInstance();	
		opController = new OperatorController();
		autoController = AutoModeController::getInstance();
		autoSelector = new AutoModeSelector();
		autoSelector->increment();
		autoSelector->incrementSecondary();
		
		
		constants = RAWCConstants::getInstance();
		constants->restoreData();
		
		//37 Max
#define COMP_BOT
#ifdef COMP_BOT
		const int zeroPoint = 29;
#else
		const int zeroPoint = 34;
#error
#endif
		
		constants->insertKeyAndValue("KickerPosFar", zeroPoint + 21);//59
		constants->insertKeyAndValue("KickerPosMiddle", zeroPoint + 6);//4
		constants->insertKeyAndValue("KickerPosNear", zeroPoint + 3);//4
		constants->insertKeyAndValue("KickerPosShort", zeroPoint);
		
		// No bounce
		constants->insertKeyAndValue("KickerPosAutoFar1", zeroPoint + 20); //20
		constants->insertKeyAndValue("KickerPosAutoFar2", zeroPoint + 18); //18
		constants->insertKeyAndValue("KickerPosAutoFar3", zeroPoint + 14); //14
		
		// bounce
		constants->insertKeyAndValue("KickerPosAutoFar1Bounce", zeroPoint+ 12);
		constants->insertKeyAndValue("KickerPosAutoFar2Bounce", zeroPoint + 9);
		constants->insertKeyAndValue("KickerPosAutoFar3Bounce", zeroPoint + 6);
		
		constants->insertKeyAndValue("KickerPosAutoMiddle1", zeroPoint + 5); //5
		constants->insertKeyAndValue("KickerPosAutoMiddle2", zeroPoint + 4); //4
		
		constants->insertKeyAndValue("KickerPosAutoNear", zeroPoint + 2);
				
		constants->insertKeyAndValue("turnP", 0.033);
		constants->insertKeyAndValue("turnI", 0.020);
		constants->insertKeyAndValue("turnD", 0.26);
				
		constants->insertKeyAndValue("turnDeadband", 0.025);
				
		constants->insertKeyAndValue("integralLimit", .8);
		constants->insertKeyAndValue("turnA", 0.4);
		constants->insertKeyAndValue("turnV", -0.45);
		
		constants->insertKeyAndValue("winchP", 0.17);
		constants->insertKeyAndValue("winchI", 0.015);
		constants->insertKeyAndValue("winchD", 0.6);
		constants->insertKeyAndValue("winchI_max", 0.5);
		
		constants->insertKeyAndValue("winchI_min", 5);
		constants->insertKeyAndValue("lowKI", 0.025);
		constants->insertKeyAndValue("lowKP", 0.23);
		
		constants->insertKeyAndValue("SensitivityHigh", 1.53);
		constants->insertKeyAndValue("SensitivityLow", 1.53);
		
		constants->save();
		
		#ifdef USE_CAMERA
		AxisCamera &camera = AxisCamera::GetInstance();
		camera.WriteResolution(AxisCamera::kResolution_320x240);
		camera.WriteColorLevel(0);
		camera.WriteBrightness(0);
		camera.WriteCompression(1);
		#endif
		
	}
	void RobotInit(void) {	
	}
	void DisabledInit(void) {
		constants->restoreData();
		/*double p = constants->getValueForKey("winchP");
			double i = constants->getValueForKey("winchI");
			double d = constants->getValueForKey("winchD");
			double Imax = constants->getValueForKey("winchI_max");
			double Imin = constants->getValueForKey("winchI_min");
			double lowKI = constants->getValueForKey("lowKI");
			double lowKP = constants->getValueForKey("lowKP");
			
			bot->kicker->winchPID->changeGains(p,i,d,Imax,0,0,Imin,lowKI,lowKP);*/
		autoIndex = 1;
	}
	void AutonomousInit(void) {
		// Commit the selected auto mode to the controller
		//autoSelector->writeToAutoModeController(autoController);
		//printf("In auto init\r\n");
		constants->restoreData();
		autoController->reset();
		autoSelector->writeToAutoModeController(autoController);
		RAWCRobot::getInstance()->getLeftEncoder()->Reset();
		RAWCRobot::getInstance()->getGyro()->Reset();
		
		
	}
	void TeleopInit(void) {
	}
	void DisabledContinuous(void) {
		
	}
	void AutonomousContinuous(void)	{

	}
	void TeleopContinuous(void) {
		
	}
	void DisabledPeriodic(void)  {
		static bool selectLatch = false;
		autoController->reset();
		
		if( opController->cb->getButtonAutoSelect() ){
			autoSelector->increment();
		}

		if( opController->cb->getQuickTrun()){
			if(!selectLatch)
				autoSelector->incrementSecondary();
			selectLatch = true;
		}
		else{
			selectLatch = false;
		}

		//Print it
		PrintToLCD::print(true, 1, 1, "Auto Mode: ");
		PrintToLCD::print(true, 2, 1, autoSelector->description().c_str());
		PrintToLCD::print(true,3 , 1, autoSelector->descriptionSecondary().c_str());
		PrintToLCD::print(true, 4, 1, "D:%2f",constants->getValueForKey("KickerPosShort")*10.0/10 );
		PrintToLCD::print(true, 4, 7, "N:%2f",constants->getValueForKey("KickerPosNear")*10.0/10 );
		PrintToLCD::print(true, 4, 12, "M:%2f",constants->getValueForKey("KickerPosMiddle")*10.0/10 );
		PrintToLCD::print(true, 4, 17, "F:%2f", constants->getValueForKey("KickerPosFar")*10.0/10 );
		PrintToLCD::finalizeUpdate();
		sendIOPortData();
	}
	void AutonomousPeriodic(void) {
		autoController->handle();
		bot->handle();
		
		sendIOPortData();
	}
	void TeleopPeriodic(void) {
		opController->handle();
		bot->handle();
		
		sendIOPortData();
	}
	
	
};

START_ROBOT_CLASS(RAWCBase);
