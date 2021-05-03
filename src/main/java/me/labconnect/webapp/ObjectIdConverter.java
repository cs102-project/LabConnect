package me.labconnect.webapp;

import com.fasterxml.jackson.databind.util.StdConverter;

import org.bson.types.ObjectId;

public class ObjectIdConverter extends StdConverter<ObjectId, String> {

    @Override
    public String convert(ObjectId value) {
        
        return value.toString();
        
    }
    
    
    
}
