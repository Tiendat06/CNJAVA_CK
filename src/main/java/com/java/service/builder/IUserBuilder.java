package com.java.service.builder;

import com.java.models.User;

import java.sql.Date;

public interface IUserBuilder {
    IUserBuilder setUserIdBuilder(String user_id);
    IUserBuilder setFirstNameBuilder(String first_name);
    IUserBuilder setLastNameBuilder(String lastName);
    IUserBuilder setEmailBuilder(String email);
    IUserBuilder setPhoneNumberBuilder(String phone_number);
    IUserBuilder setAddressBuilder(String address);
    IUserBuilder setImgBuilder(String img);
    IUserBuilder setAccIdBuilder(String acc_id);
    IUserBuilder setBirthdayBuilder(Date dob);
    IUserBuilder setGenderBuilder(String gender);
    User build();

}
