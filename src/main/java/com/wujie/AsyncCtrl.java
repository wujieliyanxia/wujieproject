package com.wujie;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

/**
 * springMVC的异步处理
 */
@RestController
public class AsyncCtrl {
    // 使用DeferredResult
    @RequestMapping(value = "/asyncTask", method = RequestMethod.GET)
    public DeferredResult<ModelAndView> asyncTask() {
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<>();
        System.out.println("/asyncTask 调用！thread id is : " + Thread.currentThread().getId());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("实际任务开始");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ModelAndView mv = new ModelAndView();
                mv.addObject("test", "test");
                System.out.println("实际任务完成");
                deferredResult.setResult(mv);
            }
        });
        thread.start();
        System.out.println("主线程完成");
        return deferredResult;
    }
}
