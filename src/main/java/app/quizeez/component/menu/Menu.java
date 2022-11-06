package app.quizeez.component.menu;

import app.quizeez.system.Colors;
import app.quizeez.system.SVGIcon;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class Menu extends JPanel {

    private final List<MenuEvent> events = new ArrayList<>();
    private int targetLocation;
    private TimingTarget target;
    private int selectedIndex = 1;
    private int selectedLocation = 100;
    private Animator animator;

    private MigLayout layout;
    private HomeButton homeBtn;
    private JPanel menu_panel;
    private ProfileBottom profile;
    private JButton cmdShowing;

    public Menu() {
        initComponents();
        setOpaque(false);
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        setLayout(new MigLayout("wrap, fillx, insets 0",
                "[fill]", "0[]0"));
        layout = new MigLayout("fillx, wrap",
                "0[fill]0", "0[fill]0");

        menu_panel = new JPanel();
        menu_panel.setOpaque(false);
        menu_panel.setLayout(layout);

        createShowingButton();
        createHomeButton();

        initMenu();
        space();
        createProfile();
        setAnimation();
    }

    private void createShowingButton() {
        SVGIcon.Showing_Menu.setColorFilter(
                SVGIcon.setColor(Colors.FG_MENU_SHOWING));
        cmdShowing = new JButton(SVGIcon.Showing_Menu);
        cmdShowing.setContentAreaFilled(false);
        cmdShowing.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdShowing.setBorder(new EmptyBorder(25, 15, 15, 30));
        cmdShowing.setHorizontalAlignment(JButton.CENTER);
        add(cmdShowing, "pos 1al 0al 80 60");
    }

    public void setAnimation() {
        animator = new Animator(300);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void end() {
                runEvent();
            }

        });
        animator.setDeceleration(.5f);
        animator.setAcceleration(.5f);
        animator.setResolution(0);
    }

    private void initMenu() {
        split("Home");
        addMenuItem("Dashboard", SVGIcon.Home_Item_Menu, 1);
        addMenuItem("Trends", SVGIcon.Trends_Item_Menu, 2);
        addMenuItem("Newsfeed", SVGIcon.NewsFeed_Item_Menu, 3);
        split("Test");
        addMenuItem("New & Notes", SVGIcon.NewAndNote_Item_Menu, 5);
        addMenuItem("Calendar", SVGIcon.Calendar_Item_Menu, 6);
        addMenuItem("Event", SVGIcon.Event_Item_Menu, 7);
        split("Playing");
        addMenuItem("Favorite", SVGIcon.Favorite_Item_Menu, 9);
        this.add(menu_panel);
    }

    private void addMenuItem(String itemName, FlatSVGIcon icon, int index) {
        icon.setColorFilter(SVGIcon.setColor(Colors.FG_MENU_ITEM));

        ButtonMenu item = new ButtonMenu();
        item.setText(itemName);
        item.setForeground(Colors.FG_MENU_ITEM);
        item.setIcon(icon);
        item.setIndex(index);
        item.setHorizontalAlignment(JButton.LEADING);
        item.setHorizontalTextPosition(JButton.RIGHT);
        item.setIconTextGap(32);
        item.setFont(item.getFont().deriveFont(Font.PLAIN, 14));
        item.setToolTipText(itemName);

        item.addActionListener((ActionEvent ae) -> {
            if (index != selectedIndex) {
                if (animator.isRunning()) {
                    animator.stop();
                }
                int positionItem = item.getY() + menu_panel.getY();
                targetLocation = positionItem;
                selectedIndex = index;
                target = new PropertySetter(Menu.this,
                        "selectedLocation",
                        selectedLocation, targetLocation);
                animator.addTarget(target);
                animator.start();
            }
        });

        menu_panel.add(item, "w 150!");
    }

    private void space() {
        this.add(new JLabel(), "push");
    }

    private void split(String name) {
        JLabel spliter = new JLabel() {
            @Override
            public void updateUI() {
                super.updateUI();
                this.setForeground(Colors.FG_MENU_SPLITER);
            }
        };

        spliter.setOpaque(false);
        spliter.setText(name);
        spliter.setHorizontalAlignment(JLabel.CENTER);

        menu_panel.add(spliter, "w 80!, h 30!");
    }

    private void runEvent() {
        for (MenuEvent event : events) {
            event.selectedItem(selectedIndex);
        }
    }

    public void setShowingAction(ActionListener action) {
        cmdShowing.addActionListener(action);
    }

    public int getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(int selectedLocation) {
        this.selectedLocation = selectedLocation;
        repaint();
    }

    public void addEventMenu(MenuEvent event) {
        this.events.add(event);
    }

    public void setAlpha(float alpha) {
        homeBtn.alpha = alpha;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int y = selectedLocation;
        g2.setColor(UIManager.getColor("Button.default.focusedBorderColor"));
        g2.fill(createShape(y));
    }

    private Shape createShape(int y) {
        int width = getWidth();
        int r = 15;
        Area area = new Area(new RoundRectangle2D.Float(15, y, width - 35,
                40, r, r));
        area.add(new Area(new RoundRectangle2D.Float(width - 3, y, 3, 40,
                3, 3)));
        return area;
    }

    private void createHomeButton() {
        SVGIcon.LOGO.setColorFilter(SVGIcon.setColor(Colors.BG_LOGO));
        homeBtn = new HomeButton(SVGIcon.LOGO);
        this.add(homeBtn);
    }

    private void createProfile() {
        FlatSVGIcon userAvatar = new FlatSVGIcon("svg/setting-profile_bottom.svg",
                50, 50);
        profile = new ProfileBottom(userAvatar, "To Hoang Tuan");
        this.add(profile, "h 60!");
    }

    private class HomeButton extends JButton {

        private final String[] values = new String[]{"Bee", "Quiz"};
        private float alpha = 1.0f;

        public HomeButton(ImageIcon icon) {
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(15, 15, 15, 5));
            setIcon(icon);
            setToolTipText("Home");
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setHorizontalAlignment(JButton.LEFT);
            setHorizontalTextPosition(JButton.LEFT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setFont(homeBtn.getFont().deriveFont(Font.BOLD, 16));
            g2.setColor(Colors.BG_LOGO_NAME);
            g2.drawString(values[0], 78, getHeight() / 2);

            g2.setFont(homeBtn.getFont().deriveFont(Font.BOLD, 16));
            g2.setColor(UIManager.getColor("Button.focusedBorderColor"));
            g2.drawString(values[1], 78, getHeight() / 2 + 15);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paintComponent(g);
        }

        @Override
        public void paint(Graphics grphcs) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            super.paint(grphcs);
        }

    }

    private class ProfileBottom extends JPanel {

        private ImageIcon img;
        private String nameUser;

        public ProfileBottom(ImageIcon img, String nameUser) {
            this.img = img;
            this.nameUser = nameUser;
            setOpaque(false);
            setLayout(new MigLayout("fill, inset 0",
                    "15[]0[]0", "0[]0"));

            JLabel profile = new JLabel();

            profile.setText(nameUser);
            profile.setIcon(img);
            profile.setFont(getFont().deriveFont(Font.BOLD, 15));
            profile.setIconTextGap(15);
            profile.setHorizontalAlignment(JLabel.LEFT);

            SVGIcon.Chevron_right.setColorFilter(
                    SVGIcon.setColor(Colors.FG_PROFILE_MORE_ICON));
            JButton moreBtn = new JButton(SVGIcon.Chevron_right);
            moreBtn.setContentAreaFilled(false);
            moreBtn.setOpaque(false);
            moreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            moreBtn.setBorder(new EmptyBorder(0, 0, 0, 15));
            add(profile, "w 150!");
            add(moreBtn, "east, w 25!, h 25!");
            revalidate();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Colors.FG_PROFILE_SPLIT);
            g2.drawLine(0, 0, this.getWidth(), 0);

            super.paintComponent(g);
        }

        public ImageIcon getImg() {
            return img;
        }

        public void setImg(ImageIcon img) {
            this.img = img;
        }

        public String getNameUser() {
            return nameUser;
        }

        public void setNameUser(String nameUser) {
            this.nameUser = nameUser;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
