package app.quizeez.material.progress;

import javax.swing.JProgressBar;

public class Progress extends JProgressBar {

    private static final long serialVersionUID = 1L;
	private final ProgressCircleUI ui;

    public Progress() {
        setOpaque(false);
        setStringPainted(true);
        ui = new ProgressCircleUI();
        setUI(ui);
    }

    @Override
    public String getString() {
        return ((int) (getValue() * ui.getAnimate())) + "%";
    }

    public void start() {
        ui.start();
    }
}
