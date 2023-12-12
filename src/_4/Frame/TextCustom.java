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

            String text = "Some Text";

            // Цвет текста
            g2d.setColor(Color.WHITE);

            // Получаем границы текста
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle2D textBounds = fm.getStringBounds(text, g2d);

            // Вычисляем координаты для отображения текста
            int x = (getWidth() - (int) textBounds.getWidth()) / 2;
            int y = (getHeight() + (int) textBounds.getHeight()) / 2;

            // Рисуем текст с черным контуром
            g2d.setColor(Color.BLACK);
            // Толщина контура
            int contourThickness = 2;

            // Рисуем текст с контуром
            g2d.drawString(text, x, y);

            // Рисуем контур
            for (int i = 1; i <= contourThickness; i++) {
                g2d.drawString(text, x - i, y); // Влево
                g2d.drawString(text, x + i, y); // Вправо
                g2d.drawString(text, x, y - i); // Вверх
                g2d.drawString(text, x, y + i); // Вниз
            }
            // Рисуем основной текст белым цветом
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, x, y);

            // Применяем эффект тени (1-ый вариант)
            // AffineTransform transform = new AffineTransform();
            // transform.setToRotation(Math.PI); // Поворот на 180 градусов
            // g2d.setTransform(transform);
            // g2d.setColor(new Color(192, 192, 192)); // Цвет тени
            // int shadowOffsetX = -250; // -250
            // g2d.drawString(text, -x + shadowOffsetX, -y); // Рисуем тень

            // Применяем эффект тени (2-ой вариант)
            AffineTransform originalTransform = g2d.getTransform();
            g2d.setColor(new Color(192, 192, 192)); // Цвет тени

            int xOffset = 14;
            int additionalSpacing = 0; // Дополнительный интервал между буквами тени

            // Отрисовываем текст тени с перевернутыми буквами
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

                    xOffset += letterWidth + additionalSpacing; // Учитываем ширину символа
                } else {
                    // Если символ - пробел, увеличиваем xOffset на ширину пробела
                    int spaceWidth = fm.stringWidth(" ");
                    xOffset += spaceWidth + additionalSpacing;
                }
            }

        }
    }
}
