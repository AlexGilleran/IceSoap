package com.alexgilleran.icesoap.example.processors;

import java.util.ArrayList;
import java.util.List;

import com.alexgilleran.icesoap.parser.processor.Processor;

public class CSVProcessor implements Processor<List<Integer>> {
    @Override
    public List<Integer> process(String inputValue) {
        String[] values = inputValue.split(",");

        List<Integer> integerList = new ArrayList<Integer>();
        for (String value: values) {
            integerList.add(Integer.parseInt(value));
        }

        return integerList;
    }
}