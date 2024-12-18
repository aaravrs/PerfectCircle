package org.github.aaravrs;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

public class DrawShapeScript {

    private Robot robot;
    private final Runtime runtime;

    private final int xSet = (int) Math.round(960 / 1.25);
    private final int ySet = (int) Math.round(620 / 1.25);

    public DrawShapeScript() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        runtime = Runtime.getRuntime();
    }

    public void drawSine_Paint() {
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

    public void drawCircle_Paint() {
        int x = 0;
        int y = 0;

        int r = 300;

        runMSPaint();

        robot.delay(1200);

        robot.mouseMove(xSet + r, ySet);
        leftClick();

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        for(int i = 0; i < 375; i++) { // i = angle in degrees // max = 375 to account for inaccuracies near the end due to rounding

            // Polar coordinates to determine x and y
            double theta = i * Math.PI / 180.0; // angle from positive x-axis radians
            x = (int) Math.round(r * Math.cos(theta));
            y = (int) Math.round(r * Math.sin(theta));

            robot.mouseMove(x + xSet, (-1) * y + ySet);
            // Circle draws clockwise due to y being a flipped axis (top of screen is y = 0, bottom is max y)
            // So we use (-y) to account for this to go counter-clockwise

            robot.delay(2);
        }
        // robot.mouseMove(xSet, ySet);
        // robot.delay(1);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }


    public void drawCircle_Game(int radius, int delay_ms) {
        int x = 0;
        int y = 0;

        robot.delay(3000);

        robot.mouseMove(xSet + radius, ySet);
        leftClick();

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        for(int i = 0; i < 360; i++) {

            double theta = i * Math.PI / 180.0;
            x = (int) Math.round( radius * Math.cos(theta) );
            y = (int) Math.round( radius * Math.sin(theta) );

            robot.mouseMove(x + xSet, (-1) * y + ySet);
            robot.delay(delay_ms);
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
