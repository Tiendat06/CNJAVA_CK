package com.java.builder;

import com.java.models.User;

import java.sql.Date;
public class UserBuilder implements IUserBuilder{
    private String user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String address;
    private String image;
    private String account_id;
    private Date birthday;
    private String gender;


    @Override
    public IUserBuilder setUserIdBuilder(String user_id) {
        this.user_id = user_id;
        return this;
    }

    @Override
    public IUserBuilder setFirstNameBuilder(String first_name) {
        this.first_name = first_name;
        return this;
    }

    @Override
    public IUserBuilder setLastNameBuilder(String lastName) {
        this.last_name = lastName;
        return this;
    }

    @Override
    public IUserBuilder setEmailBuilder(String email) {
        this.email = email;
        return this;
    }

    @Override
    public IUserBuilder setPhoneNumberBuilder(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    @Override
    public IUserBuilder setAddressBuilder(String address) {
        this.address = address;
        return this;
    }

    @Override
    public IUserBuilder setImgBuilder(String img) {
        this.image = img;
        return this;
    }

    @Override
    public IUserBuilder setAccIdBuilder(String acc_id) {
        this.account_id = acc_id;
        return this;
    }

    @Override
    public IUserBuilder setBirthdayBuilder(Date dob) {
        this.birthday = dob;
        return this;
    }

    @Override
    public IUserBuilder setGenderBuilder(String gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public User build() {
        return new User(user_id, first_name, last_name, email, phone_number, address, image, account_id, birthday, gender);
    }
}
