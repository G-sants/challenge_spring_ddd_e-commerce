package g.sants.challenge_e_commerce.repository_test;

import g.sants.challenge_e_commerce.application.port.output.CartRepository;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Item;
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
    void CartRepository_SavesCart(){
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        Assertions.assertNotNull(savedCart);
    }

    @Test
    void CartRepository_FindAllCarts(){
        Cart cart1 = new Cart();
        Cart savedCart1 = cartRepository.save(cart1);

        Cart cart2 = new Cart();
        Cart savedCart2 = cartRepository.save(cart2);

        List<Cart> cartList = cartRepository.findAll();

        Assertions.assertNotNull(cartList);
        Assertions.assertEquals(2, cartList.size());
    }

    @Test
    void CartRepository_FindCartById(){
        Cart cart = new Cart();
        Cart savedCart = cartRepository.save(cart);

        Optional<Cart> cartID = cartRepository.findById(cart.getId());

        Assertions.assertEquals(savedCart.getId(), cartID.get().getId());
    }

    @Test
    void CartRepository_AddsItemToCart(){
        Cart cart = new Cart();
        Item item1 = new Item(0.99,"Potato",10);

        cart.addItem(item1);
        Cart savedCart = cartRepository.save(cart);
        String itemName = savedCart.getItems().get(0).getItemName();

        Assertions.assertNotNull(savedCart.getItems());
        Assertions.assertEquals("Potato",itemName);
        Assertions.assertEquals(1,savedCart.getItems().size());
    }

    @Test
    void CartRepository_CancelledStatusOnCart(){
        Cart cart = new Cart();

        cart.setStatus("CANCELLED");
        Cart cancelledCart = cartRepository.save(cart);

        Assertions.assertEquals("CANCELLED",cancelledCart.getStatus());
    }
}
