// 2024/12/3
// الحمد لله رب العالمين، والصلاة والسلام على سيدنا محمد الصادق الوعد الأمين
// سبحانك لا علم لنا إلا ما علمتنا إنك أنت العليم الحكيم
//  بداية العمل على مشروع التخرج, نسأل الله التوفيق والسداد
package com.ibrahimRamadan.forexAPI.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users") // setting the table name in db
@Data
public class UserEntity {
    @Id // it's like saying userId is our pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incrementing pk userId
    @Column(name = "user_id")
    private long userId;
    @Column(nullable = false, unique = true) // username can't be null, and also should be unique
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    )
    private List<Role> roles = new ArrayList<>();



}
