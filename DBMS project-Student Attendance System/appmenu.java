import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
/**
 * Write a description of class appmenu here.
 *
 * @author (AKHILESH)
 * @version (01 or a 14-OCT-2017)
 */
public class appmenu{
    private String year;
    private String userName;
    private String userRole;
    private String staffid=null;
    private int ssid=0;
    private String staffname;
    private String usn;
    private String studname;
    private String studstatus=null;
    private String branch;
    private String sec;
    private String subjcode=null;
    private String subjname=null;
    private String sem=null;
    private Boolean userStatus;
    private JPanel JpnlInput,JpnlTitle;
    private JPanel JpnlButtons,mainPanel;
    private JPanel pmenu;
    private Connection pconnection;
    private JFrame myframe;
    private JLabel lbl_image;
    private String ImgPath = null;
    private JPanel jPanelptitle,JpanelTop;
    private JLabel jlbaptitle;
    private int pxw=1000,pxh=600;
    public appmenu(String loginName,String loginRole, Boolean loginStatus, Connection myconnection,String id,String name,String branch,String section, String subjcode, String subjname,String sem, String status){
        this.userName = loginName;
        System.out.println("login name="+loginName);
        System.out.println("login role="+loginRole);
        System.out.println("staff id="+id);
        this.userRole = loginRole;
        this.pconnection = myconnection;       
    if (loginRole.equals("STAFF")){
        this.staffid = id;
        this.ssid=Integer.parseInt(id);
        this.staffname = name;
        }
    if (loginRole.equals("STUDENT")){
        this.usn = id;
        this.studname = name;
        this.studstatus=status;
        this.branch = branch;
        this.sec = section;
    }
    if (loginRole.equals("ADMIN"))
        this.staffid = id;
        System.out.println("STAFF ID="+id);
    }
    public String getsemester() { 
        return this.sem; 
    }
    public String getstudstatus() { 
        return this.studstatus; 
    }
    public String getsbranch() { 
        return this.branch; 
    }
    public String getssection() { 
        return this.sec; 
    }
   public String getstaffname() { 
        return this.staffname; 
    }
    public String getstudname() { 
        return this.studname; 
    }
    public String getstaffsem() { 
        return this.sem; 
    }
    public String getsubjname() { 
        return this.subjname; 
    }
    public String getsubjcode() { 
        return this.subjcode; 
    }
    public Connection getuserConnection() { 
        return this.pconnection; 
    } 
    public void setuserConnection(Connection myconnection) { 
        this.pconnection = myconnection; 
    } 
    public String getuserName() { 
        return this.userName; 
    } 
    
    public String getStaffid() { 
        return this.staffid; 
    } 
    
    public int getssid() { 
        return this.ssid; 
    } 
    public String getStudid() { 
        return this.usn; 
    } 
    public void setuserName(String loginName) { 
        this.userName = loginName; 
    } 
   
    public String touserString() { 
        return this.userName; 
    }
    public String getuserRole() { 
        return this.userRole; 
    } 
   
    public void setuserRole(String loginRole) { 
        this.userRole = loginRole; 
    } 
    public String toroleString() { 
        return this.userRole; 
    }
    public Boolean getuserStatus() { 
        return this.userStatus; 
    }  
    public void setuserStatus(Boolean loginStatus) { 
        this.userStatus = loginStatus; 
    } 
    public Boolean touserBoolean() { 
        return this.userStatus; 
}


// Function To Resize The Image To Fit Into JLabel
public ImageIcon ResizeImage(String imagePath, byte[] pic){
ImageIcon myImage = null;
if(imagePath != null)
{
    myImage = new ImageIcon(imagePath);
}else{
    myImage = new ImageIcon(pic);
}
Image img = myImage.getImage();
Image img2 = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
ImageIcon image = new ImageIcon(img2);
return image;
}
/**
 * Create the Menu for the Staff attendance
 *
 * @author (AKHILESH)
 * @version (01 or a 14-OCT-2017)
 */ 
public void CreateMenu(){
        JpnlTitle =new JPanel();
        JpanelTop =new JPanel();
        jPanelptitle = new JPanel();
        jPanelptitle.setBackground(new java.awt.Color(255, 178,102));
        jlbaptitle = new JLabel();
        jlbaptitle.setFont(new java.awt.Font("Tahoma", 1, 16));
        JpanelTop.setLayout(new GridLayout(2,1,30,30));
        JpanelTop.setBackground(new java.awt.Color(51, 255, 51));
        JpnlTitle.setLayout(new GridLayout(1,1,20,20));
        JpnlTitle.setBackground(new java.awt.Color(255, 255, 204));
        JLabel jlapptitle = new JLabel();
        jlapptitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI14N
        jlapptitle.setText("Attendance Application Menu");
        JpnlTitle.add(jlapptitle); // north of the frame     
        JpnlInput = new JPanel();  // holds user login information
        JpnlInput.setLayout(new GridLayout(1,6,25,25));
        JpnlInput.setBackground(new java.awt.Color(255, 204, 0));
        JLabel jlname = new JLabel();
        jlname.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlname.setText(this.getuserName());
        JLabel jlrole = new JLabel();
        jlrole.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlrole.setText(this.getuserRole());
        JpnlInput.add(jlname);
        JpnlInput.add(jlrole);  // south of the frame
        if (this.getuserRole().equals("STAFF")){
        JLabel jlstaffid = new JLabel();
        jlstaffid.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlstaffid.setText(this.getStaffid());
        JLabel jlstaffname = new JLabel();
        jlstaffname.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlstaffname.setText(this.getstaffname());
        JpnlInput.add(jlstaffid);
        JpnlInput.add(jlstaffname);
    }
        if (this.getuserRole().equals("STUDENT")){
        JLabel jlusn = new JLabel();
        jlusn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlusn.setText(this.getStudid());
        JLabel jlstudname = new JLabel();
        jlstudname.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlstudname.setText(this.getstudname());
        JLabel jlstudstatus = new JLabel();
        jlstudstatus.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI14N
        jlstudstatus.setText(this.getstudstatus());
        JpnlInput.add(jlusn);
        JpnlInput.add(jlstudname);
        JpnlInput.add(jlstudstatus);
//        JpnlInput.setBackground(new java.awt.Color(0, 205, 204));
    }
myframe=new JFrame();
myframe.setTitle("Student Attendance Application");
myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //exit upon closing
//get the dimension of the screen
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
int screenWidth =screenSize.width;
int screenHeight =screenSize.height;
//Get the Dimension of the Frame
Dimension frameSize = myframe.getSize();
int x =(screenWidth-frameSize.width)/2;
int y =(screenHeight-frameSize.height)/2;
myframe.setLocation(x,y); //set the frame to the center of the screen
myframe.setMinimumSize(new Dimension(pxw, pxh));
myframe.setPreferredSize(new Dimension(pxw, pxh));
myframe.setSize(pxw,pxh);
myframe.setLocationRelativeTo(null);
myframe.pack();
myframe.setVisible(true);
}
    public void CreateStaffMenu(){
      pxw=600; pxh=250;  
      CreateMenu();
      JpnlButtons = new JPanel(new GridLayout(1,3,12,12)); // menu pof button should go the center
      JButton jbstats=new JButton(); //component 21
      jbstats.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbstats.setIcon(new ImageIcon(getClass().getResource("stats.png"))); // NOI14N
      jbstats.setText("Statistics");
      jbstats.setIconTextGap(15);
      JpnlButtons.add(jbstats);

      jbstats.addActionListener(new ActionListener(){              
      public void actionPerformed(ActionEvent e){    
      StaffStudPercAttendForm stfstudperc=new StaffStudPercAttendForm(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getstaffname());
      stfstudperc.CreateStaffStudPercAttendForm();
     }  
});
      JButton jbtakeattend=new JButton(); //component 21
      jbtakeattend.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbtakeattend.setIcon(new ImageIcon(getClass().getResource("attendance.png"))); // NOI14N
      jbtakeattend.setText("Take Attendance");
      jbtakeattend.setIconTextGap(15);
      jbtakeattend.setToolTipText("Click to Generate/View and save Attendance");
      JpnlButtons.add(jbtakeattend);    
      jbtakeattend.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){ 
             StaffStudAttendForm stafstud= new StaffStudAttendForm(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getstaffname());
             stafstud.CreateStaffAttendForm();
            } 
});
jlbaptitle.setText("STAFF MENU");
jPanelptitle.add(jlbaptitle);
JpanelTop.add(jPanelptitle);
JpanelTop.add(JpnlTitle);
myframe.add(JpanelTop, BorderLayout.NORTH);       
myframe.add(JpnlButtons, BorderLayout.CENTER);
myframe.add(JpnlInput, BorderLayout.SOUTH);
}

/**
 * Create the Menu for the Student viewing
 *
 * @author (AKHILESH)
 * @version (01 or a 14-OCT-2017)
 */
      public void CreateStudentMenu(){
      pxw=800; pxh=250;    
      CreateMenu();
      JpnlButtons = new JPanel(new GridLayout(1,1,30,30)); // menu pof button should go the center
      JpnlButtons.setBackground(new java.awt.Color(51, 102, 0));
      JButton jbattendance=new JButton(); //component 11
      jbattendance.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI14N
      jbattendance.setIcon(new ImageIcon(getClass().getResource("attendance.png"))); // NOI14N
      jbattendance.setText("Percentage Attendance");
      jbattendance.setIconTextGap(30);
      jbattendance.setToolTipText("Click to View Student Percentage Attendance All subjects");
      jbattendance.addActionListener(new ActionListener(){  
       public void actionPerformed(ActionEvent e){    
       StudPercAttendForm spaf=new StudPercAttendForm(getuserName(),getuserRole(),true,getuserConnection(),getStudid(),getstudname());
       spaf.CreateStudPercAttendForm();
       }  
        }); 
JpnlButtons.add(jbattendance);
jlbaptitle.setText("STUDENT MENU");
jPanelptitle.add(jlbaptitle);
JpanelTop.add(JpnlTitle);
JpanelTop.add(jPanelptitle);
myframe.add(JpanelTop, BorderLayout.NORTH);     
myframe.add(JpnlButtons,BorderLayout.CENTER);
myframe.add(JpnlInput,BorderLayout.SOUTH);
}
    
 /*
 /* Create the Menu for the administrator.
 *
 * @author (AKHILESH)
 * @version (01 or a 14-OCT-2017)
 */    
public void CreateAdminMenu(){
     CreateMenu();
     JpnlButtons = new JPanel(new GridLayout(4,3,12,12)); // menu pof button should go the center
     JButton jbbranch=new JButton();  //component 01
     jbbranch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbbranch.setIcon(new ImageIcon(getClass().getResource("branch.png"))); // NOI14N
      jbbranch.setText("Branch");
      jbbranch.setIconTextGap(15);
      jbbranch.setToolTipText("Click to Setup/View Branch/Department Information");
      JpnlButtons.add(jbbranch);
      jbbranch.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){  
          BranchApp MybranchMenu = new BranchApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
          MybranchMenu.CreateBranchForm();
            }  
        });
     //   JButton jbcourse=new JButton("Course"); //component 02
      JButton jbcourse=new JButton();  //component 01
      jbcourse.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbcourse.setIcon(new ImageIcon(getClass().getResource("courses.png"))); // NOI14N
      jbcourse.setText("Student Courses");
      jbcourse.setToolTipText("Click to Setup/View Student Registered Courses");
      jbcourse.setIconTextGap(15);
      JpnlButtons.add(jbcourse);
      jbcourse.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){ 
             System.out.println("SCOURSE constructor executed"+userName+" " +userRole+" "+staffid+ " "+getStudid());
             ScourseApp MycourseMenu = new ScourseApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
             System.out.println("SCOURSE constructor executed"+getuserName()+" " +getuserRole()+" "+getStaffid());
             MycourseMenu.CreateSCourseForm();
            }  
        }); 
      // JButton jbperiod=new JButton("Period"); //component 03
       JButton jbperiod=new JButton();  //component 01
      jbperiod.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbperiod.setIcon(new ImageIcon(getClass().getResource("periods.png"))); // NOI14N
      jbperiod.setText("Period");
      jbperiod.setIconTextGap(15);
      jbperiod.setToolTipText("Click to Setup/View Academic Semester Calendar");
      JpnlButtons.add(jbperiod);
      jbperiod.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){ 
             PeriodApp MyperiodMenu = new PeriodApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
            MyperiodMenu.CreatePeriodForm();
            }  
        });
      //  JButton jbsection=new JButton("Section");  //component 11
      JButton jbsection=new JButton();  //component 01
      jbsection.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbsection.setIcon(new ImageIcon(getClass().getResource("sections.png"))); // NOI14N
      jbsection.setText("Section");
      jbsection.setIconTextGap(15);
      jbsection.setToolTipText("Click to Setup/View Sections");
      JpnlButtons.add(jbsection);
      jbsection.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){   
            SectionApp MysectionMenu = new SectionApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
           MysectionMenu.CreateSectionForm();
            }  
        }); 
     //   JButton jbstaff=new JButton("Staff");  //component 12
      JButton jbstaff=new JButton();  //component 01
      jbstaff.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbstaff.setIcon(new ImageIcon(getClass().getResource("faculty.png"))); // NOI14N
      jbstaff.setText("Staff");
      jbstaff.setIconTextGap(15);
      jbstaff.setToolTipText("Click to Setup/View Staff/faculty Details");
      JpnlButtons.add(jbstaff);
      jbstaff.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){    
            StaffApp MystaffMenu = new StaffApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
            MystaffMenu.CreateStaffForm();
            }  
        });  
    //  JButton jbstudent=new JButton("Student"); //component 13
      JButton jbstudent=new JButton();  //component 01
      jbstudent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbstudent.setIcon(new ImageIcon(getClass().getResource("students.png"))); // NOI14N
      jbstudent.setText("Student");
      jbstudent.setIconTextGap(15);
      jbstudent.setToolTipText("Click to Setup/View Student Details");
      JpnlButtons.add(jbstudent);
      jbstudent.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){    
           StudentApp MystudentMenu = new StudentApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
           MystudentMenu.CreateStudentForm();
            }  
        });  
      //JButton jbsubject=new JButton("Subject");  //component 21
       JButton jbsubject=new JButton();  //component 01
      jbsubject.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbsubject.setIcon(new ImageIcon(getClass().getResource("subjects.png"))); // NOI14N
      jbsubject.setText("Subject");
      jbsubject.setIconTextGap(15);
      jbsubject.setToolTipText("Click to Setup/View Subject Catalog");
      JpnlButtons.add(jbsubject);
      jbsubject.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){    
           SubjectsApp MysubjectMenu = new SubjectsApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
           MysubjectMenu.CreateSubjectsForm();
            }  
        }); 
     // JButton jbstaffalloc=new JButton("Staff Allocation"); //component 22
      JButton jbstaffalloc=new JButton();  //component 01
      jbstaffalloc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbstaffalloc.setIcon(new ImageIcon(getClass().getResource("staffallocation.png"))); // NOI14N
      jbstaffalloc.setText("Staff Allocation");
      jbstaffalloc.setIconTextGap(15);
      jbstaffalloc.setToolTipText("Click to Setup/View Staff-Subject-Section-Branch mapping");
      JpnlButtons.add(jbstaffalloc);
      jbstaffalloc.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){    
         StaffAllocApp MystaffAllocMenu = new StaffAllocApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
         MystaffAllocMenu.CreateStaffAllocForm();
            }  
        }); 
     // JButton jbacadyr=new JButton("Academic Year"); //component 23
      JButton jbacadyr=new JButton();  //component 01
      jbacadyr.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbacadyr.setIcon(new ImageIcon(getClass().getResource("academicyear.png"))); // NOI14N
      jbacadyr.setText("Academic Year");
      jbacadyr.setIconTextGap(15);
      jbacadyr.setToolTipText("Click to Setup/View Academic Year  Table");
      JpnlButtons.add(jbacadyr);
      jbacadyr.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){ 
               AcadYearApp MyacadyearMenu = new AcadYearApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
              MyacadyearMenu.CreateAcadYearForm();
            }  
        }); 
     // JButton jbmnguser=new JButton("Manage User"); //component 31
      JButton jbmnguser=new JButton();  //component 01
      jbmnguser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbmnguser.setIcon(new ImageIcon(getClass().getResource("loginusers.png"))); // NOI14N
      jbmnguser.setText("Manage User");
      jbmnguser.setIconTextGap(15);
      jbmnguser.setToolTipText("Click to Manage Login User Table");
      JpnlButtons.add(jbmnguser);
      jbmnguser.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){ 
             LoginApp MyManageUserMenu = new LoginApp(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
             MyManageUserMenu.CreateLoginAppForm();
            }  
        });
        
      JButton jbviewattend=new JButton(); //component 21
      jbviewattend.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI14N
      jbviewattend.setIcon(new ImageIcon(getClass().getResource("view.png"))); // NOI14N
      jbviewattend.setText("View Attendance");
      jbviewattend.setIconTextGap(15);
      jbviewattend.setToolTipText("View Attendance");
      JpnlButtons.add(jbviewattend);    
      jbviewattend.addActionListener(new ActionListener(){  
          public void actionPerformed(ActionEvent e){ 
             AdminAttendenceViewForm adminViewAttend= new AdminAttendenceViewForm(getuserName(),getuserRole(),true,getuserConnection(),getStaffid(),getStudid());
             adminViewAttend.CreateAdminAttendView();
            } 
});
jlbaptitle.setText("ADMIN MENU");
jPanelptitle.add(jlbaptitle);
JpanelTop.add(jPanelptitle);
JpanelTop.add(JpnlTitle);
myframe.add(JpanelTop, BorderLayout.NORTH);
myframe.add(JpnlButtons, BorderLayout.CENTER);       
myframe.add(JpnlInput, BorderLayout.SOUTH);
}
}
