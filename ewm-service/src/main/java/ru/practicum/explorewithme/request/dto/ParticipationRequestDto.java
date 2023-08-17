package ru.practicum.explorewithme.request.dto;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
public final class ParticipationRequestDto {

    private final Long id;
    private final String created;
    private final Long event;
    private final Long requester;
    private final String status;
}