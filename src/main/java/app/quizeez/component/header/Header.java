package app.quizeez.component.header;

import app.quizeez.event.SwitchAdapter;
import app.quizeez.gui.Login;
import app.quizeez.main.Application;
import app.quizeez.material.button.SwitchButton;
import app.quizeez.system.Colors;
import app.quizeez.system.SVGIcon;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class Header extends JPanel {

    private final JPanel profilePane;
    private final MigLayout layout;

    public Header() {
        profilePane = new JPanel();

        layout = new MigLayout("filly, ins 0",
                "20[100%, r]20",
                "15[40, shrink, fill]15");
        setOpaque(false);
        setLayout(layout);

        createProfile();
    }

    private void createProfile() {
        final MigLayout profileLayout = new MigLayout("filly, r",
                "20[fill, c]20",
                "0[c]0"
        );
        profilePane.setOpaque(false);
        profilePane.setLayout(profileLayout);

        createSwitchButton(false);
        crateLoginButton();
        this.add(profilePane);
    }

    private void createSwitchButton(boolean selected) {
        SwitchButton switchButton = new SwitchButton();

        switchButton.setSelected(selected, false);
        switchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchButton.setBorder(new EmptyBorder(5, 0, 0, 0));
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
        SVGIcon.LOGIN.setColorFilter(SVGIcon.setColor(Colors.FG_MENU_SHOWING));
        JButton loginBtn = new JButton(SVGIcon.LOGIN);
        loginBtn.setFont(getFont().deriveFont(Font.BOLD, 15));
        loginBtn.setOpaque(false);
        loginBtn.setHorizontalAlignment(JButton.CENTER);
        loginBtn.setVerticalAlignment(JButton.CENTER);

        loginBtn.setHorizontalTextPosition(JButton.RIGHT);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setContentAreaFilled(false);
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)
                        && e.getClickCount() == 1) {
                    SwingUtilities.invokeLater(() -> {
                        Application.getDashBoard().setVisible(false);
                        new Login().setVisible(true);
                    });
                }
            }
        });
        profilePane.add(loginBtn);
    }

    public void resetProfile(boolean darkMode) {
        profilePane.removeAll();
        createSwitchButton(darkMode);
        createLogoutButton();
    }

    private void createLogoutButton() {
        JButton logout = new JButton();
        logout.setFont(getFont().deriveFont(Font.BOLD, 15));
        logout.setOpaque(false);
        logout.setHorizontalAlignment(JButton.CENTER);
        logout.setVerticalAlignment(JButton.CENTER);

        logout.setHorizontalTextPosition(JButton.LEFT);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.setContentAreaFilled(false);
        SVGIcon.SETTING.setColorFilter(SVGIcon.setColor(Colors.FG_MENU_ITEM));
        logout.setIcon(SVGIcon.SETTING);
        profilePane.add(logout);
    }
}
