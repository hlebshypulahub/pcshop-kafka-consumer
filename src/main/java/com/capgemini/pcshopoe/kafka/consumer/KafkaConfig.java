package com.capgemini.pcshopoe.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class KafkaConfig {

    private final Environment env;

    public KafkaConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public KafkaEventRecordPoller consumer() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.bootstrap-servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("spring.kafka.consumer.group-id"));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, env.getProperty("spring.kafka.consumer.auto-offset-reset"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, env.getProperty("spring.kafka.consumer.key-deserializer"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, env.getProperty("spring.kafka.consumer.value-deserializer"));

        KafkaConsumer<String, OrderEvent> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(env.getProperty("com.capgemini.pcshop.kafka.topic")));

        KafkaEventRecordPoller kafkaEventRecordPoller = new KafkaEventRecordPoller(kafkaConsumer);
        executorService.submit(kafkaEventRecordPoller);

        return kafkaEventRecordPoller;
    }

}
