package com.server.util.upper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonSplit {
    private String json;
    private static String UUID = "uuid";
    private static String ADM = "adm";
    private static String BID = "bid";
    private static String BROG = "BROG";

    private static String DEVICESTATUS = "devicestatus";

    private JsonParser jsonParser = new JsonParser();

    private JsonObject jsonObject;
    public JsonSplit(String json){
        jsonObject = (JsonObject)jsonParser.parse(json);
        this.json=json;
    }

    public String getBROG(){
        return String.valueOf(jsonObject.get(BROG));
    }
    public String getDEVICESTATUS(){return String.valueOf(jsonObject.get(DEVICESTATUS));}
    public  String getUUID() {
        return String.valueOf(jsonObject.get(UUID));
    }

    public  String getADM() {
        return String.valueOf(jsonObject.get(ADM));
    }
    public String getBID(){
        return String.valueOf(jsonObject.get(BID));
    }
    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

}
