package contract.UI;

import Communication.PackageFun;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Struct.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

@SuppressWarnings( "serial")
public class LogoutUI extends JFrame implements ActionListener {
    User current_user = new User();
    JButton jb1, jb2;  //按钮
    JPanel jp1,jp2,jp3, jp4,jp5;		//面板
    JLabel jlb1;//标签
    JTextField jtf1;   //文本框



    public LogoutUI(User temp_user) {
        current_user = temp_user;
        // TODO Auto-generated constructor stub
        jb1 = new JButton("确定");
        jb2 = new JButton("取消");
        //设置按钮监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);


        jp1 = new JPanel();  //创建面板
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();




        jtf1 = new JTextField(10);//创建文本框
        jlb1 = new JLabel("注销原因"); //添加标签



        //加入面板中
        jp1.add(jlb1);
        jp1.add(jtf1);

        jp3.add(jb1);
        jp3.add(jb2);


        //将JPane加入JFrame中
        this.add(jp5);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);



        //设置布局
        this.setTitle("用户注销:");
        this.setLayout(new GridLayout(5,1));
        this.setSize(250, 250);   //设置窗体大小
        this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //设置仅关闭当前窗口

        this.setVisible(true);  //设置可见
        this.setResizable(false);	//设置不可拉伸大小

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand()=="确定")
        {
            try {
                logout();
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getActionCommand()=="取消")
        {
            //  clear();
            dispose();  //使窗口消失
            new UserRegisterUI();
        }
    }


    //验证登录信息，并做处理
    @SuppressWarnings("deprecation")
    public  void logout() throws UnknownHostException {
        //调用注销合约
        String[] strArr = {current_user.getUser_name(),current_user.getId_number()};
        String returnStr = User_Contract.userout(strArr,Tool.FROMCLIENT);
        int flag= Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
        String output = returnStr.split("&")[1];
        String input = "contract.Contracts.User_Contract,userout;"+current_user.getUser_name()+","+current_user.getId_number()+";"+output;

        String packDate = null;
        try {
            packDate = PackageFun.Pack(input, User_Contract.realPK);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            User_Contract.PackToFile(packDate);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (flag==1){
            JOptionPane.showMessageDialog(null,"注销成功！",
                    "提示消息",JOptionPane.WARNING_MESSAGE);
            System.exit(0);  //结束程序
        }else {

        }
        JOptionPane.showMessageDialog(null,"注销失败！",
                "提示消息",JOptionPane.WARNING_MESSAGE);

    }

    //清空账号和密码框
    private void clear() {
        // TODO Auto-generated method stub
        jtf1.setText("");    //设置为空

    }
}




