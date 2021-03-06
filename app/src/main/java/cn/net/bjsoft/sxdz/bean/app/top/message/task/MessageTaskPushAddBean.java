package cn.net.bjsoft.sxdz.bean.app.top.message.task;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Zrzc on 2017/5/12.
 */

public class MessageTaskPushAddBean implements Serializable {

    public String id = "";
    public String title = "";
    public String starttime = "";
    public String limittime = "";
    public String message = "";
    public String priority = "";
    public String description = "";
    public boolean shared = false;
    public ArrayList<MessageTaskDetailDataUsersBean> users = new ArrayList<>();
    public ArrayList<MessageTaskDetailDataFilesBean> files = new ArrayList<>();


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        sb.append("\"id\":\"");
        sb.append(id);
        sb.append("\",");

        sb.append("\"title\":\"");
        sb.append(title);
        sb.append("\",");

        sb.append("\"shared\":");
        sb.append(shared);
        sb.append(",");

        sb.append("\"starttime\":\"");
        sb.append(starttime);
        sb.append("\",");

        sb.append("\"limittime\":\"");
        sb.append(limittime);
        sb.append("\",");

        sb.append("\"message\":\"");
        sb.append(message);
        sb.append("\",");

        sb.append("\"priority\":\"");
        sb.append(priority);
        sb.append("\",");

        sb.append("\"description\":\"");
        sb.append(description);
        sb.append("\",");


        if (files != null) {
            sb.append("\"files\":[");
            for (int i = 0; i < files.size(); i++) {
                sb.append("{");

                sb.append("\"id\":\"");
                sb.append(files.get(i).id);
                sb.append("\",");

                sb.append("\"title\":\"");
                sb.append(files.get(i).title);
                sb.append("\",");

                sb.append("\"ctime\":\"");
                sb.append(files.get(i).ctime);
                sb.append("\",");

                sb.append("\"url\":\"");
                sb.append(files.get(i).url);
                sb.append("\"");

                if (i == files.size() - 1) {
                    sb.append("}");
                } else {
                    sb.append("},");
                }

            }

            sb.append("],");
        }


        if (users != null) {
            sb.append("\"users\":[");
            for (int i = 0; i < users.size(); i++) {
                sb.append("{");

                sb.append("\"id\":\"");
                sb.append(users.get(i).userid);
                sb.append("\",");

                if (i == users.size() - 1) {
                    sb.append("}");
                } else {
                    sb.append("},");
                }

            }

            sb.append("]");
        }

        sb.append("}");

        return sb.toString();
    }
}
