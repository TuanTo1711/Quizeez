package app.quizeez.component.header;

import app.quizeez.gui.Login;
import app.quizeez.main.Application;
import app.quizeez.material.button.SwitchAdapter;
import app.quizeez.material.button.SwitchButton;
import app.quizeez.system.SVGIcon;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class Header extends JPanel {

    private final JPanel controlPage;
    private final JPanel profilePane;
    private final JButton prev, refresh, next;

    private final MigLayout layout;

    public Header() {
        controlPage = new JPanel();
        profilePane = new JPanel();
        prev = new JButton(SVGIcon.PREVIOUS_PAGE);
        refresh = new JButton(SVGIcon.REFRESH_PAGE);
        next = new JButton(SVGIcon.NEXT_PAGE);

        layout = new MigLayout("filly, ins 0",
                "20[70%, l | 30%, r]20",
                "15[40, shrink, fill]15");
        setOpaque(false);
        setLayout(layout);

        createHistoryControl();
        createProfile();
    }

    private void createHistoryControl() {
        controlPage.setLayout(new MigLayout("fill, center",
                "10[]20[]10",
                "0[]0"));
        controlPage.setOpaque(false);

        addControl(prev);
        addControl(refresh);
        addControl(next);

        this.add(controlPage);
    }

    private void addControl(JButton button) {
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        controlPage.add(button);
    }

    private void createProfile() {
        final MigLayout profileLayout = new MigLayout("fill, center",
                "20[fill, r]20",
                "0[center]0"
        );
        profilePane.setOpaque(false);
        profilePane.setLayout(profileLayout);

        createSwitchButton();
        crateLoginButton();
        this.add(profilePane);
    }

    private void createSwitchButton() {
        SwitchButton switchButton = new SwitchButton();

        switchButton.addEventToggleSelected(new SwitchAdapter() {
            @Override
            public void onSelected(boolean selected) {
                if (!selected) {
                    SwingUtilities.invokeLater(() -> {
                        FlatAnimatedLafChange.showSnapshot();
                        FlatCyanLightIJTheme.setup();
                        FlatCyanLightIJTheme.updateUI();
                        FlatAnimatedLafChange.hideSnapshotWithAnimation();
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        FlatAnimatedLafChange.showSnapshot();
                        FlatDarkPurpleIJTheme.setup();
                        FlatDarkPurpleIJTheme.updateUI();
                        FlatAnimatedLafChange.hideSnapshotWithAnimation();
                    });
                }
            }
        });
        profilePane.add(switchButton, "w 50!, h 30!, c");
    }

    private void crateLoginButton() {
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setOpaque(false);
        loginBtn.setHorizontalAlignment(JButton.CENTER);
        loginBtn.setHorizontalTextPosition(JButton.LEFT);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setContentAreaFilled(false);
        loginBtn.setIcon(SVGIcon.LOGO);
        loginBtn.addActionListener((ActionEvent e) -> {
            new Login().setVisible(true);
        });
        profilePane.add(loginBtn);
    }
}
