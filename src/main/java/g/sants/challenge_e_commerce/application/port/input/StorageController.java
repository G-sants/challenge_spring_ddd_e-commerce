package g.sants.challenge_e_commerce.application.port.input;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTOResponse;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.domain.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class StorageController {

    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService){
        this.storageService = storageService;
    }

    @GetMapping
    public List<ItemDTOResponse> getAllItems(){
        return storageService.getAllItems();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDTOResponse> getItemById (@PathVariable Long itemId) {
        ItemDTOResponse item = storageService.getItem(itemId);
        if (item == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Storage> createItem(@RequestBody ItemDTORequest item) {
        storageService.createItem(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Storage> updateItem(@PathVariable Long itemId, @RequestBody ItemDTORequest itemDetails){
        Storage updateItem =storageService.updateItem(itemId,itemDetails);
        if(updateItem ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId){
        storageService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}