package app.quizeez.view.page;

import app.quizeez.component.slideshow.SlideShow;
import app.quizeez.entity.Question;
import app.quizeez.system.SVGIcon;
import app.quizeez.view.form.QuestionForm;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class PlayQuiz extends JPanel {

    private JLabel score;
    private FlatButton nextBtn, prevBtn;
    private SlideShow slideQuestion;
    private List<QuestionForm> questionsForm;

    public PlayQuiz(List<Question> list) {
        setOpaque(false);
        init();
        setQuestions(list);
    }

    private void init() {
        setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("fill, ins 0",
                "10[]10", "10[]10[]10"));
        questionsForm = new ArrayList<>();
        slideQuestion = new SlideShow();
        slideQuestion.setTimer(3000);

        score = new JLabel("Score: ");
        score.setFont(getFont().deriveFont(Font.BOLD, 20f));
        score.setHorizontalAlignment(SwingConstants.LEFT);

        prevBtn = createButton(SVGIcon.PREVIOUS);
        prevBtn.addActionListener((act) -> slideQuestion.back());
        prevBtn.setHorizontalAlignment(SwingConstants.CENTER);
        prevBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        nextBtn = createButton(SVGIcon.NEXT);
        nextBtn.addActionListener((act) -> slideQuestion.next());
        nextBtn.setHorizontalAlignment(SwingConstants.CENTER);
        nextBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        slideQuestion.setTimer(30000);
        this.add(score, "north, w 100%, gapleft 20px");
        this.add(prevBtn, "w 20!, h 20!");
        this.add(slideQuestion, "w 100%, h 100%");
        this.add(nextBtn, "w 20!, h 20!");
    }

    private void setQuestions(List<Question> list) {
        list.forEach(q -> {
            QuestionForm qf = new QuestionForm(q);
            questionsForm.add(qf);
        });
        slideQuestion.initSlideshow(questionsForm.toArray(Component[]::new));
    }

    public void nextQuestion() {
        slideQuestion.next();
        questionsForm.forEach(form -> {

        });
    }

    private FlatButton createButton(FlatSVGIcon icon) {
        FlatButton button = new FlatButton();

        button.setIcon(icon);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
}
