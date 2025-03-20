package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTOResponse;
import g.sants.challenge_e_commerce.application.dto.UserDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.ItemRepository;
import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    private KartRepository kartRepository;
    private UserRepository userRepository;

    public List<ItemDTOResponse> getAllItems() {
        List<ItemDTOResponse>  itemList = itemRepository.findAll().stream()
                .map(ItemDTOResponse::new)
                .collect(Collectors.toList());
        return itemList;
    }

    public ItemDTOResponse getItem(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(ItemDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Item not Found"));
    }

    public Item createItem(ItemDTORequest data) {
        Item item = new Item(data.price(),data.name(), data.quantity());
        return itemRepository.save(item);
    }

    public Item updateItem(Long item_id, ItemDTORequest itemDetails) {
        try {
            Item item = itemRepository.findById(item_id)
                    .orElseThrow(() -> new RuntimeException("Item not found with id" + item_id));
            if (item != null) {
                item.setItemName(itemDetails.name());
                item.setPrice(itemDetails.price());
                return itemRepository.save(item);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating item" + e.getMessage());
        }
        return null;
    }


    public void deleteItem(Long item_id) {
        try {
            Optional<Optional<Item>> item = Optional.ofNullable(itemRepository.findById(item_id));
            itemRepository.deleteById(item_id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting item" + e.getMessage());
        }
    }
}