package ru.practicum.explorewithme.event.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;
    private String status;
}