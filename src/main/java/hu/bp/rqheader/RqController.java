package hu.bp.rqheader;

import hu.bp.rqheader.annotations.RequestHeaderEmail;
import org.springframework.web.bind.annotation.*;

@RestController
public class RqController {
    @GetMapping
    String displayEmailHeader(@RequestHeaderEmail String email) {
        return email;
    }
}
