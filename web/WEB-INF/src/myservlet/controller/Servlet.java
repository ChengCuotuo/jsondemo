package myservlet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mydata.dao.CityName;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by lei02 on 2019/4/17.
 */
public class Servlet extends HttpServlet {
    private CityName cn = new CityName();

    @Override
    public void init(ServletConfig config)
        throws ServletException {
        super.init(config);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");

        //需要根据前台提交的查询的内容调用不同的方法
        String name = request.getParameter("choose");
        //System.out.println("name:" + name);
        //获取省市县的数据信息
        //返回的是 JSON 对象
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        if (name.equals("province")) {
            response.setContentType("text/html;charset=UTF-8");
            List<String> provinces = cn.getProvinces();
            request.setAttribute("provinces", provinces);
            //System.out.print(result);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/show/city.jsp");
            dispatcher.forward(request, response);
        } else if (name.equals("city")) {
            response.setContentType("text/javascript;charset=UTF-8");
            //获取省份名称
            String provinceName = request.getParameter("provinceName");
            if (provinceName != "") {
                List<String> cities = cn.getCities(provinceName);
                result = mapper.writeValueAsString(cities);
            }
        } else if (name.equals("county")) {
            response.setContentType("text/javascript;charset=UTF-8");
            String countyName = request.getParameter("countyName");
            if (countyName != "") {
                List<String> cities = cn.getCities(countyName);
                result = mapper.writeValueAsString(cities);
            }
        }

        if(result != null) {
            //System.out.print(result);
            response.getWriter().print(result);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        doPost(request, response);
    }
}
