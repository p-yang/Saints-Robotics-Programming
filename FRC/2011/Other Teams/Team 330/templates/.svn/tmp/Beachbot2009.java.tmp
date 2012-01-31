/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.BeachbotEncoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Dashboard;
//import edu.wpi.first.wpilibj.camera.AxisCamera;

//used by dashboard
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Beachbot2009 extends IterativeRobot {
    private RobotDrive drivetrain;
    private Jaguar turntable;
    private Victor pickup;
    private Victor dumperA;
    private Victor dumperB;
    private Joystick steeringWheel;
    private Joystick driverJoystick;
    private Joystick operatorJoystick;
    private BeachbotEncoder turntableEncoder;
    private PIDController turntableController;
    private Gyro baseGyro;
    private PIDController baseGyroController;
    private DriverStationLCD driverStationLCD;
//    private AxisCamera camera;

//    private int teleopLoops = 0;
    private int autoMode = 0;
    static final int numAutoModes = 2;

    private boolean prevButton4 = false;
    private boolean prevButton5 = false;

    private double autoTimer = 0;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        final double TURNTABLEWHEEL = 1.625;
        final int TURNTABLEWHEELGEARRATIO = 22;
        final int TURNTABLEENCODERGEARRATIO = 2;
        final double TURNTABLEFUDGEFACTOR = 1.06;
        final int TURNTABLETICKSPERREVOLUTION = 128;
        final int DEGREESPERREVOLUTION = 360;
        

        drivetrain = new RobotDrive(1,3);
        turntable = new Jaguar(7);
        pickup = new Victor(8);
        dumperA = new Victor(9);
        dumperB = new Victor(10);
        steeringWheel = new Joystick(1);
        driverJoystick = new Joystick(2);
        operatorJoystick = new Joystick(3);
        turntableEncoder = new BeachbotEncoder(9, 10, false, BeachbotEncoder.EncodingType.k4X);
        turntableController = new PIDController(0.03, 0, 0, turntableEncoder, turntable, 0.005);
        baseGyro = new Gyro(1);
        baseGyroController = new PIDController(0.03, 0, 0, baseGyro, turntable, 0.005);


        turntableEncoder.setDistancePerPulse(DEGREESPERREVOLUTION/(TURNTABLEWHEELGEARRATIO/TURNTABLEWHEEL*TURNTABLEFUDGEFACTOR*TURNTABLEENCODERGEARRATIO*TURNTABLETICKSPERREVOLUTION));
        turntableEncoder.start();
        turntableController.setSetpoint(0);
        turntableController.setOutputRange(-0.8, 0.8);

        baseGyro.setSensitivity(0.006);
        baseGyro.reset();
        baseGyroController.setSetpoint(0);
        baseGyroController.setOutputRange(-0.8, 0.8);

        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Auto   : MoonRock");
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "Drive  : Arc + Abs");
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "Traction: None");
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, "Odometer: N/A");
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "Lang   : Java");
        DriverStationLCD.getInstance().updateLCD();
    }

    public void autonomousInit()
    {
        turntableController.disable();  
        baseGyroController.enable();

        drivetrain.arcadeDrive(0, 0);
        pickup.set(0);
        dumperA.set(0);
        dumperB.set(0);

        autoTimer = 0;

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {



        if (autoMode == 1)
        {
            if (autoTimer < 0.02)
            {
                baseGyroController.setSetpoint(50);
                drivetrain.arcadeDrive(1, 0);
            }
            else if (autoTimer >= 0.02 && autoTimer <= 1.5)
            {

            }
            else if (autoTimer >= 1.5 && autoTimer <= 10.5)
            {
                drivetrain.arcadeDrive(0.5, 0, false);
            }
            else
            {
                baseGyroController.setSetpoint(0);
                drivetrain.arcadeDrive(0,0);
            }
        }

        autoTimer += 1/getLoopsPerSec();

        sendDashboardData();
    }

    public void disabledPeriodic() {

        if (steeringWheel.getRawButton(4) && prevButton4)
            autoMode++;
        if (steeringWheel.getRawButton(5) && prevButton5)
            autoMode++;

        prevButton4 = steeringWheel.getRawButton(4);
        prevButton5 = steeringWheel.getRawButton(5);

        if (autoMode < 0)
            autoMode = numAutoModes - 1;
        if (autoMode >= numAutoModes)
            autoMode = 0;

        if (autoMode == 0)
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Auto   : MoonRock");
        else if (autoMode == 1)
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1, "Auto   : GyroFuel");
        DriverStationLCD.getInstance().updateLCD();

        sendDashboardData();
    }

    public void teleopInit()
    {
        baseGyroController.disable();
        turntableController.enable();

        drivetrain.arcadeDrive(0, 0);
        pickup.set(0);
        dumperA.set(0);
        dumperB.set(0);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        drivetrain.arcadeDrive(driverJoystick, false);
        turntableController.setSetpoint(steeringWheel.getX()*135);

        if (operatorJoystick.getRawButton(4) || driverJoystick.getRawButton(4))
        {
            pickup.set(1.0);
        }
        else if (operatorJoystick.getRawButton(5) || driverJoystick.getRawButton(5))
        {
            pickup.set(-1.0);
        }
        else
        {
            pickup.set(0);
        }

        if (operatorJoystick.getRawButton(1) || driverJoystick.getRawButton(1))
        {
            dumperA.set(1.0);
            dumperB.set(1.0);
        }
        else if (operatorJoystick.getRawButton(3) || driverJoystick.getRawButton(3))
        {
            dumperA.set(-1.0);
            dumperB.set(-1.0);
        }
        else
        {
            dumperA.set(0);
            dumperB.set(0);
        }

        if (driverJoystick.getRawButton(2))
        {
            turntableEncoder.reset();
        }

        sendDashboardData();

    }

    private void buildPIDDashboard(Dashboard dashboard, PIDController pid)
    {
        double setpoint = 0;
        double output = 0;
        boolean stable = false;
        double p = 0;
        double i = 0;
        double d = 0;

        if (pid != null)
        {
            setpoint = pid.getSetpoint();
            output = pid.get();
            stable = pid.onTarget();
            p = pid.getP();
            i = pid.getI();
            d = pid.getD();
        }

        dashboard.addCluster();
        {
            dashboard.addDouble(setpoint);         //setpoint
            dashboard.addDouble(output);                 //output
            dashboard.addBoolean(false);                    //usePID
            dashboard.addDouble(10);                         //process variable
            dashboard.addDouble(11);                         //stable time
            dashboard.addDouble(12);                         //tolerance
            dashboard.addBoolean(stable);           //stable

            dashboard.addCluster();
            {
                dashboard.addCluster();
                {
                    dashboard.addDouble(p);        //P
                    dashboard.addDouble(i);        //I
                    dashboard.addDouble(d);        //D
                }
                dashboard.finalizeCluster();

                dashboard.addDouble(13);                   //Max Output
                dashboard.addDouble(14);                   //Auto Xfer Time
            }
            dashboard.finalizeCluster();
        }
        dashboard.finalizeCluster();
    }

    private void buildCameraDashboard(Dashboard dashboard)
    {

        dashboard.addCluster();
        {
            dashboard.addCluster();
            {
                dashboard.addInt(1);        //Left
                dashboard.addInt(2);        //Top
                dashboard.addInt(3);        //Right
                dashboard.addInt(4);        //Bottom
            }
            dashboard.finalizeCluster();

            dashboard.addDouble(5);         //Area

            dashboard.addArray();           //Measurements
            {
                dashboard.addDouble(6);
            }
            dashboard.finalizeArray();

            dashboard.addShort((short)0);
        }
        dashboard.finalizeCluster();

    }

       public void sendDashboardData() {
        Dashboard dash = DriverStation.getInstance().getDashboardPacker();
        dash.addCluster();
        {
            dash.addCluster();
            {     //analog modules
                dash.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        dash.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                dash.finalizeCluster();
                dash.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        dash.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                dash.finalizeCluster();
            }
            dash.finalizeCluster();

            dash.addCluster();
            { //digital modules
                dash.addCluster();
                {
                    dash.addCluster();
                    {
                        int module = 4;
                        dash.addByte(DigitalModule.getInstance(module).getRelayForward());
                        dash.addByte(DigitalModule.getInstance(module).getRelayForward());
                        dash.addShort(DigitalModule.getInstance(module).getAllDIO());
                        dash.addShort(DigitalModule.getInstance(module).getDIODirection());
                        dash.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                dash.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        dash.finalizeCluster();
                    }
                    dash.finalizeCluster();
                }
                dash.finalizeCluster();

                dash.addCluster();
                {
                    dash.addCluster();
                    {
                        int module = 6;
                        dash.addByte(DigitalModule.getInstance(module).getRelayForward());
                        dash.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        dash.addShort(DigitalModule.getInstance(module).getAllDIO());
                        dash.addShort(DigitalModule.getInstance(module).getDIODirection());
                        dash.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                dash.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        dash.finalizeCluster();
                    }
                    dash.finalizeCluster();
                }
                dash.finalizeCluster();

            }
            dash.finalizeCluster();

            dash.addByte(Solenoid.getAll());
        }
        dash.finalizeCluster();

        buildPIDDashboard(dash,null);
        buildPIDDashboard(dash,null);
        buildPIDDashboard(dash,turntableController);
        buildPIDDashboard(dash,null);
        buildPIDDashboard(dash,null);

        buildCameraDashboard(dash);

        dash.commit();
    }

    
}
