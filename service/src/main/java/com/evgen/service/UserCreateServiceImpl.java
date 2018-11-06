package com.evgen.service;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;

import com.evgen.Guest;
import com.evgen.dao.HotelDao;

@Service
public class UserCreateServiceImpl implements UserCreateService {

  private final HotelDao hotelDao;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserCreateServiceImpl(HotelDao hotelDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.hotelDao = hotelDao;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public Guest createGuest(Guest guest) {
    validationGuest(guest);
    guest.setReservations(new ArrayList<>());
    guest.setPassword(bCryptPasswordEncoder.encode(guest.getPassword()));

    return hotelDao.createGuest(guest);
  }

  public Guest createGuestFromGoogle(String name) {
    Guest guest = new Guest();
    guest.setReservations(new ArrayList<>());
    guest.setName(name);
    return hotelDao.createGuest(guest);
  }

  private void validationGuest(Guest guest) {
    if (StringUtils.isBlank(guest.getName()) || StringUtils.isBlank(guest.getPassword())) {
      throw new ServerWebInputException("Bad guest request");
    }
  }
}