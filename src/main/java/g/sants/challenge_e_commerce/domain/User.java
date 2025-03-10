package g.sants.challenge_e_commerce.domain;

import g.sants.challenge_e_commerce.application.dto.UserDtoRequest;
import jakarta.persistence.*;

@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long customerID;
    private String name;
    private String lastName;
    private String email;
    private Kart shoppingkart;

    @OneToMany(mappedBy = "user")
    private Kart kart;

    @OneToMany(mappedBy = "user")
    private Item item;

    public User() {}

    public User(UserDtoRequest user){
        this.customerID = user.customerId();
        this.name = user.name();;
        this.lastName = user.lastName();;
        this.email = user.email();
    }

    public User(long id, long customerID, String name, String lastName) {
        this.id = id;
        this.customerID = customerID;
        this.name = name;
        this.lastName = lastName;
        this.shoppingkart = shoppingkart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Kart getKart() {
        return kart;
    }

    public void setKart(Kart kart) {
        this.kart = kart;
    }
}
