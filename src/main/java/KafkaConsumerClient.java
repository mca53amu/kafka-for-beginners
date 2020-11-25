import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerClient {
	
	public static void main(String a[]) {
		
		final Logger logger = LoggerFactory.getLogger(KafkaConsumerClient.class);
		Properties prop = new Properties();
		prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		prop.put(ConsumerConfig.GROUP_ID_CONFIG , "my-consumer-App");
		prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG , "earliest");
		KafkaConsumer<String,String> consumer=new KafkaConsumer<String,String>(prop);
		consumer.subscribe(Arrays.asList("first_topic"));
		
		while(true) {
			ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String,String> record : records) {
				logger.info("Key: "+record.key()+" value: "+record.value());
				logger.info("Partition: "+record.partition()+" Offset: "+record.offset());
			}
		}
		
	}

}
