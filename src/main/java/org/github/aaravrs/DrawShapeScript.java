package org.github.aaravrs;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

public class DrawShapeScript {

    private Robot robot;
    private Runtime runtime;

    private final int xSet = 600;
    private final int ySet = 400;

    public DrawShapeScript() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        runtime = Runtime.getRuntime();
    }

    public void drawSine() {
        int x = 0;
        int y = 0;
        double alpha = 0;

        runMSPaint();

        robot.delay(1200);

        robot.mouseMove(xSet,ySet);

        // Activate microsoft paint window
        leftClick();

        // Press down onto canvas (hold left click)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        for(int i = 0; i < 360; i++) {
            robot.mouseMove(x + xSet, y + ySet); // Move to origin of "coordinate system"
            y = (int) Math.round(100 * Math.sin(alpha));
            x++;
            alpha += 2 * Math.PI / 360 * 1;
            robot.delay(2);
        }

        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }

    public void drawCircle() {
        int x = 0;
        int y = 0;

        int r = 30;
        double theta = 0; // angle from positive x-axis radians

        runMSPaint();

        robot.delay(1200);

        robot.mouseMove(xSet , ySet);
        leftClick();

        int xVal = x + xSet;
        int yVal = y + ySet;

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        for(int i = 0; i < 360; i++) { // i = angle in degrees
            robot.mouseMove(x + xSet, y + ySet);
            // Circle draws clockwise due to y being a flipped axis (top of screen is y = 0, bottom is max y)


            // Polar coordinates to determine x and y
            theta = i * Math.PI / 180.0; // angle in radians
            x = (int) Math.round(r * Math.cos(theta));
            y = (int) Math.round(r * Math.sin(theta));

            robot.delay(2);
        }

        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }


    public void drawSquare() {




    }


    private void leftClick() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void runMSPaint() {
        try {
            runtime.exec("mspaint.exe");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
