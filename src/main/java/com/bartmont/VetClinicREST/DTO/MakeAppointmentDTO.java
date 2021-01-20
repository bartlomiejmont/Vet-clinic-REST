package com.bartmont.VetClinicREST.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MakeAppointmentDTO {
    private LocalDateTime dateTime;

    private Long customerId;

    private Long doctorId;
}
