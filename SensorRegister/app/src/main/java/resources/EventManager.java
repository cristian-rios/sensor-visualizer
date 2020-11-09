package resources;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.sensorregister.R;
import com.example.sensorregister.requestUtilities.event.EventRequest;
import com.example.sensorregister.requestUtilities.event.EventResponse;
import com.example.sensorregister.requestUtilities.services.RequestService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventManager {
    private Context context;
    private  RequestService service;
    private String token;

    public EventManager setToken(String token) {
        this.token = token;
        return this;
    }

    public EventManager(Context applicationContext, RequestService eventService) {
        context = applicationContext;
        service = eventService;
    }
    public void post(EventRequest request) {
        Call<EventResponse> call = service.event("Bearer" + token, request);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(context.getString(R.string.eventTagLog), context.getString(R.string.eventSuccessMsgLog));
                    Toast.makeText(context, request.getDescription(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(context.getString(R.string.eventTagLog), context.getString(R.string.eventErrorMsgLog));
                    try {
                        JSONObject jsonResponse = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jsonResponse.getString(context.getString(R.string.errorResponseMsgKey)), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(context.getString(R.string.exception), e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e(context.getString(R.string.eventTagLog), t.getMessage());
            }
        });
    }
}
