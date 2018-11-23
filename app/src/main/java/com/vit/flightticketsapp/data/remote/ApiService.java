package com.vit.flightticketsapp.data.remote;

import com.vit.flightticketsapp.data.model.Price;
import com.vit.flightticketsapp.data.model.Ticket;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("airline-tickets.php")
    Single<List<Ticket>> getTickets(@Query("from") String from,
                                       @Query("to") String to);

    @GET("airline-tickets-price.php")
    Single<Price> getPrice(@Query("flight_number") String flightNumber,
                           @Query("from") String from,
                           @Query("to") String to);

}
