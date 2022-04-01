//package io.dailyworker.flight.ws;
//
//import io.dailyworker.flight.domain.Airplane;
//import io.dailyworker.flight.domain.Airport;
//import io.dailyworker.flight.domain.FlightSchedule;
//import io.dailyworker.flight.enumerate.AirportInfo;
//import io.dailyworker.flight.enumerate.CountryInfo;
//import io.dailyworker.flight.repositories.AirPlanRepository;
//import io.dailyworker.flight.repositories.AirportRepository;
//import io.dailyworker.flight.repositories.FlightScheduleRepository;
//import io.dailyworker.flight.ws.config.FlightWebserviceConfiguration;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.ws.test.server.MockWebServiceClient;
//import org.springframework.xml.transform.StringSource;
//
//import javax.transaction.Transactional;
//import javax.xml.transform.Source;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//
//import static org.springframework.ws.test.server.RequestCreators.withPayload;
//import static org.springframework.ws.test.server.ResponseMatchers.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Import(FlightWebserviceConfiguration.class)
//class FlightEndpointTest {
//    private static final String MESSAGE_NAMESPACE = "http://dailyworker.github.io/tiny-travel/flight/schemas/messages";
//    private static final String TYPE_NAMESPACE = "http://dailyworker.github.io/tiny-travel/flight/schemas/types";
//    private static final Resource schema = new ClassPathResource("messages.xsd");
//
//    @Autowired
//    ApplicationContext applicationContext;
//
//    @Autowired
//    FlightScheduleRepository flightScheduleRepository;
//
//    @Autowired
//    AirPlanRepository airplaneRepository;
//
//    @Autowired
//    AirportRepository airportRepository;
//
//    private MockWebServiceClient mockClient;
//
//    @BeforeEach
//    @Transactional
//    public void setUp() {
//        mockClient = MockWebServiceClient.createClient(applicationContext);
//
//        Airplane airplane = new Airplane("SU-123", "707", 100);
//        Airport gimpoAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.GMP);
//        Airport jejuAirport = new Airport(CountryInfo.DOMESTIC, AirportInfo.JEJU);
//
//        airplaneRepository.save(airplane);
//        airportRepository.save(gimpoAirport);
//        airportRepository.save(jejuAirport);
//
//        ZonedDateTime departAt = LocalDate.parse("2022-01-05").atStartOfDay(ZoneId.of("UTC"));
//        ZonedDateTime arriveAt = LocalDate.parse("2022-01-10").atStartOfDay(ZoneId.of("UTC"));
//
//        FlightSchedule flightSchedule = FlightSchedule.createFlightSchedule(airplane, gimpoAirport, jejuAirport, departAt.toInstant(), arriveAt.toInstant());
//        flightScheduleRepository.save(flightSchedule);
//    }
//
//    @Test
//    @DisplayName("XML 웹서비스 요청이 가능하다")
//    public void getFlightSchedule() throws IOException {
//        Source request = new StringSource("<gs:GetFlightsRequest xmlns:gs ='" + MESSAGE_NAMESPACE + "'>\n" +
//                "<gs:searchKey>GMP-CJU-20211231</gs:searchKey>\n" + "</gs:GetFlightsRequest>");
//
//        Source response = new StringSource("<ns3:GetFlightsResponse xmlns:ns3='" + MESSAGE_NAMESPACE + "' xmlns:ns2= '"+ TYPE_NAMESPACE + "'>" +
//                "<ns3:flightSchedule>" +
//                "<ns2:Airplane>" +
//                    "<ns2:flightModelName>707</ns2:flightModelName>" +
//                    "<ns2:flightNumber>SU-123</ns2:flightNumber>" +
//                    "<ns2:available>100</ns2:available>" +
//                "</ns2:Airplane>" +
//                "<ns2:departAt>2022-01-05T00:00:00.000Z</ns2:departAt>"+
//                "<ns2:from>" +
//                    "<ns2:city>국내</ns2:city>" +
//                    "<ns2:airport>GMP</ns2:airport>" +
//                "</ns2:from>"+
//                "<ns2:arriveAt>2022-01-10T00:00:00.000Z</ns2:arriveAt>" +
//                "<ns2:to>" +
//                    "<ns2:city>국내</ns2:city>" +
//                    "<ns2:airport>CJU</ns2:airport>" +
//                "</ns2:to>" +
//                "</ns3:flightSchedule>" +
//                "</ns3:GetFlightsResponse>");
//
//
//        mockClient
//                .sendRequest(withPayload(request))
//                .andExpect(noFault())
//                .andExpect(payload(response))
//                .andExpect(validPayload(schema));
//    }
//
//}