package siyeonson.spring_basic.aop;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import siyeonson.spring_basic.repository.BannedWordRepository;

import java.util.List;

@RequiredArgsConstructor
public class BannedWordValidator implements ConstraintValidator<NotBannedWord, String> {

    private final BannedWordRepository bannedWordRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        var contentWords = List.of(value.split(" "));
        var bannedWord = bannedWordRepository.findBannedWordsInWordList(contentWords);

        if (!bannedWord.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Cannot use banned word : " + bannedWord.get(0))
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

}
