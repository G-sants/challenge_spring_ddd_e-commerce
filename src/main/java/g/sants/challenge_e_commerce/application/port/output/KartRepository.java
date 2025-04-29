package g.sants.challenge_e_commerce.application.port.output;

import g.sants.challenge_e_commerce.domain.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KartRepository extends JpaRepository<Kart,Long> {
}
