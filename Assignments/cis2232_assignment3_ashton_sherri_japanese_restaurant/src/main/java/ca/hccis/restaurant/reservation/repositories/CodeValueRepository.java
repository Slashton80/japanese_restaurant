package ca.hccis.restaurant.reservation.repositories;

import ca.hccis.restaurant.reservation.jpa.entity.CodeValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeValueRepository extends CrudRepository<CodeValue, Integer> {
        List<CodeValue> findByCodeTypeId(Integer codeTypeId);
}