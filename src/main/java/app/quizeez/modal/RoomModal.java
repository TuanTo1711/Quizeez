package app.quizeez.modal;

import app.quizeez.entity.Question;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class RoomModal {

    List<Question> list;
    String roomID;
    String roomName;
    String roomPass;
    String[] participants;
    Integer numOfParticipants;

    public RoomModal() {
    }
}
