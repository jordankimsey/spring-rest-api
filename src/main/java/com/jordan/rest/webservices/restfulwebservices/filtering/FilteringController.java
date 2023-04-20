package com.jordan.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean myBean = new SomeBean("value1", "value2", "value3");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(myBean);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
       List<SomeBean> list =  Arrays.asList(
                new SomeBean("value1", "value2", "value3"),
                new SomeBean("value2", "value3", "value4"),
                new SomeBean("value7", "value6", "value5")
                );
       MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
       SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
       FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
       mappingJacksonValue.setFilters(filters);
       return mappingJacksonValue;
    }
}
