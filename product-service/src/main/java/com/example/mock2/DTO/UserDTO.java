package com.example.mock2.DTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private long userId;
    private String username;


    @NotBlank(message = "user address is not blank")
    private String userAddress;
    @NotBlank(message = "user full name is not blank")
    private String userFullname;
    @Pattern(regexp = "0[0-9]{9}",message = "phone number is invalid . format: 0123456789 -> 10 numbers start with 0")
    private String userPhone;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "user email is invalid. form: jwhite@domain.com")
//    @Email
    private String userEmail;
//    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$",
//    message = "user dob is invalid. must be  yyyy-mm-dd")
    private Date userDob;
    @Pattern(regexp = "[MFmf]{1}",message = "user Gender must be M or m(Male), F or f(Female)" )

    private String userGender;

//    public User convertToUser(){
//        return new User( userId,  username,  userAddress,  userFullname,  userPhone,  userEmail,  userDob, userGender);
//    }
}
