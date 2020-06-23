package com.example.healthfull.search_nutri;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NutritionInfoTest {

    private NutritionInfo nutriInfo;
    String name;

    @Mock
    private NutrientObject nutriObject;

    @Before
    public void setUpNutriInfo()
    {
        MockitoAnnotations.initMocks(this);
        nutriInfo = new NutritionInfo();


    }

    @Test
    public void getNutritionRecord(View view){
        nutriInfo.getNutritionRecord(view);
    }




}
