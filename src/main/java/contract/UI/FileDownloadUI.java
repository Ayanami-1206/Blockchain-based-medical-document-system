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


public class FileDownloadUI extends JFrame{
    
    int current_Role = 3;
	User current_user = new User();

	public String filePath;
	public String filename;
	public boolean flag=false;

    //
    
    public FileDownloadUI(User user){
        current_user = user;

    }
}
