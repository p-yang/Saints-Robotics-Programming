#ifndef _AUTO_CONTROLLER_H
#define _AUTO_CONTROLLER_H
#include "../RAWCRobot.h"
#include "../PID.h"
#include "../DaisyFilter.h"

// What type of argument?
typedef float cmdArg;



typedef enum RobotCommandNames_e
{
	CMD_NULL = 0,
	CMD_DRIVE_DISTANCE_AND_COAST,
	CMD_DRIVE_DISTANCE_AND_STOP,
	CMD_TURN_TO_HEADING,
	CMD_WAIT,
	CMD_TURN_ON_ROLLER,
	CMD_TURN_OFF_ROLLER,
	CMD_SET_KICKER_DISTANCE,
	CMD_KICK,
	CMD_SHIFT_LOW,
	CMD_SHIFT_HIGH,
	CMD_DRIVE_TO_BALL_LOCK,
	CMD_DRIVE_TO_BALL_LOCK_AND_STOP,
	CMD_LIMIT_DISTANCE,
	CMD_DRIVE_OVER_BUMP,
	CMD_RESET_GYRO,
	CMD_TURN_OFF_LINE,
	CMD_CHANGE_DRIVE_HEADING
}RobotCommandNames_e;

typedef struct RobotCommand{
	RobotCommandNames_e cmd;
	cmdArg arg1;
	cmdArg arg2;
	cmdArg arg3;
}RobotCommand;

// A dead command for use later
const RobotCommand cmdNULL =
{
	CMD_NULL,
	0,
	0,
	0
};

class AutoModeController
{
private:
	
	static AutoModeController *singletonInstance;
	
	enum goingOverBumpStates{
		DRIVE_TO_BUMP,
		GO_UP_BUMP,
		TOP_OF_BUMP,
		GO_DOWN_BUMP,
		AFTER_BUMP,
		WAIT_FOR_DONE
	};
	
	Timer * timer;
	RAWCRobot *bot;
	deque<RobotCommand> cmdList;
	RobotCommand curCmd;
	bool firstRun;
	unsigned int count;
	unsigned int settleCounts;
	unsigned int driveStopCounts;
	bool driveStopping;
	
#define AMT_RATES	8
	float filteredPitch;
	float rates[AMT_RATES];
	
	goingOverBumpStates overBumpState;
	int goDownBumpCounts;
	
	cmdArg headingSP;
	
	// Drive Distance
	bool driveDistance(cmdArg distance, cmdArg maxSpeed, cmdArg doStop,  cmdArg stopOnBall);
	bool driveDistanceAndCoast(cmdArg distance, cmdArg maxSpeed);
	bool driveDistanceAndStop(cmdArg distance, cmdArg maxSpeed);
	bool autoWait(cmdArg seconds);
	bool turnOnRoller();
	bool turnOffRoller();
	bool kick();
	bool setKickerDistance(cmdArg distance);
	bool shift(bool wantedPos);
	bool driveUntilBallLock(cmdArg maxDistance,cmdArg maxSpeed, bool doStop);
	bool checkIfPastGoalAndDriveBack(cmdArg goalPoint, cmdArg maxSpeed);
	bool driveOverBump(cmdArg speed, cmdArg reverse);
	double startPosition;
	DaisyFilter * pitchLowPass;
	
	int timeToWaitForBallLock;
	
	int start;
	
	PID *turnPID;
	PID *drivePID;
	
	//Turn To heading
	bool turnToHeading(cmdArg heading, cmdArg maxSpeed);
	
	// do nothing
	void doNothing();
	
	float offsetHeading;
	
public:
	float getFilteredPitch();
	AutoModeController();
	static AutoModeController* getInstance();
	void addCommand(RobotCommandNames_e cmd, cmdArg arg1, cmdArg arg2, cmdArg arg3);
	
	cmdArg getHeadingSP();
	
	bool handle();
	void reset();
	
};


#endif

