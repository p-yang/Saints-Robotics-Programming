#include "AutoModeSelector.h"
#include <string>


// Note:
// This all sucks.
// I want to change it

void AutoModeSelector::increment()
{
	index++;
	if(index == amLast)
		index = amFirst + 1;
	printf("Auto mode selected: %d\r\n",index);
}

void AutoModeSelector::incrementSecondary()
{
	secIndex++;
	if(secIndex == amsLast)
		secIndex = amsFirst + 1;
	printf("Auto mode selected: %d\r\n", secIndex);
}

string AutoModeSelector::description()
{
	char str[25];
	memset(str, '.', 25);
	string s = "";

	switch (index)
	{
	case amFarZoneBounce:
		sprintf(str,"Far Zone - Bounce     ");
		s.assign(str);
		break;
	case amFarZoneNoBounce:
		sprintf(str,"Far Zone - No Bounce  ");
		s.assign(str);
		break;
	case amMiddleZone:
		sprintf(str,"Middle Zone Straight   ");
		s.assign(str);
		break;
	case amMiddleZoneFromAngle:
		sprintf(str,"Middle Zone At Angle   ");
		s.assign(str);
		break;
		
	case amNearZone:
		sprintf(str,"Near Zone             ");
		s.assign(str);
		break;
	case amDoNothing:
		sprintf(str,"Do Nothing (Far Zone) ");
		s.assign(str);
		break;
	case amDoNothingMiddle:
		sprintf(str,"Do Nothing (Mid Zone) ");
		s.assign(str);
		break;	
	
	default:
		sprintf(str,"It broke              ");
		index = amFirst + 1;
		s.assign(str);
		break;
	}

	return s;
}

string AutoModeSelector::descriptionSecondary()
{
	char str[25];
	memset(str, '.', 25);
	string s = "";

	switch (secIndex)
	{
	case amsCrossBump:
		sprintf(str,"Cross Bump and Kick   ");
		s.assign(str);
		break;
		
	case amsCrossBumpAndPlow:
		sprintf(str,"Cross Bump and Plow   ");
		s.assign(str);
		break;
		
	case amsCrossBumpAndWait:
		sprintf(str,"Cross Bump and Wait   ");
		s.assign(str);
		break;
		
	case amsCrossTwoBumps:
		sprintf(str,"Cross Hella Bumps     ");
		s.assign(str);
		break;
		
	case amsDoNothing:
		sprintf(str,"Do Nothing            ");
		s.assign(str);
		break;

	case amsTurnToOpp:
		sprintf(str,"Turn to face opponent ");
		s.assign(str);
		break;
		
	case amsBlockTunnel:
		sprintf(str,"Austin Powers, Hella  ");
		s.assign(str);
		break;
		
	default:
		sprintf(str,"It broke              ");
		secIndex = amFirst + 1;
		s.assign(str);
		break;
	}

	return s;
}

void AutoModeSelector::writeToAutoModeController(AutoModeController * autoController)
{
	autoController->reset();
	autoController->addCommand(CMD_WAIT,.125,0,0);
	
	switch(index)
	{
	case amFarZoneNoBounce:
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);

		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_1,0,0);
					
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_1,0,0);			
		autoController->addCommand(CMD_WAIT,.25,0,0);
					
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,40,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_LIMIT_DISTANCE,70,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_2,0,0);
		autoController->addCommand(CMD_WAIT,.1,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_2,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,90,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_LIMIT_DISTANCE,200,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_3,0,0);
		autoController->addCommand(CMD_WAIT,.1,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_3,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,110,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_LIMIT_DISTANCE,230,.7,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		
		autoController->addCommand(CMD_TURN_OFF_ROLLER,0,0,0);
	
		break;
		
		
	case amFarZoneBounce:
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);

		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_1_Bounce,0,0);
					
		autoController->addCommand(CMD_WAIT,.25,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_1_Bounce,0,0);			
		autoController->addCommand(CMD_WAIT,.25,0,0);
					
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,40,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_LIMIT_DISTANCE,70,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_2_Bounce,0,0);
		autoController->addCommand(CMD_WAIT,.1,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_2_Bounce,0,0);
				
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,90,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_LIMIT_DISTANCE,200,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_3_Bounce,0,0);
		autoController->addCommand(CMD_WAIT,.1,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_FAR_3_Bounce,0,0);
		
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,110,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_LIMIT_DISTANCE,230,.7,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
				
		autoController->addCommand(CMD_TURN_OFF_ROLLER,0,0,0);
		break;
		
	case amMiddleZoneFromAngle:
		//intentional fall through!
	case amMiddleZone:
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_MIDDLE_1,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_MIDDLE_1,0,0);		
		autoController->addCommand(CMD_WAIT,.105,0,0);
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,75,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_MIDDLE_2,0,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
				
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,175,.5,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);

		autoController->addCommand(CMD_TURN_OFF_ROLLER,0,0,0);
		
		break;
		
	case amNearZone:
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.50,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_DEFAULT,0,0);				autoController->addCommand(CMD_WAIT,1.0,0,0);
				
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);
		autoController->addCommand(CMD_WAIT,.75,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_DEFAULT,0,0);		
		autoController->addCommand(CMD_WAIT,.75,0,0);
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,400,.5,0);
		autoController->addCommand(CMD_WAIT,.250,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.5,0,0);
		break;
	case amDoNothing:

		break;
	default:
		index = amFirst + 1;
		break;
	}
	
	
	
	// Add the secondary functions
	
	switch(secIndex){
	case amsCrossBump:
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,70,.75,0);
		//curCmd.argautoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		
		autoController->addCommand(CMD_DRIVE_OVER_BUMP, 1,0,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		autoController->addCommand(CMD_CHANGE_DRIVE_HEADING,-13.4,0,0);
		
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_MIDDLE_1,0,0);		
		autoController->addCommand(CMD_WAIT,.05,0,0);
		
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,125,.5,0);
		autoController->addCommand(CMD_WAIT,.1,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_MIDDLE_2,0,0);
		autoController->addCommand(CMD_WAIT,.125,0,0);
		autoController->addCommand(CMD_SET_KICKER_DISTANCE,Kicker::KICKER_DISTANCE_AUTO_MIDDLE_2,0,0);
		autoController->addCommand(CMD_CHANGE_DRIVE_HEADING,0.0,0,0);
		
		autoController->addCommand(CMD_DRIVE_TO_BALL_LOCK_AND_STOP,200,.5,0);
		autoController->addCommand(CMD_WAIT,.05,0,0);
		autoController->addCommand(CMD_KICK,0,0,0);

		autoController->addCommand(CMD_TURN_OFF_ROLLER,0,0,0);
		break;
	
		
	case amsCrossBumpAndWait:

		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,25,.75,0);
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
			
		autoController->addCommand(CMD_DRIVE_OVER_BUMP, 1,0,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		
		break;
	case amsCrossBumpAndPlow:
		if(index == amMiddleZone )
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,100,.75,0);
		else if (index == amMiddleZoneFromAngle){
			autoController->addCommand(CMD_TURN_TO_HEADING,20,1.0,0);
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,25,.75,0);
		}
		else
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,25,.75,0);
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);		
		autoController->addCommand(CMD_DRIVE_OVER_BUMP, 1,0,0);
		autoController->addCommand(CMD_TURN_TO_HEADING,-25,1.0,0);
		autoController->addCommand(CMD_SHIFT_HIGH,0,0,0);
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,70,1,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		autoController->addCommand(CMD_TURN_TO_HEADING,20,1.0,0);
		autoController->addCommand(CMD_SHIFT_HIGH,0,0,0);
		autoController->addCommand(CMD_TURN_OFF_ROLLER,0,0,0);
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,220,1,0);
		
		break;
	case amsCrossTwoBumps:
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,25,.75,0);
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
					
		autoController->addCommand(CMD_DRIVE_OVER_BUMP, 1,0,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		
		autoController->addCommand(CMD_SHIFT_HIGH,0,0,0);
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,240,1,0);
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
				
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,25,.75,0);
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
							
		autoController->addCommand(CMD_DRIVE_OVER_BUMP, 1,0,0);
		autoController->addCommand(CMD_WAIT,.25,0,0);
		break;
			
	case amsTurnToOpp:
		autoController->addCommand(CMD_WAIT,.1,0,0);
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_STOP,-100,.8,0);
		autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
		autoController->addCommand(CMD_TURN_TO_HEADING,90,1.0,0);
		autoController->addCommand(CMD_TURN_ON_ROLLER,0,0,0);
		autoController->addCommand(CMD_DRIVE_DISTANCE_AND_STOP,100,1,0);
		break;
		
	case amsBlockTunnel:
		switch(index){
		case amFarZoneNoBounce:
			//intentional fall through
		case amFarZoneBounce:
			autoController->addCommand(CMD_WAIT,.1,0,0);
			autoController->addCommand(CMD_TURN_TO_HEADING,95,1.0,0);
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_STOP,152,1,0);
			autoController->addCommand(CMD_TURN_TO_HEADING,-64,1.0,0);
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,100,1,0);
			autoController->addCommand(CMD_TURN_TO_HEADING,-34,1.0,0);
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,20,1,0);
			break;
		
			
		case amMiddleZone:
			autoController->addCommand(CMD_LIMIT_DISTANCE,140,.9,0);
			autoController->addCommand(CMD_TURN_TO_HEADING,-59,1.0,0);
			autoController->addCommand(CMD_SHIFT_HIGH,0,0,0);
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_COAST,-200,1,0);
			autoController->addCommand(CMD_WAIT,.1,0,0);
			autoController->addCommand(CMD_SHIFT_LOW,0,0,0);
			break;
			
		case amMiddleZoneFromAngle:
			autoController->addCommand(CMD_WAIT,.1,0,0);
			autoController->addCommand(CMD_TURN_TO_HEADING,-10,1.0,0);
			autoController->addCommand(CMD_DRIVE_DISTANCE_AND_STOP,-200,1,0);
			break;
		default:
			break;
		}
		
		
		break;
		
	default:
		break;
	}
		
	
	//TDO: Make this
}

