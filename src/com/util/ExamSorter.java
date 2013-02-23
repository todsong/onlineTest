package com.util;

import java.util.Comparator;
import com.pojo.Exam;

public class ExamSorter implements Comparator<Exam>
{    
    @Override
    public int compare(Exam e1, Exam e2)
    {
        String t1 = e1.getStartTime();
        String t2 = e2.getStartTime();
        return t1.compareTo(t2);
    }
//    public static void main(String[] args)
//    {
//        List<Exam> l = new ArrayList<Exam>();
//        Exam e1 = new Exam();
//        Exam e2 = new Exam();
//        Exam e3 = new Exam();
//        Exam e4 = new Exam();
//        e1.setStartTime("12291218");
//        e2.setStartTime("12301218");
//        e3.setStartTime("12290918");
//        e4.setStartTime("11291218");
//        l.add(e1);
//        l.add(e2);
//        l.add(e3);
//        l.add(e4);
//        ExamSorter comparator=new ExamSorter();
//        Collections.sort(l, comparator);
//        for(int i=0;i<l.size();i++)
//        {
//            System.out.println(l.get(i).getStartTime());
//        }
//        
//    }
}
