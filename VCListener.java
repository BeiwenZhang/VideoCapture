package VideoCapture;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.imageio.plugins.tiff.TIFFImageReadParam;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VCListener implements ActionListener {
    private Graphics g =null;
    //现在需要把那边的graphics传过来，写一个封装方法
    public void setGraphics(Graphics g){
        this.g=g;
    }//当参数需要从一个类传到另一个类的时候，使用这种方法（写一个方法
    //在那个类里面创建这个类的对象之后调用这个方法

    boolean flag=true;
    Webcam webcam0=null;
    RunVideoAble rvt=null;

    private void camOP(String str) {
        if(str.equals("启动")){
            webcam0=Webcam.getDefault();//有这样一个静态方法直接获取
            //加载摄像头，还有开关
            //现在咱们，启动摄像头
            webcam0.setViewSize(WebcamResolution.VGA.getSize());
            webcam0.open();
            //注意这里是把前面启动摄像头、建立线程这些都放在这

            rvt=new RunVideoAble(webcam0,g);
            rvt.open();
            //接下来启动线程

            Thread t=new Thread(rvt);
            t.start();


        }//if 启动



        else if(str.equals("关闭")){
            webcam0.close();
            rvt.close();//在写了rvt之后，open，close就可以代替对flag的操作
            //相当于两个按钮对应的是两个线程同时进行，所以我在其中一个线程进入
            //死循环的时候，仍然可以对另一个线程的内容进行操作
        }
        else if(str.equals("拍照")){
//自己实现，把image存在本地，用imageIO写成一张图片
            //因为是在RunAble里面拿到的getImage，故也是在RunAble里面写getImage
            //的方法，现在调用即可
            BufferedImage image=rvt.getImage();
            //绘制
            g.drawImage(image,50,300,null);
            //打开文件保存,注意，文件选择器是可以代码复制的！
            //注意这一串是谁家给你写好了的
            JFileChooser chooser = new JFileChooser();
            //下面这个是文件名过滤器
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
            //设置文件名过滤器
            chooser.setFileFilter(filter);
            //打开文件选择框
            //注意同时要把open改成save，然后parent写为null，此时基本上会居中显示
            int returnVal = chooser.showSaveDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
                try {
                    ImageIO.write(image,"png",chooser.getSelectedFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            //存储
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {//actionEvent是自带参数
        String ac=e.getActionCommand();//鼠标监听器(后面是返回的值），获取文本
     //这个方法如果之前没有赋值，是null就会返回cam;
        //如果有赋值就返回赋值
        //现在还是需要文本去做if判断
        JButton btn=(JButton) e.getSource();//e点击后的东西强制转换成按钮
        String str=btn.getText();

        if(ac.equals("camOP")){
camOP(str);
        }else if(ac.equals("videoOP")){
rvt.setVideoOP(str);
        }







    }
}




