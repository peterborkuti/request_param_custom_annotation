package hu.bp.rqheader;

import hu.bp.rqheader.annotations.RequestHeaderEmail;
import hu.bp.rqheader.annotations.RequestHeaderEmailToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class RqController {
    @GetMapping("/email")
    String displayEmailHeader(@RequestHeaderEmail String email) {
        return email;
    }

    @GetMapping("/token")
    String displayEmailTokenHeader(@RequestHeaderEmailToken String token) {
        return token;
    }

    @GetMapping("/email-and-token")
    String displayEmailAndEmailTokenHeader(
            @RequestHeaderEmail String email,
            @RequestHeaderEmailToken String token) {
        return email + token;
    }
}
