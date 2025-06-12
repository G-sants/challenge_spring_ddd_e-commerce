package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


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

    }

    @Test
    public void storageService_FindById_ReturnsItemById(){

    }

    @Test
    public void storageService_UpdateItem_UpdatesItemInDataBase(){

    }

    @Test
    public void userService_DeleteUser_ReturnsNull(){

    }

}