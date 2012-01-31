#ifndef _RAWC_CONSTANTS_H
#define _RAWC_CONSTANTS_H
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
#include <string>
#include <map>

#define RAWC_CONSTANTS_DEFAULT_FILE		"constants.csv"

class RAWCConstants
{
	// A RAWCConstant is contained in RAWCConstants
	typedef double RAWCConstant;
	static const RAWCConstant RAWC_CONSTANTS_DEFAULT_RET_VAL = 0.0;
	
private:
	map<string, RAWCConstant> data;
	static RAWCConstants * singletonInstance;
	
	RAWCConstants();
	//TODO: Add functionality to let this map persist to a file
	
	void saveDataToFile(string fileName); // Save to another file
	

	void restoreDataFromFile(string fileName);
	
	
public:
	void restoreData(); // from default file
	void save(); // Save to the default file
	// Get the shared object
	static RAWCConstants * getInstance();
	
	// Main mechanism to input data
	void insertKeyAndValue(string key, RAWCConstant value);
	
	// Main mechanism to look up data
	// NOTE: I need to figure out how this can fail elegantly 
	// 		instead of just checking to see if it exists first
	//		Maybe pass in a pointer to a RAWCConstant and write to that?
	RAWCConstant getValueForKey(string key);
	bool doesKeyExist(string key);
	
};

#endif // _RAWC_CONSTANTS_H
