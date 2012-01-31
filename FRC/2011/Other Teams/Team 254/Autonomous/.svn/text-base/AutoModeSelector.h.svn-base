#ifndef _AUTO_SELECTOR_H
#define _AUTO_SELECTOR_H
#include <string>
#include "../Controllers/AutoModeController.h"

// Note:
// This all sucks.
// I want to change it

class AutoModeSelector{
	
public:
	enum AutoModes{
		amFirst = 0,
		amFarZoneBounce,
		amFarZoneNoBounce,
		amMiddleZone,
		amMiddleZoneFromAngle,
		amNearZone,
		amDoNothing,
		amDoNothingMiddle,
		amLast
	};
	
	enum AutoModesSecondary{
		amsFirst,
		amsCrossBumpAndWait,
		amsCrossBump,
		amsCrossBumpAndPlow,
		amsCrossTwoBumps,
		amsTurnToOpp,
		amsBlockTunnel,
		amsDoNothing,
		amsLast
	};
	
	
	void increment();
	void incrementSecondary();
	
	// Get a description of this auto mode
	string description();
	string descriptionSecondary();
	void writeToAutoModeController(AutoModeController * ac);
	
	
private:
	int index;
	int secIndex;
};

#endif
