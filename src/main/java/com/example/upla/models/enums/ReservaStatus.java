package com.example.upla.models.enums;

public enum ReservaStatus {
    PENDING,    // Just created
    CONFIRMED,  // Validated and paid
    CANCELLED,  // User decided not to come
    COMPLETED   // Stay is over
}