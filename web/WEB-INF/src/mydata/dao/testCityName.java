package mydata.dao;

/**
 * Created by lei02 on 2019/4/17.
 */
public class testCityName {
    public static void main(String[] args) {
        CityName cn = new CityName();
        System.out.println(cn.getProvinces());
        System.out.println(cn.getCities("江苏"));
        System.out.println(cn.getCounties("徐州"));
    }
}
