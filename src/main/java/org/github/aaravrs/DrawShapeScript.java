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

    // https://en.wikipedia.org/wiki/Fractal#Simulated_fractals
    // https://en.wikipedia.org/wiki/Fractal-generating_software
    public void drawFractal_Paint() {

    }



    public void drawParabola_Paint() {
        int x = 0;
        int y = 0;

        int leftBound = -100;
        int rightBount = 100;

        runMSPaint();

        robot.delay(1500);

        robot.mouseMove(xSet + leftBound,ySet - getParabolaOutput(leftBound));
        leftClick();

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        for(int i = leftBound; i <= rightBount; i++) {
            x = i;
            y = getParabolaOutput(x);

            robot.mouseMove(x + xSet, (-1) * y + ySet); // (-1) to account for y being a flipped axis
            robot.delay(1);
        }
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    // TODO: add custom parameter functionality
    // Helper method for getting the output of the parabola at a given x
    private int getParabolaOutput(int x) {
        return (int) Math.round( 2 * Math.pow(x, 2) / 100);
    }

    public void drawSine_Paint() {
        int x = 0;
        int y = 0;
        double alpha = 0;

        runMSPaint();

        robot.delay(1500);

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

        robot.delay(1500);

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

    public void drawHeart_Paint(int scale) {
        int x = 0;
        int y = 0;

        runMSPaint();

        robot.delay(1500);

        robot.mouseMove(xSet, ySet + (-1) * getY_Heart(0, scale));
        leftClick();

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        for(int i = 0; i <= 360; i++) {

            double t = i * Math.PI / 180.0;
            x = (int) Math.round(scale * (16 * Math.pow(Math.sin(t), 3)));
            y = getY_Heart(t, scale);

            robot.mouseMove(x + xSet, (-1) * y + ySet);
            robot.delay(10);
        }
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private int getY_Heart(double t, int scale) {
        return (int) Math.round(scale * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
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


    /*
    This counts as a circle on neal.fun because we draw 4 points equidistant from the center very quickly
     */
    public void drawRectangle_Game(int width, int height, int delay) {
        Rectangle rectangle = new Rectangle(width, height);
        // xSet & ySet are the center of the rectangle
        int topLeft_X = xSet - (width / 2);
        int topLeft_Y = ySet - (height / 2);
        
        robot.delay(3000);

        robot.mouseMove(topLeft_X, topLeft_Y); // top left corner
        leftClick();
        robot.delay(delay);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseMove(topLeft_X + rectangle.width, topLeft_Y); // top right
        robot.delay(delay);
        robot.mouseMove(topLeft_X + rectangle.width,  topLeft_Y + rectangle.height); // bottom right
        robot.delay(delay);
        robot.mouseMove(topLeft_X, topLeft_Y + rectangle.height); // bottom left
        robot.delay(delay);
        robot.mouseMove(topLeft_X, topLeft_Y); // top left corner
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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


    /*
     TODO: draw a 3d coordinate plane to support drawing 3d function
            Text renderer
            Fractals
            3d functions (hard)
            patterns (tiling)
            Moving / simulating a moving ball?
            Other stuff in polar
     */


    // public void textRenderer_Paint(String text) {
    //
    // }

}
