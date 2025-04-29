package g.sants.challenge_e_commerce.application.service;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTORequest;
import g.sants.challenge_e_commerce.application.dto.KartDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.ItemRepository;
import g.sants.challenge_e_commerce.application.port.output.KartRepository;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.port.output.UserRepository;
import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Kart;
import g.sants.challenge_e_commerce.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StorageRepository storageRepository;

    public List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

    public KartDTOResponse getKart(Long id) {
        Optional<Kart> kart = kartRepository.findById(id);
        return kart.map(KartDTOResponse::new)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }

    public Kart createKart(Long id,Kart kart) {
           Optional<User> user = userRepository.findById(id);
           if(user.isPresent()){

               kart.setUser(user.get());
               kart.setTotalPrice();
               kart.setTotalPriceDiscount();
               kart.setTotalDiscount();
               return kartRepository.save(kart);
           }
        return null;
    }
     
    public Kart updateKart(Long id, Long kart_id, KartDTORequest kartDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Kart> optionalKart = kartRepository.findById(kart_id);
                        if(optionalKart.isPresent()) {
                            Kart kart = optionalKart.get();
                            for (ItemDTORequest itemDTO : kartDetails.items()){
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

    public Kart deletedKart(Long id, Long kart_id, KartDTORequest kartDetails)  {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Kart> optionalKart = kartRepository.findById(kart_id);
                    if(optionalKart.isPresent()) {
                        Kart kart = optionalKart.get();
                        for (ItemDTORequest itemDTO : kartDetails.items()) {
                            Item itemRemove = kart.getItems().stream()
                                    .filter(item -> item.getItemName().equalsIgnoreCase(itemDTO.itemName()))
                                    .findFirst().orElse(null);
                            if (itemRemove != null) {
                                List<Item> items = kart.getItems();
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

    public Kart deleteKart(Long id, Long kart_id, KartDTORequest statusDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not find with id" + id));
            if (user != null) {
                try {
                    Optional<Kart> optionalKart = kartRepository.findById(kart_id);
                    if(optionalKart.isPresent()) {
                        Kart kart = optionalKart.get();
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
