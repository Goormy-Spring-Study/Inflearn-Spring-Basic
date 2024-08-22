package siyeonson.spring_basic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class BannedWord {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "banned_word_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String word;

    public BannedWord(String word) {
        this.word = word;
    }

}
