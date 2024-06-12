package VideoCapture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VCFilter {
    //灰度
    public static BufferedImage grayImageFilter(Graphics g, BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();
//图像拆分 每个像素点的单通道的RGB
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                int rgb=image.getRGB(i,j);
                //对这个像素点拿RGB值
                //位运算
                int r=(rgb>>16)&0xff;
                //在RGB中二进制数是从右往左写，所以B在低位，在最右边的八个二进制数
                //所以位运算移动16个，&0xff是几乎所有二进制转int型约定俗称的做法
                //可以处理从这个开始的8位，进行稳定的处理
                int green=(rgb>>8)&0xff;
                int b=rgb>>0xff;

                int gray=(int)(0.3*r+0.59*green+0.11*b);
                //rgb我们需要用二进制型，所以逐一把bgr赋过来

                rgb=(rgb&0xff000000)|(gray<<16)|(gray<<8)|gray;
                re.setRGB(i,j,rgb);
            }
        }
        g.drawImage(re,50,120,null);
        return re;
    }


    //原图
    public static BufferedImage drawImage(Graphics g, BufferedImage image){
        g.drawImage(image,50,120,null);
        return image;
    }


//马赛克,深深的发现自己写错了！！！！！！！
    //如果想要ij的RGB，之后又想10个10个一set，怎么办？
    public static BufferedImage drawImage_02(Graphics g, BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//现在遇到了一个难题，根本上是对ij去获得RGB，改变之后再把img返回，不过我之前是怎么写的？我懂了，之前我的
        Graphics bufferedg=re.getGraphics();//我懂了！！！此处新建一个画笔，画笔可以直接在img上面画

        for(int i=0;i<width;i+=10) {
            for(int j=0;j<height;j+=10) {
              int  rgb =image.getRGB(i,j);

                Color color=new Color(rgb);
                bufferedg.setColor(color);
                bufferedg.fillRect(i, j, 10, 10);}

            }
        //注意，前面是在虚拟上面画好一张图，之后还需要g来话！
        g.drawImage(re,50,120,null);


        return re;
    }
    //底片也成反片
    public static BufferedImage drawImage_03(Graphics g, BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();int rgb;
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//现在遇到了一个难题，根本上是对ij去获得RGB，改变之后再把img返回，不过我之前是怎么写的？我懂了，之前我的


        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++) {
                rgb =image.getRGB(i,j);

                Color color=new Color(rgb);

                int red=color.getRed();
                int green=color.getGreen();
                int blue =color.getBlue();

                Color ncolor=new Color(255-red,255-green,255-blue);
                int n=ncolor.getRGB();
			/*	Color color=new Color(rgb);
			g.setColor(color);
			g.fillRect(100+i, 100+j, 1, 1);*/
                re.setRGB(i, j, n);
            }
        }
        g.drawImage(re, 50, 120,null);
        return re;

    }
    //灰度
   /* public BufferedImage drawImage_04(int[][]imgarr,Graphics g) {
        BufferedImage img=new BufferedImage(imgarr.length,imgarr[0].length,BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<imgarr.length;i++) {
            for(int j=0;j<imgarr[0].length;j++) {
                int rgb=imgarr[i][j];
                Color color=new Color(rgb);

                int red=color.getRed();
                int green=color.getGreen();
                int blue =color.getBlue();
                int grey=(red+blue+green)/3;
                Color ncolor=new Color(grey,grey,grey);
                int n=ncolor.getRGB();
				g.setColor(color);
				g.fillRect(100+i, 100+j, 1, 1);
                img.setRGB(i, j, n);
            }
        }
        g.drawImage(img, 100, 100,null);
        return img;

    }*/


    //轮廓提取，拿像素点的颜色深度（用rgb取平均值）和他左上角像素点的颜色深度作比较 ，
    //颜色深度相差大（在原图上就是鲜明的轮廓）的画黑色，颜色深度相差小的画白色
    public static BufferedImage drawImage_05(Graphics g, BufferedImage image) {
        //返回的是缓冲图片
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//现在遇到了一个难题，根本上是对ij去获得RGB，改变之后再把img返回，不过我之前是怎么写的？我懂了，之前我的


        for(int i=0;i<width-2;i++) {
            for(int j=0;j<height-2;j++) {
                int rgb=image.getRGB(i,j);
                Color color=new Color(rgb);
                int red=color.getRed();
                int green=color.getGreen();
                int blue =color.getBlue();
                int grey=(red+blue+green)/3;

                int nrgb=image.getRGB(i+2,j+2);
                Color ncolor=new Color(nrgb);
                int nred=ncolor.getRed();
                int ngreen=ncolor.getGreen();
                int nblue =ncolor.getBlue();
                int ngrey=(nred+nblue+ngreen)/3;

                if(Math.abs(grey-ngrey)>15) {
                    Color B=new Color(0,0,0);
                    int n=B.getRGB();
                    re.setRGB(i, j, n);

                }else {
                    Color B=new Color(225,225,225);
                    int n=B.getRGB();
                    re.setRGB(i, j, n);
                }



            }
        }
        g.drawImage(re, 100, 120,null);
        return re;
    }
    //美白，其实想要整体偏白就是rgb，每个值都变大一些，但是存在本来就接近255的值容易溢出，所以用一个判断（可以用三目，也可以
    //用判断；构造新的颜色之后还要对这个颜色本来的深度做判断，grey小于100的说明不是脸部，就用原来的color；大于100则美白
    public static BufferedImage drawImage_06(Graphics g, BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//现在遇到了一个难题，根本上是对ij去获得RGB，改变之后再把img返回，不过我之前是怎么写的？我懂了，之前我的


        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++) {
                int rgb=image.getRGB(i,j);

                Color color=new Color(rgb);

                int red=color.getRed();
                int green=color.getGreen();
                int blue =color.getBlue();
                int grey=(red+blue+green)/3;
                int nred=red>220?255:red+30;
                int nblue=blue>220?255:blue+30;
                int ngreen=green>220?255:green+30;

                Color ncolor=new Color(nred,ngreen,nblue);


                if(grey<100) {

                    int n=color.getRGB();
                    re.setRGB(i, j, n);
                }else {

                    int n=ncolor.getRGB();
                    re.setRGB(i, j, n);
                }



            }
        }
        g.drawImage(re, 50, 120,null);
        return re;
    }


    //油画?(不知道怎么setRGB这一步,所以油画返回的img是什么？）
    public static BufferedImage drawImage_07(Graphics g, BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//现在遇到了一个难题，根本上是对ij去获得RGB，改变之后再把img返回，不过我之前是怎么写的？我懂了，之前我的
        Graphics bufferedg=re.getGraphics();//我懂了！！！此处新建一个画笔，画笔可以直接在img上面画

        for(int i=0;i<width;i+=10) {
            for(int j=0;j<height;j+=10) {
                int rgb=image.getRGB(i,j);
                Color color=new Color(rgb);
                Random random=new Random();
                int ran=random.nextInt(20)+10;
                 bufferedg.setColor(color);
                bufferedg.fillOval(i, j, ran, ran);}}
        //获取缓冲图片的画笔
        g.drawImage (re, 50, 120, null);
        return re;
    }


    //二值化之前，参数是arr[][]，现在改成了缓冲图片，所以方法体内部也要拍全部改，主要是取颜色的操作，还有遍历的边界值
    public static BufferedImage drawImage_10(Graphics g, BufferedImage image) {
        int width=image.getWidth();
        int height=image.getHeight();
        BufferedImage re=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//现在遇到了一个难题，根本上是对ij去获得RGB，改变之后再把img返回，不过我之前是怎么写的？我懂了，之前我的


        for(int i=0;i<width;i++) {
            for(int j=0;j<height;j++) {
                int rgb=image.getRGB(i,j);
                Color color=new Color(rgb);

                int red=color.getRed();
                int green=color.getGreen();
                int blue =color.getBlue();
                int grey=(red+blue+green)/3;



                if(grey<100) {
                    Color B=new Color(0,0,0);//什么都没有是黑色
                    int n=B.getRGB();
                    re.setRGB(i, j, n);
                }else {
                    Color B=new Color(255,255,255);
                    int n=B.getRGB();
                    re.setRGB(i, j, n);
                }



            }
        }
        g.drawImage(re, 50, 120,null);
        return re;

    }
  /*  public BufferedImage drawImage_11(int[][]imgarr2,Graphics g) {






        //第11个效果：溶图，设定一个新的color，然后color的第四个参数就是透明度，把这个放进img，然后drawImage

        BufferedImage img2=new BufferedImage(imgarr2.length,imgarr2[0].length,BufferedImage.TYPE_INT_ARGB);


        for(int i=0;i<imgarr2.length;i++) {
            for(int j=0;j<imgarr2[0].length;j++) {
                int rgb=imgarr2[i][j];
                Color color=new Color(rgb);
                int red=color.getRed();
                int blue=color.getBlue();
                int green=color.getGreen();
                Color ncolor=new Color(red,green,blue,50);
                int n=ncolor.getRGB();

                img2.setRGB(i, j, n);

            }
        }
        g.drawImage(img2, 100, 100,null);

        return img2;



    }*/
}
