package g.sants.challenge_e_commerce.application.port.output;

import g.sants.challenge_e_commerce.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage,Long> {
}
