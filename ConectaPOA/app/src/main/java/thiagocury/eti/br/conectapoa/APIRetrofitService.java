package thiagocury.eti.br.conectapoa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRetrofitService {String BASE_URL = "http://www.portoalegrelivre.com.br/php/services/";

    @GET("WSPoaLivreRedes.php")
    Call<List<Wifi>> getWifiCall();
}
