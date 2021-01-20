package com.bartmont.VetClinicREST.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelAppointmentDTO {
    private Long appointmentId;
    private String CustomerID;
    private String CustomerPIN;
}
