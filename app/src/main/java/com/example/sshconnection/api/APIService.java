package com.example.sshconnection.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {

    @GET
    public Observable<String> getCommandSSH(@Url String url);

}