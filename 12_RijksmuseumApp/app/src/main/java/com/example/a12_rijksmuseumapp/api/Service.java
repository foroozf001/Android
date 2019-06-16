package com.example.a12_rijksmuseumapp.api;

import com.example.a12_rijksmuseumapp.model.ArtResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("api/en/collection")
    Call<ArtResponse> getAllArtPieces(
            @Query("key") String apiKey,
            @Query("format") String format,
            @Query("ps") int ps,
            @Query("q") String query,
            @Query("s") String sort,
            @Query("imgonly") boolean imgOnly

    );
}
