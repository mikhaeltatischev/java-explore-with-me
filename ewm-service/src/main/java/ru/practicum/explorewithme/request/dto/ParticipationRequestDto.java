package ru.practicum.explorewithme.request.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class ParticipationRequestDto {

    private Long id;
    private String created;
    private Long event;
    private Long requester;
    private String status;
}