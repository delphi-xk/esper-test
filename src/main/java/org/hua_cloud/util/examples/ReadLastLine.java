package org.hua_cloud.util.examples;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ReadLastLine {

    public enum Color {
        RED("red",1), BLUE("blue",2);

        private String color;
        private int index;
        private Color(String color, int index){
            this.color = color;
            this.index = index;
        }

        @Override
        public String toString(){
            return ("color: "+this.color+" index: "+this.index);
        }

    }

    @Test
    public void readLastLine() throws Exception{
        File file = new File("/e:/test.libsvm");
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        BufferedReader bis = new BufferedReader(isr);
        BufferedReader reader = new BufferedReader(new FileReader("e:/test.libsvm"));
        List list = new ArrayList();
        String tmp;
        String endString = "";
        while( (tmp = reader.readLine()) != null){
            endString = tmp;
        }
//        System.out.println(endString);
        System.out.println(endString.equals("Done"));
        reader.close();
    }

    @Test
    public void printTime(){
        System.out.println(new Date());
    }

    @Test
    public void test3(){
        Color color1 = Color.BLUE;
        System.out.println(color1);
    }

    @Test
    public void getFileSize(){
        File file = new File("/e:/doc/deeplearning.pdf");
        System.out.println(file.exists());
        System.out.println(file.length());
        System.out.println(file.length()/1024);
    }



}
