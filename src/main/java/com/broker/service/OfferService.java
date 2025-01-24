package com.broker.service;

import com.broker.repo.entity.Offer;

import java.util.List;


public interface OfferService {

    List<Offer> getAllOffers();

    Offer getOfferById(Long id);

    Offer createOffer(Offer offer);

    Offer updateOffer(Long id, Offer updatedOffer);

    void deleteOffer(Long id);

    Offer approve(String offerId);

    Offer reject(String offerId);

    List<Offer> findByTofMali(String tofMali);
}

