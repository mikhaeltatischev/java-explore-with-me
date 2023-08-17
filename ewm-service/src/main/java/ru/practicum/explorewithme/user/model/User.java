package ru.practicum.explorewithme.user.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "users")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String name;
}