package com.example.logdemo.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/***
 *
 * @author PengZheng
 * 切面
 */
@Aspect
@Component
public class ERPWebLogAspect {


    /**
     * 获取@controllerLog 注解上信息
     *
     * @param joinPoint
     * @return map
     * @throws Exception
     */
    private static Map<String, Object> getControllerAnnotationValue(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Map<String, Object> map = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] classes = method.getParameterTypes();
                if (classes.length == arguments.length) {
                    //取入参数据
                    String module = method.getAnnotation(ControllerLog.class).module().name();
                    String opType = method.getAnnotation(ControllerLog.class).opType().name();
                    map.put("module", module);
                    map.put("opType", opType);
                    break;
                }
            }
        }
        return map;
    }

    /**
     * 定义一个切入点.
     * execution(* com.xyy.web.finance..*(..))
     * 解释下：
     * 第一个 * 代表任意修饰符及任意返回值.
     * 第二个 * 任意包名
     * 第三个 * 代表任意方法.
     * 第四个 * 定义在web包或者子包
     * 第五个 * 任意方法
     * .. 匹配任意数量的参数.
     */
    @Pointcut("execution(* com.example.logdemo.web..*(..)) && @annotation(com.example.logdemo.log.ControllerLog)")
    public void webLog() {
    }

    // before(前置通知)： 在方法开始执行前执行
    @Before("webLog()")
    public void around(JoinPoint joinPoint) throws Throwable {
        Map<String, Object> values = getControllerAnnotationValue(joinPoint);
        String opType = null != values.get("opType") ? values.get("opType").toString() : null;
        String module = null != values.get("module") ? values.get("module").toString() : null;
        System.out.println("opType:" + opType);
        System.out.println("module:" + module);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println("ip:" + this.getAddr(request));

        // 记录日志
        this.saveLog();
    }

    // 获取客户端IP
    private String getAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && ip.length() != 0 && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    // 记录日志
    private void saveLog() {
        System.out.println("记录日志-----------------");
    }

}
