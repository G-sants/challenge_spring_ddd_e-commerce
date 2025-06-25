package g.sants.challenge_e_commerce.application.port.output;

import g.sants.challenge_e_commerce.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
