/**
 * File Name: Declarations.h
 * Function: Header file: constant declarations
 * 
 * Author: Jakub Fiedorowicz (poland2005@gmail.com)
 * Student Lead: PJ Goesseringer (greeneyehawk@gmail.com)
 * 
 * SVN Repository: https://free2.projectlocker.com/Team254Robotics/2010Code/svn
 * 
 * This code is confidential and cannot be released or published
 * without explicit permission in writing from the Author.
 */
#ifndef DECLARATIONS_H_
#define DECLARATIONS_H_

#define THROTTLE_CENTER_VALUE 1.605
#define WHEEL_CENTER_VALUE 1.536

// PWM PORTS
#define LEFT_DRIVE_PWM_A 	1
#define LEFT_DRIVE_PWM_B 	2
#define KICKER_MOTOR_CHAN	4
#define ROLLER_MOTOR_CHAN	3
#define RIGHT_DRIVE_PWM_A 	7
#define RIGHT_DRIVE_PWM_B 	8

//DIGITAL IN
#define COMPRESSOR_SWITCH 		1
#define LEFT_ENCODER_A_CHAN		2
#define LEFT_ENCODER_B_CHAN		3
#define RIGHT_ENCODER_A_CHAN	13  // this doesn't exist
#define RIGHT_ENCODER_B_CHAN	14  // this doesn't exist

#define KICKER_ENC_A_CHAN		6
#define KICKER_ENC_B_CHAN		7
#define ROLLER_ENC_A_CHAN		4
#define ROLLER_ENC_B_CHAN		5
#define AUTO_HANG_SWITCH          8 //blue
#define ARM_DEPLOY_LIMIT		9 //green
#define STOP_HANGING_LIMIT		10 //orange

//RELAY
#define COMPRESSOR_RELAY 		1

//SOLENOID
#define SHIFTER_A_CHAN			5 
#define SHIFTER_B_CHAN			4
#define PTO_A_CHAN				3
#define PTO_B_CHAN				2
#define KICKER_SOLENOID_CHAN	1 // False-Engaged, True-Disengaged
#define UPPER_STAGE_RATCHET_SOLENOID_CHAN	6

#define KICKER_LOCK_ENGAGED		0
#define KICKER_LOCK_OPEN		1

//ANALOG IN
#define GYRO_IN_CHAN 			1
#define PITCH_GYRO_IN_CHAN		2


// Driver Controller
#define PORT_THROTTLE	1
#define PORT_WHEEL		2


#endif
