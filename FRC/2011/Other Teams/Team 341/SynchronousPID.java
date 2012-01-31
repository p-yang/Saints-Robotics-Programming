package edu.missdaisy;

import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * Class implements a PID Control Loop.
 *
 * Does all computation synchronously (i.e. the calculate() function must be
 * called by the user from his own thread)
 */
public class SynchronousPID
{
    private double m_P;			// factor for "proportional" control
    private double m_I;			// factor for "integral" control
    private double m_D;			// factor for "derivative" control
    private double m_maximumOutput = 1.0;	// |maximum output|
    private double m_minimumOutput = -1.0;	// |minimum output|
    private double m_maximumInput = 0.0;		// maximum input - limit setpoint to this
    private double m_minimumInput = 0.0;		// minimum input - limit setpoint to this
    private boolean m_continuous = false;	// do the endpoints wrap around? eg. Absolute encoder
    private boolean m_enabled = false; 			//is the pid controller enabled
    private double m_prevError = 0.0;	// the prior sensor input (used to compute velocity)
    private double m_totalError = 0.0; //the sum of the errors for use in the integral calc
    private double m_setpoint = 0.0;
    private double m_error = 0.0;
    private double m_result = 0.0;
    private double m_last_input = 0.0;

    /**
     * Allocate a PID object with the given constants for P, I, D
     * @param Kp the proportional coefficient
     * @param Ki the integral coefficient
     * @param Kd the derivative coefficient
     */
    public SynchronousPID(double Kp, double Ki, double Kd)
    {
        m_P = Kp;
        m_I = Ki;
        m_D = Kd;
    }

    /**
     * Read the input, calculate the output accordingly, and write to the output.
     * This should only be called by the Notifier indirectly through CallCalculate
     * and is created during initialization.
     * @param input the input
     * @param the current time (for making sure that everything is scaled appropriately)
     */
    public synchronized double calculate(double input, double time)
    {
        m_last_input = input;
        if (m_enabled)
        {
            m_error = m_setpoint - input;
            if (m_continuous)
            {
                if (Math.abs(m_error) >
                        (m_maximumInput - m_minimumInput) / 2)
                {
                    if (m_error > 0)
                    {
                        m_error = m_error - m_maximumInput + m_minimumInput;
                    } else
                    {
                        m_error = m_error +
                                m_maximumInput - m_minimumInput;
                    }
                }
            }

            if (((m_totalError + m_error) * m_I < m_maximumOutput) &&
                    ((m_totalError + m_error) * m_I > m_minimumOutput))
            {
                m_totalError += m_error;
            }

            m_result = (m_P * m_error + m_I * m_totalError + m_D * (m_error - m_prevError));
            m_prevError = m_error;

            if (m_result > m_maximumOutput)
            {
                m_result = m_maximumOutput;
            } else if (m_result < m_minimumOutput)
            {
                m_result = m_minimumOutput;
            }
            return m_result;
        }
        return 0;
    }

    /**
     * Set the PID Controller gain parameters.
     * Set the proportional, integral, and differential coefficients.
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     */
    public synchronized void setPID(double p, double i, double d)
    {
        m_P = p;
        m_I = i;
        m_D = d;
    }

    /**
     * Get the Proportional coefficient
     * @return proportional coefficient
     */
    public double getP()
    {
        return m_P;
    }

    /**
     * Get the Integral coefficient
     * @return integral coefficient
     */
    public double getI()
    {
        return m_I;
    }

    /**
     * Get the Differential coefficient
     * @return differential coefficient
     */
    public synchronized double getD()
    {
        return m_D;
    }

    /**
     * Return the current PID result
     * This is always centered on zero and constrained the the max and min outs
     * @return the latest calculated output
     */
    public synchronized double get()
    {
        return m_result;
    }

    /**
     *  Set the PID controller to consider the input to be continuous,
     *  Rather then using the max and min in as constraints, it considers them to
     *  be the same point and automatically calculates the shortest route to
     *  the setpoint.
     * @param continuous Set to true turns on continuous, false turns off continuous
     */
    public synchronized void setContinuous(boolean continuous)
    {
        m_continuous = continuous;
    }

    /**
     *  Set the PID controller to consider the input to be continuous,
     *  Rather then using the max and min in as constraints, it considers them to
     *  be the same point and automatically calculates the shortest route to
     *  the setpoint.
     */
    public synchronized void setContinuous()
    {
        this.setContinuous(true);
    }

    /**
     * Sets the maximum and minimum values expected from the input.
     *
     * @param minimumInput the minimum value expected from the input
     * @param maximumInput the maximum value expected from the output
     */
    public synchronized void setInputRange(double minimumInput, double maximumInput)
    {
        if (minimumInput > maximumInput)
        {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        m_minimumInput = minimumInput;
        m_maximumInput = maximumInput;
        setSetpoint(m_setpoint);
    }

    /**
     * Sets the minimum and maximum values to write.
     *
     * @param minimumOutput the minimum value to write to the output
     * @param maximumOutput the maximum value to write to the output
     */
    public synchronized void setOutputRange(double minimumOutput, double maximumOutput)
    {
        if (minimumOutput > maximumOutput)
        {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        m_minimumOutput = minimumOutput;
        m_maximumOutput = maximumOutput;
    }

    /**
     * Set the setpoint for the PIDController
     * @param setpoint the desired setpoint
     */
    public synchronized void setSetpoint(double setpoint)
    {
        if (m_maximumInput > m_minimumInput)
        {
            if (setpoint > m_maximumInput)
            {
                m_setpoint = m_maximumInput;
            } else if (setpoint < m_minimumInput)
            {
                m_setpoint = m_minimumInput;
            } else
            {
                m_setpoint = setpoint;
            }
        } else
        {
            m_setpoint = setpoint;
        }
    }

    /**
     * Returns the current setpoint of the PIDController
     * @return the current setpoint
     */
    public synchronized double getSetpoint()
    {
        return m_setpoint;
    }

    /**
     * Retruns the current difference of the input from the setpoint
     * @return the current error
     */
    public synchronized double getError()
    {
        return m_error;
    }

    /**
     * Return true if the error is within the tolerance
     * @return true if the error is less than the tolerance
     */
    public synchronized boolean onTarget(double tolerance)
    {
        if( Math.abs(m_last_input - m_setpoint ) < tolerance )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Begin running the PIDController
     */
    public synchronized void enable()
    {
        m_enabled = true;
    }

    /**
     * Stop running the PIDController, this sets the output to zero before stopping.

     */
    public synchronized void disable()
    {
        m_result = 0;
        m_enabled = false;
    }

    /**
     * Query if this task is enabled

     */
    public synchronized boolean isEnabled()
    {
        return m_enabled;
    }

    /**
     * Reset the previous error,, the integral term, and disable the controller.
     */
    public synchronized void reset()
    {
        disable();
        m_last_input = 0;
        m_prevError = 0;
        m_totalError = 0;
        m_result = 0;
        m_setpoint = 0;
    }
}
