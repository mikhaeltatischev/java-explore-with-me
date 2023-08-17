package ru.practicum.stats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@Table(name = "hits")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String app;

    @Column
    private String uri;

    @Column
    private String ip;

    @Column(name = "time_stamp")
    private LocalDateTime timestamp;

    public EndpointHit(String app, String uri, String ip, LocalDateTime timestamp) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
        this.timestamp = timestamp;
    }
}
