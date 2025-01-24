package com.broker.repo.dao;

import com.broker.repo.entity.Offer;
import com.broker.repo.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {


    List<Offer> findAllByTofMali(Long aLong);
    List<Offer> findAllByTofMaliAndStatus(Long aLong , OfferStatus status);


}
