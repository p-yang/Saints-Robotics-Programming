#include "PID.h"
#include "RAWCConstants.h"

PID::PID(double gainP, double gainI, double gainD, double maxIntegral, double gainV, double gainA, double minI_in, double lowKI_in, double lowKP_in)
{
	changeGains(gainP,gainI,gainD,maxIntegral,gainV,gainA,minI_in,lowKI_in, lowKP_in);
	reset();
}

double PID::calculate(double error, double velocity, double acceleration)
{	
	double P = error * kP;
	
	if(error > minI)
		sumError = 0;
	
	sumError += error;
	
	if(sumError > maxSumError / kI)
		sumError = maxSumError;
	else if( sumError < -maxSumError / kI)
		sumError = -maxSumError;
	
	double I = sumError * kI;
	double D = (error - lastError) * kD;
	
	lastError = error;
	
	
	
	float ret = P + I + D + (velocity * kV) + (acceleration * kA);
	
	float deadband = RAWCConstants::getInstance()->getValueForKey("turnDeadband");
	if(ret < deadband && ret > -deadband){
		ret = 0;
	}
	
	return ret;
}
double PID::calculateVoltage(double error, double velocity, double acceleration)
{
	float voltage = DriverStation::GetInstance()->GetBatteryVoltage();
	
	double newKP = (((voltage - 12.5)*lowKP)/(9.5-12.5)) + (((voltage - 9.5)*kP)/(12.5-9.5));
	
	double newKI = (((voltage - 12.5)*lowKI)/(9.5-12.5)) + (((voltage - 9.5)*kI)/(12.5-9.5));
	
	double P = error * newKP;

	
	if(error > minI)
		sumError = 0;
	
	sumError += error;
	
	if(sumError > maxSumError / newKI)
		sumError = maxSumError;
	else if( sumError < -maxSumError /  newKI)
		sumError = -maxSumError;
	
	double I = sumError * newKI;
	double D = (error - lastError) * kD;
	
	lastError = error;
	
	
	
	float ret = P + I + D + (velocity * kV) + (acceleration * kA);
	
	float deadband = RAWCConstants::getInstance()->getValueForKey("turnDeadband");
	if(ret < deadband && ret > -deadband){
		ret = 0;
	}
	
	return ret;
}

void PID::reset()
{
	sumError = 0;
	lastError = 0;
}

void PID::changeGains(double gainP, double gainI, double gainD, double maxIntegral, double gainV, double gainA,double minI_in, double lowKI_in,double lowKP_in)
{
	kP = gainP;
	kI = gainI;
	kD = gainD;
	kA = gainA;
	kV = gainV;
	minI = minI_in;
	maxSumError = maxIntegral;	
	lowKI = lowKI_in;
	lowKP = lowKP_in;
}
