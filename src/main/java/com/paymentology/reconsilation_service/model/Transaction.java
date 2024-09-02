package com.paymentology.reconsilation_service.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @CsvBindByPosition(position = 0)
    private String profileName;

    @CsvBindByPosition(position = 1)
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @CsvBindByPosition(position = 2)
    @CsvNumber("#.##")
    private int amount;

    @CsvBindByPosition(position = 3)
    private String narrative;

    @CsvBindByPosition(position = 4)
    private String description;

    @CsvBindByPosition(position = 5)
    private String id;

    @CsvBindByPosition(position = 6)
    private String type;

    @CsvBindByPosition(position = 7)
    private String walletReference;

}
