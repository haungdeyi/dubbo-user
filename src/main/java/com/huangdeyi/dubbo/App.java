package com.huangdeyi.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception {
        //得到spring的容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("order-consumer.xml");
        //远程调用下单服务
        OrderServiceApi or = (OrderServiceApi)context.getBean("orderServices");
        OrderRequest request = new OrderRequest();
        request.setName("大呲花");
        or.doOrder(request);
        //异步调用时获取返回的结果
        Future<OrderResponse> asycnResponse = RpcContext.getContext().getFuture();

        System.out.println("aaaaa");
        //get时存在阻塞
        System.out.println(asycnResponse.get().getStatus() + (String)asycnResponse.get().getData());
    }
}
