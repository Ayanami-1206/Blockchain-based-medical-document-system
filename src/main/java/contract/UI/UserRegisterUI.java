package contract.UI;

import Communication.PackageFun;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 此类完成对注册页面的编写， 用户需填写 账户，密码
 *用户的姓名、用户的身份证号、用户工号、用户电话号码、已经初始化的用户列表,已经注册的设备列表
 * 并且会进行验证操作， 如姓名是否合法（中文）
 *
 */
@SuppressWarnings("serial")
public class UserRegisterUI extends JFrame implements ActionListener {
    int flag = 0;
    JButton jb1, jb2;  //按钮
    JLabel jlb1, jlb2, jlb3,jlb4,jlb5, jlb6;  //标签
    JTextField jtf1,jtf2,jtf3,jtf4;   //文本框
    JPasswordField jpf1,jpf2; //密码框
    JPanel jp1,jp2,jp3, jp4,jp5,jp6,jp7,jp8;		//面板

    public UserRegisterUI() {
        // TODO Auto-generated constructor stub
        //按钮
        jb1 = new JButton("提交");
        jb2 = new JButton("登录");
        //设置按钮监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        //标签信息
        //用户的姓名、用户的身份证号、用户ID、用户电话号码、已经初始化的用户列表,已经注册的设备列表
        jlb1 = new JLabel("     姓\u2003\u2003名");
        jlb2 = new JLabel("     电话号码");
        jlb3 = new JLabel("     用户ID");
        jlb4 = new JLabel("     身份证号");
        jlb5 = new JLabel("     密\u2003\u2003码");
        jlb6 = new JLabel("     确认密码");

        jtf1 = new JTextField(13);
        jtf2 = new JTextField(13);
        jtf3 = new JTextField(13);
        jtf4 = new JTextField(13);
        jpf1 = new JPasswordField(13);
        jpf2 = new JPasswordField(13);


        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
        jp8 = new JPanel();
        //将对应信息加入面板中
        jp1.add(jlb1);
        jp1.add(jtf1);

        jp2.add(jlb2);
        jp2.add(jtf2);

        jp3.add(jlb3);
        jp3.add(jtf3);

        jp4.add(jlb4);
        jp4.add(jtf4);

        jp5.add(jlb5);
        jp5.add(jpf1);

        jp6.add(jlb6);
        jp6.add(jpf2);

        jp8.add(jb1);
        jp8.add(jb2);



        //将JPane加入JFrame中
        this.add(jp7);  //先加入提示语

        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);
        this.add(jp8);


        //设置布局
        this.setTitle("注册信息");
        this.setLayout(new GridLayout(9, 1));
        this.setSize(250, 350);   //设置窗体大小
        this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //设置仅关闭当前窗口

        this.setVisible(true);  //设置可见
        this.setResizable(false);	//设置不可拉伸大小

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand()=="提交")
        {
            try {
                register();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getActionCommand()=="登录")
        {
            new LoginUI();
            dispose();  //使窗口消失
        }

    }
    //验证注册信息，并做处理
    public void register() throws Exception
    {

        //判断信息是否补全
        if (jtf1.getText().isEmpty()||jtf2.getText().isEmpty()||jtf3.getText().isEmpty()||jtf4.getText().isEmpty()||jpf1.getPassword().length==0||jpf2.getPassword().length==0)
        {
            JOptionPane.showMessageDialog(null, "信息有空缺，请补全！","消息提示",JOptionPane.WARNING_MESSAGE);
        }

        //判断用户的身份证号、用户工号、用户电话号码账户名和密码是否包含中文
        else if (new Check().checkcountname(jtf2.getText())||new Check().checkcountname(jtf3.getText())||new Check().checkcountname(jtf4.getText()))
        {
            JOptionPane.showMessageDialog(null, "用户的身份证号、用户工号、用户电话号码存在中文，不合法!","消息提示",JOptionPane.WARNING_MESSAGE);
            jpf1.setText("");
            jpf1.setText("");
        }
        //比较两个输入的密码是否一致
        else if(!String.valueOf(jpf1.getPassword()).equals(String.copyValueOf(jpf2.getPassword()))) {
            JOptionPane.showMessageDialog(null, "两次密码不一致，请重新输入","消息提示",JOptionPane.WARNING_MESSAGE);

        }
        //满足要求
        else
        {
            String name = jtf1.getText();
            Tool.currentUserName=name;
            String phone = jtf2.getText();
            String worknumber = jtf3.getText();
            String idnumber = jtf4.getText();
            String psw = String.valueOf(jpf1.getPassword());
            //调用智能合约
            String[] strArr = {name,phone,worknumber,idnumber,psw};
            String  returnStr = User_Contract.userRegister(strArr,Tool.FROMCLIENT);
            flag = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
            //将数据输出到本地文件
            System.out.println("returnStr="+returnStr);
            String output = returnStr.split("&")[1];
            String input = "contract.Contracts.User_Contract,userRegister;"+name+","+name+","+phone+","+worknumber+","+idnumber+","+psw+";"+output;
            String packDate = PackageFun.Pack(input, User_Contract.realPK);
            User_Contract.PackToFile(packDate);
            //等待共识的结果
            System.out.println(flag);
            if (flag == 2){
                //注册成功
                JOptionPane.showMessageDialog(null,"注册成功！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                dispose();
                //进入登录界面
                new LoginUI();
            }else if (flag == 1){
                JOptionPane.showMessageDialog(null,"该用户重复注册，请登录！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                dispose();
                //进入登录界面
                new LoginUI();
            }else{
                JOptionPane.showMessageDialog(null,"注册失败！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                //内容清空
                clear();
            }
        }
    }

    //清空账号和密码框
    private void clear() {
        // TODO Auto-generated method stub
        jtf1.setText("");
        jtf2.setText("");
        jtf3.setText("");
        jtf4.setText("");
        jpf1.setText("");
        jpf2.setText("");
    }
}

