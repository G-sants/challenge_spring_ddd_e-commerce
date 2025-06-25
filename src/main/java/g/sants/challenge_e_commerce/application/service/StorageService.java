package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTOResponse;
import g.sants.challenge_e_commerce.application.exceptions.errors.ItemNotFoundException;
import g.sants.challenge_e_commerce.application.exceptions.errors.WrongItemEntryException;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.domain.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StorageService {

    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository){
        this.storageRepository = storageRepository;
    }

    public List<ItemDTOResponse> getAllItems() {
        return storageRepository.findAll().stream()
                .map(ItemDTOResponse::new)
                .collect(Collectors.toList());
    }

    public ItemDTOResponse getItem(Long id) {
        Optional<Storage> item = storageRepository.findById(id);
        return item.map(ItemDTOResponse::new)
                .orElseThrow(ItemNotFoundException::new);
    }

    public Storage createItem(ItemDTORequest data) {
        Storage item = new Storage(data.itemName(),data.price(), data.quantity());
        storageRepository.save(item);
        return item;
    }

    public Storage updateItem(Long item_id, ItemDTORequest itemDetails) {
        Storage item = storageRepository.findById(item_id)
                    .orElseThrow(ItemNotFoundException::new);
        if (item != null) {
            item.setName(itemDetails.itemName());
            item.setPrice(itemDetails.price());
            item.setQuantity(itemDetails.quantity());
            return storageRepository.save(item);
        }
        throw new WrongItemEntryException();
    }


    public void deleteItem(Long item_id) {
        storageRepository.findById(item_id)
                .orElseThrow(ItemNotFoundException::new);
        storageRepository.deleteById(item_id);
    }

    public Storage findItemByName(String name){
        return storageRepository.findByName(name);
    }

    public void saveItemInStorage(Storage storage){
        storageRepository.save(storage);
    }
}