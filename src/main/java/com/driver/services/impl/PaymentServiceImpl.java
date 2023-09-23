package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Optional<Reservation> optionalReservation=reservationRepository2.findById(reservationId);

        if(!optionalReservation.isPresent())
        {

        }
        Reservation reservation=optionalReservation.get();



        int price=reservation.getNumberOfHours() * reservation.getSpot().getPricePerHour();

        if(price>amountSent)
        {
          throw  new Exception("Insufficient Amount");
        }

        if(!PaymentMode.CARD.toString().equals(mode) && !PaymentMode.UPI.toString().equals(mode) && !PaymentMode.CASH.toString().equals(mode))
        {
            throw new Exception("Payment mode not detected");
        }
        Payment payment =new Payment();
        payment.setPaymentMode(PaymentMode.valueOf(mode.toUpperCase()));
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);


        reservation.setPayment(payment);

        return paymentRepository2.save(payment);






    }
}
