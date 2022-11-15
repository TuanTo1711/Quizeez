package app.quizeez.gui;

import app.quizeez.component.form.PlayOnline;
import app.quizeez.component.menu.Menu;
import app.quizeez.component.titlebar.TitleBar;
import app.quizeez.component.header.Header;
import app.quizeez.modal.Account;
import app.quizeez.material.panel.RoundedPanel;
import app.quizeez.system.SVGIcon;
import app.quizeez.view.page.Home;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Dashboard extends JFrame {

    private final Dimension minimized = new Dimension(720, 480);
    private final Dimension fullscreen
            = Toolkit.getDefaultToolkit().getScreenSize();
    private Animator showingAnimation, open, close;

    private final TitleBar titleBar;
    private final Menu menu;
    private final RoundedPanel main;
    private final Header header;
    private final MigLayout miglayout;

    private boolean menuShow = true;

    private Dashboard() {
        initComponents();
        titleBar = new TitleBar();
        menu = new Menu();
        header = new Header();
        miglayout = new MigLayout("fill, inset 0",
                "0[]0[]0",
                "0[fill]0");
        main = new RoundedPanel();
        main.setLayout(new BorderLayout(0, 0));

        setContentPane(roundedBackground);
        init();
    }

    public Dashboard(boolean login) {
        this();
        if (login) {
            reset();
        }
    }

    private void reset() {
        header.resetProfile(false);
        // change profile and popup header menu
    }

    private void init() {
        this.setOpacity(0.0f);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        this.makeAnimation();

        roundedBackground.setLayout(miglayout);
        roundedBackground.add(titleBar, "north"); // always on top
        roundedBackground.add(menu, "west, w 200!"); // left and width resizable
        roundedBackground.add(main, "center, w 100%"); // center

        titleBar.initMoving(this);
        titleBar.initEvent(this, roundedBackground);

        menu.setShowingAction((ActionEvent e) -> {
            if (!showingAnimation.isRunning()) {
                showingAnimation.start();
            }
        });
        menu.setShow(!menuShow);

        main.setOpaque(false);
        main.setBackground(new Color(0, 0, 0, 0));
        main.add(header, BorderLayout.NORTH);
        showingButtonAnimation();
        showPage(new Home());
    }

    public void showingButtonAnimation() {
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuShow) {
                    width = 80 + (120 * (1f - fraction));
                    menu.setAlpha(1f - fraction);
                } else {
                    width = 80 + (120 * fraction);
                    menu.setAlpha(fraction);
                }
                miglayout.setComponentConstraints(menu, "w " + width + "!");
                roundedBackground.revalidate();
                menu.setShow(menuShow);
            }

            @Override
            public void end() {
                menuShow = !menuShow;
            }
        };
        showingAnimation = new Animator(400, target);
        showingAnimation.setResolution(0);
        showingAnimation.setAcceleration(0.5f);
        showingAnimation.setDeceleration(0.5f);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedBackground = new app.quizeez.material.panel.RoundedPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(minimized);
        setUndecorated(true);

        roundedBackground.setMaximumSize(fullscreen);
        roundedBackground.setMinimumSize(minimized);
        roundedBackground.setPreferredSize(new java.awt.Dimension(1080, 720));

        javax.swing.GroupLayout roundedBackgroundLayout = new javax.swing.GroupLayout(roundedBackground);
        roundedBackground.setLayout(roundedBackgroundLayout);
        roundedBackgroundLayout.setHorizontalGroup(
            roundedBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1081, Short.MAX_VALUE)
        );
        roundedBackgroundLayout.setVerticalGroup(
            roundedBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1081, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundedBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.quizeez.material.panel.RoundedPanel roundedBackground;
    // End of variables declaration//GEN-END:variables

    private void makeAnimation() {
        open = new Animator(800, new TimingTargetAdapter() {
            @Override
            public void begin() {
                super.begin();
                setOpacity(0.0f);
            }

            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fraction);
            }

            @Override
            public void end() {
                super.end();
                setOpacity(1.0f);
            }
        });

        open.setResolution(0);
        open.setAcceleration(0.5f);
        open.setDeceleration(0.5f);

        close = new Animator(800, new TimingTargetAdapter() {
            float fr = 1f;

            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fr - fraction);
            }
        });
        close.setResolution(1);
        close.setAcceleration(0.5f);
        close.setDeceleration(0.5f);
    }

    public void showPage(JPanel form) {
        main.removeAll();
        main.add(header, BorderLayout.NORTH);
        main.add(form, BorderLayout.CENTER);
    }

    @Override
    public void setVisible(boolean b) {
        if (!b && !close.isRunning()) {
            close.start();
            return;
        }
        super.setVisible(b);
        if (b && !open.isRunning()) {
            open.start();
        }
    }
}
