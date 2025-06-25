package g.sants.challenge_e_commerce.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import g.sants.challenge_e_commerce.application.dto.CartDTORequest;
    import g.sants.challenge_e_commerce.application.dto.CartDTOResponse;
    import g.sants.challenge_e_commerce.application.dto.ItemDTORequest;
    import g.sants.challenge_e_commerce.application.exceptions.errors.ItemNotFoundException;
    import g.sants.challenge_e_commerce.application.service.methods.CartOperations;
    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Iterator;
    import java.util.List;
    import java.util.stream.Collectors;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @Entity
    public class Cart {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(nullable = false)
        private double totalPrice;
        @Column(nullable = false)
        private double totalPriceDiscount;
        @Column(nullable = false)
        private double totalDiscount;
        @Column(nullable = false)
        private String status;
        @Column(nullable = false)
        public String date;
        private static HashMap<Long, Item> usercart;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;


        @JsonIgnore
        @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private List<Item> items = new ArrayList<>();

        public Cart() {
            this.totalPrice = 0.0;
            this.totalPriceDiscount = 0.0;
            this.totalDiscount = 0.0;
            this.status = CartOperations.status();
            this.date = CartOperations.dateCreation();
        }

        public static Cart dtoCreateCart(CartDTOResponse cartResponse){
            Cart newcart = new Cart();
            newcart.items = cartResponse.items().stream().map(Item::new)
                    .collect(Collectors.toList());
            newcart.date = cartResponse.date();
            newcart.status = cartResponse.status();
            return newcart;
        }

        public void setId(long id) {
            this.id = id;
        }

        public double getTotalPrice() {
            return CartOperations.totalPrice(items);
        }

        public double getTotalPriceDiscount() {
            return CartOperations
                    .totalPriceDiscount(CartOperations.totalPrice(items));
        }

        public double getTotalDiscount() {
            return CartOperations.totalDiscount(totalPrice);
        }

        public List<Item> getUsercart(long id) {
            return items;
        }

        public void setTotalPrice() {

            this.totalPrice = CartOperations.totalPrice(items);
        }

        public void setTotalPriceDiscount() {
            this.totalPriceDiscount = CartOperations.totalPriceDiscount(getTotalPrice());
        }

        public void setTotalDiscount() {
            this.totalDiscount = CartOperations.totalDiscount(getTotalPrice());
        }

        public long getId() {
            return id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public User getUser () {
            return user;
        }

        public void setUser (User user) {
            this.user = user;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }

        public void addItem(Item item) {
            items.add(item);
        }

        public void removeItem(Item item) {
            items.remove(item);
        }

        public static void updateCartItems(Cart cart, CartDTORequest cartDetails) {
            if (!cart.getItems().isEmpty()) {
                for (ItemDTORequest itemDTO : cartDetails.items()) {
                    updateExistingItem(cart, itemDTO);
                }
            } else {
                initializeCartItems(cart, cartDetails);
            }
        }

        private static void updateExistingItem(Cart cart, ItemDTORequest itemDTO) {
            for (Item item : cart.getItems()) {
                if (item.getItemName().equalsIgnoreCase(itemDTO.itemName())) {
                    item.setQuantity(item.getQuantity() + itemDTO.quantity());
                    updateCartTotals(cart);
                    return;
                }
            }
            addNewItem(cart, itemDTO);
        }

        private static void addNewItem(Cart cart, ItemDTORequest itemDTO) {
            Item newItem = new Item();
            newItem.setItemName(itemDTO.itemName());
            newItem.setPrice(itemDTO.price());
            newItem.setQuantity(itemDTO.quantity());
            cart.addItem(newItem);
        }

        private static void initializeCartItems(Cart cart, CartDTORequest cartDetails) {
            List<Item> newItemList = new ArrayList<>();
            cart.setItems(newItemList);
            for (ItemDTORequest itemDTO : cartDetails.items()) {
                addNewItem(cart, itemDTO);
            }
            updateCartTotals(cart);
        }

        public static void updateCartTotals(Cart cart) {
            cart.setTotalPrice();
            cart.setTotalPriceDiscount();
            cart.setTotalDiscount();
        }

        public static void processItemsInCart(Cart cart, CartDTORequest cartDetails) {
            for (ItemDTORequest itemDTO : cartDetails.items()) {
                removeItemFromCart(cart, itemDTO);
            }
        }
        private static void removeItemFromCart(Cart cart, ItemDTORequest itemDTO) {
            Item itemToRemove = findItemInCart(cart, itemDTO);
            if (itemToRemove != null) {
                updateItemQuantity(cart, itemToRemove);
            } else {
                throw new ItemNotFoundException("Item not found: " + itemDTO.itemName());
            }
        }

        private static Item findItemInCart(Cart cart, ItemDTORequest itemDTO) {
            return cart.getItems().stream()
                    .filter(item -> item.getItemName().equalsIgnoreCase(itemDTO.itemName()))
                    .findFirst()
                    .orElse(null);
        }

        private static void updateItemQuantity(Cart cart, Item itemToRemove) {
            Iterator<Item> iterator = cart.getItems().iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (item.getItemName().equalsIgnoreCase(itemToRemove.getItemName())) {
                    if (item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1);
                    } else {
                        iterator.remove();
                    }
                    break;
                }
            }
        }
    }
