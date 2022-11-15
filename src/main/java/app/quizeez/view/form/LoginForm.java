package app.quizeez.view.form;

import app.quizeez.controller.Validator;
import app.quizeez.entity.User;
import app.quizeez.modal.Account;
import app.quizeez.material.field.PasswordField;
import app.quizeez.material.field.TextField;
import app.quizeez.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatCheckBox;
import com.formdev.flatlaf.icons.FlatHelpButtonIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class LoginForm extends JPanel {

    private final User loginUser;
    private TextField usernameField;
    private PasswordField passwordField;
    private FlatCheckBox rememberButton;
    private FlatButton loginButton, forgetPasswordButton;

    public LoginForm() {
        loginUser = new User();
        init();
    }

    private void init() {
        this.setLayout(new MigLayout("wrap, gap 25! 10!, ins 0",
                "push[c]push",
                "push[c][][][][][c]push"));

        JLabel title = new JLabel("SIGN IN");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 35f));
        title.setForeground(getForeground());

        usernameField = new TextField("Username", SVGIcon.USER);
        passwordField = new PasswordField("Password");

        rememberButton = new FlatCheckBox();
        rememberButton.setText("Remember?");
        rememberButton.setForeground(Color.white);

        forgetPasswordButton = new FlatButton();
        forgetPasswordButton.setForeground(Color.white);
        forgetPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgetPasswordButton.setIcon(new FlatHelpButtonIcon());
        forgetPasswordButton.setContentAreaFilled(false);
        forgetPasswordButton.setOpaque(false);
        forgetPasswordButton.setHorizontalAlignment(JButton.LEFT);
        forgetPasswordButton.setText("Forget password?");

        loginButton = createButtonAction("SIGN IN");

        this.add(title, "gapbottom 40px");
        this.add(usernameField, "w 300!, h 55!");
        this.add(passwordField, "w 300!, h 55!");
        this.add(forgetPasswordButton,
                "split, w 200!, h 35!, left");
        this.add(rememberButton, "wrap, w 100!, r");
        this.add(loginButton,
                "gaptop 30px, w 150!, h 35!");
    }

    public boolean valid() {
        Boolean[] rules = new Boolean[]{
            Validator.isRequired(usernameField, "Username hasn't empty!"),
            Validator.isRequired(passwordField, "Password hasn't empty!"),
            Validator.isPassword(passwordField, "Password incorrect!")
        };

        boolean isValid = Validator.validate(rules);

        if (isValid) {
            Account.username = usernameField.getText();
            Account.password = passwordField.getValue();
            Account.hasRemember = rememberButton.isSelected();
        }

        return isValid;
    }

    public void reset() {
        usernameField.setText("");
        usernameField.setMessage("");
        passwordField.setText("");
        passwordField.setMessage("");
    }

    private FlatButton createButtonAction(String text) {
        FlatButton button = new FlatButton();

        button.setText(text);
        button.setBackground(UIManager.getColor(FlatIconColors.ACTIONS_YELLOW.key));
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setButtonType(FlatButton.ButtonType.roundRect);

        return button;
    }

    public FlatButton getLoginButton() {
        return loginButton;
    }

    public FlatButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }
}
