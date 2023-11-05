package contract.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import MinIO.*;

import contract.Contracts.User_Contract;
import contract.Struct.User;
import contract.Struct.FileInfo;

import Communication.PackageFun;
import contract.Contracts.Tool;
import contract.Contracts.Upload_Contract;
import contract.Contracts.Inquiry_Contract;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import java.util.ArrayList;

//完成文件下载
public class FileDownloadUI extends JFrame{
    
    int current_Role = 3;
	User current_user = new User();

	public String filePath;
	public String filename;
	public boolean flag=false;

    //
    
    JPanel jp1,jp3,jp4,jp5;
    JButton jbchoose,jbdownload,jbview;
    JTextField jtfid,jtfhash,jtffn;
	JLabel jlid,jlhash,jlfn;
	JFileChooser jfc;
	/* 
    public static void main(String[] args)  {
        FileDownloadUI show = new FileDownloadUI();//显示界面
	}
	*/
    
    public FileDownloadUI(User user){
		
		//String dname = user.getUser_name();//Doctor name
		//String dID = user.getWork_number();//Doctor id
		//System.out.println(dname);
		//System.out.println(dID);
		current_user =user;
		
		jp1=new JPanel();
    	jbdownload=new JButton("确认下载");
    	jp1.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    	jp1.add(jbdownload);
		

		jp3=new JPanel();
		jlid = new JLabel("患者I\u2003D");
        jtfid = new JTextField(15);
		jp3.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
    	jp3.add(jlid);
		jp3.add(jtfid);

        jp4=new JPanel();
		jlhash = new JLabel("摘要");
        jtfhash = new JTextField(15);
		jp4.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
    	jp4.add(jlhash);
		jp4.add(jtfhash);
		
		jp5=new JPanel();
		jlfn = new JLabel("文件名");
        jtffn = new JTextField(15);
		jp5.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
    	jp5.add(jlfn);
		jp5.add(jtffn);

		this.add(jp3);//id
		this.add(jp4);//filehash
		this.add(jp1);//confirm&download
		this.add(jp5);//filename
    
    	//设置布局
		this.setTitle("文件下载");
		this.setSize(500, 350);				//设置界面像素
		this.setLayout(new GridLayout(4,1));//设置窗口布局
		this.setLocation(200, 200);			//设置界面初始位置
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setVisible(true);			//设置界面可视化

	

	jbdownload.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(jtffn.getText().isEmpty()||jtfhash.getText().isEmpty()||jtfid.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "信息有空缺，请补全！","消息提示",JOptionPane.WARNING_MESSAGE);
			} else {
				String pid = jtfid.getText();//Patient ID
				String fname = jtffn.getText();//File name
				String fhash = jtfhash.getText();

				String[] strArr = {pid, fname, fhash};
				String returnStr;

				returnStr = Inquiry_Contract.downloadFile(strArr, Tool.FROMCLIENT);
				boolean flag = returnStr.contains(fhash);
				if(flag){
					JOptionPane.showMessageDialog(null, "下载成功！","提示消息",JOptionPane.WARNING_MESSAGE);
					dispose();  //使“文件上传”窗体消失
				}else{
					JOptionPane.showMessageDialog(null, "jiaoyan失败！","提示消息",JOptionPane.WARNING_MESSAGE);
				}
			}

		}
	});
}	
    	

}
