package g.sants.challenge_e_commerce.domain;

import g.sants.challenge_e_commerce.application.dto.UserDtoRequest;
import g.sants.challenge_e_commerce.application.schemas.UserCategory;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "user")
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerID;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private UserCategory category;

    @OneToMany(mappedBy = "user")
    private List<Kart> karts = new ArrayList<>();

    public User() {}

    public User(Long customerID, String name,String lastName, String email, String password, UserCategory category){
        this.customerID = customerID;
        this.name = name;
        this.lastName= lastName;
        this.email = email;
        this.password = password;
        this.category = category;
    }

    public User(UserDtoRequest user){
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Kart> getKart() {
        return karts;
    }

    public void addKart(Kart kart) {
        karts.add(kart);
        kart.setUser (this);
    }

    public void removeKart(Kart kart) {
        karts.remove(kart);
        kart.setUser (null);
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
        return String.valueOf(customerID);
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
