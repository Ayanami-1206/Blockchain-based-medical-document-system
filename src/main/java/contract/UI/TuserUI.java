package contract.UI;


import contract.Struct.ApplyMessage;
import contract.Struct.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 此类完成对页面普通用户界面的编写
 *用户的姓名、用户的身份证号、用户工号、用户电话号码、已经初始化的用户列表,已经注册的设备列表
 */
@SuppressWarnings("serial")
public class TuserUI extends JFrame implements ActionListener {

    User current_user = new User();

    JButton jb1, jb2,jb3,jb4,jb5,jb6,jb7,jb8;  //按钮
    JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp9;		//面板
    public TuserUI(User temp_user) {
        current_user =temp_user;
        // TODO Auto-generated constructor stub
        //按钮
        //jb1 = new JButton("测发流程");
        //jb2 = new JButton("测发数据");
        jb3 = new JButton("角色变更");
        jb4 = new JButton("权限管理");
        jb5 = new JButton("用户注销");
        jb6 = new JButton("重新登录");
        jb7 = new JButton("数据追溯");
        jb8 = new JButton("文件管理");
        //设置按钮监听
        //jb1.addActionListener(this);
        //jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        jb5.addActionListener(this);
        jb6.addActionListener(this);
        jb7.addActionListener(this);
        jb8.addActionListener(this);


        //标签信息

        //jp1 = new JPanel();
        //jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();
        jp5 = new JPanel();
        jp6 = new JPanel();
        jp7 = new JPanel();
        jp8 = new JPanel();
        jp9 = new JPanel();
        //将对应信息加入面板中
        //jp1.add(jb1);
        //jp2.add(jb2);
        jp3.add(jb3);
        jp4.add(jb4);
        jp5.add(jb5);
        jp7.add(jb6);
        jp8.add(jb7);
        jp9.add(jb8);



        //将JPane加入JFrame中
        this.add(jp6);  //先加入提示语
        //this.add(jp1);
        //this.add(jp2);
        this.add(jp9);
        this.add(jp3);
        this.add(jp4);
        this.add(jp8);
        this.add(jp5);
        this.add(jp7);


        //设置布局
        this.setTitle("管理节点");
        this.setLayout(new GridLayout(9, 1));
        this.setSize(250, 300);   //设置窗体大小
        this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //设置仅关闭当前窗口

        this.setVisible(true);  //设置可见
        this.setResizable(false);	//设置不可拉伸大小

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //if (e.getActionCommand()=="测发流程")
        //{
        //    //进入测发流程界面
        //    RoneUI roneUI = new RoneUI(current_user);
        //    roneUI.setVisible(true);
        //}
        //else if (e.getActionCommand()=="测发数据")
        //{
        //    //进入测试数据的界面
        //    RtwoUI rtwoUI = new RtwoUI(current_user);
        //    rtwoUI.setVisible(true);
        //}
        //else 
        if (e.getActionCommand()=="角色变更")
        {
           new ApplyRoleUI(current_user);
        }
        else if (e.getActionCommand()=="权限管理")
        {
            //进入权限管理界面
            RoleManageUI roleManageUI = new RoleManageUI(current_user);
            roleManageUI.setVisible(true);

        }else if (e.getActionCommand()=="重新登录")
        {
            //进入用户登录的界面
            dispose();  //使窗口消失
            new LoginUI();
        }

        else if (e.getActionCommand()=="用户注销")
        {
            //进入用户注销的界面
            new LogoutUI(current_user);
        }
        else if (e.getActionCommand()=="数据追溯")
        {
            //进入用户注销的界面
            new ShowMessageUI();
        }
        else if (e.getActionCommand()=="文件管理")
        {
            //进入用户注销的界面
            new FileUploadUI(current_user);
            //new FileUploadUI();
        }


    }


}


