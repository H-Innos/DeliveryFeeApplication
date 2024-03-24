package com.example.DeliveryFeeApplication.scheduled;

import com.example.DeliveryFeeApplication.service.HttpClientService;
import com.example.DeliveryFeeApplication.weather.WeatherEntry;
import com.example.DeliveryFeeApplication.weather.WeatherRepository;
import com.example.DeliveryFeeApplication.service.XmlParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class WeatherDataFetcher {
    private static final String DATA_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private static final List<String> OBSERVABLE_STATIONS = List.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");
    private final WeatherRepository weatherRepository;
    private final HttpClientService httpClientService;
    private final XmlParser xmlParser;

    public WeatherDataFetcher(WeatherRepository weatherRepository, HttpClientService httpClientService, XmlParser xmlParser) {
        this.weatherRepository = weatherRepository;
        this.httpClientService = httpClientService;
        this.xmlParser = xmlParser;
    }

    /**
     * Scheduled task for retrieving weather data.
     */
    @Scheduled(cron = "0 * * * * *")
    public void saveData(){
        try {
            HttpResponse<InputStream> response = httpClientService.fetchData(DATA_URL);
            Document doc = xmlParser.parseXml(response.body());

            Long timestamp = Long.parseLong(doc.getDocumentElement().getAttribute("timestamp"));

            List<WeatherEntry> newEntries = getWeatherEntriesForObservableStationsFromDocument(doc, timestamp);

            weatherRepository.saveAll(newEntries);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the weather data for all stations from the document.
     * @param doc       document
     * @param timestamp timestamp to be added to the entries
     * @return          list of weather entries, 1 for each of the relevant stations
     */
    private List<WeatherEntry> getWeatherEntriesForObservableStationsFromDocument(Document doc, Long timestamp) {
        List<WeatherEntry> entries = new ArrayList<>();

        NodeList stationNodes = doc.getElementsByTagName("station");

        for (int i = 0; i < stationNodes.getLength(); i++) {
            Element stationElement = (Element) stationNodes.item(i);
            String stationName = stationElement.getElementsByTagName("name").item(0).getTextContent();
            if (WeatherDataFetcher.OBSERVABLE_STATIONS.contains(stationName)) {
                WeatherEntry weatherEntry = createWeatherEntry(stationElement, timestamp);
                entries.add(weatherEntry);
            }
        }
        return entries;
    }

    /**
     * Transforms an XML element into a weather entry object.
     * @param station   XML element for the station
     * @param timestamp attached timestamp
     * @return          weather entry object
     */
    private WeatherEntry createWeatherEntry(Element station, Long timestamp) {
        String name = extractValueByTag(station, "name");
        String phenomenon = extractValueByTag(station, "phenomenon");
        int wmoCode = parseIntOrDefault(extractValueByTag(station, "wmocode"));
        double airTemperature = parseDoubleOrDefault(extractValueByTag(station, "airtemperature"));
        double windSpeed = parseDoubleOrDefault(extractValueByTag(station, "windspeed"));
        return new WeatherEntry(timestamp, name, phenomenon, wmoCode, airTemperature, windSpeed);
    }

    /**
     * Extracts the text value from the tag of the XML element.
     * @param station   XML element
     * @param tag       tag name
     * @return          corresponding value
     */
    private String extractValueByTag(Element station, String tag) {
        NodeList nodeList = station.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        } else {
            return null;
        }
    }

    /**
     * Returns integer value from the string or -1 if the string is empty.
     * @param value string
     * @return      integer value
     */
    private int parseIntOrDefault(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Returns double value from the string or Double.NaN, if the string is empty.
     * @param value string
     * @return      double value
     */
    private double parseDoubleOrDefault(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
}
