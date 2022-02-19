package io.dailyworker.flight.ws;

import io.dailyworker.flight.domain.FlightSchedule;
import io.dailyworker.flight.domain.dto.FlightScheduleRequest;
import io.dailyworker.flight.domain.vo.FlightScheduleSearchKey;
import io.dailyworker.flight.service.FlightScheduleService;
import io.dailyworker.flight.shema.support.SchemaConversionUtils;
import io.github.dailyworker.tiny_travel.flight.schemas.messages.GetFlightsResponse;
import io.github.dailyworker.tiny_travel.flight.schemas.messages.ObjectFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;

import java.text.ParseException;
import java.util.List;

import static io.dailyworker.flight.ws.constants.FlightWebserviceConstant.GET_FLIGHTS_REQUEST;
import static io.dailyworker.flight.ws.constants.FlightWebserviceConstant.MESSAGES_NAMESPACE;


@Slf4j
@Endpoint
public class FlightEndpoint {
    private final ObjectFactory objectFactory = new ObjectFactory();
    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private final FlightScheduleService flightScheduleService;

    public FlightEndpoint(FlightScheduleService flightScheduleService) {
        this.flightScheduleService = flightScheduleService;
    }

    @PayloadRoot(localPart = GET_FLIGHTS_REQUEST, namespace = MESSAGES_NAMESPACE)
    @Namespace(prefix = "m", uri = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetFlightsResponse getFlights(@XPathParam("//m:searchKey") String searchKey)
            throws DatatypeConfigurationException, ParseException {

        FlightScheduleRequest flightSearchKey = new FlightScheduleSearchKey(searchKey).toRequest();

        List<FlightSchedule> flights = flightScheduleService.searchSchedule(flightSearchKey);

        GetFlightsResponse response = objectFactory.createGetFlightsResponse();

        for (FlightSchedule domainFlightSchedule : flights) {
            response.getFlightSchedule()
                    .add(SchemaConversionUtils.toSchemaType(domainFlightSchedule));
        }
        return response;
    }
}
