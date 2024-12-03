// 2024/12/3
// الحمد لله رب العالمين، والصلاة والسلام على سيدنا محمد الصادق الوعد الأمين
// سبحانك لا علم لنا إلا ما علمتنا إنك أنت العليم الحكيم
//  بداية العمل على مشروع التخرج, نسأل الله التوفيق والسداد
package com.ibrahimRamadan.forexAPI.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users") // setting the table name in db
public class User {
    @Id // it's like saying userId is our pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incrementing pk userId
    private long userId;
    private String username;
    private String email;

}
