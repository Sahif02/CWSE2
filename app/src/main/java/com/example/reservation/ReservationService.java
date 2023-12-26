package com.example.reservation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReservationService {
    @POST("Reservations")
    Call<Void> createReservation(@Body Reservation reservation);

    @GET("Reservations")
    Call<List<Reservation>> getReservation();

    @PUT("Reservations/{id}")
    Call<Void> updateHistoryItem(@Path("id") int historyItemId, @Body Reservation updatedHistoryItem);
}

