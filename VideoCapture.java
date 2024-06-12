package VideoCapture;

import com.github.sarxos.webcam.Webcam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoCapture extends JFrame {

    //初始化监听器对象
    VCListener  vcl=new VCListener();
    //之后要做的 1，把vcl加入按钮的监听器 2，把画笔传过去（setGraphics）
    public void initUI(){
        setTitle("Video Capture");
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //我们现在加个面板，流式布局去掉
       // setLayout(new FlowLayout());
        JPanel btnpanel=new JPanel();
btnpanel.setBackground(Color.orange);
btnpanel.setPreferredSize(new Dimension(0,100));

        //下面加入按钮
        String[] strs={"启动","关闭","拍照"};
        for(String str:strs){//增强型for循环，数据类型 变量名：集合//可实现遍历集合里所有，一个一个取出来，每个取出来的变量都是str
        JButton btn= new JButton(str);
            btn.setActionCommand("camOP");
        btn.setPreferredSize(new Dimension(90,35));
        btn.addActionListener(vcl);//目前还没有
            btnpanel.add(btn);
        }

        String[] FilterStrs={"原图","灰度", "二值化","轮廓提取","马赛克","美白","油画","底片"};
        for(String str:FilterStrs){//增强型for循环，数据类型 变量名：集合//可实现遍历集合里所有，一个一个取出来，每个取出来的变量都是str
            JButton btn= new JButton(str);
            btn.setActionCommand("videoOP");
            btn.setPreferredSize(new Dimension(90,35));
            btn.addActionListener(vcl);//目前还没有
            btnpanel.add(btn);
        }

        JPanel videopanel=new JPanel();
        videopanel.setBackground(Color.gray);
        //面板panel这些是和BorderLayout搭配使用，可以把整个界面划分为不同的区块，
        //videopanel用于视频，因为vcl调用时候传参数传的画笔是从这个面板上获取的

        add(btnpanel,BorderLayout.SOUTH);
        add(videopanel,BorderLayout.CENTER);
        //最后一步是可视化
        setVisible(true);
        //获取graphics
     //   Graphics g=getGraphics();//先弄一个新的对象
    //    vcl.setGraphics(g);//再把画笔传过去，只有vcl才能调用这个listener里的方法
      //  vcl.setGraphics(getGraphics());
vcl.setGraphics(videopanel.getGraphics());//改成panel来获取画笔

    }

    public static void main(String[] args)
    {
        new VideoCapture().initUI();
}


}
