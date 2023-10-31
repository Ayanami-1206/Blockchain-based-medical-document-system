package contract.UI;

import Communication.PackageFun;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Struct.Equip;
import contract.Struct.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings( "serial")
public class ApplyRoleUI extends JFrame implements ActionListener {
    User user = new User();
    JButton jb1, jb2;  //按钮
    JPanel jp1,jp2,jp3, jp4,jp5;		//面板
    JTextField jtf1;   //文本框
    JLabel jlb1; //标签
    JComboBox jcb1;		//定义下拉框


    public ApplyRoleUI(User user_temp) {

        //传递用户信息
        user  = user_temp;
        // TODO Auto-generated constructor stub
        jb1 = new JButton("确定");
        jb2 = new JButton("取消");
        //设置按钮监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);

        // String []role= {"后端综合测试","数据处理与判读","测发进程模拟配置","管理节点"};	//创建角色权限
        String []role= {"后端综合测试","数据处理与判读","测发进程模拟配置"};//创建角色权限
        jcb1=new JComboBox(role);		//添加到下拉框中

        jp1 = new JPanel();  //创建面板
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();


        jlb1 = new JLabel("申请等级");  //添加标签

        jtf1 = new JTextField(10);//创建文本框


        //加入面板中
        jp1.add(jlb1);
        jp1.add(jcb1);

        jp3.add(jb1);
        jp3.add(jb2);


        //将JPane加入JFrame中
        this.add(jp5);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);



        //设置布局
        this.setTitle("角色变更");
        this.setLayout(new GridLayout(5,1));
        this.setSize(500, 400);   //设置窗体大小
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
            //申请权限
            System.out.println(jcb1.getSelectedIndex());
            int new_role = jcb1.getSelectedIndex();
            int flag =0;
            System.out.println("role"+new_role+user.getUser_role());
            System.out.println(new_role != user.getUser_role());
            if (new_role != user.getUser_role()){
                try {
                    String[] strArr = {user.getUser_name(),user.getWork_number(), String.valueOf(user.getUser_role()), String.valueOf(new_role)};
                    System.out.println("权限变更信息"+String.valueOf(strArr));
                    String returnStr = User_Contract.applyRole(strArr,Tool.FROMCLIENT);
                    flag= Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
                    String output = returnStr.split("&")[1];
                    String input = "contract.Contracts.User_Contract,applyRole;"+user.getUser_name()+","+user.getWork_number()+","+Integer.toString(user.getUser_role())+","+Integer.toString(new_role)+";"+output;
                    String packDate = null;
                    try {
                        packDate = PackageFun.Pack(input, User_Contract.realPK);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    User_Contract.PackToFile(packDate);
                } catch (IOException ex1) {
                    ex1.printStackTrace();
                }
            }
            if (flag==1){
                JOptionPane.showMessageDialog(null,"已经发出申请！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);

            }else {
                JOptionPane.showMessageDialog(null,"申请失败！",
                        "提示消息",JOptionPane.WARNING_MESSAGE);
            }
        }
        else if(e.getActionCommand()=="取消")
        {
            //  clear();
            dispose();  //使窗口消失
        }
    }


    //
    @SuppressWarnings("deprecation")
    //清空账号和密码框
    private void clear() {
        // TODO Auto-generated method stub
        jtf1.setText("");    //设置为空

    }
}


