package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTOResponse;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.domain.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StorageServicesTests {

    @Mock
    private StorageRepository storageRepository;

    @InjectMocks
    private StorageService storageService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void storageService_FindAll_ReturnsAllItems(){
    Storage item1 = new Storage("Potato",0.99,100);

    when(storageRepository.save(any(Storage.class))).thenAnswer(arg -> {
        Storage savedItem1 = arg.getArgument(0);
        savedItem1.setId(1L);
        return savedItem1;
    });

    storageRepository.save(item1);

    Storage item2 = new Storage("Apple",2.99,100);

    when(storageRepository.save(any(Storage.class))).thenAnswer(arg -> {
        Storage savedItem2 = arg.getArgument(0);
        savedItem2.setId(2L);
        return savedItem2;
        });

    storageRepository.save(item2);

    when(storageRepository.findAll()).thenReturn(List.of(item1, item2));

    List<ItemDTOResponse> itemList = storageService.getAllItems();

        Assertions.assertNotNull(itemList);
        Assertions.assertEquals(2, itemList.size());
    }

    @Test
    public void storageService_FindById_ReturnsItemById(){
        Storage item1 = new Storage("Potato",0.99,100);

        when(storageRepository.save(any(Storage.class))).thenAnswer(arg -> {
            Storage savedItem = arg.getArgument(0);
            savedItem.setId(1L);
            return savedItem;
        });

        storageRepository.save(item1);

        when(storageRepository.findById(1L)).thenReturn(Optional.of(item1));

        ItemDTOResponse itemResponse = storageService.getItem(1L);

        Assertions.assertNotNull(item1);
        Assertions.assertEquals("Potato", itemResponse.name());
        Assertions.assertEquals(100, itemResponse.quantity());
        Assertions.assertEquals(0.99, itemResponse.price());
    }

    @Test
    public void storageService_UpdateItem_UpdatesItemInDataBase(){
        Storage item1 = new Storage("Potato",0.99,100);

        when(storageRepository.save(any(Storage.class))).thenAnswer(arg -> {
            Storage savedItem = arg.getArgument(0);
            savedItem.setId(1L);
            return savedItem;
        });

        storageRepository.save(item1);

        when(storageRepository.findById(1L)).thenReturn(Optional.of(item1));

        ItemDTORequest itemDTORequest = new ItemDTORequest("Potato",1.19,
                50);
        storageService.updateItem(1L,itemDTORequest);

        Assertions.assertEquals(1.19, storageRepository.findById(1L).get().getPrice());
        Assertions.assertEquals(50, storageRepository.findById(1L).get().getQuantity());
    }

    @Test
    public void userService_DeleteUser_ReturnsNull(){
        Storage item1 = new Storage("Potato",0.99,100);
        List<Storage> itemList = new ArrayList<>();

        when(storageRepository.save(any(Storage.class))).thenAnswer(arg -> {
            Storage savedItem = arg.getArgument(0);
            savedItem.setId(1L);
            itemList.add(savedItem);
            return savedItem;
        });

        when(storageRepository.findById(1L)).thenAnswer(arg ->
                itemList.stream().filter(u -> u.getId()==1L).findFirst());
        storageRepository.save(item1);

        storageRepository.deleteById(1L);

        when(storageRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertFalse(storageRepository.findById(1L).isPresent());
    }

}
