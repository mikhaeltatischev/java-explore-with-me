package ru.practicum.explorewithme.category.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity(name = "categories")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}