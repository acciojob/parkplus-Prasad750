package com.driver.model;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean paymentCompleted;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;


    @OneToOne
    @JoinColumn
    Reservation reservation;

    public Payment() {
    }

    public Payment(int id, PaymentMode paymentMode, Reservation reservation,boolean paymentCompleted) {
        this.id = id;
        this.paymentMode = paymentMode;
        this.reservation = reservation;
        this.paymentCompleted=paymentCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }
}
