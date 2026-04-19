package journalApp.Entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entry")
@Data //Collective for @Getter and @Setter
@NoArgsConstructor
public class JournalEntries {
    @Id
    private String id;
    @NonNull
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime date;

    public JournalEntries(LocalDateTime date, String content, String title, String id) {
        this.date = date;
        this.content = content;
        this.title = title;
        this.id = id;
    }
}
