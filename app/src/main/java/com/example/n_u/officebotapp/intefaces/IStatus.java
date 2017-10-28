package com.example.n_u.officebotapp.intefaces;

import com.example.n_u.officebotapp.models.Status;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IStatus {
    @POST ("add/tag")
    Call<Status> addNewTag(@Body HashMap<String, Object> paramHashMap);

    @POST ("add/group/members")
    Call<Status> getFriendAddInGroupStatus(@Body HashMap<String, Object> paramHashMap);

    @POST ("add/group")
    Call<Status> getGroupAddStatus(@Body HashMap<String, Object> paramHashMap);

    @POST ("delete/group")
    Call<Status> getGroupDeleteStatus(@Body HashMap<String, Object> paramHashMap);

    @POST ("add/request")
    Call<Status> getSendRequestStatus(@Body HashMap<String, Object> paramHashMap);

    @POST ("accept/request")
    Call<Status> verifyRequestAccept(@Body HashMap<String, Object> paramHashMap);

    @POST ("block/request")
    Call<Status> verifyRequestBlock(@Body HashMap<String, Object> paramHashMap);

    @POST ("verify/user/tag")
    Call<Status> verifyTag(@Body HashMap<String, Object> paramHashMap);
}



/* Location:           C:\Users\N_U\Desktop\Tolo\ob\classes30-dex2jar.jar

 * Qualified Name:     com.example.n_u.officebot.intefaces.IStatus

 * JD-Core Version:    0.7.0.1

 */