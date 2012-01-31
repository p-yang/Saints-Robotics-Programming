#include "Kicker.h"
#include "../RAWCRobot.h"
#include "../RAWCConstants.h"
#include "WPILib.h"


/// Constructor. This will
Kicker::Kicker(int winchMotorIn, int  solenoidIn, int rollerMotorIn, 
		int kickerEncoderAChannel, int kickerEncoderBChannel,
		int rollerEncoderAChannel, int rollerEncoderBChannel)
{
	
	winchPID = new PID(0.00,0,0,0,0,0,0,0.0,0.0);
	RAWCConstants * constants = RAWCConstants::getInstance();
	
	double p = constants->getValueForKey("winchP");
	double i = constants->getValueForKey("winchI");
	double d = constants->getValueForKey("winchD");
	double Imax = constants->getValueForKey("winchI_max");
	double Imin = constants->getValueForKey("winchI_min");
	double lowKI = constants->getValueForKey("lowKI");
	double lowKP = constants->getValueForKey("lowKP");
	
	winchPID->changeGains(p,i,d,Imax,0,0,Imin,lowKI,lowKP);
			
	
	
	// motors and such
	motor = new Victor(winchMotorIn);
	solenoid = new Solenoid(solenoidIn);
	roller = new Victor(rollerMotorIn);
	
	kickerSensor = new Encoder(kickerEncoderAChannel, kickerEncoderBChannel, false);
	rollerSensor = new Encoder(rollerEncoderAChannel, rollerEncoderBChannel, true);
	
	kickerSensor->Start();
	rollerSensor->Start();
	
	//FSM init
	cur_state = STATE_KICKER_STARTUP;
	next_state = cur_state;
	
	// state vars
	wantToKick = false;
	distance = KICKER_DISTANCE_NONE;
	wantedDistance = KICKER_DISTANCE_NONE;
	
	count = 0;
	oldPosition = 0;
	
	kickDoneCounts = 0;
	
	kickerReady = false;
	rollerOn = false;
	doLock = false;
	enabled = true;
	
	winchSetpoint = 0;
}

Kicker::~Kicker()
{
	delete motor;
	delete solenoid;
	delete roller;
	delete kickerSensor;
	delete rollerSensor;
}

void Kicker::setDistance(KickerDistances dist)
{
	wantedDistance = dist;
}

// FSM handler
void Kicker::handle()
{
	static bool isSettling = false;
	static int initCounts = 0;
	static bool doRollerWait  = false;
	int position = kickerSensor->Get();
	float motorValue = 0.0;
	float rollerValue = 0.0;
	bool locked = true;
	static bool rollerWasOn = false;
	static int rollerOutCounts = 0;
	int amountToWait = 4;
	
	// Handle the roller
	if(rollerOn){
		rollerValue = ROLLER_SPEED_DEFAULT_VAL;
		rollerWasOn = true;
		rollerOutCounts = 0;
	}
	else if(rollerWasOn && !rollerOn){
		rollerOutCounts++;
		rollerValue = ROLLER_SPEED_BACKWARD_DEFAULT_VAL;
		if(rollerOutCounts > 5 ){
			rollerWasOn = false;
		}
	}
	
	
	
	switch(cur_state){
	
	case STATE_KICKER_STARTUP:
		motorValue = 0;
		wantedDistance = KICKER_DISTANCE_NONE;
		locked = true;
		if(wantToKick)
			next_state = STATE_KICKER_REZERO;
		break;
		
	case STATE_KICKER_REZERO:
		kickDoneCounts = 0;
		motorValue = 0;
		locked = false;
		if( position > (oldPosition -1) && position < (oldPosition+1)){
			isSettling = true;
			if(initCounts > 5){
				next_state = STATE_KICKER_IDLE;
				if(wantedDistance == KICKER_DISTANCE_NONE)
					wantedDistance = KICKER_DISTANCE_DEFAULT;
				kickerSensor->Reset();
				position = kickerSensor->Get();
				initCounts = 0;
			}
			initCounts++;
		}
		else {
			isSettling = false;
			initCounts = 0;
		}
		doRollerWait = false;
		break;
		
	case STATE_KICKER_IDLE:
		motorValue = 0;
		locked = false;
		kickerReady = true;
		doRollerWait = false;
		initCounts++;
		if(wantToKick){// still holding button
			wantToKick = false;
			locked = false;
			break;
		}
		
		wantToKick = false;
		if (wantedDistance != KICKER_DISTANCE_NONE && initCounts > 10)
			next_state = STATE_KICKER_PULLING;
		
		break;
	
	case STATE_KICKER_PULLING:
		//motorValue = KICKER_PULLING_MOTOR_VAL;
		winchSetpoint = getEncoderPosition(wantedDistance);
		motorValue = winchPID->calculateVoltage(getEncoderPosition(wantedDistance) - position,0,0);
		distance = KICKER_DISTANCE_NONE;
		locked = true;
		//TODO: Replace this with a sensor reading
		if( position >= getEncoderPosition(wantedDistance)){
			next_state = STATE_KICKER_LOCKED;
			distance = wantedDistance;
		}

		break;
	
	case STATE_KICKER_LOCKED:
		doRollerWait = true;
		if(wantedDistance != distance){
			//printf("Change in distance! New: %d | Old: %d\r\n", wantedDistance, distance);
			if( getEncoderPosition(wantedDistance) >  getEncoderPosition(distance))
				next_state = STATE_KICKER_PULLING;
			else
				next_state = cur_state;
		}
		locked = true;
		motorValue = 0;
		break;
		
		
	case STATE_KICKER_KICKING:
		kickDoneCounts++;
	
		if(position >= 45)
			amountToWait -=2;
		else if(position >= 37)
			amountToWait -= 1;
		
#if 1
		if(doRollerWait){
			if(kickDoneCounts >= amountToWait){ //1/8 second
				//release the kicker after a delay for the rollers
				doRollerWait = false;
				locked = false;
			}
			else{
				locked = true;
			}
		}
		else{
			locked = false;
		}
#else
#error
		locked = false;
#endif
		
		
		if(kickDoneCounts < 100){
			rollerValue = 0;
		}
		
		/*
		if(kickDoneCounts > 100){
			if(kickDoneCounts < 150){
				locked = true;
			}
			else if (kickDoneCounts < 200){
				locked = false;
			}
			else
			{
				kickDoneCounts = 100;
			}
		}
		*/
			
		distance = KICKER_DISTANCE_NONE;
		wantedDistance = KICKER_DISTANCE_NONE;
		
		//Dont let it pull until it hits the end
		//TODO: Replace this with a sensor reading
		
		if(position  <= 4){// || kickDoneCounts > 50 * 6){
			wantToKick = false;
			next_state = STATE_KICKER_REZERO;
		}
		break;
	
	case STATE_KICKER_MOVING_DOWN:
		//TODO: Make this do the right thing
		next_state = STATE_KICKER_IDLE;
		break;
	default:
		break;
	}
		
	// Jump to a kick
	if(wantToKick && kickerReady)
		next_state = STATE_KICKER_KICKING;
	
	// Keep track of sensor
	oldPosition = position;
		
	if(doLock)
		locked = true;
	
	// Set outputs
	locked = (locked) ? KICKER_LOCK_ENGAGED : KICKER_LOCK_OPEN;

	solenoid->Set(locked);
	doLock = false;
	
	// Clip the motor
	if(motorValue < 0)
	{
		motorValue = 0;
	}
	if(enabled)
		motor->Set(motorValue);
	else motor->Set(0);
	
	roller->Set(-rollerValue);
	
	// Poke state machine
	cur_state = next_state;
	
	count++;
	if(count % 7 == 0) {
		//printf("Encoder count: %d\n", position);
		//printf("State: %d , Kicker Pos: %d - Wanted Dist: %d - Wanted Pos: %d - R: %d - M: %f\r\n", 
			//			cur_state, position , wantedDistance, (int) getEncoderPosition(wantedDistance), (int) rollerOn, motorValue);
				//printf("Kicker State: %d \tSensor: %d\tMotor: %f\tLock: %d\r\n", cur_state, position,motorValue,locked);

	}
}

/// Transform the KickerDistances enum into real world pot/encoder values
int Kicker::getEncoderPosition(KickerDistances distance)
{
	RAWCConstants * rc = RAWCConstants::getInstance();
	int retValue;
	
	switch(distance)
	{
	case KICKER_DISTANCE_NONE:
		retValue = 0;
		break;
	case KICKER_DISTANCE_NEAR:
		retValue = (int) rc->getValueForKey("KickerPosNear");
		break;
	case KICKER_DISTANCE_MIDDLE:
		retValue = (int) rc->getValueForKey("KickerPosMiddle");
		break;
	case KICKER_DISTANCE_FAR:
		retValue =(int) rc->getValueForKey("KickerPosFar");
		break;
	case KICKER_DISTANCE_DEFAULT:
		retValue = (int) rc->getValueForKey("KickerPosShort");
		break;
	case KICKER_DISTANCE_AUTO_FAR_1:
		retValue = (int) rc->getValueForKey("KickerPosAutoFar1");
		break;
	case KICKER_DISTANCE_AUTO_FAR_2:
		retValue = (int) rc->getValueForKey("KickerPosAutoFar2");
		break;
	case KICKER_DISTANCE_AUTO_FAR_3:
		retValue = (int) rc->getValueForKey("KickerPosAutoFar3");
		break;
	case KICKER_DISTANCE_AUTO_FAR_1_Bounce:
		retValue = (int) rc->getValueForKey("KickerPosAutoFar1Bounce");
		break;
	case KICKER_DISTANCE_AUTO_FAR_2_Bounce:
		retValue = (int) rc->getValueForKey("KickerPosAutoFar2Bounce");
		break;
	case KICKER_DISTANCE_AUTO_FAR_3_Bounce:
		retValue = (int) rc->getValueForKey("KickerPosAutoFar3Bounce");
		break;
	case KICKER_DISTANCE_AUTO_MIDDLE_1:
		retValue = (int) rc->getValueForKey("KickerPosAutoMiddle1");
		break;
	case KICKER_DISTANCE_AUTO_MIDDLE_2:
		retValue = (int) rc->getValueForKey("KickerPosAutoMiddle2");
		break;
	case KICKER_DISTANCE_AUTO_NEAR :
		retValue = (int) rc->getValueForKey("KickerPosAutoNear");
		break;
	default:
		retValue = 0;
		break;
	}
	
	//printf("-- Setting Distance: %d | pos: %d\n", distance, retValue);
	
	return retValue;
}

/// Tell kicker to kick
void Kicker::kick()
{
	wantToKick = true;
}

/// Sets whether the roller is on or off
void Kicker::setRoller(bool isRollerOn){
	rollerOn = isRollerOn;
}

/// Tell kicker to sit and wait
void Kicker::idle()
{
	// set FSM to idle. This should let you kick, but will do nothing after
}

/// Tell kicker to start functioning as normal
void Kicker::run()
{
	
}

/// Get whether the kicker is locked in
Kicker::KickerDistances Kicker::getLockStatus()
{
	if(cur_state == STATE_KICKER_LOCKED)
		return distance;
	else return KICKER_DISTANCE_NONE;
}

bool Kicker::getBallLock()
{
	if(rollerOn && (cur_state != STATE_KICKER_KICKING)){
		if( rollerSensor->GetRate() < 300 &&  rollerSensor->GetRate() > -300 )
			return true;
	}
	return false;
}

void Kicker::disable()
{
	enabled = false;
}

void Kicker::enable()
{
	enabled = true;
}
void Kicker::lock(){
	doLock = true;
}

Encoder * Kicker::getRollerEncoder(){
	return rollerSensor;
}
Encoder * Kicker::getKickerEncoder(){
	return kickerSensor;
}

bool Kicker::kickerLocked()
{
	return (cur_state == STATE_KICKER_LOCKED) || (cur_state == STATE_KICKER_STARTUP);
}

bool Kicker::kickerPastHome()
{
	return (kickerSensor->Get() >= getEncoderPosition(KICKER_DISTANCE_DEFAULT));
}
