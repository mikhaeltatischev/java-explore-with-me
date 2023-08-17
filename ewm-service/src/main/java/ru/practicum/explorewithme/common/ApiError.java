package ru.practicum.explorewithme.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private List<String> error;
    private String message;
    private String reason;
    private String status;
    private String timestamp;
}