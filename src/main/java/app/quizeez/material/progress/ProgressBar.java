package app.quizeez.material.progress;

import com.formdev.flatlaf.extras.components.FlatProgressBar;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressBar extends FlatProgressBar {
    
    public ProgressBar() {
        setPreferredSize(new Dimension(100, 5));
        setForeground(getForeground());
        setUI(new BasicProgressBarUI() {
            @Override
            protected void paintString(Graphics grphcs, int i, int i1, int i2, int i3, int i4, Insets insets) {
                grphcs.setColor(getForeground());
                super.paintString(grphcs, i, i1, i2, i3, i4, insets);
            }
        });
    }
}
