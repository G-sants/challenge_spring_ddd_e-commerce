package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/items")
public class ItemController {

    @Autowired
    ItemService
}
