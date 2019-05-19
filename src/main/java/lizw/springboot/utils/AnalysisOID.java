package lizw.springboot.utils;

import java.util.HashMap;
import java.util.Map;

public class AnalysisOID {

    public static Map<String,String> analysisOID(String result){
        System.out.println("返回字符串的长度："+result.length());

        Map<String,String> map = new HashMap<String,String>();
        try {
            System.out.println("----------oid------------");
            int count = 0;
            int oidendChar = 0;
            for(int firstChar=0;firstChar<result.length()-6;firstChar++){
                oidendChar =firstChar+5;
                if (result.substring(firstChar,oidendChar).equals("only>") ){//第一个标志位子进行判断
                    System.out.println("oidendChar:"+oidendChar);
                    System.out.println("firstChar:"+firstChar);
                    for (int secondChar=oidendChar;secondChar<result.length()-1;secondChar++){
                        if (result.substring(secondChar,secondChar+1).equals("<")){//对结束的位子进行判断
                            //System.out.println(result.substring(endChar,secondChar));
                            String str = result.substring(oidendChar,secondChar);
                            if (count == 0){
                                map.put("oidfistline",str);
                                count++;
                                System.out.println("存放成功oidfistline：    "+str);
                                break;
                            }else if (count ==1){
                                map.put("oidsecondline",str);
                                System.out.println("存放成功oidsecondline：      "+str);
                                count++;
                                break;
                            }else if (count ==2){
                                map.put("oidthirdline",str);
                                count++;
                                System.out.println("存放成功oidthirdline：     "+str);
                                break;
                            }
                        }
                    }
                }
                if (count == 3){
                    break;//取完oid里面的三个值之后种植循环
                }
            }

            System.out.println("----------description------------");
            int descriptionendChar = 0;
            int descriptionSecondendChar = 0;
            int descriptionthirdendChar = 0;
            for(int firstChar=oidendChar;firstChar<result.length()-12;firstChar++){
                descriptionendChar =firstChar+11;
                if (result.substring(firstChar,descriptionendChar).equals("Description") ){//对第一个位子进行判断
                    System.out.println("----第一层判断---");
                    for (int secondChar=descriptionendChar;secondChar<result.length()-4;secondChar++){
                        descriptionSecondendChar =secondChar +3;
                        if (result.substring(secondChar,descriptionSecondendChar).equals("br>")){//对第二个位子进行判断
                            System.out.println("----第二层判断---");
                            System.out.println("secondChar:"+secondChar);
                            System.out.println("descriptionSecondendChar:"+descriptionSecondendChar);
                            for (int thirdChar=descriptionSecondendChar;thirdChar<result.length()-1;thirdChar++){//对结束的位子进行判断
                                if (result.substring(thirdChar,thirdChar+1).equals("<")){
                                    descriptionthirdendChar = thirdChar;
                                    System.out.println(result.substring(descriptionSecondendChar,thirdChar));
                                    String str = result.substring(descriptionSecondendChar,thirdChar).trim();
                                    map.put("description",str);
                                    System.out.println("存放成功descriptionthirdendChar：    "+descriptionthirdendChar);
                                    System.out.println("存放成功description：    "+str);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }

            System.out.println("----------Information------------");
            //int informationSecondSection = 0;
            for(int firstChar=descriptionthirdendChar;firstChar<result.length()-12;firstChar++){
                int informationendChar =firstChar+11;
                if (result.substring(firstChar,informationendChar).equals("Information") ){
                    System.out.println("----Information判断第一点：Information");
                    for (int secondChar=informationendChar;secondChar<result.length()-4;secondChar++){
                        int informationSecondendChar =secondChar +3;
                        if (result.substring(secondChar,informationSecondendChar).equals("<td")) {
                            System.out.println("----Information判断第二点：<td");
                            for (int thirdChar = informationSecondendChar; thirdChar < result.length() - 1; thirdChar++) {
                                if (result.substring(thirdChar, thirdChar + 1).equals(">")) {
                                    System.out.println("----Information判断第三点：>");
                                    int informationFourthStartChar = thirdChar + 1;
                                    for (int fourthChar=informationSecondendChar;fourthChar<result.length()-4;fourthChar++){
                                        if (result.substring(fourthChar, fourthChar+4).equals("</td")){
                                            System.out.println("----Information判断结尾点：</td");
                                            String str = result.substring(informationFourthStartChar, fourthChar).trim();
                                            map.put("Information:", str);
                                            System.out.println("成功将Information的值放入map:");
                                            System.out.println(str);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
    /*
                            if (result.substring(secondChar,informationSecondendChar).equals("br>")){
                                informationSecondSection++;
                                System.out.println("判断到br>的数量:"+informationSecondSection);
                            }

                            if (result.substring(secondChar,informationSecondendChar).equals("br>") && informationSecondSection ==1) {
                                System.out.println("----Information1第二层判断---");
                                System.out.println("secondChar:" + secondChar);
                                System.out.println("informationSecondendChar:" + informationSecondendChar);
                                for (int thirdChar = informationSecondendChar; thirdChar < result.length() - 1; thirdChar++) {
                                    if (result.substring(thirdChar, thirdChar + 1).equals("<")) {
                                        System.out.println(result.substring(informationSecondendChar, thirdChar));
                                        String str = result.substring(informationSecondendChar, thirdChar).trim();
                                        map.put("Information1:", str);
                                        System.out.println("存放成功Information1：    " + str);
                                        System.out.println("存放成功Information1长度：    " + str.length());
                                        break;
                                    }
                                }
                            }
                            if (result.substring(secondChar,informationSecondendChar).equals("br>") && informationSecondSection ==3){
                                System.out.println("----Information2第二层判断---");
                                System.out.println("secondChar:"+secondChar);
                                System.out.println("informationSecondendChar:"+informationSecondendChar);
                                for (int thirdChar=informationSecondendChar;thirdChar<result.length()-1;thirdChar++){
                                    if (result.substring(thirdChar,thirdChar+3).equals("<br")){
                                        System.out.println(result.substring(informationSecondendChar,thirdChar));
                                        String str = result.substring(informationSecondendChar,thirdChar).trim();
                                        map.put("Information2:",str);
                                        System.out.println("存放成功Information2：    "+str);
                                        System.out.println("存放成功Information2长度：    "+str.length());
                                        break;
                                    }
                                }
                                //break;
                            }*/
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
