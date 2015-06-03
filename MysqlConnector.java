package demo;


import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraMessagingException;
import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraSmsMessage;
import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraSmsMoServlet;
import hsenidmobile.sdp.rest.servletbase.MchoiceAventuraSmsSender;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: aravindaweerasekara
 * Date: 6/1/14
 * Time: 12:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class MysqlConnector extends MchoiceAventuraSmsMoServlet {
    public static void main(String args[]) throws ClassNotFoundException, SQLException{

        String $user = null;
        MysqlConnector user_id = new MysqlConnector();
        user_id.onMessage("userID");
        String usr_sche = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/ncd-hs", "root", "");
        PreparedStatement st = con.prepareStatement("SELECT * FROM work_schedule WHERE user_id=" + $user);
        ResultSet r1 = st.executeQuery();

        if (r1.next()) {

            usr_sche = r1.getString("user_id") + " " + r1.getString("schedule") + " " + r1.getString("date");
        }
        System.out.println("user ID" + "schedule" + "date");
        System.out.println(usr_sche);


    } //end-displayMaxSalary.



    @Override
    public void onMessage(MchoiceAventuraSmsMessage msg) {
        System.out.println("Message Received: "+ msg.getMessage());
        System.out.println("Mobile Number: "+ msg.getAddress());

        String userID = msg.getMessage();



        try {
            MchoiceAventuraSmsSender smsSender = new MchoiceAventuraSmsSender(new URL("http://127.0.0.1:8000/sms/"), "appid", "password");
            smsSender.sendMessage("Message Received. Thank You", msg.getAddress());
        } catch (MchoiceAventuraMessagingException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
}
