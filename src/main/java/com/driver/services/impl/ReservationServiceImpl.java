package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {


        Optional<ParkingLot> optionalParkingLot=parkingLotRepository3.findById(parkingLotId);
        if (!optionalParkingLot.isPresent())
        {
            throw  new Exception("Cannot make reservation");
        }
        ParkingLot parkingLot=optionalParkingLot.get();

        List<Spot> spots= parkingLot.getSpotList().stream()
                .sorted(Comparator.comparingInt(Spot ::getPricePerHour))
                .collect(Collectors.toList());


        Spot spot=null;

        for(Spot s:spots)
        {
            if(!s.getOccupied() &&  s.getSpotType().equals(SpotType.TWO_WHEELER) && numberOfWheels<=2 )
            {
                spot=s;
                break;
            }
            else if(!s.getOccupied() &&  s.getSpotType().equals(SpotType.FOUR_WHEELER) && numberOfWheels<=4)
            {
                spot=s;
                break;
            }
            else if (!s.getOccupied() &&  s.getSpotType().equals(SpotType.OTHERS) && numberOfWheels>4)
            {
                spot=s;
                break;
            }
        }

        if(spot==null)
        {
            throw new Exception("Cannot make reservation");
        }

        spot.setOccupied(true);

        Reservation reservation=new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(spot);
        reservation.setPayment(null);


        //user
        User user=userRepository3.findById(userId).get();
        reservation.setUser(user);
        user.getReservationList().add(reservation);

        userRepository3.save(user);

        spotRepository3.save(spot);

       // reservationRepository3.save(reservation)

        return reservation;




    }
}
