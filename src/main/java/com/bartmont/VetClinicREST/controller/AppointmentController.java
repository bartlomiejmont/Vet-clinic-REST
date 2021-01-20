package com.bartmont.VetClinicREST.controller;

import com.bartmont.VetClinicREST.DTO.CancelAppointmentDTO;
import com.bartmont.VetClinicREST.DTO.MakeAppointmentDTO;
import com.bartmont.VetClinicREST.entity.Appointment;
import com.bartmont.VetClinicREST.exeptions.NotAuthorizedExeption;
import com.bartmont.VetClinicREST.exeptions.NotFoundExeption;
import com.bartmont.VetClinicREST.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/api/appointment")
@Validated
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @RequestMapping(path = "/make", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MakeAppointmentDTO> create(@RequestBody MakeAppointmentDTO makeAppointmentDTO) {
        MakeAppointmentDTO appointment;
        try {
            appointment = appointmentService.makeAppointment(makeAppointmentDTO);
        } catch (NotFoundExeption exeption) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointment);
    }

    @RequestMapping(path = "/get/{doctorId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Appointment>> getAppointments(@PathVariable Long doctorId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Collection<Appointment> appointments;
        try {
            appointments = appointmentService.getAllDoctorApointments(doctorId, date);
        } catch (NotFoundExeption exeption) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(appointments);
    }

    @RequestMapping(path = "/cancel", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Appointment> cancel (@RequestBody CancelAppointmentDTO cancelAppointmentDTO){
        Appointment appointment;
        try {
            appointment = appointmentService.cancel(cancelAppointmentDTO);
        } catch (NotFoundExeption exeption) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (NotAuthorizedExeption exeption) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(appointment);
    }
}
