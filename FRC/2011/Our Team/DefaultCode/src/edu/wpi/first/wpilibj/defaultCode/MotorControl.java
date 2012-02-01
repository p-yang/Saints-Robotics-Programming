/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.defaultCode;

import java.lang.*;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author temp
 */
public class MotorControl extends RobotDrive implements PIDOutput
{
    Joystick joystick_ch1;

    CANJaguar frontleft;
    CANJaguar frontright;
    CANJaguar backleft;
    CANJaguar backright;

    public MotorControl(CANJaguar frontleft, CANJaguar frontright, CANJaguar backleft, CANJaguar backright)
    {
        super(frontleft, backleft, frontright, backright);

        joystick_ch1 = new Joystick(1);

        this.frontleft = frontleft;
        this.frontright = frontright;
        this.backleft = backleft;
        this.backright = backright;

        CANSetup(360, 0.20, 0.000, 0.00);

        setMaxOutput(200);
        setInvertedMotor(MotorType.kFrontLeft, true);
        setInvertedMotor(MotorType.kRearLeft, true);
    }

    public void CANSetup(int encoderCodesPerRev, double P, double I, double D)
    {
        try
        {
            frontleft.configEncoderCodesPerRev(encoderCodesPerRev);
            frontright.configEncoderCodesPerRev(encoderCodesPerRev);
            backleft.configEncoderCodesPerRev(encoderCodesPerRev);
            backright.configEncoderCodesPerRev(encoderCodesPerRev);

            frontleft.setPID(P, I, D);
            frontright.setPID(P, I, D);
            backleft.setPID(P, I, D);
            backright.setPID(P, I, D);

            frontleft.enableControl();
            frontright.enableControl();
            backleft.enableControl();
            backright.enableControl();
        }
        catch (Exception e)
        {

        }
    }

    public void setPID(double P, double I, double D)
    {
        try
        {
            frontleft.setPID(P, I, D);
            frontright.setPID(P, I, D);
            backleft.setPID(P, I, D);
            backright.setPID(P, I, D);
        }
        catch (Exception e)
        {

        }
    }

    public void setPID()
    {
        setPID(DriverStation.getInstance().getAnalogIn(1),DriverStation.getInstance().getAnalogIn(2),DriverStation.getInstance().getAnalogIn(3));
    }

    public void pidWrite(double gyroPIDoutput)
    {
        try
        {
            frontleft.setX(-deadZone(400*joystick_ch1.getRawAxis(2), 40) + deadZone(400*gyroPIDoutput, 40));
            frontright.setX(-deadZone(400*joystick_ch1.getRawAxis(2), 40) + deadZone(400*gyroPIDoutput, 40));
            backleft.setX(-deadZone(400*joystick_ch1.getRawAxis(2), 40) + deadZone(400*gyroPIDoutput, 40));
            backright.setX(-deadZone(400*joystick_ch1.getRawAxis(2), 40) + deadZone(400*gyroPIDoutput, 40));
        }
        catch (Exception e)
        {

        }
        //arcadeDrive(deadZone(joystick_ch1.getRawAxis(2), 0.1), deadZone(gyroPIDoutput, 0.1), false);
    }
    
    private double deadZone(double input, double deadzone)
    {
        if (Math.abs(input) < deadzone)
        {
            return 0;
        }
        else
        {
            return input;
        }
    }
}
