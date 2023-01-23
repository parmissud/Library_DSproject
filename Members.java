import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Members {

class Member{
    String name;
    Book[] borrowedBooks;
    boolean isInLib;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime[] arrives;
    LocalDateTime[] leaves;

}
}
