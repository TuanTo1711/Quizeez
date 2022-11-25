package app.quizeez.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Entity {

    private Integer ID;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private Boolean gender;
    private String status;

    @Override
    public Object[] toInsertData() {
        return new Object[]{
            username, password, email, fullname, status
        };
    }

    @Override
    public Object[] toUpdateData() {
        return new Object[]{
            username, password, email, fullname, gender, ID
        };
    }

    @Override
    public Object[] toDeleteData() {
        return new Object[]{
            ID, username
        };
    }

}
