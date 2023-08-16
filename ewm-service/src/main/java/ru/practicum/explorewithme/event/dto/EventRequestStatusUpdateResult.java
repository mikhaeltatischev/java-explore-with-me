package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class EventRequestStatusUpdateResult {

    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}