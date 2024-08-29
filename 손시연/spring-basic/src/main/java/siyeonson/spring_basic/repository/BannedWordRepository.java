package siyeonson.spring_basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import siyeonson.spring_basic.domain.BannedWord;

import java.util.List;

public interface BannedWordRepository extends JpaRepository<BannedWord, Long> {

    @Query("SELECT bw.word FROM BannedWord bw WHERE bw.word IN :words")
    List<String> findBannedWordsInWordList(@Param("words") List<String> words);

}