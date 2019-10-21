package dzh.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserManager
{
    List<User> userList  = new ArrayList<>();
    File file;
    public UserManager(File file)
    {
        this.file = file;
    }

    // 将数据保存到文件
    public void save() throws Exception
    {
        FileOutputStream fileOutputStream = new FileOutputStream(file); // 在这一步中，如果 file所指向的文件不存在，系统将自动创建一个
        try
        {

            for(User u : userList)
            {
                String data = u.password + "," + u.password + "\n";
                byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
                fileOutputStream.write(bytes);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            fileOutputStream.close();
        }
    }

    // 从文件中加载数据
    public void load() throws Exception
    {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);  // 以后可以将 UTF-8 写成 StandardCharsets.UTF-8
        BufferedReader reader = new BufferedReader(inputStreamReader);
        // 新接触了两个类，InputStreamReader BufferedReader 还不知道这俩类都有什么作用
        userList.clear();
        while (true)
        {
            String line = reader.readLine();
            if(line == null) break;
            String[] cols = line.split(",");
            if(cols.length < 2) continue;
            User u = new User();
            u.username = cols[0].trim();
            u.password = cols[1].trim();
            userList.add(u);
        }
        reader.close(); // reader 关闭后，fileInputStream也会自动关闭
    }

    // dzh：注册一个用户
    public void add(User u)
    {
        userList.add(u);
    }

    // 按照username搜索一个用户
    public User find(String username)
    {
        for(User u : userList)
        {
            if(u.username.equals(username))
            {
                return u;
            }
        }
        return null;
    }


}
