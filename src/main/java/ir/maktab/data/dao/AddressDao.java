package ir.maktab.data.dao;

import ir.maktab.data.model.order.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, Integer> {

    Address save(Address address);

}
