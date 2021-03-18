package org.example;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class App {
    private static final String JSON_BOOK_DOCUMENT =
            "{\n" +
                    "    \"store\": {\n" +
                    "        \"book\": [\n" +
                    "            {\n" +
                    "                \"category\": \"reference\",\n" +
                    "                \"author\": \"Nigel Rees\",\n" +
                    "                \"title\": \"Sayings of the Century\",\n" +
                    "                \"price\": 8.95\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"category\": \"fiction\",\n" +
                    "                \"author\": \"Evelyn Waugh\",\n" +
                    "                \"title\": \"Sword of Honour\",\n" +
                    "                \"price\": 12.99\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"category\": \"fiction\",\n" +
                    "                \"author\": \"Herman Melville\",\n" +
                    "                \"title\": \"Moby Dick\",\n" +
                    "                \"isbn\": \"0-553-21311-3\",\n" +
                    "                \"price\": 8.99\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"category\": \"fiction\",\n" +
                    "                \"author\": \"J. R. R. Tolkien\",\n" +
                    "                \"title\": \"The Lord of the Rings\",\n" +
                    "                \"isbn\": \"0-395-19395-8\",\n" +
                    "                \"price\": 22.99\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"bicycle\": {\n" +
                    "            \"color\": \"red\",\n" +
                    "            \"price\": 19.95\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"expensive\": 10\n" +
                    "}";
    private static Configuration jsonPathConf;

    public static void main(String[] args) {
        var b = Configuration
                .builder()
                .mappingProvider(new JacksonMappingProvider())
                .jsonProvider(new JacksonJsonProvider());

        if (args.length == 1 && args[0].equalsIgnoreCase("SUPPRESS_EXCEPTIONS")){
            b.options(Option.SUPPRESS_EXCEPTIONS);
        }
        jsonPathConf = b.build();

        test("$.store.bicycle.color", String.class);
        test("$.store.book[*].author", List.class);
        test("$.store.book[?(@.price > 20)]", List.class);
        test("$.keys()", List.class);
    }

    private static <T> void test(String path, Class<T> type) {
        var value = JsonPath.using(jsonPathConf).parse(JSON_BOOK_DOCUMENT)
                .read(path, type);
        System.out.printf("Read '%s' returns [%s]: %s%n", path,
                Optional.ofNullable(value).map(v ->v.getClass().getCanonicalName()).orElse("null"),
                value
        );
    }
}
