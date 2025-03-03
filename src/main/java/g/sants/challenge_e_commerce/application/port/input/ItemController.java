package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.service.ItemService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    private UserService userService;

    @GetMapping("{id}")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("{id}/{item_id}")
    public ResponseEntity<Item> getItemById (@PathVariable Long id,@PathVariable Long item_id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Item item = itemService.getItem(item_id);
        if (item == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping("{id}/{item_id}")
    public Item createItem(@RequestBody Item item){
        return itemService.createItem(item);
    }

    @PutMapping("{id}/{item_id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @PathVariable Long item_id, @RequestBody Item itemDetails){
        return null;
    }
}