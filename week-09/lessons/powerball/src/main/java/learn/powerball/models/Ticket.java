package learn.powerball.models;

import learn.powerball.validation.NoDuplicatePicks;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@NoDuplicatePicks(message = "Duplicate picks are not allowed.")
public class Ticket {

    @NotBlank(message = "Customer name is required.")
    @Size(max = 50, message = "Customer name cannot be greater than 50 characters.")
    private String customerName;

    @NotBlank(message = "Customer email is required.")
    @Email(message = "Customer email must be a valid email address.")
    private String customerEmail;

    @NotNull(message = "Purchase date is required.")
    @PastOrPresent(message = "Purchase date cannot be in the future.")
    private LocalDate purchaseDate;

    @Min(value = 1, message = "Pick 1 must be between 1 and 69.")
    @Max(value = 69, message = "Pick 1 must be between 1 and 69.")
    private int pick1;

    @Min(value = 1, message = "Pick 2 must be between 1 and 69.")
    @Max(value = 69, message = "Pick 2 must be between 1 and 69.")
    private int pick2;

    @Min(value = 1, message = "Pick 3 must be between 1 and 69.")
    @Max(value = 69, message = "Pick 3 must be between 1 and 69.")
    private int pick3;

    @Min(value = 1, message = "Pick 4 must be between 1 and 69.")
    @Max(value = 69, message = "Pick 4 must be between 1 and 69.")
    private int pick4;

    @Min(value = 1, message = "Pick 5 must be between 1 and 69.")
    @Max(value = 69, message = "Pick 5 must be between 1 and 69.")
    private int pick5;

    @Min(value = 1, message = "The Powerball must be between 1 and 26.")
    @Max(value = 69, message = "The Powerball must be between 1 and 26.")
    private int powerball;

    public Ticket() {
    }

    public Ticket(String customerName, String customerEmail, LocalDate purchaseDate,
                  int pick1, int pick2, int pick3, int pick4, int pick5, int powerball) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.purchaseDate = purchaseDate;
        this.pick1 = pick1;
        this.pick2 = pick2;
        this.pick3 = pick3;
        this.pick4 = pick4;
        this.pick5 = pick5;
        this.powerball = powerball;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPick1() {
        return pick1;
    }

    public void setPick1(int pick1) {
        this.pick1 = pick1;
    }

    public int getPick2() {
        return pick2;
    }

    public void setPick2(int pick2) {
        this.pick2 = pick2;
    }

    public int getPick3() {
        return pick3;
    }

    public void setPick3(int pick3) {
        this.pick3 = pick3;
    }

    public int getPick4() {
        return pick4;
    }

    public void setPick4(int pick4) {
        this.pick4 = pick4;
    }

    public int getPick5() {
        return pick5;
    }

    public void setPick5(int pick5) {
        this.pick5 = pick5;
    }

    public int getPowerball() {
        return powerball;
    }

    public void setPowerball(int powerball) {
        this.powerball = powerball;
    }

    public Set<ConstraintViolation<Ticket>> validateTicket() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator(); // Returns a Hibernate validator hidden behind an interface.
        Set<ConstraintViolation<Ticket>> violations = validator.validate(this);

        return violations;
    }
}