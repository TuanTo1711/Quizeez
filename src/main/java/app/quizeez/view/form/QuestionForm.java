package app.quizeez.view.form;

import app.quizeez.entity.Question;
import app.quizeez.material.panel.RoundedPanel;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.extras.components.FlatCheckBox;
import com.formdev.flatlaf.extras.components.FlatTextArea;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class QuestionForm extends JPanel {

    public Map<String, Boolean> map;

    private RoundedPanel questionPane, answerPane;
    private FlatButton ansBtn1, ansBtn2, ansBtn3, ansBtn4;
    private final boolean many;

    public QuestionForm(Question question) {
        setOpaque(false);
        init();
        createQuestionPane(question);
        createAnswerPane(question);
        this.many = question.getType() == Question.Type.MANY;
    }

    private void init() {
        map = new HashMap<>();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("wrap, fill, debug",
                "0[]0", "0[]10[]0"));

        questionPane = new RoundedPanel(15);
        answerPane = new RoundedPanel(15);

        this.add(questionPane, "w 100%, h 40%");
        this.add(answerPane, "w 100%, h 60%");
    }

    private void createQuestionPane(Question question) {
        MigLayout qLayout = new MigLayout("fill, wrap",
                "10[fill]10", "0[fill]10[fill]5");

        questionPane.setLayout(qLayout);
        FlatTextArea qArea = createQuestionArea(question);
        
        JLabel lblTime = new JLabel("Time");
        lblTime.setHorizontalAlignment(SwingConstants.RIGHT);

        questionPane.add(lblTime, "w 100%, h 10%");
        questionPane.add(qArea, "w 100%, h 90%");
    }

    public FlatTextArea createQuestionArea(Question question) {
        FlatTextArea qArea = new FlatTextArea();
        qArea.setEditable(false);
        qArea.setFocusable(false);
        qArea.setFont(getFont().deriveFont(Font.BOLD, 18f));
        qArea.setText(question.getValue());
        qArea.setLineWrap(true);
        qArea.setBackground(null);
        return qArea;
    }

    private void createAnswerPane(Question question) {
        MigLayout ansLayout = new MigLayout("wrap, fill, debug",
                "0[]15[]0", "15[]15[]30");

        answerPane.setLayout(ansLayout);

        initAnswerButton(question.getAns());
    }

    public void initAnswerButton(Question.Answer answer) {
        ansBtn1 = createAnswerButton(answer.getAns1());
        ansBtn2 = createAnswerButton(answer.getAns2());
        ansBtn3 = createAnswerButton(answer.getAns3());
        ansBtn4 = createAnswerButton(answer.getAns4());

        answerPane.add(ansBtn1, "w 50%, h 50%");
        answerPane.add(ansBtn2, "w 50%, h 50%");
        answerPane.add(ansBtn3, "w 50%, h 50%");
        answerPane.add(ansBtn4, "w 50%, h 50%");
    }

    public void addAnsBtnListener(ActionListener act) {
        ansBtn1.addActionListener(act);
        ansBtn2.addActionListener(act);
        ansBtn3.addActionListener(act);
        ansBtn4.addActionListener(act);
    }

    private FlatButton createAnswerButton(String ansValue) {
        FlatButton button = new FlatButton();

        button.setLayout(new MigLayout());

        button.setButtonType(FlatButton.ButtonType.square);
        button.setText(ansValue);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (many) {
            addCheckBoxButton(button);
        }
        
        return button;
    }

    public void addCheckBoxButton(FlatButton button) {
        FlatCheckBox cb = new FlatCheckBox();
        cb.removeMouseListener(cb.getMouseListeners()[0]);
        cb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cb.setSelected(!cb.isSelected());
            }
        });
        
        button.add(cb, "pos 0al 0al n n");

        button.addActionListener((ActionEvent e) -> {
            cb.setSelected(!cb.isSelected());
        });
    }

    public void changeBackground(FlatButton button) {

        
        
        if (many) {
            
        }
    }
}
