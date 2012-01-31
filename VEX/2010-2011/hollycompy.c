#pragma config(Sensor, in1,    armPot,              sensorPotentiometer)
#pragma config(Sensor, in2,    autoSet,             sensorPotentiometer)
#pragma config(Sensor, in3,    battV,               sensorPotentiometer)
#pragma config(Sensor, in4,    kill,                sensorTouch)
#pragma config(Sensor, in5,    encoder,             sensorRotation)
#pragma config(Motor,  port1,           rearRight,            tmotorNormal)
#pragma config(Motor,  port2,           rearLeft,             tmotorNormal)
#pragma config(Motor,  port3,           frontRight,           tmotorNormal)
#pragma config(Motor,  port4,           frontLeft,            tmotorNormal)
#pragma config(Motor,  port5,           convLeft,             tmotorNormal)
#pragma config(Motor,  port6,           convRight,            tmotorNormal)
#pragma config(Motor,  port8,           arm,                  tmotorNormal)
//*!!Code automatically generated by 'ROBOTC' configuration wizard               !!*//

/*1899 Competition Code
Written by Preetum Nakkiran and Edward Jiang
Saints Robotics in conjunction with Exothermic Robotics
*/


//Defines the platform this code will be functional for.
#pragma platform(VEX)

//Competition Control and Duration Settings
#pragma competitionControl(Competition)
#pragma autonomousDuration(20)
#pragma userControlDuration(180)

//Main competition background code...do not modify!
#include "Vex_Competition_Includes.c"

//Drive variables
int fr=0;
int fl=0;
int rr=0;
int rl=0;
int x,y,d,r,enc;
int flip = 1;
int rotSpd = 3;
int rearRightVs,rearLeftVs,frontRightVs,frontLeftVs;
int rotV;
int k = 1;

//PID variables
int armTarget = SensorValue[armPot] - 130;
int armPotVal = 0;
int armOut = 0;
int armError = 0;
int prevArmButton = 0;
int Kp = 2;
int armTargetState = 5; // 0 through 4, for the different goal heights

//Other variables
int autoMode;
int powerComp;

//Functions
int abs(int a);
static int seedF(int a);
static int sqrt(int a);
static int sqrtWiki(int num);
task pid();



//All tasks that occur before the competition starts
//Example: clearing encoders, setting motor reflectives, setting servo positions
void pre_auton()
{
	bMotorReflected[frontRight] = true;
	bMotorReflected[rearRight] = true;
	bMotorReflected[rearLeft] = true;
	bMotorReflected[frontLeft] = true;
	bMotorReflected[convRight] = true;
}

//Autonomous code goes here
task autonomous()
{
	powerComp = 149 - (SensorValue[battV] / 20) + ((SensorValue[battV]/10)*(SensorValue[battV]/10) *3/ 10000);
  if(SensorValue[autoSet] < 50) {
  	autoMode = 1;
  }
  else if(SensorValue[autoSet] < 200) {
  	autoMode = 2;
  }
  else if(SensorValue[autoSet] < 400) {
  	autoMode = 3;
  }
  else if(SensorValue[autoSet] < 600) {
  	autoMode = 4;
  }
  else if(SensorValue[autoSet] < 800) {
  	autoMode = 5;
  }
  else if(SensorValue[autoSet] < 1000) {
  	autoMode = 6;
  }
  else {
  	autoMode = 7;
  }
  switch(autoMode) {
  	case 1:
  	case 2:

		  armTarget = 305;
	  	StartTask(pid);
	    motor[frontLeft] = 64;
	    motor[frontRight] = -64;
	    motor[rearLeft] = 64;
	    motor[rearRight] = -64;
	  	wait10Msec(25);

	    //Now we move forwards
	    while(SensorValue[kill] == 0) {
	    motor[frontLeft] = 127;
	    motor[frontRight] = -127;
	    motor[rearLeft] = 127;
	    motor[rearRight] = -127;
	    }
	    allmotorsoff();
	    wait10Msec(50);
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < ((autoMode == 1) ? (enc + 17) : (enc + 18))) {
	    if(autoMode == 1) {
		    //Now we turn right
		    motor[frontLeft] = 64*powerComp/128;
		    motor[frontRight] = 64*powerComp/128;
		    motor[rearLeft] = 64*powerComp/128;
		    motor[rearRight] = 64*powerComp/128;
		    //wait10Msec(60);
		  } else {
		  	//Now we turn left
		    motor[frontLeft] = -64*powerComp/128;
		    motor[frontRight] = -64*powerComp/128;
		    motor[rearLeft] = -64*powerComp/128;
		    motor[rearRight] = -64*powerComp/128;
		    //wait10Msec(80);
		  } }

      if(autoMode == 1) {
		    //Now diagonal
		    while(SensorValue[kill] == 0) {
		    motor[frontLeft] = 80;//95
		    motor[frontRight] = -80;
		    motor[rearLeft] = 80;
		    motor[rearRight] = -80;//95
		    }
		  } else {
		    //Now diagonal
		    while(SensorValue[kill] == 0) {
		    motor[frontLeft] = 80;
		    motor[frontRight] = -80;
		    motor[rearLeft] = 80;
		    motor[rearRight] = -80;
		    }
		  }
		  wait10Msec(50);

	    //wait10Msec(300);
	    //Now we back up
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 9)) {
	    motor[frontLeft] = -60*powerComp/128;
	    motor[frontRight] = 60*powerComp/128;
	    motor[rearLeft] = -60*powerComp/128;
	    motor[rearRight] = 60*powerComp/128;
      }
	    //wait10Msec(40);

	    //Stop!
	    motor[frontLeft] = 0;
	    motor[frontRight] = 0;
	    motor[rearLeft] = 0;
	    motor[rearRight] = 0;
/*
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 1)) {
	    if(autoMode == 1) {
		    //Slide to the left!
		    motor[frontLeft] = -64;
		    motor[frontRight] = -64;
		    motor[rearLeft] = 64;
		    motor[rearRight] = 64;
	    } else {
	    	//Slide to the right!
		    motor[frontLeft] = 64;
		    motor[frontRight] = 64;
		    motor[rearLeft] = -64;
		    motor[rearRight] = -64;
	    }}
      allmotorsoff();*/
	    //Now we raise to 1
	    armTarget = 80;
	    wait10Msec(100);

	    //Eject 1
	    motor[convLeft] = 64*powerComp/128;
	    motor[convRight] = 64*powerComp/128;
	    wait10Msec(100);
	    motor[convLeft] = 0;
	    motor[convRight] = 0;

	    armTarget = 305;
	    StartTask(pid);
	    wait10Msec(25);

      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 8)) {
	    if(autoMode == 1) {
		    //Now we turn right
		    motor[frontLeft] = 64*powerComp/128;
		    motor[frontRight] = 64*powerComp/128;
		    motor[rearLeft] = 64*powerComp/128;
		    motor[rearRight] = 64*powerComp/128;
		    //wait10Msec(30);
		  } else {
		    //Now we turn left
		    motor[frontLeft] = -64*powerComp/128;
		    motor[frontRight] = -64*powerComp/128;
		    motor[rearLeft] = -64*powerComp/128;
		    motor[rearRight] = -64*powerComp/128;
		    //wait10Msec(30);
		  } }

	    //Now we move forwards
	    while(SensorValue[kill] == 0) {
	    motor[frontLeft] = 127;
	    motor[frontRight] = -127;
	    motor[rearLeft] = 127;
	    motor[rearRight] = -127;
	    }

	    allmotorsoff();
	    wait10Msec(25);
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 18)) {
	    if(autoMode == 1) {
		    //Now we turn right
		    motor[frontLeft] = 64*powerComp/128;
		    motor[frontRight] = 64*powerComp/128;
		    motor[rearLeft] = 64*powerComp/128;
		    motor[rearRight] = 64*powerComp/128;
		    //wait10Msec(80);
		  } else {
		    //Now we turn left
		    motor[frontLeft] = -64*powerComp/128;
		    motor[frontRight] = -64*powerComp/128;
		    motor[rearLeft] = -64*powerComp/128;
		    motor[rearRight] = -64*powerComp/128;
		    //wait10Msec(80);
		  }}

		  if(autoMode == 1) {
		    //Now diagonal
		    while(SensorValue[kill] == 0) {
		    motor[frontLeft] = 80;//95
		    motor[frontRight] = -80;
		    motor[rearLeft] = 80;
		    motor[rearRight] = -80;//95
		    }
		  } else {
		    //Now diagonal
		    while(SensorValue[kill] == 0) {
		    motor[frontLeft] = 80;
		    motor[frontRight] = -80;
		    motor[rearLeft] = 80;
		    motor[rearRight] = -80;
		    }
		  }
		  wait10Msec(50);

	    //wait10Msec(300);
	    //Now we back up
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 9)) {
	    motor[frontLeft] = -60*powerComp/128;
	    motor[frontRight] = 60*powerComp/128;
	    motor[rearLeft] = -60*powerComp/128;
	    motor[rearRight] = 60*powerComp/128;
      }
	    //wait10Msec(40);

	    //Stop!
	    motor[frontLeft] = 0;
	    motor[frontRight] = 0;
	    motor[rearLeft] = 0;
	    motor[rearRight] = 0;

	    //Now we raise to 1
	    armTarget = 80;
	    wait10Msec(100);

	    //Eject 1
	    motor[convLeft] = 64*powerComp/128;
	    motor[convRight] = 64*powerComp/128;
	    wait10Msec(100);
	    motor[convLeft] = 0;
	    motor[convRight] = 0;

	    //Raise
	    armTarget = 150;
	    wait10Msec(25);

      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 20)) {
	    if(autoMode == 1) {
		    //Slide to the right!
		    motor[frontLeft] = 127;
		    motor[frontRight] = 127;
		    motor[rearLeft] = -127;
		    motor[rearRight] = -127;
		    //wait10Msec(25);
	    } else {
	    	//Slide to the left!
		    motor[frontLeft] = -127;
		    motor[frontRight] = -127;
		    motor[rearLeft] = 127;
		    motor[rearRight] = 127;
		    //wait10Msec(25);
	    }}

	    armTarget = 0;
	    wait10Msec(100);
	    StopTask(pid);
  	  break;
  	case 3:
  	case 4:
		  armTarget = 450;
	  	StartTask(pid);
	  	wait10Msec(150);
	    //Bump forwards
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 9)) {
	    motor[frontLeft] = 60*powerComp/128;
	    motor[frontRight] = -60*powerComp/128;
	    motor[rearLeft] = 60*powerComp/128;
	    motor[rearRight] = -60*powerComp/128;
      }

	    //Stop!
	    motor[frontLeft] = 0;
	    motor[frontRight] = 0;
	    motor[rearLeft] = 0;
	    motor[rearRight] = 0;

	    //Eject 1
	    motor[convLeft] = 64*powerComp/128;
	    motor[convRight] = 64*powerComp/128;
	    wait10Msec(100);
	    motor[convLeft] = 0;
	    motor[convRight] = 0;

	    //Reverse
      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 9)) {
	    motor[frontLeft] = -60*powerComp/128;
	    motor[frontRight] = 60*powerComp/128;
	    motor[rearLeft] = -60*powerComp/128;
	    motor[rearRight] = 60*powerComp/128;
      }

      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 15)) {
	    if(autoMode == 4) {
	    	//Slide to the right!
		    motor[frontLeft] = 64;
		    motor[frontRight] = 64;
		    motor[rearLeft] = -64;
		    motor[rearRight] = -64;
	    } else {
		    //Slide to the left!
		    motor[frontLeft] = -64;
		    motor[frontRight] = -64;
		    motor[rearLeft] = 64;
		    motor[rearRight] = 64;
	    }}

      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < ((autoMode == 4) ? (enc + 1) : (enc + 2))) {
	    if(autoMode == 4) {
		    //Now we turn right
		    motor[frontLeft] = 64*powerComp/128;
		    motor[frontRight] = 64*powerComp/128;
		    motor[rearLeft] = 64*powerComp/128;
		    motor[rearRight] = 64*powerComp/128;
		    //wait10Msec(30);
		  } else {
		    //Now we turn left
		    motor[frontLeft] = -64*powerComp/128;
		    motor[frontRight] = -64*powerComp/128;
		    motor[rearLeft] = -64*powerComp/128;
		    motor[rearRight] = -64*powerComp/128;
		    //wait10Msec(30);
		  } }
	    //Now we move forwards
	    while(SensorValue[kill] == 0) {
	    motor[frontLeft] = 127;
	    motor[frontRight] = -127;
	    motor[rearLeft] = 127;
	    motor[rearRight] = -127;
	    }

      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 16)) {
	    if(autoMode == 4) {
		    //Now we turn right
		    motor[frontLeft] = 64*powerComp/128;
		    motor[frontRight] = 64*powerComp/128;
		    motor[rearLeft] = 64*powerComp/128;
		    motor[rearRight] = 64*powerComp/128;
		    //wait10Msec(60);
		  } else {
		  	//Now we turn left
		    motor[frontLeft] = -64*powerComp/128;
		    motor[frontRight] = -64*powerComp/128;
		    motor[rearLeft] = -64*powerComp/128;
		    motor[rearRight] = -64*powerComp/128;
		    //wait10Msec(80);
		  } }

      if(autoMode == 4) {
		    //Now diagonal
		    //while(SensorValue[kill] == 0) {
		    motor[frontLeft] = 80;//95
		    motor[frontRight] = -80;
		    motor[rearLeft] = 80;
		    motor[rearRight] = -80;//95
		    //}
		  } else {
		    //Now diagonal
		    //while(SensorValue[kill] == 0) {
		    motor[frontLeft] = 80;
		    motor[frontRight] = -80;
		    motor[rearLeft] = 80;
		    motor[rearRight] = -80;
		    //}
		  }
		  wait10Msec(500);

	    //Eject 1
	    motor[convLeft] = 64*powerComp/128;
	    motor[convRight] = 64*powerComp/128;
	    wait10Msec(100);
	    motor[convLeft] = 0;
	    motor[convRight] = 0;

      enc = SensorValue[encoder];
	    while(SensorValue[encoder] < (enc + 9)) {
	    motor[frontLeft] = -60*powerComp/128;
	    motor[frontRight] = 60*powerComp/128;
	    motor[rearLeft] = -60*powerComp/128;
	    motor[rearRight] = 60*powerComp/128;
      }
      allmotorsoff();
		  break;
		  case 5:
			  enc = SensorValue[encoder];
		    while(SensorValue[encoder] < (enc + 70)) {
		    motor[frontLeft] = 127;
		    motor[frontRight] = -127;
		    motor[rearLeft] = 127;
		    motor[rearRight] = -127;
        }
		    StartTask(pid);
		    armTarget = 200;
		    motor[frontLeft] = 127;
		    motor[frontRight] = 127;
		    motor[rearLeft] = 127;
		    motor[rearRight] = 127;
		  break;
  }
}


task usercontrol()
{
	 StartTask(pid);
	 //User control code here, inside the loop
	 while(true)
	 {

		x = flip*vexRT[Ch4];
		y = vexRT[Ch3];

		switch(vexRT[Ch6]) {
			case 127:
			rotSpd = 2;
			break;
			case -127:
			rotSpd = 3;
			break;
		}
		switch(vexRT[Ch5]) {
			case 127:
			flip = 1;
			break;
			case -127:
			flip = -1;
			break;
		}

		r = vexRT[Ch1];
		r = (((r*3)/25)*((r*3)/25)*((r*3)/25)/27 + r)/2*rotSpd/4;
		rotV = 1*r;

		d = sqrt(x*x + y*y);

		if (d > 127)
		{
			x = x * 127 / d;
			y = y * 127 / d;
			d = 127;
		}

		frontRightVs = -k*99*(y-x)/140;
		rearLeftVs = -frontRightVs;

		frontLeftVs = k*99*(y+x)/140;
		rearRightVs = -frontLeftVs;

		fr = (frontRightVs*d + rotV*abs(r))/(d+abs(r));
		fl	= (frontLeftVs*d + rotV*abs(r))/(d+abs(r));
		rl	 = (rearLeftVs*d + rotV*abs(r))/(d+abs(r));
		rr	= (rearRightVs*d + rotV*abs(r))/(d+abs(r));

		motor[frontRight] = (frontRightVs*d + rotV*abs(r))/(d+abs(r));
		motor[frontLeft]	= (frontLeftVs*d + rotV*abs(r))/(d+abs(r));
		motor[rearLeft]		= (rearLeftVs*d + rotV*abs(r))/(d+abs(r));
		motor[rearRight]	= (rearRightVs*d + rotV*abs(r))/(d+abs(r));

		/*
		motor[frontRight] = frontRightVs;
		motor[frontLeft]	= frontLeftVs;
		motor[rearLeft]		= rearLeftVs;
		motor[rearRight]	= rearRightVs;

		motor[frontRight] = r;
		motor[frontLeft]	= r;
		motor[rearLeft]		= r;
		motor[rearRight]	= r;
		*/

		// Conveyor control
		motor[convLeft] = vexRT[Ch2Xmtr2];
		motor[convRight] = vexRT[Ch2Xmtr2];

		//Here comes arm code!
/****************
New Button Mode Thing
This bit of code basically sets the state based on button pressed. See inline.
*****************/
		if(vexRT[Ch5Xmtr2] > 0) {
			//Left top = 1 high
			armTargetState = 1;
		}
		else if (vexRT[Ch5Xmtr2] < 0) {
			//Left bottom = 3 high
			armTargetState = 2;
		}
		if(vexRT[Ch6Xmtr2] > 0) {
			//Right top = 5 high
			armTargetState = 3;
		}
		else if (vexRT[Ch6Xmtr2] < 0) {
			//Right bottom = 7 high
			armTargetState = 4;
		}
		if(vexRT[Ch1Xmtr2] < -100) {
			//Right Thumb -> left = 0 high
			armTargetState = 0;
		}
		else if(vexRT[Ch1Xmtr2] > 100) {
			//Right Thumb -> right = 7 high
			armTargetState = 4;
		}
		if(vexRT[Ch3Xmtr2] > 30 || vexRT[Ch3Xmtr2] < -30) {
			//5 is manual control with left thumbstick
			armTargetState = 5;
		}

//This switch sets the target potentiometer position based on state.

		switch (armTargetState) {
			case 0:
				armTarget = 0;
				break;
			case 1:
				armTarget = 70;
				break;
			case 2:
				armTarget = 200;
				break;
			case 3:
				armTarget = 300;
				break;
			case 4:
				armTarget = 425;
				break;
		}

		armPotVal = SensorValue[armPot] - 130; // shift pot value so 0 is min

		if(armTarget > 510 || armTarget < 0) {
			armTarget = (armTarget > 510) ? 510 : 0;
		}
/*
//Controls the arm manually if state 5

		if(armTargetState == 5 && vexRT[Ch3Xmtr2] > 30 || vexRT[Ch3Xmtr2] < -30) {
			armTarget = armPotVal + ((vexRT[Ch3Xmtr2] > 30) ? 40 : -40);

			if (armTarget < 0 || armTarget > 510) {
				armError = ((armTarget < 0) ? 0 : 510) - armPotVal;
				armOut = armError * Kp / ((armError > 0) ? 1 : 3; // if moving down, go slower
			} else {
				armOut = vexRT[Ch3Xmtr2];
			}
		}
		else { //Otherwise PID
			armError = armTarget - armPotVal;
			armOut = armError * Kp;
		}

		motor[arm] = armOut;*/
	 }
}

int abs(int a)
{
	return a<0?-a:a;
}

static int seedF(int a)
{
  int t = 1;
  while (a > 0)
  {
    //a = (a >> 1);
  	a /= 4;
    t *= 2;
  }
  return t;
  //return 1 << (t / 2);

  //return 25;
}

static int sqrt(int a)
{
  if (a == 0) return 0;
  int num = 64; //10
  //UNCOMMENT TO ENABLE DYNAMIC SEEDING
  num = seedF(a);

  int numPrev = 0;
  do
  {
    numPrev = num;
    num = (num + (a / num)) /2;
  }
  while (!(num == numPrev || num * num < a && (num + 1) * (num + 1) > a));
  return num;
}

static int sqrtWiki(int num)
{
  int op = num;
  int res = 0;
  int one = 1 << 14; // The second-to-top bit is set: 1L<<30 for long

  // "one" starts at the highest power of four <= the argument.
  while (one > op)
    one = one / 4;

  while (one != 0)
  {
    if (op >= res + one)
    {
      op -= res + one;
      res += one * 2;
    }
    res /= 2;
    one /= 4;
  }
  return res;
}

task pid() {
  	int armPotVal = 0;
  	int armOut = 0;
  	int armError = 0;
  	int Kp = 2;

  	while(true) {
		  armPotVal = SensorValue[armPot] - 130; // shift pot value so 0 is min

		  if(armTarget > 510 || armTarget < 0) {
		  	armTarget = (armTarget > 510) ? 510 : 0;
		  }

		    armError = armTarget - armPotVal;
		    armOut = armError * Kp;

	      motor[arm] = armOut;
    }
}
