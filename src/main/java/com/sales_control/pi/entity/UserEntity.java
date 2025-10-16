package com.sales_control.pi.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.sales_control.pi.enumeration.UserTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "usuario")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "login", unique = true, nullable = false)
  private String userName;

  @Column(name = "nome")
  private String name;

  @Column(name = "senha", nullable = false)
  private String passwordHash;

  @Column(name = "tipo")
  @Enumerated(STRING)
  private UserTypeEnum userType;
}
