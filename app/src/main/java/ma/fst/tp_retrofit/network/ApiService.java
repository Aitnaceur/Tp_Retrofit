package ma.fst.tp_retrofit.network;

import java.util.List;

import ma.fst.tp_retrofit.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();
}