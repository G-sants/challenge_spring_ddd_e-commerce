package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.ItemRepository;
import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository kartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StorageRepository storageRepository;

    public List<Cart> getAllKarts() {
        return kartRepository.findAll();
    }

    public CartDTOResponse getKart(Long id) {
        Optional<Cart> kart = kartRepository.findById(id);
        return kart.map(CartDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public Cart createKart(Long id, Cart kart) {
        Optional<User> user = userRepository.findById(id);
        kart.setUser(user.get());
        kart.setTotalPrice();
        kart.setTotalPriceDiscount();
        kart.setTotalDiscount();
        return kartRepository.save(kart);
    }
     
    public Cart updateKart(Long id, Long kart_id, CartDTORequest kartDetails) {
        Optional<User> user = userRepository.findById(id);
        Optional<Cart> optionalKart = kartRepository.findById(kart_id);
        Cart kart = optionalKart.get();
        if(kart.getItems().isEmpty()){
            for (ItemDTORequest itemDTO : kartDetails.items()) {
                List<Item> items = kart.getItems();
                Iterator<Item> iterator = kart.getItems().iterator();
                while (iterator.hasNext()) {
                    Item item = iterator.next();
                    if (item.getItemName().equalsIgnoreCase(itemDTO.itemName())) {
                        item.setQuantity(item.getQuantity() + itemDTO.quantity());
                        kart.setTotalPrice();
                        kart.setTotalPriceDiscount();
                        kart.setTotalDiscount();
                        kartRepository.save(kart);
                    } else {
                        Item newItem = new Item();
                        newItem.setItemName(itemDTO.itemName());
                        newItem.setPrice(itemDTO.price());
                        newItem.setQuantity(itemDTO.quantity());
                        kart.addItem(newItem);
                    }
                }
            }return kart;
        }else {
            List<Item> newItemList = new ArrayList<>();
            kart.setItems(newItemList);
            for (ItemDTORequest itemDTO : kartDetails.items()) {
                Item newItem = new Item();
                newItem.setItemName(itemDTO.itemName());
                newItem.setPrice(itemDTO.price());
                newItem.setQuantity(itemDTO.quantity());
                kart.addItem(newItem);
            }
            kart.setTotalPrice();
            kart.setTotalPriceDiscount();
            kart.setTotalDiscount();
            return kartRepository.save(kart);
        }
    }

    public Cart deletedKart(Long id, Long kart_id, CartDTORequest kartDetails)  {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Cart> optionalKart = kartRepository.findById(kart_id);
                    if(optionalKart.isPresent()) {
                        Cart kart = optionalKart.get();
                        for (ItemDTORequest itemDTO : kartDetails.items()) {
                            Item itemRemove = kart.getItems().stream()
                                    .filter(item -> item.getItemName().equalsIgnoreCase(itemDTO.itemName()))
                                    .findFirst().orElse(null);
                            if (itemRemove != null) {
                                Iterator<Item> iterator = kart.getItems().iterator();
                                while (iterator.hasNext()) {
                                    Item item = iterator.next();
                                    if (item.getItemName().equalsIgnoreCase(itemRemove.getItemName())) {
                                        if (item.getQuantity() > 1) {
                                            item.setQuantity(item.getQuantity() - 1);
                                            kartRepository.save(kart);
                                        } else {
                                            iterator.remove();
                                            itemRepository.delete(item);
                                        }
                                    }
                                }
                            } else {
                                throw new RuntimeException("Item not found: " + itemDTO.itemName());
                            }
                        }
                        kart.setTotalPrice();
                        kart.setTotalPriceDiscount();
                        kart.setTotalDiscount();
                        return kartRepository.save(kart);
                    }else {
                        throw new RuntimeException("Order not Found within User" + id);
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

    public Cart deleteKart(Long id, Long kart_id, CartDTORequest statusDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Cart> optionalKart = kartRepository.findById(kart_id);
                    if(optionalKart.isPresent()) {
                        Cart kart = optionalKart.get();
                        String checkStatus = statusDetails.status();
                        if(checkStatus.equalsIgnoreCase("cancel")) {
                           kart.setStatus("CANCELLED");
                           return kartRepository.save(kart);
                        }
                    }else {
                        throw new RuntimeException("Order not Found within User" + id);
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

}
