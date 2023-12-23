package com.example.reservation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservationService {
    @POST("Reservations")
    Call<Void> createReservation(@Body Reservation reservation);
}

