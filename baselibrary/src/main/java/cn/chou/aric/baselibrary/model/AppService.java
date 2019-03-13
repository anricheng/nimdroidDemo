package cn.chou.aric.baselibrary.model;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface AppService {

    @GET("/getSongPoetry")
    Observable<Peotry> getPoetry(@Query("page") int page,@Query("count") int count);
}
