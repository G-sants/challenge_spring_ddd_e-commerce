package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.ItemDtoRequest;
import g.sants.challenge_e_commerce.application.service.ItemService;
import g.sants.challenge_e_commerce.application.service.KartService;
import g.sants.challenge_e_commerce.application.service.UserService;
import g.sants.challenge_e_commerce.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    private KartService kartService;
    private UserService userService;

    @GetMapping
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/{item_id}")
    public ResponseEntity<Item> getItemById (@PathVariable Long item_id) {
        Item item = itemService.getItem(item_id);
        if (item == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public Item createItem(@RequestBody ItemDtoRequest item){
        return itemService.createItem(item);
    }

    @PutMapping("/{item_id}")
    public ResponseEntity<Item> updateItem( @PathVariable Long item_id,@RequestBody ItemDtoRequest itemDetails){
        Item updateItem =itemService.updateItem(item_id,itemDetails);
        if(updateItem ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{item_id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long item_id){
        itemService.deleteItem(item_id);
        return ResponseEntity.noContent().build();
    }
}