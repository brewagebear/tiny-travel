package io.dailyworker.flight.shema.support;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

// 정적으로 빌드된 types.xsd에 대한 패키지들
import io.github.dailyworker.tiny_travel.flight.schemas.types.Airplane;
import io.github.dailyworker.tiny_travel.flight.schemas.types.FlightSchedule;
import io.github.dailyworker.tiny_travel.flight.schemas.types.Airport;
import org.springframework.util.ObjectUtils;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public abstract class SchemaConversionUtils {
    private SchemaConversionUtils() {}

    public static FlightSchedule toSchemaType(io.dailyworker.flight.domain.FlightSchedule domainFlightSchedule)
            throws DatatypeConfigurationException {

        FlightSchedule schemaFlight = new FlightSchedule();

        schemaFlight.setAirPlane(toSchemaType(domainFlightSchedule.getAirplane()));
        schemaFlight.setFrom(toSchemaType(domainFlightSchedule.getFrom()));
        schemaFlight.setDepartDate(toXMLGregorianCalendar(domainFlightSchedule.getDepartDate()));
        schemaFlight.setTo(toSchemaType(domainFlightSchedule.getTo()));
        schemaFlight.setArriveDate(toXMLGregorianCalendar(domainFlightSchedule.getArriveDate()));
        return schemaFlight;
    }

    public static Airplane toSchemaType(io.dailyworker.flight.domain.Airplane domainAirplane) {
        if(hasNotObject(domainAirplane)) {
            return null;
        }

        Airplane schemaAirplane = new Airplane();
        schemaAirplane.setFlightNumber(domainAirplane.getFlightNumber());
        schemaAirplane.setFlightModelName(domainAirplane.getFlightModelName());
        schemaAirplane.setAvailable(domainAirplane.getAvailable());
        return schemaAirplane;
    }

    public static Airport toSchemaType(io.dailyworker.flight.domain.Airport domainAirport) {
        if (hasNotObject(domainAirport)) {
            return null;
        }

        Airport schemaAirport = new Airport();
        schemaAirport.setCity(domainAirport.cityName("KOR"));
        schemaAirport.setAirport(domainAirport.itatCode());
        return schemaAirport;
    }

    public static boolean hasNotObject(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(ZonedDateTime dateTime)
            throws DatatypeConfigurationException {

        DatatypeFactory factory = DatatypeFactory.newInstance();
        return factory.newXMLGregorianCalendar(GregorianCalendar.from(dateTime));
    }
}
