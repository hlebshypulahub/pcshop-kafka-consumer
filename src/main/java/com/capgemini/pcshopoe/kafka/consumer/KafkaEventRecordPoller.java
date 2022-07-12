package com.capgemini.pcshopoe.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaEventRecordPoller implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventRecordPoller.class);
    private final Consumer<String, OrderEvent> consumer;
    private final AtomicBoolean isRunning;

    public KafkaEventRecordPoller(Consumer<String, OrderEvent> consumer) {
        this.consumer = consumer;
        this.isRunning = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        while (this.isRunning.get()) {
            ConsumerRecords<String, OrderEvent> records = consumer.poll(Duration.ofSeconds(1));
            records.forEach(record -> {
                logger.info("Message received: " + record.value());
            });
            consumer.commitAsync();
        }
    }
}
