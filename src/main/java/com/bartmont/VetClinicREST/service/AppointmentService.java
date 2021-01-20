package com.bartmont.VetClinicREST.service;

import com.bartmont.VetClinicREST.DTO.CancelAppointmentDTO;
import com.bartmont.VetClinicREST.DTO.MakeAppointmentDTO;
import com.bartmont.VetClinicREST.entity.Appointment;
import com.bartmont.VetClinicREST.entity.Customer;
import com.bartmont.VetClinicREST.exeptions.NotAuthorizedExeption;
import com.bartmont.VetClinicREST.exeptions.NotFoundExeption;
import com.bartmont.VetClinicREST.repository.AppointmentRepository;
import com.bartmont.VetClinicREST.repository.CustomerRepository;
import com.bartmont.VetClinicREST.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentService {

    AppointmentRepository appointmentRepository;
    CustomerRepository customerRepository;
    DoctorRepository doctorRepository;

    public MakeAppointmentDTO makeAppointment (MakeAppointmentDTO makeAppointmentDTO){

        if(doctorRepository.findById(makeAppointmentDTO.getDoctorId()).isEmpty() ||
                customerRepository.findById(makeAppointmentDTO.getCustomerId()).isEmpty())
        {
            throw new NotFoundExeption();
        }
        if(!customerRepository.findById(makeAppointmentDTO.getCustomerId()).get().getCustomerPIN().equals(makeAppointmentDTO.getCustomerPIN())){
            throw new NotAuthorizedExeption();
        }

        Appointment appointment = Appointment.builder()
                .customerId(makeAppointmentDTO.getCustomerId())
                .doctorId(makeAppointmentDTO.getDoctorId())
                .dateTime(makeAppointmentDTO.getDateTime())
                .build();
        appointmentRepository.save(appointment);

        return makeAppointmentDTO;
    }

    public Appointment cancel (CancelAppointmentDTO cancelAppointmentDTO){
        if(appointmentRepository.findById(cancelAppointmentDTO.getAppointmentId()).isEmpty() ||
                appointmentRepository.findById(cancelAppointmentDTO.getAppointmentId()).isEmpty())
        {
            throw new NotFoundExeption();
        }
        Appointment appointment = appointmentRepository.findById(cancelAppointmentDTO.getAppointmentId()).get();
        Customer customer = customerRepository.findByCustomerID(cancelAppointmentDTO.getCustomerID()).get();
        if(!customer.getCustomerPIN().equals(cancelAppointmentDTO.getCustomerPIN())){
            throw new NotAuthorizedExeption();
        }
        appointment.setCanceled(true);
        return appointment;
    }


    public Collection<Appointment> getAllDoctorApointments(Long doctorId, Date date){
        if(doctorRepository.findById(doctorId).isEmpty())
        {
            throw new NotFoundExeption();
        }

        return doctorRepository.findById(doctorId).get().getAppointments()
                .stream()
                .filter(a -> isSameDay(java.sql.Timestamp.valueOf(a.getDateTime()),date))
                .collect(Collectors.toList());
    }


    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }


    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

}
