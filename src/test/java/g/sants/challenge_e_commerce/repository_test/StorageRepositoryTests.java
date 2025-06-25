package g.sants.challenge_e_commerce.repository_test;

import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.domain.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StorageRepositoryTests {

    @Autowired
    StorageRepository storageRepository;

    @Test
    void StorageRepository_SavesItem(){
        Storage item = new Storage("Potato",0.99,100);
        Storage savedItem = storageRepository.save(item);

        Assertions.assertNotNull(savedItem);
        Assertions.assertEquals("Potato",savedItem.getName());
    }

    @Test
    void StorageRepository_FindAllItems(){
        Storage item1 = new Storage("Potato",0.99,100);
        Storage savedItem1 = storageRepository.save(item1);

        Storage item2 = new Storage("Apple",1.99,100);
        Storage savedItem2 = storageRepository.save(item2);

        List<Storage> itemList = storageRepository.findAll();

        Assertions.assertNotNull(itemList);
        Assertions.assertEquals(2, itemList.size());
    }

    @Test
    void StorageRepository_FindItemByName(){
        Storage item1 = new Storage("Potato",0.99,100);
        Storage savedItem1 = storageRepository.save(item1);

        Storage nameItem = storageRepository.findByName("Potato");

        Assertions.assertEquals(savedItem1.getName(), nameItem.getName());
    }

    @Test
    void StorageRepository_UpdatesItem(){
        Storage item1 = new Storage("Potato",0.99,100);
        Storage savedItem1 = storageRepository.save(item1);

        Storage nameItem = storageRepository.findByName("Potato");
        nameItem.setPrice(2.99);
        nameItem.setQuantity(50);
        storageRepository.save(nameItem);

        Assertions.assertEquals(2.99,nameItem.getPrice());
        Assertions.assertEquals(50,nameItem.getQuantity());
    }

    @Test
    void StorageRepository_DeleteItem(){
        Storage item1 = new Storage("Potato",0.99,100);
        Storage savedItem1 = storageRepository.save(item1);

        storageRepository.deleteById(savedItem1.getId());

        Assertions.assertNull(storageRepository.findByName("Potato"));
    }
}
