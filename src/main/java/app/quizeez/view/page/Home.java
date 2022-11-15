package app.quizeez.view.page;

import app.quizeez.component.slideshow.SlideShow;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class Home extends JPanel {

    private MigLayout layout;
    private SlideShow slide;
    private Card card1, card2, card3;

    public Home() {
        setOpaque(false);
        setLayout(null);
        init();
    }

    private void init() {
        layout = new MigLayout("fillx, ins 0, debug",
                "0[fill][][]0", "0[fill][]0");
        setLayout(layout);

        slide = new SlideShow();
        card1 = new Card();
        card1 = new Card();
        card2 = new Card();
        card3 = new Card();

        slide.initSlideshow(new JLabel("aaaa"), 
                new JLabel("aaaa"), new JLabel("aaaa"), 
                new JLabel("aaaa"), new JLabel("aaaa"), 
                new JLabel("aaaa"));
        
        add(slide, "spanX 3, wrap, w 100%, h 30%");
        add(new JLabel("Archive"), "spanX 3, wrap, w 100%, h 50!");
        add(card1, "w 100%/3, h 60%");
        add(card2, "w 100%/3, h 60%");
        add(card3, "w 100%/3, h 60%");

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(createShape());
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private Shape createShape() {
        float width = getWidth();
        float height = getHeight();

        Area area = new Area();
        area.add(new Area(new RoundRectangle2D.Float(0f, 0f, width, height, 15f, 15f)));
        area.add(new Area(new Rectangle2D.Float(0, 0, 15, 15)));
        area.add(new Area(new Rectangle2D.Float(0, height - 15, 15, 15)));
        area.add(new Area(new Rectangle2D.Float(width - 15, 0, 15, 15)));

        return area;
    }

    class Card extends JPanel {

    }
}
