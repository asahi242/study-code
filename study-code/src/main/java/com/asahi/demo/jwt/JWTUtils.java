package com.asahi.demo.jwt;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.asahi.demo.jwt.utils.HttpUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JWTUtils {

    public static SecretKey getGeneralKey(){
        String keyword = "admin";
        byte[] bytes = Base64.decodeBase64(keyword);
        SecretKeySpec aes = new SecretKeySpec(bytes, 0, bytes.length, "AES");
        return aes;
    }

    public String getJWT(String id,String jwtStr,long ttl){
        //1.header 签名算法
        final SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        //创建JWT生成的时间
        long millis = System.currentTimeMillis();
        //计算失效时间
        long expMillis = millis-ttl;
        HashMap<String,Object> map = new HashMap<>();
        SecretKey generalKey = getGeneralKey();
        //2.playod
        JwtBuilder builder = Jwts.builder();
        builder.setId(id);
        builder.setSubject(jwtStr);
        builder.setIssuer("admin");
        builder.signWith(hs256,generalKey);
        if (ttl>=0){
            builder.setExpiration(new Date(expMillis));
        }
        return builder.compact();
    }
    public Claims parseJWT(String jwtStr){
        SecretKey generalKey = getGeneralKey();
        Claims claims = Jwts.parser().setSigningKey(generalKey).parseClaimsJws(jwtStr).getBody();
        return claims;
    }
    //解析JWT串中存储的claims信息
    public Claims parseJWT1(String jwt){
        String split = jwt.split("\\.")[1];
        String sb = TextCodec.BASE64URL.decodeToString(split);
        JSONObject parse = JSONUtil.parseObj(sb);
        Set<String> strings = parse.keySet();
        Iterator<String> iterator = strings.iterator();
        Claims claims = new DefaultClaims();
        while (iterator.hasNext()){
            String next = iterator.next();
            claims.put(next,parse.get(next));
        }

        return claims;
    }
    public static void main(String[] args) {
        JWTUtils jwtUtils = new JWTUtils();
//        String jwt = jwtUtils.getJWT("111", "{userid:1,username:asahi}", -1);
//        System.out.println(jwt);
//        Claims claims = jwtUtils.parseJWT(jwt);
//        System.out.println(claims);
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VyaWRcIjpcIjEzXCIsXCJ1c2VyVHlwZVwiOlwiMDdcIixcImZ1bGxOYW1lXCI6XCJhZG1pblwiLFwiY29tcGFueUlEXCI6XCI4MGZkZjNlZmZjOTQ0YmI5YjE4OTg0MWQ2ZDgwNGU4MFwiLFwiY29tcGFueXROYW1lXCI6XCLlvKDlrrblj6Png63lipvpm4blm6Llhazlj7hcIixcInRpbWVTdGFtcFwiOlwiMTU5NTMxMDM1ODEzM1wiLFwidXNlclR5cGVOYW1lXCI6XCLnlKjmmpblrqLmiLdcIixcInVwcGVyQ29tcGFueUlkXCI6XCIwXCIsXCJhcmVhQ29kZVwiOlwiODZcIixcImxvZ2luSWRcIjpcImFkbWluXCIsXCJ4enFoZG1cIjpcIjEzMDcwMzAwMDAwMFwiLFwieHpxaHFjXCI6XCLmsrPljJfnnIHlvKDlrrblj6PluILmoaXopb_ljLpcIn0iLCJpc3MiOiJhZG1pbnVzZXIiLCJleHAiOjE1OTY3ODE1ODcsImlhdCI6MTU5NTMxMDM1OCwianRpIjoiMTMifQ.vUDAyEmAsATALjed0z5vlaBr8zdSwfZFLSWhVUZdUWE";
        Claims claims = jwtUtils.parseJWT1(jwt);
        System.out.println(claims);
//        Map<String,Object> map = new HashMap<>();
//        map.put("params",jwt);
//        String url = "https://localhost:8011/call-nes-center/equipmentInfo/getDeviceListNew";
//        HttpUtils.submitDataByDoPostForJson(String.valueOf(JSONUtil.parse(map)), url);
    }

}
