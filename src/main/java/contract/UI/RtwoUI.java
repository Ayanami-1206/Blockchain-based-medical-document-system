package contract.UI;

import Communication.PackageFun;
import contract.Contracts.Res_Contract;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Struct.Resource;
import contract.Struct.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

//测试资数据的访问界面
public class RtwoUI extends JFrame{
    int current_Role = 10;
    User user;
    private DefaultTableModel tableModel;   //表格模型对象
    private JTable table;


    public RtwoUI(User user_temp) //将修改的内容传递给数据库
    {
        super();
        user = user_temp;
        setTitle("表格");
        setBounds(100,100,550,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {"资源名称","资源权限","资源版本"};   //列名
        String [][]tableVales={}; //数据
        tableModel = new DefaultTableModel(tableVales,columnNames){
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int rowIndex, int columnIndex) 	//重写方法改编可编辑性
            {
                if( columnIndex == getColumnCount())
                    return true;
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);   //支持滚动
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        //排序:
        //table.setRowSorter(new TableRowSorter(tableModel));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //设置表单只可单选
        table.addMouseListener(new MouseAdapter(){    //鼠标事件
            public void mouseClicked(MouseEvent e){
                int selectedRow = table.getSelectedRow(); //获得选中行索引
                Object oa = tableModel.getValueAt(selectedRow, 0);
                Object ob = tableModel.getValueAt(selectedRow, 1);
                Object oc = tableModel.getValueAt(selectedRow, 2);
            }
        });
        scrollPane.setViewportView(table);
        final JPanel panel = new JPanel();
        getContentPane().add(panel,BorderLayout.SOUTH);


        final JButton updateButton = new JButton("获取资源");   //添加按钮
        updateButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                //清空原有表格
                ((DefaultTableModel) table.getModel()).getDataVector().clear();   //清除表格数据
                ((DefaultTableModel) table.getModel()).fireTableDataChanged();//通知模型更新
                table.updateUI();
                //读取存储的申请数据，显示在表格中
                String returnStr=User_Contract.getResource(null, Tool.FROMCLIENT);
                ArrayList<Resource> Res=new ArrayList<>();
                String[] rarray=returnStr.split(";");
                for(int i=0;i<rarray.length;i+=3){
                    Res.add(new Resource(rarray[i],Integer.parseInt(rarray[i+1]),rarray[i+2]));
                }
                System.out.println("获取资源"+Res);
                ArrayList<Resource> Rones = new ArrayList<>();
                for (int i =0;i<Res.size();i++){
                    Resource current =Res.get(i);
                    if (current.getResource_level()==1){
                        Rones.add(current);
                    }
                }
                if (Rones.size() == 0){
                    JOptionPane.showMessageDialog(null, "资源为空","消息提示",JOptionPane.WARNING_MESSAGE);
                }else{
                    for (int index = 0;index<Rones.size();index++){
                        Resource rCurrent = Rones.get(index);
                        if (rCurrent.getResource_level() == 1){
                            //获取第二类数据
                            String []rowValues = {rCurrent.getResource_name(), String.valueOf(rCurrent.getResource_level()),rCurrent.getResource_version()};
                            tableModel.addRow(rowValues);  //添加一行
                        }

                    }
                }

            }
        });

        panel.add(updateButton);

        final JButton passButton = new JButton("删除");   //修改按钮
        passButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();//获得选中行的索引

                if(selectedRow!= -1)   //是否存在选中行
                {
                    int flag = 0;
                    //对数据库中的资源进行操作
                    String old_name = table.getValueAt(selectedRow,0).toString();
                    int old_level = Integer.parseInt(table.getValueAt(selectedRow,1).toString());
                    String old_version = table.getValueAt(selectedRow,2).toString();
                    String old_R = old_name+","+old_level+","+old_version;
//                    Resource old_R = new Resource(old_name,old_level,old_version);
                    String new_R = old_name+","+old_level+","+old_version;
//                    Resource new_R= new Resource(new_name,new_level,new_version);
                    //调用资源查询的代码
                    int r_level = Integer.parseInt(table.getValueAt(selectedRow, 1).toString().trim());
                    String res_name = table.getValueAt(selectedRow, 0).toString();
                    try {
                        current_Role = user.getUser_role();
                        String[] strArr ={String.valueOf(current_Role), String.valueOf(r_level),res_name};
                        String returnStr = Res_Contract.operateResource(strArr,Tool.FROMCLIENT);
                        flag = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
                        String output = returnStr.split("&")[1];
                        String input = "contract.Contracts.Res_Contract,operateResource;"+String.valueOf(current_Role)+","+String.valueOf(r_level)+","+res_name+";"+output;

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
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (flag==1){
                        String operator= "删除";
                        String[] strArr = {old_R,new_R,operator};
                        //将修改的内容传递给数据库
                        String returnStr = Res_Contract.changeResource(strArr,Tool.FROMCLIENT);
                        int result = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
                        String output = returnStr.split("&")[1];
                        String input = "contract.Contracts.Res_Contract,changeResource;"+old_R+","+new_R+","+operator+";"+output;


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
                        if (result==2){
                            tableModel.removeRow(selectedRow);  //删除行
                            JOptionPane.showMessageDialog(null, "删除成功","消息提示",JOptionPane.WARNING_MESSAGE);
                        }else {
                            JOptionPane.showMessageDialog(null, "删除失败","消息提示",JOptionPane.WARNING_MESSAGE);
                        }

                    }else {
                        JOptionPane.showMessageDialog(null, "无法删除","消息提示",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        panel.add(passButton);
    }





}

