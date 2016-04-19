using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NIMDemo
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            listBox1.DrawMode = DrawMode.OwnerDrawVariable;
            listBox1.DrawItem += ListBox1_DrawItem;
            listBox1.ItemHeight = 40;
            for (int i = 0; i < 10; i++)
            {
                Label l = new Label();
                l.Text = i.ToString();
                listBox1.Items.Add(i);
                
            }
        }

        private void ListBox1_DrawItem(object sender, DrawItemEventArgs e)
        {
            // Draw the background of the ListBox control for each item.
            e.DrawBackground();
            // Define the default color of the brush as black.
            Brush myBrush = Brushes.Black;

            // Determine the color of the brush to draw each item based  
            // on the index of the item to draw. 
            switch (e.Index)
            {
                case 0:
                    myBrush = Brushes.Red;
                    break;
                case 1:
                    myBrush = Brushes.Orange;
                    break;
                case 2:
                    myBrush = Brushes.Purple;
                    break;
            }
            Rectangle top = new Rectangle(e.Bounds.Location, new Size(e.Bounds.Size.Width, e.Bounds.Height/2));
            Rectangle b = new Rectangle(e.Bounds.Location.X, e.Bounds.Location.Y + e.Bounds.Height/2, e.Bounds.Size.Width, e.Bounds.Size.Height/2);
            // Draw the current item text based on the current Font  
            // and the custom brush settings.
            e.Graphics.DrawString(listBox1.Items[e.Index].ToString(),
                e.Font, myBrush, top, StringFormat.GenericDefault);
            e.Graphics.DrawString("this is a test",
                e.Font, Brushes.Blue, b, StringFormat.GenericDefault);
            // If the ListBox has focus, draw a focus rectangle around the selected item.
            e.DrawFocusRectangle();
        }
    }
}
