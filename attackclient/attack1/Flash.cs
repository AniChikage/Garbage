using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;


namespace attack1
{
    public partial class Flash : Form
    {
        public Flash()
        {
            InitializeComponent();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            MessageBox.Show("控制塔 油库 机库都不修复");
            Image image1 = Image.FromFile("G://boom1.gif");
            Image image2=  Image.FromFile("G://boom1.gif");
            Image image3 = Image.FromFile("G://boom1.gif");

            FrameDimension fd1 = new FrameDimension(image1.FrameDimensionsList[0]);
            FrameDimension fd2 = new FrameDimension(image2.FrameDimensionsList[0]);
            FrameDimension fd3 = new FrameDimension(image3.FrameDimensionsList[0]);

            int count = image1.GetFrameCount(fd1);
            

            Graphics g1 = this.panel3.CreateGraphics();
            Graphics g2 = this.panel2.CreateGraphics();
            Graphics g3 = this.panel1.CreateGraphics();

            while (true)
            {



                for (int i = 0; i < count; i++)
                {



                    //g.Clear(Color.White);

                    image1.SelectActiveFrame(fd1, i);
                    image2.SelectActiveFrame(fd2, i);
                    image3.SelectActiveFrame(fd3, i);

                    g1.DrawImage(image1, new Point(0,0));
                    g2.DrawImage(image2, new Point(0,0));
                    g3.DrawImage(image3, new Point(0,0));

                    System.Threading.Thread.Sleep(100);

                    Application.DoEvents();

                }

            }
            
        }

        private void button4_Click(object sender, EventArgs e)
        {
            MessageBox.Show("控制塔 油库 不修复；机库修复");
            Image image1 = Image.FromFile("G://boom1.gif");
            Image image2 = Image.FromFile("G://boom1.gif");
            Image image3 = Image.FromFile("G://boom1.gif");
            Image image4 = Image.FromFile("G://repair.gif");

            FrameDimension fd1 = new FrameDimension(image1.FrameDimensionsList[0]);
            FrameDimension fd2 = new FrameDimension(image2.FrameDimensionsList[0]);
            FrameDimension fd3 = new FrameDimension(image3.FrameDimensionsList[0]);
            FrameDimension fd4 = new FrameDimension(image4.FrameDimensionsList[0]);

            int count = image1.GetFrameCount(fd1);


            Graphics g1 = this.panel3.CreateGraphics();
            Graphics g2 = this.panel2.CreateGraphics();
            Graphics g3 = this.panel1.CreateGraphics();
            Graphics g4 = this.panel6.CreateGraphics();

            while (true)
            {



                for (int i = 0; i < count; i++)
                {



                    //g.Clear(Color.White);

                    image1.SelectActiveFrame(fd1, i);
                    image2.SelectActiveFrame(fd2, i);
                    image3.SelectActiveFrame(fd3, i);
                    image3.SelectActiveFrame(fd4, i);

                    g1.DrawImage(image1, new Point(0, 0));
                    g2.DrawImage(image2, new Point(0, 0));
                    g3.DrawImage(image3, new Point(0, 0));
                    g4.DrawImage(image3, new Point(0, 0));

                    System.Threading.Thread.Sleep(100);

                    Application.DoEvents();

                }

            }
        }
    }
}
