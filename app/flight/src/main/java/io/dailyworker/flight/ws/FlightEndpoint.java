package io.dailyworker.flight.ws;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.service.FlightScheduleService;
import io.dailyworker.flight.shema.support.SchemaConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.server.endpoint.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;


import io.github.dailyworker.tiny_travel.flight.schemas.messages.GetFlightsResponse;
import io.github.dailyworker.tiny_travel.flight.schemas.messages.ObjectFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.dailyworker.flight.ws.constants.FlightWebserviceConstant.GET_FLIGHTS_REQUEST;
import static io.dailyworker.flight.ws.constants.FlightWebserviceConstant.MESSAGES_NAMESPACE;


@Slf4j
@Endpoint
public class FlightEndpoint {
    private static final Log logger = LogFactory.getLog(FlightEndpoint.class);

    private final ObjectFactory objectFactory = new ObjectFactory();
    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private final FlightScheduleService flightScheduleService;

    public FlightEndpoint(FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }

    @PayloadRoot(localPart = GET_FLIGHTS_REQUEST, namespace = MESSAGES_NAMESPACE)
    @Namespace(prefix = "m", uri = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetFlightsResponse getFlights(@XPathParam("//m:from") String from, @XPathParam("//m:to") String to,
                                         @XPathParam("//m:departureDate") String departureDateString) throws DatatypeConfigurationException {

        if (logger.isDebugEnabled()) {
            logger.debug("Received GetFlightsRequest '" + from + "' to '" + to + "' on " + departureDateString);
        }
        

        LocalDate departureDate = LocalDate.parse(departureDateString, DateTimeFormatter.ISO_DATE);

        List<FlightSchedule> flights = flightScheduleService.searchSchedule(from, to, departureDate);

        GetFlightsResponse response = objectFactory.createGetFlightsResponse();
        for (FlightSchedule domainFlightSchedule : flights) {
            response.getFlightSchedule()
                    .add(SchemaConversionUtils.toSchemaType(domainFlightSchedule));
        }

        return response;
    }
}
