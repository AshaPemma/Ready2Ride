package com.warrous.ready2ride.data.volley.volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.warrous.ready2ride.Ready2RideApp;
import com.warrous.ready2ride.constants.Constants;


import org.json.JSONObject;

import java.util.Map;


public class VolleyHelperLayer {
    public void startHandlerVolley(final Context context, String URL, JSONObject input, Response.Listener<JSONObject> listener, final ProgressDialog progressDialog, Request.Priority priority) {
        try {
            String tag_json_obj = "json_obj_req";
            Log.d("[Request]", URL);
            Log.d("[INPUT]", input.toString());
            JsonObjectRequestPriority jsonReq = new JsonObjectRequestPriority(Request.Method.POST,
                    URL,
                    input,
                    listener,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                         //   Utility.showToast(context, "network_error", Constants.CONNECTION_ERROR, true);
                            if (progressDialog != null && progressDialog.isShowing()) {
                                try {
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
            //Adding request to request QUEUE
            jsonReq.setPriority(priority);
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Ready2RideApp.getInstance().addToRequestQueue(jsonReq, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startHandlerVolley(String URL, JSONObject input, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Request.Priority priority) {
        try {
            String tag_json_obj = "json_obj_req";
            Log.d("[Request]", URL);
            if (input != null)
                Log.d("[INPUT]", input.toString());
            JsonObjectRequestPriority jsonObjectRequest = new JsonObjectRequestPriority(Request.Method.POST, URL, input, listener, errorListener);
            jsonObjectRequest.setPriority(priority);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Ready2RideApp.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //for Custom request type POST,GET,PUT,DELETE
    public void startHandlerVolley(final Context context, String URL, JSONObject input, Response.Listener<JSONObject> listener, final ProgressDialog progressDialog, Request.Priority priority, int requestType) {
        try {
            String tag_json_obj = "json_obj_req";
            Log.d("[Request]", URL);
            if (input != null) {
                Log.d("[INPUT]", input.toString());
            }
            JsonObjectRequestPriority jsonReq = new JsonObjectRequestPriority(requestType,
                    URL,
                    input,
                    listener,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                           // Utility.showToast(context, "network_error", Constants.CONNECTION_ERROR, true);
                            if (progressDialog != null) {
                                try {
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
            //Adding request to request QUEUE
            jsonReq.setPriority(priority);
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Ready2RideApp.getInstance().addToRequestQueue(jsonReq, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startHandlerVolley(String URL, JSONObject input, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Request.Priority priority, int requestType) {
        try {
            String tag_json_obj = "json_obj_req";
            Log.d("[Request]", URL);
            if (input != null) {
                Log.d("[INPUT]", input.toString());
            }
            JsonObjectRequestPriority jsonObjectRequest = new JsonObjectRequestPriority(requestType, URL, input, listener, errorListener);
            jsonObjectRequest.setPriority(priority);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Ready2RideApp.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startHandlerVolley(String URL, Map<String, String> input, Response.Listener<String> listener, Response.ErrorListener errorListener, Request.Priority priority, int requestType) {
        try {
            String tag_json_obj = "key_value_object";
            Log.d("[Request]", URL);
            if (input != null) {
                Log.d("[INPUT]", input.toString());
            }
            KeyValueObjectRequestPriority keyValueObjectRequest = new KeyValueObjectRequestPriority(requestType, URL, listener, errorListener);
            if (input != null)

                keyValueObjectRequest.setParams(input);
            keyValueObjectRequest.setPriority(priority);
            keyValueObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Ready2RideApp.getInstance().addToRequestQueue(keyValueObjectRequest, tag_json_obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public void startHandlerVolley(String URL, Map<String, String> input,Map<String, String> headers, Response.Listener<String> listener, Response.ErrorListener errorListener, Request.Priority priority, int requestType) {
//        try {
//            String tag_json_obj = "key_value_object";
//            Logger.d("[Request]", URL);
//            if (input != null) {
//                Logger.d("[INPUT]", input.toString());
//            }
//            KeyValueObjectRequestPriority keyValueObjectRequest = new KeyValueObjectRequestPriority(requestType, URL, listener, errorListener);
//            if (input != null)
//
//                keyValueObjectRequest.setParams(input);
//            keyValueObjectRequest.setPriority(priority);
//            keyValueObjectRequest.setHeaders(headers);
//            keyValueObjectRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            VGaragesApplication.getInstance().addToRequestQueue(keyValueObjectRequest, tag_json_obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}