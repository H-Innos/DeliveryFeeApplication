package com.example.DeliveryFeeApplication.scheduled;

import com.example.DeliveryFeeApplication.service.HttpClientService;
import com.example.DeliveryFeeApplication.service.XmlParser;
import com.example.DeliveryFeeApplication.weather.WeatherEntry;
import com.example.DeliveryFeeApplication.weather.WeatherRepository;
import com.example.DeliveryFeeApplication.weather.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherDataFetcherTest {

    @Mock
    private HttpClientService httpClientService;
    @Mock
    private XmlParser xmlParser;
    @Mock
    private WeatherRepository weatherRepository;
    private WeatherDataFetcher weatherDataFetcher;
    @BeforeEach
    void setUp() {
        weatherDataFetcher = new WeatherDataFetcher(weatherRepository, httpClientService, xmlParser);
    }

    @Test
    @Disabled
    void canSaveData() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        weatherDataFetcher.saveData();
        verify(weatherRepository).saveAll(anyList());
    }
}