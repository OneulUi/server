package com.swyg.oneului.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressPosition {
    String nx;
    String ny;

    @Builder
    public AddressPosition(String nx, String ny) {
        this.nx = nx;
        this.ny = ny;
    }
}
