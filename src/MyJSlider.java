/**
 * This class represents the custom JSlider to set the tool tip
 * text to the current value of the slider.
 */

import javax.swing.*;

public class MyJSlider extends JSlider {

    // The JSlider object
    JSlider jSlider;

    /**
     * Constructor for MyJSlider class tht initializes the JSlider
     * object with the minimum and maximum speeds, while setting the
     * tool tip text to the current value of the slider
     */
    public MyJSlider(int minSpeed, int maxSpeed) {
        super();
        jSlider = new JSlider(JSlider.HORIZONTAL, minSpeed, maxSpeed, (maxSpeed + minSpeed) / 2);
        this.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            slider.setToolTipText(String.valueOf(slider.getValue()));
        });
    }
}