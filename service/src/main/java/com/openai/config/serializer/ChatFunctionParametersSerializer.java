package com.openai.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;

import java.io.IOException;

public class ChatFunctionParametersSerializer extends JsonSerializer<Class<?>> {

    private final ObjectMapper mapper = new ObjectMapper();
    // openai api 最低為DRAFT4格式 官方推薦用2020-12 version Draft 8 patch 1  
    private final JsonSchemaConfig config = JsonSchemaConfig.vanillaJsonSchemaDraft4();
    private final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper, config);

    @Override
    public void serialize(Class<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            try {
                JsonNode schema = jsonSchemaGenerator.generateJsonSchema(value);
                gen.writeObject(schema);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate JSON Schema", e);
            }
        }
    }
}





