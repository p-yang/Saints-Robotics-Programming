/* File Name: Team254Code.cpp
 * Function: code any extra helper functions, such that may include limits/print to LCD's etc.
 * 
 * Author: Jakub Fiedorowicz (poland2005@gmail.com)
 * Student Lead: PJ Goesseringer (greeneyehawk@gmail.com)
 * 
 * SVN Repository: https://free2.projectlocker.com/Team254Robotics/2010Code/svn
 * 
 * This code is confidential and cannot be released or published
 * without explicit permission in writing from the Author.
 */
#include "RAWCLib.h"

extern DriverStationLCD * m_dsLCD;

float RAWCLib::AnalogInScale(float oldx, double center)
{
	const double min = 0 - center;
	const double max = 3.25 - center;
	const double deadband = .05;
	
	float x = oldx - center;

	if ((x <= (center*deadband)) && (x >= (-1*center*deadband)))
	{
		x = 0;
	}
	else if (x > (max - center*deadband))
	{
		x = 1;
	}
	else if (x < (min + center*deadband))
	{
		x = -1;
	}
	else if (x > 0)
	{
		x = ((x - (center*deadband))/((max - center*deadband) - (center
				*deadband)));
	}
	else if (x < 0)
	{
		x = ((x + (center*deadband))/(-1*((min + center*deadband) + (center
				*deadband))));
	}
	return x;
}

void PrintToLCD::print(bool enabled, int lineNumber, int startChar, const char * text){
	PrintToLCD::print(enabled, lineNumber, startChar, text, 0.0, 0.0);
}
void PrintToLCD::print(bool enabled, int lineNumber, int startChar, const char * text, float var1){
	PrintToLCD::print(enabled, lineNumber, startChar, text, var1, 0.0);
}	
void PrintToLCD::print(bool enabled, int lineNumber, int startChar, const char * text, float var1, float var2){
	if(!enabled)
		return;
	switch(lineNumber){
		case 1:
			m_dsLCD->Printf(DriverStationLCD::kUser_Line1,startChar,text, var1, var2);
			break;
		case 2:
			m_dsLCD->Printf(DriverStationLCD::kUser_Line2,startChar,text, var1, var2);	
			break;
		case 3:
			m_dsLCD->Printf(DriverStationLCD::kUser_Line3,startChar,text, var1, var2);	
			break;
		case 4:
			m_dsLCD->Printf(DriverStationLCD::kUser_Line4,startChar,text, var1, var2);				
			break;
		case 5:
			m_dsLCD->Printf(DriverStationLCD::kUser_Line5,startChar,text, var1, var2);
			break;
		case 6:
			m_dsLCD->Printf(DriverStationLCD::kUser_Line6,startChar,text, var1, var2);				
			break;
	}
}
void PrintToLCD::finalizeUpdate(){
		m_dsLCD->UpdateLCD();
		
}

float victor_linearize(double goal_speed)
{
	const double deadband_value = 0.082;
	if (goal_speed > deadband_value)
		goal_speed -= deadband_value;
	else if (goal_speed < -deadband_value)
		goal_speed += deadband_value;
	else
		goal_speed = 0.0;
	goal_speed = goal_speed / (1.0 - deadband_value);

	double goal_speed2 = goal_speed * goal_speed;
	double goal_speed3 = goal_speed2 * goal_speed;
	double goal_speed4 = goal_speed3 * goal_speed;
	double goal_speed5 = goal_speed4 * goal_speed;
	double goal_speed6 = goal_speed5 * goal_speed;
	double goal_speed7 = goal_speed6 * goal_speed;

	// Original untweaked one.
	//double victor_fit_c		= -1.6429;
	//double victor_fit_d		= 4.58861e-08;
	//double victor_fit_e		= 0.547087;
	//double victor_fit_f		= -1.19447e-08;

	// Constants for the 5th order polynomial
	double victor_fit_e1		= 0.437239;
	double victor_fit_c1		= -1.56847;
	double victor_fit_a1		= (- (125.0 * victor_fit_e1  + 125.0 * victor_fit_c1 - 116.0) / 125.0);
	double answer_5th_order = (victor_fit_a1 * goal_speed5
					+ victor_fit_c1 * goal_speed3
					+ victor_fit_e1 * goal_speed);

	// Constants for the 7th order polynomial
	double victor_fit_c2 = -5.46889;
	double victor_fit_e2 = 2.24214;
	double victor_fit_g2 = -0.042375;
	double victor_fit_a2 = (- (125.0 * (victor_fit_c2 + victor_fit_e2 + victor_fit_g2) - 116.0) / 125.0);
	double answer_7th_order = (victor_fit_a2 * goal_speed7
					+ victor_fit_c2 * goal_speed5
					+ victor_fit_e2 * goal_speed3
					+ victor_fit_g2 * goal_speed);


	// Average the 5th and 7th order polynomials
	double answer =  0.85 * 0.5 * (answer_7th_order + answer_5th_order)
			+ .15 * goal_speed * (1.0 - deadband_value);

	if (answer > 0.001)
		answer += deadband_value;
	else if (answer < -0.001)
		answer -= deadband_value;

	return answer;
}
