package space.harbour.orders.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import space.harbour.orders.domain.Order;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static space.harbour.orders.util.Constants.CSV_EXTENSION;

public class FileUtils {

    public static List<Order> readOrdersFile(String filePath) throws
            FileNotFoundException, IOException {
        List<Order> orderList = new ArrayList<>();

        final String[] HEADERS = {"id", "date", "customer_name", "price"};
        Reader fileReader = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .parse(fileReader);
        for (CSVRecord record : records) {
            String id = record.get("id");
            String date = record.get("date");
            String customerName = record.get("customer_name");
            String price = record.get("price");
            Order order = new Order(
                    id,
                    LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(date)),
                    customerName,
                    Float.parseFloat(price)
            );
            orderList.add(order);
        }

        return orderList;
    }

    public static boolean isCsvFile(String filepath) {
        String type = filepath.substring(filepath.lastIndexOf(".") + 1);
        if (!type.equals(CSV_EXTENSION)) {
            return false;
        }

        return true;
    }
}
