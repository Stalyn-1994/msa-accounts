package com.devsu.accounts.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "movements", schema = "account")
public class  MovementsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date", nullable = false)
  private Date date;

  @Column(name = "type", nullable = false, length = 255)
  private String type;

  @Column(name = "amount")
  private Double amount;

  @Column(name = "balance")
  private Double balance;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_number", referencedColumnName = "id")
  private AccountEntity accountNumber;
}