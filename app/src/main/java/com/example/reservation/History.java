package com.example.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class History extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Make API call and populate the RecyclerView
        fetchHistoryData();

        return view;
    }

    private void fetchHistoryData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReservationService apiService = retrofit.create(ReservationService.class);
        Call<List<Reservation>> call = apiService.getReservation();

        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> historyItems = response.body();
                    // Populate the RecyclerView with history items
                    historyAdapter = new HistoryAdapter(historyItems);
                    recyclerView.setAdapter(historyAdapter);
                } else {
                    // Handle error response
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}