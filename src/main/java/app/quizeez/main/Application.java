package app.quizeez.main;

import app.quizeez.gui.Dashboard;
import app.quizeez.gui.SplashScreen;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import javax.swing.SwingUtilities;

public class Application {

    public static void main(String[] args) {
        FlatCyanLightIJTheme.setup();
        dashBoard = new Dashboard();
        SwingUtilities.invokeLater(() -> {
            new SplashScreen(null, true).setVisible(true);
        });
    }

    private static Dashboard dashBoard;

    public static Dashboard getDashBoard() {
        return dashBoard;
    }
}
