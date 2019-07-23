package com.warrous.ready2ride.jsonparser;

import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JSONParser {
      InputStream is = null;

      // JSONObject jObj = null;
      // static String json = "";

      public JSONParser () {
      }

      public JSONObject makeHttpRequest (String url, String method, JSONObject obj) {
            String json = "";
            // Making HTTP request
            try {
                  // check for request method
                  DefaultHttpClient httpClient = new DefaultHttpClient();
                  HttpResponse httpResponse = null;
                  HttpEntity httpEntity;

                  if (method == "POST") {
                        HttpPost httpPost = new HttpPost(url);
                        httpPost.setEntity(new StringEntity(obj.toString()));
                        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
                        System.out.println("ITS call JSON PArser");
                        httpResponse = httpClient.execute(httpPost);

                  } else if (method == "GET") {
                        HttpGet httpGet = new HttpGet(url);
                        // httpPost.setEntity(new StringEntity(obj.toString()));
                        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
                        System.out.println("ITS call JSON PArser");
                        httpResponse = httpClient.execute(httpGet);
                  }

                  if (httpResponse != null) {
                        httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }

            try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(
                          is, "iso-8859-1"), 8);
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                  while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                  }
                  is.close();
                  json = sb.toString();
                  System.out.println("hello Json --- " + json);
            } catch (Exception e) {
                  Log.e("Buffer Error", "Error converting result" + e.toString());
            }
            // try parse the string to a JSON object
            JSONObject jObj = null;
            try {
                  jObj = new JSONObject(json);
            } catch (JSONException e) {
                  Log.e("JSON PArser", "Error Parsing data" + e.toString());
            }
            return jObj;
      }

      public String makeHttpRequestPlainText (String url, String method,
                                              JSONObject obj) {
            String json = "";
            // Making HTTP request
            try {
                  // check for request method
                  if (method == "POST") {
                        DefaultHttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(url);

                        httpPost.setEntity(new StringEntity(obj.toString()));
                        httpPost.setHeader("Content-Type",
                                "application/json;charset=UTF-8");
                        System.out.println("ITS call JSON PArser");
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();
                  } else if (method == "GET") {

                        DefaultHttpClient httpClient = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(url);

                        // httpPost.setEntity(new StringEntity(obj.toString()));

                        httpGet.setHeader("Content-Type",
                                "application/json;charset=UTF-8");
                        System.out.println("ITS call JSON PArser");
                        HttpResponse httpResponse = httpClient.execute(httpGet);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();

                  }
            } catch (IOException e) {
                  e.printStackTrace();
            }

            try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(
                          is, "iso-8859-1"), 8);
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                  while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                  }
                  is.close();
                  json = sb.toString();
                  System.out.println("hello Json --- " + json);
            } catch (Exception e) {
                  Log.e("Buffer Error", "Error converting result" + e.toString());
            }

            return json;
      }

      public String makeHttpRequestPlainText (String url, String method,
                                              JSONArray jsonArObject) {
            String json = "";
            // Making HTTP request
            try {
                  // check for request method
                  if (method == "POST") {
                        DefaultHttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(url);

                        httpPost.setEntity(new StringEntity(jsonArObject.toString()));
                        httpPost.setHeader("Content-Type",
                                "application/json;charset=UTF-8");
                        System.out.println("ITS call JSON PArser");
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();
                  } else if (method == "GET") {

                        DefaultHttpClient httpClient = new DefaultHttpClient();
                        HttpGet httpGet = new HttpGet(url);

                        // httpPost.setEntity(new StringEntity(obj.toString()));

                        httpGet.setHeader("Content-Type",
                                "application/json;charset=UTF-8");
                        System.out.println("ITS call JSON PArser");
                        HttpResponse httpResponse = httpClient.execute(httpGet);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();

                  }
            } catch (IOException e) {
                  e.printStackTrace();
            }

            try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(
                          is, "iso-8859-1"), 8);
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                  while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                  }
                  is.close();
                  json = sb.toString();
                  System.out.println("hello Json --- " + json);
            } catch (Exception e) {
                  Log.e("Buffer Error", "Error converting result" + e.toString());
            }

            return json;
      }

      public JSONObject makeHttpRequestFormData (String url, String method,
                                                 List<NameValuePair> nameValuePairs) {
            String json = "";
            // Making HTTP request
            try {
                  // check for request method

                  DefaultHttpClient httpClient = new DefaultHttpClient();
                  HttpPost httpPost = new HttpPost(url);

                  // httpPost.setEntity(new StringEntity(obj.toString()));

                  httpPost.setHeader("Content-Type",
                          "application/x-www-form-urlencoded;charset=UTF-8");

                  System.out.println("ITS call JSON PArser");

			/*
             * List<NameValuePair> nameValuePairs = new
			 * ArrayList<NameValuePair>(2); nameValuePairs.add(new
			 * BasicNameValuePair("id", "12345")); nameValuePairs.add(new
			 * BasicNameValuePair("stringdata", "AndDev is Cool!"));
			 */
                  httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                  HttpResponse httpResponse = httpClient.execute(httpPost);
                  HttpEntity httpEntity = httpResponse.getEntity();
                  is = httpEntity.getContent();
            } catch (IOException e) {
                  e.printStackTrace();
            }

            try {
                  BufferedReader reader = new BufferedReader(new InputStreamReader(
                          is, "iso-8859-1"), 8);
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                  while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                  }
                  is.close();
                  json = sb.toString();
                  System.out.println("hello Json --- " + json);
            } catch (Exception e) {
                  Log.e("Buffer Error", "Error converting result" + e.toString());
            }

            // try parse the string to a JSON object
            JSONObject jObj = null;
            try {
                  jObj = new JSONObject(json);
            } catch (JSONException e) {
                  Log.e("JSON PArser", "Error Parsing data" + e.toString());
            }
            return jObj;

            // return json;
      }

}