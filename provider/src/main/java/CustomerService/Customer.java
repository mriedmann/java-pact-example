package CustomerService;

import java.util.Date;

public class Customer {

    private final long id;
    private final String name;
    private final String address;
    private final Date dateOfBirth;
    private final String emailAddress;
    private final CustomerStatus status;

    public Customer(long id, String name, String address, Date dateOfBirth, String emailaddress, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailaddress;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public CustomerStatus getStatus() {
        return status;
    }

}