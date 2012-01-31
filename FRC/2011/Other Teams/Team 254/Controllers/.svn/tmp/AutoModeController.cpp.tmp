#include "AutoModeController.h"
#include "../RAWCRobot.h"
#include "../RAWCConstants.h"
#include "../accelFilter.h"
#include "../PID.h"
#include <math.h>

AutoModeController* AutoModeController::singletonInstance = NULL;

/// Creates (if needed) and returns a sidngleton Control Board instance
AutoModeController* AutoModeController::getInstance()
{
	if( singletonInstance == NULL)
		singletonInstance = new AutoModeController(); 
	return singletonInstance;
}

AutoModeController::AutoModeController()
{
	bot = RAWCRobot::getInstance();
	settleCounts = 0;
	count = 0;
	timer = new Timer();
	driveStopping = false;
	turnPID = new PID( 0.0 , 0.000 , 0.000,0.0,0.0, 0.0,0.0,0.0,0.0);
	headingSP = 0;
	curCmd = cmdNULL;
	startPosition = 0;
	overBumpState = DRIVE_TO_BUMP;
	goDownBumpCounts = 0;
	offsetHeading = 0;
	
	pitchLowPass = DaisyFilter::SinglePoleIIRFilter(0.4f);
	
	reset();
}

void AutoModeController::addCommand(RobotCommandNames_e cmd, 
					cmdArg arg1, cmdArg arg2, cmdArg arg3)
{
	// Make the new command
	RobotCommand newCmd;
	newCmd.cmd = cmd;
	newCmd.arg1 = arg1;
	newCmd.arg2 = arg2;
	newCmd.arg3 = arg3;
	
	// add it to the end of the list
	cmdList.push_back(newCmd);
}

void AutoModeController::reset()
{
	RAWCConstants * rc = RAWCConstants::getInstance();
	bot->getPitchGyro()->Reset();
	overBumpState = DRIVE_TO_BUMP;
	goDownBumpCounts = 0;
	//bot->getGyro()->Reset();
	cmdList.clear();
	curCmd = cmdNULL;
	turnPID->changeGains(rc->getValueForKey("turnP"), 
				rc->getValueForKey("turnI"), 
				rc->getValueForKey("turnD"), 
				rc->getValueForKey("integralLimit") ,
				rc->getValueForKey("turnV"),
				rc->getValueForKey("turnA"),
				2.0,
				0.0,
				0.0
				);
	
	for(int i = 0; i < AMT_RATES; i++){
		rates[i] = 0.0;
	}
	filteredPitch = 0;
	offsetHeading = 0;
	timeToWaitForBallLock = 10;
}

bool AutoModeController::handle()
{
	bool result = false;
	bool thisIsNull = false;
	float spin = 0.0;
	
	// Run the command
	switch(curCmd.cmd){
	
	case CMD_DRIVE_DISTANCE_AND_STOP:
		result = driveDistanceAndStop(curCmd.arg1,curCmd.arg2);
		break;
		
	case CMD_DRIVE_DISTANCE_AND_COAST:
		result = driveDistanceAndCoast(curCmd.arg1,curCmd.arg2);
		break;
	
	case CMD_TURN_TO_HEADING:
		result = turnToHeading(curCmd.arg1,curCmd.arg2);
		break;
		
	case CMD_CHANGE_DRIVE_HEADING:
		if(curCmd.arg1 == 0)
			offsetHeading = curCmd.arg1;
		else
			offsetHeading = 0.475 * bot->getGyro()->GetAngle();
		result = true;
	
	case CMD_WAIT:
		result = autoWait(curCmd.arg1);
		break;
	
	case CMD_TURN_ON_ROLLER:
		result = turnOnRoller();
		break;
	
	case CMD_TURN_OFF_ROLLER:
		result = turnOffRoller();
		break;
		
	case CMD_KICK:
		result = kick();
		break;
		
	case CMD_LIMIT_DISTANCE:
		result = checkIfPastGoalAndDriveBack(curCmd.arg1,curCmd.arg2);
		break;
	
	case CMD_DRIVE_TO_BALL_LOCK:
		result = driveUntilBallLock(curCmd.arg1, curCmd.arg2, false);
		break;
	case CMD_DRIVE_TO_BALL_LOCK_AND_STOP:
		result = driveUntilBallLock(curCmd.arg1, curCmd.arg2, true);
		break;
	case CMD_SET_KICKER_DISTANCE:
		result = setKickerDistance(curCmd.arg1);
		break;
	
	case CMD_SHIFT_LOW:
		result = shift(false);
		break;
	
	case CMD_SHIFT_HIGH:
		result = shift(true);
		break;
	case CMD_DRIVE_OVER_BUMP:
		result = driveOverBump(curCmd.arg1, curCmd.arg2);
		break;
		
	case CMD_RESET_GYRO:
		result = true;
		bot->getGyro()->Reset();
		break;
		
	case CMD_TURN_OFF_LINE:
		count++;
		result = false;
		spin = -.75;
		if(count == 17){
			result = true;
			spin = 0.0;
		}
		bot->quickTurn(spin);
		break;
	case CMD_NULL:
		thisIsNull = true;
		doNothing();
		result = true;
		break;
	
	default :
		doNothing();
		result = true;
		break;
	}
	
	// Check if this command is done
	if(result == true || thisIsNull){
		// This command is done, go get the next one
		if(cmdList.size() >0 ){
			curCmd = cmdList.front();
			cmdList.pop_front();
		}
		else curCmd = cmdNULL;
		
		count = 0;
		pitchLowPass->Reset();
	}
	return false;
}

// Drive Functions
void AutoModeController::doNothing()
{
	bot->driveSpeedTurn(0,0);
}

bool AutoModeController::driveDistance(cmdArg distance, cmdArg maxSpeed, cmdArg doStop, cmdArg stopOnBall)
{
	cmdArg speedInFunc = fabs(maxSpeed);
#define DRIVE_DISTANCE_KP .0125
#define DRIVE_DISTANCE_DEADBAND	2
#define DRIVE_DISTANCE_SETTLE_COUNTS	30
#define	DEFAULT_BALL_WAIT		10
	bool ret = false;
	float driveSwap = (distance < 0) ? -1 : 1;
	bool reverse = (distance < 0);
	bool doQT = false;
	if(count == 0){
		timeToWaitForBallLock = DEFAULT_BALL_WAIT;
		if(stopOnBall && bot->kicker->getBallLock())
			timeToWaitForBallLock = 40;
		startPosition = bot->getLeftEncoder()->GetDistance();
		//bobt->getLeftEncoder()->Reset();
		bot->getGyro()->Reset();
		driveStopCounts = 0;
	}
	count++;
	
	if((timeToWaitForBallLock != DEFAULT_BALL_WAIT) && !bot->kicker->getBallLock())
		timeToWaitForBallLock = DEFAULT_BALL_WAIT;
	
	double error = driveSwap * ((distance + startPosition) - bot->getLeftEncoder()->GetDistance());
	float speed = maxSpeed * driveSwap;//DRIVE_DISTANCE_KP * error;
	float turn = 0.0;
	float heading = -offsetHeading;
	
#define DRIVE_STRAIGHT_COEFF	-.055 / speedInFunc //diff on prac bot
	// This used to be .2
	
	turn = ( heading - bot->getGyro()->GetAngle() ) * DRIVE_STRAIGHT_COEFF;
	if(offsetHeading != 0){
		turn = turn * .75;
	}
	if(speed > maxSpeed)
		speed = maxSpeed;
	else if (speed < -maxSpeed)
		speed = maxSpeed;
	//bot->setMode(RAWCRobot::DRIVE_MODE);
	
	
	
	//if(count % 20 == 0);
			//printf("Dis: %f\twanted: %f\terr:%f\tspeed:%f\r\n ",bot->getLeftEncoder()->GetDistance(),(distance+startPosition),error,speed);
	
	
		if(error <= 0 && doStop == false){
			ret = true;
			speed = 0;
		}
		else if( error <= 0 && doStop == true){
			if(!driveStopping)
				driveStopping = true;
			if(!stopOnBall && !driveStopping)
				driveStopCounts = 0;
		}
	
	if(stopOnBall == true){
		if( bot->kicker->getBallLock() && count > timeToWaitForBallLock && bot->kicker->kickerPastHome())
		{
			if(doStop == true)
			{
				driveStopping = true;
				if(!driveStopping)
					driveStopCounts = 0;
			}
			else{
				ret = true;
				speed = 0;
				turn = 0;
			}
			
		}
	}

	if(driveStopping)
	{
		int whenToStop = 6;
		speed = -speed;
		if( bot->inHighGear()){
			;//speed /= ;
			whenToStop += 6;
		}
//		/turn = -.5;
		if(driveStopCounts >= whenToStop){
			ret = true;
			speed = 0;
			turn = 0;
			driveStopping = false;
			//doQT = true;
		}
		driveStopCounts++;
	}
	
	if(ret == true){
		speed = 0;
		turn = 0;
		
	}
	if(doQT){
		bot->quickTurn(turn);
	}
	else
		bot->driveSpeedTurn(speed, turn); //fix this?
	
	return ret;
}
bool AutoModeController::driveDistanceAndCoast(cmdArg distance, cmdArg maxSpeed)
{
	return driveDistance(distance,maxSpeed,false, false);
}

bool AutoModeController::driveDistanceAndStop(cmdArg distance, cmdArg maxSpeed)
{
	return driveDistance(distance,maxSpeed,true, false);
}

bool AutoModeController::driveUntilBallLock(cmdArg maxDistance,cmdArg maxSpeed, bool doStop)
{
	return driveDistance(maxDistance,maxSpeed,doStop,true);
}

// Turn to Heading
bool AutoModeController::turnToHeading(cmdArg heading, cmdArg maxSpeed)
{
	
	static int v = 0;
	static int pos = 0;
	static int retCounts = 0;
	static int settleCounts = 0;
	static double old_error = 0;
	
	if(count == 0){
		pos = 0;
		retCounts = 0;
		old_error = 0;
		settleCounts = 0;
	}
	count++;
	
	int accel =  get_acceleration((int32_t)((0.5 +(heading * 10)) ) - pos, v, 0);
	v = v + accel;
	pos = pos + v;
	
	headingSP = heading;
	bool ret = false;
	double error =  -bot->getGyro()->GetAngle() - headingSP; 
	//double spin = turnPID->calculate(error, v/10.0 , accel / 10.0);
	double spin = turnPID->calculate(error, 0 , 0);
	
	if( error < 1.5 && error > -1.5){
		retCounts++;
		if(retCounts > 10){
			ret = true;
			spin = 0;
		}
	}
	double errorRate = error - old_error;
	if(count > 20 && (errorRate < .1) && (errorRate > -.1) )
	{
		settleCounts++;
		if(settleCounts > 10){
			ret = true;
			spin = 0;
		}
		
	}
	
	bot->quickTurn(-spin);
	//sign fix
	// This is a stub
	//printf("In Turn To Heading. Turning %f units at %f percent speed\r\n", heading, maxSpeed);
	old_error = error;
	return ret ;
}

bool AutoModeController::autoWait(cmdArg seconds){
	if(count == 0){
		timer->Reset();
		timer->Start();
		printf("Start timer - %f\n", seconds);
	}
	count++;
	
	if(timer->Get() >= (double)seconds){
		printf("Timer done!\r\n");
		timer->Stop();
		return true;
	}
	
	return false;
}

bool AutoModeController::turnOnRoller()
{
	bot->kicker->setRoller(true);
	return true;
}

bool AutoModeController::turnOffRoller()
{
	bot->kicker->setRoller(false);
	return true;
}

bool AutoModeController::kick()
{
	if(bot->kicker->kickerLocked()){
		bot->kicker->kick();
		return true;
	}
	return false;
}

bool AutoModeController::setKickerDistance(cmdArg distance)
{
	Kicker::KickerDistances kickerDistances = (Kicker::KickerDistances) distance;
	/*
	switch(kickerDistances){
	case Kicker::KICKER_DISTANCE_AUTO_FAR_1:
		printf("***Setting AF1 | %d\n", Kicker::KICKER_DISTANCE_AUTO_FAR_1);
		bot->kicker->setDistance(Kicker::KICKER_DISTANCE_AUTO_FAR_1);
		break;
	case Kicker::KICKER_DISTANCE_AUTO_FAR_2:
		printf("***Setting AF2 | %d\n",Kicker::KICKER_DISTANCE_AUTO_FAR_2);
		bot->kicker->setDistance(Kicker::KICKER_DISTANCE_AUTO_FAR_2);
		break;
	case Kicker::KICKER_DISTANCE_AUTO_FAR_3:
		printf("***Setting AF3 | %d\n",Kicker::KICKER_DISTANCE_AUTO_FAR_3);
		bot->kicker->setDistance(Kicker::KICKER_DISTANCE_AUTO_FAR_3);
		break;
	default: break;
	
	}
	*/
	bot->kicker->setDistance(kickerDistances);
	return true;
}

bool AutoModeController::shift(bool highGear)
{
	if(highGear)
		bot->askForShift(RAWCRobot::SHIFTER_POS_HIGH);
	else
		bot->askForShift(RAWCRobot::SHIFTER_POS_LOW);
	
	return true;
}

cmdArg AutoModeController::getHeadingSP(){
	return headingSP;
}

bool AutoModeController::checkIfPastGoalAndDriveBack(cmdArg goalPoint, cmdArg maxSpeed){
	static double offset = 0;
	if(count == 0){
		offset = bot->getLeftEncoder()->GetDistance() - goalPoint; 
		offset *= -1;
	}
	count++;
	
	
	if(bot->getLeftEncoder()->GetDistance() < (double) goalPoint){ //effed up
		printf("behind goal point: %f wanted:%f\r\n", bot->getLeftEncoder()->GetDistance(), goalPoint);
		bot->driveSpeedTurn(0, 0); //fix this?
		offset = 0;
		return true;
	}
	/*printf("In front of goal: %f, %f\r\n",bot->getLeftEncoder()->GetDistance(),(double) goalPoint );
	float heading = 0;
#define DRIVE_STRAIGHT_COEFF_2 0
	float turn = ( heading - bot->getGyro()->GetAngle() ) * DRIVE_STRAIGHT_COEFF_2;
	if(maxSpeed > 0)
		maxSpeed = -maxSpeed;
	
	bot->driveSpeedTurn(maxSpeed,turn);
	
	return false;
	*/
	else
		return driveDistanceAndStop(offset,maxSpeed);
}

bool AutoModeController::driveOverBump(cmdArg speed, cmdArg reverse){
	goingOverBumpStates nextState = overBumpState;
	bool ret = false;
	float angle = bot->getPitchGyro()->GetAngle();
	speed = (reverse == 0) ? speed : speed * -1;
	angle = (reverse == 0) ? angle : angle * -1;
	
	filteredPitch = pitchLowPass->Calculate(angle);
	static float oldFilteredPitch = filteredPitch;

	static float oldAngle = angle;
	if(count == 0){
		oldAngle = angle;
	}
	count++;
	
	static int indexRates = 0;

	float realRate = angle - oldAngle;
	float rate = 0.0;
	
	rates[indexRates] = realRate;
	
	//moving average
	for(int i = 0; i < AMT_RATES; i++){
		rate += rates[i];
	}
	rate = rate / AMT_RATES;
	
	rate = filteredPitch - oldFilteredPitch;
	
	indexRates++;
	if(indexRates == AMT_RATES)
		indexRates = 0;
	
	
	
#define UP_BUMP_RATE		1
#define TOP_OF_BUMP_RATE	-1.0//-.3
#define DOWN_BUMP_RATE		-2.0
#define FLAT_AGAIN_RATE	1.25
	static int heyo = 0;
	heyo++;
	if(heyo % 7 == 0)
		;//printf("bump state: %d, %f\r\n", overBumpState, rate);
	
	switch(overBumpState){
	case DRIVE_TO_BUMP:
		if(rate > UP_BUMP_RATE)
			nextState = GO_UP_BUMP;
		break;
		
	case GO_UP_BUMP:
		if(rate < TOP_OF_BUMP_RATE)
			nextState = TOP_OF_BUMP;
		break;
		
	case TOP_OF_BUMP:
		speed /= 2.5;//3; //2; //3
		if(rate < DOWN_BUMP_RATE)
			nextState = GO_DOWN_BUMP;
		break;
		
	case GO_DOWN_BUMP:
		speed = 0;//speed /= -80;
		goDownBumpCounts++;
		if(goDownBumpCounts > 9){
			goDownBumpCounts = 0;
			nextState = AFTER_BUMP;
		}
			
		break;
		
	case AFTER_BUMP:
		speed /= 3;
		if(rate  > FLAT_AGAIN_RATE ){
			nextState = WAIT_FOR_DONE;
		}
		
		break;
	case WAIT_FOR_DONE:
		speed /= 8;
		if(rate < .1 && rate > -.1){
			goDownBumpCounts++;
			if(goDownBumpCounts > 6){
				goDownBumpCounts = 0;
				ret = true;
			
			}
		}
		else
			goDownBumpCounts = 0;
		
	default:
		speed = 0;
		break;
		
	}
	if(ret){
		goDownBumpCounts = 0;
		bot->getPitchGyro()->Reset();
		speed = 0;
		nextState = DRIVE_TO_BUMP;
	}
	
	bot->driveSpeedTurn(speed,0);
	overBumpState = nextState;
	
	oldAngle = angle;
	oldFilteredPitch = filteredPitch;
	return ret;
}

float AutoModeController::getFilteredPitch(){
	return filteredPitch;
}

