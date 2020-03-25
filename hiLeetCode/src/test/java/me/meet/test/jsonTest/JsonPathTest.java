package me.meet.test.jsonTest;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.util.Date;
import java.util.List;

public class JsonPathTest {

    private static void example1(String json) {
        /**
         * 如果你只想读取一次，那么上面的代码就可以了
         */
        List<String> authors = JsonPath.read(json, "$.store.book[*].author");
        System.out.println(authors);
    }

    private static void example2(String json) {
        /**
         * 如果你还想读取其他路径，现在上面不是很好的方法，因为他每次获取都需要再解析整个文档。
         * 所以，我们可以先解析整个文档，再选择调用路径。
         */
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        String author0 = JsonPath.read(document, "$.store.book[0].author");
        String author1 = JsonPath.read(document, "$.store.book[1].author");

        System.out.println(author0);
        System.out.println(author1);
    }

    private static void example3(String json) {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        // 正常
        String author0 = JsonPath.read(document, "$.store.book[0].author");
        // 抛出 java.lang.ClassCastException 异常
        List<String> author1 = JsonPath.read(document, "$.store.book[0].author");

        System.out.println(author0);
        System.out.println(author1);
    }

    private static void example4() {
        /**
         * 默认情况下，MappingProvider SPI提供了一个简单的对象映射器。 这允许您指定所需的返回类型，MappingProvider将尝试执行映射。 在下面的示例中，演示了Long和Date之间的映射。
         */
        String json = "{\"date_as_long\" : 1411455611975}";
        Date date = JsonPath.parse(json).read("$['date_as_long']", Date.class);
        System.out.println(date);
    }

    private static void example5(String json) {
        /**
         * 如果您将JsonPath配置为使用JacksonMappingProvider或GsonMappingProvider，您甚至可以将JsonPath输出直接映射到POJO中。
         */

        class Book {
            private String category;
            private String author;
            private String title;
            private Double price;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
            }
        }

        Book book = JsonPath.parse(json).read("$.store.book[0]", Book.class);
        System.out.println(book);
    }

    public static void main(String[] args) {
        String json = "{\n" +
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
                "}\n";

        example1(json);
        example2(json);
//        example3(json);
        example4();
        example5(json);
    }
}
