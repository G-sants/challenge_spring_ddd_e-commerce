package g.sants.challenge_e_commerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Storage {

    private List<Item> items;

    @OneToMany(mappedBy = "storage")
    private Item item;

    public Storage(){}

    public Storage(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
