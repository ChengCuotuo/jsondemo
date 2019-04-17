package mydata.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei02 on 2019/4/17.
 * 查询数据库 city 中的省市县名称
 */
public class CityName {
    private Connection conn;
    private PreparedStatement sql;
    private ResultSet rs;

    private String url = "jdbc:mysql://127.0.0.1/china?user=root&password=021191&characterEncoding=utf-8";;

    //加载数据库连接
    public CityName() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //获取省份名称
    public List<String> getProvinces() {
        //使用的 sql 语句是 : select cityname from city where pid = 1;
        List<String> provinces = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            sql = conn.prepareStatement("select cityname from city where pid = 1");
            rs = sql.executeQuery();
            conn.commit();
            while (rs.next()) {
                provinces.add(rs.getString(1));
            }

            return provinces;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //根据提供的省份名称获取市区名称
    public List<String> getCities(String province) {
        //使用 sql 语句是：
        // select cityname from city where pid in (select id from city where cityname='江苏');
        List<String> cities = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            sql = conn.prepareStatement("select cityname from city where pid in (select id from city where cityname=?)");
            sql.setString(1, province);
            rs = sql.executeQuery();
            conn.commit();
            while (rs.next()) {
                cities.add(rs.getString(1));
            }

            return cities;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e){
                e.printStackTrace();
            }
            ex.printStackTrace();
            return null;
        }
    }

    //根据 市区 名称查询 县
    public List<String> getCounties (String city) {
       //使用的 sql 语句和查询 市区 的是一样的
        return getCities(city);
    }
}
