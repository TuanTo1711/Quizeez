package app.quizeez.main;

import app.quizeez.gui.Dashboard;
import com.groupdocs.conversion.internal.c.f.j.db.util.Converter;
import com.groupdocs.foundation.domain.FileType;

public class Application {

    public static void main(String[] args) {

//        FlatIntelliJLaf.setup();
//
//        dashBoard = new Dashboard(false);
//        SwingUtilities.invokeLater(() -> {
//            new SplashScreen(null, false).setVisible(true);
//        });
        
    }

    private static Dashboard dashBoard;

    public static Dashboard getDashBoard() {
        return dashBoard;
    }

    public static void setDashboard(Dashboard d) {
        dashBoard = d;
    }
}
