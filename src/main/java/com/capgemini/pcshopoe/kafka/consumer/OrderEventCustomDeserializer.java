package com.capgemini.pcshopoe.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class OrderEventCustomDeserializer implements Deserializer<OrderEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OrderEvent deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }

            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), OrderEvent.class);

        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to OrderEvent");
        }
    }

    @Override
    public void close() {
    }
}
