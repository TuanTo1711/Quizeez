package app.quizeez.main;

import app.quizeez.gui.Dashboard;
import app.quizeez.gui.SplashScreen;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.SwingUtilities;

public class Application {

    public static void main(String[] args) throws IOException, URISyntaxException {

        FlatCyanLightIJTheme.setup();
        instance = new Dashboard(false);
        SwingUtilities.invokeLater(() -> {
            new SplashScreen(instance, false).setVisible(true);
        });
    }

    private static Dashboard instance;

    public static Dashboard getInstance() {
        return instance;
    }

    public static void setInstance(Dashboard ins) {
        instance = ins;
    }
}
