package edu.pet.project.petrova.client;

import edu.pet.project.petrova.model.FileForPrediction;
import edu.pet.project.petrova.model.PatternPrediction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GofPatternPredictApi {
    @POST("/predict")
    Call<PatternPrediction> predictPattern(@Body FileForPrediction request);
}
