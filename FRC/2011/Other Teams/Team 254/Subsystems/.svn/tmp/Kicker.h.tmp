#ifndef _KICKER_H
#define _KICKER_H

#include "WPILib.h"
#include "../PID.h"

#define ROLLER_SPEED_DEFAULT_VAL	1.0
#define ROLLER_SPEED_BACKWARD_DEFAULT_VAL	-.4

#define KICKER_PULLING_MOTOR_VAL	1.00

/// This class contains the functionality of RAWC's 2010 Kicker
class Kicker
{
public:
	// FSM States
	enum KickerStates{
		STATE_KICKER_IDLE = 0,
		STATE_KICKER_PULLING = 1,
		STATE_KICKER_LOCKED = 2,
		STATE_KICKER_KICKING = 3,
		STATE_KICKER_MOVING_DOWN = 4,
		STATE_KICKER_STARTUP = 5,
		STATE_KICKER_REZERO = 6
		// May need to add kStateLatching for when lock is engaging
	};
	
	//Dont change these. It will break stuff.
	enum KickerDistances{
		KICKER_DISTANCE_NONE = 0,
		KICKER_DISTANCE_DEFAULT = 1,
		KICKER_DISTANCE_NEAR = 2 ,
		KICKER_DISTANCE_MIDDLE = 3,
		KICKER_DISTANCE_FAR = 4,
		KICKER_DISTANCE_AUTO_FAR_1 = 7,
		KICKER_DISTANCE_AUTO_FAR_2 = 6,
		KICKER_DISTANCE_AUTO_FAR_3 = 5,
		KICKER_DISTANCE_AUTO_FAR_1_Bounce = 10,
		KICKER_DISTANCE_AUTO_FAR_2_Bounce = 9,
		KICKER_DISTANCE_AUTO_FAR_3_Bounce = 8,
		KICKER_DISTANCE_AUTO_MIDDLE_1 = 11,
		KICKER_DISTANCE_AUTO_MIDDLE_2 = 12,
		KICKER_DISTANCE_AUTO_NEAR = 13
		
	};

private:
	SpeedController * motor;
	SpeedController * roller;
	Solenoid * solenoid;
	AnalogChannel * sensor;
	Encoder * kickerSensor;
	Encoder * rollerSensor;
	
	// Map kicker distances to encoder ticks
	int getEncoderPosition(KickerDistances distance);
	
	bool wantToKick;
	int count;
	int oldPosition;
	bool kickerReady;
	bool rollerOn;
	bool doLock;
	bool enabled;
	int kickDoneCounts;
	
	
	
	KickerStates cur_state;	 // FSM index
	KickerStates next_state;  // FSM Pusher
	KickerDistances distance, wantedDistance; //Distance index
	
public:
	Kicker(int winchMotorIn, int  solenoidIn, int rollerMotorIn, 
			int kickerEncoderAChannel, int kickerEncoderBChannel,
			int rollerEncoderAChannel, int rollerEncoderBChannel);
	
	~Kicker();
	void handle(); // prod the FSM
	void setDistance(KickerDistances dist);
	void kick();  // make it kick on commands
	void idle(); // set FSM to idle. This should let you kick, but will do nothing after
	void run();  // set FSM back to run. This should also happen when you hit a distance button
	void setRoller(bool isRollerOn);
	KickerDistances getLockStatus();
	bool getBallLock();
	void enable();
	void disable();
	void lock();
	bool kickerLocked();
	Encoder * getRollerEncoder();
	Encoder * getKickerEncoder();
	int winchSetpoint;
	
	bool kickerPastHome();
	
	PID * winchPID;
	
};

#endif
