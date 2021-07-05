package contract.UI;

import Communication.PackageFun;
import contract.Contracts.User_Contract;
import contract.Struct.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


@SuppressWarnings("serial")
public class LoginUI extends JFrame implements ActionListener {
    int flag = 0;
    JButton jb1, jb2, jb3;  //按钮
    JPanel jp1,jp2,jp3, jp4,jp5;		//面板
    JTextField jtf1,jtf2;   //文本框
    JLabel jlb1, jlb2, jlb3; //标签
    JPasswordField jpf; //密码框


    public LoginUI() {

        // TODO Auto-generated constructor stub
        jb1 = new JButton("登录");
        jb2 = new JButton("注册");
        //设置按钮监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);

        jp1 = new JPanel();  //创建面板
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();


        jlb1 = new JLabel("用户名");  //添加标签
        jlb2 = new JLabel("工\u2003号");
        jlb3 = new JLabel("密\u2003码");

        jtf1 = new JTextField(10);
        jtf2 = new JTextField(10);//创建文本框和密码框
        jpf = new JPasswordField(10);

        //加入面板中
        jp1.add(jlb1);
        jp1.add(jtf1);

        jp2.add(jlb2);
        jp2.add(jtf2);

        jp3.add(jlb3);
        jp3.add(jpf);

        jp4.add(jb1);
        jp4.add(jb2);


        //将JPane加入JFrame中
        this.add(jp5);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);




        //设置布局
        this.setTitle("用户登录");
        this.setLayout(new GridLayout(6,1));
        this.setSize(250, 250);   //设置窗体大小
        this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //设置仅关闭当前窗口

        this.setVisible(true);  //设置可见
        this.setResizable(false);	//设置不可拉伸大小

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getActionCommand()=="登录")
        {
            try {
                login();
            } catch (HeadlessException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        else if(e.getActionCommand()=="注册")
        {
            //  clear();
            new UserRegisterUI();
            dispose();  //使窗口消失
        }
    }
    //清空账号和密码框
    private void clear() {
        // TODO Auto-generated method stub
        jtf1.setText("");    //设置为空
        jtf2.setText("");
        jpf.setText("");

    }

    //验证登录信息，并做处理
    @SuppressWarnings("deprecation")
    public  void login() throws HeadlessException, IOException
    {
        //判断身份证号和密码是否包含中文
        if (new Check().checkcountname(jtf2.getText())||new Check().checkcountname(jpf.getText()))
        {
            JOptionPane.showMessageDialog(null, "工号或密码存在中文，不合法!","消息提示",JOptionPane.WARNING_MESSAGE);
        }else if(jtf2.getText().isEmpty()||jtf2.getText().isEmpty()||jpf.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "请输入完整内容！","消息提示",JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            //获取进行登录的信息
            String name = jtf1.getText();
            String workNumber = jtf2.getText();
            String psw = jpf.getText();
            //调用登录智能合约
            String[] strArr ={name,workNumber,psw};
            String returnStr = User_Contract.userLogin(strArr,false);
            flag = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
            //将数据输出到本地文件
            String output = returnStr.split("&")[1];
            String input = "contract.Contracts.User_Contract,userLogin;"+name+","+workNumber+","+psw+";"+output;
            String packDate = null;
            try {
                packDate = PackageFun.Pack(input, User_Contract.realPK);
            } catch (Exception e) {
                e.printStackTrace();
            }
            User_Contract.PackToFile(packDate);
            //等待共识的结果
            //
            //获取当前用户
            User currentUser = User_Contract.getCurrentUser(name,workNumber,psw);
            System.out.println(flag);
            if(flag == 4){
                JOptionPane.showMessageDialog(null, "账号密码错误请重新输入！",
                        "消息提示",JOptionPane.ERROR_MESSAGE);
                clear();  //调用清除函数
            }else if(flag == 5){
                JOptionPane.showMessageDialog(null, "用户信息有误！",
                        "消息提示",JOptionPane.ERROR_MESSAGE);
                clear();  //调用清除函数
            }else if (flag == 6){
                JOptionPane.showMessageDialog(null, "非法设备！",
                        "消息提示",JOptionPane.ERROR_MESSAGE);
                clear();
            }
            else if (flag ==0){
                JOptionPane.showMessageDialog(null,"登录成功！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                //进入用户界面
                new GuserUI(currentUser);
                dispose();  //使文原窗体消失

            }
            else if (flag ==1){
                JOptionPane.showMessageDialog(null,"登录成功！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                //进入用户界面
                new GuserUI(currentUser);
                dispose();  //使文原窗体消失

            }
            else if (flag ==2){
                JOptionPane.showMessageDialog(null,"登录成功！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                //进入用户界面
                new GuserUI(currentUser);
                dispose();  //使文原窗体消失
            }
            else if (flag ==3){
                JOptionPane.showMessageDialog(null,"登录成功！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
                //进入用户界面
                new TuserUI(currentUser);
                dispose();  //使文原窗体消失
            } else {
                JOptionPane.showMessageDialog(null, "登录失败！",
                        "消息提示",JOptionPane.ERROR_MESSAGE);
                clear();
            };
        }

    }

}


