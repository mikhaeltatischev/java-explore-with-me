package ru.practicum.explorewithme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.client.StatsClient;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class HomeController {

    private final StatsClient client;

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam String start,
                                           @RequestParam String end,
                                           @RequestParam(required = false) String[] uris,
                                           @RequestParam(defaultValue = "false") Boolean unique) {
        return client.getStats(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestParam String app,
                                         @RequestParam String uri,
                                         @RequestParam String ip,
                                         @RequestParam String timestamp) {
        return client.addHit(app, uri, ip, timestamp);
    }
}
