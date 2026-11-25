package org.springframework.samples.petclinic.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactForwardController {

    @GetMapping("{path:^(?!api|resources|webjars|static|public|css|js|images|uploads|swagger|v3|oups|h2-console)[^\\.]*}/**")
    public String handleForward() {
        return "forward:/";
    }

}