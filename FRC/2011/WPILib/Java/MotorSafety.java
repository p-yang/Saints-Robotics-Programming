/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj;

/**
 *
 * @author brad
 */
public interface MotorSafety {
    public static final double DEFAULT_SAFETY_EXPIRATION = 0.1;
    void setExpiration(double timeout);
    double getExpiration();
    boolean isAlive();
    void stopMotor();
    void setSafetyEnabled(boolean enabled);
    boolean isSafetyEnabled();
}
