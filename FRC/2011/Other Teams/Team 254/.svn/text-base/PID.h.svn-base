#ifndef _PID_H
#define _PID_H


#include "WPILib.h"


class PID
{
public:
	PID(double gainP, double gainI, double gainD, double maxIntegral, double gainV, double gainA, double minI_in, double lowKI_in,double lowKP_in);
	double calculate(double error, double velocity, double acceleration);
	double calculateVoltage(double error, double velocity, double acceleration);
	void reset();
	void changeGains(double gainP, double gainI, double gainD, double maxIntegral, double gainV, double gainA, double minI_in, double lowKI_in, double lowKP_in);
	
	
private:
	double kP;
	double kI;
	double kD; // Gains
	double kV;
	double kA;
	double sumError; // Integral
	double lastError; // Differential helper
	double minI;
	double lowKI;
	double lowKP;
	
	double maxSumError; // Intergral wind up stop
};


#endif
