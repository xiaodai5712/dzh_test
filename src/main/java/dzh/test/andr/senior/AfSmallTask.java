package dzh.test.andr.senior;


import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public abstract class AfSmallTask extends Thread
{
    Handler handler = new MyHandler();

    // 子类需要重写这个方法,此方法在线程里调用
    protected abstract Object doInBackground();

    // 子类需重写此方法，此方法在handler中调用
    protected abstract void onPostExecute(Object result);

    // 启动小任务
    public void execute()
    {
        start();
    }

    @Override
    public void run()
    {
        // 完成小任务
        Object result = doInBackground();
        // 发送消息给handler
        Message msg = new Message();
        msg.what = 1;
        msg.obj = result;
        handler.sendMessage(msg);
    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            Object result = msg.obj; // 取出结果
            onPostExecute(result);

        }
    }
}
