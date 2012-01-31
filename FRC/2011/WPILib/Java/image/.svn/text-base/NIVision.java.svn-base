/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.image;

import com.sun.cldc.jna.BlockingFunction;
import com.sun.cldc.jna.Function;
import com.sun.cldc.jna.NativeLibrary;
import com.sun.cldc.jna.Pointer;
import com.sun.cldc.jna.Structure;
import com.sun.cldc.jna.TaskExecutor;
import com.sun.cldc.jna.ptr.IntByReference;
import edu.wpi.first.wpilibj.util.BoundaryException;

/**
 * Class for interfacing with the NIVision libraries
 * @author dtjones
 */
class NIVision {

    static final TaskExecutor taskExecutor = new TaskExecutor("nivision task");

    private static final BlockingFunction imaqGetLastErrorFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqGetLastError");
    static { imaqGetLastErrorFn.setTaskExecutor(taskExecutor);  }

    protected static void assertCleanStatus (int code) throws NIVisionException {
        if (code == 0) {
            throw new NIVisionException(imaqGetLastErrorFn.call0());
        }
    }
    private static final BlockingFunction Priv_ReadJPEGString_CFn = NativeLibrary.getDefaultInstance().getBlockingFunction("Priv_ReadJPEGString_C");
    static { Priv_ReadJPEGString_CFn.setTaskExecutor(taskExecutor); }

    /**
     * Read a pointer to a byte array into the given image
     * @param image pointer to an imaq image object
     * @param p pointer to a byte array holding image data
     */
    public static void readJpegString(Pointer image, Pointer p) {
        Priv_ReadJPEGString_CFn.call3(image, p, p.getSize());
    }

    private static final BlockingFunction Priv_SetWriteFileAllowedFn = NativeLibrary.getDefaultInstance().getBlockingFunction("Priv_SetWriteFileAllowed");
    static { Priv_SetWriteFileAllowedFn.setTaskExecutor(taskExecutor); }

    /**
     * Set true to be able to create files on cRio
     * @param val true allows files to be created
     */
    public static void setWriteFileAllowed(boolean val) {
        Priv_SetWriteFileAllowedFn.call1(val ? 1 : 0);
    }

    /**
     * Enumeration representing the possible types of imaq images
     */
    public static class ImageType {

        public final int value;
        /** The image type is 8-bit unsigned integer grayscale. */
        public static final ImageType imaqImageU8 = new ImageType(0);
        /** The image type is 16-bit unsigned integer grayscale. */
        public static final ImageType imaqImageU16 = new ImageType(7);
        /** The image type is 16-bit signed integer grayscale. */
        public static final ImageType imaqImageI16 = new ImageType(1);
        /** The image type is 32-bit floating-point grayscale. */
        public static final ImageType imaqImageSGL = new ImageType(2);
        /** The image type is complex. */
        public static final ImageType imaqImageComplex = new ImageType(3);
        /** The image type is RGB color. */
        public static final ImageType imaqImageRGB = new ImageType(4);
        /** The image type is HSL color. */
        public static final ImageType imaqImageHSL = new ImageType(5);
        /** The image type is 64-bit unsigned RGB color. */
        public static final ImageType imaqImageRGBU64 = new ImageType(6);
        /** Reserved */
        public static final ImageType imaqImageTypeSizeGuard = new ImageType(0xFFFFFFFF);

        private ImageType(int value) {
            this.value = value;
        }
    }

    /**
     * Enumerations representing the possible color spaces to operate in
     */
    public static class ColorMode {

        public final int value;
        /** The function operates in the RGB (Red, Blue, Green) color space. */
        public static final ColorMode IMAQ_RGB = new ColorMode(0);
        /** The function operates in the HSL (Hue, Saturation, Luminance) color space. */
        public static final ColorMode IMAQ_HSL = new ColorMode(1);
        /** The function operates in the HSV (Hue, Saturation, Value) color space. */
        public static final ColorMode IMAQ_HSV = new ColorMode(2);
        /** The function operates in the HSI (Hue, Saturation, Intensity) color space. */
        public static final ColorMode IMAQ_HSI = new ColorMode(3);
        /** The function operates in the CIE L*a*b* color space. */
        public static final ColorMode IMAQ_CIE = new ColorMode(4);
        /** The function operates in the CIE XYZ color space. */
        public static final ColorMode IMAQ_CIEXYZ = new ColorMode(5);
        public static final ColorMode IMAQ_COLOR_MODE_SIZE_GUARD = new ColorMode(0xFFFFFFFF);

        private ColorMode(int value) {
            this.value = value;
        }
    }

    public static class MeasurementType {

        public final int value;
        /** X-coordinate of the point representing the average position of the total particle mass, assuming every point in the particle has a constant density. */
        public static final MeasurementType IMAQ_MT_CENTER_OF_MASS_X = new MeasurementType(0);
        /** Y-coordinate of the point representing the average position of the total particle mass, assuming every point in the particle has a constant density. */
        public static final MeasurementType IMAQ_MT_CENTER_OF_MASS_Y = new MeasurementType(1);
        /** X-coordinate of the highest, leftmost particle pixel. */
        public static final MeasurementType IMAQ_MT_FIRST_PIXEL_X = new MeasurementType(2);
        /** Y-coordinate of the highest, leftmost particle pixel. */
        public static final MeasurementType IMAQ_MT_FIRST_PIXEL_Y = new MeasurementType(3);
        /** X-coordinate of the leftmost particle point. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_LEFT = new MeasurementType(4);
        /** Y-coordinate of highest particle point. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_TOP = new MeasurementType(5);
        /** X-coordinate of the rightmost particle point. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_RIGHT = new MeasurementType(6);
        /** Y-coordinate of the lowest particle point. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_BOTTOM = new MeasurementType(7);
        /** X-coordinate of the start of the line segment connecting the two perimeter points that are the furthest apart. */
        public static final MeasurementType IMAQ_MT_MAX_FERET_DIAMETER_START_X = new MeasurementType(8);
        /** Y-coordinate of the start of the line segment connecting the two perimeter points that are the furthest apart. */
        public static final MeasurementType IMAQ_MT_MAX_FERET_DIAMETER_START_Y = new MeasurementType(9);
        /** X-coordinate of the end of the line segment connecting the two perimeter points that are the furthest apart. */
        public static final MeasurementType IMAQ_MT_MAX_FERET_DIAMETER_END_X = new MeasurementType(10);
        /** Y-coordinate of the end of the line segment connecting the two perimeter points that are the furthest apart. */
        public static final MeasurementType IMAQ_MT_MAX_FERET_DIAMETER_END_Y = new MeasurementType(11);
        /** X-coordinate of the leftmost pixel in the longest row of contiguous pixels in the particle. */
        public static final MeasurementType IMAQ_MT_MAX_HORIZ_SEGMENT_LENGTH_LEFT = new MeasurementType(12);
        /** X-coordinate of the rightmost pixel in the longest row of contiguous pixels in the particle. */
        public static final MeasurementType IMAQ_MT_MAX_HORIZ_SEGMENT_LENGTH_RIGHT = new MeasurementType(13);
        /** Y-coordinate of all of the pixels in the longest row of contiguous pixels in the particle. */
        public static final MeasurementType IMAQ_MT_MAX_HORIZ_SEGMENT_LENGTH_ROW = new MeasurementType(14);
        /** Distance between the x-coordinate of the leftmost particle point and the x-coordinate of the rightmost particle point. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_WIDTH = new MeasurementType(16);
        /** Distance between the y-coordinate of highest particle point and the y-coordinate of the lowest particle point. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_HEIGHT = new MeasurementType(17);
        /** Distance between opposite corners of the bounding rectangle. */
        public static final MeasurementType IMAQ_MT_BOUNDING_RECT_DIAGONAL = new MeasurementType(18);
        /** Length of the outer boundary of the particle. */
        public static final MeasurementType IMAQ_MT_PERIMETER = new MeasurementType(19);
        /** Perimeter of the smallest convex polygon containing all points in the particle. */
        public static final MeasurementType IMAQ_MT_CONVEX_HULL_PERIMETER = new MeasurementType(20);
        /** Sum of the perimeters of each hole in the particle. */
        public static final MeasurementType IMAQ_MT_HOLES_PERIMETER = new MeasurementType(21);
        /** Distance between the start and end of the line segment connecting the two perimeter points that are the furthest apart. */
        public static final MeasurementType IMAQ_MT_MAX_FERET_DIAMETER = new MeasurementType(22);
        /** Length of the major axis of the ellipse with the same perimeter and area as the particle. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_ELLIPSE_MAJOR_AXIS = new MeasurementType(23);
        /** Length of the minor axis of the ellipse with the same perimeter and area as the particle. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_ELLIPSE_MINOR_AXIS = new MeasurementType(24);
        /** Length of the minor axis of the ellipse with the same area as the particle, and Major Axis equal in length to the Max Feret Diameter. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_ELLIPSE_MINOR_AXIS_FERET = new MeasurementType(25);
        /** Longest side of the rectangle with the same perimeter and area as the particle. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE = new MeasurementType(26);
        /** Shortest side of the rectangle with the same perimeter and area as the particle. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE = new MeasurementType(27);
        /** Distance between opposite corners of the rectangle with the same perimeter and area as the particle. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_RECT_DIAGONAL = new MeasurementType(28);
        /** Shortest side of the rectangle with the same area as the particle, and longest side equal in length to the Max Feret Diameter. */
        public static final MeasurementType IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE_FERET = new MeasurementType(29);
        /** Average length of a horizontal segment in the particle. */
        public static final MeasurementType IMAQ_MT_AVERAGE_HORIZ_SEGMENT_LENGTH = new MeasurementType(30);
        /** Average length of a vertical segment in the particle. */
        public static final MeasurementType IMAQ_MT_AVERAGE_VERT_SEGMENT_LENGTH = new MeasurementType(31);
        /** The particle area divided by the particle perimeter. */
        public static final MeasurementType IMAQ_MT_HYDRAULIC_RADIUS = new MeasurementType(32);
        /** Diameter of a disk with the same area as the particle. */
        public static final MeasurementType IMAQ_MT_WADDEL_DISK_DIAMETER = new MeasurementType(33);
        /** Area of the particle. */
        public static final MeasurementType IMAQ_MT_AREA = new MeasurementType(35);
        /** Sum of the areas of each hole in the particle. */
        public static final MeasurementType IMAQ_MT_HOLES_AREA = new MeasurementType(36);
        /** Area of a particle that completely covers the image. */
        public static final MeasurementType IMAQ_MT_PARTICLE_AND_HOLES_AREA = new MeasurementType(37);
        /** Area of the smallest convex polygon containing all points in the particle. */
        public static final MeasurementType IMAQ_MT_CONVEX_HULL_AREA = new MeasurementType(38);
        /** Area of the image. */
        public static final MeasurementType IMAQ_MT_IMAGE_AREA = new MeasurementType(39);
        /** Number of holes in the particle. */
        public static final MeasurementType IMAQ_MT_NUMBER_OF_HOLES = new MeasurementType(41);
        /** Number of horizontal segments in the particle. */
        public static final MeasurementType IMAQ_MT_NUMBER_OF_HORIZ_SEGMENTS = new MeasurementType(42);
        /** Number of vertical segments in the particle. */
        public static final MeasurementType IMAQ_MT_NUMBER_OF_VERT_SEGMENTS = new MeasurementType(43);
        /** The angle of the line that passes through the particle Center of Mass about which the particle has the lowest moment of inertia. */
        public static final MeasurementType IMAQ_MT_ORIENTATION = new MeasurementType(45);
        /** The angle of the line segment connecting the two perimeter points that are the furthest apart. */
        public static final MeasurementType IMAQ_MT_MAX_FERET_DIAMETER_ORIENTATION = new MeasurementType(46);
        /** Percentage of the particle Area covering the Image Area. */
        public static final MeasurementType IMAQ_MT_AREA_BY_IMAGE_AREA = new MeasurementType(48);
        /** Percentage of the particle Area in relation to its Particle and Holes Area. */
        public static final MeasurementType IMAQ_MT_AREA_BY_PARTICLE_AND_HOLES_AREA = new MeasurementType(49);
        /** Equivalent Ellipse Major Axis divided by Equivalent Ellipse Minor Axis. */
        public static final MeasurementType IMAQ_MT_RATIO_OF_EQUIVALENT_ELLIPSE_AXES = new MeasurementType(50);
        /** Equivalent Rect Long Side divided by Equivalent Rect Short Side. */
        public static final MeasurementType IMAQ_MT_RATIO_OF_EQUIVALENT_RECT_SIDES = new MeasurementType(51);
        /** Max Feret Diameter divided by Equivalent Rect Short Side (Feret). */
        public static final MeasurementType IMAQ_MT_ELONGATION_FACTOR = new MeasurementType(53);
        /** Area divided by the product of Bounding Rect Width and Bounding Rect Height. */
        public static final MeasurementType IMAQ_MT_COMPACTNESS_FACTOR = new MeasurementType(54);
        /** Perimeter divided by the circumference of a circle with the same area. */
        public static final MeasurementType IMAQ_MT_HEYWOOD_CIRCULARITY_FACTOR = new MeasurementType(55);
        /** Factor relating area to moment of inertia. */
        public static final MeasurementType IMAQ_MT_TYPE_FACTOR = new MeasurementType(56);
        /** The sum of all x-coordinates in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_X = new MeasurementType(58);
        /** The sum of all y-coordinates in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_Y = new MeasurementType(59);
        /** The sum of all x-coordinates squared in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_XX = new MeasurementType(60);
        /** The sum of all x-coordinates times y-coordinates in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_XY = new MeasurementType(61);
        /** The sum of all y-coordinates squared in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_YY = new MeasurementType(62);
        /** The sum of all x-coordinates cubed in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_XXX = new MeasurementType(63);
        /** The sum of all x-coordinates squared times y-coordinates in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_XXY = new MeasurementType(64);
        /** The sum of all x-coordinates times y-coordinates squared in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_XYY = new MeasurementType(65);
        /** The sum of all y-coordinates cubed in the particle. */
        public static final MeasurementType IMAQ_MT_SUM_YYY = new MeasurementType(66);
        /** The moment of inertia in the x-direction twice. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_XX = new MeasurementType(68);
        /** The moment of inertia in the x and y directions. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_XY = new MeasurementType(69);
        /** The moment of inertia in the y-direction twice. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_YY = new MeasurementType(70);
        /** The moment of inertia in the x-direction three times. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_XXX = new MeasurementType(71);
        /** The moment of inertia in the x-direction twice and the y-direction once. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_XXY = new MeasurementType(72);
        /** The moment of inertia in the x-direction once and the y-direction twice. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_XYY = new MeasurementType(73);
        /** The moment of inertia in the y-direction three times. */
        public static final MeasurementType IMAQ_MT_MOMENT_OF_INERTIA_YYY = new MeasurementType(74);
        /** The normalized moment of inertia in the x-direction twice. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_XX = new MeasurementType(75);
        /** The normalized moment of inertia in the x- and y-directions. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_XY = new MeasurementType(76);
        /** The normalized moment of inertia in the y-direction twice. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_YY = new MeasurementType(77);
        /** The normalized moment of inertia in the x-direction three times. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_XXX = new MeasurementType(78);
        /** The normalized moment of inertia in the x-direction twice and the y-direction once. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_XXY = new MeasurementType(79);
        /** The normalized moment of inertia in the x-direction once and the y-direction twice. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_XYY = new MeasurementType(80);
        /** The normalized moment of inertia in the y-direction three times. */
        public static final MeasurementType IMAQ_MT_NORM_MOMENT_OF_INERTIA_YYY = new MeasurementType(81);
        /** The first Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_1 = new MeasurementType(82);
        /** The second Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_2 = new MeasurementType(83);
        /** The third Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_3 = new MeasurementType(84);
        /** The fourth Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_4 = new MeasurementType(85);
        /** The fifth Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_5 = new MeasurementType(86);
        /** The sixth Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_6 = new MeasurementType(87);
        /** The seventh Hu moment. */
        public static final MeasurementType IMAQ_MT_HU_MOMENT_7 = new MeasurementType(88);

        public static final MeasurementType IMAQ_MEASUREMENT_TYPE_SIZE_GUARD = new MeasurementType(0xFFFFFFFF);

        private MeasurementType(int value) {
            this.value = value;
        }
    }

    public static class Range extends Structure {

        int lower;
        int upper;

        public void read() {
            lower = backingNativeMemory.getInt(0);
            upper = backingNativeMemory.getInt(4);
        }

        public void write() {
            backingNativeMemory.setInt(0, lower);
            backingNativeMemory.setInt(4, upper);
        }

        public int size() {
            return 8;
        }

        /**
         * Free the memory used by this range
         */
        public void free() {
            release();
        }

        /**
         * Create a new range with the specified upper and lower boundaries
         * @param lower The lower limit
         * @param upper The upper limit
         */
        public Range(final int lower, final int upper) {
            allocateMemory();
            set(lower, upper);
        }

        /**
         * Set the upper and lower boundaries
         * @param lower The lower limit
         * @param upper The upper limit
         */
        public void set(final int lower, final int upper) {
            if (lower > upper) {
                throw new BoundaryException("Lower boundary is greater than upper");
            }
            this.lower = lower;
            this.upper = upper;
            write();
        }

        /**
         * Get the lower boundary
         * @return The lower boundary.
         */
        public int getLower() {
            read();
            return lower;
        }

        /**
         * Get the upper boundary
         * @return The upper boundary.
         */
        public int getUpper() {
            read();
            return upper;
        }
    }

    protected static final BlockingFunction imaqCreateImageFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqCreateImage");
    static { imaqCreateImageFn.setTaskExecutor(taskExecutor); }

    /**
     * Allocates space for and creates a new imaq image
     * @param type the imaq encoding to use for the image
     * @param borderSize teh size of border to use for the image
     * @return a newly allocated pointer to an imaqImage
     */
    public static Pointer imaqCreateImage(ImageType type, int borderSize) throws NIVisionException{
        int toReturn = imaqCreateImageFn.call2(type.value, borderSize);
        assertCleanStatus(toReturn);
        return new Pointer(toReturn, 0);
    }
    private static final Function imaqWriteFileFn = NativeLibrary.getDefaultInstance().getFunction("imaqWriteFile");
//    static { imaqWriteFileFn.setTaskExecutor(taskExecutor); }

    /**
     * Write an image to the given file.
     * 
     * Supported extensions:
     * .aipd or .apd AIPD
     * .bmp BMP
     * .jpg or .jpeg JPEG
     * .jp2 JPEG2000
     * .png PNG
     * .tif or .tiff TIFF
     *
     * @param image The image to write to a file.
     * @param fileName The name of the destination file.
     */
    public static void writeFile(Pointer image, String fileName) throws NIVisionException {
        Pointer p = new Pointer(fileName.length() + 1);
        p.setString(0, fileName);
        setWriteFileAllowed(true);
        try {
            assertCleanStatus(imaqWriteFileFn.call3(image, p, 0)); //zero is unused color table
        } finally {
            p.free();
        }
    }

    /**
     * Write an image to the given file.
     *
     * Supported extensions:
     * .aipd or .apd AIPD
     * .bmp BMP
     * .jpg or .jpeg JPEG
     * .jp2 JPEG2000
     * .png PNG
     * .tif or .tiff TIFF
     *
     * @param image The image to write to a file.
     * @param fileName The name of the destination file.
     * @param colorTable The color table to use for 8-bit images
     */
    public static void writeFile(Pointer image, String fileName, Pointer colorTable)  throws NIVisionException{
        Pointer p = new Pointer(fileName.length() + 1);
        p.setString(0, fileName);
        setWriteFileAllowed(true);
        try {
            assertCleanStatus(imaqWriteFileFn.call3(image, p, colorTable)); //zero is unused color table
        } finally {
            p.free();
        }
    }
    
    private static final BlockingFunction imaqReadFileFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqReadFile");
    static { imaqReadFileFn.setTaskExecutor(taskExecutor); }

    /**
     * Read an image from to the given image from the given filename
     * @param image The image to store the data in.
     * @param fileName The name of the file to load.
     */
    public static void readFile(Pointer image, String fileName)  throws NIVisionException{
        Pointer p = new Pointer(fileName.length() + 1);
        p.setString(0, fileName);
        try {
            assertCleanStatus(imaqReadFileFn.call4(image, p, 0, 0)); //zeros are unused color table and num colors
        } finally {
            p.free();
        }
    }

    private static final BlockingFunction imaqColorThresholdFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqColorThreshold");
    static { imaqColorThresholdFn.setTaskExecutor(taskExecutor); }

    /**
     * Convert the given image into a binary image true where the colors match the given thresholds
     * @param dest the 8 bit monocolor image to store the result in
     * @param source the color image source
     * @param mode The mode of color to use
     * @param plane1Range First color range
     * @param plane2Range Second color range
     * @param plane3Range Third color range
     */
    public static void colorThreshold(Pointer dest, Pointer source, ColorMode mode,
            Pointer plane1Range, Pointer plane2Range, Pointer plane3Range)  throws NIVisionException{
        int replaceValue = 1;
        assertCleanStatus(imaqColorThresholdFn.call7(dest.address().toUWord().toPrimitive(),
                source.address().toUWord().toPrimitive(),
                replaceValue, mode.value,
                plane1Range.address().toUWord().toPrimitive(),
                plane2Range.address().toUWord().toPrimitive(),
                plane3Range.address().toUWord().toPrimitive()));
    }
    
    private static final BlockingFunction imaqCountParticlesFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqCountParticles");
    static { imaqCountParticlesFn.setTaskExecutor(taskExecutor); }

    /**
     * Counts the number of particles in a binary image.
     * @param image The image to count the particles in
     * @return The number of particles
     */
    public static int countParticles(Pointer image)  throws NIVisionException{
        IntByReference i = new IntByReference(0);
        int val;
        try {
            assertCleanStatus(imaqCountParticlesFn.call3(image, 1, i.getPointer()));
        } finally {
            val = i.getValue();
            i.free();
        }
        return val;
    }

    private static final BlockingFunction imaqMeasureParticleFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqMeasureParticle");
    static { imaqMeasureParticleFn.setTaskExecutor(taskExecutor); }

    /**
     * Returns a measurement associated with a particle
     * @param image The image containing the particle to get information about.
     * @param particleNum The number of the particle to get information about.
     * @param calibrated Specifies whether to return the measurement as a real-world value.
     * @param type The measurement to make on the particle.
     * @return The value of the requested measurement.
     */
    public static double MeasureParticle (Pointer image, int particleNum, boolean calibrated, MeasurementType type)  throws NIVisionException{
        Pointer l = new Pointer(8);
        double val;
        try {
            assertCleanStatus(imaqMeasureParticleFn.call5(image, particleNum, calibrated?1:0, type.value, l));
        } finally {
            val = l.getDouble(0);
            l.free();
        }
        return val;
    }

    private static final BlockingFunction imaqDisposeFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqDispose");
    static { imaqDisposeFn.setTaskExecutor(taskExecutor); }

    /**
     * Cleans up resources associated with images, regions of interest (ROIs),
     * arrays, and reports that you no longer need.
     * After you dispose of something, you can no longer use it.
     * @param item The image, ROI, array, or report whose memory you want to free.
     */
    public static void dispose (Pointer item)  throws NIVisionException{
        assertCleanStatus(imaqDisposeFn.call1(item));
    }

    // TODO: Make non-blocking...
    private static final BlockingFunction imaqGetImageSizeFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqGetImageSize");
    static { imaqGetImageSizeFn.setTaskExecutor(taskExecutor); }

    /**
     * Get the height of an image.
     * @param image The image to get the height of.
     * @return The height of the image.
     */
    public static int getHeight(Pointer image)  throws NIVisionException{
        Pointer i = new Pointer(4);
        int val;
        try {
            assertCleanStatus(imaqGetImageSizeFn.call3(image, 0, i));
        } finally {
            val = i.getInt(0);
            i.free();
        }
        return val;
    }

    /**
     * Get the width of an image.
     * @param image The image to get the width of.
     * @return The width of the image.
     */
    public static int getWidth(Pointer image)  throws NIVisionException{
        Pointer i = new Pointer(4);
        int val;
        try {
            assertCleanStatus(imaqGetImageSizeFn.call3(image, i, 0));
        } finally {
            val = i.getInt(0);
            i.free();
        }
        return val;
    }

    private static final BlockingFunction imaqExtractColorPlanesFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqExtractColorPlanes");
    static { imaqExtractColorPlanesFn.setTaskExecutor(taskExecutor); }

    /**
     * Extract the color planes from the given source image into the given planes
     * @param source The color source image.
     * @param mode The color space to extract the planes of.
     * @param plane1 MonoImage destination for the first plane.
     * @param plane2 MonoImage destination for the first plane.
     * @param plane3 MonoImage destination for the first plane.
     */
    public static void extractColorPlanes(Pointer source, ColorMode mode, Pointer plane1, Pointer plane2, Pointer plane3)  throws NIVisionException{
        int plane_1 = 0;
        int plane_2 = 0;
        int plane_3 = 0;
        if (plane1 != null)
            plane_1 = plane1.address().toUWord().toPrimitive();
        if (plane2 != null)
            plane_2 = plane2.address().toUWord().toPrimitive();
        if (plane3 != null)
            plane_3 = plane3.address().toUWord().toPrimitive();
        assertCleanStatus(imaqExtractColorPlanesFn.call5(source, mode.value, plane_1, plane_2, plane_3));
    }

    private static final BlockingFunction imaqReplaceColorPlanesFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqReplaceColorPlanes");
    static { imaqReplaceColorPlanesFn.setTaskExecutor(taskExecutor); }

    /**
     * Replaces one or more of the color planes of a color image. The plane you
     * replace may be independent of the image type. For example, you can
     * replace the green plane of an RGB image or the hue plane of an HSL image.
     * @param dest The destination image.
     * @param source The source image.
     * @param mode The color space in which the function replaces planes.
     * @param plane1 The first plane of replacement data. Set this parameter to null if you do not want to change the first plane of the source image.
     * @param plane2 The second plane of replacement data. Set this parameter to null if you do not want to change the second plane of the source image.
     * @param plane3The third plane of replacement data. Set this parameter to null if you do not want to change the third plane of the source image.
     */
    public static void replaceColorPlanes(Pointer dest, Pointer source,
            ColorMode mode, Pointer plane1, Pointer plane2, Pointer plane3)  throws NIVisionException{
        int plane_1 = 0;
        int plane_2 = 0;
        int plane_3 = 0;
        if (plane1 != null)
            plane_1 = plane1.address().toUWord().toPrimitive();
        if (plane2 != null)
            plane_2 = plane2.address().toUWord().toPrimitive();
        if (plane3 != null)
            plane_3 = plane3.address().toUWord().toPrimitive();
        assertCleanStatus(imaqReplaceColorPlanesFn.call6(
                dest.address().toUWord().toPrimitive(),
                source.address().toUWord().toPrimitive(),
                mode.value,
                plane_1,
                plane_2,
                plane_3));
    }

    private static final BlockingFunction imaqColorEqualizeFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqColorEqualize");
    static { imaqColorEqualizeFn.setTaskExecutor(taskExecutor); }

    /**
     * Calculates the histogram of each plane of a color image and redistributes
     * pixel values across the desired range while maintaining pixel value
     * groupings.
     * @param destination The destination image.
     * @param source The image to equalize.
     * @param all Set this parameter to TRUE to equalize all three planes of the
     * image. Set this parameter to FALSE to equalize only the luminance plane.
     */
    public static void colorEqualize(Pointer destination, Pointer source, boolean all)  throws NIVisionException{
        assertCleanStatus(imaqColorEqualizeFn.call3(destination, source,all?1:0));
    }


    private static final BlockingFunction imaqDetectEllipsesFn =
            NativeLibrary.getDefaultInstance().getBlockingFunction("imaqDetectEllipses");
    static { imaqDetectEllipsesFn.setTaskExecutor(NIVision.taskExecutor); }
    private static Pointer numberOfEllipsesDetected = new Pointer(4);


    public static EllipseMatch[] detectEllipses(MonoImage image, EllipseDescriptor ellipseDescriptor,
            CurveOptions curveOptions, ShapeDetectionOptions shapeDetectionOptions,
            RegionOfInterest roi) throws NIVisionException {

        int curveOptionsPointer = 0;
        if (curveOptions != null)
            curveOptionsPointer = curveOptions.getPointer().address().toUWord().toPrimitive();
        int shapeDetectionOptionsPointer = 0;
        if (shapeDetectionOptions != null)
            shapeDetectionOptionsPointer = shapeDetectionOptions.getPointer().address().toUWord().toPrimitive();
        int roiPointer = 0;
        if (roi != null)
            roiPointer = roi.getPointer().address().toUWord().toPrimitive();

        int returnedAddress =
                imaqDetectEllipsesFn.call6(
                image.image.address().toUWord().toPrimitive(),
                ellipseDescriptor.getPointer().address().toUWord().toPrimitive(),
                curveOptionsPointer, shapeDetectionOptionsPointer,
                roiPointer,
                numberOfEllipsesDetected.address().toUWord().toPrimitive());

        try {
            NIVision.assertCleanStatus(returnedAddress);
        } catch (NIVisionException ex) {
            if (!ex.getMessage().equals("No error."))
                throw ex;
        }

        EllipseMatch[] matches = EllipseMatch.getMatchesFromMemory(returnedAddress, numberOfEllipsesDetected.getInt(0));
        NIVision.dispose(new Pointer(returnedAddress,0));
        return matches;
    }
}
