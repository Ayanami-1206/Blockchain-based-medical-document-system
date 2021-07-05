package contract.UI;

import Communication.PackageFun;
import contract.Contracts.Tool;
import contract.Contracts.User_Contract;
import contract.Struct.ApplyMessage;
import contract.Struct.DbStore;
import contract.Struct.User;

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
public class RoleManageUI extends JFrame{

    private DefaultTableModel tableModel;   //表格模型对象
    private JTable table;
    User currentUser = new User();

    public RoleManageUI(User usertemp)
    {
        super();
        currentUser = usertemp;
        setTitle("权限管理");
        setBounds(100,100,500,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] columnNames = {"申请人","工号","原角色","申请角色","申请时间"};   //列名
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
                Object oe = tableModel.getValueAt(selectedRow, 4);
            }
        });
        scrollPane.setViewportView(table);
        final JPanel panel = new JPanel();
        getContentPane().add(panel,BorderLayout.SOUTH);

        final JButton updateButton = new JButton("更新数据");   //添加按钮
        updateButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                //清空原有表格
                ((DefaultTableModel) table.getModel()).getDataVector().clear();   //清除表格数据
                ((DefaultTableModel) table.getModel()).fireTableDataChanged();//通知模型更新
                table.updateUI();
                //读取存储的申请数据，显示在表格中
                ArrayList<ApplyMessage> applyMessages = Tool.getApplayMessages();
                System.out.println(applyMessages);
                for (int index = 0;index<applyMessages.size();index++){
                    ApplyMessage currentMessage = applyMessages.get(index);
                    String []rowValues = {currentMessage.getApply_name(),currentMessage.getApply_worknumber(),currentMessage.getOld_role(),
                    currentMessage.getNew_role(),currentMessage.getApply_time()};
                    tableModel.addRow(rowValues);  //添加一行
                }
            }
        });
        panel.add(updateButton);
        final JButton passButton = new JButton("通过");   //修改按钮
        passButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();//获得选中行的索引
                if(selectedRow!= -1)   //是否存在选中行
                {
                    //调用权限修改的智能合约
                    String user_name = table.getValueAt(selectedRow,0).toString();
                    String user_work = table.getValueAt(selectedRow,1).toString();
                    String old_role = table.getValueAt(selectedRow,2).toString();
                    String new_role = table.getValueAt(selectedRow,3).toString();

                    String[] strArr = {user_name,user_work,old_role,new_role,"1"};
                    String returnStr = User_Contract.changeRole(strArr,false);
                    int result = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
                    String output = returnStr.split("&")[1];
                    String input = "contract.Contracts.User_Contract,userout;"+currentUser.getUser_name()+","+currentUser.getWork_number()+","+new_role+","+"1"+";"+output;
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
                    tableModel.removeRow(selectedRow);  //删除行
                }
            }
        });
        panel.add(passButton);

        final JButton noPassButton = new JButton("不通过");
        noPassButton.addActionListener(new ActionListener(){//添加事件
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();//获得选中行的索引
                if(selectedRow!=-1)  //存在选中行
                {
                    //调用权限变更的智能合约
                    String user_name = table.getValueAt(selectedRow,0).toString();
                    String user_work = table.getValueAt(selectedRow,1).toString();
                    String old_role = table.getValueAt(selectedRow,2).toString();
                    String new_role = table.getValueAt(selectedRow,3).toString();

                    String[] strArr = {user_name,user_work,old_role,new_role,"0"};
                    String returnStr = User_Contract.changeRole(strArr,false);
                    int result = Integer.parseInt(returnStr.substring(returnStr.length()-1,returnStr.length()));
                    String output = returnStr.split("&")[1];
                    String input = "contract.Contracts.User_Contract,userout;"+currentUser.getUser_name()+","+currentUser.getWork_number()+","+new_role+","+"0"+";"+output;
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
                    tableModel.removeRow(selectedRow);  //删除行
                }
            }
        });
        panel.add(noPassButton);
    }


}
