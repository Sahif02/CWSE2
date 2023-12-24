package com.example.reservation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.List;
import retrofit2.http.GET;

public interface ReservationService {
    @POST("Reservations")
    Call<Void> createReservation(@Body Reservation reservation);

    @GET("Reservations") // Replace with the actual endpoint
    Call<List<Reservation>> getReservation();
}

