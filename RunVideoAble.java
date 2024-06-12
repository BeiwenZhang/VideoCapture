package VideoCapture;

import com.github.sarxos.webcam.Webcam;

import java.awt.*;
import java.awt.image.BufferedImage;
//使用第二种，实现Runable接口的方法去做，在类里面就可以写死循环了
public class RunVideoAble implements Runnable{
String VideoOP;
    boolean flag;
    Webcam webcam0;
    Graphics g;

    BufferedImage image;
    String videoOP="原图";

    ////上面只是声明，但是我们需要把之前我们写的两个参数相关内容和这个连接起来；
//// 现在我们需要把两个参数传进来，那么就使用构造方法
    //构造方法是和类同名，在另一个类中直接创建这个类的时候相当于这个方法，把参数也传进来
    public RunVideoAble(Webcam webcam0,Graphics g) {
        this.webcam0 = webcam0;
        this.g=g;
    }
/*public void setFlag(boolean flag){
        this.flag=flag;
}*/


        //考虑把三个方法都封装起来
    public void open(){
            flag=true;

        }
     public void close(){
            flag=false;
        }

    public BufferedImage getImage() {
        return image;
    }

    public  void setVideoOP(String videoOP){
        this.videoOP=videoOP;
    }
    //run方法执行完成，线程就自动结束（被杀死了）-唯一的让线程停下来的合法方法
    //rvt.stop的话是不合法的，任何线程执行完成之前一定要让run方法执行完成
    //如果启动，暂停一次，这是一线程；flag是0了，while就结束了
    //这个时候再启动，前面的线程就被杀死，重新按照前面，点击了启动，new一个摄像头并且启动，后来启动线程云云
    public void run(){

        while(flag){

            BufferedImage image0 = webcam0.getImage();


        if(videoOP.equals("原图")){
            image= VCFilter.drawImage(g,image0);

        }
        else if(videoOP.equals("灰度")){
          image=  VCFilter.grayImageFilter(g,image0);

        }
        else if(videoOP.equals("马赛克")){
            image=  VCFilter.drawImage_02(g,image0);

        }
        else if(videoOP.equals("底片")){
            image=  VCFilter.drawImage_03(g,image0);

        }
        else if(videoOP.equals("轮廓提取")){
            image=  VCFilter.drawImage_05(g,image0);

        }
        else if(videoOP.equals("美白")){
            image=  VCFilter.drawImage_06(g,image0);

        }
        else if(videoOP.equals("油画")){
            image=  VCFilter.drawImage_07(g,image0);

        }
        else if(videoOP.equals("二值化")){
            image=  VCFilter.drawImage_10(g,image0);

        }
       }
        //分支任务，死循环可以写进来
    }

    }

