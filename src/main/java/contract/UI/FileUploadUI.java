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

/**
 * 此类完成对文件的上传
*/
 
public class FileUploadUI extends JFrame{
	int current_Role = 3;
	User current_user = new User();

	public static MinioUtil miniotool;
	public String filePath;
	public String filename;
	public boolean flag=false;

	JPanel jp1,jp2,jp3,jp4;
    JButton jbchoose,jbupload,jbview;
    JTextField jtfusn,jtfid,jtfchoose;
	JLabel jlusn, jlid,jlchoose;
	JFileChooser jfc;
	/* 
    public static void main(String[] args)  {
        FileUploadUI show = new FileUploadUI();//显示界面
	}
	*/
    
    public FileUploadUI(User user){
		
		String dname = user.getUser_name();//Doctor name
		String dID = user.getWork_number();//Doctor id
		System.out.println(dname);
		System.out.println(dID);
		current_user =user;

    	jp1=new JPanel();
    	jbupload=new JButton("确认上传");
    	jp1.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
    	jp1.add(jbupload);

		jp2=new JPanel();
		jlusn = new JLabel("患者姓名"); 
		jtfusn = new JTextField(15);
		jp2.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));
		jp2.add(jlusn);
		jp2.add(jtfusn);

		jp3=new JPanel();
		jlid = new JLabel("患者I\u2003D");
        jtfid = new JTextField(15);
		jp3.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
    	jp3.add(jlid);
		jp3.add(jtfid);

		jp4=new JPanel();
		jlchoose=new JLabel("所选文件路径：");
		jtfchoose=new JTextField(25);
		jbview=new JButton("浏览");
		jp4.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		jp4.add(jlchoose);
		jp4.add(jtfchoose);
		jp4.add(jbview);

		this.add(jp2);//name
		this.add(jp3);//id
		this.add(jp4);//filepath
		this.add(jp1);//confirm&upload
    
    	//设置布局
		this.setTitle("医疗文件上传");
		this.setSize(500, 350);				//设置界面像素
		this.setLayout(new GridLayout(4,1));//设置窗口布局
		this.setLocation(200, 200);			//设置界面初始位置
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setVisible(true);			//设置界面可视化
    	
    	
    	jbupload.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				if(jtfusn.getText().isEmpty()||jtfid.getText().isEmpty()||jtfchoose.getText().isEmpty())
				{
					System.out.println(jtfusn.getText());
					JOptionPane.showMessageDialog(null, "信息有空缺，请补全！","消息提示",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					String pname = jtfusn.getText();//Patient name
					String pid = jtfid.getText();//Patient ID

					//Smart Contract
					// String[] a = {"1","2","3","4","5"};
					// String b = Upload_Contract.fileUpload(a,1);
					// System.out.println(b);
					String[] strArr = {dID,pname,pid,filename,"test"};
					String returnStr; 

					try{
						strArr[4] = FileHashUtil.md5HashCode32(filePath);
					}catch(Exception ex) {
						ex.printStackTrace();
					}

//					System.out.println(filename);
//					System.out.println(strArr[4]);
					
					returnStr = Upload_Contract.fileUpload(strArr, Tool.FROMCLIENT);
					int blockFlag = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
					
					// System.out.println(blockFlag);
					if(blockFlag == 0){
						JOptionPane.showMessageDialog(null, "用户查询失败！","提示消息",JOptionPane.WARNING_MESSAGE);
					}else{
						//Upload

						miniotool=new MinioUtil();
						miniotool.init(pid);//more than 3
						//JOptionPane.showMessageDialog(null, "chushihua！","提示消息",JOptionPane.WARNING_MESSAGE);
					
						flag=miniotool.uploadFile(filename,filePath,pid);

						if(flag){
							JOptionPane.showMessageDialog(null, "上传成功！","提示消息",JOptionPane.WARNING_MESSAGE);
							dispose();  //使“文件上传”窗体消失
							
							String[] a = {pid,filename,strArr[4]};
		
							//String test = Inquiry_Contract.downloadFile(a, Tool.FROMCLIENT);
							//System.out.println(strArr[4]);
							//System.out.println(test);


							//String[] a = {pid};
							//System.out.println(Inquiry_Contract.fileCount(a, Tool.FROMCLIENT));
							//System.out.println(Inquiry_Contract.fileInquiry(a, Tool.FROMCLIENT));
						}else{
							JOptionPane.showMessageDialog(null, "上传失败！","提示消息",JOptionPane.WARNING_MESSAGE);
						
						}
					}
				}
    		}
    	});
    	/*jbchoose.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
			}
        		
    	});*/
		
		jbview.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
				jfc=new JFileChooser("/home/bupt/Desktop/");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  //可以选择文件
				int val=jfc.showOpenDialog(null);    //文件打开对话框
				if(val==jfc.APPROVE_OPTION){
					//正常选择文件
					filePath=jfc.getSelectedFile().toString();
					jtfchoose.setText(filePath);
					String temp[] = filePath.split("/");
					filename = temp[temp.length - 1];
					//label2.setText("选择了文件：【"+jfc.getSelectedFile().getAbsolutePath()+"】");
				}else{
					//未正常选择文件，如选择取消按钮
					jtfchoose.setText("未选择文件");
				}
			}	
    	});
    }
        
    
}


