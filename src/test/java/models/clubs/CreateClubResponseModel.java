package models.clubs;
import java.util.List;
public record CreateClubResponseModel (String id, String bookTitle, String bookAuthors,
                                      int publicationYear, String description, String telegramChatLink,
                                      int owner, List<Integer> members, List<Integer> reviews,
                                      String created, String modified) {
}
