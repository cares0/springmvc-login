package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        // ModelAttribute는 뷰 템플릿에서 th:object를 사용하기 위함임
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) { // 로그인 실패는 특정 필드의 오류가 아니라 글로벌 오류
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            // 글로벌 오류는 객체 단위로 오류를 검증할 수가 없음 repository에서 데이터를 조회하는 행동까지 연관됨 -> @ScriptAssert의 한계점임
            return "login/loginForm";
        }

        // 로그인 성공 처리 TODO


        return "redirect:/";
    }
}
