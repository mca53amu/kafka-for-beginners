import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducerClient {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(KafkaProducerClient.class);
		Properties prop = new Properties();
		prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
		for (int i = 0; i < 10; i++) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "Hello World"+Integer.toString(i));
			
			producer.send(record, new Callback() {

				public void onCompletion(RecordMetadata metadata, Exception exception) {

					if (exception == null) {
						logger.info("Recieved new Metadata. \n" + "Topic : " + metadata.topic() + "\n" + "Partition: "
								+ metadata.partition() + "\n" + "Offset: " + metadata.partition() + "\n" + "Timestamp:  "
								+ metadata.timestamp());
					} else {
						logger.error("Error while producing ",exception);

					}

				}
			});

		}
		producer.flush();
		producer.close();

	}

}
