package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private KartRepository kartRepository;
    @Autowired
    private UserRepository userRepository;

    public List<ItemDTOResponse> getAllItems() {
        List<ItemDTOResponse>  itemList = storageRepository.findAll().stream()
                .map(ItemDTOResponse::new)
                .collect(Collectors.toList());
        return itemList;
    }

    public ItemDTOResponse getItem(Long id) {
        Optional<Storage> item = storageRepository.findById(id);
        return item.map(ItemDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Item not Found"));
    }

    public Storage createItem(ItemDTORequest data) {
        Storage item = new Storage(data.itemName(),data.price(), data.quantity());
        return storageRepository.save(item);
    }

    public Storage updateItem(Long item_id, ItemDTORequest itemDetails) {
        try {
            Storage item = storageRepository.findById(item_id)
                    .orElseThrow(() -> new RuntimeException("Item not found with id" + item_id));
            if (item != null) {
                item.setName(itemDetails.itemName());
                item.setPrice(itemDetails.price());
                item.setQuantity(itemDetails.quantity());
                return storageRepository.save(item);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating item" + e.getMessage());
        }
        return null;
    }


    public void deleteItem(Long item_id) {
        try {
            Optional<Optional<Storage>> item = Optional.ofNullable(storageRepository.findById(item_id));
            storageRepository.deleteById(item_id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting item" + e.getMessage());
        }
    }

    public Storage findItemByName(String name){
       return storageRepository.findByName(name);
    }

    public Storage saveItemInStorage(Storage storage){
        return storageRepository.save(storage);
    }
}