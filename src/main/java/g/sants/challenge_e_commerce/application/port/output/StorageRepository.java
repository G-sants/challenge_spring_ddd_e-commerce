package g.sants.challenge_e_commerce.application.port.output;

import g.sants.challenge_e_commerce.domain.Item;
import g.sants.challenge_e_commerce.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage,Long> {
    Storage findByName(String name);
}
