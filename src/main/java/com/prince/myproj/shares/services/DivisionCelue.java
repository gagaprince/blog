package com.prince.myproj.shares.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gagaprince on 2017/5/20.
 */
@Service
public class DivisionCelue {
    private List<Integer> goodDivisions = new ArrayList<Integer>();

    private void initGoodDivisions (){
        goodDivisions.add(80108116);
    }
}
