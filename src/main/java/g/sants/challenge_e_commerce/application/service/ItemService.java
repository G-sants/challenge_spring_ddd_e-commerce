package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.port.output.ItemRepository;
import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    private KartRepository kartRepository;
    private UserRepository userRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not Found"));
    }

    public Item createItem(Item item) {
        
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Long kart_id, Long item_id, Item itemDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Kart> kart = kartRepository.findById(kart_id);
                    if(kart != null) {
                        try {
                            Item item = itemRepository.findById(item_id)
                                    .orElseThrow(() -> new RuntimeException("Item not found with id" + item_id));
                            if (item != null) {
                                item.setItemName(itemDetails.getItemName());
                                item.setPrice(itemDetails.getPrice());
                                item.setQuantity(itemDetails.getQuantity());
                                return itemRepository.save(item);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("Error updating item" + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                throw new RuntimeException("Error finding order" + e.getMessage());
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Error finding user" + e.getMessage());
        }
        return null;
    }

    public void deleteItem(Long id,Long kart_id, Long item_id) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not Found with this id: " + id)));
                Optional<Optional<Kart>> kart = Optional.ofNullable(Optional.ofNullable(kartRepository.findById(kart_id))
                    .orElseThrow(() -> new RuntimeException("Order not found with id" + kart_id)));
                         Optional<Optional<Item>> item = Optional.ofNullable(itemRepository.findById(item_id));
                        itemRepository.deleteById(item_id);
        }catch (Exception e) {
            throw new RuntimeException("Error deleting item" + e.getMessage());
        }
    }
}