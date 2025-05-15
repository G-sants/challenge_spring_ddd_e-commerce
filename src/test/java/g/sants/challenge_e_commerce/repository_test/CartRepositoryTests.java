package g.sants.challenge_e_commerce.repository_test;

import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CartRepositoryTests {

    @Autowired
    CartRepository cartRepository;

    @Test
    public void CartRepository_SavesCart(){
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        Assertions.assertNotNull(savedCart);
    }

    @Test
    public void CartRepository_FindAllCarts(){
        Cart cart1 = new Cart();
        Cart savedCart1 = cartRepository.save(cart1);

        Cart cart2 = new Cart();
        Cart savedCart2 = cartRepository.save(cart2);

        List<Cart> cartList = cartRepository.findAll();

        Assertions.assertNotNull(cartList);
        Assertions.assertEquals(2, cartList.size());
    }

    @Test
    public void CartRepository_FindCartById(){
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        Optional<Cart> cartID = cartRepository.findById(cart.getId());

        Assertions.assertEquals(savedCart.getId(), cartID.get().getId());
    }

    @Test
    public void CartRepository_AddsItemToCart(){
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        Storage nameItem = storageRepository.findByName("Potato");
        nameItem.setPrice(2.99);
        nameItem.setQuantity(50);
        storageRepository.save(nameItem);

        Assertions.assertEquals(2.99,nameItem.getPrice());
        Assertions.assertEquals(50,nameItem.getQuantity());
    }

    @Test
    public void CartRepository_CancelCart(){
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        Cart cartID = cartRepository.findById(savedCart.getId());

        Assertions.assertNull(storageRepository.findByName("Potato"));
    }
}
