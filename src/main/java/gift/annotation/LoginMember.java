package gift.annotation;

import java.lang.annotation.*;

// 커스텀 어노테이션을 만드는 코드
/*
제작한 어노테이션이 어디에 붙을 수 있을지를 지정한다.
ElementType.PARAMETER은 매서드의 파라미터에만 붙일 수 있다고 가정한것
ex) public String login(@LoginMember Member member){}
 */
@Target(ElementType.PARAMETER)
/*
자바는 컴파일 되면 내부 구조를 더이상 볼 수 없는데
실행중에도 읽기 위한 것
 */
@Retention(RetentionPolicy.RUNTIME)

// LoginMember라는 이름을 가진 커스텀 어노션을 만들겠다.
public @interface LoginMember {

}