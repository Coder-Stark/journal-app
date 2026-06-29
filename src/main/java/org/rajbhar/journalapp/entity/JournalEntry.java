package org.rajbhar.journalapp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
//@Getter                       //now no requirement of getter and setter in function
//@Setter
@Data                         //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
public class JournalEntry {
    @Id
    private ObjectId id;

    private  String title;
    private  String content;
    private LocalDateTime date;
}
