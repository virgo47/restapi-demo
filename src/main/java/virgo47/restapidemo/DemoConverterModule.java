package virgo47.restapidemo;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

class DemoConverterModule extends SimpleModule {

	DemoConverterModule() {
		super("DemoConverterModule");
		addSerializer(Instant.class, new InstantSerializer());
		addDeserializer(Instant.class, new InstantDeserializer());
	}

	private static class InstantSerializer extends JsonSerializer<Instant> {
		@Override
		public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException
		{
			gen.writeString(value.toString());
		}
	}

	private static class InstantDeserializer extends JsonDeserializer<Instant> {
		@Override
		public Instant deserialize(JsonParser parser, DeserializationContext ctx)
			throws IOException
		{
			return Instant.parse(parser.getValueAsString());
		}
	}
}
