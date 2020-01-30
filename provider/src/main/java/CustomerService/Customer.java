package CustomerService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private long id;
    private String name;
    private String address;
    private Date dateOfBirth;
    private String email;
    private CustomerStatus status;
}