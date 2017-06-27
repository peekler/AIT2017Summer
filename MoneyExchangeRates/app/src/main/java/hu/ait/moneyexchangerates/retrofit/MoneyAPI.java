package hu.ait.moneyexchangerates.retrofit;

import hu.ait.moneyexchangerates.data.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoneyAPI {

    //http://api.fixer.io/latest?base=USD

    @GET("latest")
    Call<MoneyResult> getRatesToUSD(@Query("base") String base);

}
