## 1. 프로젝트 환경 설정
### 스프링 부트 라이브러리
- spring-boot-starter-web
	- spring-boot-starter-tomcat: 톰캣 (웹서버)
	- spring-webmvc: 스프링 웹 MVC
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
	- spring-boot
	- spring-core
	- spring-boot-starter-logging
		- logback, slf4j
### 테스트 라이브러리
- spring-boot-starter-test
	- junit: 테스트 프레임워크  
	- mockito: 목 라이브러리  
	- assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
	- spring-test: 스프링 통합 테스트 지원

---

## 2. Spring 웹 개발
### 1. 정적 컨텐츠
- 원하는 파일을 그대로 반환

### 2. 템플릿 엔진 동작 원리
- HelloController
- 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버( `viewResolver` )가 화면을 찾아서 처리한다.
	- 스프링 부트 템플릿 엔진 기본 viewName 매핑
	- `resources:templates/` +{ViewName}+ `.html`
```java
@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}
```

- hello-html
```html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Hello</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p th:text="'안녕하세요. ' + ${data}" >안녕하세요. 손님</p>
</body>
</html>
```

- 결과
![](./images/image1.png)

- 동작 원리
![](./images/image2.png)

### 3. API
- HelloController
```java
@Controller
public class HelloController {
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
```

- 결과
![](./images/image3.png)

![](./images/image4.png)

- 동작 원리
![](./images/image5.png)

- @ResponseBody
	- HTTP의 BODY에 문자 내용을 직접 반환
	- `viewResolver` 대신에 `HttpMessageConverter` 가 동작
	- byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
	- 기본 문자처리: `StringHttpMessageConverter`
	- 기본 객체처리: `MappingJackson2HttpMessageConverter`

---

## 3. 스프링 빈과 의존관계
### DI
- Dependency Injection
- 의존성 주입
- 객체 의존관계를 외부에서 넣어주는 것
- DI 3가지 방법: 필드 주입, setter 주입, 생성자 주입
  의존관계가 실행중에 동적으 로 변하는 경우는 거의 없으므로 생성자 주입을 권장한다.

### 스프링 빈을 등록하는 2가지 방법
#### 1. 컴포넌트 스캔과 자동 의존관계 설정
- `@Component` 애노테이션이 있으면 스프링 빈으로 자동 등록된다.
- 생성자에 `@Autowired` 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다.
	- 생성자가 1개만 있으면 `@Autowired` 는 생략할 수 있다.
	- `@Autowired`를 통한 DI는 스프링 빈으로 등록했을 때만 동작한다.
- 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다.
  따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.
- 컴포넌트 스캔의 범위: `@SpringBootApplication`이 위치한 클래스부터 하위까지 검색한다.

#### 2. 자바 코드로 직접 스프링 빈 등록하기
- 별도의 Config 파일을 생성하여 스프링 빈을 관리한다.
- 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용한다.
- 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록한다.

---

## 4. 스프링 DB 접근 기술
### 1. 순수 JDBC
- MemoryMemberRepository -> JdbcMemberRepository 로 변경
- 개방-폐쇄 원칙(OCP, Open-Closed Principle)
	- 확장에는 열려있고, 수정, 변경에는 닫혀있다.
	- 인터페이스에서 구현체를 바꿀 때 기존 코드 수정 없이 변경할 수 있다.
- 스프링의 DI (Dependencies Injection)을 사용하면 **기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경**할 수 있다.
![](./images/image6.png)

- 예: `findById()`
```java
@Override
public Optional<Member> findById(Long id) {
	String sql = "select * from member where id = ?";
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		conn = getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, id);
		rs = pstmt.executeQuery();
		if(rs.next()) {
			Member member = new Member();
			member.setId(rs.getLong("id"));
			member.setName(rs.getString("name"));
			return Optional.of(member);
		} else {
			return Optional.empty();
		}
	}
	catch (Exception e) {
		throw new IllegalStateException(e);
	}
	finally {
		close(conn, pstmt, rs);
	}
}

private Connection getConnection() {
	return DataSourceUtils.getConnection(dataSource);
}

private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
	try {
		if (rs != null) {
			rs.close();
		}
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		if (pstmt != null) {
			pstmt.close();
		}
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		if (conn != null) {
			close(conn);
		}
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
}

private void close(Connection conn) throws SQLException {
	DataSourceUtils.releaseConnection(conn, dataSource);
}
```

#### 테스트
- `@SpringBootTest` : 스프링 컨테이너와 테스트를 함께 실행한다.
- `@Transactional` : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.

### 2. 스프링 JdbcTemplate
- 스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다.
- 예: `findById()`
```java
@Override
public Optional<Member> findById(Long id) {
	List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
	return result.stream().findAny();
}

private RowMapper<Member> memberRowMapper() {
	return (rs, rowNum) -> {
		Member member = new Member();
		member.setId(rs.getLong("id"));
		member.setName(rs.getString("name"));
		return member;
	};
}
```

### 3. JPA
- EntityManager 사용
- 예: `findById()`
```java
@Override
public Optional<Member> findById(Long id) {
	Member member = em.find(Member.class, id);
	return Optional.ofNullable(member);
}
```

### 4. Spring Data JPA
- 예: `findById()`
```java
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {  
}
```

---

## 5. AOP
- 관점 지향 프로그래밍(Aspect Oriented Programming)
    - 공통 관심 사항(Cross Cutting Concern)과 핵심 관리 사항(Core Concern)을 분리하는 것
    - 특정 로직(로그인, 성능 테스트 등)을 모든 메소드에 적용하고 싶을 때, 모든 메소드에 일일이 로직을 추가하는 것이 아니라, 로직을 만들어서 모든 메소드에 적용한다.
    - 공통 관심 사항을 수행해 중복 코드를 줄이는 것
- 스프링 AOP는 프록시 패턴을 기반으로 동작한다.
	- 클라이언트가 객체를 사용할 때, 스프링 컨테이너는 대상 객체 대신 프록시 객체를 제공한다. 메소드 호출 전후에 추가적인 기능을 수행할 수 있기 때문이다.
	- AspectJ 표현식을 사용하여 어떤 메소드에 어드바이스(추가 기능)를 적용할지 결정한다. 프록시 객체는 실제 객체의 메소드를 호출하는 동시에, AOP가 적용된 특정 포인트에서 사용자 정의 코드를 실행한다.
	- 이 방식을 통해 개발자는 비즈니스 로직과 공통 기능을 분리하여 관리할 수 있다.