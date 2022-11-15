package app.quizeez.dialog.notification;

import app.quizeez.util.ShadowRenderer;
import app.quizeez.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.UIManager;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Notification extends JComponent {

    private JDialog dialog;
    private Animator animator;
    private final Frame fram;
    private boolean showing;
    private Thread thread;
    private final int animate = 10;
    private BufferedImage imageShadow;
    private final int shadowSize = 8;
    private final Type type;
    private final Location location;

    public Notification(Frame fram, Type type, Location location, String message) {
        this.fram = fram;
        this.type = type;
        this.location = location;
        initComponents();
        init(message);
        initAnimator();
        cmdClose.setIcon(SVGIcon.CLOSE);
    }

    private void init(String message) {
        setBackground(Color.WHITE);
        dialog = new JDialog(fram);
        dialog.setUndecorated(true);
        dialog.setFocusableWindowState(false);
        dialog.setBackground(new Color(0, 0, 0, 0));
        dialog.add(this);
        dialog.setSize(getPreferredSize());
        switch (type) {
            case SUCCESS -> {
                lbIcon.setIcon(SVGIcon.SUCCESS);
                lbMessage.setText("Success");
            }
            case INFO -> {
                lbIcon.setIcon(SVGIcon.INFORMATION);
                lbMessage.setText("Info");
            }
            case ERROR -> {
                lbIcon.setIcon(SVGIcon.ERROR);
                lbMessage.setText("Error");
            }
            case WARNING -> {
                lbIcon.setIcon(SVGIcon.WARNING);
                lbMessage.setText("Error");
            }
            default -> {
                lbIcon.setIcon(null);
                lbMessage.setText(null);
            }
        }
        lbMessageText.setText(message);
    }

    private void initAnimator() {
        TimingTarget target = new TimingTargetAdapter() {

            private int xPos;
            private int top;
            private boolean top_to_bot;

            @Override
            public void timingEvent(float fraction) {
                float alpha = showing ? 1f - fraction : fraction;

                int yPos = showing
                        ? (int) ((1f - fraction) * animate)
                        : (int) (fraction * animate);

                dialog.setLocation(xPos, top_to_bot ? top + yPos : top - yPos);
                dialog.setOpacity(alpha);
            }

            @Override
            public void begin() {
                if (!showing) {
                    dialog.setOpacity(0f);
                    
                    int left = fram.getX();
                    int right = left + fram.getWidth() - dialog.getWidth();
                    int width = fram.getWidth() - dialog.getWidth();
                    int height = fram.getHeight() - dialog.getHeight();

                    int margin = 10;
                    int y;
                    switch (location) {
                        case TOP_CENTER -> {
                            // ~ x at 1/2 width and y at 0
                            xPos = left + width / 2;
                            y = fram.getY();
                            top_to_bot = true;
                        }
                        case TOP_RIGHT -> {
                            // ~ x at width of frame - margin and y at 0
                            xPos = right - margin;
                            y = fram.getY();
                            top_to_bot = true;
                        }
                        case TOP_LEFT -> {
                            // ~ x at 0 + margin and y at 0
                            xPos = left + margin;
                            y = fram.getY();
                            top_to_bot = true;
                        }
                        case BOTTOM_CENTER -> {
                            // ~ x at 1/2 width and y at 0
                            xPos = left + width / 2;
                            y = fram.getY() + height;
                            top_to_bot = false;
                        }
                        case BOTTOM_RIGHT -> {
                            xPos = right - margin;
                            y = fram.getY() + height;
                            top_to_bot = false;
                        }
                        case BOTTOM_LEFT -> {
                            xPos = left + margin;
                            y = fram.getY() + height;
                            top_to_bot = false;
                        }
                        default -> {
                            xPos = left + width / 2;
                            y = fram.getY() + height / 2;
                            top_to_bot = true;
                        }
                    }
                    top = y;
                    dialog.setLocation(xPos, y);
                    dialog.setVisible(true);
                }
            }

            @Override
            public void end() {
                showing = !showing;
                if (showing) {
                    thread = new Thread(() -> {
                        sleep();
                        closeNotification();
                    });
                    thread.start();
                } else {
                    dialog.dispose();
                }
            }
        };
        animator = new Animator(500, target);
        animator.setResolution(5);
    }

    public void showNotification() {
        animator.start();
    }

    private void closeNotification() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
        if (animator.isRunning()) {
            if (!showing) {
                animator.stop();
                showing = true;
                animator.start();
            }
        } else {
            showing = true;
            animator.start();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void paint(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.drawImage(imageShadow, 0, 0, null);
        int x = shadowSize;
        int y = shadowSize;
        int width = getWidth() - shadowSize * 2;
        int height = getHeight() - shadowSize * 2;
        g2.fillRect(x, y, width, height);
        switch (type) {
            case SUCCESS ->
                g2.setColor(UIManager.getColor(FlatIconColors.ACTIONS_GREEN.key));
            case INFO ->
                g2.setColor(UIManager.getColor(FlatIconColors.ACTIONS_BLUE.key));
            case WARNING ->
                g2.setColor(UIManager.getColor(FlatIconColors.ACTIONS_YELLOW.key));
            case ERROR ->
                g2.setColor(UIManager.getColor(FlatIconColors.ACTIONS_RED.key));
            default ->
                g2.setColor(null);
        }
        g2.fillRect(6, 5, 5, getHeight() - shadowSize * 2 + 1);
        g2.dispose();
        super.paint(grphcs);
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createImageShadow();
    }

    private void createImageShadow() {
        imageShadow = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(createShadow(), 0, 0, null);
        g2.dispose();
    }

    private BufferedImage createShadow() {
        BufferedImage img
                = new BufferedImage(getWidth() - shadowSize * 2, getHeight() - shadowSize * 2,
                        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        g2.dispose();
        return new ShadowRenderer(shadowSize, 0.3f,
                new Color(100, 100, 100)).createShadow(img);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbIcon = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        lbMessage = new javax.swing.JLabel();
        lbMessageText = new javax.swing.JLabel();
        cmdClose = new javax.swing.JButton();

        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        panel.setOpaque(false);

        lbMessage.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lbMessage.setForeground(new java.awt.Color(38, 38, 38));
        lbMessage.setText("Message");

        lbMessageText.setForeground(new java.awt.Color(127, 127, 127));
        lbMessageText.setText("Message Text");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbMessage)
                    .addComponent(lbMessageText))
                .addContainerGap(267, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMessage)
                .addGap(3, 3, 3)
                .addComponent(lbMessageText)
                .addContainerGap())
        );

        cmdClose.setBorder(null);
        cmdClose.setContentAreaFilled(false);
        cmdClose.setFocusable(false);
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbIcon)
                .addGap(10, 10, 10)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdClose)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        closeNotification();
    }//GEN-LAST:event_cmdCloseActionPerformed

    public static enum Type {
        SUCCESS, INFO, WARNING, ERROR
    }

    public static enum Location {
        TOP_CENTER, TOP_RIGHT, TOP_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT, CENTER
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdClose;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JLabel lbMessageText;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
