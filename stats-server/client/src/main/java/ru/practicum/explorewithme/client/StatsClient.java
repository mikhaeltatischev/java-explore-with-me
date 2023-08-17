package ru.practicum.explorewithme.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.dto.EndpointHitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    private static final String STATS_PATH = "/stats?start={start}&end={end}&unique={unique}&uris={uris}";
    private static final String STATS_PATH_WITHOUT_URIS = "/stats?start={start}&end={end}&unique={unique}";
    private static final String STATS_PATH_UNIQUE = "/unique?uri={uri}&ip={ip}";
    private static final String HIT_PATH = "/hit";

    @Autowired
    public StatsClient(@Value("${explore-with-me-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addHit(String app, String uri, String ip, String timestamp) {
        EndpointHitDto hit = new EndpointHitDto(app, uri, ip, timestamp);

        return post(HIT_PATH, hit);
    }

    public ResponseEntity<Object> getStats(String start, String end, @Nullable List<String> uris, Boolean unique) {
        Map<String, Object> parameters;

        if (uris == null) {
            parameters = Map.of("start", encodeValue(start),
                    "end", encodeValue(end),
                    "unique", unique);
            return get(STATS_PATH_WITHOUT_URIS, parameters);
        } else {
            parameters = Map.of("start", encodeValue(start),
                    "end", encodeValue(end),
                    "uris", uris,
                    "unique", unique);
            return get(STATS_PATH, parameters);
        }
    }

    public Boolean checkUnique(String uri, String ip) {
        Map<String, Object> parameters = Map.of("ip", ip,
                                                "uri", uri);

        Object response = get(STATS_PATH_UNIQUE, parameters).getBody();
        String re = response.toString();

        if (response.equals(true)) {
            return true;
        }
        return false;
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}