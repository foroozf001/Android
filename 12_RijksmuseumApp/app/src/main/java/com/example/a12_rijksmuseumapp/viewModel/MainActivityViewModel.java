package com.example.a12_rijksmuseumapp.viewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.a12_rijksmuseumapp.MainActivity;
import com.example.a12_rijksmuseumapp.model.Art;
import com.example.a12_rijksmuseumapp.model.ArtResponse;

import static com.example.a12_rijksmuseumapp.MainActivity.pd;
import static com.example.a12_rijksmuseumapp.MainActivity.swipeContainer;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<List<Art>> artPieces = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private Repository repository = new Repository();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getError() { return error; }

    public LiveData<List<Art>> getAllArtPieces() { return artPieces; }

    public void getAllArtPieces(String apiKey, String format, int ps, String query, String sort, boolean imgonly) {
        repository
                .getAllArtPieces(apiKey, format, ps, query, sort, imgonly)
                .enqueue(new Callback<ArtResponse>() {
                    @Override
                    public void onResponse(Call<ArtResponse> call, Response<ArtResponse> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            artPieces.setValue(response.body().getArtObjects());
                        }
                    }
                    @Override
                    public void onFailure(Call<ArtResponse> call, Throwable t) {
                        error.setValue("API error: " + t.getMessage());
                    }
                });
    }
}
