package g.sants.challenge_e_commerce.application.port.output;

import g.sants.challenge_e_commerce.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
}
