package siyeonson.spring_basic.controller;

import org.springframework.validation.annotation.Validated;
import siyeonson.spring_basic.aop.NotBannedWord;

@Validated
public class MemberForm {

    @NotBannedWord
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
