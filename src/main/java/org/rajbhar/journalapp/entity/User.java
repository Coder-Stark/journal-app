package org.rajbhar.journalapp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
//@Getter                       //now no requirement of getter and setter in function
//@Setter
@Data                         //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private  String userName;
    @NonNull
    private  String password;
    @DBRef                                             //for reference (connect user to journalEntries)
    private List<JournalEntry> journalEntries = new ArrayList<>();
}
