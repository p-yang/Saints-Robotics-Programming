package edu.missdaisy;

import java.util.Vector;

/**
 *
 * @author jrussell
 */
public class DigitalFilter
{
    private class CircularBuffer
    {
        private double[] data;
        private int front;

        public CircularBuffer(int size)
        {
            data = new double[size];
            front = 0;
        }

        public void increment()
        {
            front++;
            if( front >= data.length )
            {
                front = 0;
            }
        }

        public void reset()
        {
            for( int d = 0; d < data.length; d++ )
            {
                data[d] = 0;
            }
        }

        public int size()
        {
            return data.length;
        }

        public double elementAt(int position)
        {
            return data[ (position+front) % data.length ];
        }

        public void setElementAt(double value, int position)
        {
            data[ (position+front) % data.length ] = value;
        }
    }

    private CircularBuffer inputs;
    private CircularBuffer outputs;
    private Vector inputGains;
    private Vector outputGains;

    public DigitalFilter(Vector inputGains, Vector outputGains)
    {
        this.inputGains = inputGains;
        this.outputGains = outputGains;
        inputs = new CircularBuffer( inputGains.size() );
        outputs = new CircularBuffer( outputGains.size() );
    }

    public static DigitalFilter SinglePoleIIRFilter(double gain)
    {
        Vector inputGain = new Vector();
        Vector outputGain = new Vector();
        inputGain.addElement(new Double(gain));
        outputGain.addElement(new Double(1.0f - gain));

        return new DigitalFilter(inputGain, outputGain);
    }

    public static DigitalFilter MovingAverageFilter(int taps)
    {
        if( taps < 1 )
        {
            taps = 1;
        }

        Double gain = new Double(1.0/taps);
        Vector gains = new Vector();
        for( int i = 0; i < taps; i++ )
        {
            gains.addElement(gain);
        }

        return new DigitalFilter(gains, new Vector());
    }

    public void reset()
    {
        inputs.reset();
        outputs.reset();
    }

    public double calculate(double value)
    {
        double retVal = 0.0;

        // Rotate the inputs
        if( inputs.size() > 0 )
        {
            inputs.increment();
            inputs.setElementAt(value, 0);
        }

        // Calculate the new output
        for( int i = 0; i < inputs.size(); i++ )
        {
            retVal += inputs.elementAt(i) * ((Double)inputGains.elementAt(i)).doubleValue();
        }
        for( int i = 0; i < outputs.size(); i++ )
        {
            retVal += outputs.elementAt(i) * ((Double)outputGains.elementAt(i)).doubleValue();
        }

        // Rotate the outputs
        if( outputs.size() > 0 )
        {
            outputs.increment();
            outputs.setElementAt(retVal, 0);
        }

        return retVal;
    }

}
