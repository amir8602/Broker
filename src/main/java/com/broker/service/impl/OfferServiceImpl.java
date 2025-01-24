package com.broker.service.impl;

import com.broker.repo.dao.OfferRepository;
import com.broker.repo.entity.Offer;
import com.broker.repo.entity.Order;
import com.broker.repo.enums.OfferStatus;
import com.broker.repo.enums.OrderAction;
import com.broker.repo.enums.OrderStatus;
import com.broker.service.OfferService;
import com.broker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private final OrderService orderService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, OrderService orderService) {
        this.offerRepository = offerRepository;
        this.orderService = orderService;
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer with id " + id + " not found"));
    }

    @Override
    public Offer createOffer(Offer offer) {
        Optional<Order> orderById = orderService.getOrderById(offer.getTofMali());
        offer.setOrder(orderById.get());
        if (orderById.get().getAction().equals(OrderAction.BUY)) {
            offer.setAction(OrderAction.SELL);
        } else {
            offer.setAction(OrderAction.BUY);
        }
        Long value = (long) (offer.getAmount() * offer.getQuantity());
        offer.setTotalAmount(value);
        return offerRepository.save(offer);
    }

    @Override
    public Offer updateOffer(Long id, Offer updatedOffer) {


        Offer existingOffer = getOfferById(id); // Check if the offer exists
        // Update the fields of the existing offer
//        existingOffer.setId(updatedOffer.getField1()); // Replace `setField1` with actual field setters
//        existingOffer.setField2(updatedOffer.getField2()); // Replace `setField2` with actual field setters
        // Add other fields as needed
        return offerRepository.save(existingOffer);
    }

    @Override
    public void deleteOffer(Long id) {
        Offer offer = getOfferById(id); // Check if the offer exists
        offerRepository.delete(offer);
    }

    @Override
    public Offer approve(String offerId) {
        Offer offer = offerRepository.findById(Long.valueOf(offerId)).get();
        offer.setStatus(OfferStatus.Accepted);
        offerRepository.save(offer);
        Order order = orderService.getOrderById(offer.getTofMali()).get();
        List<Offer> allByTofMali = offerRepository.findAllByTofMaliAndStatus(order.getId(), OfferStatus.Pending);
        for (Offer offer1 : allByTofMali) {
            offer1.setStatus(OfferStatus.Rejected);
        }
        offerRepository.saveAll(allByTofMali);
        order.setStatus(OrderStatus.COMPLETED);
        orderService.update(order);
        if (order.getQuantity() > offer.getQuantity()) {
            Long l = (long) (order.getQuantity() - offer.getQuantity());
            Order order1 = new Order();
            order1.setQuantity(Math.toIntExact(l));
            order1.setAmount(order.getAmount());
            order1.setUserId(order.getUserId());
            order1.setStatus(OrderStatus.ACTIVE);
            order1.setAction(order.getAction());
            order1.setType(order.getType());
            order1.setTotalAmount(l * order.getAmount());
            orderService.createOrder(order1);
        }
        return null;
    }

    @Override
    public Offer reject(String offerId) {
        Offer offer = offerRepository.findById(Long.valueOf(offerId)).get();
        offer.setStatus(OfferStatus.Rejected);
        offerRepository.save(offer);
        return null;
    }

    @Override
    public List<Offer> findByTofMali(String tofMali) {
        return offerRepository.findAllByTofMali(Long.valueOf(tofMali));
    }
}
