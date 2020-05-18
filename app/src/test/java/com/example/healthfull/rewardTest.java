package com.example.healthfull;

import com.example.healthfull.RewardsSystem.Rewards;
import com.example.healthfull.RewardsSystem.user;

import org.junit.Test;

public class rewardTest{
    public static Rewards rewardsDummy;
    public static user userExample = new user();

    @Test
    public void compareCaloriesTest(){

        rewardsDummy.compareCalories();
        }

     @Test
    public void assignPointsTest(){
        rewardsDummy.assignPoints();
        System.out.println(userExample.getCount());
     }

     @Test
    public void redeemRecipeTest(){

        rewardsDummy.redeemRecipe();
     }

     @Test
     public void test(){
        rewardsDummy.getRecipe();
     }
}
