package app.quizeez.component.form;

import app.quizeez.system.Colors;
import app.quizeez.system.SVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class PlayOnline extends JPanel {

    private final MigLayout layout;
    private Animator showRule;
    private boolean show = true;
    private JPanel header;
    private JPanel rule;
    private JPanel rooms;

    public PlayOnline() {
        layout = new MigLayout("wrap, fillx, ins 15, debug",
                "0[]0",
                "0[]0[c]0[]0");
        initComponents();
        this.setLayout(layout);
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        header = new JPanel();
        rule = new JPanel();
        rooms = new JPanel();

        this.add(header, "w 100%, h 5%");
        this.add(rule, "w 100%, h 25%");
        this.add(rooms, "w 100%, h 70%");

        header.setOpaque(false);
        rule.setOpaque(false);
        rooms.setOpaque(false);

        rule.add(new JButton("dasdsadas"));
//        rule.setAlpha(1f);

        showRulesAnimate();
        createHeaderPanel();
        createRooms();
    }

    private void createHeaderPanel() {
        header.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 3));
        FlatButton createRoom = createButton("Create", SVGIcon.NEW);

        createRoom.addActionListener((ActionEvent e) -> {

        });

        FlatButton joinBtn = createButton("Join", SVGIcon.JOIN);

        FlatButton ruleButton = new FlatButton();
        ruleButton.setButtonType(FlatButton.ButtonType.help);
        ruleButton.addActionListener((ActionEvent e) -> {
            System.out.println("clicked");
            if (!showRule.isRunning()) {
                showRule.start();
            }
        });

        header.add(ruleButton);
        header.add(createRoom);
        header.add(joinBtn);
    }

    private FlatButton createButton(String name, FlatSVGIcon icon) {
        icon.setColorFilter(SVGIcon.setColor(Colors.FG_MENU_ITEM));
        FlatButton button = new FlatButton();

        button.setButtonType(FlatButton.ButtonType.square);
        button.setText(name);
        button.setIcon(icon);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFont(getFont().deriveFont(Font.BOLD, 15f));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void createRooms() {
        MigLayout roomLayout = new MigLayout("wrap, fillx, ins 0",
                "10[]35[]35[]10",
                "35[]35");
        rooms.setLayout(roomLayout);
        addRoom("Room of admin", generateRoomCode(), 1);
        addRoom("Room of admin", generateRoomCode(), 2);
        addRoom("Room of admin", generateRoomCode(), 3);
        addRoom("Room of admin", generateRoomCode(), 4);
        addRoom("Room of admin", generateRoomCode(), 5);

    }

    private void addRoom(String nameroom, String codeRoom, int index) {

    }

    private void createRules() {

    }

    private String generateRoomCode() {
        DecimalFormat df = new DecimalFormat("00000");

        return df.format(new Random().nextInt(100000));// 0 -> 9999
    }

    private void showRulesAnimate() {
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                DecimalFormat df = new DecimalFormat("##.##");
                double ruleHeight;
                float roomsHeight;
                if (show) {
                    ruleHeight = (1f - fraction) * 25;
                    roomsHeight = 70 + (25 * fraction);
                } else {
                    ruleHeight = 25 * fraction;
                    roomsHeight = 100 - (30 * fraction);
                }
                layout.setComponentConstraints(rule, "w 100%, h "
                        + df.format(ruleHeight) + "%");
                layout.setComponentConstraints(rooms, "w 100%, h "
                        + df.format(roomsHeight) + "%");
                getParent().revalidate();
            }

            @Override
            public void end() {
                super.end();
                show = !show;
            }
        };

        showRule = new Animator(500, target);
        showRule.setResolution(0);
        showRule.setDeceleration(0.5f);
        showRule.setAcceleration(0.5f);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
