package com.evgen.test;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.Reservation;
import com.evgen.ReservationRequest;
import com.evgen.config.DaoImplTestConfig;
import com.evgen.connector.Connector;
import com.evgen.dao.HotelDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoImplTestConfig.class)
public class DaoImplTest {

  private static final Logger LOGGER = LogManager.getLogger(DaoImplTest.class);

  private static final String HOTEL = "/Hotel.json";
  private static final String GUEST = "/Guest-with-reservations.json";
  private static final String RESERVATIONS = "/Reservations.json";
  private static final String RESERVATION_REQUEST = "/Reservation-request.json";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private Connector connectorMock;

  @Autowired
  private HotelDao hotelDao;

  @Value("${url.getHotels}")
  private String getHotelsUrl;

  @Value("${url.getHotelByName}")
  private String getHotelByNameUrl;

  @Value("${url.getGuestByName}")
  private String getGuestByNameUrl;

  @Value("${url.getReservations}")
  private String getReservationsUrl;

  @Value("${url.deleteReservation}")
  private String deleteReservationUrl;

  @Value("${url.createReservation}")
  private String createReservationUrl;

  @After
  public void clean() {
    verify();
  }

  @Before
  public void setUp() {
    reset(connectorMock);
  }

  @Test
  public void getHotels() throws IOException {
    LOGGER.debug("test: get hotels");

    Hotel hotel = objectMapper.readValue(getClass().getResourceAsStream(HOTEL), Hotel.class);
    ArrayList<Hotel> hotels = new ArrayList<>();
    hotels.add(hotel);
    URI uri = UriComponentsBuilder.fromUriString(getHotelsUrl).build().toUri();

    expect(connectorMock.sendRequestWithoutBody(new HttpHeaders(), uri, HttpMethod.GET, ArrayList.class))
        .andReturn(hotels);
    replay(connectorMock);

    Assert.assertEquals(hotelDao.getHotels().size(), 1);
  }

  @Test
  public void getHotelByName() throws IOException {
    LOGGER.debug("test: get hotel by name");

    Hotel hotel = objectMapper.readValue(getClass().getResourceAsStream(HOTEL), Hotel.class);
    ArrayList<Hotel> hotels = new ArrayList<>();
    hotels.add(hotel);
    URI uri = UriComponentsBuilder.fromUriString(getHotelsUrl).queryParam("hotelName", "Abc").build().toUri();

    expect(connectorMock.sendRequestWithoutBody(new HttpHeaders(), uri, HttpMethod.GET, ArrayList.class))
        .andReturn(hotels);
    replay(connectorMock);

    Assert.assertEquals(hotelDao.getHotelByName("Abc").size(), 1);
  }

  @Test
  public void getGuestByName() throws IOException {
    LOGGER.debug("test: get guest by name");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    URI uri = UriComponentsBuilder.fromUriString(getGuestByNameUrl).queryParam("name", "sergei").build().toUri();

    expect(connectorMock.sendRequestWithoutBody(new HttpHeaders(), uri, HttpMethod.GET, Guest.class))
        .andReturn(guest);
    replay(connectorMock);

    Assert.assertEquals(hotelDao.getGuestByName("sergei").getGuestId(), "5bc449c09ddbcd660ac58f07");
  }

  @Test
  public void getReservations() throws IOException {
    LOGGER.debug("test: get reservations by guestId");

    Reservation reservation = objectMapper.readValue(getClass().getResourceAsStream(RESERVATIONS), Reservation.class);
    ArrayList<Reservation> reservations = new ArrayList<>();
    reservations.add(reservation);
    HttpHeaders headers = new HttpHeaders();
    headers.add("guestId", "1");
    URI uri = UriComponentsBuilder.fromUriString(getReservationsUrl).build().toUri();

    expect(connectorMock.sendRequestWithoutBody(headers, uri, HttpMethod.GET, ArrayList.class))
        .andReturn(reservations);
    replay(connectorMock);

    Assert.assertEquals(hotelDao.getReservations("1").size(), 1);
  }

  @Test
  public void deleteReservation() throws IOException {
    LOGGER.debug("test: delete reservation");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    HttpHeaders headers = new HttpHeaders();
    headers.add("guestId", "5bc449c09ddbcd660ac58f07");
    URI uri = UriComponentsBuilder.fromUriString(deleteReservationUrl).buildAndExpand("2").toUri();

    expect(connectorMock.sendRequestWithoutBody(headers, uri, HttpMethod.DELETE, Guest.class))
        .andReturn(guest);
    replay(connectorMock);

    Guest guestReturn = hotelDao.deleteReservation("5bc449c09ddbcd660ac58f07", "2");

    Assert.assertEquals(guestReturn.getGuestId(), "5bc449c09ddbcd660ac58f07");
  }

  @Test
  public void createReservation() throws IOException {
    LOGGER.debug("test: create reservation");

    ReservationRequest createReservation = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    URI uri = UriComponentsBuilder.fromUriString(createReservationUrl).build().toUri();

    expect(connectorMock.sendRequestWithBody(createReservation, new HttpHeaders(), uri, HttpMethod.POST, Guest.class))
        .andReturn(guest);
    replay(connectorMock);

    Guest guestReturn = hotelDao.createReservation(createReservation);

    Assert.assertEquals(guestReturn.getReservations().size(), 1);
  }

  @Test
  public void editReservation() throws IOException {
    LOGGER.debug("test: edit reservation");

    ReservationRequest createReservation = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);
    URI uri = UriComponentsBuilder.fromUriString(deleteReservationUrl).buildAndExpand("5bc7340b677aa44e986d19db")
        .toUri();

    expect(connectorMock.sendRequestWithBody(createReservation, new HttpHeaders(), uri, HttpMethod.PUT, Guest.class))
        .andReturn(guest);
    replay(connectorMock);

    Guest guestReturn = hotelDao.editReservation(createReservation, "5bc7340b677aa44e986d19db");

    Assert.assertEquals(guestReturn.getReservations().size(), 1);
  }
}
