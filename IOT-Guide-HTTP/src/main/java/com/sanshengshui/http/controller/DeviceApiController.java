package com.sanshengshui.http.controller;

import com.google.gson.JsonParser;
import com.sanshengshui.tsl.adaptor.JsonConverter;
import com.sanshengshui.tsl.data.kv.AttributeKvEntry;
import com.sanshengshui.tsl.data.kv.KvEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @Author: 穆书伟
 * @Date: 19-4-2 下午3:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class DeviceApiController {

    @RequestMapping(value = "/{deviceToken}/attributes",method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postDeviceAttributes(
            @PathVariable("deviceToken") String deviceToken,
            @RequestBody String json) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        responseWriter.setResult(new ResponseEntity<>(HttpStatus.ACCEPTED));
        Set<AttributeKvEntry> attributeKvEntrySet = JsonConverter.convertToAttributes(new JsonParser().parse(json)).getAttributes();
        for (AttributeKvEntry attributeKvEntry : attributeKvEntrySet){
            System.out.println("属性名="+attributeKvEntry.getKey()+" 属性值="+attributeKvEntry.getValueAsString());
        }
        return responseWriter;
    }

    @RequestMapping(value = "/{deviceToken}/attributes", method = RequestMethod.GET, produces = "application/json")
    public DeferredResult<ResponseEntity> getDeviceAttributes(@PathVariable("deviceToken") String deviceToken,
                                                              @RequestParam(value = "clientKeys", required = false, defaultValue = "") String clientKeys,
                                                              @RequestParam(value = "sharedKeys", required = false, defaultValue = "") String sharedKeys) {
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        if (StringUtils.isEmpty(clientKeys) && StringUtils.isEmpty(sharedKeys)) {

        }else {
            Set<String> clientKeySet = !StringUtils.isEmpty(clientKeys) ? new HashSet<>(Arrays.asList(clientKeys.split(","))) : null;
            Set<String> sharedKeySet = !StringUtils.isEmpty(sharedKeys) ? new HashSet<>(Arrays.asList(sharedKeys.split(","))) : null;
        }
        return responseWriter;

    }

    @RequestMapping(value = "/{deviceToken}/telemetry",method = RequestMethod.POST)
    public DeferredResult<ResponseEntity> postTelemetry(@PathVariable("deviceToken") String deviceToken,
                                                        @RequestBody String json){
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        responseWriter.setResult(new ResponseEntity(HttpStatus.ACCEPTED));
        Map<Long, List<KvEntry>> telemetryMaps = JsonConverter.convertToTelemetry(new JsonParser().parse(json)).getData();
        for (Map.Entry<Long,List<KvEntry>> entry : telemetryMaps.entrySet()) {
            System.out.println("key= " + entry.getKey());
            for (KvEntry kvEntry: entry.getValue()) {
                System.out.println("属性名="+kvEntry.getKey()+ " 属性值="+kvEntry.getValueAsString());
            }
        }
        return responseWriter;
    }

    @RequestMapping(value = "/{deviceToken}/attributes/updates", method = RequestMethod.GET, produces = "application/json")
    public DeferredResult<ResponseEntity> subscribeToAttributes(@PathVariable("deviceToken") String deviceToken,
                                                                @RequestParam(value = "timeout", required = false, defaultValue = "0") long timeout,
                                                                HttpServletRequest httpRequest){
        DeferredResult<ResponseEntity> responseWriter = new DeferredResult<ResponseEntity>();
        return responseWriter;
    }
}
