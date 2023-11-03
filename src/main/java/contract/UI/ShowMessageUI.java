package contract.UI;

import javax.swing.*;

import contract.Contracts.Tool;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowMessageUI extends JFrame {	//继承JFrame顶层框架

    //定义组件
    //上部组件
    JPanel jp1;		//定义面板
    JSplitPane jsp;	//定义拆分窗格
    JTextArea jta1;	//定义文本域
    JScrollPane jspane1;	//定义滚动窗格
    JTextArea jta2;
    JScrollPane jspane2;
    //下部组件
    JPanel jp2;
    JButton jb1,jb2;	//定义按钮
    JComboBox jcb1;		//定义下拉框

    public ShowMessageUI(GraphicsConfiguration gc) {
        super(gc);
    }

    public static void main(String[] args)  {
        ShowMessageUI show = new ShowMessageUI();//显示界面
    }
    public ShowMessageUI()		//构造函数
    {
        //创建组件
        //上部组件
        jp1=new JPanel();	//创建面板
        jta1=new JTextArea();	//创建多行文本框
        jta1.setLineWrap(true);	//设置多行文本框自动换行
        jta1.setEnabled(false);//设置文本不可写
        jta1.setDisabledTextColor(Color.BLACK);
        jspane1=new JScrollPane(jta1);	//创建滚动窗格
        jta2=new JTextArea("请输入要查询的用户的名字");
        jta2.setLineWrap(true);
        jspane2=new JScrollPane(jta2);
        jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,jspane1,jspane2); //创建拆分窗格
        jsp.setDividerLocation(200);	//设置拆分窗格分频器初始位置
        jsp.setDividerSize(1);			//设置分频器大小
        //下部组件
        jp2=new JPanel();
        //创建按钮
        jb2=new  JButton("获取数据");
        String [] name= {"注册","登录","资源访问","权限变更","注销", "文件上传"};
        //userRegister, userLogin, operateResource/changeResource, 
        //applyRole/changeRole, userout
        jcb1=new JComboBox(name);	//创建下拉框

        //设置布局管理
        jp1.setLayout(new BorderLayout());	//设置面板布局
        jp2.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //添加组件
        jp1.add(jsp);
        jp2.add(jcb1);
        jp2.add(jb2);

        this.add(jp1,BorderLayout.CENTER);
        this.add(jp2,BorderLayout.SOUTH);

        //设置窗体实行
        this.setTitle("数据追溯");		//设置界面标题
        this.setSize(400, 350);				//设置界面像素
        this.setLocation(200, 200);			//设置界面初始位置
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);			//设置界面可视化

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = jta2.getText();
                int type=jcb1.getSelectedIndex();
                //检索数据显示在文本框中
                // String content = "hello BolckChain";
                String content=Tool.queryBlockData(name, type);
                jta1.setText(content);
            }
        });
    }


}
