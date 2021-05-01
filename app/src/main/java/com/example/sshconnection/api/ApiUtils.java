package com.example.sshconnection.api;

public class ApiUtils {
    private ApiUtils() {}

    public static APIService getAPIService() {
        return RetrofitClient.getClient("http://localhost/").create(APIService.class);
    }


}
