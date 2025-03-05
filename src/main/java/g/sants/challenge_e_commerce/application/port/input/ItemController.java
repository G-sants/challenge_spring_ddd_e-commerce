package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.service.ItemService;
import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{id}/orders/{id}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    private KartService kartService;
    private UserService userService;

    @GetMapping
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById (@PathVariable Long id,@PathVariable Long kart_id,@PathVariable Long item_id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Kart kart = kartService.getKart(kart_id);
        if(kart != null) {
            return ResponseEntity.noContent().build();
        }
        Item item = itemService.getItem(item_id);
        if (item == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping("/{id}")
    public Item createItem(@RequestBody Item item){
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @PathVariable Long item_id, @PathVariable Long kart_id,@RequestBody Item itemDetails){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id,@PathVariable Long kart_id,@PathVariable Long item_id){
        itemService.deleteItem(id,kart_id,item_id);
        return ResponseEntity.noContent().build();
    }
}