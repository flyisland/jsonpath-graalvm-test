package org.example;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.util.List;

public class App {
    public static final String JSON_BOOK_DOCUMENT =
            "{ " +
                    "   \"category\" : \"reference\",\n" +
                    "   \"author\" : \"Nigel Rees\",\n" +
                    "   \"title\" : \"Sayings of the Century\",\n" +
                    "   \"display-price\" : 8.95\n" +
                    "}";

    public static void main(String[] args) {
        Configuration jsonPathConf = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .jsonProvider(new JacksonJsonProvider())
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();

        var keys = JsonPath.using(jsonPathConf).parse(JSON_BOOK_DOCUMENT)
                .read("$.keys()", List.class);
        System.out.println("The keys are " + keys);
    }
}
