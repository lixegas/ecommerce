package com.lixega.ecommerce.shipment.mapper;


import com.lixega.ecommerce.sdk.core.model.dto.ShipmentDto;
import com.lixega.ecommerce.shipment.model.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;


@Mapper(imports = Instant.class, componentModel = "spring")
public interface ShipmentMapper {


    @Mapping(target = "trackingNumber", expression = "java(generateTrackingNumber())")
    @Mapping(target = "carrier", constant = "Default Carrier")
    @Mapping(target = "shipmentStatus", source = "shipmentDto.shipmentStatus")
    @Mapping(target = "shippedDate", expression = "java(generateRandomShippedDate())")
    @Mapping(target = "deliveryDate", expression = "java(generateRandomDeliveryDate())")
    Shipment toShipment(ShipmentDto shipmentDto);

    default String generateTrackingNumber() {
        UUID uuid = UUID.randomUUID();
        return "TRK-" + uuid.toString().replace("-", "").toUpperCase();
    }


    default Instant generateRandomShippedDate() {
        Instant startDate = Instant.parse("2024-01-01T00:00:00Z");
        Instant endDate = Instant.now();

        long startEpoch = startDate.toEpochMilli();
        long endEpoch = endDate.toEpochMilli();
        long randomEpoch = startEpoch + (long) (new Random().nextDouble() * (endEpoch - startEpoch));

        return Instant.ofEpochMilli(randomEpoch);
    }

    default Instant generateRandomDeliveryDate() {
        Instant startDate = Instant.now().plus(1, ChronoUnit.DAYS);
        Instant endDate = startDate.plus(30, ChronoUnit.DAYS);

        // Genera una data di consegna casuale tra startDate e endDate
        long startEpoch = startDate.toEpochMilli();
        long endEpoch = endDate.toEpochMilli();
        long randomEpoch = startEpoch + (long) (new Random().nextDouble() * (endEpoch - startEpoch));

        return Instant.ofEpochMilli(randomEpoch);
    }
}