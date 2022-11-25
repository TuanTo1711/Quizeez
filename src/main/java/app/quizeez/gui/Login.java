package app.quizeez.gui;

import app.quizeez.dialog.verify.VerifyPanel;
import app.quizeez.dialog.notification.Notification;
import app.quizeez.modal.Account;
import app.quizeez.entity.User;
import app.quizeez.util.MailUtils;
import app.quizeez.entity.service.UserService;
import app.quizeez.view.form.Detail;
import app.quizeez.view.form.LaRForm;
import app.quizeez.dialog.loading.Loading;
import app.quizeez.main.Application;
import app.quizeez.modal.NotificationModal;
import app.quizeez.modal.RegisterModal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Login extends JFrame {

    private JLayeredPane bg;
    private MigLayout layout;
    private Animator animator;

    private Detail cover;
    private VerifyPanel verifyCode;
    private Loading loading;
    private LaRForm loginAndRegister;

    private boolean isLogin = false;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;

    private final UserService service;

    public Login() {
        service = new UserService();
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        init();
        setOpacity(0f);
        addMouseListener(new MouseAdapter() {
        });
        addKeyListener(new KeyAdapter() {
        });
        setFocusCycleRoot(true);
    }

    private void init() {
        this.setBackground(new Color(0, 0, 0, 0));
        this.setMinimumSize(new Dimension(900, 500));
        this.setLocationRelativeTo(null);
        this.setLayout(new MigLayout("fill, ins 0",
                "", ""));

        bg = new JLayeredPane();
        layout = new MigLayout("fill, insets 0");
        cover = new Detail();
        loading = new Loading();
        verifyCode = new VerifyPanel();
        loginAndRegister = new LaRForm();
        makeAnimation();
        initEvent();

        bg.setOpaque(true);
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);

        this.add(bg, "w 900!, h 500!, center");
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(verifyCode, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%"); //  1al as 100%
    }

    private void initEvent() {
        loginAndRegister.addRegisterEvent((ActionEvent e) -> {
            if (loginAndRegister.getRegisterValidation()) {
                register();
                return;
            }
            new Notification(
                    this,
                    Notification.Type.INFO,
                    Notification.Location.TOP_CENTER,
                    "Please check and try again!"
            ).showNotification();
        });

        loginAndRegister.addLoginEvent((ActionEvent e) -> {
            if (loginAndRegister.getLoginValidation()) {
                login();
                return;
            }
            new Notification(
                    this,
                    Notification.Type.INFO,
                    Notification.Location.TOP_CENTER,
                    "Please check and try again!"
            ).showNotification();
        });

        verifyCode.addEventButtonOK((ae) -> {
            User user = RegisterModal.user;
            if (verifyCode.getInputCode()
                    .equalsIgnoreCase(RegisterModal.code)) {
                user.setStatus("Verified");
                service.insert(user);
                new Notification(
                        this,
                        Notification.Type.SUCCESS,
                        Notification.Location.TOP_CENTER,
                        "Register Successfully"
                ).showNotification();
                verifyCode.setVisible(false);
                verifyCode.resetInputCode();
                loginAndRegister.resetRegister();
            } else {
                new Notification(
                        this,
                        Notification.Type.WARNING,
                        Notification.Location.TOP_CENTER,
                        "Verify Code Incorrect!"
                ).showNotification();
                verifyCode.resetInputCode();
            }
        });

        loading.setVisible(false);
    }

    public void makeAnimation() {
        // animation slide
        final DecimalFormat formatter = new DecimalFormat("##0.###");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.parseDouble(formatter.format(fractionCover));
                fractionLogin = Double.parseDouble(formatter.format(fractionLogin));
                layout.setComponentConstraints(cover,
                        "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister,
                        "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
                loginAndRegister.resetRegister();
                loginAndRegister.resetLogin();
            }
        };

        animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);  //  for smooth animation
        cover.addActionEvent((ActionEvent ae) -> {
            if (!animator.isRunning()) {
                animator.start();
            }
        });

        // close login form animation
        Animator openAnimation = new Animator(800, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fraction);
            }
        });

        openAnimation.setResolution(0);
        openAnimation.setAcceleration(0.5f);
        openAnimation.setDeceleration(0.5f);

        Animator closeAnimation = new Animator(800, new TimingTargetAdapter() {
            float fr = 1f;

            @Override
            public void timingEvent(float fraction) {
                super.timingEvent(fraction);
                setOpacity(fr - fraction);
            }

            @Override
            public void end() {
                super.end();
                dispose();
                Application.getInstance().setVisible(true);
            }
        });

        closeAnimation.setResolution(0);
        closeAnimation.setAcceleration(0.5f);
        closeAnimation.setDeceleration(0.5f);

        cover.addCloseEvent((ActionEvent ae) -> {
            if (!closeAnimation.isRunning()) {
                closeAnimation.start();
            }
        });

        // showPage frame animation
        if (!openAnimation.isRunning()) {
            openAnimation.start();
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                if (!closeAnimation.isRunning()) {
                    closeAnimation.start();
                }
            }
        });
    }

    private void register() {
        User data = service.find(RegisterModal.user.getUsername());
        if (Objects.isNull(data)) {
            RegisterModal.code = service.generateCode();
            sendMail(RegisterModal.user.getEmail(), RegisterModal.code);
        } else {
            new Notification(
                    this,
                    Notification.Type.WARNING,
                    Notification.Location.TOP_CENTER,
                    "Account has exists!"
            ).showNotification();
        }
    }

    private void login() {
        User data = service.find(Account.username);
        if (Objects.isNull(data)) {
            new Notification(
                    this,
                    Notification.Type.ERROR,
                    Notification.Location.TOP_CENTER,
                    "Account doesn't exist!"
            ).showNotification();
            return;
        }

        Account.fullname = data.getFullname();

        if (data.getPassword().equalsIgnoreCase(Account.password)) {
            this.dispose();
            Application.getInstance().dispose();
            Application.setInstance(new Dashboard(true));
            Application.getInstance().setVisible(true);
            new Notification(
                    Application.getInstance(),
                    Notification.Type.SUCCESS,
                    Notification.Location.TOP_CENTER,
                    "Login Successfully!"
            ).showNotification();
        } else {
            new Notification(
                    this,
                    Notification.Type.WARNING,
                    Notification.Location.TOP_CENTER,
                    "Username or password incorrectly!"
            ).showNotification();
        }
    }

    private void sendMail(String toEmail, String code) {
        new Thread(() -> {
            loading.setVisible(true);
            NotificationModal ms = MailUtils.sendMail(toEmail, code);
            if (ms.isSuccess()) {
                loading.setVisible(false);
                verifyCode.setVisible(true);
                new Notification(this,
                        Notification.Type.SUCCESS,
                        Notification.Location.TOP_CENTER,
                        ms.getMessage()
                ).showNotification();
            } else {
                loading.setVisible(false);
                new Notification(this,
                        Notification.Type.WARNING,
                        Notification.Location.TOP_CENTER,
                        ms.getMessage()
                ).showNotification();
            }

        }).start();
    }
}
