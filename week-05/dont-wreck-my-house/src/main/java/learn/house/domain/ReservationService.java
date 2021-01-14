package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.data.HostRepository;
import learn.house.data.ReservationRepository;
import learn.house.models.Host;
import learn.house.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository repository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.repository = repository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findByHost(Host host) {
        return repository.findByHost(host);
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        reservation.setTotal(reservation.calculateTotal());
        Result<Reservation> result = validate(reservation);

        if (!result.isSuccess()) {
            return result;
        }

        boolean overlapsAnotherReservation = checkForOverlap(reservation);
        if (overlapsAnotherReservation) {
            result.addErrorMessage("Reservation conflicts with an existing reservation.");
            return result;
        }

        result.setPayload(repository.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        reservation.setTotal(reservation.calculateTotal());
        Result<Reservation> result = validateForAltering(reservation);

        if (!result.isSuccess()) {
            return result;
        }

        boolean overlapsAnotherReservation = checkForOverlap(reservation);
        if (overlapsAnotherReservation) {
            result.addErrorMessage("Reservation conflicts with an existing reservation.");
            return result;
        }

        boolean success = repository.update(reservation);
        if (!success) {
            result.addErrorMessage(String.format("Reservation Id %s not found for Host '%s'.",
                    reservation.getId(), reservation.getHost().getEmail()));
        }

        result.setPayload(reservation);
        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = validateForAltering(reservation);

        if (!result.isSuccess()) {
            return result;
        }

        boolean success = repository.delete(reservation);
        if (!success) {
            result.addErrorMessage(String.format("Reservation Id %s not found for Host '%s'.",
                    reservation.getId(), reservation.getHost().getEmail()));
        }

        result.setPayload(reservation);
        return result;
    }

    private Result<Reservation> validate(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        validateNulls(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateDates(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);
        return result;
    }

    private Result<Reservation> validateForAltering(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        validateNulls(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        Reservation reservationExists = checkIfReservationExists(reservation);
        if (reservationExists == null) {
            result.addErrorMessage(String.format("Reservation Id %s not found for Host '%s'.",
                    reservation.getId(), reservation.getHost().getEmail()));
            return result;
        }

        boolean reservationHasPassed = checkIfReservationHasPassed(reservationExists);
        if (reservationHasPassed) {
            result.addErrorMessage("Cannot alter a past reservation.");
            return result;
        }

        boolean reservationIsOngoing = checkIfReservationIsOngoing(reservationExists);
        if (reservationIsOngoing) {
            result.addErrorMessage("Cannot alter a reservation that has already started.");
            return result;
        }

        validateDates(reservation, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(reservation, result);
        return result;
    }

    private void validateNulls(Reservation reservation, Result<Reservation> result) {
        if (reservation == null) {
            result.addErrorMessage("No reservation to save.");
            return;
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Reservation host is required.");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Reservation guest is required.");
        }

        if (reservation.getStartDate() == null) {
            result.addErrorMessage("Reservation start date is required.");
        }

        if (reservation.getEndDate() == null) {
            result.addErrorMessage("Reservation end date is required.");
        }
    }

    private void validateDates(Reservation reservation, Result<Reservation> result) {
        if (reservation.getStartDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Reservation start date cannot be in the past.");
        }

        if (!reservation.getEndDate().isAfter(reservation.getStartDate())) {
            result.addErrorMessage("Reservation end date must be after the start date.");
        }
    }

    private void validateChildrenExist(Reservation reservation, Result<Reservation> result) {
        if (reservation.getHost().getId() == null || hostRepository.findById(reservation.getHost().getId()) == null) {
            result.addErrorMessage("Host does not exist.");
        }

        if (reservation.getGuest().getId() <= 0 || guestRepository.findById(reservation.getGuest().getId()) == null) {
            result.addErrorMessage("Guest does not exist.");
        }
    }

    private Reservation checkIfReservationExists(Reservation reservation) {
        return repository.findByHost(reservation.getHost()).stream()
                .filter(r -> reservation.getId() == r.getId())
                .findFirst()
                .orElse(null);
    }

    private boolean checkIfReservationIsOngoing(Reservation reservation) {

        return repository.findByHost(reservation.getHost()).stream()
                .anyMatch(r -> reservation.getStartDate().isBefore(LocalDate.now())
                        && !reservation.getEndDate().isBefore(LocalDate.now()));
    }

    private boolean checkIfReservationHasPassed(Reservation reservation) {
        return repository.findByHost(reservation.getHost()).stream()
                .anyMatch(r -> reservation.getEndDate().isBefore(LocalDate.now()));
    }

    private boolean checkForOverlap(Reservation reservation) {
        return repository.findByHost(reservation.getHost()).stream()
                .anyMatch(r -> reservation.checkForOverlapWith(r) && reservation.getId() != r.getId());
    }
}
