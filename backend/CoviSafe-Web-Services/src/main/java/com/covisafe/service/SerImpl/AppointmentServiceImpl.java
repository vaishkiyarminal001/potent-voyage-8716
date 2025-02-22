package com.covisafe.service.SerImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covisafe.exception.AppointmentNotFoundException;
import com.covisafe.modal.Appointment;
import com.covisafe.modal.Member;
import com.covisafe.modal.Slot;
import com.covisafe.modal.VaccinationCenter;
import com.covisafe.repository.AppointmentRepository;
import com.covisafe.repository.MemberRepository;
import com.covisafe.repository.VaccinationCenterRepository;
import com.covisafe.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private VaccinationCenterRepository vaccinationCenterRepository;

	@Override
	public Appointment getAppointmentDetails(String bookingId) {
//		Optional<Appointment> optional = appointmentRepository.findById(bookingId);
//		if(optional.isEmpty()) throw new AppointmentNotFoundException("Appointment Not Found by Given Id, Please get an Appointment first");
		Appointment appointment = appointmentRepository.findById(bookingId)
				.orElseThrow(() -> new AppointmentNotFoundException(
						"Appointment Not Found by Given Id, Please get an Appointment first"));
		return appointment;
	}

	@Override
	public Appointment addAppointment(String memberid, String vaxcenterid, Appointment appointment) {
		if (appointment == null)
			throw new AppointmentNotFoundException("Appointment couldn't saved");

		Member member = memberRepository.findById(memberid).orElseThrow(
				() -> new AppointmentNotFoundException("Please Provide a valid MemberId to book an Appointment"));
		VaccinationCenter vaxCenter = vaccinationCenterRepository.findById(vaxcenterid)
				.orElseThrow(() -> new AppointmentNotFoundException(
						"Please Provide a valid Vaccination center id to book an Appointment"));

		member.setAppointment(appointment);
//		memberRepository.save(member);

		vaxCenter.getAppointments().add(appointment);
//		vaccinationCenterRepository.save(vaxCenter);

		appointment.setMemberId(member);
		appointment.setVaxCenter(vaxCenter);
		appointment.setSlot(appointment.getSlot());
		Appointment appoint = appointmentRepository.save(appointment);
		return appoint;
	}

	@Override
	public Appointment updateAppointment(String bookingId, Appointment appointment) {
		if (bookingId == null)
			throw new AppointmentNotFoundException("Please provide Appointment id you want to update");
		if (appointment == null)
			throw new AppointmentNotFoundException("Please provide Updated Appointment details");
		appointment.setBookingId(bookingId);
		return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment deleteAppointment(String bookingId) {
		if (bookingId == null)
			throw new AppointmentNotFoundException("Please provide Appointment id you want to delete");
		Optional<Appointment> optional = appointmentRepository.findById(bookingId);
		if (optional.isEmpty())
			throw new AppointmentNotFoundException("Appointment Not Found");
		Appointment appointment = optional.get();
		appointmentRepository.delete(appointment);
		return appointment;
	}

}
