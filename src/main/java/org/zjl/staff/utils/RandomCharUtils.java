package org.zjl.staff.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class RandomCharUtils {
    public static Map<Integer,String> get(int num,Integer startVal,Integer okNum){
        List<String> stringList=new ArrayList<>();
        stringList.add("√");
        stringList.add("×");
        Random random=new Random();
        int okNumTotal=0;
        int notOkNumTotal=0;
        Map<Integer,String> randomMap=new HashMap<>();
        for (int n = startVal; n < num+startVal; n++) {
            int number=random.nextInt(2);
            String s=stringList.get(number);
            if(s.equals("√")){
                okNumTotal++;
            }else{
               notOkNumTotal++;
            }
            if(okNumTotal>okNum){
                randomMap.put(n,"×");
            }else if(notOkNumTotal>num-okNum){
                randomMap.put(n,"√");
            }else{
                randomMap.put(n,s);
            }

        }
        return randomMap;
    }

    public static void main(String[] args) {
        System.out.println(get(20,2,3));
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
    }
}
