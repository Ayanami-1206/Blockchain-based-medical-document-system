package contract.UI;

import Communication.PackageFun;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Struct.ApplyMessage;
import contract.Struct.DbStore;
import contract.Struct.Resource;
import contract.Struct.User;
import contract.Struct.FileInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

//权限管理的界面
public class FileSearchUI extends JFrame{

    private DefaultTableModel tableModel;   //表格模型对象
    private JTable table;
    User currentUser = new User();

    public FileSearchUI(User usertemp)
    {
        super();
        currentUser = usertemp;
        setTitle("文件查询");
        setBounds(100,100,500,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //String[] columnNames = {"申请人","工号","原角色","申请角色","申请时间"};   列明
        String[] columnNames = {"医生","文件简介","文件摘要","上传时间"};
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
                Object od = tableModel.getValueAt(selectedRow, 3);

            }
        });
        scrollPane.setViewportView(table);
        final JPanel panel = new JPanel();
        getContentPane().add(panel,BorderLayout.SOUTH);


        final JButton downloadButton = new JButton("文件下载");
        downloadButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                //清空原有表格
                ((DefaultTableModel) table.getModel()).getDataVector().clear();   //清除表格数据
                ((DefaultTableModel) table.getModel()).fireTableDataChanged();//通知模型更新
                table.updateUI();

                ArrayList<FileInfo> fileinfo = new ArrayList<>(Tool.getFileInfoAll());

//                String[] rarray=returnStr.split(";");
//                for(int i=0;i<rarray.length;i+=5){
//                    fileinfo.add(new FileInfo(rarray[i],rarray[i+1],rarray[i+2],rarray[i+3],rarray[i+4]));0
//                }
                // System.out.println("更新数据"+fileinfo);

                for (int index = 0;index<fileinfo.size();index++){
                    FileInfo currentfile = fileinfo.get(index);
                    String[] rowValues = {currentfile.getDoc_number(),currentfile.getUser_number(),currentfile.getFile_name(), currentfile.getFile_hash()};
                    tableModel.addRow(rowValues);  //添加一行
                }
            }
        });
        panel.add(downloadButton);
        final JButton passButton = new JButton("查询单人");   //修改按钮
        passButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();//获得选中行的索引
                if(selectedRow!= -1)   //是否存在选中行
                {
                    //调用权限修改的智能合约
                    String doc_number = table.getValueAt(selectedRow, 0).toString();
                    String User_number = table.getValueAt(selectedRow, 1).toString();
                    String file_name = table.getValueAt(selectedRow, 2).toString();
                    String file_hash = table.getValueAt(selectedRow, 3).toString();

                    String[] strArr = {doc_number, User_number, file_name, file_hash};
                    // String returnStr = User_Contract.changeRole(strArr,Tool.FROMCLIENT);
//                    String returnStr = Tool.getFileInfo(User_number);
                    ArrayList<FileInfo> fileinfo = new ArrayList<>(Tool.getFileInfos(User_number));
//                    String[] rarray = returnStr.split(";");
//                    for (int i = 0; i < rarray.length; i += 4) {
//                        fileinfo.add(new FileInfo(rarray[i], rarray[i + 1], rarray[i + 2], rarray[i + 3]));

//                    }
//                    FileInfo currentfile = fileinfo.get(index);
//                    String[] rowValues = {currentfile.getApply_name(), currentfile.getApply_worknumber(), currentfile.getOld_role(), currentfile.getNew_role(), currentMessage.getApply_time()};
//                    tableModel.addRow(rowValues);
                }
            }
        });
        panel.add(passButton);
    }


}
