package com.example.a12_rijksmuseumapp.viewModel;

import com.example.a12_rijksmuseumapp.api.Client;
import com.example.a12_rijksmuseumapp.api.Service;
import com.example.a12_rijksmuseumapp.model.ArtResponse;

import retrofit2.Call;

public class Repository {
    Service apiService = Client.create();

    public Call<ArtResponse> getAllArtPieces(String apiKey, String format, int ps, String query, String sort, boolean imgonly) {
        return apiService.getAllArtPieces(apiKey, format, ps, query, sort, imgonly);
    }
}
