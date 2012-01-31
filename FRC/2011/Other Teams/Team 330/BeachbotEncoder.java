/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj;

/**
 * Class to read quaderature encoders.
 * Quadrature encoders are devices that count shaft rotation and can sense direction. The output of
 * the QuadEncoder class is an integer that can count either up or down, and can go negative for
 * reverse direction counting. When creating QuadEncoders, a direction is supplied that changes the
 * sense of the output to make code more readable if the encoder is mounted such that forward movement
 * generates negative values. Quadrature encoders have two digital outputs, an A Channel and a B Channel
 * that are out of phase with each other to allow the FPGA to do direction sensing.
 */

/**
 * Class to read quaderature encoders.
 * Quadrature encoders are devices that count shaft rotation and can sense direction. The output of
 * the QuadEncoder class is an integer that can count either up or down, and can go negative for
 * reverse direction counting. When creating QuadEncoders, a direction is supplied that changes the
 * sense of the output to make code more readable if the encoder is mounted such that forward movement
 * generates negative values. Quadrature encoders have two digital outputs, an A Channel and a B Channel
 * that are out of phase with each other to allow the FPGA to do direction sensing.
 * The BeachbotEncoder class extends the Encoder Class to add the pidWrite method so this can be used
 * as a PIDSource.
 * @author Joe Ross
 */
public class BeachbotEncoder extends Encoder implements PIDSource {

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b modules and channels fully specified.
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     */
    public BeachbotEncoder(final int aSlot, final int aChannel,
            final int bSlot, final int bChannel,
            boolean reverseDirection) {
        super(aSlot, aChannel, bSlot, bChannel, reverseDirection);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b modules and channels fully specified.
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     */
    public BeachbotEncoder(final int aSlot, final int aChannel,
            final int bSlot, final int bChannel) {
        this(aSlot, aChannel, bSlot, bChannel, false);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b modules and channels fully specified.
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X decoding. If 4X is
     * selected, then an encoder FPGA object is used and the returned counts will be 4x the encoder
     * spec'd value since all rising and falling edges are counted. If 1X or 2X are selected then
     * a counter object will be used and the returned value will either exactly match the spec'd count
     * or be double (2x) the spec'd count.
     */
    public BeachbotEncoder(final int aSlot, final int aChannel,
            final int bSlot, final int bChannel,
            boolean reverseDirection, final EncodingType encodingType) {
            super(aSlot, aChannel, bSlot, bChannel, reverseDirection, encodingType);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b modules and channels fully specified.
     * Using the index pulse forces 4x encoding.
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param indexSlot The index channel digital input module.
     * @param indexChannel The index channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     */
    public BeachbotEncoder(final int aSlot, final int aChannel,
            final int bSlot, final int bChannel, final int indexSlot,
            final int indexChannel,
            boolean reverseDirection) {
            super(aSlot, aChannel, bSlot, bChannel, indexSlot, indexChannel, reverseDirection);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b modules and channels fully specified.
     * Using the index pulse forces 4x encoding.
     * @param aSlot The a channel digital input module.
     * @param aChannel The a channel digital input channel.
     * @param bSlot The b channel digital input module.
     * @param bChannel The b channel digital input channel.
     * @param indexSlot The index channel digital input module.
     * @param indexChannel The index channel digital input channel.
     */
    public BeachbotEncoder(final int aSlot, final int aChannel,
            final int bSlot, final int bChannel, final int indexSlot,
            final int indexChannel) {
        this(aSlot, aChannel, bSlot, bChannel, indexSlot, indexChannel, false);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels assuming the default module.
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     */
    public BeachbotEncoder(final int aChannel, final int bChannel, boolean reverseDirection) {
        super(aChannel, bChannel, reverseDirection);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels assuming the default module.
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     */
    public BeachbotEncoder(final int aChannel, final int bChannel) {
        this(aChannel, bChannel, false);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels assuming the default module.
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X decoding. If 4X is
     * selected, then an encoder FPGA object is used and the returned counts will be 4x the encoder
     * spec'd value since all rising and falling edges are counted. If 1X or 2X are selected then
     * a counter object will be used and the returned value will either exactly match the spec'd count
     * or be double (2x) the spec'd count.
     */
    public BeachbotEncoder(final int aChannel, final int bChannel, boolean reverseDirection, final EncodingType encodingType) {
        super(aChannel, bChannel, reverseDirection, encodingType);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels assuming the default module.
     * Using an index pulse forces 4x encoding
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param indexChannel The index channel digital input channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     */
    public BeachbotEncoder(final int aChannel, final int bChannel, final int indexChannel, boolean reverseDirection) {
        super (aChannel, bChannel, indexChannel, reverseDirection);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels assuming the default module.
     * Using an index pulse forces 4x encoding
     * @param aChannel The a channel digital input channel.
     * @param bChannel The b channel digital input channel.
     * @param indexChannel The index channel digital input channel.
     */
    public BeachbotEncoder(final int aChannel, final int bChannel, final int indexChannel) {
        this(aChannel, bChannel, indexChannel, false);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels as digital inputs. This is used in the case
     * where the digital inputs are shared. The Encoder class will not allocate the digital inputs
     * and assume that they already are counted.
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     */
    public BeachbotEncoder(DigitalSource aSource, DigitalSource bSource, boolean reverseDirection) {
        super(aSource, bSource, reverseDirection);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels as digital inputs. This is used in the case
     * where the digital inputs are shared. The Encoder class will not allocate the digital inputs
     * and assume that they already are counted.
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     */
    public BeachbotEncoder(DigitalSource aSource, DigitalSource bSource) {
        this(aSource, bSource, false);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels as digital inputs. This is used in the case
     * where the digital inputs are shared. The Encoder class will not allocate the digital inputs
     * and assume that they already are counted.
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     * @param encodingType either k1X, k2X, or k4X to indicate 1X, 2X or 4X decoding. If 4X is
     * selected, then an encoder FPGA object is used and the returned counts will be 4x the encoder
     * spec'd value since all rising and falling edges are counted. If 1X or 2X are selected then
     * a counter object will be used and the returned value will either exactly match the spec'd count
     * or be double (2x) the spec'd count.
     */
    public BeachbotEncoder(DigitalSource aSource, DigitalSource bSource, boolean reverseDirection, final EncodingType encodingType) {
        super(aSource, bSource, reverseDirection, encodingType);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels as digital inputs. This is used in the case
     * where the digital inputs are shared. The Encoder class will not allocate the digital inputs
     * and assume that they already are counted.
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param indexSource the source that should be used for the index channel.
     * @param reverseDirection represents the orientation of the encoder and inverts the output values
     * if necessary so forward represents positive values.
     */
    public BeachbotEncoder(DigitalSource aSource, DigitalSource bSource,
            DigitalSource indexSource, boolean reverseDirection) {
            super(aSource, bSource, indexSource, reverseDirection);
    }

    /**
     * Encoder constructor.
     * Construct a Encoder given a and b channels as digital inputs. This is used in the case
     * where the digital inputs are shared. The Encoder class will not allocate the digital inputs
     * and assume that they already are counted.
     * @param aSource The source that should be used for the a channel.
     * @param bSource the source that should be used for the b channel.
     * @param indexSource the source that should be used for the index channel.
     */
    public BeachbotEncoder(DigitalSource aSource, DigitalSource bSource,
            DigitalSource indexSource) {
        this(aSource, bSource, indexSource, false);
    }


    public double pidGet()
    {
        return getDistance();
    }
}
