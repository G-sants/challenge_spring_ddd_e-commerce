package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.port.output.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
}
