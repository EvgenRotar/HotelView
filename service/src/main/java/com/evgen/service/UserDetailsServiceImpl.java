package com.evgen.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.evgen.Guest;
import com.evgen.dao.HotelDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private HotelDao hotelDao;

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Guest guest = hotelDao.getGuestByName(name);
    Set<GrantedAuthority> roles = new HashSet();
    roles.add(new SimpleGrantedAuthority("Admin"));


    UserDetails userDetails =
        new org.springframework.security.core.userdetails.User(guest.getName(),
            "111",
            roles);

    return userDetails;
  }

}