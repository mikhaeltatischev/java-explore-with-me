package ru.practicum.explorewithme.comments.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Comment {

    @Id
    private Long id;
}