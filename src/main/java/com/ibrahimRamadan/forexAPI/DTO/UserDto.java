package com.ibrahimRamadan.forexAPI.DTO;

import com.ibrahimRamadan.forexAPI.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long userId;
    private String username;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private String creditCurrency;
    private double credit;
}
