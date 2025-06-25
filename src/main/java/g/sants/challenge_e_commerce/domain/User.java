package g.sants.challenge_e_commerce.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import g.sants.challenge_e_commerce.application.dto.UserDTORequest;
import g.sants.challenge_e_commerce.application.service.methods.UserCategory;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "\"user\"")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long customerID;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private UserCategory category;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Cart> carts = new ArrayList<>();

    public User() {}

    public User(Long customerID, String name,String lastName, String email, String password, UserCategory category){
        this.customerID = customerID;
        this.name = name;
        this.lastName= lastName;
        this.email = email;
        this.password = password;
        this.category = category;
    }

    public User(UserDTORequest user){
        this.customerID = user.customerID();
        this.name = user.name();;
        this.lastName = user.lastName();;
        this.email = user.email();
        this.category=UserCategory.ADMIN;
        this.password = user.password();
    }

    public User(long customerID, String name, String lastName, String email, String password) {
        this.customerID = customerID;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.category = UserCategory.ADMIN;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cart> getcart() {
        return carts;
    }

    public UserCategory getCategory() {
        return category;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.category == UserCategory.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}