package g.sants.challenge_e_commerce.services_test;

import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.application.port.output.StorageRepository;
import g.sants.challenge_e_commerce.application.service.CartService;
import g.sants.challenge_e_commerce.application.service.StorageService;
import g.sants.challenge_e_commerce.domain.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServicesTests {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }


}
