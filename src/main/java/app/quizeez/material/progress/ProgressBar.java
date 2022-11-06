package app.quizeez.material.progress;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

@SuppressWarnings("serial")
public class ProgressBar extends JProgressBar {
    
    private Color colorString = Color.decode("#00dbde");
    
    public ProgressBar() {
        setPreferredSize(new Dimension(100, 5));
        setBackground(new Color(77, 77, 77));
        setForeground(colorString);
        setUI(new BasicProgressBarUI() {
            @Override
            protected void paintString(Graphics grphcs, int i, int i1, int i2, int i3, int i4, Insets insets) {
                grphcs.setColor(getColorString());
                super.paintString(grphcs, i, i1, i2, i3, i4, insets);
            }
        });
    }
    
    public Color getColorString() {
        return colorString;
    }
    
    public void setColorString(Color colorString) {
        this.colorString = colorString;
    }
}
