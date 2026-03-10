package models.Update;

import java.util.List;

public record SuccessfulUpdateUserModel (int id, String username, String firstName, String lastName, String email, String remoteAddr){
}
