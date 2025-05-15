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

    @Autowired
    private StorageService storageService;

    @GetMapping
    public List<ItemDTOResponse> getAllItems(){
        return storageService.getAllItems();
    }

    @GetMapping("/{item_id}")
    public ResponseEntity<ItemDTOResponse> getItemById (@PathVariable Long item_id) {
        ItemDTOResponse item = storageService.getItem(item_id);
        if (item == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public Storage createItem(@RequestBody ItemDTORequest item){
        return storageService.createItem(item);
    }

    @PutMapping("/{item_id}")
    public ResponseEntity<Storage> updateItem( @PathVariable Long item_id,@RequestBody ItemDTORequest itemDetails){
        Storage updateItem =storageService.updateItem(item_id,itemDetails);
        if(updateItem ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateItem);
    }

    @DeleteMapping("/{item_id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long item_id){
        storageService.deleteItem(item_id);
        return ResponseEntity.noContent().build();
    }
}