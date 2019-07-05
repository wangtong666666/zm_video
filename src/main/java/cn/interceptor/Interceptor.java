package cn.interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Interceptor implements HandlerInterceptor {
	



    @Override
    public void afterCompletion(HttpServletRequest httpRequest,HttpServletResponse httpResponse, Object arg2, Exception arg3)throws Exception {
    	httpResponse.setHeader("Access-Control-Allow-Origin", "*"); 
    	
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
    	arg1.setHeader("Access-Control-Allow-Origin", "*"); 

    	
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object object) throws Exception {
        /*HttpServletRequest httpRequest = (HttpServletRequest) request;  
         HttpServletResponse httpResponse = (HttpServletResponse) response;*/
    	
    	
        String urlString = request.getRequestURI();
        String contextPath=request.getContextPath();
       
  
            
        if(true){
        	System.out.println(urlString);
        	
            return true;
        }else{
        	System.out.println("["+urlString+"]"+"-拦截");
        	return false;
        }
      
    

    }

}