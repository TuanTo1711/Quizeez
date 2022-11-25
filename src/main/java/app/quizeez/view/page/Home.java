package app.quizeez.view.page;

import app.quizeez.material.scrollbar.ScrollBarCustom;
import app.quizeez.modal.RoomModal;
import app.quizeez.dialog.room.CreateRoomForm;
import app.quizeez.main.Application;
import app.quizeez.system.Colors;
import app.quizeez.system.SVGIcon;
import com.formdev.flatlaf.FlatIconColors;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatTextArea;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

public class Home extends JPanel {

    private final ScrollBarCustom scrolling = new ScrollBarCustom();
    private List<RoomModal> rooms;
    private JLabel title;
    private JPanel panel, header;
    private MigLayout layout;
    private JScrollPane scrollBarCustom;
    private CreateRoomForm form;

    public Home() {
        setOpaque(false);
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, wrap, ins 0",
                "15[fill]15", "5[]0"));
        rooms = new ArrayList<>();

        layout = new MigLayout("wrap, fillx, ins 0",
                "10[]40[]40[]10",
                "10[]35");

        header = new JPanel();
        panel = new JPanel(layout);
        initScroller();
        form = new CreateRoomForm(Application.getInstance(),
                true);

        header.setLayout(new MigLayout("filly, right",
                "0[c]20[c]35", "[fill]"));

        FlatButton createRoomButton = createButton("Create", SVGIcon.NEW);
        createRoomButton.setBackground(new Color(0, 0, 0, 0));

        FlatButton joinBtn = createButton("Join", SVGIcon.JOIN);

        joinBtn.setButtonType(FlatButton.ButtonType.roundRect);

        createRoomButton.addActionListener((act) -> {
            form.setVisible(true);
        });

        form.addEventCreateButton((ActionEvent e) -> {
            if (!form.validation()) {
                return;
            }
            addRoom(form.getRoomModal());
            form.setVisible(false);
        });

        header.add(createRoomButton, "w 120!, h 40!");
        header.add(joinBtn, "w 100!, h 40!");

        title = new JLabel("Room");
        title.setFont(new Font("Tahoma", Font.BOLD, 15));

        add(title, "split, h 40!");
        add(header, "wrap, h 40");
        add(scrollBarCustom, "w 100%, h 100%");
    }

    public void initScroller() {
        scrollBarCustom = new JScrollPane(panel) {
            @Override
            public void updateUI() {
                super.updateUI();
                setBorder(null);
            }
        };

        scrollBarCustom.setBorder(null);
        scrollBarCustom.setViewportBorder(null);
        scrollBarCustom.getViewport().setOpaque(false);
        scrollBarCustom.setVerticalScrollBar(scrolling);
        scrollBarCustom.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
    }

    private FlatButton createButton(String text, FlatSVGIcon icon) {
        icon.setColorFilter(SVGIcon.setColor(Colors.FG_MENU_ITEM));
        FlatButton button = new FlatButton();

        button.setButtonType(FlatButton.ButtonType.roundRect);
        button.setText(text);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        button.setIcon(icon);
        button.setIconTextGap(15);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);

        return button;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(createShape());
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private Shape createShape() {
        float width = getWidth();
        float height = getHeight();

        Area area = new Area();
        area.add(new Area(new RoundRectangle2D.Float(0f, 0f, width, height, 15f, 15f)));
        area.add(new Area(new Rectangle2D.Float(0, 0, 15, 15)));
        area.add(new Area(new Rectangle2D.Float(0, height - 15, 15, 15)));
        area.add(new Area(new Rectangle2D.Float(width - 15, 0, 15, 15)));

        return area;
    }

    public void addRoom(RoomModal room) {
        Room card = new Room(room);

        panel.add(card, "w 250!, h 150!");
        rooms.add(room);

        revalidate();
    }

    class Room extends JPanel {

        private final RoomModal modal;
        private PlayQuiz play;
        private MigLayout layout;
        private JPanel info;
        private FlatTextArea lblName;
        private JLabel participantsCount;
        private FlatButton joinBtn;

        public Room(RoomModal modal) {
            setOpaque(false);
            this.modal = modal;
            initCard();
            setBorder(new TitledBorder(
                    new LineBorder(
                            UIManager.getColor(
                                    FlatIconColors.ACTIONS_GREYINLINE.key),
                            3, true
                    ),
                    modal.getRoomID(),
                    TitledBorder.CENTER,
                    TitledBorder.TOP));
        }

        private void initCard() {
            setBackground(randomColor(getForeground()).brighter());
            layout = new MigLayout("wrap, fill, ins 0",
                    "0[fill]0", "0[80%, fill]10[20%]10");

            info = new JPanel(new GridLayout(1, 1));

            setLayout(layout);
            lblName = new FlatTextArea();
            lblName.setLineWrap(true);
            lblName.setText(modal.getRoomName());
            lblName.setEditable(false);
            lblName.setFocusable(false);
            
            info.add(lblName);
            info.setBackground(Color.white);
            info.setOpaque(true);
            participantsCount = new JLabel("0/0");
            participantsCount.setHorizontalAlignment(JLabel.CENTER);
            joinBtn = new FlatButton();

            joinBtn.addActionListener((ActionEvent e) -> {
                play = new PlayQuiz(modal.getList());
                Application.getInstance().showPage(play);
            });

            joinBtn.setText("Join");
            joinBtn.setOpaque(false);
            joinBtn.setButtonType(FlatButton.ButtonType.roundRect);

            add(info, "c");
            add(participantsCount, "split, w 80%");
            add(joinBtn, "w 20%, h 30!");
        }

        @Override
        protected void paintComponent(Graphics grphcs) {
            super.paintComponent(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(),
                    getHeight(), 15, 15);
            g2.dispose();
        }

        private Color randomColor(Color anotherColor) {
            Random rand = new Random();

            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);

            Color color;
            do {
                color = new Color(r, g, b);
            } while (color.equals(anotherColor) || color.equals(Color.white));

            return color;
        }
    }
}
