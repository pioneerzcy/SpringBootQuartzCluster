package com.jadework.quartz;

import com.squareup.okhttp.*;
import org.quartz.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Copyright: Copyright (c) 2016,${year},
 * @Description: ${todo}
 */

public class ScheduledJob implements Job {

    public ScheduledJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        //http---start
        OkHttpClient client = new OkHttpClient();

        String api = "http://"+dataMap.getString("app_address")
                +"/taskschedule_"+jobKey.getGroup()
                +"_"+jobKey.getName();
        System.out.println("------api------:"+api);

        RequestBody formBody = new FormEncodingBuilder()
                .add("name","TaskScheduleServer")
                .add("job_name", jobKey.getName())
                .add("task_group",jobKey.getGroup())
                .build();

        Request request = new Request.Builder()
                .url(api)
                .post(formBody)
                .build();

        //--- 异步调用start ---
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("服务器端错误: " + response);
                }

                System.out.println(response.body().string());
            }
        });
        System.out.println("TaskScheduleServer:请求执行"
                +jobKey.getGroup()+"_"+jobKey.getName()+" --- Date:"
                +new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        //--- 异步调用end ---
    }
}
