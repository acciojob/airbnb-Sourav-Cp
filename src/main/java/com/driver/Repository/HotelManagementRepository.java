package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelManagementRepository {

    private Map<String, Hotel> hotelMap = new HashMap<>();

    private Map<Integer, User> userMap = new HashMap<>();

    private Map<String, Booking> bookingMap = new HashMap<>();

    public String addHotel(Hotel hotel)
    {
        if(hotelMap.containsKey(hotel.getHotelName())) return "FAILURE";

        hotelMap.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user)
    {
        userMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public List<String> allHotels()
    {
        return new ArrayList<>(hotelMap.keySet());
    }
    public List<Facility> getAllFacilities(String hotelName)
    {
        Hotel hotel = hotelMap.get(hotelName);

        return hotel.getFacilities();
    }

    public int roomsAvailable(String hotelName)
    {
        Hotel hotel = hotelMap.get(hotelName);
        return hotel.getAvailableRooms();
    }
    public int getPricePerNight(String hotelName)
    {
        Hotel hotel = hotelMap.get(hotelName);
        return hotel.getPricePerNight();
    }
    public void saveUpdatedRoomsAvail(int roomsBooked,String hotelName)
    {
        Hotel hotel = hotelMap.get(hotelName);
        hotel.setAvailableRooms(hotel.getAvailableRooms()-roomsBooked);
        hotelMap.put(hotelName, hotel);
    }

    public void saveBookingInRepo(Booking booking)
    {
        bookingMap.put(booking.getBookingId(), booking);
    }

    public List<Booking> allBookingList()
    {
        return new ArrayList<>(bookingMap.values());
    }
}
