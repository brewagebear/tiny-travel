package io.dailyworker.flight.shema.support;

import io.github.dailyworker.tiny_travel.flight.schemas.types.Airplane;
import io.github.dailyworker.tiny_travel.flight.schemas.types.Airport;
import io.github.dailyworker.tiny_travel.flight.schemas.types.FlightSchedule;
import org.springframework.util.ObjectUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public abstract class SchemaConversionUtils {
    private SchemaConversionUtils() {}

    public static FlightSchedule toSchemaType(io.dailyworker.flight.domain.FlightSchedule domainFlightSchedule)
            throws DatatypeConfigurationException {

        FlightSchedule schemaFlight = new FlightSchedule();

        schemaFlight.setAirplane(toSchemaType(domainFlightSchedule.getAirplane()));
        schemaFlight.setFrom(toSchemaType(domainFlightSchedule.getFrom()));
        schemaFlight.setDepartAt(toXMLGregorianCalendar(domainFlightSchedule.getDepartAt()));
        schemaFlight.setTo(toSchemaType(domainFlightSchedule.getTo()));
        schemaFlight.setArriveAt(toXMLGregorianCalendar(domainFlightSchedule.getArriveAt()));
        return schemaFlight;
    }

    public static Airplane toSchemaType(io.dailyworker.flight.domain.Airplane domainAirplane) {
        if(ObjectUtils.isEmpty(domainAirplane)) {
            return null;
        }

        Airplane schemaAirplane = new Airplane();
        schemaAirplane.setFlightNumber(domainAirplane.getFlightNumber());
        schemaAirplane.setFlightModelName(domainAirplane.getFlightModelName());
        return schemaAirplane;
    }

    public static Airport toSchemaType(io.dailyworker.flight.domain.Airport domainAirport) {
        if (ObjectUtils.isEmpty(domainAirport)) {
            return null;
        }

        Airport schemaAirport = new Airport();
        schemaAirport.setCity(domainAirport.cityName("KOR"));
        schemaAirport.setAirport(domainAirport.itatCode());
        return schemaAirport;
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(Instant instant)
            throws DatatypeConfigurationException {

        DatatypeFactory factory = DatatypeFactory.newInstance();
        return factory.newXMLGregorianCalendar(GregorianCalendar.from(instant.atZone(ZoneId.of("UTC"))));
    }
}
