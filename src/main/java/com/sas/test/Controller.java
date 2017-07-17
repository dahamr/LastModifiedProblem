package com.sas.test;
/*
 * copyright(c) 2017 SAS Institute, Cary NC 27513
 * Created on Mar 20, 2017
 *
 */


import com.sas.test.representation.TestObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author <A HREF="mailto:Gary.Williams@sas.com">Gary Williams</A>
 */
@RestController
public class Controller
{
    Map<String, TestObject> resourceMap = new HashMap<>();


    @RequestMapping(value = {"/test/objects"},
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TestObject> createObject(@RequestBody(required = true) TestObject testObject)
    {
        String newId = UUID.randomUUID().toString();
        testObject.setId(newId);
        testObject.setCreationTimeStamp(new Date());
        testObject.setModifiedTimeStamp(new Date());
        resourceMap.put(newId, testObject);
        HttpHeaders headers = new HttpHeaders();
        headers.setETag(testObject.getEtag());
        headers.setLastModified(testObject.getModifiedTimeStamp().getTime());
        headers.add(HttpHeaders.LOCATION, "/test/objects/" + testObject.getId());
        return new ResponseEntity<>(testObject, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/test/objects/{objectId}"}, method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TestObject> updateObject(@PathVariable(value="objectId") String objectId,
                                                   @RequestHeader(name=HttpHeaders.IF_MATCH, required=false) String etagIn,
                                                   @RequestHeader(name=HttpHeaders.IF_UNMODIFIED_SINCE, required=false) Date lastModifiedIn,
                                                   @RequestBody(required = true) TestObject testObject)
    {
        if(!objectId.equals(testObject.getId()))
        {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "The identifier in the path and in the body don't match.");
        }
        TestObject existing = resourceMap.get(objectId);
        if(existing == null)
        {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "The test object with the given identifier wasn't found.");
        }
        existing.setName(testObject.getName());
        existing.setDescription(testObject.getDescription());
        existing.setModifiedTimeStamp(new Date());
        HttpHeaders headers = new HttpHeaders();
        headers.setETag(existing.getEtag());
        headers.setLastModified(existing.getModifiedTimeStamp().getTime());
        headers.add(HttpHeaders.LOCATION, "/test/objects/" + testObject.getId());
        return new ResponseEntity<>(testObject, headers, HttpStatus.OK);
    }
}
