package com.driver.Service;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.List;
import java.util.UUID;

public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel)
    {
        if(hotel == null || hotel.getHotelName() == null) return "FAILURE";

        return hotelManagementRepository.addHotel(hotel);
    }
    public Integer addUser(User user)
    {
        return hotelManagementRepository.addUser(user);
    }
    public String getHotelWithMostFacilities()
    {
        List<String> hotelsList = hotelManagementRepository.allHotels();

        String hotelName = null;
        int noOFFacilities = Integer.MIN_VALUE;

        for(String name : hotelsList)
        {
            List<Facility> facilities = hotelManagementRepository.getAllFacilities(name);
            if(noOFFacilities < facilities.size())
            {
                hotelName = name;
                noOFFacilities = facilities.size();
            }
            else if (noOFFacilities == facilities.size()) {
                hotelName = select(hotelName,name);
            }
        }
        return hotelName;
    }

    public String select(String hotelName,String name)
    {
        int i = 0;
        while(i < hotelName.length() && i < name.length())
        {
            if(hotelName.charAt(i) > name.charAt(i))
            {
                return  name;
            }
            else if(name.charAt(i) > hotelName.charAt(i))
            {
                return hotelName;
            }
            else i++;
        }

        if(hotelName.length() > name.length()) return name;

        return hotelName;
    }

    public int bookARoom(Booking booking)
    {
        String id = String.valueOf(UUID.randomUUID());
        booking.setBookingId(id);

        if(hotelManagementRepository.roomsAvailable(booking.getHotelName()) < booking.getNoOfRooms())
        {
            return -1;
        }

        int roomsBooked = booking.getNoOfRooms();
        int price = hotelManagementRepository.getPricePerNight(booking.getHotelName());

        int totalBill = roomsBooked*price;

        hotelManagementRepository.saveUpdatedRoomsAvail(roomsBooked,booking.getHotelName());

        booking.setAmountToBePaid(totalBill);

        hotelManagementRepository.saveBookingInRepo(booking);

        return totalBill;
    }

    public int numberOfBookings(Integer aadharNo)
    {
        List<Booking> bookingList = hotelManagementRepository.allBookingList();
        int cnt = 0;

        for(Booking booking : bookingList)
        {
            if(booking.getBookingAadharCard() == aadharNo)
            {
                cnt ++;
            }
        }
        return cnt;
    }
}
