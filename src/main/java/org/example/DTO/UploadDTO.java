package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadDTO {
    private Long id;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private byte[] imageData; // Optional: If you want to include binary data
}
