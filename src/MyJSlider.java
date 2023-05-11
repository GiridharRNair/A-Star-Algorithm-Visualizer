import javax.swing.*;

public class MyJSlider extends JSlider {

    static final int MIN_SPEED = 1;
    static final int MAX_SPEED = 100;
    static final int DEFAULT_SPEED = 50;

    JSlider jSlider;

    public MyJSlider() {
        super();
        jSlider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        this.addChangeListener(e -> {
            JSlider slider = (JSlider) e.getSource();
            slider.setToolTipText(String.valueOf(slider.getValue()));
        });
    }
}
