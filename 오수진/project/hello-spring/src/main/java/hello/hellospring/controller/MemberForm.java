package hello.hellospring.controller;

public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    // 스프링이 setter를 통해 필드에 값을 넣어준다.
    public void setName(String name) {
        this.name = name;
    }
}
