package com.example.practiceShop.test;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller // RestController는 ViewResolver를 거치지 않음
@RequestMapping("/hello")
@RequiredArgsConstructor
@Slf4j
public class HelloController {
    private final HelloService helloService;
    private final MessageSource messageSource;


    /**
     * 전체 리스트 표시
     */
    @GetMapping("")
    public String list(Model model) {
        // 엔티티가 변화하면 템플릿 스펙이 변화할 수 있기 때문에
        // 엔티티 객체를 그대로 사용하는 것보다 DTO를 반환해서 넘겨주도록 해야 함.
        List<HelloEntity> hellos = helloService.findHellos();
        List<HelloResponse> helloResponses
                = hellos.stream().map(HelloResponse::createHelloResponse)
                        .toList();

        model.addAttribute("helloResponses", helloResponses);
        return "/hello/helloList";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("helloForm", new HelloForm());

        return "/hello/createHelloForm";
    }

    @PostMapping("/new")
    // 원래 Valid에서 에러가 발생하면 Controller 코드가 실행안되고 에러페이지를 요청하는데,
    // BindingResult 가 파라미터에 있으면 코드가 그대로 실행됨 (에러도 코드 내에서 처리하겠다는 의미)
    public String create(@Valid HelloForm helloForm, BindingResult result) {
        if (result.hasErrors()) {
            log.info("errors: {}", result);
            for(FieldError fieldError: result.getFieldErrors()) {
                if(fieldError.getCode().contains("typeMismatch")) {
                    String typeMismatch = messageSource.getMessage("typeMismatch", null, LocaleContextHolder.getLocale());
                    log.info("typeMismatch Properties Msg : {}", typeMismatch);
                    log.info("typeMismatch Default Msg : {}", fieldError.getDefaultMessage());
                }
            }

            return "/hello/createHelloForm";
        }

        HelloEntity hello = HelloEntity.createHello(helloForm.getAge(), helloForm.getName());
        helloService.save(hello);
        return "redirect:/hello";
    }
}
