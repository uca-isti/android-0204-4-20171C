package uca.apps.isi.nicamustgo.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.Header;
import uca.apps.isi.nicamustgo.models.Categoria;

/**
 * Created by isi3 on 7/4/2017.
 */

public interface ApiInterface {
    @GET("categoria")
    Call<List<Categoria>> getCategorias();

}
