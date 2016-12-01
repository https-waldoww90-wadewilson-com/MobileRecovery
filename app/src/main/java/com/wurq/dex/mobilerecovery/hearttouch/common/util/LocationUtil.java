package com.wurq.dex.mobilerecovery.hearttouch.common.util;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by ht-template
 **/
public class LocationUtil {
    private static Map<String, Integer> sProvice2Code = new LinkedHashMap<>();
    private static Map<String, Map<String, Integer>> sProvice2Citys = new LinkedHashMap<>();
    private static Map<String, Map<String, Map<String, Integer>>> sProvice2Districts = new LinkedHashMap<>();
    //    private static Map<Integer, String> sCode2Province = new HashMap<>();
//    private static Map<String, String> sCode2City = new HashMap<>(); // code = province code + city code
//    private static Map<String, String> sCode2District = new HashMap<>(); // code = province code + city code + district code
    private static LocationUtil sInstance;

    private LocationUtil() {
        readLocationData();
    }

    public static void init() {
        if (sInstance == null) {
            synchronized (LocationUtil.class) {
                if (sInstance == null) {
                    sInstance = new LocationUtil();
                }
            }
        }
    }

    public static Set<String> getProvices() {
        init();
        return sProvice2Code.keySet();
    }

//    public static String getProvince(int code) {
//        initView();
//        if (sCode2Province.containsKey(code)) {
//            return sCode2Province.get(code);
//        }
//
//        for (Map.Entry<String, Integer> entry : sProvice2Code.entrySet()) {
//            if (entry.getValue() == code) {
//                sCode2Province.put(entry.getValue(), entry.getKey());
//                return entry.getKey();
//            }
//        }
//
//        return null;
//    }

    public static int getProviceCode(String provice) {
        init();
        return sProvice2Code.get(provice);
    }

    public static Set<String> getCities(String provice) {
        init();

        Map<String, Integer> city2Code = sProvice2Citys.get(provice);
        if (city2Code == null) return null;

        return city2Code.keySet();
    }

    public static int getCityCode(String province, String city) {
        init();

        Map<String, Integer> city2Code = sProvice2Citys.get(province);
        if (city2Code == null) {
            throw new RuntimeException();
        }

        return city2Code.get(city);
    }

    public static int getDistrictCode(String province, String city, String district) throws RuntimeException {
        init();
        Map<String, Map<String, Integer>> city2District = sProvice2Districts.get(province);
        if (city2District == null) throw new RuntimeException();
        Map<String, Integer> district2Code = city2District.get(city);
        if (district2Code == null) throw new RuntimeException();
        return district2Code.get(district);
    }

//    public static String getCity(int provinceCode, int cityCode) {
//        initView();
//
//        String cityKey = "prov" + provinceCode + "city" + cityCode;
//        if (sCode2City.containsKey(cityKey))
//            return sCode2City.get(cityKey);
//
//        String province = getProvince(provinceCode);
//        if (province == null) return null;
//
//        Map<String, Integer> city2Code = sProvice2Citys.get(province);
//        if (city2Code == null) return null;
//
//        for (Map.Entry<String, Integer> entry : city2Code.entrySet()) {
//            if (entry.getValue() == cityCode) {
//                sCode2City.put(cityKey, entry.getKey());
//                return entry.getKey();
//            }
//        }
//
//        return null;
//    }

    public static Set<String> getDistricts(String province, String city) {
        init();

        Map<String, Map<String, Integer>> city2Districts = sProvice2Districts.get(province);
        if (city2Districts == null) return null;

        Map<String, Integer> district2Code = city2Districts.get(city);

        if (district2Code == null) return null;

        return district2Code.keySet();
    }

    private void readLocationData() throws RuntimeException
    {
//        try {
//      //      Return an AssetManager instance for your application's package
//            InputStream is = AppProfile.getContext().getAssets().open("location.json");
//            int size = is.available();
//
//            // Read the entire asset into a local byte buffer.
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            // Convert the buffer into a string.
//            String jsonString = new String(buffer, "UTF-8");
//            JSONObject jsonObj = JSONObject.parseObject(jsonString);
//
//            JSONArray provJsonArray = jsonObj.getJSONArray("prov");
//            JSONArray provCodeJsonArray = jsonObj.getJSONArray("code");
//            final int provSize = provJsonArray.size();
//            for (int i = 0; i < provSize; ++i) {
//                String prov = provJsonArray.getString(i);
//                Integer provCode = provCodeJsonArray.getInteger(i);
//                sProvice2Code.put(prov, provCode);
//
//                JSONArray cityJsonArray = jsonObj.getJSONArray("prov" + provCode);
//                JSONArray cityCodeJsonArray = jsonObj.getJSONArray("code" + provCode);
//                Map<String, Integer> city2Code = new LinkedHashMap<>();
//                Map<String, Map<String, Integer>> city2District = new LinkedHashMap<>();
//                final int cityArraySize = cityJsonArray.size();
//                for (int j = 0; j < cityArraySize; ++j) {
//                    String city = cityJsonArray.getString(j);
//                    Integer cityCode = cityCodeJsonArray.getInteger(j);
//
//                    JSONArray districtJsonArray = jsonObj.getJSONArray("prov" + provCode + "city" + cityCode);
//                    JSONArray districtCodeJsonArray = jsonObj.getJSONArray("code" + "prov" + provCode + "city" + cityCode);
//
//                    Map<String, Integer> district2Code = new LinkedHashMap<>();
//                    final int districtArraySize = districtJsonArray != null ? districtJsonArray.size() : 0;
//                    for (int k = 0; k < districtArraySize; k++) {
//                        String district = districtJsonArray.getString(k);
//                        Integer districtCode = districtCodeJsonArray.getInteger(k);
//                        district2Code.put(district, districtCode);
//                    }
//                    city2Code.put(city, cityCode);
//                    city2District.put(city, district2Code);
//                }
//                sProvice2Citys.put(prov, city2Code);
//                sProvice2Districts.put(prov, city2District);
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}

