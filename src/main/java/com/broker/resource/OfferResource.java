package com.broker.resource;

import com.broker.repo.entity.Offer;
import com.broker.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
@Slf4j
public class OfferResource {

    private final OfferService offerService;

    @Autowired
    public OfferResource(OfferService offerService) {
        this.offerService = offerService;
    }

    // Get all offers
    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    // Get offer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Offer offer = offerService.getOfferById(id);
        return ResponseEntity.ok(offer);
    }

    @PostMapping(path = "/approved/{offerId}")
    public ResponseEntity<Offer> approve(@PathVariable("offerId") String offerId) {
        Offer createdOffer = offerService.approve(offerId);
        return ResponseEntity.ok(createdOffer);
    }

    @PostMapping(path = "/rejected/{offerId}")
    public ResponseEntity<Offer> reject(@PathVariable("offerId") String offerId) {
        Offer createdOffer = offerService.reject(offerId);
        return ResponseEntity.ok(createdOffer);
    }

    // Create a new offer
    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {



        Offer createdOffer = offerService.createOffer(offer);
        return ResponseEntity.ok(createdOffer);
    }

    // Update an offer
    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offer) {
        Offer updatedOffer = offerService.updateOffer(id, offer);
        return ResponseEntity.ok(updatedOffer);
    }

    // Delete an offer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/tofMali/{tofMali}")
    public ResponseEntity<List<Offer>> findByTofMali(@PathVariable String tofMali) {
        List<Offer> byTofMali = offerService.findByTofMali(tofMali);
        return ResponseEntity.ok(byTofMali);
    }


}
