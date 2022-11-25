package app.quizeez.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question extends Entity {

    private Integer ID;
    private String value;
    private Type type;
    private Integer quizID;
    private Answer ans;

    public Question() {
        this.ans = new Answer();
        this.value = "";
    }

    @Override
    public Object[] toInsertData() {
        return new Object[]{
            value,
            type == Type.MANY,
            ans.getAns1(),
            ans.getAns2(),
            ans.getAns3(),
            ans.getAns4(),
            ans.correctsToString()
        };
    }

    @Override
    public Object[] toUpdateData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[] toDeleteData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        return value + '\n'
                + type + '\n'
                + ans.toString();
    }

    public static enum Type {
        ONE, MANY
    }

    @Getter
    @Setter
    public static class Answer {

        private String questionID;
        private String ans1;
        private String ans2;
        private String ans3;
        private String ans4;
        private Boolean[] corrects;

        public Answer() {
            corrects = new Boolean[4];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ans1).append('\n')
                    .append(ans2).append('\n')
                    .append(ans3).append('\n')
                    .append(ans4).append('\n')
                    .append(correctsToString()).append('\n');

            return sb.toString();
        }

        public String correctsToString() {
            return Arrays.toString(corrects)
                    .replaceAll("\\[|\\]", "");
        }
    }
}
