package org.project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CoreStock {

    private String symbol;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Double volumeCurr;
    private Double volumeUsd;
    private LocalDateTime date;

}
