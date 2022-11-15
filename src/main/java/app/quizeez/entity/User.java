package app.quizeez.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    Integer ID;
    String username;
    String password;
    String email;
    String fullname;
    String status;
}
