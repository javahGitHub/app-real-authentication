package pdp.uz.apprealauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pdp.uz.apprealauthentication.entity.Product;

import java.util.UUID;

@RepositoryRestResource(path = "product")
public interface ProductRepo extends JpaRepository<Product, UUID> {
}
