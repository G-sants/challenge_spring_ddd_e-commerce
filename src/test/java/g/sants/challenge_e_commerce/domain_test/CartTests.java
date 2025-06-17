package g.sants.challenge_e_commerce.domain_test;

import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
import g.sants.challenge_e_commerce.application.exceptions.errors.ItemNotFoundException;
import g.sants.challenge_e_commerce.domain.Cart;
import g.sants.challenge_e_commerce.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartTests {

    private Item item;
    private ItemDTORequest newItem,removeItem,notItem;
    private ArrayList<ItemDTORequest> addItemList,removeItemList,notItemList;
    private Cart cart;
    private CartDTORequest cartDTOAdd,cartDTORemove,cartDTOnot;

    @BeforeEach
    void setup(){
        item = new Item(1.00,"Apple",3);
        newItem = new ItemDTORequest("Apple", 1.00, 5);
        removeItem = new ItemDTORequest("Apple", 1.00, 1);
        notItem = new ItemDTORequest("Banana", 1.10, 1);

        addItemList = new ArrayList<>();
        addItemList.add(newItem);
        removeItemList = new ArrayList<>();
        removeItemList.add(removeItem);
        notItemList = new ArrayList<>();
        notItemList.add(notItem);

        cart = new Cart();
        cartDTOAdd = new CartDTORequest(addItemList,"PENDING");
        cartDTORemove = new CartDTORequest(removeItemList,"PENDING");
        cartDTOnot = new CartDTORequest(notItemList,"PENDING");
    }

    @Test
    void UpdateCartItems_AddsItemCorrectly(){
        Cart.updateCartItems(cart, cartDTOAdd);

        assertEquals(1, cart.getItems().size());
        assertEquals("Apple", cart.getItems().get(0).getItemName());
        assertEquals(5, cart.getItems().get(0).getQuantity());
    }

    @Test
    void UpdateCartItem_UpdateItem(){
        cart.addItem(item);
        Cart.updateCartItems(cart, cartDTOAdd);

        assertEquals(1, cart.getItems().size());
        assertEquals("Apple", cart.getItems().get(0).getItemName());
        assertEquals(8, cart.getItems().get(0).getQuantity());
    }

    @Test
    void RemovesItem_RemovesItemFromCart(){
        cart.addItem(item);
        Cart.processItemsInCart(cart, cartDTORemove);

        assertEquals(1, cart.getItems().size());
        assertEquals("Apple", cart.getItems().get(0).getItemName());
        assertEquals(2, cart.getItems().get(0).getQuantity());
    }

    @Test
    void RemovesItem_ThrowsException_WhenItemNotFound(){
        cart.addItem(item);

        assertThrows(ItemNotFoundException.class,()-> {Cart.
                processItemsInCart(cart,cartDTOnot);});

    }
}