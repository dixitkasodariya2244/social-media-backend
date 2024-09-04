package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDTO {
    private Long id;
    private String text;
    private LocalDateTime timestamp;
//    // Add this line to include uploaded files
//    private List<UploadDTO> uploads;

}
