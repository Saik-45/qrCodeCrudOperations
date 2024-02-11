package com.sai.qrCodeGenerator.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QrTable")
public class QrCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name Cannot be blank")
    @Column(name = "qrName")
    private String name;

    @Pattern(regexp="\\d{10}", message="Phone number must be 10 digits")
    @Column(name = "mobileNumber")
    private String phoneNumber;

    @NotBlank(message = "Color Cannot be Blank")
    @Column(name = "color")
    private String color;

    @NotBlank(message = "Address Cannot be Blank")
    @Column(name = "address")
    private String address;

    @Column(name = "qrId")
    private String qrId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getPhoneNumber() {
        return phoneNumber.toUpperCase();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getColor() {
        return color.toUpperCase();
    }

    public void setColor(String color) {
        this.color = color.toUpperCase();
    }

    public String getAddress() {
        return address.toUpperCase();
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();
    }

    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }
}
