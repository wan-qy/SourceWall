package net.nashlegend.sourcewall.swrequest.parsers;

import net.nashlegend.sourcewall.swrequest.JsonHandler;
import net.nashlegend.sourcewall.swrequest.ResponseObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NashLegend on 2015/9/23 0023.
 */
public class JsonObjectParser implements Parser<JSONObject> {
    @Override
    public JSONObject parse(String str, ResponseObject<JSONObject> responseObject) throws
            JSONException {
        return JsonHandler.getUniversalJsonObject(str, responseObject);
    }
}
