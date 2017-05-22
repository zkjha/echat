<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
 <%

        //String webUrl="http://test.ppseeker.com/";
        //String webUrl="http://linux.fushoukeji.com/";
         String webUrl="http://localhost:8083/";
        String static_baseUrl=webUrl+"static/";
        
       
        String imageBaseUrl="http://linux.fushoukeji.com:88/";
        //String imageBaseUrl="http://test.ppseeker.com:88/";

%>
   <script type="text/javascript">
  var EK={STATIC_ROOT:"<%=static_baseUrl%>",webUrl:"<%=webUrl%>",imageBaseUrl:"<%=imageBaseUrl%>"}
</script>

