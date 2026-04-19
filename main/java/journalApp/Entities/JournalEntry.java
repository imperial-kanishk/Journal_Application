package journalApp.Entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entry")
@Data //Collective for @Getter and @Setter
@NoArgsConstructor
public class JournalEntry {
    @Id
    private String id;
    @NonNull
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private LocalDateTime date;

    public JournalEntry(LocalDateTime date, String content, String title, String id) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.id = id;
    }
}
