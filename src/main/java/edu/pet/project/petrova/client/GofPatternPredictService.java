package edu.pet.project.petrova.client;

import edu.pet.project.petrova.model.FileForPrediction;
import edu.pet.project.petrova.model.PatternPrediction;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Slf4j
public class GofPatternPredictService {
    private final GofPatternPredictApi gofPatternPredictApi;

    public GofPatternPredictService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gofPatternPredictApi = retrofit.create(GofPatternPredictApi.class);
    }

    @Nullable
    public PatternPrediction predict(FileForPrediction fileForPrediction) {
        try {
            return gofPatternPredictApi.predictPattern(fileForPrediction).execute().body();
        } catch (IOException e) {
            log.error("Error while executing request: {}", e.toString());
            return null;
        }
    }
}
