package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        // 세션 생성후 클라이언트에게 보내주기
        MockHttpServletResponse response = new MockHttpServletResponse();
        // response, request는 인터페이스임, 따라서 구현체가 필요함
        // 하지만 스프링을 사용할 경우 구현체를 서블릿이 만들어주지만, 지금처럼 테스트에서는 사용할 수 없음
        // 따라서 스프링이 Mock 객체를 제공함
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 클라이언트가 받은 쿠키를 요청 시에 전달하는 상황
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 클라이언트가 보내준 세션을 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();

    }


}
