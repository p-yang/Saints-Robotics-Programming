/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.trackerdemo;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TrackerDemo extends SimpleRobot {

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        Tracker tracker = Tracker.getInstance();
        AxisCamera.getInstance().writeCompression(0);
        AxisCamera.getInstance().writeBrightness(10);
        AxisCamera.getInstance().writeResolution(AxisCamera.ResolutionT.k160x120);
        Timer.delay(5.0);
        Timer timer = new Timer();

//        tracker.getTarget();

        System.out.println("Starting getImage test");
        timer.start();
        for (int i = 0; i < 100; i++) {
            try {
                AxisCamera.getInstance().freshImage();
                AxisCamera.getInstance().getImage().free();
                Timer.delay(.001);
            } catch (NIVisionException e) {
            } catch (AxisCameraException e) {
            }
        }
        System.out.println("Took " + timer.get() + " seconds for 100 images");
        timer.reset();

        System.out.println("Starting freshImage test");
        timer.start();
        for (int i = 0; i < 100; i++) {
            while (!AxisCamera.getInstance().freshImage()) {
                Timer.delay(.001);
            }
            try {
                AxisCamera.getInstance().getImage().free();
            } catch (AxisCameraException e) {
            } catch (NIVisionException e) {
            }
//            tracker.getTarget();
            Timer.delay(.001);
        }
        System.out.println("Took " + timer.get() + " seconds for 100 images");
        timer.reset();

        System.out.println("Starting threshold test");
        timer.start();
        for (int i = 0; i < 100; i++) {
            try {
                AxisCamera.getInstance().freshImage();
                ColorImage image = AxisCamera.getInstance().getImage();
                image.thresholdHSL(10, 20, 30, 40, 50, 60).free();
                image.thresholdHSL(50, 60, 30, 40, 10, 20).free();
                image.free();
                Timer.delay(.001);
            } catch (AxisCameraException e) {
            } catch (NIVisionException e) {
            }
        }
        System.out.println("Took " + timer.get() + " seconds for 100 images");
        timer.reset();

        System.out.println("Starting getTarget test");
        timer.start();
        for (int i = 0; i < 100; i++) {
            AxisCamera.getInstance().freshImage();
            try {
                tracker.getTarget();
            } catch (AxisCameraException e) {
            } catch (NIVisionException e) {
            }
            Timer.delay(.001);
        }
        System.out.println("Took " + timer.get() + " seconds for 100 images");
        timer.reset();

        while (true) {
            if (AxisCamera.getInstance().freshImage()) {
                System.out.println("Fresh image");
            }
            Target target = null;
            try {
                target = tracker.getTarget();
            } catch (AxisCameraException e) {
            } catch (NIVisionException e) {
            }
            if (target != null) {
                System.out.println(target + " in " + timer.get() + " seconds");
            } else {
                System.out.println("No target found for " + timer.get() + " seconds");
            }
            timer.reset();
            Timer.delay(.001);
        }

    }
}
