//=============================================================================
// File: RAWCConstants.cpp
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
#include "RAWCConstants.h"
#include "WPILib.h"
#include "nivision.h"
#include <iostream>
#include <fstream>
#include <sstream>

// Private NI function needed to write to the VxWorks target 
IMAQ_FUNC int Priv_SetWriteFileAllowed(UINT32 enable); 

// Singleton instance
RAWCConstants *  RAWCConstants::singletonInstance = NULL;

// Helper function for converting a string to a float
template <class T>
bool from_string(T& t, 
                 const std::string& s, 
                 std::ios_base& (*f)(std::ios_base&))
{
  std::istringstream iss(s);
  return !(iss >> f >> t).fail();
}


/// This static method returns a shared instance of RAWCConstants
/// @return The shared instance of RAWCConstants
RAWCConstants * RAWCConstants::getInstance()
{
	if(singletonInstance == NULL)
	{
		singletonInstance = new RAWCConstants();
		// We need this to write to files
		Priv_SetWriteFileAllowed(1);
	}
	return singletonInstance;
}

/// Constructor
RAWCConstants::RAWCConstants()
{
	restoreData();
}

/// Returns a bool indicating if the key/value pair exists 
bool RAWCConstants::doesKeyExist(string key)
{
	return (bool) data.count(key);
}

/// Returns the stored value for a string key
/// @param key
/// @return Stored Value
RAWCConstants::RAWCConstant RAWCConstants::getValueForKey(string key)
{
	if( doesKeyExist(key) )
	{
		return data[key];
	}
	else 
		return RAWC_CONSTANTS_DEFAULT_RET_VAL;
}

/// Insert a value into the constant storage system
/// @param key String key
/// @param value RAWCConstant value
void RAWCConstants::insertKeyAndValue(string key, RAWCConstant value)
{
	data[key] = value;
}

/// Save data to the default file
void RAWCConstants::save()
{
	saveDataToFile(RAWC_CONSTANTS_DEFAULT_FILE);
}

/// Save data to a file
/// @param fileName File to save to
void RAWCConstants::saveDataToFile(string fileName)
{
	Priv_SetWriteFileAllowed(1);
	map<string, RAWCConstant>::iterator it; //Iterator for map
	
	//Open a file
	ofstream outfile;
	outfile.open (fileName.c_str());
	
	//if it fails, return
	// print content:
	for ( it = data.begin() ; it != data.end(); it++ ){
		printf("Writing: %s - %f\r\n", (*it).first.c_str(),(*it).second);
		outfile << (*it).first << ", " << (*it).second << "\r\n";
	}
	
	outfile.close();
}

/// Restores constant system data from the default file
void RAWCConstants::restoreData()
{
	restoreDataFromFile(RAWC_CONSTANTS_DEFAULT_FILE);
}

/// Restores constant system data from a file
/// @param fileName File to read from
void RAWCConstants::restoreDataFromFile(string fileName)
{
	string key;
	string valueString;
	
	ifstream infile ( RAWC_CONSTANTS_DEFAULT_FILE );
	//printf("In restore\r\n ");
	if (infile.is_open())
	{	
		//printf("File is open\r\n");
		while (! infile.eof() )
		{
			//Get the key name
			getline (infile, key, ',');
			// get the value string
			getline(infile, valueString);
			
			// Handle weird new line situations
			if(infile.eof())
				break;
			
			// Convert value string to a number
			RAWCConstant value;
			if(from_string<RAWCConstant>(value, valueString, std::dec)){
				//printf("Got a value %f for key %s\r\n", value, key.c_str());
				data[key] = value;
			}
			else
			{
				//printf( "from_string failed\r\n");
			}
	    }
		infile.close();
	}

	else printf("Unable to open file"); 	
}
