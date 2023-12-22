package _4.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.awt.geom.Rectangle2D;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class TextCustom extends JFrame {
    public TextCustom() {
        setTitle("Текст с контурами и эффектом тени");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        add(new TextPanel());
    }

    class TextPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            setBackground(Color.WHITE);

            Graphics2D g2d = (Graphics2D) g;

            Font font = new Font("Arial", Font.PLAIN, 48);
            g2d.setFont(font);

            String text = "Some Text 123";

            // границы текста
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D textBounds = fm.getStringBounds(text, g2d);

            // координаты для отображения текста
            int x = (getWidth() - (int) textBounds.getWidth()) / 2;
            int y = (getHeight() + (int) textBounds.getHeight()) / 2;

            // рисуем текст с черным контуром
            g2d.setColor(Color.BLACK);
            int contourThickness = 2; // толщина контура

            // рисуем контур
            for (int i = 1; i <= contourThickness; i++) {
                g2d.drawString(text, x - i, y); // Влево
                g2d.drawString(text, x + i, y); // Вправо
                g2d.drawString(text, x, y - i); // Вверх
                g2d.drawString(text, x, y + i); // Вниз
            }
            // рисуем основной текст белым цветом (цветом фона)
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, x, y);

            // эффект тени
            AffineTransform originalTransform = g2d.getTransform();
            g2d.setColor(new Color(192, 192, 192)); // Цвет тени

            int xOffset = 14; // чтобы отображение было ровно под текстом

            // отрисовываем текст тени с перевернутыми буквами
            for (int i = 0; i < text.length(); i++) {
                char letter = text.charAt(i);
                String letterString = String.valueOf(letter);

                if (!Character.isWhitespace(letter)) {
                    int letterWidth = fm.stringWidth(letterString);

                    AffineTransform transform = new AffineTransform();
                    transform.translate(x + xOffset, y + 5);
                    transform.rotate(Math.PI); // Поворот на 180 градусов
                    g2d.setTransform(transform);

                    g2d.drawString(letterString, -letterWidth / 2, 0);

                    g2d.setTransform(originalTransform);

                    xOffset += letterWidth; // учитываем ширину символа
                } else {
                    // Если символ - пробел, увеличиваем xOffset на ширину пробела
                    int spaceWidth = fm.stringWidth(" ");
                    xOffset += spaceWidth;
                }
            }

        }
    }
}
