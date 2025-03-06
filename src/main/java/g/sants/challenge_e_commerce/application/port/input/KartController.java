package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{id}/orders/")
public class KartController {

    @Autowired
    KartService kartService;
    UserService userService;

    
}
